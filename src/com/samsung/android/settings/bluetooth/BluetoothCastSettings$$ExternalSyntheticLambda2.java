package com.samsung.android.settings.bluetooth;

import com.samsung.android.bluetooth.SemBluetoothCastDevice;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class BluetoothCastSettings$$ExternalSyntheticLambda2
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        boolean z = BluetoothCastSettings.LOG_DEBUG;
        return ((SemBluetoothCastDevice) obj).getAddress() != null;
    }
}
