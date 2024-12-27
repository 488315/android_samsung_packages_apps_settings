package com.samsung.android.settings.asbase.reset;

import android.R;
import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.android.settings.Utils;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.asbase.rune.VibRune;
import com.samsung.android.settings.asbase.utils.VibUtils;
import com.samsung.android.settings.csc.CscParser;
import com.samsung.android.settings.general.BaseResetSettingsData;
import com.samsung.android.settings.general.OnResetSettingsDataListener;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class ResetSoundSystemFeedbackSettings {
    public static final OnResetSettingsDataListener RESET_SETTINGS_DATA = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.asbase.reset.ResetSoundSystemFeedbackSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseResetSettingsData {
        @Override // com.samsung.android.settings.general.BaseResetSettingsData,
                  // com.samsung.android.settings.general.OnResetSettingsDataListener
        public final void loadCscSettings(Context context, CscParser cscParser) {
            ContentResolver contentResolver = context.getContentResolver();
            String str = cscParser.get("Settings.Main.Sound.TouchTone");
            if (TextUtils.isEmpty(str)) {
                Log.d("ResetSoundSystemFeedbackSettings", "Touch Sounds is not found");
                return;
            }
            Log.w("ResetSoundSystemFeedbackSettings", "Settings.Main.Sound.TouchTone");
            if (TextUtils.equals("on", str)) {
                Settings.System.putInt(contentResolver, "sound_effects_enabled", 1);
            } else if (TextUtils.equals("off", str)) {
                Settings.System.putInt(contentResolver, "sound_effects_enabled", 0);
            }
        }

        @Override // com.samsung.android.settings.general.OnResetSettingsDataListener
        public final void resetSettings(Context context) {
            ContentResolver contentResolver = context.getContentResolver();
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            String salesCode = Utils.getSalesCode();
            Settings.System.putIntForUser(contentResolver, "sound_effects_enabled", 0, -2);
            Settings.System.putIntForUser(contentResolver, "lockscreen_sounds_enabled", 1, -2);
            Settings.Secure.putIntForUser(contentResolver, "charging_sounds_enabled", 1, -2);
            Settings.System.putIntForUser(contentResolver, "dtmf_tone", 1, -2);
            if (Rune.isJapanModel()
                    || !SemFloatingFeature.getInstance()
                            .getBoolean("SEC_FLOATING_FEATURE_SIP_ENABLE_DEF_KEY_SOUND")) {
                Settings.System.putIntForUser(contentResolver, "sip_key_feedback_sound", 0, -2);
            } else {
                Settings.System.putIntForUser(contentResolver, "sip_key_feedback_sound", 1, -2);
            }
            Settings.System.putIntForUser(contentResolver, "folder_sounds_enabled", 1, -2);
            Settings.System.putIntForUser(contentResolver, "gps_noti_sound_enabled", 0, -2);
            if (SemFloatingFeature.getInstance()
                    .getBoolean("SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_DOCK")) {
                Settings.Global.putInt(contentResolver, "dock_sounds_enabled", 1);
            } else {
                Settings.Global.putInt(contentResolver, "dock_sounds_enabled", 0);
            }
            Settings.Global.putInt(contentResolver, "dock_audio_media_enabled", 1);
            if (TextUtils.equals(salesCode, "USC")) {
                Settings.Global.putInt(contentResolver, "emergency_tone", 1);
            } else if (TextUtils.equals(salesCode, "CHA")) {
                Settings.Global.putInt(contentResolver, "emergency_tone", 2);
            } else {
                Settings.Global.putInt(contentResolver, "emergency_tone", 0);
            }
            Settings.System.putIntForUser(
                    contentResolver, "wifispeaker_chromecast_mode_enabled", 0, -2);
            ContentResolver contentResolver2 = context.getContentResolver();
            if (VibUtils.isSupportDcHaptic(context)) {
                Settings.System.putIntForUser(contentResolver2, "haptic_feedback_enabled", 1, -2);
                Settings.System.putIntForUser(contentResolver2, "dialing_keypad_vibrate", 1, -2);
                Settings.System.putIntForUser(
                        contentResolver2, "navigation_gestures_vibrate", 1, -2);
                Settings.Secure.putIntForUser(
                        contentResolver2, "charging_vibration_enabled", 1, -2);
            } else if (VibUtils.hasVibrator(context) && VibUtils.isEnableIntensity(context)) {
                Settings.System.putIntForUser(contentResolver2, "haptic_feedback_enabled", 1, -2);
                Settings.System.putIntForUser(contentResolver2, "dialing_keypad_vibrate", 1, -2);
                Settings.System.putIntForUser(contentResolver2, "camera_feedback_vibrate", 1, -2);
                Settings.System.putIntForUser(
                        contentResolver2, "navigation_gestures_vibrate", 1, -2);
                Settings.Secure.putIntForUser(
                        contentResolver2, "charging_vibration_enabled", 1, -2);
            } else {
                Settings.System.putIntForUser(contentResolver2, "haptic_feedback_enabled", 0, -2);
                Settings.System.putIntForUser(contentResolver2, "dialing_keypad_vibrate", 0, -2);
                Settings.System.putIntForUser(contentResolver2, "camera_feedback_vibrate", 0, -2);
                Settings.System.putIntForUser(
                        contentResolver2, "navigation_gestures_vibrate", 0, -2);
                Settings.Secure.putIntForUser(
                        contentResolver2, "charging_vibration_enabled", 0, -2);
            }
            if (VibUtils.isSupportDcHaptic(context) || Rune.isJapanModel()) {
                Settings.System.putIntForUser(
                        contentResolver2, "sip_key_feedback_vibration", 0, -2);
            } else {
                Settings.System.putIntForUser(
                        contentResolver2, "sip_key_feedback_vibration", 1, -2);
            }
            if (VibRune.SUPPORT_SEC_VIBRATION_PICKER) {
                Settings.System.putInt(contentResolver2, "ringtone_vibration_sep_index", 50035);
                Settings.System.putString(
                        contentResolver2,
                        "default_notification_vibration_pattern",
                        context.getResources().getString(R.string.global_action_bug_report));
                Settings.System.putInt(contentResolver2, "notification_vibration_sep_index", 50034);
            }
            Settings.System.putInt(contentResolver2, "ringtone_vibration_sep_index_2", 50035);
            Settings.System.putInt(contentResolver2, "sync_vibration_with_ringtone", 0);
            Settings.System.putInt(contentResolver2, "sync_vibration_with_ringtone_2", 0);
            Settings.System.putInt(contentResolver2, "sync_vibration_with_notification", 0);
            Settings.System.putInt(contentResolver2, "vibration_sound_enabled", 0);
        }
    }
}
