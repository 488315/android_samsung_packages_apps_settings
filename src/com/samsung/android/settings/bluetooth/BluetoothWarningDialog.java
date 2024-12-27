package com.samsung.android.settings.bluetooth;

import android.app.StatusBarManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDump;
import android.bluetooth.BluetoothManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;
import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BluetoothWarningDialog extends AlertActivity
        implements DialogInterface.OnClickListener {
    public boolean isClickedPositive = false;
    public BluetoothAdapter mBtAdapter;
    public Intent mDialogIntent;

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i != -1) {
            return;
        }
        this.isClickedPositive = true;
        this.mBtAdapter.enable();
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("BluetoothWarningDialog", "onCreate()");
        this.mDialogIntent = getIntent();
        if ((getApplicationContext().getResources().getConfiguration().uiMode & 48) == 32) {
            setTheme(4);
        } else {
            setTheme(5);
        }
        BluetoothManager bluetoothManager =
                (BluetoothManager) getApplicationContext().getSystemService(BluetoothManager.class);
        if (bluetoothManager == null) {
            Log.e("BluetoothWarningDialog", "onCreate: BluetoothManager not supported by system");
        } else {
            BluetoothAdapter adapter = bluetoothManager.getAdapter();
            this.mBtAdapter = adapter;
            if (adapter == null) {
                Log.e(
                        "BluetoothWarningDialog",
                        "onCreate: BluetoothAdapter not supported by system");
            }
        }
        ((StatusBarManager) getSystemService("statusbar")).collapsePanels();
        getWindow().setGravity(80);
        String stringExtra = this.mDialogIntent.getStringExtra("package_name");
        ((AlertActivity) this).mAlertParams.mTitle =
                getString(R.string.bluetooth_permission_request);
        ((AlertActivity) this).mAlertParams.mMessage =
                getString(
                        R.string.bluetooth_china_popup_message,
                        new Object[] {stringExtra, getString(R.string.bluetooth_settings)});
        ((AlertActivity) this)
                .mAlert.setButton(
                        -1,
                        (CharSequence) null,
                        (DialogInterface.OnClickListener) null,
                        (Message) null);
        ((AlertActivity) this)
                .mAlert.setButton(
                        -2,
                        (CharSequence) null,
                        (DialogInterface.OnClickListener) null,
                        (Message) null);
        ((AlertActivity) this).mAlertParams.mPositiveButtonText = getString(R.string.allow);
        AlertController.AlertParams alertParams = ((AlertActivity) this).mAlertParams;
        alertParams.mPositiveButtonListener = this;
        alertParams.mNegativeButtonText = getString(R.string.deny);
        ((AlertActivity) this).mAlertParams.mNegativeButtonListener = this;
        setupAlert();
    }

    public final void onDestroy() {
        super.onDestroy();
        if (this.isClickedPositive) {
            return;
        }
        int state = this.mBtAdapter.getState();
        BluetoothDump.BtLog("BluetoothWarningDialog -- enable(), China popup state : cancel");
        if (state == 11 || state == 12) {
            return;
        }
        try {
            getApplicationContext()
                    .sendBroadcast(
                            new Intent("com.samsung.settings.CHINA_NAL_SECURITY_SCAN_CANCEL"));
        } catch (Exception e) {
            Log.e("BluetoothWarningDialog", ApnSettings.MVNO_NONE, e);
        }
    }

    public final void onPause() {
        super.onPause();
    }

    public final void onResume() {
        super.onResume();
    }
}
