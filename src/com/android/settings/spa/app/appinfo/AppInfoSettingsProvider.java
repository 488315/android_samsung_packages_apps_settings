package com.android.settings.spa.app.appinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.FeatureFlagUtils;

import androidx.activity.compose.PredictiveBackHandlerKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionScopedCoroutineScopeCanceller;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.fragment.app.Fragment;
import androidx.navigation.NamedNavArgument;
import androidx.navigation.NamedNavArgumentKt;
import androidx.navigation.NavArgument;
import androidx.navigation.NavArgumentBuilder;
import androidx.navigation.NavType;
import androidx.navigation.NavType$Companion$IntType$1;

import com.android.settings.R;
import com.android.settings.applications.AppInfoBase;
import com.android.settings.applications.appinfo.AppInfoDashboardFragment;
import com.android.settings.spa.SpaActivity;
import com.android.settingslib.spa.framework.common.SettingsPageProvider;
import com.android.settingslib.spa.framework.compose.NavControllerWrapperKt;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;

import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.internal.ContextScope;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppInfoSettingsProvider implements SettingsPageProvider {
    public static final AppInfoSettingsProvider INSTANCE = new AppInfoSettingsProvider();
    public static final List parameter =
            CollectionsKt__CollectionsKt.listOf(
                    (Object[])
                            new NamedNavArgument[] {
                                NamedNavArgumentKt.navArgument(
                                        "packageName",
                                        new Function1() { // from class:
                                                          // com.android.settings.spa.app.appinfo.AppInfoSettingsProvider$parameter$1
                                            @Override // kotlin.jvm.functions.Function1
                                            public final Object invoke(Object obj) {
                                                NavArgumentBuilder navArgument =
                                                        (NavArgumentBuilder) obj;
                                                Intrinsics.checkNotNullParameter(
                                                        navArgument, "$this$navArgument");
                                                NavType$Companion$IntType$1
                                                        navType$Companion$IntType$1 =
                                                                NavType.StringType;
                                                NavArgument.Builder builder = navArgument.builder;
                                                builder.getClass();
                                                builder.type = navType$Companion$IntType$1;
                                                return Unit.INSTANCE;
                                            }
                                        }),
                                NamedNavArgumentKt.navArgument(
                                        "userId",
                                        new Function1() { // from class:
                                                          // com.android.settings.spa.app.appinfo.AppInfoSettingsProvider$parameter$2
                                            @Override // kotlin.jvm.functions.Function1
                                            public final Object invoke(Object obj) {
                                                NavArgumentBuilder navArgument =
                                                        (NavArgumentBuilder) obj;
                                                Intrinsics.checkNotNullParameter(
                                                        navArgument, "$this$navArgument");
                                                NavType$Companion$IntType$1
                                                        navType$Companion$IntType$1 =
                                                                NavType.IntType;
                                                NavArgument.Builder builder = navArgument.builder;
                                                builder.getClass();
                                                builder.type = navType$Companion$IntType$1;
                                                return Unit.INSTANCE;
                                            }
                                        })
                            });

    public static Function0 navigator(ApplicationInfo app, Composer composer) {
        Intrinsics.checkNotNullParameter(app, "app");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(942954083);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Function0 navigator =
                NavControllerWrapperKt.navigator(
                        composerImpl, "AppInfoSettings/" + ApplicationInfosKt.toRoute(app));
        composerImpl.end(false);
        return navigator;
    }

    public static final void startAppInfoSettings(
            String packageName, int i, Fragment source, int i2, int i3) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(source, "source");
        Context context = source.getContext();
        if (context == null) {
            return;
        }
        if (!FeatureFlagUtils.isEnabled(context, "settings_enable_spa")) {
            AppInfoBase.startAppInfoFragment(
                    AppInfoDashboardFragment.class,
                    context.getString(R.string.application_info_label),
                    packageName,
                    i,
                    source,
                    i2,
                    i3);
        } else {
            SpaActivity.Companion companion = SpaActivity.Companion;
            SpaActivity.Companion.startSpaActivity(
                    context, INSTANCE.getRoute(UserHandle.getUserId(i), packageName));
        }
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final void Page(final Bundle bundle, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(878487921);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Intrinsics.checkNotNull(bundle);
        String string = bundle.getString("packageName");
        Intrinsics.checkNotNull(string);
        int i2 = bundle.getInt("userId");
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
        composerImpl.startReplaceGroup(1337936610);
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (rememberedValue2 == composer$Companion$Empty$1) {
            rememberedValue2 =
                    new PackageInfoPresenter(context, string, i2, (ContextScope) coroutineScope);
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        PackageInfoPresenter packageInfoPresenter = (PackageInfoPresenter) rememberedValue2;
        composerImpl.end(false);
        AppInfoSettingsKt.access$AppInfoSettings(packageInfoPresenter, composerImpl, 8);
        packageInfoPresenter.PackageFullyRemovedEffect(composerImpl, 8);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.appinfo.AppInfoSettingsProvider$Page$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppInfoSettingsProvider.this.Page(
                                    bundle,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final String getName() {
        return "AppInfoSettings";
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final List getParameter() {
        return parameter;
    }

    public final String getRoute(int i, String packageName) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        return "AppInfoSettings/" + packageName + "/" + i;
    }
}
