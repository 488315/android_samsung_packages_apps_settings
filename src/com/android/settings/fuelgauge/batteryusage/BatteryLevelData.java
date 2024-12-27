package com.android.settings.fuelgauge.batteryusage;

import android.util.ArrayMap;

import androidx.compose.runtime.PrioritySet$$ExternalSyntheticOutline0;
import androidx.core.util.Preconditions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryLevelData {
    static Calendar sTestCalendar;
    public final PeriodBatteryLevelData mDailyBatteryLevels;
    public final List mHourlyBatteryLevelsPerDay;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PeriodBatteryLevelData {
        public final boolean mIsStartTimestamp;
        public final List mLevels;
        public final List mTimestamps;

        /* renamed from: -$$Nest$mgetIndexByTimestamps, reason: not valid java name */
        public static int m908$$Nest$mgetIndexByTimestamps(
                PeriodBatteryLevelData periodBatteryLevelData, long j, long j2) {
            for (int i = 0; i < periodBatteryLevelData.mTimestamps.size() - 1; i++) {
                if (((Long) periodBatteryLevelData.mTimestamps.get(i)).longValue() <= j
                        && j2
                                <= ((Long) periodBatteryLevelData.mTimestamps.get(i + 1))
                                        .longValue()) {
                    return i;
                }
            }
            return -2;
        }

        public PeriodBatteryLevelData(List list, Map map, boolean z) {
            this.mTimestamps = list;
            this.mLevels = new ArrayList(list.size());
            this.mIsStartTimestamp = z;
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Long l = (Long) it.next();
                ArrayMap arrayMap = (ArrayMap) map;
                ((ArrayList) this.mLevels)
                        .add(
                                Integer.valueOf(
                                        arrayMap.containsKey(l)
                                                ? ((Integer) arrayMap.get(l)).intValue()
                                                : -1));
            }
        }

        public final String toString() {
            Locale locale = Locale.ENGLISH;
            return "timestamps: "
                    + Objects.toString(this.mTimestamps)
                    + "; levels: "
                    + Objects.toString(this.mLevels);
        }
    }

    public BatteryLevelData(Map map) {
        ArrayMap arrayMap = (ArrayMap) map;
        int size = arrayMap.size();
        Preconditions.checkArgument("batteryLevelMap size:" + size, ((long) size) >= 2);
        ArrayList arrayList = new ArrayList(arrayMap.keySet());
        Collections.sort(arrayList);
        long longValue = ((Long) arrayList.get(0)).longValue();
        Calendar calendar = sTestCalendar;
        boolean z =
                longValue
                        > DatabaseUtils.getTimestampSixDaysAgo(calendar == null ? null : calendar);
        List<Long> dailyTimestamps = getDailyTimestamps(arrayList);
        ArrayList arrayList2 = new ArrayList();
        int i = 0;
        while (i < dailyTimestamps.size() - 1) {
            ArrayList arrayList3 = new ArrayList();
            Long l = dailyTimestamps.get(i);
            long longValue2 = l.longValue();
            i++;
            Long l2 = dailyTimestamps.get(i);
            long longValue3 = l2.longValue();
            arrayList3.add(l);
            Calendar sharpHourCalendar = TimestampUtils.getSharpHourCalendar(longValue2);
            sharpHourCalendar.add(11, sharpHourCalendar.get(11) % 2 != 0 ? 1 : 2);
            for (long timeInMillis = sharpHourCalendar.getTimeInMillis();
                    timeInMillis < longValue3;
                    timeInMillis += 7200000) {
                arrayList3.add(Long.valueOf(timeInMillis));
            }
            arrayList3.add(l2);
            arrayList2.add(arrayList3);
        }
        this.mDailyBatteryLevels = new PeriodBatteryLevelData(dailyTimestamps, map, z);
        this.mHourlyBatteryLevelsPerDay = new ArrayList(arrayList2.size());
        int i2 = 0;
        while (i2 < arrayList2.size()) {
            ((ArrayList) this.mHourlyBatteryLevelsPerDay)
                    .add(new PeriodBatteryLevelData((List) arrayList2.get(i2), map, z && i2 == 0));
            i2++;
        }
    }

    public static List<Long> getDailyTimestamps(List<Long> list) {
        Preconditions.checkArgument("timestampList size:" + list.size(), ((long) list.size()) >= 2);
        ArrayList arrayList = new ArrayList();
        long longValue = list.get(0).longValue();
        Long l = (Long) PrioritySet$$ExternalSyntheticOutline0.m(1, list);
        long longValue2 = l.longValue();
        while (longValue < longValue2) {
            arrayList.add(Long.valueOf(longValue));
            Calendar sharpHourCalendar = TimestampUtils.getSharpHourCalendar(longValue);
            sharpHourCalendar.add(6, 1);
            sharpHourCalendar.set(11, 0);
            longValue = sharpHourCalendar.getTimeInMillis();
        }
        arrayList.add(l);
        return arrayList;
    }

    public final String toString() {
        Locale locale = Locale.ENGLISH;
        return "dailyBatteryLevels: "
                + Objects.toString(this.mDailyBatteryLevels)
                + "; hourlyBatteryLevelsPerDay: "
                + Objects.toString(this.mHourlyBatteryLevelsPerDay);
    }
}
