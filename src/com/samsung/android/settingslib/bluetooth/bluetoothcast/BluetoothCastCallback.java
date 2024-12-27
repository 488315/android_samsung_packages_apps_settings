package com.samsung.android.settingslib.bluetooth.bluetoothcast;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public interface BluetoothCastCallback {
    void onCastDeviceAdded(CachedBluetoothCastDevice cachedBluetoothCastDevice);

    void onCastDeviceRemoved(CachedBluetoothCastDevice cachedBluetoothCastDevice);

    void onCastDiscoveryStateChanged(boolean z);

    void onCastProfileStateChanged(CachedBluetoothCastDevice cachedBluetoothCastDevice, int i);
}
