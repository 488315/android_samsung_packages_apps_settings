package com.android.settings.spa.app.backgroundinstall;

import android.content.Context;
import android.content.pm.IBackgroundInstallControlService;
import android.os.Bundle;
import android.os.ServiceManager;
import android.provider.DeviceConfig;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;

import com.android.settings.R;
import com.android.settingslib.spa.framework.common.SettingsEntryBuilder;
import com.android.settingslib.spa.framework.common.SettingsPage;
import com.android.settingslib.spa.framework.common.SettingsPageProvider;
import com.android.settingslib.spa.framework.common.SettingsPageProviderKt;
import com.android.settingslib.spa.framework.compose.NavControllerWrapperKt;
import com.android.settingslib.spa.widget.preference.PreferenceKt;
import com.android.settingslib.spa.widget.preference.PreferenceModel;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BackgroundInstalledAppsPageProvider implements SettingsPageProvider {
    public static final BackgroundInstalledAppsPageProvider INSTANCE;
    public static IBackgroundInstallControlService backgroundInstallService;
    public static boolean featureIsDisabled;
    public static final SettingsPage owner;

    static {
        BackgroundInstalledAppsPageProvider backgroundInstalledAppsPageProvider =
                new BackgroundInstalledAppsPageProvider();
        INSTANCE = backgroundInstalledAppsPageProvider;
        owner =
                SettingsPageProviderKt.createSettingsPage(
                        backgroundInstalledAppsPageProvider, null);
        backgroundInstallService =
                IBackgroundInstallControlService.Stub.asInterface(
                        ServiceManager.getService("background_install_control"));
        featureIsDisabled = !DeviceConfig.getBoolean("settings_ui", "key_bic_ui_enabled", false);
    }

    public final void EntryItem(Composer composer, final int i) {
        final ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1476886924);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        if (featureIsDisabled) {
            RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
            if (endRestartGroup != null) {
                endRestartGroup.block =
                        new Function2() { // from class:
                                          // com.android.settings.spa.app.backgroundinstall.BackgroundInstalledAppsPageProvider$EntryItem$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                BackgroundInstalledAppsPageProvider.this.EntryItem(
                                        (Composer) obj,
                                        RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                return Unit.INSTANCE;
                            }
                        };
                return;
            }
            return;
        }
        composerImpl.startReplaceGroup(-1348813024);
        final MutableState produceState =
                SnapshotStateKt.produceState(
                        composerImpl,
                        StringResources_androidKt.stringResource(
                                composerImpl, R.string.summary_placeholder),
                        new BackgroundInstalledAppsPageProvider$generatePreferenceSummary$1(
                                (Context)
                                        composerImpl.consume(
                                                AndroidCompositionLocals_androidKt.LocalContext),
                                null));
        composerImpl.end(false);
        PreferenceKt.Preference(
                new PreferenceModel(
                        composerImpl,
                        produceState) { // from class:
                                        // com.android.settings.spa.app.backgroundinstall.BackgroundInstalledAppsPageProvider$EntryItem$2
                    public final Lambda onClick;
                    public final Function0 summary;
                    public final String title;

                    {
                        this.title =
                                StringResources_androidKt.stringResource(
                                        composerImpl, R.string.background_install_title);
                        this.summary =
                                new Function0() { // from class:
                                                  // com.android.settings.spa.app.backgroundinstall.BackgroundInstalledAppsPageProvider$EntryItem$2$summary$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    /* renamed from: invoke */
                                    public final Object mo1068invoke() {
                                        State state = produceState;
                                        BackgroundInstalledAppsPageProvider
                                                backgroundInstalledAppsPageProvider =
                                                        BackgroundInstalledAppsPageProvider
                                                                .INSTANCE;
                                        return (String) state.getValue();
                                    }
                                };
                        BackgroundInstalledAppsPageProvider backgroundInstalledAppsPageProvider =
                                BackgroundInstalledAppsPageProvider.INSTANCE;
                        this.onClick =
                                (Lambda)
                                        NavControllerWrapperKt.navigator(
                                                composerImpl, "BackgroundInstalledAppsPage");
                    }

                    /* JADX WARN: Type inference failed for: r0v1, types: [kotlin.jvm.functions.Function0, kotlin.jvm.internal.Lambda] */
                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final Function0 getOnClick() {
                        return this.onClick;
                    }

                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final Function0 getSummary() {
                        return this.summary;
                    }

                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final String getTitle() {
                        return this.title;
                    }
                },
                false,
                composerImpl,
                0,
                2);
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            endRestartGroup2.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.backgroundinstall.BackgroundInstalledAppsPageProvider$EntryItem$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            BackgroundInstalledAppsPageProvider.this.EntryItem(
                                    (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final void Page(final Bundle bundle, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1903967448);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        if (featureIsDisabled) {
            RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
            if (endRestartGroup != null) {
                endRestartGroup.block =
                        new Function2() { // from class:
                                          // com.android.settings.spa.app.backgroundinstall.BackgroundInstalledAppsPageProvider$Page$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                BackgroundInstalledAppsPageProvider.this.Page(
                                        bundle,
                                        (Composer) obj,
                                        RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                return Unit.INSTANCE;
                            }
                        };
                return;
            }
            return;
        }
        BackgroundInstalledAppsPageProviderKt.BackgroundInstalledAppList(null, composerImpl, 0, 1);
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            endRestartGroup2.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.backgroundinstall.BackgroundInstalledAppsPageProvider$Page$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            BackgroundInstalledAppsPageProvider.this.Page(
                                    bundle,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public final SettingsEntryBuilder buildInjectEntry() {
        SettingsEntryBuilder createInject$default =
                SettingsEntryBuilder.Companion.createInject$default(owner);
        BackgroundInstalledAppsPageProvider$buildInjectEntry$1 fn =
                new Function1() { // from class:
                                  // com.android.settings.spa.app.backgroundinstall.BackgroundInstalledAppsPageProvider$buildInjectEntry$1
                    @Override // kotlin.jvm.functions.Function1
                    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                        return null;
                    }
                };
        Intrinsics.checkNotNullParameter(fn, "fn");
        createInject$default.searchDataFn = fn;
        createInject$default.isAllowSearch = true;
        createInject$default.setUiLayoutFn(
                ComposableSingletons$BackgroundInstalledAppsPageProviderKt.f47lambda1);
        return createInject$default;
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final String getName() {
        return "BackgroundInstalledAppsPage";
    }

    public final BackgroundInstalledAppsPageProvider setBackgroundInstallControlService(
            IBackgroundInstallControlService bic) {
        Intrinsics.checkNotNullParameter(bic, "bic");
        backgroundInstallService = bic;
        return this;
    }

    public final BackgroundInstalledAppsPageProvider setDisableFeature(boolean z) {
        featureIsDisabled = z;
        return this;
    }
}
