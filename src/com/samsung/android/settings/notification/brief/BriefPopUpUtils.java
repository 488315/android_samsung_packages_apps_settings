package com.samsung.android.settings.notification.brief;

import android.content.Context;
import android.provider.Settings;

import com.android.settings.accessibility.PreferredShortcuts$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class BriefPopUpUtils {
    public static int loadCustomTextColor(Context context, String str) {
        if (str != null) {
            str =
                    str.replace("\u2068", ApnSettings.MVNO_NONE)
                            .replace("\u2069", ApnSettings.MVNO_NONE)
                            .replace("\u200f", ApnSettings.MVNO_NONE);
        }
        HashMap loadCustomTextList = loadCustomTextList(context);
        if (loadCustomTextList == null || !loadCustomTextList.containsKey(str)) {
            return -1;
        }
        return ((Integer) loadCustomTextList.get(str)).intValue();
    }

    public static HashMap loadCustomTextList(Context context) {
        String[] split;
        HashMap hashMap = new HashMap();
        String stringForUser =
                Settings.System.getStringForUser(
                        context.getContentResolver(), "edge_lighting_custom_text_color", -2);
        if (stringForUser == null
                || (split = stringForUser.split(";")) == null
                || split.length % 2 != 0) {
            return null;
        }
        for (int i = 0; i < split.length; i++) {
            try {
                if (i % 2 == 0) {
                    hashMap.put(split[i], Integer.valueOf(Integer.parseInt(split[i + 1])));
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return hashMap;
    }

    public static void removeCustomTextColor(Context context, String str) {
        HashMap loadCustomTextList = loadCustomTextList(context);
        StringBuilder sb = new StringBuilder();
        if (loadCustomTextList != null && loadCustomTextList.containsKey(str)) {
            loadCustomTextList.remove(str);
            for (Map.Entry entry : loadCustomTextList.entrySet()) {
                sb.append((String) entry.getKey());
                sb.append(";");
                sb.append(entry.getValue());
                sb.append(";");
            }
        }
        Settings.System.putStringForUser(
                context.getContentResolver(), "edge_lighting_custom_text_color", sb.toString(), -2);
    }

    public static void saveCustomTextColor(Context context, int i, String str) {
        String str2 = ApnSettings.MVNO_NONE;
        if (str != null) {
            str =
                    str.replace("\u2068", ApnSettings.MVNO_NONE)
                            .replace("\u2069", ApnSettings.MVNO_NONE);
        }
        String stringForUser =
                Settings.System.getStringForUser(
                        context.getContentResolver(), "edge_lighting_custom_text_color", -2);
        if (stringForUser != null) {
            str2 = stringForUser;
        }
        Settings.System.putStringForUser(
                context.getContentResolver(),
                "edge_lighting_custom_text_color",
                PreferredShortcuts$$ExternalSyntheticOutline0.m(i, str2, str, ";", ";").toString(),
                -2);
    }
}
