package com.android.settings.spa.app.appinfo;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;

import androidx.compose.foundation.layout.ColumnScope;
import androidx.compose.foundation.layout.RowScope;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.lifecycle.compose.FlowExtKt;

import com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags;
import com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlagsImpl;
import com.android.settings.R;
import com.android.settings.spa.app.appcompat.UserAspectRatioAppPreferenceKt;
import com.android.settings.spa.app.specialaccess.AlarmsAndRemindersAppListProvider;
import com.android.settings.spa.app.specialaccess.DisplayOverOtherAppsAppListProvider;
import com.android.settings.spa.app.specialaccess.InstallUnknownAppsListProvider;
import com.android.settings.spa.app.specialaccess.ModifySystemSettingsAppListProvider;
import com.android.settings.spa.app.specialaccess.PictureInPictureListProvider;
import com.android.settingslib.spa.widget.scaffold.RegularScaffoldKt;
import com.android.settingslib.spa.widget.ui.CategoryKt;
import com.android.settingslib.spaprivileged.template.app.AppInfoProvider;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppInfoSettingsKt {
    /* JADX WARN: Type inference failed for: r3v0, types: [com.android.settings.spa.app.appinfo.AppInfoSettingsKt$AppInfoSettings$1, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.settings.spa.app.appinfo.AppInfoSettingsKt$AppInfoSettings$2, kotlin.jvm.internal.Lambda] */
    public static final void access$AppInfoSettings(
            final PackageInfoPresenter packageInfoPresenter, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1260686757);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final MutableState collectAsStateWithLifecycle =
                FlowExtKt.collectAsStateWithLifecycle(packageInfoPresenter.flow, composerImpl);
        final FeatureFlagsImpl featureFlagsImpl = new FeatureFlagsImpl();
        RegularScaffoldKt.RegularScaffold(
                StringResources_androidKt.stringResource(
                        composerImpl, R.string.application_info_label),
                ComposableLambdaKt.rememberComposableLambda(
                        321472029,
                        new Function3() { // from class:
                                          // com.android.settings.spa.app.appinfo.AppInfoSettingsKt$AppInfoSettings$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(3);
                            }

                            @Override // kotlin.jvm.functions.Function3
                            public final Object invoke(Object obj, Object obj2, Object obj3) {
                                RowScope RegularScaffold = (RowScope) obj;
                                Composer composer2 = (Composer) obj2;
                                int intValue = ((Number) obj3).intValue();
                                Intrinsics.checkNotNullParameter(
                                        RegularScaffold, "$this$RegularScaffold");
                                if ((intValue & 81) == 16) {
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    if (composerImpl2.getSkipping()) {
                                        composerImpl2.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                PackageInfo packageInfo =
                                        (PackageInfo) collectAsStateWithLifecycle.getValue();
                                ApplicationInfo applicationInfo =
                                        packageInfo != null ? packageInfo.applicationInfo : null;
                                if (applicationInfo != null) {
                                    FeatureFlags featureFlags = featureFlagsImpl;
                                    PackageInfoPresenter packageInfoPresenter2 =
                                            packageInfoPresenter;
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                    composerImpl3.startReplaceGroup(-281275858);
                                    Intrinsics.checkNotNullParameter(featureFlags, "featureFlags");
                                    if (featureFlags.archiving()) {
                                        TopBarAppLaunchButtonKt.TopBarAppLaunchButton(
                                                packageInfoPresenter2,
                                                applicationInfo,
                                                composerImpl3,
                                                72);
                                    }
                                    composerImpl3.end(false);
                                    AppInfoSettingsMoreOptionsKt.AppInfoSettingsMoreOptions(
                                            packageInfoPresenter2,
                                            applicationInfo,
                                            null,
                                            composerImpl3,
                                            72,
                                            4);
                                }
                                return Unit.INSTANCE;
                            }
                        },
                        composerImpl),
                ComposableLambdaKt.rememberComposableLambda(
                        1834395126,
                        new Function2() { // from class:
                                          // com.android.settings.spa.app.appinfo.AppInfoSettingsKt$AppInfoSettings$2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.spa.app.appinfo.AppInfoSettingsKt$AppInfoSettings$2$1, kotlin.jvm.internal.Lambda] */
                            /* JADX WARN: Type inference failed for: r0v3, types: [com.android.settings.spa.app.appinfo.AppInfoSettingsKt$AppInfoSettings$2$2, kotlin.jvm.internal.Lambda] */
                            /* JADX WARN: Type inference failed for: r0v5, types: [com.android.settings.spa.app.appinfo.AppInfoSettingsKt$AppInfoSettings$2$3, kotlin.jvm.internal.Lambda] */
                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                final ApplicationInfo applicationInfo;
                                Composer composer2 = (Composer) obj;
                                if ((((Number) obj2).intValue() & 11) == 2) {
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    if (composerImpl2.getSkipping()) {
                                        composerImpl2.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                PackageInfo packageInfo =
                                        (PackageInfo) collectAsStateWithLifecycle.getValue();
                                if (packageInfo != null
                                        && (applicationInfo = packageInfo.applicationInfo)
                                                != null) {
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                    composerImpl3.startReplaceGroup(-281275492);
                                    boolean changed = composerImpl3.changed(packageInfo);
                                    Object rememberedValue = composerImpl3.rememberedValue();
                                    if (changed || rememberedValue == Composer.Companion.Empty) {
                                        rememberedValue = new AppInfoProvider(packageInfo);
                                        composerImpl3.updateRememberedValue(rememberedValue);
                                    }
                                    AppInfoProvider appInfoProvider =
                                            (AppInfoProvider) rememberedValue;
                                    composerImpl3.end(false);
                                    final StateFlowImpl MutableStateFlow =
                                            StateFlowKt.MutableStateFlow(Boolean.FALSE);
                                    appInfoProvider.AppInfo(false, false, composerImpl3, 512, 3);
                                    AppButtonsKt.AppButtons(
                                            packageInfoPresenter,
                                            MutableStateFlow,
                                            null,
                                            composerImpl3,
                                            72,
                                            4);
                                    AppSettingsPreferenceKt.AppSettingsPreference(
                                            applicationInfo, composerImpl3, 8);
                                    AppAllServicesPreferenceKt.AppAllServicesPreference(
                                            applicationInfo, composerImpl3, 8);
                                    AppNotificationPreferenceKt.AppNotificationPreference(
                                            applicationInfo, null, composerImpl3, 8, 2);
                                    AppPermissionPreferenceKt.AppPermissionPreference(
                                            applicationInfo, null, composerImpl3, 8, 2);
                                    AppStoragePreferenceKt.AppStoragePreference(
                                            applicationInfo, composerImpl3, 8);
                                    InstantAppDomainsPreferenceKt.InstantAppDomainsPreference(
                                            applicationInfo, composerImpl3, 8);
                                    AppDataUsagePreferenceKt.AppDataUsagePreference(
                                            applicationInfo, null, null, composerImpl3, 8, 6);
                                    AppTimeSpentPreferenceKt.AppTimeSpentPreference(
                                            applicationInfo, composerImpl3, 8);
                                    AppBatteryPreferenceKt.AppBatteryPreference(
                                            applicationInfo, composerImpl3, 8);
                                    AppLocalePreferenceKt.AppLocalePreference(
                                            applicationInfo, composerImpl3, 8);
                                    AppOpenByDefaultPreferenceKt.AppOpenByDefaultPreference(
                                            applicationInfo, composerImpl3, 8);
                                    DefaultAppShortcutsKt.DefaultAppShortcuts(
                                            applicationInfo, composerImpl3, 8);
                                    CategoryKt.Category(
                                            StringResources_androidKt.stringResource(
                                                    composerImpl3, R.string.unused_apps_category),
                                            ComposableLambdaKt.rememberComposableLambda(
                                                    895646802,
                                                    new Function3() { // from class:
                                                                      // com.android.settings.spa.app.appinfo.AppInfoSettingsKt$AppInfoSettings$2.1
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(3);
                                                        }

                                                        @Override // kotlin.jvm.functions.Function3
                                                        public final Object invoke(
                                                                Object obj3,
                                                                Object obj4,
                                                                Object obj5) {
                                                            ColumnScope Category =
                                                                    (ColumnScope) obj3;
                                                            Composer composer3 = (Composer) obj4;
                                                            int intValue =
                                                                    ((Number) obj5).intValue();
                                                            Intrinsics.checkNotNullParameter(
                                                                    Category, "$this$Category");
                                                            if ((intValue & 81) == 16) {
                                                                ComposerImpl composerImpl4 =
                                                                        (ComposerImpl) composer3;
                                                                if (composerImpl4.getSkipping()) {
                                                                    composerImpl4.skipToGroupEnd();
                                                                    return Unit.INSTANCE;
                                                                }
                                                            }
                                                            OpaqueKey opaqueKey3 =
                                                                    ComposerKt.invocation;
                                                            HibernationSwitchPreferenceKt
                                                                    .HibernationSwitchPreference(
                                                                            applicationInfo,
                                                                            MutableStateFlow,
                                                                            composer3,
                                                                            72);
                                                            return Unit.INSTANCE;
                                                        }
                                                    },
                                                    composerImpl3),
                                            composerImpl3,
                                            48);
                                    CategoryKt.Category(
                                            StringResources_androidKt.stringResource(
                                                    composerImpl3, R.string.advanced_apps),
                                            ComposableLambdaKt.rememberComposableLambda(
                                                    -774108983,
                                                    new Function3() { // from class:
                                                                      // com.android.settings.spa.app.appinfo.AppInfoSettingsKt$AppInfoSettings$2.2
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(3);
                                                        }

                                                        @Override // kotlin.jvm.functions.Function3
                                                        public final Object invoke(
                                                                Object obj3,
                                                                Object obj4,
                                                                Object obj5) {
                                                            ColumnScope Category =
                                                                    (ColumnScope) obj3;
                                                            Composer composer3 = (Composer) obj4;
                                                            int intValue =
                                                                    ((Number) obj5).intValue();
                                                            Intrinsics.checkNotNullParameter(
                                                                    Category, "$this$Category");
                                                            if ((intValue & 81) == 16) {
                                                                ComposerImpl composerImpl4 =
                                                                        (ComposerImpl) composer3;
                                                                if (composerImpl4.getSkipping()) {
                                                                    composerImpl4.skipToGroupEnd();
                                                                    return Unit.INSTANCE;
                                                                }
                                                            }
                                                            OpaqueKey opaqueKey3 =
                                                                    ComposerKt.invocation;
                                                            UserAspectRatioAppPreferenceKt
                                                                    .UserAspectRatioAppPreference(
                                                                            applicationInfo,
                                                                            composer3,
                                                                            8);
                                                            DisplayOverOtherAppsAppListProvider
                                                                    .INSTANCE
                                                                    .InfoPageEntryItem(
                                                                            applicationInfo,
                                                                            composer3,
                                                                            56);
                                                            ModifySystemSettingsAppListProvider
                                                                    .INSTANCE
                                                                    .InfoPageEntryItem(
                                                                            applicationInfo,
                                                                            composer3,
                                                                            56);
                                                            PictureInPictureListProvider.INSTANCE
                                                                    .InfoPageEntryItem(
                                                                            applicationInfo,
                                                                            composer3,
                                                                            56);
                                                            InstallUnknownAppsListProvider.INSTANCE
                                                                    .InfoPageEntryItem(
                                                                            applicationInfo,
                                                                            composer3,
                                                                            56);
                                                            InteractAcrossProfilesDetailsPreferenceKt
                                                                    .InteractAcrossProfilesDetailsPreference(
                                                                            applicationInfo,
                                                                            composer3,
                                                                            8);
                                                            AlarmsAndRemindersAppListProvider
                                                                    .INSTANCE
                                                                    .InfoPageEntryItem(
                                                                            applicationInfo,
                                                                            composer3,
                                                                            56);
                                                            return Unit.INSTANCE;
                                                        }
                                                    },
                                                    composerImpl3),
                                            composerImpl3,
                                            48);
                                    CategoryKt.Category(
                                            StringResources_androidKt.stringResource(
                                                    composerImpl3,
                                                    R.string.app_install_details_group_title),
                                            ComposableLambdaKt.rememberComposableLambda(
                                                    -72174390,
                                                    new Function3() { // from class:
                                                                      // com.android.settings.spa.app.appinfo.AppInfoSettingsKt$AppInfoSettings$2.3
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(3);
                                                        }

                                                        @Override // kotlin.jvm.functions.Function3
                                                        public final Object invoke(
                                                                Object obj3,
                                                                Object obj4,
                                                                Object obj5) {
                                                            ColumnScope Category =
                                                                    (ColumnScope) obj3;
                                                            Composer composer3 = (Composer) obj4;
                                                            int intValue =
                                                                    ((Number) obj5).intValue();
                                                            Intrinsics.checkNotNullParameter(
                                                                    Category, "$this$Category");
                                                            if ((intValue & 81) == 16) {
                                                                ComposerImpl composerImpl4 =
                                                                        (ComposerImpl) composer3;
                                                                if (composerImpl4.getSkipping()) {
                                                                    composerImpl4.skipToGroupEnd();
                                                                    return Unit.INSTANCE;
                                                                }
                                                            }
                                                            OpaqueKey opaqueKey3 =
                                                                    ComposerKt.invocation;
                                                            AppInstallerInfoPreferenceKt
                                                                    .AppInstallerInfoPreference(
                                                                            applicationInfo,
                                                                            composer3,
                                                                            8);
                                                            return Unit.INSTANCE;
                                                        }
                                                    },
                                                    composerImpl3),
                                            composerImpl3,
                                            48);
                                    appInfoProvider.FooterAppVersion(false, composerImpl3, 64, 1);
                                }
                                return Unit.INSTANCE;
                            }
                        },
                        composerImpl),
                composerImpl,
                FileType.CRT,
                0);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.appinfo.AppInfoSettingsKt$AppInfoSettings$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppInfoSettingsKt.access$AppInfoSettings(
                                    PackageInfoPresenter.this,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
