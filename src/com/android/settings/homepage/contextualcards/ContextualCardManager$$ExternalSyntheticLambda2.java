package com.android.settings.homepage.contextualcards;

import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ContextualCardManager$$ExternalSyntheticLambda2
        implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        ContextualCard contextualCard = (ContextualCard) obj;
        switch (this.$r8$classId) {
            case 0:
                return contextualCard.mName;
            default:
                return Integer.valueOf(contextualCard.getCardType());
        }
    }
}
