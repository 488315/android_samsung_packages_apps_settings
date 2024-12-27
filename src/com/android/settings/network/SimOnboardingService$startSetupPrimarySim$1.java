package com.android.settings.network;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class SimOnboardingService$startSetupPrimarySim$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ SimOnboardingService this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SimOnboardingService$startSetupPrimarySim$1(
            SimOnboardingService simOnboardingService, Continuation continuation) {
        super(continuation);
        this.this$0 = simOnboardingService;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.startSetupPrimarySim(null, null, this);
    }
}
