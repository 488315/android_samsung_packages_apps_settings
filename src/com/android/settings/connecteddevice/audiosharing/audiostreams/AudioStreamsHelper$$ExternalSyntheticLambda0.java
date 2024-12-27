package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.bluetooth.BluetoothDevice;

import com.android.settingslib.bluetooth.CachedBluetoothDevice;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AudioStreamsHelper$$ExternalSyntheticLambda0
        implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AudioStreamsHelper$$ExternalSyntheticLambda0(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                return ((AudioStreamsHelper) obj2)
                        .mLeBroadcastAssistant.getAllSources((BluetoothDevice) obj).stream();
            default:
                List list = (List) obj2;
                CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) obj;
                Stream concat =
                        Stream.concat(
                                Stream.of(cachedBluetoothDevice.mDevice),
                                cachedBluetoothDevice.mMemberDevices.stream()
                                        .map(new AudioStreamsHelper$$ExternalSyntheticLambda3(1)));
                Objects.requireNonNull(list);
                return concat.filter(new AudioStreamsHelper$$ExternalSyntheticLambda2(2, list))
                        .toList();
        }
    }
}
