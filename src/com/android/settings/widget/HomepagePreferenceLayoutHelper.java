package com.android.settings.widget;

import android.content.res.ColorStateList;
import android.view.View;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.samsung.android.settings.Trace;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class HomepagePreferenceLayoutHelper {
    public View mIcon;
    public TextView mSummary;
    public final ColorStateList mSummaryTextPrimaryColor;
    public View mText;
    public TextView mTitle;
    public boolean mIconVisible = true;
    public int mIconPaddingStart = -1;
    public int mTextPaddingStart = -1;
    public ColorStateList mTitleTextColor = null;
    public ColorStateList mSummaryTextColor = null;
    public boolean mUseSummaryPrimaryColor = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface HomepagePreferenceLayout {
        HomepagePreferenceLayoutHelper getHelper();
    }

    public HomepagePreferenceLayoutHelper(Preference preference) {
        this.mSummaryTextPrimaryColor = null;
        Trace.beginSection("HomepagePreferenceLayoutHelper");
        if (preference.getLayoutResource() == R.layout.sesl_preference) {
            preference.setLayoutResource(R.layout.sec_homepage_preference);
        }
        this.mSummaryTextPrimaryColor =
                preference
                        .getContext()
                        .getColorStateList(R.color.sec_preference_summary_primary_color);
        Trace.endSection();
    }

    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        Trace.beginSection("HomepagePreferenceLayoutHelper#onBindViewHolder");
        preferenceViewHolder.itemView.setTag(R.id.preference_highlightable, Boolean.TRUE);
        this.mIcon = preferenceViewHolder.findViewById(R.id.icon_frame);
        this.mText = preferenceViewHolder.findViewById(R.id.text_frame);
        this.mTitle = (TextView) preferenceViewHolder.findViewById(android.R.id.title);
        this.mSummary = (TextView) preferenceViewHolder.findViewById(android.R.id.summary);
        boolean z = this.mIconVisible;
        this.mIconVisible = z;
        View view = this.mIcon;
        if (view != null) {
            view.setVisibility(z ? 0 : 8);
        }
        int i = this.mIconPaddingStart;
        this.mIconPaddingStart = i;
        View view2 = this.mIcon;
        if (view2 != null && i >= 0) {
            view2.setPaddingRelative(
                    i,
                    view2.getPaddingTop(),
                    this.mIcon.getPaddingEnd(),
                    this.mIcon.getPaddingBottom());
        }
        int i2 = this.mTextPaddingStart;
        this.mTextPaddingStart = i2;
        View view3 = this.mText;
        if (view3 != null && i2 >= 0) {
            view3.setPaddingRelative(
                    i2,
                    view3.getPaddingTop(),
                    this.mText.getPaddingEnd(),
                    this.mText.getPaddingBottom());
        }
        View view4 = this.mIcon;
        if (view4 != null) {
            view4.setAlpha(view4.isEnabled() ? 1.0f : 0.4f);
        }
        ColorStateList colorStateList = this.mTitleTextColor;
        this.mTitleTextColor = colorStateList;
        TextView textView = this.mTitle;
        if (textView != null && colorStateList != null) {
            textView.setTextColor(colorStateList);
        }
        ColorStateList colorStateList2 = this.mSummaryTextColor;
        this.mSummaryTextColor = colorStateList2;
        TextView textView2 = this.mSummary;
        if (textView2 != null && colorStateList2 != null) {
            textView2.setTextColor(colorStateList2);
        }
        if (this.mUseSummaryPrimaryColor) {
            this.mSummary.setTextColor(this.mSummaryTextPrimaryColor);
        }
        this.mSummary.setLineBreakWordStyle(0);
        Trace.endSection();
    }
}
