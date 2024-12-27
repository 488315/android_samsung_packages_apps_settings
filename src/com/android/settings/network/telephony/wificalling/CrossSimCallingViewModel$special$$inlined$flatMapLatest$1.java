package com.android.settings.network.telephony.wificalling;

import com.android.settings.network.MobileDataEnabledFlowKt;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.flow.ChannelAsFlow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__MergeKt;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

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
public final class CrossSimCallingViewModel$special$$inlined$flatMapLatest$1 extends SuspendLambda
        implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ CrossSimCallingViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CrossSimCallingViewModel$special$$inlined$flatMapLatest$1(
            CrossSimCallingViewModel crossSimCallingViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = crossSimCallingViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CrossSimCallingViewModel$special$$inlined$flatMapLatest$1
                crossSimCallingViewModel$special$$inlined$flatMapLatest$1 =
                        new CrossSimCallingViewModel$special$$inlined$flatMapLatest$1(
                                this.this$0, (Continuation) obj3);
        crossSimCallingViewModel$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        crossSimCallingViewModel$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return crossSimCallingViewModel$special$$inlined$flatMapLatest$1.invokeSuspend(
                Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        boolean z;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        Unit unit = Unit.INSTANCE;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            int[] activeSubscriptionIdList =
                    this.this$0.subscriptionManager.getActiveSubscriptionIdList();
            Intrinsics.checkNotNullExpressionValue(
                    activeSubscriptionIdList, "getActiveSubscriptionIdList(...)");
            List list = ArraysKt___ArraysKt.toList(activeSubscriptionIdList);
            CrossSimCallingViewModel crossSimCallingViewModel = this.this$0;
            crossSimCallingViewModel.getClass();
            List list2 = list;
            ArrayList arrayList =
                    new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            Iterator it = list2.iterator();
            while (true) {
                z = false;
                if (!it.hasNext()) {
                    break;
                }
                arrayList.add(
                        MobileDataEnabledFlowKt.mobileDataEnabledFlow(
                                crossSimCallingViewModel.application,
                                ((Number) it.next()).intValue(),
                                false));
            }
            int i2 = FlowKt__MergeKt.$r8$clinit;
            ChannelLimitedFlowMerge channelLimitedFlowMerge =
                    new ChannelLimitedFlowMerge(
                            arrayList, EmptyCoroutineContext.INSTANCE, -2, BufferOverflow.SUSPEND);
            BufferedChannel bufferedChannel = this.this$0.updateChannel;
            Intrinsics.checkNotNullParameter(bufferedChannel, "<this>");
            ChannelLimitedFlowMerge merge =
                    FlowKt.merge(channelLimitedFlowMerge, new ChannelAsFlow(bufferedChannel, z));
            CrossSimCallingViewModel crossSimCallingViewModel2 = this.this$0;
            this.label = 1;
            FlowKt.ensureActive(flowCollector);
            Object collect =
                    merge.collect(
                            new CrossSimCallingViewModel$_init_$lambda$1$$inlined$map$1$2(
                                    flowCollector, list, crossSimCallingViewModel2),
                            this);
            CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (collect != coroutineSingletons2) {
                collect = unit;
            }
            if (collect != coroutineSingletons2) {
                collect = unit;
            }
            if (collect == coroutineSingletons) {
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
