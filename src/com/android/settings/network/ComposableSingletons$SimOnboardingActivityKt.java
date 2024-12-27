package com.android.settings.network;

import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material.icons.outlined.SignalCellularAltKt;
import androidx.compose.material3.ColorScheme;
import androidx.compose.material3.ColorSchemeKt;
import androidx.compose.material3.IconKt;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.text.style.TextAlign;

import com.android.settings.R;
import com.android.settingslib.spa.framework.theme.SettingsDimension;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ComposableSingletons$SimOnboardingActivityKt {

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f19lambda1 =
            new ComposableLambdaImpl(
                    862369887,
                    false,
                    new Function2() { // from class:
                                      // com.android.settings.network.ComposableSingletons$SimOnboardingActivityKt$lambda-1$1
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            Composer composer = (Composer) obj;
                            if ((((Number) obj2).intValue() & 11) == 2) {
                                ComposerImpl composerImpl = (ComposerImpl) composer;
                                if (composerImpl.getSkipping()) {
                                    composerImpl.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            OpaqueKey opaqueKey = ComposerKt.invocation;
                            TextKt.m210Text4IGK_g(
                                    StringResources_androidKt.stringResource(
                                            composer, R.string.sim_action_restart_dialog_msg),
                                    null,
                                    0L,
                                    0L,
                                    null,
                                    null,
                                    null,
                                    0L,
                                    null,
                                    null,
                                    0L,
                                    0,
                                    false,
                                    0,
                                    0,
                                    null,
                                    null,
                                    composer,
                                    0,
                                    0,
                                    131070);
                            return Unit.INSTANCE;
                        }
                    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f20lambda2 =
            new ComposableLambdaImpl(
                    -426838442,
                    false,
                    new Function2() { // from class:
                                      // com.android.settings.network.ComposableSingletons$SimOnboardingActivityKt$lambda-2$1
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            Composer composer = (Composer) obj;
                            if ((((Number) obj2).intValue() & 11) == 2) {
                                ComposerImpl composerImpl = (ComposerImpl) composer;
                                if (composerImpl.getSkipping()) {
                                    composerImpl.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            OpaqueKey opaqueKey = ComposerKt.invocation;
                            TextKt.m210Text4IGK_g(
                                    StringResources_androidKt.stringResource(
                                            composer, R.string.privileged_action_disable_fail_text),
                                    null,
                                    0L,
                                    0L,
                                    null,
                                    null,
                                    null,
                                    0L,
                                    null,
                                    null,
                                    0L,
                                    0,
                                    false,
                                    0,
                                    0,
                                    null,
                                    null,
                                    composer,
                                    0,
                                    0,
                                    131070);
                            return Unit.INSTANCE;
                        }
                    });

    /* renamed from: lambda-3, reason: not valid java name */
    public static final ComposableLambdaImpl f21lambda3 =
            new ComposableLambdaImpl(
                    -215522728,
                    false,
                    new Function2() { // from class:
                                      // com.android.settings.network.ComposableSingletons$SimOnboardingActivityKt$lambda-3$1
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            Composer composer = (Composer) obj;
                            if ((((Number) obj2).intValue() & 11) == 2) {
                                ComposerImpl composerImpl = (ComposerImpl) composer;
                                if (composerImpl.getSkipping()) {
                                    composerImpl.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            OpaqueKey opaqueKey = ComposerKt.invocation;
                            TextKt.m210Text4IGK_g(
                                    StringResources_androidKt.stringResource(
                                            composer, R.string.sim_action_enable_sim_fail_text),
                                    null,
                                    0L,
                                    0L,
                                    null,
                                    null,
                                    null,
                                    0L,
                                    null,
                                    null,
                                    0L,
                                    0,
                                    false,
                                    0,
                                    0,
                                    null,
                                    null,
                                    composer,
                                    0,
                                    0,
                                    131070);
                            return Unit.INSTANCE;
                        }
                    });

    /* renamed from: lambda-4, reason: not valid java name */
    public static final ComposableLambdaImpl f22lambda4 =
            new ComposableLambdaImpl(
                    1077746633,
                    false,
                    new Function2() { // from class:
                                      // com.android.settings.network.ComposableSingletons$SimOnboardingActivityKt$lambda-4$1
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            Composer composer = (Composer) obj;
                            if ((((Number) obj2).intValue() & 11) == 2) {
                                ComposerImpl composerImpl = (ComposerImpl) composer;
                                if (composerImpl.getSkipping()) {
                                    composerImpl.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            OpaqueKey opaqueKey = ComposerKt.invocation;
                            TextKt.m210Text4IGK_g(
                                    StringResources_androidKt.stringResource(
                                            composer, R.string.dsds_activation_failure_body_msg2),
                                    null,
                                    0L,
                                    0L,
                                    null,
                                    null,
                                    null,
                                    0L,
                                    null,
                                    null,
                                    0L,
                                    0,
                                    false,
                                    0,
                                    0,
                                    null,
                                    null,
                                    composer,
                                    0,
                                    0,
                                    131070);
                            return Unit.INSTANCE;
                        }
                    });

    /* renamed from: lambda-5, reason: not valid java name */
    public static final ComposableLambdaImpl f23lambda5 =
            new ComposableLambdaImpl(
                    1452805870,
                    false,
                    new Function2() { // from class:
                                      // com.android.settings.network.ComposableSingletons$SimOnboardingActivityKt$lambda-5$1
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            Composer composer = (Composer) obj;
                            if ((((Number) obj2).intValue() & 11) == 2) {
                                ComposerImpl composerImpl = (ComposerImpl) composer;
                                if (composerImpl.getSkipping()) {
                                    composerImpl.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            OpaqueKey opaqueKey = ComposerKt.invocation;
                            IconKt.m188Iconww6aTOc(
                                    SignalCellularAltKt.getSignalCellularAlt(),
                                    (String) null,
                                    SizeKt.m96size3ABfNKs(
                                            Modifier.Companion.$$INSTANCE,
                                            SettingsDimension.iconLarge),
                                    ((ColorScheme)
                                                    ((ComposerImpl) composer)
                                                            .consume(
                                                                    ColorSchemeKt.LocalColorScheme))
                                            .primary,
                                    composer,
                                    48,
                                    0);
                            return Unit.INSTANCE;
                        }
                    });

    /* renamed from: lambda-6, reason: not valid java name */
    public static final ComposableLambdaImpl f24lambda6 =
            new ComposableLambdaImpl(
                    1716502895,
                    false,
                    new Function2() { // from class:
                                      // com.android.settings.network.ComposableSingletons$SimOnboardingActivityKt$lambda-6$1
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            Composer composer = (Composer) obj;
                            if ((((Number) obj2).intValue() & 11) == 2) {
                                ComposerImpl composerImpl = (ComposerImpl) composer;
                                if (composerImpl.getSkipping()) {
                                    composerImpl.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            OpaqueKey opaqueKey = ComposerKt.invocation;
                            TextKt.m210Text4IGK_g(
                                    StringResources_androidKt.stringResource(
                                            composer, R.string.sim_onboarding_dialog_starting_msg),
                                    SizeKt.FillWholeMaxWidth,
                                    0L,
                                    0L,
                                    null,
                                    null,
                                    null,
                                    0L,
                                    null,
                                    new TextAlign(3),
                                    0L,
                                    0,
                                    false,
                                    0,
                                    0,
                                    null,
                                    null,
                                    composer,
                                    48,
                                    0,
                                    130556);
                            return Unit.INSTANCE;
                        }
                    });
}
