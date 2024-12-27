package com.android.settingslib.spaprivileged.model.app;

import android.app.Application;
import android.content.Context;
import android.icu.text.Collator;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelKt;

import com.android.settingslib.spa.framework.util.FlowsKt;
import com.android.settingslib.spa.framework.util.StateFlowBridge;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;
import kotlinx.coroutines.internal.ContextScope;

import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppListViewModelImpl extends AndroidViewModel {
    public final AppListViewModelImpl$special$$inlined$map$1 appEntryListFlow;
    public final StateFlowBridge appListConfig;
    public final ReadonlySharedFlow appListDataFlow;
    public final AppListRepository appListRepository;
    public final AppRepository appRepository;
    public final Collator collator;
    public final ReadonlySharedFlow combinedRecordListFlow;
    public final ConcurrentHashMap labelMap;
    public final StateFlowBridge listModel;
    public final StateFlowImpl optionFlow;
    public final ContextScope scope;
    public final StateFlowBridge searchQuery;
    public final StateFlowBridge showSystem;
    public final ReadonlySharedFlow spinnerOptionsFlow;
    public final ReadonlySharedFlow userSubGraphsFlow;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            k = 3,
            mv = {1, 9, 0},
            xi = 48)
    /* renamed from: com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$1, reason: invalid class name */
    final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function1 {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        public AnonymousClass1() {
            super(
                    1,
                    AppListRepositoryImpl.class,
                    "<init>",
                    "<init>(Landroid/content/Context;)V",
                    0);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            Context p0 = (Context) obj;
            Intrinsics.checkNotNullParameter(p0, "p0");
            return new AppListRepositoryImpl(p0);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            k = 3,
            mv = {1, 9, 0},
            xi = 48)
    /* renamed from: com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$2, reason: invalid class name */
    final /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function1 {
        public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

        public AnonymousClass2() {
            super(1, AppRepositoryImpl.class, "<init>", "<init>(Landroid/content/Context;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            Context p0 = (Context) obj;
            Intrinsics.checkNotNullParameter(p0, "p0");
            return new AppRepositoryImpl(p0);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class UserSubGraph {
        public final StateFlowImpl appsStateFlow;
        public final ReadonlySharedFlow listModelFilteredFlow;
        public final boolean matchAnyUserForAdmin;
        public final ReadonlySharedFlow recordListFlow;
        public final boolean showInstantApps;
        public final ReadonlySharedFlow systemFilteredFlow;
        public final int userId;
        public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 userIdFlow;

        public UserSubGraph(int i, boolean z, boolean z2) {
            this.userId = i;
            this.showInstantApps = z;
            this.matchAnyUserForAdmin = z2;
            FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 =
                            new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Integer.valueOf(i));
            this.userIdFlow = flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
            this.appsStateFlow = StateFlowKt.MutableStateFlow(null);
            ChannelFlowTransformLatest transformLatest =
                    FlowKt.transformLatest(
                            AppListViewModelImpl.this.listModel.flow,
                            new AppListViewModelImpl$UserSubGraph$special$$inlined$flatMapLatest$1(
                                    null, this));
            StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
            ContextScope contextScope = AppListViewModelImpl.this.scope;
            ReadonlySharedFlow shareIn =
                    FlowKt.shareIn(transformLatest, contextScope, startedEagerly, 1);
            this.recordListFlow = shareIn;
            FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 showSystemFlow =
                    AppListViewModelImpl.this.showSystem.flow;
            AppListRepositoryImpl appListRepositoryImpl =
                    (AppListRepositoryImpl) AppListViewModelImpl.this.appListRepository;
            appListRepositoryImpl.getClass();
            Intrinsics.checkNotNullParameter(showSystemFlow, "showSystemFlow");
            this.systemFilteredFlow =
                    FlowKt.shareIn(
                            FlowKt.flowCombine(
                                    FlowKt.flowCombine(
                                            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2,
                                            showSystemFlow,
                                            new AppListRepositoryImpl$showSystemPredicate$1(
                                                    3,
                                                    appListRepositoryImpl,
                                                    AppListRepositoryImpl.class,
                                                    "showSystemPredicate",
                                                    "showSystemPredicate(IZLkotlin/coroutines/Continuation;)Ljava/lang/Object;",
                                                    0)),
                                    shareIn,
                                    new AppListViewModelImpl$UserSubGraph$systemFilteredFlow$1(
                                            3, null)),
                            contextScope,
                            startedEagerly,
                            1);
            this.listModelFilteredFlow =
                    FlowKt.shareIn(
                            FlowKt.transformLatest(
                                    FlowKt.filterNotNull(AppListViewModelImpl.this.optionFlow),
                                    new AppListViewModelImpl$UserSubGraph$special$$inlined$flatMapLatest$2(
                                            this, AppListViewModelImpl.this, null)),
                            contextScope,
                            startedEagerly,
                            1);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppListViewModelImpl(Application application) {
        super(application);
        AnonymousClass1 appListRepositoryFactory = AnonymousClass1.INSTANCE;
        AnonymousClass2 appRepositoryFactory = AnonymousClass2.INSTANCE;
        Intrinsics.checkNotNullParameter(application, "application");
        Intrinsics.checkNotNullParameter(appListRepositoryFactory, "appListRepositoryFactory");
        Intrinsics.checkNotNullParameter(appRepositoryFactory, "appRepositoryFactory");
        StateFlowBridge stateFlowBridge = new StateFlowBridge();
        this.appListConfig = stateFlowBridge;
        StateFlowBridge stateFlowBridge2 = new StateFlowBridge();
        this.listModel = stateFlowBridge2;
        this.showSystem = new StateFlowBridge();
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this.optionFlow = MutableStateFlow;
        StateFlowBridge stateFlowBridge3 = new StateFlowBridge();
        this.searchQuery = stateFlowBridge3;
        this.appListRepository = (AppListRepository) appListRepositoryFactory.invoke(application);
        this.appRepository = (AppRepository) appRepositoryFactory.invoke(application);
        this.collator = Collator.getInstance().freeze();
        this.labelMap = new ConcurrentHashMap();
        ContextScope plus =
                CoroutineScopeKt.plus(ViewModelKt.getViewModelScope(this), Dispatchers.IO);
        this.scope = plus;
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1
                flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = stateFlowBridge.flow;
        final int i = 0;
        Flow flow = new Flow() { // from class:
                    // com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$special$$inlined$map$1

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    /* renamed from: com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$special$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ AppListViewModelImpl this$0;

                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                        @Metadata(
                                k = 3,
                                mv = {1, 9, 0},
                                xi = 48)
                        /* renamed from: com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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
                                AppListViewModelImpl appListViewModelImpl) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = appListViewModelImpl;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(
                                java.lang.Object r10, kotlin.coroutines.Continuation r11) {
                            /*
                                r9 = this;
                                boolean r0 = r11 instanceof com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r11
                                com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$special$$inlined$map$1$2$1 r0 = (com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$special$$inlined$map$1$2$1 r0 = new com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$special$$inlined$map$1$2$1
                                r0.<init>(r11)
                            L18:
                                java.lang.Object r11 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r11)
                                goto L71
                            L27:
                                java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                                java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
                                r9.<init>(r10)
                                throw r9
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r11)
                                com.android.settingslib.spaprivileged.template.app.AppListConfig r10 = (com.android.settingslib.spaprivileged.template.app.AppListConfig) r10
                                java.util.List r11 = r10.userIds
                                java.lang.Iterable r11 = (java.lang.Iterable) r11
                                java.util.ArrayList r2 = new java.util.ArrayList
                                r4 = 10
                                int r4 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r11, r4)
                                r2.<init>(r4)
                                java.util.Iterator r11 = r11.iterator()
                            L47:
                                boolean r4 = r11.hasNext()
                                if (r4 == 0) goto L66
                                java.lang.Object r4 = r11.next()
                                java.lang.Number r4 = (java.lang.Number) r4
                                int r4 = r4.intValue()
                                com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$UserSubGraph r5 = new com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$UserSubGraph
                                com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl r6 = r9.this$0
                                boolean r7 = r10.showInstantApps
                                boolean r8 = r10.matchAnyUserForAdmin
                                r5.<init>(r4, r7, r8)
                                r2.add(r5)
                                goto L47
                            L66:
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r9 = r9.$this_unsafeFlow
                                java.lang.Object r9 = r9.emit(r2, r0)
                                if (r9 != r1) goto L71
                                return r1
                            L71:
                                kotlin.Unit r9 = kotlin.Unit.INSTANCE
                                return r9
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                        + " kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(
                            FlowCollector flowCollector, Continuation continuation) {
                        switch (i) {
                            case 0:
                                Object collect =
                                        flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1
                                                .collect(
                                                        new AnonymousClass2(flowCollector, this),
                                                        continuation);
                                if (collect != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                    break;
                                }
                                break;
                            default:
                                Object collect2 =
                                        flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1
                                                .collect(
                                                        new AppListViewModelImpl$special$$inlined$asyncMapItem$1$2(
                                                                flowCollector, this),
                                                        continuation);
                                if (collect2 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                    break;
                                }
                                break;
                        }
                        return Unit.INSTANCE;
                    }
                };
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        ReadonlySharedFlow shareIn = FlowKt.shareIn(flow, plus, startedEagerly, 1);
        this.userSubGraphsFlow = shareIn;
        ReadonlySharedFlow shareIn2 =
                FlowKt.shareIn(
                        FlowKt.transformLatest(
                                shareIn,
                                new AppListViewModelImpl$special$$inlined$flatMapLatest$1(3, null)),
                        plus,
                        startedEagerly,
                        1);
        AppListViewModelImpl$spinnerOptionsFlow$1 appListViewModelImpl$spinnerOptionsFlow$1 =
                new AppListViewModelImpl$spinnerOptionsFlow$1(3, null);
        FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1
                flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$12 =
                        stateFlowBridge2.flow;
        this.spinnerOptionsFlow =
                FlowKt.shareIn(
                        FlowKt.flowCombine(
                                shareIn2,
                                flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$12,
                                appListViewModelImpl$spinnerOptionsFlow$1),
                        plus,
                        startedEagerly,
                        1);
        final ChannelFlowTransformLatest transformLatest =
                FlowKt.transformLatest(
                        shareIn,
                        new AppListViewModelImpl$special$$inlined$flatMapLatest$2(3, null));
        final int i2 = 1;
        ReadonlySharedFlow shareIn3 =
                FlowKt.shareIn(
                        FlowKt.flowCombine(
                                FlowKt.combine(
                                        new Flow() { // from class:
                                            // com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$special$$inlined$map$1

                                            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                            /* renamed from: com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$special$$inlined$map$1$2, reason: invalid class name */
                                            public final class AnonymousClass2
                                                    implements FlowCollector {
                                                public final /* synthetic */ FlowCollector
                                                        $this_unsafeFlow;
                                                public final /* synthetic */ AppListViewModelImpl
                                                        this$0;

                                                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                                @Metadata(
                                                        k = 3,
                                                        mv = {1, 9, 0},
                                                        xi = 48)
                                                /* renamed from: com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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
                                                        AppListViewModelImpl appListViewModelImpl) {
                                                    this.$this_unsafeFlow = flowCollector;
                                                    this.this$0 = appListViewModelImpl;
                                                }

                                                @Override // kotlinx.coroutines.flow.FlowCollector
                                                public final Object emit(
                                                        Object obj, Continuation continuation) {
                                                    /*
                                                        this = this;
                                                        boolean r0 = r11 instanceof com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                                        if (r0 == 0) goto L13
                                                        r0 = r11
                                                        com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$special$$inlined$map$1$2$1 r0 = (com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                                        int r1 = r0.label
                                                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                                        r3 = r1 & r2
                                                        if (r3 == 0) goto L13
                                                        int r1 = r1 - r2
                                                        r0.label = r1
                                                        goto L18
                                                    L13:
                                                        com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$special$$inlined$map$1$2$1 r0 = new com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$special$$inlined$map$1$2$1
                                                        r0.<init>(r11)
                                                    L18:
                                                        java.lang.Object r11 = r0.result
                                                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                                        int r2 = r0.label
                                                        r3 = 1
                                                        if (r2 == 0) goto L2f
                                                        if (r2 != r3) goto L27
                                                        kotlin.ResultKt.throwOnFailure(r11)
                                                        goto L71
                                                    L27:
                                                        java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                                                        java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
                                                        r9.<init>(r10)
                                                        throw r9
                                                    L2f:
                                                        kotlin.ResultKt.throwOnFailure(r11)
                                                        com.android.settingslib.spaprivileged.template.app.AppListConfig r10 = (com.android.settingslib.spaprivileged.template.app.AppListConfig) r10
                                                        java.util.List r11 = r10.userIds
                                                        java.lang.Iterable r11 = (java.lang.Iterable) r11
                                                        java.util.ArrayList r2 = new java.util.ArrayList
                                                        r4 = 10
                                                        int r4 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r11, r4)
                                                        r2.<init>(r4)
                                                        java.util.Iterator r11 = r11.iterator()
                                                    L47:
                                                        boolean r4 = r11.hasNext()
                                                        if (r4 == 0) goto L66
                                                        java.lang.Object r4 = r11.next()
                                                        java.lang.Number r4 = (java.lang.Number) r4
                                                        int r4 = r4.intValue()
                                                        com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$UserSubGraph r5 = new com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$UserSubGraph
                                                        com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl r6 = r9.this$0
                                                        boolean r7 = r10.showInstantApps
                                                        boolean r8 = r10.matchAnyUserForAdmin
                                                        r5.<init>(r4, r7, r8)
                                                        r2.add(r5)
                                                        goto L47
                                                    L66:
                                                        r0.label = r3
                                                        kotlinx.coroutines.flow.FlowCollector r9 = r9.$this_unsafeFlow
                                                        java.lang.Object r9 = r9.emit(r2, r0)
                                                        if (r9 != r1) goto L71
                                                        return r1
                                                    L71:
                                                        kotlin.Unit r9 = kotlin.Unit.INSTANCE
                                                        return r9
                                                    */
                                                    throw new UnsupportedOperationException(
                                                            "Method not decompiled:"
                                                                + " com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                                                + " kotlin.coroutines.Continuation):java.lang.Object");
                                                }
                                            }

                                            @Override // kotlinx.coroutines.flow.Flow
                                            public final Object collect(
                                                    FlowCollector flowCollector,
                                                    Continuation continuation) {
                                                switch (i2) {
                                                    case 0:
                                                        Object collect =
                                                                transformLatest.collect(
                                                                        new AnonymousClass2(
                                                                                flowCollector,
                                                                                this),
                                                                        continuation);
                                                        if (collect
                                                                != CoroutineSingletons
                                                                        .COROUTINE_SUSPENDED) {
                                                            break;
                                                        }
                                                        break;
                                                    default:
                                                        Object collect2 =
                                                                transformLatest.collect(
                                                                        new AppListViewModelImpl$special$$inlined$asyncMapItem$1$2(
                                                                                flowCollector,
                                                                                this),
                                                                        continuation);
                                                        if (collect2
                                                                != CoroutineSingletons
                                                                        .COROUTINE_SUSPENDED) {
                                                            break;
                                                        }
                                                        break;
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        },
                                        flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$12,
                                        FlowKt.filterNotNull(MutableStateFlow),
                                        new AppListViewModelImpl$appListDataFlow$1(4, null)),
                                stateFlowBridge3.flow,
                                new AppListViewModelImpl$appListDataFlow$2(3, null)),
                        plus,
                        startedEagerly,
                        1);
        this.appListDataFlow = shareIn3;
        FlowKt.launchIn(
                FlowKt.flowCombine(
                        FlowsKt.waitFirst(shareIn2, shareIn3),
                        flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$12,
                        new AppListViewModelImpl$scheduleOnFirstLoaded$1(this, null)),
                plus);
    }

    public final void reloadApps() {
        BuildersKt.launch$default(
                this.scope, null, null, new AppListViewModelImpl$reloadApps$1(this, null), 3);
    }
}
