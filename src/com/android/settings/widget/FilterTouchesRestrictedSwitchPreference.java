package com.android.settings.widget;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.preference.PreferenceViewHolder;

import com.android.settingslib.RestrictedSwitchPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FilterTouchesRestrictedSwitchPreference extends RestrictedSwitchPreference {
    public FilterTouchesRestrictedSwitchPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // com.android.settingslib.RestrictedSwitchPreference,
              // androidx.preference.SwitchPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View findViewById = preferenceViewHolder.findViewById(R.id.switch_widget);
        if (findViewById != null) {
            findViewById.getRootView().setFilterTouchesWhenObscured(true);
        }
    }

    public FilterTouchesRestrictedSwitchPreference(
            Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public FilterTouchesRestrictedSwitchPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public FilterTouchesRestrictedSwitchPreference(Context context) {
        super(context);
    }
}
