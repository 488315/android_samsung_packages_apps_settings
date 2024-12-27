package com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.udfps.ui.fragment;

import android.content.Intent;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.RepeatOnLifecycleKt;

import com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.common.util.FingerprintOptionUtilKt;

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
final class UdfpsEnrollFragment$onViewCreated$3 extends SuspendLambda implements Function2 {
    final /* synthetic */ UdfpsEnrollFragment $fragment;
    final /* synthetic */ GlifLayout $glifLayout;
    int label;
    final /* synthetic */ UdfpsEnrollFragment this$0;

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
    /* renamed from: com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.udfps.ui.fragment.UdfpsEnrollFragment$onViewCreated$3$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ UdfpsEnrollFragment $fragment;
        final /* synthetic */ GlifLayout $glifLayout;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ UdfpsEnrollFragment this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(
                UdfpsEnrollFragment udfpsEnrollFragment,
                GlifLayout glifLayout,
                UdfpsEnrollFragment udfpsEnrollFragment2,
                Continuation continuation) {
            super(2, continuation);
            this.this$0 = udfpsEnrollFragment;
            this.$glifLayout = glifLayout;
            this.$fragment = udfpsEnrollFragment2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 =
                    new AnonymousClass1(
                            this.this$0, this.$glifLayout, this.$fragment, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2))
                    .invokeSuspend(Unit.INSTANCE);
            throw null;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.this$0.getViewModel();
            Intent intent = this.this$0.requireActivity().getIntent();
            Intrinsics.checkNotNullExpressionValue(intent, "getIntent(...)");
            FingerprintOptionUtilKt.toFingerprintEnrollOptions(intent);
            throw null;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UdfpsEnrollFragment$onViewCreated$3(
            UdfpsEnrollFragment udfpsEnrollFragment,
            GlifLayout glifLayout,
            UdfpsEnrollFragment udfpsEnrollFragment2,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = udfpsEnrollFragment;
        this.$glifLayout = glifLayout;
        this.$fragment = udfpsEnrollFragment2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UdfpsEnrollFragment$onViewCreated$3(
                this.this$0, this.$glifLayout, this.$fragment, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UdfpsEnrollFragment$onViewCreated$3)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            UdfpsEnrollFragment udfpsEnrollFragment = this.this$0;
            Lifecycle.State state = Lifecycle.State.RESUMED;
            AnonymousClass1 anonymousClass1 =
                    new AnonymousClass1(
                            udfpsEnrollFragment, this.$glifLayout, this.$fragment, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(
                            udfpsEnrollFragment, state, anonymousClass1, this)
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
