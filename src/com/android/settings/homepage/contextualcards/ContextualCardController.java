package com.android.settings.homepage.contextualcards;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public interface ContextualCardController {
    void onActionClick(ContextualCard contextualCard);

    void onDismissed(ContextualCard contextualCard);

    void onPrimaryClick(ContextualCard contextualCard);

    void setCardUpdateListener(ContextualCardUpdateListener contextualCardUpdateListener);
}
