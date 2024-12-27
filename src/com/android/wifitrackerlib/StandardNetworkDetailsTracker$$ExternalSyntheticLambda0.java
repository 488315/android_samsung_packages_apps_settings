package com.android.wifitrackerlib;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;

import com.samsung.android.wifi.SemWifiConfiguration;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class StandardNetworkDetailsTracker$$ExternalSyntheticLambda0
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StandardNetworkDetailsTracker f$0;

    public /* synthetic */ StandardNetworkDetailsTracker$$ExternalSyntheticLambda0(
            StandardNetworkDetailsTracker standardNetworkDetailsTracker, int i) {
        this.$r8$classId = i;
        this.f$0 = standardNetworkDetailsTracker;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        StandardNetworkDetailsTracker standardNetworkDetailsTracker = this.f$0;
        switch (i) {
            case 0:
                WifiConfiguration wifiConfiguration = (WifiConfiguration) obj;
                standardNetworkDetailsTracker.getClass();
                if (wifiConfiguration.isPasspoint()) {
                    return false;
                }
                StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey =
                        standardNetworkDetailsTracker.mKey;
                return standardWifiEntryKey.equals(
                        new StandardWifiEntry.StandardWifiEntryKey(
                                wifiConfiguration, standardWifiEntryKey.mIsTargetingNewNetworks));
            case 1:
                standardNetworkDetailsTracker.getClass();
                return ((SemWifiConfiguration) obj)
                        .configKey.equals(
                                standardNetworkDetailsTracker
                                        .mChosenEntry
                                        .getWifiConfiguration()
                                        .getKey());
            default:
                standardNetworkDetailsTracker.getClass();
                return new StandardWifiEntry.ScanResultKey((ScanResult) obj)
                        .equals(standardNetworkDetailsTracker.mKey.mScanResultKey);
        }
    }
}
