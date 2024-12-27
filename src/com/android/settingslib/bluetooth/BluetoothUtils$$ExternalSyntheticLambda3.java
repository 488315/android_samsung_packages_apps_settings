package com.android.settingslib.bluetooth;

import android.util.Log;

import com.samsung.android.bluetooth.SemBluetoothAudioCast;
import com.samsung.android.bluetooth.SemBluetoothCastDevice;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.AudioCastProfile;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class BluetoothUtils$$ExternalSyntheticLambda3 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AudioCastProfile f$0;

    public /* synthetic */ BluetoothUtils$$ExternalSyntheticLambda3(
            AudioCastProfile audioCastProfile, int i) {
        this.$r8$classId = i;
        this.f$0 = audioCastProfile;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        AudioCastProfile audioCastProfile = this.f$0;
        SemBluetoothCastDevice semBluetoothCastDevice = (SemBluetoothCastDevice) obj;
        switch (i) {
            case 0:
                Log.d(audioCastProfile.TAG, "getConnectionState");
                SemBluetoothAudioCast semBluetoothAudioCast = audioCastProfile.mService;
                if ((semBluetoothAudioCast == null
                                ? 0
                                : semBluetoothAudioCast.getConnectionState(semBluetoothCastDevice))
                        == 2) {}
                break;
            default:
                Log.d(audioCastProfile.TAG, "getConnectionState");
                SemBluetoothAudioCast semBluetoothAudioCast2 = audioCastProfile.mService;
                if ((semBluetoothAudioCast2 == null
                                ? 0
                                : semBluetoothAudioCast2.getConnectionState(semBluetoothCastDevice))
                        == 2) {}
                break;
        }
        return false;
    }
}
