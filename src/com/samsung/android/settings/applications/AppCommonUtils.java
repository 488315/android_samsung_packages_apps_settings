package com.samsung.android.settings.applications;

import android.util.ArraySet;

import com.android.settings.R;

import com.samsung.android.settings.Rune;

import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AppCommonUtils {
    public static final Set SEARCH_CLASS_NAME_LIST =
            new ArraySet(
                    List.of(
                            "com.android.settings.intelligence.search.SearchActivity",
                            "com.samsung.android.settings.intelligence.search.DeviceSearchTrampoline"));

    public static int getOverlayTitle() {
        return Rune.SUPPORT_TEXT_REQUEST_APPS_OVERLAY_WINDOW_TITLE_BY_VZW
                ? R.string.sec_overlay_settings_vzw
                : R.string.sec_overlay_settings;
    }

    public static int getWriteSettingsTitle() {
        return Rune.SUPPORT_TEXT_REQUEST_APPS_WRITE_SETTING_TITLE_BY_VZW
                ? R.string.sec_write_system_settings_vzw
                : R.string.sec_write_system_settings;
    }
}
