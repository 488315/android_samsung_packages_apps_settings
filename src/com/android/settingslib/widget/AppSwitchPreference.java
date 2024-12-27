package com.android.settingslib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.preference.PreferenceViewHolder;
import androidx.preference.SwitchPreference;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppSwitchPreference extends SwitchPreference {
    public AppSwitchPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        setLayoutResource(R.layout.sec_preference_app);
    }

    @Override // androidx.preference.SwitchPreference, androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View findViewById = preferenceViewHolder.findViewById(android.R.id.switch_widget);
        if (findViewById != null) {
            findViewById.getRootView().setFilterTouchesWhenObscured(true);
        }
    }

    public AppSwitchPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setLayoutResource(R.layout.sec_preference_app);
    }

    public AppSwitchPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i, 0);
        setLayoutResource(R.layout.sec_preference_app);
    }

    public AppSwitchPreference(Context context) {
        super(context, null);
        setLayoutResource(R.layout.sec_preference_app);
    }
}
