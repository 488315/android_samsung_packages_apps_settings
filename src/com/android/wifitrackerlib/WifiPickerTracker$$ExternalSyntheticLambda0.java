package com.android.wifitrackerlib;

import android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus;

import com.samsung.android.wifitrackerlib.SemWifiEntryFilter;

import java.util.List;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda0
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda0(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        boolean z;
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                WifiEntry wifiEntry = (WifiEntry) obj;
                SemWifiEntryFilter semWifiEntryFilter = ((WifiPickerTracker) obj2).mSemFilter;
                semWifiEntryFilter.getClass();
                if (wifiEntry.getConnectedInfo() != null) {
                    if (semWifiEntryFilter.DISPLAY_SSID_STATUS_BAR_INFO
                            && "Swisscom".equals(wifiEntry.getSsid())) {
                        return false;
                    }
                } else if (semWifiEntryFilter.CSC_WIFI_SUPPORT_VZW_EAP_AKA
                        && "VerizonWiFi".equals(wifiEntry.getSsid())) {
                    return false;
                }
                return true;
            case 1:
                return ((HotspotNetworkEntry) obj).mKey.mDeviceId
                        == ((HotspotNetworkConnectionStatus) obj2)
                                .getHotspotNetwork()
                                .getDeviceId();
            default:
                List list = (List) obj2;
                StandardWifiEntry standardWifiEntry = (StandardWifiEntry) obj;
                synchronized (standardWifiEntry) {
                    z = standardWifiEntry.mIsUserShareable;
                }
                if (z) {
                    return true;
                }
                return list.contains(standardWifiEntry);
        }
    }
}
