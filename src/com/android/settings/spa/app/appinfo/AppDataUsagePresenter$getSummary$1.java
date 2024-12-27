package com.android.settings.spa.app.appinfo;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class AppDataUsagePresenter$getSummary$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ AppDataUsagePresenter this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppDataUsagePresenter$getSummary$1(
            AppDataUsagePresenter appDataUsagePresenter, Continuation continuation) {
        super(continuation);
        this.this$0 = appDataUsagePresenter;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return AppDataUsagePresenter.access$getSummary(this.this$0, null, this);
    }
}
