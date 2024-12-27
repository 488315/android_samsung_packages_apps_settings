package com.samsung.android.settings.autopoweronoff;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.secutil.Log;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.sec.ims.configuration.DATA;

import java.util.Calendar;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AutoPowerOnOffHelper {
    public static PendingIntent mPendingPowerOffIntent;
    public static PendingIntent mPendingPowerOnIntent;

    public static long calculateAlarm(int i, int i2, int i3) {
        StringBuilder m =
                RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                        "calculateAlarm : hour = ", ", minute = ", i, i2, ", repeatDays = ");
        m.append(i3);
        Log.secD("AutoPowerOnOffHelper", m.toString());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int i4 = calendar.get(11);
        int i5 = calendar.get(12);
        if (i < i4 || (i == i4 && i2 <= i5)) {
            calendar.add(6, 1);
        }
        calendar.set(11, i);
        calendar.set(12, i2);
        int i6 = 0;
        calendar.set(13, 0);
        calendar.set(14, 0);
        if (i3 == 0) {
            Log.secD(
                    "AutoPowerOnOffHelper",
                    "calculateAlarm : c.getTimeInMillis(1) = " + calendar.getTimeInMillis());
            return calendar.getTimeInMillis();
        }
        int i7 = calendar.get(7);
        if (i7 >= 1) {
            while (i6 < 7 && ((1 << ((((i7 + i6) - 1) % 7) + 1)) & i3) <= 0) {
                i6++;
            }
            Log.secD("AutoPowerOnOffHelper", "mCount = " + i6);
            calendar.add(6, i6);
        }
        Log.secD(
                "AutoPowerOnOffHelper",
                "calculateAlarm : c.getTimeInMillis(2) = " + calendar.getTimeInMillis());
        return calendar.getTimeInMillis();
    }

    public static String getDataString(long j) {
        StringBuilder sb;
        String m;
        String m2;
        if (j == -1) {
            return "0000000000000";
        }
        Time time = new Time("UTC");
        time.set(j);
        time.normalize(false);
        String m3 =
                Anchor$$ExternalSyntheticOutline0.m(
                        new StringBuilder(), time.year, ApnSettings.MVNO_NONE);
        if (time.month + 1 < 10) {
            sb = new StringBuilder(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
            sb.append(time.month + 1);
        } else {
            sb = new StringBuilder();
            sb.append(time.month + 1);
            sb.append(ApnSettings.MVNO_NONE);
        }
        String sb2 = sb.toString();
        StringBuilder sb3 =
                time.monthDay < 10
                        ? new StringBuilder(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN)
                        : new StringBuilder(ApnSettings.MVNO_NONE);
        sb3.append(time.monthDay);
        String sb4 = sb3.toString();
        if (time.hour < 10) {
            m = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN + time.hour;
        } else {
            m =
                    Anchor$$ExternalSyntheticOutline0.m(
                            new StringBuilder(), time.hour, ApnSettings.MVNO_NONE);
        }
        if (time.minute < 10) {
            m2 = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN + time.minute;
        } else {
            m2 =
                    Anchor$$ExternalSyntheticOutline0.m(
                            new StringBuilder(), time.minute, ApnSettings.MVNO_NONE);
        }
        StringBuilder m4 = SeslRoundedCorner$$ExternalSyntheticOutline0.m("1", m3, sb2, sb4, m);
        m4.append(m2);
        return m4.toString();
    }

    public static void registerAutoPowerOffAlarm(Context context, boolean z) {
        Log.secD("AutoPowerOnOffHelper", "registerAutoPowerOffAlarm");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
        PendingIntent pendingIntent = mPendingPowerOffIntent;
        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
        }
        if (Settings.System.getInt(context.getContentResolver(), "auto_power_off_settings", 0)
                <= 0) {
            Log.d("AutoPowerOnOffHelper", "Don't register auto power off, switch is close");
            return;
        }
        int i = Settings.System.getInt(context.getContentResolver(), "auto_power_off_time", 2300);
        int i2 =
                Settings.System.getInt(
                        context.getContentResolver(), "auto_power_off_repeat_days", 124);
        Intent intent = new Intent("com.samsung.settings.action.AUTO_POWER_OFF_ACTION");
        intent.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
        mPendingPowerOffIntent = PendingIntent.getBroadcast(context, 0, intent, 335544320);
        long calculateAlarm = calculateAlarm(i / 100, i % 100, i2);
        if (i2 != 0) {
            alarmManager.setExact(0, calculateAlarm, mPendingPowerOffIntent);
        } else if (z) {
            alarmManager.setExact(0, calculateAlarm, mPendingPowerOffIntent);
        } else {
            long j =
                    Settings.System.getLong(
                            context.getContentResolver(), "auto_power_off_alert_time", -1L);
            Log.secD(
                    "AutoPowerOnOffHelper",
                    "registerAutoPowerOffAlarm, savedPowerOffOneTime = " + j);
            if (j >= calculateAlarm) {
                alarmManager.setExact(0, calculateAlarm, mPendingPowerOffIntent);
            } else {
                Settings.System.putInt(context.getContentResolver(), "auto_power_off_settings", 0);
                calculateAlarm = -1;
            }
        }
        Settings.System.putLong(
                context.getContentResolver(), "auto_power_off_alert_time", calculateAlarm);
    }

    public static void registerAutoPowerOnAlarm(Context context, boolean z) {
        Log.secD("AutoPowerOnOffHelper", "registerAutoPowerOnAlarm");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
        PendingIntent pendingIntent = mPendingPowerOnIntent;
        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
        }
        if (Settings.System.getInt(context.getContentResolver(), "auto_power_on_settings", 0)
                <= 0) {
            Log.d("AutoPowerOnOffHelper", "Don't register auto power on, switch is close");
            return;
        }
        int i =
                Settings.System.getInt(
                        context.getContentResolver(),
                        "auto_power_on_time",
                        KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED);
        int i2 =
                Settings.System.getInt(
                        context.getContentResolver(), "auto_power_on_repeat_days", 124);
        Intent intent = new Intent("com.samsung.settings.action.AUTO_POWER_ON_ACTION");
        intent.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
        mPendingPowerOnIntent = PendingIntent.getBroadcast(context, 0, intent, 335544320);
        long calculateAlarm = calculateAlarm(i / 100, i % 100, i2);
        if (i2 != 0) {
            alarmManager.setExact(0, calculateAlarm, mPendingPowerOnIntent);
        } else if (z) {
            alarmManager.setExact(0, calculateAlarm, mPendingPowerOnIntent);
        } else {
            long j =
                    Settings.System.getLong(
                            context.getContentResolver(), "auto_power_on_alert_time", -1L);
            Log.secD(
                    "AutoPowerOnOffHelper", "registerAutoPowerOnAlarm, savedPowerOnOneTime = " + j);
            if (j >= calculateAlarm) {
                alarmManager.setExact(0, calculateAlarm, mPendingPowerOnIntent);
            } else {
                Settings.System.putInt(context.getContentResolver(), "auto_power_on_settings", 0);
                calculateAlarm = -1;
            }
        }
        sendData(context, calculateAlarm);
    }

    public static void sendData(Context context, long j) {
        long j2 =
                Settings.System.getLong(
                        context.getContentResolver(), "clock_power_up_alert_time", -1L);
        long currentTimeMillis = System.currentTimeMillis();
        if (j != -1) {
            if (j <= currentTimeMillis) {
                Settings.System.putLong(
                        context.getContentResolver(), "auto_power_on_alert_time", -1L);
                return;
            } else if (j2 <= currentTimeMillis || j2 > j) {
                j2 = j;
            }
        }
        Settings.System.putLong(context.getContentResolver(), "auto_power_on_alert_time", j);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
        String dataString = getDataString(j2);
        Log.secD("AutoPowerOnOffHelper", "sendData, setTime: " + j2 + ", mData: " + dataString);
        if (TextUtils.isEmpty(dataString)) {
            return;
        }
        alarmManager.semSetAutoPowerUp(dataString);
        Log.secD("AutoPowerOnOffHelper", "SetAutoPowerUp success !");
    }
}
