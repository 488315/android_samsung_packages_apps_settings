package com.samsung.android.settings.wifi.mobileap;

import android.content.Context;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.configuration.DATA;
import com.sec.ims.extensions.WiFiManagerExt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiApBigDataLogger {
    public final SemWifiManager mSemWifiManager;

    public WifiApBigDataLogger(Context context) {
        this.mSemWifiManager =
                (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
    }

    public final void insertMHSBigData(int i, int i2) {
        boolean z;
        String str = DATA.DM_FIELD_INDEX.SMS_OVER_IMS;
        String str2 = ApnSettings.MVNO_NONE;
        switch (i) {
            case 0:
                Log.i("WifiApBigDataLogger", "MHS SSID Logging");
                break;
            case 1:
                Log.i("WifiApBigDataLogger", "MHS Password Logging");
                this.mSemWifiManager.reportMHSBigData("MHPW", ApnSettings.MVNO_NONE);
                break;
            case 2:
                if (i2 == 0) {
                    str2 = "OPEN";
                } else if (i2 == 1 || i2 == 2) {
                    str2 = "WPA2 PSK";
                } else if (i2 == 3) {
                    str2 = "WPA3";
                } else if (i2 == 4) {
                    str2 = "WPA3 TRANSITION";
                }
                Log.i("WifiApBigDataLogger", "MHS Security Logging ".concat(str2));
                this.mSemWifiManager.reportMHSBigData("MHSC", str2);
                break;
            case 3:
                if (i2 == 1) {
                    str = "1";
                } else if (i2 == 2) {
                    str = "2";
                } else if (i2 == 3) {
                    str = DATA.DM_FIELD_INDEX.PUBLIC_USER_ID;
                } else if (i2 == 4) {
                    str = "4";
                } else if (i2 == 5) {
                    str = DATA.DM_FIELD_INDEX.LBO_PCSCF_ADDRESS_TYPE;
                } else if (i2 == 6) {
                    str = DATA.DM_FIELD_INDEX.AMR_AUDIO_BITRATE;
                } else if (i2 == 7) {
                    str = DATA.DM_FIELD_INDEX.AMR_AUDIO_BITRATE_WB;
                } else if (i2 == 8) {
                    str = DATA.DM_FIELD_INDEX.SIP_SESSION_TIMER;
                } else if (i2 == 9) {
                    str = DATA.DM_FIELD_INDEX.SMS_FORMAT;
                } else if (i2 != 10) {
                    str = ApnSettings.MVNO_NONE;
                }
                Log.i("WifiApBigDataLogger", "MHS MaxClient Logging ".concat(str));
                this.mSemWifiManager.reportMHSBigData("MHMC", str);
                break;
            case 4:
                if (i2 == 0) {
                    str = "AUTO";
                } else if (i2 == 1) {
                    str = "1";
                } else if (i2 == 2) {
                    str = "2";
                } else if (i2 == 3) {
                    str = DATA.DM_FIELD_INDEX.PUBLIC_USER_ID;
                } else if (i2 == 4) {
                    str = "4";
                } else if (i2 == 5) {
                    str = DATA.DM_FIELD_INDEX.LBO_PCSCF_ADDRESS_TYPE;
                } else if (i2 == 6) {
                    str = DATA.DM_FIELD_INDEX.AMR_AUDIO_BITRATE;
                } else if (i2 == 7) {
                    str = DATA.DM_FIELD_INDEX.AMR_AUDIO_BITRATE_WB;
                } else if (i2 == 8) {
                    str = DATA.DM_FIELD_INDEX.SIP_SESSION_TIMER;
                } else if (i2 == 9) {
                    str = DATA.DM_FIELD_INDEX.SMS_FORMAT;
                } else if (i2 != 10) {
                    str =
                            i2 == 11
                                    ? DATA.DM_FIELD_INDEX.SMS_WRITE_UICC
                                    : i2 == 149
                                            ? DATA.DM_FIELD_INDEX.SIP_T1_TIMER
                                            : ApnSettings.MVNO_NONE;
                }
                Log.i("WifiApBigDataLogger", "MHS Channel Logging ".concat(str));
                this.mSemWifiManager.reportMHSBigData("MHBC", str);
                break;
            case 5:
                z = i2 == 1;
                AbsAdapter$$ExternalSyntheticOutline0.m(
                        "MHS Hidden SSID Logging ", "WifiApBigDataLogger", z);
                if (!z) {
                    this.mSemWifiManager.reportMHSBigData("MHHD", "OFF");
                    break;
                } else {
                    this.mSemWifiManager.reportMHSBigData("MHHD", "ON");
                    break;
                }
            case 7:
                if (i2 == 0) {
                    str2 = "NEVER TIMEOUT";
                } else if (i2 == 1) {
                    str2 = "5 MINS";
                } else if (i2 == 2) {
                    str2 = "10 MINS";
                } else if (i2 == 3) {
                    str2 = "20 MINS";
                } else if (i2 == 4) {
                    str2 = "30 MINS";
                } else if (i2 == 5) {
                    str2 = "60 MINS";
                }
                Log.i("WifiApBigDataLogger", "MHS TimeOut Logging ".concat(str2));
                this.mSemWifiManager.reportMHSBigData("MHTO", str2);
                break;
            case 8:
                z = i2 == 1;
                AbsAdapter$$ExternalSyntheticOutline0.m(
                        "MHS Wi-Fi Sharing Logging ", "WifiApBigDataLogger", z);
                if (!z) {
                    this.mSemWifiManager.reportMHSBigData("MHWS", "OFF");
                    break;
                } else {
                    this.mSemWifiManager.reportMHSBigData("MHWS", "ON");
                    break;
                }
        }
    }
}
