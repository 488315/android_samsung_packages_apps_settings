package com.samsung.android.settings.display;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.LocaleList;
import android.provider.Settings;
import android.util.Log;

import com.android.internal.app.LocalePicker;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecDisplaySettingsReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        ContentResolver contentResolver = context.getContentResolver();
        if (action.equals("android.intent.action.BOOT_COMPLETED")) {
            if (Settings.System.getInt(contentResolver, "screen_off_timeout", 30000) == 121000) {
                Log.i("SecDisplaySettingsReceiver", "Overriding setup wizard set screen timeout");
                Settings.System.putInt(contentResolver, "screen_off_timeout", 30000);
                int i = Settings.System.getInt(contentResolver, "screen_off_timeout_backup", 0);
                if (i > 0) {
                    Log.i("SecDisplaySettingsReceiver", "screen timeout updated by CSC value");
                    Settings.System.putInt(contentResolver, "screen_off_timeout", i);
                }
            }
            Settings.System.putInt(contentResolver, "shown_max_brightness_dialog", 0);
            LocaleList locales = LocalePicker.getLocales();
            int size = locales.size();
            Locale[] localeArr = new Locale[size];
            boolean z = false;
            for (int i2 = 0; i2 < size; i2++) {
                Locale locale = locales.get(i2);
                if (locale != null) {
                    if (locale.toString().equals("bn_BD") || locale.toString().equals("bn_IN")) {
                        locale =
                                new Locale.Builder()
                                        .setLocale(locale)
                                        .setExtension('u', "nu-latn")
                                        .build();
                        z = true;
                    }
                    localeArr[i2] = locale;
                }
            }
            LocaleList localeList = new LocaleList(localeArr);
            if (!z || locales.equals(localeList)) {
                return;
            }
            LocalePicker.updateLocales(localeList);
        }
    }
}
