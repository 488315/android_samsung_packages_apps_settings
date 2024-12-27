package com.samsung.android.settings.biometrics.fingerprint;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;

import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.widget.LockPatternUtils;
import com.android.settings.DisplaySettings$$ExternalSyntheticOutline0;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.biometrics.BiometricUtils;
import com.android.settings.biometrics.GatekeeperPasswordProvider;
import com.android.settings.password.ChooseLockSettingsHelper;

import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.biometrics.BiometricsGenericHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class RegisterFingerprint extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public FingerprintManager mFingerprintManager;
    public String mPreviousStage;
    public IStatusBarService mStatusBarService;
    public IBinder mStatusBarToken;
    public boolean mIsFromKnoxFingerprintPlus = false;
    public boolean mIsFromKnoxSetupWizard = false;
    public boolean mIsFromKnoxOtherCases = false;
    public boolean mIsFromKnoxTwoStep = false;
    public LockPatternUtils mLockPatternUtils = null;
    public byte[] mToken = null;
    public long mChallenge = 0;
    public long mGkPwHandle = 0;
    public boolean mWasSecureBefore = true;
    public boolean mFromSetupwizard = false;
    public boolean mIsFinishRegistration = false;
    public boolean mIsReCreatedRegistration = false;
    public int mUserId = 0;
    public int mSensorStatus = 0;

    public final void launchConfirmLock() {
        Log.d("FpstRegisterTouchFingerprint", "launchConfirmLock");
        ChooseLockSettingsHelper.Builder builder = new ChooseLockSettingsHelper.Builder(this);
        builder.mRequestCode = 1002;
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
        Log.w("FpstRegisterTouchFingerprint", "Fail launchConfirmLock!");
        finish();
    }

    @Override // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        StringBuilder m =
                RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                        "requestCode : ", " resultCode : ", i, i2, " data : ");
        m.append(intent);
        Log.d("FpstRegisterTouchFingerprint", m.toString());
        setResult(i2, intent);
        if (GatekeeperPasswordProvider.containsGatekeeperPasswordHandle(intent)) {
            this.mGkPwHandle = GatekeeperPasswordProvider.getGatekeeperPasswordHandle(intent);
        }
        switch (i) {
            case 1002:
                if (i2 == -1 && this.mGkPwHandle != 0) {
                    startFingerprintRegistration();
                    break;
                } else {
                    finish();
                    break;
                }
            case 1003:
                if (i2 == 1 && this.mGkPwHandle != 0) {
                    startFingerprintRegistration();
                    break;
                } else {
                    finish();
                    break;
                }
            case 1005:
                if (i2 != -1) {
                    finish();
                    break;
                } else {
                    Log.d("FpstRegisterTouchFingerprint", "launchChooseLock");
                    Intent chooseLockIntent = BiometricUtils.getChooseLockIntent(this, getIntent());
                    chooseLockIntent.putExtra("hide_insecure_options", true);
                    chooseLockIntent.putExtra("request_gk_pw_handle", true);
                    chooseLockIntent.putExtra("for_fingerprint", true);
                    int i3 = this.mUserId;
                    if (i3 != -10000) {
                        chooseLockIntent.putExtra("android.intent.extra.USER_ID", i3);
                    }
                    try {
                        startActivityForResult(chooseLockIntent, 1003);
                        break;
                    } catch (ActivityNotFoundException unused) {
                        Log.d(
                                "FpstRegisterTouchFingerprint",
                                "launchChooseLock : Activity Not Found !");
                        finish();
                        return;
                    }
                }
            case 1006:
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                        i2,
                        "FINGERPRINT_ENROLL_REQUEST : resultCode = ",
                        "FpstRegisterTouchFingerprint");
                boolean booleanExtra =
                        intent == null ? false : intent.getBooleanExtra("IsReCreated", false);
                this.mIsReCreatedRegistration = booleanExtra;
                if (!booleanExtra) {
                    if (i2 == 3) {
                        setResult(i2);
                    } else {
                        FingerprintManager fingerprintManager = this.mFingerprintManager;
                        if (fingerprintManager != null
                                && !fingerprintManager.hasEnrolledFingerprints(this.mUserId)) {
                            setResult(0, intent);
                        }
                    }
                    if (!this.mIsFinishRegistration) {
                        Log.d("FpstRegisterTouchFingerprint", "finishRegistration()");
                        this.mIsFinishRegistration = true;
                        if (!Rune.isShopDemo(getBaseContext())) {
                            finish();
                            break;
                        } else {
                            Log.d(
                                    "FpstRegisterTouchFingerprint",
                                    "isShopDemo is true, showLDUDialog()");
                            new AlertDialog.Builder(this)
                                    .setTitle(R.string.sec_fingerprint_unable_to_register)
                                    .setMessage(
                                            getString(R.string.sec_fingerprint_ldu_warning_message))
                                    .setNegativeButton(
                                            android.R.string.ok,
                                            new DialogInterface
                                                    .OnClickListener() { // from class:
                                                                         // com.samsung.android.settings.biometrics.fingerprint.RegisterFingerprint$$ExternalSyntheticLambda2
                                                @Override // android.content.DialogInterface.OnClickListener
                                                public final void onClick(
                                                        DialogInterface dialogInterface, int i4) {
                                                    RegisterFingerprint registerFingerprint =
                                                            RegisterFingerprint.this;
                                                    int i5 = RegisterFingerprint.$r8$clinit;
                                                    registerFingerprint.finish();
                                                }
                                            })
                                    .setOnCancelListener(
                                            new DialogInterface
                                                    .OnCancelListener() { // from class:
                                                                          // com.samsung.android.settings.biometrics.fingerprint.RegisterFingerprint$$ExternalSyntheticLambda3
                                                @Override // android.content.DialogInterface.OnCancelListener
                                                public final void onCancel(
                                                        DialogInterface dialogInterface) {
                                                    RegisterFingerprint registerFingerprint =
                                                            RegisterFingerprint.this;
                                                    int i4 = RegisterFingerprint.$r8$clinit;
                                                    registerFingerprint.finish();
                                                }
                                            })
                                    .setCancelable(false)
                                    .create()
                                    .show();
                            break;
                        }
                    } else {
                        Log.d("FpstRegisterTouchFingerprint", "finishRegistration() already run.");
                        break;
                    }
                } else {
                    startFingerprintRegistration();
                    break;
                }
                break;
        }
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        Log.d("FpstRegisterTouchFingerprint", "onConfigurationChanged");
        super.onConfigurationChanged(configuration);
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        Log.d("FpstRegisterTouchFingerprint", "onCreate");
        getWindow().requestFeature(1);
        super.onCreate(bundle);
        if (!BiometricsGenericHelper.isAvailableMemorySizeToEnroll()) {
            showSensorErrorDialog(4);
            return;
        }
        boolean z = FingerprintSettingsUtils.SUB_DISPLAY_IS_LARGE_SCREEN;
        if (Settings.Global.getInt(getContentResolver(), "always_finish_activities", 0) != 0) {
            FingerprintSettingsUtils.showSensorErrorDialog(
                    this,
                    getString(R.string.sec_fingerprint_attention),
                    getResources()
                            .getString(
                                    R.string.sec_fingerprint_error_message_always_finish_activities,
                                    getString(R.string.immediately_destroy_activities)),
                    true);
            return;
        }
        Context baseContext = getBaseContext();
        if (baseContext == null) {
            Log.w("FpstFingerprintSettingsUtils", "isOneHandedMode : context = null");
        } else if (Settings.System.getInt(baseContext.getContentResolver(), "any_screen_running", 0)
                == 1) {
            FingerprintSettingsUtils.showSensorErrorDialog(
                    this,
                    getString(R.string.sec_fingerprint_attention),
                    getResources()
                            .getString(R.string.sec_fingerprint_error_message_one_handed_mode),
                    true);
            return;
        }
        Intent intent = getIntent();
        this.mPreviousStage = intent.getStringExtra("previousStage");
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                new StringBuilder("[previousStage] = "),
                this.mPreviousStage,
                "FpstRegisterTouchFingerprint");
        if (this.mPreviousStage == null) {
            this.mPreviousStage = "fingerprint_register_external";
        }
        this.mUserId = intent.getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId());
        Log.secD("FpstRegisterTouchFingerprint", "mUserId : " + this.mUserId);
        if (bundle != null && SemPersonaManager.isSecureFolderId(this.mUserId)) {
            this.mToken = null;
            this.mGkPwHandle = 0L;
        } else if (GatekeeperPasswordProvider.containsGatekeeperPasswordHandle(intent)) {
            this.mGkPwHandle = intent.getLongExtra("gk_pw_handle", 0L);
            this.mToken = intent.getByteArrayExtra("hw_auth_token");
            this.mChallenge = intent.getLongExtra("challenge", 0L);
        }
        if (SemPersonaManager.isSecureFolderId(this.mUserId)
                || SemDualAppManager.isDualAppId(this.mUserId)) {
            this.mUserId = 0;
            Log.secD(
                    "FpstRegisterTouchFingerprint",
                    "SecureFolder or DualApp use owner's fingerprint. mUserId : " + this.mUserId);
        }
        this.mFromSetupwizard = intent.getBooleanExtra("fromSetupWizard", false);
        this.mIsFromKnoxSetupWizard = intent.getBooleanExtra("isFromKnoxSetupWizard", false);
        this.mIsFromKnoxFingerprintPlus =
                intent.getBooleanExtra("isFromKnoxFingerprintPlus", false);
        this.mIsFromKnoxOtherCases = intent.getBooleanExtra("is_knox", false);
        this.mIsFromKnoxTwoStep = intent.getBooleanExtra("is_knox_two_step", false);
        Log.secD(
                "FpstRegisterTouchFingerprint",
                "[KNOX FINGERPRINT] : init, mIsFromKnoxSetupWizard:"
                        + this.mIsFromKnoxSetupWizard
                        + ", mIsFromKnoxOtherCases:"
                        + this.mIsFromKnoxOtherCases
                        + ", mIsFromKnoxTwoStep"
                        + this.mIsFromKnoxTwoStep);
        this.mLockPatternUtils = new LockPatternUtils(this);
        FingerprintManager fingerprintManager =
                (FingerprintManager) getSystemService("fingerprint");
        this.mFingerprintManager = fingerprintManager;
        if (fingerprintManager == null) {
            Log.secD("FpstRegisterTouchFingerprint", "[onCreate] mFingerprintManager == null");
            showSensorErrorDialog(0);
            return;
        }
        if (!fingerprintManager.isHardwareDetected()) {
            Log.e("FpstRegisterTouchFingerprint", "[onCreate] isHardwareDetected = false");
            showSensorErrorDialog(0);
            return;
        }
        if (bundle != null) {
            this.mToken = bundle.getByteArray("hw_auth_token");
            this.mChallenge = bundle.getLong("challenge", this.mChallenge);
            this.mGkPwHandle = bundle.getLong("gk_pw_handle", this.mGkPwHandle);
            this.mWasSecureBefore = bundle.getBoolean("WasSecureBefore");
        } else {
            this.mWasSecureBefore = this.mLockPatternUtils.isSecure(this.mUserId);
        }
        if (bundle != null) {
            this.mStatusBarToken = bundle.getBinder("StatusBarToken");
        }
        if (this.mGkPwHandle != 0 || this.mToken != null) {
            startFingerprintRegistration();
            return;
        }
        if (!this.mIsFromKnoxOtherCases
                && !this.mIsFromKnoxSetupWizard
                && !this.mIsFromKnoxTwoStep
                && !this.mIsFromKnoxFingerprintPlus
                && !SemPersonaManager.isDoEnabled(this.mUserId)) {
            if (this.mLockPatternUtils.isSecure(this.mUserId)) {
                launchConfirmLock();
                return;
            } else {
                startBiometricsDisclaimer();
                return;
            }
        }
        FingerprintManager fingerprintManager2 = this.mFingerprintManager;
        if (fingerprintManager2 != null) {
            if (!fingerprintManager2.hasEnrolledFingerprints(this.mUserId)
                    || this.mIsFromKnoxFingerprintPlus) {
                Log.d(
                        "FpstRegisterTouchFingerprint",
                        "[KNOX FINGERPRPINT] : nonFinger chooseLock CASE");
                if ((this.mLockPatternUtils.isSecure(this.mUserId)
                                && (this.mLockPatternUtils.isSeparateProfileChallengeEnabled(
                                                this.mUserId)
                                        || this.mUserId == 0))
                        || (SemPersonaManager.isKnoxId(this.mUserId)
                                && !this.mLockPatternUtils.isSeparateProfileChallengeEnabled(
                                        this.mUserId)
                                && this.mLockPatternUtils.isSecure(0))) {
                    launchConfirmLock();
                } else {
                    startBiometricsDisclaimer();
                }
            }
        }
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        Log.d("FpstRegisterTouchFingerprint", "onDestroy");
        if (this.mIsFromKnoxOtherCases) {
            Intent intent = new Intent();
            intent.putExtra("KnoxKeyguardEventFlag", 4);
            intent.setComponent(
                    new ComponentName(
                            "com.samsung.knox.kss", "com.samsung.knox.kss.KnoxKeyguardService"));
            startService(intent);
        }
        super.onDestroy();
    }

    @Override // android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        Intent intent = new Intent();
        intent.putExtra("enrollResult", 0);
        setResult(1, intent);
        finish();
        return true;
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        Log.d("FpstRegisterTouchFingerprint", "onResume");
    }

    @Override // android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Log.d("FpstRegisterTouchFingerprint", "onSaveInstanceState");
        bundle.putByteArray("hw_auth_token", this.mToken);
        bundle.putLong("challenge", this.mChallenge);
        bundle.putLong("gk_pw_handle", this.mGkPwHandle);
        bundle.putBoolean("WasSecureBefore", this.mWasSecureBefore);
        bundle.putBinder("StatusBarToken", this.mStatusBarToken);
    }

    @Override // android.app.Activity
    public final void onStop() {
        super.onStop();
        Log.d("FpstRegisterTouchFingerprint", "onStop");
        if (isFinishing()
                || TextUtils.equals(
                        "com.sec.android.app.launcher",
                        ((ActivityManager)
                                        getApplicationContext()
                                                .getSystemService(ActivityManager.class))
                                .getRunningTasks(1)
                                .get(0)
                                .baseActivity
                                .getPackageName())) {
            boolean z = FingerprintSettingsUtils.SUB_DISPLAY_IS_LARGE_SCREEN;
            try {
                IStatusBarService iStatusBarService = this.mStatusBarService;
                if (iStatusBarService != null) {
                    iStatusBarService.disable(0, this.mStatusBarToken, getPackageName());
                }
            } catch (RemoteException e) {
                Log.w(
                        "FpstRegisterTouchFingerprint",
                        "enableNavigationBar - StatusBarService : " + e);
            }
        }
    }

    public final void showSensorErrorDialog(int i) {
        String string;
        String string2;
        int i2;
        if (i == 4) {
            string = getResources().getString(R.string.sec_fingerprint_error_title_no_space);
            string2 = getResources().getString(R.string.sec_fingerprint_error_message_no_space);
        } else if (i == 1001) {
            string2 =
                    getResources()
                            .getString(R.string.sec_fingerprint_error_message_something_on_sensor);
            string = null;
        } else if (i != 1003) {
            string =
                    getResources()
                            .getString(R.string.sec_fingerprint_error_message_sensor_error_title);
            string2 = getResources().getString(R.string.sec_fingerprint_error_message_sensor_error);
        } else {
            string =
                    getResources()
                            .getString(R.string.sec_fingerprint_error_message_sensor_error_title);
            string2 =
                    getResources().getString(R.string.sec_fingerprint_error_message_not_responding);
        }
        FingerprintManager fingerprintManager = this.mFingerprintManager;
        if (fingerprintManager != null) {
            this.mSensorStatus = fingerprintManager.semGetSensorStatus();
        }
        FingerprintManager fingerprintManager2 = this.mFingerprintManager;
        if (fingerprintManager2 != null
                && (((i2 = this.mSensorStatus) == 100040 || i2 == 100041)
                        && !fingerprintManager2.semIsEnrollSession())) {
            Log.d("FpstRegisterTouchFingerprint", "Session closed!");
            finish();
        } else {
            AlertDialog create =
                    new AlertDialog.Builder(
                                    this, BiometricsGenericHelper.isLightTheme(this) ? 5 : 4)
                            .setTitle(string)
                            .setMessage(string2)
                            .setPositiveButton(
                                    android.R.string.ok,
                                    new RegisterFingerprint$$ExternalSyntheticLambda0())
                            .setOnDismissListener(
                                    new DialogInterface
                                            .OnDismissListener() { // from class:
                                                                   // com.samsung.android.settings.biometrics.fingerprint.RegisterFingerprint$$ExternalSyntheticLambda1
                                        @Override // android.content.DialogInterface.OnDismissListener
                                        public final void onDismiss(
                                                DialogInterface dialogInterface) {
                                            RegisterFingerprint registerFingerprint =
                                                    RegisterFingerprint.this;
                                            int i3 = RegisterFingerprint.$r8$clinit;
                                            registerFingerprint.getClass();
                                            Log.secD(
                                                    "FpstRegisterTouchFingerprint",
                                                    "showSensorErrorDialog dismiss!!");
                                            Intent intent = new Intent();
                                            intent.putExtra("enrollResult", 1);
                                            registerFingerprint.setResult(0, intent);
                                            registerFingerprint.finish();
                                        }
                                    })
                            .create();
            create.getWindow().setDimAmount(0.3f);
            create.getWindow().addFlags(2);
            create.show();
        }
    }

    public final void startBiometricsDisclaimer() {
        Log.d("FpstRegisterTouchFingerprint", "startBiometricsDisclaimer");
        Intent intent = new Intent();
        intent.setClassName(
                getPackageName(),
                "com.samsung.android.settings.biometrics.BiometricsDisclaimerActivity");
        intent.putExtra("BIOMETRICS_LOCK_TYPE", 1);
        intent.putExtra("android.intent.extra.USER_ID", this.mUserId);
        if ("google_setupwizard_fingerprint".equals(this.mPreviousStage)) {
            intent.putExtra("fromSetupWizard", true);
            intent.setClassName(
                    getPackageName(),
                    "com.samsung.android.settings.biometrics.SuwBiometricsDisclaimerActivity");
        }
        try {
            startActivityForResult(intent, 1005);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Log.d(
                    "FpstRegisterTouchFingerprint",
                    "startBiometricsDisclaimer : Activity Not Found !");
            finish();
        }
    }

    public final void startFingerprintRegistration() {
        Log.d("FpstRegisterTouchFingerprint", "startFingerprintRegistration()");
        FingerprintManager fingerprintManager = this.mFingerprintManager;
        if (fingerprintManager == null
                || FingerprintSettingsUtils.getEnrolledFingerNumber(
                                fingerprintManager, this.mUserId)
                        >= FingerprintSettingsUtils.getMaxFingerEnroll(this.mFingerprintManager)) {
            finish();
            return;
        }
        if (this.mStatusBarService == null) {
            this.mStatusBarService =
                    IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        }
        if (this.mStatusBarToken == null) {
            this.mStatusBarToken = new Binder();
        }
        try {
            IStatusBarService iStatusBarService = this.mStatusBarService;
            if (iStatusBarService != null) {
                iStatusBarService.disable(18874368, this.mStatusBarToken, getPackageName());
            }
        } catch (RemoteException e) {
            Log.w("FpstRegisterTouchFingerprint", "disableNavigationBar - StatusBarService : " + e);
        }
        Intent m =
                DisplaySettings$$ExternalSyntheticOutline0.m(
                        "com.samsung.android.biometrics.app.setting",
                        "com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity");
        m.putExtra("hw_auth_token", this.mToken);
        m.putExtra("challenge", this.mChallenge);
        m.putExtra("gk_pw_handle", this.mGkPwHandle);
        m.putExtra("android.intent.extra.USER_ID", this.mUserId);
        boolean z = false;
        if (this.mFingerprintManager.hasEnrolledFingerprints(this.mUserId)
                && Settings.System.getInt(getContentResolver(), "fingerprint_guide_shown", 0)
                        == 1) {
            z = true;
        }
        m.putExtra("skipGuideScreen", z);
        m.putExtra("fromSetupWizard", this.mFromSetupwizard);
        m.putExtra("isFromKnoxFingerprintPlus", this.mIsFromKnoxFingerprintPlus);
        m.putExtra("IsReCreated", this.mIsReCreatedRegistration);
        try {
            startActivityForResult(m, 1006);
        } catch (ActivityNotFoundException unused) {
            Log.d("FpstRegisterTouchFingerprint", "Activity Not Found !");
        }
    }
}
