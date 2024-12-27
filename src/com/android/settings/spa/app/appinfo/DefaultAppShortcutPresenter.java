package com.android.settings.spa.app.appinfo;

import android.app.role.RoleManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.ExecutorsKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.scheduling.DefaultIoScheduler;

import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DefaultAppShortcutPresenter {
    public final ApplicationInfo app;
    public final Context context;
    public final Executor executor;
    public final RoleManager roleManager;
    public final String roleName;
    public final Flow summaryFlow;

    public DefaultAppShortcutPresenter(Context context, String roleName, ApplicationInfo app) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(roleName, "roleName");
        Intrinsics.checkNotNullParameter(app, "app");
        this.context = context;
        this.roleName = roleName;
        this.app = app;
        Object systemService = context.getSystemService((Class<Object>) RoleManager.class);
        Intrinsics.checkNotNull(systemService);
        this.roleManager = (RoleManager) systemService;
        DefaultIoScheduler defaultIoScheduler = Dispatchers.IO;
        this.executor = ExecutorsKt.asExecutor(defaultIoScheduler);
        this.summaryFlow =
                FlowKt.flowOn(
                        new SafeFlow(new DefaultAppShortcutPresenter$summaryFlow$1(this, null)),
                        defaultIoScheduler);
    }
}
