package com.android.settingslib.spaprivileged.model.app;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.CombineKt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0010\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\u0010\u0005\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u00028\u00010\u00022\u0006\u0010\u0003\u001a\u00028\u0000H\u008a@"
        },
        d2 = {
            "T",
            "R",
            "Lkotlinx/coroutines/flow/FlowCollector;",
            "it",
            ApnSettings.MVNO_NONE,
            "<anonymous>"
        },
        k = 3,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class AppListViewModelImpl$special$$inlined$flatMapLatest$2 extends SuspendLambda
        implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AppListViewModelImpl$special$$inlined$flatMapLatest$2
                appListViewModelImpl$special$$inlined$flatMapLatest$2 =
                        new AppListViewModelImpl$special$$inlined$flatMapLatest$2(
                                3, (Continuation) obj3);
        appListViewModelImpl$special$$inlined$flatMapLatest$2.L$0 = (FlowCollector) obj;
        appListViewModelImpl$special$$inlined$flatMapLatest$2.L$1 = obj2;
        return appListViewModelImpl$special$$inlined$flatMapLatest$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        Unit unit = Unit.INSTANCE;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            List list = (List) this.L$1;
            ArrayList arrayList =
                    new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(
                        ((AppListViewModelImpl.UserSubGraph) it.next()).listModelFilteredFlow);
            }
            final Flow[] flowArr =
                    (Flow[]) CollectionsKt___CollectionsKt.toList(arrayList).toArray(new Flow[0]);
            this.label = 1;
            FlowKt.ensureActive(flowCollector);
            Object combineInternal =
                    CombineKt.combineInternal(
                            this,
                            new Function0() { // from class:
                                              // com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$appEntryListFlow$lambda$7$$inlined$combine$1$2
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                /* renamed from: invoke */
                                public final Object mo1068invoke() {
                                    return new List[flowArr.length];
                                }
                            },
                            new AppListViewModelImpl$appEntryListFlow$lambda$7$$inlined$combine$1$3(
                                    3, null),
                            flowCollector,
                            flowArr);
            CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (combineInternal != coroutineSingletons2) {
                combineInternal = unit;
            }
            if (combineInternal != coroutineSingletons2) {
                combineInternal = unit;
            }
            if (combineInternal == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return unit;
    }
}
