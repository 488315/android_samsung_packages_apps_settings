package com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.RepeatOnLifecycleKt;

import com.android.settings.R;
import com.android.settings.biometrics.fingerprint2.ui.enrollment.viewmodel.FingerprintEnrollConfirmationViewModel;

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;

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
final class FingerprintEnrollConfirmationV2Fragment$onCreateView$1$1 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ FooterBarMixin $mixin;
    int label;
    final /* synthetic */ FingerprintEnrollConfirmationV2Fragment this$0;

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
    /* renamed from: com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.FingerprintEnrollConfirmationV2Fragment$onCreateView$1$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ FooterBarMixin $mixin;
        int label;
        final /* synthetic */ FingerprintEnrollConfirmationV2Fragment this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(
                FingerprintEnrollConfirmationV2Fragment fingerprintEnrollConfirmationV2Fragment,
                FooterBarMixin footerBarMixin,
                Continuation continuation) {
            super(2, continuation);
            this.this$0 = fingerprintEnrollConfirmationV2Fragment;
            this.$mixin = footerBarMixin;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, this.$mixin, continuation);
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
                FingerprintEnrollConfirmationViewModel fingerprintEnrollConfirmationViewModel =
                        (FingerprintEnrollConfirmationViewModel)
                                this.this$0.viewModel$delegate.getValue();
                final FooterBarMixin footerBarMixin = this.$mixin;
                final FingerprintEnrollConfirmationV2Fragment
                        fingerprintEnrollConfirmationV2Fragment = this.this$0;
                FlowCollector flowCollector =
                        new FlowCollector() { // from class:
                                              // com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.FingerprintEnrollConfirmationV2Fragment.onCreateView.1.1.1.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                ((Boolean) obj2).getClass();
                                FingerprintEnrollConfirmationV2Fragment
                                        fingerprintEnrollConfirmationV2Fragment2 =
                                                fingerprintEnrollConfirmationV2Fragment;
                                FooterBarMixin.this.setSecondaryButton(
                                        new FooterButton(
                                                fingerprintEnrollConfirmationV2Fragment2
                                                        .requireContext()
                                                        .getString(
                                                                R.string
                                                                        .fingerprint_enroll_button_add),
                                                new FingerprintEnrollConfirmationV2Fragment$onCreateView$1$2(
                                                        fingerprintEnrollConfirmationV2Fragment2,
                                                        1),
                                                7,
                                                2132083806),
                                        false);
                                return Unit.INSTANCE;
                            }
                        };
                this.label = 1;
                if (fingerprintEnrollConfirmationViewModel.isAddAnotherButtonVisible.collect(
                                flowCollector, this)
                        == coroutineSingletons) {
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
    public FingerprintEnrollConfirmationV2Fragment$onCreateView$1$1(
            FingerprintEnrollConfirmationV2Fragment fingerprintEnrollConfirmationV2Fragment,
            FooterBarMixin footerBarMixin,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = fingerprintEnrollConfirmationV2Fragment;
        this.$mixin = footerBarMixin;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FingerprintEnrollConfirmationV2Fragment$onCreateView$1$1(
                this.this$0, this.$mixin, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FingerprintEnrollConfirmationV2Fragment$onCreateView$1$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FingerprintEnrollConfirmationV2Fragment fingerprintEnrollConfirmationV2Fragment =
                    this.this$0;
            Lifecycle.State state = Lifecycle.State.RESUMED;
            AnonymousClass1 anonymousClass1 =
                    new AnonymousClass1(fingerprintEnrollConfirmationV2Fragment, this.$mixin, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(
                            fingerprintEnrollConfirmationV2Fragment, state, anonymousClass1, this)
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
