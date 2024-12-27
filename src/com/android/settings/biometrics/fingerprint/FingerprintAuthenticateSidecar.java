package com.android.settings.biometrics.fingerprint;

import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.core.InstrumentedFragment;

import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FingerprintAuthenticateSidecar extends InstrumentedFragment {
    public final AnonymousClass1 mAuthenticationCallback =
            new FingerprintManager
                    .AuthenticationCallback() { // from class:
                                                // com.android.settings.biometrics.fingerprint.FingerprintAuthenticateSidecar.1
                @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
                public final void onAuthenticationError(int i, CharSequence charSequence) {
                    FingerprintAuthenticateSidecar fingerprintAuthenticateSidecar =
                            FingerprintAuthenticateSidecar.this;
                    Listener listener = fingerprintAuthenticateSidecar.mListener;
                    if (listener != null) {
                        FingerprintSettings.FingerprintSettingsFragment.this
                                .mHandler
                                .obtainMessage(1003, i, 0, charSequence)
                                .sendToTarget();
                        return;
                    }
                    AuthenticationError authenticationError = new AuthenticationError();
                    authenticationError.error = i;
                    authenticationError.errorString = charSequence;
                    fingerprintAuthenticateSidecar.mAuthenticationError = authenticationError;
                    fingerprintAuthenticateSidecar.mAuthenticationResult = null;
                }

                @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
                public final void onAuthenticationFailed() {
                    Listener listener = FingerprintAuthenticateSidecar.this.mListener;
                    if (listener != null) {
                        FingerprintSettings.FingerprintSettingsFragment.this
                                .mHandler
                                .obtainMessage(1002)
                                .sendToTarget();
                    }
                }

                @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
                public final void onAuthenticationHelp(int i, CharSequence charSequence) {
                    Listener listener = FingerprintAuthenticateSidecar.this.mListener;
                    if (listener != null) {
                        FingerprintSettings.FingerprintSettingsFragment.this
                                .mHandler
                                .obtainMessage(
                                        VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI,
                                        i,
                                        0,
                                        charSequence)
                                .sendToTarget();
                    }
                }

                @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
                public final void onAuthenticationSucceeded(
                        FingerprintManager.AuthenticationResult authenticationResult) {
                    FingerprintAuthenticateSidecar fingerprintAuthenticateSidecar =
                            FingerprintAuthenticateSidecar.this;
                    fingerprintAuthenticateSidecar.mCancellationSignal = null;
                    Listener listener = fingerprintAuthenticateSidecar.mListener;
                    if (listener != null) {
                        FingerprintSettings.FingerprintSettingsFragment.this
                                .mHandler
                                .obtainMessage(
                                        1001,
                                        authenticationResult.getFingerprint().getBiometricId(),
                                        0)
                                .sendToTarget();
                    } else {
                        fingerprintAuthenticateSidecar.mAuthenticationResult = authenticationResult;
                        fingerprintAuthenticateSidecar.mAuthenticationError = null;
                    }
                }
            };
    public AuthenticationError mAuthenticationError;
    public FingerprintManager.AuthenticationResult mAuthenticationResult;
    public CancellationSignal mCancellationSignal;
    public FingerprintManager mFingerprintManager;
    public Listener mListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AuthenticationError {
        public int error;
        public CharSequence errorString;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Listener {}

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1221;
    }

    @VisibleForTesting
    public boolean isCancelled() {
        CancellationSignal cancellationSignal = this.mCancellationSignal;
        return cancellationSignal == null || cancellationSignal.isCanceled();
    }

    public final void setListener(Listener listener) {
        int i;
        if (this.mListener == null && listener != null) {
            FingerprintManager.AuthenticationResult authenticationResult =
                    this.mAuthenticationResult;
            if (authenticationResult != null) {
                FingerprintSettings.FingerprintSettingsFragment.this
                        .mHandler
                        .obtainMessage(
                                1001, authenticationResult.getFingerprint().getBiometricId(), 0)
                        .sendToTarget();
                this.mAuthenticationResult = null;
            }
            AuthenticationError authenticationError = this.mAuthenticationError;
            if (authenticationError != null && (i = authenticationError.error) != 5) {
                FingerprintSettings.FingerprintSettingsFragment.this
                        .mHandler
                        .obtainMessage(1003, i, 0, authenticationError.errorString)
                        .sendToTarget();
                this.mAuthenticationError = null;
            }
        }
        this.mListener = listener;
    }
}
