package com.samsung.android.settings.asbase.reset;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

import com.samsung.android.settings.asbase.rune.AudioRune;
import com.samsung.android.settings.general.BaseResetSettingsData;
import com.samsung.android.settings.general.OnResetSettingsDataListener;

import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class ResetDefaultRingtonePreference {
    public static final OnResetSettingsDataListener RESET_SETTINGS_DATA;
    public static final ArrayList SYSTEM_SOUND_DB_LIST;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.asbase.reset.ResetDefaultRingtonePreference$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseResetSettingsData {
        @Override // com.samsung.android.settings.general.OnResetSettingsDataListener
        public final void resetSettings(Context context) {
            final ContentResolver contentResolver = context.getContentResolver();
            if (Settings.System.getInt(contentResolver, "special_edition_system_sound_set", 0)
                    != 1) {
                Settings.System.putString(contentResolver, "system_sound", "Galaxy");
                Settings.Global.putString(contentResolver, "theme_touch_sound", null);
                final int i = 0;
                ResetDefaultRingtonePreference.SYSTEM_SOUND_DB_LIST.forEach(
                        new Consumer() { // from class:
                                         // com.samsung.android.settings.asbase.reset.ResetDefaultRingtonePreference$1$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                int i2 = i;
                                ContentResolver contentResolver2 = contentResolver;
                                String str = (String) obj;
                                switch (i2) {
                                    case 0:
                                        Settings.System.putString(contentResolver2, str, null);
                                        break;
                                    default:
                                        String string =
                                                Settings.System.getString(contentResolver2, str);
                                        ArrayList arrayList =
                                                ResetDefaultRingtonePreference.SYSTEM_SOUND_DB_LIST;
                                        if (string == null
                                                || !string.startsWith("/data/overlays")) {
                                            Settings.System.putString(contentResolver2, str, null);
                                            break;
                                        }
                                        break;
                                }
                            }
                        });
            } else {
                if (!TextUtils.equals(
                        "Open_theme", Settings.System.getString(contentResolver, "system_sound"))) {
                    Settings.System.putString(contentResolver, "system_sound", "Galaxy");
                }
                String string = Settings.Global.getString(contentResolver, "theme_touch_sound");
                if (string == null || !string.startsWith("/data/overlays")) {
                    Settings.Global.putString(contentResolver, "theme_touch_sound", null);
                }
                final int i2 = 1;
                ResetDefaultRingtonePreference.SYSTEM_SOUND_DB_LIST.forEach(
                        new Consumer() { // from class:
                                         // com.samsung.android.settings.asbase.reset.ResetDefaultRingtonePreference$1$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                int i22 = i2;
                                ContentResolver contentResolver2 = contentResolver;
                                String str = (String) obj;
                                switch (i22) {
                                    case 0:
                                        Settings.System.putString(contentResolver2, str, null);
                                        break;
                                    default:
                                        String string2 =
                                                Settings.System.getString(contentResolver2, str);
                                        ArrayList arrayList =
                                                ResetDefaultRingtonePreference.SYSTEM_SOUND_DB_LIST;
                                        if (string2 == null
                                                || !string2.startsWith("/data/overlays")) {
                                            Settings.System.putString(contentResolver2, str, null);
                                            break;
                                        }
                                        break;
                                }
                            }
                        });
            }
            Settings.System.putString(contentResolver, "prev_system_sound", "Galaxy");
            Settings.Global.putString(contentResolver, "backup_theme_touch_sound", null);
            Settings.System.putString(contentResolver, "backup_key_sound_path", null);
            Settings.System.putString(contentResolver, "backup_backspace_key_sound_path", null);
            Settings.System.putString(contentResolver, "backup_modifier_key_sound_path", null);
            if (AudioRune.SUPPORT_SOUND_THEME_ONEUI31_EXTENSION) {
                Settings.System.putString(contentResolver, "backup_dialer_sound_theme_path", null);
            }
        }
    }

    static {
        ArrayList arrayList = new ArrayList();
        SYSTEM_SOUND_DB_LIST = arrayList;
        arrayList.add("default_key_sound_path");
        arrayList.add("backspace_key_sound_path");
        arrayList.add("modifier_key_sound_path");
        if (AudioRune.SUPPORT_SOUND_THEME_ONEUI31_EXTENSION) {
            arrayList.add("dialer_sound_theme_path");
        }
        RESET_SETTINGS_DATA = new AnonymousClass1();
    }
}
