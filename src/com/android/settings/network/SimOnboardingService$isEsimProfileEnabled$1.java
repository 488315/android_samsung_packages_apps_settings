package com.android.settings.network;

import android.telephony.SubscriptionInfo;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SimOnboardingService$isEsimProfileEnabled$1 implements Predicate {
    public static final SimOnboardingService$isEsimProfileEnabled$1 INSTANCE =
            new SimOnboardingService$isEsimProfileEnabled$1();

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((SubscriptionInfo) obj).isEmbedded();
    }
}
