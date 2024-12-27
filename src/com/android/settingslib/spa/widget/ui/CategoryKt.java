package com.android.settingslib.spa.widget.ui;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Top$1;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.material3.ColorScheme;
import androidx.compose.material3.ColorSchemeKt;
import androidx.compose.material3.Typography;
import androidx.compose.material3.TypographyKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.StructuralEqualityPolicy;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.OnGloballyPositionedModifierKt;
import androidx.compose.ui.node.ComposeUiNode;

import com.android.settingslib.spa.framework.theme.SettingsDimension;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class CategoryKt {
    public static final void Category(
            final String title, final Function3 content, Composer composer, final int i) {
        int i2;
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(content, "content");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(397296728);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(title) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl.changedInstance(content) ? 32 : 16;
        }
        if ((i2 & 91) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Modifier.Companion companion = Modifier.Companion.$$INSTANCE;
            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
            BiasAlignment.Horizontal horizontal = Alignment.Companion.Start;
            ColumnMeasurePolicy columnMeasurePolicy =
                    ColumnKt.columnMeasurePolicy(arrangement$Top$1, horizontal, composerImpl, 0);
            int i3 = composerImpl.compoundKeyHash;
            PersistentCompositionLocalMap currentCompositionLocalScope =
                    composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier =
                    ComposedModifierKt.materializeModifier(composerImpl, companion);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            boolean z = composerImpl.applier instanceof Applier;
            if (!z) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
            Updater.m221setimpl(composerImpl, columnMeasurePolicy, function2);
            Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m221setimpl(composerImpl, currentCompositionLocalScope, function22);
            Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting
                    || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i3))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(i3, composerImpl, i3, function23);
            }
            Function2 function24 = ComposeUiNode.Companion.SetModifier;
            Updater.m221setimpl(composerImpl, materializeModifier, function24);
            composerImpl.startReplaceGroup(-391206090);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue =
                        SnapshotStateKt.mutableStateOf(
                                Boolean.FALSE, StructuralEqualityPolicy.INSTANCE);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            final MutableState mutableState = (MutableState) rememberedValue;
            composerImpl.end(false);
            composerImpl.startReplaceGroup(-391206047);
            if (((Boolean) mutableState.getValue()).booleanValue()) {
                CategoryTitle(title, composerImpl, i2 & 14);
            }
            composerImpl.end(false);
            composerImpl.startReplaceGroup(-391205931);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 =
                        new Function1() { // from class:
                                          // com.android.settingslib.spa.widget.ui.CategoryKt$Category$1$1$1
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                LayoutCoordinates coordinates = (LayoutCoordinates) obj;
                                Intrinsics.checkNotNullParameter(coordinates, "coordinates");
                                MutableState.this.setValue(
                                        Boolean.valueOf(
                                                ((int)
                                                                (coordinates.mo414getSizeYbymL2g()
                                                                        & 4294967295L))
                                                        > 0));
                                return Unit.INSTANCE;
                            }
                        };
                composerImpl.updateRememberedValue(rememberedValue2);
            }
            composerImpl.end(false);
            Modifier onGloballyPositioned =
                    OnGloballyPositionedModifierKt.onGloballyPositioned(
                            companion, (Function1) rememberedValue2);
            int i4 = ((i2 << 6) & 7168) | 6;
            ColumnMeasurePolicy columnMeasurePolicy2 =
                    ColumnKt.columnMeasurePolicy(arrangement$Top$1, horizontal, composerImpl, 0);
            int i5 = composerImpl.compoundKeyHash;
            PersistentCompositionLocalMap currentCompositionLocalScope2 =
                    composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier2 =
                    ComposedModifierKt.materializeModifier(composerImpl, onGloballyPositioned);
            if (!z) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m221setimpl(composerImpl, columnMeasurePolicy2, function2);
            Updater.m221setimpl(composerImpl, currentCompositionLocalScope2, function22);
            if (composerImpl.inserting
                    || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i5))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(i5, composerImpl, i5, function23);
            }
            Updater.m221setimpl(composerImpl, materializeModifier2, function24);
            content.invoke(
                    ColumnScopeInstance.INSTANCE,
                    composerImpl,
                    Integer.valueOf(((i4 >> 6) & 112) | 6));
            composerImpl.end(true);
            composerImpl.end(true);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.ui.CategoryKt$Category$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            CategoryKt.Category(
                                    title,
                                    content,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public static final void CategoryTitle(final String title, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl;
        Intrinsics.checkNotNullParameter(title, "title");
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(295651755);
        if ((i & 14) == 0) {
            i2 = (composerImpl2.changed(title) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            composerImpl = composerImpl2;
            androidx.compose.material3.TextKt.m210Text4IGK_g(
                    title,
                    PaddingKt.m88paddingqDBjuR0(
                            Modifier.Companion.$$INSTANCE,
                            SettingsDimension.itemPaddingStart,
                            20,
                            SettingsDimension.itemPaddingEnd,
                            8),
                    ((ColorScheme) composerImpl2.consume(ColorSchemeKt.LocalColorScheme)).primary,
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
                    ((Typography) composerImpl2.consume(TypographyKt.LocalTypography)).labelMedium,
                    composerImpl,
                    (i2 & 14) | 48,
                    0,
                    65528);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.ui.CategoryKt$CategoryTitle$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            CategoryKt.CategoryTitle(
                                    title,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
