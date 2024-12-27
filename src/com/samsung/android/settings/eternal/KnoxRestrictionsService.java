package com.samsung.android.settings.eternal;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.android.internal.util.Preconditions;
import com.android.settings.Settings;

import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.lib.episode.Scene;
import com.samsung.android.settings.asbase.audio.SecSoundKeyboardSoundController;
import com.samsung.android.settings.asbase.vibration.SecSoundDeviceVibrationController;
import com.samsung.android.settings.asbase.vibration.SecSoundKeyboardVibrationController;
import com.samsung.android.settings.eternal.utils.KnoxRestrictionsFileLog;
import com.samsung.android.settings.taskbar.TaskBarPreferenceController;
import com.samsung.android.util.SemLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class KnoxRestrictionsService extends IntentService {
    public static final Map mapUIKeysToContractKey = new HashMap<String, String>() { // from class:
                // com.samsung.android.settings.eternal.KnoxRestrictionsService.1
                {
                    put("direct_call", "/Settings/Advanced/DirectCall");
                    put("easy_mute", "/Settings/Advanced/EasyMute");
                    put("palm_swipe_to_capture", "/Settings/Advanced/PalmSwipeToCapture");
                    put("smart_alert", "/Settings/Advanced/SmartAlert");
                    put("screenshots_and_screen_recorder", "/Settings/Advanced/SmartCapture");
                    put("smart_stay", "/Settings/Advanced/SmartStay");
                    put("direct_share", "/Settings/Advanced/DirectShare");
                    put("lift_to_wake", "/Settings/Advanced/LiftToWake");
                    put("double_tab_to_wake_up", "/Settings/Advanced/DoubleTapToWake");
                    put("double_tap_to_sleep", "/Settings/Advanced/DoubleTapToSleep");
                    put("onehand_operation_settings", "/Settings/Advanced/OneHandedMode");
                    put("finger_sensor_gesture", "/Settings/Advanced/FingerSensorGestures");
                    put("cover_screen_orientation", "/Settings/Advanced/CoverTextDirection");
                    put("short_press_key", "/Settings/Advanced/ActiveKeyShortPress");
                    put("long_press_key", "/Settings/Advanced/ActiveKeyLongPress");
                    put("active_key_on_lockscreen_key", "/Settings/Advanced/ActiveKeyOnLockScreen");
                    put("xcover_key_dedicated_app", "/Settings/Advanced/XcoverKeyDedicatedApp");
                    put("xcover_top_short_press_key", "/Settings/Advanced/XcoverTopKeyShortPress");
                    put("xcover_top_long_press_key", "/Settings/Advanced/XcoverTopKeyLongPress");
                    put(
                            "xcover_top_key_on_lockscreen_key",
                            "/Settings/Advanced/XcoverTopKeyOnLockScreen");
                    put(
                            "xcover_top_key_dedicated_app",
                            "/Settings/Advanced/XcoverTopKeyDedicatedApp");
                    put("function_key_double_press", "/Settings/Advanced/SideKeyDoublePress");
                    put("function_key_long_press", "/Settings/Advanced/SideKeyLongPressType");
                    put("smart_popup_view", "/Settings/Advanced/SmartPopupView");
                    put("top_level_location", "/Settings/Connections/Location");
                    put(
                            "location_services_bluetooth_scanning",
                            "/Settings/Connections/LocationBluetoothScan");
                    put(
                            "location_services_wifi_scanning",
                            "/Settings/Connections/LocationWifiScan");
                    put("nfc_settings", "/Settings/Connections/Nfc");
                    put(
                            "mode_normal_mode ",
                            "/Settings/Connections/WiFi/Advanced/SwitchToMobileData/Aggressive");
                    put(
                            "mode_aggressive_mode ",
                            "/Settings/Connections/WiFi/Advanced/SwitchToMobileData/Aggressive");
                    put(
                            "wifi_poor_network_detection",
                            "/Settings/Connections/WiFi/Advanced/SwitchToMobileData/Enabled");
                    put(
                            "wifi_switch_for_individual_apps",
                            "/Settings/Connections/WiFi/Advanced/SwitchToMobileData/IndividualApps");
                    put("auto_wifi", "/Settings/Connections/WiFi/Advanced/AutoWifi");
                    put("wifi_offload", "/Settings/Connections/WiFi/Advanced/WifiOffload");
                    put(
                            "notify_open_networks",
                            "/Settings/Connections/WiFi/Advanced/WifiNotifications");
                    put(
                            "switch_to_better_wifi",
                            "/Settings/Connections/WiFi/Intelligent/SwitchToBetterWifi");
                    put("ape", "/Settings/Connections/WiFi/Intelligent/RealtimeDataPriorityMode");
                    put("private_dns_settings", "/Settings/Connections/PrivateDnsMode");
                    put(
                            "private_dns_settings_specifier",
                            "/Settings/Connections/PrivateDnsSpecifier");
                    put(
                            "wifi_hs20_profile",
                            "/Settings/Connections/WiFi/Advanced/Hotspot20/Enabled");
                    put("MobileWIPS", "/Settings/Connections/WiFi/Advanced/WIPS");
                    put("auto_brightness", "/Settings/Display/AutoBrightness");
                    put("screen_off_pocket", "/Settings/Display/BlockAccidentalTouches");
                    put("blue_light_filter", "/Settings/Display/BluelightFilter");
                    put("sec_font_size", "/Settings/Display/FontSize");
                    put("sec_font_style", "/Settings/Display/FontStyle");
                    put("bold_text", "/Settings/Display/FontBold");
                    put("screen_timeout", "/Settings/Display/ScreenTimeout");
                    put("sec_screen_size", "/Settings/Display/ScreenZoom");
                    put("button_order", "/Settings/Display/ButtonLayout");
                    put("show_battery_percent", "/Settings/Display/ShowBatteryPercentage");
                    put(
                            "key_notification_icons_on_status_bar",
                            "/Settings/Display/ShowRecentNotificationOnly");
                    put("night_theme", "/Settings/Display/NightMode");
                    put("screen_mode", "/Settings/Display/ScreenMode");
                    put("screen_resolution", "/Settings/Display/ScreenResolution");
                    put("gesture_preview", "/Settings/Display/NavigationTypes");
                    put("gesture_hint", "/Settings/Display/NavigationGestureHint");
                    put("screensaver", "/Settings/Display/ScreenSaver");
                    put("increse_touch_sensetivity", "/Settings/Display/TouchSensetivity");
                    put("sec_high_refresh_rate", "/Settings/Display/RefreshRate");
                    put(
                            TaskBarPreferenceController.KEY_TASK_BAR_SETTING,
                            "/Settings/Display/TaskBar");
                    put("key_show_keyboard_button", "/Settings/General/ShowKeyboardButton");
                    put("change_language_shortcut", "/Settings/General/ChangeLanguageShortcut");
                    put("show_virtual_keyboard_switch", "/Settings/General/ShowOnScreenKeyboard");
                    put(
                            "location_time_zone_detection",
                            "/Settings/General/LocationTimeZoneDetection");
                    put("lock_screen_dualclock", "/Settings/LockScreen/RoamingClock");
                    put("homecity_timezone", "/Settings/LockScreen/RoamingClockHomeCity");
                    put("where_to_show", "/Settings/LockScreen/RoamingClockPosition");
                    put("lock_screen_additional_info", "/Settings/LockScreen/FaceWidget");
                    put("facewidget_where_to_show", "/Settings/LockScreen/FaceWidgetPosition");
                    put("set_visibility", "/Settings/LockScreen/HideContent");
                    put("noti_view_style", "/Settings/LockScreen/NotificationIconOnly");
                    put("notification_icons_only", "/Settings/LockScreen/NotificationIconOnly");
                    put("lock_screen_menu_notifications", "/Settings/LockScreen/ShowNotification");
                    put("noti_card_seekbar", "/Settings/LockScreen/Transparency");
                    put("noti_inverse_text", "/Settings/LockScreen/AutoReverseTextColor");
                    put("notification_badging", "/Settings/Notifications/AppIconBadge");
                    put(
                            "homescreen_noti_preview",
                            "/Settings/Notifications/AppIconBadgeShowNotifications");
                    put("app_icon_number", "/Settings/Notifications/AppIconBadgeStyle");
                    put("show_password", "/Settings/Security/MakePasswordVisible");
                    put("charging_sounds", "/Settings/Sound/ChargingSound");
                    put("dial_pad_tones", "/Settings/Sound/DialingKeypadTone");
                    put(SecSoundKeyboardSoundController.KEY, "/Settings/Sound/KeyboardSound");
                    put(
                            SecSoundKeyboardVibrationController.KEY,
                            "/Settings/Sound/KeyboardVibration");
                    put("notification", "/Settings/Sound/NotificationSound");
                    put("notification_2", "/Settings/Sound/NotificationSoundSim2");
                    put("screen_locking_sounds", "/Settings/Sound/ScreenLockSound");
                    put("touch_sounds", "/Settings/Sound/TouchSound");
                    put("volume_key_control", "/Settings/Sound/UseVolumeKeyForMedia");
                    put("vibrate_on_touch", "/Settings/Sound/VibrationFeedback");
                    put(SecSoundDeviceVibrationController.KEY, "/Settings/Sound/VibrationPattern");
                    put(
                            "notification_vibration_pattern",
                            "/Settings/Sound/NotificationVibrationPattern");
                    put("vibrate_when_ringing", "/Settings/Sound/VibrationWhileRinging");
                    put("sync_vibration_with_ringtone", "/Settings/Sound/SyncWithRingtone");
                    put("tts_engine_preference", "/Settings/Accessibility/PreferredEngine");
                    put("tts_default_pitch", "/Settings/Accessibility/Pitch");
                    put("tts_default_rate", "/Settings/Accessibility/Rate");
                    put("spen_detachment_sound", "/AirCommand/Spen/SoundEnabled");
                    put("SETTINGS_USE_TOOLBAR", "/HBD/Toolbar/UseToolbar");
                    put("SETTINGS_PHYSICAL_KEYBOARD_TOOLBAR", "/HBD/PhysicalKeyboardToolbar");
                }
            };
    public ActivityManager mActivityManager;
    public Context mContext;
    public EnterpriseDeviceManager mEdm;
    public boolean mGalaxyAIStatus;
    public Injector mInjector;
    public PackageManager mPackageManager;
    public Bundle mRestrictionsBundle;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Injector {
        public final Context mContext;

        public Injector(Context context) {
            this.mContext = context;
        }
    }

    public KnoxRestrictionsService() {
        super("KnoxRestrictionsService");
        this.mGalaxyAIStatus = false;
    }

    public static Scene buildScene(Bundle bundle, String str) {
        Scene.Builder builder = new Scene.Builder(str);
        for (String str2 : bundle.keySet()) {
            if ("value".equals(str2)) {
                builder.setValue(bundle.getString(str2), false);
            } else {
                builder.addAttribute(bundle.getString(str2), str2);
            }
        }
        return builder.build();
    }

    public final void disableSuggestion(Bundle bundle) {
        if (bundle == null || bundle.isEmpty()) {
            setComponentEnabledSetting(
                    new ComponentName(
                            this.mContext.getPackageName(),
                            Settings.KnoxRestrictionsActivity.class.getName()),
                    false);
        } else if (this.mRestrictionsBundle.containsKey("disable_suggestion")
                && "1"
                        .equals(
                                this.mRestrictionsBundle
                                        .getBundle("disable_suggestion")
                                        .getString("value"))) {
            setComponentEnabledSetting(
                    new ComponentName(
                            this.mContext.getPackageName(),
                            Settings.KnoxRestrictionsActivity.class.getName()),
                    false);
        } else {
            setComponentEnabledSetting(
                    new ComponentName(
                            this.mContext.getPackageName(),
                            Settings.KnoxRestrictionsActivity.class.getName()),
                    true);
        }
    }

    @Override // android.app.IntentService, android.app.Service
    public final void onCreate() {
        super.onCreate();
        KnoxRestrictionsFileLog.d("KnoxRestrictionsService", "onCreate()");
    }

    @Override // android.app.IntentService, android.app.Service
    public final void onDestroy() {
        super.onDestroy();
        KnoxRestrictionsFileLog.d("KnoxRestrictionsService", "onDestroy()");
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x015e, code lost:

       if (r0.contains("tablet") != false) goto L41;
    */
    @Override // android.app.IntentService
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onHandleIntent(android.content.Intent r18) {
        /*
            Method dump skipped, instructions count: 701
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.eternal.KnoxRestrictionsService.onHandleIntent(android.content.Intent):void");
    }

    @Override // android.app.IntentService, android.app.Service
    public final int onStartCommand(Intent intent, int i, int i2) {
        KnoxRestrictionsFileLog.d("KnoxRestrictionsService", "onStartCommand()");
        Context baseContext = getBaseContext();
        this.mInjector = new Injector(baseContext);
        this.mContext = (Context) Preconditions.checkNotNull(baseContext);
        this.mPackageManager =
                (PackageManager)
                        Preconditions.checkNotNull(this.mInjector.mContext.getPackageManager());
        this.mActivityManager =
                (ActivityManager)
                        Preconditions.checkNotNull(
                                (ActivityManager)
                                        this.mInjector.mContext.getSystemService("activity"));
        this.mEdm =
                (EnterpriseDeviceManager)
                        Preconditions.checkNotNull(
                                EnterpriseDeviceManager.getInstance(this.mInjector.mContext));
        return super.onStartCommand(intent, i, i2);
    }

    public final void preventForceStopSetting(boolean z) {
        try {
            ArrayList arrayList = new ArrayList();
            arrayList.add(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
            if (z) {
                KnoxRestrictionsFileLog.d(
                        "KnoxRestrictionsService", "addPackagesToForceStopBlackList");
                this.mEdm.getApplicationPolicy().addPackagesToForceStopBlackList(arrayList);
            } else {
                KnoxRestrictionsFileLog.d(
                        "KnoxRestrictionsService", "removePackagesFromForceStopBlackList");
                this.mEdm.getApplicationPolicy().removePackagesFromForceStopBlackList(arrayList);
            }
        } catch (Exception e) {
            String str = "failed to preventForceStopSetting " + e;
            KnoxRestrictionsFileLog.fileLog("KnoxRestrictionsService", str);
            SemLog.e("KnoxRestrictionsService", str);
        }
    }

    public final void setComponentEnabledSetting(ComponentName componentName, boolean z) {
        try {
            int componentEnabledSetting =
                    this.mPackageManager.getComponentEnabledSetting(componentName);
            if ((componentEnabledSetting == 1) != z || componentEnabledSetting == 0) {
                this.mPackageManager.setComponentEnabledSetting(componentName, z ? 1 : 2, 1);
            }
        } catch (IllegalArgumentException e) {
            String str = "failed to setComponentEnabledSetting " + e;
            KnoxRestrictionsFileLog.fileLog("KnoxRestrictionsService", str);
            SemLog.e("KnoxRestrictionsService", str);
        }
    }
}
