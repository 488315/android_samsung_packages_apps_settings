package com.android.settings.spa.app.backgroundinstall;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.IBackgroundInstallControlService;
import android.os.ServiceManager;
import android.provider.DeviceConfig;
import android.util.Log;

import androidx.compose.material.icons.outlined.DeleteKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;

import com.android.settings.R;
import com.android.settings.spa.app.AppUtilKt;
import com.android.settings.spa.app.appinfo.AppInfoSettingsProvider;
import com.android.settingslib.spa.framework.util.MessageFormatsKt;
import com.android.settingslib.spaprivileged.model.app.AppListModel;
import com.android.settingslib.spaprivileged.model.app.AppRecord;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;
import com.android.settingslib.spaprivileged.template.app.AppListButtonItemKt;
import com.android.settingslib.spaprivileged.template.app.AppListItemModel;

import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;

import java.util.Comparator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BackgroundInstalledAppsWithGroupingListModel implements AppListModel {
    public IBackgroundInstallControlService backgroundInstallService;
    public final Context context;

    public BackgroundInstalledAppsWithGroupingListModel(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.backgroundInstallService =
                IBackgroundInstallControlService.Stub.asInterface(
                        ServiceManager.getService("background_install_control"));
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final void AppItem(
            final AppListItemModel appListItemModel, Composer composer, final int i) {
        Intrinsics.checkNotNullParameter(appListItemModel, "<this>");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1193908406);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final Context context =
                (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        final ApplicationInfo applicationInfo =
                ((BackgroundInstalledAppListWithGroupingAppRecord) appListItemModel.record).app;
        AppInfoSettingsProvider appInfoSettingsProvider = AppInfoSettingsProvider.INSTANCE;
        AppListButtonItemKt.AppListButtonItem(
                appListItemModel,
                AppInfoSettingsProvider.navigator(applicationInfo, composerImpl),
                new Function0() { // from class:
                                  // com.android.settings.spa.app.backgroundinstall.BackgroundInstalledAppsWithGroupingListModel$AppItem$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        Context context2 = context;
                        String packageName = applicationInfo.packageName;
                        Intrinsics.checkNotNullExpressionValue(packageName, "packageName");
                        AppUtilKt.startUninstallActivity(
                                context2,
                                packageName,
                                ApplicationInfosKt.getUserHandle(applicationInfo),
                                false);
                        return Unit.INSTANCE;
                    }
                },
                DeleteKt.getDelete(),
                StringResources_androidKt.stringResource(
                        composerImpl, R.string.background_install_uninstall_button_description),
                composerImpl,
                8);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.backgroundinstall.BackgroundInstalledAppsWithGroupingListModel$AppItem$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            BackgroundInstalledAppsWithGroupingListModel.this.AppItem(
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
            ReadonlySharedFlow recordListFlow) {
        Intrinsics.checkNotNullParameter(recordListFlow, "recordListFlow");
        if (this.backgroundInstallService != null) {
            return FlowKt.flowCombine(
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2,
                    recordListFlow,
                    new BackgroundInstalledAppsWithGroupingListModel$filter$1(this, null));
        }
        Log.e(
                "AppListModel<BackgroundInstalledAppListWithGroupingAppRecord>",
                "Failed to retrieve Background Install Control Service");
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1(new List[0]);
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final Comparator getComparator(int i) {
        return new BackgroundInstalledAppsWithGroupingListModel$getComparator$$inlined$compareByDescending$1();
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final String getGroupTitle(AppRecord appRecord) {
        BackgroundInstalledAppListWithGroupingAppRecord record =
                (BackgroundInstalledAppListWithGroupingAppRecord) appRecord;
        Intrinsics.checkNotNullParameter(record, "record");
        String property = DeviceConfig.getProperty("settings_ui", "key_grouping_by_month");
        int i = 6;
        if (property != null) {
            try {
                if (!StringsKt__StringsJVMKt.isBlank(property)) {
                    i = Integer.parseInt(property);
                }
            } catch (Exception e) {
                BackgroundInstalledAppsPageProvider backgroundInstalledAppsPageProvider =
                        BackgroundInstalledAppsPageProvider.INSTANCE;
                Log.d(
                        "BackgroundInstalledAppsPage",
                        "Error parsing list grouping value: "
                                + e.getMessage()
                                + " falling back to default value: 6");
            }
        }
        return record.dateOfInstall > System.currentTimeMillis() - (((long) i) * 2629800000L)
                ? MessageFormatsKt.formatString(
                        this.context,
                        R.string.background_install_before,
                        new Pair("count", Integer.valueOf(i)))
                : MessageFormatsKt.formatString(
                        this.context,
                        R.string.background_install_after,
                        new Pair("count", Integer.valueOf(i)));
    }

    public final void setBackgroundInstallControlService(IBackgroundInstallControlService bic) {
        Intrinsics.checkNotNullParameter(bic, "bic");
        this.backgroundInstallService = bic;
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
                new BackgroundInstalledAppsWithGroupingListModel$transform$1(this, null));
    }
}
