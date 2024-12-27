package com.samsung.android.settings.deviceinfo;

import android.text.TextUtils;

import com.android.settings.accessibility.PreferredShortcuts$$ExternalSyntheticOutline0;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.util.SemLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SecAboutDeviceItems {
    public static final int ABOUTITEM_STATUS_MODELNUMBER_EXTRA_SUFFIX;
    public static final int ABOUTITEM_STATUS_TRA_NUMBERS;
    public static final int ABOUTITEM_STATUS_FCC_CERTIFICATION = getSupportedStatus("fcc");
    public static final int ABOUTITEM_STATUS_ISED_CERTIFICATION = getSupportedStatus("ised");
    public static final int ABOUTITEM_STATUS_SPEN_FCC_CERTIFICATION =
            getSupportedStatus("fccforspen");
    public static final int ABOUTITEM_STATUS_SPEN_ISED_CERTIFICATION =
            getSupportedStatus("isedforspen");
    public static final int ABOUTITEM_STATUS_RATED = getSupportedStatus("rated");
    public static final int ABOUTITEM_STATUS_RATED_MAX = getSupportedStatus("ratedmax");
    public static final int ABOUTITEM_STATUS_BATTERY_CAPACITY =
            getSupportedStatus("batterycapacity");
    public static final int ABOUTITEM_STATUS_MODELNUMBER_DS_SUFFIX =
            getSupportedStatus("modelnumberDSsuffix");

    static {
        getSupportedStatus("modelnumberNFCsuffix");
        getSupportedStatus("singleSimRemoveNFCsuffix");
        ABOUTITEM_STATUS_MODELNUMBER_EXTRA_SUFFIX = getSupportedStatus("modelnumberEXTRAsuffix");
        ABOUTITEM_STATUS_TRA_NUMBERS = getSupportedStatus("traNumber");
    }

    public static String getItemValue(String str) {
        int i;
        switch (str) {
            case "fccforspen":
                i = ABOUTITEM_STATUS_SPEN_FCC_CERTIFICATION;
                break;
            case "isedforspen":
                i = ABOUTITEM_STATUS_SPEN_ISED_CERTIFICATION;
                break;
            case "fcc":
                i = ABOUTITEM_STATUS_FCC_CERTIFICATION;
                break;
            case "ised":
                i = ABOUTITEM_STATUS_ISED_CERTIFICATION;
                break;
            case "rated":
                i = ABOUTITEM_STATUS_RATED;
                break;
            case "traNumber":
                i = ABOUTITEM_STATUS_TRA_NUMBERS;
                break;
            case "ratedmax":
                i = ABOUTITEM_STATUS_RATED_MAX;
                break;
            case "batterycapacity":
                i = ABOUTITEM_STATUS_BATTERY_CAPACITY;
                break;
            default:
                i = getSupportedStatus(str);
                break;
        }
        if (2 != i) {
            return ApnSettings.MVNO_NONE;
        }
        String string =
                SemCscFeature.getInstance().getString("CscFeature_Setting_ConfigAboutDeviceItems");
        int indexOf = string.indexOf(str.concat("=\""));
        int indexOf2 = string.indexOf("\"", indexOf) + 1;
        int indexOf3 = string.indexOf("\"", indexOf2);
        String substring = string.substring(indexOf2, indexOf3);
        StringBuilder m =
                PreferredShortcuts$$ExternalSyntheticOutline0.m(
                        indexOf, "Item (", str, ") IndexInfo = ", "/");
        m.append(indexOf2);
        m.append("/");
        m.append(indexOf3);
        SemLog.i("SecAboutDeviceItems", m.toString());
        return substring;
    }

    public static int getSupportedStatus(String str) {
        String string =
                SemCscFeature.getInstance().getString("CscFeature_Setting_ConfigAboutDeviceItems");
        if (TextUtils.isEmpty(string)) {
            SemLog.i("SecAboutDeviceItems", "Don't care - AboutDeviceItems Delta is Null");
            return -1;
        }
        String lowerCase = string.toLowerCase();
        String lowerCase2 = str.toLowerCase();
        SemLog.d("SecAboutDeviceItems", "CSC value: " + lowerCase + ", item: " + lowerCase2);
        if (!lowerCase.contains(lowerCase2 + "=")) {
            SemLog.i("SecAboutDeviceItems", "Don't care (" + lowerCase2 + ")");
            return -1;
        }
        if (lowerCase.contains(lowerCase2 + "=disabled")) {
            SemLog.i("SecAboutDeviceItems", "NOT SUPPORTED (" + lowerCase2 + ")");
            return 0;
        }
        SemLog.i("SecAboutDeviceItems", "SUPPORTED (" + lowerCase2 + ")");
        if (lowerCase.contains(lowerCase2 + "=enabled")) {
            return 1;
        }
        SemLog.i("SecAboutDeviceItems", "SUPPORTED with value (" + lowerCase2 + ")");
        return 2;
    }
}
