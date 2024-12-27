package com.android.settings.applications;

import android.content.Context;

import com.android.settingslib.applications.ApplicationsState;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppStateOverlayBridge extends AppStateAppOpsBridge {
    public static final String[] PM_PERMISSION = {"android.permission.SYSTEM_ALERT_WINDOW"};
    public static final AnonymousClass1 FILTER_SYSTEM_ALERT_WINDOW = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class OverlayState extends AppStateAppOpsBridge.PermissionState {
        public static final List DISABLE_PACKAGE_LIST;
        public final boolean controlEnabled;

        static {
            ArrayList arrayList = new ArrayList();
            DISABLE_PACKAGE_LIST = arrayList;
            arrayList.add("com.android.systemui");
        }

        public OverlayState(AppStateAppOpsBridge.PermissionState permissionState) {
            super(permissionState.packageName, permissionState.userHandle);
            this.packageInfo = permissionState.packageInfo;
            this.appOpMode = permissionState.appOpMode;
            this.permissionDeclared = permissionState.permissionDeclared;
            this.staticPermissionGranted = permissionState.staticPermissionGranted;
            this.controlEnabled = !((ArrayList) DISABLE_PACKAGE_LIST).contains(r0);
        }
    }

    public AppStateOverlayBridge(
            Context context,
            ApplicationsState applicationsState,
            AppStateBaseBridge.Callback callback) {
        super(context, applicationsState, callback, 24, PM_PERMISSION);
    }

    @Override // com.android.settings.applications.AppStateBaseBridge
    public final void updateExtraInfo(ApplicationsState.AppEntry appEntry, String str, int i) {
        appEntry.extraInfo = new OverlayState(getPermissionInfo(i, str));
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.applications.AppStateOverlayBridge$1, reason: invalid class name */
    public final class AnonymousClass1 implements ApplicationsState.AppFilter {
        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final boolean filterApp(ApplicationsState.AppEntry appEntry) {
            return appEntry.extraInfo != null;
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final void init() {}
    }
}
