package com.samsung.android.settings.usefulfeature;

import android.R;
import android.app.ActivityTaskManager;
import android.app.UiModeManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.android.settings.Utils;
import com.samsung.android.displaysolution.SemDisplaySolutionManager;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.activekey.ActiveKeyInfo;
import com.samsung.android.settings.activekey.DedicatedAppInfo;
import com.samsung.android.settings.csc.CscParser;
import com.samsung.android.settings.general.BaseResetSettingsData;
import com.samsung.android.settings.general.OnResetSettingsDataListener;
import com.samsung.android.util.SemLog;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class UsefulfeatureReset {
    public static final OnResetSettingsDataListener RESET_SETTINGS_DATA = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.usefulfeature.UsefulfeatureReset$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseResetSettingsData {
        @Override // com.samsung.android.settings.general.BaseResetSettingsData, com.samsung.android.settings.general.OnResetSettingsDataListener
        public final void loadCscSettings(Context context, CscParser cscParser) {
            ContentResolver contentResolver = context.getContentResolver();
            if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_MOTION")) {
                if (cscParser.get("Settings.Main.Phone.Motion.DirectCall") == null || !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_MOTION_PICK_UP_TO_CALL_OUT")) {
                    Log.d("UsefulfeatureReset", "DirctCall is not found");
                } else {
                    Log.w("UsefulfeatureReset", "Settings.Motion.DirctCall");
                    if (cscParser.get("Settings.Main.Phone.Motion.DirectCall").equalsIgnoreCase("on")) {
                        Settings.System.putInt(contentResolver, "motion_pick_up_to_call_out", 1);
                    } else if (cscParser.get("Settings.Main.Phone.Motion.DirectCall").equalsIgnoreCase("off")) {
                        Settings.System.putInt(contentResolver, "motion_pick_up_to_call_out", 0);
                    }
                }
                if (cscParser.get("Settings.Main.Phone.Motion.SmartAlert") == null || !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_MOTION_PICK_UP")) {
                    Log.d("UsefulfeatureReset", "SmartAlert is not found");
                } else {
                    Log.w("UsefulfeatureReset", "Settings.Main.Phone.Motion.SmartAlert");
                    if (cscParser.get("Settings.Main.Phone.Motion.SmartAlert").equalsIgnoreCase("on")) {
                        Settings.System.putInt(contentResolver, "motion_pick_up", 1);
                    } else if (cscParser.get("Settings.Main.Phone.Motion.SmartAlert").equalsIgnoreCase("off")) {
                        Settings.System.putInt(contentResolver, "motion_pick_up", 0);
                    }
                }
                if (cscParser.get("Settings.Main.Phone.Motion.TurnOver") == null || !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_MOTION_TRUN_OVER")) {
                    SemLog.d("UsefulfeatureReset", "TurnOver is not found");
                } else {
                    SemLog.w("UsefulfeatureReset", "Settings.Main.Phone.Motion.TurnOver");
                    if (cscParser.get("Settings.Main.Phone.Motion.TurnOver").equalsIgnoreCase("on")) {
                        Settings.System.putInt(contentResolver, "motion_overturn", 1);
                    } else if (cscParser.get("Settings.Main.Phone.Motion.TurnOver").equalsIgnoreCase("off")) {
                        Settings.System.putInt(contentResolver, "motion_overturn", 0);
                    }
                }
                if (cscParser.get("Settings.Main.Phone.Motion.PalmSwipe") == null || !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_MOTION_PALM_SWIPE")) {
                    SemLog.d("UsefulfeatureReset", "PalmSwipe is not found");
                } else {
                    SemLog.w("UsefulfeatureReset", "Settings.Main.Phone.Motion.PalmSwipe");
                    if (cscParser.get("Settings.Main.Phone.Motion.PalmSwipe").equalsIgnoreCase("on")) {
                        Settings.System.putInt(contentResolver, "surface_palm_swipe", 1);
                    } else if (cscParser.get("Settings.Main.Phone.Motion.PalmSwipe").equalsIgnoreCase("off")) {
                        Settings.System.putInt(contentResolver, "surface_palm_swipe", 0);
                    }
                }
                if (cscParser.get("Settings.Main.Phone.Motion.PalmTouch") == null || !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_MOTION_PALM_TOUCH")) {
                    SemLog.d("UsefulfeatureReset", "PalmTouch is not found");
                    return;
                }
                SemLog.w("UsefulfeatureReset", "Settings.Main.Phone.Motion.PalmTouch");
                if (cscParser.get("Settings.Main.Phone.Motion.PalmTouch").equalsIgnoreCase("on")) {
                    Settings.System.putInt(contentResolver, "surface_palm_touch", 1);
                } else if (cscParser.get("Settings.Main.Phone.Motion.PalmTouch").equalsIgnoreCase("off")) {
                    Settings.System.putInt(contentResolver, "surface_palm_touch", 0);
                }
            }
        }

        /* JADX WARN: Type inference failed for: r6v27 */
        /* JADX WARN: Type inference failed for: r6v28, types: [boolean, int] */
        /* JADX WARN: Type inference failed for: r6v29 */
        @Override // com.samsung.android.settings.general.OnResetSettingsDataListener
        public final void resetSettings(Context context) {
            String str;
            ?? r6;
            SemLog.i("UsefulfeatureReset", "Reset Usefulfeature Settings called");
            ContentResolver contentResolver = context.getContentResolver();
            String salesCode = Utils.getSalesCode();
            boolean supportFunctionKey = Rune.supportFunctionKey();
            String str2 = ApnSettings.MVNO_NONE;
            if (supportFunctionKey) {
                if (SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigDefQuickSettingItem", ApnSettings.MVNO_NONE).contains("TvMode") && Utils.hasPackage(context, "com.samsung.tvmode")) {
                    Settings.Global.putInt(contentResolver, "function_key_config_doublepress", 0);
                } else {
                    Settings.Global.putInt(contentResolver, "function_key_config_doublepress", 1);
                }
                Settings.Global.putInt(contentResolver, "function_key_config_doublepress_type", 0);
                Settings.Global.putString(contentResolver, "function_key_config_doublepress_value", ApnSettings.MVNO_NONE);
                boolean z = Settings.Global.getInt(contentResolver, "function_key_config_doublepress", 1) == 1;
                UsefulfeatureUtils.migrationFunctionKeyDB(context, 2);
                if (z) {
                    UsefulfeatureUtils.setSideKeyCustomizationInfo(context, 2, true);
                } else {
                    UsefulfeatureUtils.setSideKeyCustomizationInfo(context, 2, false);
                }
                if (Rune.isSupportAiAgent(context)) {
                    Settings.Global.putInt(contentResolver, "function_key_config_longpress_type", 2);
                } else if (Rune.supportBixbyClient()) {
                    Settings.Global.putInt(contentResolver, "function_key_config_longpress_type", 0);
                } else {
                    Settings.Global.putInt(contentResolver, "function_key_config_longpress_type", 1);
                }
                UsefulfeatureUtils.migrationFunctionKeyDB(context, 1);
                UsefulfeatureUtils.setSideKeyCustomizationInfo(context, 1, true);
                if (UsefulfeatureUtils.hasSideKeyDedicatedAppEnable(context) && (DedicatedAppInfo.getDedicatedAppSwitch(context, 3) || DedicatedAppInfo.getDedicatedAppState(context, 3))) {
                    DedicatedAppInfo.setDedicatedAppSwitch(context, 3, false);
                    DedicatedAppInfo.saveDedicatedApp(context, 3, ApnSettings.MVNO_NONE);
                    DedicatedAppInfo.saveDedicatedAppLabel(context, 3, ApnSettings.MVNO_NONE);
                }
            }
            if (UsefulfeatureUtils.hasActiveKey()) {
                UsefulfeatureUtils.hasActiveKey();
                UsefulfeatureUtils.hasActiveKey();
                Settings.System.putString(contentResolver, "short_press_app", "com.sec.android.app.camera/com.sec.android.app.camera.Camera");
                Settings.System.putString(contentResolver, "long_press_app", "com.samsung.android.calendar/com.samsung.android.app.calendar.activity.MainActivity");
                Settings.System.putInt(contentResolver, "active_key_on_lockscreen", 0);
                ActiveKeyInfo.setExtraKeyCustomizationInfo(0, "com.sec.android.app.camera/com.sec.android.app.camera.Camera", !TextUtils.isEmpty("com.sec.android.app.camera/com.sec.android.app.camera.Camera"));
                ActiveKeyInfo.setExtraKeyCustomizationInfo(1, "com.samsung.android.calendar/com.samsung.android.app.calendar.activity.MainActivity", true);
                if (UsefulfeatureUtils.hasVzwPttEnable(context)) {
                    str2 = "com.verizon.pushtotalkplus";
                    str = "Push To Talk+";
                } else if (UsefulfeatureUtils.hasEPttEnable(context)) {
                    str2 = "com.att.eptt";
                    str = "AT&T EPTT";
                } else if (UsefulfeatureUtils.hasBMCPttEnable(context)) {
                    str2 = "com.bell.ptt";
                    str = "Push-to-Talk";
                } else if (UsefulfeatureUtils.hasTMOPttEnable(context)) {
                    str2 = "com.sprint.sdcplus";
                    str = "TDC";
                } else {
                    str = ApnSettings.MVNO_NONE;
                    r6 = 0;
                    Settings.System.putInt(contentResolver, "dedicated_app_xcover_switch", r6);
                    Settings.System.putString(contentResolver, "dedicated_app_xcover", str2);
                    Settings.System.putString(contentResolver, "dedicated_app_label_xcover", str);
                    DedicatedAppInfo.setB2BDeltaKeyCustomizationInfo(context, 0, str2, r6);
                    UsefulfeatureUtils.hasActiveKey();
                }
                r6 = 1;
                Settings.System.putInt(contentResolver, "dedicated_app_xcover_switch", r6);
                Settings.System.putString(contentResolver, "dedicated_app_xcover", str2);
                Settings.System.putString(contentResolver, "dedicated_app_label_xcover", str);
                DedicatedAppInfo.setB2BDeltaKeyCustomizationInfo(context, 0, str2, r6);
                UsefulfeatureUtils.hasActiveKey();
            }
            if (context.getResources().getBoolean(R.bool.config_unfoldTransitionHapticsEnabled)) {
                Settings.Global.putInt(contentResolver, "force_resizable_activities", 0);
            }
            if (Utils.isTablet()) {
                try {
                    ActivityTaskManager.getService().resetUserPackageSettings(UserHandle.getCallingUserId(), 64);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            if (Utils.isTablet()) {
                try {
                    ActivityTaskManager.getService().resetUserPackageSettings(UserHandle.getCallingUserId(), 32);
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }
            } else {
                String str3 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            }
            String str4 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            UiModeManager uiModeManager = (UiModeManager) context.getSystemService(UiModeManager.class);
            if (uiModeManager != null) {
                for (String str5 : uiModeManager.getNightPriorityAllowedPackagesFromScpm()) {
                    if (Utils.hasPackage(context, str5)) {
                        uiModeManager.setPackageNightMode(str5, 2);
                    }
                }
            }
            if (context.getResources().getBoolean(R.bool.config_unfoldTransitionHapticsEnabled)) {
                MultiWindowManager.getInstance().setSplitImmersiveMode(false);
            }
            if (context.getResources().getBoolean(R.bool.config_unfoldTransitionHapticsEnabled)) {
                Settings.Global.putInt(contentResolver, "open_in_pop_up_view", 0);
            }
            if (context.getResources().getBoolean(R.bool.config_unfoldTransitionHapticsEnabled)) {
                Settings.Global.putInt(contentResolver, "open_in_split_screen_view", 0);
            }
            if (Utils.isTablet()) {
                Settings.Global.putInt(contentResolver, "multi_window_menu_in_full_screen", 0);
            } else if (Build.VERSION.SEM_PLATFORM_INT > 130100) {
                String str6 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            }
            if (Rune.isChinaModel()) {
                Settings.System.putInt(contentResolver, "lift_to_wake", 1);
            } else {
                Settings.System.putInt(contentResolver, "lift_to_wake", 0);
            }
            boolean z2 = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_DEFAULT_DOUBLE_TAP_TO_WAKE");
            int i = Settings.System.getInt(contentResolver, "adaptive_brightness", 0);
            int i2 = Settings.System.getInt(contentResolver, "screen_brightness_mode", 0);
            List<Sensor> sensorList = ((SensorManager) context.getSystemService("sensor")).getSensorList(-1);
            boolean z3 = false;
            boolean z4 = false;
            for (int i3 = 0; i3 < sensorList.size(); i3++) {
                int type = sensorList.get(i3).getType();
                if (type == 5) {
                    z3 = true;
                }
                if (type == 65604) {
                    z4 = true;
                }
            }
            if ((z3 || !(i == 1 || (z4 && i2 == 1))) && z2) {
                if (Rune.supportDoubleTapMenu()) {
                    Settings.System.putInt(contentResolver, "double_tab_to_wake_up", 1);
                }
                Settings.System.putInt(contentResolver, "double_tap_to_sleep", 1);
            } else {
                if (Rune.supportDoubleTapMenu()) {
                    Settings.System.putInt(contentResolver, "double_tab_to_wake_up", 0);
                }
                Settings.System.putInt(contentResolver, "double_tap_to_sleep", 0);
            }
            if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_MOTION")) {
                if (context.getPackageManager().hasSystemFeature("com.sec.feature.sensorhub")) {
                    Settings.System.putInt(contentResolver, "motion_pick_up", 1);
                } else {
                    Settings.System.putInt(contentResolver, "motion_pick_up", 0);
                }
                if ("VZW".equals(salesCode) || "VPP".equals(salesCode) || "AIO".equals(salesCode) || !UsefulfeatureUtils.isSupportMotionFeature(context, "direct_call")) {
                    Settings.System.putInt(contentResolver, "motion_pick_up_to_call_out", 0);
                } else {
                    Settings.System.putInt(contentResolver, "motion_pick_up_to_call_out", 1);
                }
                Settings.System.putInt(contentResolver, "motion_merged_mute_pause", 1);
                if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_MOTION_TRUN_OVER")) {
                    Settings.System.putInt(contentResolver, "motion_overturn", 1);
                } else {
                    Settings.System.putInt(contentResolver, "motion_overturn", 0);
                }
                if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_MOTION_PALM_TOUCH")) {
                    Settings.System.putInt(contentResolver, "surface_palm_touch", 1);
                } else {
                    Settings.System.putInt(contentResolver, "surface_palm_touch", 0);
                }
                if ("AIO".equals(salesCode)) {
                    Settings.System.putInt(contentResolver, "surface_palm_swipe", 0);
                } else {
                    Settings.System.putInt(contentResolver, "surface_palm_swipe", 1);
                }
            } else {
                Settings.System.putInt(contentResolver, "motion_pick_up", 0);
                Settings.System.putInt(contentResolver, "motion_pick_up_to_call_out", 0);
                Settings.System.putInt(contentResolver, "motion_merged_mute_pause", 0);
                Settings.System.putInt(contentResolver, "motion_overturn", 0);
                Settings.System.putInt(contentResolver, "surface_palm_touch", 0);
                Settings.System.putInt(contentResolver, "surface_palm_swipe", 0);
            }
            String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_ONEHAND_MODE_POLICY");
            if (TextUtils.isEmpty(string)) {
                Settings.System.putInt(contentResolver, "any_screen_enabled", 0);
                Settings.System.putInt(contentResolver, "one_handed_op_wakeup_type", 1);
            } else {
                if (string.contains("DefaultOn")) {
                    Settings.System.putInt(contentResolver, "any_screen_enabled", 1);
                } else {
                    Settings.System.putInt(contentResolver, "any_screen_enabled", 0);
                }
                if (string.contains("GestureType")) {
                    Settings.System.putInt(contentResolver, "one_handed_op_wakeup_type", 0);
                } else {
                    Settings.System.putInt(contentResolver, "one_handed_op_wakeup_type", 1);
                }
            }
            UsefulfeatureUtils.setOneHandModeKeyCustomizationInfo(Settings.System.getInt(context.getContentResolver(), "one_handed_op_wakeup_type", 0) == 1 && (Settings.System.getInt(context.getContentResolver(), "any_screen_enabled", 0) != 0));
            Settings.System.putInt(contentResolver, "one_handed_op_show_hard_keys", 0);
            Intent intent = new Intent("com.samsung.intent.action.ONEHAND_REDUCE_SCREEN_STATUS");
            intent.putExtra("is_enabled", 0);
            context.sendBroadcast(intent);
            Settings.System.putInt(contentResolver, "direct_share", 1);
            Settings.System.putInt(contentResolver, "hdr_effect", 0);
            SemDisplaySolutionManager semDisplaySolutionManager = (SemDisplaySolutionManager) context.getSystemService("DisplaySolution");
            String[] stringArray = context.getResources().getStringArray(R.array.config_integrityRuleProviderPackages);
            String[] stringArray2 = context.getResources().getStringArray(R.array.config_ephemeralResolverPackage);
            int length = stringArray.length + stringArray2.length;
            String[] strArr = new String[length];
            System.arraycopy(stringArray2, 0, strArr, 0, stringArray2.length);
            System.arraycopy(stringArray, 0, strArr, stringArray2.length, stringArray.length);
            for (int i4 = 0; i4 < length; i4++) {
                if (semDisplaySolutionManager.getVideoEnhancerSettingState(strArr[i4]) == 0) {
                    semDisplaySolutionManager.setVideoEnhancerSettingState(strArr[i4], 1);
                }
            }
            if ("VZW".equals(salesCode) || "VPP".equals(salesCode)) {
                Settings.System.putInt(contentResolver, "automatic_unlock", 0);
            } else {
                Settings.System.putInt(contentResolver, "automatic_unlock", 1);
            }
            if (UsefulfeatureUtils.hasFeatureAutoScreenTurnOn(context) || !context.getPackageManager().hasSystemFeature("com.sec.feature.nfc_authentication_cover")) {
                Settings.System.putInt(contentResolver, "auto_screen_on", 1);
            } else {
                Settings.System.putInt(contentResolver, "auto_screen_on", 0);
            }
            Settings.System.putInt(contentResolver, "cover_text_direction", 0);
            String str7 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            Settings.System.putInt(contentResolver, "intelligent_sleep_mode", 0);
            if (Rune.supportFingerPrint(context)) {
                Settings.System.putInt(contentResolver, "fingerprint_gesture_quick", 0);
                if (Rune.supportFingerSensorGestureSpay(context)) {
                    Settings.System.putInt(contentResolver, "fingerprint_gesture_spay", 0);
                }
            }
        }
    }
}
