package com.android.settings.homepage.contextualcards.conditional;

import android.content.Context;
import android.os.PowerManager;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.fuelgauge.BatterySaverReceiver;
import com.android.settings.fuelgauge.batterysaver.BatterySaverSettings;
import com.android.settings.homepage.contextualcards.ContextualCard;
import com.android.settingslib.fuelgauge.BatterySaverUtils;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatterySaverConditionController
        implements ConditionalCardController, BatterySaverReceiver.BatterySaverListener {
    public static final int ID = Objects.hash("BatterySaverConditionController");
    public final Context mAppContext;
    public final ConditionManager mConditionManager;
    public final PowerManager mPowerManager;
    public final BatterySaverReceiver mReceiver;

    public BatterySaverConditionController(Context context, ConditionManager conditionManager) {
        this.mAppContext = context;
        this.mConditionManager = conditionManager;
        this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
        BatterySaverReceiver batterySaverReceiver = new BatterySaverReceiver(context);
        this.mReceiver = batterySaverReceiver;
        batterySaverReceiver.mBatterySaverListener = this;
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final ContextualCard buildContextualCard() {
        ConditionalContextualCard.Builder builder = new ConditionalContextualCard.Builder();
        builder.mConditionId = ID;
        builder.mMetricsConstant = 379;
        builder.mActionText = this.mAppContext.getText(R.string.condition_turn_off);
        builder.mName =
                this.mAppContext.getPackageName()
                        + "/"
                        + ((Object) this.mAppContext.getText(R.string.condition_battery_title));
        builder.mTitleText = this.mAppContext.getText(R.string.condition_battery_title).toString();
        builder.mSummaryText =
                this.mAppContext.getText(R.string.condition_battery_summary).toString();
        builder.mIconDrawable =
                this.mAppContext.getDrawable(R.drawable.ic_battery_saver_accent_24dp);
        builder.mViewType = R.layout.conditional_card_half_tile;
        return builder.build();
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final long getId() {
        return ID;
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final boolean isDisplayable() {
        return this.mPowerManager.isPowerSaveMode();
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void onActionClick() {
        BatterySaverUtils.setPowerSaveMode(0, this.mAppContext, false, false);
    }

    @Override // com.android.settings.fuelgauge.BatterySaverReceiver.BatterySaverListener
    public final void onPowerSaveModeChanged() {
        this.mConditionManager.onConditionChanged();
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void onPrimaryClick(Context context) {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
        String name = BatterySaverSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = 35;
        subSettingLauncher.setTitleRes(R.string.battery_saver, null);
        subSettingLauncher.launch();
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void startMonitoringStateChange() {
        this.mReceiver.setListening(true);
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void stopMonitoringStateChange() {
        this.mReceiver.setListening(false);
    }

    @Override // com.android.settings.fuelgauge.BatterySaverReceiver.BatterySaverListener
    public final void onBatteryChanged(boolean z) {}
}
