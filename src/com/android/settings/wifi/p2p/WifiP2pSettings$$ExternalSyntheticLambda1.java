package com.android.settings.wifi.p2p;


/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiP2pSettings$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ WifiP2pSettings$$ExternalSyntheticLambda1(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                float f = WifiP2pSettings.mCurFontScale;
                ((WifiP2pSettings) obj).startSearch();
                break;
            default:
                WifiP2pSettings.AnonymousClass1 anonymousClass1 =
                        (WifiP2pSettings.AnonymousClass1) obj;
                WifiP2pSettings.this.mWifiP2pManager.stopPeerDiscovery(
                        WifiP2pSettings.sChannel,
                        new WifiP2pSettings.AnonymousClass5(3, anonymousClass1));
                break;
        }
    }
}
