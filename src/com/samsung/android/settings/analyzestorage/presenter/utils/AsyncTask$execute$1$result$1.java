package com.samsung.android.settings.analyzestorage.presenter.utils;

import com.samsung.android.settings.analyzestorage.domain.log.Log;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\b\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u0002H\u008a@"
        },
        d2 = {"<anonymous>", "T", "Lkotlinx/coroutines/CoroutineScope;"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes4.dex */
final class AsyncTask$execute$1$result$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function0 $doInBackground;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AsyncTask$execute$1$result$1(Function0 function0, Continuation continuation) {
        super(2, continuation);
        this.$doInBackground = function0;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AsyncTask$execute$1$result$1(this.$doInBackground, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AsyncTask$execute$1$result$1) create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Log.d(
                "AsyncTask",
                "doInBackground() ] current thread - " + Thread.currentThread().getName());
        return this.$doInBackground.mo1068invoke();
    }
}
