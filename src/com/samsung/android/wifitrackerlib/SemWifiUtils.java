package com.samsung.android.wifitrackerlib;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.os.SystemProperties;
import android.os.UserManager;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class SemWifiUtils {
    public static SubscriptionManager mSubscriptionManager;
    public static TelephonyManager mTelephonyManager;
    public static final Pattern HOSTNAME_PATTERN =
            Pattern.compile(
                    "^$|^[a-zA-Z0-9]+(\\-[a-zA-Z0-9]+)*(\\.[a-zA-Z0-9]+(\\-[a-zA-Z0-9]+)*)*$");
    public static final Pattern EXCLLIST_PATTERN =
            Pattern.compile(
                    "^$|^[a-zA-Z0-9*]+(\\-[a-zA-Z0-9*]+)*(\\.[a-zA-Z0-9*]+(\\-[a-zA-Z0-9*]+)*)*(,[a-zA-Z0-9*]+(\\-[a-zA-Z0-9*]+)*(\\.[a-zA-Z0-9*]+(\\-[a-zA-Z0-9*]+)*)*)*$");

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Mutable {
        public Object value;

        public Mutable(Object obj) {
            this.value = obj;
        }
    }

    public static int calculateSignalLevel(int i) {
        if (i <= -89) {
            return 0;
        }
        if (i > -89 && i <= -83) {
            return 1;
        }
        if (i <= -83 || i > -75) {
            return (i <= -75 || i > -64) ? 4 : 3;
        }
        return 2;
    }

    public static int getDeveloperSortingStyle(Context context) {
        return Settings.Global.getInt(
                context.getContentResolver(), "sec_wifi_developer_sorting_style", 3);
    }

    public static InetAddress getNetworkPart(InetAddress inetAddress, int i) {
        byte[] address = inetAddress.getAddress();
        if (i < 0 || i > address.length * 8) {
            throw new RuntimeException(
                    "IP address with " + address.length + " bytes has invalid prefix length " + i);
        }
        int i2 = i / 8;
        byte b = (byte) (255 << (8 - (i % 8)));
        if (i2 < address.length) {
            address[i2] = (byte) (b & address[i2]);
        }
        while (true) {
            i2++;
            if (i2 >= address.length) {
                try {
                    return InetAddress.getByAddress(address);
                } catch (UnknownHostException e) {
                    throw new RuntimeException("getNetworkPart error - " + e.toString());
                }
            }
            address[i2] = 0;
        }
    }

    public static Account getSamsungAccount(Context context) {
        Account[] accountsByType =
                AccountManager.get(context).getAccountsByType("com.osp.app.signin");
        if (accountsByType.length > 0) {
            return accountsByType[0];
        }
        return null;
    }

    public static boolean hasPackage(Context context) {
        if (context == null) {
            return false;
        }
        try {
            context.getPackageManager().getApplicationInfo("com.samsung.android.scloud", 128);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.d("SemWifiUtils", "Package not found : com.samsung.android.scloud");
            return false;
        }
    }

    public static boolean isAutoWifiEnabled(Context context) {
        return Settings.Global.getInt(
                        context.getContentResolver(),
                        "sem_auto_wifi_control_enabled",
                        ((SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE))
                                        .getAutoWifiDefaultValue()
                                ? 1
                                : 0)
                == 1;
    }

    public static boolean isEapNetworkWithCaCertError(WifiEntry wifiEntry) {
        return (wifiEntry.getSecurity$1() == 3
                        || wifiEntry.getSecurity$1() == 6
                        || wifiEntry.getSecurity$1() == 7)
                && wifiEntry.mSemFlags.getSemDisableReason() == 11;
    }

    public static boolean isEnabledUltraPowerSaving(Context context) {
        if (SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_SAFETYCARE")
                || SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_ULTRA_POWER_SAVING")
                || SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_BATTERY_CONVERSING")) {
            return SemEmergencyManager.isEmergencyMode(context);
        }
        return false;
    }

    public static boolean isSatelliteModeOn(Context context) {
        String string =
                Settings.Global.getString(context.getContentResolver(), "satellite_mode_radios");
        return string != null
                && string.contains(ImsProfile.PDN_WIFI)
                && Settings.Global.getInt(context.getContentResolver(), "satellite_mode_enabled", 0)
                        == 1;
    }

    public static boolean isSecured(int i) {
        return (i == 0 || i == 4) ? false : true;
    }

    public static boolean isSimCardReady(TelephonyManager telephonyManager) {
        if (telephonyManager == null) {
            return false;
        }
        if (telephonyManager.getPhoneCount() <= 1) {
            return telephonyManager.getSimState() == 5;
        }
        String[] split = SystemProperties.get("gsm.sim.state", "-1,-1").split(",");
        if (!"READY".equals(split[0]) && !"LOADED".equals(split[0])) {
            if (split.length <= 1) {
                return false;
            }
            if (!"READY".equals(split[1]) && !"LOADED".equals(split[1])) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSimCheck(Context context) {
        if (mSubscriptionManager == null) {
            mSubscriptionManager =
                    (SubscriptionManager)
                            context.getSystemService("telephony_subscription_service");
        }
        SubscriptionManager subscriptionManager = mSubscriptionManager;
        if (subscriptionManager == null) {
            Log.e("SemWifiUtils", "isSimCheck: subscriptionManager is null");
            return false;
        }
        List<SubscriptionInfo> activeSubscriptionInfoList =
                subscriptionManager.getActiveSubscriptionInfoList();
        if (activeSubscriptionInfoList == null) {
            Log.e("SemWifiUtils", "isSimCheck: subInfoList is null");
            return false;
        }
        if (mTelephonyManager == null) {
            mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
        }
        TelephonyManager telephonyManager = mTelephonyManager;
        if (telephonyManager == null) {
            Log.e("SemWifiUtils", "isSimCheck: TelephonyManager is null");
            return false;
        }
        Iterator<SubscriptionInfo> it = activeSubscriptionInfoList.iterator();
        while (it.hasNext()) {
            int simState = telephonyManager.getSimState(it.next().getSimSlotIndex());
            if (simState != 1 && simState != 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSupportRandomMac(WifiEntry wifiEntry) {
        String ssid = wifiEntry.getSsid();
        if (!wifiEntry.mSemFlags.isCarrierNetwork || !wifiEntry.isSuggestion()) {
            return TextUtils.isEmpty(ssid)
                    || !(ssid.contains("KT WiFi ")
                            || ssid.contains("KT GiGA WiFi ")
                            || ssid.contains("T wifi zone"));
        }
        if ("0000docomo".equals(ssid) || "0001docomo".equals(ssid)) {
            return true;
        }
        return "XFINITY".equals(ssid) || "xFi".equals(ssid) || "xfinitywifi".equals(ssid);
    }

    public static boolean isSupportRandomMacForLgu(WifiEntry wifiEntry) {
        String ssid = wifiEntry.getSsid();
        if (TextUtils.isEmpty(ssid) || !ssid.contains("U+Net")) {
            return true;
        }
        return (wifiEntry.getSecurity$1() == 0
                        || wifiEntry.getSecurity$1() == 2
                        || wifiEntry.getSecurity$1() == 4
                        || wifiEntry.getSecurity$1() == 5)
                ? false
                : true;
    }

    public static boolean isSupportedWifi7(Context context) {
        String countryCode =
                ((WifiManager) context.getSystemService(ImsProfile.PDN_WIFI)).getCountryCode();
        if (TextUtils.isEmpty(countryCode)) {
            return false;
        }
        String wifi7DisabledCountry =
                ((SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE))
                        .getWifi7DisabledCountry();
        return TextUtils.isEmpty(wifi7DisabledCountry)
                || !wifi7DisabledCountry.contains(countryCode);
    }

    public static int isSwitchToBetterWifiEnabled(Context context) {
        return Settings.Global.getInt(
                                context.getContentResolver(),
                                "sem_wifi_switch_to_better_wifi_supported",
                                1)
                        == 1
                ? 2
                : 0;
    }

    public static boolean isValidOpenRoamingToken(Context context) {
        Iterator<PasspointConfiguration> it =
                ((WifiManager) context.getSystemService(ImsProfile.PDN_WIFI))
                        .getPasspointConfigurations()
                        .iterator();
        while (true) {
            if (!it.hasNext()) {
                return true;
            }
            PasspointConfiguration next = it.next();
            if (next.getCredential() != null && next.getCredential().getUserCredential() != null) {
                String username = next.getCredential().getUserCredential().getUsername();
                String string =
                        Settings.Global.getString(
                                context.getContentResolver(), "sem_wifi_allowed_oauth_provider");
                if ((!TextUtils.isEmpty(string) && string.contains("[cisco]"))
                        && "user.openroaming.network".equals(username)) {
                    return false;
                }
            }
        }
    }

    public static boolean isWifiSyncEnabled(Context context) {
        UserManager userManager = (UserManager) context.getSystemService("user");
        return (isEnabledUltraPowerSaving(context)
                        || userManager.getUserInfo(userManager.getUserHandle()).isKnoxWorkspace()
                        || !hasPackage(context))
                ? false
                : true;
    }

    public static String readSalesCode() {
        try {
            String str = SystemProperties.get("persist.omc.sales_code");
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
            String str2 = SystemProperties.get("ro.csc.sales_code");
            return TextUtils.isEmpty(str2) ? SystemProperties.get("ril.sales_code") : str2;
        } catch (Exception unused) {
            return ApnSettings.MVNO_NONE;
        }
    }

    public static String removeDoubleQuotes(String str) {
        if (TextUtils.isEmpty(str)) {
            return ApnSettings.MVNO_NONE;
        }
        int length = str.length();
        if (length <= 1 || str.charAt(0) != '\"') {
            return str;
        }
        int i = length - 1;
        return str.charAt(i) == '\"' ? str.substring(1, i) : str;
    }

    public static void resetAiConfiguration(Context context) {
        Settings.Global.putInt(
                context.getContentResolver(),
                "wifi_handover_ai_mode",
                Integer.parseInt("2") > 0 ? 1 : -1);
        Settings.Global.putInt(
                context.getContentResolver(),
                "wifi_switch_to_mobile_data_ai_mode",
                Integer.parseInt("1") > 0 ? 1 : -1);
    }

    public static void sendAccessibilityEvent(Context context, String str) {
        AccessibilityManager accessibilityManager =
                (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        if (accessibilityManager == null || !accessibilityManager.isEnabled()) {
            return;
        }
        AccessibilityEvent obtain = AccessibilityEvent.obtain();
        obtain.setEventType(NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT);
        obtain.setClassName(context.getClass().getName());
        obtain.setPackageName(context.getPackageName());
        obtain.getText().add(str);
        accessibilityManager.sendAccessibilityEvent(obtain);
    }
}
