package com.android.settings.biometrics.fingerprint2.domain.interactor;

import android.R;
import android.content.Context;
import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.os.Handler;
import android.util.Log;

import com.android.settings.biometrics.GatekeeperPasswordProvider;
import com.android.settings.biometrics.fingerprint2.data.repository.FingerprintSensorRepositoryImpl;
import com.android.settings.biometrics.fingerprint2.lib.model.FingerprintAuthAttemptModel;
import com.android.settings.biometrics.fingerprint2.lib.model.FingerprintData;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FingerprintManagerInteractorImpl {
    public final Context applicationContext;
    public final CoroutineDispatcher backgroundDispatcher;
    public final SafeFlow canEnrollFingerprints;
    public final SafeFlow enrolledFingerprints;
    public final FingerprintManager fingerprintManager;
    public final GatekeeperPasswordProvider gatekeeperPasswordProvider;
    public final SafeFlow maxEnrollableFingerprints;
    public final int maxFingerprints;
    public final Flow sensorPropertiesInternal;

    public FingerprintManagerInteractorImpl(
            Context applicationContext,
            CoroutineDispatcher backgroundDispatcher,
            FingerprintManager fingerprintManager,
            FingerprintSensorRepositoryImpl fingerprintSensorRepository,
            GatekeeperPasswordProvider gatekeeperPasswordProvider,
            FingerprintEnrollInteractor fingerprintEnrollStateRepository) {
        Intrinsics.checkNotNullParameter(applicationContext, "applicationContext");
        Intrinsics.checkNotNullParameter(backgroundDispatcher, "backgroundDispatcher");
        Intrinsics.checkNotNullParameter(
                fingerprintSensorRepository, "fingerprintSensorRepository");
        Intrinsics.checkNotNullParameter(gatekeeperPasswordProvider, "gatekeeperPasswordProvider");
        Intrinsics.checkNotNullParameter(
                fingerprintEnrollStateRepository, "fingerprintEnrollStateRepository");
        this.backgroundDispatcher = backgroundDispatcher;
        this.fingerprintManager = fingerprintManager;
        this.gatekeeperPasswordProvider = gatekeeperPasswordProvider;
        this.maxFingerprints =
                applicationContext
                        .getResources()
                        .getInteger(R.integer.config_lowMemoryKillerMinFreeKbytesAdjust);
        this.applicationContext = applicationContext.getApplicationContext();
        this.enrolledFingerprints =
                new SafeFlow(
                        new FingerprintManagerInteractorImpl$enrolledFingerprints$1(
                                this, applicationContext, null));
        this.canEnrollFingerprints =
                new SafeFlow(
                        new FingerprintManagerInteractorImpl$canEnrollFingerprints$1(
                                this, applicationContext, null));
        this.sensorPropertiesInternal = fingerprintSensorRepository.fingerprintSensor;
        this.maxEnrollableFingerprints =
                new SafeFlow(
                        new FingerprintManagerInteractorImpl$maxEnrollableFingerprints$1(
                                this, null));
    }

    public final Object authenticate(Continuation continuation) {
        final CancellableContinuationImpl cancellableContinuationImpl =
                new CancellableContinuationImpl(
                        1, IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation));
        cancellableContinuationImpl.initCancellability();
        FingerprintManager.AuthenticationCallback authenticationCallback =
                new FingerprintManager
                        .AuthenticationCallback() { // from class:
                                                    // com.android.settings.biometrics.fingerprint2.domain.interactor.FingerprintManagerInteractorImpl$authenticate$2$authenticationCallback$1
                    @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
                    public final void onAuthenticationError(int i, CharSequence errString) {
                        Intrinsics.checkNotNullParameter(errString, "errString");
                        super.onAuthenticationError(i, errString);
                        if (cancellableContinuationImpl.isCompleted()) {
                            Log.d(
                                    "FingerprintManagerInteractor",
                                    "framework sent down onAuthError after finish");
                        } else {
                            cancellableContinuationImpl.resumeWith(
                                    new FingerprintAuthAttemptModel.Error(i, errString.toString()));
                        }
                    }

                    @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
                    public final void onAuthenticationSucceeded(
                            FingerprintManager.AuthenticationResult result) {
                        Intrinsics.checkNotNullParameter(result, "result");
                        super.onAuthenticationSucceeded(result);
                        if (cancellableContinuationImpl.isCompleted()) {
                            Log.d(
                                    "FingerprintManagerInteractor",
                                    "framework sent down onAuthError after finish");
                            return;
                        }
                        CancellableContinuation cancellableContinuation =
                                cancellableContinuationImpl;
                        Fingerprint fingerprint = result.getFingerprint();
                        cancellableContinuation.resumeWith(
                                new FingerprintAuthAttemptModel.Success(
                                        fingerprint != null ? fingerprint.getBiometricId() : -1));
                    }
                };
        final CancellationSignal cancellationSignal = new CancellationSignal();
        cancellableContinuationImpl.invokeOnCancellation(
                new Function1() { // from class:
                                  // com.android.settings.biometrics.fingerprint2.domain.interactor.FingerprintManagerInteractorImpl$authenticate$2$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        cancellationSignal.cancel();
                        return Unit.INSTANCE;
                    }
                });
        FingerprintManager fingerprintManager = this.fingerprintManager;
        if (fingerprintManager != null) {
            fingerprintManager.authenticate(
                    (FingerprintManager.CryptoObject) null,
                    cancellationSignal,
                    authenticationCallback,
                    (Handler) null,
                    this.applicationContext.getUserId());
        }
        Object result = cancellableContinuationImpl.getResult();
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        return result;
    }

    public final Object renameFingerprint(
            FingerprintData fingerprintData, String str, Continuation continuation) {
        return BuildersKt.withContext(
                this.backgroundDispatcher,
                new FingerprintManagerInteractorImpl$renameFingerprint$2(
                        this, fingerprintData, str, null),
                continuation);
    }
}
