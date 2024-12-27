package com.android.settings;

import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecPreferenceCategory;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class AccessiblePreferenceCategory extends SecPreferenceCategory {
    public String mContentDescription;

    @Override // androidx.preference.SecPreferenceCategory, androidx.preference.PreferenceCategory,
              // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.itemView.setContentDescription(this.mContentDescription);
    }
}
