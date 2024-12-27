package com.android.settings.datausage;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class AppDataUsageCycleController$updateAdapter$1 extends ContinuationImpl {
    long J$0;
    long J$1;
    Object L$0;
    boolean Z$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ AppDataUsageCycleController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppDataUsageCycleController$updateAdapter$1(
            AppDataUsageCycleController appDataUsageCycleController, Continuation continuation) {
        super(continuation);
        this.this$0 = appDataUsageCycleController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object updateAdapter;
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        updateAdapter = this.this$0.updateAdapter(0L, 0L, false, this);
        return updateAdapter;
    }
}
