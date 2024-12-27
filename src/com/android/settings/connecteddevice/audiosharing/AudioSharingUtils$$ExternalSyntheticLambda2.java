package com.android.settings.connecteddevice.audiosharing;

import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;

import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AudioSharingUtils$$ExternalSyntheticLambda2 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) obj;
        String name = cachedBluetoothDevice.getName();
        int groupId = AudioSharingUtils.getGroupId(cachedBluetoothDevice);
        boolean z = BluetoothUtils.DEBUG;
        return new AudioSharingDeviceItem(name, groupId, cachedBluetoothDevice.isActiveDevice(22));
    }
}
