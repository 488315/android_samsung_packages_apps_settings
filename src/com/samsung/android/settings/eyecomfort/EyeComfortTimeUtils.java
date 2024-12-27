package com.samsung.android.settings.eyecomfort;

import android.content.Context;
import android.provider.Settings;

import com.sec.ims.configuration.DATA;
import com.sec.ims.volte2.data.VolteConstants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class EyeComfortTimeUtils {
    static {
        String.valueOf(';');
    }

    public static boolean duringScheduleTime(Context context, int i) {
        int i2;
        int i3;
        Calendar calendar = Calendar.getInstance();
        int i4 = calendar.get(12) + (calendar.get(11) * 60);
        if (i == 1) {
            i2 =
                    (int)
                            Settings.System.getLongForUser(
                                    context.getContentResolver(),
                                    "blue_light_filter_on_time",
                                    1140L,
                                    -2);
            i3 =
                    (int)
                            Settings.System.getLongForUser(
                                    context.getContentResolver(),
                                    "blue_light_filter_off_time",
                                    420L,
                                    -2);
        } else {
            i2 =
                    Settings.System.getInt(
                            context.getContentResolver(),
                            "blue_light_filter_automatic_on_time",
                            1140);
            i3 =
                    Settings.System.getInt(
                            context.getContentResolver(),
                            "blue_light_filter_automatic_off_time",
                            VolteConstants.ErrorCode.BAD_EXTENSION);
        }
        return i2 < i3 ? i2 <= i4 && i4 < i3 : i4 >= 0 && (i4 < i3 || i4 >= i2);
    }

    public static int getCustomTimeToInt(Context context, boolean z) {
        return (int)
                (z
                        ? Settings.System.getLong(
                                context.getContentResolver(), "blue_light_filter_on_time", 1140L)
                        : Settings.System.getLong(
                                context.getContentResolver(), "blue_light_filter_off_time", 420L));
    }

    public static boolean is24HourModeEnabled(Context context) {
        String string = Settings.System.getString(context.getContentResolver(), "time_12_24");
        if (string != null) {
            return DATA.DM_FIELD_INDEX.SIP_TJ_TIMER.equals(string);
        }
        Locale locale = context.getResources().getConfiguration().getLocales().get(0);
        if (locale == null) {
            locale = Locale.getDefault();
        }
        DateFormat timeInstance = DateFormat.getTimeInstance(1, locale);
        boolean z = timeInstance instanceof SimpleDateFormat;
        String str = DATA.DM_FIELD_INDEX.SIP_T1_TIMER;
        if (z && ((SimpleDateFormat) timeInstance).toPattern().indexOf(72) >= 0) {
            str = DATA.DM_FIELD_INDEX.SIP_TJ_TIMER;
        }
        return DATA.DM_FIELD_INDEX.SIP_TJ_TIMER.equals(str);
    }
}
