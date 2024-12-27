package com.android.settings.wifi.tether;

import android.net.wifi.SoftApConfiguration;
import android.net.wifi.WifiManager;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiTetherMaximizeCompatibilityPreferenceController
        extends WifiTetherBasePreferenceController {
    public boolean mIsChecked;
    boolean mShouldHidePreference;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "wifi_tether_maximize_compatibility";
    }

    @Override // com.android.settings.wifi.tether.WifiTetherBasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (this.mShouldHidePreference) {
            return false;
        }
        return super.isAvailable();
    }

    public boolean isMaximizeCompatibilityEnabled() {
        SoftApConfiguration softApConfiguration;
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager == null
                || (softApConfiguration = wifiManager.getSoftApConfiguration()) == null) {
            return false;
        }
        if (this.mWifiManager.isBridgedApConcurrencySupported()) {
            boolean isBridgedModeOpportunisticShutdownEnabled =
                    softApConfiguration.isBridgedModeOpportunisticShutdownEnabled();
            AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                    "isBridgedModeOpportunisticShutdownEnabled:",
                    "WifiTetherMaximizeCompatibilityPref",
                    isBridgedModeOpportunisticShutdownEnabled);
            return !isBridgedModeOpportunisticShutdownEnabled;
        }
        int band = softApConfiguration.getBand();
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                band, "getBand:", "WifiTetherMaximizeCompatibilityPref");
        return band == 1;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        this.mIsChecked = ((Boolean) obj).booleanValue();
        WifiTetherBasePreferenceController.OnTetherConfigUpdateListener
                onTetherConfigUpdateListener = this.mListener;
        if (onTetherConfigUpdateListener == null) {
            return true;
        }
        onTetherConfigUpdateListener.onTetherConfigUpdated(this);
        return true;
    }

    @Override // com.android.settings.wifi.tether.WifiTetherBasePreferenceController
    public final void updateDisplay$1() {
        Preference preference = this.mPreference;
        if (preference == null) {
            return;
        }
        WifiManager wifiManager = this.mWifiManager;
        boolean z = false;
        if (wifiManager != null
                && wifiManager.is5GHzBandSupported()
                && this.mWifiManager.getCountryCode() != null) {
            z = true;
        }
        preference.setEnabled(z);
        ((TwoStatePreference) this.mPreference).setChecked(this.mIsChecked);
        this.mPreference.setSummary(
                this.mWifiManager.isBridgedApConcurrencySupported()
                        ? R.string.wifi_hotspot_maximize_compatibility_dual_ap_summary
                        : R.string.wifi_hotspot_maximize_compatibility_single_ap_summary);
    }
}
