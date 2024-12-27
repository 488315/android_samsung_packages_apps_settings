package com.samsung.android.settings.datetime;

import android.content.Context;
import android.text.format.DateFormat;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class CustomTimePickerUtils {
    public static String getTimeText(Context context, Calendar calendar) {
        String pattern = ((SimpleDateFormat) DateFormat.getTimeFormat(context)).toPattern();
        if (pattern.contains("a")) {
            pattern = pattern.replace("a", ApnSettings.MVNO_NONE).trim();
        }
        return new SimpleDateFormat(pattern).format(new Date(calendar.getTimeInMillis()));
    }
}
