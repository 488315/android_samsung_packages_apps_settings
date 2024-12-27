package com.android.settingslib;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.SemSystemProperties;
import android.util.Log;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settingslib.development.DevelopmentSettingsEnabler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class MinorModeUtils {
    public static boolean hasCHNMinorModePreventDeveloperOptionsFromBeingTurnedOn(Context context) {
        if (!"China".equalsIgnoreCase(SemSystemProperties.get("ro.csc.country_code"))) {
            return false;
        }
        boolean z = !DevelopmentSettingsEnabler.isDevelopmentSettingsEnabled(context);
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "prevent from turn on: ", "SettingsLib/MinorModeUtils", z);
        return z;
    }

    public static boolean isCHNMinorModeRestrictDateTimeModify(Context context) {
        Log.d("SettingsLib/MinorModeUtils", "datetime restriction:");
        return isCHNMinorModeRestrictionEnabled(
                context, "get_modify_datetime_restrict", "modify_datetime_restrict");
    }

    public static boolean isCHNMinorModeRestrictionEnabled(
            Context context, String str, String str2) {
        if (!"China".equalsIgnoreCase(SemSystemProperties.get("ro.csc.country_code"))) {
            return false;
        }
        try {
            Bundle call =
                    context.getContentResolver()
                            .call(
                                    Uri.parse("content://com.samsung.android.minormode.db"),
                                    str,
                                    (String) null,
                                    (Bundle) null);
            boolean z = call != null && call.getBoolean(str2);
            Log.d("SettingsLib/MinorModeUtils", "return: " + z);
            return z;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("SettingsLib/MinorModeUtils", "return: false");
            return false;
        }
    }
}
