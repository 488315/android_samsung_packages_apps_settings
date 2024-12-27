package com.samsung.android.settings.accessibility.shortcut;

import android.content.Intent;
import android.os.Bundle;

import com.sec.ims.im.ImIntent;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AccessibilityHomepageActivityShortcut extends BaseShortcutActivity {
    @Override // com.samsung.android.settings.accessibility.shortcut.BaseShortcutActivity
    public final void performShortcutOperation() {
        Intent intent = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
        intent.setFlags(271089664);
        Bundle bundle = new Bundle();
        bundle.putString(ImIntent.Extras.EXTRA_FROM, "Shortcut");
        intent.putExtra(":settings:show_fragment_args", bundle);
        startActivity(intent);
    }
}
