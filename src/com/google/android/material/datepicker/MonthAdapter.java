package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import com.android.settings.R;

import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class MonthAdapter extends BaseAdapter {
    public final CalendarConstraints calendarConstraints;
    public CalendarStyle calendarStyle;
    public final Month month;
    public final Collection previouslySelectedDates;
    public static final int MAXIMUM_WEEKS = UtcDates.getUtcCalendarOf(null).getMaximum(4);
    public static final int MAXIMUM_GRID_CELLS =
            (UtcDates.getUtcCalendarOf(null).getMaximum(7)
                            + UtcDates.getUtcCalendarOf(null).getMaximum(5))
                    - 1;

    public MonthAdapter(Month month, CalendarConstraints calendarConstraints) {
        this.month = month;
        this.calendarConstraints = calendarConstraints;
        throw null;
    }

    public final int firstPositionInMonth() {
        Month month = this.month;
        int i = this.calendarConstraints.firstDayOfWeek;
        int i2 = month.firstOfMonth.get(7);
        if (i <= 0) {
            i = month.firstOfMonth.getFirstDayOfWeek();
        }
        int i3 = i2 - i;
        return i3 < 0 ? i3 + month.daysInWeek : i3;
    }

    @Override // android.widget.Adapter
    public final int getCount() {
        return MAXIMUM_GRID_CELLS;
    }

    @Override // android.widget.Adapter
    public final long getItemId(int i) {
        return i / this.month.daysInWeek;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0064  */
    @Override // android.widget.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.view.View getView(
            int r5, android.view.View r6, android.view.ViewGroup r7) {
        /*
            r4 = this;
            android.content.Context r0 = r7.getContext()
            com.google.android.material.datepicker.CalendarStyle r1 = r4.calendarStyle
            if (r1 != 0) goto Lf
            com.google.android.material.datepicker.CalendarStyle r1 = new com.google.android.material.datepicker.CalendarStyle
            r1.<init>(r0)
            r4.calendarStyle = r1
        Lf:
            r0 = r6
            android.widget.TextView r0 = (android.widget.TextView) r0
            r1 = 0
            if (r6 != 0) goto L1f
            r6 = 2131559588(0x7f0d04a4, float:1.8744524E38)
            android.view.View r6 = androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(r7, r6, r7, r1)
            r0 = r6
            android.widget.TextView r0 = (android.widget.TextView) r0
        L1f:
            int r6 = r4.firstPositionInMonth()
            int r6 = r5 - r6
            if (r6 < 0) goto L55
            com.google.android.material.datepicker.Month r7 = r4.month
            int r2 = r7.daysInMonth
            if (r6 < r2) goto L2e
            goto L55
        L2e:
            r2 = 1
            int r6 = r6 + r2
            r0.setTag(r7)
            android.content.res.Resources r7 = r0.getResources()
            android.content.res.Configuration r7 = r7.getConfiguration()
            java.util.Locale r7 = r7.locale
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            java.lang.Object[] r6 = new java.lang.Object[]{r6}
            java.lang.String r3 = "%d"
            java.lang.String r6 = java.lang.String.format(r7, r3, r6)
            r0.setText(r6)
            r0.setVisibility(r1)
            r0.setEnabled(r2)
            goto L5d
        L55:
            r6 = 8
            r0.setVisibility(r6)
            r0.setEnabled(r1)
        L5d:
            java.lang.Long r5 = r4.getItem(r5)
            if (r5 != 0) goto L64
            goto L6b
        L64:
            long r5 = r5.longValue()
            r4.updateSelectedState(r0, r5)
        L6b:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.android.material.datepicker.MonthAdapter.getView(int,"
                    + " android.view.View, android.view.ViewGroup):android.view.View");
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public final boolean hasStableIds() {
        return true;
    }

    public boolean isEndOfRange(long j) {
        throw null;
    }

    public boolean isStartOfRange(long j) {
        throw null;
    }

    public final int lastPositionInMonth() {
        return (firstPositionInMonth() + this.month.daysInMonth) - 1;
    }

    public final void updateSelectedState(TextView textView, long j) {
        if (textView == null) {
            return;
        }
        Context context = textView.getContext();
        boolean z = UtcDates.getTodayCalendar().getTimeInMillis() == j;
        boolean isStartOfRange = isStartOfRange(j);
        boolean isEndOfRange = isEndOfRange(j);
        Calendar todayCalendar = UtcDates.getTodayCalendar();
        Calendar utcCalendarOf = UtcDates.getUtcCalendarOf(null);
        utcCalendarOf.setTimeInMillis(j);
        String format =
                todayCalendar.get(1) == utcCalendarOf.get(1)
                        ? UtcDates.getAndroidFormat(Locale.getDefault(), "MMMMEEEEd")
                                .format(new Date(j))
                        : UtcDates.getAndroidFormat(Locale.getDefault(), "yMMMMEEEEd")
                                .format(new Date(j));
        if (z) {
            format =
                    String.format(
                            context.getString(R.string.mtrl_picker_today_description), format);
        }
        if (isStartOfRange) {
            format =
                    String.format(
                            context.getString(R.string.mtrl_picker_start_date_description), format);
        } else if (isEndOfRange) {
            format =
                    String.format(
                            context.getString(R.string.mtrl_picker_end_date_description), format);
        }
        textView.setContentDescription(format);
        if (j >= ((DateValidatorPointForward) this.calendarConstraints.validator).point) {
            textView.setEnabled(true);
            throw null;
        }
        textView.setEnabled(false);
        CalendarItemStyle calendarItemStyle = this.calendarStyle.invalidDay;
        calendarItemStyle.getClass();
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
        MaterialShapeDrawable materialShapeDrawable2 = new MaterialShapeDrawable();
        ShapeAppearanceModel shapeAppearanceModel = calendarItemStyle.itemShape;
        materialShapeDrawable.setShapeAppearanceModel(shapeAppearanceModel);
        materialShapeDrawable2.setShapeAppearanceModel(shapeAppearanceModel);
        materialShapeDrawable.setFillColor(calendarItemStyle.backgroundColor);
        float f = calendarItemStyle.strokeWidth;
        ColorStateList colorStateList = calendarItemStyle.strokeColor;
        materialShapeDrawable.drawableState.strokeWidth = f;
        materialShapeDrawable.invalidateSelf();
        MaterialShapeDrawable.MaterialShapeDrawableState materialShapeDrawableState =
                materialShapeDrawable.drawableState;
        if (materialShapeDrawableState.strokeColor != colorStateList) {
            materialShapeDrawableState.strokeColor = colorStateList;
            materialShapeDrawable.onStateChange(materialShapeDrawable.getState());
        }
        textView.setTextColor(calendarItemStyle.textColor);
        RippleDrawable rippleDrawable =
                new RippleDrawable(
                        calendarItemStyle.textColor.withAlpha(30),
                        materialShapeDrawable,
                        materialShapeDrawable2);
        Rect rect = calendarItemStyle.insets;
        InsetDrawable insetDrawable =
                new InsetDrawable(
                        (Drawable) rippleDrawable, rect.left, rect.top, rect.right, rect.bottom);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        textView.setBackground(insetDrawable);
    }

    @Override // android.widget.Adapter
    public final Long getItem(int i) {
        if (i < firstPositionInMonth() || i > lastPositionInMonth()) {
            return null;
        }
        Month month = this.month;
        int firstPositionInMonth = (i - firstPositionInMonth()) + 1;
        Calendar dayCopy = UtcDates.getDayCopy(month.firstOfMonth);
        dayCopy.set(5, firstPositionInMonth);
        return Long.valueOf(dayCopy.getTimeInMillis());
    }
}
