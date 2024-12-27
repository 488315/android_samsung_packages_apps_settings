package com.android.settings.biometrics2.ui.view;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.RepeatOnLifecycleKt;

import com.android.settings.R;
import com.android.settings.biometrics2.ui.model.FingerprintEnrollIntroStatus;
import com.android.settings.biometrics2.ui.model.FingerprintEnrollable;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollIntroViewModel;

import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupcompat.template.Mixin;
import com.google.android.setupdesign.GlifLayout;
import com.google.android.setupdesign.template.RequireScrollMixin;
import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Function;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.CoroutineScope;
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
final class FingerprintEnrollIntroFragment$collectPageStatusFlowIfNeed$1 extends SuspendLambda
        implements Function2 {
    int label;
    final /* synthetic */ FingerprintEnrollIntroFragment this$0;

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
    /* renamed from: com.android.settings.biometrics2.ui.view.FingerprintEnrollIntroFragment$collectPageStatusFlowIfNeed$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ FingerprintEnrollIntroFragment this$0;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.biometrics2.ui.view.FingerprintEnrollIntroFragment$collectPageStatusFlowIfNeed$1$2$1, reason: invalid class name */
        public final /* synthetic */ class AnonymousClass1
                implements FlowCollector, FunctionAdapter {
            public final /* synthetic */ FingerprintEnrollIntroFragment $tmp0;

            public AnonymousClass1(FingerprintEnrollIntroFragment fingerprintEnrollIntroFragment) {
                this.$tmp0 = fingerprintEnrollIntroFragment;
            }

            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(Object obj, Continuation continuation) {
                FingerprintEnrollIntroFragment.access$updateFooterButtons(
                        this.$tmp0, (FingerprintEnrollIntroStatus) obj);
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
                        FingerprintEnrollIntroFragment.class,
                        "updateFooterButtons",
                        "updateFooterButtons(Lcom/android/settings/biometrics2/ui/model/FingerprintEnrollIntroStatus;)V",
                        4);
            }

            public final int hashCode() {
                return getFunctionDelegate().hashCode();
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(
                FingerprintEnrollIntroFragment fingerprintEnrollIntroFragment,
                Continuation continuation) {
            super(2, continuation);
            this.this$0 = fingerprintEnrollIntroFragment;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2))
                    .invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FingerprintEnrollIntroViewModel fingerprintEnrollIntroViewModel =
                        this.this$0._viewModel;
                Intrinsics.checkNotNull(fingerprintEnrollIntroViewModel);
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0);
                this.label = 1;
                if (fingerprintEnrollIntroViewModel.pageStatusFlow.collect(anonymousClass1, this)
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
    public FingerprintEnrollIntroFragment$collectPageStatusFlowIfNeed$1(
            FingerprintEnrollIntroFragment fingerprintEnrollIntroFragment,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = fingerprintEnrollIntroFragment;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FingerprintEnrollIntroFragment$collectPageStatusFlowIfNeed$1(
                this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FingerprintEnrollIntroFragment$collectPageStatusFlowIfNeed$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FingerprintEnrollIntroViewModel fingerprintEnrollIntroViewModel =
                    this.this$0._viewModel;
            Intrinsics.checkNotNull(fingerprintEnrollIntroViewModel);
            this.label = 1;
            obj = FlowKt.first(fingerprintEnrollIntroViewModel.pageStatusFlow, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException(
                            "call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
        }
        FingerprintEnrollIntroStatus fingerprintEnrollIntroStatus =
                (FingerprintEnrollIntroStatus) obj;
        Log.d(
                "FingerprintEnrollIntroFragment",
                "collectPageStatusFlowIfNeed status:" + fingerprintEnrollIntroStatus);
        if (!fingerprintEnrollIntroStatus.mHasScrollToBottom) {
            if (fingerprintEnrollIntroStatus.enrollableStatus
                    != FingerprintEnrollable.FINGERPRINT_ENROLLABLE_ERROR_REACH_MAX) {
                GlifLayout glifLayout = this.this$0.introView;
                Intrinsics.checkNotNull(glifLayout);
                Mixin mixin = glifLayout.getMixin(RequireScrollMixin.class);
                final FingerprintEnrollIntroFragment fingerprintEnrollIntroFragment = this.this$0;
                RequireScrollMixin requireScrollMixin = (RequireScrollMixin) mixin;
                FragmentActivity requireActivity = fingerprintEnrollIntroFragment.requireActivity();
                FooterButton footerButton = fingerprintEnrollIntroFragment.primaryFooterButton;
                Intrinsics.checkNotNull(footerButton);
                requireScrollMixin.requireScrollWithButton(
                        requireActivity,
                        footerButton,
                        R.string.security_settings_face_enroll_introduction_more,
                        fingerprintEnrollIntroFragment.onNextClickListener);
                requireScrollMixin.listener =
                        new RequireScrollMixin
                                .OnRequireScrollStateChangedListener() { // from class:
                                                                         // com.android.settings.biometrics2.ui.view.FingerprintEnrollIntroFragment$collectPageStatusFlowIfNeed$1$1$1
                            @Override // com.google.android.setupdesign.template.RequireScrollMixin.OnRequireScrollStateChangedListener
                            public final void onRequireScrollStateChanged(boolean z) {
                                FingerprintEnrollIntroFragment fingerprintEnrollIntroFragment2 =
                                        FingerprintEnrollIntroFragment.this;
                                FingerprintEnrollIntroViewModel fingerprintEnrollIntroViewModel2 =
                                        fingerprintEnrollIntroFragment2._viewModel;
                                Intrinsics.checkNotNull(fingerprintEnrollIntroViewModel2);
                                fingerprintEnrollIntroViewModel2.setHasScrolledToBottom(
                                        !z,
                                        LifecycleOwnerKt.getLifecycleScope(
                                                fingerprintEnrollIntroFragment2));
                            }
                        };
                FingerprintEnrollIntroFragment fingerprintEnrollIntroFragment2 = this.this$0;
                Lifecycle.State state = Lifecycle.State.STARTED;
                AnonymousClass2 anonymousClass2 =
                        new AnonymousClass2(fingerprintEnrollIntroFragment2, null);
                this.label = 2;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(
                                fingerprintEnrollIntroFragment2, state, anonymousClass2, this)
                        == coroutineSingletons) {
                    return coroutineSingletons;
                }
                return Unit.INSTANCE;
            }
        }
        FingerprintEnrollIntroFragment.access$updateFooterButtons(
                this.this$0, fingerprintEnrollIntroStatus);
        return Unit.INSTANCE;
    }
}
