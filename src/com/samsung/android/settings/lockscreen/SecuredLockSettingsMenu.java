package com.samsung.android.settings.lockscreen;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.SearchIndexableData;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.util.secutil.Log;
import android.view.KeyCharacterMap;
import android.widget.Toast;

import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.SecPreferenceUtils;
import androidx.preference.SecSwitchPreference;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.accessibility.AccessibilitySettings$$ExternalSyntheticOutline0;
import com.android.settings.password.ChooseLockSettingsHelper;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.Indexable$SearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.knox.UCMUtils;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.security.SecurityUtils;
import com.samsung.android.settings.widget.SecRestrictedPreference;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecuredLockSettingsMenu extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener {
    public static final int MY_USER_ID = UserHandle.myUserId();
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new SecuredLockIndexProvider();
    public FragmentActivity mActivity;
    public SecSwitchPreference mAutoFactoryReset;
    public DevicePolicyManager mDPM;
    public SecRestrictedPreference mDeletePreviousPin;
    public SecRestrictedPreference mLockAfter;
    public SecSwitchPreference mLockInstantlyWithFolding;
    public SecSwitchPreference mLockNetworkAndSecurity;
    public LockPatternUtils mLockPatternUtils;
    public SecSwitchPreference mPowerButtonInstantlyLocks;
    public SecSwitchPreference mShowLockDownOption;
    public UserManager mUserManager;
    public SecSwitchPreference mVisiblePattern;
    public final int mResetCount = 20;
    public int mLockType = 0;
    public boolean mPasswordConfirmed = false;
    public boolean mWaitingForConfirmation = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SecuredLockIndexProvider extends BaseSearchIndexProvider {
        public final String mClassName = SecuredLockSettingsMenu.class.getName();
        public LockPatternUtils mLockPatternUtils;
        public UserManager mUser;
        public int mXmlResId;

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getDynamicRawDataToIndex(Context context) {
            super.getDynamicRawDataToIndex(context);
            ArrayList arrayList = new ArrayList();
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            int credentialTypeForUser = this.mLockPatternUtils.getCredentialTypeForUser(-9899);
            if (credentialTypeForUser == 1) {
                searchIndexableRaw.title =
                        String.valueOf(
                                R.string.sec_secured_lock_settings_delete_previous_pattern_title);
            } else if (credentialTypeForUser == 3) {
                searchIndexableRaw.title =
                        String.valueOf(
                                R.string.sec_secured_lock_settings_delete_previous_pin_title);
            } else if (credentialTypeForUser == 4) {
                searchIndexableRaw.title =
                        String.valueOf(
                                R.string.sec_secured_lock_settings_delete_previous_password_title);
            }
            ((SearchIndexableData) searchIndexableRaw).key = "delete_previous_pin";
            searchIndexableRaw.screenTitle =
                    context.getResources().getString(R.string.sec_secured_lock_settings_title);
            arrayList.add(searchIndexableRaw);
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            if (this.mLockPatternUtils == null) {
                this.mLockPatternUtils = new LockPatternUtils(context);
            }
            LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
            int i = SecuredLockSettingsMenu.MY_USER_ID;
            int keyguardStoredPasswordQuality =
                    lockPatternUtils.getKeyguardStoredPasswordQuality(i);
            StringBuilder sb = Utils.sBuilder;
            SemDesktopModeManager semDesktopModeManager =
                    (SemDesktopModeManager) context.getSystemService("desktopmode");
            if (semDesktopModeManager != null ? semDesktopModeManager.isDeviceConnected() : false) {
                AccessibilitySettings$$ExternalSyntheticOutline0.m(
                        (ArrayList) nonIndexableKeys,
                        "lock_after_timeout",
                        "lock_network_and_security",
                        "power_button_instantly_locks",
                        "lockdown_in_power_menu");
            }
            if (!this.mLockPatternUtils.isSecure(i)) {
                ArrayList arrayList = (ArrayList) nonIndexableKeys;
                AccessibilitySettings$$ExternalSyntheticOutline0.m(
                        arrayList,
                        "auto_factory_reset",
                        "lock_after_timeout",
                        "visiblepattern",
                        "power_button_instantly_locks");
                arrayList.add("lock_network_and_security");
                arrayList.add("lock_instantly_with_folding");
                arrayList.add("lockdown_in_power_menu");
            }
            if (this.mLockPatternUtils.getCredentialTypeForUser(-9899) == -1) {
                ((ArrayList) nonIndexableKeys).add("delete_previous_pin");
            }
            if (keyguardStoredPasswordQuality != 65536 && keyguardStoredPasswordQuality != 589824) {
                ((ArrayList) nonIndexableKeys).add("visiblepattern");
            }
            if (!LockUtils.isSupportSubLockscreen()) {
                ((ArrayList) nonIndexableKeys).add("lock_instantly_with_folding");
            }
            if (!KeyCharacterMap.deviceHasKey(26)) {
                ((ArrayList) nonIndexableKeys).add("power_button_instantly_locks");
            }
            UserManager userManager = (UserManager) context.getSystemService("user");
            this.mUser = userManager;
            if (!userManager.isAdminUser() && this.mUser.isUserSwitcherEnabled()) {
                ((ArrayList) nonIndexableKeys).add("auto_factory_reset");
            }
            if (UCMUtils.isUCMKeyguardEnabled()) {
                ((ArrayList) nonIndexableKeys).add("auto_factory_reset");
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            Resources resources = context.getResources();
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            ((SearchIndexableData) searchIndexableRaw).key = "power_button_instantly_locks";
            searchIndexableRaw.title =
                    Rune.supportFunctionKey()
                            ? String.valueOf(
                                    R.string
                                            .lockpattern_settings_enable_power_button_instantly_locks_sidekey)
                            : String.valueOf(
                                    R.string
                                            .lockpattern_settings_enable_power_button_instantly_locks);
            searchIndexableRaw.screenTitle =
                    resources.getString(R.string.sec_secured_lock_settings_title);
            searchIndexableRaw.summaryOn =
                    resources.getString(
                            R.string
                                    .sec_secured_lock_settings_lock_instantly_with_power_key_summary);
            arrayList.add(searchIndexableRaw);
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            this.mLockPatternUtils = new LockPatternUtils(context);
            ArrayList arrayList = new ArrayList();
            this.mXmlResId = R.xml.sec_secured_lock_settings;
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.className = this.mClassName;
            searchIndexableResource.xmlResId = this.mXmlResId;
            arrayList.add(searchIndexableResource);
            return arrayList;
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 4432;
    }

    public final String getTimeoutNewEntry(long j) {
        String str;
        long j2 = j / 1000;
        long j3 = j2 / 60;
        long j4 = j2 % 60;
        if (j3 > 0) {
            int i = (int) j3;
            str =
                    String.format(
                            getResources().getQuantityString(R.plurals.lock_timeout_minutes, i),
                            Integer.valueOf(i));
        } else {
            str = ApnSettings.MVNO_NONE;
        }
        if (j3 > 0 && j4 > 0) {
            str = str.concat(" ");
        }
        if (j4 > 0) {
            StringBuilder m =
                    EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(str);
            int i2 = (int) j4;
            m.append(
                    String.format(
                            getResources().getQuantityString(R.plurals.lock_timeout_seconds, i2),
                            Integer.valueOf(i2)));
            str = m.toString();
        }
        Log.secD("SecuredLockSettingsMenu", "getTimeoutNewEntry : " + str);
        return str;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        addPreferencesFromResource(R.xml.sec_secured_lock_settings);
        if (!this.mPasswordConfirmed) {
            getListView().setVisibility(4);
        }
        boolean z = UserHandle.myUserId() != 0;
        SecSwitchPreference secSwitchPreference =
                (SecSwitchPreference) findPreference("visiblepattern");
        this.mVisiblePattern = secSwitchPreference;
        if (secSwitchPreference != null) {
            if (this.mLockPatternUtils.isVisiblePatternDisabledByMDM()) {
                this.mVisiblePattern.setEnabled(false);
            }
            this.mVisiblePattern.setChecked(
                    this.mLockPatternUtils.isVisiblePatternEnabled(MY_USER_ID));
            this.mVisiblePattern.setOnPreferenceChangeListener(this);
        }
        SecSwitchPreference secSwitchPreference2 =
                (SecSwitchPreference) findPreference("auto_factory_reset");
        this.mAutoFactoryReset = secSwitchPreference2;
        if (secSwitchPreference2 != null) {
            this.mAutoFactoryReset.setChecked(
                    Settings.Secure.getInt(getContentResolver(), "auto_swipe_main_user", 0) != 0);
            this.mAutoFactoryReset.setOnPreferenceChangeListener(this);
            String format =
                    String.format(
                            getString(R.string.sec_secured_lock_settings_auto_swipe_summary_common),
                            Integer.valueOf(this.mResetCount));
            if (z && Utils.isTablet()) {
                format =
                        String.format(
                                getString(
                                        R.string
                                                .sec_secured_lock_settings_auto_swipe_summary_subuser),
                                Integer.valueOf(this.mResetCount));
            }
            this.mAutoFactoryReset.setSummary(format);
            DevicePolicyManager devicePolicyManager = this.mDPM;
            if (devicePolicyManager != null
                    && devicePolicyManager.getMaximumFailedPasswordsForWipe(null) > 0) {
                this.mAutoFactoryReset.setEnabled(false);
            } else if (this.mUserManager.isAdminUser()
                    || !this.mUserManager.isUserSwitcherEnabled()) {
                this.mAutoFactoryReset.setEnabled(true);
            } else {
                this.mAutoFactoryReset.setEnabled(false);
            }
            if (UCMUtils.isUCMKeyguardEnabled()) {
                this.mAutoFactoryReset.setEnabled(false);
            }
        }
        SecSwitchPreference secSwitchPreference3 =
                (SecSwitchPreference) findPreference("lock_network_and_security");
        this.mLockNetworkAndSecurity = secSwitchPreference3;
        if (secSwitchPreference3 != null) {
            this.mLockNetworkAndSecurity.setChecked(
                    Settings.Secure.getInt(getContentResolver(), "lock_function_val", 0) != 0);
            this.mLockNetworkAndSecurity.setSummary(
                    com.android.settingslib.Utils.isWifiOnly(getContext())
                            ? R.string
                                    .sec_secure_lock_settings_lock_network_and_security_summary_wifi_only
                            : R.string.sec_secure_lock_settings_lock_network_and_security_summary);
            this.mLockNetworkAndSecurity.setOnPreferenceChangeListener(this);
        }
        SecSwitchPreference secSwitchPreference4 =
                (SecSwitchPreference) findPreference("lockdown_in_power_menu");
        this.mShowLockDownOption = secSwitchPreference4;
        if (secSwitchPreference4 != null) {
            this.mShowLockDownOption.setChecked(
                    Settings.Secure.getInt(getContentResolver(), "lockdown_in_power_menu", 0) != 0);
            this.mShowLockDownOption.setOnPreferenceChangeListener(this);
            if (!SecurityUtils.isBiometricsLockEnabled(getContext())) {
                this.mShowLockDownOption.setSummary(
                        R.string.sec_secured_lock_settings_lock_down_summary_no_biometrics);
            }
        }
        LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
        int i = MY_USER_ID;
        if (lockPatternUtils.isSecure(i)) {
            SecRestrictedPreference secRestrictedPreference =
                    (SecRestrictedPreference) findPreference("lock_after_timeout");
            this.mLockAfter = secRestrictedPreference;
            if (secRestrictedPreference != null) {
                secRestrictedPreference.setTitle(
                        R.string.sec_secured_lock_settings_lock_after_timeout_title);
                updateLockAfterPreferenceSummary$1();
            }
            SecSwitchPreference secSwitchPreference5 =
                    (SecSwitchPreference) findPreference("power_button_instantly_locks");
            this.mPowerButtonInstantlyLocks = secSwitchPreference5;
            if (secSwitchPreference5 != null) {
                secSwitchPreference5.setChecked(
                        this.mLockPatternUtils.getPowerButtonInstantlyLocks(i));
                this.mPowerButtonInstantlyLocks.setOnPreferenceChangeListener(this);
                if (Rune.supportFunctionKey()) {
                    this.mPowerButtonInstantlyLocks.setTitle(
                            R.string
                                    .lockpattern_settings_enable_power_button_instantly_locks_sidekey);
                } else {
                    this.mPowerButtonInstantlyLocks.setTitle(
                            R.string.lockpattern_settings_enable_power_button_instantly_locks);
                }
                if (Utils.isDesktopModeEnabled(this.mActivity)) {
                    this.mPowerButtonInstantlyLocks.setSummary(
                            R.string.sec_lock_screen_disable_by_samsung_dex_waring_text);
                    this.mPowerButtonInstantlyLocks.setEnabled(false);
                }
            }
            SecSwitchPreference secSwitchPreference6 =
                    (SecSwitchPreference) findPreference("lock_instantly_with_folding");
            this.mLockInstantlyWithFolding = secSwitchPreference6;
            if (secSwitchPreference6 != null) {
                this.mLockInstantlyWithFolding.setChecked(
                        this.mLockPatternUtils.getFolderInstantlyLocks(i));
                this.mLockInstantlyWithFolding.setOnPreferenceChangeListener(this);
            }
            SecRestrictedPreference secRestrictedPreference2 =
                    (SecRestrictedPreference) findPreference("delete_previous_pin");
            this.mDeletePreviousPin = secRestrictedPreference2;
            if (secRestrictedPreference2 != null) {
                int credentialTypeForUser = this.mLockPatternUtils.getCredentialTypeForUser(-9899);
                if (credentialTypeForUser == 1) {
                    this.mDeletePreviousPin.setTitle(
                            getResources()
                                    .getString(
                                            R.string
                                                    .sec_secured_lock_settings_delete_previous_pattern_title));
                    this.mDeletePreviousPin.setSummary(
                            getResources()
                                    .getString(
                                            R.string
                                                    .sec_secured_lock_settings_delete_previous_pattern_summary));
                } else if (credentialTypeForUser == 3) {
                    this.mDeletePreviousPin.setTitle(
                            getResources()
                                    .getString(
                                            R.string
                                                    .sec_secured_lock_settings_delete_previous_pin_title));
                    this.mDeletePreviousPin.setSummary(
                            getResources()
                                    .getString(
                                            R.string
                                                    .sec_secured_lock_settings_delete_previous_pin_summary));
                } else if (credentialTypeForUser == 4) {
                    this.mDeletePreviousPin.setTitle(
                            getResources()
                                    .getString(
                                            R.string
                                                    .sec_secured_lock_settings_delete_previous_password_title));
                    this.mDeletePreviousPin.setSummary(
                            getResources()
                                    .getString(
                                            R.string
                                                    .sec_secured_lock_settings_delete_previous_password_summary));
                }
            }
        }
        int i2 = this.mLockType;
        if (i2 != 65536 && i2 != 589824 && this.mVisiblePattern != null) {
            getPreferenceScreen().removePreference(this.mVisiblePattern);
        }
        if (this.mPowerButtonInstantlyLocks != null) {
            String str = LockUtils.CONFIG_LOCK_SCREEN_CLOCK_DISPLAY_POLICY;
            if (!KeyCharacterMap.deviceHasKey(26)) {
                getPreferenceScreen().removePreference(this.mPowerButtonInstantlyLocks);
            }
        }
        if (this.mLockInstantlyWithFolding == null || LockUtils.isSupportSubLockscreen()) {
            return;
        }
        getPreferenceScreen().removePreference(this.mLockInstantlyWithFolding);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        this.mWaitingForConfirmation = false;
        if (i != 300) {
            if (i == 400 && i2 == -1) {
                getPreferenceScreen().removePreference(this.mDeletePreviousPin);
                return;
            }
            return;
        }
        if (i2 != -1) {
            Log.secD("SecuredLockSettingsMenu", "Lock confirm failed");
            this.mActivity.finish();
            return;
        }
        Log.secD("SecuredLockSettingsMenu", "Lock confirmed");
        this.mPasswordConfirmed = true;
        getListView().setVisibility(0);
        if (getArguments() != null) {
            highlightPreferenceIfNeeded(true);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mActivity = getActivity();
        if (bundle != null) {
            this.mPasswordConfirmed = bundle.getBoolean("password_already_confirmed");
            this.mWaitingForConfirmation = bundle.getBoolean("waiting_for_confirmation");
        }
        if (!this.mPasswordConfirmed && !this.mWaitingForConfirmation) {
            ChooseLockSettingsHelper.Builder builder =
                    new ChooseLockSettingsHelper.Builder(this.mActivity, this);
            builder.mRequestCode = 300;
            builder.mTitle =
                    this.mActivity.getString(R.string.sec_unlock_set_unlock_launch_picker_title);
            builder.mReturnCredentials = true;
            builder.mUserId = MY_USER_ID;
            if (builder.show()) {
                this.mWaitingForConfirmation = true;
                Log.secD("SecuredLockSettingsMenu", "Need to confirm Current Lock");
            }
        }
        this.mDPM = (DevicePolicyManager) getSystemService("device_policy");
        LockPatternUtils lockPatternUtils = new LockPatternUtils(this.mActivity);
        this.mLockPatternUtils = lockPatternUtils;
        this.mLockType = lockPatternUtils.getKeyguardStoredPasswordQuality(MY_USER_ID);
        this.mUserManager = (UserManager) getSystemService("user");
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        Log.d("SecuredLockSettingsMenu", "onPause");
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        String key = preference.getKey();
        if ("visiblepattern".equals(key)) {
            this.mLockPatternUtils.setVisiblePatternEnabled(
                    ((Boolean) obj).booleanValue(), MY_USER_ID);
            return true;
        }
        if ("power_button_instantly_locks".equals(key)) {
            this.mLockPatternUtils.setPowerButtonInstantlyLocks(
                    ((Boolean) obj).booleanValue(), MY_USER_ID);
            return true;
        }
        if ("auto_factory_reset".equals(key)) {
            Settings.Secure.putInt(
                    getContentResolver(),
                    "auto_swipe_main_user",
                    ((Boolean) obj).booleanValue() ? 1 : 0);
            return true;
        }
        if ("lock_network_and_security".equals(key)) {
            Settings.Secure.putInt(
                    getContentResolver(),
                    "lock_function_val",
                    ((Boolean) obj).booleanValue() ? 1 : 0);
            return true;
        }
        if ("lockdown_in_power_menu".equals(key)) {
            Settings.Secure.putInt(
                    getContentResolver(),
                    "lockdown_in_power_menu",
                    ((Boolean) obj).booleanValue() ? 1 : 0);
            return true;
        }
        if (!"lock_instantly_with_folding".equals(key)) {
            return true;
        }
        this.mLockPatternUtils.setFolderInstantlyLocks(((Boolean) obj).booleanValue(), MY_USER_ID);
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        String key = preference.getKey();
        if (key.equals("lock_after_timeout")) {
            getContext().startActivity(new Intent("com.samsung.settings.LockAfterTimeout"));
            return true;
        }
        if (!key.equals("delete_previous_pin")) {
            return true;
        }
        SALogging.insertSALog(
                Settings.Secure.getInt(
                        getContext().getContentResolver(),
                        "save_previous_credential_description_show_count",
                        0),
                String.valueOf(4432),
                "LSE2042",
                (String) null);
        startActivityForResult(
                new Intent(getActivity(), (Class<?>) DeletePreviousActivity.class), 400);
        return true;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        Log.secD("SecuredLockSettingsMenu", "onResume");
        super.onResume();
        if (LockUtils.isLockSettingsBlockonDexMode(this.mActivity)) {
            this.mActivity.finish();
        }
        if (LockUtils.isInMultiWindow(this.mActivity)) {
            Toast.makeText(
                            this.mActivity,
                            getString(R.string.lock_screen_doesnt_support_multi_window_text),
                            0)
                    .show();
            this.mActivity.finish();
            return;
        }
        SecRestrictedPreference secRestrictedPreference = this.mLockAfter;
        if (secRestrictedPreference != null) {
            secRestrictedPreference.setTitle(
                    R.string.sec_secured_lock_settings_lock_after_timeout_title);
            updateLockAfterPreferenceSummary$1();
        }
        if (this.mDeletePreviousPin == null
                || this.mLockPatternUtils.getCredentialTypeForUser(-9899) != -1) {
            return;
        }
        getPreferenceScreen().removePreference(this.mDeletePreviousPin);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("password_already_confirmed", this.mPasswordConfirmed);
        bundle.putBoolean("waiting_for_confirmation", this.mWaitingForConfirmation);
    }

    public final void updateLockAfterPreferenceSummary$1() {
        ArrayList arrayList;
        ArrayList arrayList2;
        long j =
                Settings.Secure.getLong(
                        getContentResolver(), "lock_screen_lock_after_timeout", 5000L);
        String[] stringArray =
                getContext().getResources().getStringArray(R.array.lock_after_timeout_entries);
        String[] stringArray2 =
                getContext().getResources().getStringArray(R.array.lock_after_timeout_values);
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        DevicePolicyManager devicePolicyManager = this.mDPM;
        long maximumTimeToLock =
                devicePolicyManager != null ? devicePolicyManager.getMaximumTimeToLock(null) : 0L;
        Log.secD(
                "SecuredLockSettingsMenu",
                "updateLockAfterPreferenceSummary, adminTimeout = " + maximumTimeToLock);
        ArrayList arrayList5 = arrayList3;
        long max =
                (long)
                        Math.max(
                                0,
                                Settings.System.getInt(
                                        getContentResolver(), "screen_off_timeout", 0));
        Log.secD(
                "SecuredLockSettingsMenu",
                "updateLockAfterPreferenceSummary, displayTimeout = " + max);
        long max2 = Math.max(0L, maximumTimeToLock - max);
        Log.secD(
                "SecuredLockSettingsMenu",
                "updateLockAfterPreferenceSummary, maxTimeout = " + max2);
        int enterprisePolicyEnabledInt =
                Utils.getEnterprisePolicyEnabledInt(getContext(), "getPasswordLockDelay", null);
        Log.secD(
                "SecuredLockSettingsMenu",
                "getPasswordLockDelay - "
                        + enterprisePolicyEnabledInt
                        + ", adminTimeout - "
                        + maximumTimeToLock);
        if (enterprisePolicyEnabledInt >= 0) {
            max2 =
                    maximumTimeToLock == 0
                            ? enterprisePolicyEnabledInt * 1000
                            : Math.min(enterprisePolicyEnabledInt * 1000, max2);
        }
        Log.secD("SecuredLockSettingsMenu", "maxTimeout after Knox is = " + max2);
        if (maximumTimeToLock > 0) {
            int i = 0;
            while (i < stringArray2.length) {
                if (Long.valueOf(stringArray2[i].toString()).longValue() <= max2) {
                    arrayList2 = arrayList5;
                    arrayList2.add(stringArray[i]);
                    arrayList4.add(stringArray2[i]);
                } else {
                    arrayList2 = arrayList5;
                }
                i++;
                arrayList5 = arrayList2;
            }
            arrayList = arrayList5;
            Log.secD("SecuredLockSettingsMenu", "revisedValues.size() : " + arrayList4.size());
            long longValue =
                    max2
                            - Long.valueOf(
                                            ((CharSequence) arrayList4.get(arrayList4.size() - 1))
                                                    .toString())
                                    .longValue();
            Log.secD("SecuredLockSettingsMenu", "last_timeout : " + longValue);
            if (longValue > 0
                    && max2
                            < Long.valueOf(stringArray2[stringArray2.length - 1].toString())
                                    .longValue()) {
                arrayList.add(getString(R.string.lock_timeout_max, getTimeoutNewEntry(max2)));
                arrayList4.add(Long.toString(max2));
            }
        } else {
            arrayList = arrayList5;
            for (int i2 = 0; i2 < stringArray2.length; i2++) {
                arrayList.add(stringArray[i2]);
                arrayList4.add(stringArray2[i2]);
            }
        }
        int i3 = 0;
        for (int i4 = 0; i4 < arrayList4.size(); i4++) {
            if (j >= Long.valueOf(((CharSequence) arrayList4.get(i4)).toString()).longValue()) {
                i3 = i4;
            }
        }
        String string = getResources().getString(R.string.sec_extend_unlock_title);
        if (Long.valueOf(((CharSequence) arrayList4.get(i3)).toString()).longValue() >= max2
                || max2 >= j) {
            Log.secD("SecuredLockSettingsMenu", "updateLockAfterPreferenceSummary, best = " + i3);
            if (i3 == 0) {
                this.mLockAfter.setSummary(
                        R.string.sec_secured_lock_settings_lock_after_timeout_immedi_summary);
            } else {
                String[] stringArray3 =
                        getResources().getStringArray(R.array.lock_after_timeout_values);
                if (stringArray3 != null) {
                    int i5 = 0;
                    while (true) {
                        if (i5 >= stringArray3.length) {
                            break;
                        }
                        if (Long.valueOf(stringArray3[i5]).longValue() != j) {
                            i5++;
                        } else if (j < max2 || max2 == 0) {
                            this.mLockAfter.setSummary(
                                    getString(
                                            R.string
                                                    .sec_secured_lock_settings_lock_after_timeout_summary,
                                            arrayList.get(i3),
                                            string));
                        }
                    }
                }
                this.mLockAfter.setSummary(
                        getString(
                                R.string.sec_secured_lock_settings_lock_after_timeout_summary,
                                getTimeoutNewEntry(max2),
                                string));
            }
        } else {
            Log.secD(
                    "SecuredLockSettingsMenu",
                    "updateLockAfterPreferenceSummary, Long.valueOf(values[best].toString()) <"
                        + " maxTimeout && maxTimeout < currentTimeout");
            this.mLockAfter.setSummary(
                    getString(
                            R.string.sec_secured_lock_settings_lock_after_timeout_summary,
                            getTimeoutNewEntry(max2),
                            string));
        }
        SecRestrictedPreference secRestrictedPreference = this.mLockAfter;
        secRestrictedPreference.getClass();
        SecPreferenceUtils.applySummaryColor(secRestrictedPreference, true);
    }
}
