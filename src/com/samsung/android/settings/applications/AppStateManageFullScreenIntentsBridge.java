package com.samsung.android.settings.applications;

import android.app.AppOpsManager;
import android.content.pm.ApplicationInfo;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.applications.AppStateAppOpsBridge;
import com.android.settings.applications.AppStateBaseBridge;
import com.android.settingslib.applications.ApplicationsState;

import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AppStateManageFullScreenIntentsBridge extends AppStateAppOpsBridge {
    public final AppOpsManager mAppOpsManager;
    public static final String[] PM_PERMISSION = {"android.permission.USE_FULL_SCREEN_INTENT"};
    public static final AnonymousClass1 FILTER_USE_FULL_SCREEN_INTENT = new AnonymousClass1();

    public AppStateManageFullScreenIntentsBridge(
            FragmentActivity fragmentActivity,
            ApplicationsState applicationsState,
            AppStateBaseBridge.Callback callback) {
        super(
                fragmentActivity,
                applicationsState,
                callback,
                AppOpsManager.strOpToOp("android:use_full_screen_intent"),
                PM_PERMISSION);
        this.mAppOpsManager = (AppOpsManager) fragmentActivity.getSystemService("appops");
    }

    @Override // com.android.settings.applications.AppStateAppOpsBridge
    public final AppStateAppOpsBridge.PermissionState getPermissionInfo(int i, String str) {
        AppStateAppOpsBridge.PermissionState permissionInfo = super.getPermissionInfo(i, str);
        permissionInfo.appOpMode =
                this.mAppOpsManager.unsafeCheckOpNoThrow("android:use_full_screen_intent", i, str);
        return permissionInfo;
    }

    @Override // com.android.settings.applications.AppStateAppOpsBridge,
              // com.android.settings.applications.AppStateBaseBridge
    public final void loadAllExtraInfo() {
        super.loadAllExtraInfo();
        Iterator it = this.mAppSession.getAllApps().iterator();
        while (it.hasNext()) {
            ApplicationsState.AppEntry appEntry = (ApplicationsState.AppEntry) it.next();
            Object obj = appEntry.extraInfo;
            if (obj instanceof AppStateAppOpsBridge.PermissionState) {
                AppOpsManager appOpsManager = this.mAppOpsManager;
                ApplicationInfo applicationInfo = appEntry.info;
                ((AppStateAppOpsBridge.PermissionState) obj).appOpMode =
                        appOpsManager.unsafeCheckOpNoThrow(
                                "android:use_full_screen_intent",
                                applicationInfo.uid,
                                applicationInfo.packageName);
            }
        }
    }

    @Override // com.android.settings.applications.AppStateBaseBridge
    public final void updateExtraInfo(ApplicationsState.AppEntry appEntry, String str, int i) {
        appEntry.extraInfo = getPermissionInfo(i, str);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.applications.AppStateManageFullScreenIntentsBridge$1, reason: invalid class name */
    public final class AnonymousClass1 implements ApplicationsState.AppFilter {
        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final boolean filterApp(ApplicationsState.AppEntry appEntry) {
            return appEntry.extraInfo != null;
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final void init() {}
    }
}
