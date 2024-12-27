package com.android.settingslib.spa.widget.card;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.AnimatedVisibilityKt;
import androidx.compose.animation.AnimatedVisibilityScope;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$End$1;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScope;
import androidx.compose.foundation.layout.FillElement;
import androidx.compose.foundation.layout.FlowLayoutKt;
import androidx.compose.foundation.layout.FlowRowScope;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowScope;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.ButtonKt;
import androidx.compose.material3.CardDefaults;
import androidx.compose.material3.CardKt;
import androidx.compose.material3.ColorScheme;
import androidx.compose.material3.ColorSchemeKt;
import androidx.compose.material3.IconButtonKt;
import androidx.compose.material3.IconKt;
import androidx.compose.material3.SurfaceKt;
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
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.node.ComposeUiNode;
import com.android.settingslib.spa.framework.compose.ModifierExtKt;
import com.android.settingslib.spa.framework.theme.SettingsDimension;
import com.android.settingslib.spa.framework.theme.SettingsShape;
import com.android.settingslib.spa.widget.ui.TextKt;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SettingsCardKt {
    /* JADX WARN: Removed duplicated region for block: B:24:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x012a  */
    /* renamed from: CardHeader-3IgeMak, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m1035CardHeader3IgeMak(final androidx.compose.ui.graphics.vector.ImageVector r15, final long r16, kotlin.jvm.functions.Function0 r18, androidx.compose.runtime.Composer r19, final int r20, final int r21) {
        /*
            Method dump skipped, instructions count: 302
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.spa.widget.card.SettingsCardKt.m1035CardHeader3IgeMak(androidx.compose.ui.graphics.vector.ImageVector, long, kotlin.jvm.functions.Function0, androidx.compose.runtime.Composer, int, int):void");
    }

    /* renamed from: CardIcon-RPmYEkk, reason: not valid java name */
    public static final void m1036CardIconRPmYEkk(final ImageVector imageVector, final long j, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1402717039);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(imageVector) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl.changed(j) ? 32 : 16;
        }
        if ((i2 & 91) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            if (imageVector != null) {
                Modifier m96size3ABfNKs = SizeKt.m96size3ABfNKs(Modifier.Companion.$$INSTANCE, SettingsDimension.itemIconSize);
                composerImpl.startReplaceGroup(1890347988);
                long j2 = j != 16 ? j : ((ColorScheme) composerImpl.consume(ColorSchemeKt.LocalColorScheme)).primary;
                composerImpl.end(false);
                IconKt.m188Iconww6aTOc(imageVector, (String) null, m96size3ABfNKs, j2, composerImpl, (i2 & 14) | FileType.CRT, 0);
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.settingslib.spa.widget.card.SettingsCardKt$CardIcon$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    SettingsCardKt.m1036CardIconRPmYEkk(ImageVector.this, j, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Type inference failed for: r0v9, types: [com.android.settingslib.spa.widget.card.SettingsCardKt$DismissButton$2, kotlin.jvm.internal.Lambda] */
    public static final void DismissButton(final Function0 function0, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(91699755);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changedInstance(function0) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            if (function0 == null) {
                RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
                if (endRestartGroup != null) {
                    endRestartGroup.block = new Function2() { // from class: com.android.settingslib.spa.widget.card.SettingsCardKt$DismissButton$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SettingsCardKt.DismissButton(function0, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            SurfaceKt.m198SurfaceT9BRK9s(null, RoundedCornerShapeKt.CircleShape, ((ColorScheme) composerImpl.consume(ColorSchemeKt.LocalColorScheme)).secondaryContainer, 0L, 0.0f, 0.0f, null, ComposableLambdaKt.rememberComposableLambda(401455174, new Function2() { // from class: com.android.settingslib.spa.widget.card.SettingsCardKt$DismissButton$2
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
                    IconButtonKt.IconButton(Function0.this, SizeKt.m96size3ABfNKs(Modifier.Companion.$$INSTANCE, SettingsDimension.itemIconSize), false, null, null, ComposableSingletons$SettingsCardKt.f73lambda1, composer2, 196656, 28);
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 12582912, 121);
        }
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            endRestartGroup2.block = new Function2() { // from class: com.android.settingslib.spa.widget.card.SettingsCardKt$DismissButton$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    SettingsCardKt.DismissButton(function0, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void SettingsCard(final Function3 content, Composer composer, final int i) {
        int i2;
        Intrinsics.checkNotNullParameter(content, "content");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-299046785);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changedInstance(content) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            CardKt.Card(PaddingKt.m86paddingVpY3zN4(SizeKt.FillWholeMaxWidth, SettingsDimension.itemPaddingEnd, SettingsDimension.itemPaddingAround), SettingsShape.CornerExtraLarge, CardDefaults.m178cardColorsro_MJ88(Color.Transparent, composerImpl, 6), null, null, content, composerImpl, ((i2 << 15) & 458752) | 54, 24);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.settingslib.spa.widget.card.SettingsCardKt$SettingsCard$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    SettingsCardKt.SettingsCard(Function3.this, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* renamed from: SettingsCardContent-3J-VO9M, reason: not valid java name */
    public static final void m1037SettingsCardContent3JVO9M(long j, final Function3 content, Composer composer, final int i, final int i2) {
        long j2;
        int i3;
        final long j3;
        Intrinsics.checkNotNullParameter(content, "content");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(572857421);
        int i4 = i2 & 1;
        if (i4 != 0) {
            i3 = i | 6;
            j2 = j;
        } else if ((i & 14) == 0) {
            j2 = j;
            i3 = i | (composerImpl.changed(j) ? 4 : 2);
        } else {
            j2 = j;
            i3 = i;
        }
        if ((i2 & 2) != 0) {
            i3 |= 48;
        } else if ((i & 112) == 0) {
            i3 |= composerImpl.changedInstance(content) ? 32 : 16;
        }
        if ((i3 & 91) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            j3 = j2;
        } else {
            long j4 = i4 != 0 ? Color.Unspecified : j2;
            OpaqueKey opaqueKey = ComposerKt.invocation;
            RoundedCornerShape roundedCornerShape = SettingsShape.CornerExtraSmall;
            composerImpl.startReplaceGroup(381801425);
            long j5 = j4 != 16 ? j4 : ((ColorScheme) composerImpl.consume(ColorSchemeKt.LocalColorScheme)).surface;
            composerImpl.end(false);
            CardKt.Card(PaddingKt.m87paddingVpY3zN4$default(SizeKt.FillWholeMaxWidth, 0.0f, 1, 1), roundedCornerShape, CardDefaults.m178cardColorsro_MJ88(j5, composerImpl, 0), null, null, content, composerImpl, ((i3 << 12) & 458752) | 54, 24);
            j3 = j4;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.settingslib.spa.widget.card.SettingsCardKt$SettingsCardContent$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    SettingsCardKt.m1037SettingsCardContent3JVO9M(j3, content, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.settingslib.spa.widget.card.SettingsCardKt$SettingsCardImpl$1, kotlin.jvm.internal.Lambda] */
    public static final void SettingsCardImpl(final CardModel model, Composer composer, final int i) {
        Intrinsics.checkNotNullParameter(model, "model");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1730089183);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        AnimatedVisibilityKt.AnimatedVisibility(((Boolean) model.isVisible.mo1068invoke()).booleanValue(), null, null, null, null, ComposableLambdaKt.rememberComposableLambda(-197378809, new Function3() { // from class: com.android.settingslib.spa.widget.card.SettingsCardKt$SettingsCardImpl$1
            {
                super(3);
            }

            /* JADX WARN: Type inference failed for: r7v3, types: [com.android.settingslib.spa.widget.card.SettingsCardKt$SettingsCardImpl$1$1, kotlin.jvm.internal.Lambda] */
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                AnimatedVisibilityScope AnimatedVisibility = (AnimatedVisibilityScope) obj;
                Composer composer2 = (Composer) obj2;
                ((Number) obj3).intValue();
                Intrinsics.checkNotNullParameter(AnimatedVisibility, "$this$AnimatedVisibility");
                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                final CardModel cardModel = CardModel.this;
                SettingsCardKt.m1037SettingsCardContent3JVO9M(cardModel.containerColor, ComposableLambdaKt.rememberComposableLambda(928466621, new Function3() { // from class: com.android.settingslib.spa.widget.card.SettingsCardKt$SettingsCardImpl$1.1
                    {
                        super(3);
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX WARN: Type inference failed for: r11v11, types: [androidx.compose.ui.Modifier] */
                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj4, Object obj5, Object obj6) {
                        ?? m34clickableXHw0xAI$default;
                        ColumnScope SettingsCardContent = (ColumnScope) obj4;
                        Composer composer3 = (Composer) obj5;
                        int intValue = ((Number) obj6).intValue();
                        Intrinsics.checkNotNullParameter(SettingsCardContent, "$this$SettingsCardContent");
                        if ((intValue & 81) == 16) {
                            ComposerImpl composerImpl2 = (ComposerImpl) composer3;
                            if (composerImpl2.getSkipping()) {
                                composerImpl2.skipToGroupEnd();
                                return Unit.INSTANCE;
                            }
                        }
                        OpaqueKey opaqueKey3 = ComposerKt.invocation;
                        Function0 function0 = CardModel.this.onClick;
                        Modifier.Companion companion = Modifier.Companion.$$INSTANCE;
                        if (function0 != null && (m34clickableXHw0xAI$default = ClickableKt.m34clickableXHw0xAI$default(companion, false, null, null, function0, 7)) != 0) {
                            companion = m34clickableXHw0xAI$default;
                        }
                        float f = SettingsDimension.dialogItemPaddingHorizontal;
                        float f2 = SettingsDimension.itemPaddingAround;
                        Modifier m86paddingVpY3zN4 = PaddingKt.m86paddingVpY3zN4(companion, f, f2);
                        Arrangement.SpacedAligned m66spacedBy0680j_4 = Arrangement.m66spacedBy0680j_4(f2);
                        CardModel cardModel2 = CardModel.this;
                        ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(m66spacedBy0680j_4, Alignment.Companion.Start, composer3, 6);
                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer3);
                        ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                        PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                        Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer3, m86paddingVpY3zN4);
                        ComposeUiNode.Companion.getClass();
                        Function0 function02 = ComposeUiNode.Companion.Constructor;
                        if (!(composerImpl3.applier instanceof Applier)) {
                            ComposablesKt.invalidApplier();
                            throw null;
                        }
                        composerImpl3.startReusableNode();
                        if (composerImpl3.inserting) {
                            composerImpl3.createNode(function02);
                        } else {
                            composerImpl3.useNode();
                        }
                        Updater.m221setimpl(composer3, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                        Updater.m221setimpl(composer3, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                        if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                        }
                        Updater.m221setimpl(composer3, materializeModifier, ComposeUiNode.Companion.SetModifier);
                        SettingsCardKt.m1035CardHeader3IgeMak(cardModel2.imageVector, cardModel2.tintColor, cardModel2.onDismiss, composer3, 0, 0);
                        TextKt.SettingsTitle(cardModel2.title, null, false, composer3, 0, 6);
                        TextKt.SettingsBody(cardModel2.text, null, 0, composer3, 0, 6);
                        SettingsCardKt.m1039access$ButtonsRPmYEkk(cardModel2.buttons, cardModel2.tintColor, composer3, 8);
                        composerImpl3.end(true);
                        return Unit.INSTANCE;
                    }
                }, composer2), composer2, 48, 0);
                return Unit.INSTANCE;
            }
        }, composerImpl), composerImpl, 196608, 30);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.settingslib.spa.widget.card.SettingsCardKt$SettingsCardImpl$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    SettingsCardKt.SettingsCardImpl(CardModel.this, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Type inference failed for: r6v1, types: [com.android.settingslib.spa.widget.card.SettingsCardKt$Button$1, kotlin.jvm.internal.Lambda] */
    /* renamed from: access$Button-RPmYEkk, reason: not valid java name */
    public static final void m1038access$ButtonRPmYEkk(final CardButton cardButton, final long j, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1365641038);
        if ((i & 14) == 0) {
            i2 = (composerImpl2.changed(cardButton) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl2.changed(j) ? 32 : 16;
        }
        if ((i2 & 91) == 18 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            composerImpl = composerImpl2;
            ButtonKt.TextButton(cardButton.onClick, ModifierExtKt.contentDescription(Modifier.Companion.$$INSTANCE, cardButton.contentDescription), false, null, null, null, null, null, null, ComposableLambdaKt.rememberComposableLambda(-900538101, new Function3() { // from class: com.android.settingslib.spa.widget.card.SettingsCardKt$Button$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    RowScope TextButton = (RowScope) obj;
                    Composer composer2 = (Composer) obj2;
                    int intValue = ((Number) obj3).intValue();
                    Intrinsics.checkNotNullParameter(TextButton, "$this$TextButton");
                    if ((intValue & 81) == 16) {
                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                        if (composerImpl3.getSkipping()) {
                            composerImpl3.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                    androidx.compose.material3.TextKt.m210Text4IGK_g(CardButton.this.text, null, j, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer2, 0, 0, 131066);
                    return Unit.INSTANCE;
                }
            }, composerImpl2), composerImpl2, 805306368, FileType.VTS);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.settingslib.spa.widget.card.SettingsCardKt$Button$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    SettingsCardKt.m1038access$ButtonRPmYEkk(CardButton.this, j, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.settingslib.spa.widget.card.SettingsCardKt$Buttons$1, kotlin.jvm.internal.Lambda] */
    /* renamed from: access$Buttons-RPmYEkk, reason: not valid java name */
    public static final void m1039access$ButtonsRPmYEkk(final List list, final long j, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-185190345);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        if (!list.isEmpty()) {
            composerImpl.startReplaceGroup(762671784);
            FillElement fillElement = SizeKt.FillWholeMaxWidth;
            Arrangement$End$1 arrangement$End$1 = Arrangement.Start;
            float f = SettingsDimension.paddingTiny;
            FlowLayoutKt.FlowRow(fillElement, Arrangement.m67spacedByD5KLDUw(), null, 0, 0, null, ComposableLambdaKt.rememberComposableLambda(-1805215305, new Function3() { // from class: com.android.settingslib.spa.widget.card.SettingsCardKt$Buttons$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    FlowRowScope FlowRow = (FlowRowScope) obj;
                    Composer composer2 = (Composer) obj2;
                    int intValue = ((Number) obj3).intValue();
                    Intrinsics.checkNotNullParameter(FlowRow, "$this$FlowRow");
                    if ((intValue & 81) == 16) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                    Iterator<CardButton> it = list.iterator();
                    while (it.hasNext()) {
                        SettingsCardKt.m1038access$ButtonRPmYEkk(it.next(), j, composer2, 0);
                    }
                    OpaqueKey opaqueKey3 = ComposerKt.invocation;
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 1572918, 60);
            composerImpl.end(false);
        } else {
            composerImpl.startReplaceGroup(762672148);
            SpacerKt.Spacer(composerImpl, SizeKt.m91height3ABfNKs(Modifier.Companion.$$INSTANCE, SettingsDimension.itemPaddingAround));
            composerImpl.end(false);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.settingslib.spa.widget.card.SettingsCardKt$Buttons$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    SettingsCardKt.m1039access$ButtonsRPmYEkk(list, j, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.settingslib.spa.widget.card.SettingsCardKt$SettingsCard$2, kotlin.jvm.internal.Lambda] */
    public static final void SettingsCard(final CardModel model, Composer composer, final int i) {
        Intrinsics.checkNotNullParameter(model, "model");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(569729823);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        SettingsCard(ComposableLambdaKt.rememberComposableLambda(-844581329, new Function3() { // from class: com.android.settingslib.spa.widget.card.SettingsCardKt$SettingsCard$2
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                ColumnScope SettingsCard = (ColumnScope) obj;
                Composer composer2 = (Composer) obj2;
                int intValue = ((Number) obj3).intValue();
                Intrinsics.checkNotNullParameter(SettingsCard, "$this$SettingsCard");
                if ((intValue & 81) == 16) {
                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                    if (composerImpl2.getSkipping()) {
                        composerImpl2.skipToGroupEnd();
                        return Unit.INSTANCE;
                    }
                }
                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                SettingsCardKt.SettingsCardImpl(CardModel.this, composer2, 8);
                return Unit.INSTANCE;
            }
        }, composerImpl), composerImpl, 6);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.settingslib.spa.widget.card.SettingsCardKt$SettingsCard$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    SettingsCardKt.SettingsCard(CardModel.this, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
