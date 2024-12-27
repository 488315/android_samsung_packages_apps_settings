package com.android.settingslib.spaprivileged.template.app;

import android.content.pm.ApplicationInfo;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;

import com.android.settingslib.spaprivileged.model.app.AppRecord;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.AwaitKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public interface TogglePermissionAppListModel {
    default void InfoPageAdditionalContent(
            final AppRecord record, final Function0 isAllowed, Composer composer, final int i) {
        Intrinsics.checkNotNullParameter(record, "record");
        Intrinsics.checkNotNullParameter(isAllowed, "isAllowed");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(342870342);
        if ((i & 1) == 0 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel$InfoPageAdditionalContent$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            TogglePermissionAppListModel.this.InfoPageAdditionalContent(
                                    record,
                                    isAllowed,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    Flow filter(
            FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2,
            TogglePermissionInternalAppListModel$filter$$inlined$filterItem$1
                    togglePermissionInternalAppListModel$filter$$inlined$filterItem$1);

    default String getEnhancedConfirmationKey() {
        return null;
    }

    int getFooterResId();

    int getPageTitleResId();

    default List getSwitchRestrictionKeys() {
        return EmptyList.INSTANCE;
    }

    int getSwitchTitleResId();

    Function0 isAllowed(AppRecord appRecord, Composer composer);

    boolean isChangeable(AppRecord appRecord);

    void setAllowed(AppRecord appRecord, boolean z);

    default Flow transform(
            FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2,
            final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1
                    flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1) {
        return new Flow() { // from class:
            // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel$transform$$inlined$asyncMapItem$1

            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
            /* renamed from: com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel$transform$$inlined$asyncMapItem$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ TogglePermissionAppListModel $receiver$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                @Metadata(
                        k = 3,
                        mv = {1, 9, 0},
                        xi = 48)
                /* renamed from: com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel$transform$$inlined$asyncMapItem$1$2$1, reason: invalid class name */
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

                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                @Metadata(
                        d1 = {
                            "\u0000\u0010\n"
                                + "\u0002\b\u0002\n"
                                + "\u0002\u0018\u0002\n"
                                + "\u0002\u0010 \n"
                                + "\u0002\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u0001*\u00020\u0002H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"
                        },
                        d2 = {
                            "R",
                            "T",
                            "Lkotlinx/coroutines/CoroutineScope;",
                            ApnSettings.MVNO_NONE,
                            "<anonymous>",
                            "(Lkotlinx/coroutines/CoroutineScope;)Ljava/util/List;"
                        },
                        k = 3,
                        mv = {1, 9, 0})
                /* renamed from: com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel$transform$$inlined$asyncMapItem$1$2$2, reason: invalid class name and collision with other inner class name */
                public final class C00482 extends SuspendLambda implements Function2 {
                    final /* synthetic */ TogglePermissionAppListModel $receiver$inlined;
                    final /* synthetic */ Iterable $this_asyncMap;
                    private /* synthetic */ Object L$0;
                    int label;

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    @Metadata(
                            d1 = {
                                "\u0000\n\n"
                                    + "\u0002\b\u0002\n"
                                    + "\u0002\u0018\u0002\n"
                                    + "\u0000\u0010\u0003\u001a\u00028\u0000\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u0001*\u00020\u0002H\u008a@"
                            },
                            d2 = {"R", "T", "Lkotlinx/coroutines/CoroutineScope;", "<anonymous>"},
                            k = 3,
                            mv = {1, 9, 0})
                    /* renamed from: com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel$transform$$inlined$asyncMapItem$1$2$2$1, reason: invalid class name */
                    public final class AnonymousClass1 extends SuspendLambda implements Function2 {
                        final /* synthetic */ Object $item;
                        final /* synthetic */ TogglePermissionAppListModel $receiver$inlined;
                        int label;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public AnonymousClass1(
                                Object obj,
                                Continuation continuation,
                                TogglePermissionAppListModel togglePermissionAppListModel) {
                            super(2, continuation);
                            this.$item = obj;
                            this.$receiver$inlined = togglePermissionAppListModel;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Continuation create(Object obj, Continuation continuation) {
                            return new AnonymousClass1(
                                    this.$item, continuation, this.$receiver$inlined);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            return ((AnonymousClass1)
                                            create((CoroutineScope) obj, (Continuation) obj2))
                                    .invokeSuspend(Unit.INSTANCE);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            CoroutineSingletons coroutineSingletons =
                                    CoroutineSingletons.COROUTINE_SUSPENDED;
                            if (this.label != 0) {
                                throw new IllegalStateException(
                                        "call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                            return this.$receiver$inlined.transformItem(
                                    (ApplicationInfo) this.$item);
                        }
                    }

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C00482(
                            Iterable iterable,
                            Continuation continuation,
                            TogglePermissionAppListModel togglePermissionAppListModel) {
                        super(2, continuation);
                        this.$this_asyncMap = iterable;
                        this.$receiver$inlined = togglePermissionAppListModel;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        C00482 c00482 =
                                new C00482(
                                        this.$this_asyncMap, continuation, this.$receiver$inlined);
                        c00482.L$0 = obj;
                        return c00482;
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((C00482) create((CoroutineScope) obj, (Continuation) obj2))
                                .invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineSingletons coroutineSingletons =
                                CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                            Iterable iterable = this.$this_asyncMap;
                            ArrayList arrayList =
                                    new ArrayList(
                                            CollectionsKt__IterablesKt.collectionSizeOrDefault(
                                                    iterable, 10));
                            Iterator it = iterable.iterator();
                            while (it.hasNext()) {
                                arrayList.add(
                                        BuildersKt.async$default(
                                                coroutineScope,
                                                new AnonymousClass1(
                                                        it.next(), null, this.$receiver$inlined)));
                            }
                            this.label = 1;
                            obj = AwaitKt.awaitAll(arrayList, this);
                            if (obj == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        } else {
                            if (i != 1) {
                                throw new IllegalStateException(
                                        "call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                        }
                        return obj;
                    }
                }

                public AnonymousClass2(
                        FlowCollector flowCollector,
                        TogglePermissionAppListModel togglePermissionAppListModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$receiver$inlined = togglePermissionAppListModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:19:0x0060 A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:20:0x003b  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(
                        java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel$transform$$inlined$asyncMapItem$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel$transform$$inlined$asyncMapItem$1$2$1 r0 = (com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel$transform$$inlined$asyncMapItem$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel$transform$$inlined$asyncMapItem$1$2$1 r0 = new com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel$transform$$inlined$asyncMapItem$1$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 0
                        r4 = 2
                        r5 = 1
                        if (r2 == 0) goto L3b
                        if (r2 == r5) goto L33
                        if (r2 != r4) goto L2b
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L61
                    L2b:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L33:
                        java.lang.Object r6 = r0.L$0
                        kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L56
                    L3b:
                        kotlin.ResultKt.throwOnFailure(r8)
                        java.util.List r7 = (java.util.List) r7
                        java.lang.Iterable r7 = (java.lang.Iterable) r7
                        com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel$transform$$inlined$asyncMapItem$1$2$2 r8 = new com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel$transform$$inlined$asyncMapItem$1$2$2
                        com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel r2 = r6.$receiver$inlined
                        r8.<init>(r7, r3, r2)
                        kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                        r0.L$0 = r6
                        r0.label = r5
                        java.lang.Object r8 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r8, r0)
                        if (r8 != r1) goto L56
                        return r1
                    L56:
                        r0.L$0 = r3
                        r0.label = r4
                        java.lang.Object r6 = r6.emit(r8, r0)
                        if (r6 != r1) goto L61
                        return r1
                    L61:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException(
                            "Method not decompiled:"
                                + " com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel$transform$$inlined$asyncMapItem$1.AnonymousClass2.emit(java.lang.Object,"
                                + " kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect =
                        flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1.collect(
                                new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }

    AppRecord transformItem(ApplicationInfo applicationInfo);
}
