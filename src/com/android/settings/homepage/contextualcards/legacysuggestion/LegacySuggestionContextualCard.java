package com.android.settings.homepage.contextualcards.legacysuggestion;

import android.app.PendingIntent;
import android.service.settings.suggestions.Suggestion;

import com.android.settings.homepage.contextualcards.ContextualCard;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class LegacySuggestionContextualCard extends ContextualCard {
    public final PendingIntent mPendingIntent;
    public final Suggestion mSuggestion;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder extends ContextualCard.Builder {
        public PendingIntent mPendingIntent;
        public Suggestion mSuggestion;

        @Override // com.android.settings.homepage.contextualcards.ContextualCard.Builder
        public final ContextualCard build() {
            return new LegacySuggestionContextualCard(this);
        }
    }

    public LegacySuggestionContextualCard(Builder builder) {
        super(builder);
        this.mPendingIntent = builder.mPendingIntent;
        this.mSuggestion = builder.mSuggestion;
    }

    @Override // com.android.settings.homepage.contextualcards.ContextualCard
    public final int getCardType() {
        return 2;
    }
}
