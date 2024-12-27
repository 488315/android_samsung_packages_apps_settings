package com.android.settings.spa.app.appinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.InstallSourceInfo;
import android.content.pm.PackageManager;

import com.android.settingslib.spaprivileged.framework.common.ContextsKt;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppInstallerInfoPresenter {
    public final ApplicationInfo app;
    public final Context context;
    public final CoroutineScope coroutineScope;
    public final AppInstallerInfoPresenter$special$$inlined$map$5 enabledFlow;
    public InstallSourceInfo installSourceInfo;
    public final ReadonlySharedFlow installerLabelFlow;
    public final ReadonlySharedFlow installerPackageFlow;
    public final ReadonlySharedFlow intentFlow;
    public final AppInstallerInfoPresenter$special$$inlined$map$1 isAvailableFlow;
    public final PackageManager packageManager;
    public final AppInstallerInfoPresenter$special$$inlined$map$1 summaryFlow;
    public final Context userContext;

    /* JADX WARN: Type inference failed for: r3v7, types: [com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$5] */
    /* JADX WARN: Type inference failed for: r4v2, types: [com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1] */
    /* JADX WARN: Type inference failed for: r4v3, types: [com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1] */
    public AppInstallerInfoPresenter(
            Context context, ApplicationInfo app, ContextScope contextScope) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(app, "app");
        this.context = context;
        this.app = app;
        this.coroutineScope = contextScope;
        Context asUser = ContextsKt.asUser(context, ApplicationInfosKt.getUserHandle(app));
        this.userContext = asUser;
        this.packageManager = asUser.getPackageManager();
        final ReadonlySharedFlow sharedFlow =
                sharedFlow(
                        new SafeFlow(
                                new AppInstallerInfoPresenter$installerPackageFlow$1(this, null)));
        final int i = 0;
        final ReadonlySharedFlow sharedFlow2 =
                sharedFlow(
                        new Flow() { // from class:
                            // com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1

                            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                            /* renamed from: com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                                public final /* synthetic */ AppInstallerInfoPresenter this$0;

                                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                @Metadata(
                                        k = 3,
                                        mv = {1, 9, 0},
                                        xi = 48)
                                /* renamed from: com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1$2$1, reason: invalid class name */
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
                                        AppInstallerInfoPresenter appInstallerInfoPresenter) {
                                    this.$this_unsafeFlow = flowCollector;
                                    this.this$0 = appInstallerInfoPresenter;
                                }

                                /* JADX WARN: Removed duplicated region for block: B:20:0x006a A[RETURN] */
                                /* JADX WARN: Removed duplicated region for block: B:21:0x003b  */
                                /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                                */
                                public final java.lang.Object emit(
                                        java.lang.Object r9, kotlin.coroutines.Continuation r10) {
                                    /*
                                        r8 = this;
                                        boolean r0 = r10 instanceof com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                        if (r0 == 0) goto L13
                                        r0 = r10
                                        com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1$2$1 r0 = (com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                        int r1 = r0.label
                                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                        r3 = r1 & r2
                                        if (r3 == 0) goto L13
                                        int r1 = r1 - r2
                                        r0.label = r1
                                        goto L18
                                    L13:
                                        com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1$2$1 r0 = new com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1$2$1
                                        r0.<init>(r10)
                                    L18:
                                        java.lang.Object r10 = r0.result
                                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                        int r2 = r0.label
                                        r3 = 2
                                        r4 = 1
                                        r5 = 0
                                        if (r2 == 0) goto L3b
                                        if (r2 == r4) goto L33
                                        if (r2 != r3) goto L2b
                                        kotlin.ResultKt.throwOnFailure(r10)
                                        goto L6b
                                    L2b:
                                        java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                                        java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                                        r8.<init>(r9)
                                        throw r8
                                    L33:
                                        java.lang.Object r8 = r0.L$0
                                        kotlinx.coroutines.flow.FlowCollector r8 = (kotlinx.coroutines.flow.FlowCollector) r8
                                        kotlin.ResultKt.throwOnFailure(r10)
                                        goto L5d
                                    L3b:
                                        kotlin.ResultKt.throwOnFailure(r10)
                                        java.lang.String r9 = (java.lang.String) r9
                                        kotlinx.coroutines.flow.FlowCollector r10 = r8.$this_unsafeFlow
                                        if (r9 != 0) goto L46
                                        r8 = r5
                                        goto L60
                                    L46:
                                        kotlinx.coroutines.scheduling.DefaultIoScheduler r2 = kotlinx.coroutines.Dispatchers.IO
                                        com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$installerLabelFlow$1$1 r6 = new com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$installerLabelFlow$1$1
                                        com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter r8 = r8.this$0
                                        r6.<init>(r8, r9, r5)
                                        r0.L$0 = r10
                                        r0.label = r4
                                        java.lang.Object r8 = kotlinx.coroutines.BuildersKt.withContext(r2, r6, r0)
                                        if (r8 != r1) goto L5a
                                        return r1
                                    L5a:
                                        r7 = r10
                                        r10 = r8
                                        r8 = r7
                                    L5d:
                                        r7 = r10
                                        r10 = r8
                                        r8 = r7
                                    L60:
                                        r0.L$0 = r5
                                        r0.label = r3
                                        java.lang.Object r8 = r10.emit(r8, r0)
                                        if (r8 != r1) goto L6b
                                        return r1
                                    L6b:
                                        kotlin.Unit r8 = kotlin.Unit.INSTANCE
                                        return r8
                                    */
                                    throw new UnsupportedOperationException(
                                            "Method not decompiled:"
                                                + " com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                                + " kotlin.coroutines.Continuation):java.lang.Object");
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(
                                    FlowCollector flowCollector, Continuation continuation) {
                                switch (i) {
                                    case 0:
                                        Object collect =
                                                sharedFlow.collect(
                                                        new AnonymousClass2(flowCollector, this),
                                                        continuation);
                                        if (collect != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                            break;
                                        }
                                        break;
                                    case 1:
                                        Object collect2 =
                                                sharedFlow.collect(
                                                        new AppInstallerInfoPresenter$special$$inlined$map$2$2(
                                                                flowCollector, this),
                                                        continuation);
                                        if (collect2 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                            break;
                                        }
                                        break;
                                    case 2:
                                        Object collect3 =
                                                sharedFlow.collect(
                                                        new AppInstallerInfoPresenter$special$$inlined$map$3$2(
                                                                flowCollector, this),
                                                        continuation);
                                        if (collect3 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                            break;
                                        }
                                        break;
                                    default:
                                        Object collect4 =
                                                sharedFlow.collect(
                                                        new AppInstallerInfoPresenter$special$$inlined$map$4$2(
                                                                flowCollector, this),
                                                        continuation);
                                        if (collect4 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                            break;
                                        }
                                        break;
                                }
                                return Unit.INSTANCE;
                            }
                        });
        final int i2 = 1;
        this.isAvailableFlow =
                new Flow() { // from class:
                    // com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    /* renamed from: com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ AppInstallerInfoPresenter this$0;

                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                        @Metadata(
                                k = 3,
                                mv = {1, 9, 0},
                                xi = 48)
                        /* renamed from: com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1$2$1, reason: invalid class name */
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
                                AppInstallerInfoPresenter appInstallerInfoPresenter) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = appInstallerInfoPresenter;
                        }

                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj, Continuation continuation) {
                            /*
                                this = this;
                                boolean r0 = r10 instanceof com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r10
                                com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1$2$1 r0 = (com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1$2$1 r0 = new com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1$2$1
                                r0.<init>(r10)
                            L18:
                                java.lang.Object r10 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 2
                                r4 = 1
                                r5 = 0
                                if (r2 == 0) goto L3b
                                if (r2 == r4) goto L33
                                if (r2 != r3) goto L2b
                                kotlin.ResultKt.throwOnFailure(r10)
                                goto L6b
                            L2b:
                                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                                java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                                r8.<init>(r9)
                                throw r8
                            L33:
                                java.lang.Object r8 = r0.L$0
                                kotlinx.coroutines.flow.FlowCollector r8 = (kotlinx.coroutines.flow.FlowCollector) r8
                                kotlin.ResultKt.throwOnFailure(r10)
                                goto L5d
                            L3b:
                                kotlin.ResultKt.throwOnFailure(r10)
                                java.lang.String r9 = (java.lang.String) r9
                                kotlinx.coroutines.flow.FlowCollector r10 = r8.$this_unsafeFlow
                                if (r9 != 0) goto L46
                                r8 = r5
                                goto L60
                            L46:
                                kotlinx.coroutines.scheduling.DefaultIoScheduler r2 = kotlinx.coroutines.Dispatchers.IO
                                com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$installerLabelFlow$1$1 r6 = new com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$installerLabelFlow$1$1
                                com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter r8 = r8.this$0
                                r6.<init>(r8, r9, r5)
                                r0.L$0 = r10
                                r0.label = r4
                                java.lang.Object r8 = kotlinx.coroutines.BuildersKt.withContext(r2, r6, r0)
                                if (r8 != r1) goto L5a
                                return r1
                            L5a:
                                r7 = r10
                                r10 = r8
                                r8 = r7
                            L5d:
                                r7 = r10
                                r10 = r8
                                r8 = r7
                            L60:
                                r0.L$0 = r5
                                r0.label = r3
                                java.lang.Object r8 = r10.emit(r8, r0)
                                if (r8 != r1) goto L6b
                                return r1
                            L6b:
                                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                                return r8
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                        + " kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(
                            FlowCollector flowCollector, Continuation continuation) {
                        switch (i2) {
                            case 0:
                                Object collect =
                                        sharedFlow2.collect(
                                                new AnonymousClass2(flowCollector, this),
                                                continuation);
                                if (collect != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                    break;
                                }
                                break;
                            case 1:
                                Object collect2 =
                                        sharedFlow2.collect(
                                                new AppInstallerInfoPresenter$special$$inlined$map$2$2(
                                                        flowCollector, this),
                                                continuation);
                                if (collect2 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                    break;
                                }
                                break;
                            case 2:
                                Object collect3 =
                                        sharedFlow2.collect(
                                                new AppInstallerInfoPresenter$special$$inlined$map$3$2(
                                                        flowCollector, this),
                                                continuation);
                                if (collect3 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                    break;
                                }
                                break;
                            default:
                                Object collect4 =
                                        sharedFlow2.collect(
                                                new AppInstallerInfoPresenter$special$$inlined$map$4$2(
                                                        flowCollector, this),
                                                continuation);
                                if (collect4 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                    break;
                                }
                                break;
                        }
                        return Unit.INSTANCE;
                    }
                };
        final int i3 = 2;
        this.summaryFlow =
                new Flow() { // from class:
                    // com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    /* renamed from: com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ AppInstallerInfoPresenter this$0;

                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                        @Metadata(
                                k = 3,
                                mv = {1, 9, 0},
                                xi = 48)
                        /* renamed from: com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1$2$1, reason: invalid class name */
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
                                AppInstallerInfoPresenter appInstallerInfoPresenter) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = appInstallerInfoPresenter;
                        }

                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj, Continuation continuation) {
                            /*
                                this = this;
                                boolean r0 = r10 instanceof com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r10
                                com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1$2$1 r0 = (com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1$2$1 r0 = new com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1$2$1
                                r0.<init>(r10)
                            L18:
                                java.lang.Object r10 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 2
                                r4 = 1
                                r5 = 0
                                if (r2 == 0) goto L3b
                                if (r2 == r4) goto L33
                                if (r2 != r3) goto L2b
                                kotlin.ResultKt.throwOnFailure(r10)
                                goto L6b
                            L2b:
                                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                                java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                                r8.<init>(r9)
                                throw r8
                            L33:
                                java.lang.Object r8 = r0.L$0
                                kotlinx.coroutines.flow.FlowCollector r8 = (kotlinx.coroutines.flow.FlowCollector) r8
                                kotlin.ResultKt.throwOnFailure(r10)
                                goto L5d
                            L3b:
                                kotlin.ResultKt.throwOnFailure(r10)
                                java.lang.String r9 = (java.lang.String) r9
                                kotlinx.coroutines.flow.FlowCollector r10 = r8.$this_unsafeFlow
                                if (r9 != 0) goto L46
                                r8 = r5
                                goto L60
                            L46:
                                kotlinx.coroutines.scheduling.DefaultIoScheduler r2 = kotlinx.coroutines.Dispatchers.IO
                                com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$installerLabelFlow$1$1 r6 = new com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$installerLabelFlow$1$1
                                com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter r8 = r8.this$0
                                r6.<init>(r8, r9, r5)
                                r0.L$0 = r10
                                r0.label = r4
                                java.lang.Object r8 = kotlinx.coroutines.BuildersKt.withContext(r2, r6, r0)
                                if (r8 != r1) goto L5a
                                return r1
                            L5a:
                                r7 = r10
                                r10 = r8
                                r8 = r7
                            L5d:
                                r7 = r10
                                r10 = r8
                                r8 = r7
                            L60:
                                r0.L$0 = r5
                                r0.label = r3
                                java.lang.Object r8 = r10.emit(r8, r0)
                                if (r8 != r1) goto L6b
                                return r1
                            L6b:
                                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                                return r8
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                        + " kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(
                            FlowCollector flowCollector, Continuation continuation) {
                        switch (i3) {
                            case 0:
                                Object collect =
                                        sharedFlow2.collect(
                                                new AnonymousClass2(flowCollector, this),
                                                continuation);
                                if (collect != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                    break;
                                }
                                break;
                            case 1:
                                Object collect2 =
                                        sharedFlow2.collect(
                                                new AppInstallerInfoPresenter$special$$inlined$map$2$2(
                                                        flowCollector, this),
                                                continuation);
                                if (collect2 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                    break;
                                }
                                break;
                            case 2:
                                Object collect3 =
                                        sharedFlow2.collect(
                                                new AppInstallerInfoPresenter$special$$inlined$map$3$2(
                                                        flowCollector, this),
                                                continuation);
                                if (collect3 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                    break;
                                }
                                break;
                            default:
                                Object collect4 =
                                        sharedFlow2.collect(
                                                new AppInstallerInfoPresenter$special$$inlined$map$4$2(
                                                        flowCollector, this),
                                                continuation);
                                if (collect4 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                    break;
                                }
                                break;
                        }
                        return Unit.INSTANCE;
                    }
                };
        final int i4 = 3;
        final ReadonlySharedFlow sharedFlow3 =
                sharedFlow(
                        new Flow() { // from class:
                            // com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1

                            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                            /* renamed from: com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                                public final /* synthetic */ AppInstallerInfoPresenter this$0;

                                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                @Metadata(
                                        k = 3,
                                        mv = {1, 9, 0},
                                        xi = 48)
                                /* renamed from: com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1$2$1, reason: invalid class name */
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
                                        AppInstallerInfoPresenter appInstallerInfoPresenter) {
                                    this.$this_unsafeFlow = flowCollector;
                                    this.this$0 = appInstallerInfoPresenter;
                                }

                                @Override // kotlinx.coroutines.flow.FlowCollector
                                public final Object emit(Object obj, Continuation continuation) {
                                    /*
                                        this = this;
                                        boolean r0 = r10 instanceof com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                        if (r0 == 0) goto L13
                                        r0 = r10
                                        com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1$2$1 r0 = (com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                        int r1 = r0.label
                                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                        r3 = r1 & r2
                                        if (r3 == 0) goto L13
                                        int r1 = r1 - r2
                                        r0.label = r1
                                        goto L18
                                    L13:
                                        com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1$2$1 r0 = new com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1$2$1
                                        r0.<init>(r10)
                                    L18:
                                        java.lang.Object r10 = r0.result
                                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                        int r2 = r0.label
                                        r3 = 2
                                        r4 = 1
                                        r5 = 0
                                        if (r2 == 0) goto L3b
                                        if (r2 == r4) goto L33
                                        if (r2 != r3) goto L2b
                                        kotlin.ResultKt.throwOnFailure(r10)
                                        goto L6b
                                    L2b:
                                        java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                                        java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                                        r8.<init>(r9)
                                        throw r8
                                    L33:
                                        java.lang.Object r8 = r0.L$0
                                        kotlinx.coroutines.flow.FlowCollector r8 = (kotlinx.coroutines.flow.FlowCollector) r8
                                        kotlin.ResultKt.throwOnFailure(r10)
                                        goto L5d
                                    L3b:
                                        kotlin.ResultKt.throwOnFailure(r10)
                                        java.lang.String r9 = (java.lang.String) r9
                                        kotlinx.coroutines.flow.FlowCollector r10 = r8.$this_unsafeFlow
                                        if (r9 != 0) goto L46
                                        r8 = r5
                                        goto L60
                                    L46:
                                        kotlinx.coroutines.scheduling.DefaultIoScheduler r2 = kotlinx.coroutines.Dispatchers.IO
                                        com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$installerLabelFlow$1$1 r6 = new com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$installerLabelFlow$1$1
                                        com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter r8 = r8.this$0
                                        r6.<init>(r8, r9, r5)
                                        r0.L$0 = r10
                                        r0.label = r4
                                        java.lang.Object r8 = kotlinx.coroutines.BuildersKt.withContext(r2, r6, r0)
                                        if (r8 != r1) goto L5a
                                        return r1
                                    L5a:
                                        r7 = r10
                                        r10 = r8
                                        r8 = r7
                                    L5d:
                                        r7 = r10
                                        r10 = r8
                                        r8 = r7
                                    L60:
                                        r0.L$0 = r5
                                        r0.label = r3
                                        java.lang.Object r8 = r10.emit(r8, r0)
                                        if (r8 != r1) goto L6b
                                        return r1
                                    L6b:
                                        kotlin.Unit r8 = kotlin.Unit.INSTANCE
                                        return r8
                                    */
                                    throw new UnsupportedOperationException(
                                            "Method not decompiled:"
                                                + " com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                                + " kotlin.coroutines.Continuation):java.lang.Object");
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(
                                    FlowCollector flowCollector, Continuation continuation) {
                                switch (i4) {
                                    case 0:
                                        Object collect =
                                                sharedFlow.collect(
                                                        new AnonymousClass2(flowCollector, this),
                                                        continuation);
                                        if (collect != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                            break;
                                        }
                                        break;
                                    case 1:
                                        Object collect2 =
                                                sharedFlow.collect(
                                                        new AppInstallerInfoPresenter$special$$inlined$map$2$2(
                                                                flowCollector, this),
                                                        continuation);
                                        if (collect2 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                            break;
                                        }
                                        break;
                                    case 2:
                                        Object collect3 =
                                                sharedFlow.collect(
                                                        new AppInstallerInfoPresenter$special$$inlined$map$3$2(
                                                                flowCollector, this),
                                                        continuation);
                                        if (collect3 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                            break;
                                        }
                                        break;
                                    default:
                                        Object collect4 =
                                                sharedFlow.collect(
                                                        new AppInstallerInfoPresenter$special$$inlined$map$4$2(
                                                                flowCollector, this),
                                                        continuation);
                                        if (collect4 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                            break;
                                        }
                                        break;
                                }
                                return Unit.INSTANCE;
                            }
                        });
        this.intentFlow = sharedFlow3;
        this.enabledFlow =
                new Flow() { // from class:
                    // com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$5

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    /* renamed from: com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$5$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                        @Metadata(
                                k = 3,
                                mv = {1, 9, 0},
                                xi = 48)
                        /* renamed from: com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$5$2$1, reason: invalid class name */
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
                                boolean r0 = r6 instanceof com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$5.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$5$2$1 r0 = (com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$5.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$5$2$1 r0 = new com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$5$2$1
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
                                android.content.Intent r5 = (android.content.Intent) r5
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
                                        + " com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$5.AnonymousClass2.emit(java.lang.Object,"
                                        + " kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(
                            FlowCollector flowCollector, Continuation continuation) {
                        Object collect =
                                sharedFlow3.collect(
                                        new AnonymousClass2(flowCollector), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED
                                ? collect
                                : Unit.INSTANCE;
                    }
                };
    }

    public final ReadonlySharedFlow sharedFlow(Flow flow) {
        return FlowKt.shareIn(
                flow, this.coroutineScope, SharingStarted.Companion.WhileSubscribed$default(), 1);
    }
}
