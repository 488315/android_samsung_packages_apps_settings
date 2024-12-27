package com.android.settings.spa.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.State;

import com.android.settings.R;
import com.android.settings.spa.app.appinfo.AppInfoSettingsProvider;
import com.android.settingslib.spa.widget.ui.SpinnerOption;
import com.android.settingslib.spaprivileged.framework.compose.StringResourcesKt;
import com.android.settingslib.spaprivileged.model.app.AppListModel;
import com.android.settingslib.spaprivileged.model.app.AppRecord;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;
import com.android.settingslib.spaprivileged.template.app.AppListItemModel;
import com.android.settingslib.spaprivileged.template.app.AppListItemModelKt;
import com.android.settingslib.spaprivileged.template.app.AppStorageSizeKt;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AllAppListModel implements AppListModel {
    public final Context context;
    public final Function3 getStorageSummary;
    public final Function1 isDisabled;
    public final Function1 isInstant;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SpinnerItem.values().length];
            try {
                SpinnerItem spinnerItem = SpinnerItem.All;
                iArr[1] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                SpinnerItem spinnerItem2 = SpinnerItem.All;
                iArr[2] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                SpinnerItem spinnerItem3 = SpinnerItem.All;
                iArr[3] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public AllAppListModel(Context context) {
        AnonymousClass1 getStorageSummary =
                new Function3() { // from class: com.android.settings.spa.app.AllAppListModel.1
                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        ApplicationInfo applicationInfo = (ApplicationInfo) obj;
                        ((Number) obj3).intValue();
                        Intrinsics.checkNotNullParameter(applicationInfo, "$this$null");
                        ComposerImpl composerImpl = (ComposerImpl) ((Composer) obj2);
                        composerImpl.startReplaceGroup(-934660005);
                        OpaqueKey opaqueKey = ComposerKt.invocation;
                        MutableState storageSize =
                                AppStorageSizeKt.getStorageSize(applicationInfo, composerImpl);
                        composerImpl.end(false);
                        return storageSize;
                    }
                };
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(getStorageSummary, "getStorageSummary");
        this.context = context;
        this.getStorageSummary = getStorageSummary;
        this.isDisabled = AllAppListModel$isDisabled$1.INSTANCE;
        this.isInstant = AllAppListModel$isInstant$1.INSTANCE;
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final void AppItem(
            final AppListItemModel appListItemModel, Composer composer, final int i) {
        Intrinsics.checkNotNullParameter(appListItemModel, "<this>");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(741640358);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        AppInfoSettingsProvider appInfoSettingsProvider = AppInfoSettingsProvider.INSTANCE;
        AppListItemModelKt.AppListItem(
                appListItemModel,
                AppInfoSettingsProvider.navigator(
                        ((AppRecordWithSize) appListItemModel.record).app, composerImpl),
                composerImpl,
                8);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.AllAppListModel$AppItem$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AllAppListModel.this.AppItem(
                                    appListItemModel,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final Flow filter(
            FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2,
            int i,
            final ReadonlySharedFlow recordListFlow) {
        Intrinsics.checkNotNullParameter(recordListFlow, "recordListFlow");
        SpinnerItem spinnerItem =
                (SpinnerItem) CollectionsKt___CollectionsKt.getOrNull(i, SpinnerItem.$ENTRIES);
        int i2 =
                spinnerItem == null ? -1 : WhenMappings.$EnumSwitchMapping$0[spinnerItem.ordinal()];
        final Function1 function1 =
                i2 != 1
                        ? i2 != 2
                                ? i2 != 3
                                        ? new Function1() { // from class:
                                                            // com.android.settings.spa.app.AllAppListModel$filter$2
                                            @Override // kotlin.jvm.functions.Function1
                                            public final Object invoke(Object obj) {
                                                AppRecordWithSize it = (AppRecordWithSize) obj;
                                                Intrinsics.checkNotNullParameter(it, "it");
                                                return Boolean.TRUE;
                                            }
                                        }
                                        : this.isInstant
                                : this.isDisabled
                        : new Function1() { // from class:
                                            // com.android.settings.spa.app.AllAppListModel$filter$1
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                AppRecordWithSize it = (AppRecordWithSize) obj;
                                Intrinsics.checkNotNullParameter(it, "it");
                                ApplicationInfo applicationInfo = it.app;
                                return Boolean.valueOf(
                                        applicationInfo.enabled && !applicationInfo.isInstantApp());
                            }
                        };
        return new Flow() { // from class:
            // com.android.settings.spa.app.AllAppListModel$filter$$inlined$filterItem$1

            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
            /* renamed from: com.android.settings.spa.app.AllAppListModel$filter$$inlined$filterItem$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ Function1 $predicate$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                @Metadata(
                        k = 3,
                        mv = {1, 9, 0},
                        xi = 48)
                /* renamed from: com.android.settings.spa.app.AllAppListModel$filter$$inlined$filterItem$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, Function1 function1) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$predicate$inlined = function1;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(
                        java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.settings.spa.app.AllAppListModel$filter$$inlined$filterItem$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.settings.spa.app.AllAppListModel$filter$$inlined$filterItem$1$2$1 r0 = (com.android.settings.spa.app.AllAppListModel$filter$$inlined$filterItem$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.settings.spa.app.AllAppListModel$filter$$inlined$filterItem$1$2$1 r0 = new com.android.settings.spa.app.AllAppListModel$filter$$inlined$filterItem$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L66
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        java.util.List r6 = (java.util.List) r6
                        java.lang.Iterable r6 = (java.lang.Iterable) r6
                        java.util.ArrayList r7 = new java.util.ArrayList
                        r7.<init>()
                        java.util.Iterator r6 = r6.iterator()
                    L3f:
                        boolean r2 = r6.hasNext()
                        if (r2 == 0) goto L5b
                        java.lang.Object r2 = r6.next()
                        kotlin.jvm.functions.Function1 r4 = r5.$predicate$inlined
                        java.lang.Object r4 = r4.invoke(r2)
                        java.lang.Boolean r4 = (java.lang.Boolean) r4
                        boolean r4 = r4.booleanValue()
                        if (r4 == 0) goto L3f
                        r7.add(r2)
                        goto L3f
                    L5b:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r7, r0)
                        if (r5 != r1) goto L66
                        return r1
                    L66:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException(
                            "Method not decompiled:"
                                + " com.android.settings.spa.app.AllAppListModel$filter$$inlined$filterItem$1.AnonymousClass2.emit(java.lang.Object,"
                                + " kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect =
                        recordListFlow.collect(
                                new AnonymousClass2(flowCollector, function1), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final List getSpinnerOptions(List recordList) {
        boolean z;
        Intrinsics.checkNotNullParameter(recordList, "recordList");
        List list = recordList;
        Function1 function1 = this.isDisabled;
        boolean z2 = list instanceof Collection;
        boolean z3 = true;
        if (!z2 || !list.isEmpty()) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                if (((Boolean) ((AllAppListModel$isDisabled$1) function1).invoke(it.next()))
                        .booleanValue()) {
                    z = true;
                    break;
                }
            }
        }
        z = false;
        Function1 function12 = this.isInstant;
        if (!z2 || !list.isEmpty()) {
            Iterator it2 = list.iterator();
            while (it2.hasNext()) {
                if (((Boolean) ((AllAppListModel$isInstant$1) function12).invoke(it2.next()))
                        .booleanValue()) {
                    break;
                }
            }
        }
        z3 = false;
        if (!z && !z3) {
            return EmptyList.INSTANCE;
        }
        List<SpinnerItem> mutableListOf =
                CollectionsKt__CollectionsKt.mutableListOf(SpinnerItem.All, SpinnerItem.Enabled);
        if (z) {
            mutableListOf.add(SpinnerItem.Disabled);
        }
        if (z3) {
            mutableListOf.add(SpinnerItem.Instant);
        }
        ArrayList arrayList =
                new ArrayList(
                        CollectionsKt__IterablesKt.collectionSizeOrDefault(mutableListOf, 10));
        for (SpinnerItem spinnerItem : mutableListOf) {
            int ordinal = spinnerItem.ordinal();
            String string = this.context.getString(spinnerItem.getStringResId());
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            arrayList.add(new SpinnerOption(ordinal, string));
        }
        return arrayList;
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final Function0 getSummary(int i, AppRecord appRecord, Composer composer) {
        final AppRecordWithSize record = (AppRecordWithSize) appRecord;
        Intrinsics.checkNotNullParameter(record, "record");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-268690057);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final State state = (State) this.getStorageSummary.invoke(record.app, composerImpl, 8);
        Function0 function0 =
                new Function0() { // from class:
                                  // com.android.settings.spa.app.AllAppListModel$getSummary$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        ArrayList arrayList = new ArrayList();
                        String str = (String) State.this.getValue();
                        if (!StringsKt__StringsJVMKt.isBlank(str)) {
                            arrayList.add(str);
                        }
                        if (ApplicationInfosKt.getInstalled(record.app) || record.app.isArchived) {
                            if (((Boolean)
                                            ((AllAppListModel$isDisabled$1) this.isDisabled)
                                                    .invoke(record))
                                    .booleanValue()) {
                                arrayList.add(this.context.getString(R.string.disabled));
                            }
                        } else {
                            arrayList.add(this.context.getString(R.string.not_installed));
                        }
                        String lineSeparator = System.lineSeparator();
                        Intrinsics.checkNotNullExpressionValue(lineSeparator, "lineSeparator(...)");
                        String joinToString$default =
                                CollectionsKt___CollectionsKt.joinToString$default(
                                        arrayList, lineSeparator, null, null, null, 62);
                        return joinToString$default.length() == 0
                                ? StringResourcesKt.getPlaceholder(this.context)
                                : joinToString$default;
                    }
                };
        composerImpl.end(false);
        return function0;
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final Flow transform(
            FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2,
            final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1
                    flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1) {
        return new Flow() { // from class:
            // com.android.settings.spa.app.AllAppListModel$transform$$inlined$mapItem$1

            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
            /* renamed from: com.android.settings.spa.app.AllAppListModel$transform$$inlined$mapItem$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                @Metadata(
                        k = 3,
                        mv = {1, 9, 0},
                        xi = 48)
                /* renamed from: com.android.settings.spa.app.AllAppListModel$transform$$inlined$mapItem$1$2$1, reason: invalid class name */
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
                        java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.settings.spa.app.AllAppListModel$transform$$inlined$mapItem$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.settings.spa.app.AllAppListModel$transform$$inlined$mapItem$1$2$1 r0 = (com.android.settings.spa.app.AllAppListModel$transform$$inlined$mapItem$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.settings.spa.app.AllAppListModel$transform$$inlined$mapItem$1$2$1 r0 = new com.android.settings.spa.app.AllAppListModel$transform$$inlined$mapItem$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L65
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        java.util.List r6 = (java.util.List) r6
                        java.lang.Iterable r6 = (java.lang.Iterable) r6
                        java.util.ArrayList r7 = new java.util.ArrayList
                        r2 = 10
                        int r2 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r6, r2)
                        r7.<init>(r2)
                        java.util.Iterator r6 = r6.iterator()
                    L45:
                        boolean r2 = r6.hasNext()
                        if (r2 == 0) goto L5a
                        java.lang.Object r2 = r6.next()
                        android.content.pm.ApplicationInfo r2 = (android.content.pm.ApplicationInfo) r2
                        com.android.settings.spa.app.AppRecordWithSize r4 = new com.android.settings.spa.app.AppRecordWithSize
                        r4.<init>(r2)
                        r7.add(r4)
                        goto L45
                    L5a:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r7, r0)
                        if (r5 != r1) goto L65
                        return r1
                    L65:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException(
                            "Method not decompiled:"
                                + " com.android.settings.spa.app.AllAppListModel$transform$$inlined$mapItem$1.AnonymousClass2.emit(java.lang.Object,"
                                + " kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect =
                        flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1.collect(
                                new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }
}
