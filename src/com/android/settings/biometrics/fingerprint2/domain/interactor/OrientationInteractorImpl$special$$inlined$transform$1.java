package com.android.settings.biometrics.fingerprint2.domain.interactor;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0010\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0002\u0010\u0004\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u00028\u00010\u0002H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"
        },
        d2 = {
            "T",
            "R",
            "Lkotlinx/coroutines/flow/FlowCollector;",
            ApnSettings.MVNO_NONE,
            "<anonymous>",
            "(Lkotlinx/coroutines/flow/FlowCollector;)V"
        },
        k = 3,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class OrientationInteractorImpl$special$$inlined$transform$1 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ Flow $this_transform;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ OrientationInteractorImpl this$0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.biometrics.fingerprint2.domain.interactor.OrientationInteractorImpl$special$$inlined$transform$1$1, reason: invalid class name */
    public final class AnonymousClass1 implements FlowCollector {
        public final /* synthetic */ FlowCollector $$this$flow;
        public final /* synthetic */ OrientationInteractorImpl this$0;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        @Metadata(
                k = 3,
                mv = {1, 9, 0},
                xi = 48)
        /* renamed from: com.android.settings.biometrics.fingerprint2.domain.interactor.OrientationInteractorImpl$special$$inlined$transform$1$1$1, reason: invalid class name and collision with other inner class name */
        public final class C00221 extends ContinuationImpl {
            int label;
            /* synthetic */ Object result;

            public C00221(Continuation continuation) {
                super(continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return AnonymousClass1.this.emit(null, this);
            }
        }

        public AnonymousClass1(
                FlowCollector flowCollector, OrientationInteractorImpl orientationInteractorImpl) {
            this.this$0 = orientationInteractorImpl;
            this.$$this$flow = flowCollector;
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
            /*
                r4 = this;
                boolean r0 = r6 instanceof com.android.settings.biometrics.fingerprint2.domain.interactor.OrientationInteractorImpl$special$$inlined$transform$1.AnonymousClass1.C00221
                if (r0 == 0) goto L13
                r0 = r6
                com.android.settings.biometrics.fingerprint2.domain.interactor.OrientationInteractorImpl$special$$inlined$transform$1$1$1 r0 = (com.android.settings.biometrics.fingerprint2.domain.interactor.OrientationInteractorImpl$special$$inlined$transform$1.AnonymousClass1.C00221) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                com.android.settings.biometrics.fingerprint2.domain.interactor.OrientationInteractorImpl$special$$inlined$transform$1$1$1 r0 = new com.android.settings.biometrics.fingerprint2.domain.interactor.OrientationInteractorImpl$special$$inlined$transform$1$1$1
                r0.<init>(r6)
            L18:
                java.lang.Object r6 = r0.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r2 = r0.label
                r3 = 1
                if (r2 == 0) goto L2f
                if (r2 != r3) goto L27
                kotlin.ResultKt.throwOnFailure(r6)
                goto L53
            L27:
                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                r4.<init>(r5)
                throw r4
            L2f:
                kotlin.ResultKt.throwOnFailure(r6)
                java.lang.Number r5 = (java.lang.Number) r5
                r5.intValue()
                com.android.settings.biometrics.fingerprint2.domain.interactor.OrientationInteractorImpl r5 = r4.this$0
                android.content.Context r5 = r5.context
                android.view.Display r5 = r5.getDisplay()
                int r5 = r5.getRotation()
                java.lang.Integer r6 = new java.lang.Integer
                r6.<init>(r5)
                r0.label = r3
                kotlinx.coroutines.flow.FlowCollector r4 = r4.$$this$flow
                java.lang.Object r4 = r4.emit(r6, r0)
                if (r4 != r1) goto L53
                return r1
            L53:
                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                return r4
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.settings.biometrics.fingerprint2.domain.interactor.OrientationInteractorImpl$special$$inlined$transform$1.AnonymousClass1.emit(java.lang.Object,"
                        + " kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OrientationInteractorImpl$special$$inlined$transform$1(
            Flow flow,
            Continuation continuation,
            OrientationInteractorImpl orientationInteractorImpl) {
        super(2, continuation);
        this.$this_transform = flow;
        this.this$0 = orientationInteractorImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        OrientationInteractorImpl$special$$inlined$transform$1
                orientationInteractorImpl$special$$inlined$transform$1 =
                        new OrientationInteractorImpl$special$$inlined$transform$1(
                                this.$this_transform, continuation, this.this$0);
        orientationInteractorImpl$special$$inlined$transform$1.L$0 = obj;
        return orientationInteractorImpl$special$$inlined$transform$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((OrientationInteractorImpl$special$$inlined$transform$1)
                        create((FlowCollector) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow flow = this.$this_transform;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(flowCollector, this.this$0);
            this.label = 1;
            if (flow.collect(anonymousClass1, this) == coroutineSingletons) {
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
