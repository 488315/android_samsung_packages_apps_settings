package com.samsung.android.settings.gts;

import android.os.Debug;

import com.android.settings.R;

import com.samsung.android.settings.asbase.audio.SecSoundKeyboardSoundController;
import com.samsung.android.settings.asbase.vibration.SecSoundDeviceVibrationController;
import com.samsung.android.settings.asbase.vibration.SecSoundKeyboardVibrationController;
import com.samsung.android.settings.usefulfeature.videoenhancer.VideoEnhancerPreferenceController;
import com.samsung.android.util.SemLog;

import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class GtsResources {
    public static final Map mMinVersionMap;
    public static final Map mResourceMap;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum GtsGroup {
        /* JADX INFO: Fake field, exist only in values array */
        GROUP_KEY_CONNECTIONS(
                "com.samsung.android.settings.gts.category.CONNECTIONS",
                R.string.tab_category_connections),
        /* JADX INFO: Fake field, exist only in values array */
        GROUP_KEY_DATA_USAGE(
                "com.samsung.android.settings.gts.category.DATA_USAGE",
                R.string.data_usage_summary_title),
        GROUP_KEY_DISPLAY(
                "com.samsung.android.settings.gts.category.DISPLAY",
                R.string.display_settings_title),
        /* JADX INFO: Fake field, exist only in values array */
        GROUP_KEY_LOCKSCREEN(
                "com.samsung.android.settings.gts.category.LOCKSCREEN", R.string.lockscreen),
        GROUP_KEY_NOTIFICATIONS(
                "com.samsung.android.settings.gts.category.NOTIFICATIONS",
                R.string.configure_notification_settings),
        GROUP_KEY_SOUNDS(
                "com.samsung.android.settings.gts.category.SOUNDS", R.string.sec_sound_mode_sound),
        GROUP_KEY_SOUNDS_SYSTEM_SOUNDS(
                "com.samsung.android.settings.gts.category.SOUNDS_SYSTEM_SOUNDS",
                R.string.sec_system_sound_category),
        GROUP_KEY_SOUNDS_VOLUME(
                "com.samsung.android.settings.gts.category.SOUNDS_VOLUME",
                R.string.sec_volume_title),
        GROUP_KEY_VIBRATION(
                "com.samsung.android.settings.gts.category.VIBRATION",
                R.string.sec_sound_mode_vibrate),
        GROUP_KEY_VIBRATION_SYSTEM(
                "com.samsung.android.settings.gts.category.VIBRATION_SYSTEM",
                R.string.sec_vibration_system_title),
        GROUP_KEY_VIBRATION_INTENSITY(
                "com.samsung.android.settings.gts.category.VIBRATION_INTENSITY",
                R.string.sec_vibration_intensity),
        GROUP_KEY_ADVANCED(
                "com.samsung.android.settings.gts.category.ADVANCED",
                R.string.useful_feature_title),
        GROUP_KEY_ONE_HANDED_OPERATION(
                "com.samsung.android.settings.gts.category.ONE_HANDED_OPERATION",
                R.string.onehand_settings_title),
        GROUP_KEY_MOUSE_AND_TRACKPAD(
                "com.samsung.android.settings.gts.category.MOUSE_AND_TRACKPAD",
                R.string.pointer_settings_category),
        /* JADX INFO: Fake field, exist only in values array */
        GROUP_KEY_OTHERS(
                "com.samsung.android.settings.gts.category.OTHERS",
                R.string.sec_others_group_title);

        private String mGroupName;
        private int mTitleResId;

        GtsGroup(String str, int i) {
            this.mGroupName = str;
            this.mTitleResId = i;
        }

        public final String getGroupName() {
            return this.mGroupName;
        }

        public final int getTitleResId() {
            return this.mTitleResId;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class LazyHolder {
        public static final GtsResources INSTANCE;

        static {
            GtsResources gtsResources = new GtsResources();
            GtsGroup gtsGroup = GtsGroup.GROUP_KEY_NOTIFICATIONS;
            GtsResources.addResource(gtsGroup, "notification_popup_style", 1);
            GtsResources.addResource(gtsGroup, "show_battery_percent", 1);
            GtsResources.addResource(gtsGroup, "notification_badging", 1);
            GtsResources.addResource(gtsGroup, "homescreen_noti_preview", 1);
            GtsGroup gtsGroup2 = GtsGroup.GROUP_KEY_ADVANCED;
            GtsResources.addResource(gtsGroup2, "double_tab_to_wake_up", 1);
            GtsResources.addResource(gtsGroup2, "double_tap_to_sleep", 1);
            GtsResources.addResource(
                    gtsGroup2, VideoEnhancerPreferenceController.KEY_HDR_EFFECT, 1);
            GtsResources.addResource(gtsGroup2, "function_key_setting", 1);
            GtsGroup gtsGroup3 = GtsGroup.GROUP_KEY_ONE_HANDED_OPERATION;
            GtsResources.addResource(gtsGroup3, "onehand_operation_settings", 1);
            GtsResources.addResource(gtsGroup3, "reduce_screen_size", 1);
            GtsGroup gtsGroup4 = GtsGroup.GROUP_KEY_DISPLAY;
            GtsResources.addResource(gtsGroup4, "auto_brightness", 1);
            GtsResources.addResource(gtsGroup4, "dark_mode", 1);
            GtsResources.addResource(gtsGroup4, "sec_font_size_controller", 1);
            GtsResources.addResource(gtsGroup4, "sec_font_style", 1);
            GtsResources.addResource(gtsGroup4, "navigation_Bar", 1);
            GtsResources.addResource(gtsGroup4, "screen_timeout", 1);
            GtsResources.addResource(gtsGroup4, "edge_screen", 1);
            GtsGroup gtsGroup5 = GtsGroup.GROUP_KEY_VIBRATION;
            GtsResources.addResource(gtsGroup5, SecSoundDeviceVibrationController.KEY, 1);
            GtsResources.addResource(gtsGroup5, "notification_vibration_pattern", 1);
            GtsGroup gtsGroup6 = GtsGroup.GROUP_KEY_VIBRATION_INTENSITY;
            GtsResources.addResource(gtsGroup6, "ring_vibration", 1);
            GtsResources.addResource(gtsGroup6, "notification_vibration", 1);
            GtsResources.addResource(gtsGroup6, "media_vibration", 1);
            GtsResources.addResource(gtsGroup6, "force_touch", 1);
            GtsGroup gtsGroup7 = GtsGroup.GROUP_KEY_VIBRATION_SYSTEM;
            GtsResources.addResource(gtsGroup7, "vibrate_on_touch", 1);
            GtsResources.addResource(gtsGroup7, "dialing_keypad_vibration", 1);
            GtsResources.addResource(gtsGroup7, "navigation_gestures_vibration", 1);
            GtsResources.addResource(gtsGroup7, "charging_feedback_vibration", 1);
            GtsResources.addResource(gtsGroup7, SecSoundKeyboardVibrationController.KEY, 1);
            GtsResources.addResource(gtsGroup7, "camera_feedback_vibration", 1);
            GtsGroup gtsGroup8 = GtsGroup.GROUP_KEY_SOUNDS;
            GtsResources.addResource(gtsGroup8, "ringtone", 1);
            GtsResources.addResource(gtsGroup8, "notification_sound", 1);
            GtsResources.addResource(gtsGroup8, "system_sound", 1);
            GtsGroup gtsGroup9 = GtsGroup.GROUP_KEY_SOUNDS_SYSTEM_SOUNDS;
            GtsResources.addResource(gtsGroup9, "touch_sounds", 1);
            GtsResources.addResource(gtsGroup9, "screen_locking_sounds", 1);
            GtsResources.addResource(gtsGroup9, "charging_sounds", 1);
            GtsResources.addResource(gtsGroup9, "dial_pad_tones", 1);
            GtsResources.addResource(gtsGroup9, SecSoundKeyboardSoundController.KEY, 1);
            GtsGroup gtsGroup10 = GtsGroup.GROUP_KEY_MOUSE_AND_TRACKPAD;
            GtsResources.addResource(gtsGroup10, "mouse_and_trackpad_pref", 1);
            GtsResources.addResource(gtsGroup10, "pointer_speed", 1);
            GtsResources.addResource(gtsGroup10, "mouse_pointer_settings", 1);
            GtsResources.addResource(gtsGroup10, "mouse_scrolling_speed", 1);
            GtsResources.addResource(gtsGroup10, "enhance_pointer_precision", 1);
            GtsResources.addResource(gtsGroup10, "mouse_pointer_size", 1);
            GtsResources.addResource(gtsGroup10, "mouse_custom_pointer_color", 1);
            GtsResources.addResource(gtsGroup10, "key_primary_mouse_button", 1);
            GtsResources.addResource(gtsGroup10, "key_secondary_button", 1);
            GtsResources.addResource(gtsGroup10, "key_middle_button", 1);
            GtsResources.addResource(gtsGroup10, "key_additional_button_1", 1);
            GtsResources.addResource(gtsGroup10, "key_additional_button_2", 1);
            GtsResources.addResource(gtsGroup4, "sec_high_refresh_rate", 2);
            GtsResources.addResource(gtsGroup4, "camera_cutout", 2);
            GtsResources.addResource(gtsGroup4, "full_screen_apps", 2);
            GtsResources.addResource(gtsGroup4, "front_screen_apps", 2);
            GtsResources.addResource(gtsGroup4, "screen_mode", 2);
            GtsResources.addResource(gtsGroup4, "screen_resolution", 2);
            GtsGroup gtsGroup11 = GtsGroup.GROUP_KEY_SOUNDS_VOLUME;
            GtsResources.addResource(gtsGroup11, "ring_volume", 2);
            GtsResources.addResource(gtsGroup11, "media_volume", 2);
            GtsResources.addResource(gtsGroup11, "notification_volume", 2);
            GtsResources.addResource(gtsGroup11, "system_volume", 2);
            GtsResources.addResource(gtsGroup11, "bixby_volume", 2);
            GtsResources.addResource(gtsGroup11, "waiting_tone_volume", 2);
            GtsResources.addResource(gtsGroup11, "accessibility_volume", 2);
            GtsResources.addResource(gtsGroup11, "alarm_volume", 2);
            GtsResources.addResource(gtsGroup11, "volume_key_control", 2);
            GtsResources.addResource(gtsGroup11, "voip_extra_volume_control", 2);
            GtsResources.addResource(gtsGroup6, "system_vibration", 2);
            GtsResources.addResource(gtsGroup6, "vibration_sound_enabled", 2);
            INSTANCE = gtsResources;
        }
    }

    static {
        Debug.semIsProductDev();
        mResourceMap = new LinkedHashMap();
        mMinVersionMap = new LinkedHashMap();
    }

    public static void addResource(GtsGroup gtsGroup, String str, int i) {
        Map map = mResourceMap;
        if (!map.containsKey(str)) {
            Map map2 = mMinVersionMap;
            if (!map2.containsKey(str)) {
                map.put(str, gtsGroup);
                map2.put(str, Integer.valueOf(i));
                return;
            }
        }
        SemLog.e("GtsResources", "ignore (already contained) : ".concat(str));
    }
}
