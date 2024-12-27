package com.android.settingslib.spa.framework.theme;

import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.unit.TextUnitKt;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SettingsTypographyKt {
    public static final TextStyle toMediumWeight(TextStyle textStyle) {
        Intrinsics.checkNotNullParameter(textStyle, "<this>");
        return TextStyle.m527copyp1EtxEg$default(
                textStyle,
                0L,
                0L,
                FontWeight.Medium,
                null,
                TextUnitKt.getEm(0.01d),
                0L,
                null,
                null,
                16777083);
    }
}
