package com.android.settings.bugreporthandler;

import android.R;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BugReportHandlerUtil {
    public static List getBugReportHandlerAppReceivers(Context context, int i, String str) {
        Intent intent = new Intent("com.android.internal.intent.action.BUGREPORT_REQUESTED");
        intent.setPackage(str);
        return context.getPackageManager().queryBroadcastReceiversAsUser(intent, 1048576, i);
    }

    public static Pair getCurrentBugReportHandlerAppAndUser(Context context) {
        String string =
                Settings.Secure.getString(
                        context.getContentResolver(), "custom_bugreport_handler_app");
        int i =
                Settings.Secure.getInt(
                        context.getContentResolver(), "custom_bugreport_handler_user", -10000);
        boolean z = true;
        boolean z2 = false;
        if (!isBugreportAllowlistedApp(string)) {
            string = context.getResources().getString(R.string.date_picker_decrement_year_button);
            i = context.getUserId();
        } else if (getBugReportHandlerAppReceivers(context, i, string).isEmpty()) {
            string = context.getResources().getString(R.string.date_picker_decrement_year_button);
            i = context.getUserId();
            z2 = true;
        }
        if (!isBugreportAllowlistedApp(string)
                || getBugReportHandlerAppReceivers(context, i, string).isEmpty()) {
            i = context.getUserId();
            string = "com.android.shell";
        } else {
            z = z2;
        }
        if (z) {
            Settings.Secure.putString(
                    context.getContentResolver(), "custom_bugreport_handler_app", string);
            Settings.Secure.putInt(
                    context.getContentResolver(), "custom_bugreport_handler_user", i);
        }
        return Pair.create(string, Integer.valueOf(i));
    }

    public static boolean isBugreportAllowlistedApp(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            return ActivityManager.getService().getBugreportWhitelistedPackages().contains(str);
        } catch (RemoteException e) {
            Log.e("BugReportHandlerUtil", "Failed to get bugreportAllowlistedPackages:", e);
            return false;
        }
    }
}
