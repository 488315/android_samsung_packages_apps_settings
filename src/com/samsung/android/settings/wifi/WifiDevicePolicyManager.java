package com.samsung.android.settings.wifi;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.util.Log;

import com.android.settings.wifi.WifiUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class WifiDevicePolicyManager {
    public static boolean canModifyNetwork(Context context, WifiConfiguration wifiConfiguration) {
        return !WifiUtils.isNetworkLockedDown(context, wifiConfiguration);
    }

    public static boolean getCursorValuePositive(
            Context context, String str, String str2, String[] strArr, String str3) {
        if (context == null) {
            return false;
        }
        Cursor query = context.getContentResolver().query(Uri.parse(str), null, str2, strArr, null);
        if (query == null) {
            if (query != null) {
                query.close();
            }
            return false;
        }
        try {
            query.moveToFirst();
            boolean equals = query.getString(query.getColumnIndex(str2)).equals(str3);
            query.close();
            return equals;
        } catch (Throwable th) {
            try {
                query.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static boolean isAllowedToShowPasswordHiddenView(Context context) {
        if (!getCursorValuePositive(
                context,
                "content://com.sec.knox.provider2/WifiPolicy",
                "getPasswordHidden",
                null,
                "true")) {
            return true;
        }
        Log.i("WifiDevicePolicyManager", "isAllowedToShowPasswordHiddenView false");
        return false;
    }

    public static boolean isAllowedToShowRetryDialog(Context context) {
        if (!getCursorValuePositive(
                context,
                "content://com.sec.knox.provider2/WifiPolicy",
                "getPromptCredentialsEnabled",
                null,
                "false")) {
            return true;
        }
        Log.i("WifiDevicePolicyManager", "isAllowedToShowRetryDialog false");
        return false;
    }

    public static boolean isAllowedWifiScanning(Context context) {
        if (!getCursorValuePositive(
                context,
                "content://com.sec.knox.provider2/WifiPolicy",
                "isWifiScanningAllowed",
                null,
                "false")) {
            return true;
        }
        Log.i("WifiDevicePolicyManager", "isAllowedWifiScanning false");
        return false;
    }
}
