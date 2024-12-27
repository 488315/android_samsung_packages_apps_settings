package com.samsung.context.sdk.samsunganalytics.internal.util;

import android.os.Build;
import android.util.Log;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class Debug {
    public static void LogD(String str) {
        Log.d("SamsungAnalytics605015", str);
    }

    public static void LogE(String str) {
        Log.e("SamsungAnalytics605015", str);
    }

    public static void LogENG(String str) {
        if (Build.TYPE.equals("eng")) {
            DialogFragment$$ExternalSyntheticOutline0.m(
                    "[ENG ONLY] ", str, "SamsungAnalytics605015");
        }
    }

    public static void LogException(Class cls, Exception exc) {
        Log.w(
                "SamsungAnalytics605015",
                "["
                        + cls.getSimpleName()
                        + "] "
                        + exc.getClass().getSimpleName()
                        + " "
                        + exc.getMessage());
    }

    public static void LogD(String str, String str2) {
        LogD("[" + str + "] " + str2);
    }
}
