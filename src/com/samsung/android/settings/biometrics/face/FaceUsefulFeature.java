package com.samsung.android.settings.biometrics.face;

import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreference;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.biometrics.BiometricsGenericHelper;
import com.samsung.android.settings.biometrics.BiometricsTipPreference;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.security.SecurityUtils;
import com.samsung.android.settings.widget.SecBottomBarButton;
import com.sec.ims.configuration.DATA;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FaceUsefulFeature extends SettingsActivity {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class FaceUsefulFeatureFragment extends SettingsPreferenceFragment
            implements Preference.OnPreferenceChangeListener {
        public static boolean mIsFinished = false;
        public SwitchPreference mBrightenScreenSwitch;
        public FragmentActivity mContext;
        public SwitchPreference mFaceUnlock;
        public long mGkPwHandle;
        public SwitchPreference mOpenEyesSwitch;
        public String mPreviousStage;
        public SwitchPreference mRecognizeWithMask;
        public SwitchPreference mStayOnLockScreen;
        public int mUserId = 0;
        public boolean mIsFaceUnlockOn = false;

        public final void finishFaceUsefulFeature(int i) {
            Log.d("FcstFaceUsefulFeature", "finishFaceUsefulFeature");
            mIsFinished = true;
            setResult(i);
            finish();
        }

        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 0;
        }

        @Override // com.android.settings.SettingsPreferenceFragment,
                  // com.android.settings.core.InstrumentedPreferenceFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            Intent intent = getIntent();
            if (intent == null) {
                Log.e("FcstFaceUsefulFeature", "intent is null");
                finishFaceUsefulFeature(0);
                return;
            }
            this.mContext = getActivity();
            this.mUserId =
                    intent.getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId());
            this.mGkPwHandle = intent.getLongExtra("gk_pw_handle", 0L);
            this.mPreviousStage = intent.getStringExtra("previousStage");
            if (bundle != null) {
                this.mIsFaceUnlockOn = bundle.getBoolean("unlock_state", true);
            } else {
                this.mIsFaceUnlockOn = !"face_register_external".equals(r0);
            }
            Log.d("FcstFaceUsefulFeature", "User ID : " + this.mUserId);
            if (SecurityUtils.isNotAvailableBiometricsWithDexAndMultiWindow(
                    getActivity(), R.string.bio_face_recognition_title, "FcstFaceUsefulFeature")) {
                finishFaceUsefulFeature(0);
            }
        }

        @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.fragment.app.Fragment
        public final void onDestroy() {
            Log.d("FcstFaceUsefulFeature", "onDestroy");
            Log.d("FcstFaceUsefulFeature", "sendFaceFeatureLogging");
            try {
                SwitchPreference switchPreference = this.mFaceUnlock;
                String str = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
                if (switchPreference != null) {
                    LoggingHelper.insertEventLogging(
                            8424,
                            8404,
                            0L,
                            switchPreference.mChecked ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
                }
                boolean z = FaceSettingsHelper.IS_SUPPORT_FACE_DAEMON_DETECT_OPEN_EYES;
                LoggingHelper.insertEventLogging(
                        8424,
                        8437,
                        0L,
                        this.mStayOnLockScreen.mChecked ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
                if (FaceSettingsHelper.isSupportBioFaceRecognizeWithMask(this.mContext)) {
                    LoggingHelper.insertEventLogging(
                            8424,
                            8456,
                            0L,
                            this.mRecognizeWithMask.mChecked
                                    ? "1"
                                    : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
                }
                if (FaceSettingsHelper.isSupportBioFaceOpenEyes()) {
                    LoggingHelper.insertEventLogging(
                            8424,
                            8447,
                            0L,
                            this.mOpenEyesSwitch.mChecked ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
                }
                if (this.mBrightenScreenSwitch.mChecked) {
                    str = "1";
                }
                LoggingHelper.insertEventLogging(8424, 8430, 0L, str);
            } catch (NullPointerException e) {
                Log.e(
                        "FcstFaceUsefulFeature",
                        "sendFaceFeatureLogging: error fail to send  SA logging");
                e.printStackTrace();
            }
            Log.d("FcstFaceUsefulFeature", "setFinishValue : false");
            mIsFinished = false;
            super.onDestroy();
        }

        @Override // com.android.settings.core.InstrumentedPreferenceFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.fragment.app.Fragment
        public final void onPause() {
            ActionBarContextView$$ExternalSyntheticOutline0.m(
                    new StringBuilder("onPause : "), mIsFinished, "FcstFaceUsefulFeature");
            if (!mIsFinished && !getActivity().isChangingConfigurations()) {
                new Intent().putExtra("biometrics_settings_destroy", true);
                finishFaceUsefulFeature(-1);
            }
            super.onPause();
        }

        @Override // androidx.preference.Preference.OnPreferenceChangeListener
        public final boolean onPreferenceChange(Preference preference, Object obj) {
            StringBuilder m =
                    ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                            "=====onPreferenceChange : ", preference.getKey(), " - ");
            Boolean bool = (Boolean) obj;
            m.append(bool);
            Log.d("FcstFaceUsefulFeature", m.toString());
            if (preference.equals(this.mFaceUnlock)) {
                LockPatternUtils lockPatternUtils = new LockPatternUtils(this.mContext);
                boolean booleanValue = bool.booleanValue();
                this.mIsFaceUnlockOn = booleanValue;
                if (booleanValue) {
                    FaceSettingsHelper.setFaceLock(this.mUserId, this.mContext, lockPatternUtils);
                } else {
                    FaceSettingsHelper.removeFaceLock(
                            this.mUserId, this.mContext, lockPatternUtils);
                }
                SwitchPreference switchPreference = this.mStayOnLockScreen;
                if (switchPreference == null) {
                    return true;
                }
                switchPreference.setEnabled(this.mIsFaceUnlockOn);
                return true;
            }
            if (preference.equals(this.mStayOnLockScreen)) {
                FaceSettingsHelper.setFaceStayOnLockScreen(
                        this.mContext, this.mUserId, bool.booleanValue());
                return true;
            }
            if (preference.equals(this.mRecognizeWithMask)) {
                FragmentActivity fragmentActivity = this.mContext;
                boolean booleanValue2 = bool.booleanValue();
                int i = this.mUserId;
                boolean z = FaceSettingsHelper.IS_SUPPORT_FACE_DAEMON_DETECT_OPEN_EYES;
                Settings.Secure.putIntForUser(
                        fragmentActivity.getContentResolver(),
                        "face_recognize_mask",
                        booleanValue2 ? 1 : 0,
                        i);
                return true;
            }
            if (preference.equals(this.mOpenEyesSwitch)) {
                FaceSettingsHelper.setFaceOpenEyes(
                        this.mContext, this.mUserId, bool.booleanValue());
                return true;
            }
            if (!preference.equals(this.mBrightenScreenSwitch)) {
                Log.e("FcstFaceUsefulFeature", "Wrong preference");
                return true;
            }
            FaceSettingsHelper.setFaceBrightenScreen(
                    this.mContext, this.mUserId, bool.booleanValue());
            return true;
        }

        @Override // com.android.settings.SettingsPreferenceFragment,
                  // com.android.settings.core.InstrumentedPreferenceFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.fragment.app.Fragment
        public final void onResume() {
            FingerprintManager fingerprintManagerOrNull;
            SwitchPreference switchPreference;
            super.onResume();
            Log.d("FcstFaceUsefulFeature", "onResume");
            PreferenceScreen preferenceScreen = getPreferenceScreen();
            if (preferenceScreen != null) {
                preferenceScreen.removeAll();
            }
            addPreferencesFromResource(R.xml.sec_face_useful_face_recognition_feature_preference);
            Log.d("FcstFaceUsefulFeature", "setFaceUsefulFeaturePreference");
            this.mFaceUnlock = (SwitchPreference) findPreference("key_facelock_unlock");
            this.mStayOnLockScreen =
                    (SwitchPreference) findPreference("key_facelock_stay_on_lock_screen");
            this.mRecognizeWithMask = (SwitchPreference) findPreference("key_face_recognize_mask");
            this.mOpenEyesSwitch = (SwitchPreference) findPreference("key_facelock_open_eyes");
            this.mBrightenScreenSwitch =
                    (SwitchPreference) findPreference("key_facelock_brighten_screen");
            SwitchPreference switchPreference2 = this.mFaceUnlock;
            if (switchPreference2 != null) {
                switchPreference2.setOnPreferenceChangeListener(this);
                if (SecurityUtils.isFaceDisabled(this.mContext, this.mUserId)) {
                    this.mFaceUnlock.setEnabled(false);
                } else {
                    this.mFaceUnlock.setChecked(this.mIsFaceUnlockOn);
                }
            }
            boolean z = FaceSettingsHelper.IS_SUPPORT_FACE_DAEMON_DETECT_OPEN_EYES;
            SwitchPreference switchPreference3 = this.mStayOnLockScreen;
            if (switchPreference3 != null) {
                switchPreference3.setOnPreferenceChangeListener(this);
                this.mStayOnLockScreen.setEnabled(this.mIsFaceUnlockOn);
                this.mStayOnLockScreen.setChecked(
                        FaceSettingsHelper.getFaceStayOnLockScreenBooleanValue(
                                getActivity(), this.mUserId));
            } else {
                removePreference("key_facelock_stay_on_lock_screen");
            }
            if (this.mRecognizeWithMask == null
                    || !FaceSettingsHelper.isSupportBioFaceRecognizeWithMask(getActivity())) {
                removePreference("key_face_recognize_mask");
            } else {
                this.mRecognizeWithMask.setChecked(
                        FaceSettingsHelper.getFaceRecognizeMaskDbValue(getActivity(), this.mUserId)
                                == 1);
                this.mRecognizeWithMask.setOnPreferenceChangeListener(this);
            }
            if (!FaceSettingsHelper.isSupportBioFaceOpenEyes()
                    || (switchPreference = this.mOpenEyesSwitch) == null) {
                removePreference("key_facelock_open_eyes");
            } else {
                switchPreference.setChecked(
                        FaceSettingsHelper.getFaceOpenEyesBooleanValue(
                                getActivity(), this.mUserId));
                this.mOpenEyesSwitch.setOnPreferenceChangeListener(this);
            }
            SwitchPreference switchPreference4 = this.mBrightenScreenSwitch;
            if (switchPreference4 != null) {
                switchPreference4.setChecked(
                        FaceSettingsHelper.getFaceBrightenScreenBooleanValue(
                                getActivity(), this.mUserId));
                this.mBrightenScreenSwitch.setOnPreferenceChangeListener(this);
            } else {
                removePreference("key_facelock_brighten_screen");
            }
            if (!SecurityUtils.hasFingerprintFeature(this.mContext)
                    || (((fingerprintManagerOrNull =
                                                    Utils.getFingerprintManagerOrNull(
                                                            this.mContext))
                                            != null
                                    && fingerprintManagerOrNull.hasEnrolledFingerprints())
                            || SecurityUtils.isFingerprintDisabled(this.mContext, this.mUserId)
                            || SemPersonaManager.isKnoxId(this.mUserId)
                            || "face_register_external".equals(this.mPreviousStage))) {
                removePreference("key_fingerprint_enroll_tip");
                return;
            }
            BiometricsTipPreference biometricsTipPreference =
                    (BiometricsTipPreference) findPreference("key_fingerprint_enroll_tip");
            biometricsTipPreference.mBiometricType = 1;
            biometricsTipPreference.mGkPwHandle = this.mGkPwHandle;
            biometricsTipPreference.mUserId = this.mUserId;
        }

        @Override // com.android.settings.SettingsPreferenceFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);
            bundle.putBoolean("unlock_state", this.mIsFaceUnlockOn);
        }

        @Override // com.android.settings.SettingsPreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final void onViewCreated(View view, Bundle bundle) {
            super.onViewCreated(view, bundle);
            ViewGroup viewGroup = (ViewGroup) getActivity().findViewById(R.id.button_bar);
            View inflate =
                    LayoutInflater.from(getActivity())
                            .inflate(
                                    R.layout.sec_biometrics_common_bottom_bar_button,
                                    (ViewGroup) null);
            ((SecBottomBarButton) inflate.findViewById(R.id.button_ok))
                    .setOnClickListener(
                            new View
                                    .OnClickListener() { // from class:
                                                         // com.samsung.android.settings.biometrics.face.FaceUsefulFeature$FaceUsefulFeatureFragment$$ExternalSyntheticLambda0
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view2) {
                                    FaceUsefulFeature.FaceUsefulFeatureFragment
                                            faceUsefulFeatureFragment =
                                                    FaceUsefulFeature.FaceUsefulFeatureFragment
                                                            .this;
                                    faceUsefulFeatureFragment.getClass();
                                    Log.d("FcstFaceUsefulFeature", "onClick : OK");
                                    BiometricsGenericHelper.insertSaLog(
                                            faceUsefulFeatureFragment.mContext, 8424, 8425);
                                    faceUsefulFeatureFragment.finishFaceUsefulFeature(-1);
                                }
                            });
            if (viewGroup != null) {
                viewGroup.removeAllViews();
                LinearLayout.LayoutParams layoutParams =
                        (LinearLayout.LayoutParams) viewGroup.getLayoutParams();
                layoutParams.gravity = 8388613;
                viewGroup.addView(inflate, layoutParams);
                viewGroup.setVisibility(0);
            }
            ((AppCompatActivity) getActivity())
                    .getSupportActionBar()
                    .setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override // com.android.settings.SettingsActivity, android.app.Activity
    public final Intent getIntent() {
        Intent intent = new Intent(super.getIntent());
        intent.putExtra(":settings:show_fragment", FaceUsefulFeatureFragment.class.getName());
        return intent;
    }

    @Override // com.android.settings.SettingsActivity
    public final boolean isValidFragment(String str) {
        return FaceUsefulFeatureFragment.class.getName().equals(str);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void onBackPressed() {
        Log.d("FcstFaceUsefulFeature", "onBackPressed");
        Log.d("FcstFaceUsefulFeature", "setFinishValue : true");
        FaceUsefulFeatureFragment.mIsFinished = true;
        super.onBackPressed();
    }

    @Override // com.android.settings.SettingsActivity,
              // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LoggingHelper.insertFlowLogging(8424);
        LoggingHelper.insertEntranceLogging(8424);
    }
}
