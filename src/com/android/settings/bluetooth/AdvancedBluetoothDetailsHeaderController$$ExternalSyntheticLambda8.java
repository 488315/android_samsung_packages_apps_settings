package com.android.settings.bluetooth;

import android.bluetooth.BluetoothDevice;

import com.google.common.base.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */
class AdvancedBluetoothDetailsHeaderController$$ExternalSyntheticLambda8 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AdvancedBluetoothDetailsHeaderController$$ExternalSyntheticLambda8(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // com.google.common.base.Supplier
    public final Object get() {
        String lambda$refresh$3;
        Boolean lambda$refresh$4;
        Boolean lambda$refresh$5;
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                return Integer.valueOf(((BluetoothDevice) obj).getBatteryLevel());
            case 1:
                lambda$refresh$3 =
                        ((AdvancedBluetoothDetailsHeaderController) obj).lambda$refresh$3();
                return lambda$refresh$3;
            case 2:
                lambda$refresh$4 =
                        ((AdvancedBluetoothDetailsHeaderController) obj).lambda$refresh$4();
                return lambda$refresh$4;
            default:
                lambda$refresh$5 =
                        ((AdvancedBluetoothDetailsHeaderController) obj).lambda$refresh$5();
                return lambda$refresh$5;
        }
    }
}
