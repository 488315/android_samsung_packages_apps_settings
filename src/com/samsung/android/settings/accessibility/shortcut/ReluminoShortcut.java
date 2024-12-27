package com.samsung.android.settings.accessibility.shortcut;

import android.provider.Settings;

import com.samsung.android.settings.accessibility.SecAccessibilityUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ReluminoShortcut extends BaseShortcutActivity {
    @Override // com.samsung.android.settings.accessibility.shortcut.BaseShortcutActivity
    public final void performShortcutOperation() {
        Settings.Secure.putInt(
                getContentResolver(),
                "relumino_switch",
                (Settings.Secure.getInt(getContentResolver(), "relumino_switch", 0) == 1 ? 1 : 0)
                        ^ 1);
        SecAccessibilityUtils.setRelumino(this);
    }
}
