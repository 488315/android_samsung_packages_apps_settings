package com.android.settings.bluetooth;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class BluetoothDetailsButtonsController$$ExternalSyntheticLambda0
        implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BluetoothDetailsButtonsController f$0;

    public /* synthetic */ BluetoothDetailsButtonsController$$ExternalSyntheticLambda0(
            BluetoothDetailsButtonsController bluetoothDetailsButtonsController, int i) {
        this.$r8$classId = i;
        this.f$0 = bluetoothDetailsButtonsController;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        BluetoothDetailsButtonsController bluetoothDetailsButtonsController = this.f$0;
        switch (i) {
            case 0:
                bluetoothDetailsButtonsController.mMetricsFeatureProvider.action(
                        ((BluetoothDetailsController) bluetoothDetailsButtonsController).mContext,
                        868,
                        new Pair[0]);
                bluetoothDetailsButtonsController.mCachedDevice.disconnect();
                break;
            case 1:
                bluetoothDetailsButtonsController.mMetricsFeatureProvider.action(
                        ((BluetoothDetailsController) bluetoothDetailsButtonsController).mContext,
                        867,
                        new Pair[0]);
                bluetoothDetailsButtonsController.mCachedDevice.connect$1();
                break;
            default:
                String address =
                        bluetoothDetailsButtonsController.mCachedDevice.mDevice.getAddress();
                Bundle bundle = new Bundle(1);
                bundle.putString("device_address", address);
                ForgetDeviceDialogFragment forgetDeviceDialogFragment =
                        new ForgetDeviceDialogFragment();
                forgetDeviceDialogFragment.setArguments(bundle);
                forgetDeviceDialogFragment.show(
                        bluetoothDetailsButtonsController.mFragment.getFragmentManager(),
                        "ForgetBluetoothDevice");
                break;
        }
    }
}
