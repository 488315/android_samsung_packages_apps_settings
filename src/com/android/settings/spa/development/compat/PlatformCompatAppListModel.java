package com.android.settings.spa.development.compat;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.core.os.BundleKt;

import com.android.settings.core.SubSettingLauncher;
import com.android.settings.development.compat.PlatformCompatDashboard;
import com.android.settingslib.spaprivileged.model.app.AppListModel;
import com.android.settingslib.spaprivileged.model.app.AppRecord;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;
import com.android.settingslib.spaprivileged.template.app.AppListItemModel;
import com.android.settingslib.spaprivileged.template.app.AppListItemModelKt;

import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PlatformCompatAppListModel implements AppListModel {
    public final Context context;

    public PlatformCompatAppListModel(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final void AppItem(
            final AppListItemModel appListItemModel, Composer composer, final int i) {
        Intrinsics.checkNotNullParameter(appListItemModel, "<this>");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1937356075);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        AppListItemModelKt.AppListItem(
                appListItemModel,
                new Function0() { // from class:
                                  // com.android.settings.spa.development.compat.PlatformCompatAppListModel$AppItem$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        PlatformCompatAppListModel platformCompatAppListModel =
                                PlatformCompatAppListModel.this;
                        ApplicationInfo applicationInfo =
                                ((PlatformCompatAppRecord) appListItemModel.record).app;
                        SubSettingLauncher subSettingLauncher =
                                new SubSettingLauncher(platformCompatAppListModel.context);
                        String qualifiedName =
                                Reflection.factory
                                        .getOrCreateKotlinClass(PlatformCompatDashboard.class)
                                        .getQualifiedName();
                        SubSettingLauncher.LaunchRequest launchRequest =
                                subSettingLauncher.mLaunchRequest;
                        launchRequest.mDestinationName = qualifiedName;
                        launchRequest.mSourceMetricsCategory = 39;
                        launchRequest.mArguments =
                                BundleKt.bundleOf(
                                        new Pair("compat_app", applicationInfo.packageName));
                        launchRequest.mUserHandle =
                                ApplicationInfosKt.getUserHandle(applicationInfo);
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
                                      // com.android.settings.spa.development.compat.PlatformCompatAppListModel$AppItem$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            PlatformCompatAppListModel.this.AppItem(
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
        return new PlatformCompatAppListModel$filter$$inlined$filterItem$1(recordListFlow, 0);
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final Function0 getSummary(int i, AppRecord appRecord, Composer composer) {
        final PlatformCompatAppRecord record = (PlatformCompatAppRecord) appRecord;
        Intrinsics.checkNotNullParameter(record, "record");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-896675254);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Function0 function0 =
                new Function0() { // from class:
                                  // com.android.settings.spa.development.compat.PlatformCompatAppListModel$getSummary$1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        String packageName = PlatformCompatAppRecord.this.app.packageName;
                        Intrinsics.checkNotNullExpressionValue(packageName, "packageName");
                        return packageName;
                    }
                };
        composerImpl.end(false);
        return function0;
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final Flow transform(
            FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2,
            FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1
                    flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1) {
        return new PlatformCompatAppListModel$filter$$inlined$filterItem$1(
                flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1, 1);
    }
}
