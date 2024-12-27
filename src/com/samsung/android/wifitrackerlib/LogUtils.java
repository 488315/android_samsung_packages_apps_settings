package com.samsung.android.wifitrackerlib;

import android.os.Debug;
import android.text.TextUtils;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class LogUtils {
    public final boolean isProductDev = Debug.semIsProductDev();
    public final Pattern bssidPattern = Pattern.compile("([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})");

    public final String getPrintableLog(String str) {
        if (TextUtils.isEmpty(str)) {
            return ApnSettings.MVNO_NONE;
        }
        StringBuilder sb = new StringBuilder();
        Matcher matcher = this.bssidPattern.matcher(str);
        int i = 0;
        while (matcher.find(i)) {
            try {
                int start = matcher.start();
                int end = matcher.end();
                sb.append((CharSequence) str, i, start + 9);
                sb.append("**");
                sb.append((CharSequence) str, start + 11, start + 12);
                sb.append("**");
                sb.append((CharSequence) str, start + 14, end);
                i = matcher.end();
            } catch (ArrayIndexOutOfBoundsException unused) {
            }
        }
        sb.append(str.substring(i));
        return sb.toString();
    }
}
