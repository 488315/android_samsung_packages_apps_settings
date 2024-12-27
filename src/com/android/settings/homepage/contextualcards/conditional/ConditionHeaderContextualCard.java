package com.android.settings.homepage.contextualcards.conditional;

import android.text.TextUtils;

import com.android.settings.homepage.contextualcards.ContextualCard;

import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ConditionHeaderContextualCard extends ContextualCard {
    public final List mConditionalCards;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder extends ContextualCard.Builder {
        public List mConditionalCards;

        @Override // com.android.settings.homepage.contextualcards.ContextualCard.Builder
        public final ContextualCard build() {
            return new ConditionHeaderContextualCard(this);
        }
    }

    public ConditionHeaderContextualCard(Builder builder) {
        super(builder);
        this.mConditionalCards = builder.mConditionalCards;
    }

    @Override // com.android.settings.homepage.contextualcards.ContextualCard
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ConditionHeaderContextualCard)) {
            return false;
        }
        ConditionHeaderContextualCard conditionHeaderContextualCard =
                (ConditionHeaderContextualCard) obj;
        return TextUtils.equals(this.mName, conditionHeaderContextualCard.mName)
                && this.mConditionalCards.equals(conditionHeaderContextualCard.mConditionalCards);
    }

    @Override // com.android.settings.homepage.contextualcards.ContextualCard
    public final int getCardType() {
        return 4;
    }

    @Override // com.android.settings.homepage.contextualcards.ContextualCard
    public final int hashCode() {
        return Objects.hash(this.mName, this.mConditionalCards);
    }
}
