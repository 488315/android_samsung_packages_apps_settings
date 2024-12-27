package com.android.settings.system;

import android.content.Context;
import android.content.pm.PackageManager;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SystemUpdateRepository {
    public final PackageManager packageManager;

    public SystemUpdateRepository(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.packageManager = context.getPackageManager();
    }
}
