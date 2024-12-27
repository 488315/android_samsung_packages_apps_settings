package com.android.settings.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R$styleable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FixedLineSummaryPreference extends Preference {
    public final int mSummaryLineCount;

    public FixedLineSummaryPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(
                        attributeSet, R$styleable.FixedLineSummaryPreference, 0, 0);
        if (obtainStyledAttributes.hasValue(0)) {
            this.mSummaryLineCount = obtainStyledAttributes.getInteger(0, 1);
        } else {
            this.mSummaryLineCount = 1;
        }
        obtainStyledAttributes.recycle();
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.summary);
        if (textView != null) {
            textView.setMinLines(this.mSummaryLineCount);
            textView.setMaxLines(this.mSummaryLineCount);
            textView.setEllipsize(TextUtils.TruncateAt.END);
        }
    }
}
