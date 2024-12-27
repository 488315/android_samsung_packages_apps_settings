package com.android.settingslib.spa.framework.util;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.SingleValueAnimationKt;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.RepeatMode;
import androidx.compose.animation.core.RepeatableSpec;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.text.selection.SimpleLayoutKt$$ExternalSyntheticOutline0;
import androidx.compose.material3.ColorScheme;
import androidx.compose.material3.ColorSchemeKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.StructuralEqualityPolicy;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.saveable.RememberSaveableKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;

import com.android.settingslib.spa.framework.common.EntryData;
import com.android.settingslib.spa.framework.common.SettingsEntryKt;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class EntryHighlightKt {
    public static final void EntryHighlight(
            final Function2 content, Composer composer, final int i) {
        int i2;
        RepeatMode repeatMode = RepeatMode.Restart;
        Intrinsics.checkNotNullParameter(content, "content");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-289395396);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changedInstance(content) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            final EntryData entryData =
                    (EntryData) composerImpl.consume(SettingsEntryKt.LocalEntryDataProvider);
            final boolean booleanValue =
                    ((Boolean)
                                    RememberSaveableKt.rememberSaveable(
                                            new Object[0],
                                            null,
                                            new Function0() { // from class:
                                                              // com.android.settingslib.spa.framework.util.EntryHighlightKt$EntryHighlight$entryIsHighlighted$1
                                                {
                                                    super(0);
                                                }

                                                @Override // kotlin.jvm.functions.Function0
                                                /* renamed from: invoke */
                                                public final Object mo1068invoke() {
                                                    return Boolean.valueOf(
                                                            EntryData.this.isHighlighted());
                                                }
                                            },
                                            composerImpl,
                                            8,
                                            6))
                            .booleanValue();
            final MutableState mutableState =
                    (MutableState)
                            RememberSaveableKt.rememberSaveable(
                                    new Object[0],
                                    null,
                                    new Function0() { // from class:
                                                      // com.android.settingslib.spa.framework.util.EntryHighlightKt$EntryHighlight$localHighlighted$2
                                        @Override // kotlin.jvm.functions.Function0
                                        /* renamed from: invoke */
                                        public final Object mo1068invoke() {
                                            return SnapshotStateKt.mutableStateOf(
                                                    Boolean.FALSE,
                                                    StructuralEqualityPolicy.INSTANCE);
                                        }
                                    },
                                    composerImpl,
                                    3080,
                                    6);
            composerImpl.startReplaceGroup(473252449);
            boolean changed =
                    composerImpl.changed(mutableState) | composerImpl.changed(booleanValue);
            Object rememberedValue = composerImpl.rememberedValue();
            if (changed || rememberedValue == Composer.Companion.Empty) {
                rememberedValue =
                        new Function0() { // from class:
                                          // com.android.settingslib.spa.framework.util.EntryHighlightKt$EntryHighlight$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                mutableState.setValue(Boolean.valueOf(booleanValue));
                                return Unit.INSTANCE;
                            }
                        };
                composerImpl.updateRememberedValue(rememberedValue);
            }
            composerImpl.end(false);
            EffectsKt.SideEffect((Function0) rememberedValue, composerImpl);
            composerImpl.startReplaceGroup(473252574);
            long j =
                    ((Boolean) mutableState.getValue()).booleanValue()
                            ? ((ColorScheme) composerImpl.consume(ColorSchemeKt.LocalColorScheme))
                                    .surfaceVariant
                            : Color.Transparent;
            composerImpl.end(false);
            Modifier m29backgroundbw27NRU =
                    BackgroundKt.m29backgroundbw27NRU(
                            Modifier.Companion.$$INSTANCE,
                            ((Color)
                                            SingleValueAnimationKt.m14animateColorAsStateeuL9pac(
                                                            j,
                                                            new RepeatableSpec(
                                                                    AnimationSpecKt.tween$default(
                                                                            500, 0, null, 6),
                                                                    repeatMode,
                                                                    0),
                                                            "BackgroundColorAnimation",
                                                            composerImpl,
                                                            FileType.CRT,
                                                            8)
                                                    .getValue())
                                    .value,
                            RectangleShapeKt.RectangleShape);
            MeasurePolicy maybeCachedBoxMeasurePolicy =
                    BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int i3 = composerImpl.compoundKeyHash;
            PersistentCompositionLocalMap currentCompositionLocalScope =
                    composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier =
                    ComposedModifierKt.materializeModifier(composerImpl, m29backgroundbw27NRU);
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
                    composerImpl,
                    maybeCachedBoxMeasurePolicy,
                    ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m221setimpl(
                    composerImpl,
                    currentCompositionLocalScope,
                    ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting
                    || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i3))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(i3, composerImpl, i3, function2);
            }
            Updater.m221setimpl(
                    composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            SimpleLayoutKt$$ExternalSyntheticOutline0.m(i2 & 14, content, composerImpl, true);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.framework.util.EntryHighlightKt$EntryHighlight$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            EntryHighlightKt.EntryHighlight(
                                    content,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
