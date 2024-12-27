package com.samsung.android.settings.lockscreen;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.secutil.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentActivity;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.Utils;
import com.android.settings.core.InstrumentedPreferenceFragment;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.logging.SALogging;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LockAfterTimeoutActivity extends SettingsActivity {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class LockAfterTimeoutFragment extends InstrumentedPreferenceFragment {
        public RadioAdapter mAdapter;
        public FragmentActivity mContext;
        public RecyclerView mListView;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class RadioAdapter extends RecyclerView.Adapter {
            public final boolean mChecked;
            public final Context mContext;
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

            public RadioAdapter(
                    FragmentActivity fragmentActivity,
                    CharSequence[] charSequenceArr,
                    CharSequence[] charSequenceArr2,
                    long j) {
                int i = 0;
                this.mChecked = false;
                this.mContext = fragmentActivity;
                this.mEntries = charSequenceArr;
                this.mValues = charSequenceArr2;
                this.mCurrentTimeout = j;
                if (charSequenceArr == null) {
                    Log.e("LockAfterTimeoutActivity", "addScreenTimeoutListView failed");
                    return;
                }
                while (true) {
                    CharSequence[] charSequenceArr3 = this.mEntries;
                    if (i >= charSequenceArr3.length) {
                        break;
                    }
                    CharSequence charSequence = charSequenceArr3[i];
                    CharSequence charSequence2 = this.mValues[i];
                    ScreenTimeoutList screenTimeoutList = new ScreenTimeoutList();
                    screenTimeoutList.mListItemTitle = charSequence.toString();
                    screenTimeoutList.mListItemValue = charSequence2.toString();
                    if (charSequence2.equals(String.valueOf(this.mCurrentTimeout))) {
                        screenTimeoutList.mIsChecked = true;
                        this.mChecked = true;
                    }
                    ((ArrayList) this.mListItems).add(screenTimeoutList);
                    notifyDataSetChanged();
                    i++;
                }
                if (this.mChecked) {
                    return;
                }
                ((ScreenTimeoutList)
                                        ((ArrayList) this.mListItems)
                                                .get(((ArrayList) this.mListItems).size() - 1))
                                .mIsChecked =
                        true;
                this.mChecked = true;
                notifyDataSetChanged();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public final int getItemCount() {
                return ((ArrayList) this.mListItems).size();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public final void onBindViewHolder(
                    final RecyclerView.ViewHolder viewHolder, final int i) {
                if (viewHolder instanceof RecyclerViewHolder) {
                    RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) viewHolder;
                    recyclerViewHolder.mTitleView.setText(
                            ((ScreenTimeoutList) ((ArrayList) this.mListItems).get(i))
                                    .mListItemTitle);
                    recyclerViewHolder.mCheckBoxView.setChecked(
                            ((ScreenTimeoutList) ((ArrayList) this.mListItems).get(i)).mIsChecked);
                    if (((ScreenTimeoutList) ((ArrayList) this.mListItems).get(i)).mIsChecked) {
                        recyclerViewHolder.mTitleView.setStateDescription(
                                this.mContext.getString(R.string.sec_wifi_checked_tss));
                    } else {
                        recyclerViewHolder.mTitleView.setStateDescription(
                                this.mContext.getString(R.string.sec_wifi_unchecked_tss));
                    }
                    viewHolder.itemView.setOnClickListener(
                            new View
                                    .OnClickListener() { // from class:
                                                         // com.samsung.android.settings.lockscreen.LockAfterTimeoutActivity.LockAfterTimeoutFragment.RadioAdapter.1
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    RadioAdapter radioAdapter = RadioAdapter.this;
                                    RecyclerView.ViewHolder viewHolder2 = viewHolder;
                                    int i2 = i;
                                    for (int i3 = 0; i3 < radioAdapter.mEntries.length; i3++) {
                                        if (i3 == i2) {
                                            ((ScreenTimeoutList)
                                                                    ((ArrayList)
                                                                                    radioAdapter
                                                                                            .mListItems)
                                                                            .get(i3))
                                                            .mIsChecked =
                                                    true;
                                            ((RecyclerViewHolder) viewHolder2)
                                                    .mCheckBoxView.setChecked(
                                                            ((ScreenTimeoutList)
                                                                            ((ArrayList)
                                                                                            radioAdapter
                                                                                                    .mListItems)
                                                                                    .get(i3))
                                                                    .mIsChecked);
                                            radioAdapter.mCurrentTimeout =
                                                    Long.parseLong(
                                                            ((ScreenTimeoutList)
                                                                            ((ArrayList)
                                                                                            radioAdapter
                                                                                                    .mListItems)
                                                                                    .get(i3))
                                                                    .mListItemValue);
                                        } else {
                                            ((ScreenTimeoutList)
                                                                    ((ArrayList)
                                                                                    radioAdapter
                                                                                            .mListItems)
                                                                            .get(i3))
                                                            .mIsChecked =
                                                    false;
                                        }
                                        radioAdapter.notifyItemChanged(i3);
                                    }
                                    int parseInt =
                                            Integer.parseInt(
                                                    ((ScreenTimeoutList)
                                                                    ((ArrayList)
                                                                                    RadioAdapter
                                                                                            .this
                                                                                            .mListItems)
                                                                            .get(i))
                                                            .mListItemValue);
                                    long j = parseInt;
                                    String[] stringArray =
                                            LockAfterTimeoutFragment.this
                                                    .getResources()
                                                    .getStringArray(
                                                            R.array.lock_after_timeout_values);
                                    if (stringArray != null) {
                                        for (String str : stringArray) {
                                            if (Long.valueOf(str).longValue() == j) {
                                                Settings.Secure.putInt(
                                                        LockAfterTimeoutFragment.this
                                                                .getActivity()
                                                                .getContentResolver(),
                                                        "lock_after_timeout_rollback",
                                                        parseInt);
                                                break;
                                            }
                                        }
                                    }
                                    parseInt = 1800001;
                                    try {
                                        SALogging.insertSALog(
                                                String.valueOf(4432),
                                                "LSE4435",
                                                String.valueOf(i + 97));
                                        Settings.Secure.putInt(
                                                LockAfterTimeoutFragment.this
                                                        .getActivity()
                                                        .getContentResolver(),
                                                "lock_screen_lock_after_timeout",
                                                parseInt);
                                    } catch (NumberFormatException e) {
                                        Log.e(
                                                "SecuritySettings",
                                                "could not persist lockAfter timeout setting",
                                                e);
                                    }
                                    LockAfterTimeoutFragment.this.getActivity().finish();
                                }
                            });
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                View m =
                        MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                                viewGroup,
                                R.layout.sec_lock_after_timeout_list_item,
                                viewGroup,
                                false);
                RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(m);
                recyclerViewHolder.mTitleView = (TextView) m.findViewById(R.id.timeout_title);
                recyclerViewHolder.mCheckBoxView = (RadioButton) m.findViewById(R.id.checkbox);
                return recyclerViewHolder;
            }
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class VerticalLineItemDecoration extends RecyclerView.ItemDecoration {
            public final Drawable mDivider;

            public VerticalLineItemDecoration(FragmentActivity fragmentActivity) {
                TypedArray obtainStyledAttributes =
                        fragmentActivity.obtainStyledAttributes(
                                new int[] {android.R.attr.listDivider});
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
                Resources resources = LockAfterTimeoutFragment.this.mContext.getResources();
                int dimensionPixelSize =
                        resources.getDimensionPixelSize(
                                        R.dimen
                                                .sec_widget_list_checkbox_width_for_divider_padding_inset)
                                + resources.getDimensionPixelSize(
                                        R.dimen.sec_widget_list_checkbox_width_for_divider_inset);
                for (int i = 0; i < childCount - 1; i++) {
                    View childAt = recyclerView.getChildAt(i);
                    RecyclerView.LayoutParams layoutParams =
                            (RecyclerView.LayoutParams) childAt.getLayoutParams();
                    int paddingLeft = recyclerView.getPaddingLeft() + dimensionPixelSize;
                    int width = recyclerView.getWidth() - recyclerView.getPaddingRight();
                    int bottom =
                            childAt.getBottom()
                                    + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
                    int intrinsicHeight = this.mDivider.getIntrinsicHeight() + bottom;
                    if (recyclerView.getLayoutDirection() == 1) {
                        paddingLeft = recyclerView.getPaddingLeft();
                        width =
                                (recyclerView.getWidth() - recyclerView.getPaddingRight())
                                        - dimensionPixelSize;
                    }
                    this.mDivider.setBounds(paddingLeft, bottom, width, intrinsicHeight);
                    this.mDivider.draw(canvas);
                }
            }
        }

        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 4432;
        }

        @Override // com.android.settings.core.InstrumentedPreferenceFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            this.mContext = getActivity();
        }

        @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final View onCreateView(
                LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            View inflate =
                    layoutInflater.inflate(R.layout.sec_lock_after_timeout, viewGroup, false);
            RecyclerView recyclerView =
                    (RecyclerView) inflate.findViewById(R.id.lock_after_timeout_list);
            this.mListView = recyclerView;
            if (recyclerView != null) {
                recyclerView.semSetRoundedCorners(15);
                this.mListView.semSetRoundedCornerColor(
                        15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(1);
                this.mListView.addItemDecoration(new VerticalLineItemDecoration(this.mContext));
                this.mListView.setLayoutManager(linearLayoutManager);
                this.mListView.setHasFixedSize(true);
                this.mListView.seslSetFillBottomEnabled(true);
                this.mListView.setItemAnimator(null);
            }
            int listHorizontalPadding = Utils.getListHorizontalPadding(this.mContext);
            NestedScrollView nestedScrollView =
                    (NestedScrollView) inflate.findViewById(R.id.nested_scroll_view);
            nestedScrollView.setPaddingRelative(listHorizontalPadding, 0, listHorizontalPadding, 0);
            nestedScrollView.semSetRoundedCorners(15);
            nestedScrollView.semSetRoundedCornerColor(
                    15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
            nestedScrollView.semSetRoundedCornerOffset(listHorizontalPadding);
            return inflate;
        }

        @Override // com.android.settings.core.InstrumentedPreferenceFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.fragment.app.Fragment
        public final void onResume() {
            long j;
            String str;
            super.onResume();
            long j2 =
                    Settings.Secure.getLong(
                            getActivity().getContentResolver(),
                            "lock_screen_lock_after_timeout",
                            5000L);
            DevicePolicyManager devicePolicyManager =
                    (DevicePolicyManager) this.mContext.getSystemService("device_policy");
            long maximumTimeToLock =
                    devicePolicyManager != null
                            ? devicePolicyManager.getMaximumTimeToLock(null)
                            : 0L;
            long max =
                    Math.max(
                            0,
                            Settings.System.getInt(
                                    getActivity().getContentResolver(), "screen_off_timeout", 0));
            long max2 = Math.max(0L, maximumTimeToLock - max);
            StringBuilder m =
                    SnapshotStateObserver$$ExternalSyntheticOutline0.m(
                            max2, "maxTimeout before Knox is = ", ", adminTimeout : ");
            m.append(maximumTimeToLock);
            m.append(", displayTimeout : ");
            m.append(max);
            Log.d("LockAfterTimeoutActivity", m.toString());
            int enterprisePolicyEnabledInt =
                    Utils.getEnterprisePolicyEnabledInt(
                            this.mContext, "getPasswordLockDelay", null);
            Log.d(
                    "LockAfterTimeoutActivity",
                    "getPasswordLockDelay - "
                            + enterprisePolicyEnabledInt
                            + ", adminTimeout - "
                            + maximumTimeToLock);
            if (enterprisePolicyEnabledInt >= 0) {
                max2 =
                        maximumTimeToLock == 0
                                ? enterprisePolicyEnabledInt * 1000
                                : Math.min(enterprisePolicyEnabledInt * 1000, max2);
            }
            Log.d("LockAfterTimeoutActivity", "maxTimeout after Knox is = " + max2);
            String[] stringArray =
                    this.mContext.getResources().getStringArray(R.array.lock_after_timeout_entries);
            String[] stringArray2 =
                    this.mContext.getResources().getStringArray(R.array.lock_after_timeout_values);
            if (maximumTimeToLock > 0 || enterprisePolicyEnabledInt >= 0) {
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                for (int i = 0; i < stringArray2.length; i++) {
                    if (Long.valueOf(stringArray2[i].toString()).longValue() <= max2) {
                        arrayList.add(stringArray[i]);
                        arrayList2.add(stringArray2[i]);
                    }
                }
                Log.secD("LockAfterTimeoutActivity", "revisedValues.size() : " + arrayList2.size());
                long longValue =
                        max2
                                - Long.valueOf(
                                                ((CharSequence)
                                                                arrayList2.get(
                                                                        arrayList2.size() - 1))
                                                        .toString())
                                        .longValue();
                Log.secD("LockAfterTimeoutActivity", "last_timeout : " + longValue);
                if (longValue <= 0
                        || max2
                                >= Long.valueOf(stringArray2[stringArray2.length - 1].toString())
                                        .longValue()) {
                    j = j2;
                } else {
                    long j3 = max2 / 1000;
                    j = j2;
                    long j4 = j3 / 60;
                    long j5 = j3 % 60;
                    if (j4 > 0) {
                        int i2 = (int) j4;
                        str =
                                String.format(
                                        getResources()
                                                .getQuantityString(
                                                        R.plurals.lock_timeout_minutes, i2),
                                        Integer.valueOf(i2));
                    } else {
                        str = ApnSettings.MVNO_NONE;
                    }
                    if (j4 > 0 && j5 > 0) {
                        str = str.concat(" ");
                    }
                    if (j5 > 0) {
                        StringBuilder m2 =
                                EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0
                                        .m(str);
                        int i3 = (int) j5;
                        m2.append(
                                String.format(
                                        getResources()
                                                .getQuantityString(
                                                        R.plurals.lock_timeout_seconds, i3),
                                        Integer.valueOf(i3)));
                        str = m2.toString();
                    }
                    Log.secD("LockAfterTimeoutActivity", "getTimeoutNewEntry : " + str);
                    arrayList.add(getString(R.string.lock_timeout_max, str));
                    arrayList2.add(Long.toString(max2));
                }
                this.mAdapter =
                        new RadioAdapter(
                                this.mContext,
                                (CharSequence[])
                                        arrayList.toArray(new CharSequence[arrayList.size()]),
                                (CharSequence[])
                                        arrayList2.toArray(new CharSequence[arrayList2.size()]),
                                j);
            } else {
                this.mAdapter = new RadioAdapter(this.mContext, stringArray, stringArray2, j2);
            }
            this.mListView.setAdapter(this.mAdapter);
            this.mListView.setSelected(true);
            this.mListView.setClickable(true);
        }
    }

    @Override // com.android.settings.SettingsActivity, android.app.Activity
    public final Intent getIntent() {
        Intent intent = new Intent(super.getIntent());
        intent.putExtra(":settings:show_fragment", LockAfterTimeoutFragment.class.getName());
        intent.putExtra(":android:no_headers", true);
        return intent;
    }

    @Override // com.android.settings.SettingsActivity
    public final boolean isValidFragment(String str) {
        return LockAfterTimeoutFragment.class.getName().equals(str);
    }

    @Override // com.android.settings.SettingsActivity,
              // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getIntent();
        findViewById(R.id.content_parent).setFitsSystemWindows(false);
        setTitle(R.string.sec_secured_lock_settings_lock_after_timeout_title);
        View findViewById = findViewById(R.id.round_corner);
        if (findViewById != null) {
            int listHorizontalPadding = Utils.getListHorizontalPadding(getApplicationContext());
            CoordinatorLayout.LayoutParams layoutParams =
                    (CoordinatorLayout.LayoutParams) findViewById.getLayoutParams();
            layoutParams.setMarginsRelative(listHorizontalPadding, 0, listHorizontalPadding, 0);
            findViewById.setLayoutParams(layoutParams);
            findViewById.semSetRoundedCorners(12);
            findViewById.semSetRoundedCornerColor(
                    12, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        }
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity, android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        finish();
        return true;
    }

    @Override // com.android.settings.SettingsActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        if (LockUtils.isInMultiWindow(this)) {
            Toast.makeText(
                            this,
                            getString(R.string.lock_screen_doesnt_support_multi_window_text),
                            0)
                    .show();
            finish();
        }
    }
}
