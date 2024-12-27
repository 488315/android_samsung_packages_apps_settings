package com.android.settingslib.spa.widget.button;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.interaction.MutableInteractionSourceImpl;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.IntrinsicKt;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScope;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.ButtonColors;
import androidx.compose.material3.ButtonDefaults;
import androidx.compose.material3.ButtonKt;
import androidx.compose.material3.ColorScheme;
import androidx.compose.material3.ColorSchemeKt;
import androidx.compose.material3.IconKt;
import androidx.compose.material3.TextKt;
import androidx.compose.material3.Typography;
import androidx.compose.material3.TypographyKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.graphics.RectangleShapeKt$RectangleShape$1;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.text.style.TextAlign;

import com.android.settingslib.spa.framework.theme.SettingsDimension;
import com.android.settingslib.spa.framework.theme.SettingsShape;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ActionButtonsKt {
    /* JADX WARN: Type inference failed for: r3v15, types: [com.android.settingslib.spa.widget.button.ActionButtonsKt$ActionButton$2, kotlin.jvm.internal.Lambda] */
    public static final void ActionButton(
            final RowScope rowScope,
            final ActionButton actionButton,
            Composer composer,
            final int i) {
        int i2;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-347225533);
        if ((i & 14) == 0) {
            i2 = (composerImpl2.changed(rowScope) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl2.changed(actionButton) ? 32 : 16;
        }
        if ((i2 & 91) == 18 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Function0 function0 = actionButton.onClick;
            Modifier then =
                    rowScope.weight(Modifier.Companion.$$INSTANCE, true)
                            .then(SizeKt.FillWholeMaxHeight);
            composerImpl2.startReplaceGroup(339944583);
            boolean z = (i2 & 112) == 32;
            Object rememberedValue = composerImpl2.rememberedValue();
            if (z || rememberedValue == Composer.Companion.Empty) {
                rememberedValue = new MutableInteractionSourceImpl();
                composerImpl2.updateRememberedValue(rememberedValue);
            }
            MutableInteractionSource mutableInteractionSource =
                    (MutableInteractionSource) rememberedValue;
            composerImpl2.end(false);
            RectangleShapeKt$RectangleShape$1 rectangleShapeKt$RectangleShape$1 =
                    RectangleShapeKt.RectangleShape;
            PaddingValuesImpl paddingValuesImpl = ButtonDefaults.ContentPadding;
            StaticProvidableCompositionLocal staticProvidableCompositionLocal =
                    ColorSchemeKt.LocalColorScheme;
            ButtonColors m177copyjRlVdoo =
                    ButtonDefaults.getDefaultFilledTonalButtonColors$material3_release(
                                    (ColorScheme)
                                            composerImpl2.consume(staticProvidableCompositionLocal))
                            .m177copyjRlVdoo(
                                    ((ColorScheme)
                                                    composerImpl2.consume(
                                                            staticProvidableCompositionLocal))
                                            .surface,
                                    ((ColorScheme)
                                                    composerImpl2.consume(
                                                            staticProvidableCompositionLocal))
                                            .primary,
                                    ((ColorScheme)
                                                    composerImpl2.consume(
                                                            staticProvidableCompositionLocal))
                                            .surface,
                                    Color.Unspecified);
            float f = 4;
            float f2 = 20;
            composerImpl = composerImpl2;
            ButtonKt.FilledTonalButton(
                    function0,
                    then,
                    actionButton.enabled,
                    rectangleShapeKt$RectangleShape$1,
                    m177copyjRlVdoo,
                    null,
                    null,
                    new PaddingValuesImpl(f, f2, f, f2),
                    mutableInteractionSource,
                    ComposableLambdaKt.rememberComposableLambda(
                            -1396720107,
                            new Function3() { // from class:
                                              // com.android.settingslib.spa.widget.button.ActionButtonsKt$ActionButton$2
                                {
                                    super(3);
                                }

                                @Override // kotlin.jvm.functions.Function3
                                public final Object invoke(Object obj, Object obj2, Object obj3) {
                                    RowScope FilledTonalButton = (RowScope) obj;
                                    Composer composer2 = (Composer) obj2;
                                    int intValue = ((Number) obj3).intValue();
                                    Intrinsics.checkNotNullParameter(
                                            FilledTonalButton, "$this$FilledTonalButton");
                                    if ((intValue & 81) == 16) {
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                        if (composerImpl3.getSkipping()) {
                                            composerImpl3.skipToGroupEnd();
                                            return Unit.INSTANCE;
                                        }
                                    }
                                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                    BiasAlignment.Horizontal horizontal =
                                            Alignment.Companion.CenterHorizontally;
                                    ActionButton actionButton2 = ActionButton.this;
                                    Modifier.Companion companion = Modifier.Companion.$$INSTANCE;
                                    ColumnMeasurePolicy columnMeasurePolicy =
                                            ColumnKt.columnMeasurePolicy(
                                                    Arrangement.Top, horizontal, composer2, 48);
                                    int currentCompositeKeyHash =
                                            ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                    ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                    PersistentCompositionLocalMap currentCompositionLocalScope =
                                            composerImpl4.currentCompositionLocalScope();
                                    Modifier materializeModifier =
                                            ComposedModifierKt.materializeModifier(
                                                    composer2, companion);
                                    ComposeUiNode.Companion.getClass();
                                    Function0 function02 = ComposeUiNode.Companion.Constructor;
                                    boolean z2 = composerImpl4.applier instanceof Applier;
                                    if (!z2) {
                                        ComposablesKt.invalidApplier();
                                        throw null;
                                    }
                                    composerImpl4.startReusableNode();
                                    if (composerImpl4.inserting) {
                                        composerImpl4.createNode(function02);
                                    } else {
                                        composerImpl4.useNode();
                                    }
                                    Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
                                    Updater.m221setimpl(composer2, columnMeasurePolicy, function2);
                                    Function2 function22 =
                                            ComposeUiNode.Companion.SetResolvedCompositionLocals;
                                    Updater.m221setimpl(
                                            composer2, currentCompositionLocalScope, function22);
                                    Function2 function23 =
                                            ComposeUiNode.Companion.SetCompositeKeyHash;
                                    if (composerImpl4.inserting
                                            || !Intrinsics.areEqual(
                                                    composerImpl4.rememberedValue(),
                                                    Integer.valueOf(currentCompositeKeyHash))) {
                                        AnimatedContentKt$$ExternalSyntheticOutline0.m(
                                                currentCompositeKeyHash,
                                                composerImpl4,
                                                currentCompositeKeyHash,
                                                function23);
                                    }
                                    Function2 function24 = ComposeUiNode.Companion.SetModifier;
                                    Updater.m221setimpl(composer2, materializeModifier, function24);
                                    IconKt.m188Iconww6aTOc(
                                            actionButton2.imageVector,
                                            (String) null,
                                            SizeKt.m96size3ABfNKs(
                                                    companion, SettingsDimension.itemIconSize),
                                            0L,
                                            composer2,
                                            FileType.CRT,
                                            8);
                                    Modifier then2 =
                                            PaddingKt.m89paddingqDBjuR0$default(
                                                            companion, 0.0f, 4, 0.0f, 0.0f, 13)
                                                    .then(SizeKt.FillWholeMaxHeight);
                                    MeasurePolicy maybeCachedBoxMeasurePolicy =
                                            BoxKt.maybeCachedBoxMeasurePolicy(
                                                    Alignment.Companion.Center, false);
                                    int currentCompositeKeyHash2 =
                                            ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                    PersistentCompositionLocalMap currentCompositionLocalScope2 =
                                            composerImpl4.currentCompositionLocalScope();
                                    Modifier materializeModifier2 =
                                            ComposedModifierKt.materializeModifier(
                                                    composer2, then2);
                                    if (!z2) {
                                        ComposablesKt.invalidApplier();
                                        throw null;
                                    }
                                    composerImpl4.startReusableNode();
                                    if (composerImpl4.inserting) {
                                        composerImpl4.createNode(function02);
                                    } else {
                                        composerImpl4.useNode();
                                    }
                                    Updater.m221setimpl(
                                            composer2, maybeCachedBoxMeasurePolicy, function2);
                                    Updater.m221setimpl(
                                            composer2, currentCompositionLocalScope2, function22);
                                    if (composerImpl4.inserting
                                            || !Intrinsics.areEqual(
                                                    composerImpl4.rememberedValue(),
                                                    Integer.valueOf(currentCompositeKeyHash2))) {
                                        AnimatedContentKt$$ExternalSyntheticOutline0.m(
                                                currentCompositeKeyHash2,
                                                composerImpl4,
                                                currentCompositeKeyHash2,
                                                function23);
                                    }
                                    Updater.m221setimpl(
                                            composer2, materializeModifier2, function24);
                                    TextKt.m210Text4IGK_g(
                                            actionButton2.text,
                                            null,
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
                                            ((Typography)
                                                            ((ComposerImpl) composer2)
                                                                    .consume(
                                                                            TypographyKt
                                                                                    .LocalTypography))
                                                    .labelMedium,
                                            composer2,
                                            0,
                                            0,
                                            65022);
                                    composerImpl4.end(true);
                                    composerImpl4.end(true);
                                    return Unit.INSTANCE;
                                }
                            },
                            composerImpl2),
                    composerImpl2,
                    817892352,
                    96);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.button.ActionButtonsKt$ActionButton$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            ActionButtonsKt.ActionButton(
                                    RowScope.this,
                                    actionButton,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public static final void ActionButtons(
            final List actionButtons, Composer composer, final int i) {
        Intrinsics.checkNotNullParameter(actionButtons, "actionButtons");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1537200028);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Modifier height =
                IntrinsicKt.height(
                        ClipKt.clip(
                                PaddingKt.padding(
                                        Modifier.Companion.$$INSTANCE,
                                        SettingsDimension.buttonPadding),
                                SettingsShape.CornerExtraLarge));
        RowMeasurePolicy rowMeasurePolicy =
                RowKt.rowMeasurePolicy(Arrangement.Start, Alignment.Companion.Top, composerImpl, 0);
        int i2 = composerImpl.compoundKeyHash;
        PersistentCompositionLocalMap currentCompositionLocalScope =
                composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, height);
        ComposeUiNode.Companion.getClass();
        Function0 function0 = ComposeUiNode.Companion.Constructor;
        if (!(composerImpl.applier instanceof Applier)) {
            ComposablesKt.invalidApplier();
            throw null;
        }
        composerImpl.startReusableNode();
        if (composerImpl.inserting) {
            composerImpl.createNode(function0);
        } else {
            composerImpl.useNode();
        }
        Updater.m221setimpl(
                composerImpl, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
        Updater.m221setimpl(
                composerImpl,
                currentCompositionLocalScope,
                ComposeUiNode.Companion.SetResolvedCompositionLocals);
        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
        if (composerImpl.inserting
                || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i2))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i2, composerImpl, i2, function2);
        }
        Updater.m221setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
        RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
        composerImpl.startReplaceGroup(1948245716);
        Iterator it = actionButtons.iterator();
        int i3 = 0;
        while (it.hasNext()) {
            int i4 = i3 + 1;
            ActionButton actionButton = (ActionButton) it.next();
            composerImpl.startReplaceGroup(-1726948420);
            if (i3 > 0) {
                ButtonDivider(composerImpl, 0);
            }
            composerImpl.end(false);
            ActionButton(rowScopeInstance, actionButton, composerImpl, 6);
            i3 = i4;
        }
        composerImpl.end(false);
        composerImpl.end(true);
        OpaqueKey opaqueKey2 = ComposerKt.invocation;
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.button.ActionButtonsKt$ActionButtons$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            ActionButtonsKt.ActionButtons(
                                    actionButtons,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public static final void ButtonDivider(Composer composer, final int i) {
        long Color;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(94954806);
        if (i == 0 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Modifier m100width3ABfNKs = SizeKt.m100width3ABfNKs(Modifier.Companion.$$INSTANCE, 1);
            ColorScheme colorScheme =
                    (ColorScheme) composerImpl.consume(ColorSchemeKt.LocalColorScheme);
            Intrinsics.checkNotNullParameter(colorScheme, "<this>");
            Color =
                    ColorKt.Color(
                            Color.m318getRedimpl(r1),
                            Color.m317getGreenimpl(r1),
                            Color.m315getBlueimpl(r1),
                            0.2f,
                            Color.m316getColorSpaceimpl(colorScheme.onSurface));
            BoxKt.Box(
                    BackgroundKt.m29backgroundbw27NRU(
                            m100width3ABfNKs, Color, RectangleShapeKt.RectangleShape),
                    composerImpl,
                    0);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.button.ActionButtonsKt$ButtonDivider$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            ActionButtonsKt.ButtonDivider(
                                    (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
