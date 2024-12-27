package com.android.settings.spa.app.appinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;

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
public abstract class AppBatteryPreferenceKt {
    public static final void AppBatteryPreference(
            final ApplicationInfo app, Composer composer, final int i) {
        Intrinsics.checkNotNullParameter(app, "app");
        final ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-250280441);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Context context =
                (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        composerImpl.startReplaceGroup(1985859139);
        boolean changed = composerImpl.changed(app);
        Object rememberedValue = composerImpl.rememberedValue();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (changed || rememberedValue == composer$Companion$Empty$1) {
            rememberedValue = new AppBatteryPresenter(context, app);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        final AppBatteryPresenter appBatteryPresenter = (AppBatteryPresenter) rememberedValue;
        composerImpl.end(false);
        appBatteryPresenter.getClass();
        composerImpl.startReplaceGroup(-1121098189);
        composerImpl.startReplaceGroup(-422593764);
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (rememberedValue2 == composer$Companion$Empty$1) {
            rememberedValue2 =
                    Boolean.valueOf(
                            appBatteryPresenter
                                    .context
                                    .getResources()
                                    .getBoolean(R.bool.config_show_app_info_settings_battery));
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        boolean booleanValue = ((Boolean) rememberedValue2).booleanValue();
        composerImpl.end(false);
        composerImpl.end(false);
        if (!booleanValue) {
            RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
            if (endRestartGroup != null) {
                endRestartGroup.block =
                        new Function2() { // from class:
                                          // com.android.settings.spa.app.appinfo.AppBatteryPreferenceKt$AppBatteryPreference$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                AppBatteryPreferenceKt.AppBatteryPreference(
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
        PreferenceKt.Preference(
                new PreferenceModel(
                        composerImpl,
                        appBatteryPresenter) { // from class:
                                               // com.android.settings.spa.app.appinfo.AppBatteryPreferenceKt$AppBatteryPreference$2
                    public final Function0 enabled;
                    public final KFunction onClick;
                    public final Function0 summary;
                    public final String title;

                    {
                        this.title =
                                StringResources_androidKt.stringResource(
                                        composerImpl, R.string.battery_details_title);
                        this.summary = appBatteryPresenter.summary;
                        this.enabled = appBatteryPresenter.enabled;
                        this.onClick =
                                new AppBatteryPreferenceKt$AppBatteryPreference$2$onClick$1(
                                        0,
                                        appBatteryPresenter,
                                        AppBatteryPresenter.class,
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
        appBatteryPresenter.Updater(composerImpl, 8);
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            endRestartGroup2.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.appinfo.AppBatteryPreferenceKt$AppBatteryPreference$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppBatteryPreferenceKt.AppBatteryPreference(
                                    app,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
