package com.android.settings.widget;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceGroupAdapter;
import androidx.preference.PreferenceViewHolder;

import com.android.settingslib.Utils;

import com.samsung.android.settings.Rune;
import com.samsung.android.settingslib.SettingsLibRune;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class HighlightablePreferenceGroupAdapter extends PreferenceGroupAdapter {
    static final long DELAY_COLLAPSE_DURATION_MILLIS = 300;
    static final long DELAY_HIGHLIGHT_DURATION_MILLIS = 600;
    static final long DELAY_HIGHLIGHT_DURATION_MILLIS_A11Y = 300;
    public final Context mContext;
    public ArrayList mDynamicKeyList;
    boolean mFadeInAnimated;
    final int mHighlightColor;
    public final String mHighlightKey;
    public int mHighlightPosition;
    public boolean mHighlightRequested;
    public final int mNormalBackgroundRes;

    public HighlightablePreferenceGroupAdapter(
            PreferenceGroup preferenceGroup, String str, boolean z) {
        super(preferenceGroup);
        this.mHighlightPosition = -1;
        this.mDynamicKeyList = new ArrayList();
        this.mHighlightKey = str;
        this.mHighlightRequested = z;
        Context context = preferenceGroup.getContext();
        this.mContext = context;
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
        this.mNormalBackgroundRes = typedValue.resourceId;
        this.mHighlightColor =
                context.getColor(com.android.settings.R.color.preference_highlight_color);
    }

    @Override // androidx.preference.PreferenceGroupAdapter
    public final boolean isSwitchLayout(Preference preference) {
        if (preference.getLayoutResource()
                        == com.android.settings.R.layout.sec_restricted_switch_preference
                && preference.getWidgetLayoutResource()
                        == com.android.settings.R.layout.sesl_preference_widget_switch) {
            return true;
        }
        if (preference.getLayoutResource()
                        == com.android.settings.R.layout.sec_restricted_switch_preference_screen
                && preference.getWidgetLayoutResource()
                        == com.android.settings.R.layout
                                .sesl_switch_preference_screen_widget_divider) {
            return true;
        }
        return super.isSwitchLayout(preference);
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

    public final void removeHighlightBackground(
            final PreferenceViewHolder preferenceViewHolder, boolean z) {
        final View view = preferenceViewHolder.itemView;
        int i = this.mNormalBackgroundRes;
        if (!z) {
            view.setTag(com.android.settings.R.id.preference_highlighted, Boolean.FALSE);
            view.setBackgroundResource(i);
            Log.d(
                    "HighlightableAdapter",
                    "RemoveHighlight: No animation requested - setting normal background");
        } else {
            if (!Boolean.TRUE.equals(
                    view.getTag(com.android.settings.R.id.preference_highlighted))) {
                Log.d("HighlightableAdapter", "RemoveHighlight: Not highlighted - skipping");
                return;
            }
            int i2 = this.mHighlightColor;
            view.setTag(com.android.settings.R.id.preference_highlighted, Boolean.FALSE);
            ValueAnimator ofObject =
                    ValueAnimator.ofObject(
                            new ArgbEvaluator(), Integer.valueOf(i2), Integer.valueOf(i));
            ofObject.setDuration(500L);
            ofObject.addUpdateListener(
                    new HighlightablePreferenceGroupAdapter$$ExternalSyntheticLambda4(view, 0));
            ofObject.addListener(
                    new AnimatorListenerAdapter() { // from class:
                                                    // com.android.settings.widget.HighlightablePreferenceGroupAdapter.1
                        @Override // android.animation.AnimatorListenerAdapter,
                                  // android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            view.setBackgroundResource(
                                    HighlightablePreferenceGroupAdapter.this.mNormalBackgroundRes);
                            preferenceViewHolder.setIsRecyclable(true);
                        }
                    });
            ofObject.start();
            Log.d("HighlightableAdapter", "Starting fade out animation");
        }
    }

    public void requestRemoveHighlightDelayed(PreferenceViewHolder preferenceViewHolder) {
        preferenceViewHolder.itemView.postDelayed(
                new HighlightablePreferenceGroupAdapter$$ExternalSyntheticLambda0(
                        this, preferenceViewHolder, 0),
                15000L);
    }

    public void updateBackground(PreferenceViewHolder preferenceViewHolder, int i) {
        String str;
        View view = preferenceViewHolder.itemView;
        if (i != this.mHighlightPosition
                || (str = this.mHighlightKey) == null
                || !TextUtils.equals(str, getItem(i).getKey())
                || !view.isShown()) {
            if (Boolean.TRUE.equals(
                    view.getTag(com.android.settings.R.id.preference_highlighted))) {
                removeHighlightBackground(preferenceViewHolder, false);
                return;
            }
            return;
        }
        view.requestAccessibilityFocus();
        boolean z = !this.mFadeInAnimated;
        View view2 = preferenceViewHolder.itemView;
        view2.setTag(com.android.settings.R.id.preference_highlighted, Boolean.TRUE);
        if (!z) {
            view2.setBackgroundColor(this.mHighlightColor);
            Log.d(
                    "HighlightableAdapter",
                    "AddHighlight: Not animation requested - setting highlight background");
            requestRemoveHighlightDelayed(preferenceViewHolder);
            return;
        }
        this.mFadeInAnimated = true;
        ValueAnimator ofObject =
                ValueAnimator.ofObject(
                        new ArgbEvaluator(),
                        Integer.valueOf(this.mNormalBackgroundRes),
                        Integer.valueOf(this.mHighlightColor));
        ofObject.setDuration(200L);
        ofObject.addUpdateListener(
                new HighlightablePreferenceGroupAdapter$$ExternalSyntheticLambda4(view2, 1));
        ofObject.setRepeatMode(2);
        ofObject.setRepeatCount(4);
        ofObject.start();
        Log.d("HighlightableAdapter", "AddHighlight: starting fade in animation");
        preferenceViewHolder.setIsRecyclable(false);
        requestRemoveHighlightDelayed(preferenceViewHolder);
    }

    @Override // androidx.preference.PreferenceGroupAdapter,
              // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder, int i) {
        super.onBindViewHolder(preferenceViewHolder, i);
        final View view = preferenceViewHolder.itemView;
        Preference item = getItem(i);
        if (item != null) {
            view.setTag(com.android.settings.R.id.preference_key, item.getKey());
        }
        if (i == this.mHighlightPosition) {
            view.postDelayed(
                    new HighlightablePreferenceGroupAdapter$$ExternalSyntheticLambda0(
                            this, view, 1),
                    900L);
            view.postDelayed(
                    new Runnable() { // from class:
                                     // com.android.settings.widget.HighlightablePreferenceGroupAdapter$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            view.setPressed(false);
                        }
                    },
                    1050L);
        }
        if (SettingsLibRune.MENU_SHOW_EXTERNAL_CODE && Utils.isVisibleExternalCode(this.mContext)) {
            Iterator it = this.mDynamicKeyList.iterator();
            while (it.hasNext()) {
                if (i == getPreferenceAdapterPosition((String) it.next())) {
                    preferenceViewHolder.itemView.setBackgroundColor(-256);
                }
            }
        }
    }
}
