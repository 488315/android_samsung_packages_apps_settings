package com.android.settings.wifi.p2p;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.wifi.WifiEnterpriseRestrictionUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiP2pPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin, LifecycleObserver, OnPause, OnResume {
    public final IntentFilter mFilter;
    boolean mIsWifiDirectAllow;
    final BroadcastReceiver mReceiver;
    public Preference mWifiDirectPref;
    public final WifiManager mWifiManager;

    public WifiP2pPreferenceController(
            Context context, Lifecycle lifecycle, WifiManager wifiManager) {
        super(context);
        boolean z;
        this.mReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.settings.wifi.p2p.WifiP2pPreferenceController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        WifiP2pPreferenceController wifiP2pPreferenceController =
                                WifiP2pPreferenceController.this;
                        Preference preference = wifiP2pPreferenceController.mWifiDirectPref;
                        if (preference != null) {
                            preference.setEnabled(
                                    wifiP2pPreferenceController.mWifiManager.isWifiEnabled()
                                            && wifiP2pPreferenceController.mIsWifiDirectAllow);
                        }
                    }
                };
        this.mFilter = new IntentFilter("android.net.wifi.WIFI_STATE_CHANGED");
        this.mWifiManager = wifiManager;
        if (WifiEnterpriseRestrictionUtils.hasUserRestrictionFromT(context, "no_wifi_direct")) {
            Log.w("WifiEntResUtils", "Wi-Fi Direct isn't available due to user restriction.");
            z = false;
        } else {
            z = true;
        }
        this.mIsWifiDirectAllow = z;
        lifecycle.addObserver(this);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference("wifi_direct");
        this.mWifiDirectPref = findPreference;
        if (findPreference != null) {
            findPreference.setEnabled(this.mWifiManager.isWifiEnabled() && this.mIsWifiDirectAllow);
        }
        if (this.mIsWifiDirectAllow) {
            return;
        }
        this.mWifiDirectPref.setSummary(R.string.not_allowed_by_ent);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "wifi_direct";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public final void onPause() {
        this.mContext.unregisterReceiver(this.mReceiver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        this.mContext.registerReceiver(this.mReceiver, this.mFilter);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        preference.setEnabled(this.mWifiManager.isWifiEnabled() && this.mIsWifiDirectAllow);
    }
}
