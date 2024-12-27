package com.android.settings.spa.app.appinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.net.NetworkTemplate;

import androidx.activity.compose.PredictiveBackHandlerKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionScopedCoroutineScopeCanceller;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.lifecycle.compose.FlowExtKt;

import com.android.settings.R;
import com.android.settings.datausage.lib.AppDataUsageSummaryRepository;
import com.android.settings.datausage.lib.INetworkTemplates;
import com.android.settings.datausage.lib.NetworkTemplates;
import com.android.settingslib.spa.widget.preference.PreferenceKt;
import com.android.settingslib.spa.widget.preference.PreferenceModel;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;

import kotlin.Unit;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KFunction;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppDataUsagePreferenceKt {
    public static final void AppDataUsagePreference(
            final ApplicationInfo app,
            INetworkTemplates iNetworkTemplates,
            Function2 function2,
            Composer composer,
            final int i,
            final int i2) {
        Intrinsics.checkNotNullParameter(app, "app");
        final ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(924725885);
        if ((i2 & 2) != 0) {
            iNetworkTemplates = NetworkTemplates.INSTANCE;
        }
        if ((i2 & 4) != 0) {
            function2 =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.appinfo.AppDataUsagePreferenceKt$AppDataUsagePreference$1
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            Context context = (Context) obj;
                            NetworkTemplate networkTemplate = (NetworkTemplate) obj2;
                            Intrinsics.checkNotNullParameter(context, "context");
                            Intrinsics.checkNotNullParameter(networkTemplate, "networkTemplate");
                            return new AppDataUsageSummaryRepository(context, networkTemplate);
                        }
                    };
        }
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Context context =
                (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        Object rememberedValue = composerImpl.rememberedValue();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (rememberedValue == composer$Companion$Empty$1) {
            rememberedValue =
                    PredictiveBackHandlerKt$$ExternalSyntheticOutline0.m(
                            EffectsKt.createCompositionCoroutineScope(
                                    EmptyCoroutineContext.INSTANCE, composerImpl),
                            composerImpl);
        }
        CoroutineScope coroutineScope =
                ((CompositionScopedCoroutineScopeCanceller) rememberedValue).coroutineScope;
        composerImpl.startReplaceGroup(-1418450800);
        boolean changed = composerImpl.changed(app);
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (changed || rememberedValue2 == composer$Companion$Empty$1) {
            AppDataUsagePresenter appDataUsagePresenter =
                    new AppDataUsagePresenter(
                            context,
                            app,
                            (ContextScope) coroutineScope,
                            iNetworkTemplates,
                            function2);
            composerImpl.updateRememberedValue(appDataUsagePresenter);
            rememberedValue2 = appDataUsagePresenter;
        }
        final AppDataUsagePresenter appDataUsagePresenter2 =
                (AppDataUsagePresenter) rememberedValue2;
        composerImpl.end(false);
        if (!((Boolean)
                        FlowExtKt.collectAsStateWithLifecycle(
                                        appDataUsagePresenter2.isAvailableFlow,
                                        Boolean.FALSE,
                                        composerImpl,
                                        56)
                                .getValue())
                .booleanValue()) {
            RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
            if (endRestartGroup != null) {
                final INetworkTemplates iNetworkTemplates2 = iNetworkTemplates;
                final Function2 function22 = function2;
                endRestartGroup.block =
                        new Function2() { // from class:
                                          // com.android.settings.spa.app.appinfo.AppDataUsagePreferenceKt$AppDataUsagePreference$2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                AppDataUsagePreferenceKt.AppDataUsagePreference(
                                        app,
                                        iNetworkTemplates2,
                                        function22,
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
        final MutableState collectAsStateWithLifecycle =
                FlowExtKt.collectAsStateWithLifecycle(
                        appDataUsagePresenter2.summaryFlow,
                        StringResources_androidKt.stringResource(
                                composerImpl, R.string.computing_size),
                        composerImpl,
                        8);
        PreferenceKt.Preference(
                new PreferenceModel(
                        appDataUsagePresenter2,
                        composerImpl,
                        collectAsStateWithLifecycle) { // from class:
                                                       // com.android.settings.spa.app.appinfo.AppDataUsagePreferenceKt$AppDataUsagePreference$3
                    public final Function0 enabled;
                    public final KFunction onClick;
                    public final Function0 summary;
                    public final String title;

                    {
                        this.title =
                                StringResources_androidKt.stringResource(
                                        composerImpl,
                                        ((Number)
                                                        FlowExtKt.collectAsStateWithLifecycle(
                                                                        appDataUsagePresenter2
                                                                                .titleResIdFlow,
                                                                        Integer.valueOf(
                                                                                R.string
                                                                                        .summary_placeholder),
                                                                        composerImpl,
                                                                        8)
                                                                .getValue())
                                                .intValue());
                        this.summary =
                                new Function0() { // from class:
                                                  // com.android.settings.spa.app.appinfo.AppDataUsagePreferenceKt$AppDataUsagePreference$3$summary$1
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
                                                  // com.android.settings.spa.app.appinfo.AppDataUsagePreferenceKt$AppDataUsagePreference$3$enabled$1
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    /* renamed from: invoke */
                                    public final Object mo1068invoke() {
                                        return Boolean.valueOf(
                                                ApplicationInfosKt.hasFlag(
                                                        8388608, AppDataUsagePresenter.this.app));
                                    }
                                };
                        this.onClick =
                                new AppDataUsagePreferenceKt$AppDataUsagePreference$3$onClick$1(
                                        0,
                                        appDataUsagePresenter2,
                                        AppDataUsagePresenter.class,
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
            final INetworkTemplates iNetworkTemplates3 = iNetworkTemplates;
            final Function2 function23 = function2;
            endRestartGroup2.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.appinfo.AppDataUsagePreferenceKt$AppDataUsagePreference$4
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppDataUsagePreferenceKt.AppDataUsagePreference(
                                    app,
                                    iNetworkTemplates3,
                                    function23,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1),
                                    i2);
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
