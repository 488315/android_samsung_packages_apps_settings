package com.android.wifitrackerlib;

import android.net.wifi.sharedconnectivity.app.HotspotNetwork;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class HotspotNetworkDetailsTracker$$ExternalSyntheticLambda0
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ HotspotNetworkDetailsTracker f$0;

    public /* synthetic */ HotspotNetworkDetailsTracker$$ExternalSyntheticLambda0(
            HotspotNetworkDetailsTracker hotspotNetworkDetailsTracker, int i) {
        this.$r8$classId = i;
        this.f$0 = hotspotNetworkDetailsTracker;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        HotspotNetworkDetailsTracker hotspotNetworkDetailsTracker = this.f$0;
        HotspotNetwork hotspotNetwork = (HotspotNetwork) obj;
        hotspotNetworkDetailsTracker.getClass();
        switch (i) {
            case 0:
                if (hotspotNetwork.getDeviceId()
                        == hotspotNetworkDetailsTracker.mChosenEntry.mKey.mDeviceId) {}
                break;
            default:
                if (hotspotNetwork.getDeviceId()
                        == hotspotNetworkDetailsTracker.mChosenEntry.mKey.mDeviceId) {}
                break;
        }
        return false;
    }
}
