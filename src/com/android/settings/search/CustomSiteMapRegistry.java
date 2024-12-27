package com.android.settings.search;

import android.util.ArrayMap;

import com.android.settings.DisplaySettings;
import com.android.settings.applications.assist.ManageAssist;
import com.android.settings.applications.manageapplications.ManageApplications;
import com.android.settings.backup.UserBackupSettingsActivity;
import com.android.settings.connecteddevice.ConnectedDeviceDashboardFragment;
import com.android.settings.connecteddevice.usb.UsbDetailsFragment;
import com.android.settings.dashboard.profileselector.ProfileFragmentBridge$$ExternalSyntheticOutline0;
import com.android.settings.datausage.DataUsageSummary;
import com.android.settings.fuelgauge.batteryusage.PowerUsageAdvanced;
import com.android.settings.fuelgauge.batteryusage.PowerUsageSummary;
import com.android.settings.gestures.GestureNavigationSettingsFragment;
import com.android.settings.gestures.SystemNavigationGestureSettings;
import com.android.settings.location.LocationSettings;
import com.android.settings.location.RecentLocationAccessSeeAllFragment;
import com.android.settings.notification.ConfigureNotificationSettings;
import com.android.settings.notification.zen.ZenModeBlockedEffectsSettings;
import com.android.settings.notification.zen.ZenModeRestrictNotificationsSettings;
import com.android.settings.password.ChooseLockGeneric;
import com.android.settings.security.SecurityAdvancedSettings;
import com.android.settings.security.SecuritySettings;
import com.android.settings.security.screenlock.ScreenLockSettings;
import com.android.settings.system.SystemDashboardFragment;
import com.android.settings.tts.TextToSpeechSettings;
import com.android.settings.wifi.WifiSettings;
import com.android.settings.wifi.p2p.WifiP2pSettings;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.accessibility.home.SecAccessibilitySettings;
import com.samsung.android.settings.biometrics.BiometricsSettingsFragment;
import com.samsung.android.settings.biometrics.face.FaceEntry;
import com.samsung.android.settings.biometrics.fingerprint.FingerprintEntry;
import com.samsung.android.settings.bluetooth.BluetoothSettings;
import com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceSetting;
import com.samsung.android.settings.datausage.SecBillingCycleSettings;
import com.samsung.android.settings.deviceinfo.legalinfo.LegalInfoSettings;
import com.samsung.android.settings.deviceinfo.legalinfo.samsunglegal.SamsungLegalInfo;
import com.samsung.android.settings.display.SecAdaptiveDisplaySettings;
import com.samsung.android.settings.lockscreen.LockScreenNotificationSettings;
import com.samsung.android.settings.lockscreen.LockScreenSettings;
import com.samsung.android.settings.notification.StatusBarNotificationSettings;
import com.samsung.android.settings.security.SecIccLockSettings;
import com.samsung.android.settings.wifi.intelligent.IntelligentWifiSettings;
import com.samsung.android.settings.wifi.intelligent.SmartNetworkSwitchSettings;
import com.samsung.android.settings.wifi.intelligent.SwitchToBetterWifiSettings;

