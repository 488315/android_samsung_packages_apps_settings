package com.samsung.android.settings.accessibility.shortcut;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class MagnifierCameraShortcut extends BaseShortcutActivity {
    @Override // com.samsung.android.settings.accessibility.shortcut.BaseShortcutActivity
    public final void performShortcutOperation() {
        try {
            startActivity(
                    new Intent()
                            .setComponent(
                                    ComponentName.createRelative(
                                            "com.sec.android.app.magnifier", ".Magnifier"))
                            .addFlags(268435456));
        } catch (ActivityNotFoundException e) {
            Log.w("MagnifierCameraShortcut", "ActivityNotFoundException!", e);
        }
    }
}
