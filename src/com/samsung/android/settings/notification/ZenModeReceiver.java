package com.samsung.android.settings.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.samsung.android.settings.logging.SALogging;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ZenModeReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if ("com.samsung.android.settings.notification.zen.salogging.NOTIFICATION_DND_SA_LOGGING"
                .equals(intent.getAction())) {
            int i = Settings.Secure.getInt(context.getContentResolver(), "zen_duration", 0);
            HashMap hashMap = new HashMap();
            if (i == -1) {
                hashMap.put("NSTE0301", "until next alarm");
            } else if (i != 0) {
                hashMap.put("NSTE0301", String.valueOf(i));
            } else {
                hashMap.put("NSTE0301", "until I turn it off");
            }
            SALogging.insertSALog(String.valueOf(36031), "NSTE0301", hashMap, 0);
        }
    }
}
