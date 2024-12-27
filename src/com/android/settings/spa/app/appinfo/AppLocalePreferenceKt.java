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
import com.android.settingslib.spa.widget.preference.PreferenceKt;
import com.android.settingslib.spa.widget.preference.PreferenceModel;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KFunction;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppLocalePreferenceKt {
    public static final void AppLocalePreference(
            final ApplicationInfo app, Composer composer, final int i) {
        Intrinsics.checkNotNullParameter(app, "app");
        final ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(425505277);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Context context =
                (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        composerImpl.startReplaceGroup(-1124959665);
        Object rememberedValue = composerImpl.rememberedValue();
        if (rememberedValue == Composer.Companion.Empty) {
            rememberedValue = new AppLocalePresenter(context, app);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        final AppLocalePresenter appLocalePresenter = (AppLocalePresenter) rememberedValue;
        composerImpl.end(false);
        if (!((Boolean)
                        FlowExtKt.collectAsStateWithLifecycle(
                                        appLocalePresenter.isAvailableFlow,
                                        Boolean.FALSE,
                                        composerImpl,
                                        56)
                                .getValue())
                .booleanValue()) {
            RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
            if (endRestartGroup != null) {
                endRestartGroup.block =
                        new Function2() { // from class:
                                          // com.android.settings.spa.app.appinfo.AppLocalePreferenceKt$AppLocalePreference$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                AppLocalePreferenceKt.AppLocalePreference(
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
                        appLocalePresenter.summaryFlow,
                        StringResources_androidKt.stringResource(
                                composerImpl, R.string.summary_placeholder),
                        composerImpl,
                        8);
        PreferenceKt.Preference(
                new PreferenceModel(
                        composerImpl,
                        appLocalePresenter,
                        collectAsStateWithLifecycle) { // from class:
                                                       // com.android.settings.spa.app.appinfo.AppLocalePreferenceKt$AppLocalePreference$2
                    public final KFunction onClick;
                    public final Function0 summary;
                    public final String title;

                    {
                        this.title =
                                StringResources_androidKt.stringResource(
                                        composerImpl, R.string.app_locale_preference_title);
                        this.summary =
                                new Function0() { // from class:
                                                  // com.android.settings.spa.app.appinfo.AppLocalePreferenceKt$AppLocalePreference$2$summary$1
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
                        this.onClick =
                                new AppLocalePreferenceKt$AppLocalePreference$2$onClick$1(
                                        0,
                                        appLocalePresenter,
                                        AppLocalePresenter.class,
                                        "startActivity",
                                        "startActivity()V",
                                        0);
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
                                      // com.android.settings.spa.app.appinfo.AppLocalePreferenceKt$AppLocalePreference$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppLocalePreferenceKt.AppLocalePreference(
                                    app,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
