package com.samsung.android.settings.wifi.mobileap.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.wifi.aware.WifiAwareManager;
import android.os.SystemProperties;
import android.os.UserManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.Utils$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.connection.ConnectionsUtils;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApDataUsageConfig;
import com.samsung.android.wifi.aware.SemWifiAwareManager;
import com.sec.ims.configuration.DATA;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class WifiApSettingsUtils {
    public static final boolean DBG = Utils.MHSDBG;

    public static String convertDataSizeToLocaleString(Context context, double d) {
        WifiApDataUsageConfig wifiApDataUsageConfig = new WifiApDataUsageConfig();
        wifiApDataUsageConfig.mStartDateAndTime = System.currentTimeMillis();
        wifiApDataUsageConfig.mEndDateAndTime = System.currentTimeMillis();
        wifiApDataUsageConfig.mUsageValueInBytes = (long) d;
        return wifiApDataUsageConfig.getUsageValueInLocaleString(context);
    }

    public static int convertDpToPixel(Context context, int i) {
        return (int) ((context.getResources().getDisplayMetrics().densityDpi / 160.0f) * i);
    }

    public static boolean convertIntToBoolean(int i) {
        return 1 == i;
    }

    public static int convertToColorRGB(String str) {
        int parseLong = (int) Long.parseLong(str.replace("#", ApnSettings.MVNO_NONE), 16);
        return Color.rgb((parseLong >> 16) & 255, (parseLong >> 8) & 255, parseLong & 255);
    }

    public static int getColorFromAttribute(Context context, int i) {
        Resources.Theme theme;
        if (context == null) {
            return 0;
        }
        try {
            Resources resources = context.getResources();
            if ((resources == null || resources.getResourceTypeName(i).equals("attr"))
                    && (theme = context.getTheme()) != null) {
                TypedValue typedValue = new TypedValue();
                theme.resolveAttribute(i, typedValue, true);
                TypedArray obtainStyledAttributes =
                        context.obtainStyledAttributes(typedValue.data, new int[] {i});
                int color = obtainStyledAttributes.getColor(0, 0);
                obtainStyledAttributes.recycle();
                return color;
            }
        } catch (Exception unused) {
        }
        return 0;
    }

    public static String getSamsungAccount(Context context) {
        Account[] accountsByType =
                ((AccountManager) context.getSystemService("account"))
                        .getAccountsByType("com.osp.app.signin");
        if (accountsByType.length > 0) {
            return accountsByType[0].name;
        }
        return null;
    }

    public static void hideKeyboard(Context context) {
        Log.i("WifiApSettingsUtils", "hiding keyboard");
        View currentFocus = ((Activity) context).getCurrentFocus();
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService("input_method");
        if (currentFocus == null || (currentFocus instanceof EditText)) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
    }

    public static String hideSecondHalfOfString(String str) {
        if (str == null || str.isEmpty()) {
            return "(Empty/Null)";
        }
        int length = str.length() / 2;
        StringBuffer stringBuffer = new StringBuffer(str.substring(0, length));
        for (int i = 0; i < length; i++) {
            stringBuffer.append("*");
        }
        return stringBuffer.toString();
    }

    public static boolean isActiveNetworkHasInternet(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService("connectivity");
        Network activeNetwork = connectivityManager.getActiveNetwork();
        boolean z = false;
        if (activeNetwork == null) {
            return false;
        }
        NetworkCapabilities networkCapabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork);
        if (networkCapabilities == null) {
            Log.i("WifiApSettingsUtils", "networkCapabilities is Null");
            return false;
        }
        boolean hasCapability = networkCapabilities.hasCapability(16);
        boolean hasTransport = networkCapabilities.hasTransport(0);
        boolean hasCapability2 = networkCapabilities.hasCapability(12);
        if (hasCapability || (hasTransport && hasCapability2)) {
            z = true;
        }
        StringBuilder m =
                Utils$$ExternalSyntheticOutline0.m(
                        "check if device has Active Network Internet: ",
                        z,
                        ",  hasNetCapabilityValidated: ",
                        hasCapability,
                        ",  hasTransportCellular: ");
        m.append(hasTransport);
        m.append(",  hasNetCapabilityInternet: ");
        m.append(hasCapability2);
        Log.i("WifiApSettingsUtils", m.toString());
        return z;
    }

    public static boolean isAirplaneModeOn(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "airplane_mode_on", 0) != 0;
    }

    public static boolean isCarrierNEWCO() {
        return "NEWCO".equals(Utils.CONFIGOPBRANDINGFORMOBILEAP);
    }

    public static boolean isCarrierTMO() {
        return "TMO".equals(Utils.CONFIGOPBRANDINGFORMOBILEAP);
    }

    public static boolean isDualModeSlot(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager.getPhoneCount() == 2) {
            Log.d(
                    "WifiApSettingsUtils",
                    "isDualModeSlot() - This device supports multi sim: "
                            + telephonyManager.getPhoneCount());
            return true;
        }
        Log.d(
                "WifiApSettingsUtils",
                "isDualModeSlot() - This device DOES NOT supports multi sim: "
                        + telephonyManager.getPhoneCount());
        return false;
    }

    public static void isNanEnabledDialogRequired(Context context) {
        WifiAwareManager wifiAwareManager;
        SemWifiAwareManager semWifiAwareManager;
        if (context.getPackageManager().hasSystemFeature("android.hardware.wifi.aware")) {
            wifiAwareManager = (WifiAwareManager) context.getSystemService("wifiaware");
            semWifiAwareManager = (SemWifiAwareManager) context.getSystemService("sem_wifi_aware");
        } else {
            wifiAwareManager = null;
            semWifiAwareManager = null;
        }
        boolean z =
                semWifiAwareManager != null
                        && semWifiAwareManager.isAwareSoftApConcurrencySupported();
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "isNanSoftApConcurrencySupported: ", "WifiApSettingsUtils", z);
        if (z || wifiAwareManager == null || semWifiAwareManager == null) {
            return;
        }
        boolean z2 = wifiAwareManager.isDeviceAttached() && semWifiAwareManager.isPreEnabled() != 1;
        StringBuilder sb = new StringBuilder("WifiAware`s isDeviceAttached: ");
        sb.append(wifiAwareManager.isDeviceAttached());
        sb.append(", isPreEnabledResult: ");
        sb.append(semWifiAwareManager.isPreEnabled() != 1);
        sb.append(", isNanEnabled: ");
        sb.append(z2);
        Log.i("WifiApSettingsUtils", sb.toString());
    }

    public static boolean isNearByDeviceScanningEnabled(Context context) {
        return Settings.System.getInt(context.getContentResolver(), "nearby_scanning_enabled", 1)
                == 1;
    }

    public static boolean isSamsungAccountLoggedOut(Context context) {
        boolean z =
                ((AccountManager) context.getSystemService("account"))
                                .getAccountsByType("com.osp.app.signin")
                                .length
                        == 0;
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "Is samsung Account logged out: ", "WifiApSettingsUtils", z);
        return z;
    }

    public static boolean isSimEnabled(Context context) {
        if (DBG && SystemProperties.get("vendor.wifiap.simcheck.disable").equals("1")) {
            return true;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (isDualModeSlot(context)) {
            if (isDualModeSlot(context)) {
                String str = SystemProperties.get("gsm.sim.state", "-1,-1");
                String[] split = str.split(",");
                Log.d("WifiApSettingsUtils", "isDualSlotAllOff() - simStatus: ".concat(str));
                if (ConnectionsUtils.isDuosModel()) {
                    String str2 = SystemProperties.get("gsm.sim.currentcardstatus", "-1,-1");
                    Log.d(
                            "WifiApSettingsUtils",
                            "isDualSlotAndSLot1Off() - cdmaSimStatus: " + str2);
                    String[] split2 = str2.split(",");
                    if ((split2.length == 1
                                    && !DATA.DM_FIELD_INDEX.PUBLIC_USER_ID.equals(split2[0]))
                            || (split2.length > 1
                                    && !DATA.DM_FIELD_INDEX.PUBLIC_USER_ID.equals(split2[0])
                                    && !DATA.DM_FIELD_INDEX.PUBLIC_USER_ID.equals(split2[1]))) {
                        Log.d("WifiApSettingsUtils", "isDualSlotAllOff() - returning true");
                    }
                } else if ((split.length == 1
                                && !"READY".equals(split[0])
                                && !"LOADED".equals(split[0]))
                        || (split.length > 1
                                && !"READY".equals(split[0])
                                && !"LOADED".equals(split[0])
                                && !"READY".equals(split[1])
                                && !"LOADED".equals(split[1]))) {
                    Log.d("WifiApSettingsUtils", "isDualSlotAllOff() - returning true");
                }
            } else {
                Log.d(
                        "WifiApSettingsUtils",
                        "isDualSlotAllOff() - This device DOES NOT support multi sim");
            }
            return true;
        }
        if (telephonyManager.getSimState() == 5
                || "LOADED".equals(SystemProperties.get("gsm.sim.state"))
                || "READY".equals(SystemProperties.get("gsm.sim.state"))) {
            return true;
        }
        Log.i("WifiApSettingsUtils", "Sim is not ready");
        return false;
    }

    public static boolean isTetheringRestricted(Context context) {
        UserManager userManager = (UserManager) context.getSystemService("user");
        return userManager.hasUserRestriction("no_config_tethering") || !userManager.isAdminUser();
    }

    public static void launchAddSamsungAccountActivity(Activity activity) {
        Log.i("WifiApSettingsUtils", "Launching Add Samsung Account Activity");
        Intent intent = new Intent();
        intent.setAction("com.sems.app.signin.action.ADD_SAMSUNG_ACCOUNT");
        intent.setPackage("com.osp.app.signin");
        intent.setFlags(603979776);
        intent.putExtra("MODE", "ADD_ACCOUNT");
        intent.putExtra("IS_FROM_SETTING", true);
        intent.putExtra("client_id", "s5d189ajvs");
        activity.startActivityForResult(intent, 105);
    }

    public static byte[] longToBytes(long j) {
        byte[] bArr = new byte[8];
        for (int i = 7; i >= 0; i--) {
            bArr[i] = (byte) (255 & j);
            j >>= 8;
        }
        return bArr;
    }

    public static String removeAlphaColor(Context context) {
        String string = context.getString(R.color.wifi_ap_primary_text_color);
        if (string.length() != 9) {
            return string;
        }
        return "#" + string.substring(3);
    }
}
