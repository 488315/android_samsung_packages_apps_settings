package com.samsung.android.settings.deviceinfo.statusinfo.simlockstatus;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.preference.Preference$$ExternalSyntheticOutline0;

import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SimLockStatusUtils {
    public static void closeSilently(Closeable... closeableArr) {
        for (Closeable closeable : closeableArr) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException unused) {
                }
            }
        }
    }

    public static byte[] getImei(Context context) {
        byte[] bArr = new byte[16];
        try {
            bArr =
                    (((TelephonyManager) context.getSystemService("phone")).getDeviceId() + '0')
                            .getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e("SimLockStatusUtils", "Could not get IMEI : " + e.getLocalizedMessage());
        }
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("deviceId length is :  "), bArr.length, "SimLockStatusUtils");
        return bArr;
    }
}
