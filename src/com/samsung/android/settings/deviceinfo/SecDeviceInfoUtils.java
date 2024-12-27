package com.samsung.android.settings.deviceinfo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.SystemProperties;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.UiccCardInfo;
import android.telephony.UiccPortInfo;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.picker.widget.SeslNumberPickerSpinnerDelegate$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.Utils$$ExternalSyntheticLambda4;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.sec.ims.configuration.DATA;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SecDeviceInfoUtils {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum Operator {
        UK_VIRGIN;

        private final String[] mccMncList;
        private final String simType = null;

        Operator(String[] strArr) {
            this.mccMncList = strArr;
        }

        public final boolean isInsertedSim(int i) {
            String semGetTelephonyProperty =
                    TelephonyManager.semGetTelephonyProperty(
                            i, "gsm.sim.operator.numeric", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
            Stream stream = Arrays.stream(this.mccMncList);
            Objects.requireNonNull(semGetTelephonyProperty);
            return stream.anyMatch(new Utils$$ExternalSyntheticLambda4(semGetTelephonyProperty));
        }
    }

    public static String addWhitespaceEvery4digits(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        char[] charArray = str.toCharArray();
        int length = str.length();
        for (int i = 1; i < length + 1; i++) {
            sb.append(charArray[i - 1]);
            if (i % 4 == 0) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public static boolean checkRootingCondition() {
        if (isPhoneStatusUnlocked()) {
            return true;
        }
        String[] strArr = {
            "/sbin/su",
            "/system/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/system/bin/.ext/.su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su",
            "/system/app/Superuser.apk",
            "/system/usr/we-need-root/su-backup",
            "/system/xbin/mu"
        };
        String[] strArr2 = {
            "/data",
            "/",
            "/system",
            "/system/bin",
            "/system/sbin",
            "/system/xbin",
            "/vendor/bin",
            "/sys",
            "/sbin",
            "/etc",
            "/proc",
            "/dev"
        };
        for (int i = 0; i < 13; i++) {
            String str = strArr[i];
            if (TextUtils.isEmpty(str)) {
                break;
            }
            if (SeslNumberPickerSpinnerDelegate$$ExternalSyntheticOutline0.m(str)) {
                Log.secD("Utils", "rooting:su located at : " + str);
                return true;
            }
        }
        for (int i2 = 0; i2 < 12; i2++) {
            String str2 = strArr2[i2];
            if (TextUtils.isEmpty(str2)) {
                break;
            }
            if (new File(str2).canWrite()) {
                Log.secD("Utils", "rooting:read only changed as writable : " + str2);
            }
        }
        return false;
    }

    public static String getDefaultDeviceName(Context context) {
        String string =
                Settings.Global.getString(context.getContentResolver(), "default_device_name");
        if (!TextUtils.isEmpty(string) && !string.equals(Build.MODEL)) {
            return string;
        }
        String string2 =
                SemCscFeature.getInstance().getString("CscFeature_Common_ConfigDevBrandName");
        String string3 =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_SETTINGS_CONFIG_BRAND_NAME");
        return !TextUtils.isEmpty(string2)
                ? string2
                : !TextUtils.isEmpty(string3) ? string3 : Build.MODEL;
    }

    public static String getImei(Context context, int i, boolean z) {
        String str;
        String salesCode = Utils.getSalesCode();
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager != null) {
            String telephonyProperty =
                    TelephonyManager.getTelephonyProperty(i, "ril.simoperator", "ETC");
            if (isPhoneTypeCdma(context, i)
                    || telephonyProperty.contains("CTC")
                    || isMetroPCS(salesCode)
                    || "VZW".equals(salesCode)
                    || "VPP".equals(salesCode)) {
                Log.i("SecDeviceInfoUtils", "getImei() - call getImei");
                if (z) {
                    str = telephonyManager.getImei(i);
                    char c =
                            "XAR".equals(salesCode)
                                    ? (char) 0
                                    : ("VZW".equals(salesCode) || "VPP".equals(salesCode))
                                            ? (char) 1
                                            : (char) 65535;
                    if (TextUtils.isEmpty(str)) {
                        str = null;
                    } else if (c != 0) {
                        if (c == 1) {
                            str = addWhitespaceEvery4digits(str);
                        }
                    } else if (str.length() > 13) {
                        str = str.substring(0, 14);
                    }
                } else {
                    str = telephonyManager.getImei(i);
                }
            } else {
                Log.i("SecDeviceInfoUtils", "getImei() - call getDeviceId");
                str = telephonyManager.getDeviceId(i);
            }
        } else {
            str = ApnSettings.MVNO_NONE;
        }
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        Log.i("SecDeviceInfoUtils", "getImei() - value isEmpty, set Default");
        return context.getResources().getString(R.string.device_info_default);
    }

    public static String getImeiTitleSuffix(Context context, int i) {
        if (((TelephonyManager) context.getSystemService("phone")).getSimCount() <= 1) {
            return ApnSettings.MVNO_NONE;
        }
        if (!isActiveSimPresent(context, i)) {
            return " (" + context.getString(R.string.device_info_imei_available) + ")";
        }
        if (isOpportunisticSim(context, i)) {
            return " (" + context.getString(R.string.device_info_imei_available) + ")";
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        boolean z = false;
        if (telephonyManager != null) {
            Iterator<UiccCardInfo> it = telephonyManager.getUiccCardsInfo().iterator();
            loop0:
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                UiccCardInfo next = it.next();
                Iterator<UiccPortInfo> it2 = next.getPorts().iterator();
                while (it2.hasNext()) {
                    if (it2.next().getLogicalSlotIndex() == i) {
                        StringBuilder m =
                                ListPopupWindow$$ExternalSyntheticOutline0.m(
                                        i, "slot : ", " isEuicc = ");
                        m.append(next.isEuicc());
                        Log.d("SecDeviceInfoUtils", m.toString());
                        z = next.isEuicc();
                        break loop0;
                    }
                }
            }
        } else {
            Log.e("SecDeviceInfoUtils", "isEsim: TelephonyManager is null");
        }
        if (z) {
            return " (" + context.getString(R.string.device_info_imei_esim) + ")";
        }
        return " (" + context.getString(R.string.device_info_imei_sim_card) + ")";
    }

    public static String getPreferenceToSpecificActivityTitleInfo(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(intent, 0);
        int size = queryIntentActivities.size();
        for (int i = 0; i < size; i++) {
            ResolveInfo resolveInfo = queryIntentActivities.get(i);
            if ((resolveInfo.activityInfo.applicationInfo.flags & 1) != 0) {
                return resolveInfo.loadLabel(packageManager).toString();
            }
        }
        return ApnSettings.MVNO_NONE;
    }

    public static String getPrintableImei(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() <= 11) {
            return str;
        }
        return str.substring(0, 4) + "[*****]" + str.substring(str.length() - 4);
    }

    public static String getSerialNumber(Context context) {
        String serial = Build.getSerial();
        return TextUtils.isEmpty(serial)
                ? context.getResources().getString(R.string.device_info_default)
                : serial;
    }

    public static boolean isActiveSimPresent(Context context, int i) {
        int simState = ((TelephonyManager) context.getSystemService("phone")).getSimState(i);
        boolean z = (simState == 1 || simState == 0 || simState == 6) ? false : true;
        Log.d("SecDeviceInfoUtils", "slot " + i + " isActiveSimPresent = " + z);
        return z;
    }

    public static boolean isMetroPCS(String str) {
        String str2 = SystemProperties.get("ro.product.name");
        return str2 != null
                && (str2.contains("MetroPCS") || str2.endsWith("mtr") || "TMK".equals(str));
    }

    public static boolean isOpportunisticSim(Context context, int i) {
        SubscriptionInfo activeSubscriptionInfoForSimSlotIndex =
                ((SubscriptionManager) context.getSystemService(SubscriptionManager.class))
                        .getActiveSubscriptionInfoForSimSlotIndex(i);
        if (activeSubscriptionInfoForSimSlotIndex == null) {
            return false;
        }
        StringBuilder m =
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "slot ", " isOpportunistic = ");
        m.append(activeSubscriptionInfoForSimSlotIndex.isOpportunistic());
        Log.d("SecDeviceInfoUtils", m.toString());
        return Rune.isEnabledHidingByOpportunisticEsim(context)
                && activeSubscriptionInfoForSimSlotIndex.isOpportunistic();
    }

    public static boolean isPhoneStatusUnlocked() {
        String str = SystemProperties.get("ro.boot.flash.locked");
        Log.d("SecDeviceInfoUtils", "phone status : " + str);
        return DATA.DM_FIELD_INDEX.PCSCF_DOMAIN.equalsIgnoreCase(str);
    }

    public static boolean isPhoneTypeCdma(Context context, int i) {
        boolean z =
                ((TelephonyManager) context.getSystemService("phone")).getCurrentPhoneTypeForSlot(i)
                        == 2;
        Log.i("SecDeviceInfoUtils", "slotId : " + i + " Cdma : " + z);
        return z;
    }
}
