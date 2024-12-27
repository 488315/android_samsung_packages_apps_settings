package com.android.settings.network;

import android.telephony.SubscriptionInfo;

import com.android.settings.network.helper.SubscriptionAnnotation;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class SubscriptionUtil$$ExternalSyntheticLambda2 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                SubscriptionInfo subscriptionInfo = (SubscriptionInfo) obj;
                return subscriptionInfo != null
                        && SubscriptionUtil.isEmbeddedSubscriptionVisible(subscriptionInfo);
            case 1:
                SubscriptionInfo subscriptionInfo2 = (SubscriptionInfo) obj;
                return (subscriptionInfo2 == null || subscriptionInfo2.getDisplayName() == null)
                        ? false
                        : true;
            case 2:
                return ((SubscriptionAnnotation) obj).isDisplayAllowed();
            default:
                return ((SubscriptionAnnotation) obj).isActive();
        }
    }
}
