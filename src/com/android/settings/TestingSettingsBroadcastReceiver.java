package com.android.settings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class TestingSettingsBroadcastReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return;
        }
        if (intent.getAction().equals("android.telephony.action.SECRET_CODE")
                || intent.getAction().equals("com.samsung.android.action.SECRET_CODE")) {
            Intent intent2 = new Intent("android.intent.action.MAIN");
            intent2.setClass(context, Settings.TestingSettingsActivity.class);
            intent2.setFlags(268435456);
            context.startActivity(intent2);
        }
    }
}
