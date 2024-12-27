package com.samsung.android.settings.accessibility.advanced.shortcut;

import android.content.Context;
import android.content.res.Resources;
import android.provider.Settings;
import android.util.Log;

import com.android.settings.Utils;
import com.android.settings.accessibility.AccessibilityUtil;
import com.android.settingslib.applications.RecentAppOpsAccess;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;

import java.util.HashSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AccessibilityButtonGestureUtil {
    public static AccessibilityButtonMode getMode(Context context) {
        AccessibilityButtonMode accessibilityButtonMode =
                AccessibilityButtonMode.NAVIGATION_BAR_BUTTON;
        if (context == null) {
            Log.d("AccessibilityButtonMode", "context is null.");
            return accessibilityButtonMode;
        }
        boolean isFloatingMenuEnabled = AccessibilityUtil.isFloatingMenuEnabled(context);
        AccessibilityButtonMode accessibilityButtonMode2 = AccessibilityButtonMode.FLOATING_BUTTON;
        if (isFloatingMenuEnabled) {
            return accessibilityButtonMode2;
        }
        if (Settings.Global.getInt(
                        context.getContentResolver(), "navigation_bar_gesture_while_hidden", 0)
                <= 0) {
            return !hasNavigationBar(context) ? accessibilityButtonMode2 : accessibilityButtonMode;
        }
        HashSet hashSet = SecAccessibilityUtils.excludeDeviceNameSet;
        return Utils.isTalkBackEnabled(context)
                ? AccessibilityButtonMode.NAVIGATION_BAR_GESTURE_THREE_FINGER
                : AccessibilityButtonMode.NAVIGATION_BAR_GESTURE_TWO_FINGER;
    }

    public static AccessibilityNavBarSupportedType getSupportedType(Context context) {
        AccessibilityNavBarSupportedType accessibilityNavBarSupportedType =
                AccessibilityNavBarSupportedType.NAVIGATION_BAR_UNSUPPORTED;
        if (context == null) {
            Log.d("AccessibilityNavBarSupportedType", "context is null.");
            return accessibilityNavBarSupportedType;
        }
        if (Rune.supportNavigationBarForHardKey()) {
            return accessibilityNavBarSupportedType;
        }
        if (Settings.Global.getInt(
                        context.getContentResolver(), "navigation_bar_gesture_while_hidden", 0)
                <= 0) {
            return !hasNavigationBar(context)
                    ? accessibilityNavBarSupportedType
                    : AccessibilityNavBarSupportedType.NAVIGATION_BAR_BUTTON;
        }
        HashSet hashSet = SecAccessibilityUtils.excludeDeviceNameSet;
        return Utils.isTalkBackEnabled(context)
                ? AccessibilityNavBarSupportedType.NAVIGATION_BAR_GESTURE_THREE_FINGER
                : AccessibilityNavBarSupportedType.NAVIGATION_BAR_GESTURE_TWO_FINGER;
    }

    public static boolean hasNavigationBar(Context context) {
        Resources resources = context.getResources();
        int identifier =
                resources.getIdentifier(
                        "config_showNavigationBar",
                        "bool",
                        RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME);
        return identifier > 0 && resources.getBoolean(identifier);
    }
}
