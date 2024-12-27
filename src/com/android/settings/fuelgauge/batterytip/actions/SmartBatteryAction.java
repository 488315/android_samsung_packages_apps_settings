package com.android.settings.fuelgauge.batterytip.actions;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.fuelgauge.SmartBatterySettings;
import com.android.settingslib.core.instrumentation.Instrumentable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SmartBatteryAction extends BatteryTipAction {
    public Fragment mFragment;
    public SettingsActivity mSettingsActivity;

    @Override // com.android.settings.fuelgauge.batterytip.actions.BatteryTipAction
    public final void handlePositiveAction(int i) {
        this.mMetricsFeatureProvider.action(this.mContext, 1364, i);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mSettingsActivity);
        LifecycleOwner lifecycleOwner = this.mFragment;
        int metricsCategory =
                lifecycleOwner instanceof Instrumentable
                        ? ((Instrumentable) lifecycleOwner).getMetricsCategory()
                        : 0;
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mSourceMetricsCategory = metricsCategory;
        launchRequest.mDestinationName = SmartBatterySettings.class.getName();
        subSettingLauncher.setTitleRes(R.string.smart_battery_manager_title, null);
        subSettingLauncher.launch();
    }
}
