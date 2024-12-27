package com.samsung.android.settings.knox;

import android.app.Activity;
import android.content.Intent;
import android.hardware.biometrics.BiometricPrompt;

import androidx.fragment.app.FragmentManagerImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class KnoxBiometricLockHelper {
    public abstract Intent addIntentIfNeeded(Intent intent);

    public abstract boolean interceptIsBiometricAllowedIfNeeded();

    public abstract boolean interceptOnAuthenticationErrorIfNeeded(
            int i, CharSequence charSequence, Activity activity);

    public abstract void interceptOnAuthenticationFailedIfNeeded(boolean z);

    public abstract boolean interceptOnAuthenticationSucceededIfNeeded(
            BiometricPrompt.AuthenticationResult authenticationResult);

    public abstract boolean interceptShowConfirmCredentialsIfNeeded();

    public abstract boolean isBiometricAllowed(int i, int i2);

    public abstract void onStart(Activity activity, boolean z);

    public abstract void setFragmentManager(FragmentManagerImpl fragmentManagerImpl);

    public abstract void setResultIfNeeded();
}
