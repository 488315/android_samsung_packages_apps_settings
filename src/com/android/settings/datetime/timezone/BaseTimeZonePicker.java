package com.android.settings.datetime.timezone;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.util.SeslRoundedCorner;
import androidx.appcompat.widget.SearchView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.preference.Preference;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.InstrumentedFragment;
import com.android.settings.datetime.timezone.BaseTimeZoneAdapter.ArrayFilter;
import com.android.settings.datetime.timezone.model.TimeZoneData;
import com.android.settings.datetime.timezone.model.TimeZoneDataLoader;

import com.google.android.material.appbar.AppBarLayout;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.Locale;
import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BaseTimeZonePicker extends InstrumentedFragment
        implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    public BaseTimeZoneAdapter mAdapter;
    public AppBarLayout mAppBarLayout;
    public final boolean mDefaultExpandSearch;
    public RecyclerView mRecyclerView;
    public final boolean mSearchEnabled;
    public final int mSearchHintResId;
    public SearchView mSearchView;
    public SeslRoundedCorner mSeslBottomRoundedCorner;
    public SeslRoundedCorner mSeslHeaderRoundedCorner;
    public TimeZoneData mTimeZoneData;
    public final int mTitleResId;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.datetime.timezone.BaseTimeZonePicker$1, reason: invalid class name */
    public final class AnonymousClass1 extends AppBarLayout.Behavior.DragCallback {
        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior.BaseDragCallback
        public final boolean canDrag(AppBarLayout appBarLayout) {
            return appBarLayout.getResources().getConfiguration().orientation == 2;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnListItemClickListener {
        void onListItemClick(BaseTimeZoneAdapter.AdapterItem adapterItem);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class RoundedDecoration extends RecyclerView.ItemDecoration {
        public RoundedDecoration() {}

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public final void seslOnDispatchDraw(
                Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
            BaseTimeZonePicker baseTimeZonePicker;
            View childAt;
            int childCount = recyclerView.getChildCount();
            int i = childCount - 1;
            int i2 = 0;
            while (true) {
                baseTimeZonePicker = BaseTimeZonePicker.this;
                if (i2 >= i) {
                    break;
                }
                View childAt2 = recyclerView.getChildAt(i2);
                if (recyclerView.getChildViewHolder(childAt2)
                        instanceof BaseTimeZoneAdapter.ItemViewHolder) {
                    baseTimeZonePicker.mSeslHeaderRoundedCorner.drawRoundedCorner(childAt2, canvas);
                    break;
                }
                i2++;
            }
            if (i == 0
                    || recyclerView.canScrollVertically(1)
                    || (childAt = recyclerView.getChildAt(childCount - 2)) == null
                    || !(recyclerView.getChildViewHolder(childAt)
                            instanceof BaseTimeZoneAdapter.ItemViewHolder)) {
                return;
            }
            baseTimeZonePicker.mSeslBottomRoundedCorner.drawRoundedCorner(childAt, canvas);
        }
    }

    public BaseTimeZonePicker(int i, int i2, boolean z, boolean z2) {
        this.mTitleResId = i;
        this.mSearchHintResId = i2;
        this.mSearchEnabled = z;
        this.mDefaultExpandSearch = z2;
    }

    public abstract BaseTimeZoneAdapter createAdapter(TimeZoneData timeZoneData);

    public final Locale getLocale() {
        return getContext().getResources().getConfiguration().getLocales().get(0);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
        getActivity().setTitle(this.mTitleResId);
        LoggingHelper.insertEventLogging(4753, 8016);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        if (this.mSearchEnabled) {
            menuInflater.inflate(R.menu.time_zone_base_search_menu, menu);
            MenuItem findItem = menu.findItem(R.id.time_zone_search_menu);
            findItem.setOnActionExpandListener(this);
            SearchView searchView = (SearchView) findItem.getActionView();
            this.mSearchView = searchView;
            if (searchView != null) {
                searchView.setQueryHint(getText(this.mSearchHintResId));
                SearchView searchView2 = this.mSearchView;
                searchView2.mOnQueryChangeListener = this;
                searchView2.mMaxWidth = Preference.DEFAULT_ORDER;
                searchView2.requestLayout();
                if (this.mDefaultExpandSearch) {
                    findItem.expandActionView();
                    this.mSearchView.onSearchClicked();
                    this.mSearchView.setActivated(true);
                    this.mSearchView.setQuery(true);
                }
                TextView textView = (TextView) this.mSearchView.findViewById(R.id.search_src_text);
                textView.setPadding(0, textView.getPaddingTop(), 0, textView.getPaddingBottom());
                View findViewById = this.mSearchView.findViewById(R.id.search_edit_frame);
                LinearLayout.LayoutParams layoutParams =
                        (LinearLayout.LayoutParams) findViewById.getLayoutParams();
                layoutParams.setMarginStart(
                        (int)
                                getContext()
                                        .getResources()
                                        .getDimension(
                                                R.dimen.sec_timezone_search_bar_start_margin));
                layoutParams.setMarginEnd(0);
                findViewById.setLayoutParams(layoutParams);
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate =
                layoutInflater.inflate(R.layout.sec_time_zone_recycler_view, viewGroup, false);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.recycler_view);
        this.mRecyclerView = recyclerView;
        getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(1));
        this.mRecyclerView.setAdapter(this.mAdapter);
        AppBarLayout appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.app_bar);
        this.mAppBarLayout = appBarLayout;
        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = new AppBarLayout.Behavior();
        behavior.onDragCallback = new AnonymousClass1();
        layoutParams.setBehavior(behavior);
        int listHorizontalPadding = Utils.getListHorizontalPadding(getContext());
        this.mRecyclerView.setPadding(listHorizontalPadding, 0, listHorizontalPadding, 0);
        this.mRecyclerView.setScrollBarStyle(33554432);
        this.mRecyclerView.seslSetFillHorizontalPaddingEnabled(true);
        this.mRecyclerView.semSetRoundedCorners(15);
        this.mRecyclerView.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        this.mRecyclerView.semSetRoundedCornerOffset(
                getActivity()
                        .getResources()
                        .getDimensionPixelSize(R.dimen.sec_preference_horizontal_padding));
        this.mRecyclerView.semSetRoundedCorners(15);
        this.mRecyclerView.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        this.mRecyclerView.seslSetFillBottomEnabled(true);
        this.mRecyclerView.seslSetFillBottomColor(
                getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        this.mRecyclerView.mDrawLastRoundedCorner = false;
        SeslRoundedCorner seslRoundedCorner = new SeslRoundedCorner(getActivity());
        this.mSeslHeaderRoundedCorner = seslRoundedCorner;
        seslRoundedCorner.setRoundedCorners(3);
        SeslRoundedCorner seslRoundedCorner2 = new SeslRoundedCorner(getActivity());
        this.mSeslBottomRoundedCorner = seslRoundedCorner2;
        seslRoundedCorner2.setRoundedCorners(12);
        this.mRecyclerView.addItemDecoration(new RoundedDecoration());
        getLoaderManager()
                .initLoader(
                        0,
                        null,
                        new TimeZoneDataLoader.LoaderCreator(
                                getContext(),
                                new TimeZoneDataLoader
                                        .OnDataReadyCallback() { // from class:
                                                                 // com.android.settings.datetime.timezone.BaseTimeZonePicker$$ExternalSyntheticLambda0
                                    @Override // com.android.settings.datetime.timezone.model.TimeZoneDataLoader.OnDataReadyCallback
                                    public final void onTimeZoneDataReady(
                                            TimeZoneData timeZoneData) {
                                        BaseTimeZonePicker baseTimeZonePicker =
                                                BaseTimeZonePicker.this;
                                        if (baseTimeZonePicker.mTimeZoneData != null
                                                || timeZoneData == null) {
                                            return;
                                        }
                                        baseTimeZonePicker.mTimeZoneData = timeZoneData;
                                        BaseTimeZoneAdapter createAdapter =
                                                baseTimeZonePicker.createAdapter(timeZoneData);
                                        baseTimeZonePicker.mAdapter = createAdapter;
                                        RecyclerView recyclerView2 =
                                                baseTimeZonePicker.mRecyclerView;
                                        if (recyclerView2 != null) {
                                            recyclerView2.setAdapter(createAdapter);
                                        }
                                    }
                                }));
        return inflate;
    }

    @Override // android.view.MenuItem.OnActionExpandListener
    public final boolean onMenuItemActionCollapse(MenuItem menuItem) {
        this.mAppBarLayout.setExpanded(false, false, true);
        RecyclerView recyclerView = this.mRecyclerView;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api21Impl.setNestedScrollingEnabled(recyclerView, true);
        return true;
    }

    @Override // android.view.MenuItem.OnActionExpandListener
    public final boolean onMenuItemActionExpand(MenuItem menuItem) {
        this.mAppBarLayout.setExpanded(false, false, true);
        RecyclerView recyclerView = this.mRecyclerView;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api21Impl.setNestedScrollingEnabled(recyclerView, false);
        return true;
    }

    @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
    public final boolean onQueryTextChange(String str) {
        BaseTimeZoneAdapter baseTimeZoneAdapter = this.mAdapter;
        if (baseTimeZoneAdapter == null) {
            return false;
        }
        if (baseTimeZoneAdapter.mFilter == null) {
            baseTimeZoneAdapter.mFilter = baseTimeZoneAdapter.new ArrayFilter();
        }
        baseTimeZoneAdapter.mFilter.filter(str);
        return false;
    }

    @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
    public final void onQueryTextSubmit(String str) {}
}
