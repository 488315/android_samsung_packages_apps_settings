package com.samsung.android.settings.general;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.samsung.android.settings.Rune;
import com.samsung.android.util.SemLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class RetailModeResetReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (!Rune.isShopDemo(context)) {
            SemLog.d("RetailModeResetReceiver", "Retail mode is not enabled.");
            return;
        }
        SemLog.d("RetailModeResetReceiver", "onReceived, Action name = " + intent.getAction());
        if (action.equals("com.samsung.sea.rm.DEMO_RESET_STARTED")) {
            SemLog.d("RetailModeResetReceiver", "startResetSettings");
            ResetSettingsConfirm.resetSettingsInternal(context);
        }
    }
}
