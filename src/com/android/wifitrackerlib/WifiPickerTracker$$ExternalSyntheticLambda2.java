package com.android.wifitrackerlib;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.net.wifi.sharedconnectivity.app.HotspotNetwork;
import android.net.wifi.sharedconnectivity.app.KnownNetwork;

import java.util.ArrayList;
import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda2 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                KnownNetwork knownNetwork = (KnownNetwork) obj;
                return new StandardWifiEntry.ScanResultKey(
                        knownNetwork.getSsid(), new ArrayList(knownNetwork.getSecurityTypes()));
            case 1:
                return Integer.valueOf(((WifiConfiguration) obj).networkId);
            case 2:
                return ((StandardWifiEntry) obj).mKey.mScanResultKey;
            case 3:
                return Long.valueOf(((HotspotNetwork) obj).getDeviceId());
            case 4:
                return (HotspotNetwork) obj;
            case 5:
                return PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(
                        ((PasspointConfiguration) obj).getUniqueId());
            case 6:
                return (KnownNetwork) obj;
            default:
                return Integer.valueOf(((WifiConfiguration) obj).networkId);
        }
    }
}
