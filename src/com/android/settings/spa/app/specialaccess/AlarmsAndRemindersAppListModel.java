package com.android.settings.spa.app.specialaccess;

import android.app.compat.CompatChanges;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.PowerExemptionManager;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;

import com.android.settings.R;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.spa.lifecycle.FlowExtKt;
import com.android.settingslib.spaprivileged.model.app.AppOps;
import com.android.settingslib.spaprivileged.model.app.AppOpsPermissionController;
import com.android.settingslib.spaprivileged.model.app.AppRecord;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;
import com.android.settingslib.spaprivileged.model.app.IPackageManagers;
import com.android.settingslib.spaprivileged.model.app.PackageManagers;
import com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel;
import com.android.settingslib.spaprivileged.template.app.TogglePermissionInternalAppListModel$filter$$inlined$filterItem$1;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AlarmsAndRemindersAppListModel implements TogglePermissionAppListModel {
    public static final AppOps APP_OPS = new AppOps(107, 0, true, 2);
    public final Context context;
    public final String enhancedConfirmationKey;
    public final int footerResId;
    public final IPackageManagers packageManagers;
    public final int pageTitleResId;
    public final int switchTitleResId;

    public AlarmsAndRemindersAppListModel(Context context) {
        PackageManagers packageManagers = PackageManagers.INSTANCE;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(packageManagers, "packageManagers");
        this.context = context;
        this.packageManagers = packageManagers;
        this.pageTitleResId = R.string.alarms_and_reminders_title;
        this.switchTitleResId = R.string.alarms_and_reminders_switch_title;
        this.footerResId = R.string.alarms_and_reminders_footer_title;
        this.enhancedConfirmationKey = "android:schedule_exact_alarm";
    }

    public final AlarmsAndRemindersAppRecord createRecord(
            ApplicationInfo applicationInfo, boolean z) {
        boolean z2;
        if (z
                && CompatChanges.isChangeEnabled(
                        171306433L,
                        applicationInfo.packageName,
                        ApplicationInfosKt.getUserHandle(applicationInfo))) {
            boolean z3 =
                    this.packageManagers.hasRequestPermission(
                                    applicationInfo, "android.permission.USE_EXACT_ALARM")
                            && CompatChanges.isChangeEnabled(
                                    218533173L,
                                    applicationInfo.packageName,
                                    ApplicationInfosKt.getUserHandle(applicationInfo));
            PowerExemptionManager powerExemptionManager =
                    (PowerExemptionManager)
                            this.context.getSystemService(PowerExemptionManager.class);
            boolean isAllowListed =
                    powerExemptionManager != null
                            ? powerExemptionManager.isAllowListed(applicationInfo.packageName, true)
                            : false;
            if (z3 || isAllowListed) {
                z2 = true;
                return new AlarmsAndRemindersAppRecord(
                        applicationInfo,
                        z2,
                        (z || z2) ? false : true,
                        new AppOpsPermissionController(
                                this.context,
                                applicationInfo,
                                APP_OPS,
                                "android.permission.SCHEDULE_EXACT_ALARM"));
            }
        }
        z2 = false;
        return new AlarmsAndRemindersAppRecord(
                applicationInfo,
                z2,
                (z || z2) ? false : true,
                new AppOpsPermissionController(
                        this.context,
                        applicationInfo,
                        APP_OPS,
                        "android.permission.SCHEDULE_EXACT_ALARM"));
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final Flow filter(
            FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2,
            TogglePermissionInternalAppListModel$filter$$inlined$filterItem$1
                    togglePermissionInternalAppListModel$filter$$inlined$filterItem$1) {
        return new AlarmsAndRemindersAppListModel$filter$$inlined$map$1(
                togglePermissionInternalAppListModel$filter$$inlined$filterItem$1, 0);
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final String getEnhancedConfirmationKey() {
        return this.enhancedConfirmationKey;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final int getFooterResId() {
        return this.footerResId;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final int getPageTitleResId() {
        return this.pageTitleResId;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final int getSwitchTitleResId() {
        return this.switchTitleResId;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final Function0 isAllowed(AppRecord appRecord, Composer composer) {
        AlarmsAndRemindersAppRecord record = (AlarmsAndRemindersAppRecord) appRecord;
        Intrinsics.checkNotNullParameter(record, "record");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1565312800);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Function0 collectAsCallbackWithLifecycle =
                record.isTrumped
                        ? new Function0() { // from class:
                                            // com.android.settings.spa.app.specialaccess.AlarmsAndRemindersAppListModel$isAllowed$1
                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final /* bridge */ /* synthetic */ Object mo1068invoke() {
                                return Boolean.TRUE;
                            }
                        }
                        : FlowExtKt.collectAsCallbackWithLifecycle(
                                record.controller.isAllowedFlow, composerImpl);
        composerImpl.end(false);
        return collectAsCallbackWithLifecycle;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final boolean isChangeable(AppRecord appRecord) {
        AlarmsAndRemindersAppRecord record = (AlarmsAndRemindersAppRecord) appRecord;
        Intrinsics.checkNotNullParameter(record, "record");
        return record.isChangeable;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final void setAllowed(AppRecord appRecord, boolean z) {
        AlarmsAndRemindersAppRecord record = (AlarmsAndRemindersAppRecord) appRecord;
        Intrinsics.checkNotNullParameter(record, "record");
        record.controller.appOpsController.setAllowed(z);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl
                .getMetricsFeatureProvider()
                .action(0, 1752, 1869, z ? 1 : 0, ApnSettings.MVNO_NONE);
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final Flow transform(
            FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2,
            FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1
                    flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1) {
        return FlowKt.flowCombine(
                new AlarmsAndRemindersAppListModel$filter$$inlined$map$1(
                        flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, 1),
                flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1,
                new AlarmsAndRemindersAppListModel$transform$2(this, null));
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final AppRecord transformItem(ApplicationInfo app) {
        Intrinsics.checkNotNullParameter(app, "app");
        return createRecord(
                app,
                this.packageManagers.hasRequestPermission(
                        app, "android.permission.SCHEDULE_EXACT_ALARM"));
    }
}
