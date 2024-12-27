package com.samsung.android.settings.wifi.mobileap;

import android.util.Log;

import com.samsung.android.wifi.SemWifiApBleScanResult;
import com.samsung.android.wifi.SemWifiManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiApWarning$33$1 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object this$1;

    public WifiApWarning$33$1(WifiApWarning.AnonymousClass1 anonymousClass1) {
        this.this$1 = anonymousClass1;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                WifiApWarning wifiApWarning = ((WifiApWarning.AnonymousClass1) this.this$1).this$0;
                boolean z = WifiApWarning.DBG;
                wifiApWarning.startProvisioningIfNecessary();
                break;
            case 1:
                WifiApWarning wifiApWarning2 = ((WifiApWarning.AnonymousClass1) this.this$1).this$0;
                boolean z2 = WifiApWarning.DBG;
                wifiApWarning2.startProvisioningIfNecessary();
                break;
            case 2:
                WifiApWarning wifiApWarning3 = ((WifiApWarning.AnonymousClass1) this.this$1).this$0;
                boolean z3 = WifiApWarning.DBG;
                wifiApWarning3.startProvisioningIfNecessary();
                break;
            default:
                WifiApWarning.AnonymousClass47 anonymousClass47 =
                        (WifiApWarning.AnonymousClass47) this.this$1;
                WifiApWarning wifiApWarning4 = WifiApWarning.this;
                wifiApWarning4.bleAp =
                        WifiApWarning.m1360$$Nest$mcheckIfBleScanResultValid(
                                wifiApWarning4, anonymousClass47.val$mac_addr);
                if (WifiApWarning.this.bleAp == null) {
                    Log.i("WifiApWarning", "Autohotspot is not found");
                    WifiApWarning.this.finish();
                    break;
                } else {
                    Log.i("WifiApWarning", "Autohotspot connect 1");
                    WifiApWarning wifiApWarning5 = WifiApWarning.this;
                    SemWifiManager semWifiManager = wifiApWarning5.mSemWifiManager;
                    SemWifiApBleScanResult semWifiApBleScanResult = wifiApWarning5.bleAp;
                    semWifiManager.connectToSmartMHS(
                            semWifiApBleScanResult.mDevice,
                            semWifiApBleScanResult.mMHSdeviceType,
                            semWifiApBleScanResult.mhidden,
                            semWifiApBleScanResult.mSecurity,
                            semWifiApBleScanResult.mWifiMac,
                            semWifiApBleScanResult.mUserName,
                            semWifiApBleScanResult.version,
                            semWifiApBleScanResult.isWifiProfileShareEnabled);
                    WifiApWarning.this.finish();
                    break;
                }
        }
    }

    public WifiApWarning$33$1(WifiApWarning.AnonymousClass1 anonymousClass1, byte b) {
        this.this$1 = anonymousClass1;
    }

    public WifiApWarning$33$1(WifiApWarning.AnonymousClass1 anonymousClass1, char c) {
        this.this$1 = anonymousClass1;
    }

    public WifiApWarning$33$1(WifiApWarning.AnonymousClass47 anonymousClass47) {
        this.this$1 = anonymousClass47;
    }
}
