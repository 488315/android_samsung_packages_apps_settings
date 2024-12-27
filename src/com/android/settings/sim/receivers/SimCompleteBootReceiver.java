package com.android.settings.sim.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.settings.sim.SimNotificationService;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SimCompleteBootReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (!"android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Log.e("SimCompleteBootReceiver", "Invalid broadcast received.");
        } else if (context.getSharedPreferences("sim_prefs", 0)
                .getBoolean("show_sim_settings_notification", false)) {
            SimNotificationService.scheduleSimNotification(context, 1);
        }
    }
}
