package com.samsung.android.settings.connection;

import android.R;
import android.content.Context;
import android.net.TetheringManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.SystemProperties;
import android.provider.Settings;
import android.telephony.ServiceState;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.Utils;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.sec.ims.configuration.DATA;
import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class ConnectionsUtils {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        Uri.parse("content://com.sec.android.desktopmode.uiservice.SettingsProvider/");
    }

    public static String getSimOperator(Context context, int i) {
        String str;
        int[] subId = SubscriptionManager.getSubId(i);
        if (subId != null) {
            str = TelephonyManager.getDefault().getSimOperator(subId[0]);
        } else {
            Log.d("ConnectionsUtils", "SubscriptionManager.getSubId(slot)[0] is NULL");
            str = ApnSettings.MVNO_NONE;
        }
        if (!Rune.isChinaCTCModel()) {
            if ((SubscriptionManager.getSubId(i) == null
                            ? false
                            : TelephonyManager.getDefault()
                                    .isNetworkRoaming(SubscriptionManager.getSubId(i)[0]))
                    && "20404".equals(str)) {
                SubscriptionInfo activeSubscriptionInfoForSimSlotIndex =
                        SubscriptionManager.from(context)
                                .getActiveSubscriptionInfoForSimSlotIndex(i);
                String str2 = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
                String iccId =
                        activeSubscriptionInfoForSimSlotIndex != null
                                ? activeSubscriptionInfoForSimSlotIndex.getIccId()
                                : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
                if (iccId != null && iccId.length() > 6) {
                    try {
                        str2 = iccId.substring(0, 6);
                    } catch (IndexOutOfBoundsException e) {
                        Log.e("ConnectionsUtils", "IndexOutOfBoundsException getIccIdBySlot" + e);
                    }
                }
                if ("898603".equals(str2)
                        || "898605".equals(str2)
                        || "898611".equals(str2)
                        || "898530".equals(str2)) {
                    str = "46003";
                }
            }
        }
        AbsAdapter$$ExternalSyntheticOutline0.m("MCCMNC getIccIdBySlot:", str, "ConnectionsUtils");
        return str;
    }

    public static String getTelephonyProperty(int i, String str, String str2) {
        String str3 = SystemProperties.get("ril.ICC_TYPE0");
        String str4 = SystemProperties.get("ril.ICC_TYPE1");
        return str.equals("ril.ICC_TYPE0")
                ? (str3 == null || str3.length() <= 0) ? str2 : str3
                : str.equals("ril.ICC_TYPE1")
                        ? (str4 == null || str4.length() <= 0) ? str2 : str4
                        : TelephonyManager.getTelephonyProperty(i, str, str2);
    }

    public static boolean isAirplaneModeEnabled(Context context) {
        boolean z =
                Settings.Global.getInt(context.getContentResolver(), "airplane_mode_on", 0) != 0;
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "isAirplaneModeEnabled return : ", "ConnectionsUtils", z);
        return z;
    }

    public static boolean isAisSIMValid(Context context) {
        String str;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        int simCount = telephonyManager.getSimCount();
        Log.d("ConnectionsUtils", "isAisSIMValid() working");
        boolean z = false;
        for (int i = 0; i < simCount; i++) {
            if (telephonyManager.getSimState(i) != 1) {
                Log.d("ConnectionsUtils", "isAisSIMValid() SIM_STATE is available");
                int[] subId = SubscriptionManager.getSubId(i);
                if (subId != null) {
                    str = TelephonyManager.getDefault().getSimOperator(subId[0]);
                } else {
                    Log.d("ConnectionsUtils", "SubscriptionManager.getSubId(slot)[0] is NULL");
                    str = ApnSettings.MVNO_NONE;
                }
                if (!TextUtils.isEmpty(str) && str.contains("52003")) {
                    Log.d("ConnectionsUtils", "isAisSIMValid() getSimOperator : ".concat(str));
                    z = true;
                }
            }
        }
        return z;
    }

    public static boolean isAveaSIMValid(Context context) {
        String subscriberId;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        int simCount = telephonyManager.getSimCount();
        boolean z = false;
        boolean z2 = false;
        for (int i = 0; i < simCount; i++) {
            if (telephonyManager.getSimState(i) != 1) {
                z2 = true;
            }
        }
        if (z2
                && (subscriberId = telephonyManager.getSubscriberId()) != null
                && subscriberId.contains("28603")) {
            z = true;
        }
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "isAveaSIMValid() return : ", "ConnectionsUtils", z);
        return z;
    }

    public static boolean isDuosModel() {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl != null) {
            return ((SecSimFeatureProviderImpl) featureFactoryImpl.getSecSimFeatureProvider())
                            .isMultiSimModel()
                    && Rune.isChinaCTCModel();
        }
        throw new UnsupportedOperationException("No feature factory configured");
    }

    public static boolean isFastWebSIMValid(Context context) {
        String str;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        int simCount = telephonyManager.getSimCount();
        Log.d("ConnectionsUtils", "isFastWebSIMValid() working");
        boolean z = false;
        for (int i = 0; i < simCount; i++) {
            if (telephonyManager.getSimState(i) != 1) {
                Log.d("ConnectionsUtils", "isFastWebSIMValid() SIM_STATE is available");
                int[] subId = SubscriptionManager.getSubId(i);
                if (subId != null) {
                    str = TelephonyManager.getDefault().getSimOperator(subId[0]);
                } else {
                    Log.d("ConnectionsUtils", "SubscriptionManager.getSubId(slot)[0] is NULL");
                    str = ApnSettings.MVNO_NONE;
                }
                if (!TextUtils.isEmpty(str) && (str.contains("22208") || str.contains("22801"))) {
                    Log.d("ConnectionsUtils", "isFastWebSIMValid() getSimOperator : ".concat(str));
                    z = true;
                }
            }
        }
        return z;
    }

    public static boolean isMetroPCS() {
        String str = SystemProperties.get("ro.product.name");
        if (str != null) {
            if (!str.contains("MetroPCS") && !str.endsWith("mtr")) {
                String str2 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                if ("TMK".equals(Utils.getSalesCode())) {}
            }
            return true;
        }
        return false;
    }

    public static boolean isMobileNetworkEnabled(Context context) {
        boolean z = Settings.Global.getInt(context.getContentResolver(), "mobile_data", 1) == 1;
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "isMobileNetworkEnabled() return : ", "ConnectionsUtils", z);
        return z;
    }

    public static boolean isNoSIM() {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        int enabledSimCnt =
                ((SecSimFeatureProviderImpl) featureFactoryImpl.getSecSimFeatureProvider())
                        .getEnabledSimCnt();
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                enabledSimCnt, "isNoSIM : ", "ConnectionsUtils");
        return enabledSimCnt == 0;
    }

    public static boolean isOverseaArea(Context context) {
        if (!Rune.isDomesticModel()) {
            return false;
        }
        return "oversea"
                .equals(
                        TelephonyManager.semGetTelephonyProperty(
                                SubscriptionManager.getPhoneId(
                                        SubscriptionManager.getDefaultDataSubscriptionId()),
                                "ril.currentplmn",
                                ApnSettings.MVNO_NONE));
    }

    public static boolean isRoaming(Context context) {
        if (context == null) {
            Log.d("ConnectionsUtils", "isNetworkRoaming(), null context");
            return false;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager == null) {
            Log.d("ConnectionsUtils", "isNetworkRoaming(), telephonymanager is null");
            return false;
        }
        boolean isNetworkRoaming =
                telephonyManager.isNetworkRoaming(
                        SubscriptionManager.getDefaultDataSubscriptionId());
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "isNetworkRoaming() return : ", "ConnectionsUtils", isNetworkRoaming);
        return isNetworkRoaming;
    }

    public static boolean isSatelliteNetworksOn(Context context) {
        ServiceState serviceState;
        return ((SemCarrierFeature.getInstance()
                                        .getBoolean(
                                                0,
                                                "CarrierFeature_Common_Support_Satellite",
                                                false,
                                                false)
                                || SemCarrierFeature.getInstance()
                                        .getBoolean(
                                                1,
                                                "CarrierFeature_Common_Support_Satellite",
                                                false,
                                                false))
                        && (serviceState =
                                        ((TelephonyManager) context.getSystemService("phone"))
                                                .getServiceState())
                                != null
                        && serviceState.isUsingNonTerrestrialNetwork())
                || (Settings.Global.getInt(
                                context.getContentResolver(), "satellite_mode_enabled", 0)
                        == 1);
    }

    public static boolean isSupport5GConcept() {
        try {
            int parseInt =
                    Integer.parseInt(
                            SystemProperties.get(
                                    "ro.telephony.default_network",
                                    DATA.DM_FIELD_INDEX.PCSCF_DOMAIN));
            r2 = parseInt >= 23;
            Log.i("ConnectionsUtils", "default network mode : " + parseInt);
        } catch (NumberFormatException unused) {
            Log.i("ConnectionsUtils", "NumberFormatException in isSupport5GConcept");
        }
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "isSupport5GConcept : ", "ConnectionsUtils", r2);
        return r2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0054, code lost:

       if (r0.contains("ALL") != false) goto L67;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isSupportEthernet() {
        /*
            java.lang.String r0 = "/sys/class/usb_notify/usb_control/support"
            java.lang.String r1 = "support USB host:"
            boolean r2 = com.android.settings.Utils.isTablet()
            r3 = 1
            java.lang.String r4 = "Ethernet"
            if (r2 == 0) goto L15
            java.lang.String r0 = "tablet support ethernet"
            android.util.Log.d(r4, r0)
            return r3
        L15:
            r2 = 0
            r5 = 0
            java.io.File r6 = new java.io.File     // Catch: java.lang.Throwable -> L28 java.lang.StringIndexOutOfBoundsException -> L2b java.io.IOException -> L2d java.io.FileNotFoundException -> L2f
            r6.<init>(r0)     // Catch: java.lang.Throwable -> L28 java.lang.StringIndexOutOfBoundsException -> L2b java.io.IOException -> L2d java.io.FileNotFoundException -> L2f
            boolean r6 = r6.exists()     // Catch: java.lang.Throwable -> L28 java.lang.StringIndexOutOfBoundsException -> L2b java.io.IOException -> L2d java.io.FileNotFoundException -> L2f
            if (r6 != 0) goto L31
            java.lang.String r0 = "1. file not exist, can not check Host support"
            android.util.Log.d(r4, r0)     // Catch: java.lang.Throwable -> L28 java.lang.StringIndexOutOfBoundsException -> L2b java.io.IOException -> L2d java.io.FileNotFoundException -> L2f
            return r5
        L28:
            r0 = move-exception
            goto L93
        L2b:
            r0 = move-exception
            goto L6e
        L2d:
            r0 = move-exception
            goto L7c
        L2f:
            r0 = move-exception
            goto L85
        L31:
            java.io.BufferedReader r6 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L28 java.lang.StringIndexOutOfBoundsException -> L2b java.io.IOException -> L2d java.io.FileNotFoundException -> L2f
            java.io.FileReader r7 = new java.io.FileReader     // Catch: java.lang.Throwable -> L28 java.lang.StringIndexOutOfBoundsException -> L2b java.io.IOException -> L2d java.io.FileNotFoundException -> L2f
            r7.<init>(r0)     // Catch: java.lang.Throwable -> L28 java.lang.StringIndexOutOfBoundsException -> L2b java.io.IOException -> L2d java.io.FileNotFoundException -> L2f
            r6.<init>(r7)     // Catch: java.lang.Throwable -> L28 java.lang.StringIndexOutOfBoundsException -> L2b java.io.IOException -> L2d java.io.FileNotFoundException -> L2f
            java.lang.String r0 = r6.readLine()     // Catch: java.lang.Throwable -> L57 java.lang.StringIndexOutOfBoundsException -> L5a java.io.IOException -> L5d java.io.FileNotFoundException -> L60
            if (r0 == 0) goto L63
            boolean r2 = android.text.TextUtils.isEmpty(r0)     // Catch: java.lang.Throwable -> L57 java.lang.StringIndexOutOfBoundsException -> L5a java.io.IOException -> L5d java.io.FileNotFoundException -> L60
            if (r2 != 0) goto L63
            java.lang.String r1 = r1.concat(r0)     // Catch: java.lang.Throwable -> L57 java.lang.StringIndexOutOfBoundsException -> L5a java.io.IOException -> L5d java.io.FileNotFoundException -> L60
            android.util.Log.d(r4, r1)     // Catch: java.lang.Throwable -> L57 java.lang.StringIndexOutOfBoundsException -> L5a java.io.IOException -> L5d java.io.FileNotFoundException -> L60
            java.lang.String r1 = "ALL"
            boolean r0 = r0.contains(r1)     // Catch: java.lang.Throwable -> L57 java.lang.StringIndexOutOfBoundsException -> L5a java.io.IOException -> L5d java.io.FileNotFoundException -> L60
            if (r0 == 0) goto L63
            goto L64
        L57:
            r0 = move-exception
            r2 = r6
            goto L93
        L5a:
            r0 = move-exception
            r2 = r6
            goto L6e
        L5d:
            r0 = move-exception
            r2 = r6
            goto L7c
        L60:
            r0 = move-exception
            r2 = r6
            goto L85
        L63:
            r3 = r5
        L64:
            r6.close()     // Catch: java.io.IOException -> L68
            goto L6c
        L68:
            r0 = move-exception
            r0.printStackTrace()
        L6c:
            r5 = r3
            goto L8d
        L6e:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L28
            if (r2 == 0) goto L8d
            r2.close()     // Catch: java.io.IOException -> L77
            goto L8d
        L77:
            r0 = move-exception
            r0.printStackTrace()
            goto L8d
        L7c:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L28
            if (r2 == 0) goto L8d
            r2.close()     // Catch: java.io.IOException -> L77
            goto L8d
        L85:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L28
            if (r2 == 0) goto L8d
            r2.close()     // Catch: java.io.IOException -> L77
        L8d:
            java.lang.String r0 = "isSupportEthernet return : "
            com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0.m(r0, r4, r5)
            return r5
        L93:
            if (r2 == 0) goto L9d
            r2.close()     // Catch: java.io.IOException -> L99
            goto L9d
        L99:
            r1 = move-exception
            r1.printStackTrace()
        L9d:
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.connection.ConnectionsUtils.isSupportEthernet():boolean");
    }

    public static boolean isSupportMptcp() {
        boolean equals = "1".equals(SystemProperties.get("ro.supportmodel.mptcp"));
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "isSupportMptcp: ", "mptcp_settings", equals);
        return equals;
    }

    public static boolean isSupportPco() {
        return SemCscFeature.getInstance()
                .getString("CscFeature_Common_ConfigPco")
                .contains("COMMON");
    }

    public static boolean isTetheringOn(Context context) {
        TetheringManager tetheringManager =
                (TetheringManager) context.getSystemService(TetheringManager.class);
        String[] tetheredIfaces = tetheringManager.getTetheredIfaces();
        String[] tetherableUsbRegexs = tetheringManager.getTetherableUsbRegexs();
        String string = context.getResources().getString(R.string.duration_days_relative);
        boolean z = false;
        boolean z2 = false;
        for (String str : tetheredIfaces) {
            for (String str2 : tetherableUsbRegexs) {
                if (str.matches(str2)) {
                    z = true;
                }
            }
            if (str.matches(string)) {
                z2 = true;
            }
        }
        WifiManager wifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
        StringBuilder m =
                RowView$$ExternalSyntheticOutline0.m(
                        "isTetheringEnabled usbTethered:", "/isWifiApEnabled:", z);
        m.append(wifiManager.isWifiApEnabled());
        m.append(" Ethernet tethered: ");
        m.append(z2);
        Log.i("ConnectionsUtils", m.toString());
        return z || z2 || wifiManager.isWifiApEnabled();
    }

    public static boolean isVodafoneSIMValid(Context context) {
        String subscriberId;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        int simCount = telephonyManager.getSimCount();
        boolean z = false;
        boolean z2 = false;
        for (int i = 0; i < simCount; i++) {
            if (telephonyManager.getSimState(i) != 1) {
                z2 = true;
            }
        }
        if (z2
                && (subscriberId = telephonyManager.getSubscriberId()) != null
                && subscriberId.contains("28602")) {
            z = true;
        }
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "isVodafoneSIMValid() return : ", "ConnectionsUtils", z);
        return z;
    }

    public static boolean isWifiEnabled(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
        if (wifiManager != null) {
            return wifiManager.isWifiEnabled();
        }
        Log.d("SmartBonding", "Can't get WifiManager.");
        return false;
    }
}
