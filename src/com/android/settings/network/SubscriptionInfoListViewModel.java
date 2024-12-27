package com.android.settings.network;

import android.app.Application;
import android.telephony.SubscriptionManager;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelKt;

import com.android.settings.network.telephony.SubscriptionRepositoryKt;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SubscriptionInfoListViewModel extends AndroidViewModel {
    public final ContextScope scope;
    public final ReadonlyStateFlow selectableSubscriptionInfoListFlow;
    public final ReadonlyStateFlow subscriptionInfoListFlow;
    public final SubscriptionManager subscriptionManager;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SubscriptionInfoListViewModel(final Application application) {
        super(application);
        Intrinsics.checkNotNullParameter(application, "application");
        Object systemService = application.getSystemService(SubscriptionManager.class);
        Intrinsics.checkNotNull(systemService);
        this.subscriptionManager = (SubscriptionManager) systemService;
        ContextScope plus =
                CoroutineScopeKt.plus(ViewModelKt.getViewModelScope(this), Dispatchers.Default);
        final Flow subscriptionsChangedFlow =
                SubscriptionRepositoryKt.subscriptionsChangedFlow(application);
        final int i = 0;
        Flow flow = new Flow() { // from class:
                    // com.android.settings.network.SubscriptionInfoListViewModel$special$$inlined$map$1

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    /* renamed from: com.android.settings.network.SubscriptionInfoListViewModel$special$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ SubscriptionInfoListViewModel this$0;

                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                        @Metadata(
                                k = 3,
                                mv = {1, 9, 0},
                                xi = 48)
                        /* renamed from: com.android.settings.network.SubscriptionInfoListViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                                SubscriptionInfoListViewModel subscriptionInfoListViewModel) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = subscriptionInfoListViewModel;
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
                                boolean r0 = r6 instanceof com.android.settings.network.SubscriptionInfoListViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.settings.network.SubscriptionInfoListViewModel$special$$inlined$map$1$2$1 r0 = (com.android.settings.network.SubscriptionInfoListViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.settings.network.SubscriptionInfoListViewModel$special$$inlined$map$1$2$1 r0 = new com.android.settings.network.SubscriptionInfoListViewModel$special$$inlined$map$1$2$1
                                r0.<init>(r6)
                            L18:
                                java.lang.Object r6 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r6)
                                goto L47
                            L27:
                                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                r4.<init>(r5)
                                throw r4
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r6)
                                kotlin.Unit r5 = (kotlin.Unit) r5
                                com.android.settings.network.SubscriptionInfoListViewModel r5 = r4.this$0
                                android.telephony.SubscriptionManager r5 = r5.subscriptionManager
                                java.util.List r5 = com.android.settings.network.SubscriptionUtil.getActiveSubscriptions(r5)
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                java.lang.Object r4 = r4.emit(r5, r0)
                                if (r4 != r1) goto L47
                                return r1
                            L47:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.android.settings.network.SubscriptionInfoListViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                        + " kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(
                            FlowCollector flowCollector, Continuation continuation) {
                        switch (i) {
                            case 0:
                                Object collect =
                                        subscriptionsChangedFlow.collect(
                                                new AnonymousClass2(
                                                        flowCollector,
                                                        (SubscriptionInfoListViewModel) this),
                                                continuation);
                                if (collect != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                    break;
                                }
                                break;
                            default:
                                Object collect2 =
                                        subscriptionsChangedFlow.collect(
                                                new SubscriptionInfoListViewModel$special$$inlined$map$2$2(
                                                        flowCollector, (Application) this),
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
        EmptyList emptyList = EmptyList.INSTANCE;
        this.subscriptionInfoListFlow = FlowKt.stateIn(flow, plus, startedEagerly, emptyList);
        final Flow subscriptionsChangedFlow2 =
                SubscriptionRepositoryKt.subscriptionsChangedFlow(application);
        final int i2 = 1;
        this.selectableSubscriptionInfoListFlow =
                FlowKt.stateIn(
                        new Flow() { // from class:
                            // com.android.settings.network.SubscriptionInfoListViewModel$special$$inlined$map$1

                            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                            /* renamed from: com.android.settings.network.SubscriptionInfoListViewModel$special$$inlined$map$1$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                                public final /* synthetic */ SubscriptionInfoListViewModel this$0;

                                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                @Metadata(
                                        k = 3,
                                        mv = {1, 9, 0},
                                        xi = 48)
                                /* renamed from: com.android.settings.network.SubscriptionInfoListViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                                        SubscriptionInfoListViewModel
                                                subscriptionInfoListViewModel) {
                                    this.$this_unsafeFlow = flowCollector;
                                    this.this$0 = subscriptionInfoListViewModel;
                                }

                                @Override // kotlinx.coroutines.flow.FlowCollector
                                public final Object emit(Object obj, Continuation continuation) {
                                    /*
                                        this = this;
                                        boolean r0 = r6 instanceof com.android.settings.network.SubscriptionInfoListViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                        if (r0 == 0) goto L13
                                        r0 = r6
                                        com.android.settings.network.SubscriptionInfoListViewModel$special$$inlined$map$1$2$1 r0 = (com.android.settings.network.SubscriptionInfoListViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                        int r1 = r0.label
                                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                        r3 = r1 & r2
                                        if (r3 == 0) goto L13
                                        int r1 = r1 - r2
                                        r0.label = r1
                                        goto L18
                                    L13:
                                        com.android.settings.network.SubscriptionInfoListViewModel$special$$inlined$map$1$2$1 r0 = new com.android.settings.network.SubscriptionInfoListViewModel$special$$inlined$map$1$2$1
                                        r0.<init>(r6)
                                    L18:
                                        java.lang.Object r6 = r0.result
                                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                        int r2 = r0.label
                                        r3 = 1
                                        if (r2 == 0) goto L2f
                                        if (r2 != r3) goto L27
                                        kotlin.ResultKt.throwOnFailure(r6)
                                        goto L47
                                    L27:
                                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                        r4.<init>(r5)
                                        throw r4
                                    L2f:
                                        kotlin.ResultKt.throwOnFailure(r6)
                                        kotlin.Unit r5 = (kotlin.Unit) r5
                                        com.android.settings.network.SubscriptionInfoListViewModel r5 = r4.this$0
                                        android.telephony.SubscriptionManager r5 = r5.subscriptionManager
                                        java.util.List r5 = com.android.settings.network.SubscriptionUtil.getActiveSubscriptions(r5)
                                        r0.label = r3
                                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                        java.lang.Object r4 = r4.emit(r5, r0)
                                        if (r4 != r1) goto L47
                                        return r1
                                    L47:
                                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                        return r4
                                    */
                                    throw new UnsupportedOperationException(
                                            "Method not decompiled:"
                                                + " com.android.settings.network.SubscriptionInfoListViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                                + " kotlin.coroutines.Continuation):java.lang.Object");
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(
                                    FlowCollector flowCollector, Continuation continuation) {
                                switch (i2) {
                                    case 0:
                                        Object collect =
                                                subscriptionsChangedFlow2.collect(
                                                        new AnonymousClass2(
                                                                flowCollector,
                                                                (SubscriptionInfoListViewModel)
                                                                        application),
                                                        continuation);
                                        if (collect != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                            break;
                                        }
                                        break;
                                    default:
                                        Object collect2 =
                                                subscriptionsChangedFlow2.collect(
                                                        new SubscriptionInfoListViewModel$special$$inlined$map$2$2(
                                                                flowCollector,
                                                                (Application) application),
                                                        continuation);
                                        if (collect2 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                            break;
                                        }
                                        break;
                                }
                                return Unit.INSTANCE;
                            }
                        },
                        plus,
                        startedEagerly,
                        emptyList);
    }
}
