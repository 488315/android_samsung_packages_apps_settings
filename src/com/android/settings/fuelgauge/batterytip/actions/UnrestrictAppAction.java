package com.android.settings.fuelgauge.batterytip.actions;

import android.content.Context;

import com.android.settings.fuelgauge.BatteryUtils;
import com.android.settings.fuelgauge.batterytip.AppInfo;
import com.android.settings.fuelgauge.batterytip.tips.UnrestrictAppTip;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UnrestrictAppAction extends BatteryTipAction {
    BatteryUtils mBatteryUtils;
    public final UnrestrictAppTip mUnRestrictAppTip;

    public UnrestrictAppAction(Context context, UnrestrictAppTip unrestrictAppTip) {
        super(context);
        this.mUnRestrictAppTip = unrestrictAppTip;
        this.mBatteryUtils = BatteryUtils.getInstance(context);
    }

    @Override // com.android.settings.fuelgauge.batterytip.actions.BatteryTipAction
    public final void handlePositiveAction(int i) {
        AppInfo appInfo = this.mUnRestrictAppTip.mAppInfo;
        this.mBatteryUtils.setForceAppStandby(appInfo.uid, 0, 9, appInfo.packageName);
        this.mMetricsFeatureProvider.action(0, 1363, i, 0, appInfo.packageName);
    }
}
