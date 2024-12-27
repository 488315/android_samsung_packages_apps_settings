package com.samsung.android.settings.bixby.control;

import android.util.Pair;

import com.android.settings.network.telephony.ToggleSubscriptionDialogActivity;

import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import com.samsung.android.settings.usefulfeature.videoenhancer.VideoEnhancerPreferenceController;
import com.sec.ims.IMSParameter;

import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ControlFactory {
    public Map mActionMap;
    public Map mCommandMap;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class LazyHolder {
        public static final ControlFactory sControlFactory;

        static {
            ControlFactory controlFactory = new ControlFactory();
            HashMap hashMap = new HashMap();
            controlFactory.mCommandMap = hashMap;
            HashMap hashMap2 = new HashMap();
            controlFactory.mActionMap = hashMap2;
            hashMap.put("bixby.settingsApp.GetOnOff_AOD", new Pair("get", "always_on_screen"));
            hashMap.put("bixby.settingsApp.SetOnOff_AOD", new Pair("set", "always_on_screen"));
            hashMap.put("bixby.settingsApp.Goto_AODSetting", new Pair("goto", "always_on_screen"));
            hashMap.put("bixby.settingsApp.GetOnOff_Edge", new Pair("get", "edge_screen"));
            hashMap.put("bixby.settingsApp.SetOnOff_Edge", new Pair("set", "edge_screen"));
            hashMap.put("bixby.settingsApp.Goto_EdgeSetting", new Pair("goto", "edge_screen"));
            hashMap.put(
                    "bixby.settingsApp.Goto_AccountSetting",
                    new Pair("goto", "account_preference"));
            hashMap.put(
                    "bixby.settingsApp.Goto_CloudAndAccountsSetting",
                    new Pair("goto", "top_level_accounts_backup"));
            hashMap.put("bixby.settingsApp.GetOnOff_AdaptiveColorTone", new Pair("get", "sec_ead"));
            hashMap.put("bixby.settingsApp.SetOnOff_AdaptiveColorTone", new Pair("set", "sec_ead"));
            hashMap.put(
                    "bixby.settingsApp.Goto_AdaptiveColorToneSetting", new Pair("goto", "sec_ead"));
            hashMap.put(
                    "bixby.settingsApp.Goto_AdvancedFeaturesSetting",
                    new Pair("goto", "top_level_advanced_features"));
            hashMap.put("bixby.settingsApp.GetOnOff_Airplane", new Pair("get", "toggle_airplane"));
            hashMap.put(
                    "bixby.settingsApp.Enable_Airplane",
                    new Pair(ToggleSubscriptionDialogActivity.ARG_enable, "toggle_airplane"));
            hashMap.put("bixby.settingsApp.SetOnOff_Airplane", new Pair("set", "toggle_airplane"));
            hashMap.put(
                    "bixby.settingsApp.Goto_AirplaneSetting", new Pair("goto", "toggle_airplane"));
            hashMap.put(
                    "bixby.settingsApp.GetOnOff_AutoBrightness",
                    new Pair("get", "auto_brightness"));
            hashMap.put(
                    "bixby.settingsApp.SetOnOff_AutoBrightness",
                    new Pair("set", "auto_brightness"));
            hashMap.put(
                    "bixby.settingsApp.Goto_AutoBrightnessSetting",
                    new Pair("goto", "auto_brightness"));
            hashMap.put(
                    "bixby.settingsApp.GetOnOff_AutoBrightnessCover",
                    new Pair("get", "auto_brightness_cover"));
            hashMap.put(
                    "bixby.settingsApp.SetOnOff_AutoBrightnessCover",
                    new Pair("set", "auto_brightness_cover"));
            hashMap.put(
                    "bixby.settingsApp.Goto_AutoBrightnessCover",
                    new Pair("goto", "auto_brightness_cover"));
            hashMap.put(
                    "bixby.settingsApp.GetOnOff_BlueLight", new Pair("get", "blue_light_filter"));
            hashMap.put(
                    "bixby.settingsApp.SetOnOff_BlueLight", new Pair("set", "blue_light_filter"));
            hashMap.put(
                    "bixby.settingsApp.Goto_BlueLightSetting",
                    new Pair("goto", "blue_light_filter"));
            hashMap.put("bixby.settingsApp.GetOnOff_NightTheme", new Pair("get", "dark_mode"));
            hashMap.put("bixby.settingsApp.SetOnOff_NightTheme", new Pair("set", "dark_mode"));
            hashMap.put("bixby.settingsApp.Goto_NightThemeSetting", new Pair("goto", "dark_mode"));
            hashMap.put(
                    "bixby.settingsApp.Goto_DirectShareSetting", new Pair("goto", "direct_share"));
            hashMap.put(
                    "bixby.settingsApp.Goto_DualMessengerSetting", new Pair("goto", "dual_app"));
            hashMap.put("bixby.settingsApp.Goto_EasyMuteSetting", new Pair("goto", "easy_mute"));
            hashMap.put(
                    "bixby.settingsApp.Goto_FactoryDataResetSetting",
                    new Pair("goto", "factory_reset"));
            hashMap.put(
                    "bixby.settingsApp.Goto_FullScreenAppsSetting",
                    new Pair("goto", "full_screen_apps"));
            hashMap.put(
                    "bixby.settingsApp.GetOnOff_Location", new Pair("get", "location_settings"));
            hashMap.put(
                    "bixby.settingsApp.SetOnOff_Location", new Pair("set", "location_settings"));
            hashMap.put(
                    "bixby.settingsApp.Goto_LocationSetting",
                    new Pair("goto", "location_settings"));
            hashMap.put(
                    "bixby.settingsApp.GetOnOff_MobileData", new Pair("get", "data_usage_enable"));
            hashMap.put(
                    "bixby.settingsApp.SetOnOff_MobileData", new Pair("set", "data_usage_enable"));
            hashMap.put(
                    "bixby.settingsApp.Disable_MobileData",
                    new Pair("disable", "data_usage_enable"));
            hashMap.put(
                    "bixby.settingsApp.Goto_MobileDataSetting",
                    new Pair("goto", "data_usage_enable"));
            hashMap.put(
                    "bixby.settingsApp.Goto_NavigationBarSetting",
                    new Pair("goto", "navigation_Bar"));
            hashMap.put(
                    "bixby.settingsApp.Goto_SwipeToCaptureSetting",
                    new Pair("goto", "palm_swipe_to_capture"));
            hashMap.put(
                    "bixby.settingsApp.GetOnOff_PinWindows",
                    new Pair("get", "screen_pinning_settings"));
            hashMap.put(
                    "bixby.settingsApp.SetOnOff_PinWindows",
                    new Pair("set", "screen_pinning_settings"));
            hashMap.put(
                    "bixby.settingsApp.Goto_ScreenModeSetting", new Pair("goto", "screen_mode"));
            hashMap.put(
                    "bixby.settingsApp.Goto_ScreenResolutionSetting",
                    new Pair("goto", "screen_resolution"));
            hashMap.put(
                    "bixby.settingsApp.Set_ScreenResolution",
                    new Pair("goto", "screen_resolution"));
            hashMap.put("bixby.settingsApp.Get_ScreenTimeOut", new Pair("get", "screen_timeout"));
            hashMap.put("bixby.settingsApp.Set_ScreenTimeOut", new Pair("set", "screen_timeout"));
            hashMap.put(
                    "bixby.settingsApp.Goto_ResetAllSettingsToDefaultSetting",
                    new Pair("goto", "settings_reset"));
            hashMap.put(
                    "bixby.settingsApp.Goto_SideKeySetting",
                    new Pair("goto", "function_key_setting"));
            hashMap.put(
                    "bixby.settingsApp.Goto_SmartAlertSetting", new Pair("goto", "smart_alert"));
            hashMap.put(
                    "bixby.settingsApp.Goto_SoftwareUpdateSetting",
                    new Pair("goto", "software_update_for_bixby"));
            hashMap.put(
                    "bixby.settingsApp.Goto_SendSOSMessagesSetting",
                    new Pair("goto", "master_switch_preference_key"));
            hashMap.put(
                    "bixby.settingsApp.GetOnOff_Synchronize",
                    new Pair("get", "auto_sync_account_data"));
            hashMap.put(
                    "bixby.settingsApp.SetOn_Synchronize",
                    new Pair("set", "auto_sync_account_data"));
            hashMap.put(
                    "bixby.settingsApp.SetOff_Synchronize",
                    new Pair("set", "auto_sync_account_data"));
            hashMap.put(
                    "bixby.settingsApp.Goto_SynchronizeSetting",
                    new Pair("goto", "auto_sync_account_data"));
            hashMap.put(
                    "bixby.settingsApp.Goto_VideoEnhancerSetting",
                    new Pair("goto", VideoEnhancerPreferenceController.KEY_HDR_EFFECT));
            hashMap.put("bixby.settingsApp.GetOnOff_SmartStay", new Pair("get", "smart_stay"));
            hashMap.put("bixby.settingsApp.SetOnOff_SmartStay", new Pair("set", "smart_stay"));
            hashMap.put("bixby.settingsApp.GOTO_SmartStaySetting", new Pair("goto", "smart_stay"));
            hashMap.put("SetVolumeLevel", new Pair("change", "volume_control"));
            hashMap.put("SetVolumeLevelRingtones", new Pair("change", "volume_control_ringtone"));
            hashMap.put("SetVolumeLevelMedia", new Pair("change", "volume_control_media"));
            hashMap.put("SetVolumeLevelSystem", new Pair("change", "volume_control_system"));
            hashMap.put("SetVolumeLevelNoti", new Pair("change", "volume_control_noti"));
            hashMap.put("SetVolumeLevelBixby", new Pair("change", "volume_control_bixby"));
            hashMap.put("SetVolumeLevelBluetooth", new Pair("change", "volume_control_bluetooth"));
            hashMap.put("GetVolumeLevel", new Pair("get", "volume_control"));
            hashMap.put("GetVolumeLevelRingtones", new Pair("get", "volume_control_ringtone"));
            hashMap.put("GetVolumeLevelMedia", new Pair("get", "volume_control_media"));
            hashMap.put("GetVolumeLevelSystem", new Pair("get", "volume_control_system"));
            hashMap.put("GetVolumeLevelNoti", new Pair("get", "volume_control_noti"));
            hashMap.put("GetVolumeLevelBixby", new Pair("get", "volume_control_bixby"));
            hashMap.put("GetVolumeLevelBluetooth", new Pair("get", "volume_control_bluetooth"));
            hashMap.put("MuteVolume", new Pair("set", "mute_volume"));
            hashMap.put("MuteVolumeAll", new Pair("set", "mute_all_volume"));
            hashMap.put("MuteVolumeRingtones", new Pair("set", "mute_ringtones_volume"));
            hashMap.put("MuteVolumeMedia", new Pair("set", "mute_media_volume"));
            hashMap.put("MuteVolumeSystem", new Pair("set", "mute_system_volume"));
            hashMap.put("MuteVolumeNoti", new Pair("set", "mute_noti_volume"));
            hashMap.put("MuteVolumeBixby", new Pair("set", "mute_bixby_volume"));
            hashMap.put("MuteVolumeBluetooth", new Pair("set", "mute_bluetooth_volume"));
            hashMap.put("UnMuteVolume", new Pair("set", "mute_volume"));
            hashMap.put("UnMuteVolumeAll", new Pair("set", "mute_all_volume"));
            hashMap.put("UnMuteVolumeRingtones", new Pair("set", "mute_ringtones_volume"));
            hashMap.put("UnMuteVolumeMedia", new Pair("set", "mute_media_volume"));
            hashMap.put("UnMuteVolumeSystem", new Pair("set", "mute_system_volume"));
            hashMap.put("UnMuteVolumeNoti", new Pair("set", "mute_noti_volume"));
            hashMap.put("UnMuteVolumeBixby", new Pair("set", "mute_bixby_volume"));
            hashMap.put("UnMuteVolumeBluetooth", new Pair("set", "mute_bluetooth_volume"));
            hashMap.put("CaptureScreen", new Pair(SignalSeverity.NONE, "capture_screen"));
            hashMap.put("ShareScreenshot", new Pair(SignalSeverity.NONE, "share_screenshot"));
            hashMap.put(
                    "ShareScreenshotUri", new Pair(SignalSeverity.NONE, "share_screenshot_uri"));
            hashMap.put("GoToHomeScreen", new Pair(SignalSeverity.NONE, "goto_homescreen"));
            hashMap.put("Back", new Pair(SignalSeverity.NONE, "back"));
            hashMap.put("IncreaseBrightness", new Pair("change", "set_brightness"));
            hashMap.put("DecreaseBrightness", new Pair("change", "set_brightness"));
            hashMap.put("SetBrightness", new Pair("change", "set_brightness"));
            hashMap.put("ScrollUp", new Pair(SignalSeverity.NONE, "scroll_up_down"));
            hashMap.put("ScrollDown", new Pair(SignalSeverity.NONE, "scroll_up_down"));
            hashMap.put("ScrollLeft", new Pair(SignalSeverity.NONE, "scroll_up_down"));
            hashMap.put("ScrollRight", new Pair(SignalSeverity.NONE, "scroll_up_down"));
            hashMap.put("Goto_EdgeLighting", new Pair(SignalSeverity.NONE, "goto_edgelighting"));
            hashMap.put("DummySystem", new Pair("goto", "dummy_system"));
            hashMap.put("PowerOff", new Pair(SignalSeverity.NONE, "power_off"));
            hashMap.put("Reboot", new Pair(SignalSeverity.NONE, "reboot"));
            hashMap.put("TurnOnFlashlight", new Pair("set", "set_flashlight"));
            hashMap.put("TurnOffFlashlight", new Pair("set", "set_flashlight"));
            hashMap.put("SetFlashLightLevel", new Pair("set", "set_flashlight_level"));
            hashMap.put("GoToFlashLightSetting", new Pair("goto", "set_flashlight"));
            hashMap.put("TurnOffScreen", new Pair(SignalSeverity.NONE, "turnoff_screen"));
            hashMap.put("TurnOnAutoRotate", new Pair("set", "set_autorotate"));
            hashMap.put("TurnOffAutoRotate", new Pair("set", "set_autorotate"));
            hashMap.put("SetLandscapeMode", new Pair(SignalSeverity.NONE, "set_landscapemode"));
            hashMap.put("SetPortraitMode", new Pair(SignalSeverity.NONE, "set_portraitmode"));
            hashMap.put("CoverFlexDoubleTab", new Pair(SignalSeverity.NONE, "doubletab_coverflex"));
            hashMap.put("DeleteNotification", new Pair(SignalSeverity.NONE, "delete_notification"));
            hashMap.put(
                    "OpenNotificationPanel",
                    new Pair(SignalSeverity.NONE, "open_notification_panel"));
            hashMap.put(
                    "CloseQuickPanelScreen", new Pair(SignalSeverity.NONE, "close_panelscreen"));
            hashMap.put("ReadNotificationWithID", new Pair("get", "read_notification_withid"));
            hashMap.put("ReplyNotification", new Pair(SignalSeverity.NONE, "reply_notification"));
            hashMap.put(
                    "AppNotificationPermissionSetting",
                    new Pair(SignalSeverity.NONE, "set_notification_permission"));
            hashMap.put(
                    "AppNotificationPermissionSettingOff",
                    new Pair(SignalSeverity.NONE, "setoff_notification_permission"));
            hashMap.put(
                    "AppNotificationSoundSetting",
                    new Pair(SignalSeverity.NONE, "set_notification_sound"));
            hashMap.put("DiagnoseTroubleShooting", new Pair("set", "query_notification_settings"));
            hashMap.put("LaunchApplication", new Pair(SignalSeverity.NONE, "launch_application"));
            hashMap.put("CloseApplication", new Pair(SignalSeverity.NONE, "close_application"));
            hashMap.put(
                    "CloseForegroundApplication",
                    new Pair(SignalSeverity.NONE, "close_foreground_application"));
            hashMap.put(
                    "CloseAllApplications", new Pair(SignalSeverity.NONE, "close_all_application"));
            hashMap.put("StartMultiWindow", new Pair(SignalSeverity.NONE, "start_multiwindow"));
            hashMap.put(
                    "CloseAllBackgroundApps",
                    new Pair(SignalSeverity.NONE, "close_all_application_except_currentapp"));
            hashMap.put(
                    "CloseAllAppsExceptSpecificOnes",
                    new Pair(SignalSeverity.NONE, "close_all_application_except_specificapp"));
            hashMap.put("OpenRecentsApp", new Pair(SignalSeverity.NONE, "open_recentsapp"));
            hashMap.put("AppResizable", new Pair("get", "app_resizable"));
            hashMap.put(
                    "LaunchMostRecentApplication",
                    new Pair(SignalSeverity.NONE, "launch_mostrecent_application"));
            hashMap.put(
                    "CloseMultipleApplications",
                    new Pair(SignalSeverity.NONE, "close_multiple_application"));
            hashMap.put(
                    "StartAppSplitPosition",
                    new Pair(SignalSeverity.NONE, "startapp_splitposition"));
            hashMap.put(
                    "ExchangePositionOfSplitScreen",
                    new Pair(SignalSeverity.NONE, "exchange_position_splitscreen"));
            hashMap.put(
                    "ChangeLayoutOfSplitScreen",
                    new Pair(SignalSeverity.NONE, "change_layout_splitscreen"));
            hashMap.put(
                    "ReplaceAppOfSplitScreen",
                    new Pair(SignalSeverity.NONE, "replaceapp_splitscreen"));
            hashMap.put("MaximizeApp", new Pair(SignalSeverity.NONE, "maximize_app"));
            hashMap.put("CheckOrientation", new Pair("get", "check_orientation"));
            hashMap.put("CheckSplitType", new Pair("get", "check_splittype"));
            hashMap.put("CheckSplitState", new Pair("get", "check_splitstate"));
            hashMap.put("CheckLauncherVisible", new Pair("get", "check_launchervisible"));
            hashMap.put("GetPackageInSplit", new Pair("get", "get_packageinsplit"));
            hashMap.put("PlayMusic", new Pair("set", "control_music"));
            hashMap.put("StopMusic", new Pair("set", "control_music"));
            hashMap.put("SkipSong", new Pair("set", "control_music"));
            hashMap.put("PreviousSong", new Pair("set", "control_music"));
            hashMap.put("ReplayCurrentSong", new Pair("set", "control_music"));
            hashMap.put("MoveFromCurrentPosition", new Pair("set", "control_music"));
            hashMap.put("SeekTo", new Pair("set", "control_music"));
            hashMap.put("FastForward", new Pair("set", "control_music"));
            hashMap.put("Rewind", new Pair("set", "control_music"));
            hashMap.put("FindAppInfo", new Pair("get", "find_appinfo"));
            hashMap2.put("bixby.settingsApp.GetOnOff_NoDisturb", new Pair("get", "do_not_disturb"));
            hashMap2.put("bixby.settingsApp.SetOnOff_NoDisturb", new Pair("set", "do_not_disturb"));
            hashMap2.put(
                    "bixby.settingsApp.Goto_NoDisturbSetting", new Pair("goto", "do_not_disturb"));
            hashMap2.put(
                    "bixby.settingsApp.Get_JumpToAppResult",
                    new Pair("get", "get_jump_to_app_result"));
            hashMap2.put(
                    "bixby.settingsApp.Goto_JumpToAppMenu",
                    new Pair("goto", "goto_jump_to_app_menu"));
            hashMap2.put("bixby.settingsApp.Get_FontSize", new Pair("get", "font_size"));
            hashMap2.put("bixby.settingsApp.ChangeFontSize", new Pair("change", "font_size"));
            hashMap2.put("bixby.settingsApp.Goto_FontSetting", new Pair("goto", "font_size"));
            hashMap2.put(
                    "bixby.settingsApp.Get_MuteDuration",
                    new Pair("get", "temporary_mute_duration"));
            hashMap2.put(
                    "bixby.settingsApp.Set_MuteDuration",
                    new Pair("set", "temporary_mute_duration"));
            hashMap2.put(
                    "bixby.settingsApp.Goto_MuteDurationSetting",
                    new Pair("goto", "temporary_mute_duration"));
            hashMap2.put("bixby.settingsApp.Get_ScreenZoom", new Pair("get", "screen_zoom"));
            hashMap2.put("bixby.settingsApp.ChangeScreenZoom", new Pair("change", "screen_zoom"));
            hashMap2.put(
                    "bixby.settingsApp.Goto_ScreenZoomSetting", new Pair("goto", "screen_zoom"));
            hashMap2.put("bixby.settingsApp.Get_SoundMode", new Pair("get", "sound_mode"));
            hashMap2.put("bixby.settingsApp.Set_SoundMode", new Pair("set", "sound_mode"));
            hashMap2.put("bixby.settingsApp.Goto_SoundModeSetting", new Pair("goto", "sound_mode"));
            hashMap2.put("bixby.settingsApp.GetOnOff_ScreenSaver", new Pair("get", "screen_saver"));
            hashMap2.put("bixby.settingsApp.SetOnOff_ScreenSaver", new Pair("set", "screen_saver"));
            hashMap2.put(
                    "bixby.settingsApp.Goto_ScreenSaverSetting", new Pair("goto", "screen_saver"));
            hashMap2.put("bixby.settingsApp.Get_WhiteBalance", new Pair("get", "white_balance"));
            hashMap2.put(
                    "bixby.settingsApp.ChangeWhiteBalance", new Pair("change", "white_balance"));
            hashMap2.put(
                    "bixby.settingsApp.Goto_WhiteBalanceSetting",
                    new Pair("goto", "white_balance"));
            hashMap2.put(
                    "bixby.settingsApp.Goto_LockScreenSetting", new Pair("goto", "lock_screen"));
            hashMap2.put(
                    "bixby.settingsApp.Goto_RingtoneScreenSetting", new Pair("goto", "ringtone"));
            hashMap2.put("bixby.settingsApp.Goto_MainSetting", new Pair("goto", "settings_main"));
            hashMap2.put(
                    "bixby.settingsApp.Goto_SoundVibrateSetting",
                    new Pair("goto", "sound_and_vibration"));
            hashMap2.put(
                    "bixby.settingsApp.Goto_VibratePatternSetting",
                    new Pair("goto", "vibration_pattern"));
            hashMap2.put("bixby.settingsApp.Goto_DisplaySetting", new Pair("goto", "display"));
            hashMap2.put("bixby.settingsApp.Goto_StatusBarSetting", new Pair("goto", "status_bar"));
            hashMap2.put(
                    "bixby.settingsApp.Goto_OneHandModeSetting", new Pair("goto", "onehand_mode"));
            hashMap2.put(
                    "bixby.settingsApp.Goto_FingerSensorGesturesSetting",
                    new Pair("goto", "finger_sensor_gesture"));
            hashMap2.put(
                    "bixby.settingsApp.Goto_SmartCaptureSetting",
                    new Pair("goto", "smart_capture"));
            hashMap2.put("bixby.settingsApp.Goto_ApplicationMenuSetting", new Pair("goto", "apps"));
            hashMap2.put(
                    "bixby.settingsApp.Goto_GeneralManagementSetting",
                    new Pair("goto", "general_management"));
            hashMap2.put(
                    "bixby.settingsApp.Goto_LanguageSetting",
                    new Pair("goto", "language_and_input"));
            hashMap2.put("bixby.settingsApp.Goto_ChangeLanguage", new Pair("goto", "language"));
            hashMap2.put(
                    "bixby.settingsApp.Goto_ScreenKeyboardSetting",
                    new Pair("goto", "screen_keyboard"));
            hashMap2.put("bixby.settingsApp.Goto_ResetSetting", new Pair("goto", "reset_settings"));
            hashMap2.put(
                    "bixby.settingsApp.Goto_AboutDeviceSetting", new Pair("goto", "about_device"));
            hashMap2.put(
                    "bixby.settingsApp.Goto_BiometricsAndSecuritySetting",
                    new Pair("goto", "biometrics_and_security"));
            hashMap2.put(
                    "bixby.settingsApp.Goto_StatusSetting",
                    new Pair("goto", IMSParameter.CALL.STATUS));
            hashMap2.put(
                    "bixby.settingsApp.Goto_FingerprintsSetting",
                    new Pair("goto", "finger_prints"));
            hashMap2.put("bixby.settingsApp.Goto_SimStatusSetting", new Pair("goto", "sim_status"));
            hashMap2.put(
                    "bixby.settingsApp.Goto_FaceRecognitionSetting",
                    new Pair("goto", "face_recognition"));
            hashMap2.put("bixby.settingsApp.Goto_HardPressSetting", new Pair("goto", "hard_press"));
            hashMap2.put(
                    "bixby.settingsApp.Goto_NotificationSoundsSetting",
                    new Pair("goto", "notification_sounds"));
            hashMap2.put("bixby.settingsApp.Goto_FontStyleSetting", new Pair("goto", "font_style"));
            hashMap2.put(
                    "bixby.settingsApp.Goto_SamsungKeyboardSetting",
                    new Pair("goto", "samsung_keyboard"));
            hashMap2.put("bixby.settingsApp.Goto_VolumeSetting", new Pair("goto", "volume"));
            hashMap2.put("bixby.settingsApp.Goto_DataUsageSetting", new Pair("goto", "data_usage"));
            hashMap2.put(
                    "bixby.settingsApp.Goto_ScreenTimeOutSetting",
                    new Pair("goto", "screen_timeout"));
            hashMap2.put("bixby.settingsApp.Open_UserManual", new Pair("goto", "user_manual"));
            hashMap2.put(
                    "bixby.settingsApp.CheckColorSettings",
                    new Pair("get", "recover_color_settings"));
            hashMap2.put(
                    "bixby.settingsApp.RecoverColorSettings",
                    new Pair("set", "recover_color_settings"));
            hashMap2.put(
                    "viv.accessibilityApp.OpenNotificationReminder",
                    new Pair("goto", "notification_reminder"));
            hashMap2.put(
                    "viv.accessibilityApp.TurnOnNotificationReminder",
                    new Pair("set", "notification_reminder"));
            hashMap2.put(
                    "viv.accessibilityApp.TurnOffNotificationReminder",
                    new Pair("set", "notification_reminder"));
            sControlFactory = controlFactory;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x004e, code lost:

       if (r9.equals("set") == false) goto L6;
    */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00d9, code lost:

       if (r9.equals("goto") == false) goto L39;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.settings.bixby.control.Control createControl(
            com.samsung.android.settings.bixby.control.actionparam.BaseActionParam r12) {
        /*
            Method dump skipped, instructions count: 388
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.bixby.control.ControlFactory.createControl(com.samsung.android.settings.bixby.control.actionparam.BaseActionParam):com.samsung.android.settings.bixby.control.Control");
    }
}
