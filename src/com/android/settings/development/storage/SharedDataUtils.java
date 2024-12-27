package com.android.settings.development.storage;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SharedDataUtils {
    public static final SimpleDateFormat FORMATTER =
            new SimpleDateFormat("MMM dd, yyyy HH:mm:ss z");
    public static final Calendar CALENDAR =
            Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
}
