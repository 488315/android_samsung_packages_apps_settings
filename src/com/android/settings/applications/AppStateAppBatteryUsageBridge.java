package com.android.settings.applications;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settingslib.applications.ApplicationsState;
import com.android.settingslib.fuelgauge.PowerAllowlistBackend;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppStateAppBatteryUsageBridge extends AppStateBaseBridge {
    public static final boolean DEBUG = Build.IS_DEBUGGABLE;
    public static final AnonymousClass1 FILTER_BATTERY_OPTIMIZED_APPS;
    public static final AnonymousClass1 FILTER_BATTERY_RESTRICTED_APPS;
    public static final AnonymousClass1 FILTER_BATTERY_UNRESTRICTED_APPS;

    @VisibleForTesting static final int MODE_OPTIMIZED = 2;

    @VisibleForTesting static final int MODE_RESTRICTED = 3;

    @VisibleForTesting static final int MODE_UNKNOWN = 0;

    @VisibleForTesting static final int MODE_UNRESTRICTED = 1;

    @VisibleForTesting AppOpsManager mAppOpsManager;

    @VisibleForTesting Context mContext;

    @VisibleForTesting PowerAllowlistBackend mPowerAllowlistBackend;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AppBatteryUsageDetails {
        public int mMode;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.applications.AppStateAppBatteryUsageBridge$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.settings.applications.AppStateAppBatteryUsageBridge$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.settings.applications.AppStateAppBatteryUsageBridge$1] */
    static {
        final int i = 0;
        FILTER_BATTERY_UNRESTRICTED_APPS =
                new ApplicationsState
                        .AppFilter() { // from class:
                                       // com.android.settings.applications.AppStateAppBatteryUsageBridge.1
                    @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
                    public final boolean filterApp(ApplicationsState.AppEntry appEntry) {
                        switch (i) {
                            case 0:
                                return AppStateAppBatteryUsageBridge.getAppBatteryUsageDetailsMode(
                                                appEntry)
                                        == 1;
                            case 1:
                                return AppStateAppBatteryUsageBridge.getAppBatteryUsageDetailsMode(
                                                appEntry)
                                        == 2;
                            default:
                                return AppStateAppBatteryUsageBridge.getAppBatteryUsageDetailsMode(
                                                appEntry)
                                        == 3;
                        }
                    }

                    @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
                    public final void init() {
                        int i2 = i;
                    }

                    private final void
                            init$com$android$settings$applications$AppStateAppBatteryUsageBridge$1() {}

                    private final void
                            init$com$android$settings$applications$AppStateAppBatteryUsageBridge$2() {}

                    private final void
                            init$com$android$settings$applications$AppStateAppBatteryUsageBridge$3() {}
                };
        final int i2 = 1;
        FILTER_BATTERY_OPTIMIZED_APPS =
                new ApplicationsState
                        .AppFilter() { // from class:
                                       // com.android.settings.applications.AppStateAppBatteryUsageBridge.1
                    @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
                    public final boolean filterApp(ApplicationsState.AppEntry appEntry) {
                        switch (i2) {
                            case 0:
                                return AppStateAppBatteryUsageBridge.getAppBatteryUsageDetailsMode(
                                                appEntry)
                                        == 1;
                            case 1:
                                return AppStateAppBatteryUsageBridge.getAppBatteryUsageDetailsMode(
                                                appEntry)
                                        == 2;
                            default:
                                return AppStateAppBatteryUsageBridge.getAppBatteryUsageDetailsMode(
                                                appEntry)
                                        == 3;
                        }
                    }

                    @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
                    public final void init() {
                        int i22 = i2;
                    }

                    private final void
                            init$com$android$settings$applications$AppStateAppBatteryUsageBridge$1() {}

                    private final void
                            init$com$android$settings$applications$AppStateAppBatteryUsageBridge$2() {}

                    private final void
                            init$com$android$settings$applications$AppStateAppBatteryUsageBridge$3() {}
                };
        final int i3 = 2;
        FILTER_BATTERY_RESTRICTED_APPS =
                new ApplicationsState
                        .AppFilter() { // from class:
                                       // com.android.settings.applications.AppStateAppBatteryUsageBridge.1
                    @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
                    public final boolean filterApp(ApplicationsState.AppEntry appEntry) {
                        switch (i3) {
                            case 0:
                                return AppStateAppBatteryUsageBridge.getAppBatteryUsageDetailsMode(
                                                appEntry)
                                        == 1;
                            case 1:
                                return AppStateAppBatteryUsageBridge.getAppBatteryUsageDetailsMode(
                                                appEntry)
                                        == 2;
                            default:
                                return AppStateAppBatteryUsageBridge.getAppBatteryUsageDetailsMode(
                                                appEntry)
                                        == 3;
                        }
                    }

                    @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
                    public final void init() {
                        int i22 = i3;
                    }

                    private final void
                            init$com$android$settings$applications$AppStateAppBatteryUsageBridge$1() {}

                    private final void
                            init$com$android$settings$applications$AppStateAppBatteryUsageBridge$2() {}

                    private final void
                            init$com$android$settings$applications$AppStateAppBatteryUsageBridge$3() {}
                };
    }

    public AppStateAppBatteryUsageBridge(
            FragmentActivity fragmentActivity,
            ApplicationsState applicationsState,
            AppStateBaseBridge.Callback callback) {
        super(applicationsState, callback);
        this.mContext = fragmentActivity;
        this.mAppOpsManager =
                (AppOpsManager) fragmentActivity.getSystemService(AppOpsManager.class);
        this.mPowerAllowlistBackend = PowerAllowlistBackend.getInstance(this.mContext);
    }

    @VisibleForTesting
    public static int getAppBatteryUsageDetailsMode(ApplicationsState.AppEntry appEntry) {
        Object obj;
        if (appEntry == null
                || (obj = appEntry.extraInfo) == null
                || !(obj instanceof AppBatteryUsageDetails)) {
            return 0;
        }
        return ((AppBatteryUsageDetails) obj).mMode;
    }

    @Override // com.android.settings.applications.AppStateBaseBridge
    public final void loadAllExtraInfo() {
        boolean z = DEBUG;
        if (z) {
            Log.d("AppStateAppBatteryUsageBridge", "Start loadAllExtraInfo()");
        }
        this.mAppSession.getAllApps().stream()
                .forEach(
                        new Consumer() { // from class:
                                         // com.android.settings.applications.AppStateAppBatteryUsageBridge$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                AppStateAppBatteryUsageBridge appStateAppBatteryUsageBridge =
                                        AppStateAppBatteryUsageBridge.this;
                                ApplicationsState.AppEntry appEntry =
                                        (ApplicationsState.AppEntry) obj;
                                appStateAppBatteryUsageBridge.getClass();
                                ApplicationInfo applicationInfo = appEntry.info;
                                appStateAppBatteryUsageBridge.updateExtraInfo(
                                        appEntry, applicationInfo.packageName, applicationInfo.uid);
                            }
                        });
        if (z) {
            Log.d("AppStateAppBatteryUsageBridge", "End loadAllExtraInfo()");
        }
    }

    @Override // com.android.settings.applications.AppStateBaseBridge
    public final void updateExtraInfo(ApplicationsState.AppEntry appEntry, String str, int i) {
        int i2;
        boolean isAllowlisted = this.mPowerAllowlistBackend.isAllowlisted(i, str);
        int checkOpNoThrow = this.mAppOpsManager.checkOpNoThrow(70, i, str);
        boolean z = DEBUG;
        String str2 = ApnSettings.MVNO_NONE;
        if (checkOpNoThrow == 1 && !isAllowlisted) {
            i2 = 3;
            if (z) {
                str2 = "RESTRICTED";
            }
        } else if (checkOpNoThrow == 0) {
            int i3 = isAllowlisted ? 1 : 2;
            if (z) {
                str2 = isAllowlisted ? "UNRESTRICTED" : "OPTIMIZED";
            }
            i2 = i3;
        } else {
            i2 = 0;
        }
        if (z) {
            Log.d("AppStateAppBatteryUsageBridge", "Pkg: " + str + ", mode: " + str2);
        }
        AppBatteryUsageDetails appBatteryUsageDetails = new AppBatteryUsageDetails();
        appBatteryUsageDetails.mMode = i2;
        appEntry.extraInfo = appBatteryUsageDetails;
    }
}
