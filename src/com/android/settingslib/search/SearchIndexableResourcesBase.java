package com.android.settingslib.search;

import com.android.settings.accounts.AccountDashboardFragment;
import com.android.settings.accounts.ManagedProfileSettings;
import com.android.settings.applications.assist.ManageAssist;
import com.android.settings.applications.manageapplications.ManageApplications;
import com.android.settings.applications.specialaccess.SpecialAccessSettings;
import com.android.settings.applications.specialaccess.interactacrossprofiles.InteractAcrossProfilesSettings;
import com.android.settings.backup.BackupSettingsFragment;
import com.android.settings.datausage.DataSaverSummary;
import com.android.settings.datausage.DataUsageSummary;
import com.android.settings.datausage.UnrestrictedDataAccess;
import com.android.settings.datetime.DateTimeSettings;
import com.android.settings.datetime.timezone.TimeZoneSettings;
import com.android.settings.deletionhelper.AutomaticStorageManagerSettings;
import com.android.settings.development.DevelopmentMemtagPage;
import com.android.settings.development.WirelessDebuggingFragment;
import com.android.settings.development.mediadrm.MediaDrmSettingsFragment;
import com.android.settings.development.snooplogger.SnoopLoggerFiltersDashboard;
import com.android.settings.development.transcode.TranscodeSettingsFragment;
import com.android.settings.display.ColorModePreferenceFragment;
import com.android.settings.display.DeviceStateAutoRotateDetailsFragment;
import com.android.settings.emergency.EmergencyDashboardFragment;
import com.android.settings.inputmethod.AvailableVirtualKeyboardFragment;
import com.android.settings.inputmethod.ModifierKeysSettings;
import com.android.settings.inputmethod.PhysicalKeyboardFragment;
import com.android.settings.inputmethod.TrackpadTouchGestureSettings;
import com.android.settings.inputmethod.UserDictionaryList;
import com.android.settings.localepicker.LocaleListEditor;
import com.android.settings.location.LocationSettings;
import com.android.settings.location.RecentLocationAccessSeeAllFragment;
import com.android.settings.notification.BubbleNotificationSettings;
import com.android.settings.notification.ConfigureNotificationSettings;
import com.android.settings.notification.NotificationAccessSettings;
import com.android.settings.notification.app.AppBubbleNotificationSettings;
import com.android.settings.notification.modes.ZenModeSelectBypassingAppsFragment;
import com.android.settings.notification.modes.ZenModesListFragment;
import com.android.settings.notification.zen.ZenModeBlockedEffectsSettings;
import com.android.settings.notification.zen.ZenModeBypassingAppsSettings;
import com.android.settings.notification.zen.ZenModeMessagesSettings;
import com.android.settings.notification.zen.ZenModePeopleSettings;
import com.android.settings.notification.zen.ZenModeSettings;
import com.android.settings.notification.zen.ZenModeSoundVibrationSettings;
import com.android.settings.password.ChooseLockGeneric;
import com.android.settings.print.PrintSettingsFragment;
import com.android.settings.privacy.PrivacyDashboardFragment;
import com.android.settings.safetycenter.MoreSecurityPrivacyFragment;
import com.android.settings.security.ContentProtectionPreferenceFragment;
import com.android.settings.security.CredentialManagementAppFragment;
import com.android.settings.security.InstallCertificateFromStorage;
import com.android.settings.security.MemtagPage;
import com.android.settings.security.SecurityAdvancedSettings;
import com.android.settings.security.trustagent.TrustAgentSettings;
import com.android.settings.tts.TextToSpeechSettings;
import com.android.settings.tts.TtsEnginePreferenceFragment;
import com.android.settings.users.UserSettings;
import com.android.settings.wifi.WifiSettings;
import com.android.settings.wifi.p2p.WifiP2pSettings;
import com.android.settings.wifi.tether.WifiTetherSettings;

