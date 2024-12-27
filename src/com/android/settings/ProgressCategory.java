package com.android.settings;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class ProgressCategory extends ProgressCategoryBase {
    public int mEmptyTextRes;
    public boolean mNoDeviceFoundAdded;
    public Preference mNoDeviceFoundPreference;
    public boolean mProgress;

    public ProgressCategory(Context context) {
        super(context);
        this.mProgress = false;
        setLayoutResource(R.layout.preference_progress_category);
    }

    @Override // androidx.preference.PreferenceCategory, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View findViewById = preferenceViewHolder.findViewById(R.id.scanning_progress);
        boolean z =
                getPreferenceCount() == 0
                        || (getPreferenceCount() == 1
                                && getPreference(0) == this.mNoDeviceFoundPreference);
        findViewById.setVisibility(this.mProgress ? 0 : 8);
        if (this.mProgress || !z) {
            if (this.mNoDeviceFoundAdded) {
                removePreference(this.mNoDeviceFoundPreference);
                this.mNoDeviceFoundAdded = false;
                return;
            }
            return;
        }
        if (this.mNoDeviceFoundAdded) {
            return;
        }
        if (this.mNoDeviceFoundPreference == null) {
            Preference preference = new Preference(getContext());
            this.mNoDeviceFoundPreference = preference;
            preference.setLayoutResource(R.layout.preference_empty_list);
            this.mNoDeviceFoundPreference.setTitle(this.mEmptyTextRes);
            this.mNoDeviceFoundPreference.setSelectable(false);
        }
        addPreference(this.mNoDeviceFoundPreference);
        this.mNoDeviceFoundAdded = true;
    }

    public ProgressCategory(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mProgress = false;
        setLayoutResource(R.layout.preference_progress_category);
    }

    public ProgressCategory(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mProgress = false;
        setLayoutResource(R.layout.preference_progress_category);
    }

    public ProgressCategory(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mProgress = false;
        setLayoutResource(R.layout.preference_progress_category);
    }
}
