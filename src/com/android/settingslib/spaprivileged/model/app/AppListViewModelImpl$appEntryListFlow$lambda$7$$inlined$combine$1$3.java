package com.android.settingslib.spaprivileged.model.app;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

import kotlinx.coroutines.flow.FlowCollector;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0016\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0011\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0002\u0010\u0006\u001a\u00020\u0005\"\u0006\b\u0000\u0010\u0000\u0018\u0001\"\u0004\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u00028\u00010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u008a@¢\u0006\u0004\b\u0006\u0010\u0007"
        },
        d2 = {
            "T",
            "R",
            "Lkotlinx/coroutines/flow/FlowCollector;",
            ApnSettings.MVNO_NONE,
            "it",
            ApnSettings.MVNO_NONE,
            "<anonymous>",
            "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/Array;)V"
        },
        k = 3,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class AppListViewModelImpl$appEntryListFlow$lambda$7$$inlined$combine$1$3
        extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AppListViewModelImpl$appEntryListFlow$lambda$7$$inlined$combine$1$3
                appListViewModelImpl$appEntryListFlow$lambda$7$$inlined$combine$1$3 =
                        new AppListViewModelImpl$appEntryListFlow$lambda$7$$inlined$combine$1$3(
                                3, (Continuation) obj3);
        appListViewModelImpl$appEntryListFlow$lambda$7$$inlined$combine$1$3.L$0 =
                (FlowCollector) obj;
        appListViewModelImpl$appEntryListFlow$lambda$7$$inlined$combine$1$3.L$1 = (Object[]) obj2;
        return appListViewModelImpl$appEntryListFlow$lambda$7$$inlined$combine$1$3.invokeSuspend(
                Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            List flatten =
                    CollectionsKt__IterablesKt.flatten(
                            ArraysKt___ArraysKt.toList((List[]) ((Object[]) this.L$1)));
            this.label = 1;
            if (flowCollector.emit(flatten, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
