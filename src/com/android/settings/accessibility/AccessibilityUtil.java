package com.android.settings.accessibility;

import android.R;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.accessibility.util.ShortcutUtils;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AccessibilityUtil {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        new TextUtils.SimpleStringSplitter(':');
    }

    public static int getAccessibilityServiceFragmentType(AccessibilityServiceInfo accessibilityServiceInfo) {
        int i = accessibilityServiceInfo.getResolveInfo().serviceInfo.applicationInfo.targetSdkVersion;
        boolean z = (accessibilityServiceInfo.flags & 256) != 0;
        if (i <= 29) {
            return 0;
        }
        return z ? 1 : 2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v3, types: [int] */
    /* JADX WARN: Type inference failed for: r0v6 */
    public static int getUserShortcutTypesFromSettings(Context context, ComponentName componentName) {
        boolean hasValuesInSettings = hasValuesInSettings(context, 1, componentName);
        boolean z = hasValuesInSettings;
        if (hasValuesInSettings(context, 2, componentName)) {
            z = (hasValuesInSettings ? 1 : 0) | 2;
        }
        ?? r0 = z;
        if (hasValuesInSettings(context, 16, componentName)) {
            r0 = (z ? 1 : 0) | 16;
        }
        return hasValuesInSettings(context, 512, componentName) ? r0 | 512 : r0;
    }

    public static boolean hasValueInSettings(Context context, int i, ComponentName componentName) {
        return ShortcutUtils.getShortcutTargetsFromSettings(context, i, UserHandle.myUserId()).contains(componentName.flattenToString());
    }

    public static boolean hasValuesInSettings(Context context, int i, ComponentName componentName) {
        boolean hasValueInSettings = (i & 1) == 1 ? hasValueInSettings(context, 1, componentName) : false;
        if ((i & 2) == 2) {
            hasValueInSettings |= hasValueInSettings(context, 2, componentName);
        }
        if ((i & 16) == 16) {
            hasValueInSettings |= hasValueInSettings(context, 16, componentName);
        }
        return (i & 512) == 512 ? hasValueInSettings | hasValueInSettings(context, 512, componentName) : hasValueInSettings;
    }

    public static boolean isFloatingMenuEnabled(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), "accessibility_button_mode", -1) == 1;
    }

    public static boolean isGestureNavigateEnabled(Context context) {
        return context.getResources().getInteger(R.integer.config_pinnerWebviewPinBytes) == 2;
    }

    public static boolean isTouchExploreEnabled(Context context) {
        return ((AccessibilityManager) context.getSystemService(AccessibilityManager.class)).isTouchExplorationEnabled();
    }

    public static void optInAllValuesToSettings(Context context, int i, ComponentName componentName) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        if (accessibilityManager != null) {
            accessibilityManager.enableShortcutsForTargets(true, i, Set.of(componentName.flattenToString()), UserHandle.myUserId());
        }
    }

    public static void optInValueToSettings(Context context, int i, ComponentName componentName) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        if (accessibilityManager != null) {
            accessibilityManager.enableShortcutsForTargets(true, i, Set.of(componentName.flattenToString()), UserHandle.myUserId());
        }
    }

    public static void optOutAllValuesFromSettings(Context context, int i, ComponentName componentName) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        if (accessibilityManager != null) {
            accessibilityManager.enableShortcutsForTargets(false, i, Set.of(componentName.flattenToString()), UserHandle.myUserId());
        }
    }

    public static void optOutValueFromSettings(Context context, int i, ComponentName componentName) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        if (accessibilityManager != null) {
            accessibilityManager.enableShortcutsForTargets(false, i, Set.of(componentName.flattenToString()), UserHandle.myUserId());
        }
    }
}
