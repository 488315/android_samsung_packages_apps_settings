package com.samsung.android.settings.wifi.advanced;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.scloud.lib.setting.SamsungCloudRPCSettingV2;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.wifitrackerlib.SemWifiUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiCloudSyncPreferenceController extends BasePreferenceController
        implements OnResume {
    private static final String KEY_WIFI_CLOUD_SA = "wifi_cloud_sa";
    private static final String TAG = "ConfigureWifiSettings.WifiCloudSync";
    private Fragment mFragment;
    private boolean mHasDigitalLegacyModeFlag;
    private WifiCloudSettingsStore mStore;
    private Preference mWifiSync;

    public WifiCloudSyncPreferenceController(Context context, String str) {
        super(context, str);
        this.mHasDigitalLegacyModeFlag = false;
        this.mStore = new WifiCloudSettingsStore();
        this.mStore.setRpcSettings(
                new SamsungCloudRPCSettingV2(
                        this.mContext.getApplicationContext(),
                        "com.android.settings.wifiprofilesync",
                        "com.android.settings.wifiprofilesetting"));
    }

    private void openSamsungAccount() {
        Intent intent = new Intent("com.osp.app.signin.action.ADD_SAMSUNG_ACCOUNT");
        intent.putExtra("mypackage", this.mContext.getPackageName());
        intent.putExtra("OSP_VER", "OSP_02");
        intent.putExtra("MODE", "ADD_ACCOUNT");
        intent.putExtra("Is_From_SA_Verify", true);
        this.mContext.startActivity(intent);
    }

    private void openScloud() {
        Fragment fragment;
        if (SemWifiUtils.getSamsungAccount(this.mContext) == null) {
            openSamsungAccount();
            return;
        }
        SamsungCloudRPCSettingV2 samsungCloudRPCSettingV2 = this.mStore.mSetting;
        if (samsungCloudRPCSettingV2 == null || (fragment = this.mFragment) == null) {
            return;
        }
        samsungCloudRPCSettingV2.showSetting(fragment.getActivity());
    }

    private void openScloudStore() {
        Intent intent = new Intent();
        intent.setData(
                Uri.parse(
                        "samsungapps://ProductDetail/com.samsung.android.scloud/?source=Samsung"
                            + " Cloud"));
        intent.putExtra("type", "cover");
        intent.addFlags(335544352);
        this.mContext.startActivity(intent);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mWifiSync = preferenceScreen.findPreference(KEY_WIFI_CLOUD_SA);
        if ("VZW".equals(SemWifiUtils.readSalesCode())) {
            this.mWifiSync.setTitle(R.string.sec_bluetooth_advanced_sync_with_account_vzw);
        }
        refreshSummary(this.mWifiSync);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (Rune.isChinaModel()) {
            WifiCloudSettingsStore wifiCloudSettingsStore = this.mStore;
            if (!wifiCloudSettingsStore.mIsPersonalInfoOn
                    || !wifiCloudSettingsStore.mIsConsentedToUseNetwork) {
                return 0;
            }
        }
        WifiCloudSettingsStore wifiCloudSettingsStore2 = this.mStore;
        if (!wifiCloudSettingsStore2.mIsMainSyncOn || !wifiCloudSettingsStore2.mIsAllowedInEdp) {
            return 0;
        }
        int i =
                (SemWifiUtils.getSamsungAccount(this.mContext) == null
                                && SemWifiUtils.isWifiSyncEnabled(this.mContext))
                        ? 0
                        : 3;
        boolean hasFlags =
                ((UserManager) this.mContext.getSystemService("user"))
                        .semGetSemUserInfo(UserHandle.semGetMyUserId())
                        .hasFlags(16777216);
        this.mHasDigitalLegacyModeFlag = hasFlags;
        if (hasFlags) {
            return 5;
        }
        return i;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        String string = this.mContext.getResources().getString(R.string.app_name_samsung_account);
        String string2 = this.mContext.getResources().getString(R.string.cloud_title);
        if (this.mHasDigitalLegacyModeFlag) {
            return this.mContext
                    .getResources()
                    .getString(R.string.sec_bluetooth_advanced_digital_legacy_mode_summary);
        }
        if (SemWifiUtils.getSamsungAccount(this.mContext) == null) {
            return this.mContext
                    .getResources()
                    .getString(R.string.wifi_advanced_scloud_sync_account, string);
        }
        if (!this.mStore.mIsAllowedInEdp) {
            SecPreference secPreference = (SecPreference) this.mWifiSync;
            secPreference.getClass();
            SecPreferenceUtils.applySummaryColor(secPreference, true);
            return Utils.isTablet()
                    ? this.mContext
                            .getResources()
                            .getString(
                                    R.string
                                            .sec_bluetooth_advanced_sync_with_cloud_encryption_tablet)
                    : this.mContext
                            .getResources()
                            .getString(
                                    R.string
                                            .sec_bluetooth_advanced_sync_with_cloud_encryption_phone);
        }
        if (Rune.isChinaModel()) {
            WifiCloudSettingsStore wifiCloudSettingsStore = this.mStore;
            if (!wifiCloudSettingsStore.mIsConsentedToUseNetwork) {
                return this.mContext
                        .getResources()
                        .getString(
                                R.string.wifi_advanced_scloud_sync_no_consent_to_use_network_chn);
            }
            if (!wifiCloudSettingsStore.mIsPersonalInfoOn) {
                return SemWifiUtils.hasPackage(this.mContext)
                        ? this.mContext
                                .getResources()
                                .getString(
                                        R.string.wifi_advanced_scloud_sync_personal_information_chn)
                        : this.mContext
                                .getResources()
                                .getString(
                                        R.string.wifi_advanced_scloud_sync_no_scloud_package_chn,
                                        string2);
            }
            if (!wifiCloudSettingsStore.mIsUserAgreesPolicyNotice) {
                return this.mContext
                        .getResources()
                        .getString(R.string.wifi_advanced_sync_no_privacy_notice_chn);
            }
        }
        return this.mContext
                .getResources()
                .getString(R.string.sec_bluetooth_advanced_sync_turned_off);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        WifiCloudSettingsStore wifiCloudSettingsStore;
        SamsungCloudRPCSettingV2 samsungCloudRPCSettingV2;
        Fragment fragment;
        Fragment fragment2;
        if (KEY_WIFI_CLOUD_SA.equals(preference.getKey())) {
            if (!Rune.isChinaModel()
                    || (samsungCloudRPCSettingV2 = (wifiCloudSettingsStore = this.mStore).mSetting)
                            == null) {
                openScloud();
            } else if (!wifiCloudSettingsStore.mIsConsentedToUseNetwork
                    && (fragment2 = this.mFragment) != null) {
                samsungCloudRPCSettingV2.showSetting(fragment2.getActivity());
            } else if (wifiCloudSettingsStore.mIsPersonalInfoOn) {
                openScloud();
            } else if (!SemWifiUtils.hasPackage(this.mContext)
                    || (fragment = this.mFragment) == null) {
                openScloudStore();
            } else {
                this.mStore.mSetting.showSetting(fragment.getActivity());
            }
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        updateState(this.mWifiSync);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setFragment(Fragment fragment) {
        this.mFragment = fragment;
    }

    public void setSettingsStore(WifiCloudSettingsStore wifiCloudSettingsStore) {
        this.mStore = wifiCloudSettingsStore;
        Log.d(TAG, "setSettingsStore : " + this.mStore);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
