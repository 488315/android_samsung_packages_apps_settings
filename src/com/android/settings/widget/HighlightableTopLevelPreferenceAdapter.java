package com.android.settings.widget;

import android.R;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceGroupAdapter;
import androidx.preference.PreferenceViewHolder;
import androidx.recyclerview.widget.RecyclerView;
import androidx.window.embedding.ActivityEmbeddingController;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.core.RoundCornerPreferenceAdapter$1;
import com.android.settings.homepage.SettingsHomepageActivity;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.Trace;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class HighlightableTopLevelPreferenceAdapter extends PreferenceGroupAdapter {
    public final Handler mHandler;
    public String mHighlightKey;
    public boolean mHighlightNeeded;
    public int mHighlightPosition;
    public final String mHighlightableBackgroundContentDescription;
    public final int mHighlightableBackgroundRes;
    public final String mHighlightedBackgroundContentDescription;
    public final int mHighlightedBackgroundRes;
    public final SettingsHomepageActivity mHomepageActivity;
    public final int mNormalBackgroundRes;
    public final RecyclerView mRecyclerView;
    public int mScrollPosition;
    public boolean mScrolled;
    public final RoundCornerPreferenceAdapter$1 mSyncRunnable;
    public final SparseArray mViewHolders;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.core.RoundCornerPreferenceAdapter$1] */
    public HighlightableTopLevelPreferenceAdapter(
            SettingsHomepageActivity settingsHomepageActivity,
            PreferenceGroup preferenceGroup,
            RecyclerView recyclerView,
            String str,
            boolean z) {
        super(preferenceGroup);
        this.mSyncRunnable =
                new Runnable() { // from class:
                                 // com.android.settings.core.RoundCornerPreferenceAdapter$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        HighlightableTopLevelPreferenceAdapter.this.getClass();
                    }
                };
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mHighlightPosition = -1;
        this.mScrollPosition = -1;
        Trace.beginSection("HighlightableTopLevelPreferenceAdapter");
        this.mRecyclerView = recyclerView;
        this.mHighlightKey = str;
        this.mScrolled = !z;
        this.mViewHolders = new SparseArray();
        this.mHomepageActivity = settingsHomepageActivity;
        Context context = preferenceGroup.getContext();
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.listChoiceBackgroundIndicator, typedValue, true);
        this.mNormalBackgroundRes = typedValue.resourceId;
        this.mHighlightableBackgroundRes =
                com.android.settings.R.drawable.sec_highlightable_preference_background;
        this.mHighlightedBackgroundRes =
                com.android.settings.R.drawable.sec_highlighted_preference_background;
        this.mHighlightableBackgroundContentDescription =
                context.getString(com.android.settings.R.string.not_selected);
        this.mHighlightedBackgroundContentDescription =
                context.getString(com.android.settings.R.string.selected);
        Trace.endSection();
    }

    public final void highlightPreference(String str, boolean z) {
        this.mHighlightKey = str;
        this.mScrolled = !z;
        requestHighlight$2();
    }

    @Override // androidx.preference.PreferenceGroupAdapter,
              // androidx.preference.Preference.OnPreferenceChangeInternalListener
    public final void onPreferenceHierarchyChange(Preference preference) {
        super.onPreferenceHierarchyChange(preference);
        Handler handler = this.mHandler;
        RoundCornerPreferenceAdapter$1 roundCornerPreferenceAdapter$1 = this.mSyncRunnable;
        handler.removeCallbacks(roundCornerPreferenceAdapter$1);
        handler.post(roundCornerPreferenceAdapter$1);
    }

    @Override // androidx.preference.PreferenceGroupAdapter,
              // androidx.preference.Preference.OnPreferenceChangeInternalListener
    public final void onPreferenceVisibilityChange(Preference preference) {
        if ((preference instanceof HomepagePreference)
                && Rune.supportGoodSettings(preference.getContext())) {
            preference.setVisible(
                    ((HomepagePreference) preference).getPreferenceHelper().isVisible()
                            && preference.isVisible());
        }
        onPreferenceHierarchyChange(preference);
    }

    public final void removeHighlightBackground(PreferenceViewHolder preferenceViewHolder) {
        View view = preferenceViewHolder.itemView;
        if (Boolean.TRUE.equals(view.getTag(com.android.settings.R.id.preference_highlightable))) {
            SettingsHomepageActivity settingsHomepageActivity = this.mHomepageActivity;
            if (ActivityEmbeddingController.getInstance(settingsHomepageActivity)
                    .isActivityEmbedded(settingsHomepageActivity)) {
                view.setBackgroundResource(this.mHighlightableBackgroundRes);
                view.setStateDescription(this.mHighlightableBackgroundContentDescription);
            } else {
                view.setBackgroundResource(this.mNormalBackgroundRes);
                view.setStateDescription(null);
            }
        }
    }

    public final void requestHighlight$2() {
        if (this.mRecyclerView == null) {
            return;
        }
        int i = this.mHighlightPosition;
        if (TextUtils.isEmpty(this.mHighlightKey)) {
            this.mHighlightPosition = -1;
            this.mScrolled = true;
            if (i >= 0) {
                notifyItemChanged(i);
                return;
            }
            return;
        }
        int preferenceAdapterPosition = getPreferenceAdapterPosition(this.mHighlightKey);
        if (preferenceAdapterPosition < 0) {
            return;
        }
        SettingsHomepageActivity settingsHomepageActivity = this.mHomepageActivity;
        boolean isActivityEmbedded =
                ActivityEmbeddingController.getInstance(settingsHomepageActivity)
                        .isActivityEmbedded(settingsHomepageActivity);
        if (isActivityEmbedded) {
            this.mScrollPosition = preferenceAdapterPosition;
            scroll();
        }
        if (isActivityEmbedded != this.mHighlightNeeded) {
            AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                    "Highlight needed change: ",
                    "HighlightableTopLevelAdapter",
                    isActivityEmbedded);
            this.mHighlightNeeded = isActivityEmbedded;
            this.mHighlightPosition = preferenceAdapterPosition;
            notifyItemChanged(preferenceAdapterPosition);
            if (isActivityEmbedded || i < 0) {
                return;
            }
            PreferenceViewHolder preferenceViewHolder =
                    (PreferenceViewHolder) this.mViewHolders.get(i);
            if (preferenceViewHolder != null) {
                removeHighlightBackground(preferenceViewHolder);
            }
            notifyItemChanged(i);
            return;
        }
        if (preferenceAdapterPosition == this.mHighlightPosition) {
            return;
        }
        this.mHighlightPosition = preferenceAdapterPosition;
        Log.d(
                "HighlightableTopLevelAdapter",
                "Request highlight position " + preferenceAdapterPosition);
        Log.d("HighlightableTopLevelAdapter", "Is highlight needed: " + isActivityEmbedded);
        if (isActivityEmbedded) {
            notifyDataSetChanged();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0044, code lost:

       if (((r2.getHeight() - r2.seslGetCollapsedHeight()) + r1.getBottom()) > r0.getBottom()) goto L16;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void scroll() {
        /*
            r5 = this;
            boolean r0 = r5.mScrolled
            if (r0 != 0) goto L4f
            int r0 = r5.mScrollPosition
            if (r0 >= 0) goto L9
            goto L4f
        L9:
            androidx.recyclerview.widget.RecyclerView r0 = r5.mRecyclerView
            int r1 = r0.getChildCount()
            if (r1 != 0) goto L1c
            com.android.settings.widget.HighlightableTopLevelPreferenceAdapter$$ExternalSyntheticLambda0 r1 = new com.android.settings.widget.HighlightableTopLevelPreferenceAdapter$$ExternalSyntheticLambda0
            r1.<init>()
            r2 = 100
            r0.postDelayed(r1, r2)
            return
        L1c:
            r1 = 1
            r5.mScrolled = r1
            int r1 = r5.mScrollPosition
            android.view.View r1 = r0.getChildAt(r1)
            com.android.settings.homepage.SettingsHomepageActivity r2 = r5.mHomepageActivity
            com.google.android.material.appbar.AppBarLayout r2 = r2.mAppBarLayout
            if (r2 == 0) goto L4a
            if (r1 == 0) goto L46
            int r1 = r1.getBottom()
            float r1 = (float) r1
            int r3 = r2.getHeight()
            float r3 = (float) r3
            float r4 = r2.seslGetCollapsedHeight()
            float r3 = r3 - r4
            float r3 = r3 + r1
            int r1 = r0.getBottom()
            float r1 = (float) r1
            int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r1 <= 0) goto L4a
        L46:
            r1 = 0
            r2.setExpanded(r1)
        L4a:
            int r5 = r5.mScrollPosition
            r0.scrollToPosition(r5)
        L4f:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.widget.HighlightableTopLevelPreferenceAdapter.scroll():void");
    }

    public void updateBackground(PreferenceViewHolder preferenceViewHolder, int i) {
        String str;
        SettingsHomepageActivity settingsHomepageActivity = this.mHomepageActivity;
        if (!ActivityEmbeddingController.getInstance(settingsHomepageActivity)
                .isActivityEmbedded(settingsHomepageActivity)) {
            removeHighlightBackground(preferenceViewHolder);
            return;
        }
        if (i != this.mHighlightPosition
                || (str = this.mHighlightKey) == null
                || !TextUtils.equals(str, getItem(i).getKey())) {
            removeHighlightBackground(preferenceViewHolder);
            return;
        }
        View view = preferenceViewHolder.itemView;
        if (Boolean.TRUE.equals(view.getTag(com.android.settings.R.id.preference_highlightable))) {
            view.setBackgroundResource(this.mHighlightedBackgroundRes);
            view.setStateDescription(this.mHighlightedBackgroundContentDescription);
        }
    }

    @Override // androidx.preference.PreferenceGroupAdapter,
              // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder, int i) {
        Trace.beginSection("HightlightableAdapter#onBindViewHolder");
        super.onBindViewHolder(preferenceViewHolder, i);
        this.mViewHolders.put(i, preferenceViewHolder);
        updateBackground(preferenceViewHolder, i);
        Trace.endSection();
    }

    @Override // androidx.preference.PreferenceGroupAdapter,
              // androidx.recyclerview.widget.RecyclerView.Adapter
    public final PreferenceViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Trace.beginSection("HightlightableAdapter#onCreateViewHolder");
        PreferenceViewHolder onCreateViewHolder = super.onCreateViewHolder(viewGroup, i);
        Trace.endSection();
        return onCreateViewHolder;
    }
}
