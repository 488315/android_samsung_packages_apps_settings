package com.samsung.android.settings.wifi.mobileap.configure;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.network.apn.ApnPreference$$ExternalSyntheticOutline0;

import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.wifi.mobileap.WifiApEditSettings;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSoftApUtils;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApConfigureSupportWifi6Controller extends TogglePreferenceController
        implements LifecycleEventObserver {
    private static final String BUNDLE_KEY_SUPPORT_WIFI_6_SWITCH_VALUE =
            "bundle_key_support_wifi_6_switch_value";
    public static final String KEY_PREFERENCE = "support_wifi_6_standard";
    private static final String TAG = "WifiApConfigureSupportWifi6Controller";
    private boolean isValueModifiedManually;
    private Context mContext;
    private SecSwitchPreference mThisSwitchPreference;
    private WifiApEditSettings mWifiApConfigureSettings;

    public WifiApConfigureSupportWifi6Controller(Context context, String str) {
        super(context, str);
        this.isValueModifiedManually = false;
        this.mContext = context;
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
        secSwitchPreference.setTitle(R.string.wifi_ap_11ax_title);
        this.mThisSwitchPreference.setSummary(R.string.wifi_ap_11ax_summary);
        if (WifiApSoftApUtils.getWifiApBandConfig(this.mContext).isThisBand6Ghz()) {
            this.mThisSwitchPreference.setChecked(true);
        } else {
            this.mThisSwitchPreference.setChecked(
                    WifiApSoftApUtils.isSupportWifi6StandardEnabled(this.mContext));
        }
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
        Log.i(TAG, "isChecked");
        return this.mThisSwitchPreference.isChecked();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    public boolean isSupportWifi6SwitchStateModified() {
        boolean z;
        if (isAvailable()) {
            boolean isSupportWifi6StandardEnabled =
                    WifiApSoftApUtils.isSupportWifi6StandardEnabled(this.mContext);
            boolean threadEnabled = getThreadEnabled();
            Log.i(
                    TAG,
                    "Support Wifi6 Standard States Old: "
                            + isSupportWifi6StandardEnabled
                            + ", new: "
                            + threadEnabled);
            if (isSupportWifi6StandardEnabled != threadEnabled) {
                z = true;
                AbsAdapter$$ExternalSyntheticOutline0.m(
                        "Support Wifi6 Standard State modified: ", TAG, z);
                return z;
            }
        }
        z = false;
        AbsAdapter$$ExternalSyntheticOutline0.m("Support Wifi6 Standard State modified: ", TAG, z);
        return z;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    public void onRestoreInstanceState(Bundle bundle) {
        if (isAvailable() && bundle.containsKey(BUNDLE_KEY_SUPPORT_WIFI_6_SWITCH_VALUE)) {
            boolean z = bundle.getBoolean(BUNDLE_KEY_SUPPORT_WIFI_6_SWITCH_VALUE);
            AbsAdapter$$ExternalSyntheticOutline0.m("onRestoreInstanceState: ", TAG, z);
            this.mThisSwitchPreference.setChecked(z);
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (isAvailable()) {
            bundle.putBoolean(
                    BUNDLE_KEY_SUPPORT_WIFI_6_SWITCH_VALUE, this.mThisSwitchPreference.isChecked());
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public void saveSupportWifi6SwitchState() {
        if (this.mWifiApConfigureSettings.getBandPreferenceConfig().isThisBand6Ghz()) {
            Log.i(
                    TAG,
                    "As 6Ghz is selected NOT Updating new Support Wifi6 Standard state: "
                            + getThreadEnabled());
            return;
        }
        if (isSupportWifi6SwitchStateModified()) {
            Log.i(TAG, "Updating new Support Wifi6 Standard state: " + getThreadEnabled());
            Settings.Secure.putInt(
                    this.mContext.getContentResolver(),
                    "wifi_ap_11ax_mode_checked",
                    getThreadEnabled() ? 1 : 0);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        boolean isThisBand6Ghz =
                this.mWifiApConfigureSettings.getBandPreferenceConfig().isThisBand6Ghz();
        Log.i(TAG, "setChecked: isChecked" + z + ", wifiApBandConfig(is6Ghz) : " + isThisBand6Ghz);
        if (!isThisBand6Ghz) {
            this.isValueModifiedManually = true;
            return true;
        }
        Context context = this.mContext;
        ApnPreference$$ExternalSyntheticOutline0.m(
                context, R.string.wifi_ap_turn_on_6GHZ_dependents, context, 0);
        return false;
    }

    public void setHost(WifiApEditSettings wifiApEditSettings) {
        this.mWifiApConfigureSettings = wifiApEditSettings;
    }

    public void updateState() {
        if (isAvailable()) {
            boolean isThisBand6Ghz =
                    this.mWifiApConfigureSettings.getBandPreferenceConfig().isThisBand6Ghz();
            AbsAdapter$$ExternalSyntheticOutline0.m(
                    "Updating state: wifiApBandConfig(is6Ghz) : ", TAG, isThisBand6Ghz);
            if (isThisBand6Ghz) {
                this.mThisSwitchPreference.setChecked(true);
            } else {
                if (this.isValueModifiedManually) {
                    return;
                }
                this.mThisSwitchPreference.setChecked(
                        WifiApSoftApUtils.isSupportWifi6StandardEnabled(this.mContext));
            }
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {}
}
