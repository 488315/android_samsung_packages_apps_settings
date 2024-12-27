package com.samsung.android.settings.bluetooth.lebroadcast;

import android.bluetooth.BluetoothLeBroadcastReceiveState;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SecBluetoothLeBroadcastUtils {
    public static boolean isBisSync(
            BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState) {
        if (bluetoothLeBroadcastReceiveState == null) {
            return false;
        }
        for (int i = 0; i < bluetoothLeBroadcastReceiveState.getNumSubgroups(); i++) {
            Long l = (Long) bluetoothLeBroadcastReceiveState.getBisSyncState().get(i);
            if (l.longValue() != 0 && l.longValue() != 4294967295L) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPaSyncOrBisSync(
            BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState) {
        return (bluetoothLeBroadcastReceiveState != null
                        && bluetoothLeBroadcastReceiveState.getPaSyncState() == 2)
                || isBisSync(bluetoothLeBroadcastReceiveState);
    }
}
