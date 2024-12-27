package com.android.settings.homepage.contextualcards.conditional;

import com.android.settings.homepage.contextualcards.ContextualCard;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ConditionFooterContextualCard extends ContextualCard {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder extends ContextualCard.Builder {
        @Override // com.android.settings.homepage.contextualcards.ContextualCard.Builder
        public final ContextualCard build() {
            return new ConditionFooterContextualCard(this);
        }
    }

    @Override // com.android.settings.homepage.contextualcards.ContextualCard
    public final int getCardType() {
        return 5;
    }
}
