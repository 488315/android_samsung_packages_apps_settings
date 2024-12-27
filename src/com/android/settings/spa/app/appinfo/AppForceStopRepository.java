package com.android.settings.spa.app.appinfo;

import android.content.Context;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppForceStopRepository {
    public final AppButtonRepository appButtonRepository;
    public final Context context;
    public final PackageInfoPresenter packageInfoPresenter;

    public AppForceStopRepository(PackageInfoPresenter packageInfoPresenter) {
        AppButtonRepository appButtonRepository =
                new AppButtonRepository(packageInfoPresenter.context);
        Intrinsics.checkNotNullParameter(packageInfoPresenter, "packageInfoPresenter");
        this.packageInfoPresenter = packageInfoPresenter;
        this.appButtonRepository = appButtonRepository;
        this.context = packageInfoPresenter.context;
    }
}
