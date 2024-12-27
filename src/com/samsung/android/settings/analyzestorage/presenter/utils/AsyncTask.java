package com.samsung.android.settings.analyzestorage.presenter.utils;

import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.ui.dialog.FormatProgressDialogFragment$initProgress$task$1;

import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.JobImpl;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.android.HandlerContext;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AsyncTask implements CoroutineScope {
    public final FormatProgressDialogFragment$initProgress$task$1 coroutineResult;

    public AsyncTask(
            FormatProgressDialogFragment$initProgress$task$1
                    formatProgressDialogFragment$initProgress$task$1) {
        this.coroutineResult = formatProgressDialogFragment$initProgress$task$1;
    }

    public final void execute(Function0 function0) {
        BuildersKt.launch$default(
                        this,
                        getCoroutineContext(),
                        null,
                        new AsyncTask$execute$1(this, function0, null),
                        2)
                .invokeOnCompletion(
                        new Function1() { // from class:
                                          // com.samsung.android.settings.analyzestorage.presenter.utils.AsyncTask$execute$2
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                Log.d("AsyncTask", "execute() COMPLETED");
                                AsyncTask.this.getClass();
                                return Unit.INSTANCE;
                            }
                        });
    }

    @Override // kotlinx.coroutines.CoroutineScope
    public final CoroutineContext getCoroutineContext() {
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        HandlerContext handlerContext = MainDispatcherLoader.dispatcher;
        JobImpl Job$default = JobKt.Job$default();
        handlerContext.getClass();
        return CoroutineContext.DefaultImpls.plus(
                (CoroutineContext) handlerContext, (CoroutineContext) Job$default);
    }
}
