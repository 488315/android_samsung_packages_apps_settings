package com.samsung.android.settings.uwb.labs.view.uwbtest;

import android.uwb.RangingReport;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public interface UwbDataReceiver {
    void onReceive(RangingReport rangingReport);

    void onStateReceive(int i);
}
