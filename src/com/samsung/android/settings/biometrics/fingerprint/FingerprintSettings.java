package com.samsung.android.settings.biometrics.fingerprint;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.hardware.biometrics.BiometricPrompt;
import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecDropDownPreference;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceScreen;
import androidx.preference.SecPreferenceUtils;
import androidx.preference.SwitchPreference;
import androidx.recyclerview.widget.RecyclerView;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.biometrics.GatekeeperPasswordProvider;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.password.ChooseLockGeneric;
import com.android.settings.password.ChooseLockSettingsHelper;

import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.SettingsPreferenceFragmentLinkData;
import com.samsung.android.settings.biometrics.BiometricsConfig;
import com.samsung.android.settings.biometrics.BiometricsGenericHelper;
import com.samsung.android.settings.biometrics.BiometricsRestrictedSwitchPreference;
import com.samsung.android.settings.display.SecDisplayUtils;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.lockscreen.LockUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.security.SecurityUtils;
import com.samsung.android.settings.widget.SecRelativeLinkView;
import com.sec.ims.volte2.data.VolteConstants;

import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FingerprintSettings extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener {
    public PreferenceScreen mAddFingerprint;
    public SwitchPreference mAlwaysOn;
    public final AnonymousClass2 mAuthenticationCallback;
    public View mEmptyView;
    public SwitchPreference mFastRecognition;
    public SwitchPreference mFingerEffect;
    public CancellationSignal mFingerprintCancel;
    public FingerprintManager mFingerprintManager;
    public GatekeeperPasswordProvider mGkPwProvider;
    public final AnonymousClass1 mHandler;
    public boolean mHasEnrolled;
    public Intent mIntent;
    public final boolean mIsSupportFpAlwaysOn;
    public boolean mLaunchedConfirm;
    public boolean mLockConfirmed;
    public LockPatternUtils mLockPatternUtils;
    public String mPreviousStage;
    public BiometricPrompt.Builder mPromptBuilder;
    public SecRelativeLinkView mRelativeLinkView;
    public SwitchPreference mSamsungAccount;
    public FragmentActivity mSamsungAccountContext;
    public BiometricsRestrictedSwitchPreference mScreenLock;
    public SwitchPreference mScreenOffIcon;
    public SecDropDownPreference mScreenOffIconAod;
    public SecPreferenceScreen mShowFingerprintIcon;
    public SwitchPreference mStayOnLockScreen;
    public int mUserId;
    public SwitchPreference mWebSignIn;
    public static final int[] mScreenOffIconOptionSummaryString = {
        R.string.sec_fingerprint_screen_off_icon_option_summary_never,
        R.string.sec_fingerprint_screen_off_icon_option_summary_touch_to_show,
        R.string.sec_fingerprint_screen_off_icon_option_summary_always_on
    };
    public static boolean mIsKeepEnrollSession = false;
    public static boolean mIsPreferenceClicked = false;
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass4();
    public boolean through_onpreferencechange = false;
    public List items = null;
    public int mEnrolledFingerCount = 0;
    public byte[] mToken = null;
    public long mChallenge = 0;
    public long mGkPwHandle = 0;
    public boolean mIsRunRegister = false;
    public boolean mIsBiometricsSettingsDestroy = false;
    public boolean mIsAfw = false;
    public boolean mIsKnox = false;
    public boolean mIsRegisteredFromEntry = false;
    public boolean mIsInMultiWindowMode = false;
    public boolean mNeedFmmPopup = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.biometrics.fingerprint.FingerprintSettings$4, reason: invalid class name */
    public final class AnonymousClass4 extends BaseStatusLoggingProvider {
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:47:0x0161  */
        /* JADX WARN: Removed duplicated region for block: B:49:0x0173  */
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.util.List getStatusLoggingData(android.content.Context r10) {
            /*
                Method dump skipped, instructions count: 396
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.settings.biometrics.fingerprint.FingerprintSettings.AnonymousClass4.getStatusLoggingData(android.content.Context):java.util.List");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class FingerprintPreference extends SecPreference {
        public Fingerprint mFingerprint;
        public View mView;

        public FingerprintPreference(Context context) {
            super(context, null, android.R.attr.preferenceStyle, 0);
        }

        @Override // androidx.preference.Preference
        public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
            super.onBindViewHolder(preferenceViewHolder);
            this.mView = preferenceViewHolder.itemView;
            TypedValue typedValue = new TypedValue();
            getContext()
                    .getTheme()
                    .resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
            this.mView.setBackgroundResource(typedValue.resourceId);
            this.mView.setOnLongClickListener(
                    new View
                            .OnLongClickListener() { // from class:
                                                     // com.samsung.android.settings.biometrics.fingerprint.FingerprintSettings$FingerprintPreference$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnLongClickListener
                        public final boolean onLongClick(View view) {
                            FingerprintSettings.FingerprintPreference fingerprintPreference =
                                    FingerprintSettings.FingerprintPreference.this;
                            BiometricsGenericHelper.insertSaLog(
                                    fingerprintPreference.getContext(), 8217);
                            FingerprintSettings fingerprintSettings = FingerprintSettings.this;
                            int biometricId = fingerprintPreference.mFingerprint.getBiometricId();
                            if (fingerprintSettings.mEnrolledFingerCount == 1) {
                                biometricId =
                                        ((Fingerprint) fingerprintSettings.items.get(0))
                                                .getBiometricId();
                            }
                            StringBuilder m =
                                    ListPopupWindow$$ExternalSyntheticOutline0.m(
                                            biometricId,
                                            "startSelectListUI : fId[",
                                            "], twChecklist[");
                            m.append(Arrays.toString((boolean[]) null));
                            m.append("]");
                            Log.d("FpstFingerprintSettings", m.toString());
                            FingerprintSettings.mIsKeepEnrollSession = true;
                            Bundle bundle = new Bundle();
                            bundle.putInt("selected_id", biometricId);
                            bundle.putBoolean("isAfw", fingerprintSettings.mIsAfw);
                            bundle.putInt(
                                    "android.intent.extra.USER_ID", fingerprintSettings.mUserId);
                            SubSettingLauncher subSettingLauncher =
                                    new SubSettingLauncher(fingerprintSettings.getContext());
                            String name = FingerprintSettingsMultiSelect.class.getName();
                            SubSettingLauncher.LaunchRequest launchRequest =
                                    subSettingLauncher.mLaunchRequest;
                            launchRequest.mDestinationName = name;
                            subSettingLauncher.setResultListener(fingerprintSettings, 1007);
                            launchRequest.mArguments = bundle;
                            launchRequest.mSourceMetricsCategory = 8217;
                            subSettingLauncher.launch();
                            return true;
                        }
                    });
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.settings.biometrics.fingerprint.FingerprintSettings$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.samsung.android.settings.biometrics.fingerprint.FingerprintSettings$2] */
    public FingerprintSettings() {
        FingerprintSettingsUtils.isSupportFingerprintAlwaysOn();
        this.mIsSupportFpAlwaysOn = true;
        this.mPreviousStage = null;
        this.mIntent = null;
        this.mRelativeLinkView = null;
        this.mHandler =
                new Handler() { // from class:
                                // com.samsung.android.settings.biometrics.fingerprint.FingerprintSettings.1
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        Log.secD(
                                "FpstFingerprintSettings",
                                "mHandler event: "
                                        .concat(
                                                FingerprintSettingsUtils.convertEvtToString(
                                                        message.what)));
                        int i = message.what;
                        FingerprintSettings fingerprintSettings = FingerprintSettings.this;
                        if (i == 1001) {
                            BiometricsGenericHelper.insertSaLog(
                                    fingerprintSettings.getContext(), 8214, 8226);
                            return;
                        }
                        if (i != 1003) {
                            return;
                        }
                        int i2 = message.arg1;
                        CharSequence charSequence = (CharSequence) message.obj;
                        fingerprintSettings.getClass();
                        Log.secD(
                                "FpstFingerprintSettings",
                                "handleError errMsgId" + i2 + ", msg: " + ((Object) charSequence));
                        fingerprintSettings.stopFingerprintAuth();
                        FingerprintSettings.mIsPreferenceClicked = false;
                        if (i2 == 3 || i2 == 5 || i2 == 10) {
                            Log.d(
                                    "FpstFingerprintSettings",
                                    "handleError FINGERPRINT_ERROR_CANCELED");
                            return;
                        }
                        FragmentActivity activity = fingerprintSettings.getActivity();
                        if (activity != null) {
                            Toast.makeText(activity, charSequence, 0);
                        }
                    }
                };
        this.mAuthenticationCallback =
                new BiometricPrompt
                        .AuthenticationCallback() { // from class:
                                                    // com.samsung.android.settings.biometrics.fingerprint.FingerprintSettings.2
                    @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
                    public final void onAuthenticationError(int i, CharSequence charSequence) {
                        obtainMessage(1003, i, 0, charSequence).sendToTarget();
                        super.onAuthenticationError(i, charSequence);
                    }

                    @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
                    public final void onAuthenticationFailed() {
                        obtainMessage(1002).sendToTarget();
                        super.onAuthenticationFailed();
                    }

                    @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
                    public final void onAuthenticationHelp(int i, CharSequence charSequence) {
                        obtainMessage(
                                        VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI,
                                        i,
                                        0,
                                        charSequence)
                                .sendToTarget();
                        super.onAuthenticationHelp(i, charSequence);
                    }

                    @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
                    public final void onAuthenticationSucceeded(
                            BiometricPrompt.AuthenticationResult authenticationResult) {
                        obtainMessage(1001).sendToTarget();
                        super.onAuthenticationSucceeded(authenticationResult);
                    }
                };
    }

    public static boolean getReasonIsCancelSession(Intent intent) {
        return intent != null && "cancelsession".equals(intent.getStringExtra("reason"));
    }

    public static void setFingerPrintOnSAConfirmed(Context context, int i) {
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException();
        }
        Log.secD(
                "FpstFingerprintSettings",
                "setFingerPrintOnSamsungAccountConfirmed:result : "
                        + Settings.Secure.putInt(
                                context.getContentResolver(),
                                "fingerprint_samsungaccount_confirmed",
                                i));
    }

    public static void setUseFingerprintForSA(Context context, int i) {
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException();
        }
        Log.secD(
                "FpstFingerprintSettings",
                "setFingerPrintOnSamsungAccountUsed:result : "
                        + Settings.Secure.putInt(
                                context.getContentResolver(),
                                "fingerprint_used_samsungaccount",
                                i));
    }

    public final void cancelAndSessionEnd$2() {
        Log.d("FpstFingerprintSettings", "cancelAndSessionEnd() ");
        stopFingerprintAuth();
        if (mIsKeepEnrollSession) {
            Log.d("FpstFingerprintSettings", "Keep the session and activity");
            return;
        }
        Log.d("FpstFingerprintSettings", "Close the session and finish the activity");
        Log.secD("FpstFingerprintSettings", "finishFingerprintSettings()");
        FingerprintManager fingerprintManager = this.mFingerprintManager;
        if (fingerprintManager != null) {
            long j = this.mChallenge;
            if (j != 0) {
                fingerprintManager.revokeChallenge(this.mUserId, j);
            }
        }
        if (this.mIsBiometricsSettingsDestroy
                && "lock_screen_fingerprint_menu".equals(this.mPreviousStage)) {
            Intent intent = this.mIntent;
            if (intent == null) {
                intent = new Intent();
            }
            this.mIntent = intent;
            intent.putExtra("screen_lock_force_destroy", true);
            this.mIntent.putExtra("gk_pw_handle", this.mGkPwHandle);
            setResult(0, this.mIntent);
        }
        getActivity().finish();
    }

    public final boolean checkMobileKeyboard() {
        boolean z;
        FragmentActivity activity = getActivity();
        if (activity != null && isAdded()) {
            boolean z2 = FingerprintSettingsUtils.SUB_DISPLAY_IS_LARGE_SCREEN;
            if (SecurityUtils.ConnectedMobileKeypad(activity)) {
                z = true;
                StringBuilder m =
                        RowView$$ExternalSyntheticOutline0.m(
                                "checkMobileKeyboard. return : ", ", isAdded() :", z);
                m.append(isAdded());
                Log.d("FpstFingerprintSettings", m.toString());
                return z;
            }
        }
        z = false;
        StringBuilder m2 =
                RowView$$ExternalSyntheticOutline0.m(
                        "checkMobileKeyboard. return : ", ", isAdded() :", z);
        m2.append(isAdded());
        Log.d("FpstFingerprintSettings", m2.toString());
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x031b  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x03be  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x03ce  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x03e3  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x03e7  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x03ff  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0446  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x012e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void createPreferenceHierarchy$5() {
        /*
            Method dump skipped, instructions count: 1447
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.biometrics.fingerprint.FingerprintSettings.createPreferenceHierarchy$5():void");
    }

    public final boolean getFingerprintSamsungAccountVerification() {
        boolean z =
                Settings.Secure.getInt(getContentResolver(), "fingerprint_used_samsungaccount", 0)
                        != 0;
        Log.secD("FpstFingerprintSettings", "getFingerprintSamsungAccountVerification :" + z);
        return z;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        if (this.mPreviousStage == null) {
            return null;
        }
        return ChooseLockGeneric.ChooseLockGenericFragment.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 8214;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_fingerprint;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_lockscreen";
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        String stringExtra;
        super.onActivityResult(i, i2, intent);
        Log.secD(
                "FpstFingerprintSettings",
                "==onActivityResult requestCode : " + i + " resultCode : " + i2);
        mIsKeepEnrollSession = false;
        if (intent != null) {
            if (GatekeeperPasswordProvider.containsGatekeeperPasswordHandle(intent)) {
                long longExtra = intent.getLongExtra("gk_pw_handle", 0L);
                if (longExtra != 0) {
                    this.mIntent = new Intent(intent);
                    this.mGkPwHandle = longExtra;
                }
                Log.d("FpstFingerprintSettings", "GkPwHandle : " + this.mGkPwHandle);
            }
            byte[] byteArrayExtra = intent.getByteArrayExtra("hw_auth_token");
            if (byteArrayExtra != null) {
                this.mToken = byteArrayExtra;
                this.mChallenge = intent.getLongExtra("challenge", this.mChallenge);
            }
            boolean booleanExtra = intent.getBooleanExtra("biometrics_settings_destroy", false);
            this.mIsBiometricsSettingsDestroy = booleanExtra;
            if (booleanExtra) {
                Log.d("FpstFingerprintSettings", "onActivityResult: Finish Settings");
                mIsKeepEnrollSession = false;
                cancelAndSessionEnd$2();
                return;
            }
        }
        if (i == 101) {
            if (i2 == -1) {
                try {
                    setUseFingerprintForSA(this.mSamsungAccountContext, 1);
                    setFingerPrintOnSAConfirmed(this.mSamsungAccountContext, 1);
                    this.mSamsungAccount.setChecked(getFingerprintSamsungAccountVerification());
                    StringBuilder sb =
                            new StringBuilder(
                                    "SAMSUNGACCOUNT_EXISTING_REQUEST RESULT_OK setChecked : ");
                    sb.append(
                            Settings.Secure.getInt(
                                            getActivity().getContentResolver(),
                                            "fingerprint_used_samsungaccount",
                                            0)
                                    == 1);
                    Log.secD("FpstFingerprintSettings", sb.toString());
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            try {
                setUseFingerprintForSA(this.mSamsungAccountContext, 0);
                setFingerPrintOnSAConfirmed(this.mSamsungAccountContext, 0);
                this.mSamsungAccount.setChecked(getFingerprintSamsungAccountVerification());
                StringBuilder sb2 =
                        new StringBuilder(
                                "SAMSUNGACCOUNT_EXISTING_REQUEST RESULT_NOK setChecked : ");
                sb2.append(
                        Settings.Secure.getInt(
                                        getActivity().getContentResolver(),
                                        "fingerprint_used_samsungaccount",
                                        0)
                                == 1);
                Log.secD("FpstFingerprintSettings", sb2.toString());
                return;
            } catch (Exception e2) {
                e2.printStackTrace();
                return;
            }
        }
        if (i == 102) {
            if (i2 == -1 || i2 == 1) {
                try {
                    setUseFingerprintForSA(this.mSamsungAccountContext, 1);
                    setFingerPrintOnSAConfirmed(this.mSamsungAccountContext, 1);
                    this.mSamsungAccount.setChecked(getFingerprintSamsungAccountVerification());
                    StringBuilder sb3 =
                            new StringBuilder("SAMSUNGACCOUNT_REQUEST RESULT_OK setChecked : ");
                    sb3.append(
                            Settings.Secure.getInt(
                                            getActivity().getContentResolver(),
                                            "fingerprint_used_samsungaccount",
                                            0)
                                    == 1);
                    Log.secD("FpstFingerprintSettings", sb3.toString());
                    return;
                } catch (Exception e3) {
                    e3.printStackTrace();
                    return;
                }
            }
            try {
                setUseFingerprintForSA(this.mSamsungAccountContext, 0);
                setFingerPrintOnSAConfirmed(this.mSamsungAccountContext, 0);
                this.mSamsungAccount.setChecked(getFingerprintSamsungAccountVerification());
                StringBuilder sb4 =
                        new StringBuilder("SAMSUNGACCOUNT_REQUEST RESULT_NOK setChecked : ");
                sb4.append(
                        Settings.Secure.getInt(
                                        getActivity().getContentResolver(),
                                        "fingerprint_used_samsungaccount",
                                        0)
                                == 1);
                Log.secD("FpstFingerprintSettings", sb4.toString());
                return;
            } catch (Exception e4) {
                e4.printStackTrace();
                return;
            }
        }
        if (i == 201) {
            this.mLaunchedConfirm = false;
            if (i2 != -1 || this.mGkPwHandle == 0) {
                finish();
                return;
            }
            Log.d("FpstFingerprintSettings", "onActivityResult : CONFIRM_REQUEST");
            this.mLockConfirmed = true;
            highlightPreferenceIfNeeded(true);
            requestToken$1(false);
            return;
        }
        if (i == 2000) {
            Log.d("FpstFingerprintSettings", "FMM Dialog finished!");
            return;
        }
        if (i == 3001) {
            Log.d("FpstFingerprintSettings", "onActivityResult : KNOX_CHOOSE_LOCK_GENERIC_REQUEST");
            if (this.mFingerprintManager.hasEnrolledFingerprints(this.mUserId)
                    && this.mLockPatternUtils.isSeparateProfileChallengeEnabled(this.mUserId)) {
                FingerprintSettingsUtils.setFingerprintLock(
                        this.mUserId, getActivity(), this.mLockPatternUtils);
                this.mScreenLock.setChecked(true);
                return;
            }
            return;
        }
        if (i != 1000 && i != 1001) {
            switch (i) {
                case 1007:
                    Log.secD("FpstFingerprintSettings", "activity. FINGERPRINT_MULTI_SELECT");
                    if (i2 == 0 && getReasonIsCancelSession(intent)) {
                        cancelAndSessionEnd$2();
                        break;
                    }
                    break;
                case EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS /* 1008 */:
                    Log.d("FpstFingerprintSettings", "Biometrics disclaimer finished!");
                    break;
                case EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_FAILURE /* 1009 */:
                    Log.secD("FpstFingerprintSettings", "activity. FINGERPRINT_RENAME_REQUEST");
                    if (i2 == 0 && getReasonIsCancelSession(intent)) {
                        cancelAndSessionEnd$2();
                        break;
                    }
                    break;
                case EnterpriseContainerCallback.CONTAINER_MOUNT_STATUS /* 1010 */:
                    Log.d("FpstFingerprintSettings", "Fingerprint always on finished!");
                    if (i2 == 0 && getReasonIsCancelSession(intent)) {
                        cancelAndSessionEnd$2();
                        break;
                    }
                    break;
                case EnterpriseContainerCallback.CONTAINER_PACKAGE_INFORMATION /* 1011 */:
                    Log.d("FpstFingerprintSettings", "Show fingerprint icon finished!");
                    if (i2 == 0 && getReasonIsCancelSession(intent)) {
                        cancelAndSessionEnd$2();
                        break;
                    }
                    break;
            }
            return;
        }
        if (intent != null && intent.getBooleanExtra("biometrics_settings_destroy", false)) {
            Log.d("FpstFingerprintSettings", "onActivityResult: Finish Fingerprint Settings");
            mIsKeepEnrollSession = false;
            cancelAndSessionEnd$2();
            return;
        }
        if (this.mIsRunRegister) {
            Log.d("FpstFingerprintSettings", "reset runRegister");
            this.mIsRunRegister = false;
        }
        if (i2 != -1 && i2 != 1) {
            if (i2 != 0) {
                if (this.mSamsungAccount != null) {
                    try {
                        Log.secD("FpstFingerprintSettings", "SAMSUNGACCOUNT : there is no case");
                        setUseFingerprintForSA(this.mSamsungAccountContext, 0);
                        setFingerPrintOnSAConfirmed(this.mSamsungAccountContext, 0);
                        this.mSamsungAccount.setChecked(getFingerprintSamsungAccountVerification());
                        return;
                    } catch (Exception e5) {
                        e5.printStackTrace();
                        return;
                    }
                }
                return;
            }
            if (intent != null && intent.getIntExtra("enrollResult", 0) == 1) {
                mIsKeepEnrollSession = false;
                cancelAndSessionEnd$2();
                return;
            } else {
                if (this.mHasEnrolled
                        || !"lock_screen_fingerprint_menu".equals(this.mPreviousStage)) {
                    return;
                }
                setResult(0, intent);
                cancelAndSessionEnd$2();
                return;
            }
        }
        this.mLockConfirmed = true;
        this.mHasEnrolled = this.mFingerprintManager.hasEnrolledFingerprints(this.mUserId);
        if (intent != null) {
            try {
                stringExtra = intent.getStringExtra("previousStage");
                Log.d("FpstFingerprintSettings", "onActivityResult : " + stringExtra);
            } catch (NullPointerException unused) {
                Log.secD("FpstFingerprintSettings", "data is null!!");
                return;
            }
        } else {
            stringExtra = null;
        }
        if ("fingerprint_settings_set_screen_lock".equals(stringExtra)) {
            BiometricsRestrictedSwitchPreference biometricsRestrictedSwitchPreference =
                    this.mScreenLock;
            getContext();
            biometricsRestrictedSwitchPreference.setChecked(
                    FingerprintSettingsUtils.getFingerprintUnlockEnabled(
                            this.mLockPatternUtils, this.mUserId));
        }
        if ("support_web_signin".equals(stringExtra)) {
            Log.secD("FpstFingerprintSettings", "previos stage is WebSingin");
            setFingerprintVerification(true);
        }
        if ("support_samsung_account".equals(stringExtra)) {
            if (BiometricsConfig.isSamsungAccountSignedIn(this.mSamsungAccountContext)) {
                Log.secD("FpstFingerprintSettings", "SamsungAccountSignedIn is TRUE.");
                Intent intent2 = new Intent("com.msc.action.samsungaccount.CONFIRM_PASSWORD_POPUP");
                intent2.putExtra("client_id", "s5d189ajvs");
                intent2.putExtra("client_secret", "E8781246E4A0F6E9E213178CC003DE6A");
                intent2.putExtra("Is_From_SA_Verify", true);
                startActivityForResultWrapper$1(101, intent2);
                return;
            }
            Log.secD("FpstFingerprintSettings", "SamsungAccountSignedIn is FALSE.");
            Intent intent3 = new Intent("com.osp.app.signin.action.ADD_SAMSUNG_ACCOUNT");
            intent3.putExtra("mypackage", this.mSamsungAccountContext.getPackageName());
            intent3.putExtra("OSP_VER", "OSP_02");
            intent3.putExtra("client_id", "s5d189ajvs");
            intent3.putExtra("MODE", "ADD_ACCOUNT");
            intent3.putExtra("Is_From_SA_Verify", true);
            startActivityForResultWrapper$1(102, intent3);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Log.secD("FpstFingerprintSettings", "==onConfigurationChanged");
        if (BiometricsGenericHelper.isBlockedInMultiWindowMode(getActivity(), configuration)) {
            Toast.makeText(
                            getActivity(),
                            getString(R.string.sec_biometrics_common_not_use_multi_window_view),
                            0)
                    .show();
            finish();
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        boolean z;
        super.onCreate(bundle);
        Log.d("FpstFingerprintSettings", "==onCreate()");
        if (SecurityUtils.isNotAvailableBiometricsWithDexAndMultiWindow(
                getActivity(), R.string.bio_fingerprint_sanner_title, "FpstFingerprintSettings")) {
            finish();
            return;
        }
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.mPreviousStage = arguments.getString("previousStage");
            this.mGkPwHandle = arguments.getLong("gk_pw_handle", 0L);
            this.mIsRegisteredFromEntry =
                    arguments.getBoolean("fingerprint_registered_from_fingerprint", false);
            this.mIsAfw = arguments.getBoolean("isAfw");
            this.mIsKnox = arguments.getBoolean("is_knox");
            this.mUserId = arguments.getInt("android.intent.extra.USER_ID");
            this.mNeedFmmPopup = arguments.getBoolean("need_fmm_popup");
            Log.secD("FpstFingerprintSettings", "mIsAfw : " + this.mIsAfw);
            Log.secD("FpstFingerprintSettings", "mIsKnox : " + this.mIsKnox);
            Log.secD(
                    "FpstFingerprintSettings",
                    "mIsRegisteredFromEntry : " + this.mIsRegisteredFromEntry);
            Log.secD("FpstFingerprintSettings", "mUserId : " + this.mUserId);
            Log.d("FpstFingerprintSettings", "GkPwHandle : " + this.mGkPwHandle);
            Log.d("FpstFingerprintSettings", "mNeedFmmPopup : " + this.mNeedFmmPopup);
            byte[] byteArray = arguments.getByteArray("hw_auth_token");
            if (byteArray != null) {
                this.mToken = byteArray;
                this.mChallenge = arguments.getLong("challenge", this.mChallenge);
            }
        } else {
            Log.secD("FpstFingerprintSettings", "args is null");
        }
        this.mIsInMultiWindowMode = LockUtils.isInMultiWindow(getActivity());
        if (bundle != null) {
            this.mToken = bundle.getByteArray("hw_auth_token");
            this.mChallenge = bundle.getLong("challenge", this.mChallenge);
            this.mGkPwHandle = bundle.getLong("gk_pw_handle", 0L);
            z = bundle.getBoolean("is_change_configuration");
            this.mLaunchedConfirm = bundle.getBoolean("waitingForLockConfirm");
        } else {
            z = false;
        }
        FingerprintManager fingerprintManager =
                (FingerprintManager) getActivity().getSystemService("fingerprint");
        this.mFingerprintManager = fingerprintManager;
        if (fingerprintManager == null) {
            Log.e("FpstFingerprintSettings", "FingerprintManager is null.");
            FingerprintSettingsUtils.showSensorErrorDialog(
                    getActivity(),
                    getString(R.string.sec_fingerprint_error_message_sensor_error),
                    true);
            return;
        }
        if (fingerprintManager.semIsTemplateDbCorrupted()) {
            Log.e("FpstFingerprintSettings", "Finger DB is Corrupted - Remove all fingerprints.");
            this.mFingerprintManager.removeAll(
                    this.mUserId,
                    new FingerprintManager
                            .RemovalCallback() { // from class:
                                                 // com.samsung.android.settings.biometrics.fingerprint.FingerprintSettings.3
                        public final void onRemovalError(
                                Fingerprint fingerprint, int i, CharSequence charSequence) {
                            Log.e(
                                    "FpstFingerprintSettings",
                                    "onRemovalError : " + i + ", " + ((Object) charSequence));
                            FingerprintSettingsUtils.showSensorErrorDialog(
                                    FingerprintSettings.this.getActivity(),
                                    FingerprintSettings.this.getString(
                                            R.string.sec_fingerprint_error_message_sensor_error),
                                    true);
                        }

                        public final void onRemovalSucceeded(Fingerprint fingerprint, int i) {
                            Log.i(
                                    "FpstFingerprintSettings",
                                    "RemovalSucceeded of corrupted fingerprints.");
                            FingerprintSettingsUtils.showSensorErrorDialog(
                                    FingerprintSettings.this.getActivity(),
                                    FingerprintSettings.this.getString(
                                            R.string.sec_fingerprint_data_has_been_corrupted),
                                    true);
                        }
                    });
            return;
        }
        setHasOptionsMenu(true);
        LockPatternUtils lockPatternUtils = new LockPatternUtils(getActivity());
        this.mLockPatternUtils = lockPatternUtils;
        this.mGkPwProvider = new GatekeeperPasswordProvider(lockPatternUtils);
        this.mHasEnrolled = this.mFingerprintManager.hasEnrolledFingerprints(this.mUserId);
        createPreferenceHierarchy$5();
        if (z) {
            return;
        }
        if (!this.mHasEnrolled) {
            if (!this.mLockPatternUtils.isSecure(this.mUserId) || this.mGkPwHandle == 0) {
                runRegister$1("fingerprint_settings_set_screen_lock");
                return;
            } else {
                requestToken$1(true);
                return;
            }
        }
        if (this.mGkPwHandle != 0 || this.mLaunchedConfirm) {
            requestToken$1(false);
            this.mLockConfirmed = true;
            return;
        }
        Log.d("FpstFingerprintSettings", "Launch ConfirmLock");
        this.mLaunchedConfirm = true;
        mIsKeepEnrollSession = true;
        ChooseLockSettingsHelper.Builder builder =
                new ChooseLockSettingsHelper.Builder(getActivity(), this);
        builder.mRequestCode = 201;
        builder.mHeader = BiometricsGenericHelper.getConfirmLockHeader(getActivity(), this.mUserId);
        builder.mRequestGatekeeperPasswordHandle = true;
        builder.mForegroundOnly = true;
        builder.mReturnCredentials = true;
        int i = this.mUserId;
        if (i != -10000) {
            builder.mUserId = i;
        }
        if (SemPersonaManager.isKnoxId(i) && !SemPersonaManager.isSecureFolderId(this.mUserId)) {
            builder.mKnoxWorkProfileSecurity = true;
        }
        boolean show = builder.show();
        getActivity().overridePendingTransition(0, 0);
        if (show) {
            return;
        }
        Log.d("FpstFingerprintSettings", "Launch ConfirmLock - Fail");
        this.mLaunchedConfirm = false;
        mIsKeepEnrollSession = false;
        finish();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final RecyclerView.Adapter onCreateAdapter(PreferenceScreen preferenceScreen) {
        this.mPreferenceHighlighted = true;
        return super.onCreateAdapter(preferenceScreen);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        Log.d("FpstFingerprintSettings", "==onDestroy()");
        if (this.mIsRunRegister) {
            Log.d("FpstFingerprintSettings", "reset runRegister");
            this.mIsRunRegister = false;
        }
        if ("lock_screen_fingerprint_menu".equals(this.mPreviousStage)) {
            mIsKeepEnrollSession = true;
        }
        if (this.mGkPwProvider != null && !mIsKeepEnrollSession) {
            Log.d("FpstFingerprintSettings", "onDestroy: remove GatekeeperPasswordHandle");
            this.mGkPwProvider.removeGatekeeperPasswordHandle(this.mGkPwHandle);
        }
        Settings.System.putInt(getContentResolver(), "fingerprint_guide_shown", 0);
        Context context = getContext();
        int i = this.mUserId;
        boolean z = FingerprintSettingsUtils.SUB_DISPLAY_IS_LARGE_SCREEN;
        Log.d("FpstFingerprintSettingsUtils", "setFingerprintSettingsCreateValue : false");
        Settings.Secure.putIntForUser(
                context.getContentResolver(), "fingerprint_settings_create", 0, i);
        super.onDestroy();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            BiometricsGenericHelper.insertSaLog(getContext(), 8214, 8215);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        Log.d("FpstFingerprintSettings", "==onPause()");
        if (getActivity().isChangingConfigurations()) {
            return;
        }
        this.mIsBiometricsSettingsDestroy = !mIsKeepEnrollSession;
        cancelAndSessionEnd$2();
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (preference instanceof SecDropDownPreference) {
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    new StringBuilder("==onPreferenceChange : "),
                    (String) obj,
                    "FpstFingerprintSettings");
        } else {
            Log.d("FpstFingerprintSettings", "==onPreferenceChange : " + ((Boolean) obj));
        }
        String key = preference.getKey();
        r7 = false;
        boolean z = false;
        if ("support_web_signin".equals(key)) {
            getContext();
            Boolean bool = (Boolean) obj;
            LoggingHelper.insertEventLogging(
                    8214, 8225, 0L, String.valueOf(bool.booleanValue() ? 1 : 0));
            if (bool.booleanValue()) {
                Log.secD("FpstFingerprintSettings", "startDisclaimerFromWebSignin");
                if (this.mEnrolledFingerCount != 0) {
                    setFingerprintVerification(true);
                    return true;
                }
                if (checkMobileKeyboard()) {
                    showMobileKeyboardToastMsg();
                } else {
                    runRegister$1("support_web_signin");
                }
                return false;
            }
            setFingerprintVerification(bool.booleanValue());
        } else if ("support_samsung_account".equals(key)) {
            getContext();
            Boolean bool2 = (Boolean) obj;
            LoggingHelper.insertEventLogging(
                    8214, 8222, 0L, String.valueOf(bool2.booleanValue() ? 1 : 0));
            this.through_onpreferencechange = true;
            if (bool2.booleanValue()) {
                return startDisclaimerFromSamsungAccount();
            }
            try {
                setUseFingerprintForSA(this.mSamsungAccountContext, 0);
                setFingerPrintOnSAConfirmed(this.mSamsungAccountContext, 0);
                this.mSamsungAccount.setChecked(getFingerprintSamsungAccountVerification());
                Log.secD("FpstFingerprintSettings", "SAMSUNGACCOUNT value is false ");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("set_screen_lock".equals(key)) {
            if (UserManager.get(getActivity()) != null
                    && UserManager.get(getActivity()).isManagedProfile(this.mUserId)
                    && !this.mLockPatternUtils.isSeparateProfileChallengeEnabled(this.mUserId)) {
                Log.d("FpstFingerprintSettings", "launchKnoxChooseLock");
                Intent biometricsChooseLockGenericIntent =
                        BiometricsGenericHelper.getBiometricsChooseLockGenericIntent(
                                getContext(), false);
                biometricsChooseLockGenericIntent.putExtra("request_gk_pw_handle", true);
                biometricsChooseLockGenericIntent.putExtra("for_fingerprint", true);
                biometricsChooseLockGenericIntent.putExtra("hide_insecure_options", true);
                biometricsChooseLockGenericIntent.putExtra(
                        "set_biometric_lock",
                        !"fingerprint_register_external".equals(this.mPreviousStage));
                biometricsChooseLockGenericIntent.addFlags(65536);
                try {
                    startActivityForResultWrapper$1(
                            VolteConstants.ErrorCode.CANCEL_CALL_COMPLETED_ELSEWHERE,
                            biometricsChooseLockGenericIntent);
                } catch (ActivityNotFoundException unused) {
                    Log.d("FpstFingerprintSettings", "Activity Not Found !");
                }
                return false;
            }
            getContext();
            Boolean bool3 = (Boolean) obj;
            LoggingHelper.insertEventLogging(
                    8214, 8224, 0L, String.valueOf(bool3.booleanValue() ? 1 : 0));
            this.mScreenLock.setChecked(bool3.booleanValue());
            if (!bool3.booleanValue()) {
                FingerprintSettingsUtils.removeFingerprintLock(
                        this.mUserId, getActivity(), this.mLockPatternUtils);
                SwitchPreference switchPreference = this.mAlwaysOn;
                if (switchPreference != null) {
                    switchPreference.setEnabled(false);
                }
                SwitchPreference switchPreference2 = this.mStayOnLockScreen;
                if (switchPreference2 != null) {
                    switchPreference2.setEnabled(false);
                }
                if (this.mScreenOffIconAod != null) {
                    setEnableScreenOffIcon(false);
                }
                SwitchPreference switchPreference3 = this.mScreenOffIcon;
                if (switchPreference3 != null) {
                    switchPreference3.setEnabled(false);
                }
                SwitchPreference switchPreference4 = this.mFastRecognition;
                if (switchPreference4 != null) {
                    switchPreference4.setEnabled(false);
                }
                SwitchPreference switchPreference5 = this.mFingerEffect;
                if (switchPreference5 != null) {
                    switchPreference5.setEnabled(false);
                }
                if (SemPersonaManager.isDoEnabled(this.mUserId)) {
                    KnoxUtils.setTwoFactorValue(getContext(), this.mUserId, 0);
                }
            } else {
                if (!this.mLockPatternUtils.isSecure(this.mUserId)
                        || !this.mFingerprintManager.hasEnrolledFingerprints(this.mUserId)) {
                    Log.d("FpstFingerprintSettings", "Launch the FingerprintLockSettings");
                    runRegister$1("fingerprint_settings_set_screen_lock");
                    return false;
                }
                FingerprintSettingsUtils.setFingerprintLock(
                        this.mUserId, getActivity(), this.mLockPatternUtils);
                if (this.mAlwaysOn != null) {
                    getActivity();
                    this.mAlwaysOn.setEnabled(true);
                }
                SwitchPreference switchPreference6 = this.mStayOnLockScreen;
                if (switchPreference6 != null) {
                    switchPreference6.setEnabled(true);
                }
                if (this.mScreenOffIconAod != null) {
                    if (this.mIsSupportFpAlwaysOn) {
                        setEnableScreenOffIcon(
                                FingerprintSettingsUtils.getFingerprintAlwaysOnBooleanValue(
                                        getActivity(), this.mUserId));
                    } else {
                        setEnableScreenOffIcon(true);
                    }
                }
                SwitchPreference switchPreference7 = this.mScreenOffIcon;
                if (switchPreference7 != null) {
                    if (!this.mIsSupportFpAlwaysOn || this.mAlwaysOn == null) {
                        switchPreference7.setEnabled(true);
                    } else {
                        if (FingerprintSettingsUtils.getFingerprintAlwaysOnBooleanValue(
                                        getActivity(), this.mUserId)
                                && this.mAlwaysOn.isEnabled()) {
                            z = true;
                        }
                        switchPreference7.setEnabled(z);
                    }
                }
                SecPreferenceScreen secPreferenceScreen = this.mShowFingerprintIcon;
                if (secPreferenceScreen != null) {
                    secPreferenceScreen.setEnabled(true);
                }
                SwitchPreference switchPreference8 = this.mFastRecognition;
                if (switchPreference8 != null) {
                    switchPreference8.setEnabled(true);
                }
                SwitchPreference switchPreference9 = this.mFingerEffect;
                if (switchPreference9 != null) {
                    switchPreference9.setEnabled(true);
                }
            }
        } else if ("set_always_on".equals(key)) {
            Boolean bool4 = (Boolean) obj;
            this.mAlwaysOn.setChecked(bool4.booleanValue());
            FingerprintSettingsUtils.setFingerprintAlwaysOnValue(
                    getActivity(), this.mUserId, bool4.booleanValue());
            getContext();
            LoggingHelper.insertEventLogging(
                    8214, 8263, 0L, String.valueOf(bool4.booleanValue() ? 1 : 0));
            if (this.mScreenOffIconAod != null) {
                setEnableScreenOffIcon(bool4.booleanValue());
            } else {
                SwitchPreference switchPreference10 = this.mScreenOffIcon;
                if (switchPreference10 != null) {
                    switchPreference10.setEnabled(bool4.booleanValue());
                }
            }
        } else if ("stay_on_lock_screen".equals(key)) {
            Boolean bool5 = (Boolean) obj;
            this.mStayOnLockScreen.setChecked(bool5.booleanValue());
            FingerprintSettingsUtils.setStayOnLockScreen(
                    getActivity(), this.mUserId, bool5.booleanValue());
            getContext();
            LoggingHelper.insertEventLogging(
                    8214, 8291, 0L, String.valueOf(bool5.booleanValue() ? 1 : 0));
        } else if ("screen_off_icon_aod".equals(key)) {
            int parseInt = Integer.parseInt((String) obj);
            Log.d("FpstFingerprintSettings", "mScreenOffIconAod :" + parseInt);
            int i = 2 - parseInt;
            this.mScreenOffIconAod.setValueIndex(i);
            this.mScreenOffIconAod.setSummary(mScreenOffIconOptionSummaryString[parseInt]);
            FingerprintSettingsUtils.setFingerprintScreenOffIconAodValue(
                    getActivity(), parseInt, this.mUserId);
            getContext();
            LoggingHelper.insertEventLogging(8214, 8276, 0L, String.valueOf(i));
        } else if ("screen_off_icon".equals(key)) {
            Boolean bool6 = (Boolean) obj;
            this.mScreenOffIcon.setChecked(bool6.booleanValue());
            getActivity();
            boolean z2 = FingerprintSettingsUtils.SUB_DISPLAY_IS_LARGE_SCREEN;
            Log.d(
                    "FpstFingerprintSettingsUtils",
                    "setFingerprintScreenOffIconValue: not supported device");
            getContext();
            LoggingHelper.insertEventLogging(
                    8214, 8265, 0L, String.valueOf(bool6.booleanValue() ? 1 : 0));
        } else if ("fast_recognition".equals(key)) {
            Boolean bool7 = (Boolean) obj;
            this.mFastRecognition.setChecked(bool7.booleanValue());
            getActivity();
            FingerprintSettingsUtils.isSupportFasterRecognition();
            Log.d(
                    "FpstFingerprintSettingsUtils",
                    "setFingerprintFastRecognition: not ultrasonic display fingerprint sensor"
                        + " device");
            getContext();
            LoggingHelper.insertEventLogging(
                    8214, 8266, 0L, String.valueOf(bool7.booleanValue() ? 0 : 3));
        } else if ("fingerprint_effect".equals(key)) {
            Boolean bool8 = (Boolean) obj;
            this.mFingerEffect.setChecked(bool8.booleanValue());
            FingerprintSettingsUtils.setFingerprintEffectValue(
                    getActivity(), this.mUserId, bool8.booleanValue());
            getContext();
            LoggingHelper.insertEventLogging(
                    8214, 8280, 0L, String.valueOf(bool8.booleanValue() ? 1 : 0));
        } else {
            Log.v("FpstFingerprintSettings", "Unknown key:" + key);
        }
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        Log.secD("FpstFingerprintSettings", "==onPreferenceTreeClick : " + preference);
        if ((preference instanceof SwitchPreference)
                || (preference instanceof FingerprintSwitchPreference)
                || (preference instanceof BiometricsRestrictedSwitchPreference)
                || (preference instanceof SecDropDownPreference)) {
            Log.d(
                    "FpstFingerprintSettings",
                    "onPreferenceTreeClick : SwitchPreference tree clicked!");
        } else {
            if (mIsPreferenceClicked) {
                Log.d(
                        "FpstFingerprintSettings",
                        "onPreferenceTreeClick : Other preferece already clicked");
                return false;
            }
            mIsPreferenceClicked = true;
        }
        String key = preference.getKey();
        if (preference instanceof FingerprintPreference) {
            BiometricsGenericHelper.insertSaLog(getContext(), 8214, 8218);
            BiometricsGenericHelper.insertSaLog(getContext(), 8219);
            int biometricId = ((FingerprintPreference) preference).mFingerprint.getBiometricId();
            if (this.mEnrolledFingerCount == 1) {
                biometricId = ((Fingerprint) this.items.get(0)).getBiometricId();
            }
            Log.d("FpstFingerprintSettings", "startRenameFingerprint : fId[" + biometricId + "]");
            Bundle bundle = new Bundle();
            bundle.putInt("selected_id", biometricId);
            bundle.putBoolean("isAfw", this.mIsAfw);
            bundle.putInt("android.intent.extra.USER_ID", this.mUserId);
            Intent intent = new Intent();
            intent.putExtra("selected_id", biometricId);
            intent.putExtra("android.intent.extra.USER_ID", this.mUserId);
            intent.setClassName(
                    KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                    FingerprintSettingsEdit.class.getName());
            startActivityForResultWrapper$1(
                    EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_FAILURE, intent);
            return super.onPreferenceTreeClick(preference);
        }
        key.getClass();
        switch (key) {
            case "support_samsung_account":
                if (!this.through_onpreferencechange) {
                    return startDisclaimerFromSamsungAccount();
                }
                this.through_onpreferencechange = false;
                return true;
            case "key_fingerprint_about_fingerprints":
                BiometricsGenericHelper.insertSaLog(getContext(), 8214, 8272);
                Log.d("FpstFingerprintSettings", "startBiometricsDisclaimer");
                Intent intent2 = new Intent();
                intent2.setClassName(
                        getActivity().getPackageName(),
                        "com.samsung.android.settings.biometrics.BiometricsDisclaimerSettingsActivity");
                intent2.putExtra("android.intent.extra.USER_ID", this.mUserId);
                intent2.putExtra("BIOMETRICS_LOCK_TYPE", 1);
                intent2.putExtra("fromSettingsOption", true);
                try {
                    startActivityForResultWrapper$1(
                            EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS,
                            intent2);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    Log.d(
                            "FpstFingerprintSettings",
                            "startBiometricsDisclaimer : Activity Not Found !");
                }
                return true;
            case "check_added_fingerprints":
                Log.d(
                        "FpstFingerprintSettings",
                        "FingerprintSettingsUtils.KEY_CHECK_ADDED_FINGERPRINTS clicked");
                BiometricsGenericHelper.insertSaLog(getContext(), 8214, 8264);
                FragmentActivity activity = getActivity();
                if (activity != null) {}
                if (!this.mFingerprintManager.hasEnrolledFingerprints(this.mUserId)
                        || getActivity() == null) {
                    Log.secD("FpstFingerprintSettings", "registerAuthenticate : Skip");
                } else {
                    this.mFingerprintCancel = new CancellationSignal();
                    BiometricPrompt.Builder builder = new BiometricPrompt.Builder(getActivity());
                    this.mPromptBuilder = builder;
                    builder.setTitle(getText(R.string.sec_fingerprint_check_added_fingerprints));
                    this.mPromptBuilder.setDescription(
                            getText(R.string.fingerprintplus_select_msg));
                    this.mPromptBuilder.semSetBiometricType(1);
                    this.mPromptBuilder.semSetPrivilegedFlag(1);
                    this.mPromptBuilder.setNegativeButton(
                            getString(R.string.sec_biometrics_common_done),
                            getActivity().getMainExecutor(),
                            new DialogInterface
                                    .OnClickListener() { // from class:
                                                         // com.samsung.android.settings.biometrics.fingerprint.FingerprintSettings$$ExternalSyntheticLambda1
                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(DialogInterface dialogInterface, int i) {
                                    FingerprintSettings.this.stopFingerprintAuth();
                                    FingerprintSettings.mIsPreferenceClicked = false;
                                }
                            });
                    BiometricPrompt build = this.mPromptBuilder.build();
                    boolean z = FingerprintSettingsUtils.SUB_DISPLAY_IS_LARGE_SCREEN;
                    build.authenticateUser(
                            this.mFingerprintCancel,
                            getActivity().getMainExecutor(),
                            this.mAuthenticationCallback,
                            this.mUserId);
                }
                return true;
            case "set_always_on_type":
                mIsKeepEnrollSession = true;
                Bundle bundle2 = new Bundle();
                bundle2.putInt("android.intent.extra.USER_ID", this.mUserId);
                SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getContext());
                String name = FingerprintAlwaysOnSettings.class.getName();
                SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
                launchRequest.mDestinationName = name;
                subSettingLauncher.setResultListener(
                        this, EnterpriseContainerCallback.CONTAINER_MOUNT_STATUS);
                launchRequest.mArguments = bundle2;
                launchRequest.mSourceMetricsCategory = 8285;
                subSettingLauncher.launch();
                return super.onPreferenceTreeClick(preference);
            case "key_fingerprint_icon_and_animation_style":
                mIsKeepEnrollSession = true;
                return super.onPreferenceTreeClick(preference);
            case "show_fingerprint_icon":
                mIsKeepEnrollSession = true;
                Bundle bundle3 = new Bundle();
                bundle3.putInt("android.intent.extra.USER_ID", this.mUserId);
                SubSettingLauncher subSettingLauncher2 = new SubSettingLauncher(getContext());
                String name2 = FingerprintShowIconSettings.class.getName();
                SubSettingLauncher.LaunchRequest launchRequest2 =
                        subSettingLauncher2.mLaunchRequest;
                launchRequest2.mDestinationName = name2;
                subSettingLauncher2.setResultListener(
                        this, EnterpriseContainerCallback.CONTAINER_PACKAGE_INFORMATION);
                launchRequest2.mArguments = bundle3;
                launchRequest2.mSourceMetricsCategory = 8288;
                subSettingLauncher2.launch();
                return super.onPreferenceTreeClick(preference);
            case "key_fingerprint_add":
                stopFingerprintAuth();
                runRegister$1("FingerprintSettings_register");
                BiometricsGenericHelper.insertSaLog(getContext(), 8214, 8220);
                return true;
            default:
                return super.onPreferenceTreeClick(preference);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        boolean z;
        boolean z2;
        int i;
        ApplicationInfo applicationInfo;
        Bundle bundle;
        Intent launchIntentForPackage;
        super.onResume();
        Log.d("FpstFingerprintSettings", "==onResume()");
        if (!this.mLaunchedConfirm && !this.mIsRunRegister) {
            mIsKeepEnrollSession = false;
        }
        if (getActivity() != null
                && ((Rune.isSamsungDexMode(getActivity())
                                || LockUtils.isInMultiWindow(getActivity()))
                        && !BiometricsGenericHelper.isEnabledBiometricsMenuInDexMode(
                                getActivity()))) {
            Log.d("FpstFingerprintSettings", "DesktopMode or MultiWindowMode.");
            if (isRemoving()) {
                return;
            }
            finish();
            return;
        }
        createPreferenceHierarchy$5();
        getActivity();
        if (Rune.supportRelativeLink() && !this.mIsAfw && !this.mIsKnox) {
            if (this.mRelativeLinkView == null) {
                this.mRelativeLinkView = new SecRelativeLinkView(getActivity());
                if (SecurityUtils.isEnabledSamsungPass(getActivity())
                        && (launchIntentForPackage =
                                        getPackageManager()
                                                .getLaunchIntentForPackage(
                                                        "com.samsung.android.samsungpass"))
                                != null) {
                    SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData =
                            new SettingsPreferenceFragmentLinkData();
                    settingsPreferenceFragmentLinkData.flowId = "8221";
                    settingsPreferenceFragmentLinkData.callerMetric = 8214;
                    settingsPreferenceFragmentLinkData.intent = launchIntentForPackage;
                    settingsPreferenceFragmentLinkData.titleRes = R.string.iris_use_samsung_pass;
                    if (UserHandle.myUserId() == 0
                            || SemPersonaManager.isSecureFolderId(this.mUserId)) {
                        this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData);
                    }
                }
                FragmentActivity activity = getActivity();
                boolean z3 = FingerprintSettingsUtils.SUB_DISPLAY_IS_LARGE_SCREEN;
                if (activity == null) {
                    Log.secD(
                            "FpstFingerprintSettingsUtils",
                            "isFingerprintSupportSamsungPay : context is null");
                } else {
                    if (Utils.hasPackage(activity, "com.samsung.android.spay")) {
                        PackageManager packageManager = activity.getPackageManager();
                        if (packageManager != null) {
                            try {
                                applicationInfo =
                                        packageManager.getApplicationInfo(
                                                "com.samsung.android.spay", 128);
                            } catch (PackageManager.NameNotFoundException e) {
                                Log.secE(
                                        "FpstFingerprintSettingsUtils",
                                        "Failed to get information of SamsungPay, NameNotFound : "
                                                + e.getMessage());
                            }
                            if (applicationInfo != null
                                    && (bundle = applicationInfo.metaData) != null) {
                                z = !bundle.getBoolean("com.samsung.android.spay.is_stub", false);
                                z2 = true;
                            }
                        }
                        z = false;
                        z2 = true;
                    } else {
                        z = false;
                        z2 = false;
                    }
                    Utils$$ExternalSyntheticOutline0.m653m(
                            "isFingerprintSupportSamsungPay MeataData : ",
                            z,
                            ", Package : ",
                            z2,
                            "FpstFingerprintSettingsUtils");
                    if (z && z2) {
                        Intent intent = new Intent();
                        intent.setClassName(
                                "com.samsung.android.spay",
                                "com.samsung.android.spay.ui.SpayMainActivity");
                        intent.setFlags(268435456);
                        SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData2 =
                                new SettingsPreferenceFragmentLinkData();
                        settingsPreferenceFragmentLinkData2.flowId = "8223";
                        settingsPreferenceFragmentLinkData2.callerMetric = 8214;
                        settingsPreferenceFragmentLinkData2.intent = intent;
                        if (Integer.parseInt(
                                        getContentResolver()
                                                .call(
                                                        Uri.parse(
                                                                "content://com.samsung.android.spay.common.share/global"),
                                                        "GET_global",
                                                        "samsungwallet_open_quickaccess",
                                                        (Bundle) null)
                                                .getString("wallet_app_status"))
                                >= 1) {
                            i = R.string.sec_fingerprint_use_samsungwallet;
                            settingsPreferenceFragmentLinkData2.titleRes = i;
                            this.mRelativeLinkView.pushLinkData(
                                    settingsPreferenceFragmentLinkData2);
                        }
                        i = R.string.iris_use_samsung_pay;
                        settingsPreferenceFragmentLinkData2.titleRes = i;
                        this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData2);
                    }
                }
            }
            this.mRelativeLinkView.create(this);
        }
        if (this.mEmptyView == null) {
            this.mEmptyView = new View(getContext());
        }
        BiometricsGenericHelper.hideMenuList(
                getActivity(), this.mEmptyView, true ^ this.mLockConfirmed);
        if (this.mNeedFmmPopup) {
            this.mNeedFmmPopup = false;
            Log.d("FpstFingerprintSettings", "startFmmBackupPasswordPopup");
            Intent fmmPopupIntent = BiometricsGenericHelper.getFmmPopupIntent();
            fmmPopupIntent.addFlags(65536);
            try {
                startActivityForResultWrapper$1(2000, fmmPopupIntent);
            } catch (Exception e2) {
                Log.e("FpstFingerprintSettings", "Exception occured!");
                e2.printStackTrace();
            }
        }
        mIsPreferenceClicked = false;
        highlightPreferenceIfNeeded(false);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putByteArray("hw_auth_token", this.mToken);
        bundle.putLong("challenge", this.mChallenge);
        bundle.putLong("gk_pw_handle", this.mGkPwHandle);
        bundle.putBoolean("is_change_configuration", !getActivity().isChangingConfigurations());
        bundle.putBoolean("waitingForLockConfirm", this.mLaunchedConfirm);
    }

    public final void requestToken$1(final boolean z) {
        FingerprintManager fingerprintManager = this.mFingerprintManager;
        if (fingerprintManager == null || this.mGkPwHandle == 0 || this.mToken != null) {
            return;
        }
        fingerprintManager.generateChallenge(
                this.mUserId,
                new FingerprintManager
                        .GenerateChallengeCallback() { // from class:
                                                       // com.samsung.android.settings.biometrics.fingerprint.FingerprintSettings$$ExternalSyntheticLambda0
                    public final void onChallengeGenerated(int i, int i2, long j) {
                        FingerprintSettings fingerprintSettings = FingerprintSettings.this;
                        boolean z2 = z;
                        fingerprintSettings.mChallenge = j;
                        GatekeeperPasswordProvider gatekeeperPasswordProvider =
                                fingerprintSettings.mGkPwProvider;
                        if (gatekeeperPasswordProvider != null) {
                            fingerprintSettings.mToken =
                                    gatekeeperPasswordProvider.requestGatekeeperHat(
                                            fingerprintSettings.mGkPwHandle,
                                            j,
                                            fingerprintSettings.mUserId);
                        }
                        if (fingerprintSettings.mToken == null) {
                            Log.e("FpstFingerprintSettings", "token is NULL!");
                            FingerprintSettingsUtils.showSensorErrorDialog(
                                    (Activity) fingerprintSettings.getContext(),
                                    fingerprintSettings.getString(
                                            R.string.sec_biometrics_error_timed_out),
                                    true);
                        } else if (z2) {
                            fingerprintSettings.runRegister$1(
                                    "fingerprint_settings_set_screen_lock");
                        }
                    }
                });
    }

    public final void runRegister$1(String str) {
        if (checkMobileKeyboard()) {
            showMobileKeyboardToastMsg();
        } else if (this.mIsInMultiWindowMode) {
            Toast.makeText(
                            getActivity(),
                            Utils.isDesktopStandaloneMode(getActivity())
                                    ? R.string.sec_biometrics_fullscreen_register_finger
                                    : R.string.sec_fingerprint_doesnt_support_multi_window_text,
                            0)
                    .show();
        } else {
            FragmentActivity activity = getActivity();
            if (activity != null) {}
            Intent intent = new Intent().setClass(getContext(), FingerprintLockSettings.class);
            intent.putExtra("fingerIndex", -1);
            intent.putExtra("previousStage", str);
            intent.addFlags(536870912);
            intent.putExtra("gk_pw_handle", this.mGkPwHandle);
            byte[] bArr = this.mToken;
            if (bArr != null) {
                intent.putExtra("hw_auth_token", bArr);
                intent.putExtra("challenge", this.mChallenge);
            }
            if (this.mIsRunRegister) {
                ActionBarContextView$$ExternalSyntheticOutline0.m(
                        new StringBuilder("runRegister already called : "),
                        this.mIsRunRegister,
                        "FpstFingerprintSettings");
                return;
            }
            this.mIsRunRegister = true;
            ActionBarContextView$$ExternalSyntheticOutline0.m(
                    new StringBuilder("runRegister called : "),
                    this.mIsRunRegister,
                    "FpstFingerprintSettings");
            try {
                if (this.mEnrolledFingerCount > 0) {
                    startActivityForResultWrapper$1(1001, intent);
                    return;
                } else {
                    startActivityForResultWrapper$1(1000, intent);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.d("FpstFingerprintSettings", "runRegister : reset mIsPreferenceClicked value");
        mIsPreferenceClicked = false;
    }

    public final void setEnableScreenOffIcon(boolean z) {
        if (this.mScreenOffIconAod == null) {
            return;
        }
        PowerManager powerManager = (PowerManager) getActivity().getSystemService("power");
        if (powerManager != null && powerManager.isPowerSaveMode()) {
            FragmentActivity activity = getActivity();
            Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
            String string =
                    Settings.Global.getString(
                            activity.getContentResolver(), "psm_always_on_display_mode");
            if (string != null ? "1".equals(string.split(",")[0]) : true) {
                this.mScreenOffIconAod.setEnabled(false);
                this.mScreenOffIconAod.setSummary(
                        R.string.sec_biometircs_cannot_change_setting_by_powersaving_mode);
                SecDropDownPreference secDropDownPreference = this.mScreenOffIconAod;
                secDropDownPreference.getClass();
                SecPreferenceUtils.applySummaryColor(secDropDownPreference, false);
                return;
            }
        }
        this.mScreenOffIconAod.setEnabled(z);
    }

    public final void setFingerprintVerification(boolean z) {
        if (SecurityUtils.isEnabledSamsungPass(getActivity())) {
            return;
        }
        Settings.Secure.putInt(getContentResolver(), "fingerprint_webpass", z ? 1 : 0);
        Log.secD("FpstFingerprintSettings", "setFingerprintVerification: " + z);
    }

    public final void showMobileKeyboardToastMsg() {
        Toast.makeText(
                        getActivity(),
                        getString(
                                R.string.sec_fingerprint_keyboard_toast_msg,
                                getString(R.string.sec_fingerprint)),
                        0)
                .show();
    }

    public final void startActivityForResultWrapper$1(int i, Intent intent) {
        Log.d("FpstFingerprintSettings", "startActivityForResultWrapper:" + intent);
        intent.putExtra("isAfw", this.mIsAfw);
        intent.putExtra("is_knox", this.mIsKnox);
        intent.putExtra("android.intent.extra.USER_ID", this.mUserId);
        try {
            startActivityForResult(intent, i);
            mIsKeepEnrollSession = true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(
                    "FpstFingerprintSettings",
                    "startActivityForResultWrapper : reset mIsPreferenceClicked value");
            mIsPreferenceClicked = false;
        }
    }

    public final boolean startDisclaimerFromSamsungAccount() {
        Log.secD("FpstFingerprintSettings", "startDisclaimerFromSamsungAccount");
        if (this.mEnrolledFingerCount == 0) {
            if (checkMobileKeyboard()) {
                showMobileKeyboardToastMsg();
                return false;
            }
            runRegister$1("support_samsung_account");
            return false;
        }
        if (BiometricsConfig.isSamsungAccountSignedIn(this.mSamsungAccountContext)) {
            Log.secD("FpstFingerprintSettings", "SamsungAccountSignedIn is TRUE.");
            Intent intent = new Intent("com.msc.action.samsungaccount.CONFIRM_PASSWORD_POPUP");
            intent.setPackage("com.osp.app.signin");
            intent.putExtra("client_id", "s5d189ajvs");
            intent.putExtra("client_secret", "E8781246E4A0F6E9E213178CC003DE6A");
            intent.putExtra("Is_From_SA_Verify", true);
            startActivityForResultWrapper$1(101, intent);
        } else {
            Log.secD("FpstFingerprintSettings", "SamsungAccountSignedIn is TRUE.");
            Intent intent2 = new Intent("com.osp.app.signin.action.ADD_SAMSUNG_ACCOUNT");
            intent2.setPackage("com.osp.app.signin");
            intent2.putExtra("mypackage", this.mSamsungAccountContext.getPackageName());
            intent2.putExtra("OSP_VER", "OSP_02");
            intent2.putExtra("client_id", "s5d189ajvs");
            intent2.putExtra("MODE", "ADD_ACCOUNT");
            intent2.putExtra("Is_From_SA_Verify", true);
            startActivityForResultWrapper$1(102, intent2);
        }
        return true;
    }

    public final void stopFingerprintAuth() {
        CancellationSignal cancellationSignal = this.mFingerprintCancel;
        if (cancellationSignal == null || cancellationSignal.isCanceled()) {
            return;
        }
        Log.secD("FpstFingerprintSettings", "stopFingerprintAuth cancel()");
        this.mFingerprintCancel.cancel();
        this.mFingerprintCancel = null;
    }
}
