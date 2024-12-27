package com.android.settingslib.spaprivileged.template.app;

import android.content.Context;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;

import com.android.settings.R;
import com.android.settingslib.spaprivileged.framework.compose.StringResourcesKt;
import com.android.settingslib.spaprivileged.model.app.AppListModel;
import com.android.settingslib.spaprivileged.model.app.AppRecord;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;
import com.android.settingslib.spaprivileged.model.enterprise.EnhancedConfirmation;
import com.android.settingslib.spaprivileged.model.enterprise.RestrictedMode;
import com.android.settingslib.spaprivileged.model.enterprise.Restrictions;
import com.android.settingslib.spaprivileged.model.enterprise.RestrictionsProviderKt;
import com.android.settingslib.spaprivileged.template.preference.RestrictedSwitchPreferenceModel;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class TogglePermissionInternalAppListModel implements AppListModel {
    public final Context context;
    public final TogglePermissionAppListModel listModel;
    public final String permissionType;
    public final Function2 restrictionsProviderFactory;

    public TogglePermissionInternalAppListModel(
            Context context,
            String permissionType,
            TogglePermissionAppListModel listModel,
            Function2 restrictionsProviderFactory) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(permissionType, "permissionType");
        Intrinsics.checkNotNullParameter(listModel, "listModel");
        Intrinsics.checkNotNullParameter(
                restrictionsProviderFactory, "restrictionsProviderFactory");
        this.context = context;
        this.permissionType = permissionType;
        this.listModel = listModel;
        this.restrictionsProviderFactory = restrictionsProviderFactory;
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final void AppItem(
            final AppListItemModel appListItemModel, Composer composer, final int i) {
        Intrinsics.checkNotNullParameter(appListItemModel, "<this>");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1530735780);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        List list = TogglePermissionAppInfoPageProvider.PAGE_PARAMETER;
        AppListItemModelKt.AppListItem(
                appListItemModel,
                TogglePermissionAppInfoPageProvider.Companion.navigator(
                        this.permissionType, appListItemModel.record.getApp(), composerImpl),
                composerImpl,
                i & 14);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.app.TogglePermissionInternalAppListModel$AppItem$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            TogglePermissionInternalAppListModel.this.AppItem(
                                    appListItemModel,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.settingslib.spaprivileged.template.app.TogglePermissionInternalAppListModel$filter$$inlined$filterItem$1] */
    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final Flow filter(
            FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2,
            int i,
            final ReadonlySharedFlow recordListFlow) {
        Intrinsics.checkNotNullParameter(recordListFlow, "recordListFlow");
        return this.listModel.filter(
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2,
                new Flow() { // from class:
                    // com.android.settingslib.spaprivileged.template.app.TogglePermissionInternalAppListModel$filter$$inlined$filterItem$1

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    /* renamed from: com.android.settingslib.spaprivileged.template.app.TogglePermissionInternalAppListModel$filter$$inlined$filterItem$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                        @Metadata(
                                k = 3,
                                mv = {1, 9, 0},
                                xi = 48)
                        /* renamed from: com.android.settingslib.spaprivileged.template.app.TogglePermissionInternalAppListModel$filter$$inlined$filterItem$1$2$1, reason: invalid class name */
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
                                boolean r0 = r7 instanceof com.android.settingslib.spaprivileged.template.app.TogglePermissionInternalAppListModel$filter$$inlined$filterItem$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r7
                                com.android.settingslib.spaprivileged.template.app.TogglePermissionInternalAppListModel$filter$$inlined$filterItem$1$2$1 r0 = (com.android.settingslib.spaprivileged.template.app.TogglePermissionInternalAppListModel$filter$$inlined$filterItem$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.settingslib.spaprivileged.template.app.TogglePermissionInternalAppListModel$filter$$inlined$filterItem$1$2$1 r0 = new com.android.settingslib.spaprivileged.template.app.TogglePermissionInternalAppListModel$filter$$inlined$filterItem$1$2$1
                                r0.<init>(r7)
                            L18:
                                java.lang.Object r7 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r7)
                                goto L62
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
                                if (r2 == 0) goto L57
                                java.lang.Object r2 = r6.next()
                                r4 = r2
                                com.android.settingslib.spaprivileged.model.app.AppRecord r4 = (com.android.settingslib.spaprivileged.model.app.AppRecord) r4
                                boolean r4 = com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListKt.isSystemOrRootUid(r4)
                                r4 = r4 ^ r3
                                if (r4 == 0) goto L3f
                                r7.add(r2)
                                goto L3f
                            L57:
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                                java.lang.Object r5 = r5.emit(r7, r0)
                                if (r5 != r1) goto L62
                                return r1
                            L62:
                                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                                return r5
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.android.settingslib.spaprivileged.template.app.TogglePermissionInternalAppListModel$filter$$inlined$filterItem$1.AnonymousClass2.emit(java.lang.Object,"
                                        + " kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(
                            FlowCollector flowCollector, Continuation continuation) {
                        Object collect =
                                recordListFlow.collect(
                                        new AnonymousClass2(flowCollector), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED
                                ? collect
                                : Unit.INSTANCE;
                    }
                });
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final Function0 getSummary(int i, AppRecord record, Composer composer) {
        Intrinsics.checkNotNullParameter(record, "record");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-1142561976);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Function0 summary = getSummary(record, composerImpl, 64);
        composerImpl.end(false);
        return summary;
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final Flow transform(
            FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2,
            FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1
                    flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1) {
        return this.listModel.transform(
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2,
                flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1);
    }

    public final Function0 getSummary(AppRecord record, Composer composer, int i) {
        EnhancedConfirmation enhancedConfirmation;
        Intrinsics.checkNotNullParameter(record, "record");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1584309989);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        int userId = ApplicationInfosKt.getUserId(record.getApp());
        String str = record.getApp().packageName;
        composerImpl.startReplaceGroup(559935417);
        boolean changed = composerImpl.changed(userId) | composerImpl.changed(str);
        Object rememberedValue = composerImpl.rememberedValue();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        TogglePermissionAppListModel togglePermissionAppListModel = this.listModel;
        if (changed || rememberedValue == composer$Companion$Empty$1) {
            int userId2 = ApplicationInfosKt.getUserId(record.getApp());
            List switchRestrictionKeys = togglePermissionAppListModel.getSwitchRestrictionKeys();
            String enhancedConfirmationKey =
                    togglePermissionAppListModel.getEnhancedConfirmationKey();
            if (enhancedConfirmationKey != null) {
                String packageName = record.getApp().packageName;
                Intrinsics.checkNotNullExpressionValue(packageName, "packageName");
                enhancedConfirmation =
                        new EnhancedConfirmation(enhancedConfirmationKey, packageName);
            } else {
                enhancedConfirmation = null;
            }
            Restrictions restrictions =
                    new Restrictions(userId2, switchRestrictionKeys, enhancedConfirmation);
            composerImpl.updateRememberedValue(restrictions);
            rememberedValue = restrictions;
        }
        composerImpl.end(false);
        final MutableState rememberRestrictedMode =
                RestrictionsProviderKt.rememberRestrictedMode(
                        this.restrictionsProviderFactory,
                        (Restrictions) rememberedValue,
                        composerImpl);
        final Function0 isAllowed = togglePermissionAppListModel.isAllowed(record, composerImpl);
        Context context = this.context;
        composerImpl.startReplaceGroup(559936116);
        boolean changed2 = composerImpl.changed(rememberRestrictedMode);
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (changed2 || rememberedValue2 == composer$Companion$Empty$1) {
            rememberedValue2 =
                    new Function0() { // from class:
                                      // com.android.settingslib.spaprivileged.template.app.TogglePermissionInternalAppListModel$getSummary$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return (RestrictedMode) rememberRestrictedMode.getValue();
                        }
                    };
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        composerImpl.end(false);
        Function0 summary =
                RestrictedSwitchPreferenceModel.Companion.getSummary(
                        context,
                        (Function0) rememberedValue2,
                        new Function0() { // from class:
                                          // com.android.settingslib.spaprivileged.template.app.TogglePermissionInternalAppListModel$getSummary$2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                TogglePermissionInternalAppListModel
                                        togglePermissionInternalAppListModel =
                                                TogglePermissionInternalAppListModel.this;
                                Boolean bool = (Boolean) isAllowed.mo1068invoke();
                                togglePermissionInternalAppListModel.getClass();
                                if (Intrinsics.areEqual(bool, Boolean.TRUE)) {
                                    String string =
                                            togglePermissionInternalAppListModel.context.getString(
                                                    R.string.app_permission_summary_allowed);
                                    Intrinsics.checkNotNullExpressionValue(
                                            string, "getString(...)");
                                    return string;
                                }
                                if (Intrinsics.areEqual(bool, Boolean.FALSE)) {
                                    String string2 =
                                            togglePermissionInternalAppListModel.context.getString(
                                                    R.string.app_permission_summary_not_allowed);
                                    Intrinsics.checkNotNullExpressionValue(
                                            string2, "getString(...)");
                                    return string2;
                                }
                                if (bool == null) {
                                    return StringResourcesKt.getPlaceholder(
                                            togglePermissionInternalAppListModel.context);
                                }
                                throw new NoWhenBranchMatchedException();
                            }
                        },
                        isAllowed);
        composerImpl.end(false);
        return summary;
    }
}
