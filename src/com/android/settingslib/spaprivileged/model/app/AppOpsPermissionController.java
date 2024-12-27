package com.android.settingslib.spaprivileged.model.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppOpsPermissionController {
    public final ApplicationInfo app;
    public final AppOpsController appOpsController;
    public final Flow isAllowedFlow;
    public final IPackageManagers packageManagers;
    public final String permission;

    public AppOpsPermissionController(
            Context context, ApplicationInfo app, AppOps appOps, String permission) {
        PackageManagers packageManagers = PackageManagers.INSTANCE;
        AppOpsController appOpsController = new AppOpsController(context, app, appOps);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(app, "app");
        Intrinsics.checkNotNullParameter(appOps, "appOps");
        Intrinsics.checkNotNullParameter(permission, "permission");
        Intrinsics.checkNotNullParameter(packageManagers, "packageManagers");
        this.app = app;
        this.permission = permission;
        this.packageManagers = packageManagers;
        this.appOpsController = appOpsController;
        final Flow flow = appOpsController.modeFlow;
        this.isAllowedFlow =
                FlowKt.flowOn(
                        FlowKt.onEach(
                                FlowKt.buffer$default(
                                        new Flow() { // from class:
                                            // com.android.settingslib.spaprivileged.model.app.AppOpsPermissionController$special$$inlined$map$1

                                            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                            /* renamed from: com.android.settingslib.spaprivileged.model.app.AppOpsPermissionController$special$$inlined$map$1$2, reason: invalid class name */
                                            public final class AnonymousClass2
                                                    implements FlowCollector {
                                                public final /* synthetic */ FlowCollector
                                                        $this_unsafeFlow;
                                                public final /* synthetic */
                                                AppOpsPermissionController this$0;

                                                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                                @Metadata(
                                                        k = 3,
                                                        mv = {1, 9, 0},
                                                        xi = 48)
                                                /* renamed from: com.android.settingslib.spaprivileged.model.app.AppOpsPermissionController$special$$inlined$map$1$2$1, reason: invalid class name */
                                                public final class AnonymousClass1
                                                        extends ContinuationImpl {
                                                    Object L$0;
                                                    int label;
                                                    /* synthetic */ Object result;

                                                    public AnonymousClass1(
                                                            Continuation continuation) {
                                                        super(continuation);
                                                    }

                                                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                    public final Object invokeSuspend(Object obj) {
                                                        this.result = obj;
                                                        this.label |= Integer.MIN_VALUE;
                                                        return AnonymousClass2.this.emit(
                                                                null, this);
                                                    }
                                                }

                                                public AnonymousClass2(
                                                        FlowCollector flowCollector,
                                                        AppOpsPermissionController
                                                                appOpsPermissionController) {
                                                    this.$this_unsafeFlow = flowCollector;
                                                    this.this$0 = appOpsPermissionController;
                                                }

                                                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                                                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                                                @Override // kotlinx.coroutines.flow.FlowCollector
                                                /*
                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                                                */
                                                public final java.lang.Object emit(
                                                        java.lang.Object r5,
                                                        kotlin.coroutines.Continuation r6) {
                                                    /*
                                                        r4 = this;
                                                        boolean r0 = r6 instanceof com.android.settingslib.spaprivileged.model.app.AppOpsPermissionController$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                                        if (r0 == 0) goto L13
                                                        r0 = r6
                                                        com.android.settingslib.spaprivileged.model.app.AppOpsPermissionController$special$$inlined$map$1$2$1 r0 = (com.android.settingslib.spaprivileged.model.app.AppOpsPermissionController$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                                        int r1 = r0.label
                                                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                                        r3 = r1 & r2
                                                        if (r3 == 0) goto L13
                                                        int r1 = r1 - r2
                                                        r0.label = r1
                                                        goto L18
                                                    L13:
                                                        com.android.settingslib.spaprivileged.model.app.AppOpsPermissionController$special$$inlined$map$1$2$1 r0 = new com.android.settingslib.spaprivileged.model.app.AppOpsPermissionController$special$$inlined$map$1$2$1
                                                        r0.<init>(r6)
                                                    L18:
                                                        java.lang.Object r6 = r0.result
                                                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                                        int r2 = r0.label
                                                        r3 = 1
                                                        if (r2 == 0) goto L2f
                                                        if (r2 != r3) goto L27
                                                        kotlin.ResultKt.throwOnFailure(r6)
                                                        goto L5c
                                                    L27:
                                                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                                        r4.<init>(r5)
                                                        throw r4
                                                    L2f:
                                                        kotlin.ResultKt.throwOnFailure(r6)
                                                        java.lang.Number r5 = (java.lang.Number) r5
                                                        int r5 = r5.intValue()
                                                        if (r5 == 0) goto L4c
                                                        r6 = 3
                                                        if (r5 == r6) goto L3f
                                                        r5 = 0
                                                        goto L4d
                                                    L3f:
                                                        com.android.settingslib.spaprivileged.model.app.AppOpsPermissionController r5 = r4.this$0
                                                        com.android.settingslib.spaprivileged.model.app.IPackageManagers r6 = r5.packageManagers
                                                        android.content.pm.ApplicationInfo r2 = r5.app
                                                        java.lang.String r5 = r5.permission
                                                        boolean r5 = r6.hasGrantPermission(r2, r5)
                                                        goto L4d
                                                    L4c:
                                                        r5 = r3
                                                    L4d:
                                                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                                                        r0.label = r3
                                                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                                        java.lang.Object r4 = r4.emit(r5, r0)
                                                        if (r4 != r1) goto L5c
                                                        return r1
                                                    L5c:
                                                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                                        return r4
                                                    */
                                                    throw new UnsupportedOperationException(
                                                            "Method not decompiled:"
                                                                + " com.android.settingslib.spaprivileged.model.app.AppOpsPermissionController$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                                                + " kotlin.coroutines.Continuation):java.lang.Object");
                                                }
                                            }

                                            @Override // kotlinx.coroutines.flow.Flow
                                            public final Object collect(
                                                    FlowCollector flowCollector,
                                                    Continuation continuation) {
                                                Object collect =
                                                        Flow.this.collect(
                                                                new AnonymousClass2(
                                                                        flowCollector, this),
                                                                continuation);
                                                return collect
                                                                == CoroutineSingletons
                                                                        .COROUTINE_SUSPENDED
                                                        ? collect
                                                        : Unit.INSTANCE;
                                            }
                                        },
                                        -1),
                                new AppOpsPermissionController$isAllowedFlow$2(2, null)),
                        Dispatchers.Default);
    }
}
