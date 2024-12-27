package com.android.settings.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.preference.PreferenceViewHolder;

import com.android.settingslib.RestrictedTopLevelPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RestrictedHomepagePreference extends RestrictedTopLevelPreference
        implements HomepagePreferenceLayoutHelper.HomepagePreferenceLayout {
    public final HomepagePreferenceLayoutHelper mHelper;

    public RestrictedHomepagePreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mHelper = new HomepagePreferenceLayoutHelper(this);
    }

    @Override // com.android.settings.widget.HomepagePreferenceLayoutHelper.HomepagePreferenceLayout
    public final HomepagePreferenceLayoutHelper getHelper() {
        return this.mHelper;
    }

    @Override // com.android.settingslib.RestrictedTopLevelPreference,
              // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mHelper.onBindViewHolder(preferenceViewHolder);
    }

    public RestrictedHomepagePreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mHelper = new HomepagePreferenceLayoutHelper(this);
    }

    public RestrictedHomepagePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mHelper = new HomepagePreferenceLayoutHelper(this);
    }

    public RestrictedHomepagePreference(Context context) {
        super(context, null);
        this.mHelper = new HomepagePreferenceLayoutHelper(this);
    }
}
