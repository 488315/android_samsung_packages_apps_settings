package com.android.settings.network;

import android.content.Context;
import android.net.ConnectivityManager;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ConnectivityRepository {
    public final ConnectivityManager connectivityManager;

    public ConnectivityRepository(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Object systemService = context.getSystemService((Class<Object>) ConnectivityManager.class);
        Intrinsics.checkNotNull(systemService);
        this.connectivityManager = (ConnectivityManager) systemService;
    }
}
