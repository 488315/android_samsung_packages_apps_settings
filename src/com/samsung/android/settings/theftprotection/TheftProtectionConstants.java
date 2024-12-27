package com.samsung.android.settings.theftprotection;

import android.net.Uri;

import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class TheftProtectionConstants {
    public static final long MINUTE_MILLIS;
    public static final Uri SAMSUNG_ACCOUNT_PLACE_URI =
            Uri.parse("content://com.samsung.android.unifiedprofile/location");
    public static final long SECOND_MILLIS;
    public static final long SECURITY_DELAY_DURATION_MILLIS;
    public static final long SECURITY_GRACE_DURATION_MILLIS;

    static {
        TimeUnit timeUnit = TimeUnit.MINUTES;
        SECURITY_DELAY_DURATION_MILLIS = timeUnit.toMillis(60L);
        SECURITY_GRACE_DURATION_MILLIS = timeUnit.toMillis(60L);
        SECOND_MILLIS = TimeUnit.SECONDS.toMillis(1L);
        MINUTE_MILLIS = timeUnit.toMillis(1L);
    }
}
