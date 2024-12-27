package com.samsung.android.settings.accessibility.shortcut;

import android.content.Intent;
import android.provider.Settings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AmplifyShortcut extends BaseShortcutActivity {
    @Override // com.samsung.android.settings.accessibility.shortcut.BaseShortcutActivity
    public final void performShortcutOperation() {
        boolean z =
                Settings.System.getInt(getContentResolver(), "run_amplify_ambient_sound", 0) == 1;
        Intent intent = new Intent("com.samsung.accessibility.AMPLIFY_AMBIENT_SOUND_TOGGLE");
        intent.putExtra("isEnabled", true ^ z);
        sendBroadcast(intent);
    }
}
