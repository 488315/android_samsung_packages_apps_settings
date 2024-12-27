package com.android.settings.spa.app.appinfo;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.livedata.LiveDataAdapterKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;

import com.android.settings.R;
import com.android.settingslib.spa.widget.preference.PreferenceKt;
import com.android.settingslib.spa.widget.preference.PreferenceModel;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KFunction;

import java.util.Collection;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppTimeSpentPreferenceKt {
    public static final void AppTimeSpentPreference(
            final ApplicationInfo app, Composer composer, final int i) {
        ActivityInfo activityInfo;
        ApplicationInfo applicationInfo;
        Intrinsics.checkNotNullParameter(app, "app");
        final ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-728481761);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Context context =
                (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        composerImpl.startReplaceGroup(625863827);
        boolean changed = composerImpl.changed(app);
        Object rememberedValue = composerImpl.rememberedValue();
        if (changed || rememberedValue == Composer.Companion.Empty) {
            rememberedValue = new AppTimeSpentPresenter(context, app);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        final AppTimeSpentPresenter appTimeSpentPresenter = (AppTimeSpentPresenter) rememberedValue;
        composerImpl.end(false);
        List queryIntentActivitiesAsUser =
                appTimeSpentPresenter
                        .context
                        .getPackageManager()
                        .queryIntentActivitiesAsUser(
                                appTimeSpentPresenter.intent,
                                PackageManager.ResolveInfoFlags.of(0L),
                                ApplicationInfosKt.getUserId(appTimeSpentPresenter.app));
        Intrinsics.checkNotNullExpressionValue(
                queryIntentActivitiesAsUser, "queryIntentActivitiesAsUser(...)");
        List<ResolveInfo> list = queryIntentActivitiesAsUser;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            for (ResolveInfo resolveInfo : list) {
                if (resolveInfo != null
                        && (activityInfo = resolveInfo.activityInfo) != null
                        && (applicationInfo = activityInfo.applicationInfo) != null
                        && applicationInfo.isSystemApp()) {
                    final MutableState observeAsState =
                            LiveDataAdapterKt.observeAsState(
                                    appTimeSpentPresenter.summaryLiveData,
                                    StringResources_androidKt.stringResource(
                                            composerImpl, R.string.summary_placeholder),
                                    composerImpl);
                    PreferenceKt.Preference(
                            new PreferenceModel(
                                    composerImpl,
                                    appTimeSpentPresenter,
                                    observeAsState) { // from class:
                                                      // com.android.settings.spa.app.appinfo.AppTimeSpentPreferenceKt$AppTimeSpentPreference$2
                                public final Function0 enabled;
                                public final KFunction onClick;
                                public final Function0 summary;
                                public final String title;

                                {
                                    this.title =
                                            StringResources_androidKt.stringResource(
                                                    composerImpl,
                                                    R.string.time_spent_in_app_pref_title);
                                    this.summary =
                                            new Function0() { // from class:
                                                              // com.android.settings.spa.app.appinfo.AppTimeSpentPreferenceKt$AppTimeSpentPreference$2$summary$1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(0);
                                                }

                                                @Override // kotlin.jvm.functions.Function0
                                                /* renamed from: invoke */
                                                public final Object mo1068invoke() {
                                                    return (String) observeAsState.getValue();
                                                }
                                            };
                                    this.enabled =
                                            new Function0() { // from class:
                                                              // com.android.settings.spa.app.appinfo.AppTimeSpentPreferenceKt$AppTimeSpentPreference$2$enabled$1
                                                {
                                                    super(0);
                                                }

                                                @Override // kotlin.jvm.functions.Function0
                                                /* renamed from: invoke */
                                                public final Object mo1068invoke() {
                                                    return Boolean.valueOf(
                                                            ApplicationInfosKt.hasFlag(
                                                                    8388608,
                                                                    AppTimeSpentPresenter.this
                                                                            .app));
                                                }
                                            };
                                    this.onClick =
                                            new AppTimeSpentPreferenceKt$AppTimeSpentPreference$2$onClick$1(
                                                    0,
                                                    appTimeSpentPresenter,
                                                    AppTimeSpentPresenter.class,
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
                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                    RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
                    if (endRestartGroup != null) {
                        endRestartGroup.block =
                                new Function2() { // from class:
                                                  // com.android.settings.spa.app.appinfo.AppTimeSpentPreferenceKt$AppTimeSpentPreference$3
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        ((Number) obj2).intValue();
                                        AppTimeSpentPreferenceKt.AppTimeSpentPreference(
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
            }
        }
        OpaqueKey opaqueKey3 = ComposerKt.invocation;
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            endRestartGroup2.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.appinfo.AppTimeSpentPreferenceKt$AppTimeSpentPreference$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppTimeSpentPreferenceKt.AppTimeSpentPreference(
                                    app,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