import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class CustomSiteMapRegistry {
    public static final Map CUSTOM_SITE_MAP;

    static {
        ArrayMap arrayMap = new ArrayMap();
        CUSTOM_SITE_MAP = arrayMap;
        arrayMap.put(
                ProfileFragmentBridge$$ExternalSyntheticOutline0.m(
                        WifiSettings.class,
                        arrayMap,
                        ProfileFragmentBridge$$ExternalSyntheticOutline0.m(
                                SystemNavigationGestureSettings.class,
                                arrayMap,
                                ProfileFragmentBridge$$ExternalSyntheticOutline0.m(
                                        ZenModeRestrictNotificationsSettings.class,
                                        arrayMap,
                                        ProfileFragmentBridge$$ExternalSyntheticOutline0.m(
                                                SystemDashboardFragment.class,
                                                arrayMap,
                                                ProfileFragmentBridge$$ExternalSyntheticOutline0.m(
                                                        ConnectedDeviceDashboardFragment.class,
                                                        arrayMap,
                                                        ProfileFragmentBridge$$ExternalSyntheticOutline0
                                                                .m(
                                                                        LocationSettings.class,
                                                                        arrayMap,
                                                                        ProfileFragmentBridge$$ExternalSyntheticOutline0
                                                                                .m(
                                                                                        PowerUsageSummary
                                                                                                .class,
                                                                                        arrayMap,
                                                                                        ProfileFragmentBridge$$ExternalSyntheticOutline0
                                                                                                .m(
                                                                                                        SecuritySettings
                                                                                                                .class,
                                                                                                        arrayMap,
                                                                                                        ScreenLockSettings
                                                                                                                .class
                                                                                                                .getName(),
                                                                                                        PowerUsageAdvanced
                                                                                                                .class),
                                                                                        RecentLocationAccessSeeAllFragment
                                                                                                .class),
                                                                        UsbDetailsFragment.class),
                                                        UserBackupSettingsActivity.class),
                                                ZenModeBlockedEffectsSettings.class),
                                        GestureNavigationSettingsFragment.class),
                                WifiP2pSettings.class),
                        WifiP2pSettings.class),
                WifiSettings.class.getName());
        if (Rune.isUSA()) {
            arrayMap.put(SamsungLegalInfo.class.getName(), LegalInfoSettings.class.getName());
        }
        arrayMap.put(
                ProfileFragmentBridge$$ExternalSyntheticOutline0.m(
                        ConfigureNotificationSettings.class,
                        arrayMap,
                        ProfileFragmentBridge$$ExternalSyntheticOutline0.m(
                                ConfigureNotificationSettings.class,
                                arrayMap,
                                ProfileFragmentBridge$$ExternalSyntheticOutline0.m(
                                        BluetoothSettings.class,
                                        arrayMap,
                                        ProfileFragmentBridge$$ExternalSyntheticOutline0.m(
                                                DisplaySettings.class,
                                                arrayMap,
                                                ProfileFragmentBridge$$ExternalSyntheticOutline0.m(
                                                        DataUsageSummary.class,
                                                        arrayMap,
                                                        ProfileFragmentBridge$$ExternalSyntheticOutline0
                                                                .m(
                                                                        LockScreenSettings.class,
                                                                        arrayMap,
                                                                        ProfileFragmentBridge$$ExternalSyntheticOutline0
                                                                                .m(
                                                                                        SecurityAdvancedSettings
                                                                                                .class,
                                                                                        arrayMap,
                                                                                        ProfileFragmentBridge$$ExternalSyntheticOutline0
                                                                                                .m(
                                                                                                        BiometricsSettingsFragment
                                                                                                                .class,
                                                                                                        arrayMap,
                                                                                                        ProfileFragmentBridge$$ExternalSyntheticOutline0
                                                                                                                .m(
                                                                                                                        BiometricsSettingsFragment
                                                                                                                                .class,
                                                                                                                        arrayMap,
                                                                                                                        ProfileFragmentBridge$$ExternalSyntheticOutline0
                                                                                                                                .m(
                                                                                                                                        ManageApplications
                                                                                                                                                .class,
                                                                                                                                        arrayMap,
                                                                                                                                        ProfileFragmentBridge$$ExternalSyntheticOutline0
                                                                                                                                                .m(
                                                                                                                                                        IntelligentWifiSettings
                                                                                                                                                                .class,
                                                                                                                                                        arrayMap,
                                                                                                                                                        ProfileFragmentBridge$$ExternalSyntheticOutline0
                                                                                                                                                                .m(
                                                                                                                                                                        IntelligentWifiSettings
                                                                                                                                                                                .class,
                                                                                                                                                                        arrayMap,
                                                                                                                                                                        SmartNetworkSwitchSettings
                                                                                                                                                                                .class
                                                                                                                                                                                .getName(),
                                                                                                                                                                        SwitchToBetterWifiSettings
                                                                                                                                                                                .class),
                                                                                                                                                        ManageAssist
                                                                                                                                                                .class),
                                                                                                                                        FaceEntry
                                                                                                                                                .class),
                                                                                                                        FingerprintEntry
                                                                                                                                .class),
                                                                                                        SecIccLockSettings
                                                                                                                .class),
                                                                                        ChooseLockGeneric
                                                                                                .ChooseLockGenericFragment
                                                                                                .class),
                                                                        SecBillingCycleSettings
                                                                                .class),
                                                        SecAdaptiveDisplaySettings.class),
                                                SecBluetoothLeBroadcastSourceSetting.class),
                                        StatusBarNotificationSettings.class),
                                LockScreenNotificationSettings.class),
                        TextToSpeechSettings.class),
                SecAccessibilitySettings.class.getName());
    }
}
