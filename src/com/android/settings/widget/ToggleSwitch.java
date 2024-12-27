package com.android.settings.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Switch;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ToggleSwitch extends Switch {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnBeforeCheckedChangeListener {}

    public ToggleSwitch(Context context) {
        super(context);
    }

    @Override // android.widget.Switch, android.widget.CompoundButton, android.widget.Checkable
    public void setChecked(boolean z) {
        super.setChecked(z);
    }

    public void setCheckedInternal(boolean z) {
        super.setChecked(z);
    }

    public ToggleSwitch(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ToggleSwitch(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public ToggleSwitch(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public void setOnBeforeCheckedChangeListener(
            OnBeforeCheckedChangeListener onBeforeCheckedChangeListener) {}
}
