package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;

import androidx.core.util.Preconditions;

import com.google.android.material.R$styleable;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.AbsoluteCornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class CalendarItemStyle {
    public final ColorStateList backgroundColor;
    public final Rect insets;
    public final ShapeAppearanceModel itemShape;
    public final ColorStateList strokeColor;
    public final int strokeWidth;
    public final ColorStateList textColor;

    public CalendarItemStyle(
            ColorStateList colorStateList,
            ColorStateList colorStateList2,
            ColorStateList colorStateList3,
            int i,
            ShapeAppearanceModel shapeAppearanceModel,
            Rect rect) {
        Preconditions.checkArgumentNonnegative(rect.left);
        Preconditions.checkArgumentNonnegative(rect.top);
        Preconditions.checkArgumentNonnegative(rect.right);
        Preconditions.checkArgumentNonnegative(rect.bottom);
        this.insets = rect;
        this.textColor = colorStateList2;
        this.backgroundColor = colorStateList;
        this.strokeColor = colorStateList3;
        this.strokeWidth = i;
        this.itemShape = shapeAppearanceModel;
    }

    public static CalendarItemStyle create(Context context, int i) {
        Preconditions.checkArgument(
                "Cannot create a CalendarItemStyle with a styleResId of 0", i != 0);
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(i, R$styleable.MaterialCalendarItem);
        Rect rect =
                new Rect(
                        obtainStyledAttributes.getDimensionPixelOffset(0, 0),
                        obtainStyledAttributes.getDimensionPixelOffset(2, 0),
                        obtainStyledAttributes.getDimensionPixelOffset(1, 0),
                        obtainStyledAttributes.getDimensionPixelOffset(3, 0));
        ColorStateList colorStateList =
                MaterialResources.getColorStateList(context, obtainStyledAttributes, 4);
        ColorStateList colorStateList2 =
                MaterialResources.getColorStateList(context, obtainStyledAttributes, 9);
        ColorStateList colorStateList3 =
                MaterialResources.getColorStateList(context, obtainStyledAttributes, 7);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(8, 0);
        ShapeAppearanceModel build =
                ShapeAppearanceModel.builder(
                                context,
                                obtainStyledAttributes.getResourceId(5, 0),
                                obtainStyledAttributes.getResourceId(6, 0),
                                new AbsoluteCornerSize(0))
                        .build();
        obtainStyledAttributes.recycle();
        return new CalendarItemStyle(
                colorStateList, colorStateList2, colorStateList3, dimensionPixelSize, build, rect);
    }
}
