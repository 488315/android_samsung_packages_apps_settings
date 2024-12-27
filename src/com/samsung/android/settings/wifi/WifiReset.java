package com.samsung.android.settings.wifi;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;

import com.android.settings.Utils;
import com.android.settingslib.wifi.WifiEnterpriseRestrictionUtils;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.general.BaseResetSettingsData;
import com.samsung.android.settings.general.OnResetSettingsDataListener;
import com.samsung.android.settings.wifi.develop.WifiDevelopmentResetSettingsFragment;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SemWifiEntryFlags;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class WifiReset {
    public static final OnResetSettingsDataListener RESET_SETTINGS_DATA;
    public static final boolean mIsNotificationMenuSupported;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.WifiReset$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseResetSettingsData {
        @Override // com.samsung.android.settings.general.OnResetSettingsDataListener
        public final void resetSettings(final Context context) {
            Log.i("WifiReset", "Reset Wi-Fi Settings");
            if (Rune.isShopDemo(context)) {
                return;
            }
            Log.d("WifiReset", "WifiReset - reset");
            ContentResolver contentResolver = context.getContentResolver();
            WifiManager wifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
            Settings.Global.putString(
                    contentResolver, "airplane_mode_toggleable_radios", "bluetooth,wifi,nfc");
            Settings.Global.putInt(contentResolver, "wifi_scan_always_enabled", 0);
            Settings.Global.putInt(
                    contentResolver,
                    "wifi_networks_available_notification_on",
                    WifiReset.mIsNotificationMenuSupported ? 3 : 0);
            Settings.Global.putInt(contentResolver, "sem_wifi_ape_enabled", 1);
            if ("VZW".equals(SemWifiUtils.readSalesCode())) {
                Settings.Global.putInt(
                        contentResolver, "sem_wifi_switch_to_better_wifi_enabled", 0);
            } else {
                Settings.Global.putInt(
                        contentResolver, "sem_wifi_switch_to_better_wifi_enabled", 1);
            }
            Settings.Global.putInt(
                    contentResolver, "sem_wifi_switch_to_better_wifi_on_screen_enabled", 0);
            Settings.Global.putInt(contentResolver, "sem_wifi_developer_option_visible", 0);
            Settings.Global.putInt(contentResolver, "sem_auto_wifi_bubbletip_do_not_show_again", 0);
            Settings.Global.putInt(contentResolver, "sem_wifi_carrier_network_offload_enabled", 1);
            Settings.Global.putInt(contentResolver, "sem_wifi_l4s_enabled", 0);
            Settings.Global.putInt(
                    contentResolver, "wifi_switch_to_mobile_data_super_aggressive_mode_on", -1);
            HashMap hashMap = new HashMap();
            SemWifiManager semWifiManager =
                    (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
            hashMap.put(
                    "wifi_cloud_sa",
                    Boolean.valueOf(
                            SemWifiUtils.isWifiSyncEnabled(context)
                                    && SemWifiUtils.getSamsungAccount(context) == null));
            hashMap.put(
                    "wifi_cloud_sync",
                    Boolean.valueOf(
                            SemWifiUtils.isWifiSyncEnabled(context)
                                    && SemWifiUtils.getSamsungAccount(context) != null));
            hashMap.put(
                    "notify_open_networks",
                    Boolean.valueOf(
                            "USA".equalsIgnoreCase(SystemProperties.get("ro.csc.country_code"))
                                    || "Canada"
                                            .equalsIgnoreCase(
                                                    SystemProperties.get("ro.csc.country_code"))));
            hashMap.put("MobileWIPS", Boolean.valueOf(Utils.isSupportMobileWips));
            hashMap.put(
                    "auto_wifi",
                    Boolean.valueOf(
                            semWifiManager.isSupportedAutoWifi()
                                    && !com.android.settingslib.Utils.isWifiOnly(context)));
            hashMap.put("ape", Boolean.TRUE);
            hashMap.put(
                    "wifi_poor_network_detection",
                    Boolean.valueOf(Utils.locateSmartNetworkSwitch(context) != 3));
            hashMap.put(
                    "auto_connect_hotspot",
                    Boolean.valueOf(
                            UserHandle.myUserId() == 0 && Utils.SPF_SupportMobileApEnhanced));
            hashMap.put(
                    "switch_to_better_wifi_with_screen_off_only",
                    Boolean.valueOf(SemWifiUtils.isSwitchToBetterWifiEnabled(context) == 1));
            hashMap.put(
                    "switch_to_better_wifi",
                    Boolean.valueOf(SemWifiUtils.isSwitchToBetterWifiEnabled(context) == 2));
            if (hashMap.get("auto_wifi") != null
                    ? ((Boolean) hashMap.get("auto_wifi")).booleanValue()
                    : false) {
                Settings.Global.putInt(
                        contentResolver,
                        "sem_auto_wifi_control_enabled",
                        ((SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE))
                                        .getAutoWifiDefaultValue()
                                ? 1
                                : 0);
            }
            WifiDevelopmentResetSettingsFragment.resetDevelopmentOptions(context, wifiManager);
            SemWifiUtils.resetAiConfiguration(context);
            wifiManager.setWifiPasspointEnabled(true);
            wifiManager.setWepAllowed(true);
            SemWifiEntryFlags.isWepAllowed = -1;
            SharedPreferences.Editor edit =
                    context.getSharedPreferences("SettingsSCloudWifi", 0).edit();
            edit.clear();
            edit.commit();
            new Thread() { // from class: com.samsung.android.settings.wifi.WifiReset.1.1
                @Override // java.lang.Thread, java.lang.Runnable
                public final void run() {
                    WifiManager wifiManager2 =
                            (WifiManager) context.getSystemService(WifiManager.class);
                    SemWifiManager semWifiManager2 =
                            (SemWifiManager)
                                    context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
                    wifiManager2.factoryReset();
                    semWifiManager2.factoryReset();
                    if (WifiEnterpriseRestrictionUtils.isChangeWifiStateAllowed(context)) {
                        Settings.Global.putInt(context.getContentResolver(), "wifi_on", 1);
                    }
                }
            }.start();
        }
    }

    static {
        mIsNotificationMenuSupported = Rune.isUSA() || "CA".equals(Utils.readCountryCode());
        RESET_SETTINGS_DATA = new AnonymousClass1();
    }
}
