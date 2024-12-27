package com.android.settings.network;

import android.telephony.SubscriptionInfo;

import com.android.settings.network.helper.SubscriptionAnnotation;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class SubscriptionUtil$$ExternalSyntheticLambda4 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ SubscriptionUtil$$ExternalSyntheticLambda4(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        switch (i) {
            case 0:
                if (((SubscriptionInfo) obj).getSubscriptionId() == i2) {}
                break;
            case 1:
                if (((SubscriptionInfo) obj).getCarrierId() == i2) {}
                break;
            default:
                if (((SubscriptionAnnotation) obj).getSubscriptionId() == i2) {}
                break;
        }
        return false;
    }
}
