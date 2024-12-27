package com.google.android.material.datepicker;

import android.R;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.Calendar;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class MaterialCalendar<S> extends PickerFragment {
    public CalendarConstraints calendarConstraints;
    public CalendarSelector calendarSelector;
    public CalendarStyle calendarStyle;
    public Month current;
    public View dayFrame;
    public View monthNext;
    public View monthPrev;
    public RecyclerView recyclerView;
    public int themeResId;
    public View yearFrame;
    public RecyclerView yearSelector;
    static final Object MONTHS_VIEW_GROUP_TAG = "MONTHS_VIEW_GROUP_TAG";
    static final Object NAVIGATION_PREV_TAG = "NAVIGATION_PREV_TAG";
    static final Object NAVIGATION_NEXT_TAG = "NAVIGATION_NEXT_TAG";
    static final Object SELECTOR_TOGGLE_TAG = "SELECTOR_TOGGLE_TAG";

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.android.material.datepicker.MaterialCalendar$3, reason: invalid class name */
    public final class AnonymousClass3 {
        public AnonymousClass3() {}
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static final class CalendarSelector {
        public static final /* synthetic */ CalendarSelector[] $VALUES;
        public static final CalendarSelector DAY;
        public static final CalendarSelector YEAR;

        static {
            CalendarSelector calendarSelector = new CalendarSelector("DAY", 0);
            DAY = calendarSelector;
            CalendarSelector calendarSelector2 = new CalendarSelector("YEAR", 1);
            YEAR = calendarSelector2;
            $VALUES = new CalendarSelector[] {calendarSelector, calendarSelector2};
        }

        public static CalendarSelector valueOf(String str) {
            return (CalendarSelector) Enum.valueOf(CalendarSelector.class, str);
        }

        public static CalendarSelector[] values() {
            return (CalendarSelector[]) $VALUES.clone();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            bundle = getArguments();
        }
        this.themeResId = bundle.getInt("THEME_RES_ID_KEY");
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                bundle.getParcelable("GRID_SELECTOR_KEY"));
        this.calendarConstraints =
                (CalendarConstraints) bundle.getParcelable("CALENDAR_CONSTRAINTS_KEY");
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                bundle.getParcelable("DAY_VIEW_DECORATOR_KEY"));
        this.current = (Month) bundle.getParcelable("CURRENT_MONTH_KEY");
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        int i;
        final int i2;
        ContextThemeWrapper contextThemeWrapper =
                new ContextThemeWrapper(getContext(), this.themeResId);
        this.calendarStyle = new CalendarStyle(contextThemeWrapper);
        LayoutInflater cloneInContext = layoutInflater.cloneInContext(contextThemeWrapper);
        Month month = this.calendarConstraints.start;
        if (MaterialDatePicker.readMaterialCalendarStyleBoolean(
                contextThemeWrapper, R.attr.windowFullscreen)) {
            i = com.android.settings.R.layout.mtrl_calendar_vertical;
            i2 = 1;
        } else {
            i = com.android.settings.R.layout.mtrl_calendar_horizontal;
            i2 = 0;
        }
        View inflate = cloneInContext.inflate(i, viewGroup, false);
        Resources resources = requireContext().getResources();
        int dimensionPixelOffset =
                resources.getDimensionPixelOffset(
                                com.android.settings.R.dimen
                                        .mtrl_calendar_navigation_bottom_padding)
                        + resources.getDimensionPixelOffset(
                                com.android.settings.R.dimen.mtrl_calendar_navigation_top_padding)
                        + resources.getDimensionPixelSize(
                                com.android.settings.R.dimen.mtrl_calendar_navigation_height);
        int dimensionPixelSize =
                resources.getDimensionPixelSize(
                        com.android.settings.R.dimen.mtrl_calendar_days_of_week_height);
        int i3 = MonthAdapter.MAXIMUM_WEEKS;
        inflate.setMinimumHeight(
                dimensionPixelOffset
                        + dimensionPixelSize
                        + (resources.getDimensionPixelOffset(
                                        com.android.settings.R.dimen
                                                .mtrl_calendar_month_vertical_padding)
                                * (i3 - 1))
                        + (resources.getDimensionPixelSize(
                                        com.android.settings.R.dimen.mtrl_calendar_day_height)
                                * i3)
                        + resources.getDimensionPixelOffset(
                                com.android.settings.R.dimen.mtrl_calendar_bottom_padding));
        GridView gridView =
                (GridView)
                        inflate.findViewById(com.android.settings.R.id.mtrl_calendar_days_of_week);
        final int i4 = 0;
        ViewCompat.setAccessibilityDelegate(
                gridView,
                new AccessibilityDelegateCompat() { // from class:
                                                    // com.google.android.material.datepicker.MaterialCalendar.1
                    @Override // androidx.core.view.AccessibilityDelegateCompat
                    public final void onInitializeAccessibilityNodeInfo(
                            View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                        switch (i4) {
                            case 0:
                                this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(
                                        view, accessibilityNodeInfoCompat.mInfo);
                                accessibilityNodeInfoCompat.setCollectionInfo(null);
                                break;
                            default:
                                this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(
                                        view, accessibilityNodeInfoCompat.mInfo);
                                accessibilityNodeInfoCompat.setScrollable(false);
                                break;
                        }
                    }
                });
        int i5 = this.calendarConstraints.firstDayOfWeek;
        gridView.setAdapter(
                (ListAdapter) (i5 > 0 ? new DaysOfWeekAdapter(i5) : new DaysOfWeekAdapter()));
        gridView.setNumColumns(month.daysInWeek);
        gridView.setEnabled(false);
        this.recyclerView =
                (RecyclerView) inflate.findViewById(com.android.settings.R.id.mtrl_calendar_months);
        getContext();
        this.recyclerView.setLayoutManager(
                new LinearLayoutManager(
                        i2) { // from class:
                              // com.google.android.material.datepicker.MaterialCalendar.2
                    @Override // androidx.recyclerview.widget.LinearLayoutManager
                    public final void calculateExtraLayoutSpace(
                            RecyclerView.State state, int[] iArr) {
                        int i6 = i2;
                        MaterialCalendar materialCalendar = MaterialCalendar.this;
                        if (i6 == 0) {
                            iArr[0] = materialCalendar.recyclerView.getWidth();
                            iArr[1] = materialCalendar.recyclerView.getWidth();
                        } else {
                            iArr[0] = materialCalendar.recyclerView.getHeight();
                            iArr[1] = materialCalendar.recyclerView.getHeight();
                        }
                    }

                    @Override // androidx.recyclerview.widget.LinearLayoutManager,
                              // androidx.recyclerview.widget.RecyclerView.LayoutManager
                    public final void smoothScrollToPosition(int i6, RecyclerView recyclerView) {
                        SmoothCalendarLayoutManager$1 smoothCalendarLayoutManager$1 =
                                new SmoothCalendarLayoutManager$1(recyclerView.getContext());
                        smoothCalendarLayoutManager$1.mTargetPosition = i6;
                        startSmoothScroll(smoothCalendarLayoutManager$1);
                    }
                });
        this.recyclerView.setTag(MONTHS_VIEW_GROUP_TAG);
        final MonthsPagerAdapter monthsPagerAdapter =
                new MonthsPagerAdapter(
                        contextThemeWrapper, this.calendarConstraints, new AnonymousClass3());
        this.recyclerView.setAdapter(monthsPagerAdapter);
        int integer =
                contextThemeWrapper
                        .getResources()
                        .getInteger(
                                com.android.settings.R.integer.mtrl_calendar_year_selector_span);
        RecyclerView recyclerView =
                (RecyclerView)
                        inflate.findViewById(
                                com.android.settings.R.id.mtrl_calendar_year_selector_frame);
        this.yearSelector = recyclerView;
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
            this.yearSelector.setLayoutManager(new GridLayoutManager(integer, 0));
            this.yearSelector.setAdapter(new YearGridAdapter(this));
            this.yearSelector.addItemDecoration(
                    new RecyclerView
                            .ItemDecoration() { // from class:
                                                // com.google.android.material.datepicker.MaterialCalendar.5
                        {
                            UtcDates.getUtcCalendarOf(null);
                            UtcDates.getUtcCalendarOf(null);
                        }

                        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                        public final void onDraw(Canvas canvas, RecyclerView recyclerView2) {
                            if ((recyclerView2.getAdapter() instanceof YearGridAdapter)
                                    && (recyclerView2.getLayoutManager()
                                            instanceof GridLayoutManager)) {
                                MaterialCalendar.this.getClass();
                                throw null;
                            }
                        }
                    });
        }
        if (inflate.findViewById(com.android.settings.R.id.month_navigation_fragment_toggle)
                != null) {
            final MaterialButton materialButton =
                    (MaterialButton)
                            inflate.findViewById(
                                    com.android.settings.R.id.month_navigation_fragment_toggle);
            materialButton.setTag(SELECTOR_TOGGLE_TAG);
            ViewCompat.setAccessibilityDelegate(
                    materialButton,
                    new AccessibilityDelegateCompat() { // from class:
                                                        // com.google.android.material.datepicker.MaterialCalendar.6
                        @Override // androidx.core.view.AccessibilityDelegateCompat
                        public final void onInitializeAccessibilityNodeInfo(
                                View view,
                                AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                            this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(
                                    view, accessibilityNodeInfoCompat.mInfo);
                            MaterialCalendar materialCalendar = MaterialCalendar.this;
                            accessibilityNodeInfoCompat.mInfo.setHintText(
                                    materialCalendar.dayFrame.getVisibility() == 0
                                            ? materialCalendar.getString(
                                                    com.android.settings.R.string
                                                            .mtrl_picker_toggle_to_year_selection)
                                            : materialCalendar.getString(
                                                    com.android.settings.R.string
                                                            .mtrl_picker_toggle_to_day_selection));
                        }
                    });
            View findViewById =
                    inflate.findViewById(com.android.settings.R.id.month_navigation_previous);
            this.monthPrev = findViewById;
            findViewById.setTag(NAVIGATION_PREV_TAG);
            View findViewById2 =
                    inflate.findViewById(com.android.settings.R.id.month_navigation_next);
            this.monthNext = findViewById2;
            findViewById2.setTag(NAVIGATION_NEXT_TAG);
            this.yearFrame =
                    inflate.findViewById(
                            com.android.settings.R.id.mtrl_calendar_year_selector_frame);
            this.dayFrame =
                    inflate.findViewById(
                            com.android.settings.R.id.mtrl_calendar_day_selector_frame);
            setSelector(CalendarSelector.DAY);
            materialButton.setText(this.current.getLongName());
            this.recyclerView.addOnScrollListener(
                    new RecyclerView
                            .OnScrollListener() { // from class:
                                                  // com.google.android.material.datepicker.MaterialCalendar.7
                        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                        public final void onScrollStateChanged(int i6, RecyclerView recyclerView2) {
                            if (i6 == 0) {
                                recyclerView2.announceForAccessibility(materialButton.getText());
                            }
                        }

                        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                        public final void onScrolled(RecyclerView recyclerView2, int i6, int i7) {
                            MaterialCalendar materialCalendar = MaterialCalendar.this;
                            int findFirstVisibleItemPosition =
                                    i6 < 0
                                            ? ((LinearLayoutManager)
                                                            materialCalendar.recyclerView
                                                                    .getLayoutManager())
                                                    .findFirstVisibleItemPosition()
                                            : ((LinearLayoutManager)
                                                            materialCalendar.recyclerView
                                                                    .getLayoutManager())
                                                    .findLastVisibleItemPosition();
                            MonthsPagerAdapter monthsPagerAdapter2 = monthsPagerAdapter;
                            Calendar dayCopy =
                                    UtcDates.getDayCopy(
                                            monthsPagerAdapter2
                                                    .calendarConstraints
                                                    .start
                                                    .firstOfMonth);
                            dayCopy.add(2, findFirstVisibleItemPosition);
                            materialCalendar.current = new Month(dayCopy);
                            Calendar dayCopy2 =
                                    UtcDates.getDayCopy(
                                            monthsPagerAdapter2
                                                    .calendarConstraints
                                                    .start
                                                    .firstOfMonth);
                            dayCopy2.add(2, findFirstVisibleItemPosition);
                            materialButton.setText(new Month(dayCopy2).getLongName());
                        }
                    });
            materialButton.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.google.android.material.datepicker.MaterialCalendar.8
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            MaterialCalendar materialCalendar = MaterialCalendar.this;
                            CalendarSelector calendarSelector = materialCalendar.calendarSelector;
                            CalendarSelector calendarSelector2 = CalendarSelector.YEAR;
                            CalendarSelector calendarSelector3 = CalendarSelector.DAY;
                            if (calendarSelector == calendarSelector2) {
                                materialCalendar.setSelector(calendarSelector3);
                            } else if (calendarSelector == calendarSelector3) {
                                materialCalendar.setSelector(calendarSelector2);
                            }
                        }
                    });
            final int i6 = 0;
            this.monthNext.setOnClickListener(
                    new View.OnClickListener(
                            this) { // from class:
                                    // com.google.android.material.datepicker.MaterialCalendar.9
                        public final /* synthetic */ MaterialCalendar this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            switch (i6) {
                                case 0:
                                    int findFirstVisibleItemPosition =
                                            ((LinearLayoutManager)
                                                                    this.this$0.recyclerView
                                                                            .getLayoutManager())
                                                            .findFirstVisibleItemPosition()
                                                    + 1;
                                    if (findFirstVisibleItemPosition
                                            < this.this$0
                                                    .recyclerView
                                                    .getAdapter()
                                                    .getItemCount()) {
                                        MaterialCalendar materialCalendar = this.this$0;
                                        Calendar dayCopy =
                                                UtcDates.getDayCopy(
                                                        monthsPagerAdapter
                                                                .calendarConstraints
                                                                .start
                                                                .firstOfMonth);
                                        dayCopy.add(2, findFirstVisibleItemPosition);
                                        materialCalendar.setCurrentMonth(new Month(dayCopy));
                                        break;
                                    }
                                    break;
                                default:
                                    int findLastVisibleItemPosition =
                                            ((LinearLayoutManager)
                                                                    this.this$0.recyclerView
                                                                            .getLayoutManager())
                                                            .findLastVisibleItemPosition()
                                                    - 1;
                                    if (findLastVisibleItemPosition >= 0) {
                                        MaterialCalendar materialCalendar2 = this.this$0;
                                        Calendar dayCopy2 =
                                                UtcDates.getDayCopy(
                                                        monthsPagerAdapter
                                                                .calendarConstraints
                                                                .start
                                                                .firstOfMonth);
                                        dayCopy2.add(2, findLastVisibleItemPosition);
                                        materialCalendar2.setCurrentMonth(new Month(dayCopy2));
                                        break;
                                    }
                                    break;
                            }
                        }
                    });
            final int i7 = 1;
            this.monthPrev.setOnClickListener(
                    new View.OnClickListener(
                            this) { // from class:
                                    // com.google.android.material.datepicker.MaterialCalendar.9
                        public final /* synthetic */ MaterialCalendar this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            switch (i7) {
                                case 0:
                                    int findFirstVisibleItemPosition =
                                            ((LinearLayoutManager)
                                                                    this.this$0.recyclerView
                                                                            .getLayoutManager())
                                                            .findFirstVisibleItemPosition()
                                                    + 1;
                                    if (findFirstVisibleItemPosition
                                            < this.this$0
                                                    .recyclerView
                                                    .getAdapter()
                                                    .getItemCount()) {
                                        MaterialCalendar materialCalendar = this.this$0;
                                        Calendar dayCopy =
                                                UtcDates.getDayCopy(
                                                        monthsPagerAdapter
                                                                .calendarConstraints
                                                                .start
                                                                .firstOfMonth);
                                        dayCopy.add(2, findFirstVisibleItemPosition);
                                        materialCalendar.setCurrentMonth(new Month(dayCopy));
                                        break;
                                    }
                                    break;
                                default:
                                    int findLastVisibleItemPosition =
                                            ((LinearLayoutManager)
                                                                    this.this$0.recyclerView
                                                                            .getLayoutManager())
                                                            .findLastVisibleItemPosition()
                                                    - 1;
                                    if (findLastVisibleItemPosition >= 0) {
                                        MaterialCalendar materialCalendar2 = this.this$0;
                                        Calendar dayCopy2 =
                                                UtcDates.getDayCopy(
                                                        monthsPagerAdapter
                                                                .calendarConstraints
                                                                .start
                                                                .firstOfMonth);
                                        dayCopy2.add(2, findLastVisibleItemPosition);
                                        materialCalendar2.setCurrentMonth(new Month(dayCopy2));
                                        break;
                                    }
                                    break;
                            }
                        }
                    });
        }
        if (!MaterialDatePicker.readMaterialCalendarStyleBoolean(
                contextThemeWrapper, R.attr.windowFullscreen)) {
            new PagerSnapHelper().attachToRecyclerView(this.recyclerView);
        }
        this.recyclerView.scrollToPosition(
                monthsPagerAdapter.calendarConstraints.start.monthsUntil(this.current));
        final int i8 = 1;
        ViewCompat.setAccessibilityDelegate(
                this.recyclerView,
                new AccessibilityDelegateCompat() { // from class:
                                                    // com.google.android.material.datepicker.MaterialCalendar.1
                    @Override // androidx.core.view.AccessibilityDelegateCompat
                    public final void onInitializeAccessibilityNodeInfo(
                            View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                        switch (i8) {
                            case 0:
                                this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(
                                        view, accessibilityNodeInfoCompat.mInfo);
                                accessibilityNodeInfoCompat.setCollectionInfo(null);
                                break;
                            default:
                                this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(
                                        view, accessibilityNodeInfoCompat.mInfo);
                                accessibilityNodeInfoCompat.setScrollable(false);
                                break;
                        }
                    }
                });
        return inflate;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putInt("THEME_RES_ID_KEY", this.themeResId);
        bundle.putParcelable("GRID_SELECTOR_KEY", null);
        bundle.putParcelable("CALENDAR_CONSTRAINTS_KEY", this.calendarConstraints);
        bundle.putParcelable("DAY_VIEW_DECORATOR_KEY", null);
        bundle.putParcelable("CURRENT_MONTH_KEY", this.current);
    }

    public final void setCurrentMonth(Month month) {
        MonthsPagerAdapter monthsPagerAdapter = (MonthsPagerAdapter) this.recyclerView.getAdapter();
        final int monthsUntil = monthsPagerAdapter.calendarConstraints.start.monthsUntil(month);
        int monthsUntil2 =
                monthsUntil
                        - monthsPagerAdapter.calendarConstraints.start.monthsUntil(this.current);
        boolean z = Math.abs(monthsUntil2) > 3;
        boolean z2 = monthsUntil2 > 0;
        this.current = month;
        if (z && z2) {
            this.recyclerView.scrollToPosition(monthsUntil - 3);
            this.recyclerView.post(
                    new Runnable() { // from class:
                                     // com.google.android.material.datepicker.MaterialCalendar.11
                        @Override // java.lang.Runnable
                        public final void run() {
                            MaterialCalendar.this.recyclerView.smoothScrollToPosition(monthsUntil);
                        }
                    });
        } else if (!z) {
            this.recyclerView.post(
                    new Runnable() { // from class:
                                     // com.google.android.material.datepicker.MaterialCalendar.11
                        @Override // java.lang.Runnable
                        public final void run() {
                            MaterialCalendar.this.recyclerView.smoothScrollToPosition(monthsUntil);
                        }
                    });
        } else {
            this.recyclerView.scrollToPosition(monthsUntil + 3);
            this.recyclerView.post(
                    new Runnable() { // from class:
                                     // com.google.android.material.datepicker.MaterialCalendar.11
                        @Override // java.lang.Runnable
                        public final void run() {
                            MaterialCalendar.this.recyclerView.smoothScrollToPosition(monthsUntil);
                        }
                    });
        }
    }

    public final void setSelector(CalendarSelector calendarSelector) {
        this.calendarSelector = calendarSelector;
        if (calendarSelector == CalendarSelector.YEAR) {
            this.yearSelector
                    .getLayoutManager()
                    .scrollToPosition(
                            this.current.year
                                    - ((YearGridAdapter) this.yearSelector.getAdapter())
                                            .materialCalendar
                                            .calendarConstraints
                                            .start
                                            .year);
            this.yearFrame.setVisibility(0);
            this.dayFrame.setVisibility(8);
            this.monthPrev.setVisibility(8);
            this.monthNext.setVisibility(8);
            return;
        }
        if (calendarSelector == CalendarSelector.DAY) {
            this.yearFrame.setVisibility(8);
            this.dayFrame.setVisibility(0);
            this.monthPrev.setVisibility(0);
            this.monthNext.setVisibility(0);
            setCurrentMonth(this.current);
        }
    }
}
