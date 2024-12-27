package com.android.settings.wifi;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;

import com.android.internal.util.ArrayUtils;
import com.android.settings.applications.AppStateAppOpsBridge;
import com.android.settings.applications.AppStateBaseBridge;
import com.android.settingslib.applications.ApplicationsState;

import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppStateChangeWifiStateBridge extends AppStateAppOpsBridge {
    public static final String[] PM_PERMISSIONS = {"android.permission.CHANGE_WIFI_STATE"};
    public static final AnonymousClass1 FILTER_CHANGE_WIFI_STATE = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class WifiSettingsState extends AppStateAppOpsBridge.PermissionState {
        public WifiSettingsState(AppStateAppOpsBridge.PermissionState permissionState) {
            super(permissionState.packageName, permissionState.userHandle);
            this.packageInfo = permissionState.packageInfo;
            this.appOpMode = permissionState.appOpMode;
            this.permissionDeclared = permissionState.permissionDeclared;
            this.staticPermissionGranted = permissionState.staticPermissionGranted;
        }
    }

    public AppStateChangeWifiStateBridge(
            Context context,
            ApplicationsState applicationsState,
            AppStateBaseBridge.Callback callback) {
        super(context, applicationsState, callback, 71, PM_PERMISSIONS);
    }

    @Override // com.android.settings.applications.AppStateAppOpsBridge,
              // com.android.settings.applications.AppStateBaseBridge
    public final void loadAllExtraInfo() {
        Iterator it = this.mAppSession.getAllApps().iterator();
        while (it.hasNext()) {
            ApplicationsState.AppEntry appEntry = (ApplicationsState.AppEntry) it.next();
            ApplicationInfo applicationInfo = appEntry.info;
            updateExtraInfo(appEntry, applicationInfo.packageName, applicationInfo.uid);
        }
    }

    @Override // com.android.settings.applications.AppStateBaseBridge
    public final void updateExtraInfo(ApplicationsState.AppEntry appEntry, String str, int i) {
        appEntry.extraInfo = new WifiSettingsState(getPermissionInfo(i, str));
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.wifi.AppStateChangeWifiStateBridge$1, reason: invalid class name */
    public final class AnonymousClass1 implements ApplicationsState.AppFilter {
        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final boolean filterApp(ApplicationsState.AppEntry appEntry) {
            Object obj = appEntry.extraInfo;
            if (obj == null || !(obj instanceof WifiSettingsState)) {
                return false;
            }
            WifiSettingsState wifiSettingsState = (WifiSettingsState) obj;
            PackageInfo packageInfo = wifiSettingsState.packageInfo;
            if (packageInfo == null
                    || !ArrayUtils.contains(
                            packageInfo.requestedPermissions,
                            "android.permission.NETWORK_SETTINGS")) {
                return wifiSettingsState.permissionDeclared;
            }
            return false;
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final void init() {}
    }
}
