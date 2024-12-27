package com.samsung.android.settings.accessibility.broadcast;

import android.content.Context;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.util.Log;

import com.android.settings.R;

import com.google.android.setupcompat.util.WizardManagerHelper;

import java.util.Collections;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AccessibilityShortcutReceiver {
    public static void refreshShortcut(Context context) {
        ShortcutInfo shortcutInfo;
        if (WizardManagerHelper.isUserSetupComplete(context)) {
            ShortcutManager shortcutManager =
                    (ShortcutManager) context.getSystemService(ShortcutManager.class);
            try {
                Iterator<ShortcutInfo> it = shortcutManager.getPinnedShortcuts().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        shortcutInfo = null;
                        break;
                    } else if ("acc-shortcut".equals(it.next().getId())) {
                        shortcutInfo =
                                new ShortcutInfo.Builder(context, "acc-shortcut")
                                        .setShortLabel(
                                                context.getString(R.string.accessibility_settings))
                                        .setIcon(
                                                Icon.createWithResource(
                                                        context,
                                                        R.mipmap.ic_accessibility_launcher))
                                        .build();
                        break;
                    }
                }
                if (shortcutInfo != null) {
                    shortcutManager.updateShortcuts(Collections.singletonList(shortcutInfo));
                }
            } catch (IllegalStateException e) {
                Log.e("AccessibilityShortcutReceiver", "Current user is locked.", e);
            }
        }
    }
}
