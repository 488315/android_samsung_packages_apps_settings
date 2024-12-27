package com.android.settings.wifi.tether;

import androidx.lifecycle.Observer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiTetherSettings$$ExternalSyntheticLambda3
        implements Observer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiTetherSettings f$0;

    public /* synthetic */ WifiTetherSettings$$ExternalSyntheticLambda3(
            WifiTetherSettings wifiTetherSettings, int i) {
        this.$r8$classId = i;
        this.f$0 = wifiTetherSettings;
    }

    @Override // androidx.lifecycle.Observer
    public final void onChanged(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.mWifiHotspotSecurity.setSummary(((Integer) obj).intValue());
                break;
            case 1:
                this.f$0.mWifiHotspotSpeed.setSummary(((Integer) obj).intValue());
                break;
            case 2:
                this.f$0.onInstantHotspotChanged((String) obj);
                break;
            default:
                this.f$0.onRestartingChanged((Boolean) obj);
                break;
        }
    }
}
