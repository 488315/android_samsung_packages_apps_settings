package com.samsung.android.settings.analyzestorage.external.database.utils;

import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class CategoryQueryUtils {
    public static String getRegexpExtensionPatterns(String[] strArr) {
        String str =
                (String)
                        Arrays.stream(strArr)
                                .filter(new CategoryQueryUtils$$ExternalSyntheticLambda0())
                                .collect(Collectors.joining("|"));
        Locale locale = Locale.US;
        return ComposerKt$$ExternalSyntheticOutline0.m(
                "(_display_name REGEXP ('(?i).*\\.(", str, ")$'))");
    }
}
