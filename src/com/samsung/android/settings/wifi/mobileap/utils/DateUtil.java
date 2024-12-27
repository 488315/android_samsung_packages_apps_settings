package com.samsung.android.settings.wifi.mobileap.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class DateUtil {
    /* JADX WARN: Type inference failed for: r0v3, types: [java.time.LocalDateTime] */
    public static LocalDateTime getDateTimeFromMillis(long j) {
        return Instant.ofEpochMilli(j).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static int getIndexFromFirstDayOfWeek(long j) {
        int value =
                getDateTimeFromMillis(j).getDayOfWeek().getValue()
                        - WeekFields.of(Locale.getDefault()).getFirstDayOfWeek().getValue();
        if (value < 0) {
            value += 7;
        }
        return value + 1;
    }

    public static long getMillisFromDateTime(LocalDateTime localDateTime) {
        ZoneId systemDefault = ZoneId.systemDefault();
        List<ZoneOffset> validOffsets = systemDefault.getRules().getValidOffsets(localDateTime);
        int size = validOffsets.size();
        return ZonedDateTime.ofLocal(
                        localDateTime, systemDefault, size > 1 ? validOffsets.get(size - 1) : null)
                .toInstant()
                .toEpochMilli();
    }
}
