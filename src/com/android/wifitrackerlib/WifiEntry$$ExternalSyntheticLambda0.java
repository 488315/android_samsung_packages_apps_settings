package com.android.wifitrackerlib;


/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiEntry$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiEntry f$0;

    public /* synthetic */ WifiEntry$$ExternalSyntheticLambda0(WifiEntry wifiEntry, int i) {
        this.$r8$classId = i;
        this.f$0 = wifiEntry;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        WifiEntry wifiEntry = this.f$0;
        switch (i) {
            case 0:
                WifiEntry.WifiEntryCallback wifiEntryCallback = wifiEntry.mListener;
                if (wifiEntryCallback != null) {
                    wifiEntryCallback.onUpdated();
                    break;
                }
                break;
            case 1:
                WifiEntry.ConnectCallback connectCallback = wifiEntry.mConnectCallback;
                if (connectCallback != null) {
                    connectCallback.onConnectResult(0);
                    break;
                }
                break;
            default:
                WifiEntry.DisconnectCallback disconnectCallback = wifiEntry.mDisconnectCallback;
                if (disconnectCallback != null) {
                    disconnectCallback.onDisconnectResult(0);
                    break;
                }
                break;
        }
    }
}
