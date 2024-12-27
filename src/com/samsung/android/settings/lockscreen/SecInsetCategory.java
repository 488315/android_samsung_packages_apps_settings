package com.samsung.android.settings.lockscreen;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceViewHolder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecInsetCategory extends PreferenceCategory {
    public SecInsetCategory(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // androidx.preference.PreferenceCategory, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.height = 1;
        preferenceViewHolder.itemView.setLayoutParams(layoutParams);
    }

    public SecInsetCategory(Context context) {
        super(context);
        seslSetSubheaderRoundedBackground(0);
    }
}
