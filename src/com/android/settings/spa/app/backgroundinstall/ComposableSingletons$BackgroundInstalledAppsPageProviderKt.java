package com.android.settings.spa.app.backgroundinstall;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.res.StringResources_androidKt;

import com.android.settings.R;
import com.android.settingslib.spa.framework.theme.SettingsDimension;
import com.android.settingslib.spa.widget.ui.TextKt;
import com.android.settingslib.spaprivileged.template.app.AppListInput;
import com.android.settingslib.spaprivileged.template.app.AppListKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ComposableSingletons$BackgroundInstalledAppsPageProviderKt {

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f47lambda1 =
            new ComposableLambdaImpl(
                    -453368071,
                    false,
                    new Function3() { // from class:
                                      // com.android.settings.spa.app.backgroundinstall.ComposableSingletons$BackgroundInstalledAppsPageProviderKt$lambda-1$1
                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            ((Number) obj3).intValue();
                            OpaqueKey opaqueKey = ComposerKt.invocation;
                            BackgroundInstalledAppsPageProvider.INSTANCE.EntryItem(
                                    (Composer) obj2, 8);
                            return Unit.INSTANCE;
                        }
                    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f48lambda2 =
            new ComposableLambdaImpl(
                    1960050747,
                    false,
                    new Function3() { // from class:
                                      // com.android.settings.spa.app.backgroundinstall.ComposableSingletons$BackgroundInstalledAppsPageProviderKt$lambda-2$1
                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            AppListInput appListInput = (AppListInput) obj;
                            Composer composer = (Composer) obj2;
                            int intValue = ((Number) obj3).intValue();
                            Intrinsics.checkNotNullParameter(appListInput, "$this$null");
                            if ((intValue & 14) == 0) {
                                intValue |= ((ComposerImpl) composer).changed(appListInput) ? 4 : 2;
                            }
                            if ((intValue & 91) == 18) {
                                ComposerImpl composerImpl = (ComposerImpl) composer;
                                if (composerImpl.getSkipping()) {
                                    composerImpl.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            OpaqueKey opaqueKey = ComposerKt.invocation;
                            AppListKt.AppList(appListInput, composer, (intValue & 14) | 8);
                            return Unit.INSTANCE;
                        }
                    });

    /* renamed from: lambda-3, reason: not valid java name */
    public static final ComposableLambdaImpl f49lambda3 =
            new ComposableLambdaImpl(
                    -1498576629,
                    false,
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.backgroundinstall.ComposableSingletons$BackgroundInstalledAppsPageProviderKt$lambda-3$1
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
                            Modifier padding =
                                    PaddingKt.padding(
                                            Modifier.Companion.$$INSTANCE,
                                            SettingsDimension.itemPadding);
                            MeasurePolicy maybeCachedBoxMeasurePolicy =
                                    BoxKt.maybeCachedBoxMeasurePolicy(
                                            Alignment.Companion.TopStart, false);
                            int currentCompositeKeyHash =
                                    ComposablesKt.getCurrentCompositeKeyHash(composer);
                            ComposerImpl composerImpl2 = (ComposerImpl) composer;
                            PersistentCompositionLocalMap currentCompositionLocalScope =
                                    composerImpl2.currentCompositionLocalScope();
                            Modifier materializeModifier =
                                    ComposedModifierKt.materializeModifier(composer, padding);
                            ComposeUiNode.Companion.getClass();
                            Function0 function0 = ComposeUiNode.Companion.Constructor;
                            if (!(composerImpl2.applier instanceof Applier)) {
                                ComposablesKt.invalidApplier();
                                throw null;
                            }
                            composerImpl2.startReusableNode();
                            if (composerImpl2.inserting) {
                                composerImpl2.createNode(function0);
                            } else {
                                composerImpl2.useNode();
                            }
                            Updater.m221setimpl(
                                    composer,
                                    maybeCachedBoxMeasurePolicy,
                                    ComposeUiNode.Companion.SetMeasurePolicy);
                            Updater.m221setimpl(
                                    composer,
                                    currentCompositionLocalScope,
                                    ComposeUiNode.Companion.SetResolvedCompositionLocals);
                            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                            if (composerImpl2.inserting
                                    || !Intrinsics.areEqual(
                                            composerImpl2.rememberedValue(),
                                            Integer.valueOf(currentCompositeKeyHash))) {
                                AnimatedContentKt$$ExternalSyntheticOutline0.m(
                                        currentCompositeKeyHash,
                                        composerImpl2,
                                        currentCompositeKeyHash,
                                        function2);
                            }
                            Updater.m221setimpl(
                                    composer,
                                    materializeModifier,
                                    ComposeUiNode.Companion.SetModifier);
                            TextKt.SettingsBody(
                                    StringResources_androidKt.stringResource(
                                            composer, R.string.background_install_summary),
                                    null,
                                    0,
                                    composer,
                                    0,
                                    6);
                            composerImpl2.end(true);
                            return Unit.INSTANCE;
                        }
                    });
}
