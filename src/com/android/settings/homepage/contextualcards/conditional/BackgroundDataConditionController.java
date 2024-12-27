package com.android.settings.homepage.contextualcards.conditional;

import android.content.Context;
import android.content.Intent;
import android.net.NetworkPolicyManager;

import com.android.settings.R;
import com.android.settings.Settings;
import com.android.settings.homepage.contextualcards.ContextualCard;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BackgroundDataConditionController implements ConditionalCardController {
    public static final int ID = Objects.hash("BackgroundDataConditionController");
    public final Context mAppContext;
    public final ConditionManager mConditionManager;
    public final NetworkPolicyManager mNetworkPolicyManager;

    public BackgroundDataConditionController(Context context, ConditionManager conditionManager) {
        this.mAppContext = context;
        this.mConditionManager = conditionManager;
        this.mNetworkPolicyManager = (NetworkPolicyManager) context.getSystemService("netpolicy");
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final ContextualCard buildContextualCard() {
        ConditionalContextualCard.Builder builder = new ConditionalContextualCard.Builder();
        builder.mConditionId = ID;
        builder.mMetricsConstant = 378;
        builder.mActionText = this.mAppContext.getText(R.string.condition_turn_off);
        builder.mName =
                this.mAppContext.getPackageName()
                        + "/"
                        + ((Object) this.mAppContext.getText(R.string.condition_bg_data_title));
        builder.mTitleText = this.mAppContext.getText(R.string.condition_bg_data_title).toString();
        builder.mSummaryText =
                this.mAppContext.getText(R.string.condition_bg_data_summary).toString();
        builder.mIconDrawable = this.mAppContext.getDrawable(R.drawable.ic_data_saver);
        builder.mViewType = R.layout.conditional_card_half_tile;
        return builder.build();
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final long getId() {
        return ID;
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final boolean isDisplayable() {
        return this.mNetworkPolicyManager.getRestrictBackground();
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void onActionClick() {
        this.mNetworkPolicyManager.setRestrictBackground(false);
        this.mConditionManager.onConditionChanged();
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void onPrimaryClick(Context context) {
        context.startActivity(
                new Intent(context, (Class<?>) Settings.DataUsageSummaryActivity.class));
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void startMonitoringStateChange() {}

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void stopMonitoringStateChange() {}
}
