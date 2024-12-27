package com.android.settings.accessibility;

import android.content.ContentResolver;
import android.provider.Settings;

import com.google.common.primitives.Ints;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AccessibilityTimeoutUtils {
    public static int getSecureAccessibilityTimeoutValue(ContentResolver contentResolver) {
        Integer tryParse;
        String string =
                Settings.Secure.getString(
                        contentResolver, "accessibility_interactive_ui_timeout_ms");
        if (string == null || (tryParse = Ints.tryParse(string)) == null) {
            return 0;
        }
        return tryParse.intValue();
    }
}
