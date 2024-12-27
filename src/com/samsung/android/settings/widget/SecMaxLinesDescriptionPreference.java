package com.samsung.android.settings.widget;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecMaxLinesDescriptionPreference extends Preference {
    public TextView summaryView;

    public SecMaxLinesDescriptionPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.summary);
        this.summaryView = textView;
        textView.setMaxLines(Preference.DEFAULT_ORDER);
        this.summaryView.setEllipsize(null);
    }
}
