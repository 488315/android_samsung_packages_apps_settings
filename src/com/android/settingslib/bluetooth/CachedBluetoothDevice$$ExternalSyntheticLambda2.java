package com.android.settingslib.bluetooth;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class CachedBluetoothDevice$$ExternalSyntheticLambda2
        implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        CachedBluetoothDevice.Callback callback = (CachedBluetoothDevice.Callback) obj;
        Objects.requireNonNull(callback);
        ((Executor) obj2).execute(new CachedBluetoothDevice$$ExternalSyntheticLambda1(2, callback));
    }
}
