package com.android.settings.spa.app.appinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.android.settingslib.spaprivileged.framework.common.ContextsKt;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;
import com.android.settingslib.spaprivileged.model.app.PermissionsChangedFlowKt;

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
public final class AppPermissionSummaryRepository {
    public final ApplicationInfo app;
    public final Context context;
    public final Flow flow;
    public final Context userContext;

    public AppPermissionSummaryRepository(Context context, ApplicationInfo app) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(app, "app");
        this.context = context;
        this.app = app;
        this.userContext = ContextsKt.asUser(context, ApplicationInfosKt.getUserHandle(app));
        final Flow permissionsChangedFlow =
                PermissionsChangedFlowKt.permissionsChangedFlow(context, app);
        this.flow =
                FlowKt.flowOn(
                        new Flow() { // from class:
                            // com.android.settings.spa.app.appinfo.AppPermissionSummaryRepository$special$$inlined$map$1

                            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                            /* renamed from: com.android.settings.spa.app.appinfo.AppPermissionSummaryRepository$special$$inlined$map$1$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                                public final /* synthetic */ AppPermissionSummaryRepository this$0;

                                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                @Metadata(
                                        k = 3,
                                        mv = {1, 9, 0},
                                        xi = 48)
                                /* renamed from: com.android.settings.spa.app.appinfo.AppPermissionSummaryRepository$special$$inlined$map$1$2$1, reason: invalid class name */
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
                                        AppPermissionSummaryRepository
                                                appPermissionSummaryRepository) {
                                    this.$this_unsafeFlow = flowCollector;
                                    this.this$0 = appPermissionSummaryRepository;
                                }

                                /* JADX WARN: Removed duplicated region for block: B:19:0x0084 A[RETURN] */
                                /* JADX WARN: Removed duplicated region for block: B:20:0x003b  */
                                /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                                */
                                public final java.lang.Object emit(
                                        java.lang.Object r8, kotlin.coroutines.Continuation r9) {
                                    /*
                                        r7 = this;
                                        boolean r0 = r9 instanceof com.android.settings.spa.app.appinfo.AppPermissionSummaryRepository$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                        if (r0 == 0) goto L13
                                        r0 = r9
                                        com.android.settings.spa.app.appinfo.AppPermissionSummaryRepository$special$$inlined$map$1$2$1 r0 = (com.android.settings.spa.app.appinfo.AppPermissionSummaryRepository$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                        int r1 = r0.label
                                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                        r3 = r1 & r2
                                        if (r3 == 0) goto L13
                                        int r1 = r1 - r2
                                        r0.label = r1
                                        goto L18
                                    L13:
                                        com.android.settings.spa.app.appinfo.AppPermissionSummaryRepository$special$$inlined$map$1$2$1 r0 = new com.android.settings.spa.app.appinfo.AppPermissionSummaryRepository$special$$inlined$map$1$2$1
                                        r0.<init>(r9)
                                    L18:
                                        java.lang.Object r9 = r0.result
                                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                        int r2 = r0.label
                                        r3 = 0
                                        r4 = 2
                                        r5 = 1
                                        if (r2 == 0) goto L3b
                                        if (r2 == r5) goto L33
                                        if (r2 != r4) goto L2b
                                        kotlin.ResultKt.throwOnFailure(r9)
                                        goto L85
                                    L2b:
                                        java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                                        java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                                        r7.<init>(r8)
                                        throw r7
                                    L33:
                                        java.lang.Object r7 = r0.L$0
                                        kotlinx.coroutines.flow.FlowCollector r7 = (kotlinx.coroutines.flow.FlowCollector) r7
                                        kotlin.ResultKt.throwOnFailure(r9)
                                        goto L7a
                                    L3b:
                                        kotlin.ResultKt.throwOnFailure(r9)
                                        kotlin.Unit r8 = (kotlin.Unit) r8
                                        kotlinx.coroutines.flow.FlowCollector r8 = r7.$this_unsafeFlow
                                        r0.L$0 = r8
                                        r0.label = r5
                                        com.android.settings.spa.app.appinfo.AppPermissionSummaryRepository r7 = r7.this$0
                                        r7.getClass()
                                        kotlinx.coroutines.CancellableContinuationImpl r9 = new kotlinx.coroutines.CancellableContinuationImpl
                                        kotlin.coroutines.Continuation r2 = kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.intercepted(r0)
                                        r9.<init>(r5, r2)
                                        r9.initCancellability()
                                        android.content.Context r2 = r7.userContext
                                        android.content.pm.ApplicationInfo r5 = r7.app
                                        java.lang.String r5 = r5.packageName
                                        com.android.settings.spa.app.appinfo.AppPermissionSummaryRepository$getPermissionSummary$2$1 r6 = new com.android.settings.spa.app.appinfo.AppPermissionSummaryRepository$getPermissionSummary$2$1
                                        r6.<init>(r7, r9)
                                        java.lang.Class<android.permission.PermissionControllerManager> r7 = android.permission.PermissionControllerManager.class
                                        java.lang.Object r7 = r2.getSystemService(r7)
                                        android.permission.PermissionControllerManager r7 = (android.permission.PermissionControllerManager) r7
                                        com.android.settingslib.applications.PermissionsSummaryHelper$$ExternalSyntheticLambda0 r2 = new com.android.settingslib.applications.PermissionsSummaryHelper$$ExternalSyntheticLambda0
                                        r2.<init>(r6)
                                        r7.getAppPermissions(r5, r2, r3)
                                        java.lang.Object r9 = r9.getResult()
                                        if (r9 != r1) goto L79
                                        return r1
                                    L79:
                                        r7 = r8
                                    L7a:
                                        r0.L$0 = r3
                                        r0.label = r4
                                        java.lang.Object r7 = r7.emit(r9, r0)
                                        if (r7 != r1) goto L85
                                        return r1
                                    L85:
                                        kotlin.Unit r7 = kotlin.Unit.INSTANCE
                                        return r7
                                    */
                                    throw new UnsupportedOperationException(
                                            "Method not decompiled:"
                                                + " com.android.settings.spa.app.appinfo.AppPermissionSummaryRepository$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                                + " kotlin.coroutines.Continuation):java.lang.Object");
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(
                                    FlowCollector flowCollector, Continuation continuation) {
                                Object collect =
                                        Flow.this.collect(
                                                new AnonymousClass2(flowCollector, this),
                                                continuation);
                                return collect == CoroutineSingletons.COROUTINE_SUSPENDED
                                        ? collect
                                        : Unit.INSTANCE;
                            }
                        },
                        Dispatchers.Default);
    }
}
