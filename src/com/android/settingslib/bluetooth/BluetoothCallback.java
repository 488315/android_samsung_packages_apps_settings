package com.android.settingslib.bluetooth;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public interface BluetoothCallback {
    default void onAudioModeChanged() {}

    default void onAutoOnStateChanged(int i) {}

    default void onBluetoothStateChanged(int i) {}

    default void onDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {}

    default void onDeviceDeleted(CachedBluetoothDevice cachedBluetoothDevice) {}

    default void onScanningStateChanged(boolean z) {}

    default void onAclConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    default void onActiveDeviceChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    default void onConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    default void onDeviceBondStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    default void onProfileConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i, int i2) {}
}
