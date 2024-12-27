package com.android.settingslib.fuelgauge;

import android.content.Intent;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BatteryStatus {
    public static int getBatteryLevel(Intent intent) {
        if (intent == null) {
            return -1;
        }
        int intExtra = intent.getIntExtra("level", -1);
        int intExtra2 = intent.getIntExtra("scale", 0);
        if (intExtra2 == 0) {
            return -1;
        }
        return Math.round((intExtra / intExtra2) * 100.0f);
    }
}
