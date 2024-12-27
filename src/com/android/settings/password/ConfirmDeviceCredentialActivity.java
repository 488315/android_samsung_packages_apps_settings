package com.android.settings.password;

import android.app.admin.DevicePolicyManager;
import android.app.trust.TrustManager;
import android.content.ComponentName;
import android.content.Intent;
import android.hardware.biometrics.BiometricPrompt;
import android.hardware.biometrics.PromptInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManagerImpl;

import com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.Flags;
import com.android.internal.widget.LockPatternUtils;
import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.biometrics.BiometricsEnrollEnrolling$$ExternalSyntheticOutline0;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.knox.KnoxBiometricLockHelper;
import com.samsung.android.settings.knox.KnoxBiometricsUiHelper;

import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ConfirmDeviceCredentialActivity extends FragmentActivity
        implements KnoxBiometricsUiHelper.Callback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public BiometricFragment mBiometricFragment;
    public int mBiometricsAuthenticators;
    public boolean mCheckDevicePolicyManager;
    public ConfirmDeviceCredentialActivity mContext;
    public CharSequence mDetails;
    public DevicePolicyManager mDevicePolicyManager;
    public boolean mGoingToBackground;
    public KnoxBiometricsUiHelper mKnoxBiometricsUiHelper;
    public LockPatternUtils mLockPatternUtils;
    public boolean mTaskOverlay;
    public String mTitle;
    public TrustManager mTrustManager;
    public int mUserId;
    public UserManager mUserManager;
    public boolean mWaitingForBiometricCallback;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public boolean mForceVerifyPath = false;
    public boolean mBiometricAllowed = true;
    public boolean mUnlockRecovery = false;
    public final ConfirmDeviceCredentialActivity$$ExternalSyntheticLambda3 mExecutor =
            new Executor() { // from class:
                             // com.android.settings.password.ConfirmDeviceCredentialActivity$$ExternalSyntheticLambda3
                @Override // java.util.concurrent.Executor
                public final void execute(Runnable runnable) {
                    ConfirmDeviceCredentialActivity.this.mHandler.post(runnable);
                }
            };
    public final AnonymousClass1 mAuthenticationCallback =
            new BiometricPrompt
                    .AuthenticationCallback() { // from class:
                                                // com.android.settings.password.ConfirmDeviceCredentialActivity.1
                @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
                public final void onAuthenticationError(int i, CharSequence charSequence) {
                    int i2 = ConfirmDeviceCredentialActivity.$r8$clinit;
                    Log.d(
                            "ConfirmDeviceCredentialActivity",
                            "onAuthenticationError errorCode = "
                                    + i
                                    + ", errString = "
                                    + ((Object) charSequence));
                    ConfirmDeviceCredentialActivity confirmDeviceCredentialActivity =
                            ConfirmDeviceCredentialActivity.this;
                    if (confirmDeviceCredentialActivity.mGoingToBackground) {
                        if (confirmDeviceCredentialActivity.mWaitingForBiometricCallback) {
                            confirmDeviceCredentialActivity.mWaitingForBiometricCallback = false;
                            confirmDeviceCredentialActivity.finish();
                            return;
                        }
                        return;
                    }
                    KnoxBiometricLockHelper knoxBiometricLockHelper =
                            confirmDeviceCredentialActivity
                                    .mKnoxBiometricsUiHelper
                                    .mKnoxBiometricLockHelper;
                    if (knoxBiometricLockHelper != null
                            ? knoxBiometricLockHelper.interceptOnAuthenticationErrorIfNeeded(
                                    i, charSequence, confirmDeviceCredentialActivity)
                            : false) {
                        return;
                    }
                    ConfirmDeviceCredentialActivity confirmDeviceCredentialActivity2 =
                            ConfirmDeviceCredentialActivity.this;
                    confirmDeviceCredentialActivity2.mWaitingForBiometricCallback = false;
                    if (i == 10 || i == 5) {
                        confirmDeviceCredentialActivity2.finish();
                        return;
                    }
                    if (confirmDeviceCredentialActivity2.mUserManager.getUserInfo(
                                    confirmDeviceCredentialActivity2.mUserId)
                            == null) {
                        TooltipPopup$$ExternalSyntheticOutline0.m(
                                new StringBuilder("Finishing, user no longer valid: "),
                                ConfirmDeviceCredentialActivity.this.mUserId,
                                "ConfirmDeviceCredentialActivity");
                        ConfirmDeviceCredentialActivity.this.finish();
                        return;
                    }
                    ConfirmDeviceCredentialActivity confirmDeviceCredentialActivity3 =
                            ConfirmDeviceCredentialActivity.this;
                    if ((confirmDeviceCredentialActivity3.mBiometricsAuthenticators
                                    & NetworkAnalyticsConstants.DataPoints.FLAG_UID)
                            != 0) {
                        confirmDeviceCredentialActivity3.showConfirmCredentials();
                        return;
                    }
                    Log.i(
                            "ConfirmDeviceCredentialActivity",
                            "Finishing, device credential not requested");
                    if (Flags.mandatoryBiometrics() && i == 9) {
                        ConfirmDeviceCredentialActivity.this.setResult(100);
                    }
                    ConfirmDeviceCredentialActivity.this.finish();
                }

                @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
                public final void onAuthenticationFailed() {
                    ConfirmDeviceCredentialActivity confirmDeviceCredentialActivity =
                            ConfirmDeviceCredentialActivity.this;
                    confirmDeviceCredentialActivity.mWaitingForBiometricCallback = false;
                    KnoxBiometricsUiHelper knoxBiometricsUiHelper =
                            confirmDeviceCredentialActivity.mKnoxBiometricsUiHelper;
                    boolean z = confirmDeviceCredentialActivity.mGoingToBackground;
                    KnoxBiometricLockHelper knoxBiometricLockHelper =
                            knoxBiometricsUiHelper.mKnoxBiometricLockHelper;
                    if (knoxBiometricLockHelper != null) {
                        knoxBiometricLockHelper.interceptOnAuthenticationFailedIfNeeded(z);
                    } else {
                        confirmDeviceCredentialActivity.mDevicePolicyManager
                                .reportFailedBiometricAttempt(
                                        confirmDeviceCredentialActivity.mUserId);
                    }
                }

                @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
                public final void onAuthenticationSucceeded(
                        BiometricPrompt.AuthenticationResult authenticationResult) {
                    KnoxBiometricLockHelper knoxBiometricLockHelper =
                            ConfirmDeviceCredentialActivity.this
                                    .mKnoxBiometricsUiHelper
                                    .mKnoxBiometricLockHelper;
                    if (knoxBiometricLockHelper != null
                            ? knoxBiometricLockHelper.interceptOnAuthenticationSucceededIfNeeded(
                                    authenticationResult)
                            : false) {
                        return;
                    }
                    ConfirmDeviceCredentialActivity confirmDeviceCredentialActivity =
                            ConfirmDeviceCredentialActivity.this;
                    confirmDeviceCredentialActivity.mWaitingForBiometricCallback = false;
                    confirmDeviceCredentialActivity.mTrustManager.setDeviceLockedForUser(
                            confirmDeviceCredentialActivity.mUserId, false);
                    boolean z = authenticationResult.getAuthenticationType() == 1;
                    ConfirmDeviceCredentialActivity confirmDeviceCredentialActivity2 =
                            ConfirmDeviceCredentialActivity.this;
                    ConfirmDeviceCredentialUtils.reportSuccessfulAttempt(
                            confirmDeviceCredentialActivity2.mLockPatternUtils,
                            confirmDeviceCredentialActivity2.mUserManager,
                            confirmDeviceCredentialActivity2.mDevicePolicyManager,
                            confirmDeviceCredentialActivity2.mUserId,
                            z);
                    ConfirmDeviceCredentialUtils.checkForPendingIntent(
                            ConfirmDeviceCredentialActivity.this);
                    ConfirmDeviceCredentialActivity.this.setResult(-1);
                    ConfirmDeviceCredentialActivity.this.finish();
                }

                public final void onSystemEvent(int i) {
                    int i2 = ConfirmDeviceCredentialActivity.$r8$clinit;
                    ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                            i, "SystemEvent: ", "ConfirmDeviceCredentialActivity");
                    if (i != 1) {
                        return;
                    }
                    ConfirmDeviceCredentialActivity.this.finish();
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class InternalActivity extends ConfirmDeviceCredentialActivity {}

    @Override // android.app.Activity
    public final Intent getIntent() {
        KnoxBiometricsUiHelper knoxBiometricsUiHelper = this.mKnoxBiometricsUiHelper;
        if (knoxBiometricsUiHelper == null) {
            return super.getIntent();
        }
        Intent intent = super.getIntent();
        KnoxBiometricLockHelper knoxBiometricLockHelper =
                knoxBiometricsUiHelper.mKnoxBiometricLockHelper;
        return knoxBiometricLockHelper != null
                ? knoxBiometricLockHelper.addIntentIfNeeded(intent)
                : intent;
    }

    public final boolean isBiometricAllowed(int i, int i2) {
        boolean z;
        KnoxBiometricLockHelper knoxBiometricLockHelper =
                this.mKnoxBiometricsUiHelper.mKnoxBiometricLockHelper;
        if (knoxBiometricLockHelper != null
                ? knoxBiometricLockHelper.interceptIsBiometricAllowedIfNeeded()
                : false) {
            KnoxBiometricLockHelper knoxBiometricLockHelper2 =
                    this.mKnoxBiometricsUiHelper.mKnoxBiometricLockHelper;
            if (knoxBiometricLockHelper2 != null) {
                return knoxBiometricLockHelper2.isBiometricAllowed(i, i2);
            }
            return false;
        }
        if (this.mLockPatternUtils.isBiometricAllowedForUser(i)) {
            int i3 = this.mUserId;
            if (!(!((com.android.internal.hidden_from_bootclasspath.android.os.Flags
                                    .allowPrivateProfile()
                            && android.multiuser.Flags.enableBiometricsToUnlockPrivateSpace()
                            && android.multiuser.Flags.enablePrivateSpaceFeatures())
                    ? StorageManager.isCeStorageUnlocked(i3)
                    : this.mUserManager.isUserUnlocked(i3)))) {
                z = false;
                return z ? false : false;
            }
        }
        z = true;
        return z ? false : false;
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x03f4  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x03c8  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x03d1  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0502  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0520  */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCreate(android.os.Bundle r23) {
        /*
            Method dump skipped, instructions count: 1333
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.password.ConfirmDeviceCredentialActivity.onCreate(android.os.Bundle):void");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onPause() {
        KnoxBiometricLockHelper knoxBiometricLockHelper;
        super.onPause();
        if (isChangingConfigurations()) {
            this.mGoingToBackground = false;
            return;
        }
        this.mGoingToBackground = true;
        BiometricFragment biometricFragment = this.mBiometricFragment;
        if (biometricFragment != null) {
            boolean z = biometricFragment.mAuthenticating;
            AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                    "Authenticating: ", "ConfirmDeviceCredentialActivity", z);
            if (z
                    && (knoxBiometricLockHelper =
                                    this.mKnoxBiometricsUiHelper.mKnoxBiometricLockHelper)
                            != null) {
                knoxBiometricLockHelper.setResultIfNeeded();
            }
        }
        if (this.mWaitingForBiometricCallback) {
            return;
        }
        finish();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onStart() {
        super.onStart();
        setVisible(true);
        boolean z = this instanceof InternalActivity;
        KnoxBiometricLockHelper knoxBiometricLockHelper =
                this.mKnoxBiometricsUiHelper.mKnoxBiometricLockHelper;
        if (knoxBiometricLockHelper != null) {
            knoxBiometricLockHelper.onStart(this, z);
        }
        if ((getResources().getConfiguration().uiMode & 48) != 32) {
            getWindow().getInsetsController().setSystemBarsAppearance(8, 8);
        }
    }

    public final void showBiometricPrompt(PromptInfo promptInfo, int i) {
        boolean z;
        BiometricFragment biometricFragment =
                (BiometricFragment) getSupportFragmentManager().findFragmentByTag("fragment");
        this.mBiometricFragment = biometricFragment;
        if (biometricFragment == null) {
            ComponentName callingActivity = getCallingActivity();
            BiometricFragment biometricFragment2 = new BiometricFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("prompt_info", promptInfo);
            bundle.putParcelable("calling_activity", callingActivity);
            biometricFragment2.setArguments(bundle);
            this.mBiometricFragment = biometricFragment2;
            z = true;
        } else {
            z = false;
        }
        BiometricFragment biometricFragment3 = this.mBiometricFragment;
        ConfirmDeviceCredentialActivity$$ExternalSyntheticLambda3
                confirmDeviceCredentialActivity$$ExternalSyntheticLambda3 = this.mExecutor;
        AnonymousClass1 anonymousClass1 = this.mAuthenticationCallback;
        biometricFragment3.mClientExecutor =
                confirmDeviceCredentialActivity$$ExternalSyntheticLambda3;
        biometricFragment3.mClientCallback = anonymousClass1;
        biometricFragment3.mUserId = i;
        if (SemPersonaManager.isKnoxId(this.mUserId)) {
            this.mBiometricFragment.mIsInternal = this instanceof InternalActivity;
        }
        if (z) {
            FragmentManagerImpl supportFragmentManager = getSupportFragmentManager();
            BackStackRecord m =
                    BiometricsEnrollEnrolling$$ExternalSyntheticOutline0.m(
                            supportFragmentManager, supportFragmentManager);
            m.doAddOp(0, this.mBiometricFragment, "fragment", 1);
            m.commitInternal(false);
        }
    }

    public final void showConfirmCredentials() {
        KnoxBiometricLockHelper knoxBiometricLockHelper =
                this.mKnoxBiometricsUiHelper.mKnoxBiometricLockHelper;
        if (knoxBiometricLockHelper != null
                ? knoxBiometricLockHelper.interceptShowConfirmCredentialsIfNeeded()
                : false) {
            return;
        }
        ChooseLockSettingsHelper.Builder builder = new ChooseLockSettingsHelper.Builder(this);
        builder.mHeader = this.mTitle;
        builder.mDescription = this.mDetails;
        builder.mExternal = true;
        builder.mUserId = this.mUserId;
        builder.mTaskOverlay = this.mTaskOverlay;
        builder.mForceVerifyPath = this.mForceVerifyPath;
        if (!builder.show()) {
            Log.d("ConfirmDeviceCredentialActivity", "No pin/pattern/pass set");
            setResult(-1);
        }
        finish();
    }
}
