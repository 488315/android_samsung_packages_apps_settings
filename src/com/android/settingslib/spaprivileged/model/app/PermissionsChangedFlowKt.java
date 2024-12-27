package com.android.settingslib.spaprivileged.model.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class PermissionsChangedFlowKt {
    public static final Flow permissionsChangedFlow(Context context, ApplicationInfo app) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(app, "app");
        return FlowKt.flowOn(
                FlowKt.buffer$default(
                        FlowKt.callbackFlow(
                                new PermissionsChangedFlowKt$permissionsChangedFlow$1(
                                        context, app, null)),
                        -1),
                Dispatchers.Default);
    }
}
