package com.android.settings.widget;

import com.android.settingslib.RestrictedLockUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SwitchWidgetController {
    public OnSwitchChangeListener mListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnSwitchChangeListener {
        boolean onSwitchToggled(boolean z);
    }

    public abstract boolean isChecked();

    public abstract void setChecked(boolean z);

    public abstract void setDisabledByAdmin(RestrictedLockUtils.EnforcedAdmin enforcedAdmin);

    public abstract void setEnabled(boolean z);

    public abstract void startListening();

    public abstract void stopListening();

    public void setupView() {}

    public void teardownView() {}
}
