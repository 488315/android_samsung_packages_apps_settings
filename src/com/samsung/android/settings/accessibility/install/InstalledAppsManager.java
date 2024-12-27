package com.samsung.android.settings.accessibility.install;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.view.accessibility.AccessibilityManager;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.android.settings.accessibility.AccessibilitySettingsContentObserver;
import com.android.settings.accessibility.RestrictedPreferenceHelper;
import com.android.settingslib.RestrictedPreference;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.util.SemLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class InstalledAppsManager implements DefaultLifecycleObserver {
    public final Context context;
    public final Handler handler;
    public OnInstalledAppsChangedListener listener;
    public final AnonymousClass1 packageMonitorReceiver;
    public final List preferences;
    public final AccessibilitySettingsContentObserver settingsContentObserver;
    public static final Set EXCLUDE_PACKAGE_SET =
            Set.of("com.samsung.accessibility", KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
    public static final Set EXCLUDE_COMPONENT_NAME_SET =
            Set.of(
                    AccessibilityConstant.COMPONENT_NAME_SAMSUNG_TALKBACK,
                    AccessibilityConstant.COMPONENT_NAME_GOOGLE_TALKBACK,
                    AccessibilityConstant.COMPONENT_NAME_BIXBY_AGENT,
                    AccessibilityConstant.COMPONENT_NAME_SPEAK_KEYBOARD_INPUT_ALOUD);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.accessibility.install.InstalledAppsManager$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        public AnonymousClass1() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                switch (action) {
                    case "android.intent.action.PACKAGE_CHANGED":
                    case "android.intent.action.PACKAGE_REMOVED":
                    case "android.intent.action.PACKAGE_ADDED":
                        InstalledAppsManager.this.handler.postDelayed(
                                new Runnable() { // from class:
                                                 // com.samsung.android.settings.accessibility.install.InstalledAppsManager$1$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        InstalledAppsManager.this.updateRestrictedPreferences();
                                    }
                                },
                                1000L);
                        break;
                }
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnInstalledAppsChangedListener {
        void onAppsChanged(List list);
    }

    public InstalledAppsManager(Context context, Lifecycle lifecycle) {
        Handler handler = new Handler(Looper.getMainLooper());
        this.handler = handler;
        this.packageMonitorReceiver = new AnonymousClass1();
        AccessibilitySettingsContentObserver accessibilitySettingsContentObserver =
                new AccessibilitySettingsContentObserver(handler);
        this.settingsContentObserver = accessibilitySettingsContentObserver;
        this.preferences = new ArrayList();
        lifecycle.addObserver(this);
        this.context = context;
        AccessibilitySettingsContentObserver.ContentObserverCallback contentObserverCallback =
                new AccessibilitySettingsContentObserver
                        .ContentObserverCallback() { // from class:
                                                     // com.samsung.android.settings.accessibility.install.InstalledAppsManager$$ExternalSyntheticLambda1
                    @Override // com.android.settings.accessibility.AccessibilitySettingsContentObserver.ContentObserverCallback
                    public final void onChange(String str) {
                        InstalledAppsManager.this.updateRestrictedPreferences();
                    }
                };
        ((HashMap) accessibilitySettingsContentObserver.mUrisToCallback)
                .put(Collections.emptyList(), contentObserverCallback);
    }

    public static boolean checkValidApp(RestrictedPreference restrictedPreference) {
        ComponentName unflattenFromString =
                ComponentName.unflattenFromString(restrictedPreference.getKey());
        if (unflattenFromString != null) {
            return (EXCLUDE_PACKAGE_SET.contains(unflattenFromString.getPackageName())
                            || EXCLUDE_COMPONENT_NAME_SET.contains(unflattenFromString))
                    ? false
                    : true;
        }
        SemLog.i(
                "InstalledAppsManager",
                "checkValidApp - key is wrong. key :" + restrictedPreference.getKey());
        return false;
    }

    public static List getInstalledAccessibilityList(Context context) {
        AccessibilityManager accessibilityManager = AccessibilityManager.getInstance(context);
        RestrictedPreferenceHelper restrictedPreferenceHelper =
                new RestrictedPreferenceHelper(context);
        List createAccessibilityActivityPreferenceList =
                restrictedPreferenceHelper.createAccessibilityActivityPreferenceList(
                        accessibilityManager.getInstalledAccessibilityShortcutListAsUser(
                                context, UserHandle.myUserId()));
        List createAccessibilityServicePreferenceList =
                restrictedPreferenceHelper.createAccessibilityServicePreferenceList(
                        new ArrayList(accessibilityManager.getInstalledAccessibilityServiceList()));
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) createAccessibilityActivityPreferenceList).iterator();
        while (it.hasNext()) {
            RestrictedPreference restrictedPreference = (RestrictedPreference) it.next();
            if (checkValidApp(restrictedPreference)) {
                arrayList.add(restrictedPreference);
            }
        }
        Iterator it2 = ((ArrayList) createAccessibilityServicePreferenceList).iterator();
        while (it2.hasNext()) {
            RestrictedPreference restrictedPreference2 = (RestrictedPreference) it2.next();
            if (checkValidApp(restrictedPreference2)) {
                arrayList.add(restrictedPreference2);
            }
        }
        return arrayList;
    }

    public final List getNewAppList() {
        ArrayList<String> stringArrayList;
        ArrayList<String> stringArrayList2;
        SharedPreferences accessibilitySharedPreferences =
                SecAccessibilityUtils.getAccessibilitySharedPreferences(this.context);
        if (!accessibilitySharedPreferences.getBoolean("installedAppMigrated", false)) {
            HashSet hashSet = new HashSet();
            Bundle bundle = new Bundle();
            bundle.putString("prefKey", "SERVICE_SET_KEY");
            bundle.putString("valueType", "stringSet");
            ContentResolver contentResolver = this.context.getContentResolver();
            Uri uri = AccessibilityConstant.URI_ACCESSIBILITY_PROVIDER;
            Bundle call =
                    contentResolver.call(
                            uri,
                            "GET_a11ysharedpreferences",
                            "INSTALLED_ACCESSIBILITY_BADGE_MANAGER",
                            bundle);
            if (call != null && (stringArrayList2 = call.getStringArrayList("result")) != null) {
                hashSet.addAll(stringArrayList2);
            }
            Bundle bundle2 = new Bundle();
            bundle2.putString("prefKey", "SHORTCUT_SET_KEY");
            bundle2.putString("valueType", "stringSet");
            Bundle call2 =
                    this.context
                            .getContentResolver()
                            .call(
                                    uri,
                                    "GET_a11ysharedpreferences",
                                    "INSTALLED_ACCESSIBILITY_BADGE_MANAGER",
                                    bundle2);
            if (call2 != null && (stringArrayList = call2.getStringArrayList("result")) != null) {
                hashSet.addAll(stringArrayList);
            }
            SecAccessibilityUtils.getAccessibilitySharedPreferences(this.context)
                    .edit()
                    .putStringSet("alreadyInstalledApps", hashSet)
                    .apply();
            accessibilitySharedPreferences.edit().putBoolean("installedAppMigrated", true).apply();
        }
        final Set<String> stringSet =
                accessibilitySharedPreferences.getStringSet("alreadyInstalledApps", new HashSet());
        return (List)
                this.preferences.stream()
                        .filter(
                                new Predicate() { // from class:
                                                  // com.samsung.android.settings.accessibility.install.InstalledAppsManager$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        return !stringSet.contains(
                                                ((RestrictedPreference) obj).getKey());
                                    }
                                })
                        .collect(Collectors.toList());
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public final void onCreate(LifecycleOwner lifecycleOwner) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addDataScheme("package");
        this.context.registerReceiver(this.packageMonitorReceiver, intentFilter);
        this.settingsContentObserver.register(this.context.getContentResolver());
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public final void onDestroy(LifecycleOwner lifecycleOwner) {
        this.context.unregisterReceiver(this.packageMonitorReceiver);
        ContentResolver contentResolver = this.context.getContentResolver();
        AccessibilitySettingsContentObserver accessibilitySettingsContentObserver =
                this.settingsContentObserver;
        accessibilitySettingsContentObserver.getClass();
        contentResolver.unregisterContentObserver(accessibilitySettingsContentObserver);
    }

    public final void updateRestrictedPreferences() {
        ((ArrayList) this.preferences).clear();
        ((ArrayList) this.preferences).addAll(getInstalledAccessibilityList(this.context));
        OnInstalledAppsChangedListener onInstalledAppsChangedListener = this.listener;
        if (onInstalledAppsChangedListener != null) {
            onInstalledAppsChangedListener.onAppsChanged(this.preferences);
        }
    }
}
