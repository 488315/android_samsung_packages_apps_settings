package com.samsung.android.settingslib.bluetooth;

import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public interface SemBluetoothCallback {
    void onProfileStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice,
            LocalBluetoothProfile localBluetoothProfile,
            int i,
            int i2);

    void onResourceUpdated();

    void onSyncDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice);
}
