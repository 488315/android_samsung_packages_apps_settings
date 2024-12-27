package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.bluetooth.BluetoothDevice;

import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import java.util.List;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AudioStreamsHelper$$ExternalSyntheticLambda2
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AudioStreamsHelper$$ExternalSyntheticLambda2(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                return AudioStreamsHelper.hasConnectedBroadcastSource(
                        (CachedBluetoothDevice) obj, (LocalBluetoothManager) obj2);
            case 1:
                return AudioStreamsHelper.hasConnectedBroadcastSource(
                        (CachedBluetoothDevice) obj, (LocalBluetoothManager) obj2);
            default:
                return ((List) obj2).contains((BluetoothDevice) obj);
        }
    }
}
