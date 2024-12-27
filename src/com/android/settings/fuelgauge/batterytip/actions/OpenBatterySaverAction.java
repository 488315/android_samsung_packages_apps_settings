package com.android.settings.fuelgauge.batterytip.actions;

import com.android.settings.core.SubSettingLauncher;
import com.android.settings.fuelgauge.batterysaver.BatterySaverSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class OpenBatterySaverAction extends BatteryTipAction {
    @Override // com.android.settings.fuelgauge.batterytip.actions.BatteryTipAction
    public final void handlePositiveAction(int i) {
        this.mMetricsFeatureProvider.action(this.mContext, 1388, i);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = BatterySaverSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = i;
        subSettingLauncher.launch();
    }
}
