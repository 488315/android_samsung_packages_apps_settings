package com.android.wifitrackerlib;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.text.TextUtils;

import java.util.Map;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda1
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        boolean z;
        boolean z2;
        switch (this.$r8$classId) {
            case 0:
                return !TextUtils.isEmpty(((ScanResult) obj).SSID);
            case 1:
                return !((WifiConfiguration) obj).isEphemeral();
            case 2:
                return ((WifiEntry) obj).getConnectedState() == 0;
            case 3:
                StandardWifiEntry standardWifiEntry = (StandardWifiEntry) obj;
                if (standardWifiEntry.getConnectedState() == 0) {
                    synchronized (standardWifiEntry) {
                        z = standardWifiEntry.mIsUserShareable;
                    }
                    if (z) {
                        return true;
                    }
                }
                return false;
            case 4:
                return ((PasspointWifiEntry) obj).getConnectedState() == 0;
            case 5:
                OsuWifiEntry osuWifiEntry = (OsuWifiEntry) obj;
                if (osuWifiEntry.getConnectedState() == 0) {
                    synchronized (osuWifiEntry) {
                        z2 = osuWifiEntry.mIsAlreadyProvisioned;
                    }
                    if (!z2) {
                        return true;
                    }
                }
                return false;
            case 6:
                return ((WifiEntry) obj).getConnectedState() == 0;
            case 7:
                return ((HotspotNetworkEntry) obj).getConnectedState() == 0;
            case 8:
                ScanResult scanResult = (ScanResult) obj;
                return (TextUtils.isEmpty(scanResult.SSID)
                                || scanResult.capabilities.contains("[IBSS]"))
                        ? false
                        : true;
            case 9:
                StandardWifiEntry standardWifiEntry2 = (StandardWifiEntry) obj;
                return standardWifiEntry2.mLevel == -1
                        && standardWifiEntry2.getConnectedState() == 0;
            case 10:
                return ((OsuWifiEntry) ((Map.Entry) obj).getValue()).mLevel == -1;
            case 11:
                ScanResult scanResult2 = (ScanResult) obj;
                return (TextUtils.isEmpty(scanResult2.SSID)
                                || scanResult2.capabilities.contains("[IBSS]"))
                        ? false
                        : true;
            case 12:
                StandardWifiEntry standardWifiEntry3 = (StandardWifiEntry) obj;
                return standardWifiEntry3.mLevel == -1
                        && standardWifiEntry3.getConnectedState() == 0;
            default:
                KnownNetworkEntry knownNetworkEntry = (KnownNetworkEntry) obj;
                return knownNetworkEntry.mLevel == -1 && knownNetworkEntry.getConnectedState() == 0;
        }
    }
}
