package com.android.settingslib.spa.widget.scaffold;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
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
import androidx.compose.ui.unit.Dp;

import com.android.settingslib.spa.widget.preference.PreferenceKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ComposableSingletons$SearchScaffoldKt {

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f91lambda1 =
            new ComposableLambdaImpl(
                    -1116581859,
                    false,
                    new Function3() { // from class:
                                      // com.android.settingslib.spa.widget.scaffold.ComposableSingletons$SearchScaffoldKt$lambda-1$1
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
    public static final ComposableLambdaImpl f92lambda2 =
            new ComposableLambdaImpl(
                    967676420,
                    false,
                    new Function3() { // from class:
                                      // com.android.settingslib.spa.widget.scaffold.ComposableSingletons$SearchScaffoldKt$lambda-2$1
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

    /* renamed from: lambda-3, reason: not valid java name */
    public static final ComposableLambdaImpl f93lambda3 =
            new ComposableLambdaImpl(
                    1853159711,
                    false,
                    new Function3() { // from class:
                                      // com.android.settingslib.spa.widget.scaffold.ComposableSingletons$SearchScaffoldKt$lambda-3$1
                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            RowScope SearchTopAppBar = (RowScope) obj;
                            Composer composer = (Composer) obj2;
                            int intValue = ((Number) obj3).intValue();
                            Intrinsics.checkNotNullParameter(
                                    SearchTopAppBar, "$this$SearchTopAppBar");
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

    /* renamed from: lambda-5, reason: not valid java name */
    public static final ComposableLambdaImpl f94lambda5;

    static {
        int i = ComposableSingletons$SearchScaffoldKt$lambda4$1.$r8$clinit;
        f94lambda5 =
                new ComposableLambdaImpl(
                        1190076194,
                        false,
                        new Function4() { // from class:
                                          // com.android.settingslib.spa.widget.scaffold.ComposableSingletons$SearchScaffoldKt$lambda-5$1
                            @Override // kotlin.jvm.functions.Function4
                            public final Object invoke(
                                    Object obj, Object obj2, Object obj3, Object obj4) {
                                int i2 = 2;
                                float f = ((Dp) obj).value;
                                Function0 anonymous$parameter$1$ = (Function0) obj2;
                                Composer composer = (Composer) obj3;
                                int intValue = ((Number) obj4).intValue();
                                Intrinsics.checkNotNullParameter(
                                        anonymous$parameter$1$, "$anonymous$parameter$1$");
                                if ((intValue & 641) == 128) {
                                    ComposerImpl composerImpl = (ComposerImpl) composer;
                                    if (composerImpl.getSkipping()) {
                                        composerImpl.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                OpaqueKey opaqueKey = ComposerKt.invocation;
                                Modifier.Companion companion = Modifier.Companion.$$INSTANCE;
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
                                        ComposedModifierKt.materializeModifier(composer, companion);
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
                                                .AnonymousClass1(i2),
                                        false,
                                        composer,
                                        0,
                                        2);
                                PreferenceKt.Preference(
                                        new ComposableSingletons$RegularScaffoldKt$lambda2$1
                                                .AnonymousClass1(3),
                                        false,
                                        composer,
                                        0,
                                        2);
                                composerImpl2.end(true);
                                return Unit.INSTANCE;
                            }
                        });
        int i2 = ComposableSingletons$SearchScaffoldKt$lambda6$1.$r8$clinit;
    }
}
