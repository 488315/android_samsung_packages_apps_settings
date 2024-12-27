package com.android.settings.biometrics.fingerprint2.ui.settings.binder;

import com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;

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
final class FingerprintSettingsViewBinder$bind$5 extends SuspendLambda implements Function2 {
    final /* synthetic */ FingerprintSettingsViewBinder.FingerprintView $view;
    final /* synthetic */ FingerprintSettingsViewModel $viewModel;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FingerprintSettingsViewBinder$bind$5(
            FingerprintSettingsViewBinder.FingerprintView fingerprintView,
            FingerprintSettingsViewModel fingerprintSettingsViewModel,
            Continuation continuation) {
        super(2, continuation);
        this.$viewModel = fingerprintSettingsViewModel;
        this.$view = fingerprintView;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FingerprintSettingsViewBinder$bind$5(this.$view, this.$viewModel, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FingerprintSettingsViewBinder$bind$5)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 filterNotNull =
                    FlowKt.filterNotNull(this.$viewModel.authFlow);
            FingerprintSettingsViewBinder$bind$1.AnonymousClass1 anonymousClass1 =
                    new FingerprintSettingsViewBinder$bind$1.AnonymousClass1(this.$view, 3);
            this.label = 1;
            if (filterNotNull.collect(anonymousClass1, this) == coroutineSingletons) {
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
