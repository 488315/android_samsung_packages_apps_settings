package com.samsung.android.settings.asbase.reset;

import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioManager;
import android.provider.Settings;
import android.util.Log;

import com.samsung.android.settings.csc.CscParser;
import com.samsung.android.settings.general.BaseResetSettingsData;
import com.samsung.android.settings.general.OnResetSettingsDataListener;
import com.sec.ims.presence.ServiceTuple;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class ResetVolumeSettings {
    public static final OnResetSettingsDataListener RESET_SETTINGS_DATA = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.asbase.reset.ResetVolumeSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseResetSettingsData {
        public static void updateVolume(Context context, int i, String str) {
            try {
                ((AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO))
                        .setStreamVolume(i, Integer.parseInt(str), 0);
            } catch (NumberFormatException e) {
                Log.e("ResetVolumeSettings", "updateVolume explain error", e);
            }
        }

        @Override // com.samsung.android.settings.general.BaseResetSettingsData,
                  // com.samsung.android.settings.general.OnResetSettingsDataListener
        public final void loadCscSettings(Context context, CscParser cscParser) {
            String str = cscParser.get("Settings.Main.Sound.KeyTone.KeyVolume");
            if (str != null) {
                Log.w("ResetVolumeSettings", "Settings.Main.Sound.KeyTone.KeyVolume");
                updateVolume(context, 1, str);
            } else {
                Log.d("ResetVolumeSettings", "System volume is not found.");
            }
            String str2 = cscParser.get("Settings.Main.Sound.MediaVolume");
            if (str2 != null) {
                Log.w("ResetVolumeSettings", "Settings.Main.Sound.MediaVolume");
                updateVolume(context, 3, str2);
            } else {
                Log.d("ResetVolumeSettings", "Media volume is not found.");
            }
            String str3 = cscParser.get("Settings.Main.Sound.MsgToneVolume");
            if (str3 != null) {
                Log.w("ResetVolumeSettings", "Settings.Main.Sound.NotificationVolume");
                updateVolume(context, 5, str3);
            } else {
                Log.d("ResetVolumeSettings", "Notification volume is not found.");
            }
            String str4 = cscParser.get("Settings.Main.Sound.RngVolume");
            if (str4 != null) {
                Log.w("ResetVolumeSettings", "Settings.Main.Sound.RingVolume");
                updateVolume(context, 2, str4);
            } else {
                Log.d("ResetVolumeSettings", "Ring volume is not found.");
            }
            String str5 = cscParser.get("Settings.Main.Sound.ALARMVolume");
            if (str5 == null) {
                Log.d("ResetVolumeSettings", "Alarm volume is not found.");
            } else {
                Log.w("ResetVolumeSettings", "Settings.Main.Sound.AlarmVolume");
                updateVolume(context, 4, str5);
            }
        }

        @Override // com.samsung.android.settings.general.OnResetSettingsDataListener
        public final void resetSettings(Context context) {
            ContentResolver contentResolver = context.getContentResolver();
            Settings.System.putIntForUser(contentResolver, "volume_waiting_tone", 2, -2);
            Settings.System.putIntForUser(contentResolver, "volume_music_headset", 8, -2);
            Settings.System.putIntForUser(contentResolver, "volume_music_headphone", 8, -2);
            Settings.System.putIntForUser(contentResolver, "volume_music_usb_headset", 8, -2);
            Settings.System.putIntForUser(contentResolver, "volume_music_bt_a2dp", 8, -2);
            Settings.System.putIntForUser(contentResolver, "volume_music_remote_submix", 15, -2);
        }
    }
}
