package com.android.settingslib.spaprivileged.template.app;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.lifecycle.compose.FlowExtKt;

import com.android.settingslib.spaprivileged.model.app.AppOps;
import com.android.settingslib.spaprivileged.model.app.AppOpsPermissionController;
import com.android.settingslib.spaprivileged.model.app.AppRecord;
import com.android.settingslib.spaprivileged.model.app.IPackageManagers;
import com.android.settingslib.spaprivileged.model.app.PackageManagers;

import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;

import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppOpPermissionListModel implements TogglePermissionAppListModel {
    public final Context context;
    public final Set notChangeablePackages;
    public final IPackageManagers packageManagers;
    public final boolean permissionHasAppOpFlag;

    public AppOpPermissionListModel(Context context, IPackageManagers packageManagers) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(packageManagers, "packageManagers");
        this.context = context;
        this.packageManagers = packageManagers;
        this.permissionHasAppOpFlag = true;
        this.notChangeablePackages = SetsKt.setOf("com.android.systemui");
    }

    public final AppOpPermissionRecord createRecord(ApplicationInfo applicationInfo, boolean z) {
        String broaderPermission = getBroaderPermission();
        return new AppOpPermissionRecord(
                applicationInfo,
                broaderPermission != null
                        ? this.packageManagers.hasRequestPermission(
                                applicationInfo, broaderPermission)
                        : false,
                z,
                new AppOpsPermissionController(
                        this.context, applicationInfo, getAppOps(), getPermission()));
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final Flow filter(
            FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2,
            TogglePermissionInternalAppListModel$filter$$inlined$filterItem$1
                    togglePermissionInternalAppListModel$filter$$inlined$filterItem$1) {
        return new AppOpPermissionListModel$transform$$inlined$map$1(
                togglePermissionInternalAppListModel$filter$$inlined$filterItem$1, this, 1);
    }

    public abstract AppOps getAppOps();

    public String getBroaderPermission() {
        return null;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final String getEnhancedConfirmationKey() {
        return AppOpsManager.opToPublicName(getAppOps().op);
    }

    public abstract String getPermission();

    public boolean getPermissionHasAppOpFlag() {
        return this.permissionHasAppOpFlag;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final Function0 isAllowed(AppRecord appRecord, Composer composer) {
        AppOpPermissionRecord record = (AppOpPermissionRecord) appRecord;
        Intrinsics.checkNotNullParameter(record, "record");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-1810849504);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        if (record.hasRequestBroaderPermission) {
            AppOpPermissionListModel$isAllowed$1 appOpPermissionListModel$isAllowed$1 =
                    new Function0() { // from class:
                                      // com.android.settingslib.spaprivileged.template.app.AppOpPermissionListModel$isAllowed$1
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final /* bridge */ /* synthetic */ Object mo1068invoke() {
                            return Boolean.TRUE;
                        }
                    };
            composerImpl.end(false);
            return appOpPermissionListModel$isAllowed$1;
        }
        final MutableState collectAsStateWithLifecycle =
                FlowExtKt.collectAsStateWithLifecycle(
                        record.appOpsPermissionController.isAllowedFlow, null, composerImpl, 56);
        composerImpl.startReplaceGroup(1207645480);
        boolean changed = composerImpl.changed(collectAsStateWithLifecycle);
        Object rememberedValue = composerImpl.rememberedValue();
        if (changed || rememberedValue == Composer.Companion.Empty) {
            rememberedValue =
                    new Function0() { // from class:
                                      // com.android.settingslib.spaprivileged.template.app.AppOpPermissionListModel$isAllowed$2$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return (Boolean) collectAsStateWithLifecycle.getValue();
                        }
                    };
            composerImpl.updateRememberedValue(rememberedValue);
        }
        Function0 function0 = (Function0) rememberedValue;
        composerImpl.end(false);
        composerImpl.end(false);
        return function0;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final Flow transform(
            FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2,
            FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1
                    flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1) {
        return getPermissionHasAppOpFlag()
                ? FlowKt.flowCombine(
                        new AppOpPermissionListModel$transform$$inlined$map$1(
                                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, this, 0),
                        flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1,
                        new AppOpPermissionListModel$transform$2(this, null))
                : new AppOpPermissionListModel$transform$$inlined$map$1(
                        flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1, this, 2);
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final AppRecord transformItem(ApplicationInfo app) {
        Intrinsics.checkNotNullParameter(app, "app");
        return createRecord(app, this.packageManagers.hasRequestPermission(app, getPermission()));
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public boolean isChangeable(AppOpPermissionRecord record) {
        Intrinsics.checkNotNullParameter(record, "record");
        return (!record.hasRequestPermission
                        || record.hasRequestBroaderPermission
                        || this.notChangeablePackages.contains(record.app.packageName))
                ? false
                : true;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public void setAllowed(AppOpPermissionRecord record, boolean z) {
        Intrinsics.checkNotNullParameter(record, "record");
        record.appOpsPermissionController.appOpsController.setAllowed(z);
    }

    public /* synthetic */ AppOpPermissionListModel(Context context) {
        this(context, PackageManagers.INSTANCE);
    }
}
