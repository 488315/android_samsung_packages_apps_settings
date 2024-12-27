package com.android.settingslib.spa.widget.ui;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.ColumnScope;
import androidx.compose.foundation.layout.FillElement;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.AndroidMenu_androidKt;
import androidx.compose.material3.ColorScheme;
import androidx.compose.material3.ColorSchemeKt;
import androidx.compose.material3.MenuDefaults;
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
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidClipboardManager;
import androidx.compose.ui.platform.ClipboardManager;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.unit.DpOffset;

import com.android.settingslib.spa.framework.theme.SettingsDimension;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class CopyableBodyKt {
    /* JADX WARN: Type inference failed for: r4v5, types: [com.android.settingslib.spa.widget.ui.CopyableBodyKt$CopyableBody$2$2, kotlin.jvm.internal.Lambda] */
    public static final void CopyableBody(final String body, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl;
        Intrinsics.checkNotNullParameter(body, "body");
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1873747931);
        if ((i & 14) == 0) {
            i2 = (composerImpl2.changed(body) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            composerImpl2.startReplaceGroup(2140153645);
            Object rememberedValue = composerImpl2.rememberedValue();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            StructuralEqualityPolicy structuralEqualityPolicy = StructuralEqualityPolicy.INSTANCE;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue =
                        SnapshotStateKt.mutableStateOf(Boolean.FALSE, structuralEqualityPolicy);
                composerImpl2.updateRememberedValue(rememberedValue);
            }
            final MutableState mutableState = (MutableState) rememberedValue;
            composerImpl2.end(false);
            composerImpl2.startReplaceGroup(2140153700);
            Object rememberedValue2 = composerImpl2.rememberedValue();
            if (rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 =
                        SnapshotStateKt.mutableStateOf(
                                new DpOffset(9205357640488583168L), structuralEqualityPolicy);
                composerImpl2.updateRememberedValue(rememberedValue2);
            }
            MutableState mutableState2 = (MutableState) rememberedValue2;
            composerImpl2.end(false);
            FillElement fillElement = SizeKt.FillWholeMaxWidth;
            Unit unit = Unit.INSTANCE;
            composerImpl2.startReplaceGroup(2140153831);
            Object rememberedValue3 = composerImpl2.rememberedValue();
            if (rememberedValue3 == composer$Companion$Empty$1) {
                rememberedValue3 =
                        new CopyableBodyKt$CopyableBody$1$1(mutableState2, mutableState, null);
                composerImpl2.updateRememberedValue(rememberedValue3);
            }
            composerImpl2.end(false);
            Modifier pointerInput =
                    SuspendingPointerInputFilterKt.pointerInput(
                            fillElement, unit, (Function2) rememberedValue3);
            MeasurePolicy maybeCachedBoxMeasurePolicy =
                    BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int i3 = composerImpl2.compoundKeyHash;
            PersistentCompositionLocalMap currentCompositionLocalScope =
                    composerImpl2.currentCompositionLocalScope();
            Modifier materializeModifier =
                    ComposedModifierKt.materializeModifier(composerImpl2, pointerInput);
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
                    composerImpl2,
                    maybeCachedBoxMeasurePolicy,
                    ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m221setimpl(
                    composerImpl2,
                    currentCompositionLocalScope,
                    ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl2.inserting
                    || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(i3))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(i3, composerImpl2, i3, function2);
            }
            Updater.m221setimpl(
                    composerImpl2, materializeModifier, ComposeUiNode.Companion.SetModifier);
            TextKt.SettingsBody(body, null, 0, composerImpl2, i2 & 14, 6);
            boolean booleanValue = ((Boolean) mutableState.getValue()).booleanValue();
            composerImpl2.startReplaceGroup(1727039444);
            Object rememberedValue4 = composerImpl2.rememberedValue();
            if (rememberedValue4 == composer$Companion$Empty$1) {
                rememberedValue4 =
                        new Function0() { // from class:
                                          // com.android.settingslib.spa.widget.ui.CopyableBodyKt$CopyableBody$2$1$1
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                MutableState.this.setValue(Boolean.FALSE);
                                return Unit.INSTANCE;
                            }
                        };
                composerImpl2.updateRememberedValue(rememberedValue4);
            }
            composerImpl2.end(false);
            composerImpl = composerImpl2;
            AndroidMenu_androidKt.m176DropdownMenuIlH_yew(
                    booleanValue,
                    (Function0) rememberedValue4,
                    null,
                    ((DpOffset) mutableState2.getValue()).packedValue,
                    null,
                    null,
                    null,
                    0L,
                    0.0f,
                    0.0f,
                    null,
                    ComposableLambdaKt.rememberComposableLambda(
                            1819362512,
                            new Function3() { // from class:
                                              // com.android.settingslib.spa.widget.ui.CopyableBodyKt$CopyableBody$2$2
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(3);
                                }

                                @Override // kotlin.jvm.functions.Function3
                                public final Object invoke(Object obj, Object obj2, Object obj3) {
                                    ColumnScope DropdownMenu = (ColumnScope) obj;
                                    Composer composer2 = (Composer) obj2;
                                    int intValue = ((Number) obj3).intValue();
                                    Intrinsics.checkNotNullParameter(
                                            DropdownMenu, "$this$DropdownMenu");
                                    if ((intValue & 81) == 16) {
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                        if (composerImpl3.getSkipping()) {
                                            composerImpl3.skipToGroupEnd();
                                            return Unit.INSTANCE;
                                        }
                                    }
                                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                    CopyableBodyKt.access$DropdownMenuTitle(body, composer2, 0);
                                    String str = body;
                                    ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                    composerImpl4.startReplaceGroup(1528495485);
                                    final MutableState mutableState3 = mutableState;
                                    Object rememberedValue5 = composerImpl4.rememberedValue();
                                    if (rememberedValue5 == Composer.Companion.Empty) {
                                        rememberedValue5 =
                                                new Function0() { // from class:
                                                                  // com.android.settingslib.spa.widget.ui.CopyableBodyKt$CopyableBody$2$2$1$1
                                                    {
                                                        super(0);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function0
                                                    /* renamed from: invoke */
                                                    public final Object mo1068invoke() {
                                                        MutableState.this.setValue(Boolean.FALSE);
                                                        return Unit.INSTANCE;
                                                    }
                                                };
                                        composerImpl4.updateRememberedValue(rememberedValue5);
                                    }
                                    composerImpl4.end(false);
                                    CopyableBodyKt.access$DropdownMenuCopy(
                                            str, (Function0) rememberedValue5, composerImpl4, 48);
                                    return Unit.INSTANCE;
                                }
                            },
                            composerImpl2),
                    composerImpl,
                    48,
                    48,
                    2036);
            composerImpl.end(true);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.ui.CopyableBodyKt$CopyableBody$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            CopyableBodyKt.CopyableBody(
                                    body,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public static final void access$DropdownMenuCopy(
            final String str, final Function0 function0, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-850836067);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(str) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl.changedInstance(function0) ? 32 : 16;
        }
        if ((i2 & 91) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            final ClipboardManager clipboardManager =
                    (ClipboardManager)
                            composerImpl.consume(CompositionLocalsKt.LocalClipboardManager);
            AndroidMenu_androidKt.DropdownMenuItem(
                    ComposableSingletons$CopyableBodyKt.f100lambda1,
                    new Function0() { // from class:
                                      // com.android.settingslib.spa.widget.ui.CopyableBodyKt$DropdownMenuCopy$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            Function0.this.mo1068invoke();
                            ((AndroidClipboardManager) clipboardManager)
                                    .setText(new AnnotatedString(str, null, 6));
                            return Unit.INSTANCE;
                        }
                    },
                    null,
                    null,
                    null,
                    false,
                    null,
                    null,
                    null,
                    composerImpl,
                    6,
                    FileType.VTS);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.ui.CopyableBodyKt$DropdownMenuCopy$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            CopyableBodyKt.access$DropdownMenuCopy(
                                    str,
                                    function0,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public static final void access$DropdownMenuTitle(
            final String str, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-2032913078);
        if ((i & 14) == 0) {
            i2 = (composerImpl2.changed(str) ? 4 : 2) | i;
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
                    str,
                    PaddingKt.m89paddingqDBjuR0$default(
                            PaddingKt.padding(
                                    Modifier.Companion.$$INSTANCE,
                                    MenuDefaults.DropdownMenuItemContentPadding),
                            0.0f,
                            SettingsDimension.itemPaddingAround,
                            0.0f,
                            SettingsDimension.buttonPaddingVertical,
                            5),
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
                    i2 & 14,
                    0,
                    65528);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.ui.CopyableBodyKt$DropdownMenuTitle$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            CopyableBodyKt.access$DropdownMenuTitle(
                                    str,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
