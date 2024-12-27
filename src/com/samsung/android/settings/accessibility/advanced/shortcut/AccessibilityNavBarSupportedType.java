package com.samsung.android.settings.accessibility.advanced.shortcut;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AccessibilityNavBarSupportedType {
    public static final /* synthetic */ AccessibilityNavBarSupportedType[] $VALUES;
    public static final AccessibilityNavBarSupportedType NAVIGATION_BAR_BUTTON;
    public static final AccessibilityNavBarSupportedType NAVIGATION_BAR_GESTURE_THREE_FINGER;
    public static final AccessibilityNavBarSupportedType NAVIGATION_BAR_GESTURE_TWO_FINGER;
    public static final AccessibilityNavBarSupportedType NAVIGATION_BAR_UNSUPPORTED;

    static {
        AccessibilityNavBarSupportedType accessibilityNavBarSupportedType =
                new AccessibilityNavBarSupportedType("NAVIGATION_BAR_BUTTON", 0);
        NAVIGATION_BAR_BUTTON = accessibilityNavBarSupportedType;
        AccessibilityNavBarSupportedType accessibilityNavBarSupportedType2 =
                new AccessibilityNavBarSupportedType("NAVIGATION_BAR_GESTURE_TWO_FINGER", 1);
        NAVIGATION_BAR_GESTURE_TWO_FINGER = accessibilityNavBarSupportedType2;
        AccessibilityNavBarSupportedType accessibilityNavBarSupportedType3 =
                new AccessibilityNavBarSupportedType("NAVIGATION_BAR_GESTURE_THREE_FINGER", 2);
        NAVIGATION_BAR_GESTURE_THREE_FINGER = accessibilityNavBarSupportedType3;
        AccessibilityNavBarSupportedType accessibilityNavBarSupportedType4 =
                new AccessibilityNavBarSupportedType("NAVIGATION_BAR_UNSUPPORTED", 3);
        NAVIGATION_BAR_UNSUPPORTED = accessibilityNavBarSupportedType4;
        $VALUES =
                new AccessibilityNavBarSupportedType[] {
                    accessibilityNavBarSupportedType,
                    accessibilityNavBarSupportedType2,
                    accessibilityNavBarSupportedType3,
                    accessibilityNavBarSupportedType4,
                    new AccessibilityNavBarSupportedType("NAVIGATION_BAR_DISABLED", 4)
                };
    }

    public static AccessibilityNavBarSupportedType valueOf(String str) {
        return (AccessibilityNavBarSupportedType)
                Enum.valueOf(AccessibilityNavBarSupportedType.class, str);
    }

    public static AccessibilityNavBarSupportedType[] values() {
        return (AccessibilityNavBarSupportedType[]) $VALUES.clone();
    }
}
