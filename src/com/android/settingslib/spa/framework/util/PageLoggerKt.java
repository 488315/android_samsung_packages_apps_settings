package com.android.settingslib.spa.framework.util;

import android.os.Bundle;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.core.os.BundleKt;

import com.android.settings.spa.SettingsSpaEnvironment;
import com.android.settingslib.spa.framework.common.LogCategory;
import com.android.settingslib.spa.framework.common.LogEvent;
import com.android.settingslib.spa.framework.common.SettingsPage;
import com.android.settingslib.spa.framework.common.SpaEnvironment;
import com.android.settingslib.spa.framework.common.SpaEnvironmentFactory;
import com.android.settingslib.spa.framework.compose.LifecycleEffectKt;
import com.android.settingslib.spa.framework.compose.NavControllerWrapper;
import com.android.settingslib.spa.framework.compose.NavControllerWrapperKt;

import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class PageLoggerKt {
    public static final void PageLogger(
            final SettingsPage settingsPage, Composer composer, final int i) {
        Intrinsics.checkNotNullParameter(settingsPage, "<this>");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-848887740);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final NavControllerWrapper navControllerWrapper =
                (NavControllerWrapper)
                        composerImpl.consume(NavControllerWrapperKt.LocalNavController);
        LifecycleEffectKt.LifecycleEffect(
                new Function0() { // from class:
                                  // com.android.settingslib.spa.framework.util.PageLoggerKt$PageLogger$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        PageLoggerKt.access$logPageEvent(
                                SettingsPage.this, LogEvent.PAGE_ENTER, navControllerWrapper);
                        return Unit.INSTANCE;
                    }
                },
                new Function0() { // from class:
                                  // com.android.settingslib.spa.framework.util.PageLoggerKt$PageLogger$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        PageLoggerKt.access$logPageEvent(
                                SettingsPage.this, LogEvent.PAGE_LEAVE, navControllerWrapper);
                        return Unit.INSTANCE;
                    }
                },
                composerImpl,
                0,
                0);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.framework.util.PageLoggerKt$PageLogger$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            PageLoggerKt.PageLogger(
                                    SettingsPage.this,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public static final void access$logPageEvent(
            SettingsPage settingsPage,
            LogEvent logEvent,
            NavControllerWrapper navControllerWrapper) {
        SpaEnvironment spaEnvironment = SpaEnvironmentFactory.spaEnvironment;
        if (spaEnvironment == null) {
            throw new UnsupportedOperationException("Spa environment is not set");
        }
        SettingsSpaEnvironment settingsSpaEnvironment = (SettingsSpaEnvironment) spaEnvironment;
        String str = settingsPage.id;
        LogCategory logCategory = LogCategory.FRAMEWORK;
        Bundle bundleOf =
                BundleKt.bundleOf(
                        new Pair("name", settingsPage.displayName),
                        new Pair("session", navControllerWrapper.getSessionSourceName()),
                        new Pair("metricsCategory", Integer.valueOf(settingsPage.metricsCategory)));
        Bundle normalize =
                ParameterKt.normalize(settingsPage.parameter, settingsPage.arguments, false);
        if (normalize != null) {
            bundleOf.putAll(normalize);
        }
        settingsSpaEnvironment.logger.event(str, logEvent, logCategory, bundleOf);
    }
}
