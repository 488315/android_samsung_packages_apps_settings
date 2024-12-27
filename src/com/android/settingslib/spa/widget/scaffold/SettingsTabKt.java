package com.android.settingslib.spa.widget.scaffold;

import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.layout.ColumnScope;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.material3.ColorScheme;
import androidx.compose.material3.ColorSchemeKt;
import androidx.compose.material3.InteractiveComponentSizeKt;
import androidx.compose.material3.MinimumInteractiveModifier;
import androidx.compose.material3.TabKt;
import androidx.compose.material3.TextKt;
import androidx.compose.material3.Typography;
import androidx.compose.material3.TypographyKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.text.TextStyle;

import com.android.settingslib.spa.framework.theme.SettingsShape;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SettingsTabKt {
    /* JADX WARN: Type inference failed for: r3v5, types: [com.android.settingslib.spa.widget.scaffold.SettingsTabKt$SettingsTab$1, kotlin.jvm.internal.Lambda] */
    public static final void SettingsTab(
            final String title,
            final boolean z,
            final float f,
            final Function0 onClick,
            Composer composer,
            final int i) {
        int i2;
        ComposerImpl composerImpl;
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(onClick, "onClick");
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1283695285);
        if ((i & 14) == 0) {
            i2 = (composerImpl2.changed(title) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl2.changed(z) ? 32 : 16;
        }
        if ((i & 896) == 0) {
            i2 |= composerImpl2.changed(f) ? 256 : 128;
        }
        if ((i & 7168) == 0) {
            i2 |= composerImpl2.changedInstance(onClick) ? 2048 : 1024;
        }
        if ((i2 & 5851) == 1170 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            final float coerceAtMost = z ? RangesKt.coerceAtMost(2 * f, 1.0f) : 1.0f;
            StaticProvidableCompositionLocal staticProvidableCompositionLocal =
                    InteractiveComponentSizeKt.LocalMinimumInteractiveComponentSize;
            Modifier clip =
                    ClipKt.clip(
                            PaddingKt.m86paddingVpY3zN4(MinimumInteractiveModifier.INSTANCE, 4, 6),
                            SettingsShape.CornerMedium);
            StaticProvidableCompositionLocal staticProvidableCompositionLocal2 =
                    ColorSchemeKt.LocalColorScheme;
            composerImpl = composerImpl2;
            TabKt.m201TabbogVsAg(
                    z,
                    onClick,
                    BackgroundKt.m29backgroundbw27NRU(
                            clip,
                            ColorKt.m321lerpjxsXWHM(
                                    ((ColorScheme)
                                                    composerImpl2.consume(
                                                            staticProvidableCompositionLocal2))
                                            .primaryContainer,
                                    ((ColorScheme)
                                                    composerImpl2.consume(
                                                            staticProvidableCompositionLocal2))
                                            .surface,
                                    coerceAtMost),
                            RectangleShapeKt.RectangleShape),
                    false,
                    0L,
                    0L,
                    null,
                    ComposableLambdaKt.rememberComposableLambda(
                            1921859176,
                            new Function3() { // from class:
                                              // com.android.settingslib.spa.widget.scaffold.SettingsTabKt$SettingsTab$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(3);
                                }

                                @Override // kotlin.jvm.functions.Function3
                                public final Object invoke(Object obj, Object obj2, Object obj3) {
                                    ColumnScope Tab = (ColumnScope) obj;
                                    Composer composer2 = (Composer) obj2;
                                    int intValue = ((Number) obj3).intValue();
                                    Intrinsics.checkNotNullParameter(Tab, "$this$Tab");
                                    if ((intValue & 81) == 16) {
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                        if (composerImpl3.getSkipping()) {
                                            composerImpl3.skipToGroupEnd();
                                            return Unit.INSTANCE;
                                        }
                                    }
                                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                    ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                    TextStyle textStyle =
                                            ((Typography)
                                                            composerImpl4.consume(
                                                                    TypographyKt.LocalTypography))
                                                    .labelLarge;
                                    StaticProvidableCompositionLocal
                                            staticProvidableCompositionLocal3 =
                                                    ColorSchemeKt.LocalColorScheme;
                                    TextKt.m210Text4IGK_g(
                                            title,
                                            null,
                                            ColorKt.m321lerpjxsXWHM(
                                                    ((ColorScheme)
                                                                    composerImpl4.consume(
                                                                            staticProvidableCompositionLocal3))
                                                            .onPrimaryContainer,
                                                    ((ColorScheme)
                                                                    composerImpl4.consume(
                                                                            staticProvidableCompositionLocal3))
                                                            .onSurface,
                                                    coerceAtMost),
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
                                            textStyle,
                                            composer2,
                                            0,
                                            0,
                                            65530);
                                    return Unit.INSTANCE;
                                }
                            },
                            composerImpl2),
                    composerImpl2,
                    ((i2 >> 3) & 14) | 12582912 | ((i2 >> 6) & 112),
                    120);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.scaffold.SettingsTabKt$SettingsTab$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SettingsTabKt.SettingsTab(
                                    title,
                                    z,
                                    f,
                                    onClick,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
