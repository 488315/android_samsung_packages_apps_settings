package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.bluetooth.BluetoothLeAudioContentMetadata;
import android.bluetooth.BluetoothLeBroadcastSubgroup;

import com.android.settingslib.bluetooth.CachedBluetoothDevice;

import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AudioStreamsHelper$$ExternalSyntheticLambda3
        implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ AudioStreamsHelper$$ExternalSyntheticLambda3(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((BluetoothLeBroadcastSubgroup) obj).getContentMetadata().getProgramInfo();
            case 1:
                return ((CachedBluetoothDevice) obj).mDevice;
            default:
                return ((BluetoothLeAudioContentMetadata) obj).getProgramInfo();
        }
    }
}
