package com.samsung.android.settings.wifi.details;

import android.content.Context;
import android.net.wifi.hotspot2.PasspointConfiguration;

import androidx.preference.PreferenceScreen;

import com.android.settingslib.core.AbstractPreferenceController;
import com.android.wifitrackerlib.PasspointWifiEntry;
import com.android.wifitrackerlib.WifiEntry;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiPasspointPreferenceController extends AbstractPreferenceController {
    public final PasspointConfiguration mPasspointConfig;
    public final PasspointWifiEntry mWifiEntry;

    public WifiPasspointPreferenceController(Context context, WifiEntry wifiEntry) {
        super(context);
        PasspointWifiEntry passpointWifiEntry = (PasspointWifiEntry) wifiEntry;
        this.mWifiEntry = passpointWifiEntry;
        this.mPasspointConfig = passpointWifiEntry.mPasspointConfig;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        preferenceScreen
                .findPreference("passpoint")
                .setSummary(this.mPasspointConfig.getHomeSp().getFqdn());
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "passpoint";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mWifiEntry.isSubscription();
    }
}
