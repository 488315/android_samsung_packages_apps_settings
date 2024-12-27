package com.android.settingslib;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.system.StructUtsname;
import android.text.TextUtils;
import android.util.Log;

import com.android.settings.R;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class DeviceInfoUtils {
    public static String formatKernelVersion(Context context, StructUtsname structUtsname) {
        if (structUtsname == null) {
            return context.getString(R.string.status_unavailable);
        }
        Matcher matcher =
                Pattern.compile("(#\\d+) (?:.*?)?((Sun|Mon|Tue|Wed|Thu|Fri|Sat).+)")
                        .matcher(structUtsname.version);
        if (!matcher.matches()) {
            Log.e(
                    "DeviceInfoUtils",
                    "Regex did not match on uname version " + structUtsname.version);
            return context.getString(R.string.status_unavailable);
        }
        return structUtsname.release + "\n" + matcher.group(1) + " " + matcher.group(2);
    }

    public static String getFeedbackReporterPackage(Context context) {
        String string = context.getResources().getString(R.string.oem_preferred_feedback_reporter);
        if (TextUtils.isEmpty(string)) {
            return string;
        }
        Intent intent = new Intent("android.intent.action.BUG_REPORT");
        PackageManager packageManager = context.getPackageManager();
        for (ResolveInfo resolveInfo : packageManager.queryIntentActivities(intent, 64)) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (activityInfo != null && !TextUtils.isEmpty(activityInfo.packageName)) {
                try {
                    if ((packageManager.getApplicationInfo(resolveInfo.activityInfo.packageName, 0)
                                                    .flags
                                            & 1)
                                    != 0
                            && TextUtils.equals(resolveInfo.activityInfo.packageName, string)) {
                        return string;
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                    continue;
                }
            }
        }
        return null;
    }

    public static String readLine() {
        BufferedReader bufferedReader =
                new BufferedReader(new FileReader("/sys/board_properties/soc/msv"), 256);
        try {
            return bufferedReader.readLine();
        } finally {
            bufferedReader.close();
        }
    }
}
