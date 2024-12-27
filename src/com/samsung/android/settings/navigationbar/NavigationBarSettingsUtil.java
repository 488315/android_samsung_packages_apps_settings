package com.samsung.android.settings.navigationbar;

import android.R;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.TypedArray;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.SystemProperties;
import android.provider.Settings;

import com.android.settings.Utils;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.Rune$$ExternalSyntheticOutline0;
import com.samsung.android.settings.inputmethod.TouchPadGestureSettingsController;
import com.samsung.android.settings.taskbar.TaskBarPreferenceController;
import com.samsung.android.settings.taskbar.TaskBarUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class NavigationBarSettingsUtil {
    public static float[] sBottomInsetScale = null;
    public static float[] sInsetScale = null;
    public static boolean sIsForceUpdate = false;
    public static boolean sIsWalletApp = false;

    public static float[] getFloatArray(TypedArray typedArray) {
        int length = typedArray.length();
        float[] fArr = new float[length];
        for (int i = 0; i < length; i++) {
            fArr[i] = typedArray.getFloat(i, 1.0f);
        }
        typedArray.recycle();
        return fArr;
    }

    public static int getNavBarMode(Context context) {
        return Settings.Global.getInt(
                context.getContentResolver(), "navigation_bar_gesture_while_hidden", 0);
    }

    public static boolean isGestureDefault() {
        return Rune.isChinaModel()
                && SystemProperties.getInt(TouchPadGestureSettingsController.FIRST_API_LEVEL, 0)
                        >= 35;
    }

    public static boolean isGestureHintOn(Context context) {
        return Settings.Global.getInt(
                        context.getContentResolver(), "navigation_bar_gesture_hint", 1)
                != 0;
    }

    public static boolean isSimplifiedGesture() {
        int i = SystemProperties.getInt(TouchPadGestureSettingsController.FIRST_API_LEVEL, 0);
        return !(i == 34
                        && Rune$$ExternalSyntheticOutline0.m(
                                "SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_NAVIGATION_BAR_THEME",
                                "SupportLegacyGestureOptions")
                        && !isSupportSearcle())
                && Build.VERSION.SEM_PLATFORM_INT >= 150100
                && (i >= 34 || isSupportSearcle());
    }

    public static boolean isSupportLegacyFeatures(Context context) {
        return ((Settings.Global.getInt(
                                                context.getContentResolver(),
                                                "navigationbar_splugin_flags",
                                                0)
                                        & 4)
                                == 0
                        && isSimplifiedGesture())
                ? false
                : true;
    }

    public static boolean isSupportSearcle() {
        return "bsxasm1".equals(SystemProperties.get("ro.com.google.cdb.spa1"))
                || isSupportTouch2Search();
    }

    public static boolean isSupportTouch2Search() {
        return "true".equals(SystemProperties.get("ro.bbt.support.circle2search"));
    }

    public static boolean isTaskBarEnableCondition(Context context) {
        boolean z = context.getResources().getConfiguration().semDisplayDeviceType == 0;
        boolean isTablet = Utils.isTablet();
        boolean z2 =
                Settings.System.getIntForUser(
                                context.getContentResolver(), "easy_mode_switch", 1, -2)
                        == 0;
        boolean z3 =
                Settings.System.getInt(context.getContentResolver(), "minimal_battery_use", 0) == 0;
        DisplayManager displayManager =
                (DisplayManager) context.getSystemService(DisplayManager.class);
        return (z || isTablet)
                && !z2
                && z3
                && !(displayManager.getWifiDisplayStatus().getActiveDisplayState() == 2
                        && displayManager.semIsFitToActiveDisplay())
                && !(Utils.getEnterprisePolicyEnabled(
                                context,
                                "content://com.sec.knox.provider2/KioskMode",
                                "isNavigationBarHidden")
                        == 1)
                && TaskBarUtils.isSamsungLauncher(context);
    }

    public static boolean isTaskBarEnabled(Context context) {
        return Settings.Global.getInt(
                                context.getContentResolver(),
                                TaskBarPreferenceController.KEY_TASK_BAR_SETTING,
                                1)
                        == 1
                && isTaskBarEnableCondition(context);
    }

    public static void setSensitivityInsetScale(Context context) {
        if (sInsetScale == null) {
            sInsetScale =
                    getFloatArray(
                            context.getResources().obtainTypedArray(R.array.config_ntpServers));
        }
        int i =
                Settings.Global.getInt(
                        context.getContentResolver(),
                        context.getResources().getConfiguration().semDisplayDeviceType == 5
                                ? "navigation_bar_back_gesture_sensitivity_sub"
                                : "navigation_bar_back_gesture_sensitivity",
                        1);
        Settings.Secure.putFloat(
                context.getContentResolver(), "back_gesture_inset_scale_left", sInsetScale[i]);
        Settings.Secure.putFloat(
                context.getContentResolver(), "back_gesture_inset_scale_right", sInsetScale[i]);
        ContentResolver contentResolver = context.getContentResolver();
        if (sBottomInsetScale == null) {
            sBottomInsetScale =
                    getFloatArray(
                            context.getResources()
                                    .obtainTypedArray(R.array.config_rearDisplayDeviceStates));
        }
        Settings.Global.putFloat(
                contentResolver, "bottom_gesture_inset_scale", sBottomInsetScale[i]);
    }
}
