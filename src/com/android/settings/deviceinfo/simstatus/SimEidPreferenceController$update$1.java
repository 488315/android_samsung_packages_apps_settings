package com.android.settings.deviceinfo.simstatus;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class SimEidPreferenceController$update$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ SimEidPreferenceController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SimEidPreferenceController$update$1(
            SimEidPreferenceController simEidPreferenceController, Continuation continuation) {
        super(continuation);
        this.this$0 = simEidPreferenceController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object update;
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        update = this.this$0.update(this);
        return update;
    }
}