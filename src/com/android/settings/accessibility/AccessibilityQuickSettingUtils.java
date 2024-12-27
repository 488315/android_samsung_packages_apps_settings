package com.android.settings.accessibility;

import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.StringJoiner;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AccessibilityQuickSettingUtils {
    public static final TextUtils.SimpleStringSplitter sStringColonSplitter =
            new TextUtils.SimpleStringSplitter(':');

    public static void optInValueToSharedPreferences(Context context, ComponentName componentName) {
        TextUtils.SimpleStringSplitter simpleStringSplitter;
        String string =
                context.getSharedPreferences("accessibility_prefs", 0)
                        .getString("tile_service_shown", ApnSettings.MVNO_NONE);
        if (!TextUtils.isEmpty(string)) {
            sStringColonSplitter.setString(string);
            do {
                simpleStringSplitter = sStringColonSplitter;
                if (simpleStringSplitter.hasNext()) {}
            } while (!TextUtils.equals(
                    componentName.flattenToString(), simpleStringSplitter.next()));
            return;
        }
        StringJoiner stringJoiner = new StringJoiner(String.valueOf(':'));
        if (!TextUtils.isEmpty(string)) {
            stringJoiner.add(string);
        }
        stringJoiner.add(componentName.flattenToString());
        context.getSharedPreferences("accessibility_prefs", 0)
                .edit()
                .putString("tile_service_shown", stringJoiner.toString())
                .apply();
    }
}
