package com.android.settings.password;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;

import com.android.settingslib.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class PasswordUtils extends Utils {
    public static void crashCallingApplication(IBinder iBinder) {
        IActivityManager service = ActivityManager.getService();
        try {
            int launchedFromUid = service.getLaunchedFromUid(iBinder);
            service.crashApplicationWithType(
                    launchedFromUid,
                    -1,
                    getCallingAppPackageName(iBinder),
                    UserHandle.getUserId(launchedFromUid),
                    "Must have permission android.permission.REQUEST_PASSWORD_COMPLEXITY to use"
                        + " extra android.app.extra.PASSWORD_COMPLEXITY",
                    false,
                    4);
        } catch (RemoteException e) {
            Log.v("Settings", "Could not talk to activity manager.", e);
        }
    }

    public static String getCallingAppPackageName(IBinder iBinder) {
        try {
            return ActivityManager.getService().getLaunchedFromPackage(iBinder);
        } catch (RemoteException e) {
            Log.v("Settings", "Could not talk to activity manager.", e);
            return null;
        }
    }

    public static boolean isCallingAppPermitted(Context context, IBinder iBinder) {
        try {
            return context.checkPermission(
                            "android.permission.REQUEST_PASSWORD_COMPLEXITY",
                            -1,
                            ActivityManager.getService().getLaunchedFromUid(iBinder))
                    == 0;
        } catch (RemoteException e) {
            Log.v("Settings", "Could not talk to activity manager.", e);
            return false;
        }
    }
}
