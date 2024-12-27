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
import androidx.compose.runtime.livedata.LiveDataAdapterKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.lifecycle.CoroutineLiveData;
import androidx.lifecycle.CoroutineLiveDataKt;
import androidx.lifecycle.compose.FlowExtKt;

import com.android.settings.R;
import com.android.settingslib.spa.widget.preference.PreferenceKt;
import com.android.settingslib.spa.widget.preference.PreferenceModel;
import com.android.settingslib.spaprivileged.framework.common.ContextsKt;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KFunction;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class DefaultAppShortcutPreferenceKt {
    public static final void DefaultAppShortcutPreference(
            final DefaultAppShortcut shortcut,
            final ApplicationInfo app,
            Composer composer,
            final int i) {
        Intrinsics.checkNotNullParameter(shortcut, "shortcut");
        Intrinsics.checkNotNullParameter(app, "app");
        final ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-906530873);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Context context =
                (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        composerImpl.startReplaceGroup(-655932674);
        String str = shortcut.roleName;
        boolean changed = composerImpl.changed(str) | composerImpl.changed(app);
        Object rememberedValue = composerImpl.rememberedValue();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (changed || rememberedValue == composer$Companion$Empty$1) {
            rememberedValue = new DefaultAppShortcutPresenter(context, str, app);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        final DefaultAppShortcutPresenter defaultAppShortcutPresenter =
                (DefaultAppShortcutPresenter) rememberedValue;
        composerImpl.end(false);
        composerImpl.startReplaceGroup(-655932556);
        boolean changed2 = composerImpl.changed(defaultAppShortcutPresenter);
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (changed2 || rememberedValue2 == composer$Companion$Empty$1) {
            rememberedValue2 =
                    Boolean.valueOf(
                            ContextsKt.getUserManager(defaultAppShortcutPresenter.context)
                                    .isManagedProfile(
                                            ApplicationInfosKt.getUserId(
                                                    defaultAppShortcutPresenter.app)));
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        boolean booleanValue = ((Boolean) rememberedValue2).booleanValue();
        composerImpl.end(false);
        if (booleanValue) {
            RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
            if (endRestartGroup != null) {
                endRestartGroup.block =
                        new Function2() { // from class:
                                          // com.android.settings.spa.app.appinfo.DefaultAppShortcutPreferenceKt$DefaultAppShortcutPreference$2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                DefaultAppShortcutPreferenceKt.DefaultAppShortcutPreference(
                                        DefaultAppShortcut.this,
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
        defaultAppShortcutPresenter.getClass();
        CoroutineLiveData liveData$default =
                CoroutineLiveDataKt.liveData$default(
                        null,
                        new DefaultAppShortcutPresenter$isVisible$1(
                                defaultAppShortcutPresenter, null),
                        3);
        composerImpl.startReplaceableGroup(-2027206144);
        MutableState observeAsState =
                LiveDataAdapterKt.observeAsState(
                        liveData$default, liveData$default.getValue(), composerImpl);
        composerImpl.end(false);
        if (!Intrinsics.areEqual(observeAsState.getValue(), Boolean.TRUE)) {
            RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
            if (endRestartGroup2 != null) {
                endRestartGroup2.block =
                        new Function2() { // from class:
                                          // com.android.settings.spa.app.appinfo.DefaultAppShortcutPreferenceKt$DefaultAppShortcutPreference$3
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                DefaultAppShortcutPreferenceKt.DefaultAppShortcutPreference(
                                        DefaultAppShortcut.this,
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
                        defaultAppShortcutPresenter.summaryFlow,
                        StringResources_androidKt.stringResource(
                                composerImpl, R.string.summary_placeholder),
                        composerImpl,
                        8);
        PreferenceKt.Preference(
                new PreferenceModel(
                        shortcut,
                        composerImpl,
                        defaultAppShortcutPresenter,
                        collectAsStateWithLifecycle) { // from class:
                                                       // com.android.settings.spa.app.appinfo.DefaultAppShortcutPreferenceKt$DefaultAppShortcutPreference$4
                    public final KFunction onClick;
                    public final Function0 summary;
                    public final String title;

                    {
                        this.title =
                                StringResources_androidKt.stringResource(
                                        composerImpl, shortcut.titleResId);
                        this.summary =
                                new Function0() { // from class:
                                                  // com.android.settings.spa.app.appinfo.DefaultAppShortcutPreferenceKt$DefaultAppShortcutPreference$4$summary$1
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
                                new DefaultAppShortcutPreferenceKt$DefaultAppShortcutPreference$4$onClick$1(
                                        0,
                                        defaultAppShortcutPresenter,
                                        DefaultAppShortcutPresenter.class,
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
        RecomposeScopeImpl endRestartGroup3 = composerImpl.endRestartGroup();
        if (endRestartGroup3 != null) {
            endRestartGroup3.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.appinfo.DefaultAppShortcutPreferenceKt$DefaultAppShortcutPreference$5
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            DefaultAppShortcutPreferenceKt.DefaultAppShortcutPreference(
                                    DefaultAppShortcut.this,
                                    app,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
