package com.android.wifitrackerlib;


/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class BaseWifiTracker$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BaseWifiTracker.BaseWifiTrackerCallback f$0;

    public /* synthetic */ BaseWifiTracker$$ExternalSyntheticLambda3(
            BaseWifiTracker.BaseWifiTrackerCallback baseWifiTrackerCallback, int i) {
        this.$r8$classId = i;
        this.f$0 = baseWifiTrackerCallback;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        BaseWifiTracker.BaseWifiTrackerCallback baseWifiTrackerCallback = this.f$0;
        switch (i) {
            case 0:
                baseWifiTrackerCallback.onWifiStateChanged();
                break;
            default:
                baseWifiTrackerCallback.onScanRequested();
                break;
        }
    }
}
