package com.samsung.android.settings.notification.zen;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.ToggleButton;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CheckBoxToggleButton extends ToggleButton {
    public CheckBoxToggleButton(Context context) {
        super(context);
    }

    @Override // android.widget.ToggleButton, android.widget.CompoundButton, android.widget.Button,
              // android.widget.TextView, android.view.View
    public final CharSequence getAccessibilityClassName() {
        return CheckBox.class.getName();
    }

    public CheckBoxToggleButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CheckBoxToggleButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
