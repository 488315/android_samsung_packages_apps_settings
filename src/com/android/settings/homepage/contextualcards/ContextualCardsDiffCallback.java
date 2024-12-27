package com.android.settings.homepage.contextualcards;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ContextualCardsDiffCallback extends DiffUtil.Callback {
    public final List mNewCards;
    public final List mOldCards;

    public ContextualCardsDiffCallback(List list, List list2) {
        this.mOldCards = list;
        this.mNewCards = list2;
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public final boolean areContentsTheSame(int i, int i2) {
        ContextualCard contextualCard = (ContextualCard) this.mNewCards.get(i2);
        int i3 = contextualCard.mCategory;
        if (i3 == 6 || i3 == 3 || contextualCard.mHasInlineAction) {
            return false;
        }
        return ((ContextualCard) this.mOldCards.get(i)).equals(contextualCard);
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public final boolean areItemsTheSame(int i, int i2) {
        return ((ContextualCard) this.mOldCards.get(i))
                .mName.equals(((ContextualCard) this.mNewCards.get(i2)).mName);
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public final int getNewListSize() {
        return this.mNewCards.size();
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public final int getOldListSize() {
        return this.mOldCards.size();
    }
}
