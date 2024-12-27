package com.android.settings.spa.app.appinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.UserHandle;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;

import com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags;
import com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlagsImpl;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.spa.app.AppUtilKt;
import com.android.settingslib.spa.framework.compose.NavControllerWrapper;
import com.android.settingslib.spa.framework.compose.NavControllerWrapperKt;
import com.android.settingslib.spaprivileged.framework.common.BroadcastReceiverAsUserFlowKt;
import com.android.settingslib.spaprivileged.framework.common.ContextsKt;
import com.android.settingslib.spaprivileged.framework.compose.DisposableBroadcastReceiverAsUserKt;
import com.android.settingslib.spaprivileged.model.app.IPackageManagers;
import com.android.settingslib.spaprivileged.model.app.PackageManagers;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PackageInfoPresenter {
    public final PackageInfoPresenter$special$$inlined$map$1 appChangeFlow;
    public final Context context;
    public final CoroutineScope coroutineScope;
    public final FeatureFlags featureFlags;
    public final ReadonlyStateFlow flow;
    public final SettingsMetricsFeatureProvider metricsFeatureProvider;
    public final IPackageManagers packageManagers;
    public final String packageName;
    public final Lazy userContext$delegate;
    public final UserHandle userHandle;
    public final int userId;
    public final Lazy userPackageManager$delegate;

    public PackageInfoPresenter(Context context, String str, int i, ContextScope contextScope) {
        PackageManagers packageManagers = PackageManagers.INSTANCE;
        FeatureFlagsImpl featureFlagsImpl = new FeatureFlagsImpl();
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(packageManagers, "packageManagers");
        this.context = context;
        this.packageName = str;
        this.userId = i;
        this.coroutineScope = contextScope;
        this.packageManagers = packageManagers;
        this.featureFlags = featureFlagsImpl;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.metricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
        UserHandle userHandle = UserHandle.of(i);
        this.userHandle = userHandle;
        this.userContext$delegate =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.android.settings.spa.app.appinfo.PackageInfoPresenter$userContext$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                PackageInfoPresenter packageInfoPresenter =
                                        PackageInfoPresenter.this;
                                Context context2 = packageInfoPresenter.context;
                                UserHandle userHandle2 = packageInfoPresenter.userHandle;
                                Intrinsics.checkNotNullExpressionValue(
                                        userHandle2, "access$getUserHandle$p(...)");
                                return ContextsKt.asUser(context2, userHandle2);
                            }
                        });
        this.userPackageManager$delegate =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.android.settings.spa.app.appinfo.PackageInfoPresenter$userPackageManager$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                return ((Context)
                                                PackageInfoPresenter.this.userContext$delegate
                                                        .getValue())
                                        .getPackageManager();
                            }
                        });
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addAction("android.intent.action.PACKAGE_RESTARTED");
        intentFilter.addDataScheme("package");
        Intrinsics.checkNotNullExpressionValue(userHandle, "userHandle");
        final Flow broadcastReceiverAsUserFlow =
                BroadcastReceiverAsUserFlowKt.broadcastReceiverAsUserFlow(
                        context, intentFilter, userHandle);
        final int i2 = 1;
        final Flow flow = new Flow() { // from class:
                    // com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$map$1

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    /* renamed from: com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ PackageInfoPresenter this$0;

                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                        @Metadata(
                                k = 3,
                                mv = {1, 9, 0},
                                xi = 48)
                        /* renamed from: com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$map$1$2$1, reason: invalid class name */
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
                                PackageInfoPresenter packageInfoPresenter) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = packageInfoPresenter;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(
                                java.lang.Object r9, kotlin.coroutines.Continuation r10) {
                            /*
                                r8 = this;
                                boolean r0 = r10 instanceof com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r10
                                com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$map$1$2$1 r0 = (com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$map$1$2$1 r0 = new com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$map$1$2$1
                                r0.<init>(r10)
                            L18:
                                java.lang.Object r10 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r10)
                                goto L64
                            L27:
                                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                                java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                                r8.<init>(r9)
                                throw r8
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r10)
                                android.content.Intent r9 = (android.content.Intent) r9
                                com.android.settings.spa.app.appinfo.PackageInfoPresenter r9 = r8.this$0
                                com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags r10 = r9.featureFlags
                                java.lang.String r2 = "featureFlags"
                                kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r10, r2)
                                boolean r10 = r10.archiving()
                                if (r10 == 0) goto L49
                                r4 = 4294967296(0x100000000, double:2.121995791E-314)
                                goto L4b
                            L49:
                                r4 = 0
                            L4b:
                                r6 = 4198912(0x401200, double:2.074538E-317)
                                long r4 = r4 | r6
                                int r10 = r9.userId
                                com.android.settingslib.spaprivileged.model.app.IPackageManagers r2 = r9.packageManagers
                                java.lang.String r9 = r9.packageName
                                android.content.pm.PackageInfo r9 = r2.getPackageInfoAsUser(r4, r10, r9)
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r8 = r8.$this_unsafeFlow
                                java.lang.Object r8 = r8.emit(r9, r0)
                                if (r8 != r1) goto L64
                                return r1
                            L64:
                                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                                return r8
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                        + " kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(
                            FlowCollector flowCollector, Continuation continuation) {
                        switch (i2) {
                            case 0:
                                Object collect =
                                        broadcastReceiverAsUserFlow.collect(
                                                new AnonymousClass2(flowCollector, this),
                                                continuation);
                                if (collect != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                    break;
                                }
                                break;
                            case 1:
                                Object collect2 =
                                        broadcastReceiverAsUserFlow.collect(
                                                new PackageInfoPresenter$special$$inlined$filter$1$2(
                                                        flowCollector, this),
                                                continuation);
                                if (collect2 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                    break;
                                }
                                break;
                            default:
                                Object collect3 =
                                        broadcastReceiverAsUserFlow.collect(
                                                new PackageInfoPresenter$special$$inlined$filter$2$2(
                                                        flowCollector, this),
                                                continuation);
                                if (collect3 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                    break;
                                }
                                break;
                        }
                        return Unit.INSTANCE;
                    }
                };
        final int i3 = 2;
        final ChannelLimitedFlowMerge merge =
                FlowKt.merge(
                        new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null),
                        new Flow() { // from class:
                            // com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$map$1

                            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                            /* renamed from: com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$map$1$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                                public final /* synthetic */ PackageInfoPresenter this$0;

                                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                @Metadata(
                                        k = 3,
                                        mv = {1, 9, 0},
                                        xi = 48)
                                /* renamed from: com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$map$1$2$1, reason: invalid class name */
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
                                        PackageInfoPresenter packageInfoPresenter) {
                                    this.$this_unsafeFlow = flowCollector;
                                    this.this$0 = packageInfoPresenter;
                                }

                                @Override // kotlinx.coroutines.flow.FlowCollector
                                public final Object emit(Object obj, Continuation continuation) {
                                    /*
                                        this = this;
                                        boolean r0 = r10 instanceof com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                        if (r0 == 0) goto L13
                                        r0 = r10
                                        com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$map$1$2$1 r0 = (com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                        int r1 = r0.label
                                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                        r3 = r1 & r2
                                        if (r3 == 0) goto L13
                                        int r1 = r1 - r2
                                        r0.label = r1
                                        goto L18
                                    L13:
                                        com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$map$1$2$1 r0 = new com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$map$1$2$1
                                        r0.<init>(r10)
                                    L18:
                                        java.lang.Object r10 = r0.result
                                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                        int r2 = r0.label
                                        r3 = 1
                                        if (r2 == 0) goto L2f
                                        if (r2 != r3) goto L27
                                        kotlin.ResultKt.throwOnFailure(r10)
                                        goto L64
                                    L27:
                                        java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                                        java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                                        r8.<init>(r9)
                                        throw r8
                                    L2f:
                                        kotlin.ResultKt.throwOnFailure(r10)
                                        android.content.Intent r9 = (android.content.Intent) r9
                                        com.android.settings.spa.app.appinfo.PackageInfoPresenter r9 = r8.this$0
                                        com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags r10 = r9.featureFlags
                                        java.lang.String r2 = "featureFlags"
                                        kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r10, r2)
                                        boolean r10 = r10.archiving()
                                        if (r10 == 0) goto L49
                                        r4 = 4294967296(0x100000000, double:2.121995791E-314)
                                        goto L4b
                                    L49:
                                        r4 = 0
                                    L4b:
                                        r6 = 4198912(0x401200, double:2.074538E-317)
                                        long r4 = r4 | r6
                                        int r10 = r9.userId
                                        com.android.settingslib.spaprivileged.model.app.IPackageManagers r2 = r9.packageManagers
                                        java.lang.String r9 = r9.packageName
                                        android.content.pm.PackageInfo r9 = r2.getPackageInfoAsUser(r4, r10, r9)
                                        r0.label = r3
                                        kotlinx.coroutines.flow.FlowCollector r8 = r8.$this_unsafeFlow
                                        java.lang.Object r8 = r8.emit(r9, r0)
                                        if (r8 != r1) goto L64
                                        return r1
                                    L64:
                                        kotlin.Unit r8 = kotlin.Unit.INSTANCE
                                        return r8
                                    */
                                    throw new UnsupportedOperationException(
                                            "Method not decompiled:"
                                                + " com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                                + " kotlin.coroutines.Continuation):java.lang.Object");
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(
                                    FlowCollector flowCollector, Continuation continuation) {
                                switch (i3) {
                                    case 0:
                                        Object collect =
                                                flow.collect(
                                                        new AnonymousClass2(flowCollector, this),
                                                        continuation);
                                        if (collect != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                            break;
                                        }
                                        break;
                                    case 1:
                                        Object collect2 =
                                                flow.collect(
                                                        new PackageInfoPresenter$special$$inlined$filter$1$2(
                                                                flowCollector, this),
                                                        continuation);
                                        if (collect2 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                            break;
                                        }
                                        break;
                                    default:
                                        Object collect3 =
                                                flow.collect(
                                                        new PackageInfoPresenter$special$$inlined$filter$2$2(
                                                                flowCollector, this),
                                                        continuation);
                                        if (collect3 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                            break;
                                        }
                                        break;
                                }
                                return Unit.INSTANCE;
                            }
                        });
        final int i4 = 0;
        this.flow =
                FlowKt.stateIn(
                        new Flow() { // from class:
                            // com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$map$1

                            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                            /* renamed from: com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$map$1$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                                public final /* synthetic */ PackageInfoPresenter this$0;

                                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                @Metadata(
                                        k = 3,
                                        mv = {1, 9, 0},
                                        xi = 48)
                                /* renamed from: com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$map$1$2$1, reason: invalid class name */
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
                                        PackageInfoPresenter packageInfoPresenter) {
                                    this.$this_unsafeFlow = flowCollector;
                                    this.this$0 = packageInfoPresenter;
                                }

                                @Override // kotlinx.coroutines.flow.FlowCollector
                                public final Object emit(Object obj, Continuation continuation) {
                                    /*
                                        this = this;
                                        boolean r0 = r10 instanceof com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                        if (r0 == 0) goto L13
                                        r0 = r10
                                        com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$map$1$2$1 r0 = (com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                        int r1 = r0.label
                                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                        r3 = r1 & r2
                                        if (r3 == 0) goto L13
                                        int r1 = r1 - r2
                                        r0.label = r1
                                        goto L18
                                    L13:
                                        com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$map$1$2$1 r0 = new com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$map$1$2$1
                                        r0.<init>(r10)
                                    L18:
                                        java.lang.Object r10 = r0.result
                                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                        int r2 = r0.label
                                        r3 = 1
                                        if (r2 == 0) goto L2f
                                        if (r2 != r3) goto L27
                                        kotlin.ResultKt.throwOnFailure(r10)
                                        goto L64
                                    L27:
                                        java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                                        java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                                        r8.<init>(r9)
                                        throw r8
                                    L2f:
                                        kotlin.ResultKt.throwOnFailure(r10)
                                        android.content.Intent r9 = (android.content.Intent) r9
                                        com.android.settings.spa.app.appinfo.PackageInfoPresenter r9 = r8.this$0
                                        com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags r10 = r9.featureFlags
                                        java.lang.String r2 = "featureFlags"
                                        kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r10, r2)
                                        boolean r10 = r10.archiving()
                                        if (r10 == 0) goto L49
                                        r4 = 4294967296(0x100000000, double:2.121995791E-314)
                                        goto L4b
                                    L49:
                                        r4 = 0
                                    L4b:
                                        r6 = 4198912(0x401200, double:2.074538E-317)
                                        long r4 = r4 | r6
                                        int r10 = r9.userId
                                        com.android.settingslib.spaprivileged.model.app.IPackageManagers r2 = r9.packageManagers
                                        java.lang.String r9 = r9.packageName
                                        android.content.pm.PackageInfo r9 = r2.getPackageInfoAsUser(r4, r10, r9)
                                        r0.label = r3
                                        kotlinx.coroutines.flow.FlowCollector r8 = r8.$this_unsafeFlow
                                        java.lang.Object r8 = r8.emit(r9, r0)
                                        if (r8 != r1) goto L64
                                        return r1
                                    L64:
                                        kotlin.Unit r8 = kotlin.Unit.INSTANCE
                                        return r8
                                    */
                                    throw new UnsupportedOperationException(
                                            "Method not decompiled:"
                                                + " com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                                + " kotlin.coroutines.Continuation):java.lang.Object");
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(
                                    FlowCollector flowCollector, Continuation continuation) {
                                switch (i4) {
                                    case 0:
                                        Object collect =
                                                merge.collect(
                                                        new AnonymousClass2(flowCollector, this),
                                                        continuation);
                                        if (collect != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                            break;
                                        }
                                        break;
                                    case 1:
                                        Object collect2 =
                                                merge.collect(
                                                        new PackageInfoPresenter$special$$inlined$filter$1$2(
                                                                flowCollector, this),
                                                        continuation);
                                        if (collect2 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                            break;
                                        }
                                        break;
                                    default:
                                        Object collect3 =
                                                merge.collect(
                                                        new PackageInfoPresenter$special$$inlined$filter$2$2(
                                                                flowCollector, this),
                                                        continuation);
                                        if (collect3 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                            break;
                                        }
                                        break;
                                }
                                return Unit.INSTANCE;
                            }
                        },
                        CoroutineScopeKt.plus(contextScope, Dispatchers.Default),
                        SharingStarted.Companion.Eagerly,
                        null);
    }

    public final void PackageFullyRemovedEffect(Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1584733684);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_FULLY_REMOVED");
        intentFilter.addDataScheme("package");
        final NavControllerWrapper navControllerWrapper =
                (NavControllerWrapper)
                        composerImpl.consume(NavControllerWrapperKt.LocalNavController);
        UserHandle userHandle = this.userHandle;
        Intrinsics.checkNotNullExpressionValue(userHandle, "userHandle");
        DisposableBroadcastReceiverAsUserKt.DisposableBroadcastReceiverAsUser(
                intentFilter,
                userHandle,
                new Function1() { // from class:
                                  // com.android.settings.spa.app.appinfo.PackageInfoPresenter$PackageFullyRemovedEffect$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        Intent intent = (Intent) obj;
                        Intrinsics.checkNotNullParameter(intent, "intent");
                        PackageInfoPresenter packageInfoPresenter = PackageInfoPresenter.this;
                        packageInfoPresenter.getClass();
                        Uri data = intent.getData();
                        if (Intrinsics.areEqual(
                                packageInfoPresenter.packageName,
                                data != null ? data.getSchemeSpecificPart() : null)) {
                            navControllerWrapper.navigateBack();
                        }
                        return Unit.INSTANCE;
                    }
                },
                composerImpl,
                72);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.appinfo.PackageInfoPresenter$PackageFullyRemovedEffect$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            PackageInfoPresenter.this.PackageFullyRemovedEffect(
                                    (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public final PackageManager getUserPackageManager() {
        Object value = this.userPackageManager$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (PackageManager) value;
    }

    public final boolean isInterestedAppChange(Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        return !Intrinsics.areEqual(intent.getAction(), "android.intent.action.PACKAGE_REMOVED")
                || intent.getBooleanExtra("android.intent.extra.ARCHIVAL", false);
    }

    public final void logAction(int i) {
        this.metricsFeatureProvider.action(this.context, i, this.packageName);
    }

    public final void startUninstallActivity(boolean z) {
        logAction(872);
        Context context = this.context;
        UserHandle userHandle = this.userHandle;
        Intrinsics.checkNotNullExpressionValue(userHandle, "userHandle");
        AppUtilKt.startUninstallActivity(context, this.packageName, userHandle, z);
    }
}
