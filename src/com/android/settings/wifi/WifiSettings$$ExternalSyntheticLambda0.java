package com.android.settings.wifi;

import android.net.wifi.WifiConfiguration;

import com.android.wifitrackerlib.WifiEntry;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiSettings$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        WifiConfiguration.NetworkSelectionStatus networkSelectionStatus;
        WifiEntry wifiEntry = (WifiEntry) obj;
        switch (this.$r8$classId) {
            case 0:
                boolean z = WifiSettings.mWifiSettingsForeground;
                if (wifiEntry.getLevel() == -1) {
                    break;
                }
                break;
            case 1:
                boolean z2 = WifiSettings.mWifiSettingsForeground;
                if (wifiEntry.getSecurity$1() == 0 || wifiEntry.getSecurity$1() == 4) {
                    break;
                }
                break;
            default:
                boolean z3 = WifiSettings.mWifiSettingsForeground;
                if (wifiEntry.isSaved()) {
                    WifiConfiguration wifiConfiguration = wifiEntry.getWifiConfiguration();
                    if (wifiConfiguration == null
                            || (networkSelectionStatus =
                                            wifiConfiguration.getNetworkSelectionStatus())
                                    == null
                            || networkSelectionStatus.getNetworkSelectionStatus() == 0
                            || 8 != networkSelectionStatus.getNetworkSelectionDisableReason()) {
                        break;
                    }
                }
                break;
        }
        return false;
    }
}
