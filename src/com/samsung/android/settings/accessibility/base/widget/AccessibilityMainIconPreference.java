package com.samsung.android.settings.accessibility.base.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AccessibilityMainIconPreference extends Preference {
    public AccessibilityMainIconPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        Resources resources = getContext().getResources();
        preferenceViewHolder.mDividerStartOffset =
                resources.getDimensionPixelSize(R.dimen.system_setting_icon_start_margin)
                        + resources.getDimensionPixelSize(R.dimen.system_setting_icon_size);
    }

    public AccessibilityMainIconPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public AccessibilityMainIconPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AccessibilityMainIconPreference(Context context) {
        super(context);
    }
}
