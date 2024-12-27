package com.android.settingslib.bluetooth;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class CsipDeviceManager$$ExternalSyntheticLambda1
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ CsipDeviceManager$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((CachedBluetoothDevice) obj)
                        .getConnectableProfiles().stream()
                                .anyMatch(new CsipDeviceManager$$ExternalSyntheticLambda1(2));
            case 1:
                return ((CachedBluetoothDevice) obj)
                        .getConnectableProfiles().stream()
                                .anyMatch(new CsipDeviceManager$$ExternalSyntheticLambda1(3));
            case 2:
                return ((LocalBluetoothProfile) obj) instanceof LeAudioProfile;
            default:
                LocalBluetoothProfile localBluetoothProfile = (LocalBluetoothProfile) obj;
                return (localBluetoothProfile instanceof A2dpProfile)
                        || (localBluetoothProfile instanceof HeadsetProfile);
        }
    }
}
