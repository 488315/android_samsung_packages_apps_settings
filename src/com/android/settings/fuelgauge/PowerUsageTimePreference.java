package com.android.settings.fuelgauge;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PowerUsageTimePreference extends Preference {
    CharSequence mAnomalyHintText;
    CharSequence mTimeSummary;
    CharSequence mTimeTitle;

    public PowerUsageTimePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setLayoutResource(R.layout.power_usage_time);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        View findViewById;
        TextView textView;
        super.onBindViewHolder(preferenceViewHolder);
        ((TextView) preferenceViewHolder.findViewById(R.id.time_title)).setText(this.mTimeTitle);
        ((TextView) preferenceViewHolder.findViewById(R.id.time_summary))
                .setText(this.mTimeSummary);
        if (TextUtils.isEmpty(this.mAnomalyHintText)
                || (findViewById = preferenceViewHolder.findViewById(R.id.anomaly_hints)) == null
                || (textView = (TextView) findViewById.findViewById(R.id.warning_info)) == null) {
            return;
        }
        textView.setText(this.mAnomalyHintText);
        findViewById.setVisibility(0);
    }
}
