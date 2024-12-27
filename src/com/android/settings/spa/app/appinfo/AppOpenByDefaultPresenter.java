package com.android.settings.spa.app.appinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.verify.domain.DomainVerificationManager;

import com.android.settingslib.spaprivileged.framework.common.ContextsKt;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppOpenByDefaultPresenter {
    public final ApplicationInfo app;
    public final Context context;
    public final DomainVerificationManager domainVerificationManager;
    public final Flow summaryFlow;

    public AppOpenByDefaultPresenter(Context context, ApplicationInfo app) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(app, "app");
        this.context = context;
        this.app = app;
        Object systemService =
                ContextsKt.asUser(context, ApplicationInfosKt.getUserHandle(app))
                        .getSystemService((Class<Object>) DomainVerificationManager.class);
        Intrinsics.checkNotNull(systemService);
        this.domainVerificationManager = (DomainVerificationManager) systemService;
        this.summaryFlow =
                FlowKt.flowOn(
                        new SafeFlow(new AppOpenByDefaultPresenter$summaryFlow$1(this, null)),
                        Dispatchers.IO);
    }
}
