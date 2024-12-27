package com.android.settings.development;

import android.content.Context;
import android.net.wifi.WifiManager;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiScanThrottlingPreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    public final WifiManager mWifiManager;

    public WifiScanThrottlingPreferenceController(Context context) {
        super(context);
        this.mWifiManager = (WifiManager) context.getSystemService(WifiManager.class);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "wifi_scan_throttling";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        this.mWifiManager.setScanThrottleEnabled(true);
        ((TwoStatePreference) this.mPreference).setChecked(true);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        this.mWifiManager.setScanThrottleEnabled(((Boolean) obj).booleanValue());
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        ((TwoStatePreference) this.mPreference)
                .setChecked(this.mWifiManager.isScanThrottleEnabled());
    }
}
