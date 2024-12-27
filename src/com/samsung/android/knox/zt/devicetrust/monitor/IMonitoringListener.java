package com.samsung.android.knox.zt.devicetrust.monitor;

import android.os.Bundle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public interface IMonitoringListener {
    int checkUrlReputation(String str);

    void onEvent(int i, Bundle bundle);

    void onEventGeneralized(int i, String str);

    void onEventSimplified(int i, String str);

    int onSignal(String str);

    void onUnauthorizedAccessDetected(
            int i, int i2, int i3, long j, int i4, int i5, String str, String str2);
}
