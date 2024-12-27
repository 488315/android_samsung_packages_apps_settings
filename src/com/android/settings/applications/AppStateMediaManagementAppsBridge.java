package com.android.settings.applications;

import android.app.AppOpsManager;
import android.content.pm.ApplicationInfo;

import androidx.fragment.app.FragmentActivity;

import com.android.settingslib.applications.ApplicationsState;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppStateMediaManagementAppsBridge extends AppStateAppOpsBridge {
    public static final AnonymousClass1 FILTER_MEDIA_MANAGEMENT_APPS = new AnonymousClass1();
    public final AppOpsManager mAppOpsManager;

    public AppStateMediaManagementAppsBridge(
            FragmentActivity fragmentActivity,
            ApplicationsState applicationsState,
            AppStateBaseBridge.Callback callback) {
        super(
                fragmentActivity,
                applicationsState,
                callback,
                AppOpsManager.strOpToOp("android:manage_media"),
                new String[] {"android.permission.MANAGE_MEDIA"});
        this.mAppOpsManager =
                (AppOpsManager) fragmentActivity.getSystemService(AppOpsManager.class);
    }

    public final AppStateAppOpsBridge.PermissionState createPermissionState(int i, String str) {
        AppStateAppOpsBridge.PermissionState permissionInfo = getPermissionInfo(i, str);
        permissionInfo.appOpMode =
                this.mAppOpsManager.unsafeCheckOpNoThrow("android:manage_media", i, str);
        return permissionInfo;
    }

    @Override // com.android.settings.applications.AppStateAppOpsBridge,
              // com.android.settings.applications.AppStateBaseBridge
    public final void loadAllExtraInfo() {
        super.loadAllExtraInfo();
        ArrayList allApps = this.mAppSession.getAllApps();
        int size = allApps.size();
        for (int i = 0; i < size; i++) {
            ApplicationsState.AppEntry appEntry = (ApplicationsState.AppEntry) allApps.get(i);
            if (appEntry.extraInfo instanceof AppStateAppOpsBridge.PermissionState) {
                ApplicationInfo applicationInfo = appEntry.info;
                updateExtraInfo(appEntry, applicationInfo.packageName, applicationInfo.uid);
            }
        }
    }

    @Override // com.android.settings.applications.AppStateBaseBridge
    public final void updateExtraInfo(ApplicationsState.AppEntry appEntry, String str, int i) {
        appEntry.extraInfo = createPermissionState(i, str);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.applications.AppStateMediaManagementAppsBridge$1, reason: invalid class name */
    public final class AnonymousClass1 implements ApplicationsState.AppFilter {
        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final boolean filterApp(ApplicationsState.AppEntry appEntry) {
            return appEntry.extraInfo != null;
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final void init() {}
    }
}
