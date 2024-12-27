package com.android.settings.wifi;

import android.util.Log;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiEnabler$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiEnabler f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ WifiEnabler$$ExternalSyntheticLambda0(
            WifiEnabler wifiEnabler, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = wifiEnabler;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = 1;
        switch (this.$r8$classId) {
            case 0:
                WifiEnabler wifiEnabler = this.f$0;
                boolean z = this.f$1;
                if (!wifiEnabler.mWifiManager.setWifiEnabled(z)) {
                    boolean z2 = !z;
                    wifiEnabler.setSwitchBarChecked$2(z2);
                    wifiEnabler.setSwitchBarEnabled(true, true);
                    if (wifiEnabler.mListener != null) {
                        wifiEnabler.mMainHandler.post(
                                new WifiEnabler$$ExternalSyntheticLambda0(wifiEnabler, z2, i));
                        break;
                    }
                }
                break;
            default:
                WifiEnabler wifiEnabler2 = this.f$0;
                boolean z3 = this.f$1;
                WifiSettings.AnonymousClass6 anonymousClass6 = wifiEnabler2.mListener;
                anonymousClass6.getClass();
                Log.d("WifiSettings", "enabler changed - " + z3);
                WifiSettings wifiSettings = WifiSettings.this;
                if (!z3) {
                    boolean z4 = WifiSettings.mWifiSettingsForeground;
                    wifiSettings.setEmptyView$1(R.string.wifi_stopping);
                    wifiSettings.mIgnoreUpdateOnDisabling = true;
                    break;
                } else {
                    boolean z5 = WifiSettings.mWifiSettingsForeground;
                    wifiSettings.setEmptyView$1(R.string.wifi_starting);
                    wifiSettings.mIgnoreUpdateOnDisabling = false;
                    break;
                }
        }
    }
}
