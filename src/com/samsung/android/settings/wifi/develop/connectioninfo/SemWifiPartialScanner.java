package com.samsung.android.settings.wifi.develop.connectioninfo;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiScanner;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SemWifiPartialScanner implements WifiScanner.ScanListener {
    public ConnectionInfoRepo mListener;
    public WifiScanner mWifiScanner;

    public final void onResults(WifiScanner.ScanData[] scanDataArr) {
        ConnectionInfoRepo connectionInfoRepo = this.mListener;
        if (scanDataArr == null) {
            connectionInfoRepo.getClass();
            Log.d("ConnectionInfoRepo", "Failed to scan");
        } else {
            ConnectionInfoRepo.ConnectionInfoRepoHandler connectionInfoRepoHandler =
                    connectionInfoRepo.mHandler;
            connectionInfoRepoHandler.sendMessage(
                    connectionInfoRepoHandler.obtainMessage(3, scanDataArr));
        }
    }

    public final void onSuccess() {}

    public final void onFullResult(ScanResult scanResult) {}

    public final void onPeriodChanged(int i) {}

    public final void onFailure(int i, String str) {}
}
