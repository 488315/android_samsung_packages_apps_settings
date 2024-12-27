package com.samsung.android.settings.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;
import com.android.settings.R;
import com.android.settingslib.bluetooth.BluetoothCallback;

import com.samsung.android.settings.Rune;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BluetoothOffDialog extends AlertActivity
        implements DialogInterface.OnClickListener, BluetoothCallback {
    public BluetoothAdapter adapter;
    public Context mContext;
    public boolean mReceiverRegistered = false;
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.bluetooth.BluetoothOffDialog.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (action == null) {
                        Log.d(
                                "BluetoothOffDialog",
                                "onReceive :: Intent.getAction() is return null");
                        return;
                    }
                    Log.d("BluetoothOffDialog", "onReceive :: getAction = ".concat(action));
                    if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(action)
                            && intent.getIntExtra(
                                            "android.bluetooth.adapter.extra.STATE",
                                            Integer.MIN_VALUE)
                                    == 10) {
                        BluetoothOffDialog.this.finish();
                    }
                }
            };

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i != -1) {
            return;
        }
        this.adapter.disable();
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getApplicationContext();
        this.adapter = BluetoothAdapter.getDefaultAdapter();
        Settings.Global.putInt(this.mContext.getContentResolver(), "bluetooth_dial_for_btoff", 1);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        registerReceiver(this.mReceiver, intentFilter);
        this.mReceiverRegistered = true;
        AlertController.AlertParams alertParams = ((AlertActivity) this).mAlertParams;
        alertParams.mTitle = getText(R.string.sec_bluetooth_off_quickshare_title);
        if (Rune.isChinaModel()) {
            alertParams.mMessage = getText(R.string.sec_bluetooth_off_quickshare_message_chn);
        } else {
            alertParams.mMessage = getText(R.string.sec_bluetooth_off_quickshare_message);
        }
        alertParams.mPositiveButtonText = getText(R.string.turn_off_button);
        alertParams.mNegativeButtonText = getText(R.string.common_cancel);
        alertParams.mPositiveButtonListener = this;
        setupAlert();
    }

    public final void onDestroy() {
        if (this.mReceiverRegistered) {
            unregisterReceiver(this.mReceiver);
            this.mReceiverRegistered = false;
        }
        super.onDestroy();
    }
}
