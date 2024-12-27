package com.samsung.android.settings.biometrics.fingerprint;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.window.embedding.ActivityEmbeddingController;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.biometrics.GatekeeperPasswordProvider;
import com.android.settings.biometrics.combination.CombinedBiometricSearchIndexProvider;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.password.ChooseLockSettingsHelper;
import com.android.settings.search.BaseSearchIndexProvider;

import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.app.SemMultiWindowManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.biometrics.BiometricsGenericHelper;
import com.samsung.android.settings.knox.UCMUtils;
import com.samsung.android.settings.lockscreen.LockUtils;
import com.samsung.android.settings.security.SecurityUtils;
import com.samsung.android.settings.theftprotection.TheftProtectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FingerprintEntry extends Activity {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass2();
    public static int mUserId;
    public FingerprintEntry mContext;
    public FingerprintManager mGFingerprintManager;
    public boolean mIsFromBiometricsMenu;
    public boolean mIsFromFragment;
    public String mPreviousStage;
    public SemMultiWindowManager mSemMultiWindowManager;
    public int mSensorStatus;
    public String key = ApnSettings.MVNO_NONE;
    public byte[] mToken = null;
    public long mChallenge = 0;
    public long mGkPwHandle = 0;
    public boolean mIsWaitingResult = false;
    public boolean mIsKnox = false;
    public boolean mIsSkipMandatoryBiometrics = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.biometrics.fingerprint.FingerprintEntry$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            FingerprintManager fingerprintManagerOrNull =
                    Utils.getFingerprintManagerOrNull(context);
            boolean z =
                    !(fingerprintManagerOrNull != null
                            && fingerprintManagerOrNull.hasEnrolledFingerprints());
            FingerprintManager fingerprintManagerOrNull2 =
                    Utils.getFingerprintManagerOrNull(context);
            if (z) {
                ArrayList arrayList = (ArrayList) nonIndexableKeys;
                arrayList.add("set_screen_lock");
                arrayList.add("check_added_fingerprints");
            }
            if (FingerprintSettingsUtils.getEnrolledFingerNumber(
                            fingerprintManagerOrNull2, UserHandle.myUserId())
                    >= FingerprintSettingsUtils.getMaxFingerEnroll(fingerprintManagerOrNull2)) {
                ((ArrayList) nonIndexableKeys).add("key_fingerprint_add");
            }
            if (z
                    || !Utils.hasPackage(context, "com.sec.android.app.sbrowser")
                    || SecurityUtils.isEnabledSamsungPass(context)) {
                ((ArrayList) nonIndexableKeys).add("support_web_signin");
            }
            if (z
                    || !Utils.hasPackage(context, "com.osp.app.signin")
                    || ((SecurityUtils.isEnabledSamsungPass(context)
                                    && SecurityUtils.checkSAMenuChanged(context))
                            || UserHandle.myUserId() != 0)) {
                ((ArrayList) nonIndexableKeys).add("support_samsung_account");
            }
            if (z) {
                ((ArrayList) nonIndexableKeys).add("set_always_on");
            } else {
                FingerprintSettingsUtils.isSupportFingerprintAlwaysOn();
            }
            if (!z) {
                FingerprintSettingsUtils.isSupportFingerprintAlwaysOnType();
            }
            ArrayList arrayList2 = (ArrayList) nonIndexableKeys;
            arrayList2.add("set_always_on_type");
            if (!z) {
                UserHandle.myUserId();
            }
            arrayList2.add("stay_on_lock_screen");
            if (z || !FingerprintSettingsUtils.isSupportFingerprintScreenOffIconAod()) {
                arrayList2.add("screen_off_icon_aod");
            }
            arrayList2.add("screen_off_icon");
            arrayList2.add("show_fingerprint_icon");
            if (!z) {
                FingerprintSettingsUtils.isSupportFasterRecognition();
            }
            arrayList2.add("fast_recognition");
            if (z) {
                arrayList2.add("key_fingerprint_about_fingerprints");
            }
            if (z) {
                arrayList2.add("fingerprint_effect");
            }
            arrayList2.add("key_fingerprint_icon_and_animation_style");
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            if (Utils.isGuestUser(context) || !SecurityUtils.hasFingerprintFeature(context)) {
                return null;
            }
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.intentAction = "android.intent.action.MAIN";
            searchIndexableResource.intentTargetPackage =
                    KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG;
            searchIndexableResource.intentTargetClass =
                    "com.samsung.android.settings.biometrics.fingerprint.FingerprintEntry";
            searchIndexableResource.xmlResId = R.xml.sec_fingerprint;
            return Arrays.asList(searchIndexableResource);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            if (new FingerprintSettingsPreferenceController(context).getAvailabilityStatus() == 3) {
                return false;
            }
            return !(this instanceof CombinedBiometricSearchIndexProvider);
        }
    }

    public final void launchConfirmLock() {
        Log.secD("FpstFingerprintEntry", "launchConfirmLock ");
        this.mIsWaitingResult = true;
        ChooseLockSettingsHelper.Builder builder = new ChooseLockSettingsHelper.Builder(this);
        builder.mRequestCode = 101;
        builder.mHeader = BiometricsGenericHelper.getConfirmLockHeader(this, mUserId);
        builder.mRequestGatekeeperPasswordHandle = true;
        builder.mForegroundOnly = true;
        builder.mReturnCredentials = true;
        int i = mUserId;
        if (i != -10000) {
            builder.mUserId = i;
        }
        boolean show = builder.show();
        overridePendingTransition(0, 0);
        if (show) {
            return;
        }
        Log.secD("FpstFingerprintEntry", "ConfirmLock Fail");
        this.mIsWaitingResult = false;
        finish();
    }

    @Override // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        Bundle bundleExtra;
        StringBuilder m =
                RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                        "=====onActivityResult : requestCode : ",
                        " resultCode : ",
                        i,
                        i2,
                        " data : ");
        m.append(intent);
        Log.secD("FpstFingerprintEntry", m.toString());
        this.mIsWaitingResult = false;
        if (GatekeeperPasswordProvider.containsGatekeeperPasswordHandle(intent)) {
            this.mGkPwHandle = GatekeeperPasswordProvider.getGatekeeperPasswordHandle(intent);
        }
        if (i == 101) {
            if (i2 != -1) {
                finish();
                return;
            }
            if (this.mGkPwHandle == 0) {
                Log.e("FpstFingerprintEntry", "CONFIRM_REQUEST: GatekeeperPasswordHandle is 0L.");
                FingerprintSettingsUtils.showSensorErrorDialog(
                        this.mContext,
                        getString(R.string.sec_fingerprint_error_message_sensor_error),
                        true);
                return;
            } else if (!this.mGFingerprintManager.hasEnrolledFingerprints(mUserId)) {
                runRegister();
                return;
            } else {
                setResult(-1);
                startFragment(this.mContext);
                return;
            }
        }
        if (i == 102) {
            FingerprintManager fingerprintManager = this.mGFingerprintManager;
            if (fingerprintManager == null
                    || !fingerprintManager.hasEnrolledFingerprints(mUserId)) {
                finish();
                return;
            }
            if (intent != null
                    && (bundleExtra = intent.getBundleExtra(":settings:show_fragment_args"))
                            != null) {
                this.mGkPwHandle = bundleExtra.getLong("gk_pw_handle", 0L);
                byte[] byteArray = bundleExtra.getByteArray("hw_auth_token");
                if (byteArray != null) {
                    this.mToken = byteArray;
                    this.mChallenge = bundleExtra.getLong("challenge", this.mChallenge);
                }
            }
            startFragment(this.mContext);
            return;
        }
        if (i != 1030) {
            super.onActivityResult(i, i2, intent);
            return;
        }
        if (i2 != -1 || !BiometricsGenericHelper.isMandatoryBiometricsAccessible(this.mContext)) {
            finish();
            return;
        }
        this.mIsSkipMandatoryBiometrics = true;
        if ("fingerprint_register_external".equals(this.mPreviousStage)) {
            runRegister();
            return;
        }
        if (this.mGFingerprintManager.hasEnrolledFingerprints(mUserId)) {
            startFragment(this.mContext);
        } else if (this.mGkPwHandle == 0) {
            launchConfirmLock();
        } else {
            runRegister();
        }
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Log.secD("FpstFingerprintEntry", "=====onConfigurationChanged");
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        boolean z;
        String string;
        super.onCreate(bundle);
        Log.d("FpstFingerprintEntry", "=====onCreate()");
        this.mContext = this;
        this.mSemMultiWindowManager = new SemMultiWindowManager();
        FingerprintEntry fingerprintEntry = this.mContext;
        boolean z2 = FingerprintSettingsUtils.SUB_DISPLAY_IS_LARGE_SCREEN;
        if (fingerprintEntry != null) {
            z = SecurityUtils.ConnectedMobileKeypad(fingerprintEntry);
        } else {
            Log.e(
                    "FpstFingerprintSettingsUtils",
                    "isNotAvailableFingerprintWithMobileKeyboard : config ="
                        + " google_touch_display_ultrasonic context = "
                            + fingerprintEntry);
            z = false;
        }
        if (z) {
            Log.d("FpstFingerprintEntry", "Mobile keyboard attached");
            Toast.makeText(
                            this.mContext,
                            getString(
                                    R.string.sec_fingerprint_keyboard_toast_msg,
                                    new Object[] {getString(R.string.sec_fingerprint)}),
                            0)
                    .show();
            finish();
            return;
        }
        if (new FingerprintSettingsPreferenceController(this.mContext).getAvailabilityStatus()
                == 3) {
            Toast.makeText(this.mContext, getString(R.string.sec_fingerprint_unable_to_register), 0)
                    .show();
            Log.d("FpstFingerprintEntry", "FingerprintSettingsPreferenceController unsupported");
            finish();
            return;
        }
        if (SecurityUtils.isWinnerProduct() && this.mSemMultiWindowManager.getMode() == 2) {
            boolean semExitMultiWindowMode = semExitMultiWindowMode();
            Toast.makeText(
                            this,
                            getString(R.string.sec_biometrics_common_not_use_multi_window_view),
                            1)
                    .show();
            if (!semExitMultiWindowMode) {
                Log.d("FpstFingerprintEntry", "Fail to split view to full screen");
                finish();
                return;
            }
        } else if (SecurityUtils.isNotAvailableBiometricsWithDexAndMultiWindow(
                this, R.string.bio_fingerprint_sanner_title, "FpstFingerprintEntry")) {
            finish();
            return;
        }
        if (getIntent() != null && getIntent().hasExtra(":settings:fragment_args_key")) {
            this.key = getIntent().getStringExtra(":settings:fragment_args_key");
        }
        this.mIsFromBiometricsMenu = getIntent().getBooleanExtra("fromBiometricsMenu", false);
        this.mIsFromFragment = getIntent().getBooleanExtra("from_biometric_fragment", false);
        mUserId = getIntent().getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId());
        this.mPreviousStage = getIntent().getStringExtra("previousStage");
        this.mIsKnox = SemPersonaManager.isKnoxId(mUserId);
        this.mSensorStatus = getIntent().getIntExtra("sensor_status", -1);
        this.mGkPwHandle = GatekeeperPasswordProvider.getGatekeeperPasswordHandle(getIntent());
        this.mGFingerprintManager =
                (FingerprintManager) this.mContext.getSystemService("fingerprint");
        if (SemPersonaManager.isSecureFolderId(mUserId) || SemDualAppManager.isDualAppId(mUserId)) {
            mUserId = 0;
            Log.secD(
                    "FpstFingerprintEntry",
                    "SecureFolder or DualApp use owner's fingerprint. mUserId : " + mUserId);
        }
        Log.secD("FpstFingerprintEntry", "mUserId : " + mUserId);
        Log.secD("FpstFingerprintEntry", "mIsAfw : false, mIsKnox : " + this.mIsKnox);
        if (UCMUtils.isUCMKeyguardEnabledAsUser(mUserId)
                && !UCMUtils.isSupportBiometricForUCM(mUserId)) {
            Log.d("FpstFingerprintEntry", "UCMKeyguard enabled");
            finish();
            return;
        }
        FingerprintManager fingerprintManager = this.mGFingerprintManager;
        if (fingerprintManager == null) {
            Log.secE("FpstFingerprintEntry", "onCreate mGFingerprintManager == null");
            FingerprintSettingsUtils.showSensorErrorDialog(
                    this,
                    getString(R.string.sec_fingerprint_attention),
                    getString(R.string.sec_fingerprint_error_message_sensor_error),
                    true);
            return;
        }
        boolean isHardwareDetected = fingerprintManager.isHardwareDetected();
        int i = this.mSensorStatus;
        if (i == -1) {
            i = this.mGFingerprintManager.semGetSensorStatus();
        }
        if (!isHardwareDetected || (i != 100040 && i != 100041)) {
            Log.e(
                    "FpstFingerprintEntry",
                    "onCreate isHardwareDetected = "
                            + isHardwareDetected
                            + ", semGetSensorStatus = "
                            + i);
            String string2 = getString(R.string.sec_fingerprint_error_title_not_responding);
            if (i != 100045) {
                string = getString(R.string.sec_fingerprint_error_message_sensor_error);
            } else {
                string2 = getString(R.string.sec_fingerprint_error_title_calibration);
                string = getString(R.string.sec_fingerprint_error_contact_customer_service);
            }
            FingerprintSettingsUtils.showSensorErrorDialog(this, string2, string, true);
            return;
        }
        LockPatternUtils lockPatternUtils = new LockPatternUtils(this.mContext);
        if (bundle != null) {
            boolean z3 = bundle.getBoolean("IsWaitingResult");
            this.mIsWaitingResult = z3;
            if (z3) {
                return;
            }
        }
        Settings.System.putInt(this.mContext.getContentResolver(), "fingerprint_guide_shown", 0);
        FingerprintEntry fingerprintEntry2 = this.mContext;
        int i2 = mUserId;
        Log.d("FpstFingerprintSettingsUtils", "setFingerprintSettingsCreateValue : true");
        Settings.Secure.putIntForUser(
                fingerprintEntry2.getContentResolver(), "fingerprint_settings_create", 1, i2);
        if (TheftProtectionUtils.isMandatoryBiometricEnabled(this.mContext)) {
            FingerprintManager fingerprintManager2 = this.mGFingerprintManager;
            String str = this.mPreviousStage;
            int i3 = mUserId;
            if ((!"fingerprint_register_external".equals(str)
                            && !"google_setupwizard_fingerprint".equals(str))
                    || FingerprintSettingsUtils.getEnrolledFingerNumber(fingerprintManager2, i3)
                            != FingerprintSettingsUtils.getMaxFingerEnroll(fingerprintManager2)) {
                this.mIsWaitingResult = true;
                startActivityForResult(
                        BiometricsGenericHelper.getMandatoryBiometricIntentForBiometricsSetting(
                                this.mContext,
                                1,
                                "fingerprint_register_external".equals(this.mPreviousStage)),
                        1030);
                return;
            }
        }
        if ("fingerprint_register_external".equals(this.mPreviousStage)) {
            runRegister();
            return;
        }
        boolean isSecure = lockPatternUtils.isSecure(mUserId);
        if (this.mGFingerprintManager.hasEnrolledFingerprints(mUserId) && isSecure) {
            startFragment(this.mContext);
        } else if (isSecure
                && this.mGkPwHandle == 0
                && ActivityEmbeddingController.getInstance(this).isActivityEmbedded(this)) {
            launchConfirmLock();
        } else {
            runRegister();
        }
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        Log.d("FpstFingerprintEntry", "=====onDestroy()");
        super.onDestroy();
    }

    @Override // android.app.Activity
    public final void onPause() {
        Log.d("FpstFingerprintEntry", "onPause");
        super.onPause();
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
    }

    @Override // android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        Log.d("FpstFingerprintEntry", "onSaveInstanceState");
        bundle.putBoolean("IsWaitingResult", this.mIsWaitingResult);
        super.onSaveInstanceState(bundle);
    }

    @Override // android.app.Activity
    public final void onStop() {
        Log.d("FpstFingerprintEntry", "=====onStop()");
        if (!((KeyguardManager) this.mContext.getSystemService("keyguard")).isDeviceSecure(mUserId)
                && !this.mIsWaitingResult) {
            finish();
        }
        super.onStop();
    }

    public final void runRegister() {
        Log.d(
                "FpstFingerprintEntry",
                "There is no enrolled fingerprint. Run RegisterFingerprint!!");
        FingerprintEntry fingerprintEntry = this.mContext;
        if (fingerprintEntry != null) {}
        if (Settings.Global.getInt(getContentResolver(), "always_finish_activities", 0) != 0) {
            String string =
                    getResources()
                            .getString(
                                    R.string.sec_fingerprint_error_message_always_finish_activities,
                                    getString(R.string.immediately_destroy_activities));
            boolean z = FingerprintSettingsUtils.SUB_DISPLAY_IS_LARGE_SCREEN;
            FingerprintSettingsUtils.showSensorErrorDialog(
                    this, getString(R.string.sec_fingerprint_attention), string, true);
            return;
        }
        if (LockUtils.isInMultiWindow(this)) {
            Toast.makeText(this, getString(R.string.sec_biometrics_fullscreen_register_finger), 0)
                    .show();
            finish();
            return;
        }
        Intent intent = new Intent().setClass(this, FingerprintLockSettings.class);
        if (!"fingerprint_register_external".equals(this.mPreviousStage)) {
            this.mPreviousStage = "fingerprint_entry";
        }
        intent.putExtra("previousStage", this.mPreviousStage);
        intent.putExtra("gk_pw_handle", this.mGkPwHandle);
        intent.addFlags(536870912);
        intent.putExtra("isAfw", false);
        intent.putExtra("is_knox", this.mIsKnox);
        intent.putExtra("skip_mandatory_biometrics", this.mIsSkipMandatoryBiometrics);
        intent.putExtra("android.intent.extra.USER_ID", mUserId);
        intent.putExtra("from_biometric_fragment", this.mIsFromFragment);
        intent.putExtra("fromBiometricsMenu", this.mIsFromBiometricsMenu);
        try {
            if (this.mIsFromFragment) {
                startActivityForResult(intent, 102);
                this.mIsWaitingResult = true;
            } else {
                startActivity(intent);
                this.mIsWaitingResult = false;
                finish();
            }
        } catch (ActivityNotFoundException unused) {
            Log.d("FpstFingerprintEntry", "runRegister : Activity Not Found !");
        }
    }

    public final void startFragment(Context context) {
        if (this.mGFingerprintManager.semIsTemplateDbCorrupted()) {
            Log.secE("FpstFingerprintEntry", "onCreate DB Corrupt");
            this.mGFingerprintManager.removeAll(
                    mUserId,
                    new FingerprintManager
                            .RemovalCallback() { // from class:
                                                 // com.samsung.android.settings.biometrics.fingerprint.FingerprintEntry.1
                        public final void onRemovalError(
                                Fingerprint fingerprint, int i, CharSequence charSequence) {
                            Log.e(
                                    "FpstFingerprintEntry",
                                    "onRemovalError : " + i + ", " + ((Object) charSequence));
                            FingerprintEntry fingerprintEntry = FingerprintEntry.this;
                            FingerprintSettingsUtils.showSensorErrorDialog(
                                    fingerprintEntry.mContext,
                                    fingerprintEntry.getString(
                                            R.string.sec_fingerprint_error_message_sensor_error),
                                    true);
                        }

                        public final void onRemovalSucceeded(Fingerprint fingerprint, int i) {
                            Log.i("FpstFingerprintEntry", "onRemovalSucceeded");
                            final FingerprintEntry fingerprintEntry = FingerprintEntry.this;
                            int i2 = FingerprintEntry.mUserId;
                            fingerprintEntry.getWindow().setDimAmount(0.5f);
                            fingerprintEntry.getWindow().addFlags(2);
                            AlertDialog create =
                                    new AlertDialog.Builder(
                                                    fingerprintEntry.mContext,
                                                    BiometricsGenericHelper.isLightTheme(
                                                                    fingerprintEntry)
                                                            ? 5
                                                            : 4)
                                            .setTitle(R.string.sec_fingerprint_attention)
                                            .setMessage(
                                                    R.string
                                                            .sec_fingerprint_data_has_been_corrupted)
                                            .setPositiveButton(
                                                    android.R.string.ok,
                                                    new DialogInterface
                                                            .OnClickListener() { // from class:
                                                                                 // com.samsung.android.settings.biometrics.fingerprint.FingerprintEntry$$ExternalSyntheticLambda0
                                                        @Override // android.content.DialogInterface.OnClickListener
                                                        public final void onClick(
                                                                DialogInterface dialogInterface,
                                                                int i3) {
                                                            FingerprintEntry fingerprintEntry2 =
                                                                    FingerprintEntry.this;
                                                            int i4 = FingerprintEntry.mUserId;
                                                            fingerprintEntry2.getClass();
                                                            Log.d(
                                                                    "FpstFingerprintEntry",
                                                                    "showDatabaseFailureDialog");
                                                            fingerprintEntry2.startFragment(
                                                                    fingerprintEntry2.mContext);
                                                        }
                                                    })
                                            .create();
                            create.setOnDismissListener(
                                    new DialogInterface
                                            .OnDismissListener() { // from class:
                                                                   // com.samsung.android.settings.biometrics.fingerprint.FingerprintEntry$$ExternalSyntheticLambda1
                                        @Override // android.content.DialogInterface.OnDismissListener
                                        public final void onDismiss(
                                                DialogInterface dialogInterface) {
                                            FingerprintEntry fingerprintEntry2 =
                                                    FingerprintEntry.this;
                                            int i3 = FingerprintEntry.mUserId;
                                            fingerprintEntry2.finish();
                                        }
                                    });
                            create.show();
                        }
                    });
            return;
        }
        if (context == null) {
            Log.secD("FpstFingerprintEntry", "startFragment : context is null");
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(":settings:fragment_args_key", this.key);
            bundle.putLong("gk_pw_handle", this.mGkPwHandle);
            bundle.putBoolean("isAfw", false);
            bundle.putBoolean("is_knox", this.mIsKnox);
            bundle.putInt("android.intent.extra.USER_ID", mUserId);
            byte[] bArr = this.mToken;
            if (bArr != null) {
                bundle.putByteArray("hw_auth_token", bArr);
                bundle.putLong("challenge", this.mChallenge);
            }
            SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
            String name = FingerprintSettings.class.getName();
            SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
            launchRequest.mDestinationName = name;
            launchRequest.mArguments = bundle;
            launchRequest.mExtras = bundle;
            launchRequest.mSourceMetricsCategory = 9031;
            subSettingLauncher.setTitleRes(R.string.bio_fingerprint_sanner_title, null);
            subSettingLauncher.launch();
            overridePendingTransition(0, 0);
        }
        finish();
    }
}
