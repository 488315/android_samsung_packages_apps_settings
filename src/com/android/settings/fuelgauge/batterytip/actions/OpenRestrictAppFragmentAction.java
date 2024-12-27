package com.android.settings.fuelgauge.batterytip.actions;

import com.android.settings.core.InstrumentedPreferenceFragment;
import com.android.settings.fuelgauge.RestrictedAppDetails;
import com.android.settings.fuelgauge.batterytip.BatteryDatabaseManager;
import com.android.settings.fuelgauge.batterytip.tips.RestrictAppTip;
import com.android.settingslib.utils.ThreadUtils;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class OpenRestrictAppFragmentAction extends BatteryTipAction {
    BatteryDatabaseManager mBatteryDatabaseManager;
    public final InstrumentedPreferenceFragment mFragment;
    public final RestrictAppTip mRestrictAppTip;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public OpenRestrictAppFragmentAction(
            com.android.settings.core.InstrumentedPreferenceFragment r2,
            com.android.settings.fuelgauge.batterytip.tips.RestrictAppTip r3) {
        /*
            r1 = this;
            android.content.Context r0 = r2.getContext()
            r1.<init>(r0)
            r1.mFragment = r2
            r1.mRestrictAppTip = r3
            com.android.settings.fuelgauge.batterytip.BatteryDatabaseManager r2 = com.android.settings.fuelgauge.batterytip.BatteryDatabaseManager.getInstance(r0)
            r1.mBatteryDatabaseManager = r2
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.fuelgauge.batterytip.actions.OpenRestrictAppFragmentAction.<init>(com.android.settings.core.InstrumentedPreferenceFragment,"
                    + " com.android.settings.fuelgauge.batterytip.tips.RestrictAppTip):void");
    }

    @Override // com.android.settings.fuelgauge.batterytip.actions.BatteryTipAction
    public final void handlePositiveAction(int i) {
        this.mMetricsFeatureProvider.action(this.mContext, 1361, i);
        final List list = this.mRestrictAppTip.mRestrictAppList;
        RestrictedAppDetails.startRestrictedAppDetails(this.mFragment, list);
        ThreadUtils.postOnBackgroundThread(
                new Runnable() { // from class:
                                 // com.android.settings.fuelgauge.batterytip.actions.OpenRestrictAppFragmentAction$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        OpenRestrictAppFragmentAction openRestrictAppFragmentAction =
                                OpenRestrictAppFragmentAction.this;
                        openRestrictAppFragmentAction.mBatteryDatabaseManager.updateAnomalies(list);
                    }
                });
    }
}
