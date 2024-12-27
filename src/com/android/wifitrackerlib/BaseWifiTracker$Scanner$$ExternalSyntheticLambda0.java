package com.android.wifitrackerlib;

import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class BaseWifiTracker$Scanner$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BaseWifiTracker.Scanner f$0;

    public /* synthetic */ BaseWifiTracker$Scanner$$ExternalSyntheticLambda0(
            BaseWifiTracker.Scanner scanner, int i) {
        this.$r8$classId = i;
        this.f$0 = scanner;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        BaseWifiTracker.Scanner scanner = this.f$0;
        switch (i) {
            case 0:
                scanner.stopScanning();
                break;
            case 1:
                scanner.scanLoop();
                break;
            default:
                if (scanner.mIsWifiEnabled && scanner.mIsStartedState) {
                    BaseWifiTracker baseWifiTracker = scanner.this$0;
                    baseWifiTracker.getClass();
                    Log.i(baseWifiTracker.mTag, "Scanning started");
                    scanner.scanLoop();
                    break;
                }
                break;
        }
    }
}
