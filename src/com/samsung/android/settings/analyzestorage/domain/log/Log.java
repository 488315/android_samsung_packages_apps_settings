package com.samsung.android.settings.analyzestorage.domain.log;

import android.os.Debug;
import android.os.SemSystemProperties;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.domain.utils.EncodeUtils;

import java.util.Base64;
import java.util.Locale;
import java.util.Random;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class Log {
    public static final Boolean sIsEngBinary;
    public static final Boolean sIsUserShipBinary;
    public static int sLogIndex;

    static {
        Boolean valueOf = Boolean.valueOf(!"eng".equals(SemSystemProperties.get("ro.build.type")));
        sIsEngBinary = valueOf;
        sIsUserShipBinary =
                Boolean.valueOf((Debug.semIsProductDev() || valueOf.booleanValue()) ? false : true);
    }

    public static void d(String str, String str2) {
        android.util.Log.d("ManageStorage", getMsg(str, str2));
    }

    public static void e(String str, String str2) {
        android.util.Log.e("ManageStorage", getMsg(str, str2));
    }

    public static String getEncodedMsg(String str) {
        if (!sIsEngBinary.booleanValue()) {
            return str;
        }
        if (str == null || str.isEmpty()) {
            return ApnSettings.MVNO_NONE;
        }
        int nextInt = new Random(System.currentTimeMillis()).nextInt(4);
        byte[] bytes = str.getBytes();
        byte[] bArr = EncodeUtils.sEncV2Key[nextInt];
        byte[] bArr2 = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            bArr2[i] = (byte) (bytes[i] ^ bArr[i % bArr.length]);
        }
        return " #G$E" + nextInt + Base64.getEncoder().encodeToString(bArr2) + " ";
    }

    public static String getMsg(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        if (str != null) {
            Locale locale = Locale.getDefault();
            int i = sLogIndex + 1;
            sLogIndex = i;
            if (i > 9999) {
                sLogIndex = 0;
            }
            sb.append(String.format(locale, "[%04d/%-20s] ", Integer.valueOf(sLogIndex), str));
        } else {
            Locale locale2 = Locale.getDefault();
            int i2 = sLogIndex + 1;
            sLogIndex = i2;
            if (i2 > 9999) {
                sLogIndex = 0;
            }
            sb.append(String.format(locale2, "[%04d] ", Integer.valueOf(sLogIndex)));
        }
        sb.append(str2);
        return sb.toString();
    }

    public static void i(String str, String str2) {
        android.util.Log.i("ManageStorage", getMsg(str, str2));
    }

    public static void v(String str, String str2) {
        if (sIsUserShipBinary.booleanValue()) {
            return;
        }
        android.util.Log.v("ManageStorage", getMsg(str, str2));
    }

    public static void w(String str, String str2) {
        android.util.Log.w("ManageStorage", getMsg(str, str2));
    }
}
