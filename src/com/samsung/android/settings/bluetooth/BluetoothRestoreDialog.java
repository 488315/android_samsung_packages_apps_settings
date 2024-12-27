package com.samsung.android.settings.bluetooth;

import android.app.StatusBarManager;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;
import com.android.settings.R;
import com.android.settings.accounts.ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.BluetoothCallback;

import com.samsung.android.settings.wifi.WifiTipsUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BluetoothRestoreDialog extends AlertActivity
        implements DialogInterface.OnClickListener, BluetoothCallback {
    public Context mContext;
    public String targetAddress;
    public boolean mReceiverRegistered = false;
    public final AnonymousClass1 mReceiver = new BroadcastReceiver() { // from class:
                // com.samsung.android.settings.bluetooth.BluetoothRestoreDialog.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    BluetoothDevice bluetoothDevice;
                    String action = intent.getAction();
                    if (action == null) {
                        Log.d(
                                "BluetoothRestoreDialog",
                                "onReceive: Intent.getAction() is return null");
                        return;
                    }
                    Log.d("BluetoothRestoreDialog", "onReceive: getAction = ".concat(action));
                    if (!"android.bluetooth.device.action.BOND_STATE_CHANGED".equals(action)) {
                        if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(action)
                                && intent.getIntExtra(
                                                "android.bluetooth.adapter.extra.STATE",
                                                Integer.MIN_VALUE)
                                        == 10) {
                            BluetoothRestoreDialog.this.finish();
                            return;
                        }
                        return;
                    }
                    if (intent.getIntExtra(
                                            "android.bluetooth.device.extra.BOND_STATE",
                                            Integer.MIN_VALUE)
                                    == 11
                            && (bluetoothDevice =
                                            (BluetoothDevice)
                                                    intent.getParcelableExtra(
                                                            "android.bluetooth.device.extra.DEVICE"))
                                    != null
                            && bluetoothDevice
                                    .getAddress()
                                    .equals(BluetoothRestoreDialog.this.targetAddress)) {
                        BluetoothRestoreDialog.this.finish();
                    }
                }
            };

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i != -1) {
            return;
        }
        Intent intent = new Intent();
        intent.setClassName(
                "com.samsung.android.net.wifi.wifiguider",
                "com.samsung.android.net.wifi.wifiguider.activity.bluetooth.BluetoothGuideActivity");
        intent.putExtra("disableReason", "CONNECTION_FAILURE");
        intent.setFlags(335544320);
        this.mContext.startActivity(intent);
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getApplicationContext();
        Intent intent = getIntent();
        if (intent != null) {
            this.targetAddress = intent.getStringExtra("cachedAddress");
        }
        if (!WifiTipsUtils.isSupportWifiTips(this.mContext)) {
            finish();
            return;
        }
        registerReceiver(
                this.mReceiver,
                ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0.m(
                        "android.bluetooth.adapter.action.STATE_CHANGED",
                        "android.bluetooth.device.action.BOND_STATE_CHANGED"));
        this.mReceiverRegistered = true;
        AlertController.AlertParams alertParams = ((AlertActivity) this).mAlertParams;
        alertParams.mTitle = getText(R.string.sec_bluetooth_restore_dialog_title);
        alertParams.mMessage = getText(R.string.sec_bluetooth_restore_dialog_message);
        alertParams.mPositiveButtonText =
                getText(R.string.sec_bluetooth_restore_dialog_positive_button);
        alertParams.mNegativeButtonText = getText(R.string.okay);
        alertParams.mPositiveButtonListener = this;
        setupAlert();
        StatusBarManager statusBarManager = (StatusBarManager) getSystemService("statusbar");
        if (statusBarManager != null) {
            statusBarManager.collapsePanels();
        }
    }

    public final void onDestroy() {
        if (this.mReceiverRegistered) {
            unregisterReceiver(this.mReceiver);
            this.mReceiverRegistered = false;
        }
        super.onDestroy();
    }
}
