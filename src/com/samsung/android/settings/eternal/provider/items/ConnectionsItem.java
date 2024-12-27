package com.samsung.android.settings.eternal.provider.items;

import android.R;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.nfc.NfcAdapter;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import android.provider.Settings;
import android.sysprop.BluetoothProperties;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.uwb.UwbManager;

import androidx.preference.PreferenceManager;

import com.android.internal.telephony.TelephonyFeatures;
import com.android.internal.util.MemInfoReader;
import com.android.settings.Utils;

import com.samsung.android.bluetooth.SemBluetoothCastAdapter;
import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.lib.episode.Scene;
import com.samsung.android.lib.episode.SceneResult;
import com.samsung.android.lib.episode.SourceInfo;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.bluetooth.BluetoothIBRSettings;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.vpn.smartswitch.VpnProfileUtils;
import com.samsung.android.util.SemLog;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SemWifiEntryFlags;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.sec.ims.extensions.WiFiManagerExt;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ConnectionsItem implements Recoverable {
    public NfcAdapter mNfcAdapter;

    public static boolean isDefaultWipsSettingEnabled() {
        if ("wifi-only".equalsIgnoreCase(SemSystemProperties.get("ro.carrier", "Unknown").trim())
                || "yes".equalsIgnoreCase(SemSystemProperties.get("ro.radio.noril", "no").trim())) {
            SemLog.d("ConnectionsItem", "WI-Fi only model");
            return true;
        }
        SemLog.d("ConnectionsItem", "Carrier Model");
        if ("VZW".equals(TelephonyFeatures.getSalesCode())) {
            SemLog.d("ConnectionsItem", "VZW operator");
        } else if ("VPP".equals(TelephonyFeatures.getSalesCode())) {
            SemLog.d("ConnectionsItem", "VPP operator");
        } else {
            if (!"CCT".equals(TelephonyFeatures.getSalesCode())) {
                String salesCode = TelephonyFeatures.getSalesCode();
                if (salesCode == null) {
                    return true;
                }
                SemLog.d("ConnectionsItem", "Other Operator Name = ".concat(salesCode));
                return true;
            }
            SemLog.d("ConnectionsItem", "CCT operator");
        }
        return false;
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final boolean followRestoreSkipPolicy(Scene scene) {
        if ("/Settings/Connections/Nfc".equals(scene.mSceneKey)) {
            return false;
        }
        if ("/Settings/Connections/Bluetooth/Advanced/MusicShare".equals(scene.mSceneKey)) {
            String value = scene.getValue(null, false);
            int attributeInt = scene.getAttributeInt(0, "castRange");
            int attributeInt2 = scene.getAttributeInt(0, "castPermission");
            int attributeInt3 = scene.getAttributeInt(600000, "castTimeout");
            if ((!TextUtils.isEmpty(value) && Integer.valueOf(value).intValue() != 1)
                    || attributeInt != 0
                    || attributeInt2 != 0
                    || attributeInt3 != 600000) {
                return false;
            }
        } else if ("/Settings/Connections/WiFi/Advanced/SwitchToMobileData/Enabled"
                .equals(scene.mSceneKey)) {
            return false;
        }
        return true;
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final List getTestScenes(Context context, String str) {
        return null;
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final Scene.Builder getValue(Context context, SourceInfo sourceInfo, String str) {
        boolean z;
        char c;
        int i;
        int i2;
        Scene.Builder builder = new Scene.Builder(str);
        ContentResolver contentResolver = context.getContentResolver();
        try {
            z = true;
            switch (str.hashCode()) {
                case -2056475201:
                    if (str.equals("/Settings/Connections/LocationBluetoothScan")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -2013957089:
                    if (str.equals("/Settings/Connections/WiFi/Advanced/WIPS")) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case -1315592334:
                    if (str.equals("/Settings/Connections/Bluetooth/Advanced/RingtoneSync")) {
                        c = 23;
                        break;
                    }
                    c = 65535;
                    break;
                case -1147230441:
                    if (str.equals(
                            "/Settings/Connections/WiFi/Advanced/SwitchToMobileData/Enabled")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -913370528:
                    if (str.equals("/Settings/Connections/PrivateDnsMode")) {
                        c = 19;
                        break;
                    }
                    c = 65535;
                    break;
                case -404770921:
                    if (str.equals(
                            "/Settings/Connections/WiFi/Intelligent/SwitchToBetterWifiWhileScreenIsOn")) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case -327826001:
                    if (str.equals("/Settings/Connections/WiFi/Intelligent/SwitchToBetterWifi")) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case -257058968:
                    if (str.equals("/Settings/Connections/WiFi/Advanced/AllowWepNetworks")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case -144438508:
                    if (str.equals("/Settings/Connections/Nfc")) {
                        c = 16;
                        break;
                    }
                    c = 65535;
                    break;
                case -144431255:
                    if (str.equals("/Settings/Connections/Uwb")) {
                        c = 17;
                        break;
                    }
                    c = 65535;
                    break;
                case 7256044:
                    if (str.equals("/Settings/Connections/Location")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 13616958:
                    if (str.equals("/Settings/Connections/LocationWifiScan")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 124804277:
                    if (str.equals("/Settings/Connections/WiFi/Advanced/Hotspot20/Enabled")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 482301911:
                    if (str.equals(
                            "/Settings/Connections/WiFi/Intelligent/RealtimeDataPriorityMode")) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case 848530791:
                    if (str.equals("/Settings/Connections/VpnProfiles")) {
                        c = 21;
                        break;
                    }
                    c = 65535;
                    break;
                case 894520135:
                    if (str.equals("/Settings/Connections/NearbyScanning")) {
                        c = 18;
                        break;
                    }
                    c = 65535;
                    break;
                case 900723733:
                    if (str.equals(
                            "/Settings/Connections/WiFi/Advanced/SwitchToMobileData/IndividualApps")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 1271747361:
                    if (str.equals("/Settings/Connections/PrivateDnsSpecifier")) {
                        c = 20;
                        break;
                    }
                    c = 65535;
                    break;
                case 1395313742:
                    if (str.equals("/Settings/Connections/WiFi/Advanced/AutoWifi")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 1461201006:
                    if (str.equals(
                            "/Settings/Connections/WiFi/Advanced/SwitchToMobileData/Aggressive")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 1471337453:
                    if (str.equals("/Settings/Connections/LocationMethod")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1649983190:
                    if (str.equals("/Settings/Connections/WiFi/Advanced/WifiOffload")) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case 1965260815:
                    if (str.equals("/Settings/Connections/Bluetooth/Advanced/MusicShare")) {
                        c = 22;
                        break;
                    }
                    c = 65535;
                    break;
                case 1996385321:
                    if (str.equals("/Settings/Connections/WiFi/Advanced/WifiNotifications")) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case 2116697573:
                    if (str.equals("/Settings/Connections/Bluetooth/Moremenu/Auracast")) {
                        c = 24;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
        } catch (Exception e) {
            SemLog.e("ConnectionsItem", e.getStackTrace()[0].toString());
        }
        switch (c) {
            case 0:
                builder.setValue(
                        Integer.valueOf(
                                Settings.Secure.getInt(contentResolver, "location_mode", 0)),
                        false);
                return builder;
            case 1:
                builder.setValue(
                        Settings.Secure.getString(contentResolver, "location_providers_allowed"),
                        false);
                return builder;
            case 2:
                builder.setValue(
                        Integer.valueOf(
                                Settings.Global.getInt(
                                        contentResolver, "wifi_scan_always_enabled", 0)),
                        false);
                return builder;
            case 3:
                builder.setValue(
                        Integer.valueOf(
                                Settings.Global.getInt(
                                        contentResolver, "ble_scan_always_enabled", 0)),
                        false);
                return builder;
            case 4:
                int i3 =
                        Settings.Global.getInt(
                                contentResolver, "wifi_watchdog_poor_network_test_enabled", -1);
                int i4 =
                        !((SemWifiManager)
                                                context.getSystemService(
                                                        WiFiManagerExt.SEM_WIFI_SERVICE))
                                        .isSwitchToMobileDataDefaultOff()
                                ? 1
                                : 0;
                SemLog.i("ConnectionsItem", "SNS getValue=" + i3 + " defaultValue=" + i4);
                builder.setValue(Integer.valueOf(i3), false);
                if (i4 != i3) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case 5:
                int i5 =
                        Settings.Global.getInt(
                                contentResolver,
                                "wifi_watchdog_poor_network_aggressive_mode_on",
                                -1);
                int i6 =
                        !((SemWifiManager)
                                                context.getSystemService(
                                                        WiFiManagerExt.SEM_WIFI_SERVICE))
                                        .isSwitchToMobileDataDefaultOff()
                                ? 1
                                : 0;
                builder.setValue(Integer.valueOf(i5), false);
                if (i6 != i5) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case 6:
                builder.setValue(
                        Integer.valueOf(
                                Settings.Global.getInt(
                                        contentResolver,
                                        "wifi_switch_for_individual_apps_enabled",
                                        0)),
                        false);
                return builder;
            case 7:
                SubscriptionManager subscriptionManager = SemWifiUtils.mSubscriptionManager;
                boolean autoWifiDefaultValue =
                        ((SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE))
                                .getAutoWifiDefaultValue();
                int i7 =
                        Settings.Global.getInt(
                                contentResolver,
                                "sem_auto_wifi_control_enabled",
                                autoWifiDefaultValue ? 1 : 0);
                builder.setValue(Integer.valueOf(i7), false);
                if (autoWifiDefaultValue != i7) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case '\b':
                boolean isWifiPasspointEnabled =
                        ((WifiManager) context.getSystemService(WifiManager.class))
                                .isWifiPasspointEnabled();
                builder.setValue(Integer.valueOf(isWifiPasspointEnabled ? 1 : 0), false);
                if (true != isWifiPasspointEnabled) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case '\t':
                boolean isWepAllowed = SemWifiEntryFlags.isWepAllowed(context);
                builder.setValue(Integer.valueOf(isWepAllowed ? 1 : 0), false);
                if (true != isWepAllowed) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case '\n':
                MemInfoReader memInfoReader = new MemInfoReader();
                memInfoReader.readMemInfo();
                int i8 =
                        (memInfoReader.getTotalSizeKb() < 4445304 || !isDefaultWipsSettingEnabled())
                                ? 0
                                : 1;
                int i9 = Settings.Secure.getInt(contentResolver, "wifi_mwips", i8);
                builder.setValue(Integer.valueOf(i9), false);
                if (i9 != i8) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case 11:
                String str2 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                boolean equals = "VZW".equals(Utils.getSalesCode());
                int i10 =
                        Settings.System.getInt(
                                contentResolver, "wifi_offload_network_notify", equals ? 1 : 0);
                builder.setValue(Integer.valueOf(i10), false);
                if (i10 != equals) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case '\f':
                String str3 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                int i11 = !"CCT".equals(Utils.getSalesCode()) ? 1 : 0;
                int i12 =
                        Settings.Global.getInt(
                                contentResolver, "wifi_networks_available_notification_on", i11);
                builder.setValue(Integer.valueOf(i12), false);
                if (i12 != i11) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case '\r':
                String str4 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                int i13 = !"VZW".equals(Utils.getSalesCode()) ? 1 : 0;
                int i14 =
                        Settings.Global.getInt(
                                contentResolver, "sem_wifi_switch_to_better_wifi_enabled", i13);
                builder.setValue(Integer.valueOf(i14), false);
                if (i14 != i13) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case 14:
                String str5 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                int i15 = !"VZW".equals(Utils.getSalesCode()) ? 1 : 0;
                int i16 =
                        Settings.Global.getInt(
                                contentResolver,
                                "sem_wifi_switch_to_better_wifi_on_screen_enabled",
                                i15);
                builder.setValue(Integer.valueOf(i16), false);
                if (i16 != i15) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case 15:
                int i17 = Settings.Global.getInt(contentResolver, "sem_wifi_ape_enabled", 1);
                builder.setValue(Integer.valueOf(i17), false);
                if (i17 != 1) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case 16:
                NfcAdapter defaultAdapter = NfcAdapter.getDefaultAdapter(context);
                this.mNfcAdapter = defaultAdapter;
                if (defaultAdapter != null) {
                    builder.setValue(Integer.valueOf(defaultAdapter.getAdapterState()), false);
                    if (this.mNfcAdapter.isSecureNfcSupported()) {
                        builder.addAttribute(
                                Integer.valueOf(this.mNfcAdapter.isSecureNfcEnabled() ? 1 : 0),
                                "securenfc");
                    }
                    if (this.mNfcAdapter.isReaderOptionSupported()) {
                        builder.addAttribute(
                                Integer.valueOf(this.mNfcAdapter.isReaderOptionEnabled() ? 1 : 0),
                                "nfcReaderMode");
                    }
                }
                return builder;
            case 17:
                if (context.getPackageManager().hasSystemFeature("android.hardware.uwb")) {
                    i = Settings.Global.getInt(context.getContentResolver(), "uwb_enabled", 1);
                    i2 = 1;
                } else {
                    i = 0;
                    i2 = 0;
                }
                builder.setValue(Integer.valueOf(i), false);
                if (i2 != i) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case 18:
                int i18 =
                        Settings.System.getInt(
                                context.getContentResolver(), "nearby_scanning_enabled", 1);
                builder.setValue(Integer.valueOf(i18), false);
                if (1 != i18) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case 19:
                builder.setValue(
                        Settings.Global.getString(context.getContentResolver(), "private_dns_mode"),
                        false);
                return builder;
            case 20:
                Object string =
                        Settings.Global.getString(
                                context.getContentResolver(), "private_dns_specifier");
                if (NetworkAnalyticsConstants.DataPoints.HOSTNAME.equalsIgnoreCase(
                        Settings.Global.getString(
                                context.getContentResolver(), "private_dns_mode"))) {
                    builder.setValue(string, false);
                } else {
                    builder.setValue(null, false);
                }
                return builder;
            case 21:
                String vpnProfileData = VpnProfileUtils.getVpnProfileData();
                if (!TextUtils.isEmpty(vpnProfileData)) {
                    builder.setValue(vpnProfileData, true);
                    builder.setDefault(false);
                }
                return builder;
            case 22:
                if (SemBluetoothCastAdapter.isBluetoothCastSupported()) {
                    int i19 = Settings.Secure.getInt(contentResolver, "bluetooth_cast_mode", 1);
                    builder.setValue(Integer.valueOf(i19), false);
                    builder.addAttribute(
                            Integer.valueOf(
                                    Settings.Secure.getInt(
                                            contentResolver, "bluetooth_cast_range", 0)),
                            "castRange");
                    builder.addAttribute(
                            Integer.valueOf(
                                    Settings.Secure.getInt(
                                            contentResolver, "bluetooth_cast_permission", 0)),
                            "castPermission");
                    builder.addAttribute(
                            Integer.valueOf(
                                    Settings.Secure.getInt(
                                            contentResolver, "bluetooth_cast_timeout", 600000)),
                            "castTimeout");
                    if (1 != i19) {
                        z = false;
                    }
                    builder.setDefault(z);
                } else {
                    builder.setValue(null, false);
                    builder.setDefault(false);
                }
                return builder;
            case 23:
                String str6 = SystemProperties.get("persist.bluetooth.enableinbandringing");
                boolean z2 = context.getResources().getBoolean(R.bool.config_windowNoTitleDefault);
                boolean isSupportSoftPhone = BluetoothIBRSettings.isSupportSoftPhone();
                if (!ApnSettings.MVNO_NONE.equals(str6) && (z2 || isSupportSoftPhone)) {
                    boolean z3 =
                            SystemProperties.getBoolean(
                                    "persist.bluetooth.disableinbandringing", false);
                    builder.setValue(Boolean.valueOf(z3), false);
                    builder.setDefault(!z3);
                    return builder;
                }
                builder.setValue(null, false);
                builder.setDefault(false);
                return builder;
            case 24:
                if (((Boolean)
                                BluetoothProperties.isProfileBapBroadcastSourceEnabled()
                                        .orElse(Boolean.FALSE))
                        .booleanValue()) {
                    Object string2 =
                            Settings.Secure.getString(
                                    contentResolver, "bluetooth_le_broadcast_name");
                    if (string2 == null) {
                        builder.setValue("No", false);
                        builder.setDefault(true);
                    } else {
                        SharedPreferences sharedPreferences =
                                context.getSharedPreferences(
                                        PreferenceManager.getDefaultSharedPreferencesName(context),
                                        0);
                        builder.setValue(string2, false);
                        builder.addAttribute(
                                Settings.Secure.getString(
                                        contentResolver, "bluetooth_le_broadcast_code"),
                                KnoxContainerManager.CONTAINER_CREATION_STATUS_CODE);
                        builder.addAttribute(
                                Integer.valueOf(
                                        Settings.Secure.getInt(
                                                contentResolver,
                                                "bluetooth_le_encrypted_broadcast",
                                                1)),
                                "useCode");
                        builder.addAttribute(
                                Boolean.valueOf(
                                        sharedPreferences.getBoolean(
                                                "CompatibilitySwitchState", false)),
                                "compatibility");
                        builder.setDefault(false);
                    }
                } else {
                    builder.setValue("No", false);
                    builder.setDefault(true);
                }
                return builder;
            default:
                SemLog.d("ConnectionsItem", "unknown key : ".concat(str));
                return builder;
        }
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final SceneResult isOpenable(Context context, String str) {
        return null;
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final SceneResult setValue(
            Context context, String str, Scene scene, SourceInfo sourceInfo) {
        char c;
        ContentResolver contentResolver = context.getContentResolver();
        String value = scene.getValue(null, false);
        SceneResult.Builder builder = new SceneResult.Builder(str);
        builder.mResultType = SceneResult.ResultType.RESULT_OK;
        try {
            int i = 1;
            boolean z = true;
            boolean z2 = true;
            boolean z3 = true;
            switch (str.hashCode()) {
                case -2056475201:
                    if (str.equals("/Settings/Connections/LocationBluetoothScan")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -2013957089:
                    if (str.equals("/Settings/Connections/WiFi/Advanced/WIPS")) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case -1315592334:
                    if (str.equals("/Settings/Connections/Bluetooth/Advanced/RingtoneSync")) {
                        c = 23;
                        break;
                    }
                    c = 65535;
                    break;
                case -1147230441:
                    if (str.equals(
                            "/Settings/Connections/WiFi/Advanced/SwitchToMobileData/Enabled")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -913370528:
                    if (str.equals("/Settings/Connections/PrivateDnsMode")) {
                        c = 19;
                        break;
                    }
                    c = 65535;
                    break;
                case -404770921:
                    if (str.equals(
                            "/Settings/Connections/WiFi/Intelligent/SwitchToBetterWifiWhileScreenIsOn")) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case -327826001:
                    if (str.equals("/Settings/Connections/WiFi/Intelligent/SwitchToBetterWifi")) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case -257058968:
                    if (str.equals("/Settings/Connections/WiFi/Advanced/AllowWepNetworks")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case -144438508:
                    if (str.equals("/Settings/Connections/Nfc")) {
                        c = 16;
                        break;
                    }
                    c = 65535;
                    break;
                case -144431255:
                    if (str.equals("/Settings/Connections/Uwb")) {
                        c = 17;
                        break;
                    }
                    c = 65535;
                    break;
                case 7256044:
                    if (str.equals("/Settings/Connections/Location")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 13616958:
                    if (str.equals("/Settings/Connections/LocationWifiScan")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 124804277:
                    if (str.equals("/Settings/Connections/WiFi/Advanced/Hotspot20/Enabled")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 482301911:
                    if (str.equals(
                            "/Settings/Connections/WiFi/Intelligent/RealtimeDataPriorityMode")) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case 848530791:
                    if (str.equals("/Settings/Connections/VpnProfiles")) {
                        c = 21;
                        break;
                    }
                    c = 65535;
                    break;
                case 894520135:
                    if (str.equals("/Settings/Connections/NearbyScanning")) {
                        c = 18;
                        break;
                    }
                    c = 65535;
                    break;
                case 900723733:
                    if (str.equals(
                            "/Settings/Connections/WiFi/Advanced/SwitchToMobileData/IndividualApps")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 1271747361:
                    if (str.equals("/Settings/Connections/PrivateDnsSpecifier")) {
                        c = 20;
                        break;
                    }
                    c = 65535;
                    break;
                case 1395313742:
                    if (str.equals("/Settings/Connections/WiFi/Advanced/AutoWifi")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 1461201006:
                    if (str.equals(
                            "/Settings/Connections/WiFi/Advanced/SwitchToMobileData/Aggressive")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 1471337453:
                    if (str.equals("/Settings/Connections/LocationMethod")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1649983190:
                    if (str.equals("/Settings/Connections/WiFi/Advanced/WifiOffload")) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case 1965260815:
                    if (str.equals("/Settings/Connections/Bluetooth/Advanced/MusicShare")) {
                        c = 22;
                        break;
                    }
                    c = 65535;
                    break;
                case 1996385321:
                    if (str.equals("/Settings/Connections/WiFi/Advanced/WifiNotifications")) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case 2116697573:
                    if (str.equals("/Settings/Connections/Bluetooth/Moremenu/Auracast")) {
                        c = 24;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            SceneResult.ResultType resultType = SceneResult.ResultType.RESULT_FAIL;
            switch (c) {
                case 0:
                    Settings.Secure.putInt(
                            contentResolver, "location_mode", Integer.valueOf(value).intValue());
                    break;
                case 1:
                    Settings.Secure.putString(contentResolver, "location_providers_allowed", value);
                    break;
                case 2:
                    Settings.Global.putInt(
                            contentResolver,
                            "wifi_scan_always_enabled",
                            Integer.valueOf(value).intValue());
                    break;
                case 3:
                    Settings.Global.putInt(
                            contentResolver,
                            "ble_scan_always_enabled",
                            Integer.valueOf(value).intValue());
                    break;
                case 4:
                    if (!KnoxUtils.appRestrictionState) {
                        if (Integer.valueOf(value).intValue() != -1) {
                            int i2 =
                                    Settings.Global.getInt(
                                            contentResolver,
                                            "wifi_num_of_switch_to_mobile_data_toggle",
                                            0);
                            int intValue = Integer.valueOf(value).intValue();
                            int i3 =
                                    Settings.Global.getInt(
                                            contentResolver,
                                            "wifi_watchdog_poor_network_test_enabled",
                                            -1);
                            if (intValue > 1) {
                                intValue -= 2;
                            }
                            SemLog.i(
                                    "ConnectionsItem",
                                    "SNS toggle=" + i2 + " backup=" + intValue + " current=" + i3);
                            if (i2 != 0) {
                                i = i3;
                            } else if (i3 != 1) {
                                i = intValue;
                            }
                            Settings.Global.putInt(
                                    contentResolver, "wifi_watchdog_poor_network_test_enabled", i);
                            break;
                        } else {
                            break;
                        }
                    } else {
                        int intValue2 = Integer.valueOf(value).intValue();
                        if (intValue2 > 1) {
                            intValue2 -= 2;
                        }
                        Settings.Global.putInt(
                                contentResolver,
                                "wifi_watchdog_poor_network_test_enabled",
                                intValue2);
                        SemLog.i(
                                "ConnectionsItem",
                                "wifi_watchdog_poor_network_test_enabled was set as "
                                        + intValue2
                                        + " because of restriction");
                        break;
                    }
                case 5:
                    String str2 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                    String salesCode = Utils.getSalesCode();
                    if (Integer.valueOf(value).intValue() != -1
                            && !"vzw".equalsIgnoreCase(salesCode)) {
                        Settings.Global.putInt(
                                contentResolver,
                                "wifi_watchdog_poor_network_aggressive_mode_on",
                                Integer.valueOf(value).intValue());
                        break;
                    }
                    break;
                case 6:
                    if (Integer.valueOf(value).intValue() != -1) {
                        Settings.Global.putInt(
                                contentResolver,
                                "wifi_switch_for_individual_apps_enabled",
                                Integer.valueOf(value).intValue());
                        break;
                    } else {
                        builder.mResultType = resultType;
                        builder.mErrorType = SceneResult.ErrorType.NOT_SUPPORTED;
                        break;
                    }
                case 7:
                    Settings.Global.putInt(
                            contentResolver,
                            "sem_auto_wifi_control_enabled",
                            Integer.valueOf(value).intValue());
                    break;
                case '\b':
                    WifiManager wifiManager =
                            (WifiManager) context.getSystemService(WifiManager.class);
                    if (Integer.valueOf(value).intValue() != 1) {
                        z3 = false;
                    }
                    wifiManager.setWifiPasspointEnabled(z3);
                    break;
                case '\t':
                    WifiManager wifiManager2 =
                            (WifiManager) context.getSystemService(WifiManager.class);
                    if (Integer.valueOf(value).intValue() != 1) {
                        z2 = false;
                    }
                    wifiManager2.setWepAllowed(z2);
                    SemWifiEntryFlags.isWepAllowed = -1;
                    break;
                case '\n':
                    Settings.Secure.putInt(
                            contentResolver,
                            "wifi_mwips",
                            Integer.valueOf(value).intValue() == 1 ? 3 : 2);
                    break;
                case 11:
                    Settings.System.putInt(
                            contentResolver,
                            "wifi_offload_network_notify",
                            Integer.valueOf(value).intValue());
                    break;
                case '\f':
                    Settings.Global.putInt(
                            contentResolver,
                            "wifi_networks_available_notification_on",
                            Integer.valueOf(value).intValue());
                    break;
                case '\r':
                    Settings.Global.putInt(
                            contentResolver,
                            "sem_wifi_switch_to_better_wifi_enabled",
                            Integer.valueOf(value).intValue());
                    break;
                case 14:
                    Settings.Global.putInt(
                            contentResolver,
                            "sem_wifi_switch_to_better_wifi_on_screen_enabled",
                            Integer.valueOf(value).intValue());
                    break;
                case 15:
                    Settings.Global.putInt(
                            contentResolver,
                            "sem_wifi_ape_enabled",
                            Integer.valueOf(value).intValue());
                    break;
                case 16:
                    NfcAdapter defaultAdapter = NfcAdapter.getDefaultAdapter(context);
                    this.mNfcAdapter = defaultAdapter;
                    if (defaultAdapter == null) {
                        builder.mResultType = resultType;
                        builder.mErrorType = SceneResult.ErrorType.NOT_SUPPORTED;
                        break;
                    } else {
                        SemLog.d(
                                "ConnectionsItem",
                                "isRestoreViaFastTrack = " + sourceInfo.mRestoreViaFastTrack);
                        if (sourceInfo.mRestoreViaFastTrack) {
                            context.sendBroadcast(
                                    new Intent(
                                            "com.samsung.android.nfc.action.FAST_TRACK_RESTORE"));
                        }
                        if (this.mNfcAdapter.getAdapterState() == 1) {
                            String str3 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                            if (context.getPackageManager()
                                    .hasSystemFeature("com.samsung.android.nfc.gpfelica")) {
                                builder.mResultType = resultType;
                                builder.mErrorType = SceneResult.ErrorType.INVALID_DATA;
                            } else {
                                this.mNfcAdapter.enable();
                            }
                        } else if (!this.mNfcAdapter.isReaderOptionEnabled()) {
                            this.mNfcAdapter.enableReaderOption(true);
                        }
                        int attributeInt = scene.getAttributeInt(-1, "securenfc");
                        int attributeInt2 = scene.getAttributeInt(-1, "nfcReaderMode");
                        if (this.mNfcAdapter.isSecureNfcSupported()) {
                            if (attributeInt == 1) {
                                this.mNfcAdapter.enableSecureNfc(true);
                            } else if (attributeInt == 0) {
                                this.mNfcAdapter.enableSecureNfc(false);
                            } else {
                                builder.mResultType = resultType;
                                builder.mErrorType = SceneResult.ErrorType.INVALID_DATA;
                            }
                        }
                        if (this.mNfcAdapter.isReaderOptionSupported()) {
                            SemLog.d(
                                    "ConnectionsItem",
                                    "restoring nfc reader state to : " + attributeInt2);
                            if (attributeInt2 == 1) {
                                this.mNfcAdapter.enableReaderOption(true);
                            } else if (attributeInt2 == 0) {
                                this.mNfcAdapter.enableReaderOption(false);
                            } else {
                                builder.mResultType = resultType;
                                builder.mErrorType = SceneResult.ErrorType.INVALID_DATA;
                            }
                        }
                        int intValue3 = Integer.valueOf(value).intValue();
                        SemLog.d("ConnectionsItem", "restoring nfc state to : " + intValue3);
                        if (intValue3 == 1) {
                            String str4 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                            if (!context.getPackageManager()
                                    .hasSystemFeature("com.samsung.android.nfc.gpfelica")) {
                                this.mNfcAdapter.disable();
                                break;
                            } else {
                                builder.mResultType = resultType;
                                builder.mErrorType = SceneResult.ErrorType.INVALID_DATA;
                                break;
                            }
                        } else if (!this.mNfcAdapter.isReaderOptionEnabled()) {
                            String str5 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                            String str6 = SystemProperties.get("ro.csc.country_code");
                            if (!"KOREA".equalsIgnoreCase(str6)
                                    && !"CHINA".equalsIgnoreCase(str6)) {
                                builder.mResultType = resultType;
                                builder.mErrorType = SceneResult.ErrorType.INVALID_DATA;
                                break;
                            }
                            this.mNfcAdapter.enableReaderOption(false);
                        }
                    }
                    break;
                case 17:
                    int intValue4 = Integer.valueOf(value).intValue();
                    boolean isDefault = scene.isDefault();
                    if (!context.getPackageManager().hasSystemFeature("android.hardware.uwb")) {
                        Settings.Global.putInt(contentResolver, "uwb_enabled", 0);
                        break;
                    } else {
                        UwbManager uwbManager =
                                (UwbManager) context.getSystemService(UwbManager.class);
                        if (!isDefault || intValue4 != 0) {
                            Settings.Global.putInt(contentResolver, "uwb_enabled", intValue4);
                            if (intValue4 != 1) {
                                z = false;
                            }
                            uwbManager.setUwbEnabled(z);
                            break;
                        } else {
                            Settings.Global.putInt(contentResolver, "uwb_enabled", 1);
                            uwbManager.setUwbEnabled(true);
                            break;
                        }
                    }
                case 18:
                    Settings.System.putInt(
                            contentResolver,
                            "nearby_scanning_enabled",
                            Integer.valueOf(value).intValue());
                    break;
                case 19:
                    Settings.Global.putString(contentResolver, "private_dns_mode", value);
                    break;
                case 20:
                    if (!TextUtils.isEmpty(value)) {
                        Settings.Global.putString(contentResolver, "private_dns_specifier", value);
                        break;
                    } else {
                        break;
                    }
                case 21:
                    String value2 = scene.getValue(null, true);
                    if (!TextUtils.isEmpty(value2)) {
                        if (!VpnProfileUtils.putVpnProfileData(value2)) {
                            builder.mResultType = resultType;
                            builder.mErrorType = SceneResult.ErrorType.UNKNOWN_ERROR;
                            break;
                        }
                    } else {
                        builder.mResultType = resultType;
                        builder.mErrorType = SceneResult.ErrorType.INVALID_DATA;
                        break;
                    }
                    break;
                case 22:
                    if (!TextUtils.isEmpty(value)) {
                        if (!SemBluetoothCastAdapter.isBluetoothCastSupported()) {
                            builder.mResultType = resultType;
                            builder.mErrorType = SceneResult.ErrorType.NOT_SUPPORTED;
                            break;
                        } else {
                            Settings.Secure.putInt(
                                    context.getContentResolver(),
                                    "bluetooth_cast_mode",
                                    Integer.valueOf(value).intValue());
                            Settings.Secure.putInt(
                                    contentResolver,
                                    "bluetooth_cast_range",
                                    scene.getAttributeInt(0, "castRange"));
                            Settings.Secure.putInt(
                                    contentResolver,
                                    "bluetooth_cast_permission",
                                    scene.getAttributeInt(0, "castPermission"));
                            Settings.Secure.putInt(
                                    contentResolver,
                                    "bluetooth_cast_timeout",
                                    scene.getAttributeInt(600000, "castTimeout"));
                            break;
                        }
                    } else {
                        break;
                    }
                case 23:
                    if (TextUtils.isEmpty(value)) {
                        break;
                    } else {
                        String str7 = SystemProperties.get("persist.bluetooth.enableinbandringing");
                        boolean z4 =
                                context.getResources()
                                        .getBoolean(R.bool.config_windowNoTitleDefault);
                        boolean isSupportSoftPhone = BluetoothIBRSettings.isSupportSoftPhone();
                        if (!ApnSettings.MVNO_NONE.equals(str7) && (z4 || isSupportSoftPhone)) {
                            SystemProperties.set("persist.bluetooth.disableinbandringing", value);
                            break;
                        }
                        builder.mResultType = resultType;
                        builder.mErrorType = SceneResult.ErrorType.NOT_SUPPORTED;
                    }
                case 24:
                    if (((Boolean)
                                            BluetoothProperties.isProfileBapBroadcastSourceEnabled()
                                                    .orElse(Boolean.FALSE))
                                    .booleanValue()
                            && !TextUtils.isEmpty(value)
                            && !"No".equals(value)) {
                        SharedPreferences.Editor edit =
                                context.getSharedPreferences(
                                                PreferenceManager.getDefaultSharedPreferencesName(
                                                        context),
                                                0)
                                        .edit();
                        Settings.Secure.putString(
                                contentResolver, "bluetooth_le_broadcast_name", value);
                        Settings.Secure.putString(
                                contentResolver,
                                "bluetooth_le_broadcast_code",
                                scene.getAttribute(
                                        KnoxContainerManager.CONTAINER_CREATION_STATUS_CODE));
                        Settings.Secure.putInt(
                                contentResolver,
                                "bluetooth_le_encrypted_broadcast",
                                scene.getAttributeInt(1, "useCode"));
                        edit.putBoolean(
                                "CompatibilitySwitchState",
                                scene.getAttributeBoolean("compatibility"));
                        edit.commit();
                        break;
                    }
                    break;
            }
        } catch (Exception e) {
            SemLog.e("ConnectionsItem", e.getStackTrace()[0].toString());
        }
        return builder.build();
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final void open(Context context, String str) {}
}
