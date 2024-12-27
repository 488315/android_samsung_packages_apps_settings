package com.android.settings.spa.app.battery;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.os.BundleKt;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.fuelgauge.AdvancedPowerUsageDetail;
import com.android.settings.spa.app.AppRecordWithSize;
import com.android.settingslib.Utils;
import com.android.settingslib.fuelgauge.PowerAllowlistBackend;
import com.android.settingslib.spa.widget.ui.SpinnerOption;
import com.android.settingslib.spaprivileged.model.app.AppListModel;
import com.android.settingslib.spaprivileged.model.app.AppRecord;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;
import com.android.settingslib.spaprivileged.template.app.AppListItemModel;
import com.android.settingslib.spaprivileged.template.app.AppListItemModelKt;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.enums.EnumEntries;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryOptimizationModeAppListModel implements AppListModel {
    public final Context context;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[OptimizationModeSpinnerItem.values().length];
            try {
                OptimizationModeSpinnerItem[] optimizationModeSpinnerItemArr =
                        OptimizationModeSpinnerItem.$VALUES;
                iArr[1] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                OptimizationModeSpinnerItem[] optimizationModeSpinnerItemArr2 =
                        OptimizationModeSpinnerItem.$VALUES;
                iArr[2] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                OptimizationModeSpinnerItem[] optimizationModeSpinnerItemArr3 =
                        OptimizationModeSpinnerItem.$VALUES;
                iArr[3] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public BatteryOptimizationModeAppListModel(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final void AppItem(
            final AppListItemModel appListItemModel, Composer composer, final int i) {
        Intrinsics.checkNotNullParameter(appListItemModel, "<this>");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1764820001);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        AppListItemModelKt.AppListItem(
                appListItemModel,
                new Function0() { // from class:
                                  // com.android.settings.spa.app.battery.BatteryOptimizationModeAppListModel$AppItem$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        Bundle bundleOf =
                                BundleKt.bundleOf(
                                        new Pair(
                                                "extra_package_name",
                                                ((AppRecordWithSize) AppListItemModel.this.record)
                                                        .app
                                                        .packageName),
                                        new Pair(
                                                "extra_power_usage_percent",
                                                Utils.formatPercentage(0)),
                                        new Pair(
                                                "extra_uid",
                                                Integer.valueOf(
                                                        ((AppRecordWithSize)
                                                                        AppListItemModel.this
                                                                                .record)
                                                                .app
                                                                .uid)));
                        SubSettingLauncher subSettingLauncher =
                                new SubSettingLauncher(this.context);
                        String name = AdvancedPowerUsageDetail.class.getName();
                        SubSettingLauncher.LaunchRequest launchRequest =
                                subSettingLauncher.mLaunchRequest;
                        launchRequest.mDestinationName = name;
                        subSettingLauncher.setTitleRes(R.string.battery_details_title, null);
                        launchRequest.mArguments = bundleOf;
                        launchRequest.mUserHandle =
                                ApplicationInfosKt.getUserHandle(
                                        ((AppRecordWithSize) AppListItemModel.this.record).app);
                        launchRequest.mSourceMetricsCategory = 20;
                        subSettingLauncher.launch();
                        return Unit.INSTANCE;
                    }
                },
                composerImpl,
                8);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.battery.BatteryOptimizationModeAppListModel$AppItem$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            BatteryOptimizationModeAppListModel.this.AppItem(
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
            final int i,
            final ReadonlySharedFlow recordListFlow) {
        Intrinsics.checkNotNullParameter(recordListFlow, "recordListFlow");
        PowerAllowlistBackend.getInstance(this.context).refreshList();
        return new Flow() { // from class:
            // com.android.settings.spa.app.battery.BatteryOptimizationModeAppListModel$filter$$inlined$filterItem$1

            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
            /* renamed from: com.android.settings.spa.app.battery.BatteryOptimizationModeAppListModel$filter$$inlined$filterItem$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ int $option$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ BatteryOptimizationModeAppListModel this$0;

                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                @Metadata(
                        k = 3,
                        mv = {1, 9, 0},
                        xi = 48)
                /* renamed from: com.android.settings.spa.app.battery.BatteryOptimizationModeAppListModel$filter$$inlined$filterItem$1$2$1, reason: invalid class name */
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
                        BatteryOptimizationModeAppListModel batteryOptimizationModeAppListModel,
                        int i) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = batteryOptimizationModeAppListModel;
                    this.$option$inlined = i;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
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
                        boolean r0 = r10 instanceof com.android.settings.spa.app.battery.BatteryOptimizationModeAppListModel$filter$$inlined$filterItem$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r10
                        com.android.settings.spa.app.battery.BatteryOptimizationModeAppListModel$filter$$inlined$filterItem$1$2$1 r0 = (com.android.settings.spa.app.battery.BatteryOptimizationModeAppListModel$filter$$inlined$filterItem$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.settings.spa.app.battery.BatteryOptimizationModeAppListModel$filter$$inlined$filterItem$1$2$1 r0 = new com.android.settings.spa.app.battery.BatteryOptimizationModeAppListModel$filter$$inlined$filterItem$1$2$1
                        r0.<init>(r10)
                    L18:
                        java.lang.Object r10 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L30
                        if (r2 != r3) goto L28
                        kotlin.ResultKt.throwOnFailure(r10)
                        goto L97
                    L28:
                        java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                        java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                        r8.<init>(r9)
                        throw r8
                    L30:
                        kotlin.ResultKt.throwOnFailure(r10)
                        java.util.List r9 = (java.util.List) r9
                        java.lang.Iterable r9 = (java.lang.Iterable) r9
                        java.util.ArrayList r10 = new java.util.ArrayList
                        r10.<init>()
                        java.util.Iterator r9 = r9.iterator()
                    L40:
                        boolean r2 = r9.hasNext()
                        if (r2 == 0) goto L8c
                        java.lang.Object r2 = r9.next()
                        r4 = r2
                        com.android.settings.spa.app.AppRecordWithSize r4 = (com.android.settings.spa.app.AppRecordWithSize) r4
                        com.android.settings.fuelgauge.BatteryOptimizeUtils r5 = new com.android.settings.fuelgauge.BatteryOptimizeUtils
                        com.android.settings.spa.app.battery.BatteryOptimizationModeAppListModel r6 = r8.this$0
                        android.content.Context r6 = r6.context
                        android.content.pm.ApplicationInfo r4 = r4.app
                        int r7 = r4.uid
                        java.lang.String r4 = r4.packageName
                        r5.<init>(r6, r7, r4)
                        r4 = 0
                        int r4 = r5.getAppOptimizationMode(r4)
                        kotlin.enums.EnumEntries r5 = com.android.settings.spa.app.battery.OptimizationModeSpinnerItem.$ENTRIES
                        int r6 = r8.$option$inlined
                        java.lang.Object r5 = kotlin.collections.CollectionsKt___CollectionsKt.getOrNull(r6, r5)
                        com.android.settings.spa.app.battery.OptimizationModeSpinnerItem r5 = (com.android.settings.spa.app.battery.OptimizationModeSpinnerItem) r5
                        if (r5 != 0) goto L6f
                        r5 = -1
                        goto L77
                    L6f:
                        int[] r6 = com.android.settings.spa.app.battery.BatteryOptimizationModeAppListModel.WhenMappings.$EnumSwitchMapping$0
                        int r5 = r5.ordinal()
                        r5 = r6[r5]
                    L77:
                        if (r5 == r3) goto L86
                        r6 = 3
                        r7 = 2
                        if (r5 == r7) goto L83
                        if (r5 == r6) goto L80
                        goto L88
                    L80:
                        if (r4 != r7) goto L40
                        goto L88
                    L83:
                        if (r4 != r6) goto L40
                        goto L88
                    L86:
                        if (r4 != r3) goto L40
                    L88:
                        r10.add(r2)
                        goto L40
                    L8c:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r8 = r8.$this_unsafeFlow
                        java.lang.Object r8 = r8.emit(r10, r0)
                        if (r8 != r1) goto L97
                        return r1
                    L97:
                        kotlin.Unit r8 = kotlin.Unit.INSTANCE
                        return r8
                    */
                    throw new UnsupportedOperationException(
                            "Method not decompiled:"
                                + " com.android.settings.spa.app.battery.BatteryOptimizationModeAppListModel$filter$$inlined$filterItem$1.AnonymousClass2.emit(java.lang.Object,"
                                + " kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect =
                        recordListFlow.collect(
                                new AnonymousClass2(flowCollector, this, i), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final List getSpinnerOptions(List recordList) {
        Intrinsics.checkNotNullParameter(recordList, "recordList");
        EnumEntries<OptimizationModeSpinnerItem> enumEntries = OptimizationModeSpinnerItem.$ENTRIES;
        ArrayList arrayList =
                new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(enumEntries, 10));
        for (OptimizationModeSpinnerItem optimizationModeSpinnerItem : enumEntries) {
            int ordinal = optimizationModeSpinnerItem.ordinal();
            String string = this.context.getString(optimizationModeSpinnerItem.getStringResId());
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
        composerImpl.startReplaceGroup(-655845072);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Function0 function0 =
                new Function0() { // from class:
                                  // com.android.settings.spa.app.battery.BatteryOptimizationModeAppListModel$getSummary$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        String str = new String();
                        ApplicationInfo applicationInfo = AppRecordWithSize.this.app;
                        return (ApplicationInfosKt.getInstalled(applicationInfo)
                                        || applicationInfo.isArchived)
                                ? !applicationInfo.enabled
                                        ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                                                str, this.context.getString(R.string.disabled))
                                        : str
                                : AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                                        str, this.context.getString(R.string.not_installed));
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
            // com.android.settings.spa.app.battery.BatteryOptimizationModeAppListModel$transform$$inlined$mapItem$1

            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
            /* renamed from: com.android.settings.spa.app.battery.BatteryOptimizationModeAppListModel$transform$$inlined$mapItem$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                @Metadata(
                        k = 3,
                        mv = {1, 9, 0},
                        xi = 48)
                /* renamed from: com.android.settings.spa.app.battery.BatteryOptimizationModeAppListModel$transform$$inlined$mapItem$1$2$1, reason: invalid class name */
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
                        boolean r0 = r7 instanceof com.android.settings.spa.app.battery.BatteryOptimizationModeAppListModel$transform$$inlined$mapItem$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.settings.spa.app.battery.BatteryOptimizationModeAppListModel$transform$$inlined$mapItem$1$2$1 r0 = (com.android.settings.spa.app.battery.BatteryOptimizationModeAppListModel$transform$$inlined$mapItem$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.settings.spa.app.battery.BatteryOptimizationModeAppListModel$transform$$inlined$mapItem$1$2$1 r0 = new com.android.settings.spa.app.battery.BatteryOptimizationModeAppListModel$transform$$inlined$mapItem$1$2$1
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
                                + " com.android.settings.spa.app.battery.BatteryOptimizationModeAppListModel$transform$$inlined$mapItem$1.AnonymousClass2.emit(java.lang.Object,"
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
