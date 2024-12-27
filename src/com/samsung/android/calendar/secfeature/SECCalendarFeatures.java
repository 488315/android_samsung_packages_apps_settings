package com.samsung.android.calendar.secfeature;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class SECCalendarFeatures {
    public static SECCalendarFeatures sInstance;

    public boolean isLunarCalendarSupported() {
        return this instanceof CHN_SECCalendarFeatures;
    }
}
