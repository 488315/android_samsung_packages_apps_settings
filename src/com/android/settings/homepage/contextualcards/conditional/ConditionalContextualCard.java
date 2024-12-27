package com.android.settings.homepage.contextualcards.conditional;

import com.android.settings.homepage.contextualcards.ContextualCard;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ConditionalContextualCard extends ContextualCard {
    static final double UNSUPPORTED_RANKING_SCORE = -100.0d;
    public final CharSequence mActionText;
    public final long mConditionId;
    public final int mMetricsConstant;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder extends ContextualCard.Builder {
        public CharSequence mActionText;
        public long mConditionId;
        public int mMetricsConstant;

        @Override // com.android.settings.homepage.contextualcards.ContextualCard.Builder
        public final ContextualCard build() {
            this.mRankingScore = ConditionalContextualCard.UNSUPPORTED_RANKING_SCORE;
            return new ConditionalContextualCard(this);
        }
    }

    public ConditionalContextualCard(Builder builder) {
        super(builder);
        this.mConditionId = builder.mConditionId;
        this.mMetricsConstant = builder.mMetricsConstant;
        this.mActionText = builder.mActionText;
    }

    @Override // com.android.settings.homepage.contextualcards.ContextualCard
    public final int getCardType() {
        return 3;
    }
}
