package com.android.settingslib.bluetooth;

import android.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextThemeWrapper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class BluetoothUtils$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ Context f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ Runnable f$4;
    public final /* synthetic */ Runnable f$5;

    public /* synthetic */ BluetoothUtils$$ExternalSyntheticLambda6(
            Context context, int i, int i2, int i3, Runnable runnable, Runnable runnable2) {
        this.f$0 = context;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = i3;
        this.f$4 = runnable;
        this.f$5 = runnable2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Context context = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        int i3 = this.f$3;
        final Runnable runnable = this.f$4;
        final Runnable runnable2 = this.f$5;
        AlertDialog.Builder message =
                new AlertDialog.Builder(
                                new ContextThemeWrapper(
                                        context, R.style.Theme.DeviceDefault.Settings))
                        .setTitle(i)
                        .setMessage(i2);
        final int i4 = 0;
        AlertDialog.Builder positiveButton =
                message.setPositiveButton(
                        i3,
                        new DialogInterface
                                .OnClickListener() { // from class:
                                                     // com.android.settingslib.bluetooth.BluetoothUtils$$ExternalSyntheticLambda11
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i5) {
                                int i6 = i4;
                                Runnable runnable3 = runnable;
                                switch (i6) {
                                    case 0:
                                        runnable3.run();
                                        break;
                                    default:
                                        runnable3.run();
                                        dialogInterface.dismiss();
                                        break;
                                }
                            }
                        });
        final int i5 = 1;
        positiveButton
                .setNegativeButton(
                        R.string.cancel,
                        new DialogInterface
                                .OnClickListener() { // from class:
                                                     // com.android.settingslib.bluetooth.BluetoothUtils$$ExternalSyntheticLambda11
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i52) {
                                int i6 = i5;
                                Runnable runnable3 = runnable2;
                                switch (i6) {
                                    case 0:
                                        runnable3.run();
                                        break;
                                    default:
                                        runnable3.run();
                                        dialogInterface.dismiss();
                                        break;
                                }
                            }
                        })
                .setOnCancelListener(
                        new DialogInterface
                                .OnCancelListener() { // from class:
                                                      // com.android.settingslib.bluetooth.BluetoothUtils$$ExternalSyntheticLambda13
                            @Override // android.content.DialogInterface.OnCancelListener
                            public final void onCancel(DialogInterface dialogInterface) {
                                runnable2.run();
                                dialogInterface.dismiss();
                            }
                        })
                .create()
                .show();
    }
}
