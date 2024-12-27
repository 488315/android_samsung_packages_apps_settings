package com.android.settings.bluetooth;

import android.widget.Toast;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */
class BluetoothDetailsHearingAidsPresetsController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BluetoothDetailsHearingAidsPresetsController f$0;

    public /* synthetic */ BluetoothDetailsHearingAidsPresetsController$$ExternalSyntheticLambda0(
            BluetoothDetailsHearingAidsPresetsController
                    bluetoothDetailsHearingAidsPresetsController,
            int i) {
        this.$r8$classId = i;
        this.f$0 = bluetoothDetailsHearingAidsPresetsController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        BluetoothDetailsHearingAidsPresetsController bluetoothDetailsHearingAidsPresetsController =
                this.f$0;
        switch (i) {
            case 0:
                bluetoothDetailsHearingAidsPresetsController.refresh();
                break;
            case 1:
                bluetoothDetailsHearingAidsPresetsController.refresh();
                Toast.makeText(
                                ((BluetoothDetailsController)
                                                bluetoothDetailsHearingAidsPresetsController)
                                        .mContext,
                                R.string.bluetooth_hearing_aids_presets_error,
                                0)
                        .show();
                break;
            case 2:
                bluetoothDetailsHearingAidsPresetsController.refresh();
                Toast.makeText(
                                ((BluetoothDetailsController)
                                                bluetoothDetailsHearingAidsPresetsController)
                                        .mContext,
                                R.string.bluetooth_hearing_aids_presets_error,
                                0)
                        .show();
                break;
            default:
                bluetoothDetailsHearingAidsPresetsController.refresh();
                Toast.makeText(
                                ((BluetoothDetailsController)
                                                bluetoothDetailsHearingAidsPresetsController)
                                        .mContext,
                                R.string.bluetooth_hearing_aids_presets_error,
                                0)
                        .show();
                break;
        }
    }
}
