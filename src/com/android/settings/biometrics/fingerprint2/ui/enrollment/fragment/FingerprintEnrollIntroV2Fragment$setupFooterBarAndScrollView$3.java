package com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment;

import com.android.settings.R;
import com.android.settings.biometrics.fingerprint2.ui.enrollment.viewmodel.FingerprintScrollViewModel;

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
final class FingerprintEnrollIntroV2Fragment$setupFooterBarAndScrollView$3 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ FooterButton $primaryButton;
    final /* synthetic */ FooterButton $secondaryButton;
    int label;
    final /* synthetic */ FingerprintEnrollIntroV2Fragment this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FingerprintEnrollIntroV2Fragment$setupFooterBarAndScrollView$3(
            FingerprintEnrollIntroV2Fragment fingerprintEnrollIntroV2Fragment,
            FooterButton footerButton,
            FooterButton footerButton2,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = fingerprintEnrollIntroV2Fragment;
        this.$primaryButton = footerButton;
        this.$secondaryButton = footerButton2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FingerprintEnrollIntroV2Fragment$setupFooterBarAndScrollView$3(
                this.this$0, this.$primaryButton, this.$secondaryButton, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FingerprintEnrollIntroV2Fragment$setupFooterBarAndScrollView$3)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FingerprintScrollViewModel fingerprintScrollViewModel =
                    (FingerprintScrollViewModel)
                            this.this$0.fingerprintScrollViewModel$delegate.getValue();
            final FooterButton footerButton = this.$primaryButton;
            final FingerprintEnrollIntroV2Fragment fingerprintEnrollIntroV2Fragment = this.this$0;
            final FooterButton footerButton2 = this.$secondaryButton;
            FlowCollector flowCollector =
                    new FlowCollector() { // from class:
                                          // com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.FingerprintEnrollIntroV2Fragment$setupFooterBarAndScrollView$3.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            boolean booleanValue = ((Boolean) obj2).booleanValue();
                            FooterButton footerButton3 = footerButton2;
                            if (booleanValue) {
                                FooterButton.this.setText(
                                        fingerprintEnrollIntroV2Fragment.requireContext(),
                                        R.string
                                                .security_settings_fingerprint_enroll_introduction_agree);
                                footerButton3.setVisibility(0);
                            } else {
                                footerButton3.setVisibility(4);
                            }
                            return Unit.INSTANCE;
                        }
                    };
            this.label = 1;
            if (fingerprintScrollViewModel.hasReadConsentScreen.$$delegate_0.collect(
                            flowCollector, this)
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
