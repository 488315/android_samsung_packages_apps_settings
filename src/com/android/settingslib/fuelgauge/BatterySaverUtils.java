package com.android.settingslib.fuelgauge;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.KeyValueListParser;
import android.util.Slog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BatterySaverUtils {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Parameters {
        public final int endNth;
        public final int startNth;

        public Parameters(Context context) {
            String string =
                    Settings.Global.getString(
                            context.getContentResolver(), "low_power_mode_suggestion_params");
            KeyValueListParser keyValueListParser = new KeyValueListParser(',');
            try {
                keyValueListParser.setString(string);
            } catch (IllegalArgumentException unused) {
                Slog.wtf("BatterySaverUtils", "Bad constants: " + string);
            }
            this.startNth = keyValueListParser.getInt("start_nth", 4);
            this.endNth = keyValueListParser.getInt("end_nth", 8);
        }
    }

    public static String getBatterySaverScheduleKey(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        if (Settings.Global.getInt(contentResolver, "automatic_power_save_mode", 0) == 0) {
            return Settings.Global.getInt(contentResolver, "low_power_trigger_level", 0) <= 0
                    ? "key_battery_saver_no_schedule"
                    : "key_battery_saver_percentage";
        }
        revertScheduleToNoneIfNeeded(context);
        return "key_battery_saver_no_schedule";
    }

    public static boolean maybeShowBatterySaverConfirmation(Context context, Bundle bundle) {
        if (Settings.Secure.getInt(
                                context.getContentResolver(), "low_power_warning_acknowledged", 0)
                        != 0
                && Settings.Secure.getInt(
                                context.getContentResolver(),
                                "extra_low_power_warning_acknowledged",
                                0)
                        != 0) {
            return false;
        }
        sendSystemUiBroadcast(context, "PNW.startSaverConfirmation", bundle);
        return true;
    }

    public static void revertScheduleToNoneIfNeeded(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        if (Settings.Global.getInt(contentResolver, "automatic_power_save_mode", 0) == 1) {
            Settings.Global.putInt(contentResolver, "low_power_trigger_level", 0);
            Settings.Global.putInt(contentResolver, "automatic_power_save_mode", 0);
        }
    }

    public static void sendSystemUiBroadcast(Context context, String str, Bundle bundle) {
        Intent intent = new Intent(str);
        intent.setFlags(268435456);
        intent.setPackage("com.android.systemui");
        intent.putExtras(bundle);
        context.sendBroadcast(intent);
    }

    public static synchronized boolean setPowerSaveMode(
            int i, Context context, boolean z, boolean z2) {
        synchronized (BatterySaverUtils.class) {
            ContentResolver contentResolver = context.getContentResolver();
            Bundle bundle = new Bundle(1);
            bundle.putBoolean("extra_confirm_only", false);
            if (z && z2 && maybeShowBatterySaverConfirmation(context, bundle)) {
                return false;
            }
            if (z && !z2) {
                Settings.Secure.putInt(
                        context.getContentResolver(), "low_power_warning_acknowledged", 1);
                Settings.Secure.putInt(
                        context.getContentResolver(), "extra_low_power_warning_acknowledged", 1);
            }
            if (!((PowerManager) context.getSystemService(PowerManager.class))
                    .setPowerSaveModeEnabled(z)) {
                return false;
            }
            if (z) {
                int i2 =
                        Settings.Secure.getInt(
                                        contentResolver, "low_power_manual_activation_count", 0)
                                + 1;
                Settings.Secure.putInt(contentResolver, "low_power_manual_activation_count", i2);
                Parameters parameters = new Parameters(context);
                if (i2 >= parameters.startNth
                        && i2 <= parameters.endNth
                        && Settings.Global.getInt(contentResolver, "low_power_trigger_level", 0)
                                == 0
                        && Settings.Secure.getInt(
                                        contentResolver,
                                        "suppress_auto_battery_saver_suggestion",
                                        0)
                                == 0) {
                    sendSystemUiBroadcast(context, "PNW.autoSaverSuggestion", bundle);
                }
            }
            Bundle bundle2 = new Bundle(2);
            bundle2.putInt("extra_power_save_mode_manual_enabled_reason", i);
            bundle2.putBoolean("extra_power_save_mode_manual_enabled", z);
            sendSystemUiBroadcast(
                    context,
                    "com.android.settingslib.fuelgauge.ACTION_SAVER_STATE_MANUAL_UPDATE",
                    bundle2);
            return true;
        }
    }
}
