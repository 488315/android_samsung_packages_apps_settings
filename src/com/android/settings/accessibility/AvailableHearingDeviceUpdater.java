package com.android.settings.accessibility;

import com.android.settings.bluetooth.AvailableMediaBluetoothDeviceUpdater;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AvailableHearingDeviceUpdater extends AvailableMediaBluetoothDeviceUpdater {
    @Override // com.android.settings.bluetooth.AvailableMediaBluetoothDeviceUpdater,
              // com.android.settings.bluetooth.BluetoothDeviceUpdater
    public final boolean isFilterMatched(CachedBluetoothDevice cachedBluetoothDevice) {
        return cachedBluetoothDevice.isHearingAidDevice()
                && isDeviceConnected(cachedBluetoothDevice)
                && isDeviceInCachedDevicesList(cachedBluetoothDevice);
    }
}
