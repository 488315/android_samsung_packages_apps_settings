package com.samsung.android.settings.datetime;

import android.app.timezonedetector.TimeZoneDetector;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.icu.util.TimeZone;
import android.provider.Settings;

import com.android.settings.Utils;

import com.samsung.android.settings.Rune;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class DateTimeSettingsReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            String salesCode = Utils.getSalesCode();
            if ("TMB".equals(salesCode) || "TMK".equals(salesCode)) {
                Settings.Global.putInt(context.getContentResolver(), "auto_time", 1);
                Settings.Global.putInt(context.getContentResolver(), "auto_time_zone", 1);
            }
            if (Rune.FEATURE_REMOVE_TZ_WESTERN_SAHARA_IN_MOROCCO
                    && TimeZone.getDefault().getID().equals("Africa/El_Aaiun")) {
                ((TimeZoneDetector) context.getSystemService(TimeZoneDetector.class))
                        .suggestManualTimeZone(
                                TimeZoneDetector.createManualTimeZoneSuggestion(
                                        "Africa/Casablanca", "Settings: Set time zone"));
            }
        }
    }
}
