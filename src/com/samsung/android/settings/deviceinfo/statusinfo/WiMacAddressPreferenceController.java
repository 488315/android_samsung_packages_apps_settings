package com.samsung.android.settings.deviceinfo.statusinfo;

import android.net.ConnectivityManager;
import android.os.SystemProperties;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.deviceinfo.AbstractConnectivityPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class WiMacAddressPreferenceController extends AbstractConnectivityPreferenceController
        implements PreferenceControllerMixin {
    public static final String[] CONNECTIVITY_INTENTS = {
        "android.bluetooth.adapter.action.STATE_CHANGED",
        "android.net.conn.CONNECTIVITY_CHANGE",
        "android.net.wifi.LINK_CONFIGURATION_CHANGED",
        "android.net.wifi.STATE_CHANGE"
    };
    public ConnectivityManager mCM;
    public Preference mWiMacPref;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mWiMacPref = preferenceScreen.findPreference("wimax_mac_address");
        updateConnectivity();
    }

    @Override // com.android.settingslib.deviceinfo.AbstractConnectivityPreferenceController
    public final String[] getConnectivityIntents() {
        return CONNECTIVITY_INTENTS;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "wimax_mac_address";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mCM.getNetworkInfo(6) != null;
    }

    @Override // com.android.settingslib.deviceinfo.AbstractConnectivityPreferenceController
    public final void updateConnectivity() {
        if (!isAvailable() || this.mWiMacPref == null) {
            return;
        }
        this.mWiMacPref.setSummary(
                SystemProperties.get(
                        "net.wimax.mac.address",
                        this.mContext.getString(R.string.status_unavailable)));
    }
}
