package com.samsung.android.settings.accessibility.advanced.shortcut;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public enum AccessibilityButtonMode {
    NAVIGATION_BAR_BUTTON(true),
    NAVIGATION_BAR_GESTURE_TWO_FINGER(false),
    NAVIGATION_BAR_GESTURE_THREE_FINGER(false),
    FLOATING_BUTTON(true);

    public final boolean isButton;

    AccessibilityButtonMode(boolean z) {
        this.isButton = z;
    }
}
