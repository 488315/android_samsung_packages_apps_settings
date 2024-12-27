package com.android.settingslib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.annotation.Keep;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settingslib.core.instrumentation.SettingsJankMonitor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PrimarySwitchPreference extends RestrictedPreference {
    public boolean mChecked;
    public boolean mCheckedSet;
    public boolean mEnableSwitch;
    public CompoundButton mSwitch;

    public PrimarySwitchPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mEnableSwitch = true;
    }

    @Keep
    public Boolean getCheckedState() {
        if (this.mCheckedSet) {
            return Boolean.valueOf(this.mChecked);
        }
        return null;
    }

    @Override // com.android.settingslib.widget.TwoTargetPreference
    public final int getSecondTargetResId() {
        return R.layout.preference_widget_primary_switch;
    }

    public final boolean isChecked() {
        return this.mSwitch != null && this.mChecked;
    }

    public boolean isSwitchEnabled() {
        return this.mEnableSwitch;
    }

    @Override // com.android.settingslib.RestrictedPreference,
              // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View findViewById = preferenceViewHolder.findViewById(android.R.id.widget_frame);
        if (findViewById instanceof LinearLayout) {
            ((LinearLayout) findViewById).setGravity(8388629);
        }
        CompoundButton compoundButton =
                (CompoundButton) preferenceViewHolder.findViewById(R.id.switchWidget);
        this.mSwitch = compoundButton;
        if (compoundButton != null) {
            compoundButton.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settingslib.PrimarySwitchPreference$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            PrimarySwitchPreference primarySwitchPreference =
                                    PrimarySwitchPreference.this;
                            CompoundButton compoundButton2 = primarySwitchPreference.mSwitch;
                            if (compoundButton2 == null || compoundButton2.isEnabled()) {
                                boolean z = !primarySwitchPreference.mChecked;
                                if (primarySwitchPreference.callChangeListener(
                                        Boolean.valueOf(z))) {
                                    SettingsJankMonitor.detectToggleJank(
                                            primarySwitchPreference.mSwitch,
                                            primarySwitchPreference.getKey());
                                    primarySwitchPreference.setChecked(z);
                                    primarySwitchPreference.persistBoolean(z);
                                }
                            }
                        }
                    });
            this.mSwitch.setOnTouchListener(
                    new PrimarySwitchPreference$$ExternalSyntheticLambda1());
            this.mSwitch.setContentDescription(getTitle());
            this.mSwitch.setChecked(this.mChecked);
            this.mSwitch.setEnabled(this.mEnableSwitch);
        }
    }

    public final void setChecked(boolean z) {
        if (this.mChecked == z && this.mCheckedSet) {
            return;
        }
        this.mChecked = z;
        this.mCheckedSet = true;
        CompoundButton compoundButton = this.mSwitch;
        if (compoundButton != null) {
            compoundButton.setChecked(z);
        }
    }

    @Override // com.android.settingslib.RestrictedPreference
    public final void setDisabledByAdmin(RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
        super.setDisabledByAdmin(enforcedAdmin);
        setSwitchEnabled(enforcedAdmin == null);
    }

    public final void setSwitchEnabled(boolean z) {
        this.mEnableSwitch = z;
        CompoundButton compoundButton = this.mSwitch;
        if (compoundButton != null) {
            compoundButton.setEnabled(z);
        }
    }

    @Override // com.android.settingslib.widget.TwoTargetPreference
    public final boolean shouldHideSecondTarget() {
        return false;
    }

    public PrimarySwitchPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mEnableSwitch = true;
    }

    public PrimarySwitchPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mEnableSwitch = true;
    }

    public PrimarySwitchPreference(Context context) {
        super(context);
        this.mEnableSwitch = true;
    }
}
