package com.samsung.android.settings.wifi.mobileap.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemProperties;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.wifi.SemWifiApCust;
import com.samsung.android.wifitrackerlib.SemWifiUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class WifiApFeatureUtils {
    public static boolean is5GhzBandSupported(Context context) {
        return WifiApFrameworkUtils.getSemWifiManager(context).supportWifiAp5GBasedOnCountry();
    }

    public static boolean isMobileDataUsageSupported(Context context) {
        if (SemWifiApCust.DBG) {
            int i = Settings.Secure.getInt(context.getContentResolver(), "vendor.wifiap.newUX", 0);
            if (i == 1) {
                Log.d("WifiApFeatureUtils", "wifiap newUX force enable ");
                return true;
            }
            if (i == 2) {
                Log.d("WifiApFeatureUtils", "wifiap newUX force disable ");
                return false;
            }
        }
        if (!((TelephonyManager) context.getSystemService(TelephonyManager.class))
                .isDataCapable()) {
            return false;
        }
        String str = SystemProperties.get("ro.kernel.version");
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        float parseFloat = Float.parseFloat(str);
        if (parseFloat < 5.0d) {
            Log.d(
                    "WifiApFeatureUtils",
                    "semWifiApDataUsageSupported kernelVersionFloat:" + parseFloat);
            if (SystemProperties.getInt("vendor.wifiap.kernel.datausage", -1) != 1) {
                return false;
            }
        }
        if (SystemProperties.getInt("ro.build.version.oneui", 0) >= 50101) {
            return true;
        }
        return "SWA".equalsIgnoreCase(SemCscParser.getRegion());
    }

    public static boolean isOneTimePasswordSupported(Context context) {
        if (SemWifiApCust.DBG) {
            int i = Settings.Secure.getInt(context.getContentResolver(), "vendor.wifiap.newUX", 0);
            if (i == 1) {
                Log.d("WifiApFeatureUtils", "wifiap newUX force enable ");
                return true;
            }
            if (i == 2) {
                Log.d("WifiApFeatureUtils", "wifiap newUX force disable ");
                return false;
            }
        }
        if (SystemProperties.getInt("ro.build.version.oneui", 0) >= 50101) {
            return true;
        }
        return "SWA".equalsIgnoreCase(SemCscParser.getRegion());
    }

    public static boolean isOneUIVersion6_0_AtMost() {
        return SystemProperties.getInt("ro.build.version.oneui", 0) < 60100;
    }

    public static boolean isSAFamilySupportedBasedOnCountry(Context context) {
        boolean isSAFamilySupportedBasedOnCountry =
                WifiApFrameworkUtils.getSemWifiManager(context).isSAFamilySupportedBasedOnCountry();
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "isSAFamilySupportedBasedOnCountry:",
                "WifiApFeatureUtils",
                isSAFamilySupportedBasedOnCountry);
        return isSAFamilySupportedBasedOnCountry;
    }

    public static boolean isSAFamilySupportedInSAFamilyDB(Context context) {
        boolean z = false;
        try {
            Bundle call =
                    context.getContentResolver()
                            .call(
                                    Uri.parse(
                                            "content://com.samsung.android.samsungaccount.accountmanagerprovider"),
                                    "getFamilyServiceInfo",
                                    context.getString(R.string.security_dashboard_sa_client_id),
                                    (Bundle) null);
            if (call != null) {
                if (call.getInt("result_code", 1) != 0) {
                    AbsAdapter$$ExternalSyntheticOutline0.m(
                            "isSAFamilySupportedInSAFamilyDB:",
                            call.getString("result_message", ApnSettings.MVNO_NONE),
                            "WifiApFeatureUtils");
                } else if (call.getBundle("result_bundle")
                        .getBoolean("isSupportFamilyService", false)) {
                    z = true;
                } else {
                    Log.i(
                            "WifiApFeatureUtils",
                            "isSAFamilySupportedInSAFamilyDB: isSupportFamilyService is FALSE");
                }
            }
            AbsAdapter$$ExternalSyntheticOutline0.m(
                    "isSAFamilySupportedInSAFamilyDB:", "WifiApFeatureUtils", z);
        } catch (Exception unused) {
        }
        return z;
    }

    public static void isSamsungAccountFamilySupported() {
        String region = SemCscParser.getRegion();
        if (!"NA".equalsIgnoreCase(region) && !"KOR".equalsIgnoreCase(region)) {
            "EUR".equalsIgnoreCase(region);
        }
        SystemProperties.getInt("ro.build.version.oneui", 0);
    }

    public static boolean isSatelliteModeOn(Context context) {
        boolean isSatelliteModeOn = SemWifiUtils.isSatelliteModeOn(context);
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "isSatelliteModeOn:", "WifiApFeatureUtils", isSatelliteModeOn);
        return isSatelliteModeOn;
    }

    public static boolean isWifiApClientDeviceTypeSupported() {
        return SystemProperties.getInt("ro.build.version.oneui", 0) >= 60100;
    }

    public static boolean isWifiApLockNetworkSupported() {
        return SystemProperties.getInt("ro.build.version.oneui", 0) >= 60100;
    }
}
