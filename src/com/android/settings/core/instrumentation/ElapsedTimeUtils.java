package com.android.settings.core.instrumentation;

import android.content.Context;
import android.util.Log;

import java.util.Optional;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ElapsedTimeUtils {
    static final long DEFAULT_SETUP_TIME = -1;
    public static Optional sSuwFinishedTimeStamp = Optional.empty();

    public static long getElapsedTime(long j) {
        if (!sSuwFinishedTimeStamp.isPresent()) {
            Log.w("ElapsedTimeUtils", "getElapsedTime: sSuwFinishedTimeStamp is null");
            return -1L;
        }
        if (((Long) sSuwFinishedTimeStamp.get()).longValue() == -1) {
            return -1L;
        }
        long longValue = j - ((Long) sSuwFinishedTimeStamp.get()).longValue();
        if (longValue > 0) {
            return longValue;
        }
        return -1L;
    }

    public static long getSuwFinishedTimestamp(Context context) {
        return context.getApplicationContext()
                .getSharedPreferences("elapsed_time_info", 0)
                .getLong("suw_finished_time_ms", -1L);
    }
}
