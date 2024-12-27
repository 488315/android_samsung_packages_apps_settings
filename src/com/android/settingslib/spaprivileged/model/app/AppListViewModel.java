package com.android.settingslib.spaprivileged.model.app;

import android.app.Application;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppListViewModel extends AppListViewModelImpl {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppListViewModel(Application application) {
        super(application);
        Intrinsics.checkNotNullParameter(application, "application");
    }
}
