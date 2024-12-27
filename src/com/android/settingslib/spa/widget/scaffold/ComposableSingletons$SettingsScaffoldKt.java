package com.android.settingslib.spa.widget.scaffold;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.RowScope;
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
import androidx.compose.ui.node.ComposeUiNode;

import com.android.settingslib.spa.widget.preference.PreferenceKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ComposableSingletons$SettingsScaffoldKt {

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f97lambda1 =
            new ComposableLambdaImpl(
                    -715157594,
                    false,
                    new Function3() { // from class:
                                      // com.android.settingslib.spa.widget.scaffold.ComposableSingletons$SettingsScaffoldKt$lambda-1$1
                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            Composer composer = (Composer) obj2;
                            int intValue = ((Number) obj3).intValue();
                            Intrinsics.checkNotNullParameter((RowScope) obj, "$this$null");
                            if ((intValue & 81) == 16) {
                                ComposerImpl composerImpl = (ComposerImpl) composer;
                                if (composerImpl.getSkipping()) {
                                    composerImpl.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            OpaqueKey opaqueKey = ComposerKt.invocation;
                            return Unit.INSTANCE;
                        }
                    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f98lambda2 =
            new ComposableLambdaImpl(
                    2076196984,
                    false,
                    new Function3() { // from class:
                                      // com.android.settingslib.spa.widget.scaffold.ComposableSingletons$SettingsScaffoldKt$lambda-2$1
                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            int i = 4;
                            PaddingValues paddingValues = (PaddingValues) obj;
                            Composer composer = (Composer) obj2;
                            int intValue = ((Number) obj3).intValue();
                            Intrinsics.checkNotNullParameter(paddingValues, "paddingValues");
                            if ((intValue & 14) == 0) {
                                intValue |=
                                        ((ComposerImpl) composer).changed(paddingValues) ? 4 : 2;
                            }
                            if ((intValue & 91) == 18) {
                                ComposerImpl composerImpl = (ComposerImpl) composer;
                                if (composerImpl.getSkipping()) {
                                    composerImpl.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            OpaqueKey opaqueKey = ComposerKt.invocation;
                            Modifier padding =
                                    PaddingKt.padding(Modifier.Companion.$$INSTANCE, paddingValues);
                            ColumnMeasurePolicy columnMeasurePolicy =
                                    ColumnKt.columnMeasurePolicy(
                                            Arrangement.Top,
                                            Alignment.Companion.Start,
                                            composer,
                                            0);
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
                                    columnMeasurePolicy,
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
                            PreferenceKt.Preference(
                                    new ComposableSingletons$RegularScaffoldKt$lambda2$1
                                            .AnonymousClass1(i),
                                    false,
                                    composer,
                                    0,
                                    2);
                            PreferenceKt.Preference(
                                    new ComposableSingletons$RegularScaffoldKt$lambda2$1
                                            .AnonymousClass1(5),
                                    false,
                                    composer,
                                    0,
                                    2);
                            composerImpl2.end(true);
                            return Unit.INSTANCE;
                        }
                    });

    static {
        int i = ComposableSingletons$SettingsScaffoldKt$lambda3$1.$r8$clinit;
    }
}
