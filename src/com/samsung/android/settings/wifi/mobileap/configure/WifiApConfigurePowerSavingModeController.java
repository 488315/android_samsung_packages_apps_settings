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

import com.samsung.android.settings.WifiApUtils;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.mobileap.WifiApEditSettings;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApConfigurePowerSavingModeController extends TogglePreferenceController
        implements LifecycleEventObserver {
    private static final String BUNDLE_KEY_POWER_SAVING_MODE_SWITCH_VALUE =
            "bundle_key_power_saving_mode_switch_value";
    public static final String KEY_PREFERENCE = "power_saving_mode";
    private static final String TAG = "WifiApConfigurePowerSavingModeController";
    private Context mContext;
    private SecSwitchPreference mThisSwitchPreference;
    private WifiApEditSettings mWifiApEditSettings;

    public WifiApConfigurePowerSavingModeController(Context context, String str) {
        super(context, str);
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
        secSwitchPreference.setTitle(R.string.wifi_ap_power_save);
        this.mThisSwitchPreference.setSummary(
                WifiApUtils.getString(this.mContext, R.string.wifi_ap_power_save_hint));
        this.mThisSwitchPreference.setChecked(
                Settings.Secure.getInt(
                                this.mContext.getContentResolver(),
                                "wifi_ap_powersave_mode_checked",
                                0)
                        == 1);
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

    public boolean isPowerSavingModeModified() {
        boolean z = false;
        if (isAvailable()) {
            boolean z2 =
                    Settings.Secure.getInt(
                                    this.mContext.getContentResolver(),
                                    "wifi_ap_powersave_mode_checked",
                                    0)
                            == 1;
            boolean threadEnabled = getThreadEnabled();
            Log.i(TAG, "Power Saving Mode States Old: " + z2 + ", new: " + threadEnabled);
            if (z2 != threadEnabled) {
                z = true;
            }
        }
        AbsAdapter$$ExternalSyntheticOutline0.m("Is Power Saving Mode State modified: ", TAG, z);
        return z;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    public void onRestoreInstanceState(Bundle bundle) {
        if (isAvailable() && bundle.containsKey(BUNDLE_KEY_POWER_SAVING_MODE_SWITCH_VALUE)) {
            boolean z = bundle.getBoolean(BUNDLE_KEY_POWER_SAVING_MODE_SWITCH_VALUE);
            AbsAdapter$$ExternalSyntheticOutline0.m("onRestoreInstanceState: ", TAG, z);
            this.mThisSwitchPreference.setChecked(z);
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (isAvailable()) {
            bundle.putBoolean(
                    BUNDLE_KEY_POWER_SAVING_MODE_SWITCH_VALUE,
                    this.mThisSwitchPreference.isChecked());
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public void savePowerSavingModeSwitchState() {
        if (isPowerSavingModeModified()) {
            Log.i(TAG, "Updating new Power Saving Mode value: " + getThreadEnabled());
            Settings.Secure.putInt(
                    this.mContext.getContentResolver(),
                    "wifi_ap_powersave_mode_checked",
                    getThreadEnabled() ? 1 : 0);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        AbsAdapter$$ExternalSyntheticOutline0.m("setChecked: ", TAG, z);
        SALogging.insertSALog(z ? 1L : 0L, "TETH_012", "8023");
        return true;
    }

    public void setHost(WifiApEditSettings wifiApEditSettings) {
        this.mWifiApEditSettings = wifiApEditSettings;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {}
}
