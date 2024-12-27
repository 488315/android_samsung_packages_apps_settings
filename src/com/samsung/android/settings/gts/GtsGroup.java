package com.samsung.android.settings.gts;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
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
            "com.samsung.android.settings.gts.category.DISPLAY", R.string.display_settings_title),
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
            "com.samsung.android.settings.gts.category.SOUNDS_VOLUME", R.string.sec_volume_title),
    GROUP_KEY_VIBRATION(
            "com.samsung.android.settings.gts.category.VIBRATION", R.string.sec_sound_mode_vibrate),
    GROUP_KEY_VIBRATION_SYSTEM(
            "com.samsung.android.settings.gts.category.VIBRATION_SYSTEM",
            R.string.sec_vibration_system_title),
    GROUP_KEY_VIBRATION_INTENSITY(
            "com.samsung.android.settings.gts.category.VIBRATION_INTENSITY",
            R.string.sec_vibration_intensity),
    GROUP_KEY_ADVANCED(
            "com.samsung.android.settings.gts.category.ADVANCED", R.string.useful_feature_title),
    GROUP_KEY_MOUSE_AND_TRACKPAD(
            "com.samsung.android.settings.gts.category.MOUSE_AND_TRACKPAD",
            R.string.pointer_settings_category),
    /* JADX INFO: Fake field, exist only in values array */
    GROUP_KEY_OTHERS(
            "com.samsung.android.settings.gts.category.OTHERS", R.string.sec_others_group_title);

    private String mGroupName;
    private int mTitleResId;

    GtsGroup(String str, int i) {
        this.mGroupName = str;
        this.mTitleResId = i;
    }

    public final String getGroupName() {
        return this.mGroupName;
    }
}
