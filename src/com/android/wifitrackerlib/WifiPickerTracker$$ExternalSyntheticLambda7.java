package com.android.wifitrackerlib;

import android.net.wifi.sharedconnectivity.app.HotspotNetwork;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda7 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Set f$0;
    public final /* synthetic */ Map f$1;

    public /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda7(Set set, Map map, int i) {
        this.$r8$classId = i;
        this.f$0 = set;
        this.f$1 = map;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                Set set = this.f$0;
                Map map = this.f$1;
                KnownNetworkEntry knownNetworkEntry = (KnownNetworkEntry) obj;
                StandardWifiEntry.ScanResultKey scanResultKey =
                        knownNetworkEntry.mKey.mScanResultKey;
                set.remove(scanResultKey);
                knownNetworkEntry.updateScanResultInfo((List) map.get(scanResultKey));
                break;
            case 1:
                Set set2 = this.f$0;
                Map map2 = this.f$1;
                HotspotNetworkEntry hotspotNetworkEntry = (HotspotNetworkEntry) obj;
                Long valueOf = Long.valueOf(hotspotNetworkEntry.mKey.mDeviceId);
                set2.remove(valueOf);
                hotspotNetworkEntry.updateHotspotNetworkData((HotspotNetwork) map2.get(valueOf));
                break;
            default:
                Set set3 = this.f$0;
                Map map3 = this.f$1;
                StandardWifiEntry standardWifiEntry = (StandardWifiEntry) obj;
                StandardWifiEntry.ScanResultKey scanResultKey2 =
                        standardWifiEntry.mKey.mScanResultKey;
                set3.remove(scanResultKey2);
                standardWifiEntry.updateScanResultInfo((List) map3.get(scanResultKey2));
                break;
        }
    }
}
