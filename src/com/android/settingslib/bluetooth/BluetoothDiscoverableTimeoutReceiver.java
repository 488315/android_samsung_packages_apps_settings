package com.android.settingslib.bluetooth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BluetoothDiscoverableTimeoutReceiver extends BroadcastReceiver {
    public static final /* synthetic */ int $r8$clinit = 0;

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (intent.getAction() == null
                || !intent.getAction().equals("android.bluetooth.intent.DISCOVERABLE_TIMEOUT")) {
            return;
        }
        LocalBluetoothAdapter localBluetoothAdapter = LocalBluetoothAdapter.getInstance();
        if (localBluetoothAdapter == null || localBluetoothAdapter.mAdapter.getState() != 12) {
            Log.e("BluetoothDiscoverableTimeoutReceiver", "localBluetoothAdapter is NULL!!");
        } else {
            Log.d("BluetoothDiscoverableTimeoutReceiver", "Disable discoverable...");
            localBluetoothAdapter.mAdapter.setScanMode(21);
        }
    }
}
