package com.android.settings.wifi.calling;

import android.telephony.SubscriptionInfo;

import java.util.function.ToIntFunction;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiCallingSettings$$ExternalSyntheticLambda0
        implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        SubscriptionInfo subscriptionInfo = (SubscriptionInfo) obj;
        if (subscriptionInfo == null) {
            return -1;
        }
        return subscriptionInfo.getSubscriptionId();
    }
}
