package com.samsung.android.settings.accessibility.dexterity.autoaction;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settingslib.widget.LottieColorUtils$$ExternalSyntheticOutline0;

import com.samsung.android.knox.zt.config.securelog.SignalSeverity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AutoActionUtils {
    public static final HashMap mActionMap;

    static {
        HashMap hashMap = new HashMap();
        mActionMap = hashMap;
        LottieColorUtils$$ExternalSyntheticOutline0.m(
                R.string.accessibility_corner_action_type_none,
                hashMap,
                SignalSeverity.NONE,
                R.string.accessibility_corner_action_type_open_close_notifications,
                "open_close_notifications");
        LottieColorUtils$$ExternalSyntheticOutline0.m(
                R.string.accessibility_corner_action_type_open_close_quick_panel,
                hashMap,
                "open_close_quick_panel",
                R.string.accessibility_corner_action_type_screenshot,
                "screen_shot");
        LottieColorUtils$$ExternalSyntheticOutline0.m(
                R.string.accessibility_corner_action_type_talk_to_bixby,
                hashMap,
                "talk_to_bixby",
                R.string.accessibility_corner_action_type_sos_messages,
                "send_sos_messages");
        LottieColorUtils$$ExternalSyntheticOutline0.m(
                R.string.accessibility_corner_action_type_media_volume_up,
                hashMap,
                "media_volume_up",
                R.string.accessibility_corner_action_type_media_volume_down,
                "media_volume_down");
        LottieColorUtils$$ExternalSyntheticOutline0.m(
                R.string.accessibility_corner_action_type_ringtone_volume_up,
                hashMap,
                "ringtone_volume_up",
                R.string.accessibility_corner_action_type_ringtone_volume_down,
                "ringtone_volume_down");
        LottieColorUtils$$ExternalSyntheticOutline0.m(
                R.string.accessibility_corner_action_type_increase_brightness,
                hashMap,
                "increase_brightness",
                R.string.accessibility_corner_action_type_reduce_brightness,
                "reduce_brightness");
        LottieColorUtils$$ExternalSyntheticOutline0.m(
                R.string.accessibility_corner_action_type_screen_rotation,
                hashMap,
                "screen_rotation",
                R.string.accessibility_corner_action_type_sound_vibrate_mute,
                "sound_vibrate_mute");
        LottieColorUtils$$ExternalSyntheticOutline0.m(
                R.string.accessibility_corner_action_type_screen_off,
                hashMap,
                "screen_off",
                R.string.accessibility_corner_action_type_power_off_menu,
                "power_off_menu");
        LottieColorUtils$$ExternalSyntheticOutline0.m(
                R.string.accessibility_corner_action_type_home,
                hashMap,
                "home",
                R.string.accessibility_corner_action_type_recents,
                "recents");
        LottieColorUtils$$ExternalSyntheticOutline0.m(
                R.string.accessibility_corner_action_type_back,
                hashMap,
                "back",
                R.string.accessibility_corner_action_type_accessibility_button,
                "accessibility_button");
        LottieColorUtils$$ExternalSyntheticOutline0.m(
                R.string.accessibility_corner_action_type_double_click,
                hashMap,
                "double_click",
                R.string.accessibility_corner_action_type_click_and_hold,
                "click_and_hold");
        LottieColorUtils$$ExternalSyntheticOutline0.m(
                R.string.accessibility_corner_action_type_drag,
                hashMap,
                "drag",
                R.string.accessibility_corner_action_type_drag_and_drop,
                "drag_and_drop");
        LottieColorUtils$$ExternalSyntheticOutline0.m(
                R.string.accessibility_corner_action_type_zoom_in,
                hashMap,
                "zoom_in",
                R.string.accessibility_corner_action_type_zoom_out,
                "zoom_out");
        LottieColorUtils$$ExternalSyntheticOutline0.m(
                R.string.accessibility_corner_action_type_swipe_left,
                hashMap,
                "swipe_left",
                R.string.accessibility_corner_action_type_swipe_right,
                "swipe_right");
        LottieColorUtils$$ExternalSyntheticOutline0.m(
                R.string.accessibility_corner_action_type_swipe_up,
                hashMap,
                "swipe_up",
                R.string.accessibility_corner_action_type_swipe_down,
                "swipe_down");
        hashMap.put(
                "pause_resume_auto_click",
                Integer.valueOf(R.string.accessibility_corner_action_type_pause_resume_auto_click));
    }

    public static void autoActionSetAsDefault(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Settings.Secure.putInt(contentResolver, "accessibility_auto_action_type", 0);
        Settings.Secure.putInt(contentResolver, "accessibility_pause_auto_click_with", 0);
        Settings.Secure.putInt(contentResolver, "accessibility_corner_action_enabled", 0);
        Settings.Secure.putString(
                contentResolver, "accessibility_corner_actions", "none:none:none:none");
        Settings.Secure.putInt(contentResolver, "accessibility_auto_action_delay", 600);
        Settings.Secure.putInt(contentResolver, "accessibility_auto_click_paused_state", 0);
    }

    public static void updateAction(Context context, int i, int i2) {
        String string =
                Settings.Secure.getString(
                        context.getContentResolver(), "accessibility_corner_actions");
        if (string == null) {
            string = "none:none:none:none";
        }
        String[] split = string.split(":");
        if (i != -1) {
            ArrayList arrayList = new ArrayList(Arrays.asList(split[i].split(",")));
            arrayList.remove(0);
            if (arrayList.size() == 0) {
                split[i] = SignalSeverity.NONE;
            } else {
                split[i] = String.join(",", arrayList);
            }
        }
        if (i2 != -1) {
            ArrayList arrayList2 = new ArrayList(Arrays.asList(split[i2].split(",")));
            if (arrayList2.size() == 1
                    && ((String) arrayList2.get(0)).equals(SignalSeverity.NONE)) {
                split[i2] = "pause_resume_auto_click";
            } else {
                arrayList2.add(0, "pause_resume_auto_click");
                split[i2] = String.join(",", arrayList2);
            }
        }
        String join = String.join(":", split);
        Settings.Secure.putInt(
                context.getContentResolver(),
                "accessibility_corner_action_enabled",
                1 ^ ("none:none:none:none".equals(join) ? 1 : 0));
        Settings.Secure.putString(
                context.getContentResolver(), "accessibility_corner_actions", join);
    }
}
