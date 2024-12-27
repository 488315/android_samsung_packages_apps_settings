package com.android.wifitrackerlib;


/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class MergedCarrierEntry$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MergedCarrierEntry f$0;

    public /* synthetic */ MergedCarrierEntry$$ExternalSyntheticLambda0(
            MergedCarrierEntry mergedCarrierEntry, int i) {
        this.$r8$classId = i;
        this.f$0 = mergedCarrierEntry;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        MergedCarrierEntry mergedCarrierEntry = this.f$0;
        switch (i) {
            case 0:
                WifiEntry.DisconnectCallback disconnectCallback =
                        mergedCarrierEntry.mDisconnectCallback;
                if (disconnectCallback != null) {
                    disconnectCallback.onDisconnectResult(0);
                    break;
                }
                break;
            default:
                WifiEntry.ConnectCallback connectCallback = mergedCarrierEntry.mConnectCallback;
                if (connectCallback != null) {
                    connectCallback.onConnectResult(0);
                    break;
                }
                break;
        }
    }
}
