package com.samsung.android.settings.biometrics.face;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.face.FaceManager;
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
public class FaceEntry extends Activity {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass1();
    public static int mUserId;
    public FaceEntry mContext;
    public boolean mIsFromFragment;
    public String mPreviousStage;
    public SemMultiWindowManager mSemMultiWindowManager;
    public String key = ApnSettings.MVNO_NONE;
    public int mSensorId = -1;
    public byte[] mToken = null;
    public long mChallenge = 0;
    public long mGkPwHandle = 0;
    public boolean mIsWaitingResult = false;
    public boolean mIsKnox = false;
    public FaceManager mFaceManager = null;
    public boolean mIsNeedFmmPopup = false;
    public boolean mIsSkipMandatoryBiometrics = false;
    public LockPatternUtils mLockPatternUtils = null;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.biometrics.face.FaceEntry$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            FaceManager faceManagerOrNull;
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            boolean z = !SecurityUtils.isEnrolledFace(context, FaceEntry.mUserId);
            if (z) {
                ArrayList arrayList = (ArrayList) nonIndexableKeys;
                arrayList.add("key_facelock_remove");
                arrayList.add("key_facelock_unlock");
                arrayList.add("key_face_about_face_recognition");
            } else {
                ((ArrayList) nonIndexableKeys).add("key_facelock_register");
            }
            if (z || !FaceSettingsHelper.isSupportBioFaceAlternativeFace()) {
                ArrayList arrayList2 = (ArrayList) nonIndexableKeys;
                arrayList2.add("key_facelock_register_alternative");
                arrayList2.add("key_facelock_remove_alternative");
            } else {
                int i = FaceEntry.mUserId;
                if (FaceSettingsHelper.isSupportBioFaceAlternativeFace()
                        && (faceManagerOrNull = Utils.getFaceManagerOrNull(context)) != null) {
                    List enrolledFaces = faceManagerOrNull.getEnrolledFaces(i);
                    if (enrolledFaces == null || enrolledFaces.isEmpty()) {
                        Log.d("FcstFaceSettingsHelper", "faceList is null or size is 0");
                    } else if (FaceSettingsHelper.isSupportBioFaceAlternativeFace()
                            && enrolledFaces.size() > 1) {
                        Log.d("FcstFaceSettingsHelper", "There is an enrolled Alternative Face.");
                        ((ArrayList) nonIndexableKeys).add("key_facelock_register_alternative");
                    }
                }
                ((ArrayList) nonIndexableKeys).add("key_facelock_remove_alternative");
            }
            if (z) {
                ((ArrayList) nonIndexableKeys).add("key_facelock_stay_on_lock_screen");
            } else {
                boolean z2 = FaceSettingsHelper.IS_SUPPORT_FACE_DAEMON_DETECT_OPEN_EYES;
            }
            if (z || !FaceSettingsHelper.isSupportBioFaceRecognizeWithMask(context)) {
                ((ArrayList) nonIndexableKeys).add("key_face_recognize_mask");
            }
            if (z || !FaceSettingsHelper.isSupportBioFaceOpenEyes()) {
                ((ArrayList) nonIndexableKeys).add("key_facelock_open_eyes");
            }
            if (z) {
                ((ArrayList) nonIndexableKeys).add("key_facelock_brighten_screen");
            } else {
                boolean z3 = FaceSettingsHelper.IS_SUPPORT_FACE_DAEMON_DETECT_OPEN_EYES;
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            if (Utils.isGuestUser(context)) {
                return null;
            }
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.intentAction = "android.intent.action.MAIN";
            searchIndexableResource.intentTargetPackage =
                    KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG;
            searchIndexableResource.intentTargetClass =
                    "com.samsung.android.settings.biometrics.face.FaceEntry";
            searchIndexableResource.xmlResId = R.xml.sec_face_settings;
            return Arrays.asList(searchIndexableResource);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            if (new FaceSettingsPreferenceController(context).getAvailabilityStatus() == 3) {
                return false;
            }
            return !(this instanceof CombinedBiometricSearchIndexProvider);
        }
    }

    public final void launchConfirmLock() {
        Log.d("FcstFaceEntry", "launchConfirmLock ");
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
        Log.e("FcstFaceEntry", "ConfirmLock Fail");
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
        Log.d("FcstFaceEntry", m.toString());
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
                Log.e("FcstFaceEntry", "CONFIRM_REQUEST: GatekeeperPasswordHandle is 0L.");
                FaceSettingsHelper.showFaceSensorErrorDialog(
                        this.mContext,
                        null,
                        getString(R.string.sec_face_error_message_sensor_error),
                        true);
                return;
            } else if (!this.mFaceManager.hasEnrolledTemplates(mUserId)) {
                runRegister();
                return;
            } else {
                setResult(-1);
                startFragment(this.mContext);
                return;
            }
        }
        if (i == 102) {
            FaceManager faceManager = this.mFaceManager;
            if (faceManager == null || !faceManager.hasEnrolledTemplates(mUserId)) {
                finish();
                return;
            }
            if (intent != null
                    && (bundleExtra = intent.getBundleExtra(":settings:show_fragment_args"))
                            != null) {
                this.mGkPwHandle = bundleExtra.getLong("gk_pw_handle", 0L);
                this.mIsNeedFmmPopup = bundleExtra.getBoolean("need_fmm_popup");
                byte[] byteArray = bundleExtra.getByteArray("hw_auth_token");
                if (byteArray != null) {
                    this.mToken = byteArray;
                    this.mChallenge = bundleExtra.getLong("challenge", this.mChallenge);
                    this.mSensorId = bundleExtra.getInt("sensor_id", this.mSensorId);
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
        if ("face_register_external".equals(this.mPreviousStage)) {
            runRegister();
            return;
        }
        if (this.mFaceManager.hasEnrolledTemplates(mUserId)) {
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
        Log.d("FcstFaceEntry", "=====onConfigurationChanged");
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("FcstFaceEntry", "=====onCreate()");
        this.mContext = this;
        this.mSemMultiWindowManager = new SemMultiWindowManager();
        if (SecurityUtils.isWinnerProduct() && this.mSemMultiWindowManager.getMode() == 2) {
            boolean semExitMultiWindowMode = semExitMultiWindowMode();
            Toast.makeText(
                            this,
                            getString(R.string.sec_biometrics_common_not_use_multi_window_view),
                            1)
                    .show();
            if (!semExitMultiWindowMode) {
                Log.d("FcstFaceEntry", "Fail to split view to full screen");
                finish();
                return;
            }
        } else if (SecurityUtils.isNotAvailableBiometricsWithDexAndMultiWindow(
                this, R.string.bio_face_recognition_title, "FcstFaceEntry")) {
            finish();
            return;
        }
        if (new FaceSettingsPreferenceController(this.mContext).getAvailabilityStatus() == 3) {
            Toast.makeText(this.mContext, getString(R.string.sec_face_error_title_retry), 0).show();
            Log.d("FcstFaceEntry", "FaceSettingsPreferenceController unsupported");
            finish();
            return;
        }
        if (UCMUtils.isUCMKeyguardEnabled() && !UCMUtils.isSupportBiometricForUCM(mUserId)) {
            Log.d("FcstFaceEntry", "UCMKeyguard enabled");
            finish();
            return;
        }
        this.mLockPatternUtils = new LockPatternUtils(this.mContext);
        if (this.mFaceManager == null) {
            this.mFaceManager = Utils.getFaceManagerOrNull(this.mContext);
        }
        if (getIntent() != null && getIntent().hasExtra(":settings:fragment_args_key")) {
            this.key = getIntent().getStringExtra(":settings:fragment_args_key");
        }
        this.mIsFromFragment = getIntent().getBooleanExtra("from_biometric_fragment", false);
        int intExtra =
                getIntent().getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId());
        mUserId = intExtra;
        this.mIsKnox = SemPersonaManager.isKnoxId(intExtra);
        this.mPreviousStage = getIntent().getStringExtra("previousStage");
        this.mGkPwHandle = GatekeeperPasswordProvider.getGatekeeperPasswordHandle(getIntent());
        Log.d("FcstFaceEntry", "mUserId : " + mUserId);
        Log.d("FcstFaceEntry", "mIsAfw : false");
        FaceManager faceManager = this.mFaceManager;
        if (faceManager == null) {
            Log.e("FcstFaceEntry", "Error. mFaceManager is null");
            finish();
            return;
        }
        if (!faceManager.isHardwareDetected()) {
            FaceSettingsHelper.showFaceSensorErrorDialog(
                    this, null, getString(R.string.sec_face_error_message_sensor_error), true);
            return;
        }
        if (bundle != null) {
            boolean z = bundle.getBoolean("IsWaitingResult");
            this.mIsWaitingResult = z;
            if (z) {
                return;
            }
        }
        if (TheftProtectionUtils.isMandatoryBiometricEnabled(this.mContext)
                && (!"face_register_external".equals(this.mPreviousStage)
                        || !this.mFaceManager.hasEnrolledTemplates(mUserId))) {
            this.mIsWaitingResult = true;
            startActivityForResult(
                    BiometricsGenericHelper.getMandatoryBiometricIntentForBiometricsSetting(
                            this.mContext,
                            256,
                            "face_register_external".equals(this.mPreviousStage)),
                    1030);
            return;
        }
        if ("face_register_external".equals(this.mPreviousStage)) {
            runRegister();
            return;
        }
        LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
        boolean z2 = lockPatternUtils != null && lockPatternUtils.isSecure(mUserId);
        if (this.mFaceManager.hasEnrolledTemplates(mUserId) && z2) {
            startFragment(this.mContext);
        } else if (z2
                && this.mGkPwHandle == 0
                && ActivityEmbeddingController.getInstance(this).isActivityEmbedded(this)) {
            launchConfirmLock();
        } else {
            runRegister();
        }
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        Log.d("FcstFaceEntry", "=====onDestroy()");
        super.onDestroy();
    }

    @Override // android.app.Activity
    public final void onPause() {
        Log.d("FcstFaceEntry", "onPause");
        super.onPause();
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        Log.d("FcstFaceEntry", "onResume");
    }

    @Override // android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        Log.d("FcstFaceEntry", "onSaveInstanceState");
        bundle.putBoolean("IsWaitingResult", this.mIsWaitingResult);
        super.onSaveInstanceState(bundle);
    }

    @Override // android.app.Activity
    public final void onStop() {
        Log.d("FcstFaceEntry", "=====onStop()");
        if (!((KeyguardManager) this.mContext.getSystemService("keyguard")).isDeviceSecure(mUserId)
                && !this.mIsWaitingResult) {
            finish();
        }
        super.onStop();
    }

    public final void runRegister() {
        Log.d("FcstFaceEntry", "There is no enrolled face. Run FaceLockSettings!!");
        if (Settings.Global.getInt(getContentResolver(), "always_finish_activities", 0) != 0) {
            FaceSettingsHelper.showFaceSensorErrorDialog(
                    this,
                    getResources().getString(R.string.sec_fingerprint_attention),
                    getResources()
                            .getString(
                                    R.string.sec_face_error_message_always_finish_activities,
                                    getString(R.string.immediately_destroy_activities)),
                    true);
            return;
        }
        if (LockUtils.isInMultiWindow(this)) {
            Toast.makeText(this, getString(R.string.sec_biometrics_fullscreen_register_face), 0)
                    .show();
            finish();
            return;
        }
        Intent intent = new Intent().setClass(this, FaceLockSettings.class);
        if (!"face_register_external".equals(this.mPreviousStage)) {
            this.mPreviousStage = "key_face_settings_entry";
        }
        intent.putExtra("previousStage", this.mPreviousStage);
        intent.putExtra("gk_pw_handle", this.mGkPwHandle);
        intent.addFlags(536870912);
        intent.putExtra("isAfw", false);
        intent.putExtra("skip_mandatory_biometrics", this.mIsSkipMandatoryBiometrics);
        intent.putExtra("is_knox", this.mIsKnox);
        intent.putExtra("android.intent.extra.USER_ID", mUserId);
        intent.putExtra("from_biometric_fragment", this.mIsFromFragment);
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
            Log.e("FcstFaceEntry", "runRegister : Activity Not Found !");
        }
    }

    public final void startFragment(Context context) {
        if (context == null) {
            Log.e("FcstFaceEntry", "startFragment : context is null");
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(":settings:fragment_args_key", this.key);
            bundle.putLong("gk_pw_handle", this.mGkPwHandle);
            bundle.putBoolean("isAfw", false);
            bundle.putBoolean("is_knox", this.mIsKnox);
            bundle.putInt("android.intent.extra.USER_ID", mUserId);
            bundle.putBoolean("identifyFace", false);
            bundle.putBoolean("needTurnOnCheck", false);
            bundle.putBoolean("need_fmm_popup", this.mIsNeedFmmPopup);
            byte[] bArr = this.mToken;
            if (bArr != null) {
                bundle.putByteArray("hw_auth_token", bArr);
                bundle.putLong("challenge", this.mChallenge);
                bundle.putInt("sensor_id", this.mSensorId);
            }
            SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
            String name = FaceSettings.class.getName();
            SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
            launchRequest.mDestinationName = name;
            launchRequest.mArguments = bundle;
            launchRequest.mExtras = bundle;
            launchRequest.mSourceMetricsCategory = 9031;
            subSettingLauncher.setTitleRes(R.string.bio_face_recognition_title, null);
            subSettingLauncher.launch();
            overridePendingTransition(0, 0);
        }
        finish();
    }
}
