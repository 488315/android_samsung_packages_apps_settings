package com.android.settings;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.communal.CommunalPreferenceController;
import com.android.settings.enterprise.EnterprisePrivacyFeatureProviderImpl;
import com.android.settings.enterprise.EnterprisePrivacySettings;
import com.android.settings.network.MobileNetworkIntentConverter;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.safetycenter.SafetyCenterManagerWrapper;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.security.SecuritySettingsFeatureProvider;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.biometrics.face.FaceEntry;
import com.samsung.android.settings.biometrics.fingerprint.FingerprintEntry;
import com.samsung.android.settings.connection.SecSatelliteNetworksPreferenceController;
import com.samsung.android.settings.datausage.DataUsageUtilsCHN;
import com.samsung.android.settings.notification.StatusBarNotificationSettings;
import com.samsung.android.settings.notification.zen.SecZenModeDeleteRulesSetting;
import com.samsung.android.settings.privacy.SecurityDashboardUtils;
import com.samsung.android.settings.security.SecurityUtils;
import com.samsung.android.settings.softwareupdate.SoftwareUpdateLaunchFromExternal;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public abstract class Settings extends SettingsActivity {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AccessibilityButtonPreferenceActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AccessibilityColorAdjustmentSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AccessibilityColorLensSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AccessibilityDaltonizerSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AccessibilityDetailsSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AccessibilityEditShortcutsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AccessibilityHearingEnhancementsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AccessibilityHighContrastFontSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AccessibilityInversionSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AccessibilityMagnificationSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AccessibilitySettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AccessibilityShortcutEditRequestActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AccountDashboardActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AccountSyncSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ActiveKeySettingsActivity extends SettingsActivity {
        @Override // com.android.settings.SettingsActivity,
                  // com.android.settings.core.SettingsBaseActivity,
                  // com.samsung.android.settings.core.SecSettingsBaseActivity,
                  // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
                  // androidx.core.app.ComponentActivity, android.app.Activity
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            if (UsefulfeatureUtils.hasActiveKey()) {
                return;
            }
            finish();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AdaptiveBrightnessActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AdvancedConnectedDeviceActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AirplaneModeSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AlarmsAndRemindersActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AlarmsAndRemindersAppActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AmplifyAmbientSoundActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ApnEditorActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ApnSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AppAllowedCoverScreenSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AppBatteryUsageActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AppBubbleNotificationSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AppChannelListSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AppDashboardActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AppDrawOverlaySettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AppInteractAcrossProfilesSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AppManageExternalStorageActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AppManageFullScreenIntentsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AppMediaManagementAppsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AppMediaRoutingActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AppMemoryUsageActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AppNotificationSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AppPictureInPictureSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AppSplitViewSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AppTurnScreenOnSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AppUsageAccessSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AppWriteSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AutoHotspotConnectionActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AutoRotateAppsSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AutoSwitchSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AutomaticStorageManagerSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AvailableVirtualKeyboardActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class BadgeAppIconSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class BatteryInfoActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class BatterySaverScheduleSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class BatterySaverSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class BiometricsSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class BlueToothPairingActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class BluetoothAdvancedSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class BluetoothCastSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class BluetoothControlHistoryActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class BluetoothDashboardActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class BluetoothFindBroadcastsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class BluetoothPairingBlockSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class BluetoothScanHistoryActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class BluetoothSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class BugReportHandlerPickerActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ButtonNavigationSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class CameraCutoutSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class CaptioningSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class CellularSecuritySettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class ChangeNfcTagAppsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class ChangeWifiStateActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ChannelGroupNotificationSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ChooseAccountActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ClonedAppsListActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class CloudAccountSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ColorAndMotionActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ColorContrastActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class CombinedBiometricProfileSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class CombinedBiometricSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class CommunalSettingsActivity extends SettingsActivity {
        @Override // com.android.settings.SettingsActivity,
                  // com.android.settings.core.SettingsBaseActivity,
                  // com.samsung.android.settings.core.SecSettingsBaseActivity,
                  // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
                  // androidx.core.app.ComponentActivity, android.app.Activity
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            if (CommunalPreferenceController.isAvailable(this)) {
                return;
            }
            finish();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ConfigureNotificationMoreSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ConfigureNotificationSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ConfigureWifiSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ConnectedDeviceDashboardActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ConnectionsSettingsActivity extends SettingsActivity {
        @Override // com.android.settings.SettingsActivity
        public final boolean isValidFragment(String str) {
            if (!TextUtils.equals(
                    "com.samsung.android.settings.connection.ConnectionsSettings", str)) {
                return false;
            }
            Log.i("ConnectionsSettingsActivity", "Connectionsettings isValidFragment");
            return true;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ContinuityActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ConversationListSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class CreateShortcutActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class CryptSDCardSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class DarkModeAppsSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class DarkModeSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class DarkThemeSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class DataSaverSummaryActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class DataUsageSummaryActivity extends SettingsActivity {
        @Override // com.android.settings.SettingsActivity,
                  // com.android.settings.core.SettingsBaseActivity,
                  // com.samsung.android.settings.core.SecSettingsBaseActivity,
                  // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
                  // androidx.core.app.ComponentActivity, android.app.Activity
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            if (!Rune.SUPPORT_SMARTMANAGER_CN
                    || isFinishing()
                    || SemPersonaManager.isKnoxId(UserHandle.myUserId())) {
                return;
            }
            startActivity(DataUsageUtilsCHN.getSMDataUsageSummaryIntent(true));
            finish();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class DataUsageSummaryCHNActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class DateTimeSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class DevelopmentSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class DeviceAdminSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class DisplaySettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class DockSettingsActivity extends SettingsActivity {
        @Override // com.android.settings.SettingsActivity,
                  // com.android.settings.core.SettingsBaseActivity,
                  // com.samsung.android.settings.core.SecSettingsBaseActivity,
                  // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
                  // androidx.core.app.ComponentActivity, android.app.Activity
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            if (UsefulfeatureUtils.hasDockSettings(this)) {
                return;
            }
            finish();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class DreamSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class EasyModeAppActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class EnterprisePrivacySettingsActivity extends SettingsActivity {
        @Override // com.android.settings.SettingsActivity,
                  // com.android.settings.core.SettingsBaseActivity,
                  // com.samsung.android.settings.core.SecSettingsBaseActivity,
                  // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
                  // androidx.core.app.ComponentActivity, android.app.Activity
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            EnterprisePrivacyFeatureProviderImpl enterprisePrivacyFeatureProvider =
                    featureFactoryImpl.getEnterprisePrivacyFeatureProvider();
            DevicePolicyManager devicePolicyManager = enterprisePrivacyFeatureProvider.mDpm;
            int i = EnterprisePrivacyFeatureProviderImpl.MY_USER_ID;
            ComponentName profileOwnerOrDeviceOwnerSupervisionComponent =
                    devicePolicyManager.getProfileOwnerOrDeviceOwnerSupervisionComponent(
                            new UserHandle(i));
            Intent intent = null;
            if (profileOwnerOrDeviceOwnerSupervisionComponent != null) {
                Intent addFlags =
                        new Intent("android.settings.SHOW_PARENTAL_CONTROLS")
                                .setPackage(
                                        profileOwnerOrDeviceOwnerSupervisionComponent
                                                .getPackageName())
                                .addFlags(268435456);
                if (enterprisePrivacyFeatureProvider
                                .mPm
                                .queryIntentActivitiesAsUser(addFlags, 0, i)
                                .size()
                        != 0) {
                    intent = addFlags;
                }
            }
            if (intent != null) {
                enterprisePrivacyFeatureProvider.mContext.startActivity(intent);
                finish();
                return;
            }
            BaseSearchIndexProvider baseSearchIndexProvider =
                    EnterprisePrivacySettings.SEARCH_INDEX_DATA_PROVIDER;
            FeatureFactoryImpl featureFactoryImpl2 = FeatureFactoryImpl._factory;
            if (featureFactoryImpl2 == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            if (featureFactoryImpl2.getEnterprisePrivacyFeatureProvider().hasDeviceOwner()) {
                return;
            }
            finish();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class EthernetSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class EyeComfortSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class FOTASuggestionActivity extends Activity {
        @Override // android.app.Activity
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            sendBroadcast(
                    new Intent("sec.fota.action.SOFTWARE_UPDATE")
                            .setFlags(268435488)
                            .setPackage("com.wssyncmldm"));
            finish();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class FaceSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class FactoryResetActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class FactoryResetConfirmActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class FingerSensorGestureSettinsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class FingerprintSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class FlexModePanelDetailsSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class FlexModePanelSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class FloatingIconsNotificationSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class FrontScreenAppsSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class FullScreenAppsSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class FullScreenInSplitScreenViewSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class FunctionKeyDoublePressSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class FunctionKeyLongPressSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class FunctionKeySettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class FunctionKeyTipsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class GamesStorageActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class GeneralDeviceSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class GestureNavigationSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class HDReffectSettinsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class HearingDevicesActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class HearingDevicesPairingActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class HighPowerApplicationsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class HighRefreshRatesSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class IccLockSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class IdentityCheckMenuSettingsActivity extends SettingsActivity {
        @Override // com.android.settings.SettingsActivity,
                  // com.android.settings.core.SettingsBaseActivity,
                  // com.samsung.android.settings.core.SecSettingsBaseActivity,
                  // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
                  // androidx.core.app.ComponentActivity, android.app.Activity
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            if (SecurityDashboardUtils.isTheftProtectionSupported(getApplicationContext())) {
                return;
            }
            finish();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class IdentityCheckSettingsActivity extends SettingsActivity {
        @Override // com.android.settings.SettingsActivity,
                  // com.android.settings.core.SettingsBaseActivity,
                  // com.samsung.android.settings.core.SecSettingsBaseActivity,
                  // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
                  // androidx.core.app.ComponentActivity, android.app.Activity
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            if (SecurityDashboardUtils.isTheftProtectionSupported(getApplicationContext())) {
                return;
            }
            finish();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class InstallCertificateFromStorageActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class IntelligenceServiceSettingsActivity extends SettingsActivity {
        @Override // com.android.settings.SettingsActivity,
                  // com.android.settings.core.SettingsBaseActivity,
                  // com.samsung.android.settings.core.SecSettingsBaseActivity,
                  // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
                  // androidx.core.app.ComponentActivity, android.app.Activity
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            if (UserHandle.myUserId()
                    == UsefulfeatureUtils.getManagedProfileId(getApplicationContext())) {
                Intent intent =
                        new Intent(
                                "com.samsung.android.settings.action.INTELLIGENCE_SERVICE_GLOBAL_SETTINGS");
                intent.addFlags(603979776);
                startActivityAsUser(intent, UserHandle.of(0));
                finish();
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class IntelligentWifiSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class InteractAcrossProfilesSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class KeyboardLayoutPickerActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class KeyboardSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class KnoxRestrictionsActivity extends SettingsActivity {
        @Override // com.android.settings.SettingsActivity,
                  // com.android.settings.core.SettingsBaseActivity,
                  // com.samsung.android.settings.core.SecSettingsBaseActivity,
                  // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
                  // androidx.core.app.ComponentActivity, android.app.Activity
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            finish();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class LabsSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class LanguageAndInputSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class LanguagePackSettingsActivity extends SettingsActivity {
        public String mCallingPackage = ApnSettings.MVNO_NONE;

        @Override // com.android.settings.SettingsActivity,
                  // com.android.settings.core.SettingsBaseActivity,
                  // com.samsung.android.settings.core.SecSettingsBaseActivity,
                  // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
                  // androidx.core.app.ComponentActivity, android.app.Activity
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            if (Rune.FEATURE_OFFLINE_LANGUAGE_PACK) {
                this.mCallingPackage = getIntent().getStringExtra("package");
            } else {
                Toast.makeText(this, R.string.sec_offline_lang_pack_toast_not_support, 0).show();
                finish();
            }
        }

        @Override // com.samsung.android.settings.core.SecSettingsBaseActivity, android.app.Activity
        public final boolean onOptionsItemSelected(MenuItem menuItem) {
            if (menuItem.getItemId() != 16908332 || TextUtils.isEmpty(this.mCallingPackage)) {
                return super.onOptionsItemSelected(menuItem);
            }
            finish();
            return true;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class LanguageSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class LegalInformationActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class LocalePickerActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class LocationSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class LockscreenNotificationActivity extends SettingsActivity {
        @Override // com.samsung.android.settings.core.SecSettingsBaseActivity
        public final boolean canUsePathProvider() {
            return true;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class LockscreenSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class LongBackgroundTasksActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class LongBackgroundTasksAppActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ManageAppExternalSourcesActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ManageApplicationsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ManageAssistActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ManageDomainUrlsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ManageExternalSourcesActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ManageExternalStorageActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ManageFullScreenIntentsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ManageUnknownSourceAppsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ManagedProfileSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class MediaControlsSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class MediaManagementAppsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class MediaRoutingActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class MemtagPageActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class MobileDataUsageListActivity extends SettingsActivity {
        @Override // com.android.settings.SettingsActivity,
                  // com.android.settings.core.SettingsBaseActivity,
                  // com.samsung.android.settings.core.SecSettingsBaseActivity,
                  // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
                  // androidx.core.app.ComponentActivity, android.app.Activity
        public final void onCreate(Bundle bundle) {
            if (Rune.SUPPORT_SMARTMANAGER_CN) {
                Intent intent = new Intent();
                intent.setClassName(
                        "com.samsung.android.sm_cn",
                        "com.samsung.android.sm.datausage.ui.ManageAppData.ManageAppDataActivity");
                intent.addFlags(67108864);
                startActivity(intent);
                finish();
            }
            super.onCreate(bundle);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class MobileNetworkActivity extends SettingsActivity {
        public static final /* synthetic */ int $r8$clinit = 0;
        public MobileNetworkIntentConverter mIntentConverter;

        @Override // com.android.settings.SettingsActivity, android.app.Activity
        public final Intent getIntent() {
            Intent intent = super.getIntent();
            if (this.mIntentConverter == null) {
                this.mIntentConverter = new MobileNetworkIntentConverter(this);
            }
            Intent apply = this.mIntentConverter.apply(intent);
            return apply == null ? intent : apply;
        }

        @Override // androidx.activity.ComponentActivity, android.app.Activity
        public final void onNewIntent(Intent intent) {
            super.onNewIntent(intent);
            Log.d("MobileNetworkActivity", "Starting onNewIntent");
            if (this.mIntentConverter == null) {
                this.mIntentConverter = new MobileNetworkIntentConverter(this);
            }
            Intent apply = this.mIntentConverter.apply(intent);
            if (apply != null) {
                intent = apply;
            }
            createUiFromIntent(intent, null);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class MobileNetworkListActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ModifierKeysSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ModuleLicensesActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class MonoAudioActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class MoreSecurityPrivacySettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class MotionAndGestureSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class MouseAndTrackpadSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class MousePointerSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class MultiDevicesActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class MultiwindowSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class MuteAllSoundsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class MyDeviceInfoActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class NavigationBarGestureDetailSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class NavigationBarSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class NavigationBarSettingsInnerActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class NavigationModeSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class NearbyScanningActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class NetworkProviderSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class NetworkSelectActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class NewModePreviewActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class NfcAdvancedRoutingSettingActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class NfcOsaifukeitaiSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class NfcSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class NightDisplaySettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class NightDisplaySuggestionActivity extends NightDisplaySettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class NotificationAccessDetailsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class NotificationAccessSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class NotificationAppListActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class NotificationAssistantSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class NotificationPopupStyleSettingsActivity extends SettingsActivity {
        @Override // com.android.settings.SettingsActivity
        public final boolean isValidFragment(String str) {
            if (!TextUtils.equals(
                    "com.samsung.android.settings.notification.brief.BriefPopUpSettings", str)) {
                return false;
            }
            Log.i("NotificationPopupStyleSettingsActivity", "BriefPopUpSettings isValidFragment");
            return true;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class NotificationReviewPermissionsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class NotificationStationActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class OneHandedSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class OnehandOperationSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class OtherSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class OverlaySettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class PasswordsAndAutofillPickerActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class PaymentDCMSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class PaymentSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class PersonalizationServiceSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class PhysicalKeyboardActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class PictureInPictureSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class PowerMenuSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class PowerUsageSummaryActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class PremiumSmsAccessActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class PreviouslyConnectedDeviceActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class PrintJobSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class PrintSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class PrivacyControlsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class PrivacyDashboardActivity extends SettingsActivity {
        @VisibleForTesting
        public void handleSafetyCenterRedirection() {
            if (!isFinishing()
                    && "android.settings.PRIVACY_SETTINGS".equals(getIntent().getAction())) {
                SafetyCenterManagerWrapper.get().getClass();
                if (SafetyCenterManagerWrapper.isEnabled(this)) {
                    try {
                        startActivity(
                                new Intent("android.intent.action.SAFETY_CENTER")
                                        .setPackage(
                                                getPackageManager()
                                                        .getPermissionControllerPackageName()));
                        finish();
                    } catch (ActivityNotFoundException e) {
                        Log.e("PrivacyDashboardActivity", "Unable to open safety center", e);
                    }
                }
            }
        }

        @Override // com.android.settings.SettingsActivity,
                  // com.android.settings.core.SettingsBaseActivity,
                  // com.samsung.android.settings.core.SecSettingsBaseActivity,
                  // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
                  // androidx.core.app.ComponentActivity, android.app.Activity
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            handleSafetyCenterRedirection();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class PrivacySettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class PrivateSpaceBiometricSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class PrivateVolumeForgetActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class PublicVolumeSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ReduceBrightColorsSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class RegionalPreferencesActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class RegisterFaceSettingsActivity extends Activity {
        @Override // android.app.Activity
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            Intent intent = new Intent();
            intent.setClassName(getPackageName(), FaceEntry.class.getName());
            intent.setFlags(8388608);
            intent.putExtra("previousStage", "face_register_external");
            try {
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException unused) {
                    Log.e("FcstSettings_internal", "runRegister : Activity Not Found !");
                }
            } finally {
                finish();
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class RegisterFingerprintSettingsActivity extends Activity {
        @Override // android.app.Activity
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            if (SecurityUtils.hasFingerprintFeature(this)) {
                Intent intent = getIntent();
                intent.setClassName(getPackageName(), FingerprintEntry.class.getName());
                intent.putExtra("previousStage", "fingerprint_register_external");
                intent.setFlags(8388608);
                try {
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException unused) {
                        Log.e("FpstSettings_internal", "runRegister : Activity Not Found !");
                    }
                } finally {
                    finish();
                }
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ResetDashboardFragmentActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ResetMobileNetworkSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ResetNetworkActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ResetSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class RouterHistoryActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class RunningServicesActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SatelliteSettingActivity extends SettingsActivity {
        @Override // com.android.settings.SettingsActivity,
                  // com.android.settings.core.SettingsBaseActivity,
                  // com.samsung.android.settings.core.SecSettingsBaseActivity,
                  // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
                  // androidx.core.app.ComponentActivity, android.app.Activity
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            SecSatelliteNetworksPreferenceController secSatelliteNetworksPreferenceController =
                    new SecSatelliteNetworksPreferenceController(
                            getApplicationContext(), "satellite_networks");
            if (secSatelliteNetworksPreferenceController.isAvailable()) {
                if (secSatelliteNetworksPreferenceController.getMultiSimState()) {
                    Log.i("SatelliteSettingsActivity", "Call SatelliteSettings Multi Sim");
                    int intExtra = getIntent().getIntExtra("sub_id", -1);
                    Intent intent =
                            new Intent(
                                    "com.samsung.android.app.telephonyui.action.OPEN_NET_SETTINGS");
                    intent.putExtra("root_key", "SATELLITE_MESSAGING_CATEGORY");
                    intent.putExtra("sub_id", intExtra);
                    intent.putExtra("multi_sim", true);
                    intent.setPackage("com.samsung.android.app.telephonyui");
                    try {
                        Log.i(
                                "SatelliteSettingsActivity",
                                "start SatelliteSettings : " + intent.toString());
                        startActivity(intent);
                    } catch (ActivityNotFoundException unused) {
                        Log.e(
                                "SatelliteSettingsActivity",
                                "Activity NOT Found : " + intent.toString());
                    }
                } else {
                    Log.i("SatelliteSettingsActivity", "Call SatelliteSettings Not Multi Sim");
                    int intExtra2 = getIntent().getIntExtra("sub_id", -1);
                    Intent intent2 =
                            new Intent(
                                    "com.samsung.android.app.telephonyui.action.OPEN_NET_SETTINGS");
                    intent2.putExtra("root_key", "SATELLITE_MESSAGING_CATEGORY");
                    intent2.putExtra("sub_id", intExtra2);
                    intent2.setPackage("com.samsung.android.app.telephonyui");
                    try {
                        Log.i(
                                "SatelliteSettingsActivity",
                                "start SatelliteSettings : " + intent2.toString());
                        startActivity(intent2);
                    } catch (ActivityNotFoundException unused2) {
                        Log.e(
                                "SatelliteSettingsActivity",
                                "Activity NOT Found : " + intent2.toString());
                    }
                }
            }
            finish();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SavedAccessPointsSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ScanningSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ScreenTimeoutActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SecAllowedNetworksSettingsActivity extends SettingsActivity {
        @Override // com.android.settings.SettingsActivity,
                  // com.android.settings.core.SettingsBaseActivity,
                  // com.samsung.android.settings.core.SecSettingsBaseActivity,
                  // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
                  // androidx.core.app.ComponentActivity, android.app.Activity
        public final void onCreate(Bundle bundle) {
            if (Rune.SUPPORT_SMARTMANAGER_CN) {
                Intent intent = new Intent();
                intent.setClassName(
                        "com.samsung.android.sm_cn",
                        "com.samsung.android.sm.datausage.ui.ManageAppData.ManageAppDataActivity");
                intent.addFlags(67108864);
                startActivity(intent);
                finish();
            }
            super.onCreate(bundle);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SecBluetoothDeviceDetailsSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SecBluetoothLeBroadcastSourceActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SecCursorThicknessActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SecFontSizeActivity extends SettingsActivity {
        public boolean mIsFromRelativeLink;

        @Override // com.android.settings.SettingsActivity,
                  // com.android.settings.core.SettingsBaseActivity,
                  // com.samsung.android.settings.core.SecSettingsBaseActivity,
                  // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
                  // androidx.core.app.ComponentActivity, android.app.Activity
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            this.mIsFromRelativeLink = getIntent().getBooleanExtra("fromRelativeLink", false);
        }

        @Override // com.samsung.android.settings.core.SecSettingsBaseActivity, android.app.Activity
        public final boolean onOptionsItemSelected(MenuItem menuItem) {
            if (!this.mIsFromRelativeLink || menuItem.getItemId() != 16908332) {
                return super.onOptionsItemSelected(menuItem);
            }
            startActivity(
                    new Intent(getApplicationContext(), (Class<?>) DisplaySettingsActivity.class));
            finish();
            return true;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SecFontStyleActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SecScreenZoomActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SecSoundSystemSoundSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SecTetherSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SecVibrationIntensitySettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SecVolumeLimiterSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SecVolumeSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SecZenModeDeleteRulesSettingsActivity extends SettingsActivity {
        @Override // com.android.settings.SettingsActivity
        public final boolean isValidFragment(String str) {
            return super.isValidFragment(str)
                    || SecZenModeDeleteRulesSetting.class.getName().equals(str);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SecurityAdvancedSettings extends SettingsActivity {
        @VisibleForTesting
        public void handleMoreSettingsRedirection() {
            if (isFinishing()) {
                return;
            }
            SafetyCenterManagerWrapper.get().getClass();
            if (SafetyCenterManagerWrapper.isEnabled(this)) {
                try {
                    startActivity(
                            new Intent("com.android.settings.MORE_SECURITY_PRIVACY_SETTINGS"));
                    finish();
                } catch (ActivityNotFoundException e) {
                    Log.e("SecurityAdvancedActivity", "Unable to open More Settings", e);
                }
            }
        }

        @Override // com.android.settings.SettingsActivity,
                  // com.android.settings.core.SettingsBaseActivity,
                  // com.samsung.android.settings.core.SecSettingsBaseActivity,
                  // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
                  // androidx.core.app.ComponentActivity, android.app.Activity
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            handleMoreSettingsRedirection();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SecurityAndPrivacySettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SecurityDashboardActivity extends SettingsActivity {
        @Override // com.android.settings.SettingsActivity
        public final String getInitialFragmentName(Intent intent) {
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            ((SecuritySettingsFeatureProvider)
                            featureFactoryImpl.securitySettingsFeatureProvider$delegate.getValue())
                    .getClass();
            return super.getInitialFragmentName(intent);
        }

        @VisibleForTesting
        public void handleSafetyCenterRedirection() {
            if (isFinishing()) {
                return;
            }
            SafetyCenterManagerWrapper.get().getClass();
            if (SafetyCenterManagerWrapper.isEnabled(this)) {
                try {
                    startActivity(
                            new Intent("android.intent.action.SAFETY_CENTER")
                                    .setPackage(
                                            getPackageManager()
                                                    .getPermissionControllerPackageName()));
                    finish();
                } catch (ActivityNotFoundException e) {
                    Log.e("SecurityDashboardActivity", "Unable to open safety center", e);
                }
            }
        }

        @Override // com.android.settings.SettingsActivity
        @VisibleForTesting
        public boolean isValidFragment(String str) {
            if (!super.isValidFragment(str)) {
                if (str != null) {
                    FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                    if (featureFactoryImpl == null) {
                        throw new UnsupportedOperationException("No feature factory configured");
                    }
                    ((SecuritySettingsFeatureProvider)
                                    featureFactoryImpl.securitySettingsFeatureProvider$delegate
                                            .getValue())
                            .getClass();
                    if (TextUtils.equals(str, null)) {}
                }
                return false;
            }
            return true;
        }

        @Override // com.android.settings.SettingsActivity,
                  // com.android.settings.core.SettingsBaseActivity,
                  // com.samsung.android.settings.core.SecSettingsBaseActivity,
                  // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
                  // androidx.core.app.ComponentActivity, android.app.Activity
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            handleSafetyCenterRedirection();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ShowRegulatoryActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SideAndVolumeUpPreferenceActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SimLockStatusActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SimStatusActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SmartAutoRotateSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SoftwareUpdateSettingActivity extends SettingsActivity {
        public String mCallingPackage = ApnSettings.MVNO_NONE;

        @Override // com.android.settings.SettingsActivity,
                  // com.android.settings.core.SettingsBaseActivity,
                  // com.samsung.android.settings.core.SecSettingsBaseActivity,
                  // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
                  // androidx.core.app.ComponentActivity, android.app.Activity
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            Intent intent = getIntent();
            if (intent != null) {
                this.mCallingPackage = intent.getStringExtra("callingPackage");
            }
        }

        @Override // com.samsung.android.settings.core.SecSettingsBaseActivity, android.app.Activity
        public final boolean onOptionsItemSelected(MenuItem menuItem) {
            if (menuItem.getItemId() != 16908332 || TextUtils.isEmpty(this.mCallingPackage)) {
                return super.onOptionsItemSelected(menuItem);
            }
            finish();
            return true;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SoftwareUpdateSettingLaunchActivity extends Activity {
        @Override // android.app.Activity
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            try {
                startActivity(new SoftwareUpdateLaunchFromExternal(this).intent);
            } catch (RuntimeException e) {
                Log.w("SoftwareUpdateSettingLaunchActivity", "failed to launch", e);
            }
            finish();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SoundSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SpellCheckersSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class StatusActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class StatusBarNotificationActivity extends SettingsActivity {
        @Override // com.android.settings.SettingsActivity
        public final boolean isValidFragment(String str) {
            return super.isValidFragment(str)
                    || StatusBarNotificationSettings.class.getName().equals(str);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class StorageDashboardActivity extends SettingsActivity {
        @Override // com.android.settings.SettingsActivity,
                  // com.android.settings.core.SettingsBaseActivity,
                  // com.samsung.android.settings.core.SecSettingsBaseActivity,
                  // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
                  // androidx.core.app.ComponentActivity, android.app.Activity
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            try {
                try {
                    startActivity(new Intent("com.sec.android.app.myfiles.RUN_STORAGE_ANALYSIS"));
                } catch (ActivityNotFoundException unused) {
                    Log.e("StorageDashboardFrag", "RUN_STORAGE_ANALYSIS : Activity Not Found");
                }
            } finally {
                finish();
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class StorageUseActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class StylusUsiDetailsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SwipeForPopupViewSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SwipeForSplitViewSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SwitchForIndividualAppsSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class TaskBarRecentAppsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class TaskBarSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class TaskbarStyleSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class TestingSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class TetherSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class TextReadingSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class TextToSpeechSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class TouchAndHoldPreferenceActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class TrustedCredentialsSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class TurnScreenOnSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class UsageAccessSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class UsbDetailsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class UsefulFeatureMainActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class UserAspectRatioAppActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class UserAspectRatioAppListActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class UserCredentialsSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class UserDictionarySettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class UserManualLaunchActivity extends Activity {
        @Override // android.app.Activity
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            startActivity(Utils.getUserManualSearchURLIntent(this, null));
            finish();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class UserSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class VibrationSystemIntensitySettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class VirtualKeyboardActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class VolumeUpAndDownPreferenceActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class VpnSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class VrListenersSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class WallpaperSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class WebViewAppPickerActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class WifiAPITestActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class WifiApAutoHotspotSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class WifiApClientsManageMobileHotspotActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class WifiApEditSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class WifiApFamilySharingSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class WifiApInvitationListActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class WifiApOtpSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class WifiApSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class WifiCallingDisclaimerActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class WifiCallingSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class WifiControlHistoryActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class WifiDisplaySettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class WifiInfoActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class WifiP2pSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class WifiQrCodeActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class WifiScanningSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class WifiSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class WifiSwitchToMobileDataActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class WifiTetherSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class WirelessSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class WriteSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class XcoverTopKeySettingsActivity extends SettingsActivity {
        @Override // com.android.settings.SettingsActivity,
                  // com.android.settings.core.SettingsBaseActivity,
                  // com.samsung.android.settings.core.SecSettingsBaseActivity,
                  // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
                  // androidx.core.app.ComponentActivity, android.app.Activity
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            if (UsefulfeatureUtils.hasActiveKey()) {
                UsefulfeatureUtils.hasActiveKey();
            }
            finish();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ZenAccessDetailSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ZenAccessSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ZenModeAutomationSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ZenModeEventRuleSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ZenModeScheduleRuleSettingsActivity extends SettingsActivity {
        @Override // com.android.settings.SettingsActivity,
                  // com.android.settings.core.SettingsBaseActivity,
                  // com.samsung.android.settings.core.SecSettingsBaseActivity,
                  // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
                  // androidx.core.app.ComponentActivity, android.app.Activity
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            setContentView(R.layout.sec_dnd_schedule_rule_settings);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ZenModeSettingsActivity extends SettingsActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ZenModeSoundAndVibrationSettingsActivity extends SettingsActivity {
        @Override // com.android.settings.SettingsActivity
        public final boolean isValidFragment(String str) {
            if (!TextUtils.equals(
                    "com.android.settings.notification.zen.ZenModeSoundVibrationSettings", str)) {
                return false;
            }
            Log.i(
                    "ZenModeSoundAndVibrationSettingsActivity",
                    "ZenModeSoundVibrationSettings isValidFragment");
            return true;
        }
    }
}
