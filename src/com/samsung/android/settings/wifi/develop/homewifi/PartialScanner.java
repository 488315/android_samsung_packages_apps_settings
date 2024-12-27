package com.samsung.android.settings.wifi.develop.homewifi;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiScanner;

import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class PartialScanner {
    public SignalMeasureFragment$$ExternalSyntheticLambda4 mReceiver;
    public WifiScanner mWifiScanner;
    public final ArrayList mTargetFrequencies = new ArrayList();
    public final AnonymousClass1 mPartialScanListener =
            new WifiScanner
                    .ScanListener() { // from class:
                                      // com.samsung.android.settings.wifi.develop.homewifi.PartialScanner.1
                public final void onResults(WifiScanner.ScanData[] scanDataArr) {
                    ArrayList arrayList = new ArrayList();
                    for (WifiScanner.ScanData scanData : scanDataArr) {
                        arrayList.addAll(Arrays.asList(scanData.getResults()));
                    }
                    SignalMeasureFragment$$ExternalSyntheticLambda4
                            signalMeasureFragment$$ExternalSyntheticLambda4 =
                                    PartialScanner.this.mReceiver;
                    if (signalMeasureFragment$$ExternalSyntheticLambda4 != null) {
                        signalMeasureFragment$$ExternalSyntheticLambda4
                                .f$0
                                .mHandler
                                .obtainMessage(2, arrayList)
                                .sendToTarget();
                    }
                }

                public final void onSuccess() {}

                public final void onFullResult(ScanResult scanResult) {}

                public final void onPeriodChanged(int i) {}

                public final void onFailure(int i, String str) {}
            };
}
