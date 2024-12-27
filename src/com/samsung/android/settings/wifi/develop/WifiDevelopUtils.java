package com.samsung.android.settings.wifi.develop;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class WifiDevelopUtils {
    public static String convertTimeToSimpleDateFormat(long j) {
        String str = ApnSettings.MVNO_NONE + (j / 1000);
        try {
            return new SimpleDateFormat("yy.MM.dd", Locale.US).format(new Date(j));
        } catch (IllegalArgumentException unused) {
            return str;
        }
    }
}
