package com.android.settings.accessibility;

import android.bluetooth.BluetoothDevice;

import com.android.settings.bluetooth.SavedBluetoothDeviceUpdater;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SavedHearingDeviceUpdater extends SavedBluetoothDeviceUpdater {
    @Override // com.android.settings.bluetooth.SavedBluetoothDeviceUpdater,
              // com.android.settings.bluetooth.BluetoothDeviceUpdater
    public final boolean isFilterMatched(CachedBluetoothDevice cachedBluetoothDevice) {
        BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
        return cachedBluetoothDevice.isHearingAidDevice()
                && bluetoothDevice.getBondState() == 12
                && !bluetoothDevice.isConnected()
                && isDeviceInCachedDevicesList(cachedBluetoothDevice);
    }
}
