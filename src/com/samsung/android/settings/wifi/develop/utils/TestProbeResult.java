package com.samsung.android.settings.wifi.develop.utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class TestProbeResult {
    public final int mHttpResponseCode;

    public TestProbeResult(int i) {
        this.mHttpResponseCode = i;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Test result : ");
        int i = this.mHttpResponseCode;
        sb.append(i == 204);
        sb.append(i != 204 && i >= 200 && i <= 399);
        sb.append(i == -1);
        return sb.toString();
    }
}
