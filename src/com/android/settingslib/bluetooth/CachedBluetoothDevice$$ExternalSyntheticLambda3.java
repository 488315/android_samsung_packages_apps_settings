package com.android.settingslib.bluetooth;

import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class CachedBluetoothDevice$$ExternalSyntheticLambda3
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return Integer.valueOf(((CachedBluetoothDevice) obj).mDevice.getBatteryLevel());
    }
}
