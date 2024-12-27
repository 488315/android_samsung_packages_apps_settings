package com.android.settings.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AlwaysDiscoverable extends BroadcastReceiver {
    public final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    public final Context mContext;
    public final IntentFilter mIntentFilter;
    boolean mStarted;

    public AlwaysDiscoverable(Context context) {
        this.mContext = context;
        IntentFilter intentFilter = new IntentFilter();
        this.mIntentFilter = intentFilter;
        intentFilter.addAction("android.bluetooth.adapter.action.SCAN_MODE_CHANGED");
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (intent.getAction() == "android.bluetooth.adapter.action.SCAN_MODE_CHANGED"
                && this.mBluetoothAdapter.getScanMode() != 23) {
            this.mBluetoothAdapter.setScanMode(23);
        }
    }

    public final void start() {
        if (this.mStarted) {
            return;
        }
        this.mContext.registerReceiver(this, this.mIntentFilter, 2);
        this.mStarted = true;
        if (this.mBluetoothAdapter.getScanMode() != 23) {
            this.mBluetoothAdapter.setScanMode(23);
        }
    }

    public final void stop() {
        if (this.mStarted) {
            this.mContext.unregisterReceiver(this);
            this.mStarted = false;
            this.mBluetoothAdapter.setScanMode(21);
        }
    }
}
