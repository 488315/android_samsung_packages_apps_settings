package com.android.settingslib.deviceinfo;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settingslib.core.lifecycle.Lifecycle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AbstractWifiMacAddressPreferenceController
        extends AbstractConnectivityPreferenceController {
    public static final String[] CONNECTIVITY_INTENTS = {
        "android.net.conn.CONNECTIVITY_CHANGE",
        "android.net.wifi.LINK_CONFIGURATION_CHANGED",
        "android.net.wifi.STATE_CHANGE"
    };
    static final String KEY_WIFI_MAC_ADDRESS = "wifi_mac_address";
    static final int OFF = 0;
    static final int ON = 1;
    public Preference mWifiMacAddress;
    public final WifiManager mWifiManager;

    public AbstractWifiMacAddressPreferenceController(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
        this.mWifiManager = (WifiManager) context.getSystemService(WifiManager.class);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mWifiMacAddress = preferenceScreen.findPreference(KEY_WIFI_MAC_ADDRESS);
        updateConnectivity();
    }

    @Override // com.android.settingslib.deviceinfo.AbstractConnectivityPreferenceController
    public final String[] getConnectivityIntents() {
        return CONNECTIVITY_INTENTS;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return KEY_WIFI_MAC_ADDRESS;
    }

    @Override // com.android.settingslib.deviceinfo.AbstractConnectivityPreferenceController
    public final void updateConnectivity() {
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager == null || this.mWifiMacAddress == null) {
            return;
        }
        String[] factoryMacAddresses = wifiManager.getFactoryMacAddresses();
        String str =
                (factoryMacAddresses == null || factoryMacAddresses.length <= 0)
                        ? null
                        : factoryMacAddresses[0];
        if (TextUtils.isEmpty(str) || str.equals("02:00:00:00:00:00")) {
            this.mWifiMacAddress.setSummary(R.string.status_unavailable);
        } else {
            this.mWifiMacAddress.setSummary(str.toUpperCase());
        }
    }
}
