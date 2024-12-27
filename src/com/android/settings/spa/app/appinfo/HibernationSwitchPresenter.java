package com.android.settings.spa.app.appinfo;

import android.app.AppOpsManager;
import android.apphibernation.AppHibernationManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.permission.PermissionControllerManager;

import com.android.settingslib.spa.framework.compose.OverridableFlow;
import com.android.settingslib.spaprivileged.framework.common.ContextsKt;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.ExecutorsKt;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class HibernationSwitchPresenter {
    public final ApplicationInfo app;
    public final AppHibernationManager appHibernationManager;
    public final AppOpsManager appOpsManager;
    public final Executor executor;
    public final OverridableFlow isChecked;
    public final ChannelLimitedFlowMerge isCheckedFlow;
    public final SafeFlow isEligibleFlow;
    public final PermissionControllerManager permissionControllerManager;

    public HibernationSwitchPresenter(Context context, ApplicationInfo app) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(app, "app");
        this.app = app;
        this.appOpsManager = ContextsKt.getAppOpsManager(context);
        Object systemService =
                ContextsKt.asUser(context, ApplicationInfosKt.getUserHandle(app))
                        .getSystemService((Class<Object>) PermissionControllerManager.class);
        Intrinsics.checkNotNull(systemService);
        this.permissionControllerManager = (PermissionControllerManager) systemService;
        Object systemService2 =
                context.getSystemService((Class<Object>) AppHibernationManager.class);
        Intrinsics.checkNotNull(systemService2);
        this.appHibernationManager = (AppHibernationManager) systemService2;
        this.executor = ExecutorsKt.asExecutor(Dispatchers.IO);
        this.isEligibleFlow =
                new SafeFlow(new HibernationSwitchPresenter$isEligibleFlow$1(this, null));
        OverridableFlow overridableFlow =
                new OverridableFlow(
                        new SafeFlow(new HibernationSwitchPresenter$isChecked$1(this, null)));
        this.isChecked = overridableFlow;
        this.isCheckedFlow = overridableFlow.flow;
    }
}
