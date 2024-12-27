package com.android.wifitrackerlib;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiNetworkSuggestion;
import android.net.wifi.hotspot2.PasspointConfiguration;

import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class SavedNetworkTracker$$ExternalSyntheticLambda0
        implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ SavedNetworkTracker$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((WifiNetworkSuggestion) obj).getWifiConfiguration();
            case 1:
                return new StandardWifiEntry.StandardWifiEntryKey((WifiConfiguration) obj, false);
            case 2:
                return ((WifiConfiguration) obj).SSID;
            case 3:
                return ((WifiNetworkSuggestion) obj).getWifiConfiguration();
            case 4:
                return PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(
                        ((PasspointConfiguration) obj).getUniqueId());
            default:
                return new StandardWifiEntry.ScanResultKey((ScanResult) obj);
        }
    }
}
