package com.android.settings.spa.app.appinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.android.settings.datausage.lib.INetworkTemplates;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppDataUsagePresenter {
    public final ApplicationInfo app;
    public final Context context;
    public final SafeFlow isAvailableFlow;
    public final Function2 repositoryFactory;
    public final AppDataUsagePresenter$special$$inlined$map$2 summaryFlow;
    public final ReadonlySharedFlow templateFlow;
    public final AppDataUsagePresenter$special$$inlined$map$1 titleResIdFlow;

    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.settings.spa.app.appinfo.AppDataUsagePresenter$special$$inlined$map$1] */
    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.settings.spa.app.appinfo.AppDataUsagePresenter$special$$inlined$map$2] */
    public AppDataUsagePresenter(
            Context context,
            ApplicationInfo app,
            ContextScope contextScope,
            INetworkTemplates networkTemplates,
            Function2 repositoryFactory) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(app, "app");
        Intrinsics.checkNotNullParameter(networkTemplates, "networkTemplates");
        Intrinsics.checkNotNullParameter(repositoryFactory, "repositoryFactory");
        this.context = context;
        this.app = app;
        this.repositoryFactory = repositoryFactory;
        this.isAvailableFlow =
                new SafeFlow(new AppDataUsagePresenter$isAvailableFlow$1(this, null));
        final ReadonlySharedFlow shareIn =
                FlowKt.shareIn(
                        new SafeFlow(
                                new AppDataUsagePresenter$templateFlow$1(
                                        networkTemplates, this, null)),
                        contextScope,
                        SharingStarted.Companion.WhileSubscribed$default(),
                        1);
        this.titleResIdFlow =
                new Flow() { // from class:
                    // com.android.settings.spa.app.appinfo.AppDataUsagePresenter$special$$inlined$map$1

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    /* renamed from: com.android.settings.spa.app.appinfo.AppDataUsagePresenter$special$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                        @Metadata(
                                k = 3,
                                mv = {1, 9, 0},
                                xi = 48)
                        /* renamed from: com.android.settings.spa.app.appinfo.AppDataUsagePresenter$special$$inlined$map$1$2$1, reason: invalid class name */
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
                                boolean r0 = r6 instanceof com.android.settings.spa.app.appinfo.AppDataUsagePresenter$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.settings.spa.app.appinfo.AppDataUsagePresenter$special$$inlined$map$1$2$1 r0 = (com.android.settings.spa.app.appinfo.AppDataUsagePresenter$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.settings.spa.app.appinfo.AppDataUsagePresenter$special$$inlined$map$1$2$1 r0 = new com.android.settings.spa.app.appinfo.AppDataUsagePresenter$special$$inlined$map$1$2$1
                                r0.<init>(r6)
                            L18:
                                java.lang.Object r6 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r6)
                                goto L68
                            L27:
                                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                r4.<init>(r5)
                                throw r4
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r6)
                                android.net.NetworkTemplate r5 = (android.net.NetworkTemplate) r5
                                java.lang.String r6 = "<this>"
                                kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r6)
                                int r5 = r5.getMatchRule()
                                if (r5 == r3) goto L55
                                r6 = 10
                                if (r5 == r6) goto L55
                                r6 = 4
                                if (r5 == r6) goto L51
                                r6 = 5
                                if (r5 == r6) goto L4d
                                r5 = 2132020098(0x7f140b82, float:1.967855E38)
                                goto L58
                            L4d:
                                r5 = 2132020834(0x7f140e62, float:1.9680042E38)
                                goto L58
                            L51:
                                r5 = 2132030598(0x7f143486, float:1.9699846E38)
                                goto L58
                            L55:
                                r5 = 2132019460(0x7f140904, float:1.9677256E38)
                            L58:
                                java.lang.Integer r6 = new java.lang.Integer
                                r6.<init>(r5)
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                java.lang.Object r4 = r4.emit(r6, r0)
                                if (r4 != r1) goto L68
                                return r1
                            L68:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.android.settings.spa.app.appinfo.AppDataUsagePresenter$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
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
                new Flow() { // from class:
                    // com.android.settings.spa.app.appinfo.AppDataUsagePresenter$special$$inlined$map$2

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    /* renamed from: com.android.settings.spa.app.appinfo.AppDataUsagePresenter$special$$inlined$map$2$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ AppDataUsagePresenter this$0;

                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                        @Metadata(
                                k = 3,
                                mv = {1, 9, 0},
                                xi = 48)
                        /* renamed from: com.android.settings.spa.app.appinfo.AppDataUsagePresenter$special$$inlined$map$2$2$1, reason: invalid class name */
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
                                AppDataUsagePresenter appDataUsagePresenter) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = appDataUsagePresenter;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:19:0x005c A[RETURN] */
                        /* JADX WARN: Removed duplicated region for block: B:20:0x003a  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(
                                java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                            /*
                                r6 = this;
                                boolean r0 = r8 instanceof com.android.settings.spa.app.appinfo.AppDataUsagePresenter$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r8
                                com.android.settings.spa.app.appinfo.AppDataUsagePresenter$special$$inlined$map$2$2$1 r0 = (com.android.settings.spa.app.appinfo.AppDataUsagePresenter$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.settings.spa.app.appinfo.AppDataUsagePresenter$special$$inlined$map$2$2$1 r0 = new com.android.settings.spa.app.appinfo.AppDataUsagePresenter$special$$inlined$map$2$2$1
                                r0.<init>(r8)
                            L18:
                                java.lang.Object r8 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 2
                                r4 = 1
                                if (r2 == 0) goto L3a
                                if (r2 == r4) goto L32
                                if (r2 != r3) goto L2a
                                kotlin.ResultKt.throwOnFailure(r8)
                                goto L5d
                            L2a:
                                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                                java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                                r6.<init>(r7)
                                throw r6
                            L32:
                                java.lang.Object r6 = r0.L$0
                                kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
                                kotlin.ResultKt.throwOnFailure(r8)
                                goto L51
                            L3a:
                                kotlin.ResultKt.throwOnFailure(r8)
                                android.net.NetworkTemplate r7 = (android.net.NetworkTemplate) r7
                                kotlinx.coroutines.flow.FlowCollector r8 = r6.$this_unsafeFlow
                                r0.L$0 = r8
                                r0.label = r4
                                com.android.settings.spa.app.appinfo.AppDataUsagePresenter r6 = r6.this$0
                                java.lang.Object r6 = com.android.settings.spa.app.appinfo.AppDataUsagePresenter.access$getSummary(r6, r7, r0)
                                if (r6 != r1) goto L4e
                                return r1
                            L4e:
                                r5 = r8
                                r8 = r6
                                r6 = r5
                            L51:
                                r7 = 0
                                r0.L$0 = r7
                                r0.label = r3
                                java.lang.Object r6 = r6.emit(r8, r0)
                                if (r6 != r1) goto L5d
                                return r1
                            L5d:
                                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                                return r6
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.android.settings.spa.app.appinfo.AppDataUsagePresenter$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object,"
                                        + " kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(
                            FlowCollector flowCollector, Continuation continuation) {
                        Object collect =
                                shareIn.collect(
                                        new AnonymousClass2(flowCollector, this), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED
                                ? collect
                                : Unit.INSTANCE;
                    }
                };
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$getSummary(
            com.android.settings.spa.app.appinfo.AppDataUsagePresenter r5,
            android.net.NetworkTemplate r6,
            kotlin.coroutines.Continuation r7) {
        /*
            r5.getClass()
            boolean r0 = r7 instanceof com.android.settings.spa.app.appinfo.AppDataUsagePresenter$getSummary$1
            if (r0 == 0) goto L16
            r0 = r7
            com.android.settings.spa.app.appinfo.AppDataUsagePresenter$getSummary$1 r0 = (com.android.settings.spa.app.appinfo.AppDataUsagePresenter$getSummary$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.settings.spa.app.appinfo.AppDataUsagePresenter$getSummary$1 r0 = new com.android.settings.spa.app.appinfo.AppDataUsagePresenter$getSummary$1
            r0.<init>(r5, r7)
        L1b:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r7)
            goto L46
        L2a:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L32:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlinx.coroutines.scheduling.DefaultIoScheduler r7 = kotlinx.coroutines.Dispatchers.IO
            com.android.settings.spa.app.appinfo.AppDataUsagePresenter$getSummary$2 r2 = new com.android.settings.spa.app.appinfo.AppDataUsagePresenter$getSummary$2
            r4 = 0
            r2.<init>(r5, r6, r4)
            r0.label = r3
            java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r7, r2, r0)
            if (r7 != r1) goto L46
            goto L4c
        L46:
            java.lang.String r5 = "withContext(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r7, r5)
            r1 = r7
        L4c:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.spa.app.appinfo.AppDataUsagePresenter.access$getSummary(com.android.settings.spa.app.appinfo.AppDataUsagePresenter,"
                    + " android.net.NetworkTemplate,"
                    + " kotlin.coroutines.Continuation):java.lang.Object");
    }
}
