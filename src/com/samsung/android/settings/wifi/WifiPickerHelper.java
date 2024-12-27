package com.samsung.android.settings.wifi;

import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Debug;
import android.util.Log;

import com.android.settings.accessibility.PreferredShortcuts$$ExternalSyntheticOutline0;
import com.android.wifitrackerlib.WifiEntry;

import com.sec.ims.gls.GlsIntent;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiPickerHelper {
    public static final boolean DBG = Debug.semIsProductDev();
    public final boolean mIsFromLocation;
    public WifiEntry mLastUserPickedNetwork;
    public final WifiManager mWifiManager;

    public WifiPickerHelper(WifiManager wifiManager, Intent intent) {
        this.mWifiManager = wifiManager;
        if (intent.hasExtra("extra_location_services")
                && intent.getStringExtra("extra_location_services")
                        .equals(GlsIntent.Extras.EXTRA_LOCATION)) {
            this.mIsFromLocation = true;
        }
    }

    public final Intent getApIntent(WifiEntry wifiEntry) {
        String semGetBssid;
        WifiEntry wifiEntry2;
        if (wifiEntry == null && (wifiEntry2 = this.mLastUserPickedNetwork) != null) {
            wifiEntry = wifiEntry2;
        }
        if (wifiEntry == null) {
            Log.e("WifiSettings.Picker", "return empty intent");
            return new Intent();
        }
        Intent intent = new Intent();
        String ssid = wifiEntry.getSsid();
        intent.putExtra("ssid", ssid);
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        if (wifiEntry.getConnectedState() != 2 || connectionInfo == null) {
            semGetBssid = wifiEntry.semGetBssid();
            if (semGetBssid == null || semGetBssid.equals("any")) {
                semGetBssid = wifiEntry.semGetBssid();
            }
        } else {
            semGetBssid = connectionInfo.getBSSID();
        }
        intent.putExtra("bssid", semGetBssid);
        intent.putExtra("security", wifiEntry.getSecurity$1());
        int semGetFrequency =
                (wifiEntry.getConnectedState() != 2 || connectionInfo == null)
                        ? wifiEntry.semGetFrequency(semGetBssid)
                        : connectionInfo.getFrequency();
        intent.putExtra("frequency", semGetFrequency);
        if (DBG && connectionInfo != null) {
            StringBuilder m =
                    PreferredShortcuts$$ExternalSyntheticOutline0.m(
                            semGetFrequency, "ssid:", ssid, ", ap.freq:", ", info.freq:");
            m.append(connectionInfo.getFrequency());
            Log.d("WifiSettings.Picker", m.toString());
        }
        return intent;
    }
}
