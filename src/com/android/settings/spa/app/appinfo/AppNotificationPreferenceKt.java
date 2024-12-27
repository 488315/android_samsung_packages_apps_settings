package com.android.settings.spa.app.appinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.lifecycle.compose.FlowExtKt;

import com.android.settings.R;
import com.android.settings.applications.appinfo.AppInfoDashboardFragment;
import com.android.settings.notification.app.AppNotificationSettings;
import com.android.settings.spa.notification.IAppNotificationRepository;
import com.android.settingslib.spa.framework.compose.RuntimeUtilsKt;
import com.android.settingslib.spa.widget.preference.PreferenceKt;
import com.android.settingslib.spa.widget.preference.PreferenceModel;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppNotificationPreferenceKt {
    public static final void AppNotificationPreference(
            final ApplicationInfo app,
            final IAppNotificationRepository iAppNotificationRepository,
            Composer composer,
            final int i,
            final int i2) {
        Intrinsics.checkNotNullParameter(app, "app");
        final ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1593892264);
        if ((i2 & 2) != 0) {
            iAppNotificationRepository =
                    (IAppNotificationRepository)
                            RuntimeUtilsKt.rememberContext(
                                    AppNotificationPreferenceKt$AppNotificationPreference$1
                                            .INSTANCE,
                                    composerImpl);
        }
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final Context context =
                (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        composerImpl.startReplaceGroup(-1792777196);
        boolean changed = composerImpl.changed(app);
        Object rememberedValue = composerImpl.rememberedValue();
        if (changed || rememberedValue == Composer.Companion.Empty) {
            rememberedValue =
                    FlowKt.flowOn(
                            new SafeFlow(
                                    new AppNotificationPreferenceKt$AppNotificationPreference$summary$2$1(
                                            iAppNotificationRepository, app, null)),
                            Dispatchers.Default);
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
        PreferenceKt.Preference(
                new PreferenceModel(
                        composerImpl,
                        collectAsStateWithLifecycle,
                        app,
                        context) { // from class:
                                   // com.android.settings.spa.app.appinfo.AppNotificationPreferenceKt$AppNotificationPreference$2
                    public final Function0 enabled;
                    public final Function0 onClick;
                    public final Function0 summary;
                    public final String title;

                    {
                        this.title =
                                StringResources_androidKt.stringResource(
                                        composerImpl, R.string.notifications_label);
                        this.summary =
                                new Function0() { // from class:
                                                  // com.android.settings.spa.app.appinfo.AppNotificationPreferenceKt$AppNotificationPreference$2$summary$1
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
                        this.enabled =
                                new Function0() { // from class:
                                                  // com.android.settings.spa.app.appinfo.AppNotificationPreferenceKt$AppNotificationPreference$2$enabled$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    /* renamed from: invoke */
                                    public final Object mo1068invoke() {
                                        return Boolean.valueOf(
                                                ApplicationInfosKt.getInstalled(app));
                                    }
                                };
                        this.onClick =
                                new Function0() { // from class:
                                                  // com.android.settings.spa.app.appinfo.AppNotificationPreferenceKt$AppNotificationPreference$2$onClick$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    /* renamed from: invoke */
                                    public final Object mo1068invoke() {
                                        AppInfoDashboardFragment.startAppInfoFragment(
                                                AppNotificationSettings.class, app, context, 20);
                                        return Unit.INSTANCE;
                                    }
                                };
                    }

                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final Function0 getEnabled() {
                        return this.enabled;
                    }

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
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.appinfo.AppNotificationPreferenceKt$AppNotificationPreference$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppNotificationPreferenceKt.AppNotificationPreference(
                                    app,
                                    iAppNotificationRepository,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1),
                                    i2);
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
