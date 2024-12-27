package com.android.settings.spa.app.appcompat;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.android.settings.applications.appcompat.UserAspectRatioManager;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.scheduling.DefaultIoScheduler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UserAspectRatioAppPresenter {
    public final ApplicationInfo app;
    public final Context context;
    public final Flow isAvailableFlow;
    public final UserAspectRatioManager manager;
    public final Flow summaryFlow;

    public UserAspectRatioAppPresenter(Context context, ApplicationInfo app) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(app, "app");
        this.context = context;
        this.app = app;
        this.manager = new UserAspectRatioManager(context);
        SafeFlow safeFlow =
                new SafeFlow(new UserAspectRatioAppPresenter$isAvailableFlow$1(this, null));
        DefaultIoScheduler defaultIoScheduler = Dispatchers.IO;
        this.isAvailableFlow = FlowKt.flowOn(safeFlow, defaultIoScheduler);
        this.summaryFlow =
                FlowKt.flowOn(
                        new SafeFlow(new UserAspectRatioAppPresenter$summaryFlow$1(this, null)),
                        defaultIoScheduler);
    }
}
