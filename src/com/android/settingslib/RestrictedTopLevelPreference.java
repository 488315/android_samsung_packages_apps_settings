package com.android.settingslib;

import android.content.Context;
import android.util.AttributeSet;

import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RestrictedTopLevelPreference extends Preference {
    public final RestrictedPreferenceHelper mHelper;

    public RestrictedTopLevelPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mHelper = new RestrictedPreferenceHelper(context, this, attributeSet);
    }

    @Override // androidx.preference.Preference
    public final void onAttachedToHierarchy(PreferenceManager preferenceManager) {
        this.mHelper.onAttachedToHierarchy();
        super.onAttachedToHierarchy(preferenceManager);
    }

    @Override // androidx.preference.Preference
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
            if (restrictedPreferenceHelper.mDisabledByAdmin) {
                restrictedPreferenceHelper.setDisabledByAdmin(null);
                return;
            }
        }
        super.setEnabled(z);
    }

    public RestrictedTopLevelPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public RestrictedTopLevelPreference(Context context, AttributeSet attributeSet) {
        this(
                context,
                attributeSet,
                TypedArrayUtils.getAttr(
                        context, R.attr.preferenceStyle, android.R.attr.preferenceStyle));
    }
}
