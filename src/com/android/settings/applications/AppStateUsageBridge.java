package com.android.settings.applications;

import android.app.AppGlobals;

import androidx.fragment.app.FragmentActivity;

import com.android.settingslib.applications.ApplicationsState;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppStateUsageBridge extends AppStateAppOpsBridge {
    public static final int[] APP_OPS_OP_CODES = {43, 95};
    public static final String[] PM_PERMISSIONS = {
        "android.permission.PACKAGE_USAGE_STATS", "android.permission.LOADER_USAGE_STATS"
    };
    public static final AnonymousClass1 FILTER_APP_USAGE = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class UsageState extends AppStateAppOpsBridge.PermissionState {
        public UsageState(AppStateAppOpsBridge.PermissionState permissionState) {
            super(permissionState.packageName, permissionState.userHandle);
            this.packageInfo = permissionState.packageInfo;
            this.appOpMode = permissionState.appOpMode;
            this.permissionDeclared = permissionState.permissionDeclared;
            this.staticPermissionGranted = permissionState.staticPermissionGranted;
        }
    }

    public AppStateUsageBridge(
            FragmentActivity fragmentActivity,
            ApplicationsState applicationsState,
            AppStateBaseBridge.Callback callback) {
        super(
                fragmentActivity,
                applicationsState,
                callback,
                APP_OPS_OP_CODES,
                PM_PERMISSIONS,
                AppGlobals.getPackageManager());
    }

    @Override // com.android.settings.applications.AppStateBaseBridge
    public final void updateExtraInfo(ApplicationsState.AppEntry appEntry, String str, int i) {
        appEntry.extraInfo = new UsageState(getPermissionInfo(i, str));
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.applications.AppStateUsageBridge$1, reason: invalid class name */
    public final class AnonymousClass1 implements ApplicationsState.AppFilter {
        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final boolean filterApp(ApplicationsState.AppEntry appEntry) {
            return (appEntry.info.packageName.equals("com.samsung.knox.securefolder")
                            || appEntry.extraInfo == null)
                    ? false
                    : true;
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final void init() {}
    }
}
