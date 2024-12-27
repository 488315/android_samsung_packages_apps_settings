package com.android.settings.spa.app.appinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.internal.ContextScope;
import kotlinx.coroutines.scheduling.DefaultIoScheduler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppAllServicesPresenter {
    public final ReadonlySharedFlow activityInfoFlow;
    public final ApplicationInfo app;
    public final Context context;
    public final CoroutineScope coroutineScope;
    public final AppAllServicesPresenter$special$$inlined$map$1 isAvailableFlow;
    public final PackageManager packageManager;
    public final Flow summaryFlow;

    /* JADX WARN: Type inference failed for: r5v2, types: [com.android.settings.spa.app.appinfo.AppAllServicesPresenter$special$$inlined$map$1] */
    public AppAllServicesPresenter(
            Context context, ApplicationInfo app, ContextScope contextScope) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(app, "app");
        this.context = context;
        this.app = app;
        this.coroutineScope = contextScope;
        this.packageManager = context.getPackageManager();
        SafeFlow safeFlow =
                new SafeFlow(new AppAllServicesPresenter$activityInfoFlow$1(this, null));
        DefaultIoScheduler defaultIoScheduler = Dispatchers.IO;
        final ReadonlySharedFlow shareIn =
                FlowKt.shareIn(
                        safeFlow,
                        CoroutineScopeKt.plus(contextScope, defaultIoScheduler),
                        SharingStarted.Companion.WhileSubscribed$default(),
                        1);
        this.activityInfoFlow = shareIn;
        this.isAvailableFlow =
                new Flow() { // from class:
                    // com.android.settings.spa.app.appinfo.AppAllServicesPresenter$special$$inlined$map$1

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    /* renamed from: com.android.settings.spa.app.appinfo.AppAllServicesPresenter$special$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                        @Metadata(
                                k = 3,
                                mv = {1, 9, 0},
                                xi = 48)
                        /* renamed from: com.android.settings.spa.app.appinfo.AppAllServicesPresenter$special$$inlined$map$1$2$1, reason: invalid class name */
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

                        public AnonymousClass2(FlowCollector flowCollector) {
                            this.$this_unsafeFlow = flowCollector;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(
                                java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                            /*
                                r4 = this;
                                boolean r0 = r6 instanceof com.android.settings.spa.app.appinfo.AppAllServicesPresenter$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.settings.spa.app.appinfo.AppAllServicesPresenter$special$$inlined$map$1$2$1 r0 = (com.android.settings.spa.app.appinfo.AppAllServicesPresenter$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.settings.spa.app.appinfo.AppAllServicesPresenter$special$$inlined$map$1$2$1 r0 = new com.android.settings.spa.app.appinfo.AppAllServicesPresenter$special$$inlined$map$1$2$1
                                r0.<init>(r6)
                            L18:
                                java.lang.Object r6 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r6)
                                goto L48
                            L27:
                                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                r4.<init>(r5)
                                throw r4
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r6)
                                android.content.pm.ActivityInfo r5 = (android.content.pm.ActivityInfo) r5
                                if (r5 == 0) goto L38
                                r5 = r3
                                goto L39
                            L38:
                                r5 = 0
                            L39:
                                java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                java.lang.Object r4 = r4.emit(r5, r0)
                                if (r4 != r1) goto L48
                                return r1
                            L48:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.android.settings.spa.app.appinfo.AppAllServicesPresenter$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                        + " kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(
                            FlowCollector flowCollector, Continuation continuation) {
                        Object collect =
                                shareIn.collect(new AnonymousClass2(flowCollector), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED
                                ? collect
                                : Unit.INSTANCE;
                    }
                };
        this.summaryFlow =
                FlowKt.flowOn(
                        new Flow() { // from class:
                            // com.android.settings.spa.app.appinfo.AppAllServicesPresenter$special$$inlined$map$2

                            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                            /* renamed from: com.android.settings.spa.app.appinfo.AppAllServicesPresenter$special$$inlined$map$2$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                                public final /* synthetic */ AppAllServicesPresenter this$0;

                                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                @Metadata(
                                        k = 3,
                                        mv = {1, 9, 0},
                                        xi = 48)
                                /* renamed from: com.android.settings.spa.app.appinfo.AppAllServicesPresenter$special$$inlined$map$2$2$1, reason: invalid class name */
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
                                        AppAllServicesPresenter appAllServicesPresenter) {
                                    this.$this_unsafeFlow = flowCollector;
                                    this.this$0 = appAllServicesPresenter;
                                }

                                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                                */
                                public final java.lang.Object emit(
                                        java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                                    /*
                                        r6 = this;
                                        boolean r0 = r8 instanceof com.android.settings.spa.app.appinfo.AppAllServicesPresenter$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                                        if (r0 == 0) goto L13
                                        r0 = r8
                                        com.android.settings.spa.app.appinfo.AppAllServicesPresenter$special$$inlined$map$2$2$1 r0 = (com.android.settings.spa.app.appinfo.AppAllServicesPresenter$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                                        int r1 = r0.label
                                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                        r3 = r1 & r2
                                        if (r3 == 0) goto L13
                                        int r1 = r1 - r2
                                        r0.label = r1
                                        goto L18
                                    L13:
                                        com.android.settings.spa.app.appinfo.AppAllServicesPresenter$special$$inlined$map$2$2$1 r0 = new com.android.settings.spa.app.appinfo.AppAllServicesPresenter$special$$inlined$map$2$2$1
                                        r0.<init>(r8)
                                    L18:
                                        java.lang.Object r8 = r0.result
                                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                        int r2 = r0.label
                                        r3 = 1
                                        if (r2 == 0) goto L2f
                                        if (r2 != r3) goto L27
                                        kotlin.ResultKt.throwOnFailure(r8)
                                        goto L73
                                    L27:
                                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                                        r6.<init>(r7)
                                        throw r6
                                    L2f:
                                        kotlin.ResultKt.throwOnFailure(r8)
                                        android.content.pm.ActivityInfo r7 = (android.content.pm.ActivityInfo) r7
                                        java.lang.String r8 = ""
                                        if (r7 == 0) goto L68
                                        android.os.Bundle r7 = r7.metaData
                                        if (r7 == 0) goto L68
                                        com.android.settings.spa.app.appinfo.AppAllServicesPresenter r2 = r6.this$0
                                        r2.getClass()
                                        java.lang.String r4 = "AppAllServicesPresenter"
                                        android.content.pm.PackageManager r5 = r2.packageManager     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L63
                                        android.content.pm.ApplicationInfo r2 = r2.app     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L63
                                        android.content.res.Resources r2 = r5.getResourcesForApplication(r2)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L63
                                        kotlin.jvm.internal.Intrinsics.checkNotNull(r2)
                                        java.lang.String r5 = "app_features_preference_summary"
                                        int r7 = r7.getInt(r5)     // Catch: android.content.res.Resources.NotFoundException -> L5d
                                        java.lang.String r7 = r2.getString(r7)     // Catch: android.content.res.Resources.NotFoundException -> L5d
                                        kotlin.jvm.internal.Intrinsics.checkNotNull(r7)     // Catch: android.content.res.Resources.NotFoundException -> L5d
                                        r8 = r7
                                        goto L68
                                    L5d:
                                        java.lang.String r7 = "Resource not found for summary string."
                                        android.util.Log.d(r4, r7)
                                        goto L68
                                    L63:
                                        java.lang.String r7 = "Name not found for the application."
                                        android.util.Log.d(r4, r7)
                                    L68:
                                        r0.label = r3
                                        kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                                        java.lang.Object r6 = r6.emit(r8, r0)
                                        if (r6 != r1) goto L73
                                        return r1
                                    L73:
                                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                                        return r6
                                    */
                                    throw new UnsupportedOperationException(
                                            "Method not decompiled:"
                                                + " com.android.settings.spa.app.appinfo.AppAllServicesPresenter$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object,"
                                                + " kotlin.coroutines.Continuation):java.lang.Object");
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(
                                    FlowCollector flowCollector, Continuation continuation) {
                                Object collect =
                                        shareIn.collect(
                                                new AnonymousClass2(flowCollector, this),
                                                continuation);
                                return collect == CoroutineSingletons.COROUTINE_SUSPENDED
                                        ? collect
                                        : Unit.INSTANCE;
                            }
                        },
                        defaultIoScheduler);
    }
}
