package com.android.settings.network;

import java.util.List;
import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class SubscriptionUtil$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ SubscriptionUtil$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return SubscriptionUtil.getDefaultSubscriptionSelection((List) obj);
            case 1:
                return Integer.valueOf(
                        ((SubscriptionUtil.C1DisplayInfo) obj)
                                .subscriptionInfo.getSubscriptionId());
            case 2:
                return ((SubscriptionUtil.C1DisplayInfo) obj).uniqueName;
            case 3:
                return ((SubscriptionUtil.C1DisplayInfo) obj).originalName;
            default:
                return ((SubscriptionUtil.C1DisplayInfo) obj).uniqueName;
        }
    }
}
