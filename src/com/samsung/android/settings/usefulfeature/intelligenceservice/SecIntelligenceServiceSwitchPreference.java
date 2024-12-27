package com.samsung.android.settings.usefulfeature.intelligenceservice;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecSwitchPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecIntelligenceServiceSwitchPreference extends SecSwitchPreference {
    public SecIntelligenceServiceSwitchPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // androidx.preference.SecSwitchPreference,
              // androidx.preference.SwitchPreferenceCompat, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TextView textView = (TextView) preferenceViewHolder.itemView.findViewById(R.id.summary);
        if (textView != null) {
            textView.setMaxLines(30);
        }
    }

    public SecIntelligenceServiceSwitchPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public SecIntelligenceServiceSwitchPreference(
            Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public SecIntelligenceServiceSwitchPreference(Context context) {
        super(context);
    }
}
