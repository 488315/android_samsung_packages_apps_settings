package com.android.settings.applications.intentpicker;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settingslib.widget.TwoTargetPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LeftSideCheckBoxPreference extends TwoTargetPreference {
    public CheckBox mCheckBox;
    public boolean mChecked;

    public LeftSideCheckBoxPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        setLayoutResource(R.layout.preference_checkable_two_target);
    }

    @Override // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        CheckBox checkBox = (CheckBox) preferenceViewHolder.findViewById(android.R.id.checkbox);
        this.mCheckBox = checkBox;
        if (checkBox != null) {
            checkBox.setChecked(this.mChecked);
        }
    }

    @Override // androidx.preference.Preference
    public final void onClick() {
        CheckBox checkBox = this.mCheckBox;
        if (checkBox != null) {
            boolean z = !this.mChecked;
            this.mChecked = z;
            checkBox.setChecked(z);
            callChangeListener(Boolean.valueOf(this.mChecked));
        }
    }

    public LeftSideCheckBoxPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public LeftSideCheckBoxPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }
}
