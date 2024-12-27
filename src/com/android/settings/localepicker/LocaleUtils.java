package com.android.settings.localepicker;

import android.os.LocaleList;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class LocaleUtils {
    public static boolean isInSystemLocale(String str) {
        LocaleList localeList = LocaleList.getDefault();
        Locale build =
                new Locale.Builder()
                        .setLocale(Locale.forLanguageTag(str))
                        .clearExtensions()
                        .build();
        for (int i = 0; i < localeList.size(); i++) {
            if (build.equals(
                    new Locale.Builder().setLocale(localeList.get(i)).clearExtensions().build())) {
                return true;
            }
        }
        return false;
    }
}
