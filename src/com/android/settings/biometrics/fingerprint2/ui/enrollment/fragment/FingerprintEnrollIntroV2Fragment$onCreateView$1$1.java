package com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;

import com.android.settings.R;
import com.android.settings.biometrics.fingerprint2.lib.model.Unicorn;
import com.android.settings.biometrics.fingerprint2.ui.enrollment.viewmodel.FingerprintAction;
import com.android.settings.biometrics.fingerprint2.ui.enrollment.viewmodel.FingerprintEnrollIntroViewModel;
import com.android.settings.biometrics.fingerprint2.ui.enrollment.viewmodel.FingerprintScrollViewModel;
import com.android.systemui.biometrics.shared.model.FingerprintSensor;
import com.android.systemui.biometrics.shared.model.FingerprintSensorType;

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupcompat.template.Mixin;
import com.google.android.setupdesign.GlifLayout;
import com.google.android.setupdesign.template.RequireScrollMixin;
import com.google.android.setupdesign.util.DynamicColorPalette;
import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlowImpl;

import java.util.Iterator;
import java.util.List;

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
final class FingerprintEnrollIntroV2Fragment$onCreateView$1$1 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ View $view;
    int label;
    final /* synthetic */ FingerprintEnrollIntroV2Fragment this$0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\u0018\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\b\u0002\u0010\u0006\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0000\u0012\u0004\u0012\u00020\u00050\u00042\b\u0010\u0001\u001a\u0004\u0018\u00010\u00002\u0006\u0010\u0003\u001a\u00020\u0002H\u008a@¢\u0006\u0004\b\u0006\u0010\u0007"
            },
            d2 = {
                "Lcom/android/settings/biometrics/fingerprint2/lib/model/Unicorn;",
                "enrollType",
                "Lcom/android/systemui/biometrics/shared/model/FingerprintSensor;",
                "sensorType",
                "Lkotlin/Pair;",
                "Lcom/android/systemui/biometrics/shared/model/FingerprintSensorType;",
                "<anonymous>",
                "(Lcom/android/settings/biometrics/fingerprint2/lib/model/Unicorn;Lcom/android/systemui/biometrics/shared/model/FingerprintSensor;)Lkotlin/Pair;"
            },
            k = 3,
            mv = {1, 9, 0})
    /* renamed from: com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.FingerprintEnrollIntroV2Fragment$onCreateView$1$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(3, (Continuation) obj3);
            anonymousClass1.L$0 = (Unicorn) obj;
            anonymousClass1.L$1 = (FingerprintSensor) obj2;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return new Pair((Unicorn) this.L$0, ((FingerprintSensor) this.L$1).sensorType);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FingerprintEnrollIntroV2Fragment$onCreateView$1$1(
            FingerprintEnrollIntroV2Fragment fingerprintEnrollIntroV2Fragment,
            View view,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = fingerprintEnrollIntroV2Fragment;
        this.$view = view;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FingerprintEnrollIntroV2Fragment$onCreateView$1$1(
                this.this$0, this.$view, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FingerprintEnrollIntroV2Fragment$onCreateView$1$1)
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
                    (FingerprintEnrollIntroViewModel) this.this$0.viewModel$delegate.getValue();
            FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 combine =
                    FlowKt.combine(
                            fingerprintEnrollIntroViewModel.fingerprintFlow,
                            FlowKt.filterNotNull(
                                    ((FingerprintEnrollIntroViewModel)
                                                    this.this$0.viewModel$delegate.getValue())
                                            .sensor),
                            new AnonymousClass1(3, null));
            final FingerprintEnrollIntroV2Fragment fingerprintEnrollIntroV2Fragment = this.this$0;
            final View view = this.$view;
            FlowCollector flowCollector = new FlowCollector() { // from class:
                        // com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.FingerprintEnrollIntroV2Fragment$onCreateView$1$1.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            TextModel textModel;
                            Pair pair = (Pair) obj2;
                            Unicorn unicorn = (Unicorn) pair.getFirst();
                            FingerprintSensorType fingerprintSensorType =
                                    (FingerprintSensorType) pair.getSecond();
                            boolean areEqual = Intrinsics.areEqual(unicorn, Unicorn.INSTANCE);
                            final FingerprintEnrollIntroV2Fragment
                                    fingerprintEnrollIntroV2Fragment2 =
                                            FingerprintEnrollIntroV2Fragment.this;
                            if (areEqual) {
                                fingerprintEnrollIntroV2Fragment2.getClass();
                                textModel =
                                        new TextModel(
                                                R.string
                                                        .security_settings_fingerprint_v2_enroll_introduction_footer_message_consent_2,
                                                R.string
                                                        .security_settings_fingerprint_v2_enroll_introduction_footer_message_consent_3,
                                                R.string
                                                        .security_settings_fingerprint_v2_enroll_introduction_footer_message_consent_4,
                                                R.string
                                                        .security_settings_fingerprint_v2_enroll_introduction_footer_message_consent_5,
                                                R.string
                                                        .security_settings_fingerprint_v2_enroll_introduction_footer_message_consent_6,
                                                R.string
                                                        .security_settings_fingerprint_enroll_introduction_footer_title_consent_1,
                                                R.string
                                                        .security_settings_fingerprint_enroll_consent_introduction_title);
                            } else {
                                fingerprintEnrollIntroV2Fragment2.getClass();
                                textModel =
                                        new TextModel(
                                                R.string
                                                        .security_settings_fingerprint_v2_enroll_introduction_footer_message_2,
                                                R.string
                                                        .security_settings_fingerprint_v2_enroll_introduction_footer_message_3,
                                                R.string
                                                        .security_settings_fingerprint_v2_enroll_introduction_footer_message_4,
                                                R.string
                                                        .security_settings_fingerprint_v2_enroll_introduction_footer_message_5,
                                                R.string
                                                        .security_settings_fingerprint_v2_enroll_introduction_footer_message_6,
                                                R.string
                                                        .security_settings_fingerprint_enroll_introduction_footer_title_1,
                                                R.string
                                                        .security_settings_fingerprint_enroll_introduction_title);
                            }
                            fingerprintEnrollIntroV2Fragment2.textModel = textModel;
                            View view2 = view;
                            View requireViewById = view2.requireViewById(R.id.sud_scroll_view);
                            Intrinsics.checkNotNullExpressionValue(
                                    requireViewById, "requireViewById(...)");
                            ((ScrollView) requireViewById).setImportantForAccessibility(1);
                            final int i2 = 1;
                            View.OnClickListener onClickListener =
                                    new View.OnClickListener() { // from class:
                                        // com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.FingerprintEnrollIntroV2Fragment$setupFooterBarAndScrollView$1
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view3) {
                                            switch (i2) {
                                                case 0:
                                                    FingerprintAction[] fingerprintActionArr =
                                                            FingerprintAction.$VALUES;
                                                    throw null;
                                                default:
                                                    FingerprintAction[] fingerprintActionArr2 =
                                                            FingerprintAction.$VALUES;
                                                    throw null;
                                            }
                                        }
                                    };
                            View findViewById = view2.findViewById(R.id.setup_wizard_layout);
                            Intrinsics.checkNotNull(findViewById);
                            GlifLayout glifLayout = (GlifLayout) findViewById;
                            Mixin mixin = glifLayout.getMixin(FooterBarMixin.class);
                            Intrinsics.checkNotNullExpressionValue(mixin, "getMixin(...)");
                            FooterBarMixin footerBarMixin = (FooterBarMixin) mixin;
                            fingerprintEnrollIntroV2Fragment2.footerBarMixin = footerBarMixin;
                            footerBarMixin.setPrimaryButton(
                                    new FooterButton(
                                            fingerprintEnrollIntroV2Fragment2
                                                    .requireContext()
                                                    .getString(
                                                            R.string
                                                                    .security_settings_face_enroll_introduction_more),
                                            onClickListener,
                                            6,
                                            2132083805));
                            FooterBarMixin footerBarMixin2 =
                                    fingerprintEnrollIntroV2Fragment2.footerBarMixin;
                            if (footerBarMixin2 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException(
                                        "footerBarMixin");
                                throw null;
                            }
                            Context requireContext =
                                    fingerprintEnrollIntroV2Fragment2.requireContext();
                            if (fingerprintEnrollIntroV2Fragment2.textModel == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("textModel");
                                throw null;
                            }
                            final int i3 = 0;
                            footerBarMixin2.setSecondaryButton(
                                    new FooterButton(
                                            requireContext.getString(
                                                    R.string
                                                            .security_settings_fingerprint_enroll_introduction_no_thanks),
                                            new View.OnClickListener() { // from class:
                                                // com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.FingerprintEnrollIntroV2Fragment$setupFooterBarAndScrollView$1
                                                @Override // android.view.View.OnClickListener
                                                public final void onClick(View view3) {
                                                    switch (i3) {
                                                        case 0:
                                                            FingerprintAction[]
                                                                    fingerprintActionArr =
                                                                            FingerprintAction
                                                                                    .$VALUES;
                                                            throw null;
                                                        default:
                                                            FingerprintAction[]
                                                                    fingerprintActionArr2 =
                                                                            FingerprintAction
                                                                                    .$VALUES;
                                                            throw null;
                                                    }
                                                }
                                            },
                                            5,
                                            2132083805),
                                    true);
                            FooterBarMixin footerBarMixin3 =
                                    fingerprintEnrollIntroV2Fragment2.footerBarMixin;
                            if (footerBarMixin3 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException(
                                        "footerBarMixin");
                                throw null;
                            }
                            FooterButton footerButton = footerBarMixin3.primaryButton;
                            FooterButton footerButton2 = footerBarMixin3.secondaryButton;
                            footerButton2.setVisibility(4);
                            RequireScrollMixin requireScrollMixin =
                                    (RequireScrollMixin)
                                            glifLayout.getMixin(RequireScrollMixin.class);
                            requireScrollMixin.requireScrollWithButton(
                                    fingerprintEnrollIntroV2Fragment2.requireContext(),
                                    footerButton,
                                    R.string.security_settings_face_enroll_introduction_more,
                                    onClickListener);
                            requireScrollMixin.listener =
                                    new RequireScrollMixin
                                            .OnRequireScrollStateChangedListener() { // from class:
                                        // com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.FingerprintEnrollIntroV2Fragment$setupFooterBarAndScrollView$2
                                        @Override // com.google.android.setupdesign.template.RequireScrollMixin.OnRequireScrollStateChangedListener
                                        public final void onRequireScrollStateChanged(boolean z) {
                                            StateFlowImpl stateFlowImpl;
                                            Object value;
                                            if (z) {
                                                return;
                                            }
                                            FingerprintScrollViewModel fingerprintScrollViewModel =
                                                    (FingerprintScrollViewModel)
                                                            FingerprintEnrollIntroV2Fragment.this
                                                                    .fingerprintScrollViewModel$delegate
                                                                    .getValue();
                                            do {
                                                stateFlowImpl =
                                                        fingerprintScrollViewModel
                                                                ._hasReadConsentScreen;
                                                value = stateFlowImpl.getValue();
                                                ((Boolean) value).getClass();
                                            } while (!stateFlowImpl.compareAndSet(
                                                    value, Boolean.TRUE));
                                        }
                                    };
                            LifecycleOwner viewLifecycleOwner =
                                    fingerprintEnrollIntroV2Fragment2.getViewLifecycleOwner();
                            Intrinsics.checkNotNullExpressionValue(
                                    viewLifecycleOwner, "getViewLifecycleOwner(...)");
                            BuildersKt.launch$default(
                                    LifecycleOwnerKt.getLifecycleScope(viewLifecycleOwner),
                                    null,
                                    null,
                                    new FingerprintEnrollIntroV2Fragment$setupFooterBarAndScrollView$3(
                                            fingerprintEnrollIntroV2Fragment2,
                                            footerButton,
                                            footerButton2,
                                            null),
                                    3);
                            FooterBarMixin footerBarMixin4 =
                                    fingerprintEnrollIntroV2Fragment2.footerBarMixin;
                            if (footerBarMixin4 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException(
                                        "footerBarMixin");
                                throw null;
                            }
                            LinearLayout buttonContainer = footerBarMixin4.getButtonContainer();
                            if (buttonContainer != null) {
                                buttonContainer.setBackgroundColor(0);
                            }
                            footerButton.setEnabled(false);
                            LifecycleOwner viewLifecycleOwner2 =
                                    fingerprintEnrollIntroV2Fragment2.getViewLifecycleOwner();
                            Intrinsics.checkNotNullExpressionValue(
                                    viewLifecycleOwner2, "getViewLifecycleOwner(...)");
                            BuildersKt.launch$default(
                                    LifecycleOwnerKt.getLifecycleScope(viewLifecycleOwner2),
                                    null,
                                    null,
                                    new FingerprintEnrollIntroV2Fragment$setupFooterBarAndScrollView$4(
                                            fingerprintEnrollIntroV2Fragment2, footerButton, null),
                                    3);
                            View view3 = view;
                            Intrinsics.checkNotNull(
                                    view3,
                                    "null cannot be cast to non-null type"
                                        + " com.google.android.setupdesign.GlifLayout");
                            GlifLayout glifLayout2 = (GlifLayout) view3;
                            TextModel textModel2 = fingerprintEnrollIntroV2Fragment2.textModel;
                            if (textModel2 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("textModel");
                                throw null;
                            }
                            glifLayout2.setHeaderText(textModel2.headerText);
                            if (fingerprintEnrollIntroV2Fragment2.textModel == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("textModel");
                                throw null;
                            }
                            glifLayout2.setDescriptionText(
                                    R.string
                                            .security_settings_fingerprint_enroll_introduction_v3_message);
                            PorterDuffColorFilter porterDuffColorFilter =
                                    new PorterDuffColorFilter(
                                            DynamicColorPalette.getColor(
                                                    fingerprintEnrollIntroV2Fragment2.getContext()),
                                            PorterDuff.Mode.SRC_IN);
                            List listOf =
                                    CollectionsKt__CollectionsKt.listOf(
                                            (Object[])
                                                    new Integer[] {
                                                        new Integer(R.id.icon_fingerprint),
                                                        new Integer(R.id.icon_device_locked),
                                                        new Integer(R.id.icon_trash_can),
                                                        new Integer(R.id.icon_info),
                                                        new Integer(R.id.icon_shield),
                                                        new Integer(R.id.icon_link)
                                                    });
                            View view4 = view;
                            Iterator it = listOf.iterator();
                            while (it.hasNext()) {
                                ((ImageView) view4.requireViewById(((Number) it.next()).intValue()))
                                        .getDrawable()
                                        .setColorFilter(porterDuffColorFilter);
                            }
                            Integer num = new Integer(R.id.footer_message_2);
                            TextModel textModel3 = fingerprintEnrollIntroV2Fragment2.textModel;
                            if (textModel3 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("textModel");
                                throw null;
                            }
                            Pair pair2 = new Pair(num, new Integer(textModel3.footerMessageTwo));
                            Integer num2 = new Integer(R.id.footer_message_3);
                            TextModel textModel4 = fingerprintEnrollIntroV2Fragment2.textModel;
                            if (textModel4 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("textModel");
                                throw null;
                            }
                            Pair pair3 = new Pair(num2, new Integer(textModel4.footerMessageThree));
                            Integer num3 = new Integer(R.id.footer_message_4);
                            TextModel textModel5 = fingerprintEnrollIntroV2Fragment2.textModel;
                            if (textModel5 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("textModel");
                                throw null;
                            }
                            Pair pair4 = new Pair(num3, new Integer(textModel5.footerMessageFour));
                            Integer num4 = new Integer(R.id.footer_message_5);
                            TextModel textModel6 = fingerprintEnrollIntroV2Fragment2.textModel;
                            if (textModel6 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("textModel");
                                throw null;
                            }
                            Pair pair5 = new Pair(num4, new Integer(textModel6.footerMessageFive));
                            Integer num5 = new Integer(R.id.footer_message_6);
                            TextModel textModel7 = fingerprintEnrollIntroV2Fragment2.textModel;
                            if (textModel7 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("textModel");
                                throw null;
                            }
                            List<Pair> listOf2 =
                                    CollectionsKt__CollectionsKt.listOf(
                                            (Object[])
                                                    new Pair[] {
                                                        pair2,
                                                        pair3,
                                                        pair4,
                                                        pair5,
                                                        new Pair(
                                                                num5,
                                                                new Integer(
                                                                        textModel7
                                                                                .footerMessageSix))
                                                    });
                            View view5 = view;
                            for (Pair pair6 : listOf2) {
                                ((TextView)
                                                view5.requireViewById(
                                                        ((Number) pair6.getFirst()).intValue()))
                                        .setText(((Number) pair6.getSecond()).intValue());
                            }
                            View requireViewById2 = view.requireViewById(R.id.footer_learn_more);
                            Intrinsics.checkNotNullExpressionValue(
                                    requireViewById2, "requireViewById(...)");
                            TextView textView = (TextView) requireViewById2;
                            textView.setMovementMethod(LinkMovementMethod.getInstance());
                            textView.setText(
                                    Html.fromHtml(
                                            fingerprintEnrollIntroV2Fragment2.getString(
                                                    R.string
                                                            .security_settings_fingerprint_v2_enroll_introduction_message_learn_more),
                                            0));
                            View requireViewById3 = view.requireViewById(R.id.icon_shield);
                            Intrinsics.checkNotNullExpressionValue(
                                    requireViewById3, "requireViewById(...)");
                            ImageView imageView = (ImageView) requireViewById3;
                            View requireViewById4 = view.requireViewById(R.id.footer_message_6);
                            Intrinsics.checkNotNullExpressionValue(
                                    requireViewById4, "requireViewById(...)");
                            TextView textView2 = (TextView) requireViewById4;
                            int ordinal = fingerprintSensorType.ordinal();
                            if (ordinal == 2 || ordinal == 3) {
                                textView2.setVisibility(0);
                                imageView.setVisibility(0);
                            } else {
                                textView2.setVisibility(8);
                                imageView.setVisibility(8);
                            }
                            TextView textView3 =
                                    (TextView) view.requireViewById(R.id.footer_title_1);
                            TextModel textModel8 = fingerprintEnrollIntroV2Fragment2.textModel;
                            if (textModel8 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("textModel");
                                throw null;
                            }
                            textView3.setText(textModel8.footerTitleOne);
                            TextView textView4 =
                                    (TextView) view.requireViewById(R.id.footer_title_2);
                            TextModel textModel9 = fingerprintEnrollIntroV2Fragment2.textModel;
                            if (textModel9 != null) {
                                textView4.setText(textModel9.footerTitleOne);
                                return Unit.INSTANCE;
                            }
                            Intrinsics.throwUninitializedPropertyAccessException("textModel");
                            throw null;
                        }
                    };
            this.label = 1;
            if (combine.collect(flowCollector, this) == coroutineSingletons) {
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
