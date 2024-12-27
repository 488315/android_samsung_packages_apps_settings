package com.android.settings.spa.system;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

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
import com.android.settings.localepicker.AppLocalePickerActivity;
import com.android.settingslib.spaprivileged.model.app.AppListModel;
import com.android.settingslib.spaprivileged.model.app.AppRecord;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;
import com.android.settingslib.spaprivileged.template.app.AppListItemModel;
import com.android.settingslib.spaprivileged.template.app.AppListItemModelKt;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppLanguagesListModel implements AppListModel {
    public final Context context;
    public final PackageManager packageManager;

    public AppLanguagesListModel(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.packageManager = context.getPackageManager();
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final void AppItem(
            final AppListItemModel appListItemModel, Composer composer, final int i) {
        Intrinsics.checkNotNullParameter(appListItemModel, "<this>");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1497924077);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        AppListItemModelKt.AppListItem(
                appListItemModel,
                new Function0() { // from class:
                                  // com.android.settings.spa.system.AppLanguagesListModel$AppItem$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        Intent intent =
                                new Intent(
                                        AppLanguagesListModel.this.context,
                                        (Class<?>) AppLocalePickerActivity.class);
                        intent.setData(
                                Uri.parse(
                                        "package:"
                                                + ((AppLanguagesRecord) appListItemModel.record)
                                                        .app
                                                        .packageName));
                        AppLanguagesListModel.this.context.startActivityAsUser(
                                intent,
                                ApplicationInfosKt.getUserHandle(
                                        ((AppLanguagesRecord) appListItemModel.record).app));
                        return Unit.INSTANCE;
                    }
                },
                composerImpl,
                8);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.system.AppLanguagesListModel$AppItem$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppLanguagesListModel.this.AppItem(
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
        return new Flow() { // from class:
            // com.android.settings.spa.system.AppLanguagesListModel$filter$$inlined$filterItem$1

            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
            /* renamed from: com.android.settings.spa.system.AppLanguagesListModel$filter$$inlined$filterItem$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                @Metadata(
                        k = 3,
                        mv = {1, 9, 0},
                        xi = 48)
                /* renamed from: com.android.settings.spa.system.AppLanguagesListModel$filter$$inlined$filterItem$1$2$1, reason: invalid class name */
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
                        boolean r0 = r7 instanceof com.android.settings.spa.system.AppLanguagesListModel$filter$$inlined$filterItem$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.settings.spa.system.AppLanguagesListModel$filter$$inlined$filterItem$1$2$1 r0 = (com.android.settings.spa.system.AppLanguagesListModel$filter$$inlined$filterItem$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.settings.spa.system.AppLanguagesListModel$filter$$inlined$filterItem$1$2$1 r0 = new com.android.settings.spa.system.AppLanguagesListModel$filter$$inlined$filterItem$1$2$1
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
                        com.android.settings.spa.system.AppLanguagesRecord r4 = (com.android.settings.spa.system.AppLanguagesRecord) r4
                        boolean r4 = r4.isAppLocaleSupported
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
                                + " com.android.settings.spa.system.AppLanguagesListModel$filter$$inlined$filterItem$1.AnonymousClass2.emit(java.lang.Object,"
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
    public final Function0 getSummary(int i, AppRecord appRecord, Composer composer) {
        AppLanguagesRecord record = (AppLanguagesRecord) appRecord;
        Intrinsics.checkNotNullParameter(record, "record");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1209246014);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        ApplicationInfo applicationInfo = record.app;
        composerImpl.startReplaceGroup(-5181520);
        boolean changed = composerImpl.changed(applicationInfo);
        Object rememberedValue = composerImpl.rememberedValue();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (changed || rememberedValue == composer$Companion$Empty$1) {
            rememberedValue =
                    FlowKt.flowOn(
                            new SafeFlow(
                                    new AppLanguagesListModel$getSummary$summary$2$1(
                                            this, record, null)),
                            Dispatchers.IO);
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
        composerImpl.startReplaceGroup(-5181282);
        boolean changed2 = composerImpl.changed(collectAsStateWithLifecycle);
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (changed2 || rememberedValue2 == composer$Companion$Empty$1) {
            rememberedValue2 =
                    new Function0() { // from class:
                                      // com.android.settings.spa.system.AppLanguagesListModel$getSummary$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return (String) collectAsStateWithLifecycle.getValue();
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
            final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2,
            FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1
                    flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1) {
        return FlowKt.flowCombine(
                new Flow() { // from class:
                    // com.android.settings.spa.system.AppLanguagesListModel$transform$$inlined$map$1

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    /* renamed from: com.android.settings.spa.system.AppLanguagesListModel$transform$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ AppLanguagesListModel this$0;

                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                        @Metadata(
                                k = 3,
                                mv = {1, 9, 0},
                                xi = 48)
                        /* renamed from: com.android.settings.spa.system.AppLanguagesListModel$transform$$inlined$map$1$2$1, reason: invalid class name */
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
                                AppLanguagesListModel appLanguagesListModel) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = appLanguagesListModel;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(
                                java.lang.Object r8, kotlin.coroutines.Continuation r9) {
                            /*
                                r7 = this;
                                boolean r0 = r9 instanceof com.android.settings.spa.system.AppLanguagesListModel$transform$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r9
                                com.android.settings.spa.system.AppLanguagesListModel$transform$$inlined$map$1$2$1 r0 = (com.android.settings.spa.system.AppLanguagesListModel$transform$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.settings.spa.system.AppLanguagesListModel$transform$$inlined$map$1$2$1 r0 = new com.android.settings.spa.system.AppLanguagesListModel$transform$$inlined$map$1$2$1
                                r0.<init>(r9)
                            L18:
                                java.lang.Object r9 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r9)
                                goto L5d
                            L27:
                                java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                                java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                                r7.<init>(r8)
                                throw r7
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r9)
                                java.lang.Number r8 = (java.lang.Number) r8
                                int r8 = r8.intValue()
                                java.lang.Integer r9 = new java.lang.Integer
                                r9.<init>(r8)
                                com.android.settings.spa.system.AppLanguagesListModel r2 = r7.this$0
                                android.content.pm.PackageManager r2 = r2.packageManager
                                android.content.Intent r4 = com.android.settings.applications.AppLocaleUtil.LAUNCHER_ENTRY_INTENT
                                r5 = 128(0x80, double:6.32E-322)
                                android.content.pm.PackageManager$ResolveInfoFlags r5 = android.content.pm.PackageManager.ResolveInfoFlags.of(r5)
                                java.util.List r8 = r2.queryIntentActivitiesAsUser(r4, r5, r8)
                                kotlin.Pair r2 = new kotlin.Pair
                                r2.<init>(r9, r8)
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r7 = r7.$this_unsafeFlow
                                java.lang.Object r7 = r7.emit(r2, r0)
                                if (r7 != r1) goto L5d
                                return r1
                            L5d:
                                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                                return r7
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.android.settings.spa.system.AppLanguagesListModel$transform$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
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
                new AppLanguagesListModel$transform$2(this, null));
    }
}
