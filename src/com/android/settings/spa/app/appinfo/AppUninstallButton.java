package com.android.settings.spa.app.appinfo;

import android.content.Context;
import android.os.UserManager;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppUninstallButton {
    public final AppButtonRepository appButtonRepository;
    public final Context context;
    public final PackageInfoPresenter packageInfoPresenter;
    public final UserManager userManager;

    public AppUninstallButton(PackageInfoPresenter packageInfoPresenter) {
        Intrinsics.checkNotNullParameter(packageInfoPresenter, "packageInfoPresenter");
        this.packageInfoPresenter = packageInfoPresenter;
        Context context = packageInfoPresenter.context;
        this.context = context;
        this.appButtonRepository = new AppButtonRepository(context);
        Object systemService = context.getSystemService((Class<Object>) UserManager.class);
        Intrinsics.checkNotNull(systemService);
        this.userManager = (UserManager) systemService;
    }
}
