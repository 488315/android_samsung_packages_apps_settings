package com.android.settings.network.apn;

import android.content.Context;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.navigation.NamedNavArgument;
import androidx.navigation.NamedNavArgumentKt;
import androidx.navigation.NavArgument;
import androidx.navigation.NavArgumentBuilder;
import androidx.navigation.NavType;
import androidx.navigation.NavType$Companion$IntType$1;

import com.android.settingslib.spa.framework.common.SettingsPageProvider;
import com.android.settingslib.spa.framework.compose.NavControllerWrapper;
import com.android.settingslib.spa.framework.compose.NavControllerWrapperKt;

import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ApnEditPageProvider implements SettingsPageProvider {
    public static final ApnEditPageProvider INSTANCE = new ApnEditPageProvider();
    public static final List parameter =
            CollectionsKt__CollectionsKt.listOf(
                    (Object[])
                            new NamedNavArgument[] {
                                NamedNavArgumentKt.navArgument(
                                        "uriType",
                                        new Function1() { // from class:
                                                          // com.android.settings.network.apn.ApnEditPageProvider$parameter$1
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
                                        "uri",
                                        new Function1() { // from class:
                                                          // com.android.settings.network.apn.ApnEditPageProvider$parameter$2
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
                                        "subId",
                                        new Function1() { // from class:
                                                          // com.android.settings.network.apn.ApnEditPageProvider$parameter$3
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

    /* JADX WARN: Removed duplicated region for block: B:12:0x05a5  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0218  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x058f  */
    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void Page(
            final android.os.Bundle r89, androidx.compose.runtime.Composer r90, final int r91) {
        /*
            Method dump skipped, instructions count: 1509
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.apn.ApnEditPageProvider.Page(android.os.Bundle,"
                    + " androidx.compose.runtime.Composer, int):void");
    }

    public final void SubscriptionNotEnabledEffect(final int i, Composer composer, final int i2) {
        int i3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(883047839);
        if ((i2 & 14) == 0) {
            i3 = (composerImpl.changed(i) ? 4 : 2) | i2;
        } else {
            i3 = i2;
        }
        if ((i3 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            EffectsKt.LaunchedEffect(
                    composerImpl,
                    Integer.valueOf(i),
                    new ApnEditPageProvider$SubscriptionNotEnabledEffect$1(
                            (Context)
                                    composerImpl.consume(
                                            AndroidCompositionLocals_androidKt.LocalContext),
                            i,
                            (NavControllerWrapper)
                                    composerImpl.consume(NavControllerWrapperKt.LocalNavController),
                            null));
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.network.apn.ApnEditPageProvider$SubscriptionNotEnabledEffect$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            ApnEditPageProvider.this.SubscriptionNotEnabledEffect(
                                    i,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i2 | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final int getMetricsCategory() {
        return 13;
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final String getName() {
        return "ApnEdit";
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final List getParameter() {
        return parameter;
    }
}
