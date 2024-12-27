package com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel;

import android.util.Log;

import com.android.settings.biometrics.fingerprint2.lib.model.FingerprintData;

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
final class FingerprintSettingsViewModel$onDeleteClicked$1 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ FingerprintData $fingerprintViewModel;
    int label;
    final /* synthetic */ FingerprintSettingsViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FingerprintSettingsViewModel$onDeleteClicked$1(
            FingerprintSettingsViewModel fingerprintSettingsViewModel,
            FingerprintData fingerprintData,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = fingerprintSettingsViewModel;
        this.$fingerprintViewModel = fingerprintData;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FingerprintSettingsViewModel$onDeleteClicked$1(
                this.this$0, this.$fingerprintViewModel, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        FingerprintSettingsViewModel$onDeleteClicked$1
                fingerprintSettingsViewModel$onDeleteClicked$1 =
                        (FingerprintSettingsViewModel$onDeleteClicked$1)
                                create((CoroutineScope) obj, (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        fingerprintSettingsViewModel$onDeleteClicked$1.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (this.this$0._isShowingDialog.getValue() == null
                || !Intrinsics.areEqual(
                        this.this$0.navigationViewModel.nextStep.$$delegate_0.getValue(),
                        ShowSettings.INSTANCE)) {
            this.this$0._isShowingDialog.updateState(
                    null, new PreferenceViewModel.DeleteDialog(this.$fingerprintViewModel));
        } else {
            Log.d(
                    "FingerprintSettingsViewModel",
                    "Ignoring onDeleteClicked due to dialog showing "
                            + this.this$0._isShowingDialog.getValue());
        }
        return Unit.INSTANCE;
    }
}
