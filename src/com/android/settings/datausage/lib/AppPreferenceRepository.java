package com.android.settings.datausage.lib;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.IconDrawableFactory;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppPreferenceRepository {
    public final Context context;
    public final IconDrawableFactory iconDrawableFactory;
    public final PackageManager packageManager;

    public AppPreferenceRepository(Context context) {
        IconDrawableFactory newInstance = IconDrawableFactory.newInstance(context);
        Intrinsics.checkNotNullExpressionValue(newInstance, "newInstance(...)");
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.iconDrawableFactory = newInstance;
        this.packageManager = context.getPackageManager();
    }
}
