package com.android.settings.biometrics.fingerprint2.domain.interactor;

import android.hardware.fingerprint.FingerprintManager;

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
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/CoroutineScope;"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class FingerprintManagerInteractorImpl$renameFingerprint$2 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ FingerprintData $fp;
    final /* synthetic */ String $newName;
    int label;
    final /* synthetic */ FingerprintManagerInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FingerprintManagerInteractorImpl$renameFingerprint$2(
            FingerprintManagerInteractorImpl fingerprintManagerInteractorImpl,
            FingerprintData fingerprintData,
            String str,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = fingerprintManagerInteractorImpl;
        this.$fp = fingerprintData;
        this.$newName = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FingerprintManagerInteractorImpl$renameFingerprint$2(
                this.this$0, this.$fp, this.$newName, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FingerprintManagerInteractorImpl$renameFingerprint$2)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        FingerprintManagerInteractorImpl fingerprintManagerInteractorImpl = this.this$0;
        FingerprintManager fingerprintManager = fingerprintManagerInteractorImpl.fingerprintManager;
        if (fingerprintManager == null) {
            return null;
        }
        fingerprintManager.rename(
                this.$fp.fingerId,
                fingerprintManagerInteractorImpl.applicationContext.getUserId(),
                this.$newName);
        return Unit.INSTANCE;
    }
}
