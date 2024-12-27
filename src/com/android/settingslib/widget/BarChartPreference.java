package com.android.settingslib.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BarChartPreference extends Preference {
    public BarChartPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init$15();
    }

    public final void init$15() {
        setSelectable(false);
        setLayoutResource(R.layout.settings_bar_chart);
        getContext().getResources().getDimensionPixelSize(R.dimen.settings_bar_view_max_height);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.mDividerAllowedAbove = true;
        preferenceViewHolder.mDividerAllowedBelow = true;
        throw null;
    }

    public BarChartPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init$15();
    }

    public BarChartPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init$15();
    }
}
