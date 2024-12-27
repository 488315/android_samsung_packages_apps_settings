package com.samsung.android.settings.knox;

import android.app.ActionBar;
import android.app.StatusBarManager;
import android.app.admin.DevicePolicyManager;
import android.app.admin.PasswordMetrics;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.secutil.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreference;

import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.notification.RedactionInterstitial;
import com.android.settings.password.ChooseLockPassword;
import com.android.settings.password.ChooseLockPattern;
import com.android.settingslib.RestrictedPreference;

import com.samsung.android.bio.face.SemBioFaceManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.accounts.HostAuth;
import com.samsung.android.knox.devicesecurity.PasswordPolicy;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.security.SecurityUtils;
import com.samsung.android.settings.widget.SecBottomBarButton;
import com.samsung.android.settings.widget.SecUnclickablePreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class KnoxChooseLockTwoFactor extends SettingsActivity {
    @Override // com.android.settings.SettingsActivity, android.app.Activity
    public final Intent getIntent() {
        Intent intent = new Intent(super.getIntent());
        intent.putExtra(":settings:show_fragment", KnoxChooseLockTwoFactorFragment.class.getName());
        return intent;
    }

    public final boolean isPasswordChangedRequestedOnManagedDevice() {
        KnoxChooseLockTwoFactorFragment knoxChooseLockTwoFactorFragment =
                (KnoxChooseLockTwoFactorFragment)
                        getSupportFragmentManager().findFragmentById(R.id.main_content);
        return knoxChooseLockTwoFactorFragment != null
                && knoxChooseLockTwoFactorFragment.mIsSecondStep
                && SemPersonaManager.isDoEnabled(KnoxChooseLockTwoFactorFragment.sUserId)
                && knoxChooseLockTwoFactorFragment.getContext() != null
                && KnoxUtils.isChangeRequested(
                                knoxChooseLockTwoFactorFragment.getContext(),
                                KnoxChooseLockTwoFactorFragment.sUserId)
                        > 0;
    }

    @Override // com.android.settings.SettingsActivity
    public final boolean isValidFragment(String str) {
        return KnoxChooseLockTwoFactorFragment.class.getName().equals(str);
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity, android.app.Activity,
              // android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4 && isPasswordChangedRequestedOnManagedDevice()) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity, android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        if (isPasswordChangedRequestedOnManagedDevice()) {
            return true;
        }
        setResult(0);
        finish();
        return true;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class KnoxChooseLockTwoFactorFragment extends SettingsPreferenceFragment
            implements Preference.OnPreferenceClickListener,
                    Preference.OnPreferenceChangeListener,
                    View.OnClickListener {
        public static int sCurrentLockTypeIdx = -1;
        public static int sUserId;
        public KnoxTwoStepPasswordController mController;
        public SemBioFaceManager mFaceManager;
        public FingerprintManager mFingerprintManager;
        public long mGkPwHandle;
        public LinearLayout mLayout;
        public LockPatternUtils mLockPatternUtils;
        public LockscreenCredential mNewCredential;
        public int mRequestedMinComplexity;
        public StatusBarManager mStatusBarManager;
        public int mUserId;
        public LockscreenCredential mUserPassword;
        public boolean mIsEnforcedMultifactorNReset = false;
        public boolean mMultifactorAuthEnforced = false;
        public boolean mIsSecondStep = false;
        public boolean mIsSecured = false;
        public boolean mRequestGatekeeperPasswordHandle = false;
        public boolean mOnlyEnforceDevicePasswordRequirement = false;
        public byte[] mToken = null;
        public int mDisableStatusBarCount = 0;
        public DevicePolicyManager mDPM = null;
        public RestrictedPreference mPrefPassword = null;
        public RestrictedPreference mPrefPin = null;
        public RestrictedPreference mPrefPattern = null;
        public SwitchPreference mSwitchPrefFingerprint = null;
        public SecUnclickablePreference mTwoFactorDescription = null;
        public FragmentActivity mActivity = null;
        public LinearLayout bottomButtonLayout = null;
        public SecBottomBarButton layoutBtnLater = null;
        public SecBottomBarButton layoutBtnNext = null;

        public final String getCurrentLockTypeToString() {
            int i = sCurrentLockTypeIdx;
            if (i == 0) {
                return getResources().getString(R.string.sec_unlock_set_unlock_password_title);
            }
            if (i == 2) {
                return getResources().getString(R.string.sec_unlock_set_unlock_pin_title);
            }
            if (i == 3) {
                return getResources().getString(R.string.sec_unlock_set_unlock_pattern_title);
            }
            Log.d("KKG::KnoxChooseLockTwoFactor", "current locktype index is wrong");
            return getResources().getString(R.string.sec_unlock_set_unlock_password_title);
        }

        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 42;
        }

        @Override // androidx.fragment.app.Fragment
        public final void onActivityResult(int i, int i2, Intent intent) {
            super.onActivityResult(i, i2, intent);
            Log.d(
                    "KKG::KnoxChooseLockTwoFactor",
                    "onActivityResult : requestCode : " + i + " resultCode : " + i2);
            if (i == 10007) {
                Log.d("KKG::KnoxChooseLockTwoFactor", "[Two Factor] finger by switch");
                if (i2 == -1) {
                    int i3 = this.mUserId;
                    FingerprintManager fingerprintManager = this.mFingerprintManager;
                    if (fingerprintManager != null
                            && fingerprintManager.hasEnrolledFingerprints(i3)) {
                        this.mSwitchPrefFingerprint.setChecked(true);
                        this.mSwitchPrefFingerprint.setSummary(
                                getResources().getString(R.string.status_registered));
                    }
                } else {
                    this.mSwitchPrefFingerprint.setSummary(ApnSettings.MVNO_NONE);
                    this.mActivity.setResult(0);
                    this.mActivity.finish();
                }
                setEnableNextButton();
                return;
            }
            if (i == 10009) {
                if (i2 == -1) {
                    this.mActivity.setResult(-1);
                    this.mActivity.finish();
                    return;
                }
                return;
            }
            if (i != 10100) {
                Log.d("KKG::KnoxChooseLockTwoFactor", "[Two Factor] else");
                this.mActivity.setResult(0);
                this.mActivity.finish();
                return;
            }
            Log.d("KKG::KnoxChooseLockTwoFactor", "requestCode == REQUEST_TWO_FACTOR_SET_FIRST");
            if (i2 != 1) {
                if (i2 == -1) {
                    Log.d("KKG::KnoxChooseLockTwoFactor", "resultCode == Activity.RESULT_OK");
                    return;
                } else {
                    Log.d("KKG::KnoxChooseLockTwoFactor", "requestCode == else");
                    return;
                }
            }
            Log.d("KKG::KnoxChooseLockTwoFactor", "resultCode == Activity.RESULT_FIRST_USER");
            if (!this.mMultifactorAuthEnforced) {
                KnoxUtils.setTwoFactorValue(getContext(), this.mUserId, 0);
                Log.d("KKG::KnoxChooseLockTwoFactor", "two factor : REMOVE");
            }
            if (intent != null) {
                this.mToken = intent.getByteArrayExtra("hw_auth_token");
                try {
                    this.mNewCredential = intent.getParcelableExtra(HostAuth.PASSWORD);
                } catch (Exception e) {
                    Log.e("KKG::KnoxChooseLockTwoFactor", "exception : " + e.getMessage());
                }
                this.mGkPwHandle = intent.getLongExtra("gk_pw_handle", 0L);
            }
            int activePasswordQuality =
                    new LockPatternUtils(getActivity()).getActivePasswordQuality(this.mUserId);
            KnoxUtils.insertStatusLogForKnox(
                    getActivity(),
                    this.mUserId,
                    activePasswordQuality != 65536
                            ? (activePasswordQuality == 131072 || activePasswordQuality == 196608)
                                    ? 3
                                    : (activePasswordQuality == 262144
                                                    || activePasswordQuality == 327680
                                                    || activePasswordQuality == 393216)
                                            ? 4
                                            : 1
                            : 2);
            PreferenceScreen preferenceScreen = getPreferenceScreen();
            if (preferenceScreen != null) {
                preferenceScreen.removeAll();
                SecUnclickablePreference secUnclickablePreference = this.mTwoFactorDescription;
                if (secUnclickablePreference != null) {
                    secUnclickablePreference.setOrder(1);
                    if (SecurityUtils.hasFingerprintFeature(getActivity())) {
                        if (SemPersonaManager.isDoEnabled(this.mUserId)) {
                            this.mTwoFactorDescription.setTitle(
                                    String.format(
                                            getString(R.string.knox_guide_enroll_twostep_step2_do),
                                            getCurrentLockTypeToString()));
                        } else {
                            this.mTwoFactorDescription.setTitle(
                                    String.format(
                                            getString(
                                                    R.string
                                                            .knox_guide_enroll_twostep_step2_fingeronly_ppp),
                                            getCurrentLockTypeToString(),
                                            KnoxUtils.getKnoxName(getActivity(), this.mUserId)));
                        }
                    }
                    preferenceScreen.addPreference(this.mTwoFactorDescription);
                }
                if (this.mSwitchPrefFingerprint != null
                        && SecurityUtils.hasFingerprintFeature(getActivity())) {
                    this.mSwitchPrefFingerprint.setOrder(3);
                    preferenceScreen.addPreference(this.mSwitchPrefFingerprint);
                    this.mSwitchPrefFingerprint.setChecked(false);
                    this.mSwitchPrefFingerprint.setOnPreferenceChangeListener(this);
                    if (SecurityUtils.isFingerprintDisabled(getActivity(), this.mUserId)) {
                        this.mSwitchPrefFingerprint.setEnabled(false);
                        this.mSwitchPrefFingerprint.setSummary(
                                getResources()
                                        .getString(R.string.unlock_set_unlock_disabled_summary));
                    } else {
                        int i4 = this.mUserId;
                        FingerprintManager fingerprintManager2 = this.mFingerprintManager;
                        if (fingerprintManager2 == null
                                || !fingerprintManager2.hasEnrolledFingerprints(i4)) {
                            this.mSwitchPrefFingerprint.setSummary(ApnSettings.MVNO_NONE);
                        } else {
                            this.mSwitchPrefFingerprint.setSummary(
                                    getResources().getString(R.string.status_registered));
                        }
                    }
                }
            }
            if (this.bottomButtonLayout != null) {
                Log.d("KKG::KnoxChooseLockTwoFactor", "bottomButtonLayout != null");
                this.bottomButtonLayout.setVisibility(0);
            }
            CharSequence text = getText(R.string.unlock_set_unlock_two_factor_title_step2);
            ActionBar actionBar = getActivity().getActionBar();
            if (actionBar != null) {
                actionBar.setTitle(text);
            }
            this.mIsSecondStep = true;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            Intent createStartIntent;
            LockscreenCredential lockscreenCredential;
            LockscreenCredential lockscreenCredential2;
            if (view == this.layoutBtnLater) {
                Log.d("KKG::KnoxChooseLockTwoFactor", "onClick : Later button");
                if (SemPersonaManager.isKnoxId(this.mUserId)
                        && KnoxUtils.isChangeRequested(getContext(), this.mUserId) > 0) {
                    PasswordPolicy passwordPolicy =
                            KnoxUtils.getPasswordPolicy(getContext(), this.mUserId);
                    if (passwordPolicy != null) {
                        passwordPolicy.setPwdChangeRequested(0);
                    }
                    if (!SemPersonaManager.isDoEnabled(this.mUserId)
                            && (lockscreenCredential2 = this.mNewCredential) != null) {
                        LockPatternChecker.checkCredential(
                                this.mLockPatternUtils,
                                lockscreenCredential2,
                                this.mUserId,
                                new AnonymousClass1());
                    }
                    this.mActivity.setResult(1);
                }
                finish();
                return;
            }
            if (view == this.layoutBtnNext) {
                Log.d("KKG::KnoxChooseLockTwoFactor", "onClick : Next button");
                if (!SemPersonaManager.isDoEnabled(this.mUserId)
                        && KnoxUtils.isChangeRequested(getActivity(), this.mUserId) > 0
                        && !SemPersonaManager.isDoEnabled(this.mUserId)
                        && (lockscreenCredential = this.mNewCredential) != null) {
                    LockPatternChecker.checkCredential(
                            this.mLockPatternUtils,
                            lockscreenCredential,
                            this.mUserId,
                            new AnonymousClass1());
                }
                LockPatternUtils lockPatternUtils = new LockPatternUtils(getActivity());
                SwitchPreference switchPreference = this.mSwitchPrefFingerprint;
                if (switchPreference != null) {
                    if (switchPreference.mChecked) {
                        SecurityUtils.setBiometricLock(
                                getActivity(), this.mLockPatternUtils, 1, this.mUserId);
                    } else if (lockPatternUtils.getBiometricState(1, this.mUserId) == 1) {
                        SecurityUtils.removeBiometricLock(
                                getActivity(), this.mLockPatternUtils, 1, this.mUserId);
                    }
                }
                if (SemPersonaManager.isDoEnabled(this.mUserId)
                        && lockPatternUtils.getBiometricState(256, this.mUserId) == 1) {
                    SecurityUtils.removeBiometricLock(
                            getActivity(), this.mLockPatternUtils, 256, this.mUserId);
                }
                if (!this.mIsSecured
                        && (createStartIntent =
                                        RedactionInterstitial.createStartIntent(
                                                getActivity(), this.mUserId))
                                != null) {
                    startActivity(createStartIntent);
                }
                this.mActivity.setResult(-1);
                this.mActivity.finish();
            }
        }

        @Override // com.android.settings.SettingsPreferenceFragment,
                  // com.android.settings.core.InstrumentedPreferenceFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            addPreferencesFromResource(R.xml.knox_choose_lock_two_factor);
            FragmentActivity activity = getActivity();
            this.mActivity = activity;
            this.mUserId =
                    activity.getIntent()
                            .getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId());
            if (SecurityUtils.hasFingerprintFeature(this.mActivity)) {
                this.mFingerprintManager =
                        (FingerprintManager) this.mActivity.getSystemService("fingerprint");
            }
            this.mFaceManager = SemBioFaceManager.createInstance(this.mActivity);
            this.mLockPatternUtils = new LockPatternUtils(this.mActivity);
            this.mPrefPassword = (RestrictedPreference) findPreference("pref_lock_password");
            this.mPrefPin = (RestrictedPreference) findPreference("pref_lock_pin");
            this.mPrefPattern = (RestrictedPreference) findPreference("pref_lock_pattern");
            this.mSwitchPrefFingerprint = (SwitchPreference) findPreference("switch_fingerprint");
            this.mUserPassword = this.mActivity.getIntent().getParcelableExtra(HostAuth.PASSWORD);
            this.mTwoFactorDescription =
                    (SecUnclickablePreference) findPreference("pref_two_factor_description");
            this.mTwoFactorDescription.setTitle(
                    getResources()
                            .getString(
                                    R.string.knox_twofactor_description_fingerprint,
                                    KnoxUtils.getKnoxName(this.mActivity, this.mUserId)));
            this.mIsEnforcedMultifactorNReset =
                    this.mActivity.getIntent().getBooleanExtra("fromKnoxKeyguard", false);
            this.mIsSecured = this.mLockPatternUtils.isSecure(this.mUserId);
            this.mToken = this.mActivity.getIntent().getByteArrayExtra("hw_auth_token");
            this.mRequestGatekeeperPasswordHandle =
                    this.mActivity.getIntent().getBooleanExtra("request_gk_pw_handle", false);
            this.mMultifactorAuthEnforced =
                    KnoxUtils.isMultifactorAuthEnforced(this.mActivity, this.mUserId);
            this.mRequestedMinComplexity =
                    this.mActivity.getIntent().getIntExtra("requested_min_complexity", 0);
            this.mOnlyEnforceDevicePasswordRequirement =
                    this.mActivity
                            .getIntent()
                            .getBooleanExtra("device_password_requirement_only", false);
            getContext();
            this.mController =
                    new KnoxTwoStepPasswordController(
                            this.mUserId,
                            this.mRequestedMinComplexity,
                            this.mOnlyEnforceDevicePasswordRequirement,
                            this.mLockPatternUtils);
            PreferenceScreen preferenceScreen = getPreferenceScreen();
            if (preferenceScreen != null) {
                preferenceScreen.removeAll();
                SecUnclickablePreference secUnclickablePreference = this.mTwoFactorDescription;
                if (secUnclickablePreference != null) {
                    secUnclickablePreference.setOrder(1);
                    this.mTwoFactorDescription.setTitle(R.string.knox_guide_enroll_twostep_step1);
                    preferenceScreen.addPreference(this.mTwoFactorDescription);
                }
                RestrictedPreference restrictedPreference = this.mPrefPin;
                if (restrictedPreference != null) {
                    restrictedPreference.setOrder(3);
                    preferenceScreen.addPreference(this.mPrefPin);
                }
                RestrictedPreference restrictedPreference2 = this.mPrefPassword;
                if (restrictedPreference2 != null) {
                    restrictedPreference2.setOrder(4);
                    preferenceScreen.addPreference(this.mPrefPassword);
                }
                RestrictedPreference restrictedPreference3 = this.mPrefPattern;
                if (restrictedPreference3 != null) {
                    restrictedPreference3.setOrder(5);
                    preferenceScreen.addPreference(this.mPrefPattern);
                }
            }
            if (this.bottomButtonLayout != null) {
                Log.d("KKG::KnoxChooseLockTwoFactor", "bottomButtonLayout != null");
                this.bottomButtonLayout.setVisibility(8);
            }
            RestrictedPreference restrictedPreference4 = this.mPrefPattern;
            if (restrictedPreference4 != null) {
                restrictedPreference4.setOnPreferenceClickListener(this);
            }
            RestrictedPreference restrictedPreference5 = this.mPrefPin;
            if (restrictedPreference5 != null) {
                restrictedPreference5.setOnPreferenceClickListener(this);
            }
            RestrictedPreference restrictedPreference6 = this.mPrefPassword;
            if (restrictedPreference6 != null) {
                restrictedPreference6.setOnPreferenceClickListener(this);
            }
            CharSequence text = getText(R.string.unlock_set_unlock_two_factor_title_step1);
            ActionBar actionBar = getActivity().getActionBar();
            if (actionBar != null) {
                actionBar.setTitle(text);
            }
            this.mIsSecondStep = false;
            this.mStatusBarManager =
                    (StatusBarManager) this.mActivity.getSystemService("statusbar");
            sUserId = this.mUserId;
        }

        @Override // com.android.settings.SettingsPreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final View onCreateView(
                LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            Log.d("KKG::KnoxChooseLockTwoFactor", "onCreateView");
            LinearLayout linearLayout =
                    (LinearLayout) super.onCreateView(layoutInflater, viewGroup, bundle);
            this.mLayout = linearLayout;
            linearLayout.setVisibility(8);
            LinearLayout linearLayout2 =
                    (LinearLayout)
                            layoutInflater.inflate(
                                    R.layout.sec_knox_two_factor_bottom_button, (ViewGroup) null);
            this.bottomButtonLayout = linearLayout2;
            if (linearLayout2 != null) {
                this.mLayout.addView(linearLayout2);
                Log.d("KKG::KnoxChooseLockTwoFactor", "add bottom button");
                this.bottomButtonLayout.setVisibility(8);
            }
            if (getActivity().getActionBar() != null) {
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
            }
            this.mLayout.setVisibility(0);
            return this.mLayout;
        }

        @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.fragment.app.Fragment
        public final void onDestroy() {
            super.onDestroy();
            LockscreenCredential lockscreenCredential = this.mNewCredential;
            if (lockscreenCredential != null) {
                this.mLockPatternUtils.notifyPasswordChangedForEnterpriseUser(
                        lockscreenCredential, this.mUserId);
                this.mNewCredential.zeroize();
            }
        }

        @Override // com.android.settings.core.InstrumentedPreferenceFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.fragment.app.Fragment
        public final void onPause() {
            super.onPause();
            synchronized (this) {
                try {
                    int i = this.mDisableStatusBarCount;
                    if (i > 0) {
                        int i2 = i - 1;
                        this.mDisableStatusBarCount = i2;
                        if (i2 == 0) {
                            this.mStatusBarManager.disable(0);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // androidx.preference.Preference.OnPreferenceChangeListener
        public final boolean onPreferenceChange(Preference preference, Object obj) {
            boolean booleanValue = ((Boolean) obj).booleanValue();
            if (preference.getKey().equals("switch_fingerprint")) {
                SwitchPreference switchPreference =
                        (SwitchPreference) findPreference("switch_fingerprint");
                if (booleanValue) {
                    int i = this.mUserId;
                    FingerprintManager fingerprintManager = this.mFingerprintManager;
                    if (fingerprintManager == null
                            || !fingerprintManager.hasEnrolledFingerprints(i)) {
                        byte[] bArr = this.mToken;
                        try {
                            Intent intent = new Intent();
                            intent.setClassName(
                                    KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                                    "com.samsung.android.settings.biometrics.fingerprint.FingerprintLockSettings");
                            intent.putExtra(HostAuth.PASSWORD, (Parcelable) this.mUserPassword);
                            intent.putExtra("gk_pw_handle", this.mGkPwHandle);
                            intent.putExtra("previousStage", "knox_fingerprint_entry");
                            intent.putExtra("hw_auth_token", bArr);
                            intent.putExtra("fromKnoxKeyguard", this.mIsEnforcedMultifactorNReset);
                            intent.putExtra("android.intent.extra.USER_ID", this.mUserId);
                            intent.putExtra("is_knox_two_step", true);
                            intent.putExtra("hw_auth_token", bArr);
                            intent.putExtra("onlyIdentify", true);
                            startActivityForResult(intent, 10007);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        switchPreference.setChecked(booleanValue);
                    }
                } else {
                    switchPreference.setChecked(booleanValue);
                }
            }
            setEnableNextButton();
            return false;
        }

        @Override // androidx.preference.Preference.OnPreferenceClickListener
        public final boolean onPreferenceClick(Preference preference) {
            SemBioFaceManager semBioFaceManager;
            SemBioFaceManager semBioFaceManager2;
            Log.i(
                    "KKG::KnoxChooseLockTwoFactor",
                    preference.getKey() + " : clicked : onPreferenceClick");
            String key = preference.getKey();
            if (key == null) {
                return false;
            }
            if (key.equals("pref_lock_password")) {
                sCurrentLockTypeIdx = 0;
            } else if (key.equals("pref_lock_pin")) {
                sCurrentLockTypeIdx = 2;
            } else if (key.equals("pref_lock_pattern")) {
                sCurrentLockTypeIdx = 3;
            }
            Log.i("KKG::KnoxChooseLockTwoFactor", "launchLockType() called");
            int i = sCurrentLockTypeIdx;
            Intent intent = null;
            int i2 = 196608;
            if (i == 0) {
                DevicePolicyManager devicePolicyManager = this.mDPM;
                if (devicePolicyManager == null
                        || devicePolicyManager.getPasswordQuality(null) > 393216
                        || this.mDPM.getPasswordQuality(null) < 262144) {
                    DevicePolicyManager devicePolicyManager2 = this.mDPM;
                    i2 =
                            (devicePolicyManager2 == null
                                            || devicePolicyManager2.getPasswordQuality(null)
                                                    > 196608)
                                    ? 393216
                                    : 262144;
                } else {
                    i2 = this.mDPM.getPasswordQuality(null);
                }
            } else if (i != 2) {
                i2 = i != 3 ? 0 : 65536;
            } else {
                DevicePolicyManager devicePolicyManager3 = this.mDPM;
                if (devicePolicyManager3 != null
                        && devicePolicyManager3.getPasswordQuality(null) <= 131072) {
                    i2 = 131072;
                }
            }
            Log.i(
                    "KKG::KnoxChooseLockTwoFactor",
                    "launchLockType() : quality = " + Integer.toHexString(i2));
            FragmentActivity activity = getActivity();
            if (i2 >= 131072 && i2 <= 393216) {
                this.mDPM.getPasswordMinimumLength(null, this.mUserId);
                intent = new Intent(activity, (Class<?>) ChooseLockPassword.class);
                intent.putExtra("confirm_credentials", false);
                intent.putExtra("lockscreen.password_type", i2);
                KnoxTwoStepPasswordController knoxTwoStepPasswordController = this.mController;
                int max =
                        Math.max(
                                knoxTwoStepPasswordController.mRequestedMinComplexity,
                                knoxTwoStepPasswordController.mLockPatternUtils
                                        .getRequestedPasswordComplexity(
                                                knoxTwoStepPasswordController.mUserId,
                                                knoxTwoStepPasswordController
                                                        .mDevicePasswordRequirementOnly));
                KnoxTwoStepPasswordController knoxTwoStepPasswordController2 = this.mController;
                PasswordMetrics requestedPasswordMetrics =
                        knoxTwoStepPasswordController2.mLockPatternUtils
                                .getRequestedPasswordMetrics(
                                        knoxTwoStepPasswordController2.mUserId,
                                        knoxTwoStepPasswordController2
                                                .mDevicePasswordRequirementOnly);
                intent.putExtra("min_complexity", max);
                intent.putExtra("min_metrics", (Parcelable) requestedPasswordMetrics);
                intent.putExtra("android.intent.extra.USER_ID", this.mUserId);
                FingerprintManager fingerprintManager = this.mFingerprintManager;
                if ((fingerprintManager != null && fingerprintManager.isHardwareDetected())
                        || ((semBioFaceManager2 = this.mFaceManager) != null
                                && semBioFaceManager2.isHardwareDetected())) {
                    intent.putExtra("request_gk_pw_handle", this.mRequestGatekeeperPasswordHandle);
                }
                LockscreenCredential lockscreenCredential = this.mUserPassword;
                if (lockscreenCredential != null) {
                    intent.putExtra(HostAuth.PASSWORD, (Parcelable) lockscreenCredential);
                }
            } else if (i2 == 65536) {
                intent = new Intent(getActivity(), (Class<?>) ChooseLockPattern.class);
                intent.putExtra("confirm_credentials", false);
                intent.putExtra("android.intent.extra.USER_ID", this.mUserId);
                FingerprintManager fingerprintManager2 = this.mFingerprintManager;
                if ((fingerprintManager2 != null && fingerprintManager2.isHardwareDetected())
                        || ((semBioFaceManager = this.mFaceManager) != null
                                && semBioFaceManager.isHardwareDetected())) {
                    intent.putExtra("request_gk_pw_handle", this.mRequestGatekeeperPasswordHandle);
                }
                LockscreenCredential lockscreenCredential2 = this.mUserPassword;
                if (lockscreenCredential2 != null) {
                    intent.putExtra(HostAuth.PASSWORD, (Parcelable) lockscreenCredential2);
                }
            }
            if (intent != null) {
                intent.putExtra("knox_userId", this.mUserId);
                intent.putExtra("is_knox_two_step", true);
            }
            if (intent != null) {
                startActivityForResult(intent, 10100);
            }
            return false;
        }

        @Override // com.android.settings.SettingsPreferenceFragment,
                  // com.android.settings.core.InstrumentedPreferenceFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.fragment.app.Fragment
        public final void onResume() {
            super.onResume();
            if (this.mDPM == null) {
                this.mDPM = (DevicePolicyManager) this.mActivity.getSystemService("device_policy");
            }
            this.mPrefPassword.setEnabled(true);
            RestrictedPreference restrictedPreference = this.mPrefPin;
            KnoxTwoStepPasswordController knoxTwoStepPasswordController = this.mController;
            LockPatternUtils lockPatternUtils = knoxTwoStepPasswordController.mLockPatternUtils;
            int i = knoxTwoStepPasswordController.mUserId;
            boolean z = knoxTwoStepPasswordController.mDevicePasswordRequirementOnly;
            restrictedPreference.setEnabled(
                    196608
                            >= Math.max(
                                    0,
                                    Math.max(
                                            LockPatternUtils.credentialTypeToPasswordQuality(
                                                    lockPatternUtils.getRequestedPasswordMetrics(
                                                                    i, z)
                                                            .credType),
                                            PasswordMetrics.complexityLevelToMinQuality(
                                                    Math.max(
                                                            knoxTwoStepPasswordController
                                                                    .mRequestedMinComplexity,
                                                            knoxTwoStepPasswordController
                                                                    .mLockPatternUtils
                                                                    .getRequestedPasswordComplexity(
                                                                            i, z))))));
            RestrictedPreference restrictedPreference2 = this.mPrefPattern;
            KnoxTwoStepPasswordController knoxTwoStepPasswordController2 = this.mController;
            LockPatternUtils lockPatternUtils2 = knoxTwoStepPasswordController2.mLockPatternUtils;
            int i2 = knoxTwoStepPasswordController2.mUserId;
            boolean z2 = knoxTwoStepPasswordController2.mDevicePasswordRequirementOnly;
            restrictedPreference2.setEnabled(
                    65536
                            >= Math.max(
                                    0,
                                    Math.max(
                                            LockPatternUtils.credentialTypeToPasswordQuality(
                                                    lockPatternUtils2.getRequestedPasswordMetrics(
                                                                    i2, z2)
                                                            .credType),
                                            PasswordMetrics.complexityLevelToMinQuality(
                                                    Math.max(
                                                            knoxTwoStepPasswordController2
                                                                    .mRequestedMinComplexity,
                                                            knoxTwoStepPasswordController2
                                                                    .mLockPatternUtils
                                                                    .getRequestedPasswordComplexity(
                                                                            i2, z2))))));
            if (SemPersonaManager.isDoEnabled(this.mUserId)
                    && KnoxUtils.isChangeRequested(getContext(), this.mUserId) > 0) {
                synchronized (this) {
                    try {
                        int i3 = this.mDisableStatusBarCount;
                        this.mDisableStatusBarCount = i3 + 1;
                        if (i3 == 0) {
                            this.mStatusBarManager.disable(65536);
                        }
                    } finally {
                    }
                }
            }
            sUserId = this.mUserId;
        }

        @Override // com.android.settings.SettingsPreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final void onViewCreated(View view, Bundle bundle) {
            super.onViewCreated(view, bundle);
            SecBottomBarButton secBottomBarButton =
                    (SecBottomBarButton) this.mLayout.findViewById(R.id.later_button);
            this.layoutBtnLater = secBottomBarButton;
            if (secBottomBarButton == null) {
                Log.d("KKG::KnoxChooseLockTwoFactor", "prev button cannot be null !");
            } else {
                if (Settings.System.getInt(getContentResolver(), "show_button_background", 0)
                        != 0) {
                    this.layoutBtnLater.setBackground(
                            getResources()
                                    .getDrawable(
                                            R.drawable
                                                    .sec_wifi_setupwizard_next_show_btn_shape_background));
                }
                this.layoutBtnLater.setOnClickListener(this);
                if (this.mMultifactorAuthEnforced) {
                    Log.d(
                            "KKG::KnoxChooseLockTwoFactor",
                            "MultifactorAuthEnforced : disable later button");
                    this.layoutBtnLater.setEnabled(false);
                } else {
                    this.layoutBtnLater.setEnabled(true);
                }
            }
            SecBottomBarButton secBottomBarButton2 =
                    (SecBottomBarButton) this.mLayout.findViewById(R.id.setup_button);
            this.layoutBtnNext = secBottomBarButton2;
            if (secBottomBarButton2 == null) {
                Log.d("KKG::KnoxChooseLockTwoFactor", "next button cannot be null ! : return");
                return;
            }
            if (Settings.System.getInt(getContentResolver(), "show_button_background", 0) != 0) {
                this.layoutBtnNext.setBackground(
                        getResources()
                                .getDrawable(
                                        R.drawable
                                                .sec_wifi_setupwizard_next_show_btn_shape_background));
            }
            this.layoutBtnNext.setOnClickListener(this);
            setEnableNextButton();
        }

        public final void setEnableNextButton() {
            if (this.layoutBtnNext == null) {
                Log.d("KKG::KnoxChooseLockTwoFactor", "next button cannot be null ! : return.");
                return;
            }
            SwitchPreference switchPreference = this.mSwitchPrefFingerprint;
            boolean z = switchPreference != null && switchPreference.mChecked;
            Log.d("KKG::KnoxChooseLockTwoFactor", "setup button : " + z);
            if (z) {
                this.layoutBtnNext.setEnabled(true);
            } else {
                this.layoutBtnNext.setEnabled(false);
            }
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.samsung.android.settings.knox.KnoxChooseLockTwoFactor$KnoxChooseLockTwoFactorFragment$1, reason: invalid class name */
        public final class AnonymousClass1 implements LockPatternChecker.OnCheckCallback {
            public final void onChecked(boolean z, int i) {}
        }
    }
}
