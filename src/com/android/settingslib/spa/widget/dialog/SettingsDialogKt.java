package com.android.settingslib.spa.widget.dialog;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScope;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.material3.AlertDialogDefaults;
import androidx.compose.material3.CardDefaults;
import androidx.compose.material3.CardKt;
import androidx.compose.material3.ColorSchemeKt;
import androidx.compose.material3.tokens.ColorSchemeKeyTokens;
import androidx.compose.material3.tokens.DialogTokens;
import androidx.compose.material3.tokens.ShapeKeyTokens;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.window.AndroidDialog_androidKt;

import com.android.settingslib.spa.framework.theme.SettingsDimension;
import com.android.settingslib.spa.framework.theme.SettingsShape;
import com.android.settingslib.spa.widget.ui.TextKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SettingsDialogKt {
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.settingslib.spa.widget.dialog.SettingsDialogKt$SettingsDialog$1, kotlin.jvm.internal.Lambda] */
    public static final void SettingsDialog(
            final String title,
            final Function0 onDismissRequest,
            final Function2 content,
            Composer composer,
            final int i) {
        int i2;
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(onDismissRequest, "onDismissRequest");
        Intrinsics.checkNotNullParameter(content, "content");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1887370283);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(title) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl.changedInstance(onDismissRequest) ? 32 : 16;
        }
        if ((i & 896) == 0) {
            i2 |= composerImpl.changedInstance(content) ? 256 : 128;
        }
        if ((i2 & 731) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            AndroidDialog_androidKt.Dialog(
                    onDismissRequest,
                    null,
                    ComposableLambdaKt.rememberComposableLambda(
                            -894232660,
                            new Function2() { // from class:
                                              // com.android.settingslib.spa.widget.dialog.SettingsDialogKt$SettingsDialog$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(2);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj, Object obj2) {
                                    Composer composer2 = (Composer) obj;
                                    if ((((Number) obj2).intValue() & 11) == 2) {
                                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                        if (composerImpl2.getSkipping()) {
                                            composerImpl2.skipToGroupEnd();
                                            return Unit.INSTANCE;
                                        }
                                    }
                                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                    SettingsDialogKt.SettingsDialogCard(
                                            title, content, composer2, 0);
                                    return Unit.INSTANCE;
                                }
                            },
                            composerImpl),
                    composerImpl,
                    ((i2 >> 3) & 14) | 384,
                    2);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.dialog.SettingsDialogKt$SettingsDialog$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SettingsDialogKt.SettingsDialog(
                                    title,
                                    onDismissRequest,
                                    content,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    /* JADX WARN: Type inference failed for: r0v13, types: [com.android.settingslib.spa.widget.dialog.SettingsDialogKt$SettingsDialogCard$1, kotlin.jvm.internal.Lambda] */
    public static final void SettingsDialogCard(
            final String title, final Function2 content, Composer composer, final int i) {
        int i2;
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(content, "content");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1278492519);
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
            RoundedCornerShape roundedCornerShape = SettingsShape.CornerExtraLarge;
            float f = AlertDialogDefaults.TonalElevation;
            ShapeKeyTokens shapeKeyTokens = DialogTokens.ContainerShape;
            CardKt.Card(
                    null,
                    roundedCornerShape,
                    CardDefaults.m178cardColorsro_MJ88(
                            ColorSchemeKt.getValue(
                                    ColorSchemeKeyTokens.SurfaceContainerHigh, composerImpl),
                            composerImpl,
                            0),
                    null,
                    null,
                    ComposableLambdaKt.rememberComposableLambda(
                            188309003,
                            new Function3() { // from class:
                                              // com.android.settingslib.spa.widget.dialog.SettingsDialogKt$SettingsDialogCard$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(3);
                                }

                                @Override // kotlin.jvm.functions.Function3
                                public final Object invoke(Object obj, Object obj2, Object obj3) {
                                    ColumnScope Card = (ColumnScope) obj;
                                    Composer composer2 = (Composer) obj2;
                                    int intValue = ((Number) obj3).intValue();
                                    Intrinsics.checkNotNullParameter(Card, "$this$Card");
                                    if ((intValue & 81) == 16) {
                                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                        if (composerImpl2.getSkipping()) {
                                            composerImpl2.skipToGroupEnd();
                                            return Unit.INSTANCE;
                                        }
                                    }
                                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                    Modifier.Companion companion = Modifier.Companion.$$INSTANCE;
                                    Modifier m87paddingVpY3zN4$default =
                                            PaddingKt.m87paddingVpY3zN4$default(
                                                    companion,
                                                    0.0f,
                                                    SettingsDimension.itemPaddingAround,
                                                    1);
                                    Function2 function2 = content;
                                    String str = title;
                                    ColumnMeasurePolicy columnMeasurePolicy =
                                            ColumnKt.columnMeasurePolicy(
                                                    Arrangement.Top,
                                                    Alignment.Companion.Start,
                                                    composer2,
                                                    0);
                                    int currentCompositeKeyHash =
                                            ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                    PersistentCompositionLocalMap currentCompositionLocalScope =
                                            composerImpl3.currentCompositionLocalScope();
                                    Modifier materializeModifier =
                                            ComposedModifierKt.materializeModifier(
                                                    composer2, m87paddingVpY3zN4$default);
                                    ComposeUiNode.Companion.getClass();
                                    Function0 function0 = ComposeUiNode.Companion.Constructor;
                                    boolean z = composerImpl3.applier instanceof Applier;
                                    if (!z) {
                                        ComposablesKt.invalidApplier();
                                        throw null;
                                    }
                                    composerImpl3.startReusableNode();
                                    if (composerImpl3.inserting) {
                                        composerImpl3.createNode(function0);
                                    } else {
                                        composerImpl3.useNode();
                                    }
                                    Function2 function22 = ComposeUiNode.Companion.SetMeasurePolicy;
                                    Updater.m221setimpl(composer2, columnMeasurePolicy, function22);
                                    Function2 function23 =
                                            ComposeUiNode.Companion.SetResolvedCompositionLocals;
                                    Updater.m221setimpl(
                                            composer2, currentCompositionLocalScope, function23);
                                    Function2 function24 =
                                            ComposeUiNode.Companion.SetCompositeKeyHash;
                                    if (composerImpl3.inserting
                                            || !Intrinsics.areEqual(
                                                    composerImpl3.rememberedValue(),
                                                    Integer.valueOf(currentCompositeKeyHash))) {
                                        AnimatedContentKt$$ExternalSyntheticOutline0.m(
                                                currentCompositeKeyHash,
                                                composerImpl3,
                                                currentCompositeKeyHash,
                                                function24);
                                    }
                                    Function2 function25 = ComposeUiNode.Companion.SetModifier;
                                    Updater.m221setimpl(composer2, materializeModifier, function25);
                                    Modifier padding =
                                            PaddingKt.padding(
                                                    companion, SettingsDimension.dialogItemPadding);
                                    MeasurePolicy maybeCachedBoxMeasurePolicy =
                                            BoxKt.maybeCachedBoxMeasurePolicy(
                                                    Alignment.Companion.TopStart, false);
                                    int currentCompositeKeyHash2 =
                                            ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                    PersistentCompositionLocalMap currentCompositionLocalScope2 =
                                            composerImpl3.currentCompositionLocalScope();
                                    Modifier materializeModifier2 =
                                            ComposedModifierKt.materializeModifier(
                                                    composer2, padding);
                                    if (!z) {
                                        ComposablesKt.invalidApplier();
                                        throw null;
                                    }
                                    composerImpl3.startReusableNode();
                                    if (composerImpl3.inserting) {
                                        composerImpl3.createNode(function0);
                                    } else {
                                        composerImpl3.useNode();
                                    }
                                    Updater.m221setimpl(
                                            composer2, maybeCachedBoxMeasurePolicy, function22);
                                    Updater.m221setimpl(
                                            composer2, currentCompositionLocalScope2, function23);
                                    if (composerImpl3.inserting
                                            || !Intrinsics.areEqual(
                                                    composerImpl3.rememberedValue(),
                                                    Integer.valueOf(currentCompositeKeyHash2))) {
                                        AnimatedContentKt$$ExternalSyntheticOutline0.m(
                                                currentCompositeKeyHash2,
                                                composerImpl3,
                                                currentCompositeKeyHash2,
                                                function24);
                                    }
                                    Updater.m221setimpl(
                                            composer2, materializeModifier2, function25);
                                    TextKt.SettingsTitle(str, null, true, composer2, 384, 2);
                                    composerImpl3.end(true);
                                    function2.invoke(composer2, 0);
                                    composerImpl3.end(true);
                                    return Unit.INSTANCE;
                                }
                            },
                            composerImpl),
                    composerImpl,
                    196656,
                    25);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.dialog.SettingsDialogKt$SettingsDialogCard$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SettingsDialogKt.SettingsDialogCard(
                                    title,
                                    content,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
