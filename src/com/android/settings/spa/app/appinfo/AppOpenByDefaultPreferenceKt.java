package com.android.settings.spa.app.appinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
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
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.spa.widget.preference.PreferenceKt;
import com.android.settingslib.spa.widget.preference.PreferenceModel;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KFunction;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppOpenByDefaultPreferenceKt {
    public static final void AppOpenByDefaultPreference(
            final ApplicationInfo app, Composer composer, final int i) {
        boolean z;
        Intrinsics.checkNotNullParameter(app, "app");
        final ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1307491999);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Context context =
                (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        composerImpl.startReplaceGroup(-1460619732);
        boolean changed = composerImpl.changed(app);
        Object rememberedValue = composerImpl.rememberedValue();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (changed || rememberedValue == composer$Companion$Empty$1) {
            rememberedValue = new AppOpenByDefaultPresenter(context, app);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        final AppOpenByDefaultPresenter appOpenByDefaultPresenter =
                (AppOpenByDefaultPresenter) rememberedValue;
        composerImpl.end(false);
        composerImpl.startReplaceGroup(-1460619666);
        boolean changed2 = composerImpl.changed(appOpenByDefaultPresenter);
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (changed2 || rememberedValue2 == composer$Companion$Empty$1) {
            if (!appOpenByDefaultPresenter.app.isInstantApp()) {
                Context context2 = appOpenByDefaultPresenter.context;
                ApplicationInfo applicationInfo = appOpenByDefaultPresenter.app;
                if (!AppUtils.isBrowserApp(
                        context2,
                        ApplicationInfosKt.getUserId(applicationInfo),
                        applicationInfo.packageName)) {
                    z = true;
                    rememberedValue2 = Boolean.valueOf(!z);
                    composerImpl.updateRememberedValue(rememberedValue2);
                }
            }
            z = false;
            rememberedValue2 = Boolean.valueOf(!z);
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        boolean booleanValue = ((Boolean) rememberedValue2).booleanValue();
        composerImpl.end(false);
        if (booleanValue) {
            RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
            if (endRestartGroup != null) {
                endRestartGroup.block =
                        new Function2() { // from class:
                                          // com.android.settings.spa.app.appinfo.AppOpenByDefaultPreferenceKt$AppOpenByDefaultPreference$2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                AppOpenByDefaultPreferenceKt.AppOpenByDefaultPreference(
                                        app,
                                        (Composer) obj,
                                        RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                return Unit.INSTANCE;
                            }
                        };
                return;
            }
            return;
        }
        final MutableState collectAsStateWithLifecycle =
                FlowExtKt.collectAsStateWithLifecycle(
                        appOpenByDefaultPresenter.summaryFlow,
                        StringResources_androidKt.stringResource(
                                composerImpl, R.string.summary_placeholder),
                        composerImpl,
                        8);
        PreferenceKt.Preference(
                new PreferenceModel(
                        composerImpl,
                        appOpenByDefaultPresenter,
                        collectAsStateWithLifecycle) { // from class:
                                                       // com.android.settings.spa.app.appinfo.AppOpenByDefaultPreferenceKt$AppOpenByDefaultPreference$3
                    public final Function0 enabled;
                    public final KFunction onClick;
                    public final Function0 summary;
                    public final String title;

                    {
                        this.title =
                                StringResources_androidKt.stringResource(
                                        composerImpl, R.string.launch_by_default);
                        this.summary =
                                new Function0() { // from class:
                                                  // com.android.settings.spa.app.appinfo.AppOpenByDefaultPreferenceKt$AppOpenByDefaultPreference$3$summary$1
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
                                                  // com.android.settings.spa.app.appinfo.AppOpenByDefaultPreferenceKt$AppOpenByDefaultPreference$3$enabled$1
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    /* renamed from: invoke */
                                    public final Object mo1068invoke() {
                                        AppOpenByDefaultPresenter appOpenByDefaultPresenter2 =
                                                AppOpenByDefaultPresenter.this;
                                        return Boolean.valueOf(
                                                ApplicationInfosKt.hasFlag(
                                                                8388608,
                                                                appOpenByDefaultPresenter2.app)
                                                        && appOpenByDefaultPresenter2.app.enabled);
                                    }
                                };
                        this.onClick =
                                new AppOpenByDefaultPreferenceKt$AppOpenByDefaultPreference$3$onClick$1(
                                        0,
                                        appOpenByDefaultPresenter,
                                        AppOpenByDefaultPresenter.class,
                                        "startActivity",
                                        "startActivity()V",
                                        0);
                    }

                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final Function0 getEnabled() {
                        return this.enabled;
                    }

                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final Function0 getOnClick() {
                        return (Function0) this.onClick;
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
                                      // com.android.settings.spa.app.appinfo.AppOpenByDefaultPreferenceKt$AppOpenByDefaultPreference$4
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppOpenByDefaultPreferenceKt.AppOpenByDefaultPreference(
                                    app,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
