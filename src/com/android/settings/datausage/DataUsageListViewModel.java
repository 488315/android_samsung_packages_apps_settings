package com.android.settings.datausage;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.lifecycle.viewmodel.internal.CloseableCoroutineScope;

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
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.internal.ContextScope;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DataUsageListViewModel extends AndroidViewModel {
    public final ReadonlyStateFlow bucketsFlow;
    public final Flow chartDataFlow;
    public final Flow cyclesFlow;
    public final ContextScope scope;
    public final StateFlowImpl selectedCycleFlow;
    public final StateFlowImpl templateFlow;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataUsageListViewModel(Application application) {
        super(application);
        Intrinsics.checkNotNullParameter(application, "application");
        CloseableCoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        ContextScope plus = CoroutineScopeKt.plus(viewModelScope, defaultScheduler);
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this.templateFlow = MutableStateFlow;
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 filterNotNull =
                FlowKt.filterNotNull(MutableStateFlow);
        ReadonlyStateFlow stateIn =
                FlowKt.stateIn(
                        new Flow() { // from class:
                            // com.android.settings.datausage.DataUsageListViewModel$special$$inlined$map$1

                            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                            /* renamed from: com.android.settings.datausage.DataUsageListViewModel$special$$inlined$map$1$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                                public final /* synthetic */ DataUsageListViewModel this$0;

                                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                @Metadata(
                                        k = 3,
                                        mv = {1, 9, 0},
                                        xi = 48)
                                /* renamed from: com.android.settings.datausage.DataUsageListViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                                        DataUsageListViewModel dataUsageListViewModel) {
                                    this.$this_unsafeFlow = flowCollector;
                                    this.this$0 = dataUsageListViewModel;
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
                                        boolean r0 = r6 instanceof com.android.settings.datausage.DataUsageListViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                        if (r0 == 0) goto L13
                                        r0 = r6
                                        com.android.settings.datausage.DataUsageListViewModel$special$$inlined$map$1$2$1 r0 = (com.android.settings.datausage.DataUsageListViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                        int r1 = r0.label
                                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                        r3 = r1 & r2
                                        if (r3 == 0) goto L13
                                        int r1 = r1 - r2
                                        r0.label = r1
                                        goto L18
                                    L13:
                                        com.android.settings.datausage.DataUsageListViewModel$special$$inlined$map$1$2$1 r0 = new com.android.settings.datausage.DataUsageListViewModel$special$$inlined$map$1$2$1
                                        r0.<init>(r6)
                                    L18:
                                        java.lang.Object r6 = r0.result
                                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                        int r2 = r0.label
                                        r3 = 1
                                        if (r2 == 0) goto L2f
                                        if (r2 != r3) goto L27
                                        kotlin.ResultKt.throwOnFailure(r6)
                                        goto L4e
                                    L27:
                                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                        r4.<init>(r5)
                                        throw r4
                                    L2f:
                                        kotlin.ResultKt.throwOnFailure(r6)
                                        android.net.NetworkTemplate r5 = (android.net.NetworkTemplate) r5
                                        com.android.settings.datausage.lib.NetworkStatsRepository r6 = new com.android.settings.datausage.lib.NetworkStatsRepository
                                        com.android.settings.datausage.DataUsageListViewModel r2 = r4.this$0
                                        android.app.Application r2 = r2.getApplication()
                                        r6.<init>(r2, r5)
                                        java.util.List r5 = r6.queryDetailsForDevice()
                                        r0.label = r3
                                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                        java.lang.Object r4 = r4.emit(r5, r0)
                                        if (r4 != r1) goto L4e
                                        return r1
                                    L4e:
                                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                        return r4
                                    */
                                    throw new UnsupportedOperationException(
                                            "Method not decompiled:"
                                                + " com.android.settings.datausage.DataUsageListViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                                + " kotlin.coroutines.Continuation):java.lang.Object");
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(
                                    FlowCollector flowCollector, Continuation continuation) {
                                Object collect =
                                        filterNotNull.collect(
                                                new AnonymousClass2(flowCollector, this),
                                                continuation);
                                return collect == CoroutineSingletons.COROUTINE_SUSPENDED
                                        ? collect
                                        : Unit.INSTANCE;
                            }
                        },
                        plus,
                        SharingStarted.Companion.WhileSubscribed$default(),
                        EmptyList.INSTANCE);
        this.cyclesFlow =
                FlowKt.flowOn(
                        FlowKt.combine(
                                FlowKt.filterNotNull(MutableStateFlow),
                                stateIn,
                                new DataUsageListViewModel$cyclesFlow$1(application, null)),
                        defaultScheduler);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(null);
        this.selectedCycleFlow = MutableStateFlow2;
        this.chartDataFlow =
                FlowKt.flowOn(
                        FlowKt.combine(
                                FlowKt.filterNotNull(MutableStateFlow),
                                FlowKt.flowOn(
                                        FlowKt.combine(
                                                FlowKt.filterNotNull(MutableStateFlow2),
                                                stateIn,
                                                new DataUsageListViewModel$selectedBucketsFlow$1(
                                                        3, null)),
                                        defaultScheduler),
                                new DataUsageListViewModel$chartDataFlow$1(application, null)),
                        defaultScheduler);
    }
}
