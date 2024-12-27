package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothLeBroadcastReceiveState;

import com.samsung.android.bluetooth.SemBluetoothCastDevice;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class BluetoothUtils$$ExternalSyntheticLambda1 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return BluetoothUtils.isConnected((BluetoothLeBroadcastReceiveState) obj);
            case 1:
                return ((SemBluetoothCastDevice) obj).getLocalDeviceRole() == 1;
            case 2:
                return ((SemBluetoothCastDevice) obj).getAddress() != null;
            case 3:
                return ((SemBluetoothCastDevice) obj).getLocalDeviceRole() == 2;
            case 4:
                return ((Long) obj).longValue() != 0;
            default:
                return ((SemBluetoothCastDevice) obj).getAddress() != null;
        }
    }
}
