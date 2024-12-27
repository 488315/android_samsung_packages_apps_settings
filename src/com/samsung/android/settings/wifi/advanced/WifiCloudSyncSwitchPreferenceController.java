package com.samsung.android.settings.wifi.advanced;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleObserver;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceUtils;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.scloud.lib.setting.SamsungCloudRPCSettingV2;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.wifitrackerlib.SemWifiUtils;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiCloudSyncSwitchPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnResume {
    private static final String KEY_WIFI_CLOUD_SYNC = "wifi_cloud_sync";
    private static final String SCLOUD_WIFI_SYNC_ENABLED = "scloud_wifi_sync_enabled";
    private static final String TAG = "ConfigureWifiSettings.WifiCloudSyncSwitch";
    private Account mAccount;
    private Fragment mFragment;
    private boolean mIsChecked;
    private WifiCloudSettingsStore mStore;
    private Preference mWifiCloudSyncPreference;

    public WifiCloudSyncSwitchPreferenceController(Context context, String str) {
        super(context, str);
        this.mAccount = SemWifiUtils.getSamsungAccount(this.mContext);
        this.mStore = new WifiCloudSettingsStore();
        this.mStore.setRpcSettings(
                new SamsungCloudRPCSettingV2(
                        this.mContext.getApplicationContext(),
                        "com.android.settings.wifiprofilesync",
                        "com.android.settings.wifiprofilesetting"));
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mWifiCloudSyncPreference = preferenceScreen.findPreference(KEY_WIFI_CLOUD_SYNC);
        if ("VZW".equals(SemWifiUtils.readSalesCode())) {
            this.mWifiCloudSyncPreference.setTitle(
                    R.string.sec_bluetooth_advanced_sync_with_account_vzw);
        }
        updateState(this.mWifiCloudSyncPreference);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        int i = 3;
        if (Rune.isChinaModel()) {
            WifiCloudSettingsStore wifiCloudSettingsStore = this.mStore;
            if (!wifiCloudSettingsStore.mIsPersonalInfoOn
                    || !wifiCloudSettingsStore.mIsConsentedToUseNetwork) {
                return 3;
            }
        }
        if (SemWifiUtils.getSamsungAccount(this.mContext) != null
                && SemWifiUtils.isWifiSyncEnabled(this.mContext)) {
            WifiCloudSettingsStore wifiCloudSettingsStore2 = this.mStore;
            if (wifiCloudSettingsStore2.mIsMainSyncOn && wifiCloudSettingsStore2.mIsAllowedInEdp) {
                i = 0;
            }
        }
        if (((UserManager) this.mContext.getSystemService("user"))
                .semGetSemUserInfo(UserHandle.semGetMyUserId())
                .hasFlags(16777216)) {
            return 2;
        }
        return i;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_connections;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        Account account;
        if (this.mAccount == null) {
            this.mAccount = SemWifiUtils.getSamsungAccount(this.mContext);
        }
        return (!this.mIsChecked || (account = this.mAccount) == null)
                ? (!Rune.isChinaModel() || this.mStore.mIsUserAgreesPolicyNotice)
                        ? this.mContext
                                .getResources()
                                .getString(R.string.sec_bluetooth_advanced_auto_sync_disabled)
                        : this.mContext
                                .getResources()
                                .getString(R.string.wifi_advanced_sync_no_privacy_notice_chn)
                : account.name;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        SamsungCloudRPCSettingV2 samsungCloudRPCSettingV2;
        Fragment fragment;
        if (KEY_WIFI_CLOUD_SYNC.equals(preference.getKey())
                && (samsungCloudRPCSettingV2 = this.mStore.mSetting) != null
                && (fragment = this.mFragment) != null) {
            samsungCloudRPCSettingV2.showSetting(fragment.getActivity());
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return this.mIsChecked;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    public void onChangeWifiSyncEnabled(PreferenceScreen preferenceScreen, boolean z) {
        this.mIsChecked = z;
        displayPreference(preferenceScreen);
        updateState(this.mWifiCloudSyncPreference);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        updateState(this.mWifiCloudSyncPreference);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void refreshSummary(Preference preference) {
        SecSwitchPreferenceScreen secSwitchPreferenceScreen =
                (SecSwitchPreferenceScreen) this.mWifiCloudSyncPreference;
        boolean z = this.mIsChecked;
        secSwitchPreferenceScreen.getClass();
        SecPreferenceUtils.applySummaryColor(secSwitchPreferenceScreen, z);
        super.refreshSummary(preference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        Settings.Global.putInt(
                this.mContext.getContentResolver(), SCLOUD_WIFI_SYNC_ENABLED, z ? 1 : 0);
        this.mStore.mSetting.setAutoSync(z);
        this.mIsChecked = z;
        refreshSummary(this.mWifiCloudSyncPreference);
        SALogging.insertSALog(z ? 1L : 0L, "WIFI_200", "1245");
        return true;
    }

    public void setFragment(Fragment fragment) {
        this.mFragment = fragment;
    }

    public void setSettingsStore(WifiCloudSettingsStore wifiCloudSettingsStore) {
        this.mStore = wifiCloudSettingsStore;
        Log.d(TAG, "setSettingsStore : " + this.mStore);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        refreshSummary(this.mWifiCloudSyncPreference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
