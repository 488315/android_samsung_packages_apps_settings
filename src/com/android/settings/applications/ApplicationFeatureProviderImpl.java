package com.android.settings.applications;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.location.LocationManager;
import android.os.RemoteException;
import android.os.SystemConfigManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.telecom.DefaultDialerManager;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.webkit.Flags;
import android.webkit.IWebViewUpdateService;
import android.webkit.WebViewFactory;
import android.webkit.WebViewProviderInfo;
import android.webkit.WebViewUpdateManager;

import androidx.compose.ui.text.SaversKt$LocaleSaver$2$$ExternalSyntheticOutline0;

import com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlagsImpl;
import com.android.internal.telephony.SmsApplication;
import com.android.settings.R;
import com.android.settings.enterprise.ApplicationListPreferenceController;
import com.android.settings.webview.WebViewUpdateServiceWrapper;

import com.sec.ims.gls.GlsIntent;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ApplicationFeatureProviderImpl implements ApplicationFeatureProvider {
    public final Context mContext;
    public final DevicePolicyManager mDpm;
    public final PackageManager mPm;
    public final IPackageManager mPms;
    public final SystemConfigManager mSystemConfigManager;
    public final UserManager mUm;
    public final WebViewUpdateServiceWrapper mWebViewUpdateServiceWrapper;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CurrentUserAndManagedProfileAppWithAdminGrantedPermissionsCounter
            extends AppCounter {
        public final ApplicationFeatureProvider.NumberOfAppsCallback mCallback;
        public final DevicePolicyManager mDevicePolicyManager;
        public final IPackageManager mPackageManagerService;
        public final String[] mPermissions;

        public CurrentUserAndManagedProfileAppWithAdminGrantedPermissionsCounter(
                Context context,
                String[] strArr,
                PackageManager packageManager,
                IPackageManager iPackageManager,
                DevicePolicyManager devicePolicyManager,
                ApplicationFeatureProvider.NumberOfAppsCallback numberOfAppsCallback) {
            super(context, packageManager, new FeatureFlagsImpl());
            this.mPermissions = strArr;
            this.mPackageManagerService = iPackageManager;
            this.mDevicePolicyManager = devicePolicyManager;
            this.mCallback = numberOfAppsCallback;
        }

        @Override // com.android.settings.applications.AppCounter
        public final boolean includeInCount(ApplicationInfo applicationInfo) {
            return includeInCount(
                    this.mPermissions,
                    this.mDevicePolicyManager,
                    this.mPm,
                    this.mPackageManagerService,
                    applicationInfo);
        }

        @Override // com.android.settings.applications.AppCounter
        public final void onCountComplete(int i) {
            this.mCallback.onNumberOfAppsResult(i);
        }

        public static boolean includeInCount(
                String[] strArr,
                DevicePolicyManager devicePolicyManager,
                PackageManager packageManager,
                IPackageManager iPackageManager,
                ApplicationInfo applicationInfo) {
            if (applicationInfo.targetSdkVersion >= 23) {
                for (String str : strArr) {
                    if (devicePolicyManager.getPermissionGrantState(
                                    null, applicationInfo.packageName, str)
                            == 1) {
                        return true;
                    }
                }
                return false;
            }
            if (packageManager.getInstallReason(
                            applicationInfo.packageName,
                            new UserHandle(UserHandle.getUserId(applicationInfo.uid)))
                    != 1) {
                return false;
            }
            try {
                for (String str2 : strArr) {
                    if (iPackageManager.checkUidPermission(str2, applicationInfo.uid) == 0) {
                        return true;
                    }
                }
            } catch (RemoteException unused) {
            }
            return false;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CurrentUserAndManagedProfilePolicyInstalledAppCounter
            extends InstalledAppCounter {
        public ApplicationFeatureProvider.NumberOfAppsCallback mCallback;

        @Override // com.android.settings.applications.AppCounter
        public final void onCountComplete(int i) {
            this.mCallback.onNumberOfAppsResult(i);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CurrentUserAppWithAdminGrantedPermissionsLister extends AppLister {
        public final ApplicationListPreferenceController mCallback;
        public final DevicePolicyManager mDevicePolicyManager;
        public final IPackageManager mPackageManagerService;
        public final String[] mPermissions;

        public CurrentUserAppWithAdminGrantedPermissionsLister(
                String[] strArr,
                PackageManager packageManager,
                IPackageManager iPackageManager,
                DevicePolicyManager devicePolicyManager,
                UserManager userManager,
                ApplicationListPreferenceController applicationListPreferenceController) {
            super(packageManager, userManager);
            this.mPermissions = strArr;
            this.mPackageManagerService = iPackageManager;
            this.mDevicePolicyManager = devicePolicyManager;
            this.mCallback = applicationListPreferenceController;
        }

        @Override // com.android.settings.applications.AppLister
        public final boolean includeInCount(ApplicationInfo applicationInfo) {
            return CurrentUserAndManagedProfileAppWithAdminGrantedPermissionsCounter.includeInCount(
                    this.mPermissions,
                    this.mDevicePolicyManager,
                    this.mPm,
                    this.mPackageManagerService,
                    applicationInfo);
        }

        @Override // com.android.settings.applications.AppLister
        public final void onAppListBuilt(List list) {
            this.mCallback.onListOfAppsResult(list);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CurrentUserPolicyInstalledAppLister extends AppLister {
        public ApplicationListPreferenceController mCallback;

        @Override // com.android.settings.applications.AppLister
        public final boolean includeInCount(ApplicationInfo applicationInfo) {
            return InstalledAppCounter.includeInCount(1, this.mPm, applicationInfo);
        }

        @Override // com.android.settings.applications.AppLister
        public final void onAppListBuilt(List list) {
            this.mCallback.onListOfAppsResult(list);
        }
    }

    public ApplicationFeatureProviderImpl(
            Context context,
            PackageManager packageManager,
            IPackageManager iPackageManager,
            DevicePolicyManager devicePolicyManager) {
        WebViewUpdateServiceWrapper webViewUpdateServiceWrapper = new WebViewUpdateServiceWrapper();
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mPm = packageManager;
        this.mPms = iPackageManager;
        this.mDpm = devicePolicyManager;
        this.mUm = UserManager.get(applicationContext);
        this.mWebViewUpdateServiceWrapper = webViewUpdateServiceWrapper;
        this.mSystemConfigManager =
                (SystemConfigManager) context.getSystemService(SystemConfigManager.class);
    }

    public ComponentInfo findEuiccService(PackageManager packageManager) {
        String str;
        List<ResolveInfo> queryIntentServices =
                packageManager.queryIntentServices(
                        new Intent("android.service.euicc.EuiccService"), 269484096);
        ComponentInfo componentInfo = null;
        if (queryIntentServices != null) {
            int i = Integer.MIN_VALUE;
            for (ResolveInfo resolveInfo : queryIntentServices) {
                ComponentInfo componentInfo2 = resolveInfo.activityInfo;
                if (componentInfo2 == null
                        && (componentInfo2 = resolveInfo.serviceInfo) == null
                        && (componentInfo2 = resolveInfo.providerInfo) == null) {
                    throw new IllegalStateException("Missing ComponentInfo!");
                }
                String str2 = componentInfo2.packageName;
                if (packageManager.checkPermission(
                                "android.permission.WRITE_EMBEDDED_SUBSCRIPTIONS", str2)
                        != 0) {
                    SaversKt$LocaleSaver$2$$ExternalSyntheticOutline0.m(
                            "Package ",
                            str2,
                            " does not declare WRITE_EMBEDDED_SUBSCRIPTIONS",
                            "AppFeatureProviderImpl");
                } else {
                    if (componentInfo2 instanceof ServiceInfo) {
                        str = ((ServiceInfo) componentInfo2).permission;
                    } else {
                        if (!(componentInfo2 instanceof ActivityInfo)) {
                            throw new IllegalArgumentException(
                                    "Can only verify services/activities");
                        }
                        str = ((ActivityInfo) componentInfo2).permission;
                    }
                    if (TextUtils.equals(str, "android.permission.BIND_EUICC_SERVICE")) {
                        IntentFilter intentFilter = resolveInfo.filter;
                        if (intentFilter == null || intentFilter.getPriority() == 0) {
                            SaversKt$LocaleSaver$2$$ExternalSyntheticOutline0.m(
                                    "Package ",
                                    str2,
                                    " does not specify a priority",
                                    "AppFeatureProviderImpl");
                        } else if (resolveInfo.filter.getPriority() > i) {
                            int priority = resolveInfo.filter.getPriority();
                            ComponentInfo componentInfo3 = resolveInfo.activityInfo;
                            if (componentInfo3 == null
                                    && (componentInfo3 = resolveInfo.serviceInfo) == null
                                    && (componentInfo3 = resolveInfo.providerInfo) == null) {
                                throw new IllegalStateException("Missing ComponentInfo!");
                            }
                            ComponentInfo componentInfo4 = componentInfo3;
                            i = priority;
                            componentInfo = componentInfo4;
                        } else {
                            continue;
                        }
                    } else {
                        SaversKt$LocaleSaver$2$$ExternalSyntheticOutline0.m(
                                "Package ",
                                str2,
                                " does not require the BIND_EUICC_SERVICE permission",
                                "AppFeatureProviderImpl");
                    }
                }
            }
        }
        if (componentInfo == null) {
            Log.w("AppFeatureProviderImpl", "No valid EuiccService implementation found");
        }
        return componentInfo;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0030, code lost:

       r5 = new com.android.settings.applications.UserAppInfo(r2, r6.applicationInfo);
    */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x003b, code lost:

       if (r1.add(r5) == false) goto L30;
    */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x003d, code lost:

       r0.add(r5);
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List findPersistentPreferredActivities(
            int r8, android.content.Intent[] r9) {
        /*
            r7 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            android.util.ArraySet r1 = new android.util.ArraySet
            r1.<init>()
            android.os.UserManager r2 = r7.mUm
            android.content.pm.UserInfo r2 = r2.getUserInfo(r8)
            int r3 = r9.length
            r4 = 0
        L12:
            if (r4 >= r3) goto L43
            r5 = r9[r4]
            android.content.pm.IPackageManager r6 = r7.mPms     // Catch: android.os.RemoteException -> L40
            android.content.pm.ResolveInfo r5 = r6.findPersistentPreferredActivity(r5, r8)     // Catch: android.os.RemoteException -> L40
            if (r5 == 0) goto L40
            android.content.pm.ActivityInfo r6 = r5.activityInfo     // Catch: android.os.RemoteException -> L40
            if (r6 == 0) goto L23
            goto L2e
        L23:
            android.content.pm.ServiceInfo r6 = r5.serviceInfo     // Catch: android.os.RemoteException -> L40
            if (r6 == 0) goto L28
            goto L2e
        L28:
            android.content.pm.ProviderInfo r6 = r5.providerInfo     // Catch: android.os.RemoteException -> L40
            if (r6 == 0) goto L2d
            goto L2e
        L2d:
            r6 = 0
        L2e:
            if (r6 == 0) goto L40
            com.android.settings.applications.UserAppInfo r5 = new com.android.settings.applications.UserAppInfo     // Catch: android.os.RemoteException -> L40
            android.content.pm.ApplicationInfo r6 = r6.applicationInfo     // Catch: android.os.RemoteException -> L40
            r5.<init>(r2, r6)     // Catch: android.os.RemoteException -> L40
            boolean r6 = r1.add(r5)     // Catch: android.os.RemoteException -> L40
            if (r6 == 0) goto L40
            r0.add(r5)     // Catch: android.os.RemoteException -> L40
        L40:
            int r4 = r4 + 1
            goto L12
        L43:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.applications.ApplicationFeatureProviderImpl.findPersistentPreferredActivities(int,"
                    + " android.content.Intent[]):java.util.List");
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:24:0x0062 -> B:19:0x0063). Please report as a decompilation issue!!! */
    public final Set getKeepEnabledPackages() {
        WebViewProviderInfo webViewProviderInfo;
        ArraySet arraySet = new ArraySet();
        String defaultDialerApplication =
                DefaultDialerManager.getDefaultDialerApplication(this.mContext);
        if (!TextUtils.isEmpty(defaultDialerApplication)) {
            arraySet.add(defaultDialerApplication);
        }
        ComponentName defaultSmsApplication =
                SmsApplication.getDefaultSmsApplication(this.mContext, true);
        if (defaultSmsApplication != null) {
            arraySet.add(defaultSmsApplication.getPackageName());
        }
        ComponentInfo findEuiccService = findEuiccService(this.mPm);
        if (findEuiccService != null) {
            arraySet.add(findEuiccService.packageName);
        }
        if (Flags.updateServiceV2()) {
            this.mWebViewUpdateServiceWrapper.getClass();
            try {
            } catch (Exception e) {
                Log.e(
                        "WVUSWrapper",
                        "Exception when trying to fetch default WebView package Name",
                        e);
            }
            if (Flags.updateServiceIpcWrapper()) {
                WebViewUpdateManager webViewUpdateManager = WebViewUpdateManager.getInstance();
                if (webViewUpdateManager != null) {
                    webViewProviderInfo = webViewUpdateManager.getDefaultWebViewPackage();
                }
                webViewProviderInfo = null;
            } else {
                IWebViewUpdateService updateService = WebViewFactory.getUpdateService();
                if (updateService != null) {
                    webViewProviderInfo = updateService.getDefaultWebViewPackage();
                }
                webViewProviderInfo = null;
            }
            String str = webViewProviderInfo != null ? webViewProviderInfo.packageName : null;
            if (str != null) {
                arraySet.add(str);
            }
        }
        ArraySet arraySet2 = new ArraySet();
        arraySet2.add(this.mContext.getString(R.string.config_settingsintelligence_package_name));
        arraySet2.add(this.mContext.getString(R.string.config_package_installer_package_name));
        if (this.mPm.getWellbeingPackageName() != null) {
            arraySet2.add(this.mPm.getWellbeingPackageName());
        }
        arraySet.addAll((Collection) arraySet2);
        String extraLocationControllerPackage =
                ((LocationManager) this.mContext.getSystemService(GlsIntent.Extras.EXTRA_LOCATION))
                        .getExtraLocationControllerPackage();
        if (extraLocationControllerPackage != null) {
            arraySet.add(extraLocationControllerPackage);
        }
        arraySet.addAll(this.mSystemConfigManager.getPreventUserDisablePackages());
        arraySet.add("com.google.android.apps.setupwizard.searchselector");
        return arraySet;
    }
}
