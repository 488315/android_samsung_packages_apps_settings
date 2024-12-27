package com.samsung.android.settings.biometrics.fingerprint;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecDropDownPreference;
import androidx.preference.SecPreferenceUtils;
import androidx.preference.SwitchPreference;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.biometrics.BiometricsGenericHelper;
import com.samsung.android.settings.biometrics.BiometricsTipPreference;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.security.SecurityUtils;
import com.samsung.android.settings.widget.SecBottomBarButton;
import com.samsung.android.settings.widget.SecUnclickablePreference;
import com.sec.ims.configuration.DATA;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FingerprintUsefulFeature extends SettingsActivity {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class FingerprintUsefulFeatureFragment extends SettingsPreferenceFragment
            implements Preference.OnPreferenceChangeListener {
        public static boolean mIsAlwaysOnSetting = false;
        public static boolean mIsFinished = false;
        public static boolean mIsShowIconSetting = false;
        public SwitchPreference mAlwaysOn;
        public FragmentActivity mContext;
        public SecUnclickablePreference mDescription;
        public SwitchPreference mFingerEffect;
        public SwitchPreference mFingerUnlock;
        public long mGkPwHandle;
        public String mPreviousStage;
        public SecDropDownPreference mScreenOffIconAod;
        public SwitchPreference mStayOnLockScreen;
        public int mUserId = 0;
        public boolean mIsFingerUnlockOn = false;

        public final void finishFingerprintUsefulFeature(int i) {
            Log.d("FpstFingerprintUsefulFeature", "finishFingerprintsUsefulFeature");
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
                Log.e("FpstFingerprintUsefulFeature", "intent is null");
                finishFingerprintUsefulFeature(0);
                return;
            }
            this.mContext = getActivity();
            this.mUserId =
                    intent.getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId());
            this.mGkPwHandle = intent.getLongExtra("gk_pw_handle", 0L);
            this.mPreviousStage = intent.getStringExtra("previousStage");
            if (bundle != null) {
                this.mIsFingerUnlockOn = bundle.getBoolean("unlock_state", true);
            } else {
                this.mIsFingerUnlockOn = !"fingerprint_register_external".equals(r0);
            }
            Log.d("FpstFingerprintUsefulFeature", "User ID : " + this.mUserId);
            if (SecurityUtils.isNotAvailableBiometricsWithDexAndMultiWindow(
                    getActivity(),
                    R.string.sec_fingerprint_useful_feature_title,
                    "FpstFingerprintUsefulFeature")) {
                finishFingerprintUsefulFeature(0);
            }
        }

        @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.fragment.app.Fragment
        public final void onDestroy() {
            Log.d("FpstFingerprintUsefulFeature", "onDestroy");
            Log.d("FpstFingerprintUsefulFeature", "sendFingerprintFeatureLogging");
            try {
                SwitchPreference switchPreference = this.mFingerUnlock;
                String str = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
                if (switchPreference != null) {
                    LoggingHelper.insertEventLogging(
                            8286,
                            8224,
                            0L,
                            switchPreference.mChecked ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
                }
                FingerprintSettingsUtils.isSupportFingerprintAlwaysOn();
                LoggingHelper.insertEventLogging(
                        8286,
                        8263,
                        0L,
                        this.mAlwaysOn.mChecked ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
                FingerprintSettingsUtils.isSupportFingerprintAlwaysOnType();
                SwitchPreference switchPreference2 = this.mStayOnLockScreen;
                if (switchPreference2 != null) {
                    LoggingHelper.insertEventLogging(
                            8286,
                            8291,
                            0L,
                            switchPreference2.mChecked ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
                }
                if (FingerprintSettingsUtils.isSupportFingerprintScreenOffIconAod()) {
                    LoggingHelper.insertEventLogging(8286, 8276, 0L, this.mScreenOffIconAod.mValue);
                }
                LoggingHelper.insertEventLogging(
                        8286,
                        8289,
                        0L,
                        String.valueOf(
                                FingerprintSettingsUtils.getFingerprintLockIconValue(
                                        this.mContext, this.mUserId)));
                if (this.mFingerEffect.mChecked) {
                    str = "1";
                }
                LoggingHelper.insertEventLogging(8286, 8280, 0L, str);
            } catch (NullPointerException e) {
                Log.e(
                        "FpstFingerprintUsefulFeature",
                        "sendFingerprintsFeatureLogging: error fail to send  SA logging");
                e.printStackTrace();
            }
            Log.d("FpstFingerprintUsefulFeature", "setFinishValue : false");
            mIsFinished = false;
            super.onDestroy();
        }

        @Override // com.android.settings.core.InstrumentedPreferenceFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.fragment.app.Fragment
        public final void onPause() {
            ActionBarContextView$$ExternalSyntheticOutline0.m(
                    new StringBuilder("onPause : "), mIsFinished, "FpstFingerprintUsefulFeature");
            if (!mIsFinished
                    && !getActivity().isChangingConfigurations()
                    && !mIsAlwaysOnSetting
                    && !mIsShowIconSetting) {
                new Intent().putExtra("biometrics_settings_destroy", true);
                finishFingerprintUsefulFeature(-1);
                finish();
            }
            mIsShowIconSetting = false;
            mIsAlwaysOnSetting = false;
            super.onPause();
        }

        @Override // androidx.preference.Preference.OnPreferenceChangeListener
        public final boolean onPreferenceChange(Preference preference, Object obj) {
            if (preference instanceof SecDropDownPreference) {
                MainClearConfirm$$ExternalSyntheticOutline0.m(
                        new StringBuilder("==onPreferenceChange : "),
                        (String) obj,
                        "FpstFingerprintUsefulFeature");
            } else {
                Log.d("FpstFingerprintUsefulFeature", "==onPreferenceChange : " + ((Boolean) obj));
            }
            if (preference.equals(this.mFingerUnlock)) {
                LockPatternUtils lockPatternUtils = new LockPatternUtils(this.mContext);
                boolean booleanValue = ((Boolean) obj).booleanValue();
                this.mIsFingerUnlockOn = booleanValue;
                if (booleanValue) {
                    FingerprintSettingsUtils.setFingerprintLock(
                            this.mUserId, this.mContext, lockPatternUtils);
                } else {
                    FingerprintSettingsUtils.removeFingerprintLock(
                            this.mUserId, this.mContext, lockPatternUtils);
                }
                SwitchPreference switchPreference = this.mAlwaysOn;
                if (switchPreference != null) {
                    switchPreference.setEnabled(this.mIsFingerUnlockOn);
                    SecDropDownPreference secDropDownPreference = this.mScreenOffIconAod;
                    if (secDropDownPreference != null) {
                        secDropDownPreference.setEnabled(
                                this.mIsFingerUnlockOn && this.mAlwaysOn.mChecked);
                    }
                }
                SwitchPreference switchPreference2 = this.mStayOnLockScreen;
                if (switchPreference2 != null) {
                    switchPreference2.setEnabled(this.mIsFingerUnlockOn);
                }
                SwitchPreference switchPreference3 = this.mFingerEffect;
                if (switchPreference3 != null) {
                    switchPreference3.setEnabled(this.mIsFingerUnlockOn);
                }
            } else if (preference.equals(this.mAlwaysOn)) {
                Boolean bool = (Boolean) obj;
                FingerprintSettingsUtils.setFingerprintAlwaysOnValue(
                        this.mContext, this.mUserId, bool.booleanValue());
                SecDropDownPreference secDropDownPreference2 = this.mScreenOffIconAod;
                if (secDropDownPreference2 != null) {
                    secDropDownPreference2.setEnabled(bool.booleanValue());
                }
            } else if (preference.equals(this.mStayOnLockScreen)) {
                FingerprintSettingsUtils.setStayOnLockScreen(
                        this.mContext, this.mUserId, ((Boolean) obj).booleanValue());
            } else if (preference.equals(null)) {
                ((Boolean) obj).getClass();
                boolean z = FingerprintSettingsUtils.SUB_DISPLAY_IS_LARGE_SCREEN;
                Log.d(
                        "FpstFingerprintSettingsUtils",
                        "setFingerprintScreenOffIconValue: not supported device");
            } else if (preference.equals(this.mScreenOffIconAod)) {
                int parseInt = Integer.parseInt((String) obj);
                SecDropDownPreference secDropDownPreference3 = this.mScreenOffIconAod;
                CharSequence[] charSequenceArr = secDropDownPreference3.mEntries;
                int length = (charSequenceArr.length - 1) - parseInt;
                secDropDownPreference3.setSummary(charSequenceArr[length]);
                this.mScreenOffIconAod.setValueIndex(length);
                FingerprintSettingsUtils.setFingerprintScreenOffIconAodValue(
                        getActivity(), parseInt, this.mUserId);
            } else if (preference.equals(this.mFingerEffect)) {
                FingerprintSettingsUtils.setFingerprintEffectValue(
                        this.mContext, this.mUserId, ((Boolean) obj).booleanValue());
            } else {
                Log.e("FpstFingerprintUsefulFeature", "Wrong preference");
            }
            return true;
        }

        @Override // com.android.settings.core.InstrumentedPreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat,
                  // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
        public final boolean onPreferenceTreeClick(Preference preference) {
            Log.secD("FpstFingerprintUsefulFeature", "==onPreferenceTreeClick : " + preference);
            if (preference.getKey().equals("set_always_on_type")) {
                mIsAlwaysOnSetting = true;
                Bundle bundle = new Bundle();
                bundle.putInt("android.intent.extra.USER_ID", this.mUserId);
                bundle.putBoolean("fingerprint_useful_feature", true);
                SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
                String name = FingerprintAlwaysOnSettings.class.getName();
                SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
                launchRequest.mDestinationName = name;
                subSettingLauncher.setResultListener(
                        this, EnterpriseContainerCallback.CONTAINER_MOUNT_STATUS);
                launchRequest.mArguments = bundle;
                launchRequest.mSourceMetricsCategory = 8285;
                subSettingLauncher.launch();
                return super.onPreferenceTreeClick(preference);
            }
            if (!preference.getKey().equals("show_fingerprint_icon")) {
                return true;
            }
            mIsShowIconSetting = true;
            Bundle bundle2 = new Bundle();
            bundle2.putInt("android.intent.extra.USER_ID", this.mUserId);
            bundle2.putBoolean("fingerprint_useful_feature", true);
            SubSettingLauncher subSettingLauncher2 = new SubSettingLauncher(this.mContext);
            String name2 = FingerprintShowIconSettings.class.getName();
            SubSettingLauncher.LaunchRequest launchRequest2 = subSettingLauncher2.mLaunchRequest;
            launchRequest2.mDestinationName = name2;
            subSettingLauncher2.setResultListener(
                    this, EnterpriseContainerCallback.CONTAINER_PACKAGE_INFORMATION);
            launchRequest2.mArguments = bundle2;
            launchRequest2.mSourceMetricsCategory = 8288;
            subSettingLauncher2.launch();
            return super.onPreferenceTreeClick(preference);
        }

        @Override // com.android.settings.SettingsPreferenceFragment,
                  // com.android.settings.core.InstrumentedPreferenceFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.fragment.app.Fragment
        public final void onResume() {
            Resources resources;
            super.onResume();
            Log.d("FpstFingerprintUsefulFeature", "onResume");
            PreferenceScreen preferenceScreen = getPreferenceScreen();
            if (preferenceScreen != null) {
                preferenceScreen.removeAll();
            }
            addPreferencesFromResource(R.xml.sec_fingerprint_useful_feature);
            Log.d("FpstFingerprintUsefulFeature", "setFingerprintUsefulFeaturePreference");
            SecUnclickablePreference secUnclickablePreference =
                    (SecUnclickablePreference)
                            findPreference("fingerprint_useful_description_text");
            this.mDescription = secUnclickablePreference;
            if (secUnclickablePreference != null) {
                if (FingerprintSettingsUtils.getEnrolledFingerNumber(
                                Utils.getFingerprintManagerOrNull(this.mContext), this.mUserId)
                        == 1) {
                    this.mDescription.setTitle(R.string.sec_fingerprint_useful_feature_msg_2);
                } else {
                    this.mDescription.setTitle(R.string.sec_fingerprint_useful_feature_msg_4);
                }
            }
            SwitchPreference switchPreference =
                    (SwitchPreference) findPreference("set_screen_lock");
            this.mFingerUnlock = switchPreference;
            if (switchPreference != null) {
                switchPreference.setOnPreferenceChangeListener(this);
                if (SecurityUtils.isFingerprintDisabled(this.mContext, this.mUserId)) {
                    this.mFingerUnlock.setEnabled(false);
                } else {
                    this.mFingerUnlock.setChecked(this.mIsFingerUnlockOn);
                }
            }
            FingerprintSettingsUtils.isSupportFingerprintAlwaysOn();
            SwitchPreference switchPreference2 = (SwitchPreference) findPreference("set_always_on");
            this.mAlwaysOn = switchPreference2;
            if (switchPreference2 != null) {
                switchPreference2.setEnabled(this.mIsFingerUnlockOn);
                try {
                    resources =
                            getPackageManager()
                                    .getResourcesForApplication(
                                            "com.samsung.android.biometrics.app.setting");
                } catch (PackageManager.NameNotFoundException | Resources.NotFoundException e) {
                    Log.w(
                            "FpstFingerprintUsefulFeature",
                            "setFingerprintUsefulFeaturePreference : resources exception = " + e);
                    resources = null;
                }
                if (resources != null) {
                    this.mAlwaysOn.setSummary(
                            resources.getString(
                                    resources.getIdentifier(
                                            "sec_fingerprint_always_on_summary",
                                            "string",
                                            "com.samsung.android.biometrics.app.setting")));
                }
                this.mAlwaysOn.setChecked(
                        FingerprintSettingsUtils.getFingerprintAlwaysOnBooleanValue(
                                getActivity(), this.mUserId));
                this.mAlwaysOn.setOnPreferenceChangeListener(this);
            }
            FingerprintSettingsUtils.isSupportFingerprintAlwaysOnType();
            removePreference("set_always_on_type");
            SwitchPreference switchPreference3 =
                    (SwitchPreference) findPreference("stay_on_lock_screen");
            this.mStayOnLockScreen = switchPreference3;
            if (switchPreference3 != null) {
                getActivity();
                removePreference("stay_on_lock_screen");
                this.mStayOnLockScreen = null;
            }
            removePreference("screen_off_icon");
            if (FingerprintSettingsUtils.isSupportFingerprintScreenOffIconAod()) {
                SecDropDownPreference secDropDownPreference =
                        (SecDropDownPreference) findPreference("screen_off_icon_aod");
                this.mScreenOffIconAod = secDropDownPreference;
                if (secDropDownPreference != null) {
                    int fingerprintScreenOffIconAodDbValue =
                            FingerprintSettingsUtils.getFingerprintScreenOffIconAodDbValue(
                                    getActivity(), this.mUserId);
                    ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                            fingerprintScreenOffIconAodDbValue,
                            "iconOptionType : ",
                            "FpstFingerprintUsefulFeature");
                    this.mScreenOffIconAod.setEntries(
                            R.array.fingerprint_useful_feature_screen_off_type);
                    this.mScreenOffIconAod.mEntryValues =
                            new CharSequence[] {
                                Integer.toString(2), Integer.toString(1), Integer.toString(0)
                            };
                    SecDropDownPreference secDropDownPreference2 = this.mScreenOffIconAod;
                    CharSequence[] charSequenceArr = secDropDownPreference2.mEntries;
                    int length = (charSequenceArr.length - 1) - fingerprintScreenOffIconAodDbValue;
                    secDropDownPreference2.setSummary(charSequenceArr[length]);
                    this.mScreenOffIconAod.setValueIndex(length);
                    SecDropDownPreference secDropDownPreference3 = this.mScreenOffIconAod;
                    secDropDownPreference3.getClass();
                    SecPreferenceUtils.applySummaryColor(secDropDownPreference3, true);
                    this.mScreenOffIconAod.setOnPreferenceChangeListener(this);
                    this.mScreenOffIconAod.setEnabled(
                            FingerprintSettingsUtils.getFingerprintAlwaysOnBooleanValue(
                                            getActivity(), this.mUserId)
                                    && this.mIsFingerUnlockOn);
                }
            } else {
                removePreference("screen_off_icon_aod");
            }
            removePreference("show_fingerprint_icon");
            SwitchPreference switchPreference4 =
                    (SwitchPreference) findPreference("fingerprint_effect");
            this.mFingerEffect = switchPreference4;
            if (switchPreference4 != null) {
                switchPreference4.setChecked(
                        FingerprintSettingsUtils.getFingerprintEffectValue(
                                getActivity(), this.mUserId));
                this.mFingerEffect.setOnPreferenceChangeListener(this);
                this.mFingerEffect.setEnabled(this.mIsFingerUnlockOn);
            }
            if (SecurityUtils.isEnrolledFace(this.mContext, this.mUserId)
                    || SecurityUtils.isFaceDisabled(this.mContext, this.mUserId)
                    || SemPersonaManager.isKnoxId(this.mUserId)
                    || "fingerprint_register_external".equals(this.mPreviousStage)) {
                removePreference("key_face_enroll_tip");
                return;
            }
            BiometricsTipPreference biometricsTipPreference =
                    (BiometricsTipPreference) findPreference("key_face_enroll_tip");
            biometricsTipPreference.mBiometricType = 256;
            biometricsTipPreference.mGkPwHandle = this.mGkPwHandle;
            biometricsTipPreference.mUserId = this.mUserId;
        }

        @Override // com.android.settings.SettingsPreferenceFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);
            bundle.putBoolean("unlock_state", this.mIsFingerUnlockOn);
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
                                                         // com.samsung.android.settings.biometrics.fingerprint.FingerprintUsefulFeature$FingerprintUsefulFeatureFragment$$ExternalSyntheticLambda0
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view2) {
                                    FingerprintUsefulFeature.FingerprintUsefulFeatureFragment
                                            fingerprintUsefulFeatureFragment =
                                                    FingerprintUsefulFeature
                                                            .FingerprintUsefulFeatureFragment.this;
                                    fingerprintUsefulFeatureFragment.getClass();
                                    Log.d("FpstFingerprintUsefulFeature", "onClick : OK");
                                    BiometricsGenericHelper.insertSaLog(
                                            fingerprintUsefulFeatureFragment.mContext, 8286, 8287);
                                    fingerprintUsefulFeatureFragment.finishFingerprintUsefulFeature(
                                            -1);
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
        intent.putExtra(
                ":settings:show_fragment", FingerprintUsefulFeatureFragment.class.getName());
        return intent;
    }

    @Override // com.android.settings.SettingsActivity
    public final boolean isValidFragment(String str) {
        return FingerprintUsefulFeatureFragment.class.getName().equals(str);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void onBackPressed() {
        Log.d("FpstFingerprintUsefulFeature", "onBackPressed");
        Log.d("FpstFingerprintUsefulFeature", "setFinishValue : true");
        FingerprintUsefulFeatureFragment.mIsFinished = true;
        super.onBackPressed();
    }

    @Override // com.android.settings.SettingsActivity,
              // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LoggingHelper.insertFlowLogging(8286);
        LoggingHelper.insertEntranceLogging(8287);
    }
}
