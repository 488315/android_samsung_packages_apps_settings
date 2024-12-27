package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Paint;

import com.android.settings.R;

import com.google.android.material.R$styleable;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.resources.MaterialResources;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class CalendarStyle {
    public final CalendarItemStyle invalidDay;
    public final CalendarItemStyle todayYear;
    public final CalendarItemStyle year;

    public CalendarStyle(Context context) {
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(
                        MaterialAttributes.resolveTypedValueOrThrow(
                                        context,
                                        R.attr.materialCalendarStyle,
                                        MaterialCalendar.class.getCanonicalName())
                                .data,
                        R$styleable.MaterialCalendar);
        CalendarItemStyle.create(context, obtainStyledAttributes.getResourceId(4, 0));
        this.invalidDay =
                CalendarItemStyle.create(context, obtainStyledAttributes.getResourceId(2, 0));
        CalendarItemStyle.create(context, obtainStyledAttributes.getResourceId(3, 0));
        CalendarItemStyle.create(context, obtainStyledAttributes.getResourceId(5, 0));
        ColorStateList colorStateList =
                MaterialResources.getColorStateList(context, obtainStyledAttributes, 7);
        this.year = CalendarItemStyle.create(context, obtainStyledAttributes.getResourceId(9, 0));
        CalendarItemStyle.create(context, obtainStyledAttributes.getResourceId(8, 0));
        this.todayYear =
                CalendarItemStyle.create(context, obtainStyledAttributes.getResourceId(10, 0));
        new Paint().setColor(colorStateList.getDefaultColor());
        obtainStyledAttributes.recycle();
    }
}
