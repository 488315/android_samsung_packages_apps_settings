package com.android.settings.core.gateway;

import com.android.settings.DisplaySettings;
import com.android.settings.IccLockSettings;
import com.android.settings.MainClear;
import com.android.settings.MainClearConfirm;
import com.android.settings.ResetNetwork;
import com.android.settings.Settings;
import com.android.settings.TestingSettings;
import com.android.settings.TrustedCredentialsSettings;
import com.android.settings.accessibility.AccessibilityDetailsSettingsFragment;
import com.android.settings.accessibility.AccessibilityHearingAidsFragment;
import com.android.settings.accessibility.AccessibilitySettings;
import com.android.settings.accessibility.AccessibilitySettingsForSetupWizard;
import com.android.settings.accessibility.AutoBrightnessPreferenceFragmentForSetupWizard;
import com.android.settings.accessibility.CaptioningPropertiesFragment;
import com.android.settings.accessibility.ColorAndMotionFragment;
import com.android.settings.accessibility.HearingDevicePairingFragment;
import com.android.settings.accessibility.TextReadingPreferenceFragment;
import com.android.settings.accessibility.TextReadingPreferenceFragmentForSetupWizard;
import com.android.settings.accessibility.ToggleColorInversionPreferenceFragment;
import com.android.settings.accessibility.ToggleDaltonizerPreferenceFragment;
import com.android.settings.accessibility.ToggleReduceBrightColorsPreferenceFragment;
import com.android.settings.accessibility.VibrationIntensitySettingsFragment;
import com.android.settings.accessibility.shortcuts.EditShortcutsPreferenceFragment;
import com.android.settings.accounts.AccountDashboardFragment;
import com.android.settings.accounts.AccountSyncSettings;
import com.android.settings.accounts.ChooseAccountFragment;
import com.android.settings.accounts.ManagedProfileSettings;
import com.android.settings.applications.AppDashboardFragment;
import com.android.settings.applications.ProcessStatsSummary;
import com.android.settings.applications.ProcessStatsUi;
import com.android.settings.applications.UsageAccessDetails;
import com.android.settings.applications.appcompat.UserAspectRatioDetails;
import com.android.settings.applications.appinfo.AlarmsAndRemindersDetails;
import com.android.settings.applications.appinfo.AppInfoDashboardFragment;
import com.android.settings.applications.appinfo.AppLocaleDetails;
import com.android.settings.applications.appinfo.DrawOverlayDetails;
import com.android.settings.applications.appinfo.ExternalSourcesDetails;
import com.android.settings.applications.appinfo.LongBackgroundTasksDetails;
import com.android.settings.applications.appinfo.ManageExternalStorageDetails;
import com.android.settings.applications.appinfo.MediaManagementAppsDetails;
import com.android.settings.applications.appinfo.TurnScreenOnDetails;
import com.android.settings.applications.appinfo.WriteSettingsDetails;
import com.android.settings.applications.appops.BackgroundCheckSummary;
import com.android.settings.applications.assist.ManageAssist;
import com.android.settings.applications.defaultapps.DefaultAutofillPicker;
import com.android.settings.applications.manageapplications.ManageApplications;
import com.android.settings.applications.managedomainurls.ManageDomainUrls;
import com.android.settings.applications.specialaccess.deviceadmin.DeviceAdminSettings;
import com.android.settings.applications.specialaccess.interactacrossprofiles.InteractAcrossProfilesDetails;
import com.android.settings.applications.specialaccess.interactacrossprofiles.InteractAcrossProfilesSettings;
import com.android.settings.applications.specialaccess.notificationaccess.NotificationAccessDetails;
import com.android.settings.applications.specialaccess.pictureinpicture.PictureInPictureDetails;
import com.android.settings.applications.specialaccess.pictureinpicture.PictureInPictureSettings;
import com.android.settings.applications.specialaccess.premiumsms.PremiumSmsAccess;
import com.android.settings.applications.specialaccess.vrlistener.VrListenerSettings;
import com.android.settings.applications.specialaccess.zenaccess.ZenAccessDetails;
import com.android.settings.backup.PrivacySettings;
import com.android.settings.backup.ToggleBackupSettingFragment;
import com.android.settings.backup.UserBackupSettingsActivity;
import com.android.settings.bluetooth.BluetoothBroadcastDialog;
import com.android.settings.bluetooth.BluetoothDeviceDetailsFragment;
import com.android.settings.bluetooth.BluetoothFindBroadcastsFragment;
import com.android.settings.bluetooth.BluetoothPairingDetail;
import com.android.settings.bugreporthandler.BugReportHandlerPicker;
import com.android.settings.communal.CommunalDashboardFragment;
import com.android.settings.connecteddevice.AdvancedConnectedDeviceDashboardFragment;
import com.android.settings.connecteddevice.BluetoothDashboardFragment;
import com.android.settings.connecteddevice.ConnectedDeviceDashboardFragment;
import com.android.settings.connecteddevice.NfcAndPaymentFragment;
import com.android.settings.connecteddevice.PreviouslyConnectedDeviceDashboardFragment;
import com.android.settings.connecteddevice.stylus.StylusUsiDetailsFragment;
import com.android.settings.connecteddevice.usb.UsbDetailsFragment;
import com.android.settings.datausage.DataSaverSummary;
import com.android.settings.datausage.DataUsageList;
import com.android.settings.datausage.DataUsageSummary;
import com.android.settings.datetime.DateTimeSettings;
import com.android.settings.deletionhelper.AutomaticStorageManagerSettings;
import com.android.settings.development.DevelopmentSettingsDashboardFragment;
import com.android.settings.deviceinfo.PrivateVolumeForget;
import com.android.settings.deviceinfo.PublicVolumeSettings;
import com.android.settings.deviceinfo.StorageDashboardFragment;
import com.android.settings.deviceinfo.firmwareversion.FirmwareVersionSettings;
import com.android.settings.deviceinfo.legal.ModuleLicensesDashboard;
import com.android.settings.display.AutoBrightnessSettings;
import com.android.settings.display.ColorContrastFragment;
import com.android.settings.display.NightDisplaySettings;
import com.android.settings.display.ScreenTimeoutSettings;
import com.android.settings.display.SmartAutoRotatePreferenceFragment;
import com.android.settings.display.darkmode.DarkModeSettingsFragment;
import com.android.settings.dream.DreamSettings;
import com.android.settings.enterprise.EnterprisePrivacySettings;
import com.android.settings.fuelgauge.AdvancedPowerUsageDetail;
import com.android.settings.fuelgauge.batterysaver.BatterySaverScheduleSettings;
import com.android.settings.fuelgauge.batterysaver.BatterySaverSettings;
import com.android.settings.fuelgauge.batteryusage.PowerUsageSummary;
import com.android.settings.gestures.ButtonNavigationSettingsFragment;
import com.android.settings.gestures.DoubleTapPowerSettings;
import com.android.settings.gestures.DoubleTapScreenSettings;
import com.android.settings.gestures.DoubleTwistGestureSettings;
import com.android.settings.gestures.GestureNavigationSettingsFragment;
import com.android.settings.gestures.OneHandedSettings;
import com.android.settings.gestures.PickupGestureSettings;
import com.android.settings.gestures.PowerMenuSettings;
import com.android.settings.gestures.SwipeToNotificationSettings;
import com.android.settings.gestures.SystemNavigationGestureSettings;
import com.android.settings.inputmethod.AvailableVirtualKeyboardFragment;
import com.android.settings.inputmethod.KeyboardLayoutPickerFragment;
import com.android.settings.inputmethod.KeyboardSettings;
import com.android.settings.inputmethod.ModifierKeysSettings;
import com.android.settings.inputmethod.NewKeyboardLayoutEnabledLocalesFragment;
import com.android.settings.inputmethod.SpellCheckersSettings;
import com.android.settings.inputmethod.TrackpadSettings;
import com.android.settings.inputmethod.UserDictionaryList;
import com.android.settings.inputmethod.UserDictionarySettings;
import com.android.settings.language.LanguageSettings;
import com.android.settings.localepicker.LocaleListEditor;
import com.android.settings.location.LocationServices;
import com.android.settings.location.LocationSettings;
import com.android.settings.location.WifiScanningFragment;
import com.android.settings.network.MobileNetworkListFragment;
import com.android.settings.network.NetworkDashboardFragment;
import com.android.settings.network.NetworkProviderSettings;
import com.android.settings.network.apn.ApnEditor;
import com.android.settings.network.apn.ApnSettings;
import com.android.settings.network.telephony.CellularSecuritySettingsFragment;
import com.android.settings.network.telephony.MobileNetworkSettings;
import com.android.settings.network.telephony.NetworkSelectSettings;
import com.android.settings.network.telephony.SatelliteSetting;
import com.android.settings.network.tether.TetherSettings;
import com.android.settings.notification.ConfigureNotificationSettings;
import com.android.settings.notification.NotificationAccessSettings;
import com.android.settings.notification.NotificationAssistantPicker;
import com.android.settings.notification.SoundSettings;
import com.android.settings.notification.app.AppBubbleNotificationSettings;
import com.android.settings.notification.app.AppChannelListSettings;
import com.android.settings.notification.app.AppNotificationSettings;
import com.android.settings.notification.app.ChannelNotificationSettings;
import com.android.settings.notification.app.ConversationListSettings;
import com.android.settings.notification.history.NotificationStation;
import com.android.settings.notification.zen.ZenAccessSettings;
import com.android.settings.notification.zen.ZenModeAutomationSettings;
import com.android.settings.notification.zen.ZenModeBlockedEffectsSettings;
import com.android.settings.notification.zen.ZenModeEventRuleSettings;
import com.android.settings.notification.zen.ZenModeScheduleRuleSettings;
import com.android.settings.notification.zen.ZenModeSettings;
import com.android.settings.password.ChooseLockPassword;
import com.android.settings.password.ChooseLockPattern;
import com.android.settings.print.PrintJobSettingsFragment;
import com.android.settings.print.PrintSettingsFragment;
import com.android.settings.privacy.PrivacyControlsFragment;
import com.android.settings.privacy.PrivacyDashboardFragment;
import com.android.settings.privatespace.delete.PrivateSpaceDeleteFragment;
import com.android.settings.privatespace.delete.PrivateSpaceDeletionProgressFragment;
import com.android.settings.privatespace.onelock.PrivateSpaceBiometricSettings;
import com.android.settings.regionalpreferences.RegionalPreferencesEntriesFragment;
import com.android.settings.safetycenter.MoreSecurityPrivacyFragment;
import com.android.settings.security.LockscreenDashboardFragment;
import com.android.settings.security.MemtagPage;
import com.android.settings.security.SecurityAdvancedSettings;
import com.android.settings.security.SecuritySettings;
import com.android.settings.shortcut.CreateShortcut;
import com.android.settings.sound.MediaControlsSettings;
import com.android.settings.support.SupportDashboardActivity;
import com.android.settings.system.ResetDashboardFragment;
import com.android.settings.system.SystemDashboardFragment;
import com.android.settings.tts.TextToSpeechSettings;
import com.android.settings.users.UserSettings;
import com.android.settings.vpn2.VpnSettings;
import com.android.settings.wallpaper.WallpaperTypeSettings;
import com.android.settings.webview.WebViewAppPicker;
import com.android.settings.wfd.WifiDisplaySettings;
import com.android.settings.wifi.ConfigureWifiSettings;
import com.android.settings.wifi.WifiAPITest;
import com.android.settings.wifi.WifiInfo;
import com.android.settings.wifi.WifiSettings;
import com.android.settings.wifi.calling.WifiCallingDisclaimerFragment;
import com.android.settings.wifi.calling.WifiCallingSettings;
import com.android.settings.wifi.details.WifiNetworkDetailsFragment;
import com.android.settings.wifi.p2p.WifiP2pSettings;
import com.android.settings.wifi.savedaccesspoints2.SavedAccessPointsWifiSettings2;
import com.android.settings.wifi.tether.WifiTetherSettings;

