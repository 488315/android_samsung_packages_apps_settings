package com.samsung.android.settings.voiceinput;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.compose.ui.text.SaversKt$LocaleSaver$2$$ExternalSyntheticOutline0;

import com.samsung.android.util.SemLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class PackageUtils {
    public static final int DOWNLOADABLE = 1;
    public static final int DOWNLOADED = 3;
    public static final int NOTAVAILABLE = 0;
    private static final String TAG = "@VoiceIn: PackageUtils";
    public static final int UPADATEABLE = 2;

    public static int getVersionCode(Context context, String str) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        if (packageManager != null) {
            try {
                packageInfo = packageManager.getPackageInfo(str, 128);
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(TAG, "Failed to get [" + str + "] packageInfo, " + e);
            }
        }
        if (packageInfo != null) {
            return packageInfo.versionCode;
        }
        SaversKt$LocaleSaver$2$$ExternalSyntheticOutline0.m(
                "Failed to get [", str, "] version code", TAG);
        return -1;
    }

    public static boolean isNetworkConnected(Context context, int i) {
        NetworkInfo networkInfo;
        if (context == null) {
            SemLog.d(TAG, "isWifiEnabled ctx is null");
            return false;
        }
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null
                || (networkInfo = connectivityManager.getNetworkInfo(i)) == null) {
            return false;
        }
        return networkInfo.isConnected();
    }

    public static boolean isPackageInstalled(Context context, String str) {
        try {
            if (context == null) {
                SemLog.i(TAG, "context is null");
                return false;
            }
            context.getPackageManager().getApplicationInfo(str, 0);
            SemLog.i(TAG, str + " is installed package. return true");
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            SemLog.i(TAG, str + " is not installed package. return false");
            return false;
        }
    }
}
