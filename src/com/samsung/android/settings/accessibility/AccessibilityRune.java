package com.samsung.android.settings.accessibility;

import android.os.SystemProperties;
import android.text.TextUtils;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

import com.samsung.android.feature.SemFloatingFeature;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AccessibilityRune {
    public static boolean getFloatingFeatureBooleanValue(String str) {
        return SemFloatingFeature.getInstance().getBoolean(str);
    }

    public static boolean isAtLeastOneUI_6_1() {
        return SystemProperties.getInt("ro.build.version.oneui", 0) >= 60100;
    }

    public static boolean isSupportAudioFeature(String str) {
        String string =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_AUDIO_CONFIG_JDM_ITEMS");
        if (TextUtils.isEmpty(string)) {
            return true;
        }
        DialogFragment$$ExternalSyntheticOutline0.m("JDM: ", string, "A11yRune");
        return !string.toLowerCase(Locale.US).contains(str);
    }
}
