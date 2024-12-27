package com.android.settings.spa.app.appinfo;

import android.app.AppOpsManager;
import android.app.ecm.EnhancedConfirmationManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.widget.Toast;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.StructuralEqualityPolicy;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.saveable.RememberSaveableKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;

import com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags;
import com.android.settings.R;
import com.android.settings.applications.appinfo.AppInfoDashboardFragment;
import com.android.settingslib.spa.widget.scaffold.MoreOptionsKt;
import com.android.settingslib.spa.widget.scaffold.MoreOptionsScope;
import com.android.settingslib.spaprivileged.framework.common.ContextsKt;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;
import com.android.settingslib.spaprivileged.model.app.IPackageManagers;
import com.android.settingslib.spaprivileged.model.app.PackageManagers;
import com.android.settingslib.spaprivileged.model.enterprise.Restrictions;
import com.android.settingslib.spaprivileged.template.scaffold.RestrictedMenuItemKt;

import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppInfoSettingsMoreOptionsKt {
    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.settings.spa.app.appinfo.AppInfoSettingsMoreOptionsKt$AppInfoSettingsMoreOptions$2, kotlin.jvm.internal.Lambda] */
    public static final void AppInfoSettingsMoreOptions(
            final PackageInfoPresenter packageInfoPresenter,
            final ApplicationInfo app,
            IPackageManagers iPackageManagers,
            Composer composer,
            final int i,
            final int i2) {
        Intrinsics.checkNotNullParameter(packageInfoPresenter, "packageInfoPresenter");
        Intrinsics.checkNotNullParameter(app, "app");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(853226492);
        if ((i2 & 4) != 0) {
            iPackageManagers = PackageManagers.INSTANCE;
        }
        OpaqueKey opaqueKey = ComposerKt.invocation;
        composerImpl.startReplaceGroup(-1616849895);
        MutableState produceState =
                SnapshotStateKt.produceState(
                        null,
                        app,
                        new AppInfoSettingsMoreOptionsKt$produceState$1(
                                (Context)
                                        composerImpl.consume(
                                                AndroidCompositionLocals_androidKt.LocalContext),
                                app,
                                iPackageManagers,
                                null),
                        composerImpl,
                        582);
        composerImpl.end(false);
        final AppInfoSettingsMoreOptionsState appInfoSettingsMoreOptionsState =
                (AppInfoSettingsMoreOptionsState) produceState.getValue();
        if (appInfoSettingsMoreOptionsState == null) {
            RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
            if (endRestartGroup != null) {
                final IPackageManagers iPackageManagers2 = iPackageManagers;
                endRestartGroup.block =
                        new Function2() { // from class:
                                          // com.android.settings.spa.app.appinfo.AppInfoSettingsMoreOptionsKt$AppInfoSettingsMoreOptions$state$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                AppInfoSettingsMoreOptionsKt.AppInfoSettingsMoreOptions(
                                        PackageInfoPresenter.this,
                                        app,
                                        iPackageManagers2,
                                        (Composer) obj,
                                        RecomposeScopeImplKt.updateChangedFlags(i | 1),
                                        i2);
                                return Unit.INSTANCE;
                            }
                        };
                return;
            }
            return;
        }
        final MutableState mutableState =
                (MutableState)
                        RememberSaveableKt.rememberSaveable(
                                new Object[0],
                                null,
                                new Function0() { // from class:
                                                  // com.android.settings.spa.app.appinfo.AppInfoSettingsMoreOptionsKt$AppInfoSettingsMoreOptions$restrictedSettingsAllowed$2
                                    @Override // kotlin.jvm.functions.Function0
                                    /* renamed from: invoke */
                                    public final Object mo1068invoke() {
                                        return SnapshotStateKt.mutableStateOf(
                                                Boolean.FALSE, StructuralEqualityPolicy.INSTANCE);
                                    }
                                },
                                composerImpl,
                                3080,
                                6);
        if (!appInfoSettingsMoreOptionsState.shownUninstallUpdates
                && !appInfoSettingsMoreOptionsState.shownUninstallForAllUsers
                && (!appInfoSettingsMoreOptionsState.shouldShowAccessRestrictedSettings
                        || ((Boolean) mutableState.getValue()).booleanValue())) {
            RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
            if (endRestartGroup2 != null) {
                final IPackageManagers iPackageManagers3 = iPackageManagers;
                endRestartGroup2.block =
                        new Function2() { // from class:
                                          // com.android.settings.spa.app.appinfo.AppInfoSettingsMoreOptionsKt$AppInfoSettingsMoreOptions$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                AppInfoSettingsMoreOptionsKt.AppInfoSettingsMoreOptions(
                                        PackageInfoPresenter.this,
                                        app,
                                        iPackageManagers3,
                                        (Composer) obj,
                                        RecomposeScopeImplKt.updateChangedFlags(i | 1),
                                        i2);
                                return Unit.INSTANCE;
                            }
                        };
                return;
            }
            return;
        }
        MoreOptionsKt.MoreOptionsAction(
                ComposableLambdaKt.rememberComposableLambda(
                        -1674632137,
                        new Function3() { // from class:
                            // com.android.settings.spa.app.appinfo.AppInfoSettingsMoreOptionsKt$AppInfoSettingsMoreOptions$2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(3);
                            }

                            @Override // kotlin.jvm.functions.Function3
                            public final Object invoke(Object obj, Object obj2, Object obj3) {
                                MoreOptionsScope MoreOptionsAction = (MoreOptionsScope) obj;
                                Composer composer2 = (Composer) obj2;
                                int intValue = ((Number) obj3).intValue();
                                Intrinsics.checkNotNullParameter(
                                        MoreOptionsAction, "$this$MoreOptionsAction");
                                if ((intValue & 14) == 0) {
                                    intValue |=
                                            ((ComposerImpl) composer2).changed(MoreOptionsAction)
                                                    ? 4
                                                    : 2;
                                }
                                if ((intValue & 91) == 18) {
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    if (composerImpl2.getSkipping()) {
                                        composerImpl2.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                Restrictions restrictions =
                                        new Restrictions(
                                                ApplicationInfosKt.getUserId(app),
                                                CollectionsKt__CollectionsJVMKt.listOf(
                                                        "no_control_apps"),
                                                4);
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                composerImpl3.startReplaceGroup(431865682);
                                if (appInfoSettingsMoreOptionsState.shownUninstallUpdates) {
                                    String stringResource =
                                            StringResources_androidKt.stringResource(
                                                    composerImpl3, R.string.app_factory_reset);
                                    final PackageInfoPresenter packageInfoPresenter2 =
                                            packageInfoPresenter;
                                    RestrictedMenuItemKt.RestrictedMenuItem(
                                            MoreOptionsAction,
                                            stringResource,
                                            false,
                                            restrictions,
                                            new Function0() { // from class:
                                                // com.android.settings.spa.app.appinfo.AppInfoSettingsMoreOptionsKt$AppInfoSettingsMoreOptions$2.1
                                                {
                                                    super(0);
                                                }

                                                @Override // kotlin.jvm.functions.Function0
                                                /* renamed from: invoke */
                                                public final Object mo1068invoke() {
                                                    PackageInfoPresenter.this
                                                            .startUninstallActivity(false);
                                                    return Unit.INSTANCE;
                                                }
                                            },
                                            composerImpl3,
                                            (intValue & 14) | 4096,
                                            2);
                                }
                                composerImpl3.end(false);
                                composerImpl3.startReplaceGroup(431865990);
                                if (appInfoSettingsMoreOptionsState.shownUninstallForAllUsers) {
                                    String stringResource2 =
                                            StringResources_androidKt.stringResource(
                                                    composerImpl3,
                                                    R.string.uninstall_all_users_text);
                                    final PackageInfoPresenter packageInfoPresenter3 =
                                            packageInfoPresenter;
                                    RestrictedMenuItemKt.RestrictedMenuItem(
                                            MoreOptionsAction,
                                            stringResource2,
                                            false,
                                            restrictions,
                                            new Function0() { // from class:
                                                // com.android.settings.spa.app.appinfo.AppInfoSettingsMoreOptionsKt$AppInfoSettingsMoreOptions$2.2
                                                {
                                                    super(0);
                                                }

                                                @Override // kotlin.jvm.functions.Function0
                                                /* renamed from: invoke */
                                                public final Object mo1068invoke() {
                                                    PackageInfoPresenter.this
                                                            .startUninstallActivity(true);
                                                    return Unit.INSTANCE;
                                                }
                                            },
                                            composerImpl3,
                                            (intValue & 14) | 4096,
                                            2);
                                }
                                composerImpl3.end(false);
                                if (appInfoSettingsMoreOptionsState
                                                .shouldShowAccessRestrictedSettings
                                        && !((Boolean) mutableState.getValue()).booleanValue()) {
                                    String stringResource3 =
                                            StringResources_androidKt.stringResource(
                                                    composerImpl3,
                                                    R.string
                                                            .app_restricted_settings_lockscreen_title);
                                    final ApplicationInfo applicationInfo = app;
                                    final PackageInfoPresenter packageInfoPresenter4 =
                                            packageInfoPresenter;
                                    final MutableState mutableState2 = mutableState;
                                    MoreOptionsAction.MenuItem(
                                            stringResource3,
                                            false,
                                            new Function0() { // from class:
                                                // com.android.settings.spa.app.appinfo.AppInfoSettingsMoreOptionsKt$AppInfoSettingsMoreOptions$2.3
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(0);
                                                }

                                                @Override // kotlin.jvm.functions.Function0
                                                /* renamed from: invoke */
                                                public final Object mo1068invoke() {
                                                    final ApplicationInfo applicationInfo2 =
                                                            applicationInfo;
                                                    final Context context =
                                                            packageInfoPresenter4.context;
                                                    final MutableState mutableState3 =
                                                            mutableState2;
                                                    final Function0 function0 =
                                                            new Function0() { // from class:
                                                                // com.android.settings.spa.app.appinfo.AppInfoSettingsMoreOptionsKt.AppInfoSettingsMoreOptions.2.3.1
                                                                {
                                                                    super(0);
                                                                }

                                                                @Override // kotlin.jvm.functions.Function0
                                                                /* renamed from: invoke */
                                                                public final Object mo1068invoke() {
                                                                    MutableState.this.setValue(
                                                                            Boolean.TRUE);
                                                                    return Unit.INSTANCE;
                                                                }
                                                            };
                                                    AppInfoDashboardFragment.showLockScreen(
                                                            context,
                                                            new Runnable() { // from class:
                                                                // com.android.settings.spa.app.appinfo.AppInfoSettingsMoreOptionsKt$allowRestrictedSettings$1
                                                                @Override // java.lang.Runnable
                                                                public final void run() {
                                                                    if (Flags
                                                                                    .enhancedConfirmationModeApisEnabled()
                                                                            && android.security
                                                                                    .Flags
                                                                                    .extendEcmToAllSettings()) {
                                                                        Object systemService =
                                                                                context
                                                                                        .getSystemService(
                                                                                                (Class<
                                                                                                                Object>)
                                                                                                        EnhancedConfirmationManager
                                                                                                                .class);
                                                                        Intrinsics.checkNotNull(
                                                                                systemService);
                                                                        ((EnhancedConfirmationManager)
                                                                                        systemService)
                                                                                .clearRestriction(
                                                                                        applicationInfo2
                                                                                                .packageName);
                                                                    } else {
                                                                        AppOpsManager
                                                                                appOpsManager =
                                                                                        ContextsKt
                                                                                                .getAppOpsManager(
                                                                                                        context);
                                                                        ApplicationInfo
                                                                                applicationInfo3 =
                                                                                        applicationInfo2;
                                                                        appOpsManager.setMode(
                                                                                119,
                                                                                applicationInfo3
                                                                                        .uid,
                                                                                applicationInfo3
                                                                                        .packageName,
                                                                                0);
                                                                    }
                                                                    function0.mo1068invoke();
                                                                    Context context2 = context;
                                                                    String string =
                                                                            context2.getString(
                                                                                    R.string
                                                                                            .toast_allows_restricted_settings_successfully,
                                                                                    applicationInfo2
                                                                                            .loadLabel(
                                                                                                    context2
                                                                                                            .getPackageManager()));
                                                                    Intrinsics
                                                                            .checkNotNullExpressionValue(
                                                                                    string,
                                                                                    "getString(...)");
                                                                    Toast.makeText(
                                                                                    context, string,
                                                                                    1)
                                                                            .show();
                                                                }
                                                            });
                                                    return Unit.INSTANCE;
                                                }
                                            },
                                            composerImpl3,
                                            (intValue << 9) & 7168,
                                            2);
                                }
                                return Unit.INSTANCE;
                            }
                        },
                        composerImpl),
                composerImpl,
                6);
        RecomposeScopeImpl endRestartGroup3 = composerImpl.endRestartGroup();
        if (endRestartGroup3 != null) {
            final IPackageManagers iPackageManagers4 = iPackageManagers;
            endRestartGroup3.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.appinfo.AppInfoSettingsMoreOptionsKt$AppInfoSettingsMoreOptions$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppInfoSettingsMoreOptionsKt.AppInfoSettingsMoreOptions(
                                    PackageInfoPresenter.this,
                                    app,
                                    iPackageManagers4,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1),
                                    i2);
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
