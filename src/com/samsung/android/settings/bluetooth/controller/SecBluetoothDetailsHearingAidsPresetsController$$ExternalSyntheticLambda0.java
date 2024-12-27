package com.samsung.android.settings.bluetooth.controller;

import android.widget.Toast;

import com.android.settings.R;
import com.android.settings.bluetooth.BluetoothDetailsController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */
class SecBluetoothDetailsHearingAidsPresetsController$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecBluetoothDetailsHearingAidsPresetsController f$0;

    public /* synthetic */
    SecBluetoothDetailsHearingAidsPresetsController$$ExternalSyntheticLambda0(
            SecBluetoothDetailsHearingAidsPresetsController
                    secBluetoothDetailsHearingAidsPresetsController,
            int i) {
        this.$r8$classId = i;
        this.f$0 = secBluetoothDetailsHearingAidsPresetsController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        SecBluetoothDetailsHearingAidsPresetsController
                secBluetoothDetailsHearingAidsPresetsController = this.f$0;
        switch (i) {
            case 0:
                secBluetoothDetailsHearingAidsPresetsController.refresh();
                Toast.makeText(
                                ((BluetoothDetailsController)
                                                secBluetoothDetailsHearingAidsPresetsController)
                                        .mContext,
                                R.string.sec_bluetooth_hearing_aids_presets_error,
                                0)
                        .show();
                break;
            case 1:
                secBluetoothDetailsHearingAidsPresetsController.refresh();
                break;
            case 2:
                secBluetoothDetailsHearingAidsPresetsController.refresh();
                Toast.makeText(
                                ((BluetoothDetailsController)
                                                secBluetoothDetailsHearingAidsPresetsController)
                                        .mContext,
                                R.string.sec_bluetooth_hearing_aids_presets_error,
                                0)
                        .show();
                break;
            default:
                secBluetoothDetailsHearingAidsPresetsController.refresh();
                Toast.makeText(
                                ((BluetoothDetailsController)
                                                secBluetoothDetailsHearingAidsPresetsController)
                                        .mContext,
                                R.string.sec_bluetooth_hearing_aids_presets_error,
                                0)
                        .show();
                break;
        }
    }
}
