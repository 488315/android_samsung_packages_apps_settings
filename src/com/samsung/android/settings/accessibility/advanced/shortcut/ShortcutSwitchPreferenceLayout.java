package com.samsung.android.settings.accessibility.advanced.shortcut;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CheckBox;
import android.widget.LinearLayout;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ShortcutSwitchPreferenceLayout extends LinearLayout {
    public boolean isChecked;
    public String summary;

    public ShortcutSwitchPreferenceLayout(Context context) {
        super(context);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final CharSequence getAccessibilityClassName() {
        return CheckBox.class.getName();
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setContentDescription(this.summary);
        accessibilityEvent.setChecked(this.isChecked);
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(
            AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setText(this.summary);
        accessibilityNodeInfo.setCheckable(true);
        accessibilityNodeInfo.setChecked(this.isChecked);
    }

    @Override // android.view.ViewGroup
    public final boolean onRequestSendAccessibilityEvent(
            View view, AccessibilityEvent accessibilityEvent) {
        accessibilityEvent.setSource(this);
        return true;
    }

    public ShortcutSwitchPreferenceLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ShortcutSwitchPreferenceLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public ShortcutSwitchPreferenceLayout(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
