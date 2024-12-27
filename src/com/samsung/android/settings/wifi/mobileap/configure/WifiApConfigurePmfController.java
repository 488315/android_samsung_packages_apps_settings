package com.samsung.android.settings.wifi.mobileap.configure;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.view.SeslContentViewInsetsCallback$$ExternalSyntheticOutline0;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreference;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.settings.WifiApUtils;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.mobileap.WifiApEditSettings;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSoftApUtils;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApConfigurePmfController extends TogglePreferenceController {
    private static final String BUNDLE_KEY_PMF_SWITCH_VALUE = "bundle_key_pmf_switch_value";
    public static final String KEY_PREFERENCE = "protected_management_frames";
    private static final String TAG = "WifiApConfigurePmfController";
    private Boolean isPmfCheckedOnScreenLaunch;
    private Context mContext;
    private int mIsManuallySwitchCheckedBeforeSave;
    private SecSwitchPreference mThisSwitchPreference;
    private WifiApEditSettings mWifiApConfigureSettings;

    public WifiApConfigurePmfController(Context context, String str) {
        super(context, str);
        this.mIsManuallySwitchCheckedBeforeSave = -1;
        this.mContext = context;
        this.isPmfCheckedOnScreenLaunch =
                Boolean.valueOf(
                        Settings.Secure.getInt(
                                        context.getContentResolver(), "wifi_ap_pmf_checked", 0)
                                == 1);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Log.i(TAG, "displayPreference");
        SecSwitchPreference secSwitchPreference =
                (SecSwitchPreference) preferenceScreen.findPreference(KEY_PREFERENCE);
        this.mThisSwitchPreference = secSwitchPreference;
        secSwitchPreference.setTitle(R.string.wifi_ap_pmf_title);
        this.mThisSwitchPreference.setSummary(
                WifiApUtils.getString(this.mContext, R.string.wifi_ap_pmf_hint));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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
        return 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
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
        return this.mThisSwitchPreference.isChecked();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    public boolean isValueModified() {
        if (isAvailable()) {
            boolean z =
                    Settings.Secure.getInt(
                                    this.mContext.getContentResolver(), "wifi_ap_pmf_checked", 0)
                            == 1;
            int securityType = WifiApSoftApUtils.getSecurityType(this.mContext);
            int securityType2 = this.mWifiApConfigureSettings.getSecurityType();
            if ((securityType == 2 || securityType == 3 || securityType == 5)
                    && securityType2 != 0
                    && securityType2 != 1) {
                AbsAdapter$$ExternalSyntheticOutline0.m(
                        "Settings oldPMFvalue value to true, earlier value: ", TAG, z);
                z = true;
            }
            boolean threadEnabled = getThreadEnabled();
            r1 = z != threadEnabled;
            SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                    Utils$$ExternalSyntheticOutline0.m(
                            "Values, Old: ", z, ", New: ", threadEnabled, "Is value modified: "),
                    r1,
                    TAG);
        }
        return r1;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    public void onRestoreInstanceState(Bundle bundle) {
        if (isAvailable() && bundle.containsKey(BUNDLE_KEY_PMF_SWITCH_VALUE)) {
            boolean z = bundle.getBoolean(BUNDLE_KEY_PMF_SWITCH_VALUE);
            Log.i(TAG, "onRestoreInstanceState: " + z);
            this.isPmfCheckedOnScreenLaunch = Boolean.valueOf(z);
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (isAvailable()) {
            bundle.putBoolean(BUNDLE_KEY_PMF_SWITCH_VALUE, this.mThisSwitchPreference.isChecked());
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public void saveValue() {
        if (isValueModified()) {
            int securityType = this.mWifiApConfigureSettings.getSecurityType();
            if (securityType == 0 || securityType == 1) {
                Log.i(TAG, "Updating new value: " + getThreadEnabled());
                Settings.Secure.putInt(
                        this.mContext.getContentResolver(),
                        "wifi_ap_pmf_checked",
                        getThreadEnabled() ? 1 : 0);
            }
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        AbsAdapter$$ExternalSyntheticOutline0.m("setChecked: ", TAG, z);
        if (z) {
            this.mIsManuallySwitchCheckedBeforeSave = 1;
        } else {
            this.mIsManuallySwitchCheckedBeforeSave = 0;
        }
        SALogging.insertSALog(z ? 1L : 0L, "TETH_012", "8021");
        return true;
    }

    public void setHost(WifiApEditSettings wifiApEditSettings) {
        this.mWifiApConfigureSettings = wifiApEditSettings;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        WifiApEditSettings wifiApEditSettings;
        super.updateState(preference);
        if (!isAvailable() || (wifiApEditSettings = this.mWifiApConfigureSettings) == null) {
            return;
        }
        int securityType = wifiApEditSettings.getSecurityType();
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                securityType, "Updating state: securityType: ", TAG);
        if (securityType == 2 || securityType == 3 || securityType == 5) {
            this.mThisSwitchPreference.setEnabled(false);
            this.mThisSwitchPreference.setChecked(true);
            return;
        }
        this.mThisSwitchPreference.setEnabled(true);
        int i = this.mIsManuallySwitchCheckedBeforeSave;
        if (i == 0) {
            this.mThisSwitchPreference.setChecked(false);
        } else if (i == 1) {
            this.mThisSwitchPreference.setChecked(true);
        } else {
            this.mThisSwitchPreference.setChecked(this.isPmfCheckedOnScreenLaunch.booleanValue());
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
