package com.android.settings.development;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class Enable16KBootReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (("android.intent.action.BOOT_COMPLETED".equals(action)
                        || "com.android.settings.development.NOTIFICATION_DISMISSED".equals(action))
                && Enable16kUtils.isPageAgnosticModeOn(context)) {
            Intent intent2 = new Intent(context, (Class<?>) PageAgnosticNotificationService.class);
            intent2.setAction(action);
            context.startServiceAsUser(intent2, UserHandle.SYSTEM);
        }
    }
}
