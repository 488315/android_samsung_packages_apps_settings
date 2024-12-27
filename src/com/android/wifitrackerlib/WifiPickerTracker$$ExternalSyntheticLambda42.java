package com.android.wifitrackerlib;

import java.util.Map;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda42
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiPickerTracker f$0;

    public /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda42(
            WifiPickerTracker wifiPickerTracker, int i) {
        this.$r8$classId = i;
        this.f$0 = wifiPickerTracker;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        WifiPickerTracker wifiPickerTracker = this.f$0;
        switch (i) {
            case 0:
                ((StandardWifiEntry) obj).mSemFlags.networkScoringUiEnabled =
                        wifiPickerTracker.mNetworkScoringUiEnabled;
                break;
            case 1:
                wifiPickerTracker.getClass();
                ((OsuWifiEntry) ((Map.Entry) obj).getValue()).mSemFlags.networkScoringUiEnabled =
                        wifiPickerTracker.mNetworkScoringUiEnabled;
                break;
            case 2:
                wifiPickerTracker.getClass();
                ((PasspointWifiEntry) ((Map.Entry) obj).getValue())
                                .mSemFlags
                                .networkScoringUiEnabled =
                        wifiPickerTracker.mNetworkScoringUiEnabled;
                break;
            default:
                ((StandardWifiEntry) obj).mSemFlags.networkScoringUiEnabled =
                        wifiPickerTracker.mNetworkScoringUiEnabled;
                break;
        }
    }
}