import com.samsung.android.settings.DomesticSettings;
import com.samsung.android.settings.accessories.CoverScreenOrientation;
import com.samsung.android.settings.accessories.DockSettings;
import com.samsung.android.settings.account.CloudAccountSettings;
import com.samsung.android.settings.activekey.ActiveKeySettings;
import com.samsung.android.settings.activekey.XcoverTopKeySettings;
import com.samsung.android.settings.asbase.audio.SecSoundSystemSoundSettings;
import com.samsung.android.settings.asbase.audio.SecVolumeLimiterSettings;
import com.samsung.android.settings.asbase.audio.SecVolumeSettings;
import com.samsung.android.settings.asbase.audio.SoundSettings;
import com.samsung.android.settings.asbase.vibration.SecVibrationIntensitySettings;
import com.samsung.android.settings.asbase.vibration.VibrationSystemIntensitySettings;
import com.samsung.android.settings.autopoweronoff.AutoPowerOnOffSettings;
import com.samsung.android.settings.biometrics.BiometricsSettingsFragment;
import com.samsung.android.settings.biometrics.face.FaceEntry;
import com.samsung.android.settings.biometrics.fingerprint.FingerprintEntry;
import com.samsung.android.settings.bluetooth.BluetoothAdvancedSettings;
import com.samsung.android.settings.bluetooth.BluetoothSettings;
import com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceSetting;
import com.samsung.android.settings.connection.ConnectionsSettings;
import com.samsung.android.settings.connection.GigaLteSettings;
import com.samsung.android.settings.connection.WirelessSettings;
import com.samsung.android.settings.connection.tether.SecTetherSettings;
import com.samsung.android.settings.datausage.DataUsageSummaryCHN;
import com.samsung.android.settings.datausage.SecBillingCycleSettings;
import com.samsung.android.settings.datausage.trafficmanager.ui.DataSaverSummaryCHN;
import com.samsung.android.settings.development.bluetooth.MapDashboard;
import com.samsung.android.settings.development.bluetooth.PbapDashboard;
import com.samsung.android.settings.deviceinfo.aboutphone.SecMyDeviceInfoFragment;
import com.samsung.android.settings.deviceinfo.batteryinfo.SecBatteryInfoFragment;
import com.samsung.android.settings.deviceinfo.legalinfo.LegalInfoSettings;
import com.samsung.android.settings.deviceinfo.legalinfo.samsunglegal.SamsungLegalInfo;
import com.samsung.android.settings.deviceinfo.partinfo.PartInfoSettings;
import com.samsung.android.settings.deviceinfo.regulatoryinfo.ServiceInfoSettings;
import com.samsung.android.settings.deviceinfo.regulatoryinfo.WarrantyInfoFragment;
import com.samsung.android.settings.deviceinfo.softwareinfo.SoftwareInfoSettings;
import com.samsung.android.settings.deviceinfo.statusinfo.StatusInfoSettings;
import com.samsung.android.settings.deviceinfo.statusinfo.imei.ImeiInformation;
import com.samsung.android.settings.deviceinfo.statusinfo.simstatus.SecSimStatus;
import com.samsung.android.settings.display.NewModePreview;
import com.samsung.android.settings.display.ScreenTimeoutActivity;
import com.samsung.android.settings.display.SecAdaptiveDisplaySettings;
import com.samsung.android.settings.display.SecDarkModeSettingsFragment;
import com.samsung.android.settings.display.SecFontSizePreferenceFragment;
import com.samsung.android.settings.dynamicmenu.SecDynamicMenuSearchIndexablesProvider;
import com.samsung.android.settings.encryption.CryptSDCardSettings;
import com.samsung.android.settings.eyecomfort.EyeComfortSettings;
import com.samsung.android.settings.general.AutofillPicker;
import com.samsung.android.settings.general.GeneralDeviceSettings;
import com.samsung.android.settings.inputmethod.AppShortcutsSettings;
import com.samsung.android.settings.inputmethod.ChangeLanguageShortcutOptionFragment;
import com.samsung.android.settings.inputmethod.MouseAndTrackpadSettings;
import com.samsung.android.settings.inputmethod.MousePointerSettingsFragment;
import com.samsung.android.settings.inputmethod.VirtualKeyboardFragment;
import com.samsung.android.settings.inputmethod.WirelessKeyboardShareFragment;
import com.samsung.android.settings.lockscreen.LockScreenNotificationSettings;
import com.samsung.android.settings.lockscreen.LockScreenSettings;
import com.samsung.android.settings.lockscreen.SecuredLockSettingsMenu;
import com.samsung.android.settings.multidevices.MultiDevicesFragment;
import com.samsung.android.settings.navigationbar.NavigationBarGestureDetailedSettings;
import com.samsung.android.settings.navigationbar.NavigationBarSettings;
import com.samsung.android.settings.nfc.NfcSettings;
import com.samsung.android.settings.notification.BadgeAppIconSettings;
import com.samsung.android.settings.notification.ConfigureNotificationMoreSettings;
import com.samsung.android.settings.notification.SecAutoGroupingFragment;
import com.samsung.android.settings.notification.SecNotificationIntelligenceFragment;
import com.samsung.android.settings.notification.SecSummarizeContentFragment;
import com.samsung.android.settings.notification.StatusBarNotificationSettings;
import com.samsung.android.settings.notification.brief.BriefPopUpSettings;
import com.samsung.android.settings.privacy.OtherPrivacySettingsFragment;
import com.samsung.android.settings.privacy.SecurityAndPrivacySettings;
import com.samsung.android.settings.privacy.SecurityDashboardAccountsSecurityFragment;
import com.samsung.android.settings.privacy.SecurityDashboardAppSecurityFragment;
import com.samsung.android.settings.privacy.SecurityDashboardDeviceFindersFragment;
import com.samsung.android.settings.privacy.SecurityDashboardTheftProtectionFragment;
import com.samsung.android.settings.privacy.SecurityDashboardUpdatesFragment;
import com.samsung.android.settings.security.DeviceIdSettings;
import com.samsung.android.settings.security.SecIccLockSettings;
import com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings;
import com.samsung.android.settings.taskbar.TaskBarSettings;
import com.samsung.android.settings.usefulfeature.Usefulfeature;
import com.samsung.android.settings.usefulfeature.functionkey.FunctionKeySettings;
import com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceSettings;
import com.samsung.android.settings.usefulfeature.labs.LabsSettings;
import com.samsung.android.settings.usefulfeature.labs.flexmodepanel.FlexModePanelSettings;
import com.samsung.android.settings.usefulfeature.motionandgestures.MotionAndGestureSettings;
import com.samsung.android.settings.usefulfeature.motionandgestures.fingersensorgesture.FingerSensorGestureSettings;
import com.samsung.android.settings.usefulfeature.multiwindow.MultiwindowSettings;
import com.samsung.android.settings.usefulfeature.onehandedmode.NewOneHandOperationSettings;
import com.samsung.android.settings.wifi.advanced.ConfigureWifiSettings;
import com.samsung.android.settings.wifi.intelligent.IntelligentWifiSettings;
import com.samsung.android.settings.wifi.intelligent.MobileWIPSExceptionList;
import com.samsung.android.settings.wifi.intelligent.MobileWIPSSettings;
import com.samsung.android.settings.wifi.intelligent.SmartNetworkSwitchSettings;
import com.samsung.android.settings.wifi.intelligent.SwitchToBetterWifiSettings;
import com.samsung.android.settings.wifi.mobileap.WifiApSettings;
import com.samsung.android.settings.wifi.mobileap.clients.WifiApClientsManageMobileHotspot;
import com.samsung.android.settings.wifi.mobileap.clients.report.WifiApHotspotUsageReport;

