package com.android.settingslib.spa.framework.theme;

import android.content.Context;

import androidx.compose.ui.text.font.DeviceFontFamilyNameFont;
import androidx.compose.ui.text.font.DeviceFontFamilyNameFontKt;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontListFontFamily;
import androidx.compose.ui.text.font.FontWeight;

import com.android.settingslib.applications.RecentAppOpsAccess;

import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SettingsFontFamilyKt {
    public static final FontFamily getFontFamily(Context context, String str, String str2) {
        String string =
                context.getResources()
                        .getString(
                                context.getResources()
                                        .getIdentifier(
                                                str,
                                                "string",
                                                RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME));
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        String string2 =
                context.getResources()
                        .getString(
                                context.getResources()
                                        .getIdentifier(
                                                str2,
                                                "string",
                                                RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME));
        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
        if (string.length() == 0 || string2.length() == 0) {
            return FontFamily.Default;
        }
        if (string.length() <= 0) {
            throw new IllegalArgumentException("name may not be empty".toString());
        }
        DeviceFontFamilyNameFont m530Fontvxs03AY$default =
                DeviceFontFamilyNameFontKt.m530Fontvxs03AY$default(string, FontWeight.Normal);
        if (string2.length() > 0) {
            return new FontListFontFamily(
                    ArraysKt___ArraysKt.asList(
                            new DeviceFontFamilyNameFont[] {
                                m530Fontvxs03AY$default,
                                DeviceFontFamilyNameFontKt.m530Fontvxs03AY$default(
                                        string2, FontWeight.Medium)
                            }));
        }
        throw new IllegalArgumentException("name may not be empty".toString());
    }
}