import com.samsung.android.settings.accessibility.advanced.shortcut.AccessibilityShortcutEditRequestFragment;
import com.samsung.android.settings.accessibility.dexterity.TouchAndHoldFragment;
import com.samsung.android.settings.accessibility.hearing.AmplifyAmbientSoundPreferenceFragment;
import com.samsung.android.settings.accessibility.hearing.HearingEnhancementsFragment;
import com.samsung.android.settings.accessibility.hearing.MuteAllSoundFragment;
import com.samsung.android.settings.accessibility.home.SecAccessibilitySettings;
import com.samsung.android.settings.accessibility.home.SecAccessibilitySettingsForSetupWizard;
import com.samsung.android.settings.accessibility.vision.HighContrastFontFragment;
import com.samsung.android.settings.accessibility.vision.MagnificationTypeFragment;
import com.samsung.android.settings.accessibility.vision.color.ColorAdjustmentMainFragment;
import com.samsung.android.settings.accessibility.vision.color.ColorLensFragment;
import com.samsung.android.settings.accessories.DockSettings;
import com.samsung.android.settings.account.CloudAccountSettings;
import com.samsung.android.settings.activekey.ActiveKeyAppGridView;
import com.samsung.android.settings.activekey.ActiveKeySettings;
import com.samsung.android.settings.activekey.XcoverTopKeySettings;
import com.samsung.android.settings.applications.appinfo.FullScreenIntentsDetails;
import com.samsung.android.settings.applications.appinfo.MediaRoutingAppsDetails;
import com.samsung.android.settings.asbase.audio.SecSoundSystemSoundSettings;
import com.samsung.android.settings.asbase.audio.SecVolumeLimiterSettings;
import com.samsung.android.settings.asbase.audio.SecVolumeSettings;
import com.samsung.android.settings.asbase.vibration.SecVibrationIntensitySettings;
import com.samsung.android.settings.asbase.vibration.VibrationSystemIntensitySettings;
import com.samsung.android.settings.biometrics.BiometricsSettingsFragment;
import com.samsung.android.settings.biometrics.face.FaceEntry;
import com.samsung.android.settings.biometrics.face.FaceSettings;
import com.samsung.android.settings.biometrics.fingerprint.FingerprintEntry;
import com.samsung.android.settings.biometrics.fingerprint.FingerprintSettings;
import com.samsung.android.settings.bluetooth.BluetoothAdvancedSettings;
import com.samsung.android.settings.bluetooth.BluetoothCastSettings;
import com.samsung.android.settings.bluetooth.BluetoothControlHistory;
import com.samsung.android.settings.bluetooth.BluetoothIBRSettings;
import com.samsung.android.settings.bluetooth.BluetoothPairingBlockSettings;
import com.samsung.android.settings.bluetooth.BluetoothScanActivity;
import com.samsung.android.settings.bluetooth.BluetoothSettings;
import com.samsung.android.settings.bluetooth.SecBluetoothDeviceDetailsSettings;
import com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceSetting;
import com.samsung.android.settings.connection.AirplaneModeSettings;
import com.samsung.android.settings.connection.ConnectionsSettings;
import com.samsung.android.settings.connection.GigaMultiPath;
import com.samsung.android.settings.connection.WirelessSettings;
import com.samsung.android.settings.connection.ethernet.EthernetSettings;
import com.samsung.android.settings.connection.tether.SecTetherSettings;
import com.samsung.android.settings.datausage.DataUsageSummaryCHN;
import com.samsung.android.settings.datausage.SecAllowedNetworksSettings;
import com.samsung.android.settings.deviceinfo.aboutphone.SecMyDeviceInfoFragment;
import com.samsung.android.settings.deviceinfo.batteryinfo.SecBatteryInfoFragment;
import com.samsung.android.settings.deviceinfo.legalinfo.LegalInfoSettings;
import com.samsung.android.settings.deviceinfo.regulatoryinfo.SecRegulatoryInfoFragment;
import com.samsung.android.settings.deviceinfo.statusinfo.StatusInfoSettings;
import com.samsung.android.settings.deviceinfo.statusinfo.simlockstatus.SimLockStatus;
import com.samsung.android.settings.deviceinfo.statusinfo.simstatus.SecSimStatus;
import com.samsung.android.settings.display.AspectRatioFragmentUserSelect;
import com.samsung.android.settings.display.CameraCutoutFragmentUserSelect;
import com.samsung.android.settings.display.FrontScreenAppsFragment;
import com.samsung.android.settings.display.HighRefreshRateFragment;
import com.samsung.android.settings.display.NewModePreview;
import com.samsung.android.settings.display.ScreenResolutionFragment;
import com.samsung.android.settings.display.ScreenTimeoutActivity;
import com.samsung.android.settings.display.ScreenTimeoutDockingActivity;
import com.samsung.android.settings.display.SecCursorThicknessPreferenceFragment;
import com.samsung.android.settings.display.SecDarkModeSettingsFragment;
import com.samsung.android.settings.display.SecDreamSettings;
import com.samsung.android.settings.display.SecFontSizePreferenceFragment;
import com.samsung.android.settings.display.SecFontStylePreferenceFragment;
import com.samsung.android.settings.display.SecProcessTextManageAppsFragment;
import com.samsung.android.settings.display.SecScreenZoomPreferenceFragment;
import com.samsung.android.settings.easymode.EasyModeApp;
import com.samsung.android.settings.eyecomfort.EyeComfortSettings;
import com.samsung.android.settings.general.AutofillPicker;
import com.samsung.android.settings.general.GeneralDeviceSettings;
import com.samsung.android.settings.general.ResetSettings;
import com.samsung.android.settings.inputmethod.ChangeLanguageShortcutOptionFragment;
import com.samsung.android.settings.inputmethod.MouseAndTrackpadSettings;
import com.samsung.android.settings.inputmethod.MousePointerSettingsFragment;
import com.samsung.android.settings.inputmethod.PhysicalKeyboardFragment;
import com.samsung.android.settings.inputmethod.VirtualKeyboardFragment;
import com.samsung.android.settings.languagepack.LanguagePackSettingsFragment;
import com.samsung.android.settings.lockscreen.ChooseLockHintSettings;
import com.samsung.android.settings.lockscreen.DualClockSettings;
import com.samsung.android.settings.lockscreen.LockScreenSettings;
import com.samsung.android.settings.lockscreen.PreviousPasswordDescriptionActivity;
import com.samsung.android.settings.multidevices.MultiDevicesFragment;
import com.samsung.android.settings.multidevices.autoswitch.AutoSwitchSettings;
import com.samsung.android.settings.multidevices.continuity.ContinuitySettings;
import com.samsung.android.settings.navigationbar.NavigationBarGestureDetailedSettings;
import com.samsung.android.settings.navigationbar.NavigationBarSettings;
import com.samsung.android.settings.nearbyscan.NearbyScanning;
import com.samsung.android.settings.nfc.PaymentSettings;
import com.samsung.android.settings.notification.FloatingIconsNotificationSettings;
import com.samsung.android.settings.notification.app.ChannelGroupNotificationSettings;
import com.samsung.android.settings.notification.reminder.NotificationReminderPreferenceFragment;
import com.samsung.android.settings.privacy.OtherPrivacySettingsFragment;
import com.samsung.android.settings.privacy.PersonalizationServiceSettings;
import com.samsung.android.settings.privacy.SecurityAndPrivacySettings;
import com.samsung.android.settings.privacy.SecurityDashboardTheftProtectionFragment;
import com.samsung.android.settings.security.SecIccLockSettings;
import com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings;
import com.samsung.android.settings.taskbar.TaskBarRecentApps;
import com.samsung.android.settings.taskbar.TaskBarSettings;
import com.samsung.android.settings.taskbar.TaskbarStyleSettings;
import com.samsung.android.settings.theftprotection.MandatoryBiometricFragment;
import com.samsung.android.settings.usefulfeature.Usefulfeature;
import com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyAppGridView;
import com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyDoublePressSettings;
import com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyLongPressSettings;
import com.samsung.android.settings.usefulfeature.functionkey.FunctionKeySettings;
import com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyTips;
import com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceSettings;
import com.samsung.android.settings.usefulfeature.labs.LabsSettings;
import com.samsung.android.settings.usefulfeature.labs.appallowedoncoverscreen.AppAllowedOnCoverScreenSettings;
import com.samsung.android.settings.usefulfeature.labs.appsplitview.AppSplitViewSettings;
import com.samsung.android.settings.usefulfeature.labs.darkmodeapps.DarkModeAppsSettings;
import com.samsung.android.settings.usefulfeature.labs.flexmodepanel.FlexModePanelDetailsSettings;
import com.samsung.android.settings.usefulfeature.labs.flexmodepanel.FlexModePanelSettings;
import com.samsung.android.settings.usefulfeature.labs.rotateapps.RotateAppsSettings;
import com.samsung.android.settings.usefulfeature.motionandgestures.MotionAndGestureSettings;
import com.samsung.android.settings.usefulfeature.motionandgestures.fingersensorgesture.FingerSensorGestureSettings;
import com.samsung.android.settings.usefulfeature.multiwindow.MultiwindowSettings;
import com.samsung.android.settings.usefulfeature.multiwindow.fullscreeninsplitscreenview.FullScreenInSplitScreenViewSettings;
import com.samsung.android.settings.usefulfeature.multiwindow.swipeforpopupview.SwipeForPopUpViewSettings;
import com.samsung.android.settings.usefulfeature.multiwindow.swipeforsplitview.SwipeForSplitViewSettings;
import com.samsung.android.settings.usefulfeature.onehandedmode.NewOneHandOperationSettings;
import com.samsung.android.settings.usefulfeature.videoenhancer.HDReffectSettings;
import com.samsung.android.settings.wifi.WifiApQrCodeFragment;
import com.samsung.android.settings.wifi.WifiQrCodeFragment;
import com.samsung.android.settings.wifi.advanced.controlhistory.WifiControlHistory;
import com.samsung.android.settings.wifi.develop.history.view.RouterHistoryFragment;
import com.samsung.android.settings.wifi.intelligent.AutoConnectHotspotSettings;
import com.samsung.android.settings.wifi.intelligent.IntelligentWifiSettings;
import com.samsung.android.settings.wifi.intelligent.SmartNetworkSwitchSettings;
import com.samsung.android.settings.wifi.intelligent.SwitchForIndividualAppsSettings;
import com.samsung.android.settings.wifi.mobileap.WifiApEditSettings;
import com.samsung.android.settings.wifi.mobileap.WifiApSettings;
import com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApAutoHotspotSettings;
import com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApFamilySharingSettings;
import com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApInvitationList;
import com.samsung.android.settings.wifi.mobileap.clients.WifiApClientsManageMobileHotspot;
import com.samsung.android.settings.wifi.mobileap.clients.report.WifiApHotspotUsageReport;
import com.samsung.android.settings.wifi.mobileap.otp.WifiApOtpSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SettingsGateway {
    public static final String[] ENTRY_FRAGMENTS = {
        AdvancedConnectedDeviceDashboardFragment.class.getName(),
        CreateShortcut.class.getName(),
        BluetoothPairingDetail.class.getName(),
        BluetoothDashboardFragment.class.getName(),
        WifiNetworkDetailsFragment.class.getName(),
        ConfigureWifiSettings.class.getName(),
        SwitchForIndividualAppsSettings.class.getName(),
        SmartNetworkSwitchSettings.class.getName(),
        SavedAccessPointsWifiSettings2.class.getName(),
        TetherSettings.class.getName(),
        SmartAutoRotatePreferenceFragment.class.getName(),
        WifiP2pSettings.class.getName(),
        WifiApQrCodeFragment.class.getName(),
        WifiQrCodeFragment.class.getName(),
        WifiTetherSettings.class.getName(),
        WifiApSettings.class.getName(),
        WifiApOtpSettings.class.getName(),
        WifiApClientsManageMobileHotspot.class.getName(),
        WifiApHotspotUsageReport.class.getName(),
        WifiApEditSettings.class.getName(),
        WifiApAutoHotspotSettings.class.getName(),
        WifiApFamilySharingSettings.class.getName(),
        WifiApInvitationList.class.getName(),
        AutoConnectHotspotSettings.class.getName(),
        BackgroundCheckSummary.class.getName(),
        VpnSettings.class.getName(),
        ConnectionsSettings.class.getName(),
        WirelessSettings.class.getName(),
        AirplaneModeSettings.class.getName(),
        SecTetherSettings.class.getName(),
        GigaMultiPath.class.getName(),
        DataUsageSummaryCHN.class.getName(),
        SecAllowedNetworksSettings.class.getName(),
        EthernetSettings.class.getName(),
        DataSaverSummary.class.getName(),
        DateTimeSettings.class.getName(),
        LocaleListEditor.class.getName(),
        AvailableVirtualKeyboardFragment.class.getName(),
        LanguageSettings.class.getName(),
        KeyboardSettings.class.getName(),
        ModifierKeysSettings.class.getName(),
        NewKeyboardLayoutEnabledLocalesFragment.class.getName(),
        TrackpadSettings.class.getName(),
        SpellCheckersSettings.class.getName(),
        UserDictionaryList.class.getName(),
        UserDictionarySettings.class.getName(),
        DisplaySettings.class.getName(),
        SecMyDeviceInfoFragment.class.getName(),
        ModuleLicensesDashboard.class.getName(),
        ManageApplications.class.getName(),
        FirmwareVersionSettings.class.getName(),
        ManageAssist.class.getName(),
        ProcessStatsUi.class.getName(),
        NotificationStation.class.getName(),
        LocationSettings.class.getName(),
        WifiScanningFragment.class.getName(),
        PrivacyDashboardFragment.class.getName(),
        PrivacyControlsFragment.class.getName(),
        LocationServices.class.getName(),
        SecuritySettings.class.getName(),
        SecurityAdvancedSettings.class.getName(),
        MoreSecurityPrivacyFragment.class.getName(),
        UsageAccessDetails.class.getName(),
        PrivacySettings.class.getName(),
        DeviceAdminSettings.class.getName(),
        AccessibilityDetailsSettingsFragment.class.getName(),
        AccessibilitySettings.class.getName(),
        AccessibilitySettingsForSetupWizard.class.getName(),
        EditShortcutsPreferenceFragment.class.getName(),
        TextReadingPreferenceFragment.class.getName(),
        TextReadingPreferenceFragmentForSetupWizard.class.getName(),
        AutoBrightnessPreferenceFragmentForSetupWizard.class.getName(),
        CaptioningPropertiesFragment.class.getName(),
        ToggleDaltonizerPreferenceFragment.class.getName(),
        ToggleColorInversionPreferenceFragment.class.getName(),
        ToggleReduceBrightColorsPreferenceFragment.class.getName(),
        TextToSpeechSettings.class.getName(),
        PrivateVolumeForget.class.getName(),
        PublicVolumeSettings.class.getName(),
        DevelopmentSettingsDashboardFragment.class.getName(),
        WifiDisplaySettings.class.getName(),
        PowerUsageSummary.class.getName(),
        AccountSyncSettings.class.getName(),
        PrivateSpaceBiometricSettings.class.getName(),
        PrivateSpaceDeleteFragment.class.getName(),
        PrivateSpaceDeletionProgressFragment.class.getName(),
        SwipeToNotificationSettings.class.getName(),
        DoubleTapPowerSettings.class.getName(),
        DoubleTapScreenSettings.class.getName(),
        PickupGestureSettings.class.getName(),
        DoubleTwistGestureSettings.class.getName(),
        SystemNavigationGestureSettings.class.getName(),
        DataUsageSummary.class.getName(),
        DreamSettings.class.getName(),
        CommunalDashboardFragment.class.getName(),
        UserSettings.class.getName(),
        NotificationAccessSettings.class.getName(),
        NotificationAccessDetails.class.getName(),
        AppBubbleNotificationSettings.class.getName(),
        AppChannelListSettings.class.getName(),
        ZenAccessSettings.class.getName(),
        ZenAccessDetails.class.getName(),
        ZenModeAutomationSettings.class.getName(),
        PrintSettingsFragment.class.getName(),
        PrintJobSettingsFragment.class.getName(),
        TrustedCredentialsSettings.class.getName(),
        PaymentSettings.class.getName(),
        KeyboardLayoutPickerFragment.class.getName(),
        PhysicalKeyboardFragment.class.getName(),
        ZenModeSettings.class.getName(),
        SoundSettings.class.getName(),
        ConversationListSettings.class.getName(),
        ConfigureNotificationSettings.class.getName(),
        ChooseLockPassword.ChooseLockPasswordFragment.class.getName(),
        ChooseLockPattern.ChooseLockPatternFragment.class.getName(),
        ChooseLockHintSettings.ChooseLockHintSettingsFragment.class.getName(),
        PreviousPasswordDescriptionActivity.PreviousPasswordDescriptionFragment.class.getName(),
        AppInfoDashboardFragment.class.getName(),
        BatterySaverSettings.class.getName(),
        AppNotificationSettings.class.getName(),
        NotificationAssistantPicker.class.getName(),
        ChannelNotificationSettings.class.getName(),
        SatelliteSetting.class.getName(),
        ChannelGroupNotificationSettings.class.getName(),
        ApnSettings.class.getName(),
        ApnEditor.class.getName(),
        WifiCallingSettings.class.getName(),
        ZenModeScheduleRuleSettings.class.getName(),
        ZenModeEventRuleSettings.class.getName(),
        ZenModeBlockedEffectsSettings.class.getName(),
        ProcessStatsUi.class.getName(),
        AdvancedPowerUsageDetail.class.getName(),
        ProcessStatsSummary.class.getName(),
        DrawOverlayDetails.class.getName(),
        WriteSettingsDetails.class.getName(),
        ExternalSourcesDetails.class.getName(),
        ManageExternalStorageDetails.class.getName(),
        WallpaperTypeSettings.class.getName(),
        VrListenerSettings.class.getName(),
        PictureInPictureSettings.class.getName(),
        PictureInPictureDetails.class.getName(),
        PremiumSmsAccess.class.getName(),
        ManagedProfileSettings.class.getName(),
        ChooseAccountFragment.class.getName(),
        IccLockSettings.class.getName(),
        TestingSettings.class.getName(),
        WifiAPITest.class.getName(),
        WifiInfo.class.getName(),
        MainClear.class.getName(),
        MainClearConfirm.class.getName(),
        ResetDashboardFragment.class.getName(),
        NightDisplaySettings.class.getName(),
        ManageDomainUrls.class.getName(),
        AutomaticStorageManagerSettings.class.getName(),
        StorageDashboardFragment.class.getName(),
        SystemDashboardFragment.class.getName(),
        NetworkDashboardFragment.class.getName(),
        ConnectedDeviceDashboardFragment.class.getName(),
        UsbDetailsFragment.class.getName(),
        AppDashboardFragment.class.getName(),
        WifiCallingDisclaimerFragment.class.getName(),
        AccountDashboardFragment.class.getName(),
        EnterprisePrivacySettings.class.getName(),
        WebViewAppPicker.class.getName(),
        LockscreenDashboardFragment.class.getName(),
        MemtagPage.class.getName(),
        BluetoothDeviceDetailsFragment.class.getName(),
        BluetoothBroadcastDialog.class.getName(),
        BluetoothFindBroadcastsFragment.class.getName(),
        StylusUsiDetailsFragment.class.getName(),
        DataUsageList.class.getName(),
        ToggleBackupSettingFragment.class.getName(),
        PreviouslyConnectedDeviceDashboardFragment.class.getName(),
        BatterySaverScheduleSettings.class.getName(),
        MobileNetworkListFragment.class.getName(),
        PowerMenuSettings.class.getName(),
        DarkModeSettingsFragment.class.getName(),
        BugReportHandlerPicker.class.getName(),
        GestureNavigationSettingsFragment.class.getName(),
        ButtonNavigationSettingsFragment.class.getName(),
        InteractAcrossProfilesSettings.class.getName(),
        InteractAcrossProfilesDetails.class.getName(),
        MediaControlsSettings.class.getName(),
        NetworkProviderSettings.class.getName(),
        NetworkSelectSettings.class.getName(),
        AlarmsAndRemindersDetails.class.getName(),
        MediaManagementAppsDetails.class.getName(),
        AutoBrightnessSettings.class.getName(),
        OneHandedSettings.class.getName(),
        MobileNetworkSettings.class.getName(),
        AppLocaleDetails.class.getName(),
        TurnScreenOnDetails.class.getName(),
        NfcAndPaymentFragment.class.getName(),
        ColorAndMotionFragment.class.getName(),
        ColorContrastFragment.class.getName(),
        LongBackgroundTasksDetails.class.getName(),
        RegionalPreferencesEntriesFragment.class.getName(),
        SecBatteryInfoFragment.class.getName(),
        UserAspectRatioDetails.class.getName(),
        ScreenTimeoutSettings.class.getName(),
        ResetNetwork.class.getName(),
        VibrationIntensitySettingsFragment.class.getName(),
        CellularSecuritySettingsFragment.class.getName(),
        AccessibilityHearingAidsFragment.class.getName(),
        HearingDevicePairingFragment.class.getName(),
        NotificationReminderPreferenceFragment.class.getName()
    };
    public static final String[] SETTINGS_FOR_RESTRICTED = {
        Settings.ConnectedDeviceDashboardActivity.class.getName(),
        Settings.AppDashboardActivity.class.getName(),
        Settings.DisplaySettingsActivity.class.getName(),
        Settings.SoundSettingsActivity.class.getName(),
        Settings.StorageDashboardActivity.class.getName(),
        Settings.PowerUsageSummaryActivity.class.getName(),
        Settings.AccountDashboardActivity.class.getName(),
        Settings.PrivacySettingsActivity.class.getName(),
        Settings.SecurityDashboardActivity.class.getName(),
        Settings.AccessibilitySettingsActivity.class.getName(),
        SupportDashboardActivity.class.getName(),
        Settings.WifiSettingsActivity.class.getName(),
        Settings.DataUsageSummaryActivity.class.getName(),
        Settings.DataUsageSummaryCHNActivity.class.getName(),
        Settings.NetworkProviderSettingsActivity.class.getName(),
        Settings.NetworkSelectActivity.class.getName(),
        Settings.BluetoothSettingsActivity.class.getName(),
        Settings.WifiDisplaySettingsActivity.class.getName(),
        Settings.PrintSettingsActivity.class.getName(),
        Settings.UserSettingsActivity.class.getName(),
        Settings.ConfigureNotificationSettingsActivity.class.getName(),
        Settings.ManageApplicationsActivity.class.getName(),
        Settings.PaymentSettingsActivity.class.getName(),
        Settings.AdaptiveBrightnessActivity.class.getName(),
        Settings.LocationSettingsActivity.class.getName(),
        Settings.LanguageAndInputSettingsActivity.class.getName(),
        Settings.LanguageSettingsActivity.class.getName(),
        Settings.KeyboardSettingsActivity.class.getName(),
        Settings.DateTimeSettingsActivity.class.getName(),
        Settings.EnterprisePrivacySettingsActivity.class.getName(),
        Settings.MyDeviceInfoActivity.class.getName(),
        Settings.ModuleLicensesActivity.class.getName(),
        UserBackupSettingsActivity.class.getName(),
        Settings.MemtagPageActivity.class.getName(),
        Settings.NavigationModeSettingsActivity.class.getName(),
        Settings.GeneralDeviceSettingsActivity.class.getName(),
        Settings.NfcSettingsActivity.class.getName(),
        Settings.PaymentSettingsActivity.class.getName(),
        Settings.NfcAdvancedRoutingSettingActivity.class.getName(),
        Settings.OtherSettingsActivity.class.getName(),
        FingerprintEntry.class.getName(),
        FaceEntry.class.getName(),
        Settings.SecurityAndPrivacySettingsActivity.class.getName(),
        Settings.UsefulFeatureMainActivity.class.getName()
    };
    public static final String[] SAMSUNG_ENTRY_FRAGMENTS = {
        "com.samsung.android.settings.nfc.NfcSettings",
        "com.samsung.android.settings.nfc.TapAndPaySettings",
        "com.samsung.android.settings.nfc.PaymentSettings",
        "com.samsung.android.settings.nfc.OtherSettings",
        "com.samsung.android.settings.nfc.NfcAdvancedRoutingSetting",
        SecurityAndPrivacySettings.class.getName(),
        OtherPrivacySettingsFragment.class.getName(),
        PersonalizationServiceSettings.class.getName(),
        SecurityDashboardTheftProtectionFragment.class.getName(),
        MandatoryBiometricFragment.class.getName(),
        BluetoothSettings.class.getName(),
        BluetoothControlHistory.class.getName(),
        BluetoothScanActivity.class.getName(),
        BluetoothAdvancedSettings.class.getName(),
        BluetoothIBRSettings.class.getName(),
        BluetoothCastSettings.class.getName(),
        SecBluetoothDeviceDetailsSettings.class.getName(),
        BluetoothPairingBlockSettings.class.getName(),
        SecBluetoothLeBroadcastSourceSetting.class.getName(),
        WifiSettings.class.getName(),
        IntelligentWifiSettings.class.getName(),
        com.samsung.android.settings.wifi.advanced.ConfigureWifiSettings.class.getName(),
        "com.android.settings.UserCredentialsSettings",
        "com.android.settings.security.InstallCertificateFromStorage",
        "com.samsung.android.settings.usefulfeature.SmartStaySettings",
        "com.samsung.android.settings.usefulfeature.RecentsKeySettings",
        "com.samsung.android.settings.accessories.ShowInfomationMenu",
        "com.samsung.android.settings.accessories.CoverScreenOrientation",
        "com.samsung.android.settings.activekey.AppList",
        DockSettings.class.getName(),
        NewOneHandOperationSettings.class.getName(),
        HDReffectSettings.class.getName(),
        FingerSensorGestureSettings.class.getName(),
        Usefulfeature.class.getName(),
        ActiveKeySettings.class.getName(),
        XcoverTopKeySettings.class.getName(),
        ActiveKeyAppGridView.class.getName(),
        FunctionKeySettings.class.getName(),
        FunctionKeyDoublePressSettings.class.getName(),
        FunctionKeyLongPressSettings.class.getName(),
        FunctionKeyAppGridView.class.getName(),
        FunctionKeyTips.class.getName(),
        MotionAndGestureSettings.class.getName(),
        LabsSettings.class.getName(),
        FlexModePanelSettings.class.getName(),
        FlexModePanelDetailsSettings.class.getName(),
        AppAllowedOnCoverScreenSettings.class.getName(),
        RotateAppsSettings.class.getName(),
        AppSplitViewSettings.class.getName(),
        MultiwindowSettings.class.getName(),
        FullScreenInSplitScreenViewSettings.class.getName(),
        SwipeForPopUpViewSettings.class.getName(),
        SwipeForSplitViewSettings.class.getName(),
        DarkModeAppsSettings.class.getName(),
        IntelligenceServiceSettings.class.getName(),
        MultiDevicesFragment.class.getName(),
        ContinuitySettings.class.getName(),
        AutoSwitchSettings.class.getName(),
        NearbyScanning.class.getName(),
        com.samsung.android.settings.asbase.audio.SoundSettings.class.getName(),
        SecVolumeSettings.class.getName(),
        SecVolumeLimiterSettings.class.getName(),
        SecSoundSystemSoundSettings.class.getName(),
        VibrationSystemIntensitySettings.class.getName(),
        SecVibrationIntensitySettings.class.getName(),
        CloudAccountSettings.class.getName(),
        LockScreenSettings.class.getName(),
        DualClockSettings.class.getName(),
        SecIccLockSettings.class.getName(),
        EasyModeApp.class.getName(),
        EyeComfortSettings.class.getName(),
        NewModePreview.class.getName(),
        FrontScreenAppsFragment.class.getName(),
        AspectRatioFragmentUserSelect.class.getName(),
        CameraCutoutFragmentUserSelect.class.getName(),
        SecDreamSettings.class.getName(),
        HighRefreshRateFragment.class.getName(),
        SecDarkModeSettingsFragment.class.getName(),
        ScreenResolutionFragment.class.getName(),
        SecScreenZoomPreferenceFragment.class.getName(),
        ScreenTimeoutActivity.class.getName(),
        ScreenTimeoutDockingActivity.class.getName(),
        SecCursorThicknessPreferenceFragment.class.getName(),
        SecFontSizePreferenceFragment.class.getName(),
        SecFontStylePreferenceFragment.class.getName(),
        SecProcessTextManageAppsFragment.class.getName(),
        NavigationBarSettings.class.getName(),
        NavigationBarGestureDetailedSettings.class.getName(),
        TaskBarSettings.class.getName(),
        TaskBarRecentApps.class.getName(),
        TaskbarStyleSettings.class.getName(),
        StatusInfoSettings.class.getName(),
        SecSimStatus.class.getName(),
        SimLockStatus.class.getName(),
        LegalInfoSettings.class.getName(),
        SecRegulatoryInfoFragment.class.getName(),
        "com.samsung.android.settings.notification.BadgeAppIconSettings",
        SecAccessibilitySettings.class.getName(),
        SecAccessibilitySettingsForSetupWizard.class.getName(),
        HearingEnhancementsFragment.class.getName(),
        AmplifyAmbientSoundPreferenceFragment.class.getName(),
        MuteAllSoundFragment.class.getName(),
        ColorAdjustmentMainFragment.class.getName(),
        HighContrastFontFragment.class.getName(),
        ColorLensFragment.class.getName(),
        MagnificationTypeFragment.class.getName(),
        TouchAndHoldFragment.class.getName(),
        AccessibilityShortcutEditRequestFragment.class.getName(),
        "com.samsung.android.settings.accessibility.advanced.shortcut.AccessibilityButtonPreferenceFragment",
        "com.samsung.android.settings.accessibility.advanced.shortcut.AccessibilityButtonSwitchPreferenceFragment",
        "com.samsung.android.settings.accessibility.advanced.shortcut.SideAndVolumeUpSwitchPreferenceFragment",
        "com.samsung.android.settings.accessibility.advanced.shortcut.VolumeUpAndDownSwitchPreferenceFragment",
        "com.samsung.android.settings.notification.ConfigureNotificationMoreSettings",
        "com.samsung.android.settings.lockscreen.LockScreenNotificationSettings",
        "com.samsung.android.settings.encryption.CryptSDCardSettings",
        BiometricsSettingsFragment.class.getName(),
        FingerprintSettings.class.getName(),
        FaceSettings.class.getName(),
        MousePointerSettingsFragment.class.getName(),
        LanguagePackSettingsFragment.class.getName(),
        DefaultAutofillPicker.class.getName(),
        GeneralDeviceSettings.class.getName(),
        ChangeLanguageShortcutOptionFragment.class.getName(),
        ResetNetwork.class.getName(),
        ResetSettings.class.getName(),
        VirtualKeyboardFragment.class.getName(),
        MouseAndTrackpadSettings.class.getName(),
        AutofillPicker.class.getName(),
        FloatingIconsNotificationSettings.class.getName(),
        SoftwareUpdateSettings.class.getName(),
        SecBluetoothLeBroadcastSourceSetting.class.getName(),
        FullScreenIntentsDetails.class.getName(),
        MediaRoutingAppsDetails.class.getName(),
        "com.samsung.android.fast.ui.MainActivity",
        WifiControlHistory.class.getName(),
        RouterHistoryFragment.class.getName()
    };
}
