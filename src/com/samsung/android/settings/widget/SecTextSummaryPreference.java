package com.samsung.android.settings.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecTextSummaryPreference extends Preference {
    public SecTextSummaryPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setLayoutResource(R.layout.sec_text_summary_layout);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TextView textView =
                (TextView) preferenceViewHolder.findViewById(R.id.sec_summary_text_view);
        textView.setMaxLines(Preference.DEFAULT_ORDER);
        textView.setEllipsize(null);
        textView.setText(R.string.notification_wont_appear_title);
    }
}
