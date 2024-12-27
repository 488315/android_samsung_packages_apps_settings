package com.android.settings.spa.app.appinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.android.settingslib.spaprivileged.framework.common.ContextsKt;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;

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
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.scheduling.DefaultIoScheduler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class InstantAppDomainsPresenter {
    public final ApplicationInfo app;
    public final Context context;
    public final Flow domainsFlow;
    public final Flow summaryFlow;
    public final PackageManager userPackageManager;

    public InstantAppDomainsPresenter(Context context, ApplicationInfo app) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(app, "app");
        this.context = context;
        this.app = app;
        this.userPackageManager =
                ContextsKt.asUser(context, ApplicationInfosKt.getUserHandle(app))
                        .getPackageManager();
        SafeFlow safeFlow = new SafeFlow(new InstantAppDomainsPresenter$domainsFlow$1(this, null));
        DefaultIoScheduler defaultIoScheduler = Dispatchers.IO;
        final Flow flowOn = FlowKt.flowOn(safeFlow, defaultIoScheduler);
        this.domainsFlow = flowOn;
        this.summaryFlow =
                FlowKt.flowOn(
                        new Flow() { // from class:
                            // com.android.settings.spa.app.appinfo.InstantAppDomainsPresenter$special$$inlined$map$1

                            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                            /* renamed from: com.android.settings.spa.app.appinfo.InstantAppDomainsPresenter$special$$inlined$map$1$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                                public final /* synthetic */ InstantAppDomainsPresenter this$0;

                                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                @Metadata(
                                        k = 3,
                                        mv = {1, 9, 0},
                                        xi = 48)
                                /* renamed from: com.android.settings.spa.app.appinfo.InstantAppDomainsPresenter$special$$inlined$map$1$2$1, reason: invalid class name */
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
                                        InstantAppDomainsPresenter instantAppDomainsPresenter) {
                                    this.$this_unsafeFlow = flowCollector;
                                    this.this$0 = instantAppDomainsPresenter;
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
                                        boolean r0 = r6 instanceof com.android.settings.spa.app.appinfo.InstantAppDomainsPresenter$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                        if (r0 == 0) goto L13
                                        r0 = r6
                                        com.android.settings.spa.app.appinfo.InstantAppDomainsPresenter$special$$inlined$map$1$2$1 r0 = (com.android.settings.spa.app.appinfo.InstantAppDomainsPresenter$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                        int r1 = r0.label
                                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                        r3 = r1 & r2
                                        if (r3 == 0) goto L13
                                        int r1 = r1 - r2
                                        r0.label = r1
                                        goto L18
                                    L13:
                                        com.android.settings.spa.app.appinfo.InstantAppDomainsPresenter$special$$inlined$map$1$2$1 r0 = new com.android.settings.spa.app.appinfo.InstantAppDomainsPresenter$special$$inlined$map$1$2$1
                                        r0.<init>(r6)
                                    L18:
                                        java.lang.Object r6 = r0.result
                                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                        int r2 = r0.label
                                        r3 = 1
                                        if (r2 == 0) goto L2f
                                        if (r2 != r3) goto L27
                                        kotlin.ResultKt.throwOnFailure(r6)
                                        goto L7a
                                    L27:
                                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                        r4.<init>(r5)
                                        throw r4
                                    L2f:
                                        kotlin.ResultKt.throwOnFailure(r6)
                                        java.util.Set r5 = (java.util.Set) r5
                                        int r6 = r5.size()
                                        com.android.settings.spa.app.appinfo.InstantAppDomainsPresenter r2 = r4.this$0
                                        if (r6 == 0) goto L66
                                        if (r6 == r3) goto L52
                                        android.content.Context r6 = r2.context
                                        java.lang.Iterable r5 = (java.lang.Iterable) r5
                                        java.lang.Object r5 = kotlin.collections.CollectionsKt___CollectionsKt.first(r5)
                                        java.lang.Object[] r5 = new java.lang.Object[]{r5}
                                        r2 = 2132020549(0x7f140d45, float:1.9679464E38)
                                        java.lang.String r5 = r6.getString(r2, r5)
                                        goto L6f
                                    L52:
                                        android.content.Context r6 = r2.context
                                        java.lang.Iterable r5 = (java.lang.Iterable) r5
                                        java.lang.Object r5 = kotlin.collections.CollectionsKt___CollectionsKt.first(r5)
                                        java.lang.Object[] r5 = new java.lang.Object[]{r5}
                                        r2 = 2132020548(0x7f140d44, float:1.9679462E38)
                                        java.lang.String r5 = r6.getString(r2, r5)
                                        goto L6f
                                    L66:
                                        android.content.Context r5 = r2.context
                                        r6 = 2132020547(0x7f140d43, float:1.967946E38)
                                        java.lang.String r5 = r5.getString(r6)
                                    L6f:
                                        r0.label = r3
                                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                        java.lang.Object r4 = r4.emit(r5, r0)
                                        if (r4 != r1) goto L7a
                                        return r1
                                    L7a:
                                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                        return r4
                                    */
                                    throw new UnsupportedOperationException(
                                            "Method not decompiled:"
                                                + " com.android.settings.spa.app.appinfo.InstantAppDomainsPresenter$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
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
                        defaultIoScheduler);
    }
}
