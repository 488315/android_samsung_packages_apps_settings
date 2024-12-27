package com.android.settings.network.helper;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class SelectableSubscriptions$$ExternalSyntheticLambda4
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        SubscriptionAnnotation subscriptionAnnotation = (SubscriptionAnnotation) obj;
        if (subscriptionAnnotation.isExisted()) {
            return true;
        }
        return subscriptionAnnotation.getType() == 2 && subscriptionAnnotation.isDisplayAllowed();
    }
}
