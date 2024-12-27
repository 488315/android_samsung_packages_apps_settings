package com.android.settings.widget;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.preference.PreferenceViewHolder;
import androidx.preference.SwitchPreferenceCompat;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FilterTouchesSwitchPreference extends SwitchPreferenceCompat {
    public FilterTouchesSwitchPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // androidx.preference.SwitchPreferenceCompat, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View findViewById = preferenceViewHolder.findViewById(R.id.switch_widget);
        if (findViewById != null) {
            findViewById.getRootView().setFilterTouchesWhenObscured(true);
        }
    }

    public FilterTouchesSwitchPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public FilterTouchesSwitchPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public FilterTouchesSwitchPreference(Context context) {
        super(context);
    }
}
