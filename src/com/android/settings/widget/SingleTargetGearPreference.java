package com.android.settings.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SingleTargetGearPreference extends Preference {
    public SingleTargetGearPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        setLayoutResource(R.layout.preference_single_target);
        setWidgetLayoutResource(R.layout.preference_widget_gear_optional_background);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View findViewById = preferenceViewHolder.findViewById(R.id.two_target_divider);
        if (findViewById != null) {
            findViewById.setVisibility(4);
        }
    }

    public SingleTargetGearPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setLayoutResource(R.layout.preference_single_target);
        setWidgetLayoutResource(R.layout.preference_widget_gear_optional_background);
    }

    public SingleTargetGearPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setLayoutResource(R.layout.preference_single_target);
        setWidgetLayoutResource(R.layout.preference_widget_gear_optional_background);
    }
}
