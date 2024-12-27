package com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.education;

import com.android.settings.R;
import com.android.settings.biometrics.fingerprint2.ui.enrollment.viewmodel.FingerprintEnrollFindSensorViewModel;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupdesign.GlifLayout;
import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

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
final class UdfpsEnrollFindSensorFragment$onCreateView$2 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ FooterBarMixin $footerBarMixin;
    int label;
    final /* synthetic */ UdfpsEnrollFindSensorFragment this$0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.education.UdfpsEnrollFindSensorFragment$onCreateView$2$1, reason: invalid class name */
    public final class AnonymousClass1 implements FlowCollector {
        public final /* synthetic */ Object $footerBarMixin;
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ UdfpsEnrollFindSensorFragment this$0;

        public /* synthetic */ AnonymousClass1(
                UdfpsEnrollFindSensorFragment udfpsEnrollFindSensorFragment, Object obj, int i) {
            this.$r8$classId = i;
            this.this$0 = udfpsEnrollFindSensorFragment;
            this.$footerBarMixin = obj;
        }

        @Override // kotlinx.coroutines.flow.FlowCollector
        public final Object emit(Object obj, Continuation continuation) {
            switch (this.$r8$classId) {
                case 0:
                    ((Boolean) obj).getClass();
                    FooterBarMixin footerBarMixin = (FooterBarMixin) this.$footerBarMixin;
                    Intrinsics.checkNotNullExpressionValue(footerBarMixin, "$footerBarMixin");
                    UdfpsEnrollFindSensorFragment udfpsEnrollFindSensorFragment = this.this$0;
                    footerBarMixin.setPrimaryButton(
                            new FooterButton(
                                    udfpsEnrollFindSensorFragment
                                            .requireActivity()
                                            .getString(
                                                    R.string
                                                            .security_settings_udfps_enroll_find_sensor_start_button),
                                    new UdfpsEnrollFindSensorFragment$onCreateView$3$1$1(
                                            udfpsEnrollFindSensorFragment, 1),
                                    5,
                                    2132083805));
                    break;
                default:
                    int i =
                            ((Boolean) obj).booleanValue()
                                    ? R.raw.udfps_edu_a11y_lottie
                                    : R.raw.udfps_edu_lottie;
                    UdfpsEnrollFindSensorFragment udfpsEnrollFindSensorFragment2 = this.this$0;
                    UdfpsEnrollFindSensorFragment$onCreateView$3$1$1
                            udfpsEnrollFindSensorFragment$onCreateView$3$1$1 =
                                    new UdfpsEnrollFindSensorFragment$onCreateView$3$1$1(
                                            udfpsEnrollFindSensorFragment2, 0);
                    udfpsEnrollFindSensorFragment2.getClass();
                    LottieAnimationView lottieAnimationView =
                            (LottieAnimationView)
                                    ((GlifLayout) this.$footerBarMixin)
                                            .findViewById(R.id.illustration_lottie);
                    if (lottieAnimationView != null) {
                        lottieAnimationView.setAnimation(i);
                    }
                    if (lottieAnimationView != null) {
                        lottieAnimationView.playAnimation$1();
                    }
                    if (lottieAnimationView != null) {
                        lottieAnimationView.setOnClickListener(
                                udfpsEnrollFindSensorFragment$onCreateView$3$1$1);
                    }
                    if (lottieAnimationView != null) {
                        lottieAnimationView.setVisibility(0);
                    }
                    break;
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UdfpsEnrollFindSensorFragment$onCreateView$2(
            UdfpsEnrollFindSensorFragment udfpsEnrollFindSensorFragment,
            FooterBarMixin footerBarMixin,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = udfpsEnrollFindSensorFragment;
        this.$footerBarMixin = footerBarMixin;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UdfpsEnrollFindSensorFragment$onCreateView$2(
                this.this$0, this.$footerBarMixin, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UdfpsEnrollFindSensorFragment$onCreateView$2)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FingerprintEnrollFindSensorViewModel access$getViewModel =
                    UdfpsEnrollFindSensorFragment.access$getViewModel(this.this$0);
            AnonymousClass1 anonymousClass1 =
                    new AnonymousClass1(this.this$0, this.$footerBarMixin, 0);
            this.label = 1;
            if (access$getViewModel.showPrimaryButton.collect(anonymousClass1, this)
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
