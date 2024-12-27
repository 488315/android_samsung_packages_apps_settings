package com.android.settingslib;

import android.content.Context;

import androidx.preference.DropDownPreference;
import androidx.preference.PreferenceViewHolder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RestrictedDropDownPreference extends DropDownPreference {
    public final RestrictedPreferenceHelper mHelper;

    public RestrictedDropDownPreference(Context context) {
        super(context);
        this.mHelper = new RestrictedPreferenceHelper(context, this, null);
    }

    @Override // androidx.preference.DropDownPreference, androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mHelper.onBindViewHolder(preferenceViewHolder);
    }

    @Override // androidx.preference.Preference
    public final void performClick() {
        if (this.mHelper.performClick()) {
            return;
        }
        super.performClick();
    }

    @Override // androidx.preference.Preference
    public final void setEnabled(boolean z) {
        if (z) {
            RestrictedPreferenceHelper restrictedPreferenceHelper = this.mHelper;
            if (restrictedPreferenceHelper.mDisabledByEcm) {
                restrictedPreferenceHelper.setDisabledByEcm(null);
                return;
            }
        }
        super.setEnabled(z);
    }
}
