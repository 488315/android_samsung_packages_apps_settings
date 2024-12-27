package com.android.settings.fuelgauge;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.fuelgauge.batterytip.tips.BatteryTip;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.Utils;
import com.android.settingslib.widget.UsageProgressBarPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BatteryHeaderPreferenceController extends BasePreferenceController
        implements PreferenceControllerMixin {
    private static final int BATTERY_MAX_LEVEL = 100;
    static final String KEY_BATTERY_HEADER = "battery_header";
    private static final String TAG = "BatteryHeaderPreferenceController";
    private final BatterySettingsFeatureProvider mBatterySettingsFeatureProvider;
    BatteryStatusFeatureProvider mBatteryStatusFeatureProvider;
    private BatteryTip mBatteryTip;
    UsageProgressBarPreference mBatteryUsageProgressBarPref;
    private final PowerManager mPowerManager;

    public BatteryHeaderPreferenceController(Context context, String str) {
        super(context, str);
        this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mBatteryStatusFeatureProvider =
                (BatteryStatusFeatureProviderImpl)
                        featureFactoryImpl.batteryStatusFeatureProvider$delegate.getValue();
        FeatureFactoryImpl featureFactoryImpl2 = FeatureFactoryImpl._factory;
        if (featureFactoryImpl2 == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mBatterySettingsFeatureProvider =
                featureFactoryImpl2.getBatterySettingsFeatureProvider();
    }

    private CharSequence formatBatteryPercentageText(int i) {
        return Utils.formatPercentage(i);
    }

    private CharSequence generateLabel(BatteryInfo batteryInfo) {
        if (Utils.containsIncompatibleChargers(this.mContext, TAG)) {
            return this.mContext.getString(R.string.battery_info_status_not_charging);
        }
        if (batteryInfo.isBatteryDefender && !batteryInfo.discharging) {
            return this.mContext.getString(R.string.battery_info_status_charging_on_hold);
        }
        if (batteryInfo.remainingLabel != null) {
            this.mBatterySettingsFeatureProvider.getClass();
        }
        if (batteryInfo.remainingLabel == null || batteryInfo.batteryStatus == 4) {
            return batteryInfo.statusLabel;
        }
        if (batteryInfo.pluggedStatus == 4) {
            this.mBatterySettingsFeatureProvider.getClass();
        }
        if (batteryInfo.statusLabel != null && !batteryInfo.discharging) {
            return com.android.settingslib.fuelgauge.BatteryUtils.isChargingStringV2Enabled()
                    ? batteryInfo.isFastCharging
                            ? this.mContext.getString(
                                    R.string.battery_state_and_duration,
                                    batteryInfo.statusLabel,
                                    batteryInfo.remainingLabel)
                            : batteryInfo.remainingLabel
                    : this.mContext.getString(
                            R.string.battery_state_and_duration,
                            batteryInfo.statusLabel,
                            batteryInfo.remainingLabel);
        }
        if (this.mPowerManager.isPowerSaveMode()) {
            return this.mContext.getString(
                    R.string.battery_state_and_duration,
                    this.mContext.getString(R.string.battery_tip_early_heads_up_done_title),
                    batteryInfo.remainingLabel);
        }
        BatteryTip batteryTip = this.mBatteryTip;
        if (batteryTip == null || batteryTip.mType != 5) {
            return batteryInfo.remainingLabel;
        }
        return this.mContext.getString(
                R.string.battery_state_and_duration,
                this.mContext.getString(R.string.low_battery_summary),
                batteryInfo.remainingLabel);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        UsageProgressBarPreference usageProgressBarPreference =
                (UsageProgressBarPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mBatteryUsageProgressBarPref = usageProgressBarPreference;
        if (!TextUtils.equals(usageProgressBarPreference.mBottomSummary, " ")) {
            usageProgressBarPreference.mBottomSummary = " ";
            usageProgressBarPreference.notifyChanged();
        }
        Context context = this.mContext;
        StringBuilder sb = com.android.settings.Utils.sBuilder;
        if (context.registerReceiver(
                        null, new IntentFilter("android.intent.action.BATTERY_CHANGED"))
                .getBooleanExtra("present", true)) {
            quickUpdateHeaderPreference();
        } else {
            this.mBatteryUsageProgressBarPref.setVisible(false);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 1;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    public void quickUpdateHeaderPreference() {
        Intent batteryIntent =
                com.android.settingslib.fuelgauge.BatteryUtils.getBatteryIntent(this.mContext);
        int batteryLevel = Utils.getBatteryLevel(batteryIntent);
        batteryIntent.getIntExtra("plugged", -1);
        this.mBatteryUsageProgressBarPref.setUsageSummary(
                formatBatteryPercentageText(batteryLevel));
        this.mBatteryUsageProgressBarPref.setPercent(batteryLevel, 100L);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public void updateBatteryStatus(String str, BatteryInfo batteryInfo) {
        CharSequence generateLabel = str != null ? str : generateLabel(batteryInfo);
        UsageProgressBarPreference usageProgressBarPreference = this.mBatteryUsageProgressBarPref;
        if (!TextUtils.equals(usageProgressBarPreference.mBottomSummary, generateLabel)) {
            usageProgressBarPreference.mBottomSummary = generateLabel;
            usageProgressBarPreference.notifyChanged();
        }
        Log.d(TAG, "updateBatteryStatus: " + str + " summary: " + ((Object) generateLabel));
    }

    public void updateHeaderByBatteryTips(BatteryTip batteryTip, BatteryInfo batteryInfo) {
        this.mBatteryTip = batteryTip;
        if (batteryTip == null || batteryInfo == null) {
            return;
        }
        updateHeaderPreference(batteryInfo);
    }

    public void updateHeaderPreference(BatteryInfo batteryInfo) {
        this.mBatteryStatusFeatureProvider.getClass();
        UsageProgressBarPreference usageProgressBarPreference = this.mBatteryUsageProgressBarPref;
        CharSequence generateLabel = generateLabel(batteryInfo);
        if (!TextUtils.equals(usageProgressBarPreference.mBottomSummary, generateLabel)) {
            usageProgressBarPreference.mBottomSummary = generateLabel;
            usageProgressBarPreference.notifyChanged();
        }
        this.mBatteryUsageProgressBarPref.setUsageSummary(
                formatBatteryPercentageText(batteryInfo.batteryLevel));
        this.mBatteryUsageProgressBarPref.setPercent(batteryInfo.batteryLevel, 100L);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
