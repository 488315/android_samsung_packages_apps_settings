package com.android.settings.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settingslib.widget.TwoTargetPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PrimaryCheckBoxPreference extends TwoTargetPreference {
    public CheckBox mCheckBox;
    public boolean mChecked;
    public boolean mEnableCheckBox;

    public PrimaryCheckBoxPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mEnableCheckBox = true;
    }

    @Override // com.android.settingslib.widget.TwoTargetPreference
    public final int getSecondTargetResId() {
        return R.layout.preference_widget_primary_checkbox;
    }

    @Override // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View findViewById = preferenceViewHolder.findViewById(android.R.id.widget_frame);
        if (findViewById != null) {
            findViewById.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.widget.PrimaryCheckBoxPreference.1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            CheckBox checkBox = PrimaryCheckBoxPreference.this.mCheckBox;
                            if (checkBox == null || checkBox.isEnabled()) {
                                PrimaryCheckBoxPreference primaryCheckBoxPreference =
                                        PrimaryCheckBoxPreference.this;
                                boolean z = !primaryCheckBoxPreference.mChecked;
                                primaryCheckBoxPreference.mChecked = z;
                                CheckBox checkBox2 = primaryCheckBoxPreference.mCheckBox;
                                if (checkBox2 != null) {
                                    checkBox2.setChecked(z);
                                }
                                PrimaryCheckBoxPreference primaryCheckBoxPreference2 =
                                        PrimaryCheckBoxPreference.this;
                                if (primaryCheckBoxPreference2.callChangeListener(
                                        Boolean.valueOf(primaryCheckBoxPreference2.mChecked))) {
                                    PrimaryCheckBoxPreference primaryCheckBoxPreference3 =
                                            PrimaryCheckBoxPreference.this;
                                    primaryCheckBoxPreference3.persistBoolean(
                                            primaryCheckBoxPreference3.mChecked);
                                    return;
                                }
                                PrimaryCheckBoxPreference primaryCheckBoxPreference4 =
                                        PrimaryCheckBoxPreference.this;
                                boolean z2 = !primaryCheckBoxPreference4.mChecked;
                                primaryCheckBoxPreference4.mChecked = z2;
                                CheckBox checkBox3 = primaryCheckBoxPreference4.mCheckBox;
                                if (checkBox3 != null) {
                                    checkBox3.setChecked(z2);
                                }
                            }
                        }
                    });
        }
        CheckBox checkBox = (CheckBox) preferenceViewHolder.findViewById(R.id.checkboxWidget);
        this.mCheckBox = checkBox;
        if (checkBox != null) {
            checkBox.setContentDescription(getTitle());
            this.mCheckBox.setChecked(this.mChecked);
            this.mCheckBox.setEnabled(this.mEnableCheckBox);
        }
    }

    @Override // androidx.preference.Preference
    public final void setEnabled(boolean z) {
        super.setEnabled(z);
        this.mEnableCheckBox = z;
        CheckBox checkBox = this.mCheckBox;
        if (checkBox != null) {
            checkBox.setEnabled(z);
        }
    }

    public PrimaryCheckBoxPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mEnableCheckBox = true;
    }

    public PrimaryCheckBoxPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mEnableCheckBox = true;
    }
}
