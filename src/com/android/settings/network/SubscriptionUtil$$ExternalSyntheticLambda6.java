package com.android.settings.network;

import android.os.ParcelUuid;
import android.telephony.SubscriptionInfo;

import java.util.Set;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class SubscriptionUtil$$ExternalSyntheticLambda6 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SubscriptionUtil$$ExternalSyntheticLambda6(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                return !((Set) obj2).add(((SubscriptionUtil.C1DisplayInfo) obj).originalName);
            case 1:
                return !((Set) obj2).add(((SubscriptionUtil.C1DisplayInfo) obj).uniqueName);
            default:
                SubscriptionInfo subscriptionInfo = (SubscriptionInfo) obj;
                return subscriptionInfo.isEmbedded()
                        && ((ParcelUuid) obj2).equals(subscriptionInfo.getGroupUuid());
        }
    }
}
