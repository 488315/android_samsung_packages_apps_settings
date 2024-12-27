package com.android.settings.accessibility;

import android.content.ComponentName;
import android.util.StatsEvent;
import android.util.StatsLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AccessibilityStatsLogUtils {
    public static int convertToEntryPoint(int i) {
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                i2 = 3;
                if (i != 3) {
                    i2 = 4;
                    if (i != 4) {
                        return 0;
                    }
                }
            }
        }
        return i2;
    }

    public static int convertToHearingAidInfoBondEntry(int i) {
        if (i == 747) {
            return 0;
        }
        if (i == 1390) {
            return 3;
        }
        if (i == 1512) {
            return 1;
        }
        if (i != 1930) {
            return i != 2024 ? -1 : 4;
        }
        return 2;
    }

    public static int convertToItemKeyName(String str) {
        str.getClass();
        switch (str) {
            case "font_size":
                return 1;
            case "toggle_high_text_contrast_preference":
                return 4;
            case "toggle_force_bold_text":
                return 3;
            case "reset":
                return 5;
            case "display_size":
                return 2;
            default:
                return 0;
        }
    }

    public static void logAccessibilityServiceEnabled(ComponentName componentName, boolean z) {
        String flattenToString = componentName.flattenToString();
        int i = z ? 1 : 2;
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(267);
        newBuilder.writeString(flattenToString);
        newBuilder.writeInt(i);
        newBuilder.usePooledBuffer();
        StatsLog.write(newBuilder.build());
    }
}
