package com.android.settings.connecteddevice.audiosharing;

import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LeAudioProfile;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AudioSharingUtils$$ExternalSyntheticLambda1
        implements Predicate {
    public final /* synthetic */ CachedBluetoothDevice f$0;

    public /* synthetic */ AudioSharingUtils$$ExternalSyntheticLambda1(
            CachedBluetoothDevice cachedBluetoothDevice) {
        this.f$0 = cachedBluetoothDevice;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        CachedBluetoothDevice cachedBluetoothDevice = this.f$0;
        LocalBluetoothProfile localBluetoothProfile = (LocalBluetoothProfile) obj;
        if (localBluetoothProfile instanceof LeAudioProfile) {
            if (((LeAudioProfile) localBluetoothProfile).isEnabled(cachedBluetoothDevice.mDevice)) {
                return true;
            }
        }
        return false;
    }
}
