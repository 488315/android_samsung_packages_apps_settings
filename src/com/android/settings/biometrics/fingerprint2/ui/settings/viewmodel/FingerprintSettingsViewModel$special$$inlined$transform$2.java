package com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel;

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
public final class FingerprintSettingsViewModel$special$$inlined$transform$2 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ Flow $this_transform;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ FingerprintSettingsViewModel this$0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$transform$2$1, reason: invalid class name */
    public final class AnonymousClass1 implements FlowCollector {
        public final /* synthetic */ FlowCollector $$this$flow;
        public final /* synthetic */ FingerprintSettingsViewModel this$0;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        @Metadata(
                k = 3,
                mv = {1, 9, 0},
                xi = 48)
        /* renamed from: com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$transform$2$1$1, reason: invalid class name and collision with other inner class name */
        public final class C00271 extends ContinuationImpl {
            Object L$0;
            Object L$1;
            int label;
            /* synthetic */ Object result;

            public C00271(Continuation continuation) {
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
                FlowCollector flowCollector,
                FingerprintSettingsViewModel fingerprintSettingsViewModel) {
            this.this$0 = fingerprintSettingsViewModel;
            this.$$this$flow = flowCollector;
        }

        /* JADX WARN: Removed duplicated region for block: B:25:0x009a A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:27:0x003f  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation r9) {
            /*
                r7 = this;
                boolean r0 = r9 instanceof com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$transform$2.AnonymousClass1.C00271
                if (r0 == 0) goto L13
                r0 = r9
                com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$transform$2$1$1 r0 = (com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$transform$2.AnonymousClass1.C00271) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$transform$2$1$1 r0 = new com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$transform$2$1$1
                r0.<init>(r9)
            L18:
                java.lang.Object r9 = r0.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r2 = r0.label
                r3 = 2
                r4 = 0
                r5 = 1
                if (r2 == 0) goto L3f
                if (r2 == r5) goto L33
                if (r2 != r3) goto L2b
                kotlin.ResultKt.throwOnFailure(r9)
                goto L9b
            L2b:
                java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                r7.<init>(r8)
                throw r7
            L33:
                java.lang.Object r7 = r0.L$1
                kotlinx.coroutines.flow.FlowCollector r7 = (kotlinx.coroutines.flow.FlowCollector) r7
                java.lang.Object r8 = r0.L$0
                java.util.List r8 = (java.util.List) r8
                kotlin.ResultKt.throwOnFailure(r9)
                goto L77
            L3f:
                kotlin.ResultKt.throwOnFailure(r9)
                java.util.List r8 = (java.util.List) r8
                com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel r9 = r7.this$0
                com.android.settings.biometrics.fingerprint2.domain.interactor.FingerprintManagerInteractorImpl r9 = r9.fingerprintManagerInteractor
                r0.L$0 = r8
                kotlinx.coroutines.flow.FlowCollector r7 = r7.$$this$flow
                r0.L$1 = r7
                r0.label = r5
                r9.getClass()
                kotlinx.coroutines.CancellableContinuationImpl r2 = new kotlinx.coroutines.CancellableContinuationImpl
                kotlin.coroutines.Continuation r6 = kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.intercepted(r0)
                r2.<init>(r5, r6)
                r2.initCancellability()
                android.hardware.fingerprint.FingerprintManager r9 = r9.fingerprintManager
                if (r9 == 0) goto L6c
                boolean r9 = r9.isPowerbuttonFps()
                java.lang.Boolean r9 = java.lang.Boolean.valueOf(r9)
                goto L6d
            L6c:
                r9 = r4
            L6d:
                r2.resumeWith(r9)
                java.lang.Object r9 = r2.getResult()
                if (r9 != r1) goto L77
                return r1
            L77:
                java.lang.Boolean r2 = java.lang.Boolean.TRUE
                boolean r9 = kotlin.jvm.internal.Intrinsics.areEqual(r9, r2)
                if (r9 == 0) goto L89
                java.util.Collection r8 = (java.util.Collection) r8
                if (r8 == 0) goto L89
                boolean r8 = r8.isEmpty()
                if (r8 == 0) goto L8a
            L89:
                r5 = 0
            L8a:
                java.lang.Boolean r8 = java.lang.Boolean.valueOf(r5)
                r0.L$0 = r4
                r0.L$1 = r4
                r0.label = r3
                java.lang.Object r7 = r7.emit(r8, r0)
                if (r7 != r1) goto L9b
                return r1
            L9b:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                return r7
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$transform$2.AnonymousClass1.emit(java.lang.Object,"
                        + " kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FingerprintSettingsViewModel$special$$inlined$transform$2(
            Flow flow,
            Continuation continuation,
            FingerprintSettingsViewModel fingerprintSettingsViewModel) {
        super(2, continuation);
        this.$this_transform = flow;
        this.this$0 = fingerprintSettingsViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FingerprintSettingsViewModel$special$$inlined$transform$2
                fingerprintSettingsViewModel$special$$inlined$transform$2 =
                        new FingerprintSettingsViewModel$special$$inlined$transform$2(
                                this.$this_transform, continuation, this.this$0);
        fingerprintSettingsViewModel$special$$inlined$transform$2.L$0 = obj;
        return fingerprintSettingsViewModel$special$$inlined$transform$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FingerprintSettingsViewModel$special$$inlined$transform$2)
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
