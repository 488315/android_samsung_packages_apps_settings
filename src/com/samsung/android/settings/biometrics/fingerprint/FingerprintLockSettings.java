package com.samsung.android.settings.biometrics.fingerprint;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.biometrics.GatekeeperPasswordProvider;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.notification.RedactionInterstitial;
import com.android.settings.password.ChooseLockSettingsHelper;

import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.biometrics.BiometricsGenericHelper;
import com.samsung.android.settings.biometrics.BiometricsSetScreenLockActivity;
import com.samsung.android.settings.biometrics.SuwBiometricsSetScreenLockActivity;
import com.samsung.android.settings.lockscreen.LockUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.security.SecurityUtils;
import com.samsung.android.settings.theftprotection.TheftProtectionUtils;
import com.sec.ims.configuration.DATA;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FingerprintLockSettings extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public FingerprintLockSettings mContext;
    public String mDisplayKnoxName;
    public FingerprintManager mFingerprintManager;
    public GatekeeperPasswordProvider mGkPwProvider;
    public Intent mIntent;
    public boolean mIsFromBiometricsMenu;
    public boolean mIsFromFragment;
    public boolean mIsFromKnoxSetCases;
    public boolean mIsFromKnoxSetupWizard;
    public boolean mIsFromKnoxTwoStep;
    public LockPatternUtils mLockPatternUtils;
    public String mPreviousStage;
    public int mSelectedFingerIndex;
    public LockscreenCredential mUnificationProfileCredential;
    public boolean mOnlyIdentifyFingerprint = false;
    public boolean mKnoxIdentifyOnlyFingerprint = false;
    public boolean mIsSecured = false;
    public boolean mIsLockTypeSet = false;
    public boolean mFromSetupwizard = false;
    public boolean mIsFromKnoxFingerprintPlus = false;
    public boolean mIsOneLock = false;
    public boolean mIsAfw = false;
    public String key = ApnSettings.MVNO_NONE;
    public boolean mHasEnrolledFingerprint = false;
    public boolean mMaxEnrolledFingerprint = false;
    public boolean mIsBiometricsSettingsDestroy = false;
    public boolean mIsFromTip = false;
    public boolean mSkipMandatoryBiometrics = false;
    public final Intent result_intent = new Intent();
    public int mUserId = 0;
    public int mKeepSessionAndActivity = 0;
    public int mUnificationProfileId = -10000;
    public byte[] mToken = null;
    public long mChallenge = 0;
    public long mGkPwHandle = 0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.biometrics.fingerprint.FingerprintLockSettings$2, reason: invalid class name */
    public final class AnonymousClass2 implements DialogInterface.OnClickListener {
        public final /* synthetic */ int $r8$classId;

        public /* synthetic */ AnonymousClass2(int i) {
            this.$r8$classId = i;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            switch (this.$r8$classId) {
                case 0:
                    Log.d("FpstFingerprintLockSettings", "showDatabaseFailureDialog");
                    break;
                default:
                    Log.d("FpstFingerprintLockSettings", "showSensorErrorDialog");
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.biometrics.fingerprint.FingerprintLockSettings$3, reason: invalid class name */
    public final class AnonymousClass3 implements DialogInterface.OnDismissListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ FingerprintLockSettings this$0;

        public /* synthetic */ AnonymousClass3(
                FingerprintLockSettings fingerprintLockSettings, int i) {
            this.$r8$classId = i;
            this.this$0 = fingerprintLockSettings;
        }

        @Override // android.content.DialogInterface.OnDismissListener
        public final void onDismiss(DialogInterface dialogInterface) {
            switch (this.$r8$classId) {
                case 0:
                    FingerprintLockSettings fingerprintLockSettings = this.this$0;
                    fingerprintLockSettings.setResult(0, fingerprintLockSettings.result_intent);
                    if (this.this$0.fromFingerprintSettings(false)) {
                        this.this$0.mKeepSessionAndActivity = 1;
                    }
                    this.this$0.closeSessionAndFinish();
                    break;
                default:
                    FingerprintLockSettings fingerprintLockSettings2 = this.this$0;
                    fingerprintLockSettings2.setResult(0, fingerprintLockSettings2.result_intent);
                    if (this.this$0.fromFingerprintSettings(false)) {
                        this.this$0.mKeepSessionAndActivity = 1;
                    }
                    this.this$0.closeSessionAndFinish();
                    break;
            }
        }
    }

    public final void closeSessionAndFinish() {
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("closeSessionAndFinish : "),
                this.mKeepSessionAndActivity,
                "FpstFingerprintLockSettings");
        if ((this.mKeepSessionAndActivity & 1) != 0) {
            Log.d(
                    "FpstFingerprintLockSettings",
                    "closeSessionAndFinish : Keep the Fingerprint session!");
        } else {
            if (this.mGkPwProvider != null
                    && "fingerprint_register_external".equals(this.mPreviousStage)) {
                Log.d(
                        "FpstFingerprintLockSettings",
                        "closeSessionAndFinish : remove GatekeeperPasswordHandle");
                this.mGkPwProvider.removeGatekeeperPasswordHandle(this.mGkPwHandle);
            }
            if (this.mFromSetupwizard
                    || "fingerprint_register_external".equals(this.mPreviousStage)) {
                if (this.mFingerprintManager != null) {
                    Log.d("FpstFingerprintLockSettings", "closeSessionAndFinish : revokeChallenge");
                    this.mFingerprintManager.revokeChallenge(this.mUserId, this.mChallenge);
                }
                Settings.System.putInt(getContentResolver(), "fingerprint_guide_shown", 0);
            }
        }
        if ((this.mKeepSessionAndActivity & 16) != 0) {
            Log.d("FpstFingerprintLockSettings", "closeSessionAndFinish : Keep the activity!");
            return;
        }
        Log.d("FpstFingerprintLockSettings", "closeSessionAndFinish : finish activity");
        if (!this.mIsLockTypeSet) {
            Log.d("FpstFingerprintLockSettings", "Skip - startRedactionInterstitial");
        } else if ("lock_screen_fingerprint".equals(this.mPreviousStage)) {
            Log.d("FpstFingerprintLockSettings", "startRedactionInterstitial");
            Intent createStartIntent =
                    RedactionInterstitial.createStartIntent(this.mContext, this.mUserId);
            if (createStartIntent != null) {
                createStartIntent.putExtra("fromSetupWizard", this.mFromSetupwizard);
                startActivity(createStartIntent);
            }
        } else if (BiometricsGenericHelper.needFmmBackupPasswordPopup(this.mContext)
                && !"google_setupwizard_fingerprint".equals(this.mPreviousStage)
                && !"fingerprint_entry".equals(this.mPreviousStage)) {
            Log.d("FpstFingerprintLockSettings", "startFmmBackupPasswordPopup");
            try {
                startActivity(BiometricsGenericHelper.getFmmPopupIntent());
            } catch (Exception e) {
                Log.e("FpstFingerprintLockSettings", "Exception occured!");
                e.printStackTrace();
            }
        }
        finish();
    }

    public final boolean fromFingerprintSettings(boolean z) {
        boolean z2 = z && "fingerprint_entry".equals(this.mPreviousStage);
        if ("FingerprintSettings_register".equals(this.mPreviousStage)
                || "fingerprint_settings_set_screen_lock".equals(this.mPreviousStage)
                || "support_web_signin".equals(this.mPreviousStage)
                || "support_samsung_account".equals(this.mPreviousStage)) {
            return true;
        }
        return z2;
    }

    public final void launchChooseLock() {
        Log.d("FpstFingerprintLockSettings", "launchChooseLock");
        if (LockUtils.isLockMenuDisabledByEdm(this.mContext)) {
            FingerprintLockSettings fingerprintLockSettings = this.mContext;
            Toast.makeText(
                            fingerprintLockSettings,
                            fingerprintLockSettings.getString(
                                    R.string.prevent_to_change_by_device_policy,
                                    fingerprintLockSettings.getString(
                                            R.string.sec_biometrics_chooselock_title)),
                            0)
                    .show();
            this.mKeepSessionAndActivity = 0;
            closeSessionAndFinish();
            return;
        }
        Intent biometricsChooseLockGenericIntent =
                BiometricsGenericHelper.getBiometricsChooseLockGenericIntent(
                        this, this.mFromSetupwizard);
        biometricsChooseLockGenericIntent.putExtra("request_gk_pw_handle", true);
        biometricsChooseLockGenericIntent.putExtra("for_fingerprint", true);
        biometricsChooseLockGenericIntent.putExtra("hide_insecure_options", true);
        biometricsChooseLockGenericIntent.putExtra("fromSetupWizard", this.mFromSetupwizard);
        biometricsChooseLockGenericIntent.putExtra(
                "set_biometric_lock", !"fingerprint_register_external".equals(this.mPreviousStage));
        biometricsChooseLockGenericIntent.addFlags(65536);
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.semSetPopOverOptions(null, null, null, null);
        int i = this.mUserId;
        if (i != -10000) {
            biometricsChooseLockGenericIntent.putExtra("android.intent.extra.USER_ID", i);
        }
        if (this.mUnificationProfileId != -10000) {
            Bundle bundle = new Bundle();
            bundle.putInt("unification_profile_id", this.mUnificationProfileId);
            bundle.putParcelable(
                    "unification_profile_credential", this.mUnificationProfileCredential);
            biometricsChooseLockGenericIntent.putExtra(":settings:show_fragment_args", bundle);
        }
        try {
            this.mKeepSessionAndActivity = 17;
            startActivityForResult(biometricsChooseLockGenericIntent, 1002, makeBasic.toBundle());
        } catch (ActivityNotFoundException unused) {
            Log.d("FpstFingerprintLockSettings", "Activity Not Found !");
            setResult(0, this.result_intent);
            this.mKeepSessionAndActivity = 0;
            closeSessionAndFinish();
        }
    }

    public final void launchConfirmLock() {
        Log.d("FpstFingerprintLockSettings", "launchConfirmLock");
        this.mKeepSessionAndActivity = 17;
        ChooseLockSettingsHelper.Builder builder = new ChooseLockSettingsHelper.Builder(this);
        builder.mRequestCode = VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI;
        builder.mHeader = BiometricsGenericHelper.getConfirmLockHeader(this, this.mUserId);
        builder.mRequestGatekeeperPasswordHandle = true;
        builder.mForegroundOnly = true;
        builder.mReturnCredentials = true;
        int i = this.mUserId;
        if (i != -10000) {
            builder.mUserId = i;
        }
        if (builder.show()) {
            return;
        }
        Log.w("FpstFingerprintLockSettings", "Fail launchConfirmationActivity!");
        setResult(0, this.result_intent);
        this.mKeepSessionAndActivity = 0;
        closeSessionAndFinish();
    }

    @Override // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                        "onActivityResult : requestCode : ",
                        " resultCode : ",
                        i,
                        i2,
                        " data is NULL : "),
                intent == null,
                "FpstFingerprintLockSettings");
        this.mKeepSessionAndActivity = 0;
        if (intent != null) {
            Log.d(
                    "FpstFingerprintLockSettings",
                    "intent is not null! Copy the GkPwHandle to result_intent");
            if (GatekeeperPasswordProvider.containsGatekeeperPasswordHandle(intent)) {
                long longExtra = intent.getLongExtra("gk_pw_handle", 0L);
                this.mGkPwHandle = longExtra;
                this.result_intent.putExtra("gk_pw_handle", longExtra);
            }
            boolean booleanExtra = intent.getBooleanExtra("biometrics_settings_destroy", false);
            this.mIsBiometricsSettingsDestroy = booleanExtra;
            this.result_intent.putExtra("biometrics_settings_destroy", booleanExtra);
            ActionBarContextView$$ExternalSyntheticOutline0.m(
                    new StringBuilder("onActivityResult: BiometricsSettings should be destroyed ="),
                    this.mIsBiometricsSettingsDestroy,
                    "FpstFingerprintLockSettings");
            if (this.mIsFromKnoxSetCases && this.mIsOneLock) {
                this.mIsOneLock =
                        SemPersonaManager.isKnoxId(this.mUserId)
                                && !this.mLockPatternUtils.isSeparateProfileChallengeEnabled(
                                        this.mUserId);
            }
        }
        if (i == 900) {
            if (i2 != 1) {
                Log.d("FpstFingerprintLockSettings", "KNOXNOTICE_REQUEST_CODE got RESULT_FAILED");
                setResult(0, this.result_intent);
                closeSessionAndFinish();
            } else {
                if (this.mFingerprintManager.hasEnrolledFingerprints(this.mUserId)) {
                    Log.d("FpstFingerprintLockSettings", "recognizeFingerprint");
                } else {
                    runRegister();
                }
                Log.d("FpstFingerprintLockSettings", "KNOXNOTICE_REQUEST_CODE got RESULT_FINISHED");
                return;
            }
        }
        if (i == 1020) {
            if (i2 != -1) {
                setResult(i2, this.result_intent);
                closeSessionAndFinish();
                return;
            }
            overridePendingTransition(R.anim.sud_slide_next_in, R.anim.sud_slide_next_out);
            if (this.mGkPwHandle == 0) {
                Log.w(
                        "FpstFingerprintLockSettings",
                        "CHOOSE_LOCK_GENERIC_REQUEST : GK_PW_Handle is 0.");
                launchConfirmLock();
                return;
            }
            if (!"knox_fingerprint_entry".equals(this.mPreviousStage)) {
                Log.d(
                        "FpstFingerprintLockSettings",
                        "There is no fingerprint. Start fingerprint registration.");
                startBiometricsDisclaimer();
                return;
            } else {
                if (!this.mFingerprintManager.hasEnrolledFingerprints(this.mUserId)) {
                    runRegisterForKnox();
                    return;
                }
                Log.d("FpstFingerprintLockSettings", "Fingerprint already registered for KNOX!");
                FingerprintSettingsUtils.setFingerprintLock(
                        this.mUserId, this.mContext, this.mLockPatternUtils);
                setResult(-1, this.result_intent);
                closeSessionAndFinish();
                return;
            }
        }
        if (i == 1030) {
            if (i2 != -1) {
                closeSessionAndFinish();
                return;
            }
            if (!BiometricsGenericHelper.isMandatoryBiometricsAccessible(this.mContext)) {
                closeSessionAndFinish();
                return;
            } else if (this.mGkPwHandle == 0) {
                launchConfirmLock();
                return;
            } else {
                requestToken(false);
                return;
            }
        }
        if (i == 1004) {
            if (i2 != -1 || this.mMaxEnrolledFingerprint) {
                if (fromFingerprintSettings(false)) {
                    this.mKeepSessionAndActivity = 1;
                }
                closeSessionAndFinish();
                return;
            }
            if (this.mGkPwHandle == 0 && this.mToken == null) {
                Log.w("FpstFingerprintLockSettings", "CONFIRM_REQUEST : GK_PW_Handle is 0.");
                launchConfirmLock();
                return;
            } else if (!this.mHasEnrolledFingerprint) {
                requestToken(false);
                startBiometricsDisclaimer();
                return;
            } else if (this.mIsFromKnoxSetCases || !this.mIsOneLock) {
                requestToken(true);
                return;
            } else {
                Log.d("FpstFingerprintLockSettings", "OneLock case : Launch chooseLockGeneric");
                launchChooseLock();
                return;
            }
        }
        if (i == 1005) {
            if (i2 != -1) {
                Log.d("FpstFingerprintLockSettings", "onActivityResult: Finish the activity!");
                if (this.mFromSetupwizard) {
                    overridePendingTransition(R.anim.sud_slide_back_in, R.anim.sud_slide_back_out);
                }
                if (i2 == 11) {
                    setResult(i2, this.result_intent);
                } else {
                    setResult(0, this.result_intent);
                }
                if (fromFingerprintSettings(false)) {
                    this.mKeepSessionAndActivity = 1;
                }
                closeSessionAndFinish();
                return;
            }
            if (this.mUnificationProfileId != -10000) {
                Log.d("FpstFingerprintLockSettings", "OneLock case : Launch chooseLockGeneric");
                launchChooseLock();
                return;
            }
            overridePendingTransition(R.anim.sud_slide_next_in, R.anim.sud_slide_next_out);
            if (!this.mIsOneLock) {
                runRegister();
                return;
            } else {
                Log.d("FpstFingerprintLockSettings", "OneLock case : Launch chooseLockGeneric");
                launchChooseLock();
                return;
            }
        }
        switch (i) {
            case 1000:
            case 1001:
                if (i2 != -1) {
                    if (i2 != 3) {
                        if (i2 == 1
                                && "google_setupwizard_fingerprint".equals(this.mPreviousStage)) {
                            Log.d(
                                    "FpstFingerprintLockSettings",
                                    "Register fingerprint is closed. Restart runRegister!");
                            runRegister();
                            break;
                        } else {
                            Log.d(
                                    "FpstFingerprintLockSettings",
                                    "Fingerprint Registration failed!");
                            setResult(0, this.result_intent);
                            if (fromFingerprintSettings(false)) {
                                this.mKeepSessionAndActivity = 1;
                            }
                        }
                    } else {
                        this.result_intent.putExtra("biometrics_settings_destroy", true);
                        setResult(0, this.result_intent);
                        closeSessionAndFinish();
                        break;
                    }
                } else {
                    FingerprintLockSettings fingerprintLockSettings = this.mContext;
                    int i3 = this.mUserId;
                    boolean z = FingerprintSettingsUtils.SUB_DISPLAY_IS_LARGE_SCREEN;
                    Settings.Secure.putIntForUser(
                            fingerprintLockSettings.getContentResolver(),
                            "fingerprint_on_screen_tips",
                            1,
                            i3);
                    Log.d("FpstFingerprintSettingsUtils", "setFingerprintOnScreenTip: true");
                    FingerprintSettingsUtils.isSupportFingerprintAlwaysOn();
                    int intForUser =
                            Settings.Secure.getIntForUser(
                                    this.mContext.getContentResolver(),
                                    "fingerprint_always_on",
                                    -1,
                                    this.mUserId);
                    ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                            intForUser,
                            "getFingerprintAlwaysOnDbValue : ",
                            "FpstFingerprintSettingsUtils");
                    if (intForUser == -1) {
                        Log.d(
                                "FpstFingerprintLockSettings",
                                "onActivityResult :  alwaysOn value not set");
                        FingerprintSettingsUtils.setFingerprintAlwaysOnValue(
                                this.mContext, this.mUserId, true);
                    }
                    FingerprintSettingsUtils.isSupportFingerprintAlwaysOnType();
                    if (FingerprintSettingsUtils.isSupportFingerprintScreenOffIconAod()
                            && FingerprintSettingsUtils.getFingerprintScreenOffIconAodDbValue(
                                            this.mContext, this.mUserId)
                                    == -1) {
                        Log.d(
                                "FpstFingerprintLockSettings",
                                "onActivityResult :  screenOff icon for aod value not set");
                        FingerprintSettingsUtils.setFingerprintScreenOffIconAodValue(
                                this.mContext, 2, this.mUserId);
                    }
                    FingerprintSettingsUtils.isSupportFasterRecognition();
                    this.result_intent.putExtra("previousStage", this.mPreviousStage);
                    if (this.mIsFromKnoxSetCases && !this.mKnoxIdentifyOnlyFingerprint) {
                        if (!this.mFingerprintManager.hasEnrolledFingerprints(this.mUserId)) {
                            setResult(0, this.result_intent);
                        } else if (!this.mOnlyIdentifyFingerprint) {
                            setResult(-1, this.result_intent);
                        }
                        if (fromFingerprintSettings(true)) {
                            this.mKeepSessionAndActivity = 1;
                        }
                        finish();
                    } else if (this.mIsFromKnoxTwoStep || this.mIsFromKnoxSetupWizard) {
                        if (this.mFingerprintManager.hasEnrolledFingerprints(this.mUserId)) {
                            setResult(-1, this.result_intent);
                        } else {
                            setResult(0, this.result_intent);
                        }
                        finish();
                    } else if (!this.mKnoxIdentifyOnlyFingerprint) {
                        Log.d(
                                "FpstFingerprintLockSettings",
                                "processFingerprintRegistrationResult");
                        this.result_intent.putExtra("fingerIndex", this.mSelectedFingerIndex);
                        setResult(-1, this.result_intent);
                        if (!SecurityUtils.isFingerprintDisabled(this.mContext, this.mUserId)
                                && !this.mHasEnrolledFingerprint
                                && !"fingerprint_register_external".equals(this.mPreviousStage)) {
                            FingerprintSettingsUtils.setFingerprintLock(
                                    this.mUserId, this.mContext, this.mLockPatternUtils);
                        }
                        boolean z2 =
                                !this.mHasEnrolledFingerprint
                                        && ("google_setupwizard_fingerprint"
                                                        .equals(this.mPreviousStage)
                                                || "lock_screen_fingerprint"
                                                        .equals(this.mPreviousStage)
                                                || "knox_fingerprint_entry"
                                                        .equals(this.mPreviousStage)
                                                || "fingerprint_register_external"
                                                        .equals(this.mPreviousStage));
                        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                                "needFingerprintUsefulFeature : ",
                                "FpstFingerprintLockSettings",
                                z2);
                        if (!z2 || SemPersonaManager.isKnoxId(this.mUserId)) {
                            if (fromFingerprintSettings(true)) {
                                this.mKeepSessionAndActivity = 1;
                            }
                            if ("fingerprint_entry".equals(this.mPreviousStage)) {
                                FingerprintLockSettings fingerprintLockSettings2 = this.mContext;
                                Log.d("FpstFingerprintLockSettings", "startFingerprintSettings");
                                if (this.mIsBiometricsSettingsDestroy) {
                                    Log.d(
                                            "FpstFingerprintLockSettings",
                                            "mIsBiometricsSettingsDestroy is set!");
                                    this.mKeepSessionAndActivity = 0;
                                    closeSessionAndFinish();
                                } else if (this.mFingerprintManager.semIsTemplateDbCorrupted()) {
                                    Log.e("FpstFingerprintLockSettings", "onCreate DB Corrupt");
                                    this.mFingerprintManager.removeAll(
                                            this.mUserId,
                                            new FingerprintManager
                                                    .RemovalCallback() { // from class:
                                                                         // com.samsung.android.settings.biometrics.fingerprint.FingerprintLockSettings.1
                                                public final void onRemovalError(
                                                        Fingerprint fingerprint,
                                                        int i4,
                                                        CharSequence charSequence) {
                                                    Log.e(
                                                            "FpstFingerprintLockSettings",
                                                            "onRemovalError : "
                                                                    + i4
                                                                    + ", "
                                                                    + ((Object) charSequence));
                                                    FingerprintLockSettings
                                                            fingerprintLockSettings3 =
                                                                    FingerprintLockSettings.this;
                                                    FingerprintSettingsUtils.showSensorErrorDialog(
                                                            fingerprintLockSettings3.mContext,
                                                            fingerprintLockSettings3.getString(
                                                                    R.string
                                                                            .sec_fingerprint_error_message_sensor_error),
                                                            true);
                                                }

                                                public final void onRemovalSucceeded(
                                                        Fingerprint fingerprint, int i4) {
                                                    int i5 = 0;
                                                    Log.i(
                                                            "FpstFingerprintLockSettings",
                                                            "onRemovalSucceeded");
                                                    FingerprintLockSettings
                                                            fingerprintLockSettings3 =
                                                                    FingerprintLockSettings.this;
                                                    int i6 = FingerprintLockSettings.$r8$clinit;
                                                    fingerprintLockSettings3.getClass();
                                                    AlertDialog create =
                                                            new AlertDialog.Builder(
                                                                            fingerprintLockSettings3
                                                                                    .mContext)
                                                                    .setTitle(
                                                                            R.string
                                                                                    .sec_fingerprint_attention)
                                                                    .setMessage(
                                                                            R.string
                                                                                    .sec_fingerprint_data_has_been_corrupted)
                                                                    .setPositiveButton(
                                                                            android.R.string.ok,
                                                                            new AnonymousClass2(i5))
                                                                    .create();
                                                    create.setOnDismissListener(
                                                            new AnonymousClass3(
                                                                    fingerprintLockSettings3, i5));
                                                    create.show();
                                                }
                                            });
                                } else if (fingerprintLockSettings2 == null) {
                                    Log.d(
                                            "FpstFingerprintLockSettings",
                                            "startFragment : context is null");
                                } else {
                                    Bundle bundle = new Bundle();
                                    bundle.putString(":settings:fragment_args_key", this.key);
                                    bundle.putLong("gk_pw_handle", this.mGkPwHandle);
                                    bundle.putBoolean("isAfw", this.mIsAfw);
                                    bundle.putInt("android.intent.extra.USER_ID", this.mUserId);
                                    bundle.putBoolean(
                                            "fingerprint_registered_from_fingerprint",
                                            !this.mHasEnrolledFingerprint);
                                    byte[] bArr = this.mToken;
                                    if (bArr != null) {
                                        bundle.putByteArray("hw_auth_token", bArr);
                                        bundle.putLong("challenge", this.mChallenge);
                                    }
                                    this.mKeepSessionAndActivity = 1;
                                    if (this.mIsSecured
                                            || "lock_screen_fingerprint"
                                                    .equalsIgnoreCase(this.mPreviousStage)
                                            || !BiometricsGenericHelper.needFmmBackupPasswordPopup(
                                                    this.mContext)) {
                                        bundle.putBoolean("need_fmm_popup", false);
                                    } else {
                                        bundle.putBoolean("need_fmm_popup", true);
                                    }
                                    if (this.mIsFromFragment) {
                                        Intent intent2 = new Intent();
                                        intent2.putExtra(":settings:show_fragment_args", bundle);
                                        setResult(-1, intent2);
                                        finish();
                                    } else {
                                        SubSettingLauncher subSettingLauncher =
                                                new SubSettingLauncher(fingerprintLockSettings2);
                                        String name = FingerprintSettings.class.getName();
                                        SubSettingLauncher.LaunchRequest launchRequest =
                                                subSettingLauncher.mLaunchRequest;
                                        launchRequest.mDestinationName = name;
                                        launchRequest.mArguments = bundle;
                                        launchRequest.mSourceMetricsCategory = 9031;
                                        subSettingLauncher.setTitleRes(
                                                R.string.bio_fingerprint_sanner_title, null);
                                        subSettingLauncher.launch();
                                    }
                                }
                            }
                        } else {
                            Log.d(
                                    "FpstFingerprintLockSettings",
                                    "Run startFingerprintUsefulFeature");
                            Intent intent3 = new Intent();
                            intent3.setClassName(
                                    getPackageName(),
                                    this.mFromSetupwizard
                                            ? "com.samsung.android.settings.biometrics.fingerprint.SuwFingerprintUsefulFeature"
                                            : "com.samsung.android.settings.biometrics.fingerprint.FingerprintUsefulFeature");
                            intent3.putExtra("android.intent.extra.USER_ID", this.mUserId);
                            intent3.putExtra("fromSetupWizard", this.mFromSetupwizard);
                            intent3.putExtra("gk_pw_handle", this.mGkPwHandle);
                            intent3.putExtra("previousStage", this.mPreviousStage);
                            intent3.addFlags(65536);
                            try {
                                this.mKeepSessionAndActivity = 17;
                                startActivityForResult(
                                        intent3,
                                        EnterpriseContainerCallback
                                                .CONTAINER_PACKAGE_UNINSTALL_FAILURE);
                            } catch (ActivityNotFoundException unused) {
                                Log.d(
                                        "FpstFingerprintLockSettings",
                                        "startFingerprintUsefulFeature : Activity Not Found !");
                                if (fromFingerprintSettings(true)) {
                                    this.mKeepSessionAndActivity = 1;
                                }
                                closeSessionAndFinish();
                            }
                        }
                    } else if (this.mFingerprintManager.hasEnrolledFingerprints(this.mUserId)) {
                        setResult(-1, this.result_intent);
                    } else {
                        setResult(0, this.result_intent);
                    }
                }
                closeSessionAndFinish();
                break;
            case 1002:
                if (i2 != 1) {
                    setResult(i2, this.result_intent);
                    closeSessionAndFinish();
                    break;
                } else {
                    requestToken(false);
                    this.mIsLockTypeSet = true;
                    this.mKeepSessionAndActivity = 17;
                    Intent intent4 =
                            this.mFromSetupwizard
                                    ? new Intent(
                                            this,
                                            (Class<?>) SuwBiometricsSetScreenLockActivity.class)
                                    : new Intent(
                                            this, (Class<?>) BiometricsSetScreenLockActivity.class);
                    intent4.putExtra("BIOMETRICS_LOCK_TYPE", 1);
                    overridePendingTransition(0, 0);
                    startActivityForResult(intent4, 1020);
                    break;
                }
            default:
                switch (i) {
                    case 1007:
                        if (this.mFingerprintManager.hasEnrolledFingerprints(this.mUserId)) {
                            setResult(-1, this.result_intent);
                        } else {
                            setResult(0, this.result_intent);
                        }
                        finish();
                        break;
                    case EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS /* 1008 */:
                        if (i2 == -1) {
                            if (intent != null) {
                                DialogFragment$$ExternalSyntheticOutline0.m(
                                        "previousStage : ",
                                        intent.getStringExtra("previousStage"),
                                        "FpstFingerprintLockSettings");
                            }
                            StringBuilder sb = new StringBuilder("mIsFromKnoxSetCases : ");
                            sb.append(this.mIsFromKnoxSetCases);
                            sb.append(", mKnoxIdentifyOnlyFingerprint : ");
                            sb.append(this.mKnoxIdentifyOnlyFingerprint);
                            sb.append(", mIsFromKnoxTwoStep : ");
                            sb.append(this.mIsFromKnoxTwoStep);
                            sb.append(", mIsFromKnoxSetupWizard : ");
                            ActionBarContextView$$ExternalSyntheticOutline0.m(
                                    sb, this.mIsFromKnoxSetupWizard, "FpstFingerprintLockSettings");
                            if (this.mIsFromKnoxSetCases && !this.mKnoxIdentifyOnlyFingerprint) {
                                if (!this.mFingerprintManager.hasEnrolledFingerprints(
                                        this.mUserId)) {
                                    setResult(0, this.result_intent);
                                } else if (!this.mOnlyIdentifyFingerprint) {
                                    setResult(-1, this.result_intent);
                                    if (!SecurityUtils.isFingerprintDisabled(
                                            this.mContext, this.mUserId)) {
                                        FingerprintSettingsUtils.setFingerprintLock(
                                                this.mUserId,
                                                this.mContext,
                                                this.mLockPatternUtils);
                                    }
                                }
                                if (fromFingerprintSettings(true)) {
                                    this.mKeepSessionAndActivity = 1;
                                }
                                finish();
                            } else if (this.mIsFromKnoxTwoStep || this.mIsFromKnoxSetupWizard) {
                                if (this.mFingerprintManager.hasEnrolledFingerprints(
                                        this.mUserId)) {
                                    setResult(-1, this.result_intent);
                                } else {
                                    setResult(0, this.result_intent);
                                }
                                finish();
                            } else if (!this.mKnoxIdentifyOnlyFingerprint) {
                                this.result_intent.putExtra(
                                        "fingerIndex", this.mSelectedFingerIndex);
                                setResult(-1, this.result_intent);
                            } else if (this.mFingerprintManager.hasEnrolledFingerprints(
                                    this.mUserId)) {
                                setResult(-1, this.result_intent);
                            } else {
                                setResult(0, this.result_intent);
                            }
                        } else {
                            Log.d(
                                    "FpstFingerprintLockSettings",
                                    "Fingerprint Registration failed!");
                            setResult(0, this.result_intent);
                            if (fromFingerprintSettings(false)) {
                                this.mKeepSessionAndActivity = 1;
                            }
                        }
                        closeSessionAndFinish();
                        break;
                    case EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_FAILURE /* 1009 */:
                        closeSessionAndFinish();
                        break;
                }
        }
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("onConfigurationChanged : "),
                configuration.orientation,
                "FpstFingerprintLockSettings");
        super.onConfigurationChanged(configuration);
        Utils.applyLandscapeFullScreen(this, getWindow());
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        char c;
        super.onCreate(bundle);
        Log.d("FpstFingerprintLockSettings", "onCreate");
        this.mContext = this;
        Log.d("FpstFingerprintLockSettings", "initFingerprintLockSettings");
        Intent intent = getIntent();
        this.mIntent = intent;
        if (intent != null) {
            String stringExtra = intent.getStringExtra("previousStage");
            this.mPreviousStage = stringExtra;
            if (stringExtra == null) {
                Log.e("FpstFingerprintLockSettings", "mPreviousStage is null.");
            } else {
                this.mLockPatternUtils = new LockPatternUtils(this.mContext);
                FingerprintManager fingerprintManager =
                        (FingerprintManager) getSystemService("fingerprint");
                this.mFingerprintManager = fingerprintManager;
                if (fingerprintManager == null) {
                    Log.e("FpstFingerprintLockSettings", "FingerprintManager is null");
                    showSensorErrorDialog();
                } else {
                    int intExtra =
                            this.mIntent.getIntExtra(
                                    "android.intent.extra.USER_ID", UserHandle.myUserId());
                    this.mUserId = intExtra;
                    if (intExtra != -10000) {
                        this.mIsFromFragment =
                                this.mIntent.getBooleanExtra("from_biometric_fragment", false);
                        this.mOnlyIdentifyFingerprint =
                                this.mIntent.getBooleanExtra("onlyIdentify", false);
                        this.mIsAfw = this.mIntent.getBooleanExtra("isAfw", false);
                        this.mSelectedFingerIndex = this.mIntent.getIntExtra("fingerIndex", -1);
                        if (this.mIntent.hasExtra(":settings:fragment_args_key")) {
                            this.key = this.mIntent.getStringExtra(":settings:fragment_args_key");
                        }
                        this.mToken = this.mIntent.getByteArrayExtra("hw_auth_token");
                        this.mChallenge = this.mIntent.getLongExtra("challenge", 0L);
                        this.mKnoxIdentifyOnlyFingerprint =
                                this.mIntent.getBooleanExtra("mKnoxIdentifyOnly", false);
                        this.mGkPwProvider = new GatekeeperPasswordProvider(this.mLockPatternUtils);
                        long gatekeeperPasswordHandle =
                                GatekeeperPasswordProvider.getGatekeeperPasswordHandle(
                                        this.mIntent);
                        this.mGkPwHandle = gatekeeperPasswordHandle;
                        this.result_intent.putExtra("gk_pw_handle", gatekeeperPasswordHandle);
                        this.mIsSecured = this.mLockPatternUtils.isSecure(this.mUserId);
                        this.mFromSetupwizard =
                                this.mIntent.getBooleanExtra("fromSetupWizard", false);
                        if ("google_setupwizard_fingerprint".equals(this.mPreviousStage)) {
                            this.mFromSetupwizard = true;
                        }
                        this.mIsFromTip = this.mIntent.getBooleanExtra("fromTip", false);
                        this.mIsFromBiometricsMenu =
                                this.mIntent.getBooleanExtra("fromBiometricsMenu", false);
                        this.mHasEnrolledFingerprint =
                                this.mFingerprintManager.hasEnrolledFingerprints(this.mUserId);
                        this.mMaxEnrolledFingerprint =
                                FingerprintSettingsUtils.getEnrolledFingerNumber(
                                                this.mFingerprintManager, this.mUserId)
                                        == FingerprintSettingsUtils.getMaxFingerEnroll(
                                                this.mFingerprintManager);
                        this.mSkipMandatoryBiometrics =
                                this.mIntent.getBooleanExtra("skip_mandatory_biometrics", false);
                        this.mIsFromKnoxFingerprintPlus =
                                this.mIntent.getBooleanExtra("isFromKnoxFingerprintPlus", false);
                        this.mIsFromKnoxSetCases = this.mIntent.getBooleanExtra("is_knox", false);
                        this.mIsFromKnoxTwoStep =
                                this.mIntent.getBooleanExtra("is_knox_two_step", false);
                        this.mIsFromKnoxSetupWizard =
                                this.mIntent.getBooleanExtra("isFromKnoxSetupWizard", false);
                        this.mIsOneLock =
                                SemPersonaManager.isKnoxId(this.mUserId)
                                        && !this.mLockPatternUtils
                                                .isSeparateProfileChallengeEnabled(this.mUserId);
                        this.mDisplayKnoxName = this.mIntent.getStringExtra("displayKnoxName");
                        StringBuilder sb =
                                new StringBuilder(
                                        "[KNOX FINGERPRINT] : init, mIsFromKnoxSetupWizard:");
                        sb.append(this.mIsFromKnoxSetupWizard);
                        sb.append(", mDisplayKnoxName:");
                        sb.append(this.mDisplayKnoxName);
                        sb.append(", mIsFromKnoxSetCases:");
                        sb.append(this.mIsFromKnoxSetCases);
                        sb.append(", mIsFromKnoxTwoStep:");
                        StringBuilder m =
                                MainClearConfirm$$ExternalSyntheticOutline0.m(
                                        sb,
                                        this.mIsFromKnoxTwoStep,
                                        "FpstFingerprintLockSettings",
                                        "mUserId : ");
                        m.append(this.mUserId);
                        Log.d("FpstFingerprintLockSettings", m.toString());
                        Log.d(
                                "FpstFingerprintLockSettings",
                                "[previousStage] = " + this.mPreviousStage);
                        Preference$$ExternalSyntheticOutline0.m(
                                MainClearConfirm$$ExternalSyntheticOutline0.m(
                                        MainClearConfirm$$ExternalSyntheticOutline0.m(
                                                MainClearConfirm$$ExternalSyntheticOutline0.m(
                                                        MainClearConfirm$$ExternalSyntheticOutline0
                                                                .m(
                                                                        new StringBuilder(
                                                                                "[isSecured] = "),
                                                                        this.mIsSecured,
                                                                        "FpstFingerprintLockSettings",
                                                                        "[fromSetupwizard] = "),
                                                        this.mFromSetupwizard,
                                                        "FpstFingerprintLockSettings",
                                                        "[hasEnrolledFingerprint] = "),
                                                this.mHasEnrolledFingerprint,
                                                "FpstFingerprintLockSettings",
                                                "[maxEnrolledFingerprint] = "),
                                        this.mMaxEnrolledFingerprint,
                                        "FpstFingerprintLockSettings",
                                        "[mSelectedFingerIndex]"),
                                this.mSelectedFingerIndex,
                                "FpstFingerprintLockSettings");
                        this.mUnificationProfileId =
                                this.mIntent.getIntExtra("unification_profile_id", -10000);
                        this.mUnificationProfileCredential =
                                this.mIntent.getParcelableExtra("unification_profile_credential");
                        if (this.mSelectedFingerIndex == 0) {
                            if (fromFingerprintSettings(false)) {
                                this.mKeepSessionAndActivity = 1;
                            }
                            closeSessionAndFinish();
                        }
                        if (new FingerprintSettingsPreferenceController(this.mContext)
                                        .getAvailabilityStatus()
                                == 3) {
                            Toast.makeText(
                                            this.mContext,
                                            getString(R.string.sec_fingerprint_unable_to_register),
                                            0)
                                    .show();
                            Log.d("FpstFingerprintLockSettings", "Fingerprints is not available.");
                            finish();
                            return;
                        }
                        if (Settings.Global.getInt(
                                        getContentResolver(), "always_finish_activities", 0)
                                != 0) {
                            FingerprintSettingsUtils.showSensorErrorDialog(
                                    this,
                                    getString(R.string.sec_fingerprint_attention),
                                    getResources()
                                            .getString(
                                                    R.string
                                                            .sec_fingerprint_error_message_always_finish_activities,
                                                    getString(
                                                            R.string
                                                                    .immediately_destroy_activities)),
                                    true);
                            return;
                        }
                        int windowingMode =
                                getResources()
                                        .getConfiguration()
                                        .windowConfiguration
                                        .getWindowingMode();
                        if (windowingMode == 5 || windowingMode == 6) {
                            Toast.makeText(
                                            this,
                                            BiometricsGenericHelper
                                                            .isEnabledBiometricsMenuInDexMode(this)
                                                    ? R.string
                                                            .sec_biometrics_fullscreen_register_finger
                                                    : R.string
                                                            .sec_biometrics_common_not_use_multi_window_view,
                                            0)
                                    .show();
                            closeSessionAndFinish();
                            return;
                        }
                        if (BiometricsGenericHelper.isPopupWindowMode()) {
                            Toast.makeText(this, R.string.sec_fingerprint_block_popup_view, 0)
                                    .show();
                            closeSessionAndFinish();
                            return;
                        }
                        if (SecurityUtils.isNotAvailableBiometricsWithDexAndMultiWindow(
                                this,
                                R.string.bio_fingerprint_sanner_title,
                                "FpstFingerprintLockSettings")) {
                            closeSessionAndFinish();
                            return;
                        }
                        if (TheftProtectionUtils.isMandatoryBiometricEnabled(this.mContext)
                                && !"FingerprintSettings_register".equals(this.mPreviousStage)
                                && !this.mSkipMandatoryBiometrics) {
                            FingerprintManager fingerprintManager2 = this.mFingerprintManager;
                            String str = this.mPreviousStage;
                            int i = this.mUserId;
                            if (("fingerprint_register_external".equals(str)
                                            || "google_setupwizard_fingerprint".equals(str))
                                    && FingerprintSettingsUtils.getEnrolledFingerNumber(
                                                    fingerprintManager2, i)
                                            == FingerprintSettingsUtils.getMaxFingerEnroll(
                                                    fingerprintManager2)) {
                                Log.d(
                                        "FpstFingerprintLockSettings",
                                        "external register request, already fingerprint fully"
                                            + " registered");
                                closeSessionAndFinish();
                                return;
                            } else {
                                this.mKeepSessionAndActivity = 17;
                                startActivityForResult(
                                        BiometricsGenericHelper
                                                .getMandatoryBiometricIntentForBiometricsSetting(
                                                        this.mContext,
                                                        1,
                                                        "fingerprint_register_external"
                                                                .equals(this.mPreviousStage)),
                                        1030);
                                return;
                            }
                        }
                        String str2 = this.mPreviousStage;
                        str2.getClass();
                        switch (str2.hashCode()) {
                            case -2008961416:
                                if (str2.equals("support_samsung_account")) {
                                    c = 0;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1893897509:
                                if (str2.equals("FingerprintSettings_register")) {
                                    c = 1;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1542582452:
                                if (str2.equals("fingerprint_register_external")) {
                                    c = 2;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1415898793:
                                if (str2.equals("fingerprint_entry")) {
                                    c = 3;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -7203079:
                                if (str2.equals("lock_screen_pin_pattern_password_upgragde")) {
                                    c = 4;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 409355876:
                                if (str2.equals("knox_fingerprint_entry")) {
                                    c = 5;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 711207205:
                                if (str2.equals("lock_screen_fingerprint")) {
                                    c = 6;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1255614813:
                                if (str2.equals("support_web_signin")) {
                                    c = 7;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1518911815:
                                if (str2.equals("google_setupwizard_fingerprint")) {
                                    c = '\b';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1593825184:
                                if (str2.equals("fingerprint_settings_set_screen_lock")) {
                                    c = '\t';
                                    break;
                                }
                                c = 65535;
                                break;
                            default:
                                c = 65535;
                                break;
                        }
                        switch (c) {
                            case 0:
                            case 1:
                            case 7:
                            case '\t':
                                Log.d("FpstFingerprintLockSettings", "FingerprintSettings");
                                if (!this.mIsSecured) {
                                    Log.d(
                                            "FpstFingerprintLockSettings",
                                            "startBiometricsDisclaimer() :mIsSecured is false");
                                    launchChooseLock();
                                    break;
                                } else if (!this.mMaxEnrolledFingerprint) {
                                    if (this.mGkPwHandle != 0) {
                                        if (!this.mHasEnrolledFingerprint) {
                                            startBiometricsDisclaimer();
                                            break;
                                        } else {
                                            runRegister();
                                            break;
                                        }
                                    } else {
                                        Log.d(
                                                "FpstFingerprintLockSettings",
                                                "launchConfirmLock() : GkPwHandle is 0L");
                                        launchConfirmLock();
                                        break;
                                    }
                                } else {
                                    Log.e("FpstFingerprintLockSettings", "This is wrong case!");
                                    this.mKeepSessionAndActivity = 1;
                                    setResult(0);
                                    closeSessionAndFinish();
                                    break;
                                }
                            case 2:
                                if (!this.mIsSecured) {
                                    Log.d(
                                            "FpstFingerprintLockSettings",
                                            "startBiometricsDisclaimer() :mIsSecured is false");
                                    launchChooseLock();
                                    break;
                                } else if (!this.mMaxEnrolledFingerprint) {
                                    if (this.mGkPwHandle != 0) {
                                        if (!this.mHasEnrolledFingerprint) {
                                            startBiometricsDisclaimer();
                                            break;
                                        } else {
                                            runRegister();
                                            break;
                                        }
                                    } else {
                                        Log.w(
                                                "FpstFingerprintLockSettings",
                                                "Launch ConfirmLock : GkPWHandle is 0.");
                                        launchConfirmLock();
                                        break;
                                    }
                                } else {
                                    Log.e(
                                            "FpstFingerprintLockSettings",
                                            "This is wrong case! All fingerprint already enrolled");
                                    this.mKeepSessionAndActivity = 0;
                                    setResult(0);
                                    closeSessionAndFinish();
                                    break;
                                }
                            case 3:
                                Log.d("FpstFingerprintLockSettings", "FingerprintSettings - Entry");
                                if (!this.mIsSecured) {
                                    Log.d(
                                            "FpstFingerprintLockSettings",
                                            "startBiometricsDisclaimer() : no enrolled"
                                                + " fingerprint");
                                    launchChooseLock();
                                    break;
                                } else if (this.mGkPwHandle != 0) {
                                    startBiometricsDisclaimer();
                                    break;
                                } else {
                                    launchConfirmLock();
                                    break;
                                }
                            case 4:
                                Log.d(
                                        "FpstFingerprintLockSettings",
                                        "LockScreen - OS Upgrade with P/P/P");
                                if (!this.mHasEnrolledFingerprint) {
                                    Log.e(
                                            "FpstFingerprintLockSettings",
                                            "Wrong case! At this point, Fingerprint must be"
                                                + " exist!");
                                    setResult(0);
                                    closeSessionAndFinish();
                                    break;
                                } else {
                                    FingerprintSettingsUtils.setFingerprintLock(
                                            this.mUserId, this.mContext, this.mLockPatternUtils);
                                    break;
                                }
                            case 5:
                                Log.d(
                                        "FpstFingerprintLockSettings",
                                        "KEY_KNOX_FINGERPRINT_SETTINGS_ENTRY");
                                if (!this.mIsOneLock) {
                                    if (!this.mFingerprintManager.hasEnrolledFingerprints(
                                            this.mUserId)) {
                                        Log.d(
                                                "FpstFingerprintLockSettings",
                                                "There is no fingerprint. Register fingerprint!");
                                        if (!this.mIsFromKnoxSetupWizard
                                                && !this.mIsFromKnoxSetCases
                                                && (!this.mIsFromKnoxTwoStep
                                                        || this.mOnlyIdentifyFingerprint)) {
                                            if (!this.mLockPatternUtils.isSecure(this.mUserId)) {
                                                launchChooseLock();
                                                break;
                                            } else {
                                                Log.d(
                                                        "FpstFingerprintLockSettings",
                                                        "FingerprintLockSettings() : mIsSecured is"
                                                            + " TRUE");
                                                if (this.mGkPwHandle != 0) {
                                                    Log.d(
                                                            "FpstFingerprintLockSettings",
                                                            "startFingerprintRegister");
                                                    runRegister();
                                                    break;
                                                } else {
                                                    Log.w(
                                                            "FpstFingerprintLockSettings",
                                                            "KEY_KNOX_FINGERPRINT_SETTINGS_ENTRY:"
                                                                + " GK_PW_Handle is 0.");
                                                    launchConfirmLock();
                                                    break;
                                                }
                                            }
                                        } else {
                                            runRegisterForKnox();
                                            break;
                                        }
                                    } else if (!this.mLockPatternUtils.isSecure(this.mUserId)) {
                                        launchChooseLock();
                                        break;
                                    } else if (!this.mIsFromKnoxFingerprintPlus) {
                                        if (this.mIsFromKnoxTwoStep
                                                && !this.mOnlyIdentifyFingerprint) {
                                            runRegisterForKnox();
                                            break;
                                        } else if (!this.mIsFromKnoxSetupWizard
                                                && !this.mIsFromKnoxSetCases
                                                && !this.mOnlyIdentifyFingerprint) {
                                            startBiometricsDisclaimer();
                                            break;
                                        } else {
                                            Log.d(
                                                    "FpstFingerprintLockSettings",
                                                    "[KNOX FINGERPRINT] : START"
                                                        + " recognizeFingerprint() in FingerExist");
                                            Log.d(
                                                    "FpstFingerprintLockSettings",
                                                    "recognizeFingerprint");
                                            break;
                                        }
                                    } else {
                                        runRegister();
                                        break;
                                    }
                                } else {
                                    launchChooseLock();
                                    break;
                                }
                                break;
                            case 6:
                                Log.d("FpstFingerprintLockSettings", "LockScreen - Fingerprint");
                                if (this.mUnificationProfileId == -10000) {
                                    if (!this.mHasEnrolledFingerprint) {
                                        Log.d(
                                                "FpstFingerprintLockSettings",
                                                "There is no fingerprint. Register fingerprint!");
                                        if (!this.mIsSecured) {
                                            launchChooseLock();
                                            break;
                                        } else if (this.mGkPwHandle != 0) {
                                            startBiometricsDisclaimer();
                                            break;
                                        } else {
                                            Log.w(
                                                    "FpstFingerprintLockSettings",
                                                    "KEY_LOCK_SCREEN_FINGERPRINT: GK_PW_Handle is"
                                                        + " 0.");
                                            launchConfirmLock();
                                            break;
                                        }
                                    } else if (!this.mLockPatternUtils.isSecure(this.mUserId)) {
                                        launchChooseLock();
                                        break;
                                    } else if (!this.mIsFromKnoxFingerprintPlus) {
                                        if (this.mIsFromKnoxTwoStep
                                                && !this.mOnlyIdentifyFingerprint) {
                                            runRegisterForKnox();
                                            break;
                                        } else if (!this.mIsFromKnoxSetupWizard
                                                && !this.mIsFromKnoxSetCases
                                                && !this.mOnlyIdentifyFingerprint) {
                                            startBiometricsDisclaimer();
                                            break;
                                        } else {
                                            Log.d(
                                                    "FpstFingerprintLockSettings",
                                                    "[KNOX FINGERPRINT] : START"
                                                        + " recognizeFingerprint() in FingerExist");
                                            Log.d(
                                                    "FpstFingerprintLockSettings",
                                                    "recognizeFingerprint");
                                            break;
                                        }
                                    } else {
                                        runRegister();
                                        break;
                                    }
                                } else {
                                    Log.d(
                                            "FpstFingerprintLockSettings",
                                            "startBiometricsDisclaimer() : enabling one lock case");
                                    startBiometricsDisclaimer();
                                    break;
                                }
                                break;
                            case '\b':
                                Log.d(
                                        "FpstFingerprintLockSettings",
                                        "Google Setupwizard - Fingerprint");
                                if (!this.mIsSecured) {
                                    Log.d(
                                            "FpstFingerprintLockSettings",
                                            "startBiometricsDisclaimer() :mIsSecured is false");
                                    launchChooseLock();
                                    break;
                                } else if (!this.mMaxEnrolledFingerprint) {
                                    if (this.mGkPwHandle != 0) {
                                        runRegister();
                                        break;
                                    } else {
                                        Log.d(
                                                "FpstFingerprintLockSettings",
                                                "launchConfirmLock() : Token is null");
                                        launchConfirmLock();
                                        break;
                                    }
                                } else {
                                    Log.e("FpstFingerprintLockSettings", "This is wrong case!");
                                    setResult(0);
                                    closeSessionAndFinish();
                                    break;
                                }
                            default:
                                Log.e(
                                        "FpstFingerprintLockSettings",
                                        "This is wrong previousStage case! Finish the Activity");
                                setResult(0);
                                closeSessionAndFinish();
                                break;
                        }
                        return;
                    }
                    Log.e("FpstFingerprintLockSettings", "User ID is USER_NULL.");
                    showSensorErrorDialog();
                }
            }
        }
        closeSessionAndFinish();
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        Log.d("FpstFingerprintLockSettings", "onDestroy");
        super.onDestroy();
    }

    @Override // android.app.Activity
    public final void onPause() {
        Log.d("FpstFingerprintLockSettings", "onPause");
        closeSessionAndFinish();
        super.onPause();
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        Log.d("FpstFingerprintLockSettings", "onResume");
    }

    @Override // android.app.Activity
    public final void onStop() {
        Log.d("FpstFingerprintLockSettings", "onStop");
        super.onStop();
    }

    public final void requestToken(final boolean z) {
        FingerprintManager fingerprintManager = this.mFingerprintManager;
        if (fingerprintManager == null || this.mGkPwHandle == 0 || this.mToken != null) {
            return;
        }
        fingerprintManager.generateChallenge(
                this.mUserId,
                new FingerprintManager
                        .GenerateChallengeCallback() { // from class:
                                                       // com.samsung.android.settings.biometrics.fingerprint.FingerprintLockSettings$$ExternalSyntheticLambda0
                    public final void onChallengeGenerated(int i, int i2, long j) {
                        FingerprintLockSettings fingerprintLockSettings =
                                FingerprintLockSettings.this;
                        boolean z2 = z;
                        fingerprintLockSettings.mChallenge = j;
                        fingerprintLockSettings.result_intent.putExtra("challenge", j);
                        GatekeeperPasswordProvider gatekeeperPasswordProvider =
                                fingerprintLockSettings.mGkPwProvider;
                        if (gatekeeperPasswordProvider != null) {
                            fingerprintLockSettings.mToken =
                                    gatekeeperPasswordProvider.requestGatekeeperHat(
                                            fingerprintLockSettings.mGkPwHandle,
                                            fingerprintLockSettings.mChallenge,
                                            fingerprintLockSettings.mUserId);
                        }
                        byte[] bArr = fingerprintLockSettings.mToken;
                        if (bArr == null) {
                            Log.e("FpstFingerprintLockSettings", "token is NULL!");
                            String string =
                                    fingerprintLockSettings.getString(
                                            R.string.sec_biometrics_error_timed_out);
                            boolean z3 = FingerprintSettingsUtils.SUB_DISPLAY_IS_LARGE_SCREEN;
                            FingerprintSettingsUtils.showSensorErrorDialog(
                                    fingerprintLockSettings,
                                    fingerprintLockSettings.getString(
                                            R.string.sec_fingerprint_attention),
                                    string,
                                    true);
                            return;
                        }
                        fingerprintLockSettings.result_intent.putExtra("hw_auth_token", bArr);
                        if (z2) {
                            fingerprintLockSettings.runRegister();
                        }
                    }
                });
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x008b, code lost:

       if (android.provider.Settings.System.getInt(r3.getContentResolver(), "any_screen_running", 0) == 1) goto L26;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void runRegister() {
        /*
            Method dump skipped, instructions count: 345
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.biometrics.fingerprint.FingerprintLockSettings.runRegister():void");
    }

    public final void runRegisterForKnox() {
        if (this.mIsFromKnoxTwoStep
                && this.mFingerprintManager.hasEnrolledFingerprints(this.mUserId)) {
            Log.d("FpstFingerprintLockSettings", "recognizeFingerprint");
        } else {
            runRegister();
        }
    }

    public final void sendSALogging() {
        String str =
                this.mIsFromTip
                        ? this.mFromSetupwizard ? DATA.DM_FIELD_INDEX.LBO_PCSCF_ADDRESS_TYPE : "4"
                        : this.mFromSetupwizard
                                ? "1"
                                : (this.mIsFromBiometricsMenu
                                                || !"fingerprint_entry".equals(this.mPreviousStage))
                                        ? "fingerprint_register_external"
                                                        .equals(this.mPreviousStage)
                                                ? DATA.DM_FIELD_INDEX.PUBLIC_USER_ID
                                                : "lock_screen_fingerprint"
                                                                .equals(this.mPreviousStage)
                                                        ? DATA.DM_FIELD_INDEX.AMR_AUDIO_BITRATE
                                                        : "2"
                                        : DATA.DM_FIELD_INDEX.AMR_AUDIO_BITRATE_WB;
        getBaseContext();
        LoggingHelper.insertEventLogging(8255, 8279, 0L, str);
    }

    public final void showSensorErrorDialog() {
        AlertDialog create =
                new AlertDialog.Builder(
                                this.mContext, BiometricsGenericHelper.isLightTheme(this) ? 5 : 4)
                        .setTitle(R.string.sec_fingerprint_error_message_sensor_error_title)
                        .setMessage(R.string.sec_fingerprint_error_message_sensor_error)
                        .setPositiveButton(android.R.string.ok, new AnonymousClass2(1))
                        .create();
        create.setOnDismissListener(new AnonymousClass3(this, 1));
        create.show();
    }

    public final void startBiometricsDisclaimer() {
        Log.d("FpstFingerprintLockSettings", "startBiometricsDisclaimer");
        Intent className =
                new Intent()
                        .setClassName(
                                this,
                                "com.samsung.android.settings.biometrics.BiometricsDisclaimerActivity");
        className.putExtra("android.intent.extra.USER_ID", this.mUserId);
        className.putExtra("BIOMETRICS_LOCK_TYPE", 1);
        className.putExtra("gk_pw_handle", this.mGkPwHandle);
        if (this.mFromSetupwizard) {
            className.putExtra("fromSetupWizard", true);
            className.setClassName(
                    getPackageName(),
                    "com.samsung.android.settings.biometrics.SuwBiometricsDisclaimerActivity");
        } else if (this.mIsSecured && this.mGkPwHandle == 0) {
            className.setClassName(
                    getPackageName(),
                    "com.samsung.android.settings.biometrics.BiometricsDisclaimerWithConfirmLock");
        }
        try {
            this.mKeepSessionAndActivity = 17;
            startActivityForResult(className, 1005);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Log.d(
                    "FpstFingerprintLockSettings",
                    "startBiometricsDisclaimer : Activity Not Found !");
            closeSessionAndFinish();
        }
    }
}
