package com.samsung.android.settings.bluetooth.lebroadcast;

import java.util.concurrent.ThreadFactory;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class SecBluetoothLeBroadcastSourceSetting$$ExternalSyntheticLambda1
        implements ThreadFactory {
    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(Runnable runnable) {
        boolean z = SecBluetoothLeBroadcastSourceSetting.DEBUG;
        return new Thread(runnable, "SecBluetoothBroadcastSource");
    }
}
