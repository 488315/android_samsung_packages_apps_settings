package com.samsung.android.settings.accessibility.shortcut;

import android.provider.Settings;

import com.samsung.android.settings.accessibility.SecAccessibilityUtils;

import java.util.HashSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class HighContrastFontsShortcut extends BaseShortcutActivity {
    @Override // com.samsung.android.settings.accessibility.shortcut.BaseShortcutActivity
    public final void performShortcutOperation() {
        int i =
                (Settings.Secure.getInt(getContentResolver(), "high_text_contrast_enabled", 0) == 1
                                ? 1
                                : 0)
                        ^ 1;
        HashSet hashSet = SecAccessibilityUtils.excludeDeviceNameSet;
        Settings.Secure.putInt(getContentResolver(), "high_text_contrast_enabled", i);
    }
}
