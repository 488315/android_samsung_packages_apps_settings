package com.android.wifitrackerlib;


/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda36
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiPickerTracker.WifiPickerTrackerCallback f$0;

    public /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda36(
            WifiPickerTracker.WifiPickerTrackerCallback wifiPickerTrackerCallback, int i) {
        this.$r8$classId = i;
        this.f$0 = wifiPickerTrackerCallback;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        WifiPickerTracker.WifiPickerTrackerCallback wifiPickerTrackerCallback = this.f$0;
        switch (i) {
            case 0:
                wifiPickerTrackerCallback.onNumSavedNetworksChanged();
                break;
            default:
                wifiPickerTrackerCallback.onNumSavedSubscriptionsChanged();
                break;
        }
    }
}
