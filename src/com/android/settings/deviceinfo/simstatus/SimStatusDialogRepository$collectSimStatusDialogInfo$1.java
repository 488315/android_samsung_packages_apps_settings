package com.android.settings.deviceinfo.simstatus;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Function;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\n\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/CoroutineScope;"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class SimStatusDialogRepository$collectSimStatusDialogInfo$1 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ Function1 $action;
    final /* synthetic */ LifecycleOwner $lifecycleOwner;
    final /* synthetic */ int $simSlotIndex;
    int label;
    final /* synthetic */ SimStatusDialogRepository this$0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\n\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u0002\n"
                    + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
            },
            d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/CoroutineScope;"},
            k = 3,
            mv = {1, 9, 0},
            xi = 48)
    /* renamed from: com.android.settings.deviceinfo.simstatus.SimStatusDialogRepository$collectSimStatusDialogInfo$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function1 $action;
        final /* synthetic */ int $simSlotIndex;
        int label;
        final /* synthetic */ SimStatusDialogRepository this$0;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.deviceinfo.simstatus.SimStatusDialogRepository$collectSimStatusDialogInfo$1$1$1, reason: invalid class name and collision with other inner class name */
        public final /* synthetic */ class C00361 implements FlowCollector, FunctionAdapter {
            public final /* synthetic */ Function1 $tmp0;

            public C00361(Function1 function1) {
                this.$tmp0 = function1;
            }

            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(Object obj, Continuation continuation) {
                this.$tmp0.invoke((SimStatusDialogRepository.SimStatusDialogInfo) obj);
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                return Unit.INSTANCE;
            }

            public final boolean equals(Object obj) {
                if ((obj instanceof FlowCollector) && (obj instanceof FunctionAdapter)) {
                    return getFunctionDelegate()
                            .equals(((FunctionAdapter) obj).getFunctionDelegate());
                }
                return false;
            }

            @Override // kotlin.jvm.internal.FunctionAdapter
            public final Function getFunctionDelegate() {
                return new FunctionReferenceImpl(
                        2,
                        this.$tmp0,
                        Intrinsics.Kotlin.class,
                        "suspendConversion0",
                        "invokeSuspend$suspendConversion0(Lkotlin/jvm/functions/Function1;Lcom/android/settings/deviceinfo/simstatus/SimStatusDialogRepository$SimStatusDialogInfo;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;",
                        0);
            }

            public final int hashCode() {
                return getFunctionDelegate().hashCode();
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(
                SimStatusDialogRepository simStatusDialogRepository,
                int i,
                Function1 function1,
                Continuation continuation) {
            super(2, continuation);
            this.this$0 = simStatusDialogRepository;
            this.$simSlotIndex = i;
            this.$action = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, this.$simSlotIndex, this.$action, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2))
                    .invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SimStatusDialogRepository simStatusDialogRepository = this.this$0;
                Flow flowOn =
                        FlowKt.flowOn(
                                FlowKt.buffer$default(
                                        FlowKt.transformLatest(
                                                simStatusDialogRepository.simSlotRepository
                                                        .subIdInSimSlotFlow(this.$simSlotIndex),
                                                new SimStatusDialogRepository$simStatusDialogInfoBySlotFlow$$inlined$flatMapLatest$1(
                                                        null, simStatusDialogRepository)),
                                        -1),
                                Dispatchers.Default);
                C00361 c00361 = new C00361(this.$action);
                this.label = 1;
                if (flowOn.collect(c00361, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(
                            "call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SimStatusDialogRepository$collectSimStatusDialogInfo$1(
            LifecycleOwner lifecycleOwner,
            SimStatusDialogRepository simStatusDialogRepository,
            int i,
            Function1 function1,
            Continuation continuation) {
        super(2, continuation);
        this.$lifecycleOwner = lifecycleOwner;
        this.this$0 = simStatusDialogRepository;
        this.$simSlotIndex = i;
        this.$action = function1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SimStatusDialogRepository$collectSimStatusDialogInfo$1(
                this.$lifecycleOwner, this.this$0, this.$simSlotIndex, this.$action, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SimStatusDialogRepository$collectSimStatusDialogInfo$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = this.$lifecycleOwner;
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 =
                    new AnonymousClass1(this.this$0, this.$simSlotIndex, this.$action, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, anonymousClass1, this)
                    == coroutineSingletons) {
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
