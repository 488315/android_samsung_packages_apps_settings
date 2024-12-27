package com.android.settings.network.telephony.wificalling;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class CrossSimCallingViewModel$updateCrossSimCalling$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    boolean Z$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ CrossSimCallingViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CrossSimCallingViewModel$updateCrossSimCalling$1(
            CrossSimCallingViewModel crossSimCallingViewModel, Continuation continuation) {
        super(continuation);
        this.this$0 = crossSimCallingViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return CrossSimCallingViewModel.access$updateCrossSimCalling(
                this.this$0, null, false, this);
    }
}
