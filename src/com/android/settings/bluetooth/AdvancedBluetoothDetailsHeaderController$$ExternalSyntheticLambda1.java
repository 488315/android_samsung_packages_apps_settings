package com.android.settings.bluetooth;

import android.bluetooth.BluetoothDevice;

import com.android.settingslib.bluetooth.CachedBluetoothDevice;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */
class AdvancedBluetoothDetailsHeaderController$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AdvancedBluetoothDetailsHeaderController f$0;

    public /* synthetic */ AdvancedBluetoothDetailsHeaderController$$ExternalSyntheticLambda1(
            AdvancedBluetoothDetailsHeaderController advancedBluetoothDetailsHeaderController,
            int i) {
        this.$r8$classId = i;
        this.f$0 = advancedBluetoothDetailsHeaderController;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        AdvancedBluetoothDetailsHeaderController advancedBluetoothDetailsHeaderController =
                this.f$0;
        switch (i) {
            case 0:
                advancedBluetoothDetailsHeaderController.lambda$registerBluetoothDevice$0(
                        (CachedBluetoothDevice) obj);
                break;
            default:
                advancedBluetoothDetailsHeaderController.lambda$unRegisterBluetoothDevice$2(
                        (BluetoothDevice) obj);
                break;
        }
    }
}
