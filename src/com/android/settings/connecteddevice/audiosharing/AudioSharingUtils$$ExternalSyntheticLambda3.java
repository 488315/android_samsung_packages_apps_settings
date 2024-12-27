package com.android.settings.connecteddevice.audiosharing;

import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;

import java.sql.Timestamp;
import java.util.Comparator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AudioSharingUtils$$ExternalSyntheticLambda3
        implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) obj;
        CachedBluetoothDevice cachedBluetoothDevice2 = (CachedBluetoothDevice) obj2;
        boolean z = BluetoothUtils.DEBUG;
        int i =
                (cachedBluetoothDevice2.isActiveDevice(22) ? 1 : 0)
                        - (cachedBluetoothDevice.isActiveDevice(22) ? 1 : 0);
        if (i != 0) {
            return i;
        }
        int i2 =
                (cachedBluetoothDevice2.mBondState == 12 ? 1 : 0)
                        - (cachedBluetoothDevice.mBondState == 12 ? 1 : 0);
        if (i2 != 0) {
            return i2;
        }
        Timestamp timestamp = cachedBluetoothDevice2.mBondTimestamp;
        int i3 = timestamp != null ? 1 : 0;
        Timestamp timestamp2 = cachedBluetoothDevice.mBondTimestamp;
        int i4 = i3 - (timestamp2 != null ? 1 : 0);
        if (i4 != 0) {
            return i4;
        }
        return timestamp2 != null
                ? timestamp2.compareTo(timestamp)
                : cachedBluetoothDevice.getName().compareTo(cachedBluetoothDevice2.getName());
    }
}
