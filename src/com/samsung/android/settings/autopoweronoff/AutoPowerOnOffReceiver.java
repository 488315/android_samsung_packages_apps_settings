package com.samsung.android.settings.autopoweronoff;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import android.telecom.TelecomManager;
import android.text.TextUtils;
import android.util.secutil.Log;

import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.Rune;

import java.util.Calendar;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AutoPowerOnOffReceiver extends BroadcastReceiver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public PowerManager.WakeLock mWakeLockForPartialWakeLock;

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (!Rune.isChinaModel()
                || !SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_AUTO_POWER_ON_OFF")) {
            Log.d("AutoPowerOnOffReceiver", "Do not support the function !");
            return;
        }
        String action = intent.getAction();
        Log.d("AutoPowerOnOffReceiver", "mAction = " + action);
        if ("com.samsung.settings.action.SET_AUTO_POWER_ON".equals(action)) {
            if (intent.getBooleanExtra("power_on_reg", false)) {
                AutoPowerOnOffHelper.registerAutoPowerOnAlarm(
                        context, intent.getBooleanExtra("by_user", false));
                return;
            }
            AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
            PendingIntent pendingIntent = AutoPowerOnOffHelper.mPendingPowerOnIntent;
            if (pendingIntent != null) {
                alarmManager.cancel(pendingIntent);
            } else {
                Intent intent2 = new Intent("com.samsung.settings.action.AUTO_POWER_ON_ACTION");
                intent2.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
                AutoPowerOnOffHelper.mPendingPowerOnIntent =
                        PendingIntent.getBroadcast(context, 0, intent2, 335544320);
            }
            AutoPowerOnOffHelper.mPendingPowerOnIntent = null;
            AutoPowerOnOffHelper.sendData(context, -1L);
            return;
        }
        if ("com.samsung.settings.action.SET_AUTO_POWER_OFF".equals(action)) {
            if (intent.getBooleanExtra("power_off_reg", false)) {
                AutoPowerOnOffHelper.registerAutoPowerOffAlarm(
                        context, intent.getBooleanExtra("by_user", false));
                return;
            }
            AlarmManager alarmManager2 = (AlarmManager) context.getSystemService("alarm");
            PendingIntent pendingIntent2 = AutoPowerOnOffHelper.mPendingPowerOffIntent;
            if (pendingIntent2 != null) {
                alarmManager2.cancel(pendingIntent2);
            } else {
                Intent intent3 = new Intent("com.samsung.settings.action.AUTO_POWER_OFF_ACTION");
                intent3.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
                AutoPowerOnOffHelper.mPendingPowerOffIntent =
                        PendingIntent.getBroadcast(context, 0, intent3, 335544320);
            }
            AutoPowerOnOffHelper.mPendingPowerOffIntent = null;
            Settings.System.putLong(context.getContentResolver(), "auto_power_off_alert_time", -1L);
            return;
        }
        if ("com.samsung.settings.action.AUTO_POWER_ON_ACTION".equals(action)) {
            if (Settings.System.getInt(context.getContentResolver(), "auto_power_on_settings", 0)
                    > 0) {
                AutoPowerOnOffHelper.registerAutoPowerOnAlarm(context, false);
                return;
            } else {
                Log.d("AutoPowerOnOffReceiver", "Don't open auto power on");
                return;
            }
        }
        if ("com.samsung.settings.action.AUTO_POWER_OFF_ACTION".equals(action)) {
            if (Settings.System.getInt(context.getContentResolver(), "auto_power_off_settings", 0)
                    <= 0) {
                Log.d("AutoPowerOnOffReceiver", "Don't open auto power off");
                return;
            }
            Calendar calendar = Calendar.getInstance();
            int i = calendar.get(7);
            long timeInMillis = calendar.getTimeInMillis();
            int i2 =
                    Settings.System.getInt(
                            context.getContentResolver(), "auto_power_off_repeat_days", 124);
            long j =
                    Settings.System.getLong(
                            context.getContentResolver(), "auto_power_off_alert_time", -1L);
            StringBuilder m =
                    SnapshotStateObserver$$ExternalSyntheticOutline0.m(
                            timeInMillis, "currentTime = ", ", savedPowerOffTime = ");
            m.append(j);
            Log.secD("AutoPowerOnOffReceiver", m.toString());
            if (timeInMillis < j || j + 50000 < timeInMillis) {
                AutoPowerOnOffHelper.registerAutoPowerOffAlarm(context, false);
                return;
            }
            if (i2 != 0 && (i2 & (1 << i)) <= 0) {
                AutoPowerOnOffHelper.registerAutoPowerOffAlarm(context, false);
                return;
            }
            if (TelecomManager.from(context).isInCall()) {
                AutoPowerOnOffHelper.registerAutoPowerOffAlarm(context, false);
                Log.d(
                        "AutoPowerOnOffReceiver",
                        "Now calling, do not show power off dialog. Return!");
                return;
            }
            synchronized (this) {
                Log.d("AutoPowerOnOffReceiver", "acquirePartial");
                releasePartial();
                PowerManager.WakeLock newWakeLock =
                        ((PowerManager) context.getSystemService("power"))
                                .newWakeLock(1, "AutoPowerOnOffReceiver");
                this.mWakeLockForPartialWakeLock = newWakeLock;
                newWakeLock.acquire();
            }
            Intent intent4 = new Intent();
            intent4.setClassName(
                    KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                    "com.samsung.android.settings.autopoweronoff.AutoPowerOnOffConfirmDialog");
            intent4.setFlags(335806464);
            context.startActivity(intent4);
            new Handler()
                    .postDelayed(
                            new Runnable() { // from class:
                                             // com.samsung.android.settings.autopoweronoff.AutoPowerOnOffReceiver.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Log.secD("AutoPowerOnOffReceiver", "mHandler.postDelayed");
                                    AutoPowerOnOffReceiver autoPowerOnOffReceiver =
                                            AutoPowerOnOffReceiver.this;
                                    int i3 = AutoPowerOnOffReceiver.$r8$clinit;
                                    autoPowerOnOffReceiver.releasePartial();
                                }
                            },
                            3000L);
            return;
        }
        if (!"com.samsung.sec.android.clockpackage.AUTO_POWER_UP".equals(action)) {
            if ("android.intent.action.BOOT_COMPLETED".equals(action)
                    || "android.intent.action.TIME_SET".equals(action)) {
                AutoPowerOnOffHelper.registerAutoPowerOffAlarm(context, false);
                AutoPowerOnOffHelper.registerAutoPowerOnAlarm(context, false);
                return;
            }
            return;
        }
        long longExtra = intent.getLongExtra("Alarm_Power_Up_Time", -1L) - 180000;
        Log.d("AutoPowerOnOffReceiver", "mAlarmPowerUpTime = " + longExtra);
        Settings.System.putLong(
                context.getContentResolver(), "clock_power_up_alert_time", longExtra);
        if (Settings.System.getInt(context.getContentResolver(), "auto_power_on_settings", 0)
                <= 0) {
            Log.d("AutoPowerOnOffReceiver", "Don't open auto power on");
            return;
        }
        Log.secD("AutoPowerOnOffHelper", "registerAutoPowerOnClock");
        if (AutoPowerOnOffHelper.mPendingPowerOnIntent != null) {
            ((AlarmManager) context.getSystemService("alarm"))
                    .cancel(AutoPowerOnOffHelper.mPendingPowerOnIntent);
        }
        int i3 =
                Settings.System.getInt(
                        context.getContentResolver(),
                        "auto_power_on_time",
                        KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED);
        long calculateAlarm =
                AutoPowerOnOffHelper.calculateAlarm(
                        i3 / 100,
                        i3 % 100,
                        Settings.System.getInt(
                                context.getContentResolver(), "auto_power_on_repeat_days", 124));
        long j2 =
                Settings.System.getLong(
                        context.getContentResolver(), "clock_power_up_alert_time", -1L);
        long currentTimeMillis = System.currentTimeMillis();
        if (calculateAlarm != -1) {
            if (calculateAlarm <= currentTimeMillis) {
                Settings.System.putLong(
                        context.getContentResolver(), "auto_power_on_alert_time", -1L);
                return;
            } else if (j2 <= currentTimeMillis || j2 > calculateAlarm) {
                j2 = calculateAlarm;
            }
        }
        Settings.System.putLong(
                context.getContentResolver(), "auto_power_on_alert_time", calculateAlarm);
        AlarmManager alarmManager3 = (AlarmManager) context.getSystemService("alarm");
        Intent intent5 = new Intent("com.samsung.settings.action.AUTO_POWER_ON_ACTION");
        intent5.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
        PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, intent5, 335544320);
        AutoPowerOnOffHelper.mPendingPowerOnIntent = broadcast;
        alarmManager3.setExact(0, j2, broadcast);
        String dataString = AutoPowerOnOffHelper.getDataString(j2);
        Log.secD("AutoPowerOnOffHelper", "sendData, setTime: " + j2 + ", mData: " + dataString);
        if (TextUtils.isEmpty(dataString)) {
            return;
        }
        alarmManager3.semSetAutoPowerUp(dataString);
        Log.secD("AutoPowerOnOffHelper", "SetAutoPowerUp success !");
    }

    public final synchronized void releasePartial() {
        Log.d("AutoPowerOnOffReceiver", "releasePartial");
        PowerManager.WakeLock wakeLock = this.mWakeLockForPartialWakeLock;
        if (wakeLock != null) {
            wakeLock.release();
            this.mWakeLockForPartialWakeLock = null;
        }
    }
}
