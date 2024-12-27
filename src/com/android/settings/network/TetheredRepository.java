package com.android.settings.network;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.net.TetheringManager;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class TetheredRepository {
    public final BluetoothAdapter adapter;
    public final Context context;
    public final TetheringManager tetheringManager;

    public TetheredRepository(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        Object systemService = context.getSystemService((Class<Object>) TetheringManager.class);
        Intrinsics.checkNotNull(systemService);
        this.tetheringManager = (TetheringManager) systemService;
        Object systemService2 = context.getSystemService((Class<Object>) BluetoothManager.class);
        Intrinsics.checkNotNull(systemService2);
        this.adapter = ((BluetoothManager) systemService2).getAdapter();
    }
}
