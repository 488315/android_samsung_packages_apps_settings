package com.android.settings.biometrics2.ui.view;

import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.RepeatOnLifecycleKt;

import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollIntroAction;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollIntroViewModel;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Function;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.FunctionAdapter;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;

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
final class FingerprintEnrollmentActivity$attachIntroViewModel$1 extends SuspendLambda
        implements Function2 {
    int label;
    final /* synthetic */ FingerprintEnrollmentActivity this$0;

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
    /* renamed from: com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$attachIntroViewModel$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ FingerprintEnrollmentActivity this$0;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$attachIntroViewModel$1$1$1, reason: invalid class name and collision with other inner class name */
        public final /* synthetic */ class C00341 implements FlowCollector, FunctionAdapter {
            public final /* synthetic */ FingerprintEnrollmentActivity $tmp0;

            public C00341(FingerprintEnrollmentActivity fingerprintEnrollmentActivity) {
                this.$tmp0 = fingerprintEnrollmentActivity;
            }

            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(Object obj, Continuation continuation) {
                FingerprintEnrollIntroAction fingerprintEnrollIntroAction =
                        (FingerprintEnrollIntroAction) obj;
                int i = FingerprintEnrollmentActivity.$r8$clinit;
                FingerprintEnrollmentActivity fingerprintEnrollmentActivity = this.$tmp0;
                fingerprintEnrollmentActivity.getClass();
                Log.d(
                        "FingerprintEnrollmentActivity",
                        "onIntroAction(" + fingerprintEnrollIntroAction + ")");
                int ordinal = fingerprintEnrollIntroAction.ordinal();
                if (ordinal == 0) {
                    fingerprintEnrollmentActivity.onSetActivityResult(new ActivityResult(1, null));
                } else if (ordinal == 1) {
                    fingerprintEnrollmentActivity.startFindSensorFragment();
                } else if (ordinal == 2) {
                    fingerprintEnrollmentActivity.onSetActivityResult(new ActivityResult(2, null));
                }
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
                return new AdaptedFunctionReference(
                        2,
                        this.$tmp0,
                        FingerprintEnrollmentActivity.class,
                        "onIntroAction",
                        "onIntroAction(Lcom/android/settings/biometrics2/ui/viewmodel/FingerprintEnrollIntroAction;)V",
                        4);
            }

            public final int hashCode() {
                return getFunctionDelegate().hashCode();
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(
                FingerprintEnrollmentActivity fingerprintEnrollmentActivity,
                Continuation continuation) {
            super(2, continuation);
            this.this$0 = fingerprintEnrollmentActivity;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2))
                    .invokeSuspend(Unit.INSTANCE);
            return CoroutineSingletons.COROUTINE_SUSPENDED;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ReadonlySharedFlow asSharedFlow =
                        FlowKt.asSharedFlow(
                                ((FingerprintEnrollIntroViewModel)
                                                this.this$0.introViewModel$delegate.getValue())
                                        ._actionFlow);
                C00341 c00341 = new C00341(this.this$0);
                this.label = 1;
                if (asSharedFlow.$$delegate_0.collect(c00341, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(
                            "call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FingerprintEnrollmentActivity$attachIntroViewModel$1(
            FingerprintEnrollmentActivity fingerprintEnrollmentActivity,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = fingerprintEnrollmentActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FingerprintEnrollmentActivity$attachIntroViewModel$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FingerprintEnrollmentActivity$attachIntroViewModel$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FingerprintEnrollmentActivity fingerprintEnrollmentActivity = this.this$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 =
                    new AnonymousClass1(fingerprintEnrollmentActivity, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(
                            fingerprintEnrollmentActivity, state, anonymousClass1, this)
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