import java.util.HashSet;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SearchIndexableResourcesBase implements SearchIndexableResources {
    public final Set mProviders = new HashSet();

    public SearchIndexableResourcesBase() {
        addIndex(
                new SearchIndexableData(
                        AccountDashboardFragment.class,
                        AccountDashboardFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        ManagedProfileSettings.class,
                        ManagedProfileSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        ManageAssist.class, ManageAssist.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        ManageApplications.class, ManageApplications.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SpecialAccessSettings.class,
                        SpecialAccessSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        InteractAcrossProfilesSettings.class,
                        InteractAcrossProfilesSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        BackupSettingsFragment.class,
                        BackupSettingsFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        DataUsageSummary.class, DataUsageSummary.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        UnrestrictedDataAccess.class,
                        UnrestrictedDataAccess.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        DateTimeSettings.class, DateTimeSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        TimeZoneSettings.class, TimeZoneSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        AutomaticStorageManagerSettings.class,
                        AutomaticStorageManagerSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        DevelopmentMemtagPage.class,
                        DevelopmentMemtagPage.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        WirelessDebuggingFragment.class,
                        WirelessDebuggingFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        MediaDrmSettingsFragment.class,
                        MediaDrmSettingsFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SnoopLoggerFiltersDashboard.class,
                        SnoopLoggerFiltersDashboard.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        TranscodeSettingsFragment.class,
                        TranscodeSettingsFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        ColorModePreferenceFragment.class,
                        ColorModePreferenceFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        DeviceStateAutoRotateDetailsFragment.class,
                        DeviceStateAutoRotateDetailsFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        EmergencyDashboardFragment.class,
                        EmergencyDashboardFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        AvailableVirtualKeyboardFragment.class,
                        AvailableVirtualKeyboardFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        ModifierKeysSettings.class,
                        ModifierKeysSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        PhysicalKeyboardFragment.class,
                        PhysicalKeyboardFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        TrackpadTouchGestureSettings.class,
                        TrackpadTouchGestureSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        UserDictionaryList.class, UserDictionaryList.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        LocaleListEditor.class, LocaleListEditor.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        LocationSettings.class, LocationSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        RecentLocationAccessSeeAllFragment.class,
                        RecentLocationAccessSeeAllFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        BubbleNotificationSettings.class,
                        BubbleNotificationSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        ConfigureNotificationSettings.class,
                        ConfigureNotificationSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        NotificationAccessSettings.class,
                        NotificationAccessSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        AppBubbleNotificationSettings.class,
                        AppBubbleNotificationSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        ZenModeSelectBypassingAppsFragment.class,
                        ZenModeSelectBypassingAppsFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        ZenModesListFragment.class,
                        ZenModesListFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        ZenModeBlockedEffectsSettings.class,
                        ZenModeBlockedEffectsSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        ZenModeBypassingAppsSettings.class,
                        ZenModeBypassingAppsSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        ZenModeMessagesSettings.class,
                        ZenModeMessagesSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        ZenModePeopleSettings.class,
                        ZenModePeopleSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        ZenModeSettings.class, ZenModeSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        ZenModeSoundVibrationSettings.class,
                        ZenModeSoundVibrationSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        ChooseLockGeneric.ChooseLockGenericFragment.class,
                        ChooseLockGeneric.ChooseLockGenericFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        PrintSettingsFragment.class,
                        PrintSettingsFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        PrivacyDashboardFragment.class,
                        PrivacyDashboardFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        MoreSecurityPrivacyFragment.class,
                        MoreSecurityPrivacyFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        ContentProtectionPreferenceFragment.class,
                        ContentProtectionPreferenceFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        CredentialManagementAppFragment.class,
                        CredentialManagementAppFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        InstallCertificateFromStorage.class,
                        InstallCertificateFromStorage.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(new SearchIndexableData(MemtagPage.class, MemtagPage.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SecurityAdvancedSettings.class,
                        SecurityAdvancedSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        TrustAgentSettings.class, TrustAgentSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        TextToSpeechSettings.class,
                        TextToSpeechSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        TtsEnginePreferenceFragment.class,
                        TtsEnginePreferenceFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        UserSettings.class, UserSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        WifiSettings.class, WifiSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        WifiP2pSettings.class, WifiP2pSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        WifiTetherSettings.class, WifiTetherSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        DomesticSettings.class, DomesticSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        CoverScreenOrientation.class,
                        CoverScreenOrientation.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        DockSettings.class, DockSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        CloudAccountSettings.class,
                        CloudAccountSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        ActiveKeySettings.class, ActiveKeySettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        XcoverTopKeySettings.class,
                        XcoverTopKeySettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SecSoundSystemSoundSettings.class,
                        SecSoundSystemSoundSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SecVolumeLimiterSettings.class,
                        SecVolumeLimiterSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SecVolumeSettings.class, SecVolumeSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SoundSettings.class, SoundSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SecVibrationIntensitySettings.class,
                        SecVibrationIntensitySettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        VibrationSystemIntensitySettings.class,
                        VibrationSystemIntensitySettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        AutoPowerOnOffSettings.class,
                        AutoPowerOnOffSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        BiometricsSettingsFragment.class,
                        BiometricsSettingsFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(new SearchIndexableData(FaceEntry.class, FaceEntry.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        FingerprintEntry.class, FingerprintEntry.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        BluetoothAdvancedSettings.class,
                        BluetoothAdvancedSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        BluetoothSettings.class, BluetoothSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SecBluetoothLeBroadcastSourceSetting.class,
                        SecBluetoothLeBroadcastSourceSetting.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        ConnectionsSettings.class, ConnectionsSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        GigaLteSettings.class, GigaLteSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        WirelessSettings.class, WirelessSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SecTetherSettings.class, SecTetherSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        DataUsageSummaryCHN.class, DataUsageSummaryCHN.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SecBillingCycleSettings.class,
                        SecBillingCycleSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        DataSaverSummaryCHN.class, DataSaverSummaryCHN.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        MapDashboard.class, MapDashboard.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        PbapDashboard.class, PbapDashboard.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SecMyDeviceInfoFragment.class,
                        SecMyDeviceInfoFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SecBatteryInfoFragment.class,
                        SecBatteryInfoFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        LegalInfoSettings.class, LegalInfoSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SamsungLegalInfo.class, SamsungLegalInfo.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        PartInfoSettings.class, PartInfoSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        ServiceInfoSettings.class, ServiceInfoSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        WarrantyInfoFragment.class,
                        WarrantyInfoFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SoftwareInfoSettings.class,
                        SoftwareInfoSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        StatusInfoSettings.class, StatusInfoSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        ImeiInformation.class, ImeiInformation.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SecSimStatus.class, SecSimStatus.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        NewModePreview.class, NewModePreview.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        ScreenTimeoutActivity.class,
                        ScreenTimeoutActivity.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SecAdaptiveDisplaySettings.class,
                        SecAdaptiveDisplaySettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SecDarkModeSettingsFragment.class,
                        SecDarkModeSettingsFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SecFontSizePreferenceFragment.class,
                        SecFontSizePreferenceFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SecDynamicMenuSearchIndexablesProvider.class,
                        SecDynamicMenuSearchIndexablesProvider.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        CryptSDCardSettings.class, CryptSDCardSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        EyeComfortSettings.class, EyeComfortSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        AutofillPicker.class, AutofillPicker.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        GeneralDeviceSettings.class,
                        GeneralDeviceSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        AppShortcutsSettings.class,
                        AppShortcutsSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        ChangeLanguageShortcutOptionFragment.class,
                        ChangeLanguageShortcutOptionFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        MouseAndTrackpadSettings.class,
                        MouseAndTrackpadSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        MousePointerSettingsFragment.class,
                        MousePointerSettingsFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        com.samsung.android.settings.inputmethod.PhysicalKeyboardFragment.class,
                        com.samsung.android.settings.inputmethod.PhysicalKeyboardFragment
                                .SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        VirtualKeyboardFragment.class,
                        VirtualKeyboardFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        WirelessKeyboardShareFragment.class,
                        WirelessKeyboardShareFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        LockScreenNotificationSettings.class,
                        LockScreenNotificationSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        LockScreenSettings.class, LockScreenSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SecuredLockSettingsMenu.class,
                        SecuredLockSettingsMenu.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        MultiDevicesFragment.class,
                        MultiDevicesFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        NavigationBarGestureDetailedSettings.class,
                        NavigationBarGestureDetailedSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        NavigationBarSettings.class,
                        NavigationBarSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(NfcSettings.class, NfcSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        BadgeAppIconSettings.class,
                        BadgeAppIconSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        ConfigureNotificationMoreSettings.class,
                        ConfigureNotificationMoreSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SecAutoGroupingFragment.class,
                        SecAutoGroupingFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SecNotificationIntelligenceFragment.class,
                        SecNotificationIntelligenceFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SecSummarizeContentFragment.class,
                        SecSummarizeContentFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        StatusBarNotificationSettings.class,
                        StatusBarNotificationSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        BriefPopUpSettings.class, BriefPopUpSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        OtherPrivacySettingsFragment.class,
                        OtherPrivacySettingsFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SecurityAndPrivacySettings.class,
                        SecurityAndPrivacySettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SecurityDashboardAccountsSecurityFragment.class,
                        SecurityDashboardAccountsSecurityFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SecurityDashboardAppSecurityFragment.class,
                        SecurityDashboardAppSecurityFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SecurityDashboardDeviceFindersFragment.class,
                        SecurityDashboardDeviceFindersFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SecurityDashboardTheftProtectionFragment.class,
                        SecurityDashboardTheftProtectionFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SecurityDashboardUpdatesFragment.class,
                        SecurityDashboardUpdatesFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        DeviceIdSettings.class, DeviceIdSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SecIccLockSettings.class, SecIccLockSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SoftwareUpdateSettings.class,
                        SoftwareUpdateSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        TaskBarSettings.class, TaskBarSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        Usefulfeature.class, Usefulfeature.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        FunctionKeySettings.class, FunctionKeySettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        IntelligenceServiceSettings.class,
                        IntelligenceServiceSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        FlexModePanelSettings.class,
                        FlexModePanelSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        MotionAndGestureSettings.class,
                        MotionAndGestureSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        FingerSensorGestureSettings.class,
                        FingerSensorGestureSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        MultiwindowSettings.class, MultiwindowSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        NewOneHandOperationSettings.class,
                        NewOneHandOperationSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        ConfigureWifiSettings.class,
                        ConfigureWifiSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        IntelligentWifiSettings.class,
                        IntelligentWifiSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        MobileWIPSExceptionList.class,
                        MobileWIPSExceptionList.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        MobileWIPSSettings.class, MobileWIPSSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SmartNetworkSwitchSettings.class,
                        SmartNetworkSwitchSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        SwitchToBetterWifiSettings.class,
                        SwitchToBetterWifiSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        WifiApSettings.class, WifiApSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        WifiApClientsManageMobileHotspot.class,
                        WifiApClientsManageMobileHotspot.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        WifiApHotspotUsageReport.class,
                        WifiApHotspotUsageReport.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        DataSaverSummary.class, DataSaverSummary.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(
                new SearchIndexableData(
                        LabsSettings.class, LabsSettings.SEARCH_INDEX_DATA_PROVIDER));
    }

    public final void addIndex(SearchIndexableData searchIndexableData) {
        ((HashSet) this.mProviders).add(searchIndexableData);
    }
}
