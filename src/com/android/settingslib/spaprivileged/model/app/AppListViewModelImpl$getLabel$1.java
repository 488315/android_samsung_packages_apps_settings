package com.android.settingslib.spaprivileged.model.app;

import android.content.pm.ApplicationInfo;

import kotlin.jvm.internal.Intrinsics;

import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppListViewModelImpl$getLabel$1 implements Function {
    public final /* synthetic */ ApplicationInfo $app;
    public final /* synthetic */ AppListViewModelImpl this$0;

    public AppListViewModelImpl$getLabel$1(
            AppListViewModelImpl appListViewModelImpl, ApplicationInfo applicationInfo) {
        this.this$0 = appListViewModelImpl;
        this.$app = applicationInfo;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        String it = (String) obj;
        Intrinsics.checkNotNullParameter(it, "it");
        AppRepository appRepository = this.this$0.appRepository;
        ApplicationInfo app = this.$app;
        AppRepositoryImpl appRepositoryImpl = (AppRepositoryImpl) appRepository;
        appRepositoryImpl.getClass();
        Intrinsics.checkNotNullParameter(app, "app");
        return app.loadLabel(appRepositoryImpl.packageManager).toString();
    }
}
