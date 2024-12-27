package com.samsung.android.settings.analyzestorage.presenter.utils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.ui.dialog.FormatProgressDialogFragment$initProgress$task$1;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.scheduling.DefaultIoScheduler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u0003H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "T", "Lkotlinx/coroutines/CoroutineScope;"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes4.dex */
final class AsyncTask$execute$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function0 $doInBackground;
    int label;
    final /* synthetic */ AsyncTask this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AsyncTask$execute$1(
            AsyncTask asyncTask, Function0 function0, Continuation continuation) {
        super(2, continuation);
        this.this$0 = asyncTask;
        this.$doInBackground = function0;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AsyncTask$execute$1(this.this$0, this.$doInBackground, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AsyncTask$execute$1) create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            this.this$0.getClass();
            Log.d(
                    "AsyncTask",
                    "onPreExecute() ] current thread - " + Thread.currentThread().getName());
            DefaultIoScheduler defaultIoScheduler = CoroutineUtils$AppDispatchers.IO;
            AsyncTask$execute$1$result$1 asyncTask$execute$1$result$1 =
                    new AsyncTask$execute$1$result$1(this.$doInBackground, null);
            this.label = 1;
            obj = BuildersKt.withContext(defaultIoScheduler, asyncTask$execute$1$result$1, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        AsyncTask asyncTask = this.this$0;
        asyncTask.getClass();
        Log.d(
                "AsyncTask",
                "onPostExecute() ] current thread - "
                        + Thread.currentThread().getName()
                        + ", result - "
                        + obj);
        FormatProgressDialogFragment$initProgress$task$1
                formatProgressDialogFragment$initProgress$task$1 = asyncTask.coroutineResult;
        if (obj == null) {
            formatProgressDialogFragment$initProgress$task$1.getClass();
        } else {
            formatProgressDialogFragment$initProgress$task$1.getClass();
            formatProgressDialogFragment$initProgress$task$1.this$0.cancel();
        }
        return Unit.INSTANCE;
    }
}
