package com.android.settings.applications;

import android.content.pm.ApplicationInfo;

import androidx.fragment.app.FragmentActivity;

import com.android.settingslib.applications.ApplicationsState;
import com.android.settingslib.fuelgauge.PowerAllowlistBackend;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppStatePowerBridge extends AppStateBaseBridge {
    public static final ApplicationsState.AnonymousClass30 FILTER_POWER_ALLOWLISTED =
            new ApplicationsState.AnonymousClass30(
                    ApplicationsState.FILTER_WITHOUT_DISABLED_UNTIL_USED, new AnonymousClass1());
    public final PowerAllowlistBackend mBackend;

    public AppStatePowerBridge(
            FragmentActivity fragmentActivity,
            ApplicationsState applicationsState,
            AppStateBaseBridge.Callback callback) {
        super(applicationsState, callback);
        this.mBackend = PowerAllowlistBackend.getInstance(fragmentActivity);
    }

    @Override // com.android.settings.applications.AppStateBaseBridge
    public final void loadAllExtraInfo() {
        ArrayList allApps = this.mAppSession.getAllApps();
        int size = allApps.size();
        for (int i = 0; i < size; i++) {
            ApplicationsState.AppEntry appEntry = (ApplicationsState.AppEntry) allApps.get(i);
            ApplicationInfo applicationInfo = appEntry.info;
            appEntry.extraInfo =
                    this.mBackend.isAllowlisted(applicationInfo.uid, applicationInfo.packageName)
                            ? Boolean.TRUE
                            : Boolean.FALSE;
        }
    }

    @Override // com.android.settings.applications.AppStateBaseBridge
    public final void updateExtraInfo(ApplicationsState.AppEntry appEntry, String str, int i) {
        appEntry.extraInfo = this.mBackend.isAllowlisted(i, str) ? Boolean.TRUE : Boolean.FALSE;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.applications.AppStatePowerBridge$1, reason: invalid class name */
    public final class AnonymousClass1 implements ApplicationsState.AppFilter {
        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final boolean filterApp(ApplicationsState.AppEntry appEntry) {
            return appEntry.extraInfo == Boolean.TRUE;
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final void init() {}
    }
}
