package com.samsung.android.settings.uwb.labs.common;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.configuration.DATA;

import java.util.Calendar;
import java.util.Date;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class UwbLabsUtils {
    public static boolean mDarkMode = false;

    public static int convertWeekDay(int i) {
        switch (i) {
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            case 5:
                return 4;
            case 6:
                return 5;
            case 7:
                return 6;
            default:
                return i;
        }
    }

    public static long getHours(long j) {
        return j == 0 ? j : ((j / 60) / 60) / 1000;
    }

    public static long getMinutes(long j) {
        return j == 0 ? j : ((j / 60) / 1000) % 60;
    }

    public static String getReportFormat(long j) {
        Date date = new Date(j);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int i = calendar.get(11);
        int i2 = calendar.get(12);
        StringBuilder sb = new StringBuilder();
        sb.append(Month.values()[calendar.get(2)].getName());
        sb.append(" ");
        sb.append(calendar.get(5));
        sb.append(toDateFormat(calendar.get(5)));
        sb.append(", ");
        String str = ApnSettings.MVNO_NONE;
        sb.append(i < 10 ? DATA.DM_FIELD_INDEX.PCSCF_DOMAIN : ApnSettings.MVNO_NONE);
        sb.append(i);
        sb.append(":");
        if (i2 < 10) {
            str = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
        }
        sb.append(str);
        sb.append(i2);
        return sb.toString();
    }

    public static String toDateFormat(int i) {
        return i != 1 ? i != 2 ? i != 3 ? "th" : "rd" : "nd" : "st";
    }
}
