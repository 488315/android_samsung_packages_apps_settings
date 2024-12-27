package com.android.settings.biometrics.combination;

import android.content.Context;
import android.content.Intent;
import android.hardware.face.FaceManager;
import android.hardware.face.FaceSensorPropertiesInternal;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts$StartActivityForResult;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;

import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.VerifyCredentialResponse;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.biometrics.BiometricStatusPreferenceController;
import com.android.settings.biometrics.BiometricUtils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.password.ChooseLockSettingsHelper;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.volte2.data.VolteConstants;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BiometricsSettingsBase extends DashboardFragment {
    static final int CONFIRM_REQUEST = 2001;
    static final String RETRY_PREFERENCE_BUNDLE = "retry_preference_bundle";
    static final String RETRY_PREFERENCE_KEY = "retry_preference_key";
    public boolean mConfirmCredential;
    public boolean mDoNotFinishActivity;
    public FaceManager mFaceManager;
    public FingerprintManager mFingerprintManager;
    public long mGkPwHandle;
    public int mUserId;
    public String mRetryPreferenceKey = null;
    public Bundle mRetryPreferenceExtra = null;
    public final ActivityResultLauncher mFaceOrFingerprintPreferenceLauncher =
            registerForActivityResult(
                    new ActivityResultContracts$StartActivityForResult(0),
                    new ActivityResultCallback() { // from class:
                                                   // com.android.settings.biometrics.combination.BiometricsSettingsBase$$ExternalSyntheticLambda0
                        @Override // androidx.activity.result.ActivityResultCallback
                        public final void onActivityResult(Object obj) {
                            ActivityResult activityResult = (ActivityResult) obj;
                            BiometricsSettingsBase biometricsSettingsBase =
                                    BiometricsSettingsBase.this;
                            biometricsSettingsBase.getClass();
                            if (activityResult == null || activityResult.mResultCode != 3) {
                                return;
                            }
                            biometricsSettingsBase.finish();
                        }
                    });

    public abstract String getFacePreferenceKey();

    public abstract String getFingerprintPreferenceKey();

    public abstract String getUnlockPhonePreferenceKey();

    public String getUseAnyBiometricSummary() {
        FaceManager faceManager = this.mFaceManager;
        int i = 0;
        boolean z = faceManager != null && faceManager.isHardwareDetected();
        FingerprintManager fingerprintManager = this.mFingerprintManager;
        boolean z2 = fingerprintManager != null && fingerprintManager.isHardwareDetected();
        if (z && z2) {
            i = R.string.biometric_settings_use_face_or_fingerprint_preference_summary;
        } else if (z) {
            i = R.string.biometric_settings_use_face_preference_summary;
        } else if (z2) {
            i = R.string.biometric_settings_use_fingerprint_preference_summary;
        }
        return i == 0 ? ApnSettings.MVNO_NONE : getString(i);
    }

    public abstract String getUseInAppsPreferenceKey();

    public void launchChooseOrConfirmLock() {
        ChooseLockSettingsHelper.Builder builder =
                new ChooseLockSettingsHelper.Builder(getActivity(), this);
        builder.mRequestCode = 2001;
        builder.mTitle = getString(R.string.security_settings_biometric_preference_title);
        builder.mRequestGatekeeperPasswordHandle = true;
        builder.mForegroundOnly = true;
        builder.mReturnCredentials = true;
        int i = this.mUserId;
        if (i != -10000) {
            builder.mUserId = i;
        }
        this.mDoNotFinishActivity = true;
        if (builder.show()) {
            return;
        }
        Intent chooseLockIntent = BiometricUtils.getChooseLockIntent(getActivity(), getIntent());
        chooseLockIntent.putExtra("hide_insecure_options", true);
        chooseLockIntent.putExtra("request_gk_pw_handle", true);
        chooseLockIntent.putExtra("for_biometrics", true);
        chooseLockIntent.putExtra("page_transition_type", 1);
        int i2 = this.mUserId;
        if (i2 != -10000) {
            chooseLockIntent.putExtra("android.intent.extra.USER_ID", i2);
        }
        startActivityForResult(
                chooseLockIntent, VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_TIMER_F);
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 2001 || i == 2002) {
            this.mConfirmCredential = false;
            this.mDoNotFinishActivity = false;
            if (i2 != 1 && i2 != -1) {
                Log.d(getLogTag(), "Password not confirmed.");
                finish();
            } else if (BiometricUtils.containsGatekeeperPasswordHandle(intent)) {
                this.mGkPwHandle = BiometricUtils.getGatekeeperPasswordHandle(intent);
                if (!TextUtils.isEmpty(this.mRetryPreferenceKey)) {
                    getActivity()
                            .overridePendingTransition(
                                    R.anim.sud_slide_next_in, R.anim.sud_slide_next_out);
                    String str = this.mRetryPreferenceKey;
                    Bundle bundle = this.mRetryPreferenceExtra;
                    Preference findPreference = findPreference(str);
                    if (findPreference == null) {
                        MotionLayout$$ExternalSyntheticOutline0.m(
                                ".retryPreferenceKey, fail to find ", str, getLogTag());
                    } else {
                        if (bundle != null) {
                            findPreference.getExtras().putAll(bundle);
                        }
                        onRetryPreferenceTreeClick(findPreference, false);
                    }
                }
            } else {
                Log.d(getLogTag(), "Data null or GK PW missing.");
                finish();
            }
            this.mRetryPreferenceKey = null;
            this.mRetryPreferenceExtra = null;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mUserId =
                getActivity()
                        .getIntent()
                        .getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId());
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        boolean z;
        super.onCreate(bundle);
        this.mFaceManager = Utils.getFaceManagerOrNull(getActivity());
        this.mFingerprintManager = Utils.getFingerprintManagerOrNull(getActivity());
        if (BiometricUtils.containsGatekeeperPasswordHandle(getIntent())) {
            this.mGkPwHandle = BiometricUtils.getGatekeeperPasswordHandle(getIntent());
        }
        if (bundle != null) {
            this.mConfirmCredential = bundle.getBoolean("confirm_credential");
            this.mDoNotFinishActivity = bundle.getBoolean("do_not_finish_activity");
            this.mRetryPreferenceKey = bundle.getString(RETRY_PREFERENCE_KEY);
            this.mRetryPreferenceExtra = bundle.getBundle(RETRY_PREFERENCE_BUNDLE);
            if (bundle.containsKey("request_gk_pw_handle")) {
                this.mGkPwHandle = bundle.getLong("request_gk_pw_handle");
            }
        }
        boolean z2 = true;
        if (this.mGkPwHandle == 0 && !this.mConfirmCredential) {
            this.mConfirmCredential = true;
            launchChooseOrConfirmLock();
        }
        Preference findPreference = findPreference(getUnlockPhonePreferenceKey());
        if (findPreference != null) {
            findPreference.setSummary(getUseAnyBiometricSummary());
        }
        Preference findPreference2 = findPreference(getUseInAppsPreferenceKey());
        if (findPreference2 != null) {
            FaceManager faceManager = this.mFaceManager;
            int i = 0;
            if (faceManager != null) {
                Iterator it = faceManager.getSensorPropertiesInternal().iterator();
                while (it.hasNext()) {
                    int i2 = ((FaceSensorPropertiesInternal) it.next()).sensorStrength;
                    if (i2 == 1 || i2 == 2) {
                        z = true;
                        break;
                    }
                }
            }
            z = false;
            FingerprintManager fingerprintManager = this.mFingerprintManager;
            if (fingerprintManager != null) {
                Iterator it2 = fingerprintManager.getSensorPropertiesInternal().iterator();
                while (it2.hasNext()) {
                    int i3 = ((FingerprintSensorPropertiesInternal) it2.next()).sensorStrength;
                    if (i3 == 1 || i3 == 2) {
                        break;
                    }
                }
            }
            z2 = false;
            if (z && z2) {
                i = R.string.biometric_settings_use_face_or_fingerprint_preference_summary;
            } else if (z) {
                i = R.string.biometric_settings_use_face_preference_summary;
            } else if (z2) {
                i = R.string.biometric_settings_use_fingerprint_preference_summary;
            }
            findPreference2.setSummary(i == 0 ? ApnSettings.MVNO_NONE : getString(i));
        }
    }

    public final void onFaceOrFingerprintPreferenceTreeClick(Preference preference) {
        Iterator<List<AbstractPreferenceController>> it = getPreferenceControllers().iterator();
        while (it.hasNext()) {
            for (AbstractPreferenceController abstractPreferenceController : it.next()) {
                if (abstractPreferenceController instanceof BiometricStatusPreferenceController) {
                    BiometricStatusPreferenceController biometricStatusPreferenceController =
                            (BiometricStatusPreferenceController) abstractPreferenceController;
                    if (biometricStatusPreferenceController.setPreferenceTreeClickLauncher(
                            preference, this.mFaceOrFingerprintPreferenceLauncher)) {
                        if (biometricStatusPreferenceController.handlePreferenceTreeClick(
                                preference)) {
                            writePreferenceClickMetric(preference);
                        }
                        biometricStatusPreferenceController.setPreferenceTreeClickLauncher(
                                preference, null);
                        return;
                    }
                }
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        return onRetryPreferenceTreeClick(preference, true)
                || super.onPreferenceTreeClick(preference);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if (this.mConfirmCredential) {
            return;
        }
        this.mDoNotFinishActivity = false;
    }

    public boolean onRetryPreferenceTreeClick(final Preference preference, final boolean z) {
        String key = preference.getKey();
        final Context applicationContext = requireActivity().getApplicationContext();
        if (getFacePreferenceKey().equals(key)) {
            this.mDoNotFinishActivity = true;
            this.mFaceManager.generateChallenge(
                    this.mUserId,
                    new FaceManager.GenerateChallengeCallback() { // from class:
                        // com.android.settings.biometrics.combination.BiometricsSettingsBase$$ExternalSyntheticLambda1
                        public final void onGenerateChallengeResult(int i, int i2, long j) {
                            BiometricsSettingsBase biometricsSettingsBase =
                                    BiometricsSettingsBase.this;
                            Context context = applicationContext;
                            Preference preference2 = preference;
                            boolean z2 = z;
                            FragmentActivity activity = biometricsSettingsBase.getActivity();
                            if (activity == null || activity.isFinishing()) {
                                Log.e(
                                        biometricsSettingsBase.getLogTag(),
                                        "Stop during generating face unlock challenge because"
                                            + " activity is null or finishing");
                                return;
                            }
                            try {
                                byte[] requestGatekeeperHat =
                                        biometricsSettingsBase.requestGatekeeperHat(
                                                context,
                                                biometricsSettingsBase.mGkPwHandle,
                                                biometricsSettingsBase.mUserId,
                                                j);
                                Bundle extras = preference2.getExtras();
                                extras.putByteArray("hw_auth_token", requestGatekeeperHat);
                                extras.putInt("sensor_id", i);
                                extras.putLong("challenge", j);
                                biometricsSettingsBase.onFaceOrFingerprintPreferenceTreeClick(
                                        preference2);
                            } catch (IllegalStateException e) {
                                if (!z2) {
                                    Log.e(
                                            biometricsSettingsBase.getLogTag(),
                                            "face generateChallenge fail",
                                            e);
                                    biometricsSettingsBase.mDoNotFinishActivity = false;
                                } else {
                                    biometricsSettingsBase.mRetryPreferenceKey =
                                            preference2.getKey();
                                    biometricsSettingsBase.mRetryPreferenceExtra =
                                            preference2.getExtras();
                                    biometricsSettingsBase.mConfirmCredential = true;
                                    biometricsSettingsBase.launchChooseOrConfirmLock();
                                }
                            }
                        }
                    });
            return true;
        }
        if (!getFingerprintPreferenceKey().equals(key)) {
            return false;
        }
        this.mDoNotFinishActivity = true;
        this.mFingerprintManager.generateChallenge(
                this.mUserId,
                new FingerprintManager.GenerateChallengeCallback() { // from class:
                    // com.android.settings.biometrics.combination.BiometricsSettingsBase$$ExternalSyntheticLambda2
                    public final void onChallengeGenerated(int i, int i2, long j) {
                        BiometricsSettingsBase biometricsSettingsBase = BiometricsSettingsBase.this;
                        Context context = applicationContext;
                        Preference preference2 = preference;
                        boolean z2 = z;
                        FragmentActivity activity = biometricsSettingsBase.getActivity();
                        if (activity == null || activity.isFinishing()) {
                            Log.e(
                                    biometricsSettingsBase.getLogTag(),
                                    "Stop during generating fingerprint challenge because activity"
                                        + " is null or finishing");
                            return;
                        }
                        try {
                            byte[] requestGatekeeperHat =
                                    biometricsSettingsBase.requestGatekeeperHat(
                                            context,
                                            biometricsSettingsBase.mGkPwHandle,
                                            biometricsSettingsBase.mUserId,
                                            j);
                            Bundle extras = preference2.getExtras();
                            extras.putByteArray("hw_auth_token", requestGatekeeperHat);
                            extras.putLong("challenge", j);
                            biometricsSettingsBase.onFaceOrFingerprintPreferenceTreeClick(
                                    preference2);
                        } catch (IllegalStateException e) {
                            if (!z2) {
                                Log.e(
                                        biometricsSettingsBase.getLogTag(),
                                        "fingerprint generateChallenge fail",
                                        e);
                                biometricsSettingsBase.mDoNotFinishActivity = false;
                            } else {
                                biometricsSettingsBase.mRetryPreferenceKey = preference2.getKey();
                                biometricsSettingsBase.mRetryPreferenceExtra =
                                        preference2.getExtras();
                                biometricsSettingsBase.mConfirmCredential = true;
                                biometricsSettingsBase.launchChooseOrConfirmLock();
                            }
                        }
                    }
                });
        return true;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("confirm_credential", this.mConfirmCredential);
        bundle.putBoolean("do_not_finish_activity", this.mDoNotFinishActivity);
        long j = this.mGkPwHandle;
        if (j != 0) {
            bundle.putLong("request_gk_pw_handle", j);
        }
        if (TextUtils.isEmpty(this.mRetryPreferenceKey)) {
            return;
        }
        bundle.putString(RETRY_PREFERENCE_KEY, this.mRetryPreferenceKey);
        bundle.putBundle(RETRY_PREFERENCE_BUNDLE, this.mRetryPreferenceExtra);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        if (getActivity().isChangingConfigurations() || this.mDoNotFinishActivity) {
            return;
        }
        FragmentActivity activity = getActivity();
        new LockPatternUtils(activity).removeGatekeeperPasswordHandle(this.mGkPwHandle);
        Log.d("BiometricUtils", "Removed handle");
        getActivity().finish();
    }

    public byte[] requestGatekeeperHat(Context context, long j, int i, long j2) {
        VerifyCredentialResponse verifyGatekeeperPasswordHandle =
                new LockPatternUtils(context).verifyGatekeeperPasswordHandle(j, j2, i);
        if (verifyGatekeeperPasswordHandle.isMatched()) {
            return verifyGatekeeperPasswordHandle.getGatekeeperHAT();
        }
        Log.e("BiometricUtils", "Unable to request Gatekeeper HAT");
        return null;
    }
}
