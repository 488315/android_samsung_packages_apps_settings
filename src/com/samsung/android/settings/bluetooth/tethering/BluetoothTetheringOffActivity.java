package com.samsung.android.settings.bluetooth.tethering;

import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemProperties;
import android.util.Log;

import com.android.internal.app.AlertActivity;
import com.android.settings.R;
import com.android.settingslib.bluetooth.BluetoothUtils;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BluetoothTetheringOffActivity extends AlertActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AlertDialog mAlert;
    public AlertDialog.Builder mAlertBuilder;

    static {
        SystemProperties.getBoolean("ro.product_ship", true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addSystemFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        setFinishOnTouchOutside(true);
        requestWindowFeature(1);
        final BluetoothDevice bluetoothDevice =
                (BluetoothDevice)
                        getIntent().getParcelableExtra("android.bluetooth.device.extra.DEVICE");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        this.mAlertBuilder = builder;
        builder.setTitle(R.string.bluetooth_tethering_turn_off_bluetooth_tethering_title);
        this.mAlertBuilder.setMessage(
                R.string.bluetooth_tethering_turn_off_bluetooth_tethering_message);
        this.mAlertBuilder.setPositiveButton(
                R.string.bluetooth_tethering_turn_off,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.bluetooth.tethering.BluetoothTetheringOffActivity$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        AlertActivity alertActivity = BluetoothTetheringOffActivity.this;
                        BluetoothDevice bluetoothDevice2 = bluetoothDevice;
                        int i2 = BluetoothTetheringOffActivity.$r8$clinit;
                        alertActivity.getClass();
                        BluetoothUtils.stopTethering(alertActivity);
                        Log.d("BluetoothTetheringOffActivity", "connect " + bluetoothDevice2);
                        bluetoothDevice2.connect();
                        alertActivity.finish();
                    }
                });
        this.mAlertBuilder.setNegativeButton(
                android.R.string.cancel,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.bluetooth.tethering.BluetoothTetheringOffActivity$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        BluetoothTetheringOffActivity bluetoothTetheringOffActivity =
                                BluetoothTetheringOffActivity.this;
                        int i2 = BluetoothTetheringOffActivity.$r8$clinit;
                        bluetoothTetheringOffActivity.finish();
                    }
                });
        this.mAlertBuilder.setOnCancelListener(
                new DialogInterface
                        .OnCancelListener() { // from class:
                                              // com.samsung.android.settings.bluetooth.tethering.BluetoothTetheringOffActivity$$ExternalSyntheticLambda2
                    @Override // android.content.DialogInterface.OnCancelListener
                    public final void onCancel(DialogInterface dialogInterface) {
                        BluetoothTetheringOffActivity bluetoothTetheringOffActivity =
                                BluetoothTetheringOffActivity.this;
                        int i = BluetoothTetheringOffActivity.$r8$clinit;
                        bluetoothTetheringOffActivity.finish();
                    }
                });
        getWindow().setGravity(80);
        AlertDialog create = this.mAlertBuilder.create();
        this.mAlert = create;
        create.show();
        closeSystemDialogs();
    }

    public final void onDestroy() {
        AlertDialog alertDialog = this.mAlert;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        super.onDestroy();
    }
}
