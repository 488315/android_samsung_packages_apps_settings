package com.samsung.android.settings.asbase.reset;

import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.android.settings.Utils;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.csc.CscParser;
import com.samsung.android.settings.general.BaseResetSettingsData;
import com.samsung.android.settings.general.OnResetSettingsDataListener;
import com.sec.ims.presence.ServiceTuple;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class ResetSoundSettings {
    public static final OnResetSettingsDataListener RESET_SETTINGS_DATA = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.asbase.reset.ResetSoundSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseResetSettingsData {
        @Override // com.samsung.android.settings.general.BaseResetSettingsData,
                  // com.samsung.android.settings.general.OnResetSettingsDataListener
        public final void loadCscSettings(Context context, CscParser cscParser) {
            String str = cscParser.get("Settings.Main.Sound.RngToneAlertType");
            if (str == null) {
                Log.d("ResetSoundSettings", "RngToneAlertType is not found");
                return;
            }
            Log.w("ResetSoundSettings", "Settings.Main.Sound.RngToneAlertType");
            ContentResolver contentResolver = context.getContentResolver();
            AudioManager audioManager =
                    (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
            if (TextUtils.equals(str, "melody")) {
                Log.d("ResetSoundSettings", "updateSilentMode: melody");
                audioManager.setRingerMode(2);
            } else if (TextUtils.equals(str, "mute")) {
                Log.d("ResetSoundSettings", "updateSilentMode: mute");
                audioManager.setRingerMode(0);
            } else if (TextUtils.equals(str, "vib")) {
                Log.d("ResetSoundSettings", "updateSilentMode: vib");
                audioManager.setRingerMode(1);
            } else {
                Log.d("ResetSoundSettings", "updateSilentMode: vibmelody");
                Settings.System.putInt(contentResolver, "vibrate_when_ringing", 1);
            }
        }

        @Override // com.samsung.android.settings.general.OnResetSettingsDataListener
        public final void resetSettings(Context context) {
            ContentResolver contentResolver = context.getContentResolver();
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            String salesCode = Utils.getSalesCode();
            Settings.Global.putInt(contentResolver, "mode_ringer_time", 60);
            Settings.Global.putInt(contentResolver, "mode_ringer_time_on", 0);
            Settings.System.putIntForUser(
                    contentResolver,
                    "vibrate_when_ringing",
                    (((Rune.isChinaModel() || "USC".equals(salesCode))
                                                    ? Settings.System.getIntForUser(
                                                            contentResolver, "vibrate_on", 1, -2)
                                                    : Settings.System.getIntForUser(
                                                            contentResolver, "vibrate_on", 0, -2))
                                            & 3)
                                    == 1
                            ? 1
                            : 0,
                    -2);
        }
    }
}
