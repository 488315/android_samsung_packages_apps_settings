package com.android.settings.spa.app.appinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppLocalePresenter {
    public final ApplicationInfo app;
    public final Context context;
    public final SafeFlow isAvailableFlow;
    public final PackageManager packageManager;
    public final SafeFlow summaryFlow;

    public AppLocalePresenter(Context context, ApplicationInfo app) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(app, "app");
        this.context = context;
        this.app = app;
        this.packageManager = context.getPackageManager();
        this.isAvailableFlow = new SafeFlow(new AppLocalePresenter$isAvailableFlow$1(this, null));
        this.summaryFlow = new SafeFlow(new AppLocalePresenter$summaryFlow$1(this, null));
    }
}
