package com.samsung.android.settings.wifi.mobileap.autohotspot;


/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */
class WifiApAutoHotspotSwitchEnabler$AutoHotspotNetworkCallBack$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiApAutoHotspotSwitchEnabler.AutoHotspotNetworkCallBack f$0;

    public /* synthetic */
    WifiApAutoHotspotSwitchEnabler$AutoHotspotNetworkCallBack$$ExternalSyntheticLambda0(
            WifiApAutoHotspotSwitchEnabler.AutoHotspotNetworkCallBack autoHotspotNetworkCallBack,
            int i) {
        this.$r8$classId = i;
        this.f$0 = autoHotspotNetworkCallBack;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        WifiApAutoHotspotSwitchEnabler.AutoHotspotNetworkCallBack autoHotspotNetworkCallBack =
                this.f$0;
        switch (i) {
            case 0:
                autoHotspotNetworkCallBack.this$0.updateSwitchState(false);
                break;
            case 1:
                autoHotspotNetworkCallBack.this$0.updateSwitchState(false);
                break;
            default:
                autoHotspotNetworkCallBack.this$0.updateSwitchState(false);
                break;
        }
    }
}
