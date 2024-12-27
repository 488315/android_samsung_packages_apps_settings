package com.android.settings.network;

import android.content.Context;
import android.telephony.SubscriptionInfo;
import android.text.TextUtils;

import com.android.settings.R;

import java.util.Set;
import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class SubscriptionUtil$$ExternalSyntheticLambda10 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SubscriptionUtil$$ExternalSyntheticLambda10(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                SubscriptionUtil.C1DisplayInfo c1DisplayInfo = (SubscriptionUtil.C1DisplayInfo) obj;
                if (((Set) obj2).contains(c1DisplayInfo.uniqueName)) {
                    c1DisplayInfo.uniqueName =
                            ((Object) c1DisplayInfo.originalName)
                                    + " "
                                    + c1DisplayInfo.subscriptionInfo.getSubscriptionId();
                }
                return c1DisplayInfo;
            default:
                Context context = (Context) obj2;
                SubscriptionInfo subscriptionInfo = (SubscriptionInfo) obj;
                SubscriptionUtil.C1DisplayInfo c1DisplayInfo2 =
                        new SubscriptionUtil.C1DisplayInfo();
                c1DisplayInfo2.subscriptionInfo = subscriptionInfo;
                String charSequence = subscriptionInfo.getDisplayName().toString();
                c1DisplayInfo2.originalName =
                        TextUtils.equals(charSequence, "CARD")
                                ? context.getResources().getString(R.string.sim_card)
                                : charSequence.trim();
                return c1DisplayInfo2;
        }
    }
}
