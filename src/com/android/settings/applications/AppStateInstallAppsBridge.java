package com.android.settings.applications;

import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;

import com.android.internal.util.ArrayUtils;
import com.android.settingslib.applications.ApplicationsState;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppStateInstallAppsBridge extends AppStateBaseBridge {
    public static final AnonymousClass1 FILTER_APP_SOURCES = new AnonymousClass1();
    public final AppOpsManager mAppOpsManager;
    public final IPackageManager mIpm;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class InstallAppsState {
        public int appOpMode;
        public boolean permissionRequested;

        public final boolean canInstallApps() {
            return this.appOpMode == 0;
        }

        public final boolean isPotentialAppSource() {
            return this.appOpMode != 3 || this.permissionRequested;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[permissionRequested: " + this.permissionRequested);
            sb.append(", appOpMode: " + this.appOpMode);
            sb.append("]");
            return sb.toString();
        }
    }

    public AppStateInstallAppsBridge(
            Context context,
            ApplicationsState applicationsState,
            AppStateBaseBridge.Callback callback) {
        super(applicationsState, callback);
        this.mIpm = AppGlobals.getPackageManager();
        this.mAppOpsManager = (AppOpsManager) context.getSystemService("appops");
    }

    public final InstallAppsState createInstallAppsStateFor(int i, String str) {
        boolean z;
        InstallAppsState installAppsState = new InstallAppsState();
        installAppsState.appOpMode = 3;
        try {
            z =
                    ArrayUtils.contains(
                            this.mIpm.getAppOpPermissionPackages(
                                    "android.permission.REQUEST_INSTALL_PACKAGES",
                                    UserHandle.getUserId(i)),
                            str);
        } catch (RemoteException unused) {
            Log.e("AppStateInstallAppsBridge", "PackageManager dead. Cannot get permission info");
            z = false;
        }
        installAppsState.permissionRequested = z;
        installAppsState.appOpMode = this.mAppOpsManager.checkOpNoThrow(66, i, str);
        return installAppsState;
    }

    @Override // com.android.settings.applications.AppStateBaseBridge
    public final void loadAllExtraInfo() {
        ArrayList allApps = this.mAppSession.getAllApps();
        for (int i = 0; i < allApps.size(); i++) {
            ApplicationsState.AppEntry appEntry = (ApplicationsState.AppEntry) allApps.get(i);
            ApplicationInfo applicationInfo = appEntry.info;
            updateExtraInfo(appEntry, applicationInfo.packageName, applicationInfo.uid);
        }
    }

    @Override // com.android.settings.applications.AppStateBaseBridge
    public final void updateExtraInfo(ApplicationsState.AppEntry appEntry, String str, int i) {
        appEntry.extraInfo = createInstallAppsStateFor(i, str);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.applications.AppStateInstallAppsBridge$1, reason: invalid class name */
    public final class AnonymousClass1 implements ApplicationsState.AppFilter {
        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final boolean filterApp(ApplicationsState.AppEntry appEntry) {
            Object obj = appEntry.extraInfo;
            if (obj == null || !(obj instanceof InstallAppsState)) {
                return false;
            }
            return ((InstallAppsState) obj).isPotentialAppSource();
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final void init() {}
    }
}
