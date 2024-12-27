package com.android.settings.spa.app.appcompat;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.UserHandle;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.lifecycle.compose.FlowExtKt;

import com.android.settings.R;
import com.android.settings.applications.appcompat.UserAspectRatioDetails;
import com.android.settings.applications.appcompat.UserAspectRatioManager;
import com.android.settings.applications.appinfo.AppInfoDashboardFragment;
import com.android.settingslib.spa.widget.ui.SpinnerOption;
import com.android.settingslib.spaprivileged.model.app.AppListModel;
import com.android.settingslib.spaprivileged.model.app.AppRecord;
import com.android.settingslib.spaprivileged.template.app.AppListItemModel;
import com.android.settingslib.spaprivileged.template.app.AppListItemModelKt;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.scheduling.DefaultIoScheduler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UserAspectRatioAppListModel implements AppListModel {
    public static final PackageManager.PackageInfoFlags GET_ACTIVITIES_FLAGS;
    public final Context context;
    public final CoroutineDispatcher ioDispatcher;
    public final PackageManager packageManager;
    public final UserAspectRatioManager userAspectRatioManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SpinnerItem.values().length];
            try {
                SpinnerItem spinnerItem = SpinnerItem.Suggested;
                iArr[0] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                SpinnerItem spinnerItem2 = SpinnerItem.Suggested;
                iArr[2] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        PackageManager.PackageInfoFlags of = PackageManager.PackageInfoFlags.of(1L);
        Intrinsics.checkNotNullExpressionValue(of, "of(...)");
        GET_ACTIVITIES_FLAGS = of;
    }

    public UserAspectRatioAppListModel(Context context) {
        DefaultIoScheduler ioDispatcher = Dispatchers.IO;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(ioDispatcher, "ioDispatcher");
        this.context = context;
        this.ioDispatcher = ioDispatcher;
        this.packageManager = context.getPackageManager();
        this.userAspectRatioManager = new UserAspectRatioManager(context);
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final void AppItem(
            final AppListItemModel appListItemModel, Composer composer, final int i) {
        Intrinsics.checkNotNullParameter(appListItemModel, "<this>");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1019598530);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final ApplicationInfo applicationInfo =
                ((UserAspectRatioAppListItemModel) appListItemModel.record).app;
        AppListItemModelKt.AppListItem(
                appListItemModel,
                new Function0() { // from class:
                                  // com.android.settings.spa.app.appcompat.UserAspectRatioAppListModel$AppItem$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        Context context = UserAspectRatioAppListModel.this.context;
                        ApplicationInfo app = applicationInfo;
                        Intrinsics.checkNotNullParameter(context, "context");
                        Intrinsics.checkNotNullParameter(app, "app");
                        AppInfoDashboardFragment.startAppInfoFragment(
                                UserAspectRatioDetails.class, app, context, 2056);
                        return Unit.INSTANCE;
                    }
                },
                composerImpl,
                8);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.appcompat.UserAspectRatioAppListModel$AppItem$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            UserAspectRatioAppListModel.this.AppItem(
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
                                ? new Function1() { // from class:
                                                    // com.android.settings.spa.app.appcompat.UserAspectRatioAppListModel$filter$3
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj) {
                                        UserAspectRatioAppListItemModel it =
                                                (UserAspectRatioAppListItemModel) obj;
                                        Intrinsics.checkNotNullParameter(it, "it");
                                        return Boolean.valueOf(it.canDisplay);
                                    }
                                }
                                : new Function1() { // from class:
                                                    // com.android.settings.spa.app.appcompat.UserAspectRatioAppListModel$filter$2
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj) {
                                        boolean z;
                                        UserAspectRatioAppListItemModel it =
                                                (UserAspectRatioAppListItemModel) obj;
                                        Intrinsics.checkNotNullParameter(it, "it");
                                        UserAspectRatioManager userAspectRatioManager =
                                                UserAspectRatioAppListModel.this
                                                        .userAspectRatioManager;
                                        ApplicationInfo applicationInfo = it.app;
                                        userAspectRatioManager.getClass();
                                        int i3 = it.userOverride;
                                        if (i3 == 0 || i3 == 7) {
                                            String str = applicationInfo.packageName;
                                            int userId = UserHandle.getUserId(applicationInfo.uid);
                                            if (i3 != 0
                                                    || !userAspectRatioManager
                                                            .isOverrideToFullscreenEnabled(
                                                                    userId, str)) {
                                                z = false;
                                                return Boolean.valueOf(z);
                                            }
                                        }
                                        z = true;
                                        return Boolean.valueOf(z);
                                    }
                                }
                        : new Function1() { // from class:
                                            // com.android.settings.spa.app.appcompat.UserAspectRatioAppListModel$filter$1
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                UserAspectRatioAppListItemModel it =
                                        (UserAspectRatioAppListItemModel) obj;
                                Intrinsics.checkNotNullParameter(it, "it");
                                return Boolean.valueOf(it.canDisplay && it.suggested);
                            }
                        };
        return new Flow() { // from class:
            // com.android.settings.spa.app.appcompat.UserAspectRatioAppListModel$filter$$inlined$filterItem$1

            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
            /* renamed from: com.android.settings.spa.app.appcompat.UserAspectRatioAppListModel$filter$$inlined$filterItem$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ Function1 $predicate$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                @Metadata(
                        k = 3,
                        mv = {1, 9, 0},
                        xi = 48)
                /* renamed from: com.android.settings.spa.app.appcompat.UserAspectRatioAppListModel$filter$$inlined$filterItem$1$2$1, reason: invalid class name */
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
                        boolean r0 = r7 instanceof com.android.settings.spa.app.appcompat.UserAspectRatioAppListModel$filter$$inlined$filterItem$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.settings.spa.app.appcompat.UserAspectRatioAppListModel$filter$$inlined$filterItem$1$2$1 r0 = (com.android.settings.spa.app.appcompat.UserAspectRatioAppListModel$filter$$inlined$filterItem$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.settings.spa.app.appcompat.UserAspectRatioAppListModel$filter$$inlined$filterItem$1$2$1 r0 = new com.android.settings.spa.app.appcompat.UserAspectRatioAppListModel$filter$$inlined$filterItem$1$2$1
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
                                + " com.android.settings.spa.app.appcompat.UserAspectRatioAppListModel$filter$$inlined$filterItem$1.AnonymousClass2.emit(java.lang.Object,"
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
        List<UserAspectRatioAppListItemModel> list = recordList;
        boolean z2 = list instanceof Collection;
        boolean z3 = true;
        if (!z2 || !list.isEmpty()) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                if (((UserAspectRatioAppListItemModel) it.next()).suggested) {
                    z = true;
                    break;
                }
            }
        }
        z = false;
        if (!z2 || !list.isEmpty()) {
            for (UserAspectRatioAppListItemModel userAspectRatioAppListItemModel : list) {
                ApplicationInfo applicationInfo = userAspectRatioAppListItemModel.app;
                UserAspectRatioManager userAspectRatioManager = this.userAspectRatioManager;
                userAspectRatioManager.getClass();
                int i = userAspectRatioAppListItemModel.userOverride;
                if (i != 0 && i != 7) {
                    break;
                }
                String str = applicationInfo.packageName;
                int userId = UserHandle.getUserId(applicationInfo.uid);
                if (i == 0 && userAspectRatioManager.isOverrideToFullscreenEnabled(userId, str)) {
                    break;
                }
            }
        }
        z3 = false;
        List<SpinnerItem> mutableListOf =
                CollectionsKt__CollectionsKt.mutableListOf(SpinnerItem.All);
        if (z) {
            mutableListOf.add(0, SpinnerItem.Suggested);
        }
        if (z3) {
            mutableListOf.add(SpinnerItem.Overridden);
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
        UserAspectRatioAppListItemModel record = (UserAspectRatioAppListItemModel) appRecord;
        Intrinsics.checkNotNullParameter(record, "record");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(319413895);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        composerImpl.startReplaceGroup(-1595142116);
        boolean changed = composerImpl.changed(record.userOverride);
        Object rememberedValue = composerImpl.rememberedValue();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (changed || rememberedValue == composer$Companion$Empty$1) {
            rememberedValue =
                    FlowKt.flowOn(
                            new SafeFlow(
                                    new UserAspectRatioAppListModel$getSummary$summary$2$1(
                                            this, record, null)),
                            this.ioDispatcher);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        composerImpl.end(false);
        final MutableState collectAsStateWithLifecycle =
                FlowExtKt.collectAsStateWithLifecycle(
                        (Flow) rememberedValue,
                        StringResources_androidKt.stringResource(
                                composerImpl, R.string.summary_placeholder),
                        composerImpl,
                        8);
        composerImpl.startReplaceGroup(-1595141760);
        boolean changed2 = composerImpl.changed(collectAsStateWithLifecycle);
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (changed2 || rememberedValue2 == composer$Companion$Empty$1) {
            rememberedValue2 =
                    new Function0() { // from class:
                                      // com.android.settings.spa.app.appcompat.UserAspectRatioAppListModel$getSummary$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            String str = (String) collectAsStateWithLifecycle.getValue();
                            Intrinsics.checkNotNullExpressionValue(
                                    str, "access$getSummary$lambda$4(...)");
                            return str;
                        }
                    };
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        Function0 function0 = (Function0) rememberedValue2;
        composerImpl.end(false);
        composerImpl.end(false);
        return function0;
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final Flow transform(
            FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2,
            FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1
                    flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1) {
        return FlowKt.flowCombine(
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2,
                flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1,
                new UserAspectRatioAppListModel$transform$1(this, null));
    }
}
