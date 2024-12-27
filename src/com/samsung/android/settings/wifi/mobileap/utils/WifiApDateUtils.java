package com.samsung.android.settings.wifi.mobileap.utils;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.Utils;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class WifiApDateUtils {
    public static final Integer NUMBER_OF_DAYS_IN_WEEK;

    static {
        StringBuilder sb = Utils.sBuilder;
        NUMBER_OF_DAYS_IN_WEEK = 7;
    }

    public static String convertTimeToLocale(Context context, int i) {
        int i2 = i % 60;
        int hours = (int) TimeUnit.MINUTES.toHours(i);
        return i > 0
                ? hours <= 0
                        ? String.format(
                                context.getString(R.string.wifi_ap_num_minute), Integer.valueOf(i2))
                        : String.format(
                                context.getString(R.string.wifi_ap_num_hour_num_minute),
                                Integer.valueOf(hours),
                                Integer.valueOf(i2))
                : String.format(context.getString(R.string.wifi_ap_num_minute), 0);
    }

    public static String convertTimeToLocaleWithRemainingText(Context context, int i) {
        int i2 = i % 60;
        int hours = (int) TimeUnit.MINUTES.toHours(i);
        return i > 0
                ? hours <= 0
                        ? String.format(
                                context.getString(R.string.wifi_ap_minute_remaining),
                                Integer.valueOf(i2))
                        : String.format(
                                context.getString(R.string.wifi_ap_minute_hour_remaining),
                                Integer.valueOf(hours),
                                Integer.valueOf(i2))
                : String.format(context.getString(R.string.wifi_ap_minute_remaining), 0);
    }

    public static int convertTimeToMinutes(long j) {
        return (int) TimeUnit.MILLISECONDS.toMinutes(j);
    }

    public static String getDateRangeString(Context context, Long l) {
        Log.i("WifiApDateUtils", "getting DateRangeString: " + new Date(l.longValue()));
        LocalDateTime minusDays =
                DateUtil.getDateTimeFromMillis(l.longValue())
                        .minusDays((long) (DateUtil.getIndexFromFirstDayOfWeek(l.longValue()) - 1));
        LocalDateTime plusDays = minusDays.plusDays((long) (Period.ofWeeks(1).getDays() - 1));
        return minusDays.getMonthValue() == plusDays.getMonthValue()
                ? DateUtils.formatDateRange(
                        context,
                        DateUtil.getMillisFromDateTime(minusDays),
                        DateUtil.getMillisFromDateTime(plusDays),
                        8)
                : minusDays.getYear() == plusDays.getYear()
                        ? DateUtils.formatDateRange(
                                context,
                                DateUtil.getMillisFromDateTime(minusDays),
                                DateUtil.getMillisFromDateTime(plusDays),
                                8)
                        : DateUtils.formatDateRange(
                                context,
                                DateUtil.getMillisFromDateTime(minusDays),
                                DateUtil.getMillisFromDateTime(plusDays),
                                4);
    }

    /* JADX WARN: Type inference failed for: r2v3, types: [java.time.ZonedDateTime] */
    public static long getFirstDayOfWeek(long j) {
        long epochMilli =
                DateUtil.getDateTimeFromMillis(j)
                        .minusDays(DateUtil.getIndexFromFirstDayOfWeek(j) - 1)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli();
        Log.i("WifiApDateUtils", "getFirstDayOfWeek : " + new Date(epochMilli));
        return epochMilli;
    }

    /* JADX WARN: Type inference failed for: r2v4, types: [java.time.ZonedDateTime] */
    public static long getLastDayOfWeek(long j) {
        long epochMilli =
                DateUtil.getDateTimeFromMillis(j)
                        .minusDays(DateUtil.getIndexFromFirstDayOfWeek(j) - 1)
                        .plusDays(Period.ofWeeks(1).getDays() - 1)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli();
        Log.i("WifiApDateUtils", "getLastDayOfWeek : " + new Date(epochMilli));
        return epochMilli;
    }

    public static int getNumberOfDaysElapsedInCurrentWeek() {
        Date date = new Date(getFirstDayOfWeek(Calendar.getInstance().getTimeInMillis()));
        Date date2 = new Date(System.currentTimeMillis());
        return ((int)
                        TimeUnit.DAYS.convert(
                                Math.abs(date2.getTime() - date.getTime()), TimeUnit.MILLISECONDS))
                + 1;
    }

    public static boolean isEqualsCalendarWeekOfYear(Calendar calendar, Calendar calendar2) {
        Calendar calendar3 = (Calendar) calendar.clone();
        Calendar calendar4 = (Calendar) calendar2.clone();
        calendar3.set(7, calendar3.getFirstDayOfWeek());
        calendar4.set(7, calendar4.getFirstDayOfWeek());
        return calendar3.get(3) == calendar4.get(3) && calendar3.get(1) == calendar4.get(1);
    }

    public static boolean isEqualsDate(long j, long j2) {
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar.setTime(new Date(j));
        calendar2.setTime(new Date(j2));
        return isEqualsDate(calendar, calendar2);
    }

    public static boolean isEqualsDate(Calendar calendar, Calendar calendar2) {
        return calendar != null
                && calendar2 != null
                && calendar.get(6) == calendar2.get(6)
                && calendar.get(1) == calendar2.get(1);
    }
}
