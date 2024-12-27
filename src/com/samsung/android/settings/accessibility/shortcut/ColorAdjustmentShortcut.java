package com.samsung.android.settings.accessibility.shortcut;

import android.provider.Settings;

import com.samsung.android.settings.accessibility.SecAccessibilityUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ColorAdjustmentShortcut extends BaseShortcutActivity {
    @Override // com.samsung.android.settings.accessibility.shortcut.BaseShortcutActivity
    public final void performShortcutOperation() {
        Settings.System.putInt(
                getContentResolver(),
                "color_blind",
                (Settings.System.getInt(getContentResolver(), "color_blind", 0) == 1 ? 1 : 0) ^ 1);
        SecAccessibilityUtils.setColorAdjustment(this);
    }
}
