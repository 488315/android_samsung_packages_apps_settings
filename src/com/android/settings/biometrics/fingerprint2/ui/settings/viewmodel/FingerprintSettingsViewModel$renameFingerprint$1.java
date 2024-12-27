package com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel;

import com.android.settings.biometrics.fingerprint2.domain.interactor.FingerprintManagerInteractorImpl;
import com.android.settings.biometrics.fingerprint2.lib.model.FingerprintData;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

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
final class FingerprintSettingsViewModel$renameFingerprint$1 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ FingerprintData $fp;
    final /* synthetic */ String $newName;
    int label;
    final /* synthetic */ FingerprintSettingsViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FingerprintSettingsViewModel$renameFingerprint$1(
            FingerprintSettingsViewModel fingerprintSettingsViewModel,
            FingerprintData fingerprintData,
            String str,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = fingerprintSettingsViewModel;
        this.$fp = fingerprintData;
        this.$newName = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FingerprintSettingsViewModel$renameFingerprint$1(
                this.this$0, this.$fp, this.$newName, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FingerprintSettingsViewModel$renameFingerprint$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FingerprintManagerInteractorImpl fingerprintManagerInteractorImpl =
                    this.this$0.fingerprintManagerInteractor;
            FingerprintData fingerprintData = this.$fp;
            String str = this.$newName;
            this.label = 1;
            if (fingerprintManagerInteractorImpl.renameFingerprint(fingerprintData, str, this)
                    == coroutineSingletons) {
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
        FingerprintSettingsViewModel fingerprintSettingsViewModel = this.this$0;
        this.label = 2;
        if (FingerprintSettingsViewModel.access$updateEnrolledFingerprints(
                        fingerprintSettingsViewModel, this)
                == coroutineSingletons) {
            return coroutineSingletons;
        }
        return Unit.INSTANCE;
    }
}
