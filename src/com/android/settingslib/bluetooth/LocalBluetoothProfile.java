package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public interface LocalBluetoothProfile {
    boolean accessProfileEnabled();

    int getConnectionStatus(BluetoothDevice bluetoothDevice);

    int getDrawableResource(BluetoothClass bluetoothClass);

    int getNameResource(BluetoothDevice bluetoothDevice);

    int getOrdinal();

    int getProfileId();

    boolean isEnabled(BluetoothDevice bluetoothDevice);

    boolean isProfileReady();

    boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z);
}
