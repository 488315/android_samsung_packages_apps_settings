package com.google.android.material.datepicker;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;

import com.android.settings.R;

import java.util.Calendar;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class DaysOfWeekAdapter extends BaseAdapter {
    public final Calendar calendar;
    public final int daysInWeek;
    public final int firstDayOfWeek;

    public DaysOfWeekAdapter() {
        Calendar utcCalendarOf = UtcDates.getUtcCalendarOf(null);
        this.calendar = utcCalendarOf;
        this.daysInWeek = utcCalendarOf.getMaximum(7);
        this.firstDayOfWeek = utcCalendarOf.getFirstDayOfWeek();
    }

    @Override // android.widget.Adapter
    public final int getCount() {
        return this.daysInWeek;
    }

    @Override // android.widget.Adapter
    public final Object getItem(int i) {
        int i2 = this.daysInWeek;
        if (i >= i2) {
            return null;
        }
        int i3 = i + this.firstDayOfWeek;
        if (i3 > i2) {
            i3 -= i2;
        }
        return Integer.valueOf(i3);
    }

    @Override // android.widget.Adapter
    public final long getItemId(int i) {
        return 0L;
    }

    @Override // android.widget.Adapter
    public final View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView = (TextView) view;
        if (view == null) {
            textView =
                    (TextView)
                            MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0
                                    .m(
                                            viewGroup,
                                            R.layout.mtrl_calendar_day_of_week,
                                            viewGroup,
                                            false);
        }
        Calendar calendar = this.calendar;
        int i2 = i + this.firstDayOfWeek;
        int i3 = this.daysInWeek;
        if (i2 > i3) {
            i2 -= i3;
        }
        calendar.set(7, i2);
        textView.setText(
                this.calendar.getDisplayName(
                        7, 4, textView.getResources().getConfiguration().locale));
        textView.setContentDescription(
                String.format(
                        viewGroup
                                .getContext()
                                .getString(R.string.mtrl_picker_day_of_week_column_header),
                        this.calendar.getDisplayName(7, 2, Locale.getDefault())));
        return textView;
    }

    public DaysOfWeekAdapter(int i) {
        Calendar utcCalendarOf = UtcDates.getUtcCalendarOf(null);
        this.calendar = utcCalendarOf;
        this.daysInWeek = utcCalendarOf.getMaximum(7);
        this.firstDayOfWeek = i;
    }
}
