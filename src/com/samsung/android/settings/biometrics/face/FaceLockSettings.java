package com.samsung.android.settings.biometrics.face;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.face.FaceManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
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
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.security.SecurityUtils;
import com.samsung.android.settings.theftprotection.TheftProtectionUtils;
import com.samsung.android.settings.theftprotection.timer.ProtectionTimerService$TimeDelayState;
import com.sec.ims.configuration.DATA;
import com.sec.ims.volte2.data.VolteConstants;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FaceLockSettings extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public GatekeeperPasswordProvider mGkPwProvider;
    public boolean mIsFromFragment;
    public boolean mIsOneLock;
    public int mOrientation;
    public AlertDialog mRetryDialog;
    public LockscreenCredential mUnificationProfileCredential;
    public FaceLockSettings mContext = null;
    public LockPatternUtils mLockPatternUtils = null;
    public FaceManager mFaceManager = null;
    public String mPreviousStage = null;
    public int mUserId = 0;
    public boolean mIsSecured = false;
    public boolean mIsLockTypeSet = false;
    public final Intent result_intent = new Intent();
    public String key = ApnSettings.MVNO_NONE;
    public boolean mHasEnrolledFaces = false;
    public boolean mHasEnrolledAlternativeFaces = false;
    public int mKeepSessionAndActivity = 0;
    public boolean mFromSetupwizard = false;
    public boolean mIsBiometricsSettingsDestroy = false;
    public boolean mIsFromTip = false;
    public boolean mSkipMandatoryBiometrics = false;
    public boolean mIsFromKnoxSetCases = false;
    public int mUnificationProfileId = -10000;
    public int mSensorId = -1;
    public byte[] mToken = null;
    public long mChallenge = 0;
    public long mGkPwHandle = 0;

    public final void closeSessionAndFinish() {
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("closeSessionAndFinish : "),
                this.mKeepSessionAndActivity,
                "FcstFaceLockSettings");
        if ((this.mKeepSessionAndActivity & 1) != 0) {
            Log.d("FcstFaceLockSettings", "closeSessionAndFinish : Keep the Face session!");
        } else {
            if (this.mGkPwProvider != null
                    && "face_register_external".equals(this.mPreviousStage)) {
                Log.d(
                        "FcstFaceLockSettings",
                        "closeSessionAndFinish : remove GatekeeperPasswordHandle");
                this.mGkPwProvider.removeGatekeeperPasswordHandle(this.mGkPwHandle);
            }
            if ((this.mFromSetupwizard || "face_register_external".equals(this.mPreviousStage))
                    && this.mFaceManager != null) {
                Log.d("FcstFaceLockSettings", "closeSessionAndFinish : revokeChallenge");
                this.mFaceManager.revokeChallenge(this.mSensorId, this.mUserId, this.mChallenge);
            }
        }
        if ((this.mKeepSessionAndActivity & 16) != 0) {
            Log.d("FcstFaceLockSettings", "closeSessionAndFinish : Keep the activity!");
            return;
        }
        Log.d("FcstFaceLockSettings", "closeSessionAndFinish : finish activity");
        if (!this.mIsLockTypeSet) {
            Log.d("FcstFaceLockSettings", "Skip - startRedactionInterstitial");
        } else if ("lock_screen_face".equalsIgnoreCase(this.mPreviousStage)) {
            Log.d("FcstFaceLockSettings", "startRedactionInterstitial");
            Intent createStartIntent = RedactionInterstitial.createStartIntent(this, this.mUserId);
            if (createStartIntent != null) {
                createStartIntent.putExtra("fromSetupWizard", this.mFromSetupwizard);
                startActivity(createStartIntent);
            }
        } else if (BiometricsGenericHelper.needFmmBackupPasswordPopup(this.mContext)
                && !"setupwizard_face".equals(this.mPreviousStage)
                && !"key_face_settings_entry".equalsIgnoreCase(this.mPreviousStage)) {
            Log.d("FcstFaceLockSettings", "startFmmBackupPasswordPopup");
            try {
                startActivity(BiometricsGenericHelper.getFmmPopupIntent());
            } catch (Exception e) {
                Log.e("FcstFaceLockSettings", "Exception occured!");
                e.printStackTrace();
            }
        }
        finish();
    }

    public final boolean fromFaceSettings(boolean z) {
        boolean z2 = z && "key_face_settings_entry".equals(this.mPreviousStage);
        if ("FaceSettings_unlock_switch".equals(this.mPreviousStage)
                || "FaceSettings_register".equals(this.mPreviousStage)
                || "FaceSettings_register_alternative".equals(this.mPreviousStage)) {
            return true;
        }
        return z2;
    }

    public final void launchChooseLock() {
        Log.d("FcstFaceLockSettings", "launchChooseLock");
        if (LockUtils.isLockMenuDisabledByEdm(this.mContext)) {
            FaceLockSettings faceLockSettings = this.mContext;
            Toast.makeText(
                            faceLockSettings,
                            faceLockSettings.getString(
                                    R.string.prevent_to_change_by_device_policy,
                                    faceLockSettings.getString(
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
        biometricsChooseLockGenericIntent.putExtra("for_face", true);
        biometricsChooseLockGenericIntent.putExtra("hide_insecure_options", true);
        biometricsChooseLockGenericIntent.putExtra("fromSetupWizard", this.mFromSetupwizard);
        biometricsChooseLockGenericIntent.putExtra(
                "set_biometric_lock", !"face_register_external".equals(this.mPreviousStage));
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
            if (this.mIsOneLock) {
                Log.i("FcstFaceLockSettings", "This is Knox user, remove keep session flag");
                this.mKeepSessionAndActivity = 16;
            } else {
                this.mKeepSessionAndActivity = 17;
            }
            startActivityForResult(biometricsChooseLockGenericIntent, 1002, makeBasic.toBundle());
        } catch (ActivityNotFoundException unused) {
            Log.e("FcstFaceLockSettings", "Activity Not Found !");
            setResult(0, this.result_intent);
            this.mKeepSessionAndActivity = 0;
            closeSessionAndFinish();
        }
    }

    public final void launchConfirmLock() {
        Log.d("FcstFaceLockSettings", "launchConfirmLock");
        this.mKeepSessionAndActivity = 17;
        ChooseLockSettingsHelper.Builder builder = new ChooseLockSettingsHelper.Builder(this);
        builder.mRequestCode = 1003;
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
        Log.e("FcstFaceLockSettings", "Fail launchConfirmationActivity!");
        setResult(0, this.result_intent);
        this.mKeepSessionAndActivity = 0;
        closeSessionAndFinish();
    }

    public final boolean needFaceUsefulFeature() {
        boolean z =
                !this.mHasEnrolledFaces
                        && ("setupwizard_face".equals(this.mPreviousStage)
                                || "lock_screen_face".equals(this.mPreviousStage)
                                || "knox_lock_screen_face".equals(this.mPreviousStage)
                                || "face_register_external".equals(this.mPreviousStage));
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "needFaceUsefulFeature : ", "FcstFaceLockSettings", z);
        return z;
    }

    @Override // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        boolean z;
        super.onActivityResult(i, i2, intent);
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                        "onActivityResult : requestCode : ",
                        " resultCode : ",
                        i,
                        i2,
                        " data is NULL : "),
                intent == null,
                "FcstFaceLockSettings");
        this.mKeepSessionAndActivity = 0;
        if (intent != null) {
            Log.d(
                    "FcstFaceLockSettings",
                    "intent is not null! Copy the GkPwHandle to result_intent");
            if (GatekeeperPasswordProvider.containsGatekeeperPasswordHandle(intent)) {
                long longExtra = intent.getLongExtra("gk_pw_handle", 0L);
                this.mGkPwHandle = longExtra;
                this.result_intent.putExtra("gk_pw_handle", longExtra);
            }
            if (fromFaceSettings(true)) {
                boolean booleanExtra = intent.getBooleanExtra("biometrics_settings_destroy", false);
                this.mIsBiometricsSettingsDestroy = booleanExtra;
                this.result_intent.putExtra("biometrics_settings_destroy", booleanExtra);
                ActionBarContextView$$ExternalSyntheticOutline0.m(
                        new StringBuilder("onActivityResult: mIsBiometricsSettingsDestroy="),
                        this.mIsBiometricsSettingsDestroy,
                        "FcstFaceLockSettings");
            }
            if (this.mIsFromKnoxSetCases && this.mIsOneLock) {
                this.mIsOneLock =
                        SemPersonaManager.isKnoxId(this.mUserId)
                                && !this.mLockPatternUtils.isSeparateProfileChallengeEnabled(
                                        this.mUserId);
            }
        }
        if (i == 1020) {
            if (i2 != -1) {
                setResult(i2, this.result_intent);
                closeSessionAndFinish();
            }
            overridePendingTransition(R.anim.sud_slide_next_in, R.anim.sud_slide_next_out);
            if (this.mGkPwHandle != 0) {
                startBiometricsDisclaimer();
                return;
            } else {
                Log.w("FcstFaceLockSettings", "CHOOSE_LOCK_GENERIC_REQUEST: GK_PW_Handle is 0.");
                launchConfirmLock();
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
                startBiometricsDisclaimer();
                return;
            }
        }
        switch (i) {
            case 1002:
                if (i2 != 1) {
                    setResult(i2, this.result_intent);
                    closeSessionAndFinish();
                    break;
                } else {
                    requestToken(false);
                    this.mIsLockTypeSet = true;
                    this.mKeepSessionAndActivity = 17;
                    Intent intent2 =
                            this.mFromSetupwizard
                                    ? new Intent(
                                            this,
                                            (Class<?>) SuwBiometricsSetScreenLockActivity.class)
                                    : new Intent(
                                            this, (Class<?>) BiometricsSetScreenLockActivity.class);
                    intent2.putExtra("BIOMETRICS_LOCK_TYPE", 256);
                    overridePendingTransition(0, 0);
                    startActivityForResult(intent2, 1020);
                    break;
                }
            case 1003:
                if (i2 != -1) {
                    if (fromFaceSettings(false)) {
                        this.mKeepSessionAndActivity = 1;
                    }
                    closeSessionAndFinish();
                    break;
                } else if (this.mGkPwHandle != 0 || this.mToken != null) {
                    if (!this.mHasEnrolledFaces) {
                        requestToken(false);
                        startBiometricsDisclaimer();
                        break;
                    } else if (!FaceSettingsHelper.isSupportBioFaceAlternativeFace()
                            || this.mHasEnrolledAlternativeFaces
                            || !"FaceSettings_register_alternative".equals(this.mPreviousStage)) {
                        if (fromFaceSettings(false)) {
                            this.mKeepSessionAndActivity = 1;
                        }
                        closeSessionAndFinish();
                        break;
                    } else {
                        requestToken(true);
                        break;
                    }
                } else {
                    Log.w("FcstFaceLockSettings", "CONFIRM_REQUEST: GK_PW_Handle is 0.");
                    launchConfirmLock();
                    break;
                }
                break;
            case VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI /* 1004 */:
                if (intent != null) {
                    z = intent.getBooleanExtra("face_wear_glasses", false);
                    FaceLockSettings faceLockSettings = this.mContext;
                    int i3 = this.mUserId;
                    boolean z2 = FaceSettingsHelper.IS_SUPPORT_FACE_DAEMON_DETECT_OPEN_EYES;
                    Settings.Secure.putIntForUser(
                            faceLockSettings.getContentResolver(),
                            "face_wear_glasses",
                            z ? 1 : 0,
                            i3);
                } else {
                    z = false;
                }
                BiometricsGenericHelper.insertSaLog(this.mContext, 8416);
                LoggingHelper.insertEventLogging(
                        8416,
                        8423,
                        0L,
                        this.mIsFromTip
                                ? this.mFromSetupwizard
                                        ? DATA.DM_FIELD_INDEX.AMR_AUDIO_BITRATE
                                        : DATA.DM_FIELD_INDEX.LBO_PCSCF_ADDRESS_TYPE
                                : this.mFromSetupwizard
                                        ? "1"
                                        : "lock_screen_face".equals(this.mPreviousStage)
                                                ? DATA.DM_FIELD_INDEX.SIP_SESSION_TIMER
                                                : "face_register_external"
                                                                .equals(this.mPreviousStage)
                                                        ? "2"
                                                        : DATA.DM_FIELD_INDEX.AMR_AUDIO_BITRATE_WB);
                SALogging.insertSALog(
                        z ? 1 : 2,
                        BiometricsGenericHelper.getSaLogIdByDisplayType(this.mContext, 8426),
                        String.valueOf(8427),
                        (String) null);
                if (i2 != -5) {
                    if (i2 != -2 && i2 != -4) {
                        if (i2 != -3) {
                            if (!FaceSettingsHelper.isSupportBioFaceAlternativeFace()
                                    || !"FaceSettings_register_alternative"
                                            .equals(this.mPreviousStage)) {
                                if (i2 != -1) {
                                    setResult(0, this.result_intent);
                                    FaceSettingsHelper.removeFaceLock(
                                            this.mUserId, this.mContext, this.mLockPatternUtils);
                                    if (fromFaceSettings(false)) {
                                        this.mKeepSessionAndActivity = 1;
                                    }
                                    closeSessionAndFinish();
                                    break;
                                } else {
                                    if (FaceSettingsHelper.getFaceStayOnLockScreenDbValue(
                                                    this.mContext, this.mUserId)
                                            == -1) {
                                        FaceSettingsHelper.setFaceStayOnLockScreen(
                                                this.mContext, this.mUserId, true);
                                    }
                                    if (FaceSettingsHelper.isSupportBioFaceRecognizeWithMask(
                                                    this.mContext)
                                            && FaceSettingsHelper.getFaceRecognizeMaskDbValue(
                                                            this.mContext, this.mUserId)
                                                    == -1) {
                                        Settings.Secure.putIntForUser(
                                                this.mContext.getContentResolver(),
                                                "face_recognize_mask",
                                                0,
                                                this.mUserId);
                                    }
                                    if (FaceSettingsHelper.isSupportBioFaceOpenEyes()) {
                                        if (Settings.Secure.getIntForUser(
                                                        this.mContext.getContentResolver(),
                                                        "face_open_eyes",
                                                        -1,
                                                        this.mUserId)
                                                == -1) {
                                            if (Utils.isTalkBackEnabled(this.mContext)) {
                                                FaceSettingsHelper.setFaceOpenEyes(
                                                        this.mContext, this.mUserId, false);
                                            } else {
                                                FaceSettingsHelper.setFaceOpenEyes(
                                                        this.mContext, this.mUserId, true);
                                            }
                                        }
                                    }
                                    if (FaceSettingsHelper.getFaceBrightenScreenDbValue(
                                                    this.mContext, this.mUserId)
                                            == -1) {
                                        FaceSettingsHelper.setFaceBrightenScreen(
                                                this.mContext, this.mUserId, true);
                                    }
                                    Log.d("FcstFaceLockSettings", "processFaceRegistrationResult");
                                    setResult(-1, this.result_intent);
                                    if (!"face_register_external".equals(this.mPreviousStage)
                                            || ("face_register_external".equals(this.mPreviousStage)
                                                    && !SemPersonaManager.isKnoxId(this.mUserId)
                                                    && !SemPersonaManager.isDoEnabled(
                                                            this.mUserId))) {
                                        if (!SecurityUtils.isFaceDisabled(
                                                        this.mContext, this.mUserId)
                                                && !"face_register_external"
                                                        .equals(this.mPreviousStage)) {
                                            FaceSettingsHelper.setFaceLock(
                                                    this.mUserId,
                                                    this.mContext,
                                                    this.mLockPatternUtils);
                                        }
                                        if (needFaceUsefulFeature()
                                                && !SemPersonaManager.isKnoxId(this.mUserId)) {
                                            startFaceUsefulFeature();
                                            break;
                                        } else if ("key_face_settings_entry"
                                                .equals(this.mPreviousStage)) {
                                            startFaceSettings(this.mContext);
                                        }
                                    } else if (needFaceUsefulFeature()) {
                                        startFaceUsefulFeature();
                                        break;
                                    }
                                    if (fromFaceSettings(false)) {
                                        this.mKeepSessionAndActivity = 1;
                                    }
                                    closeSessionAndFinish();
                                    break;
                                }
                            } else {
                                setResult(-1, this.result_intent);
                                this.mKeepSessionAndActivity = 1;
                                closeSessionAndFinish();
                                break;
                            }
                        } else {
                            setResult(-3, this.result_intent);
                            closeSessionAndFinish();
                            break;
                        }
                    } else {
                        Log.d("FcstFaceLockSettings", "showFaceRetryDialog");
                        this.mKeepSessionAndActivity = 0;
                        AlertDialog alertDialog = this.mRetryDialog;
                        if (alertDialog != null) {
                            alertDialog.dismiss();
                        }
                        AlertDialog create =
                                new AlertDialog.Builder(
                                                this.mContext,
                                                BiometricsGenericHelper.isLightTheme(this) ? 5 : 4)
                                        .create();
                        this.mRetryDialog = create;
                        int i4 =
                                i2 == -4
                                        ? R.string.sec_face_error_message_retry_too_dark
                                        : R.string.sec_face_error_message_retry;
                        create.setTitle(
                                getResources().getString(R.string.sec_face_error_title_retry));
                        this.mRetryDialog.setMessage(getResources().getString(i4));
                        this.mRetryDialog.setButton(
                                -1,
                                getResources()
                                        .getString(R.string.sec_face_error_message_button_retry),
                                new DialogInterface
                                        .OnClickListener() { // from class:
                                                             // com.samsung.android.settings.biometrics.face.FaceLockSettings$$ExternalSyntheticLambda1
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i5) {
                                        FaceLockSettings faceLockSettings2 = FaceLockSettings.this;
                                        int i6 = FaceLockSettings.$r8$clinit;
                                        faceLockSettings2.getClass();
                                        ((AlertDialog) dialogInterface).setOnDismissListener(null);
                                        Log.d("FcstFaceLockSettings", "startFaceRegister");
                                        faceLockSettings2.startFaceRegister(true, true);
                                    }
                                });
                        this.mRetryDialog.setButton(
                                -2,
                                getResources().getString(R.string.common_cancel),
                                (DialogInterface.OnClickListener) null);
                        this.mRetryDialog.setOnDismissListener(
                                new DialogInterface.OnDismissListener() { // from class:
                                    // com.samsung.android.settings.biometrics.face.FaceLockSettings$$ExternalSyntheticLambda2
                                    @Override // android.content.DialogInterface.OnDismissListener
                                    public final void onDismiss(DialogInterface dialogInterface) {
                                        FaceLockSettings faceLockSettings2 = FaceLockSettings.this;
                                        int i5 = FaceLockSettings.$r8$clinit;
                                        faceLockSettings2.getClass();
                                        Log.d(
                                                "FcstFaceLockSettings",
                                                "showFaceRetryDialog dismissed! Finish the"
                                                    + " activity!");
                                        if ("FaceSettings_register"
                                                        .equalsIgnoreCase(
                                                                faceLockSettings2.mPreviousStage)
                                                || "FaceSettings_register_alternative"
                                                        .equalsIgnoreCase(
                                                                faceLockSettings2.mPreviousStage)
                                                || "FaceSettings_unlock_switch"
                                                        .equalsIgnoreCase(
                                                                faceLockSettings2.mPreviousStage)) {
                                            faceLockSettings2.mKeepSessionAndActivity |= 1;
                                        }
                                        faceLockSettings2.setResult(
                                                0, faceLockSettings2.result_intent);
                                        faceLockSettings2.closeSessionAndFinish();
                                    }
                                });
                        this.mRetryDialog.getWindow().setDimAmount(0.3f);
                        this.mRetryDialog.getWindow().addFlags(2);
                        this.mRetryDialog.show();
                        break;
                    }
                } else {
                    this.mKeepSessionAndActivity = 16;
                    startBiometricsDisclaimer();
                    break;
                }
                break;
            default:
                switch (i) {
                    case EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS /* 1008 */:
                        if (i2 != -1) {
                            if (this.mFromSetupwizard) {
                                overridePendingTransition(
                                        R.anim.sud_slide_back_in, R.anim.sud_slide_back_out);
                            }
                            if (i2 == 11) {
                                setResult(i2, this.result_intent);
                            } else {
                                setResult(0, this.result_intent);
                            }
                            if (fromFaceSettings(false)) {
                                this.mKeepSessionAndActivity = 1;
                            }
                            closeSessionAndFinish();
                            break;
                        } else if (this.mUnificationProfileId == -10000) {
                            overridePendingTransition(
                                    R.anim.sud_slide_next_in, R.anim.sud_slide_next_out);
                            LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
                            if (lockPatternUtils != null) {
                                this.mIsSecured = lockPatternUtils.isSecure(this.mUserId);
                            }
                            if (this.mIsSecured && !this.mIsOneLock) {
                                if (this.mGkPwHandle != 0) {
                                    Log.d("FcstFaceLockSettings", "startFaceRegister");
                                    startFaceRegister(false, true);
                                    break;
                                } else {
                                    launchConfirmLock();
                                    break;
                                }
                            } else {
                                launchChooseLock();
                                break;
                            }
                        } else {
                            launchChooseLock();
                            break;
                        }
                    case EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_FAILURE /* 1009 */:
                        if ("key_face_settings_entry".equals(this.mPreviousStage)) {
                            startFaceSettings(this.mContext);
                        }
                        closeSessionAndFinish();
                        break;
                    case EnterpriseContainerCallback.CONTAINER_MOUNT_STATUS /* 1010 */:
                        if (i2 != -1) {
                            setResult(0, this.result_intent);
                            if (fromFaceSettings(false)) {
                                this.mKeepSessionAndActivity = 1;
                            }
                            closeSessionAndFinish();
                            break;
                        } else {
                            startFaceRegister(false, false);
                            break;
                        }
                    case EnterpriseContainerCallback.CONTAINER_PACKAGE_INFORMATION /* 1011 */:
                        if (!needFaceUsefulFeature()) {
                            if ("key_face_settings_entry".equals(this.mPreviousStage)) {
                                startFaceSettings(this.mContext);
                            }
                            closeSessionAndFinish();
                            break;
                        } else {
                            startFaceUsefulFeature();
                            break;
                        }
                    default:
                        Log.d("FcstFaceLockSettings", "default : Not consider this case");
                        break;
                }
        }
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Log.d("FcstFaceLockSettings", "onConfigurationChanged");
        AlertDialog alertDialog = this.mRetryDialog;
        if (alertDialog != null
                && alertDialog.isShowing()
                && this.mOrientation == configuration.orientation) {
            this.mRetryDialog.dismiss();
        }
        this.mOrientation = configuration.orientation;
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        List enrolledFaces;
        super.onCreate(bundle);
        Log.d("FcstFaceLockSettings", "onCreate");
        this.mContext = this;
        if (new FaceSettingsPreferenceController(this).getAvailabilityStatus() == 3) {
            Toast.makeText(this.mContext, getString(R.string.sec_face_error_title_retry), 0).show();
            Log.d("FcstFaceLockSettings", "Face recognition is not available.");
            finish();
            return;
        }
        if (!BiometricsGenericHelper.isAvailableMemorySizeToEnroll()) {
            FaceSettingsHelper.showFaceSensorErrorDialog(
                    this,
                    getString(R.string.sec_face_error_title_no_space),
                    getString(R.string.sec_face_error_message_no_space),
                    true);
            return;
        }
        if (Settings.Global.getInt(getContentResolver(), "always_finish_activities", 0) != 0) {
            FaceSettingsHelper.showFaceSensorErrorDialog(
                    this,
                    getString(R.string.sec_fingerprint_attention),
                    getResources()
                            .getString(
                                    R.string.sec_face_error_message_always_finish_activities,
                                    getString(R.string.immediately_destroy_activities)),
                    true);
            return;
        }
        int windowingMode =
                getResources().getConfiguration().windowConfiguration.getWindowingMode();
        if (windowingMode == 5 || windowingMode == 6) {
            Toast.makeText(
                            this,
                            BiometricsGenericHelper.isEnabledBiometricsMenuInDexMode(this)
                                    ? R.string.sec_biometrics_fullscreen_register_face
                                    : R.string.sec_biometrics_common_not_use_multi_window_view,
                            0)
                    .show();
            closeSessionAndFinish();
            return;
        }
        if (SecurityUtils.isNotAvailableBiometricsWithDexAndMultiWindow(
                this, R.string.bio_face_recognition_title, "FcstFaceLockSettings")) {
            super.onCreate(bundle);
            closeSessionAndFinish();
            return;
        }
        if (BiometricsGenericHelper.isPopupWindowMode()) {
            Toast.makeText(this, R.string.sec_face_block_popup_view, 0).show();
            closeSessionAndFinish();
            return;
        }
        Intent intent = getIntent();
        if (intent != null) {
            String stringExtra = intent.getStringExtra("previousStage");
            this.mPreviousStage = stringExtra;
            if (stringExtra == null) {
                Log.e("FcstFaceLockSettings", "mPreviousStage is null.");
            } else {
                this.mLockPatternUtils = new LockPatternUtils(this.mContext);
                this.mFaceManager = Utils.getFaceManagerOrNull(this.mContext);
                this.mUserId =
                        intent.getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId());
                this.mIsFromKnoxSetCases = intent.getBooleanExtra("is_knox", false);
                this.mIsOneLock =
                        SemPersonaManager.isKnoxId(this.mUserId)
                                && !this.mLockPatternUtils.isSeparateProfileChallengeEnabled(
                                        this.mUserId);
                if (this.mUserId != -10000) {
                    this.mIsFromFragment = intent.getBooleanExtra("from_biometric_fragment", false);
                    this.mFromSetupwizard = intent.getBooleanExtra("fromSetupWizard", false);
                    this.mSkipMandatoryBiometrics =
                            intent.getBooleanExtra("skip_mandatory_biometrics", false);
                    if ("setupwizard_face".equals(this.mPreviousStage)) {
                        this.mFromSetupwizard = true;
                    }
                    intent.getBooleanExtra("identifyFace", false);
                    this.mIsFromTip = intent.getBooleanExtra("fromTip", false);
                    Log.d("FcstFaceLockSettings", "mPreviousStage : " + this.mPreviousStage);
                    Log.d("FcstFaceLockSettings", "mFromSetupwizard : " + this.mFromSetupwizard);
                    if (intent.hasExtra(":settings:fragment_args_key")) {
                        this.key = intent.getStringExtra(":settings:fragment_args_key");
                    }
                    int i = this.mUserId;
                    LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
                    if (lockPatternUtils != null) {
                        this.mIsSecured = lockPatternUtils.isSecure(i);
                        this.mGkPwProvider = new GatekeeperPasswordProvider(this.mLockPatternUtils);
                        long longExtra = intent.getLongExtra("gk_pw_handle", 0L);
                        this.mGkPwHandle = longExtra;
                        this.result_intent.putExtra("gk_pw_handle", longExtra);
                    }
                    this.mToken = intent.getByteArrayExtra("hw_auth_token");
                    this.mChallenge = intent.getLongExtra("challenge", 0L);
                    this.mSensorId = intent.getIntExtra("sensor_id", -1);
                    FaceManager faceManager = this.mFaceManager;
                    if (faceManager != null
                            && (enrolledFaces = faceManager.getEnrolledFaces(this.mUserId))
                                    != null) {
                        int size = enrolledFaces.size();
                        if (size > 0) {
                            this.mHasEnrolledFaces = true;
                            Log.d(
                                    "FcstFaceLockSettings",
                                    "mHasEnrolledFaces : " + this.mHasEnrolledFaces);
                            if (FaceSettingsHelper.isSupportBioFaceAlternativeFace() && size > 1) {
                                this.mHasEnrolledAlternativeFaces = true;
                                ActionBarContextView$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("mHasEnrolledAlternativeFaces : "),
                                        this.mHasEnrolledAlternativeFaces,
                                        "FcstFaceLockSettings");
                            }
                        } else {
                            Log.e("FcstFaceLockSettings", "faceList is null or size is 0");
                        }
                    }
                    this.mUnificationProfileId =
                            intent.getIntExtra("unification_profile_id", -10000);
                    this.mUnificationProfileCredential =
                            intent.getParcelableExtra("unification_profile_credential");
                    if (TheftProtectionUtils.isMandatoryBiometricEnabled(this.mContext)
                            && !"FaceSettings_register_alternative".equals(this.mPreviousStage)
                            && !"FaceSettings_register".equals(this.mPreviousStage)
                            && !"FaceSettings_unlock_switch".equals(this.mPreviousStage)
                            && !this.mSkipMandatoryBiometrics) {
                        if ("face_register_external".equals(this.mPreviousStage)
                                && this.mHasEnrolledFaces) {
                            Log.d(
                                    "FcstFaceLockSettings",
                                    "external register request, already face registered");
                            closeSessionAndFinish();
                            return;
                        } else {
                            this.mKeepSessionAndActivity = 17;
                            startActivityForResult(
                                    BiometricsGenericHelper
                                            .getMandatoryBiometricIntentForBiometricsSetting(
                                                    this.mContext,
                                                    256,
                                                    "face_register_external"
                                                            .equals(this.mPreviousStage)),
                                    1030);
                            return;
                        }
                    }
                    String str = this.mPreviousStage;
                    str.getClass();
                    switch (str) {
                        case "FaceSettings_unlock_switch":
                        case "lock_screen_face":
                        case "FaceSettings_register":
                        case "setupwizard_face":
                        case "face_register_external":
                            if (!this.mHasEnrolledFaces) {
                                if (!this.mIsSecured) {
                                    Log.d("FcstFaceLockSettings", "Start Screen lock type.");
                                    launchChooseLock();
                                    break;
                                } else if (this.mGkPwHandle != 0) {
                                    startBiometricsDisclaimer();
                                    break;
                                } else {
                                    launchConfirmLock();
                                    break;
                                }
                            } else {
                                Log.w(
                                        "FcstFaceLockSettings",
                                        "Wrong case! Face and digital lock already exist!");
                                setResult(0);
                                closeSessionAndFinish();
                                break;
                            }
                        case "key_face_settings_entry":
                            if (!this.mIsSecured) {
                                Log.d("FcstFaceLockSettings", "Start Screen lock type.");
                                launchChooseLock();
                                break;
                            } else if (this.mGkPwHandle != 0) {
                                startBiometricsDisclaimer();
                                break;
                            } else {
                                launchConfirmLock();
                                break;
                            }
                        case "FaceSettings_register_alternative":
                            if (!FaceSettingsHelper.isSupportBioFaceAlternativeFace()) {
                                Log.e("FcstFaceLockSettings", "onCreate : Wrong case");
                                this.mKeepSessionAndActivity = 1;
                                closeSessionAndFinish();
                                break;
                            } else if (!this.mHasEnrolledAlternativeFaces) {
                                if (!this.mIsSecured || this.mGkPwHandle != 0) {
                                    Log.d("FcstFaceLockSettings", "startFaceRegister");
                                    startFaceRegister(false, true);
                                    break;
                                } else {
                                    launchConfirmLock();
                                    break;
                                }
                            } else {
                                Log.e(
                                        "FcstFaceLockSettings",
                                        "Wrong case! Alternative face already exist");
                                finish();
                                break;
                            }
                            break;
                        case "knox_lock_screen_face":
                            if (!this.mIsOneLock) {
                                if (!this.mHasEnrolledFaces) {
                                    startBiometricsDisclaimer();
                                    break;
                                } else if (!this.mIsSecured) {
                                    startBiometricsDisclaimer();
                                    break;
                                } else {
                                    Log.e("FcstFaceLockSettings", "Wrong Case!");
                                    closeSessionAndFinish();
                                    break;
                                }
                            } else {
                                launchChooseLock();
                                break;
                            }
                        default:
                            Log.e(
                                    "FcstFaceLockSettings",
                                    "This is wrong previousStage case! Finish the Activity");
                            setResult(0);
                            closeSessionAndFinish();
                            break;
                    }
                    return;
                }
                Log.e("FcstFaceLockSettings", "User ID is USER_NULL.");
            }
        }
        closeSessionAndFinish();
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        Log.d("FcstFaceLockSettings", "onDestroy");
        super.onDestroy();
    }

    @Override // android.app.Activity
    public final void onPause() {
        Log.d("FcstFaceLockSettings", "onPause");
        closeSessionAndFinish();
        super.onPause();
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        Log.d("FcstFaceLockSettings", "onResume");
        this.mOrientation = getResources().getConfiguration().orientation;
    }

    @Override // android.app.Activity
    public final void onStop() {
        Log.d("FcstFaceLockSettings", "onStop");
        super.onStop();
    }

    public final void requestToken(final boolean z) {
        FaceManager faceManager = this.mFaceManager;
        if (faceManager == null || this.mGkPwHandle == 0 || this.mToken != null) {
            return;
        }
        faceManager.generateChallenge(
                this.mUserId,
                new FaceManager
                        .GenerateChallengeCallback() { // from class:
                                                       // com.samsung.android.settings.biometrics.face.FaceLockSettings$$ExternalSyntheticLambda0
                    public final void onGenerateChallengeResult(int i, int i2, long j) {
                        FaceLockSettings faceLockSettings = FaceLockSettings.this;
                        boolean z2 = z;
                        faceLockSettings.mSensorId = i;
                        faceLockSettings.mChallenge = j;
                        faceLockSettings.result_intent.putExtra("sensor_id", i);
                        faceLockSettings.result_intent.putExtra(
                                "challenge", faceLockSettings.mChallenge);
                        GatekeeperPasswordProvider gatekeeperPasswordProvider =
                                faceLockSettings.mGkPwProvider;
                        if (gatekeeperPasswordProvider != null) {
                            faceLockSettings.mToken =
                                    gatekeeperPasswordProvider.requestGatekeeperHat(
                                            faceLockSettings.mGkPwHandle,
                                            faceLockSettings.mChallenge,
                                            faceLockSettings.mUserId);
                        }
                        byte[] bArr = faceLockSettings.mToken;
                        if (bArr == null) {
                            Log.e("FcstFaceLockSettings", "token is NULL!");
                            FaceSettingsHelper.showFaceSensorErrorDialog(
                                    faceLockSettings.mContext,
                                    null,
                                    faceLockSettings.getString(
                                            R.string.sec_biometrics_error_timed_out),
                                    true);
                            return;
                        }
                        faceLockSettings.result_intent.putExtra("hw_auth_token", bArr);
                        if (z2) {
                            Log.d("FcstFaceLockSettings", "startFaceRegister");
                            faceLockSettings.startFaceRegister(false, true);
                        }
                    }
                });
    }

    public final void startBiometricsDisclaimer() {
        Log.d("FcstFaceLockSettings", "startBiometricsDisclaimer");
        if (Utils.isDesktopStandaloneMode(this)) {
            BiometricsGenericHelper.isLandscapeDefaultModel();
            Toast.makeText(
                            this.mContext,
                            R.string.sec_biometrics_common_dex_standalone_block_register_face,
                            0)
                    .show();
            if (fromFaceSettings(false)) {
                this.mKeepSessionAndActivity = 1;
            }
            closeSessionAndFinish();
            return;
        }
        Intent intent = new Intent();
        intent.setClassName(
                getPackageName(),
                "com.samsung.android.settings.biometrics.BiometricsDisclaimerActivity");
        intent.putExtra("android.intent.extra.USER_ID", this.mUserId);
        intent.putExtra("BIOMETRICS_LOCK_TYPE", 256);
        intent.putExtra("fromSetupWizard", this.mFromSetupwizard);
        intent.putExtra("gk_pw_handle", this.mGkPwHandle);
        if (this.mFromSetupwizard) {
            intent.setClassName(
                    getPackageName(),
                    "com.samsung.android.settings.biometrics.SuwBiometricsDisclaimerActivity");
        } else if (this.mIsSecured && this.mGkPwHandle == 0) {
            intent.setClassName(
                    getPackageName(),
                    "com.samsung.android.settings.biometrics.BiometricsDisclaimerWithConfirmLock");
        }
        try {
            this.mKeepSessionAndActivity = 17;
            startActivityForResult(
                    intent, EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Log.d("FcstFaceLockSettings", "startBiometricsDisclaimer : Activity Not Found !");
            this.mKeepSessionAndActivity = 0;
            if (fromFaceSettings(false)) {
                this.mKeepSessionAndActivity = 1;
            }
            closeSessionAndFinish();
        }
    }

    public final void startFaceRegister(boolean z, boolean z2) {
        Log.d("FcstFaceLockSettings", "startFaceRegister : " + z2);
        if (Utils.isDesktopStandaloneMode(this)) {
            BiometricsGenericHelper.isLandscapeDefaultModel();
            Toast.makeText(
                            this.mContext,
                            R.string.sec_biometrics_common_dex_standalone_block_register_face,
                            0)
                    .show();
            this.mKeepSessionAndActivity = 1;
            closeSessionAndFinish();
            return;
        }
        if (z2 && Utils.isTablet()) {
            int rotation =
                    ((WindowManager) this.mContext.getSystemService("window"))
                            .getDefaultDisplay()
                            .getRotation();
            Log.d("FcstFaceLockSettings", "rotation : " + rotation);
            BiometricsGenericHelper.isLandscapeDefaultModel();
            if (rotation != 0) {
                Log.d("FcstFaceLockSettings", "startBiometricsRotationGuide");
                Intent intent = new Intent();
                intent.setClassName(
                        getPackageName(),
                        "com.samsung.android.settings.biometrics.BiometricsRotationGuide");
                intent.putExtra("android.intent.extra.USER_ID", this.mUserId);
                intent.putExtra("BIOMETRICS_LOCK_TYPE", 256);
                intent.putExtra("fromSetupWizard", this.mFromSetupwizard);
                if (this.mFromSetupwizard) {
                    intent.setClassName(
                            getPackageName(),
                            "com.samsung.android.settings.biometrics.SuwBiometricsRotationGuide");
                }
                try {
                    this.mKeepSessionAndActivity = 17;
                    startActivityForResult(
                            intent, EnterpriseContainerCallback.CONTAINER_MOUNT_STATUS);
                    return;
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    Log.d(
                            "FcstFaceLockSettings",
                            "startBiometricsRotationGuide : Activity Not Found !");
                    this.mKeepSessionAndActivity = 0;
                    if (fromFaceSettings(false)) {
                        this.mKeepSessionAndActivity = 1;
                    }
                    closeSessionAndFinish();
                    return;
                }
            }
            BiometricsGenericHelper.isLandscapeDefaultModel();
        }
        Log.d("FcstFaceLockSettings", "There is no enrolled face. Run startFaceRegister!!");
        Intent intent2 = new Intent();
        intent2.setClassName(
                "com.samsung.android.biometrics.app.setting",
                "com.samsung.android.biometrics.app.setting.face.FaceEnrollActivity");
        intent2.putExtra("gk_pw_handle", this.mGkPwHandle);
        intent2.putExtra("hw_auth_token", this.mToken);
        intent2.putExtra("challenge", this.mChallenge);
        intent2.putExtra("sensor_id", this.mSensorId);
        intent2.putExtra("face_enroll_retry", z);
        intent2.addFlags(536870912);
        intent2.putExtra("android.intent.extra.USER_ID", this.mUserId);
        intent2.putExtra("fromSetupWizard", this.mFromSetupwizard);
        FaceLockSettings faceLockSettings = this.mContext;
        int i = this.mUserId;
        boolean z3 = FaceSettingsHelper.IS_SUPPORT_FACE_DAEMON_DETECT_OPEN_EYES;
        intent2.putExtra(
                "face_wear_glasses",
                Settings.Secure.getIntForUser(
                                faceLockSettings.getContentResolver(), "face_wear_glasses", 0, i)
                        == 1);
        try {
            this.mKeepSessionAndActivity = 17;
            startActivityForResult(intent2, VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI);
        } catch (ActivityNotFoundException unused) {
            Log.d("FcstFaceLockSettings", "runRegister : Activity Not Found !");
            if (fromFaceSettings(false)) {
                this.mKeepSessionAndActivity = 1;
            }
            closeSessionAndFinish();
        }
    }

    public final void startFaceSettings(Context context) {
        Log.d("FcstFaceLockSettings", "startFaceSettings");
        if (this.mIsBiometricsSettingsDestroy) {
            Log.d("FcstFaceLockSettings", "mIsBiometricsSettingsDestroy is set!");
            this.mKeepSessionAndActivity = 0;
            closeSessionAndFinish();
            return;
        }
        if (context == null) {
            Log.d("FcstFaceLockSettings", "startFragment : context is null");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(":settings:fragment_args_key", this.key);
        bundle.putLong("gk_pw_handle", this.mGkPwHandle);
        bundle.putBoolean("isAfw", false);
        bundle.putInt("android.intent.extra.USER_ID", this.mUserId);
        byte[] bArr = this.mToken;
        if (bArr != null) {
            bundle.putByteArray("hw_auth_token", bArr);
            bundle.putLong("challenge", this.mChallenge);
            bundle.putInt("sensor_id", this.mSensorId);
        }
        this.mKeepSessionAndActivity |= 1;
        if (this.mIsSecured
                || "lock_screen_face".equalsIgnoreCase(this.mPreviousStage)
                || !BiometricsGenericHelper.needFmmBackupPasswordPopup(this.mContext)) {
            bundle.putBoolean("need_fmm_popup", false);
        } else {
            bundle.putBoolean("need_fmm_popup", true);
        }
        if (BiometricsGenericHelper.getMandatoryBiometricsDelayTimeState(this.mContext)
                == ProtectionTimerService$TimeDelayState.GRACE_PERIOD) {
            bundle.putBoolean("skip_mandatory_biometrics", true);
        }
        if (this.mIsFromFragment) {
            Intent intent = new Intent();
            intent.putExtra(":settings:show_fragment_args", bundle);
            setResult(-1, intent);
            finish();
            return;
        }
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
        String name = FaceSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mArguments = bundle;
        launchRequest.mSourceMetricsCategory = 9031;
        subSettingLauncher.setTitleRes(R.string.bio_face_recognition_title, null);
        subSettingLauncher.addFlags(65536);
        subSettingLauncher.launch();
    }

    public final void startFaceUsefulFeature() {
        Log.d("FcstFaceLockSettings", "Run startFaceUsefulFeature");
        Intent intent = new Intent();
        intent.setClassName(
                getPackageName(),
                this.mFromSetupwizard
                        ? "com.samsung.android.settings.biometrics.face.SuwFaceUsefulFeature"
                        : "com.samsung.android.settings.biometrics.face.FaceUsefulFeature");
        intent.putExtra("android.intent.extra.USER_ID", this.mUserId);
        intent.putExtra("fromSetupWizard", this.mFromSetupwizard);
        intent.putExtra("gk_pw_handle", this.mGkPwHandle);
        intent.putExtra("previousStage", this.mPreviousStage);
        intent.addFlags(65536);
        try {
            this.mKeepSessionAndActivity = 17;
            startActivityForResult(
                    intent, EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_FAILURE);
        } catch (ActivityNotFoundException unused) {
            Log.d("FcstFaceLockSettings", "startFaceUsefulFeature : Activity Not Found !");
            if (fromFaceSettings(true)) {
                this.mKeepSessionAndActivity = 1;
            }
            closeSessionAndFinish();
        }
    }
}
