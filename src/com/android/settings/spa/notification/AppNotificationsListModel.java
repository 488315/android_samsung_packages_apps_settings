package com.android.settings.spa.notification;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.notification.app.AppNotificationSettings;
import com.android.settingslib.spa.livedata.LiveDataExtKt;
import com.android.settingslib.spa.widget.ui.SpinnerOption;
import com.android.settingslib.spaprivileged.model.app.AppEntry;
import com.android.settingslib.spaprivileged.model.app.AppListModel;
import com.android.settingslib.spaprivileged.model.app.AppRecord;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;
import com.android.settingslib.spaprivileged.template.app.AppListItemModel;
import com.android.settingslib.spaprivileged.template.app.AppListTwoTargetSwitchItemKt;
import com.android.settingslib.utils.StringUtil;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda0;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda1;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppNotificationsListModel implements AppListModel {
    public final Context context;
    public final long now;
    public final AppNotificationRepository repository;

    public AppNotificationsListModel(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.repository = new AppNotificationRepository(context);
        this.now = System.currentTimeMillis();
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final void AppItem(
            final AppListItemModel appListItemModel, Composer composer, final int i) {
        Intrinsics.checkNotNullParameter(appListItemModel, "<this>");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-147444151);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final MutableState produceState =
                SnapshotStateKt.produceState(
                        composerImpl,
                        Boolean.FALSE,
                        new AppNotificationsListModel$AppItem$changeable$2(
                                this, appListItemModel, null));
        Function0 function0 =
                new Function0() { // from class:
                                  // com.android.settings.spa.notification.AppNotificationsListModel$AppItem$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        AppNotificationsListModel appNotificationsListModel =
                                AppNotificationsListModel.this;
                        ApplicationInfo applicationInfo =
                                ((AppNotificationsRecord) appListItemModel.record).app;
                        appNotificationsListModel.getClass();
                        String string =
                                appNotificationsListModel.context.getString(
                                        R.string.notifications_title);
                        Context context = appNotificationsListModel.context;
                        Bundle bundle = new Bundle();
                        bundle.putString("package", applicationInfo.packageName);
                        bundle.putInt(
                                NetworkAnalyticsConstants.DataPoints.UID, applicationInfo.uid);
                        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
                        String name = AppNotificationSettings.class.getName();
                        SubSettingLauncher.LaunchRequest launchRequest =
                                subSettingLauncher.mLaunchRequest;
                        launchRequest.mDestinationName = name;
                        launchRequest.mSourceMetricsCategory = 133;
                        launchRequest.mTitle = string;
                        launchRequest.mArguments = bundle;
                        launchRequest.mUserHandle =
                                UserHandle.getUserHandleForUid(applicationInfo.uid);
                        subSettingLauncher.launch();
                        return Unit.INSTANCE;
                    }
                };
        AppNotificationsRecord appNotificationsRecord =
                (AppNotificationsRecord) appListItemModel.record;
        Function0 observeAsCallback =
                LiveDataExtKt.observeAsCallback(
                        appNotificationsRecord.controller._isEnabled, composerImpl);
        composerImpl.startReplaceGroup(699976494);
        boolean changed = composerImpl.changed(produceState);
        Object rememberedValue = composerImpl.rememberedValue();
        if (changed || rememberedValue == Composer.Companion.Empty) {
            rememberedValue =
                    new Function0() { // from class:
                                      // com.android.settings.spa.notification.AppNotificationsListModel$AppItem$2$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            Boolean bool = (Boolean) produceState.getValue();
                            bool.booleanValue();
                            return bool;
                        }
                    };
            composerImpl.updateRememberedValue(rememberedValue);
        }
        composerImpl.end(false);
        AppListTwoTargetSwitchItemKt.AppListTwoTargetSwitchItem(
                appListItemModel,
                function0,
                observeAsCallback,
                (Function0) rememberedValue,
                new AppNotificationsListModel$AppItem$3(
                        1,
                        appNotificationsRecord.controller,
                        AppNotificationController.class,
                        "setEnabled",
                        "setEnabled(Z)V",
                        0),
                composerImpl,
                8);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.notification.AppNotificationsListModel$AppItem$4
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppNotificationsListModel.this.AppItem(
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
        return new Flow() { // from class:
            // com.android.settings.spa.notification.AppNotificationsListModel$filter$$inlined$map$1

            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
            /* renamed from: com.android.settings.spa.notification.AppNotificationsListModel$filter$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ int $option$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                @Metadata(
                        k = 3,
                        mv = {1, 9, 0},
                        xi = 48)
                /* renamed from: com.android.settings.spa.notification.AppNotificationsListModel$filter$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, int i) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$option$inlined = i;
                }

                /* JADX WARN: Removed duplicated region for block: B:20:0x0068  */
                /* JADX WARN: Removed duplicated region for block: B:31:0x0094 A[LOOP:1: B:29:0x008e->B:31:0x0094, LOOP_END] */
                /* JADX WARN: Removed duplicated region for block: B:35:0x00ac A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:36:0x003c  */
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
                        boolean r0 = r8 instanceof com.android.settings.spa.notification.AppNotificationsListModel$filter$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.settings.spa.notification.AppNotificationsListModel$filter$$inlined$map$1$2$1 r0 = (com.android.settings.spa.notification.AppNotificationsListModel$filter$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.settings.spa.notification.AppNotificationsListModel$filter$$inlined$map$1$2$1 r0 = new com.android.settings.spa.notification.AppNotificationsListModel$filter$$inlined$map$1$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 0
                        r4 = 2
                        r5 = 1
                        if (r2 == 0) goto L3c
                        if (r2 == r5) goto L34
                        if (r2 != r4) goto L2c
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto Lad
                    L2c:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L34:
                        java.lang.Object r6 = r0.L$0
                        kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L57
                    L3c:
                        kotlin.ResultKt.throwOnFailure(r8)
                        java.util.List r7 = (java.util.List) r7
                        java.lang.Iterable r7 = (java.lang.Iterable) r7
                        com.android.settings.spa.notification.AppNotificationsListModel$filter$lambda$1$$inlined$asyncFilter$1 r8 = new com.android.settings.spa.notification.AppNotificationsListModel$filter$lambda$1$$inlined$asyncFilter$1
                        int r2 = r6.$option$inlined
                        r8.<init>(r7, r3, r2)
                        kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                        r0.L$0 = r6
                        r0.label = r5
                        java.lang.Object r8 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r8, r0)
                        if (r8 != r1) goto L57
                        return r1
                    L57:
                        java.lang.Iterable r8 = (java.lang.Iterable) r8
                        java.util.ArrayList r7 = new java.util.ArrayList
                        r7.<init>()
                        java.util.Iterator r8 = r8.iterator()
                    L62:
                        boolean r2 = r8.hasNext()
                        if (r2 == 0) goto L7f
                        java.lang.Object r2 = r8.next()
                        r5 = r2
                        kotlin.Pair r5 = (kotlin.Pair) r5
                        java.lang.Object r5 = r5.getSecond()
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        boolean r5 = r5.booleanValue()
                        if (r5 == 0) goto L62
                        r7.add(r2)
                        goto L62
                    L7f:
                        java.util.ArrayList r8 = new java.util.ArrayList
                        r2 = 10
                        int r2 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r7, r2)
                        r8.<init>(r2)
                        java.util.Iterator r7 = r7.iterator()
                    L8e:
                        boolean r2 = r7.hasNext()
                        if (r2 == 0) goto La2
                        java.lang.Object r2 = r7.next()
                        kotlin.Pair r2 = (kotlin.Pair) r2
                        java.lang.Object r2 = r2.getFirst()
                        r8.add(r2)
                        goto L8e
                    La2:
                        r0.L$0 = r3
                        r0.label = r4
                        java.lang.Object r6 = r6.emit(r8, r0)
                        if (r6 != r1) goto Lad
                        return r1
                    Lad:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException(
                            "Method not decompiled:"
                                + " com.android.settings.spa.notification.AppNotificationsListModel$filter$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                + " kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect =
                        recordListFlow.collect(new AnonymousClass2(flowCollector, i), continuation);
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
                                       // com.android.settings.spa.notification.AppNotificationsListModel$getComparator$$inlined$compareBy$1
                        @Override // java.util.Comparator
                        public final int compare(Object obj, Object obj2) {
                            switch (i2) {
                                case 0:
                                    return ComparisonsKt__ComparisonsKt.compareValues(0, 0);
                                case 1:
                                    NotificationSentState notificationSentState =
                                            ((AppNotificationsRecord) ((AppEntry) obj2).record)
                                                    .sentState;
                                    Long valueOf =
                                            notificationSentState != null
                                                    ? Long.valueOf(notificationSentState.lastSent)
                                                    : null;
                                    NotificationSentState notificationSentState2 =
                                            ((AppNotificationsRecord) ((AppEntry) obj).record)
                                                    .sentState;
                                    return ComparisonsKt__ComparisonsKt.compareValues(
                                            valueOf,
                                            notificationSentState2 != null
                                                    ? Long.valueOf(notificationSentState2.lastSent)
                                                    : null);
                                default:
                                    NotificationSentState notificationSentState3 =
                                            ((AppNotificationsRecord) ((AppEntry) obj2).record)
                                                    .sentState;
                                    Integer valueOf2 =
                                            notificationSentState3 != null
                                                    ? Integer.valueOf(
                                                            notificationSentState3.sentCount)
                                                    : null;
                                    NotificationSentState notificationSentState4 =
                                            ((AppNotificationsRecord) ((AppEntry) obj).record)
                                                    .sentState;
                                    return ComparisonsKt__ComparisonsKt.compareValues(
                                            valueOf2,
                                            notificationSentState4 != null
                                                    ? Integer.valueOf(
                                                            notificationSentState4.sentCount)
                                                    : null);
                            }
                        }
                    };
        } else if (ordinal != 1) {
            final int i3 = 0;
            comparator =
                    new Comparator() { // from class:
                                       // com.android.settings.spa.notification.AppNotificationsListModel$getComparator$$inlined$compareBy$1
                        @Override // java.util.Comparator
                        public final int compare(Object obj, Object obj2) {
                            switch (i3) {
                                case 0:
                                    return ComparisonsKt__ComparisonsKt.compareValues(0, 0);
                                case 1:
                                    NotificationSentState notificationSentState =
                                            ((AppNotificationsRecord) ((AppEntry) obj2).record)
                                                    .sentState;
                                    Long valueOf =
                                            notificationSentState != null
                                                    ? Long.valueOf(notificationSentState.lastSent)
                                                    : null;
                                    NotificationSentState notificationSentState2 =
                                            ((AppNotificationsRecord) ((AppEntry) obj).record)
                                                    .sentState;
                                    return ComparisonsKt__ComparisonsKt.compareValues(
                                            valueOf,
                                            notificationSentState2 != null
                                                    ? Long.valueOf(notificationSentState2.lastSent)
                                                    : null);
                                default:
                                    NotificationSentState notificationSentState3 =
                                            ((AppNotificationsRecord) ((AppEntry) obj2).record)
                                                    .sentState;
                                    Integer valueOf2 =
                                            notificationSentState3 != null
                                                    ? Integer.valueOf(
                                                            notificationSentState3.sentCount)
                                                    : null;
                                    NotificationSentState notificationSentState4 =
                                            ((AppNotificationsRecord) ((AppEntry) obj).record)
                                                    .sentState;
                                    return ComparisonsKt__ComparisonsKt.compareValues(
                                            valueOf2,
                                            notificationSentState4 != null
                                                    ? Integer.valueOf(
                                                            notificationSentState4.sentCount)
                                                    : null);
                            }
                        }
                    };
        } else {
            final int i4 = 2;
            comparator =
                    new Comparator() { // from class:
                                       // com.android.settings.spa.notification.AppNotificationsListModel$getComparator$$inlined$compareBy$1
                        @Override // java.util.Comparator
                        public final int compare(Object obj, Object obj2) {
                            switch (i4) {
                                case 0:
                                    return ComparisonsKt__ComparisonsKt.compareValues(0, 0);
                                case 1:
                                    NotificationSentState notificationSentState =
                                            ((AppNotificationsRecord) ((AppEntry) obj2).record)
                                                    .sentState;
                                    Long valueOf =
                                            notificationSentState != null
                                                    ? Long.valueOf(notificationSentState.lastSent)
                                                    : null;
                                    NotificationSentState notificationSentState2 =
                                            ((AppNotificationsRecord) ((AppEntry) obj).record)
                                                    .sentState;
                                    return ComparisonsKt__ComparisonsKt.compareValues(
                                            valueOf,
                                            notificationSentState2 != null
                                                    ? Long.valueOf(notificationSentState2.lastSent)
                                                    : null);
                                default:
                                    NotificationSentState notificationSentState3 =
                                            ((AppNotificationsRecord) ((AppEntry) obj2).record)
                                                    .sentState;
                                    Integer valueOf2 =
                                            notificationSentState3 != null
                                                    ? Integer.valueOf(
                                                            notificationSentState3.sentCount)
                                                    : null;
                                    NotificationSentState notificationSentState4 =
                                            ((AppNotificationsRecord) ((AppEntry) obj).record)
                                                    .sentState;
                                    return ComparisonsKt__ComparisonsKt.compareValues(
                                            valueOf2,
                                            notificationSentState4 != null
                                                    ? Integer.valueOf(
                                                            notificationSentState4.sentCount)
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
        boolean z;
        Intrinsics.checkNotNullParameter(recordList, "recordList");
        List<SpinnerItem> mutableListOf =
                CollectionsKt__CollectionsKt.mutableListOf(
                        SpinnerItem.AllApps, SpinnerItem.TurnedOff);
        int userId = ApplicationInfosKt.getUserId(((AppNotificationsRecord) recordList.get(0)).app);
        AppNotificationRepository appNotificationRepository = this.repository;
        appNotificationRepository.getClass();
        try {
            z = appNotificationRepository.userManager.isUserUnlocked(userId);
        } catch (Exception e) {
            Log.w("AppNotificationsRepo", "Error calling UserManager", e);
            z = false;
        }
        if (z) {
            mutableListOf.add(0, SpinnerItem.MostRecent);
            mutableListOf.add(1, SpinnerItem.MostFrequent);
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
        AppNotificationsRecord record = (AppNotificationsRecord) appRecord;
        Intrinsics.checkNotNullParameter(record, "record");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-1556839385);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Function0 function0 = null;
        final NotificationSentState notificationSentState = record.sentState;
        if (notificationSentState != null) {
            SpinnerItem.Companion.getClass();
            int ordinal = ((SpinnerItem) SpinnerItem.$ENTRIES.get(i)).ordinal();
            if (ordinal == 0) {
                function0 =
                        new Function0() { // from class:
                                          // com.android.settings.spa.notification.AppNotificationsListModel$getSummary$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                return StringUtil.formatRelativeTime(
                                                AppNotificationsListModel.this.context,
                                                r0.now - notificationSentState.lastSent,
                                                true,
                                                RelativeDateTimeFormatter.Style.LONG)
                                        .toString();
                            }
                        };
            } else if (ordinal == 1) {
                function0 =
                        new Function0() { // from class:
                                          // com.android.settings.spa.notification.AppNotificationsListModel$getSummary$1$2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                return AppNotificationsListModel.this.repository
                                        .calculateFrequencySummary(notificationSentState.sentCount);
                            }
                        };
            }
        }
        composerImpl.end(false);
        return function0;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onFirstLoaded(
            java.util.List r5, kotlin.coroutines.Continuation r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.android.settings.spa.notification.AppNotificationsListModel$onFirstLoaded$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.settings.spa.notification.AppNotificationsListModel$onFirstLoaded$1 r0 = (com.android.settings.spa.notification.AppNotificationsListModel$onFirstLoaded$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settings.spa.notification.AppNotificationsListModel$onFirstLoaded$1 r0 = new com.android.settings.spa.notification.AppNotificationsListModel$onFirstLoaded$1
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r4 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r6 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r0.label
            r2 = 1
            if (r1 == 0) goto L2f
            if (r1 != r2) goto L27
            kotlin.ResultKt.throwOnFailure(r4)
            goto L43
        L27:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2f:
            kotlin.ResultKt.throwOnFailure(r4)
            java.lang.Iterable r5 = (java.lang.Iterable) r5
            com.android.settings.spa.notification.AppNotificationsListModel$onFirstLoaded$$inlined$asyncForEach$1 r4 = new com.android.settings.spa.notification.AppNotificationsListModel$onFirstLoaded$$inlined$asyncForEach$1
            r1 = 0
            r4.<init>(r5, r1)
            r0.label = r2
            java.lang.Object r4 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r4, r0)
            if (r4 != r6) goto L43
            return r6
        L43:
            java.lang.Boolean r4 = java.lang.Boolean.TRUE
            return r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.spa.notification.AppNotificationsListModel.onFirstLoaded(java.util.List,"
                    + " kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final Flow transform(
            final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2,
            FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1
                    flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1) {
        final AppNotificationRepository appNotificationRepository = this.repository;
        appNotificationRepository.getClass();
        return FlowKt.flowCombine(
                new Flow() { // from class:
                    // com.android.settings.spa.notification.AppNotificationRepository$getAggregatedUsageEvents$$inlined$map$1

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    /* renamed from: com.android.settings.spa.notification.AppNotificationRepository$getAggregatedUsageEvents$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ AppNotificationRepository this$0;

                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                        @Metadata(
                                k = 3,
                                mv = {1, 9, 0},
                                xi = 48)
                        /* renamed from: com.android.settings.spa.notification.AppNotificationRepository$getAggregatedUsageEvents$$inlined$map$1$2$1, reason: invalid class name */
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
                                AppNotificationRepository appNotificationRepository) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = appNotificationRepository;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(
                                java.lang.Object r12, kotlin.coroutines.Continuation r13) {
                            /*
                                r11 = this;
                                boolean r0 = r13 instanceof com.android.settings.spa.notification.AppNotificationRepository$getAggregatedUsageEvents$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r13
                                com.android.settings.spa.notification.AppNotificationRepository$getAggregatedUsageEvents$$inlined$map$1$2$1 r0 = (com.android.settings.spa.notification.AppNotificationRepository$getAggregatedUsageEvents$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.settings.spa.notification.AppNotificationRepository$getAggregatedUsageEvents$$inlined$map$1$2$1 r0 = new com.android.settings.spa.notification.AppNotificationRepository$getAggregatedUsageEvents$$inlined$map$1$2$1
                                r0.<init>(r13)
                            L18:
                                java.lang.Object r13 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r13)
                                goto L90
                            L27:
                                java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
                                java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
                                r11.<init>(r12)
                                throw r11
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r13)
                                java.lang.Number r12 = (java.lang.Number) r12
                                int r9 = r12.intValue()
                                java.util.LinkedHashMap r12 = new java.util.LinkedHashMap
                                r12.<init>()
                                com.android.settings.spa.notification.AppNotificationRepository r13 = r11.this$0
                                r13.getClass()
                                long r7 = java.lang.System.currentTimeMillis()
                                java.util.concurrent.TimeUnit r2 = java.util.concurrent.TimeUnit.DAYS
                                r4 = 7
                                long r4 = r2.toMillis(r4)
                                long r5 = r7 - r4
                                android.app.usage.IUsageStatsManager r4 = r13.usageStatsManager     // Catch: android.os.RemoteException -> L5d
                                android.content.Context r13 = r13.context     // Catch: android.os.RemoteException -> L5d
                                java.lang.String r10 = r13.getPackageName()     // Catch: android.os.RemoteException -> L5d
                                android.app.usage.UsageEvents r13 = r4.queryEventsForUser(r5, r7, r9, r10)     // Catch: android.os.RemoteException -> L5d
                                goto L66
                            L5d:
                                r13 = move-exception
                                java.lang.String r2 = "AppNotificationsRepo"
                                java.lang.String r4 = "Failed IUsageStatsManager.queryEventsForUser(): "
                                android.util.Log.e(r2, r4, r13)
                                r13 = 0
                            L66:
                                com.android.settings.spa.notification.AppNotificationRepository$getAggregatedUsageEvents$1$1 r2 = new com.android.settings.spa.notification.AppNotificationRepository$getAggregatedUsageEvents$1$1
                                r2.<init>(r12)
                                if (r13 != 0) goto L6e
                                goto L85
                            L6e:
                                android.app.usage.UsageEvents$Event r4 = new android.app.usage.UsageEvents$Event
                                r4.<init>()
                            L73:
                                boolean r5 = r13.getNextEvent(r4)
                                if (r5 == 0) goto L85
                                int r5 = r4.getEventType()
                                r6 = 12
                                if (r5 != r6) goto L73
                                r2.invoke(r4)
                                goto L73
                            L85:
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r11 = r11.$this_unsafeFlow
                                java.lang.Object r11 = r11.emit(r12, r0)
                                if (r11 != r1) goto L90
                                return r1
                            L90:
                                kotlin.Unit r11 = kotlin.Unit.INSTANCE
                                return r11
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.android.settings.spa.notification.AppNotificationRepository$getAggregatedUsageEvents$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                        + " kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(
                            FlowCollector flowCollector, Continuation continuation) {
                        Object collect =
                                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2.collect(
                                        new AnonymousClass2(
                                                flowCollector, appNotificationRepository),
                                        continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED
                                ? collect
                                : Unit.INSTANCE;
                    }
                },
                flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1,
                new AppNotificationsListModel$transform$1(this, null));
    }
}
