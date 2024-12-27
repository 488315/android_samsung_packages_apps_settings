package com.android.settings.wifi.details2;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SimpleClock;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.compose.LocalLifecycleOwnerKt;
import androidx.navigation.NamedNavArgumentKt;
import androidx.navigation.NavArgument;
import androidx.navigation.NavArgumentBuilder;
import androidx.navigation.NavType;
import androidx.navigation.NavType$Companion$IntType$1;

import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.wifi.WifiTrackerLibProviderImpl;
import com.android.settingslib.spa.framework.common.SettingsPageProvider;
import com.android.wifitrackerlib.WifiEntry;

import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import java.time.ZoneOffset;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiPrivacyPageProvider implements SettingsPageProvider {
    public static final WifiPrivacyPageProvider INSTANCE = new WifiPrivacyPageProvider();
    public static final List parameter =
            CollectionsKt__CollectionsJVMKt.listOf(
                    NamedNavArgumentKt.navArgument(
                            "wifiEntryKey",
                            new Function1() { // from class:
                                              // com.android.settings.wifi.details2.WifiPrivacyPageProvider$parameter$1
                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj) {
                                    NavArgumentBuilder navArgument = (NavArgumentBuilder) obj;
                                    Intrinsics.checkNotNullParameter(
                                            navArgument, "$this$navArgument");
                                    NavType$Companion$IntType$1 navType$Companion$IntType$1 =
                                            NavType.StringType;
                                    NavArgument.Builder builder = navArgument.builder;
                                    builder.getClass();
                                    builder.type = navType$Companion$IntType$1;
                                    return Unit.INSTANCE;
                                }
                            }));

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final void Page(final Bundle bundle, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-2063610223);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Intrinsics.checkNotNull(bundle);
        String string = bundle.getString("wifiEntryKey");
        if (string != null) {
            Context context =
                    (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            Lifecycle liftCycle =
                    ((LifecycleOwner)
                                    composerImpl.consume(LocalLifecycleOwnerKt.LocalLifecycleOwner))
                            .getLifecycle();
            composerImpl.startReplaceGroup(-1975937668);
            Object rememberedValue = composerImpl.rememberedValue();
            if (rememberedValue == Composer.Companion.Empty) {
                Intrinsics.checkNotNullParameter(context, "context");
                Intrinsics.checkNotNullParameter(liftCycle, "liftCycle");
                HandlerThread handlerThread = new HandlerThread("WifiPrivacyPageProvider", 10);
                handlerThread.start();
                SimpleClock wifiPrivacyPageProviderKt$getWifiEntry$elapsedRealtimeClock$1 =
                        new WifiPrivacyPageProviderKt$getWifiEntry$elapsedRealtimeClock$1(
                                ZoneOffset.UTC);
                FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                if (featureFactoryImpl == null) {
                    throw new UnsupportedOperationException("No feature factory configured");
                }
                WifiTrackerLibProviderImpl wifiTrackerLibProvider =
                        featureFactoryImpl.getWifiTrackerLibProvider();
                Handler handler = new Handler(Looper.getMainLooper());
                Handler threadHandler = handlerThread.getThreadHandler();
                wifiTrackerLibProvider.getClass();
                rememberedValue =
                        WifiTrackerLibProviderImpl.createNetworkDetailsTracker(
                                        liftCycle,
                                        context,
                                        handler,
                                        threadHandler,
                                        wifiPrivacyPageProviderKt$getWifiEntry$elapsedRealtimeClock$1,
                                        string)
                                .getWifiEntry();
                Intrinsics.checkNotNullExpressionValue(rememberedValue, "getWifiEntry(...)");
                composerImpl.updateRememberedValue(rememberedValue);
            }
            composerImpl.end(false);
            WifiPrivacyPageProviderKt.WifiPrivacyPage((WifiEntry) rememberedValue, composerImpl, 8);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.wifi.details2.WifiPrivacyPageProvider$Page$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            WifiPrivacyPageProvider.this.Page(
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
        return "WifiPrivacy";
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final List getParameter() {
        return parameter;
    }
}
