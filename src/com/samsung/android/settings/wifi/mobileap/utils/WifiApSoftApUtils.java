package com.samsung.android.settings.wifi.mobileap.utils;

import android.content.Context;
import android.net.wifi.SoftApConfiguration;
import android.provider.Settings;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApBandConfig;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class WifiApSoftApUtils {
    public static final int[] BAND_2GHZ_ARRAY = {1};
    public static final int[] BAND_2GHZ_AND_5GHZ_ARRAY = {1, 2};
    public static final int[] BAND_5GHZ_ARRAY = {2};
    public static final int[] BAND_6GHZ_ARRAY = {4};

    public static boolean checkIfSecurityTypeIsAnyOpenVariant(int i) {
        boolean z = i == 0;
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "Check with Open Security Type: ", "WifiApSoftApUtils", z);
        if (z) {
            return true;
        }
        boolean z2 = i == 5;
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "Check with Open Enhanced Security Type: ", "WifiApSoftApUtils", z2);
        return z2;
    }

    public static int[] getBandArray(Context context) {
        int[] softApBands = WifiApFrameworkUtils.getSemWifiManager(context).getSoftApBands();
        if (softApBands == null || softApBands.length == 0) {
            Log.e("WifiApSoftApUtils", "softApBandArray is NULL/Empty. Resetting to 2.4Ghz");
            softApBands = BAND_2GHZ_ARRAY;
        }
        return getBandArrayAfterBitWiseOperation(softApBands);
    }

    public static int[] getBandArrayAfterBitWiseOperation(int[] iArr) {
        if (iArr.length > 1) {
            return BAND_2GHZ_AND_5GHZ_ARRAY;
        }
        int i = iArr[0];
        return (i & 4) != 0 ? BAND_6GHZ_ARRAY : (i & 2) != 0 ? BAND_5GHZ_ARRAY : BAND_2GHZ_ARRAY;
    }

    public static int getBroadcastChannel(Context context) {
        int i = 0;
        int i2 = Settings.Secure.getInt(context.getContentResolver(), "wifi_ap_last_2g_channel", 0);
        if (i2 >= 0 && i2 <= 11) {
            i = i2;
        }
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                i, "Getting Broadcast Channel): ", "WifiApSoftApUtils");
        return i;
    }

    public static int getSecurityType(Context context) {
        return getSoftApConfiguration(context).getSecurityType();
    }

    public static String getSecurityTypeString(Context context, int i) {
        String[] stringArray =
                context.getResources()
                        .getStringArray(R.array.wifi_ap_owe_wpa3_security_type_entries);
        String str = stringArray[0];
        String str2 = stringArray[1];
        String str3 = stringArray[2];
        String str4 = stringArray[3];
        String str5 = stringArray[4];
        if (i == 0) {
            return str;
        }
        if (i == 1) {
            return str3;
        }
        if (i == 2) {
            return str4;
        }
        if (i == 3) {
            return str5;
        }
        if (i == 5) {
            return str2;
        }
        WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                .m(i, "getSecurityTypeString-Invalid security type: ", "WifiApSoftApUtils");
        return str3;
    }

    public static SoftApConfiguration getSoftApConfiguration(Context context) {
        return WifiApFrameworkUtils.getSemWifiManager(context).getSoftApConfiguration();
    }

    public static List getSupportedBandList(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new WifiApBandConfig(context, BAND_2GHZ_ARRAY, 0));
        int i = 1;
        if (WifiApFeatureUtils.is5GhzBandSupported(context)) {
            arrayList.add(new WifiApBandConfig(context, BAND_2GHZ_AND_5GHZ_ARRAY, 1));
            i = 2;
        }
        if (WifiApFrameworkUtils.getSemWifiManager(context).supportWifiAp5GBasedOnCountry()) {
            arrayList.add(new WifiApBandConfig(context, BAND_5GHZ_ARRAY, i));
            i++;
        }
        if (WifiApFrameworkUtils.getSemWifiManager(context).supportWifiAp6GBasedOnCountry()) {
            arrayList.add(new WifiApBandConfig(context, BAND_6GHZ_ARRAY, i));
        }
        return arrayList;
    }

    public static int getTurnOffHotspotTimerInMinutes(Context context) {
        int i = 20;
        int i2 =
                Settings.Secure.getInt(context.getContentResolver(), "wifi_ap_timeout_setting", 20);
        if (i2 < 0) {
            setTurnOffHotspotTimerInMinutes(context, 20);
        } else if (i2 > 60) {
            i = i2 / 60;
            setTurnOffHotspotTimerInMinutes(context, i);
        } else {
            i = i2;
        }
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                i, "Getting Turn Off Hotspot Timer(Minutes): ", "WifiApSoftApUtils");
        return i;
    }

    public static WifiApBandConfig getWifiApBandConfig(Context context) {
        return new WifiApBandConfig(
                context, getBandArrayAfterBitWiseOperation(getBandArray(context)), -1);
    }

    public static boolean isDefaultPassphraseSet(Context context) {
        SoftApConfiguration softApConfiguration = getSoftApConfiguration(context);
        if (softApConfiguration.getPassphrase() == null
                || !softApConfiguration.getPassphrase().equals("\tUSER#DEFINED#PWD#\n")
                || softApConfiguration.getSecurityType() == 0) {
            return false;
        }
        Log.i("WifiApSoftApUtils", " Default password is set");
        return true;
    }

    public static boolean isSupportWifi6StandardEnabled(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), "wifi_ap_11ax_mode_checked", 0)
                == 1;
    }

    public static boolean isWifiSharingEnabled(Context context) {
        return WifiApFrameworkUtils.getSemWifiManager(context).isWifiSharingSupported()
                && Settings.Secure.getInt(context.getContentResolver(), "wifi_ap_wifi_sharing", 0)
                        == 1;
    }

    public static boolean searchForBandInArray(int i, int[] iArr) {
        for (int i2 : iArr) {
            if ((i2 & i) != 0) {
                return true;
            }
        }
        return false;
    }

    public static void setSoftApConfiguration(
            Context context, SoftApConfiguration softApConfiguration) {
        if (context != null) {
            ((SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE))
                    .setSoftApConfiguration(softApConfiguration);
        } else {
            Log.e("WifiApSoftApUtils", "Context is null");
        }
    }

    public static void setTurnOffHotspotTimerInMinutes(Context context, int i) {
        Log.i("WifiApSoftApUtils", "Setting Turn Off Hotspot Timer(Minutes): " + i);
        Settings.Secure.putInt(context.getContentResolver(), "wifi_ap_timeout_setting", i);
    }

    public static int getSecurityType(Context context, String str) {
        if (str == null) {
            str = ApnSettings.MVNO_NONE;
        }
        String[] stringArray =
                context.getResources()
                        .getStringArray(R.array.wifi_ap_owe_wpa3_security_type_entries);
        String str2 = stringArray[0];
        String str3 = stringArray[1];
        String str4 = stringArray[2];
        String str5 = stringArray[3];
        String str6 = stringArray[4];
        if (str.equals(str2)) {
            return 0;
        }
        if (str.equals(str3)) {
            return 5;
        }
        if (str.equals(str4)) {
            return 1;
        }
        if (str.equals(str5)) {
            return 2;
        }
        if (str.equals(str6)) {
            return 3;
        }
        Log.e("WifiApSoftApUtils", "getSecurityType - Invalid securityTypeString : ".concat(str));
        return 1;
    }
}
