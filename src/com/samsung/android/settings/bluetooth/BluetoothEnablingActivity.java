package com.samsung.android.settings.bluetooth;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.internal.app.AlertActivity;
import com.android.settings.R;
import com.android.settings.bluetooth.Utils;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BluetoothEnablingActivity extends AlertActivity {
    public boolean mRegistered = false;
    public AlertDialog alertDialog = null;
    public final AnonymousClass1 mTimeoutHandler =
            new Handler() { // from class:
                            // com.samsung.android.settings.bluetooth.BluetoothEnablingActivity.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    Preference$$ExternalSyntheticOutline0.m(
                            new StringBuilder("handleMessage: msg.what = "),
                            message.what,
                            "BluetoothEnablingActivity");
                    int i = message.what;
                    if (i == 0 || i == 1) {
                        BluetoothEnablingActivity.this.finish();
                    }
                }
            };
    public final AnonymousClass2 mBluetoothReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.bluetooth.BluetoothEnablingActivity.2
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (action.equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                        int intExtra =
                                intent.getIntExtra(
                                        "android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE);
                        Log.d(
                                "BluetoothEnablingActivity",
                                "onReceive :: action = " + action + ", state = " + intExtra);
                        if (intExtra == 10 || intExtra == 12) {
                            removeMessages(0);
                            AnonymousClass1 anonymousClass1 =
                                    BluetoothEnablingActivity.this.mTimeoutHandler;
                            anonymousClass1.sendMessageDelayed(
                                    anonymousClass1.obtainMessage(1), 500L);
                        }
                    }
                }
            };

    /* JADX WARN: Multi-variable type inference failed */
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(this, Utils.mOnInitCallback);
        if (localBluetoothManager == null) {
            Log.e("BluetoothEnablingActivity", "Bluetooth is not supported on this device");
            return;
        }
        if (localBluetoothManager.mLocalAdapter.mAdapter.isEnabled()) {
            finish();
            return;
        }
        registerReceiver(
                this.mBluetoothReceiver,
                new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
        this.mRegistered = true;
        Log.d("BluetoothEnablingActivity", "showEnablingDialog ::");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.sec_bt_enabling_progress);
        AlertDialog create = builder.create();
        this.alertDialog = create;
        create.setTitle(R.string.enabling_progress_title);
        this.alertDialog.show();
        ((TextView) this.alertDialog.findViewById(R.id.progress_info))
                .setText(getString(R.string.enabling_progress_content));
        AnonymousClass1 anonymousClass1 = this.mTimeoutHandler;
        anonymousClass1.sendMessageDelayed(anonymousClass1.obtainMessage(0), 20000L);
    }

    public final void onDestroy() {
        super.onDestroy();
        Log.d("BluetoothEnablingActivity", "onDestroy ::");
        AlertDialog alertDialog = this.alertDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        if (this.mRegistered) {
            unregisterReceiver(this.mBluetoothReceiver);
        }
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return true;
        }
        Log.d("BluetoothEnablingActivity", "onKeyDown() called; Key: back key");
        removeMessages(0);
        finish();
        return true;
    }
}
