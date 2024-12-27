package com.android.settings.widget;

import android.widget.CompoundButton;

import androidx.appcompat.widget.SeslSwitchBar;

import com.android.settingslib.RestrictedLockUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class MainSwitchBarController extends SwitchWidgetController
        implements CompoundButton.OnCheckedChangeListener {
    public final SettingsMainSwitchBar mMainSwitch;

    public MainSwitchBarController(SettingsMainSwitchBar settingsMainSwitchBar) {
        this.mMainSwitch = settingsMainSwitchBar;
    }

    @Override // com.android.settings.widget.SwitchWidgetController
    public final boolean isChecked() {
        return ((SeslSwitchBar) this.mMainSwitch).mSwitch.isChecked();
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        SwitchWidgetController.OnSwitchChangeListener onSwitchChangeListener = this.mListener;
        if (onSwitchChangeListener != null) {
            onSwitchChangeListener.onSwitchToggled(z);
        }
    }

    @Override // com.android.settings.widget.SwitchWidgetController
    public final void setChecked(boolean z) {
        this.mMainSwitch.setChecked(z);
    }

    @Override // com.android.settings.widget.SwitchWidgetController
    public final void setDisabledByAdmin(RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
        this.mMainSwitch.setDisabledByAdmin(enforcedAdmin);
    }

    @Override // com.android.settings.widget.SwitchWidgetController
    public final void setEnabled(boolean z) {
        this.mMainSwitch.setEnabled(z);
    }

    @Override // com.android.settings.widget.SwitchWidgetController
    public final void setupView() {
        this.mMainSwitch.show();
    }

    @Override // com.android.settings.widget.SwitchWidgetController
    public final void startListening() {
        this.mMainSwitch.addOnSwitchChangeListener(this);
    }

    @Override // com.android.settings.widget.SwitchWidgetController
    public final void stopListening() {
        this.mMainSwitch.removeOnSwitchChangeListener(this);
    }

    @Override // com.android.settings.widget.SwitchWidgetController
    public final void teardownView() {
        this.mMainSwitch.hide();
    }
}
