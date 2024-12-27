package com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.education;

import com.android.settings.R;
import com.android.settings.biometrics.fingerprint2.ui.enrollment.viewmodel.FingerprintEnrollFindSensorViewModel;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupdesign.GlifLayout;
import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.Pair;
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
final class SfpsEnrollFindSensorFragment$onCreateView$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ GlifLayout $view;
    int label;
    final /* synthetic */ SfpsEnrollFindSensorFragment this$0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.education.SfpsEnrollFindSensorFragment$onCreateView$2$1, reason: invalid class name */
    public final class AnonymousClass1 implements FlowCollector {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object $view;
        public final /* synthetic */ SfpsEnrollFindSensorFragment this$0;

        public /* synthetic */ AnonymousClass1(
                SfpsEnrollFindSensorFragment sfpsEnrollFindSensorFragment, Object obj, int i) {
            this.$r8$classId = i;
            this.this$0 = sfpsEnrollFindSensorFragment;
            this.$view = obj;
        }

        @Override // kotlinx.coroutines.flow.FlowCollector
        public final Object emit(Object obj, Continuation continuation) {
            switch (this.$r8$classId) {
                case 0:
                    Pair pair = (Pair) obj;
                    boolean booleanValue = ((Boolean) pair.getFirst()).booleanValue();
                    int intValue = ((Number) pair.getSecond()).intValue();
                    this.this$0.getClass();
                    int i =
                            intValue != 1
                                    ? intValue != 2
                                            ? intValue != 3
                                                    ? booleanValue
                                                            ? R.raw
                                                                    .fingerprint_edu_lottie_folded_top_right
                                                            : R.raw
                                                                    .fingerprint_edu_lottie_landscape_top_right
                                                    : booleanValue
                                                            ? R.raw
                                                                    .fingerprint_edu_lottie_folded_bottom_right
                                                            : R.raw
                                                                    .fingerprint_edu_lottie_portrait_bottom_right
                                            : booleanValue
                                                    ? R.raw
                                                            .fingerprint_edu_lottie_folded_bottom_left
                                                    : R.raw
                                                            .fingerprint_edu_lottie_landscape_bottom_left
                                    : booleanValue
                                            ? R.raw.fingerprint_edu_lottie_folded_top_left
                                            : R.raw.fingerprint_edu_lottie_portrait_top_left;
                    LottieAnimationView lottieAnimationView =
                            (LottieAnimationView)
                                    ((GlifLayout) this.$view)
                                            .findViewById(R.id.illustration_lottie);
                    if (lottieAnimationView != null) {
                        lottieAnimationView.setAnimation(i);
                    }
                    if (lottieAnimationView != null) {
                        lottieAnimationView.playAnimation$1();
                    }
                    if (lottieAnimationView != null) {
                        lottieAnimationView.setOnClickListener(null);
                    }
                    if (lottieAnimationView != null) {
                        lottieAnimationView.setVisibility(0);
                    }
                    break;
                default:
                    ((Boolean) obj).getClass();
                    FooterBarMixin footerBarMixin = (FooterBarMixin) this.$view;
                    Intrinsics.checkNotNullExpressionValue(footerBarMixin, "$footerBarMixin");
                    SfpsEnrollFindSensorFragment sfpsEnrollFindSensorFragment = this.this$0;
                    footerBarMixin.setPrimaryButton(
                            new FooterButton(
                                    sfpsEnrollFindSensorFragment
                                            .requireActivity()
                                            .getString(
                                                    R.string
                                                            .security_settings_udfps_enroll_find_sensor_start_button),
                                    new SfpsEnrollFindSensorFragment$setupPrimaryButton$1(
                                            sfpsEnrollFindSensorFragment, 0),
                                    5,
                                    2132083805));
                    break;
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SfpsEnrollFindSensorFragment$onCreateView$2(
            SfpsEnrollFindSensorFragment sfpsEnrollFindSensorFragment,
            GlifLayout glifLayout,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = sfpsEnrollFindSensorFragment;
        this.$view = glifLayout;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SfpsEnrollFindSensorFragment$onCreateView$2(
                this.this$0, this.$view, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SfpsEnrollFindSensorFragment$onCreateView$2)
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
                    SfpsEnrollFindSensorFragment.access$getViewModel(this.this$0);
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, this.$view, 0);
            this.label = 1;
            if (access$getViewModel.sfpsLottieInfo.collect(anonymousClass1, this)
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
