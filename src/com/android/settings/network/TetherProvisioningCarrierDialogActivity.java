package com.android.settings.network;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TetherProvisioningCarrierDialogActivity extends Activity {
    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        new AlertDialog.Builder(this)
                .setTitle(R.string.wifi_tether_carrier_unsupport_dialog_title)
                .setMessage(R.string.wifi_tether_carrier_unsupport_dialog_content)
                .setCancelable(false)
                .setPositiveButton(
                        android.R.string.ok,
                        new DialogInterface
                                .OnClickListener() { // from class:
                                                     // com.android.settings.network.TetherProvisioningCarrierDialogActivity.1
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                TetherProvisioningCarrierDialogActivity.this.finish();
                            }
                        })
                .show();
    }
}
