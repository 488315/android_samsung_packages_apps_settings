package com.samsung.android.settings.knox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.biometrics.BiometricPrompt;
import android.os.CancellationSignal;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentManagerImpl;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.password.BiometricFragment;
import com.android.settings.password.ConfirmDeviceCredentialActivity;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.Rune;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class WorkProfileBiometricLockHelper extends KnoxBiometricLockHelper {
    public KnoxBiometricsUiHelper.Callback mCallback;
    public Context mContext;
    public int mEffectiveUserId;
    public FragmentManager mFragmentManager;
    public boolean mIsInternal;
    public boolean mIsKeyguardLocked;
    public LockPatternUtils mLockPatternUtils;
    public int mUserId;
    public UserManager mUserManager;

    @Override // com.samsung.android.settings.knox.KnoxBiometricLockHelper
    public final Intent addIntentIfNeeded(Intent intent) {
        Intent intent2 = new Intent(intent);
        intent2.putExtra("knox.container.proxy.EXTRA_SHOW_WHEN_LOCKED", this.mIsKeyguardLocked);
        return intent2;
    }

    @Override // com.samsung.android.settings.knox.KnoxBiometricLockHelper
    public final boolean interceptIsBiometricAllowedIfNeeded() {
        return this.mUserId != UserHandle.myUserId() && this.mIsInternal;
    }

    @Override // com.samsung.android.settings.knox.KnoxBiometricLockHelper
    public final boolean interceptOnAuthenticationErrorIfNeeded(
            int i, CharSequence charSequence, Activity activity) {
        Log.d(
                "KKG::WorkProfileBiometricLockHelper",
                "interceptOnAuthenticationErrorIfNeeded errorCode = "
                        + i
                        + ", errString = "
                        + ((Object) charSequence));
        int myUserId = UserHandle.myUserId();
        int i2 = this.mUserId;
        if (i2 == myUserId || !this.mIsInternal) {
            return false;
        }
        boolean isMultifactorEnabledForWork =
                KnoxUtils.isMultifactorEnabledForWork(this.mContext, i2);
        KnoxBiometricsUiHelper.Callback callback = this.mCallback;
        if (isMultifactorEnabledForWork) {
            ((ConfirmDeviceCredentialActivity) callback).finish();
            return true;
        }
        if (!Rune.isSamsungDexMode(this.mContext)
                || !Rune.isSamsungDexOnPCMode(this.mContext)
                || i != 13) {
            return false;
        }
        ((ConfirmDeviceCredentialActivity) callback).finish();
        return true;
    }

    @Override // com.samsung.android.settings.knox.KnoxBiometricLockHelper
    public final void interceptOnAuthenticationFailedIfNeeded(boolean z) {
        Log.d("KKG::WorkProfileBiometricLockHelper", "onAuthenticationFailed");
        if (z) {
            ((ConfirmDeviceCredentialActivity) this.mCallback).finish();
        }
    }

    @Override // com.samsung.android.settings.knox.KnoxBiometricLockHelper
    public final boolean interceptOnAuthenticationSucceededIfNeeded(
            BiometricPrompt.AuthenticationResult authenticationResult) {
        Log.d("KKG::WorkProfileBiometricLockHelper", "onAuthenticationSucceeded");
        int myUserId = UserHandle.myUserId();
        int i = this.mUserId;
        if (i != myUserId
                && this.mIsInternal
                && authenticationResult != null
                && authenticationResult.getAuthenticationType() == 2) {
            if (KnoxUtils.isFaceLockSetForUser(this.mContext, i)) {
                this.mLockPatternUtils.reportSuccessfulBiometricUnlock(false, i);
            }
            if (KnoxUtils.isMultifactorEnabledForWork(this.mContext, i)
                    && SemPersonaManager.appliedPasswordPolicy(i)) {
                ((ConfirmDeviceCredentialActivity) this.mCallback).showConfirmCredentials();
                return true;
            }
        }
        return false;
    }

    @Override // com.samsung.android.settings.knox.KnoxBiometricLockHelper
    public final boolean interceptShowConfirmCredentialsIfNeeded() {
        int myUserId = UserHandle.myUserId();
        int i = this.mUserId;
        if (i == myUserId || !this.mIsInternal) {
            return false;
        }
        int twoFactorValue = KnoxUtils.getTwoFactorValue(this.mContext, i);
        KnoxBiometricsUiHelper.Callback callback = this.mCallback;
        if (twoFactorValue == 1) {
            if (this.mLockPatternUtils.getBiometricAttemptDeadline(this.mUserId) > 0) {
                Intent intent =
                        new Intent(
                                this.mContext, (Class<?>) BiometricLockTimerDialogActivity.class);
                intent.putExtra("mUserId", i);
                this.mContext.startActivity(intent);
                ((ConfirmDeviceCredentialActivity) callback).finish();
                return true;
            }
            if (KnoxUtils.isMultifactorAuthEnforced(this.mContext, i)
                    && !KnoxUtils.isFingerprintEnabled(this.mContext, this.mEffectiveUserId)
                    && !KnoxUtils.isPwdChangeEnforced(this.mContext, i)
                    && !KnoxUtils.needSetupCredential(this.mContext, i)) {
                Intent intent2 =
                        new Intent(
                                this.mContext,
                                (Class<?>) WorkProfileConfirmCredentialUnavailableActivity.class);
                intent2.putExtra("mUserId", i);
                intent2.addFlags(1350582272);
                this.mContext.startActivity(intent2);
                ((ConfirmDeviceCredentialActivity) callback).finish();
                return true;
            }
        } else if (KnoxUtils.isMultifactorAuthEnforced(this.mContext, i)
                && !KnoxUtils.isPwdChangeEnforced(this.mContext, i)
                && !KnoxUtils.needSetupCredential(this.mContext, i)) {
            Intent intent3 =
                    new Intent(
                            this.mContext,
                            (Class<?>) WorkProfileConfirmCredentialUnavailableActivity.class);
            intent3.putExtra("mUserId", i);
            intent3.addFlags(1350582272);
            this.mContext.startActivity(intent3);
            ((ConfirmDeviceCredentialActivity) callback).finish();
            return true;
        }
        return Rune.isSamsungDexMode(this.mContext) && Rune.isSamsungDexOnPCMode(this.mContext);
    }

    @Override // com.samsung.android.settings.knox.KnoxBiometricLockHelper
    public final boolean isBiometricAllowed(int i, int i2) {
        if (KnoxUtils.getTwoFactorValue(this.mContext, i2) == 1) {
            if (KnoxUtils.needSetupCredential(this.mContext, i2)) {
                return false;
            }
            if (KnoxUtils.isFingerprintEnabled(this.mContext, i)
                    || KnoxUtils.isFaceEnabled(this.mContext, i)) {
                return ((BiometricFragment) this.mFragmentManager.findFragmentByTag("fragment"))
                                != null
                        || this.mLockPatternUtils.getBiometricAttemptDeadline(this.mUserId) <= 0;
            }
            return false;
        }
        if (!this.mLockPatternUtils.isBiometricAllowedForUser(i)
                || !this.mUserManager.isUserUnlocked(this.mUserId)
                || this.mLockPatternUtils.hasPendingEscrowToken(i2)
                || KnoxUtils.isPwdChangeEnforced(this.mContext, i2)
                || KnoxUtils.needSetupCredential(this.mContext, i2)
                || Settings.System.getIntForUser(
                                this.mContext.getContentResolver(), "enable_one_lock_ongoing", 0, 0)
                        > 0) {
            return false;
        }
        if (KnoxUtils.isFingerprintEnabled(this.mContext, i)
                || KnoxUtils.isFaceEnabled(this.mContext, i)) {
            return ((BiometricFragment) this.mFragmentManager.findFragmentByTag("fragment")) != null
                    || this.mLockPatternUtils.getBiometricAttemptDeadline(this.mUserId) <= 0;
        }
        return false;
    }

    @Override // com.samsung.android.settings.knox.KnoxBiometricLockHelper
    public final void onStart(Activity activity, boolean z) {
        if (this.mUserId == UserHandle.myUserId() || !this.mIsInternal) {
            return;
        }
        if (!Rune.isSamsungDexMode(this.mContext)) {
            if (z) {
                activity.getWindow().setNavigationBarColor(0);
                activity.getWindow().setNavigationBarContrastEnforced(false);
                activity.getWindow().setStatusBarColor(0);
                activity.getWindow().getDecorView().setSystemUiVisibility(768);
                View decorView = activity.getWindow().getDecorView();
                activity.setContentView(R.layout.sec_confirm_device_credential_base_work_profile);
                ImageView imageView = (ImageView) decorView.findViewById(R.id.background_image);
                if (imageView != null) {
                    imageView.setImageDrawable(
                            KnoxUtils.getInflatedLayoutType(this.mContext) == 1000
                                    ? this.mContext
                                            .getResources()
                                            .getDrawable(R.drawable.sec_knox_credential_bg)
                                    : this.mContext
                                            .getResources()
                                            .getDrawable(R.drawable.sec_knox_credential_bg_land));
                    return;
                }
                return;
            }
            return;
        }
        activity.setContentView(
                R.layout.sec_confirm_device_credential_base_work_profile_desktopmode);
        ImageView imageView2 = (ImageView) activity.findViewById(R.id.backgroundDim);
        if (imageView2 != null) {
            imageView2.setImageResource(0);
            imageView2.setBackgroundColor(Color.parseColor("#990e0e0e"));
        }
        TextView textView = (TextView) activity.findViewById(R.id.guide_text_for_desktopmode);
        textView.setTextColor(Color.parseColor("#fafafa"));
        textView.setText(activity.getString(R.string.knox_keyguard_user_phone_to_unlock));
        WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
        attributes.semAddExtensionFlags(64);
        attributes.dimAmount = 0.15f;
        activity.getWindow().addFlags(2);
        activity.getWindow().setAttributes(attributes);
    }

    @Override // com.samsung.android.settings.knox.KnoxBiometricLockHelper
    public final void setFragmentManager(FragmentManagerImpl fragmentManagerImpl) {
        this.mFragmentManager = fragmentManagerImpl;
    }

    @Override // com.samsung.android.settings.knox.KnoxBiometricLockHelper
    public final void setResultIfNeeded() {
        BiometricFragment biometricFragment;
        if (this.mUserId == UserHandle.myUserId()
                || !this.mIsInternal
                || (biometricFragment =
                                (BiometricFragment)
                                        this.mFragmentManager.findFragmentByTag("fragment"))
                        == null) {
            return;
        }
        CancellationSignal cancellationSignal = biometricFragment.mCancellationSignal;
        if (cancellationSignal != null) {
            cancellationSignal.cancel();
        }
        biometricFragment.cleanup();
    }
}
