package com.samsung.android.settings.wifi.advanced;

import android.content.Context;
import android.os.SystemProperties;
import android.os.UserHandle;

import com.android.settings.Utils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.sec.ims.extensions.WiFiManagerExt;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiFeatureUtils {
    public final HashMap mFeaturesMap;

    public WifiFeatureUtils(Context context) {
        HashMap hashMap = new HashMap();
        this.mFeaturesMap = hashMap;
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
                Boolean.valueOf(UserHandle.myUserId() == 0 && Utils.SPF_SupportMobileApEnhanced));
        hashMap.put(
                "switch_to_better_wifi_with_screen_off_only",
                Boolean.valueOf(SemWifiUtils.isSwitchToBetterWifiEnabled(context) == 1));
        hashMap.put(
                "switch_to_better_wifi",
                Boolean.valueOf(SemWifiUtils.isSwitchToBetterWifiEnabled(context) == 2));
    }

    public final String getKeyIfUnsupported(String str) {
        return this.mFeaturesMap.get(str) != null
                ? ((Boolean) this.mFeaturesMap.get(str)).booleanValue()
                : false ? ApnSettings.MVNO_NONE : str;
    }
}
