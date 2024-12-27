package com.android.settingslib.spaprivileged.model.app;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.UserHandle;

import com.android.settingslib.spaprivileged.framework.common.ContextsKt;

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
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppOpsController {
    public final ApplicationInfo app;
    public final AppOps appOps;
    public final AppOpsManager appOpsManager;
    public final Flow modeFlow;
    public final PackageManager packageManager;

    public AppOpsController(Context context, final ApplicationInfo app, AppOps appOps) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(app, "app");
        Intrinsics.checkNotNullParameter(appOps, "appOps");
        this.app = app;
        this.appOps = appOps;
        final AppOpsManager appOpsManager = ContextsKt.getAppOpsManager(context);
        this.appOpsManager = appOpsManager;
        this.packageManager = context.getPackageManager();
        final int i = appOps.op;
        Flow buffer$default =
                FlowKt.buffer$default(
                        FlowKt.callbackFlow(
                                new AppOpsRepositoryKt$opChangedFlow$1(
                                        appOpsManager, i, app, null)),
                        -1);
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        final Flow flowOn = FlowKt.flowOn(buffer$default, defaultScheduler);
        this.modeFlow =
                FlowKt.flowOn(
                        new Flow() { // from class:
                            // com.android.settingslib.spaprivileged.model.app.AppOpsRepositoryKt$opModeFlow$$inlined$map$1

                            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                            /* renamed from: com.android.settingslib.spaprivileged.model.app.AppOpsRepositoryKt$opModeFlow$$inlined$map$1$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ ApplicationInfo $app$inlined;
                                public final /* synthetic */ int $op$inlined;
                                public final /* synthetic */ AppOpsManager $this_opModeFlow$inlined;
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                @Metadata(
                                        k = 3,
                                        mv = {1, 9, 0},
                                        xi = 48)
                                /* renamed from: com.android.settingslib.spaprivileged.model.app.AppOpsRepositoryKt$opModeFlow$$inlined$map$1$2$1, reason: invalid class name */
                                public final class AnonymousClass1 extends ContinuationImpl {
                                    Object L$0;
                                    int label;
                                    /* synthetic */ Object result;

                                    public AnonymousClass1(Continuation continuation) {
                                        super(continuation);
                                    }

                                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                    public final Object invokeSuspend(Object obj) {
                                        this.result = obj;
                                        this.label |= Integer.MIN_VALUE;
                                        return AnonymousClass2.this.emit(null, this);
                                    }
                                }

                                public AnonymousClass2(
                                        FlowCollector flowCollector,
                                        AppOpsManager appOpsManager,
                                        int i,
                                        ApplicationInfo applicationInfo) {
                                    this.$this_unsafeFlow = flowCollector;
                                    this.$this_opModeFlow$inlined = appOpsManager;
                                    this.$op$inlined = i;
                                    this.$app$inlined = applicationInfo;
                                }

                                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                                */
                                public final java.lang.Object emit(
                                        java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                                    /*
                                        r5 = this;
                                        boolean r0 = r7 instanceof com.android.settingslib.spaprivileged.model.app.AppOpsRepositoryKt$opModeFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                        if (r0 == 0) goto L13
                                        r0 = r7
                                        com.android.settingslib.spaprivileged.model.app.AppOpsRepositoryKt$opModeFlow$$inlined$map$1$2$1 r0 = (com.android.settingslib.spaprivileged.model.app.AppOpsRepositoryKt$opModeFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                        int r1 = r0.label
                                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                        r3 = r1 & r2
                                        if (r3 == 0) goto L13
                                        int r1 = r1 - r2
                                        r0.label = r1
                                        goto L18
                                    L13:
                                        com.android.settingslib.spaprivileged.model.app.AppOpsRepositoryKt$opModeFlow$$inlined$map$1$2$1 r0 = new com.android.settingslib.spaprivileged.model.app.AppOpsRepositoryKt$opModeFlow$$inlined$map$1$2$1
                                        r0.<init>(r7)
                                    L18:
                                        java.lang.Object r7 = r0.result
                                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                        int r2 = r0.label
                                        r3 = 1
                                        if (r2 == 0) goto L2f
                                        if (r2 != r3) goto L27
                                        kotlin.ResultKt.throwOnFailure(r7)
                                        goto L5c
                                    L27:
                                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                                        r5.<init>(r6)
                                        throw r5
                                    L2f:
                                        kotlin.ResultKt.throwOnFailure(r7)
                                        kotlin.Unit r6 = (kotlin.Unit) r6
                                        android.app.AppOpsManager r6 = r5.$this_opModeFlow$inlined
                                        android.content.pm.ApplicationInfo r7 = r5.$app$inlined
                                        java.lang.String r2 = "<this>"
                                        kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r6, r2)
                                        java.lang.String r2 = "app"
                                        kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r7, r2)
                                        int r2 = r7.uid
                                        java.lang.String r7 = r7.packageName
                                        int r4 = r5.$op$inlined
                                        int r6 = r6.checkOpNoThrow(r4, r2, r7)
                                        java.lang.Integer r7 = new java.lang.Integer
                                        r7.<init>(r6)
                                        r0.label = r3
                                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                                        java.lang.Object r5 = r5.emit(r7, r0)
                                        if (r5 != r1) goto L5c
                                        return r1
                                    L5c:
                                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                                        return r5
                                    */
                                    throw new UnsupportedOperationException(
                                            "Method not decompiled:"
                                                + " com.android.settingslib.spaprivileged.model.app.AppOpsRepositoryKt$opModeFlow$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                                + " kotlin.coroutines.Continuation):java.lang.Object");
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(
                                    FlowCollector flowCollector, Continuation continuation) {
                                Object collect =
                                        Flow.this.collect(
                                                new AnonymousClass2(
                                                        flowCollector, appOpsManager, i, app),
                                                continuation);
                                return collect == CoroutineSingletons.COROUTINE_SUSPENDED
                                        ? collect
                                        : Unit.INSTANCE;
                            }
                        },
                        defaultScheduler);
    }

    public final void setAllowed(boolean z) {
        AppOps appOps = this.appOps;
        int i = z ? 0 : appOps.modeForNotAllowed;
        boolean z2 = appOps.setModeByUid;
        int i2 = appOps.op;
        if (z2) {
            this.appOpsManager.setUidMode(i2, this.app.uid, i);
        } else {
            AppOpsManager appOpsManager = this.appOpsManager;
            ApplicationInfo applicationInfo = this.app;
            appOpsManager.setMode(i2, applicationInfo.uid, applicationInfo.packageName, i);
        }
        String opToPermission = AppOpsManager.opToPermission(i2);
        if (opToPermission != null) {
            PackageManager packageManager = this.packageManager;
            ApplicationInfo applicationInfo2 = this.app;
            packageManager.updatePermissionFlags(
                    opToPermission,
                    applicationInfo2.packageName,
                    1,
                    1,
                    UserHandle.getUserHandleForUid(applicationInfo2.uid));
        }
    }
}
