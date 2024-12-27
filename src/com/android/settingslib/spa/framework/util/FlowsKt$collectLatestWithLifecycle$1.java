package com.android.settingslib.spa.framework.util;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

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
/* loaded from: classes2.dex */
final class FlowsKt$collectLatestWithLifecycle$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function2 $action;
    final /* synthetic */ LifecycleOwner $lifecycleOwner;
    final /* synthetic */ Lifecycle.State $minActiveState;
    final /* synthetic */ Flow $this_collectLatestWithLifecycle;
    int label;

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
    /* renamed from: com.android.settingslib.spa.framework.util.FlowsKt$collectLatestWithLifecycle$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function2 $action;
        final /* synthetic */ Flow $this_collectLatestWithLifecycle;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Flow flow, Function2 function2, Continuation continuation) {
            super(2, continuation);
            this.$this_collectLatestWithLifecycle = flow;
            this.$action = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(
                    this.$this_collectLatestWithLifecycle, this.$action, continuation);
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
                Flow flow = this.$this_collectLatestWithLifecycle;
                Function2 function2 = this.$action;
                this.label = 1;
                if (FlowKt.collectLatest(flow, function2, this) == coroutineSingletons) {
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
    public FlowsKt$collectLatestWithLifecycle$1(
            LifecycleOwner lifecycleOwner,
            Lifecycle.State state,
            Flow flow,
            Function2 function2,
            Continuation continuation) {
        super(2, continuation);
        this.$lifecycleOwner = lifecycleOwner;
        this.$minActiveState = state;
        this.$this_collectLatestWithLifecycle = flow;
        this.$action = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FlowsKt$collectLatestWithLifecycle$1(
                this.$lifecycleOwner,
                this.$minActiveState,
                this.$this_collectLatestWithLifecycle,
                this.$action,
                continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FlowsKt$collectLatestWithLifecycle$1)
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
            Lifecycle.State state = this.$minActiveState;
            AnonymousClass1 anonymousClass1 =
                    new AnonymousClass1(this.$this_collectLatestWithLifecycle, this.$action, null);
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
