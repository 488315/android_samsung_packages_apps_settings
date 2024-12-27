package com.samsung.android.scloud.oem.lib;

import android.os.Build;
import android.util.Log;

import androidx.apppickerview.features.applabel.AbstractAppLabelMapFactory$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class LOG {
    public static final boolean enabled = "eng".equals(Build.TYPE);

    public static void d(String str, String str2) {
        if (!enabled || str2 == null) {
            return;
        }
        Log.d("[SCLIB_2.4.0.5]".concat(str), str2);
    }

    public static void e(String str, String str2, Throwable th) {
        Locale locale = Locale.US;
        Log.e(
                "[SCLIB_2.4.0.5]SCLOUD_ERR-".concat(str),
                AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(
                        str2,
                        " ",
                        th == null ? ApnSettings.MVNO_NONE : Log.getStackTraceString(th)));
    }

    public static void i(String str, String str2) {
        if (str2 != null) {
            Log.i("[SCLIB_2.4.0.5]".concat(str), str2);
        }
    }
}
