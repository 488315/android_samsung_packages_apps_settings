package com.android.settings.spa.development;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.text.format.DateUtils;

import androidx.apppickerview.features.applabel.AbstractAppLabelMapFactory$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;

import com.android.settings.R;
import com.android.settingslib.spa.widget.ui.SpinnerOption;
import com.android.settingslib.spaprivileged.model.app.AppEntry;
import com.android.settingslib.spaprivileged.model.app.AppListModel;
import com.android.settingslib.spaprivileged.model.app.AppRecord;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.settings.ImsProfile;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda0;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda1;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UsageStatsListModel implements AppListModel {
    public final Context context;
    public final long now;
    public final UsageStatsManager usageStatsManager;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\u0012\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u0010\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0010\b\n"
                    + "\u0002\b\u0006\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\bR\u0017\u0010\u0004\u001a\u00020\u00038\u0006¢\u0006\f\n"
                    + "\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007¨\u0006\t"
            },
            d2 = {
                "com/android/settings/spa/development/UsageStatsListModel$SpinnerItem",
                ApnSettings.MVNO_NONE,
                "Lcom/android/settings/spa/development/UsageStatsListModel$SpinnerItem;",
                ApnSettings.MVNO_NONE,
                "stringResId",
                ImsProfile.TIMER_NAME_I,
                "getStringResId",
                "()I",
                "Companion",
                "applications__sources__apps__SecSettings__android_common__SecSettings-core"
            },
            k = 1,
            mv = {1, 9, 0})
    final class SpinnerItem {
        public static final /* synthetic */ EnumEntries $ENTRIES;
        public static final /* synthetic */ SpinnerItem[] $VALUES;
        public static final Companion Companion;
        private final int stringResId;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class Companion {}

        static {
            SpinnerItem[] spinnerItemArr = {
                new SpinnerItem("UsageTime", 0, R.string.usage_stats_sort_by_usage_time),
                new SpinnerItem("LastTimeUsed", 1, R.string.usage_stats_sort_by_last_time_used),
                new SpinnerItem("AppName", 2, R.string.usage_stats_sort_by_app_name)
            };
            $VALUES = spinnerItemArr;
            $ENTRIES = EnumEntriesKt.enumEntries(spinnerItemArr);
            Companion = new Companion();
        }

        public SpinnerItem(String str, int i, int i2) {
            this.stringResId = i2;
        }

        public static SpinnerItem valueOf(String str) {
            return (SpinnerItem) Enum.valueOf(SpinnerItem.class, str);
        }

        public static SpinnerItem[] values() {
            return (SpinnerItem[]) $VALUES.clone();
        }

        public final int getStringResId() {
            return this.stringResId;
        }
    }

    public UsageStatsListModel(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        Object systemService = context.getSystemService("usagestats");
        Intrinsics.checkNotNull(
                systemService,
                "null cannot be cast to non-null type android.app.usage.UsageStatsManager");
        this.usageStatsManager = (UsageStatsManager) systemService;
        this.now = System.currentTimeMillis();
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final Flow filter(
            FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2,
            int i,
            final ReadonlySharedFlow recordListFlow) {
        Intrinsics.checkNotNullParameter(recordListFlow, "recordListFlow");
        return new Flow() { // from class:
            // com.android.settings.spa.development.UsageStatsListModel$filter$$inlined$map$1

            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
            /* renamed from: com.android.settings.spa.development.UsageStatsListModel$filter$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                @Metadata(
                        k = 3,
                        mv = {1, 9, 0},
                        xi = 48)
                /* renamed from: com.android.settings.spa.development.UsageStatsListModel$filter$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r7 instanceof com.android.settings.spa.development.UsageStatsListModel$filter$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.settings.spa.development.UsageStatsListModel$filter$$inlined$map$1$2$1 r0 = (com.android.settings.spa.development.UsageStatsListModel$filter$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.settings.spa.development.UsageStatsListModel$filter$$inlined$map$1$2$1 r0 = new com.android.settings.spa.development.UsageStatsListModel$filter$$inlined$map$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L5f
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
                        if (r2 == 0) goto L54
                        java.lang.Object r2 = r6.next()
                        r4 = r2
                        com.android.settings.spa.development.UsageStatsAppRecord r4 = (com.android.settings.spa.development.UsageStatsAppRecord) r4
                        android.app.usage.UsageStats r4 = r4.usageStats
                        if (r4 == 0) goto L3f
                        r7.add(r2)
                        goto L3f
                    L54:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r7, r0)
                        if (r5 != r1) goto L5f
                        return r1
                    L5f:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException(
                            "Method not decompiled:"
                                + " com.android.settings.spa.development.UsageStatsListModel$filter$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                + " kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect =
                        recordListFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final Comparator getComparator(int i) {
        Comparator comparator;
        SpinnerItem.Companion.getClass();
        int ordinal = ((SpinnerItem) SpinnerItem.$ENTRIES.get(i)).ordinal();
        if (ordinal == 0) {
            final int i2 = 1;
            comparator =
                    new Comparator() { // from class:
                                       // com.android.settings.spa.development.UsageStatsListModel$getComparator$$inlined$compareBy$1
                        @Override // java.util.Comparator
                        public final int compare(Object obj, Object obj2) {
                            switch (i2) {
                                case 0:
                                    return ComparisonsKt__ComparisonsKt.compareValues(0, 0);
                                case 1:
                                    UsageStats usageStats =
                                            ((UsageStatsAppRecord) ((AppEntry) obj2).record)
                                                    .usageStats;
                                    Long valueOf =
                                            usageStats != null
                                                    ? Long.valueOf(
                                                            usageStats.getTotalTimeInForeground())
                                                    : null;
                                    UsageStats usageStats2 =
                                            ((UsageStatsAppRecord) ((AppEntry) obj).record)
                                                    .usageStats;
                                    return ComparisonsKt__ComparisonsKt.compareValues(
                                            valueOf,
                                            usageStats2 != null
                                                    ? Long.valueOf(
                                                            usageStats2.getTotalTimeInForeground())
                                                    : null);
                                default:
                                    UsageStats usageStats3 =
                                            ((UsageStatsAppRecord) ((AppEntry) obj2).record)
                                                    .usageStats;
                                    Long valueOf2 =
                                            usageStats3 != null
                                                    ? Long.valueOf(usageStats3.getLastTimeUsed())
                                                    : null;
                                    UsageStats usageStats4 =
                                            ((UsageStatsAppRecord) ((AppEntry) obj).record)
                                                    .usageStats;
                                    return ComparisonsKt__ComparisonsKt.compareValues(
                                            valueOf2,
                                            usageStats4 != null
                                                    ? Long.valueOf(usageStats4.getLastTimeUsed())
                                                    : null);
                            }
                        }
                    };
        } else if (ordinal != 1) {
            final int i3 = 0;
            comparator =
                    new Comparator() { // from class:
                                       // com.android.settings.spa.development.UsageStatsListModel$getComparator$$inlined$compareBy$1
                        @Override // java.util.Comparator
                        public final int compare(Object obj, Object obj2) {
                            switch (i3) {
                                case 0:
                                    return ComparisonsKt__ComparisonsKt.compareValues(0, 0);
                                case 1:
                                    UsageStats usageStats =
                                            ((UsageStatsAppRecord) ((AppEntry) obj2).record)
                                                    .usageStats;
                                    Long valueOf =
                                            usageStats != null
                                                    ? Long.valueOf(
                                                            usageStats.getTotalTimeInForeground())
                                                    : null;
                                    UsageStats usageStats2 =
                                            ((UsageStatsAppRecord) ((AppEntry) obj).record)
                                                    .usageStats;
                                    return ComparisonsKt__ComparisonsKt.compareValues(
                                            valueOf,
                                            usageStats2 != null
                                                    ? Long.valueOf(
                                                            usageStats2.getTotalTimeInForeground())
                                                    : null);
                                default:
                                    UsageStats usageStats3 =
                                            ((UsageStatsAppRecord) ((AppEntry) obj2).record)
                                                    .usageStats;
                                    Long valueOf2 =
                                            usageStats3 != null
                                                    ? Long.valueOf(usageStats3.getLastTimeUsed())
                                                    : null;
                                    UsageStats usageStats4 =
                                            ((UsageStatsAppRecord) ((AppEntry) obj).record)
                                                    .usageStats;
                                    return ComparisonsKt__ComparisonsKt.compareValues(
                                            valueOf2,
                                            usageStats4 != null
                                                    ? Long.valueOf(usageStats4.getLastTimeUsed())
                                                    : null);
                            }
                        }
                    };
        } else {
            final int i4 = 2;
            comparator =
                    new Comparator() { // from class:
                                       // com.android.settings.spa.development.UsageStatsListModel$getComparator$$inlined$compareBy$1
                        @Override // java.util.Comparator
                        public final int compare(Object obj, Object obj2) {
                            switch (i4) {
                                case 0:
                                    return ComparisonsKt__ComparisonsKt.compareValues(0, 0);
                                case 1:
                                    UsageStats usageStats =
                                            ((UsageStatsAppRecord) ((AppEntry) obj2).record)
                                                    .usageStats;
                                    Long valueOf =
                                            usageStats != null
                                                    ? Long.valueOf(
                                                            usageStats.getTotalTimeInForeground())
                                                    : null;
                                    UsageStats usageStats2 =
                                            ((UsageStatsAppRecord) ((AppEntry) obj).record)
                                                    .usageStats;
                                    return ComparisonsKt__ComparisonsKt.compareValues(
                                            valueOf,
                                            usageStats2 != null
                                                    ? Long.valueOf(
                                                            usageStats2.getTotalTimeInForeground())
                                                    : null);
                                default:
                                    UsageStats usageStats3 =
                                            ((UsageStatsAppRecord) ((AppEntry) obj2).record)
                                                    .usageStats;
                                    Long valueOf2 =
                                            usageStats3 != null
                                                    ? Long.valueOf(usageStats3.getLastTimeUsed())
                                                    : null;
                                    UsageStats usageStats4 =
                                            ((UsageStatsAppRecord) ((AppEntry) obj).record)
                                                    .usageStats;
                                    return ComparisonsKt__ComparisonsKt.compareValues(
                                            valueOf2,
                                            usageStats4 != null
                                                    ? Long.valueOf(usageStats4.getLastTimeUsed())
                                                    : null);
                            }
                        }
                    };
        }
        return new ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda0(
                comparator,
                (ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda1) super.getComparator(i));
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final List getSpinnerOptions(List recordList) {
        Intrinsics.checkNotNullParameter(recordList, "recordList");
        EnumEntries<SpinnerItem> enumEntries = SpinnerItem.$ENTRIES;
        ArrayList arrayList =
                new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(enumEntries, 10));
        for (SpinnerItem spinnerItem : enumEntries) {
            int ordinal = spinnerItem.ordinal();
            String string = this.context.getString(spinnerItem.getStringResId());
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            arrayList.add(new SpinnerOption(ordinal, string));
        }
        return arrayList;
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final Function0 getSummary(int i, AppRecord appRecord, Composer composer) {
        UsageStatsAppRecord record = (UsageStatsAppRecord) appRecord;
        Intrinsics.checkNotNullParameter(record, "record");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-1735813079);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        UsageStats usageStats = record.usageStats;
        if (usageStats == null) {
            composerImpl.end(false);
            return null;
        }
        final String str =
                this.context.getString(R.string.last_time_used_label)
                        + ": "
                        + (usageStats.getLastTimeUsed() < Duration.ofDays(1L).toMillis()
                                ? this.context.getString(R.string.last_time_used_never)
                                : DateUtils.formatSameDayTime(
                                        usageStats.getLastTimeUsed(), this.now, 2, 2));
        String formatElapsedTime =
                DateUtils.formatElapsedTime(usageStats.getTotalTimeInForeground() / 1000);
        final String str2 =
                this.context.getString(R.string.usage_time_label) + ": " + formatElapsedTime;
        composerImpl.startReplaceGroup(1000950494);
        boolean changed = composerImpl.changed(str) | composerImpl.changed(str2);
        Object rememberedValue = composerImpl.rememberedValue();
        if (changed || rememberedValue == Composer.Companion.Empty) {
            rememberedValue =
                    new Function0() { // from class:
                                      // com.android.settings.spa.development.UsageStatsListModel$getSummary$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(
                                    str, "\n", str2);
                        }
                    };
            composerImpl.updateRememberedValue(rememberedValue);
        }
        Function0 function0 = (Function0) rememberedValue;
        composerImpl.end(false);
        composerImpl.end(false);
        return function0;
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final Flow transform(
            final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2,
            FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1
                    flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1) {
        return FlowKt.flowCombine(
                new Flow() { // from class:
                    // com.android.settings.spa.development.UsageStatsListModel$transform$$inlined$map$1

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    /* renamed from: com.android.settings.spa.development.UsageStatsListModel$transform$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ UsageStatsListModel this$0;

                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                        @Metadata(
                                k = 3,
                                mv = {1, 9, 0},
                                xi = 48)
                        /* renamed from: com.android.settings.spa.development.UsageStatsListModel$transform$$inlined$map$1$2$1, reason: invalid class name */
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
                                UsageStatsListModel usageStatsListModel) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = usageStatsListModel;
                        }

                        /* JADX WARN: Multi-variable type inference failed */
                        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                        /* JADX WARN: Type inference failed for: r2v2, types: [java.lang.Object] */
                        /* JADX WARN: Type inference failed for: r2v4, types: [android.app.usage.UsageStats] */
                        /* JADX WARN: Type inference failed for: r2v5, types: [java.lang.Object] */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(
                                java.lang.Object r13, kotlin.coroutines.Continuation r14) {
                            /*
                                r12 = this;
                                boolean r0 = r14 instanceof com.android.settings.spa.development.UsageStatsListModel$transform$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r14
                                com.android.settings.spa.development.UsageStatsListModel$transform$$inlined$map$1$2$1 r0 = (com.android.settings.spa.development.UsageStatsListModel$transform$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.settings.spa.development.UsageStatsListModel$transform$$inlined$map$1$2$1 r0 = new com.android.settings.spa.development.UsageStatsListModel$transform$$inlined$map$1$2$1
                                r0.<init>(r14)
                            L18:
                                java.lang.Object r14 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r14)
                                goto L92
                            L27:
                                java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
                                java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
                                r12.<init>(r13)
                                throw r12
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r14)
                                java.lang.Number r13 = (java.lang.Number) r13
                                r13.intValue()
                                com.android.settings.spa.development.UsageStatsListModel r13 = r12.this$0
                                r13.getClass()
                                java.util.concurrent.TimeUnit r14 = java.util.concurrent.TimeUnit.DAYS
                                r4 = 5
                                long r4 = r14.toMillis(r4)
                                long r10 = r13.now
                                long r8 = r10 - r4
                                android.app.usage.UsageStatsManager r6 = r13.usageStatsManager
                                r7 = 4
                                java.util.List r13 = r6.queryUsageStats(r7, r8, r10)
                                java.lang.String r14 = "queryUsageStats(...)"
                                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r13, r14)
                                java.lang.Iterable r13 = (java.lang.Iterable) r13
                                java.util.LinkedHashMap r14 = new java.util.LinkedHashMap
                                r14.<init>()
                                java.util.Iterator r13 = r13.iterator()
                            L5f:
                                boolean r2 = r13.hasNext()
                                if (r2 == 0) goto L87
                                java.lang.Object r2 = r13.next()
                                r4 = r2
                                android.app.usage.UsageStats r4 = (android.app.usage.UsageStats) r4
                                java.lang.String r5 = r4.getPackageName()
                                java.lang.Object r6 = r14.get(r5)
                                if (r6 != 0) goto L7d
                                boolean r7 = r14.containsKey(r5)
                                if (r7 != 0) goto L7d
                                goto L83
                            L7d:
                                r2 = r6
                                android.app.usage.UsageStats r2 = (android.app.usage.UsageStats) r2
                                r2.add(r4)
                            L83:
                                r14.put(r5, r2)
                                goto L5f
                            L87:
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r12 = r12.$this_unsafeFlow
                                java.lang.Object r12 = r12.emit(r14, r0)
                                if (r12 != r1) goto L92
                                return r1
                            L92:
                                kotlin.Unit r12 = kotlin.Unit.INSTANCE
                                return r12
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.android.settings.spa.development.UsageStatsListModel$transform$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                        + " kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(
                            FlowCollector flowCollector, Continuation continuation) {
                        Object collect =
                                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2.collect(
                                        new AnonymousClass2(flowCollector, this), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED
                                ? collect
                                : Unit.INSTANCE;
                    }
                },
                flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1,
                new UsageStatsListModel$transform$2(3, null));
    }
}
