package com.samsung.android.sdk.bixby2.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ApplicationTriggerReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        Log.i("ApplicationTriggerReceiver", "onReceived()");
        if (context != null) {
            context.unregisterReceiver(this);
            Log.i("ApplicationTriggerReceiver", "ApplicationTriggerReceiver unRegistered");
        }
    }
}
