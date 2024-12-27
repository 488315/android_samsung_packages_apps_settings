package com.android.settings.biometrics.fingerprint;

import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.FingerprintEnrollOptions;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.Utils;
import com.android.settings.safetycenter.BiometricsSafetySource;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FingerprintUpdater {
    public final Context mContext;
    public final FingerprintManager mFingerprintManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class NotifyingEnrollmentCallback extends FingerprintManager.EnrollmentCallback {
        public final FingerprintManager.EnrollmentCallback mCallback;
        public final Context mContext;

        public NotifyingEnrollmentCallback(
                Context context, FingerprintManager.EnrollmentCallback enrollmentCallback) {
            this.mContext = context;
            this.mCallback = enrollmentCallback;
        }

        public final void onAcquired(boolean z) {
            this.mCallback.onAcquired(z);
        }

        public final void onEnrollmentError(int i, CharSequence charSequence) {
            this.mCallback.onEnrollmentError(i, charSequence);
        }

        public final void onEnrollmentHelp(int i, CharSequence charSequence) {
            this.mCallback.onEnrollmentHelp(i, charSequence);
        }

        public final void onEnrollmentProgress(int i) {
            this.mCallback.onEnrollmentProgress(i);
            if (i == 0) {
                BiometricsSafetySource.onBiometricsChanged(this.mContext);
            }
        }

        public final void onUdfpsOverlayShown() {
            this.mCallback.onUdfpsOverlayShown();
        }

        public final void onUdfpsPointerDown(int i) {
            this.mCallback.onUdfpsPointerDown(i);
        }

        public final void onUdfpsPointerUp(int i) {
            this.mCallback.onUdfpsPointerUp(i);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class NotifyingRemovalCallback extends FingerprintManager.RemovalCallback {
        public final FingerprintManager.RemovalCallback mCallback;
        public final Context mContext;

        public NotifyingRemovalCallback(
                Context context, FingerprintRemoveSidecar.AnonymousClass1 anonymousClass1) {
            this.mContext = context;
            this.mCallback = anonymousClass1;
        }

        public final void onRemovalError(
                Fingerprint fingerprint, int i, CharSequence charSequence) {
            this.mCallback.onRemovalError(fingerprint, i, charSequence);
        }

        public final void onRemovalSucceeded(Fingerprint fingerprint, int i) {
            this.mCallback.onRemovalSucceeded(fingerprint, i);
            BiometricsSafetySource.onBiometricsChanged(this.mContext);
        }
    }

    public FingerprintUpdater(Context context) {
        this.mContext = context;
        this.mFingerprintManager = Utils.getFingerprintManagerOrNull(context);
    }

    public final void enroll(
            byte[] bArr,
            CancellationSignal cancellationSignal,
            int i,
            FingerprintManager.EnrollmentCallback enrollmentCallback,
            int i2,
            Intent intent) {
        FingerprintManager fingerprintManager = this.mFingerprintManager;
        NotifyingEnrollmentCallback notifyingEnrollmentCallback =
                new NotifyingEnrollmentCallback(this.mContext, enrollmentCallback);
        int intExtra = intent.getIntExtra("enroll_reason", -1);
        FingerprintEnrollOptions.Builder builder = new FingerprintEnrollOptions.Builder();
        builder.setEnrollReason(0);
        if (intExtra != -1) {
            builder.setEnrollReason(intExtra);
        }
        fingerprintManager.enroll(
                bArr, cancellationSignal, i, notifyingEnrollmentCallback, i2, builder.build());
    }

    public FingerprintUpdater(
            FragmentActivity fragmentActivity, FingerprintManager fingerprintManager) {
        this.mContext = fragmentActivity;
        this.mFingerprintManager = fingerprintManager;
    }
}
