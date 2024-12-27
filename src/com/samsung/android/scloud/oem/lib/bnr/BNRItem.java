package com.samsung.android.scloud.oem.lib.bnr;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BNRItem {
    public final long timestamp;

    public BNRItem(long j, String str, String str2) {
        int length = 13 - (j + ApnSettings.MVNO_NONE).length();
        if (length > 0) {
            j = (long) (Math.pow(10.0d, length) * j);
        }
        this.timestamp = j;
    }
}
