package com.samsung.android.settings.display;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.ContentObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.widget.LayoutPreference;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ScreenTimeoutDockingActivity extends SettingsPreferenceFragment
        implements CompoundButton.OnCheckedChangeListener {
    public ScreenTimeoutAdapter mAdapter;
    public Context mContext;
    public RecyclerView mScreenTimeoutList;
    public AnonymousClass1 mScreenTimeoutObserver;
    public View mScreenTimeoutView;
    public SettingsMainSwitchBar mSwitchBar = null;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ScreenTimeoutAdapter extends RecyclerView.Adapter {
        public long mCurrentTimeout;
        public final CharSequence[] mEntries;
        public final List mListItems = new ArrayList();
        public final CharSequence[] mValues;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class RecyclerViewHolder extends RecyclerView.ViewHolder {
            public RadioButton mCheckBoxView;
            public TextView mTitleView;
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class ScreenTimeoutList {
            public boolean mIsChecked;
            public String mListItemTitle;
            public String mListItemValue;
        }

        public ScreenTimeoutAdapter(
                CharSequence[] charSequenceArr, CharSequence[] charSequenceArr2, long j) {
            this.mEntries = charSequenceArr;
            this.mValues = charSequenceArr2;
            this.mCurrentTimeout = j;
            if (charSequenceArr == null) {
                Log.e("ScreenTimeoutDockingActivity", "addScreenTimeoutListView failed");
                return;
            }
            int i = 0;
            while (true) {
                CharSequence[] charSequenceArr3 = this.mEntries;
                if (i >= charSequenceArr3.length) {
                    return;
                }
                CharSequence charSequence = charSequenceArr3[i];
                CharSequence charSequence2 = this.mValues[i];
                ScreenTimeoutList screenTimeoutList = new ScreenTimeoutList();
                screenTimeoutList.mListItemTitle = charSequence.toString();
                screenTimeoutList.mListItemValue = charSequence2.toString();
                if (charSequence2.equals(String.valueOf(this.mCurrentTimeout))) {
                    screenTimeoutList.mIsChecked = true;
                }
                this.mListItems.add(screenTimeoutList);
                notifyDataSetChanged();
                i++;
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemCount() {
            return ((ArrayList) this.mListItems).size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemViewType(int i) {
            return 1;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int i) {
            if (viewHolder instanceof RecyclerViewHolder) {
                RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) viewHolder;
                recyclerViewHolder.mTitleView.setText(
                        ((ScreenTimeoutList) ((ArrayList) this.mListItems).get(i)).mListItemTitle);
                recyclerViewHolder.mCheckBoxView.setChecked(
                        ((ScreenTimeoutList) ((ArrayList) this.mListItems).get(i)).mIsChecked);
                viewHolder.itemView.setOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.settings.display.ScreenTimeoutDockingActivity.ScreenTimeoutAdapter.1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                ScreenTimeoutAdapter screenTimeoutAdapter =
                                        ScreenTimeoutAdapter.this;
                                RecyclerView.ViewHolder viewHolder2 = viewHolder;
                                int i2 = i;
                                for (int i3 = 0; i3 < screenTimeoutAdapter.mEntries.length; i3++) {
                                    if (i3 == i2) {
                                        ((ScreenTimeoutList)
                                                                ((ArrayList)
                                                                                screenTimeoutAdapter
                                                                                        .mListItems)
                                                                        .get(i3))
                                                        .mIsChecked =
                                                true;
                                        ((RecyclerViewHolder) viewHolder2)
                                                .mCheckBoxView.setChecked(
                                                        ((ScreenTimeoutList)
                                                                        ((ArrayList)
                                                                                        screenTimeoutAdapter
                                                                                                .mListItems)
                                                                                .get(i3))
                                                                .mIsChecked);
                                        screenTimeoutAdapter.mCurrentTimeout =
                                                Long.parseLong(
                                                        ((ScreenTimeoutList)
                                                                        ((ArrayList)
                                                                                        screenTimeoutAdapter
                                                                                                .mListItems)
                                                                                .get(i3))
                                                                .mListItemValue);
                                    } else {
                                        ((ScreenTimeoutList)
                                                                ((ArrayList)
                                                                                screenTimeoutAdapter
                                                                                        .mListItems)
                                                                        .get(i3))
                                                        .mIsChecked =
                                                false;
                                    }
                                    screenTimeoutAdapter.notifyItemChanged(i3);
                                }
                                Settings.System.putInt(
                                        ScreenTimeoutDockingActivity.this.getContentResolver(),
                                        "dock_screen_off_timeout",
                                        Integer.parseInt(
                                                ((ScreenTimeoutList)
                                                                ((ArrayList)
                                                                                ScreenTimeoutAdapter
                                                                                        .this
                                                                                        .mListItems)
                                                                        .get(i))
                                                        .mListItemValue));
                            }
                        });
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            if (i != 1) {
                return null;
            }
            View m =
                    MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                            viewGroup, R.layout.sec_screen_timeout_list_item, viewGroup, false);
            RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(m);
            recyclerViewHolder.mTitleView = (TextView) m.findViewById(R.id.timeout_title);
            recyclerViewHolder.mCheckBoxView = (RadioButton) m.findViewById(R.id.checkbox);
            return recyclerViewHolder;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class VerticalLineItemDecoration extends RecyclerView.ItemDecoration {
        public final Drawable mDivider;

        public VerticalLineItemDecoration(Context context) {
            TypedArray obtainStyledAttributes =
                    context.obtainStyledAttributes(new int[] {android.R.attr.listDivider});
            this.mDivider = obtainStyledAttributes.getDrawable(0);
            obtainStyledAttributes.recycle();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public final void getItemOffsets(
                Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            if (recyclerView.getAdapter() == null
                    || RecyclerView.getChildAdapterPosition(view)
                            == recyclerView.getAdapter().getItemCount() - 1) {
                return;
            }
            rect.bottom = this.mDivider.getIntrinsicHeight();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public final void onDraw(Canvas canvas, RecyclerView recyclerView) {
            int childCount = recyclerView.getChildCount();
            for (int i = 0; i < childCount - 1; i++) {
                View childAt = recyclerView.getChildAt(i);
                RecyclerView.LayoutParams layoutParams =
                        (RecyclerView.LayoutParams) childAt.getLayoutParams();
                int paddingLeft = recyclerView.getPaddingLeft();
                int width = recyclerView.getWidth() - recyclerView.getPaddingRight();
                int bottom =
                        childAt.getBottom()
                                + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
                this.mDivider.setBounds(
                        paddingLeft, bottom, width, this.mDivider.getIntrinsicHeight() + bottom);
                this.mDivider.draw(canvas);
            }
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return R.string.screen_timeout_docking;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return ScreenTimeoutActivity.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 4212;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_screen_timeout_docking_settings;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_display";
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.setChecked(
                    Settings.System.getInt(
                                    this.mContext.getContentResolver(),
                                    "dock_screen_off_timeout_enabled",
                                    0)
                            != 0);
            this.mSwitchBar.setSwitchBarText(R.string.switch_on_text, R.string.switch_off_text);
            this.mSwitchBar.addOnSwitchChangeListener(this);
            this.mSwitchBar.show();
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        Settings.System.putInt(getContentResolver(), "dock_screen_off_timeout_enabled", z ? 1 : 0);
        this.mSwitchBar.setChecked(z);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getContext();
        LayoutPreference layoutPreference =
                (LayoutPreference) findPreference("screen_timeout_docking_layout");
        if (layoutPreference != null) {
            LinearLayout linearLayout =
                    (LinearLayout)
                            layoutPreference.mRootView.findViewById(R.id.screen_timeout_container);
            this.mScreenTimeoutView = linearLayout;
            if (linearLayout != null) {
                linearLayout.semSetRoundedCorners(15);
                this.mScreenTimeoutView.semSetRoundedCornerColor(
                        15,
                        this.mContext
                                .getResources()
                                .getColor(R.color.sec_widget_round_and_bgcolor));
                this.mScreenTimeoutList =
                        (RecyclerView)
                                this.mScreenTimeoutView.findViewById(R.id.screen_timeout_list);
                this.mScreenTimeoutList.setLayoutManager(new LinearLayoutManager(1));
                this.mScreenTimeoutList.setHasFixedSize(true);
                this.mScreenTimeoutList.addItemDecoration(
                        new VerticalLineItemDecoration(this.mContext));
                this.mScreenTimeoutList.semSetRoundedCorners(15);
                this.mScreenTimeoutList.semSetRoundedCornerColor(
                        15,
                        this.mContext
                                .getResources()
                                .getColor(R.color.sec_widget_round_and_bgcolor));
                this.mScreenTimeoutList.seslSetFillBottomEnabled(true);
                this.mScreenTimeoutList.setItemAnimator(null);
            }
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        if (this.mScreenTimeoutObserver != null) {
            getContentResolver().unregisterContentObserver(this.mScreenTimeoutObserver);
            this.mScreenTimeoutObserver = null;
        }
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.samsung.android.settings.display.ScreenTimeoutDockingActivity$1] */
    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        setScreenTimeoutAdapter(
                Long.valueOf(
                        Settings.System.getLong(
                                this.mContext.getContentResolver(),
                                "dock_screen_off_timeout",
                                1800000L)));
        try {
            if (this.mScreenTimeoutObserver == null) {
                this.mScreenTimeoutObserver =
                        new ContentObserver(
                                new Handler()) { // from class:
                                                 // com.samsung.android.settings.display.ScreenTimeoutDockingActivity.1
                            @Override // android.database.ContentObserver
                            public final void onChange(boolean z, Uri uri) {
                                long j =
                                        Settings.System.getLong(
                                                ScreenTimeoutDockingActivity.this.mContext
                                                        .getContentResolver(),
                                                "dock_screen_off_timeout",
                                                1800000L);
                                ScreenTimeoutDockingActivity screenTimeoutDockingActivity =
                                        ScreenTimeoutDockingActivity.this;
                                if (j != screenTimeoutDockingActivity.mAdapter.mCurrentTimeout) {
                                    screenTimeoutDockingActivity.setScreenTimeoutAdapter(
                                            Long.valueOf(j));
                                }
                            }
                        };
                getContentResolver()
                        .registerContentObserver(
                                Settings.System.getUriFor("dock_screen_off_timeout"),
                                false,
                                this.mScreenTimeoutObserver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void setScreenTimeoutAdapter(Long l) {
        ScreenTimeoutAdapter screenTimeoutAdapter =
                new ScreenTimeoutAdapter(
                        SecDisplayUtils.getScreenTimeoutEntryandValue(this.mContext, l, 1),
                        SecDisplayUtils.getScreenTimeoutEntryandValue(this.mContext, l, 2),
                        l.longValue());
        this.mAdapter = screenTimeoutAdapter;
        this.mScreenTimeoutList.setAdapter(screenTimeoutAdapter);
        this.mScreenTimeoutList.setSelected(true);
        this.mScreenTimeoutList.setClickable(true);
    }
}
