package com.samsung.android.settings.wifi.mobileap.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;

import com.android.settings.Utils;

import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class WifiApFrameworkUtils {
    public static boolean canAutoHotspotBeEnabled(Context context) {
        boolean isTetheringRestricted = WifiApSettingsUtils.isTetheringRestricted(context);
        boolean isAirplaneModeOn = WifiApSettingsUtils.isAirplaneModeOn(context);
        boolean z = true;
        boolean z2 = !WifiApSettingsUtils.isSimEnabled(context);
        boolean z3 = !WifiApSettingsUtils.isActiveNetworkHasInternet(context);
        boolean isSamsungAccountLoggedOut = WifiApSettingsUtils.isSamsungAccountLoggedOut(context);
        boolean z4 = !WifiApSettingsUtils.isNearByDeviceScanningEnabled(context);
        boolean isDefaultPassphraseSet = WifiApSoftApUtils.isDefaultPassphraseSet(context);
        if (isTetheringRestricted) {
            Log.d(
                    "WifiApFrameworkUtils",
                    "AutoHotspot feature can`t be made ON: RESULT_ERROR_TETHERING_RESTRICTED");
            z = false;
        }
        if (isAirplaneModeOn) {
            Log.d(
                    "WifiApFrameworkUtils",
                    "AutoHotspot feature can`t be made ON: RESULT_ERROR_AIRPLANE_MODE_ON");
            z = false;
        }
        if (z2 && WifiApFeatureUtils.isOneUIVersion6_0_AtMost()) {
            Log.d(
                    "WifiApFrameworkUtils",
                    "AutoHotspot feature can`t be made ON: RESULT_ERROR_NO_SIM");
            z = false;
        }
        if (z3) {
            Log.d(
                    "WifiApFrameworkUtils",
                    "AutoHotspot feature can`t be made ON: RESULT_ERROR_NO_ACTIVE_NETWORK");
            z = false;
        }
        if (isSamsungAccountLoggedOut) {
            Log.d(
                    "WifiApFrameworkUtils",
                    "AutoHotspot feature can`t be made ON: RESULT_ERROR_NO_ACCOUNT");
            z = false;
        }
        if (z4) {
            Log.d(
                    "WifiApFrameworkUtils",
                    "AutoHotspot feature can`t be made ON: RESULT_ERROR_DISABLED_NEARBY_SCANNING");
            z = false;
        }
        if ((!WifiApSettingsUtils.isCarrierTMO() && !WifiApSettingsUtils.isCarrierNEWCO())
                || !isDefaultPassphraseSet) {
            return z;
        }
        Log.d(
                "WifiApFrameworkUtils",
                "AutoHotspot feature can`t be made ON: RESULT_ERROR_DEFAULT_PASSWORD");
        return false;
    }

    public static boolean canFamilySharingBeEnabled(Context context) {
        Log.i("WifiApFrameworkUtils", "Checking setCheck conditions of family sharing");
        boolean z = true;
        boolean z2 = !WifiApSettingsUtils.isSimEnabled(context);
        boolean z3 = !WifiApSettingsUtils.isActiveNetworkHasInternet(context);
        WifiApSettingsUtils.isSamsungAccountLoggedOut(context);
        boolean isGroupSharingAppDisabled =
                WifiApSmartTetheringApkUtils.isGroupSharingAppDisabled(context);
        boolean z4 = !WifiApSmartTetheringApkUtils.isFamilySharingServiceRegisteredOn(context);
        boolean isEmpty = WifiApSmartTetheringApkUtils.getFamilyGroupId(context).isEmpty();
        boolean z5 = !isAutoHotspotSetOn(context);
        if (isGroupSharingAppDisabled) {
            Log.i("WifiApFrameworkUtils", "Group sharing App check condition failed.");
            z = false;
        }
        if (z2) {
            Log.i("WifiApFrameworkUtils", "Sim check condition failed.");
            z = false;
        }
        if (z5) {
            Log.i("WifiApFrameworkUtils", "Auto Hotspot On check condition failed.");
            z = false;
        }
        if (z3) {
            Log.i("WifiApFrameworkUtils", "Network check condition failed.");
            z = false;
        }
        if (z4) {
            Log.i(
                    "WifiApFrameworkUtils",
                    "Family service registered check condition failed. Trying to register");
            if (WifiApSettingsUtils.isSamsungAccountLoggedOut(context)) {
                Log.d(
                        "WifiApSmartTetheringApkUtils",
                        "For launchFamilyServiceRegisterActivity() Error. No SA Account.");
            } else {
                Log.d("WifiApSmartTetheringApkUtils", "launchFamilyServiceRegisterActivity()");
                Intent intent = new Intent();
                intent.setAction("com.sec.mhs.smarttethering.WifiApGroupSemsActivityLauncher");
                intent.putExtra("launcher", 100);
                intent.setFlags(268435456);
                context.startActivity(intent);
            }
            Log.i("WifiApFrameworkUtils", "Trying to register Family service");
            z = false;
        }
        if (!isEmpty) {
            return z;
        }
        Log.i("WifiApFrameworkUtils", "Family group Id check failed.");
        return false;
    }

    public static ConnectivityManager getConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService("connectivity");
    }

    public static SemWifiManager getSemWifiManager(Context context) {
        return (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
    }

    public static int getWifiApMaxClientDefault() {
        int i = Utils.MAX_CLIENT_4_MOBILEAP;
        if ("TMO".equalsIgnoreCase(Utils.CONFIGOPBRANDINGFORMOBILEAP)
                || "NEWCO".equalsIgnoreCase(Utils.CONFIGOPBRANDINGFORMOBILEAP)) {
            i = Utils.MAX_CLIENT_4_MOBILEAP;
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "maxClientConnectionDefault : ", "WifiApFrameworkUtils");
        return i;
    }

    public static int getWifiApMaxClientFromFramework(Context context) {
        int wifiApMaxClientFromFramework =
                getSemWifiManager(context).getWifiApMaxClientFromFramework();
        if ("VZW".equalsIgnoreCase(Utils.CONFIGOPBRANDINGFORMOBILEAP)
                && ((TelephonyManager) context.getSystemService("phone")).getDataNetworkType()
                        != 13) {
            wifiApMaxClientFromFramework = 5;
        } else if ("TMO".equalsIgnoreCase(Utils.CONFIGOPBRANDINGFORMOBILEAP)
                || "NEWCO".equalsIgnoreCase(Utils.CONFIGOPBRANDINGFORMOBILEAP)) {
            wifiApMaxClientFromFramework =
                    ((SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE))
                            .getWifiApMaxClientFromFramework();
        }
        int i = Utils.MAX_CLIENT_4_MOBILEAP;
        if (i < wifiApMaxClientFromFramework) {
            wifiApMaxClientFromFramework = i;
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                wifiApMaxClientFromFramework,
                "maxClientConnectionFromFramework : ",
                "WifiApFrameworkUtils");
        return wifiApMaxClientFromFramework;
    }

    public static boolean isActiveNetworkisCellular(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService("connectivity");
        Network activeNetwork = connectivityManager.getActiveNetwork();
        if (activeNetwork == null) {
            Log.i("WifiApFrameworkUtils", "ActiveNetwork is Null");
            return false;
        }
        NetworkCapabilities networkCapabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork);
        if (networkCapabilities != null) {
            return networkCapabilities.hasTransport(0) && networkCapabilities.hasCapability(12);
        }
        Log.i("WifiApFrameworkUtils", "networkCapabilities is Null");
        return false;
    }

    public static boolean isAutoHotspotSetOn(Context context) {
        boolean z =
                Settings.Secure.getInt(
                                context.getContentResolver(), "wifi_ap_smart_tethering_settings", 0)
                        == 1;
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "Getting(DB) AutoHotspot: ", "WifiApFrameworkUtils", z);
        return z;
    }

    public static boolean isBandSeekBarUxSupported(Context context) {
        int i = WifiApFeatureUtils.is5GhzBandSupported(context) ? 2 : 1;
        if (getSemWifiManager(context).supportWifiAp5GBasedOnCountry()) {
            i++;
        }
        if (getSemWifiManager(context).supportWifiAp6GBasedOnCountry()) {
            i++;
        }
        return i > 2;
    }

    public static boolean isFamilySharingSetOn(Context context) {
        boolean z =
                Settings.Secure.getInt(
                                context.getContentResolver(),
                                "wifi_ap_smart_tethering_settings_with_family",
                                0)
                        == 1;
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "Getting(DB) FamilySharing: ", "WifiApFrameworkUtils", z);
        return z;
    }

    public static boolean isHotspotNetworkLocked(SemWifiManager semWifiManager) {
        boolean z = false;
        if (WifiApFeatureUtils.isWifiApLockNetworkSupported()
                && semWifiManager.getSmartMHSLockStatus() == 1) {
            z = true;
        }
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "isHotspotNetworkLocked : ", "WifiApFrameworkUtils", z);
        return z;
    }

    public static boolean isOtpConnectedClient(Context context, String str) {
        boolean isWifiApGuestClient = getSemWifiManager(context).isWifiApGuestClient(str);
        Log.i(
                "WifiApFrameworkUtils",
                "isOtpConnectedClient mac: "
                        + WifiApSettingsUtils.hideSecondHalfOfString(str)
                        + " status: "
                        + isWifiApGuestClient);
        return isWifiApGuestClient;
    }

    public static boolean isOtpPasswordEnabled(Context context) {
        boolean isWifiApGuestModeEnabled = getSemWifiManager(context).isWifiApGuestModeEnabled();
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "isOtpPasswordEnabled: ", "WifiApFrameworkUtils", isWifiApGuestModeEnabled);
        return isWifiApGuestModeEnabled;
    }

    public static boolean isPrioritizeRealTimeTrafficOn(Context context) {
        if (!WifiApFeatureUtils.isMobileDataUsageSupported(context)) {
            Log.e("WifiApFrameworkUtils", "Prioritize Real- Time Traffic Not Supported.");
            return false;
        }
        boolean z =
                Settings.Secure.getInt(
                                context.getContentResolver(), "wifi_ap_smart_priority_enable", 0)
                        == 1;
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "Getting(DB) Prioritize Real-Time Traffic: ", "WifiApFrameworkUtils", z);
        return z;
    }

    public static boolean isWifiApStateEnabled(Context context) {
        return getSemWifiManager(context).getWifiApState() == 13;
    }

    public static boolean isWifiApStateEnablingOrEnabled(Context context) {
        return getSemWifiManager(context).getWifiApState() == 12 || isWifiApStateEnabled(context);
    }

    public static void resetSoftAp(Context context) {
        Log.i("WifiApFrameworkUtils", "Resetting Hotspot");
        final SemWifiManager semWifiManager = getSemWifiManager(context);
        new Handler()
                .postDelayed(
                        new Runnable() { // from class:
                                         // com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                semWifiManager.resetSoftAp(new Message());
                            }
                        },
                        10L);
    }

    public static void setAutoHotspotDB(Context context, boolean z) {
        int i;
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "Setting(DB) AutoHotspot: ", "WifiApFrameworkUtils", z);
        if (z) {
            WifiApSmartTetheringApkUtils.startSmartTetheringApk(context, 3, null);
            i = 1;
            WifiApSmartTetheringApkUtils.startSmartTetheringApk(context, 1, null);
        } else {
            i = 0;
        }
        Settings.Secure.putInt(context.getContentResolver(), "wifi_ap_smart_tethering_settings", i);
        Log.i("WifiApFrameworkUtils", "settings Ble For MHS: " + z);
        getSemWifiManager(context).wifiApBleMhsRole(z);
    }

    public static void setFamilySharingDB(Context context, boolean z) {
        int i =
                Settings.Secure.getInt(
                        context.getContentResolver(),
                        "wifi_ap_smart_tethering_settings_with_family",
                        0);
        Log.i("WifiApFrameworkUtils", "Setting(DB) FamilySharing: " + z + ", existing_value: " + i);
        if (z == i) {
            return;
        }
        Settings.Secure.putInt(
                context.getContentResolver(),
                "wifi_ap_smart_tethering_settings_with_family",
                z ? 1 : 0);
    }

    public static void setIsHotspotScreenOpened(Context context, boolean z) {
        Settings.Secure.putInt(
                context.getContentResolver(), "wifi_ap_inside_hotspot_screen", z ? 1 : 0);
    }

    public static void setOtpPasswordEnabled(Context context, boolean z) {
        Log.i("WifiApFrameworkUtils", "setOtpPasswordEnabled: isEnabled: " + z);
        getSemWifiManager(context).setWifiApGuestModeEnabled(z);
    }

    public static void setPrioritizeRealTimeTrafficDB(Context context, boolean z) {
        Log.i("WifiApFrameworkUtils", "Setting(DB) Prioritize Real-Time Traffic : " + z);
        if (WifiApFeatureUtils.isMobileDataUsageSupported(context)) {
            Settings.Secure.putInt(
                    context.getContentResolver(), "wifi_ap_smart_priority_enable", z ? 1 : 0);
        } else {
            Log.e("WifiApFrameworkUtils", "Prioritize Real- Time Traffic Not Supported");
        }
    }
}
