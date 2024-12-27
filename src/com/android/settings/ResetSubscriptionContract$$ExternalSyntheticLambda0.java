package com.android.settings;

import android.telephony.SubscriptionManager;

import java.util.function.IntPredicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public final /* synthetic */ class ResetSubscriptionContract$$ExternalSyntheticLambda0
        implements IntPredicate {
    @Override // java.util.function.IntPredicate
    public final boolean test(int i) {
        return SubscriptionManager.isUsableSubscriptionId(i);
    }
}
