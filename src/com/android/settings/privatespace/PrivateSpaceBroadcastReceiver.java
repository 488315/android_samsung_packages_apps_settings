package com.android.settings.privatespace;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.multiuser.Flags;
import android.os.UserManager;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PrivateSpaceBroadcastReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (Flags.enablePrivateSpaceFeatures() && Flags.blockPrivateSpaceCreation()) {
            Log.d("PrivateSpaceBroadcastReceiver", "Received Intent: " + intent.getAction());
            int i =
                    (PrivateSpaceMaintainer.getInstance(context).doesPrivateSpaceExist()
                                    || ((UserManager) context.getSystemService(UserManager.class))
                                            .canAddPrivateProfile())
                            ? 1
                            : 2;
            ComponentName componentName =
                    new ComponentName(context, (Class<?>) PrivateSpaceAuthenticationActivity.class);
            Log.d(
                    "PrivateSpaceBroadcastReceiver",
                    "Setting component " + componentName + " state: " + i);
            context.getPackageManager().setComponentEnabledSetting(componentName, i, 1);
        }
    }
}
