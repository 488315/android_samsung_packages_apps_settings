package com.android.settings.spa.network;

import android.os.Bundle;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.navigation.NamedNavArgumentKt;
import androidx.navigation.NavArgument;
import androidx.navigation.NavArgumentBuilder;
import androidx.navigation.NavType;
import androidx.navigation.NavType$Companion$IntType$1;
import androidx.navigation.Navigator;
import androidx.navigation.compose.NavHostControllerKt;

import com.android.settings.network.SimOnboardingActivity;
import com.android.settings.network.SimOnboardingService;
import com.android.settingslib.spa.framework.common.SettingsPageProvider;
import com.android.settingslib.spa.framework.common.SettingsPageProviderKt;

import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SimOnboardingPageProvider implements SettingsPageProvider {
    public static final SimOnboardingPageProvider INSTANCE;
    public static final SimOnboardingService onboardingService;
    public static final List parameter;

    static {
        SimOnboardingPageProvider simOnboardingPageProvider = new SimOnboardingPageProvider();
        INSTANCE = simOnboardingPageProvider;
        parameter =
                CollectionsKt__CollectionsJVMKt.listOf(
                        NamedNavArgumentKt.navArgument(
                                "subId",
                                new Function1() { // from class:
                                                  // com.android.settings.spa.network.SimOnboardingPageProvider$parameter$1
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj) {
                                        NavArgumentBuilder navArgument = (NavArgumentBuilder) obj;
                                        Intrinsics.checkNotNullParameter(
                                                navArgument, "$this$navArgument");
                                        NavType$Companion$IntType$1 navType$Companion$IntType$1 =
                                                NavType.IntType;
                                        NavArgument.Builder builder = navArgument.builder;
                                        builder.getClass();
                                        builder.type = navType$Companion$IntType$1;
                                        return Unit.INSTANCE;
                                    }
                                }));
        SettingsPageProviderKt.createSettingsPage(simOnboardingPageProvider, null);
        onboardingService = SimOnboardingActivity.onboardingService;
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final void Page(final Bundle bundle, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1420949618);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        SimOnboardingPageProviderKt.PageImpl(
                onboardingService,
                NavHostControllerKt.rememberNavController(new Navigator[0], composerImpl),
                composerImpl,
                72);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.network.SimOnboardingPageProvider$Page$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SimOnboardingPageProvider.this.Page(
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
        return "SimOnboardingPageProvider";
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final List getParameter() {
        return parameter;
    }

    public static /* synthetic */ void getOnboardingService$annotations() {}
}
