package com.samsung.android.settings.applications;

import android.app.AppOpsManager;
import android.content.pm.ApplicationInfo;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.applications.AppStateAppOpsBridge;
import com.android.settings.applications.AppStateBaseBridge;
import com.android.settingslib.applications.ApplicationsState;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AppStateMediaRoutingAppsBridge extends AppStateAppOpsBridge {
    public static final AnonymousClass1 FILTER_MEDIA_ROUTING = new AnonymousClass1();
    public final AppOpsManager mAppOpsManager;

    public AppStateMediaRoutingAppsBridge(
            FragmentActivity fragmentActivity,
            ApplicationsState applicationsState,
            AppStateBaseBridge.Callback callback) {
        super(
                fragmentActivity,
                applicationsState,
                callback,
                AppOpsManager.strOpToOp("android:media_routing_control"),
                new String[] {"android.permission.MEDIA_ROUTING_CONTROL"});
        this.mAppOpsManager =
                (AppOpsManager) fragmentActivity.getSystemService(AppOpsManager.class);
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
        AppStateAppOpsBridge.PermissionState permissionInfo = getPermissionInfo(i, str);
        permissionInfo.appOpMode =
                this.mAppOpsManager.unsafeCheckOpNoThrow("android:media_routing_control", i, str);
        appEntry.extraInfo = permissionInfo;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.applications.AppStateMediaRoutingAppsBridge$1, reason: invalid class name */
    public final class AnonymousClass1 implements ApplicationsState.AppFilter {
        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final boolean filterApp(ApplicationsState.AppEntry appEntry) {
            return appEntry.extraInfo != null;
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final void init() {}
    }
}
