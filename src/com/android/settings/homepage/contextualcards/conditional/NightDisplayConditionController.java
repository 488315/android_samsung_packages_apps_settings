package com.android.settings.homepage.contextualcards.conditional;

import android.content.Context;
import android.hardware.display.ColorDisplayManager;
import android.hardware.display.NightDisplayListener;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.display.NightDisplaySettings;
import com.android.settings.homepage.contextualcards.ContextualCard;

import com.sec.ims.volte2.data.VolteConstants;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NightDisplayConditionController
        implements ConditionalCardController, NightDisplayListener.Callback {
    public static final int ID = Objects.hash("NightDisplayConditionController");
    public final Context mAppContext;
    public final ColorDisplayManager mColorDisplayManager;
    public final ConditionManager mConditionManager;
    public final NightDisplayListener mNightDisplayListener;

    public NightDisplayConditionController(Context context, ConditionManager conditionManager) {
        this.mAppContext = context;
        this.mConditionManager = conditionManager;
        this.mColorDisplayManager =
                (ColorDisplayManager) context.getSystemService(ColorDisplayManager.class);
        this.mNightDisplayListener = new NightDisplayListener(context);
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final ContextualCard buildContextualCard() {
        ConditionalContextualCard.Builder builder = new ConditionalContextualCard.Builder();
        builder.mConditionId = ID;
        builder.mMetricsConstant = 492;
        builder.mActionText = this.mAppContext.getText(R.string.condition_turn_off);
        builder.mName =
                this.mAppContext.getPackageName()
                        + "/"
                        + ((Object)
                                this.mAppContext.getText(R.string.condition_night_display_title));
        builder.mTitleText =
                this.mAppContext.getText(R.string.condition_night_display_title).toString();
        builder.mSummaryText =
                this.mAppContext.getText(R.string.condition_night_display_summary).toString();
        builder.mIconDrawable = this.mAppContext.getDrawable(R.drawable.ic_settings_night_display);
        builder.mViewType = R.layout.conditional_card_half_tile;
        return builder.build();
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final long getId() {
        return ID;
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final boolean isDisplayable() {
        return this.mColorDisplayManager.isNightDisplayActivated();
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void onActionClick() {
        this.mColorDisplayManager.setNightDisplayActivated(false);
    }

    public final void onActivated(boolean z) {
        this.mConditionManager.onConditionChanged();
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void onPrimaryClick(Context context) {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
        String name = NightDisplaySettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = VolteConstants.ErrorCode.SERVER_ERROR;
        subSettingLauncher.setTitleRes(R.string.night_display_title, null);
        subSettingLauncher.launch();
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void startMonitoringStateChange() {
        this.mNightDisplayListener.setCallback(this);
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void stopMonitoringStateChange() {
        this.mNightDisplayListener.setCallback((NightDisplayListener.Callback) null);
    }
}
