package com.android.settings.network.telephony;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.internal.CombineKt;

import java.util.ArrayList;

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
public final class CallStateRepository$isInCallFlow$$inlined$flatMapLatest$1 extends SuspendLambda
        implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ CallStateRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CallStateRepository$isInCallFlow$$inlined$flatMapLatest$1(
            Continuation continuation, CallStateRepository callStateRepository) {
        super(3, continuation);
        this.this$0 = callStateRepository;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CallStateRepository$isInCallFlow$$inlined$flatMapLatest$1
                callStateRepository$isInCallFlow$$inlined$flatMapLatest$1 =
                        new CallStateRepository$isInCallFlow$$inlined$flatMapLatest$1(
                                (Continuation) obj3, this.this$0);
        callStateRepository$isInCallFlow$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        callStateRepository$isInCallFlow$$inlined$flatMapLatest$1.L$1 = obj2;
        return callStateRepository$isInCallFlow$$inlined$flatMapLatest$1.invokeSuspend(
                Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flow;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            int[] activeSubscriptionIdList =
                    this.this$0.subscriptionManager.getActiveSubscriptionIdList();
            Intrinsics.checkNotNullExpressionValue(
                    activeSubscriptionIdList, "getActiveSubscriptionIdList(...)");
            if (activeSubscriptionIdList.length == 0) {
                flow = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
            } else {
                ArrayList arrayList = new ArrayList(activeSubscriptionIdList.length);
                for (int i2 : activeSubscriptionIdList) {
                    arrayList.add(
                            TelephonyRepositoryKt.telephonyCallbackFlow(
                                    this.this$0.context,
                                    i2,
                                    CallStateRepository$callStateFlow$1.INSTANCE));
                }
                final Flow[] flowArr =
                        (Flow[])
                                CollectionsKt___CollectionsKt.toList(arrayList)
                                        .toArray(new Flow[0]);
                flow =
                        new Flow() { // from class:
                            // com.android.settings.network.telephony.CallStateRepository$isInCallFlow$lambda$2$$inlined$combine$1

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
                            /* renamed from: com.android.settings.network.telephony.CallStateRepository$isInCallFlow$lambda$2$$inlined$combine$1$3, reason: invalid class name */
                            public final class AnonymousClass3 extends SuspendLambda
                                    implements Function3 {
                                private /* synthetic */ Object L$0;
                                /* synthetic */ Object L$1;
                                int label;

                                @Override // kotlin.jvm.functions.Function3
                                public final Object invoke(Object obj, Object obj2, Object obj3) {
                                    AnonymousClass3 anonymousClass3 =
                                            new AnonymousClass3(3, (Continuation) obj3);
                                    anonymousClass3.L$0 = (FlowCollector) obj;
                                    anonymousClass3.L$1 = (Object[]) obj2;
                                    return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Object invokeSuspend(Object obj) {
                                    CoroutineSingletons coroutineSingletons =
                                            CoroutineSingletons.COROUTINE_SUSPENDED;
                                    int i = this.label;
                                    if (i == 0) {
                                        ResultKt.throwOnFailure(obj);
                                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                                        Integer[] numArr = (Integer[]) ((Object[]) this.L$1);
                                        int length = numArr.length;
                                        boolean z = false;
                                        int i2 = 0;
                                        while (true) {
                                            if (i2 >= length) {
                                                break;
                                            }
                                            if (numArr[i2].intValue() != 0) {
                                                z = true;
                                                break;
                                            }
                                            i2++;
                                        }
                                        Boolean valueOf = Boolean.valueOf(z);
                                        this.label = 1;
                                        if (flowCollector.emit(valueOf, this)
                                                == coroutineSingletons) {
                                            return coroutineSingletons;
                                        }
                                    } else {
                                        if (i != 1) {
                                            throw new IllegalStateException(
                                                    "call to 'resume' before 'invoke' with"
                                                        + " coroutine");
                                        }
                                        ResultKt.throwOnFailure(obj);
                                    }
                                    return Unit.INSTANCE;
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(
                                    FlowCollector flowCollector2, Continuation continuation) {
                                final Flow[] flowArr2 = flowArr;
                                Object combineInternal =
                                        CombineKt.combineInternal(
                                                continuation,
                                                new Function0() { // from class:
                                                    // com.android.settings.network.telephony.CallStateRepository$isInCallFlow$lambda$2$$inlined$combine$1.2
                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    {
                                                        super(0);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function0
                                                    /* renamed from: invoke */
                                                    public final Object mo1068invoke() {
                                                        return new Integer[flowArr2.length];
                                                    }
                                                },
                                                new AnonymousClass3(3, null),
                                                flowCollector2,
                                                flowArr2);
                                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED
                                        ? combineInternal
                                        : Unit.INSTANCE;
                            }
                        };
            }
            this.label = 1;
            if (FlowKt.emitAll(this, flow, flowCollector) == coroutineSingletons) {
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
