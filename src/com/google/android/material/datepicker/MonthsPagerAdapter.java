package com.google.android.material.datepicker;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

import java.util.Calendar;
import java.util.Iterator;
import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class MonthsPagerAdapter extends RecyclerView.Adapter {
    public final CalendarConstraints calendarConstraints;
    public final int itemHeight;
    public final MaterialCalendar.AnonymousClass3 onDayClickListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ViewHolder extends RecyclerView.ViewHolder {
        public final MaterialCalendarGridView monthGrid;
        public final TextView monthTitle;

        public ViewHolder(LinearLayout linearLayout, boolean z) {
            super(linearLayout);
            TextView textView = (TextView) linearLayout.findViewById(R.id.month_title);
            this.monthTitle = textView;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            new ViewCompat.AnonymousClass2(R.id.tag_accessibility_heading, Boolean.class, 0, 28, 2)
                    .set(textView, Boolean.TRUE);
            this.monthGrid = (MaterialCalendarGridView) linearLayout.findViewById(R.id.month_grid);
            if (z) {
                return;
            }
            textView.setVisibility(8);
        }
    }

    public MonthsPagerAdapter(
            Context context,
            CalendarConstraints calendarConstraints,
            MaterialCalendar.AnonymousClass3 anonymousClass3) {
        Month month = calendarConstraints.start;
        Month month2 = calendarConstraints.end;
        Month month3 = calendarConstraints.openAt;
        if (month.firstOfMonth.compareTo(month3.firstOfMonth) > 0) {
            throw new IllegalArgumentException("firstPage cannot be after currentPage");
        }
        if (month3.firstOfMonth.compareTo(month2.firstOfMonth) > 0) {
            throw new IllegalArgumentException("currentPage cannot be after lastPage");
        }
        this.itemHeight =
                (context.getResources().getDimensionPixelSize(R.dimen.mtrl_calendar_day_height)
                                * MonthAdapter.MAXIMUM_WEEKS)
                        + (MaterialDatePicker.readMaterialCalendarStyleBoolean(
                                        context, android.R.attr.windowFullscreen)
                                ? context.getResources()
                                        .getDimensionPixelSize(R.dimen.mtrl_calendar_day_height)
                                : 0);
        this.calendarConstraints = calendarConstraints;
        this.onDayClickListener = anonymousClass3;
        setHasStableIds(true);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.calendarConstraints.monthSpan;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final long getItemId(int i) {
        Calendar dayCopy = UtcDates.getDayCopy(this.calendarConstraints.start.firstOfMonth);
        dayCopy.add(2, i);
        return new Month(dayCopy).firstOfMonth.getTimeInMillis();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        CalendarConstraints calendarConstraints = this.calendarConstraints;
        Calendar dayCopy = UtcDates.getDayCopy(calendarConstraints.start.firstOfMonth);
        dayCopy.add(2, i);
        Month month = new Month(dayCopy);
        viewHolder2.monthTitle.setText(month.getLongName());
        final MaterialCalendarGridView materialCalendarGridView =
                (MaterialCalendarGridView) viewHolder2.monthGrid.findViewById(R.id.month_grid);
        if (materialCalendarGridView.getAdapter() == null
                || !month.equals(materialCalendarGridView.getAdapter().month)) {
            new MonthAdapter(month, calendarConstraints);
            throw null;
        }
        materialCalendarGridView.invalidate();
        MonthAdapter adapter = materialCalendarGridView.getAdapter();
        Iterator it = adapter.previouslySelectedDates.iterator();
        while (it.hasNext()) {
            long longValue = ((Long) it.next()).longValue();
            if (Month.create(longValue).equals(adapter.month)) {
                Calendar dayCopy2 = UtcDates.getDayCopy(adapter.month.firstOfMonth);
                dayCopy2.setTimeInMillis(longValue);
                adapter.updateSelectedState(
                        (TextView)
                                materialCalendarGridView.getChildAt(
                                        (materialCalendarGridView
                                                                .getAdapter()
                                                                .firstPositionInMonth()
                                                        + (dayCopy2.get(5) - 1))
                                                - materialCalendarGridView
                                                        .getFirstVisiblePosition()),
                        longValue);
            }
        }
        materialCalendarGridView.setOnItemClickListener(
                new AdapterView
                        .OnItemClickListener() { // from class:
                                                 // com.google.android.material.datepicker.MonthsPagerAdapter.1
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public final void onItemClick(
                            AdapterView adapterView, View view, int i2, long j) {
                        MonthAdapter adapter2 = materialCalendarGridView.getAdapter();
                        if (i2 < adapter2.firstPositionInMonth()
                                || i2 > adapter2.lastPositionInMonth()) {
                            return;
                        }
                        if (materialCalendarGridView.getAdapter().getItem(i2).longValue()
                                >= ((DateValidatorPointForward)
                                                MaterialCalendar.this.calendarConstraints.validator)
                                        .point) {
                            throw null;
                        }
                    }
                });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LinearLayout linearLayout =
                (LinearLayout)
                        MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                                viewGroup, R.layout.mtrl_calendar_month_labeled, viewGroup, false);
        if (!MaterialDatePicker.readMaterialCalendarStyleBoolean(
                viewGroup.getContext(), android.R.attr.windowFullscreen)) {
            return new ViewHolder(linearLayout, false);
        }
        linearLayout.setLayoutParams(new RecyclerView.LayoutParams(-1, this.itemHeight));
        return new ViewHolder(linearLayout, true);
    }
}
