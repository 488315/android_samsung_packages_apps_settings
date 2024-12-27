package com.android.settings.homepage.contextualcards.conditional;

import android.content.Context;

import com.android.settings.R;
import com.android.settings.homepage.contextualcards.ContextualCard;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class RingerMutedConditionController extends AbnormalRingerConditionController {
    public static final int ID = Objects.hash("RingerMutedConditionController");
    public final Context mAppContext;

    public RingerMutedConditionController(Context context, ConditionManager conditionManager) {
        super(context, conditionManager);
        this.mAppContext = context;
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final ContextualCard buildContextualCard() {
        ConditionalContextualCard.Builder builder = new ConditionalContextualCard.Builder();
        builder.mConditionId = ID;
        builder.mMetricsConstant = 1368;
        builder.mActionText =
                this.mAppContext.getText(R.string.condition_device_muted_action_turn_on_sound);
        builder.mName =
                this.mAppContext.getPackageName()
                        + "/"
                        + ((Object)
                                this.mAppContext.getText(R.string.condition_device_muted_title));
        builder.mTitleText =
                this.mAppContext.getText(R.string.condition_device_muted_title).toString();
        builder.mSummaryText =
                this.mAppContext.getText(R.string.condition_device_muted_summary).toString();
        builder.mIconDrawable = this.mAppContext.getDrawable(R.drawable.ic_notifications_off_24dp);
        builder.mViewType = R.layout.conditional_card_half_tile;
        return builder.build();
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final long getId() {
        return ID;
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final boolean isDisplayable() {
        return this.mAudioManager.getRingerModeInternal() == 0;
    }
}
