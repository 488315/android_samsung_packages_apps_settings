package com.samsung.android.settings.connection.ethernet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EthernetStatetReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if ("samsung.net.ethernet.ETH_STATE_CHANGED".equals(intent.getAction())) {
            String stringExtra = intent.getStringExtra("eth_state");
            AbsAdapter$$ExternalSyntheticOutline0.m(
                    "EthernetStatetReceiver ...", stringExtra, "EthernetStatetReceiver");
            if (stringExtra != null) {
                Settings.System.putStringForUser(
                        context.getContentResolver(), "ethernet_state", stringExtra, 0);
            }
        }
    }
}
