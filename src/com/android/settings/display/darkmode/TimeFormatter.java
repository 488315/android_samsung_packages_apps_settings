package com.android.settings.display.darkmode;

import android.content.Context;

import java.text.DateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.TimeZone;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class TimeFormatter {
    public final Context mContext;
    public final DateFormat mFormatter;

    public TimeFormatter(Context context) {
        this.mContext = context;
        DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(context);
        this.mFormatter = timeFormat;
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public final String of(LocalTime localTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(this.mFormatter.getTimeZone());
        calendar.set(11, localTime.getHour());
        calendar.set(12, localTime.getMinute());
        calendar.set(13, 0);
        calendar.set(14, 0);
        return this.mFormatter.format(calendar.getTime());
    }
}
