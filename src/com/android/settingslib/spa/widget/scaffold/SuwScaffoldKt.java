package com.android.settingslib.spa.widget.scaffold;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.LayoutWeightElement;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScope;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
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
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.node.ComposeUiNode;

import com.android.settingslib.spa.framework.theme.SettingsDimension;
import com.android.settingslib.spa.framework.theme.SettingsTypographyKt;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SuwScaffoldKt {
    /* JADX WARN: Removed duplicated region for block: B:13:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0076  */
    /* JADX WARN: Type inference failed for: r12v0, types: [com.android.settingslib.spa.widget.scaffold.SuwScaffoldKt$SuwScaffold$1, kotlin.jvm.internal.Lambda] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SuwScaffold(
            final androidx.compose.ui.graphics.vector.ImageVector r27,
            final java.lang.String r28,
            com.android.settingslib.spa.widget.scaffold.BottomAppBarButton r29,
            com.android.settingslib.spa.widget.scaffold.BottomAppBarButton r30,
            final kotlin.jvm.functions.Function2 r31,
            androidx.compose.runtime.Composer r32,
            final int r33,
            final int r34) {
        /*
            Method dump skipped, instructions count: 309
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.spa.widget.scaffold.SuwScaffoldKt.SuwScaffold(androidx.compose.ui.graphics.vector.ImageVector,"
                    + " java.lang.String,"
                    + " com.android.settingslib.spa.widget.scaffold.BottomAppBarButton,"
                    + " com.android.settingslib.spa.widget.scaffold.BottomAppBarButton,"
                    + " kotlin.jvm.functions.Function2, androidx.compose.runtime.Composer, int,"
                    + " int):void");
    }

    public static final void access$ActionText(final String str, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1143780417);
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
            TextKt.m210Text4IGK_g(
                    str,
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
                    SettingsTypographyKt.toMediumWeight(
                            ((Typography) composerImpl2.consume(TypographyKt.LocalTypography))
                                    .bodyMedium),
                    composerImpl,
                    i2 & 14,
                    0,
                    65534);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.scaffold.SuwScaffoldKt$ActionText$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SuwScaffoldKt.access$ActionText(
                                    str,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    /* JADX WARN: Type inference failed for: r3v11, types: [com.android.settingslib.spa.widget.scaffold.SuwScaffoldKt$BottomBar$1$1$1, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Type inference failed for: r3v16, types: [com.android.settingslib.spa.widget.scaffold.SuwScaffoldKt$BottomBar$1$2$1, kotlin.jvm.internal.Lambda] */
    public static final void access$BottomBar(
            final BottomAppBarButton bottomAppBarButton,
            final BottomAppBarButton bottomAppBarButton2,
            Composer composer,
            final int i) {
        int i2;
        ComposerImpl composerImpl;
        boolean z;
        boolean z2;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-420642965);
        if ((i & 14) == 0) {
            i2 = (composerImpl2.changed(bottomAppBarButton) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl2.changed(bottomAppBarButton2) ? 32 : 16;
        }
        if ((i2 & 91) == 18 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Modifier.Companion companion = Modifier.Companion.$$INSTANCE;
            float f = SettingsDimension.itemPaddingEnd;
            Modifier m88paddingqDBjuR0 =
                    PaddingKt.m88paddingqDBjuR0(
                            companion,
                            f,
                            SettingsDimension.paddingExtraLarge,
                            f,
                            SettingsDimension.itemPaddingVertical);
            RowMeasurePolicy rowMeasurePolicy =
                    RowKt.rowMeasurePolicy(
                            Arrangement.Start, Alignment.Companion.Top, composerImpl2, 0);
            int i3 = composerImpl2.compoundKeyHash;
            PersistentCompositionLocalMap currentCompositionLocalScope =
                    composerImpl2.currentCompositionLocalScope();
            Modifier materializeModifier =
                    ComposedModifierKt.materializeModifier(composerImpl2, m88paddingqDBjuR0);
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
                    composerImpl2, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
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
            composerImpl2.startReplaceGroup(-1789556439);
            if (bottomAppBarButton2 == null) {
                composerImpl = composerImpl2;
                z = false;
            } else {
                composerImpl = composerImpl2;
                ButtonKt.TextButton(
                        bottomAppBarButton2.onClick,
                        null,
                        bottomAppBarButton2.enabled,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        ComposableLambdaKt.rememberComposableLambda(
                                1944682979,
                                new Function3() { // from class:
                                                  // com.android.settingslib.spa.widget.scaffold.SuwScaffoldKt$BottomBar$1$1$1
                                    {
                                        super(3);
                                    }

                                    @Override // kotlin.jvm.functions.Function3
                                    public final Object invoke(
                                            Object obj, Object obj2, Object obj3) {
                                        RowScope TextButton = (RowScope) obj;
                                        Composer composer2 = (Composer) obj2;
                                        int intValue = ((Number) obj3).intValue();
                                        Intrinsics.checkNotNullParameter(
                                                TextButton, "$this$TextButton");
                                        if ((intValue & 81) == 16) {
                                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                            if (composerImpl3.getSkipping()) {
                                                composerImpl3.skipToGroupEnd();
                                                return Unit.INSTANCE;
                                            }
                                        }
                                        OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                        SuwScaffoldKt.access$ActionText(
                                                BottomAppBarButton.this.text, composer2, 0);
                                        return Unit.INSTANCE;
                                    }
                                },
                                composerImpl2),
                        composerImpl2,
                        805306368,
                        FileType.VCF);
                z = false;
            }
            composerImpl.end(z);
            if (1.0f <= 0.0d) {
                throw new IllegalArgumentException(
                        "invalid weight 1.0; must be greater than zero".toString());
            }
            SpacerKt.Spacer(
                    composerImpl,
                    new LayoutWeightElement(RangesKt.coerceAtMost(1.0f, Float.MAX_VALUE), true));
            composerImpl.startReplaceGroup(602465229);
            if (bottomAppBarButton == null) {
                z2 = z;
            } else {
                z2 = z;
                ButtonKt.Button(
                        bottomAppBarButton.onClick,
                        null,
                        bottomAppBarButton.enabled,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        ComposableLambdaKt.rememberComposableLambda(
                                1080347769,
                                new Function3() { // from class:
                                                  // com.android.settingslib.spa.widget.scaffold.SuwScaffoldKt$BottomBar$1$2$1
                                    {
                                        super(3);
                                    }

                                    @Override // kotlin.jvm.functions.Function3
                                    public final Object invoke(
                                            Object obj, Object obj2, Object obj3) {
                                        RowScope Button = (RowScope) obj;
                                        Composer composer2 = (Composer) obj2;
                                        int intValue = ((Number) obj3).intValue();
                                        Intrinsics.checkNotNullParameter(Button, "$this$Button");
                                        if ((intValue & 81) == 16) {
                                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                            if (composerImpl3.getSkipping()) {
                                                composerImpl3.skipToGroupEnd();
                                                return Unit.INSTANCE;
                                            }
                                        }
                                        OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                        SuwScaffoldKt.access$ActionText(
                                                BottomAppBarButton.this.text, composer2, 0);
                                        return Unit.INSTANCE;
                                    }
                                },
                                composerImpl),
                        composerImpl,
                        805306368,
                        FileType.VCF);
            }
            composerImpl.end(z2);
            composerImpl.end(true);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.scaffold.SuwScaffoldKt$BottomBar$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SuwScaffoldKt.access$BottomBar(
                                    BottomAppBarButton.this,
                                    bottomAppBarButton2,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public static final void access$Header(
            final ImageVector imageVector, String str, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl;
        final String str2;
        final int i3;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1962691965);
        if ((i & 14) == 0) {
            i2 = (composerImpl2.changed(imageVector) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl2.changed(str) ? 32 : 16;
        }
        int i4 = i2;
        if ((i4 & 91) == 18 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
            str2 = str;
            i3 = i;
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Modifier.Companion companion = Modifier.Companion.$$INSTANCE;
            float f = SettingsDimension.itemPaddingStart;
            float f2 = SettingsDimension.itemPaddingVertical;
            Modifier m89paddingqDBjuR0$default =
                    PaddingKt.m89paddingqDBjuR0$default(
                            companion, f, f2, SettingsDimension.itemPaddingEnd, 0.0f, 8);
            ColumnMeasurePolicy columnMeasurePolicy =
                    ColumnKt.columnMeasurePolicy(
                            Arrangement.Top, Alignment.Companion.Start, composerImpl2, 0);
            int i5 = composerImpl2.compoundKeyHash;
            PersistentCompositionLocalMap currentCompositionLocalScope =
                    composerImpl2.currentCompositionLocalScope();
            Modifier materializeModifier =
                    ComposedModifierKt.materializeModifier(
                            composerImpl2, m89paddingqDBjuR0$default);
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
                    composerImpl2, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m221setimpl(
                    composerImpl2,
                    currentCompositionLocalScope,
                    ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl2.inserting
                    || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(i5))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(i5, composerImpl2, i5, function2);
            }
            Updater.m221setimpl(
                    composerImpl2, materializeModifier, ComposeUiNode.Companion.SetModifier);
            Modifier m96size3ABfNKs =
                    SizeKt.m96size3ABfNKs(
                            PaddingKt.m87paddingVpY3zN4$default(
                                    companion, 0.0f, SettingsDimension.itemPaddingAround, 1),
                            SettingsDimension.iconLarge);
            StaticProvidableCompositionLocal staticProvidableCompositionLocal =
                    ColorSchemeKt.LocalColorScheme;
            IconKt.m188Iconww6aTOc(
                    imageVector,
                    (String) null,
                    m96size3ABfNKs,
                    ((ColorScheme) composerImpl2.consume(staticProvidableCompositionLocal)).primary,
                    composerImpl2,
                    (i4 & 14) | FileType.CRT,
                    0);
            composerImpl = composerImpl2;
            str2 = str;
            i3 = i;
            TextKt.m210Text4IGK_g(
                    str,
                    PaddingKt.m87paddingVpY3zN4$default(companion, 0.0f, f2, 1),
                    ((ColorScheme) composerImpl2.consume(staticProvidableCompositionLocal))
                            .onSurface,
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
                    ((Typography) composerImpl2.consume(TypographyKt.LocalTypography)).displaySmall,
                    composerImpl,
                    ((i4 >> 3) & 14) | 48,
                    0,
                    65528);
            composerImpl.end(true);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.scaffold.SuwScaffoldKt$Header$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SuwScaffoldKt.access$Header(
                                    ImageVector.this,
                                    str2,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i3 | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
