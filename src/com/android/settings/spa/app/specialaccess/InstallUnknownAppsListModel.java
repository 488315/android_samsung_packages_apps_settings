package com.android.settings.spa.app.specialaccess;

import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;

import com.android.settings.R;
import com.android.settingslib.spa.lifecycle.FlowExtKt;
import com.android.settingslib.spaprivileged.model.app.AppOps;
import com.android.settingslib.spaprivileged.model.app.AppOpsController;
import com.android.settingslib.spaprivileged.model.app.AppRecord;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;
import com.android.settingslib.spaprivileged.model.app.IAppOpsController$special$$inlined$map$1;
import com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel;
import com.android.settingslib.spaprivileged.template.app.TogglePermissionInternalAppListModel$filter$$inlined$filterItem$1;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class InstallUnknownAppsListModel implements TogglePermissionAppListModel {
    public final Context context;
    public final String enhancedConfirmationKey;
    public final List switchRestrictionKeys;
    public static final Companion Companion = new Companion();
    public static final AppOps APP_OPS = new AppOps(66, 0, false, 6);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Companion {
        public static final boolean access$isChangeable(
                InstallUnknownAppsRecord installUnknownAppsRecord, Set set) {
            Companion companion = InstallUnknownAppsListModel.Companion;
            AppOpsController appOpsController = installUnknownAppsRecord.appOpsController;
            AppOpsManager appOpsManager = appOpsController.appOpsManager;
            int i = appOpsController.appOps.op;
            ApplicationInfo app = appOpsController.app;
            Intrinsics.checkNotNullParameter(appOpsManager, "<this>");
            Intrinsics.checkNotNullParameter(app, "app");
            return appOpsManager.checkOpNoThrow(i, app.uid, app.packageName) != 3
                    || set.contains(installUnknownAppsRecord.app.packageName);
        }
    }

    public InstallUnknownAppsListModel(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.switchRestrictionKeys =
                CollectionsKt__CollectionsKt.listOf(
                        (Object[])
                                new String[] {
                                    "no_install_unknown_sources",
                                    "no_install_unknown_sources_globally"
                                });
        this.enhancedConfirmationKey = "android:request_install_packages";
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final Flow filter(
            final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2,
            TogglePermissionInternalAppListModel$filter$$inlined$filterItem$1
                    togglePermissionInternalAppListModel$filter$$inlined$filterItem$1) {
        return FlowKt.flowCombine(
                new Flow() { // from class:
                    // com.android.settings.spa.app.specialaccess.InstallUnknownAppsListModel$filter$$inlined$map$1

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    /* renamed from: com.android.settings.spa.app.specialaccess.InstallUnknownAppsListModel$filter$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ InstallUnknownAppsListModel.Companion
                                receiver$inlined;

                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                        @Metadata(
                                k = 3,
                                mv = {1, 9, 0},
                                xi = 48)
                        /* renamed from: com.android.settings.spa.app.specialaccess.InstallUnknownAppsListModel$filter$$inlined$map$1$2$1, reason: invalid class name */
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
                                InstallUnknownAppsListModel.Companion companion) {
                            this.$this_unsafeFlow = flowCollector;
                            this.receiver$inlined = companion;
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
                                boolean r0 = r6 instanceof com.android.settings.spa.app.specialaccess.InstallUnknownAppsListModel$filter$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.settings.spa.app.specialaccess.InstallUnknownAppsListModel$filter$$inlined$map$1$2$1 r0 = (com.android.settings.spa.app.specialaccess.InstallUnknownAppsListModel$filter$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.settings.spa.app.specialaccess.InstallUnknownAppsListModel$filter$$inlined$map$1$2$1 r0 = new com.android.settings.spa.app.specialaccess.InstallUnknownAppsListModel$filter$$inlined$map$1$2$1
                                r0.<init>(r6)
                            L18:
                                java.lang.Object r6 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r6)
                                goto L5b
                            L27:
                                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                r4.<init>(r5)
                                throw r4
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r6)
                                java.lang.Number r5 = (java.lang.Number) r5
                                int r5 = r5.intValue()
                                com.android.settings.spa.app.specialaccess.InstallUnknownAppsListModel$Companion r6 = r4.receiver$inlined
                                r6.getClass()
                                android.content.pm.IPackageManager r6 = android.app.AppGlobals.getPackageManager()
                                java.lang.String r2 = "android.permission.REQUEST_INSTALL_PACKAGES"
                                java.lang.String[] r5 = r6.getAppOpPermissionPackages(r2, r5)
                                java.lang.String r6 = "getAppOpPermissionPackages(...)"
                                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r5, r6)
                                java.util.Set r5 = kotlin.collections.ArraysKt___ArraysKt.toSet(r5)
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                java.lang.Object r4 = r4.emit(r5, r0)
                                if (r4 != r1) goto L5b
                                return r1
                            L5b:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.android.settings.spa.app.specialaccess.InstallUnknownAppsListModel$filter$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                        + " kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    {
                        InstallUnknownAppsListModel.Companion companion =
                                InstallUnknownAppsListModel.Companion;
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(
                            FlowCollector flowCollector, Continuation continuation) {
                        Object collect =
                                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2.collect(
                                        new AnonymousClass2(
                                                flowCollector,
                                                InstallUnknownAppsListModel.Companion),
                                        continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED
                                ? collect
                                : Unit.INSTANCE;
                    }
                },
                togglePermissionInternalAppListModel$filter$$inlined$filterItem$1,
                new InstallUnknownAppsListModel$filter$2(3, null));
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final String getEnhancedConfirmationKey() {
        return this.enhancedConfirmationKey;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final int getFooterResId() {
        return R.string.install_all_warning;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final int getPageTitleResId() {
        return R.string.install_other_apps;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final List getSwitchRestrictionKeys() {
        return this.switchRestrictionKeys;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final int getSwitchTitleResId() {
        return R.string.external_source_switch_title;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final Function0 isAllowed(AppRecord appRecord, Composer composer) {
        InstallUnknownAppsRecord record = (InstallUnknownAppsRecord) appRecord;
        Intrinsics.checkNotNullParameter(record, "record");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1685084892);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        AppOpsController appOpsController = record.appOpsController;
        appOpsController.getClass();
        Function0 collectAsCallbackWithLifecycle =
                FlowExtKt.collectAsCallbackWithLifecycle(
                        new IAppOpsController$special$$inlined$map$1(appOpsController.modeFlow),
                        composerImpl);
        composerImpl.end(false);
        return collectAsCallbackWithLifecycle;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final boolean isChangeable(AppRecord appRecord) {
        InstallUnknownAppsRecord record = (InstallUnknownAppsRecord) appRecord;
        Intrinsics.checkNotNullParameter(record, "record");
        String[] appOpPermissionPackages =
                AppGlobals.getPackageManager()
                        .getAppOpPermissionPackages(
                                "android.permission.REQUEST_INSTALL_PACKAGES",
                                ApplicationInfosKt.getUserId(record.app));
        Intrinsics.checkNotNullExpressionValue(
                appOpPermissionPackages, "getAppOpPermissionPackages(...)");
        return Companion.access$isChangeable(
                record, ArraysKt___ArraysKt.toSet(appOpPermissionPackages));
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final void setAllowed(AppRecord appRecord, boolean z) {
        InstallUnknownAppsRecord record = (InstallUnknownAppsRecord) appRecord;
        Intrinsics.checkNotNullParameter(record, "record");
        record.appOpsController.setAllowed(z);
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final AppRecord transformItem(ApplicationInfo app) {
        Intrinsics.checkNotNullParameter(app, "app");
        return new InstallUnknownAppsRecord(app, new AppOpsController(this.context, app, APP_OPS));
    }
}
