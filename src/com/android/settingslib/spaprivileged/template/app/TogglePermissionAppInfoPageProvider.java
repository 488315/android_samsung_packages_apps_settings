package com.android.settingslib.spaprivileged.template.app;

import android.content.pm.ApplicationInfo;
import android.os.Bundle;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.navigation.NamedNavArgument;
import androidx.navigation.NamedNavArgumentKt;
import androidx.navigation.NavArgument;
import androidx.navigation.NavArgumentBuilder;
import androidx.navigation.NavType;
import androidx.navigation.NavType$Companion$IntType$1;

import com.android.settingslib.spa.framework.common.SettingsEntryBuilder;
import com.android.settingslib.spa.framework.common.SettingsPage;
import com.android.settingslib.spa.framework.common.SettingsPageProvider;
import com.android.settingslib.spa.framework.compose.NavControllerWrapperKt;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;

import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class TogglePermissionAppInfoPageProvider implements SettingsPageProvider {
    public static final List PAGE_PARAMETER =
            CollectionsKt__CollectionsKt.listOf(
                    (Object[])
                            new NamedNavArgument[] {
                                NamedNavArgumentKt.navArgument(
                                        "permission",
                                        new Function1() { // from class:
                                                          // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppInfoPageProvider$Companion$PAGE_PARAMETER$1
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
                                        "rt_packageName",
                                        new Function1() { // from class:
                                                          // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppInfoPageProvider$Companion$PAGE_PARAMETER$2
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
                                        "rt_userId",
                                        new Function1() { // from class:
                                                          // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppInfoPageProvider$Companion$PAGE_PARAMETER$3
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
    public final TogglePermissionAppListTemplate appListTemplate;
    public final List parameter = PAGE_PARAMETER;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class Companion {
        public static Function0 navigator(
                String permissionType, ApplicationInfo app, Composer composer) {
            Intrinsics.checkNotNullParameter(permissionType, "permissionType");
            Intrinsics.checkNotNullParameter(app, "app");
            ComposerImpl composerImpl = (ComposerImpl) composer;
            composerImpl.startReplaceGroup(-512084599);
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Function0 navigator =
                    NavControllerWrapperKt.navigator(
                            composerImpl,
                            "TogglePermissionAppInfoPage/"
                                    + permissionType
                                    + "/"
                                    + ApplicationInfosKt.toRoute(app));
            composerImpl.end(false);
            return navigator;
        }
    }

    public TogglePermissionAppInfoPageProvider(
            TogglePermissionAppListTemplate togglePermissionAppListTemplate) {
        this.appListTemplate = togglePermissionAppListTemplate;
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final void Page(final Bundle bundle, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-2136532254);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        String string = bundle != null ? bundle.getString("permission") : null;
        Intrinsics.checkNotNull(string);
        String string2 = bundle.getString("rt_packageName");
        Intrinsics.checkNotNull(string2);
        TogglePermissionAppInfoPageKt.TogglePermissionAppInfoPage(
                this.appListTemplate
                        .rememberModel$frameworks__base__packages__SettingsLib__SpaPrivileged__android_common__SeslSpaPrivilegedLib(
                                composerImpl, string),
                string2,
                bundle.getInt("rt_userId"),
                null,
                null,
                composerImpl,
                0,
                12);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppInfoPageProvider$Page$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            TogglePermissionAppInfoPageProvider.this.Page(
                                    bundle,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final List buildEntry(Bundle bundle) {
        return CollectionsKt__CollectionsJVMKt.listOf(
                new SettingsEntryBuilder(
                                "AllowControl",
                                SettingsPage.Companion.create$default(
                                        "TogglePermissionAppInfoPage", this.parameter, bundle))
                        .build());
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final String getName() {
        return "TogglePermissionAppInfoPage";
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final List getParameter() {
        return this.parameter;
    }
}
