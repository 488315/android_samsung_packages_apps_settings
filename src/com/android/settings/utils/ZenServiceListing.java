package com.android.settings.utils;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;

import androidx.fragment.app.BackStackRecord$$ExternalSyntheticOutline0;

import com.android.settings.notification.zen.AbstractZenModeAutomaticRulePreferenceController;
import com.android.settings.notification.zen.ZenRuleInfo;
import com.android.settings.notification.zen.ZenRuleSelectionDialog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenServiceListing {
    public final ManagedServiceSettings.Config mConfig;
    public final Context mContext;
    public final NotificationManager mNm;
    public final Set mApprovedComponents = new ArraySet();
    public final List mZenCallbacks = new ArrayList();

    public ZenServiceListing(Context context, ManagedServiceSettings.Config config) {
        this.mContext = context;
        this.mConfig = config;
        this.mNm = (NotificationManager) context.getSystemService("notification");
    }

    public final void reloadApprovedServices() {
        ZenRuleInfo zenRuleInfo;
        Bundle bundle;
        ((ArraySet) this.mApprovedComponents).clear();
        List enabledNotificationListenerPackages =
                this.mNm.getEnabledNotificationListenerPackages();
        ArrayList arrayList = new ArrayList();
        PackageManager packageManager = this.mContext.getPackageManager();
        int currentUser = ActivityManager.getCurrentUser();
        ManagedServiceSettings.Config config = this.mConfig;
        List queryIntentServicesAsUser =
                packageManager.queryIntentServicesAsUser(
                        new Intent(config.intentAction), 132, currentUser);
        int size = queryIntentServicesAsUser.size();
        for (int i = 0; i < size; i++) {
            ServiceInfo serviceInfo = ((ResolveInfo) queryIntentServicesAsUser.get(i)).serviceInfo;
            String str = serviceInfo.permission;
            String str2 = config.permission;
            if (str2.equals(str)) {
                arrayList.add(serviceInfo);
            } else {
                StringBuilder sb = new StringBuilder("Skipping ");
                sb.append(config.noun);
                sb.append(" service ");
                sb.append(serviceInfo.packageName);
                sb.append("/");
                Slog.w(
                        config.tag,
                        BackStackRecord$$ExternalSyntheticOutline0.m(
                                sb,
                                serviceInfo.name,
                                ": it does not require the permission ",
                                str2));
            }
        }
        List queryIntentActivitiesAsUser =
                this.mContext
                        .getPackageManager()
                        .queryIntentActivitiesAsUser(
                                new Intent(config.configIntentAction),
                                129,
                                ActivityManager.getCurrentUser());
        int size2 = queryIntentActivitiesAsUser.size();
        for (int i2 = 0; i2 < size2; i2++) {
            arrayList.add(((ResolveInfo) queryIntentActivitiesAsUser.get(i2)).activityInfo);
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ComponentInfo componentInfo = (ComponentInfo) it.next();
            String packageName = componentInfo.getComponentName().getPackageName();
            if (this.mNm.isNotificationPolicyAccessGrantedForPackage(packageName)
                    || enabledNotificationListenerPackages.contains(packageName)) {
                ((ArraySet) this.mApprovedComponents).add(componentInfo);
            }
        }
        if (((ArraySet) this.mApprovedComponents).isEmpty()) {
            return;
        }
        Iterator it2 = ((ArrayList) this.mZenCallbacks).iterator();
        while (it2.hasNext()) {
            ZenRuleSelectionDialog.AnonymousClass2 anonymousClass2 =
                    (ZenRuleSelectionDialog.AnonymousClass2) it2.next();
            Set set = this.mApprovedComponents;
            anonymousClass2.getClass();
            if (ZenRuleSelectionDialog.DEBUG) {
                Log.d("ZenRuleSelectionDialog", "Reloaded: count=" + ((ArraySet) set).size());
            }
            TreeSet treeSet = new TreeSet(ZenRuleSelectionDialog.RULE_TYPE_COMPARATOR);
            Iterator it3 = ((ArraySet) set).iterator();
            while (it3.hasNext()) {
                ComponentInfo componentInfo2 = (ComponentInfo) it3.next();
                boolean z = ZenRuleSelectionDialog.DEBUG;
                if (componentInfo2 != null && (bundle = componentInfo2.metaData) != null) {
                    boolean z2 = componentInfo2 instanceof ServiceInfo;
                    String string =
                            z2
                                    ? bundle.getString("android.service.zen.automatic.ruleType")
                                    : bundle.getString("android.service.zen.automatic.ruleType");
                    ComponentName settingsActivity =
                            AbstractZenModeAutomaticRulePreferenceController.getSettingsActivity(
                                    null, null, componentInfo2);
                    if (string != null && !string.trim().isEmpty() && settingsActivity != null) {
                        zenRuleInfo = new ZenRuleInfo();
                        zenRuleInfo.ruleInstanceLimit = -1;
                        zenRuleInfo.serviceComponent =
                                z2
                                        ? new ComponentName(
                                                componentInfo2.packageName, componentInfo2.name)
                                        : null;
                        zenRuleInfo.settingsAction =
                                "android.settings.ZEN_MODE_EXTERNAL_RULE_SETTINGS";
                        zenRuleInfo.title = string;
                        zenRuleInfo.packageName = componentInfo2.packageName;
                        zenRuleInfo.configurationActivity = settingsActivity;
                        zenRuleInfo.packageLabel = componentInfo2.applicationInfo.loadLabel(null);
                        zenRuleInfo.ruleInstanceLimit =
                                z2
                                        ? componentInfo2.metaData.getInt(
                                                "android.service.zen.automatic.ruleInstanceLimit",
                                                -1)
                                        : componentInfo2.metaData.getInt(
                                                "android.service.zen.automatic.ruleInstanceLimit",
                                                -1);
                        if (zenRuleInfo == null && zenRuleInfo.configurationActivity != null) {
                            throw null;
                        }
                    }
                }
                zenRuleInfo = null;
                if (zenRuleInfo == null) {}
            }
            anonymousClass2.this$0.getClass();
            Iterator it4 = treeSet.iterator();
            while (it4.hasNext()) {
                try {
                    String str3 = ((ZenRuleInfo) it4.next()).packageName;
                    throw null;
                } catch (PackageManager.NameNotFoundException unused) {
                }
            }
        }
    }
}
