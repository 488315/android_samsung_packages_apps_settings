package com.samsung.android.settings.knox;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.CancellationSignal;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class TwoStepAuthenticationCallback extends BiometricPrompt.AuthenticationCallback {
    public static TwoStepAuthenticationCallback singleInstance;
    public Activity activity;
    public KnoxConfirmDeviceCredentialBaseFragmentHelper.Callback callback;
    public CancellationSignal mCancellationSignal;
    public Executor mClientExecutor;
    public DevicePolicyManager mDevicePolicyManager;
    public int mUserId;

    @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
    public final void onAuthenticationError(int i, CharSequence charSequence) {
        Log.d(
                "KKG::TwoStepAuthenticationCallback",
                "onAuthenticationError error = " + i + ", message = " + ((Object) charSequence));
        this.mClientExecutor.execute(
                new TwoStepAuthenticationCallback$$ExternalSyntheticLambda0(this, 2));
    }

    @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
    public final void onAuthenticationFailed() {
        this.mClientExecutor.execute(
                new TwoStepAuthenticationCallback$$ExternalSyntheticLambda0(this, 0));
    }

    @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
    public final void onAuthenticationSucceeded(
            BiometricPrompt.AuthenticationResult authenticationResult) {
        this.mClientExecutor.execute(
                new TwoStepAuthenticationCallback$$ExternalSyntheticLambda0(this, 1));
    }

    public final void onSystemEvent(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "SystemEvent: ", "KKG::TwoStepAuthenticationCallback");
    }
}
