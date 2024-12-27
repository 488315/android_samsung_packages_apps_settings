package com.android.settingslib.spa.framework.theme;

import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValuesImpl;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SettingsDimension {
    public static final float appIconInfoSize;
    public static final float appIconItemSize;
    public static final PaddingValuesImpl buttonPadding;
    public static final float buttonPaddingVertical;
    public static final PaddingValuesImpl dialogItemPadding;
    public static final float dialogItemPaddingHorizontal;
    public static final float iconLarge;
    public static final float illustrationPadding;
    public static final float itemDividerHeight;
    public static final float itemIconSize;
    public static final PaddingValuesImpl itemPadding;
    public static final float itemPaddingEnd;
    public static final float itemPaddingStart;
    public static final float itemPaddingVertical;
    public static final PaddingValuesImpl menuFieldPadding;
    public static final float paddingExtraLarge;
    public static final PaddingValuesImpl textFieldPadding;
    public static final float paddingTiny = 2;
    public static final float paddingSmall = 4;
    public static final float itemIconContainerSize = 72;
    public static final float itemPaddingAround = 8;
    public static final float illustrationMaxWidth = FileType.SEVEN_Z;
    public static final float illustrationMaxHeight = 300;
    public static final float illustrationCornerRadius = 28;

    static {
        float f = 16;
        float f2 = 24;
        paddingExtraLarge = f2;
        itemIconSize = f2;
        itemPaddingStart = f2;
        itemPaddingEnd = f;
        itemPaddingVertical = f;
        itemPadding = new PaddingValuesImpl(f2, f, f, f);
        textFieldPadding = PaddingKt.m84PaddingValuesa9UjIt4$default(f2, f, 0.0f, 10);
        menuFieldPadding = PaddingKt.m84PaddingValuesa9UjIt4$default(f2, f, f, 2);
        float f3 = 32;
        itemDividerHeight = f3;
        float f4 = 48;
        iconLarge = f4;
        appIconItemSize = f3;
        appIconInfoSize = f4;
        float f5 = 12;
        buttonPaddingVertical = f5;
        buttonPadding = new PaddingValuesImpl(f, f5, f, f5);
        dialogItemPaddingHorizontal = f2;
        dialogItemPadding = new PaddingValuesImpl(f2, f5, f2, f5);
        illustrationPadding = f;
    }
}
