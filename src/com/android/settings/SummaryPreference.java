package com.android.settings;

import android.R;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class SummaryPreference extends Preference {
    public String mAmount;
    public final boolean mChartEnabled;
    public float mLeftRatio;
    public float mMiddleRatio;
    public String mUnits;

    public SummaryPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mChartEnabled = true;
        setLayoutResource(R.layout.settings_summary_preference);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        ProgressBar progressBar =
                (ProgressBar) preferenceViewHolder.itemView.findViewById(R.id.color_bar);
        if (this.mChartEnabled) {
            progressBar.setVisibility(0);
            int i = (int) (this.mLeftRatio * 100.0f);
            progressBar.setProgress(i);
            progressBar.setSecondaryProgress(i + ((int) (this.mMiddleRatio * 100.0f)));
        } else {
            progressBar.setVisibility(8);
        }
        if (!this.mChartEnabled || (TextUtils.isEmpty(null) && TextUtils.isEmpty(null))) {
            preferenceViewHolder.findViewById(R.id.label_bar).setVisibility(8);
            return;
        }
        preferenceViewHolder.findViewById(R.id.label_bar).setVisibility(0);
        ((TextView) preferenceViewHolder.findViewById(R.id.text1)).setText((CharSequence) null);
        ((TextView) preferenceViewHolder.findViewById(R.id.text2)).setText((CharSequence) null);
    }
}
