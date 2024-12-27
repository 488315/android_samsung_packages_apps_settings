package com.samsung.android.settings.knox;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.biometrics.BiometricPrompt;
import android.os.CancellationSignal;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentManagerImpl;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.applications.manageapplications.CloneBackend$$ExternalSyntheticOutline0;
import com.android.settings.password.BiometricFragment;
import com.android.settings.password.ConfirmDeviceCredentialActivity;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.Rune;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecureFolderBiometricLockHelper extends KnoxBiometricLockHelper {
    public Activity mActivity;
    public ActivityManager mActivityManager;
    public KnoxBiometricsUiHelper.Callback mCallback;
    public Context mContext;
    public DevicePolicyManager mDevicePolicyManager;
    public int mFailedBiometricUnlockAttemptsForSecureFolder;
    public FragmentManager mFragmentManager;
    public boolean mIsInternal;
    public boolean mIsKeyguardLocked;
    public ArrayList mKnoxEventList;
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
        boolean z;
        Log.d(
                "KKG::SecureFolderBiometricLockHelper",
                "interceptOnAuthenticationErrorIfNeeded errorCode = "
                        + i
                        + ", errString = "
                        + ((Object) charSequence));
        int myUserId = UserHandle.myUserId();
        int i2 = this.mUserId;
        if (i2 != myUserId && (z = this.mIsInternal)) {
            boolean isSamsungDexMode = Rune.isSamsungDexMode(this.mContext);
            KnoxBiometricsUiHelper.Callback callback = this.mCallback;
            if (isSamsungDexMode && Rune.isSamsungDexOnPCMode(this.mContext) && i == 13) {
                ((ConfirmDeviceCredentialActivity) callback).finish();
                return true;
            }
            if (i == 5) {
                int currentFailedPasswordAttempts =
                        this.mDevicePolicyManager.getCurrentFailedPasswordAttempts(i2);
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                        currentFailedPasswordAttempts,
                        "BIOMETRIC_ERROR_CANCELED ",
                        "KKG::SecureFolderBiometricLockHelper");
                if (KnoxUtils.isResetWithSamsungAccountEnable(this.mContext, i2)
                        && currentFailedPasswordAttempts >= 15) {
                    Log.d(
                            "KKG::SecureFolderBiometricLockHelper",
                            "BIOMETRIC_ERROR_CANCELED ( show Locked View. )");
                    Intent intent = new Intent();
                    intent.setClassName(
                            KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                            "com.samsung.android.settings.knox.SecureFolderLockedView");
                    intent.addFlags(NetworkAnalyticsConstants.DataPoints.FLAG_UID);
                    intent.putExtra("fromResetBtn", false);
                    intent.putExtra("userId", i2);
                    Activity activity2 = this.mActivity;
                    if (activity2 != null) {
                        intent.putExtras(activity2.getIntent());
                    }
                    try {
                        this.mContext.startActivityAsUser(intent, new UserHandle(0));
                        Context context = this.mContext;
                        if (context instanceof Activity) {
                            ((Activity) context).finish();
                        }
                    } catch (Exception e) {
                        CloneBackend$$ExternalSyntheticOutline0.m(
                                e,
                                new StringBuilder("Exception24 : "),
                                "KKG::SecureFolderBiometricLockHelper");
                    }
                    ((ConfirmDeviceCredentialActivity) callback).finish();
                    return true;
                }
                if (i2 != UserHandle.myUserId() && z) {
                    List<ActivityManager.RunningTaskInfo> runningTasks =
                            this.mActivityManager.getRunningTasks(1);
                    if (!runningTasks.isEmpty()
                            && activity.getComponentName()
                                    .equals(runningTasks.get(0).topActivity)) {
                        Log.d("KKG::SecureFolderBiometricLockHelper", "show ConfirmCredential");
                        ((ConfirmDeviceCredentialActivity) callback).showConfirmCredentials();
                    }
                }
            }
        }
        return false;
    }

    @Override // com.samsung.android.settings.knox.KnoxBiometricLockHelper
    public final void interceptOnAuthenticationFailedIfNeeded(boolean z) {
        Log.d("KKG::SecureFolderBiometricLockHelper", "onAuthenticationFailed");
        if (z) {
            ((ConfirmDeviceCredentialActivity) this.mCallback).finish();
        }
    }

    @Override // com.samsung.android.settings.knox.KnoxBiometricLockHelper
    public final boolean interceptOnAuthenticationSucceededIfNeeded(
            BiometricPrompt.AuthenticationResult authenticationResult) {
        Log.d("KKG::SecureFolderBiometricLockHelper", "onAuthenticationSucceeded");
        int myUserId = UserHandle.myUserId();
        int i = this.mUserId;
        if (i == myUserId || !this.mIsInternal) {
            return false;
        }
        authenticationResult.getBiometricId();
        this.mKnoxEventList.add(KnoxSamsungAnalyticsLogger.addEvent(100, 1000, "fingerprint"));
        KnoxSamsungAnalyticsLogger.send(this.mContext, this.mKnoxEventList, i);
        this.mKnoxEventList.clear();
        return false;
    }

    @Override // com.samsung.android.settings.knox.KnoxBiometricLockHelper
    public final boolean interceptShowConfirmCredentialsIfNeeded() {
        return this.mUserId != UserHandle.myUserId()
                && this.mIsInternal
                && Rune.isSamsungDexMode(this.mContext)
                && Rune.isSamsungDexOnPCMode(this.mContext);
    }

    @Override // com.samsung.android.settings.knox.KnoxBiometricLockHelper
    public final boolean isBiometricAllowed(int i, int i2) {
        if (this.mLockPatternUtils.isBiometricAllowedForUser(i)) {
            UserManager userManager = this.mUserManager;
            int i3 = this.mUserId;
            if (userManager.isUserUnlocked(i3)
                    && !this.mLockPatternUtils.hasPendingEscrowToken(i2)
                    && KnoxUtils.isFingerprintEnabled(this.mContext, i)
                    && KnoxUtils.hasSamsungAccount(this.mContext)
                    && (((BiometricFragment) this.mFragmentManager.findFragmentByTag("fragment"))
                                    != null
                            || this.mLockPatternUtils.getBiometricAttemptDeadline(i3) <= 0)) {
                Context context = this.mContext;
                LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
                if (!KnoxUtils.isResetWithSamsungAccountEnable(context, i)
                        || lockPatternUtils.getCurrentFailedPasswordAttempts(i) < 15) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.samsung.android.settings.knox.KnoxBiometricLockHelper
    public final void onStart(Activity activity, boolean z) {
        if (this.mUserId == UserHandle.myUserId() || !this.mIsInternal) {
            return;
        }
        if (Rune.isSamsungDexMode(this.mContext)) {
            activity.setContentView(
                    R.layout.sec_confirm_device_credential_base_secure_folder_desktopmode);
            TextView textView = (TextView) activity.findViewById(R.id.guide_text_for_desktopmode);
            textView.setTextColor(Color.parseColor("#fafafa"));
            textView.setText(activity.getString(R.string.knox_keyguard_user_phone_to_unlock));
        } else if (z) {
            activity.getWindow().setNavigationBarColor(0);
            activity.getWindow().setNavigationBarContrastEnforced(false);
            activity.getWindow().setStatusBarColor(0);
            activity.getWindow().getDecorView().setSystemUiVisibility(768);
            activity.setContentView(R.layout.sec_confirm_device_credential_base_work_profile);
        }
        ImageView imageView =
                (ImageView) activity.getWindow().getDecorView().findViewById(R.id.background_image);
        if (imageView != null) {
            imageView.setImageDrawable(
                    KnoxUtils.getInflatedLayoutType(this.mContext) == 1000
                            ? this.mContext
                                    .getResources()
                                    .getDrawable(R.drawable.sec_knox_credential_bg)
                            : this.mContext
                                    .getResources()
                                    .getDrawable(R.drawable.sec_knox_credential_bg_land));
        }
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
