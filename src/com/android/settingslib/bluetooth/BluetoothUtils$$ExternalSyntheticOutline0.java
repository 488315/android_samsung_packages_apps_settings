package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothDevice;

import com.samsung.android.bluetooth.SmepTag;
import com.samsung.android.settingslib.bluetooth.smep.SppByteUtil;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract /* synthetic */ class BluetoothUtils$$ExternalSyntheticOutline0 {
    public static byte[] m(SmepTag smepTag, BluetoothDevice bluetoothDevice) {
        return bluetoothDevice.semGetMetadata(SppByteUtil.intToBytes(smepTag.getTag()));
    }
}
