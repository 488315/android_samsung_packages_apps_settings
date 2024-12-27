package com.android.settingslib.bluetooth;

import java.util.Objects;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class CachedBluetoothDevice$$ExternalSyntheticLambda4
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ CachedBluetoothDevice$$ExternalSyntheticLambda4(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((Integer) obj).intValue() > -1;
            case 1:
                return ((LocalBluetoothProfile) obj) instanceof HidProfile;
            case 2:
                return Objects.nonNull((CachedBluetoothDevice) obj);
            case 3:
                return ((CachedBluetoothDevice) obj).mDevice.isConnected();
            case 4:
                return ((Integer) obj).intValue() > -1;
            default:
                return ((LocalBluetoothProfile) obj) instanceof LeAudioProfile;
        }
    }
}
