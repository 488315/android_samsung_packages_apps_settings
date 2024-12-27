package com.android.settingslib.utils;

import android.content.Context;
import android.icu.text.DateFormat;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class PowerUtil {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long FIFTEEN_MINUTES_MILLIS;
    public static final long ONE_DAY_MILLIS;
    public static final long ONE_HOUR_MILLIS;

    static {
        TimeUnit timeUnit = TimeUnit.MINUTES;
        timeUnit.toMillis(7L);
        FIFTEEN_MINUTES_MILLIS = timeUnit.toMillis(15L);
        TimeUnit timeUnit2 = TimeUnit.DAYS;
        ONE_DAY_MILLIS = timeUnit2.toMillis(1L);
        timeUnit2.toMillis(2L);
        ONE_HOUR_MILLIS = TimeUnit.HOURS.toMillis(1L);
        timeUnit.toMillis(1L);
    }

    public static String getTargetTimeShortString(Context context, long j, long j2) {
        long j3 = j2 + j;
        long j4 = FIFTEEN_MINUTES_MILLIS;
        if (j >= j4) {
            long abs = Math.abs(j3);
            long abs2 = Math.abs(j4);
            j3 = abs2 * (((abs + abs2) - 1) / abs2);
        }
        return DateFormat.getInstanceForSkeleton(
                        android.text.format.DateFormat.getTimeFormatString(context))
                .format(Date.from(Instant.ofEpochMilli(j3)));
    }

    public static long roundTimeToNearestThreshold(long j, long j2) {
        long abs = Math.abs(j);
        long abs2 = Math.abs(j2);
        long j3 = abs % abs2;
        return j3 < abs2 / 2 ? abs - j3 : (abs - j3) + abs2;
    }
}
