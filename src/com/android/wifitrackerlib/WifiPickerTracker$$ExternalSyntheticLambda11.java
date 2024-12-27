package com.android.wifitrackerlib;

import android.net.wifi.ScanResult;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda11
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StandardWifiEntry.ScanResultKey f$0;

    public /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda11(
            StandardWifiEntry.ScanResultKey scanResultKey, int i) {
        this.$r8$classId = i;
        this.f$0 = scanResultKey;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        StandardWifiEntry.ScanResultKey scanResultKey = this.f$0;
        switch (i) {
            case 0:
                return ((KnownNetworkEntry) obj).mKey.mScanResultKey.equals(scanResultKey);
            default:
                return scanResultKey.equals(new StandardWifiEntry.ScanResultKey((ScanResult) obj));
        }
    }
}
