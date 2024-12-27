package com.android.settingslib.users;

import android.app.AppGlobals;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;

import com.android.settings.users.AppRestrictionsFragment;
import com.android.settingslib.Utils;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.connection.moreconnection.EmergencyAlertsPreferenceController;
import com.samsung.android.settings.voiceinput.VoiceInputUtils;
import com.sec.ims.settings.ImsProfile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppRestrictionsHelper {
    public final Context mContext;
    public final HashMap mExcludeAppInfoList;
    public final HashMap mExcludeDownloadableAppInfoList;
    public final IPackageManager mIPm;
    public final Injector mInjector;
    public final PackageManager mPackageManager;
    public final HashMap mRelatedPackageList;
    public final boolean mRestrictedProfile;
    public final HashMap mSelectedPackages;
    public final UserHandle mUser;
    public List mVisibleApps;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AppLabelComparator implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return ((SelectableAppInfo) obj)
                    .activityName
                    .toString()
                    .toLowerCase()
                    .compareTo(((SelectableAppInfo) obj2).activityName.toString().toLowerCase());
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    class Injector {
        public Context mContext;
        public UserHandle mUser;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnDisableUiForPackageListener {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SelectableAppInfo {
        public CharSequence activityName;
        public CharSequence appName;
        public Drawable icon;
        public String packageName;
        public SelectableAppInfo primaryEntry;

        public final String toString() {
            return this.packageName
                    + ": appName="
                    + ((Object) this.appName)
                    + "; activityName="
                    + ((Object) this.activityName)
                    + "; icon="
                    + this.icon
                    + "; primaryEntry="
                    + this.primaryEntry;
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public AppRestrictionsHelper(android.content.Context r2, android.os.UserHandle r3) {
        /*
            r1 = this;
            com.android.settingslib.users.AppRestrictionsHelper$Injector r0 = new com.android.settingslib.users.AppRestrictionsHelper$Injector
            r0.<init>()
            r0.mContext = r2
            r0.mUser = r3
            r1.<init>(r0)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.users.AppRestrictionsHelper.<init>(android.content.Context,"
                    + " android.os.UserHandle):void");
    }

    public final void addSystemApps(List list, Intent intent, Set set) {
        ApplicationInfo applicationInfo;
        ApplicationInfo applicationInfo2;
        PackageManager packageManager = this.mPackageManager;
        for (ResolveInfo resolveInfo : packageManager.queryIntentActivities(intent, 8704)) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (activityInfo != null && (applicationInfo = activityInfo.applicationInfo) != null) {
                String str = activityInfo.packageName;
                int i = applicationInfo.flags;
                if ((i & 1) != 0 || (i & 128) != 0) {
                    if (!((HashSet) set).contains(str)) {
                        ActivityInfo activityInfo2 = resolveInfo.activityInfo;
                        String str2 = activityInfo2.packageName;
                        String str3 = activityInfo2.name;
                        if ("com.android.contacts.activities.PeopleActivity".equals(str3)
                                || "com.samsung.android.contacts.contactslist.PeopleActivity"
                                        .equals(str3)
                                || (!this.mExcludeAppInfoList.containsValue(str3)
                                        && !this.mExcludeAppInfoList.containsKey(str2))) {
                            try {
                                Bundle bundle =
                                        packageManager.getApplicationInfo(str, 128).metaData;
                                if (bundle != null
                                        && bundle.getBoolean(
                                                "com.samsung.android.multiuser.install_only_owner",
                                                false)) {
                                    Log.d(
                                            "AppRestrictionsHelper",
                                            "isOnlyForOwner() true - " + str);
                                }
                            } catch (Exception unused) {
                            }
                            int applicationEnabledSetting =
                                    packageManager.getApplicationEnabledSetting(str);
                            if (applicationEnabledSetting == 4 || applicationEnabledSetting == 2) {
                                try {
                                    applicationInfo2 =
                                            this.mIPm.getApplicationInfo(
                                                    str, 0, this.mUser.getIdentifier());
                                } catch (RemoteException unused2) {
                                    applicationInfo2 = null;
                                }
                                if (applicationInfo2 != null
                                        && (applicationInfo2.flags & 8388608) != 0) {}
                            }
                            SelectableAppInfo selectableAppInfo = new SelectableAppInfo();
                            ActivityInfo activityInfo3 = resolveInfo.activityInfo;
                            selectableAppInfo.packageName = activityInfo3.packageName;
                            selectableAppInfo.appName =
                                    activityInfo3.applicationInfo.loadLabel(packageManager);
                            selectableAppInfo.icon =
                                    resolveInfo.activityInfo.loadIcon(packageManager, true, 1);
                            CharSequence loadLabel =
                                    resolveInfo.activityInfo.loadLabel(packageManager);
                            selectableAppInfo.activityName = loadLabel;
                            if (loadLabel == null) {
                                selectableAppInfo.activityName = selectableAppInfo.appName;
                            }
                            ((ArrayList) list).add(selectableAppInfo);
                        }
                    }
                }
            }
        }
    }

    public final void applyUserAppState(
            String str, boolean z, OnDisableUiForPackageListener onDisableUiForPackageListener) {
        int identifier = this.mUser.getIdentifier();
        try {
            if (z) {
                ApplicationInfo applicationInfo =
                        this.mIPm.getApplicationInfo(str, 4194304L, identifier);
                if (applicationInfo == null
                        || !applicationInfo.enabled
                        || (applicationInfo.flags & 8388608) == 0) {
                    this.mIPm.installExistingPackageAsUser(
                            str, this.mUser.getIdentifier(), 4194304, 0, (List) null);
                }
                if (applicationInfo == null
                        || (1 & applicationInfo.privateFlags) == 0
                        || (applicationInfo.flags & 8388608) == 0) {
                    return;
                }
                this.mIPm.setApplicationHiddenSettingAsUser(str, false, identifier);
                return;
            }
            if (this.mIPm.getApplicationInfo(str, 0L, identifier) != null) {
                if (this.mRestrictedProfile) {
                    this.mIPm.setApplicationHiddenSettingAsUser(str, true, identifier);
                    return;
                }
                AppRestrictionsFragment appRestrictionsFragment =
                        (AppRestrictionsFragment) onDisableUiForPackageListener;
                appRestrictionsFragment.getClass();
                AppRestrictionsFragment.AppRestrictionsPreference appRestrictionsPreference =
                        (AppRestrictionsFragment.AppRestrictionsPreference)
                                appRestrictionsFragment.findPreference(
                                        AppRestrictionsFragment.getKeyForPackage$1(str));
                if (appRestrictionsPreference != null) {
                    appRestrictionsPreference.setEnabled(false);
                }
                this.mIPm.setApplicationHiddenSettingAsUser(str, true, identifier);
            }
        } catch (RemoteException unused) {
        }
    }

    public final void applyUserAppsStates(
            OnDisableUiForPackageListener onDisableUiForPackageListener) {
        if (!this.mRestrictedProfile && this.mUser.getIdentifier() != UserHandle.myUserId()) {
            Log.e(
                    "AppRestrictionsHelper",
                    "Cannot apply application restrictions on another user!");
            return;
        }
        for (Map.Entry entry : this.mSelectedPackages.entrySet()) {
            applyUserAppState(
                    (String) entry.getKey(),
                    ((Boolean) entry.getValue()).booleanValue(),
                    onDisableUiForPackageListener);
        }
    }

    public final void fetchAndMergeApps() {
        this.mVisibleApps = new ArrayList();
        PackageManager packageManager = this.mPackageManager;
        IPackageManager iPackageManager = this.mIPm;
        HashSet hashSet = new HashSet();
        Injector injector = this.mInjector;
        for (InputMethodInfo inputMethodInfo :
                ((InputMethodManager) injector.mContext.getSystemService("input_method"))
                        .getInputMethodListAsUser(injector.mUser.getIdentifier())) {
            try {
                if (inputMethodInfo.isDefault(this.mContext)) {
                    ApplicationInfo applicationInfo =
                            this.mPackageManager.getPackageInfo(inputMethodInfo.getPackageName(), 0)
                                    .applicationInfo;
                    if (applicationInfo != null) {
                        int i = applicationInfo.flags;
                        if ((i & 1) != 0 || (i & 128) != 0) {
                            hashSet.add(inputMethodInfo.getPackageName());
                        }
                    }
                }
            } catch (PackageManager.NameNotFoundException | Resources.NotFoundException unused) {
            }
        }
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        addSystemApps(this.mVisibleApps, intent, hashSet);
        addSystemApps(
                this.mVisibleApps,
                new Intent("android.appwidget.action.APPWIDGET_UPDATE"),
                hashSet);
        for (ApplicationInfo applicationInfo2 : packageManager.getInstalledApplications(4194304)) {
            int i2 = applicationInfo2.flags;
            if ((8388608 & i2) != 0) {
                if ((i2 & 1) == 0 && (i2 & 128) == 0) {
                    if (!this.mExcludeDownloadableAppInfoList.containsKey(
                            applicationInfo2.packageName)) {
                        SelectableAppInfo selectableAppInfo = new SelectableAppInfo();
                        selectableAppInfo.packageName = applicationInfo2.packageName;
                        CharSequence loadLabel = applicationInfo2.loadLabel(packageManager);
                        selectableAppInfo.appName = loadLabel;
                        selectableAppInfo.activityName = loadLabel;
                        selectableAppInfo.icon = applicationInfo2.loadIcon(packageManager, true, 1);
                        ((ArrayList) this.mVisibleApps).add(selectableAppInfo);
                    }
                } else {
                    try {
                        PackageInfo packageInfo =
                                packageManager.getPackageInfo(applicationInfo2.packageName, 0);
                        if (this.mRestrictedProfile
                                && packageInfo.requiredAccountType != null
                                && packageInfo.restrictedAccountType == null) {
                            this.mSelectedPackages.put(applicationInfo2.packageName, Boolean.FALSE);
                        }
                    } catch (PackageManager.NameNotFoundException unused2) {
                    }
                }
            }
        }
        List<ApplicationInfo> list = null;
        try {
            ParceledListSlice installedApplications =
                    iPackageManager.getInstalledApplications(8192L, this.mUser.getIdentifier());
            if (installedApplications != null) {
                list = installedApplications.getList();
            }
        } catch (RemoteException unused3) {
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if (list != null) {
            for (ApplicationInfo applicationInfo3 : list) {
                int i3 = applicationInfo3.flags;
                if ((i3 & 8388608) != 0 && (i3 & 1) == 0 && (i3 & 128) == 0) {
                    SelectableAppInfo selectableAppInfo2 = new SelectableAppInfo();
                    selectableAppInfo2.packageName = applicationInfo3.packageName;
                    CharSequence loadLabel2 = applicationInfo3.loadLabel(packageManager);
                    selectableAppInfo2.appName = loadLabel2;
                    selectableAppInfo2.activityName = loadLabel2;
                    selectableAppInfo2.icon = applicationInfo3.loadIcon(packageManager);
                    ((ArrayList) this.mVisibleApps).add(selectableAppInfo2);
                }
            }
        }
        Collections.sort(this.mVisibleApps, new AppLabelComparator());
        HashSet hashSet2 = new HashSet();
        for (int size = ((ArrayList) this.mVisibleApps).size() - 1; size >= 0; size--) {
            SelectableAppInfo selectableAppInfo3 =
                    (SelectableAppInfo) ((ArrayList) this.mVisibleApps).get(size);
            String str =
                    selectableAppInfo3.packageName
                            + "+"
                            + ((Object) selectableAppInfo3.activityName);
            if (TextUtils.isEmpty(selectableAppInfo3.packageName)
                    || TextUtils.isEmpty(selectableAppInfo3.activityName)
                    || !hashSet2.contains(str)) {
                hashSet2.add(str);
            } else {
                ((ArrayList) this.mVisibleApps).remove(size);
            }
        }
        HashMap hashMap = new HashMap();
        Iterator it = ((ArrayList) this.mVisibleApps).iterator();
        while (it.hasNext()) {
            SelectableAppInfo selectableAppInfo4 = (SelectableAppInfo) it.next();
            if (hashMap.containsKey(selectableAppInfo4.packageName)) {
                selectableAppInfo4.primaryEntry =
                        (SelectableAppInfo) hashMap.get(selectableAppInfo4.packageName);
            } else {
                hashMap.put(selectableAppInfo4.packageName, selectableAppInfo4);
            }
        }
    }

    public final void setPackageSelected(String str, boolean z) {
        this.mSelectedPackages.put(str, Boolean.valueOf(z));
        String str2 = (String) this.mRelatedPackageList.get(str);
        if (str2 != null) {
            this.mSelectedPackages.put(str2, Boolean.valueOf(z));
        }
    }

    public AppRestrictionsHelper(Injector injector) {
        HashMap hashMap = new HashMap();
        this.mExcludeAppInfoList = hashMap;
        new HashMap();
        HashMap hashMap2 = new HashMap();
        this.mExcludeDownloadableAppInfoList = hashMap2;
        this.mSelectedPackages = new HashMap();
        HashMap hashMap3 = new HashMap();
        this.mRelatedPackageList = hashMap3;
        HashMap hashMap4 = new HashMap();
        this.mInjector = injector;
        Context context = injector.mContext;
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
        this.mIPm = AppGlobals.getPackageManager();
        UserHandle userHandle = injector.mUser;
        this.mUser = userHandle;
        this.mRestrictedProfile =
                ((UserManager) injector.mContext.getSystemService(UserManager.class))
                        .getUserInfo(userHandle.getIdentifier())
                        .isRestricted();
        hashMap.put("com.android.documentsui", ApnSettings.MVNO_NONE);
        hashMap.put(ImsProfile.PDN_WIFI, "com.android.settings.Settings$WifiApSettingsActivity");
        hashMap.put(
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_CONTACTS_CONFIG_PACKAGE_NAME"),
                ApnSettings.MVNO_NONE);
        hashMap.put("com.sec.knox.app.container", ApnSettings.MVNO_NONE);
        hashMap.put("com.sec.yosemite.tab", ApnSettings.MVNO_NONE);
        hashMap.put("com.sec.knox.bridge", ApnSettings.MVNO_NONE);
        hashMap.put("com.samsung.knox.rcp.components", ApnSettings.MVNO_NONE);
        hashMap.put("com.sec.knox.shortcuti", ApnSettings.MVNO_NONE);
        hashMap.put("com.sec.knox.shortcutii", ApnSettings.MVNO_NONE);
        hashMap.put("com.samsung.android.knox.containeragent", ApnSettings.MVNO_NONE);
        hashMap.put("com.samsung.android.knox.containercore", ApnSettings.MVNO_NONE);
        hashMap.put("com.sec.knox.shortcutsms", ApnSettings.MVNO_NONE);
        hashMap.put("com.sec.knox.switcher", ApnSettings.MVNO_NONE);
        hashMap.put("com.samsung.knox.kss", ApnSettings.MVNO_NONE);
        hashMap.put("com.sec.knox.switchknoxI", ApnSettings.MVNO_NONE);
        hashMap.put("com.sec.knox.switchknoxII", ApnSettings.MVNO_NONE);
        hashMap.put("com.sec.knox.knoxmodeswitcher", ApnSettings.MVNO_NONE);
        hashMap.put("com.sec.knox.containeragent2", ApnSettings.MVNO_NONE);
        hashMap.put("com.sec.knox.knoxsetupwizardclient", ApnSettings.MVNO_NONE);
        hashMap.put("Samsung KNOX apps", "com.sec.android.app.samsungapps.KnoxAppsMainActivity");
        hashMap.put("com.android.stk", ApnSettings.MVNO_NONE);
        hashMap.put("com.samsung.android.game.gametools", ApnSettings.MVNO_NONE);
        hashMap.put("com.samsung.android.game.gamehome", ApnSettings.MVNO_NONE);
        hashMap.put("com.samsung.knox.securefolder", ApnSettings.MVNO_NONE);
        hashMap.put("com.samsung.android.app.spage", ApnSettings.MVNO_NONE);
        hashMap.put("com.samsung.android.authfw", ApnSettings.MVNO_NONE);
        hashMap.put("com.samsung.android.scloud", ApnSettings.MVNO_NONE);
        hashMap.put("com.samsung.android.aremoji", ApnSettings.MVNO_NONE);
        hashMap.put("com.samsung.android.app.routines", ApnSettings.MVNO_NONE);
        hashMap.put(VoiceInputUtils.BIXBY_PACKAGE_NAME, ApnSettings.MVNO_NONE);
        if (!Utils.isVoiceCapable(context)) {
            hashMap.put("com.samsung.android.dialer", ApnSettings.MVNO_NONE);
        }
        hashMap.put("com.sec.android.easyMover", ApnSettings.MVNO_NONE);
        hashMap.put("com.samsung.android.galaxycontinuity", ApnSettings.MVNO_NONE);
        hashMap.put(EmergencyAlertsPreferenceController.AOSP_CMAS_PACKAGE, ApnSettings.MVNO_NONE);
        hashMap.put("com.samsung.android.samsungpass", ApnSettings.MVNO_NONE);
        hashMap.put("com.samsung.android.oneconnect", ApnSettings.MVNO_NONE);
        hashMap.put("com.samsung.android.app.tips", ApnSettings.MVNO_NONE);
        hashMap3.put("com.hancom.androidpc.viewer.launcher", "com.hancom.androidpc.appwidget");
        hashMap3.put("com.samsung.everglades.video", "com.sec.android.app.videoplayer");
        hashMap3.put(
                "com.samsung.android.app.episodes", "com.samsung.android.app.storyalbumwidget");
        hashMap3.put(
                "com.sec.android.app.clockpackage", "com.sec.android.widgetapp.dualclockdigital");
        hashMap4.put("com.sec.android.gallery3d", "com.sec.android.app.camera");
        hashMap2.put("com.sec.android.app.kidshome", ApnSettings.MVNO_NONE);
        hashMap2.put("com.sec.android.easyMover", ApnSettings.MVNO_NONE);
        hashMap2.put("com.samsung.android.galaxycontinuity", ApnSettings.MVNO_NONE);
        hashMap2.put("com.samsung.sree", ApnSettings.MVNO_NONE);
        hashMap2.put("com.samsung.android.oneconnect", ApnSettings.MVNO_NONE);
        hashMap2.put("com.samsung.android.app.tips", ApnSettings.MVNO_NONE);
    }
}
