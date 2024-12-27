package com.android.settingslib.spa.framework.theme;

import android.R;
import android.content.Context;
import android.content.res.Configuration;

import androidx.compose.material3.ColorResourceHelper;
import androidx.compose.material3.ColorScheme;
import androidx.compose.material3.ColorSchemeKt;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.MaterialThemeKt;
import androidx.compose.material3.Typography;
import androidx.compose.material3.tokens.ColorDarkTokens;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.unit.TextUnitKt;

import com.android.settingslib.spa.framework.compose.RuntimeUtilsKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SettingsThemeKt {
    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.settingslib.spa.framework.theme.SettingsThemeKt$SettingsTheme$1, kotlin.jvm.internal.Lambda] */
    public static final void SettingsTheme(
            final Function2 content, Composer composer, final int i) {
        int i2;
        ColorScheme m182lightColorSchemeCXl9yA$default;
        Intrinsics.checkNotNullParameter(content, "content");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-985702945);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changedInstance(content) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            boolean z =
                    (((Configuration)
                                                    composerImpl.consume(
                                                            AndroidCompositionLocals_androidKt
                                                                    .LocalConfiguration))
                                            .uiMode
                                    & 48)
                            == 32;
            composerImpl.startReplaceGroup(1983108581);
            Context context =
                    (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            composerImpl.startReplaceGroup(-621266646);
            boolean changed = composerImpl.changed(z);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (changed || rememberedValue == composer$Companion$Empty$1) {
                if (z) {
                    long m179getColorWaAFU9c =
                            ColorResourceHelper.m179getColorWaAFU9c(
                                    context, R.color.system_primary_dark);
                    long m179getColorWaAFU9c2 =
                            ColorResourceHelper.m179getColorWaAFU9c(
                                    context, R.color.system_on_primary_dark);
                    long m179getColorWaAFU9c3 =
                            ColorResourceHelper.m179getColorWaAFU9c(
                                    context, R.color.system_primary_container_dark);
                    long m179getColorWaAFU9c4 =
                            ColorResourceHelper.m179getColorWaAFU9c(
                                    context, R.color.system_on_primary_container_dark);
                    long m179getColorWaAFU9c5 =
                            ColorResourceHelper.m179getColorWaAFU9c(
                                    context, R.color.system_primary_light);
                    long m179getColorWaAFU9c6 =
                            ColorResourceHelper.m179getColorWaAFU9c(
                                    context, R.color.system_secondary_dark);
                    long m179getColorWaAFU9c7 =
                            ColorResourceHelper.m179getColorWaAFU9c(
                                    context, R.color.system_on_secondary_dark);
                    long m179getColorWaAFU9c8 =
                            ColorResourceHelper.m179getColorWaAFU9c(
                                    context, R.color.system_secondary_container_dark);
                    long m179getColorWaAFU9c9 =
                            ColorResourceHelper.m179getColorWaAFU9c(
                                    context, R.color.system_on_secondary_container_dark);
                    long m179getColorWaAFU9c10 =
                            ColorResourceHelper.m179getColorWaAFU9c(
                                    context, R.color.system_tertiary_dark);
                    long m179getColorWaAFU9c11 =
                            ColorResourceHelper.m179getColorWaAFU9c(
                                    context, R.color.system_on_tertiary_dark);
                    long m179getColorWaAFU9c12 =
                            ColorResourceHelper.m179getColorWaAFU9c(
                                    context, R.color.system_tertiary_container_dark);
                    long m179getColorWaAFU9c13 =
                            ColorResourceHelper.m179getColorWaAFU9c(
                                    context, R.color.system_on_tertiary_container_dark);
                    long m179getColorWaAFU9c14 =
                            ColorResourceHelper.m179getColorWaAFU9c(
                                    context, R.color.system_background_dark);
                    long m179getColorWaAFU9c15 =
                            ColorResourceHelper.m179getColorWaAFU9c(
                                    context, R.color.system_on_background_dark);
                    long m179getColorWaAFU9c16 =
                            ColorResourceHelper.m179getColorWaAFU9c(
                                    context, R.color.system_surface_dark);
                    long m179getColorWaAFU9c17 =
                            ColorResourceHelper.m179getColorWaAFU9c(
                                    context, R.color.system_on_surface_dark);
                    long m179getColorWaAFU9c18 =
                            ColorResourceHelper.m179getColorWaAFU9c(
                                    context, R.color.system_surface_variant_dark);
                    long m179getColorWaAFU9c19 =
                            ColorResourceHelper.m179getColorWaAFU9c(
                                    context, R.color.system_on_surface_variant_dark);
                    long m179getColorWaAFU9c20 =
                            ColorResourceHelper.m179getColorWaAFU9c(
                                    context, R.color.system_surface_light);
                    long m179getColorWaAFU9c21 =
                            ColorResourceHelper.m179getColorWaAFU9c(
                                    context, R.color.system_on_surface_light);
                    long m179getColorWaAFU9c22 =
                            ColorResourceHelper.m179getColorWaAFU9c(
                                    context, R.color.system_outline_dark);
                    long m179getColorWaAFU9c23 =
                            ColorResourceHelper.m179getColorWaAFU9c(
                                    context, R.color.system_outline_variant_dark);
                    long m179getColorWaAFU9c24 =
                            ColorResourceHelper.m179getColorWaAFU9c(
                                    context, R.color.system_surface_bright_dark);
                    long m179getColorWaAFU9c25 =
                            ColorResourceHelper.m179getColorWaAFU9c(
                                    context, R.color.system_surface_dim_dark);
                    long m179getColorWaAFU9c26 =
                            ColorResourceHelper.m179getColorWaAFU9c(
                                    context, R.color.system_surface_container_dark);
                    long m179getColorWaAFU9c27 =
                            ColorResourceHelper.m179getColorWaAFU9c(
                                    context, R.color.system_surface_container_high_dark);
                    long m179getColorWaAFU9c28 =
                            ColorResourceHelper.m179getColorWaAFU9c(
                                    context, R.color.system_surface_container_highest_dark);
                    long m179getColorWaAFU9c29 =
                            ColorResourceHelper.m179getColorWaAFU9c(
                                    context, R.color.system_surface_container_low_dark);
                    long m179getColorWaAFU9c30 =
                            ColorResourceHelper.m179getColorWaAFU9c(
                                    context, R.color.system_surface_container_lowest_dark);
                    long m179getColorWaAFU9c31 =
                            ColorResourceHelper.m179getColorWaAFU9c(
                                    context, R.color.system_primary_dark);
                    StaticProvidableCompositionLocal staticProvidableCompositionLocal =
                            ColorSchemeKt.LocalColorScheme;
                    m182lightColorSchemeCXl9yA$default =
                            new ColorScheme(
                                    m179getColorWaAFU9c,
                                    m179getColorWaAFU9c2,
                                    m179getColorWaAFU9c3,
                                    m179getColorWaAFU9c4,
                                    m179getColorWaAFU9c5,
                                    m179getColorWaAFU9c6,
                                    m179getColorWaAFU9c7,
                                    m179getColorWaAFU9c8,
                                    m179getColorWaAFU9c9,
                                    m179getColorWaAFU9c10,
                                    m179getColorWaAFU9c11,
                                    m179getColorWaAFU9c12,
                                    m179getColorWaAFU9c13,
                                    m179getColorWaAFU9c14,
                                    m179getColorWaAFU9c15,
                                    m179getColorWaAFU9c16,
                                    m179getColorWaAFU9c17,
                                    m179getColorWaAFU9c18,
                                    m179getColorWaAFU9c19,
                                    m179getColorWaAFU9c31,
                                    m179getColorWaAFU9c20,
                                    m179getColorWaAFU9c21,
                                    ColorDarkTokens.Error,
                                    ColorDarkTokens.OnError,
                                    ColorDarkTokens.ErrorContainer,
                                    ColorDarkTokens.OnErrorContainer,
                                    m179getColorWaAFU9c22,
                                    m179getColorWaAFU9c23,
                                    ColorDarkTokens.Scrim,
                                    m179getColorWaAFU9c24,
                                    m179getColorWaAFU9c25,
                                    m179getColorWaAFU9c26,
                                    m179getColorWaAFU9c27,
                                    m179getColorWaAFU9c28,
                                    m179getColorWaAFU9c29,
                                    m179getColorWaAFU9c30);
                } else {
                    m182lightColorSchemeCXl9yA$default =
                            ColorSchemeKt.m182lightColorSchemeCXl9yA$default(
                                    ColorResourceHelper.m179getColorWaAFU9c(
                                            context, R.color.system_primary_light),
                                    ColorResourceHelper.m179getColorWaAFU9c(
                                            context, R.color.system_on_primary_light),
                                    ColorResourceHelper.m179getColorWaAFU9c(
                                            context, R.color.system_primary_container_light),
                                    ColorResourceHelper.m179getColorWaAFU9c(
                                            context, R.color.system_on_primary_container_light),
                                    ColorResourceHelper.m179getColorWaAFU9c(
                                            context, R.color.system_primary_dark),
                                    ColorResourceHelper.m179getColorWaAFU9c(
                                            context, R.color.system_secondary_light),
                                    ColorResourceHelper.m179getColorWaAFU9c(
                                            context, R.color.system_on_secondary_light),
                                    ColorResourceHelper.m179getColorWaAFU9c(
                                            context, R.color.system_secondary_container_light),
                                    ColorResourceHelper.m179getColorWaAFU9c(
                                            context, R.color.system_on_secondary_container_light),
                                    ColorResourceHelper.m179getColorWaAFU9c(
                                            context, R.color.system_tertiary_light),
                                    ColorResourceHelper.m179getColorWaAFU9c(
                                            context, R.color.system_on_tertiary_light),
                                    ColorResourceHelper.m179getColorWaAFU9c(
                                            context, R.color.system_tertiary_container_light),
                                    ColorResourceHelper.m179getColorWaAFU9c(
                                            context, R.color.system_on_tertiary_container_light),
                                    ColorResourceHelper.m179getColorWaAFU9c(
                                            context, R.color.system_background_light),
                                    ColorResourceHelper.m179getColorWaAFU9c(
                                            context, R.color.system_on_background_light),
                                    ColorResourceHelper.m179getColorWaAFU9c(
                                            context, R.color.system_surface_light),
                                    ColorResourceHelper.m179getColorWaAFU9c(
                                            context, R.color.system_on_surface_light),
                                    ColorResourceHelper.m179getColorWaAFU9c(
                                            context, R.color.system_surface_variant_light),
                                    ColorResourceHelper.m179getColorWaAFU9c(
                                            context, R.color.system_on_surface_variant_light),
                                    ColorResourceHelper.m179getColorWaAFU9c(
                                            context, R.color.system_primary_light),
                                    ColorResourceHelper.m179getColorWaAFU9c(
                                            context, R.color.system_surface_dark),
                                    ColorResourceHelper.m179getColorWaAFU9c(
                                            context, R.color.system_on_surface_dark),
                                    ColorResourceHelper.m179getColorWaAFU9c(
                                            context, R.color.system_outline_light),
                                    ColorResourceHelper.m179getColorWaAFU9c(
                                            context, R.color.system_outline_variant_light),
                                    0L,
                                    ColorResourceHelper.m179getColorWaAFU9c(
                                            context, R.color.system_surface_bright_light),
                                    ColorResourceHelper.m179getColorWaAFU9c(
                                            context, R.color.system_surface_container_light),
                                    ColorResourceHelper.m179getColorWaAFU9c(
                                            context, R.color.system_surface_container_high_light),
                                    ColorResourceHelper.m179getColorWaAFU9c(
                                            context,
                                            R.color.system_surface_container_highest_light),
                                    ColorResourceHelper.m179getColorWaAFU9c(
                                            context, R.color.system_surface_container_low_light),
                                    ColorResourceHelper.m179getColorWaAFU9c(
                                            context, R.color.system_surface_container_lowest_light),
                                    ColorResourceHelper.m179getColorWaAFU9c(
                                            context, R.color.system_surface_dim_light),
                                    331350016,
                                    0);
                }
                rememberedValue = m182lightColorSchemeCXl9yA$default;
                composerImpl.updateRememberedValue(rememberedValue);
            }
            ColorScheme colorScheme = (ColorScheme) rememberedValue;
            composerImpl.end(false);
            composerImpl.end(false);
            composerImpl.startReplaceGroup(1450991359);
            composerImpl.startReplaceGroup(-933566224);
            SettingsFontFamily settingsFontFamily =
                    (SettingsFontFamily)
                            RuntimeUtilsKt.rememberContext(
                                    SettingsFontFamilyKt$rememberSettingsFontFamily$1.INSTANCE,
                                    composerImpl);
            composerImpl.end(false);
            composerImpl.startReplaceGroup(-371341875);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (rememberedValue2 == composer$Companion$Empty$1) {
                Intrinsics.checkNotNullParameter(settingsFontFamily, "settingsFontFamily");
                FontWeight fontWeight = FontWeight.Normal;
                long sp = TextUnitKt.getSp(57);
                long sp2 = TextUnitKt.getSp(64);
                long sp3 = TextUnitKt.getSp(-0.2d);
                FontFamily fontFamily = settingsFontFamily.brand;
                TextStyle textStyle =
                        new TextStyle(0L, sp, fontWeight, fontFamily, sp3, 0, sp2, 2, 12451673);
                TextStyle textStyle2 =
                        new TextStyle(
                                0L,
                                TextUnitKt.getSp(45),
                                fontWeight,
                                fontFamily,
                                TextUnitKt.getSp(0.0d),
                                0,
                                TextUnitKt.getSp(52),
                                2,
                                12451673);
                TextStyle textStyle3 =
                        new TextStyle(
                                0L,
                                TextUnitKt.getSp(36),
                                fontWeight,
                                fontFamily,
                                TextUnitKt.getSp(0.0d),
                                0,
                                TextUnitKt.getSp(44),
                                2,
                                12451673);
                TextStyle textStyle4 =
                        new TextStyle(
                                0L,
                                TextUnitKt.getSp(32),
                                fontWeight,
                                fontFamily,
                                TextUnitKt.getSp(0.0d),
                                0,
                                TextUnitKt.getSp(40),
                                2,
                                12451673);
                TextStyle textStyle5 =
                        new TextStyle(
                                0L,
                                TextUnitKt.getSp(28),
                                fontWeight,
                                fontFamily,
                                TextUnitKt.getSp(0.0d),
                                0,
                                TextUnitKt.getSp(36),
                                2,
                                12451673);
                TextStyle textStyle6 =
                        new TextStyle(
                                0L,
                                TextUnitKt.getSp(24),
                                fontWeight,
                                fontFamily,
                                TextUnitKt.getSp(0.0d),
                                0,
                                TextUnitKt.getSp(32),
                                2,
                                12451673);
                TextStyle textStyle7 =
                        new TextStyle(
                                0L,
                                TextUnitKt.getSp(22),
                                fontWeight,
                                fontFamily,
                                TextUnitKt.getEm(0.02d),
                                0,
                                TextUnitKt.getSp(28),
                                2,
                                12451673);
                TextStyle textStyle8 =
                        new TextStyle(
                                0L,
                                TextUnitKt.getSp(20),
                                fontWeight,
                                fontFamily,
                                TextUnitKt.getEm(0.02d),
                                0,
                                TextUnitKt.getSp(24),
                                2,
                                12451673);
                TextStyle textStyle9 =
                        new TextStyle(
                                0L,
                                TextUnitKt.getSp(18),
                                fontWeight,
                                fontFamily,
                                TextUnitKt.getEm(0.02d),
                                0,
                                TextUnitKt.getSp(20),
                                2,
                                12451673);
                long sp4 = TextUnitKt.getSp(16);
                long sp5 = TextUnitKt.getSp(24);
                long em = TextUnitKt.getEm(0.01d);
                FontFamily fontFamily2 = settingsFontFamily.plain;
                TextStyle textStyle10 =
                        new TextStyle(0L, sp4, fontWeight, fontFamily2, em, 0, sp5, 2, 12451673);
                TextStyle textStyle11 =
                        new TextStyle(
                                0L,
                                TextUnitKt.getSp(14),
                                fontWeight,
                                fontFamily2,
                                TextUnitKt.getEm(0.01d),
                                0,
                                TextUnitKt.getSp(20),
                                2,
                                12451673);
                TextStyle textStyle12 =
                        new TextStyle(
                                0L,
                                TextUnitKt.getSp(12),
                                fontWeight,
                                fontFamily2,
                                TextUnitKt.getEm(0.01d),
                                0,
                                TextUnitKt.getSp(16),
                                2,
                                12451673);
                FontWeight fontWeight2 = FontWeight.Medium;
                rememberedValue2 =
                        new Typography(
                                textStyle,
                                textStyle2,
                                textStyle3,
                                textStyle4,
                                textStyle5,
                                textStyle6,
                                textStyle7,
                                textStyle8,
                                textStyle9,
                                textStyle10,
                                textStyle11,
                                textStyle12,
                                new TextStyle(
                                        0L,
                                        TextUnitKt.getSp(16),
                                        fontWeight2,
                                        fontFamily2,
                                        TextUnitKt.getEm(0.01d),
                                        0,
                                        TextUnitKt.getSp(24),
                                        2,
                                        12451673),
                                new TextStyle(
                                        0L,
                                        TextUnitKt.getSp(14),
                                        fontWeight2,
                                        fontFamily2,
                                        TextUnitKt.getEm(0.01d),
                                        0,
                                        TextUnitKt.getSp(20),
                                        2,
                                        12451673),
                                new TextStyle(
                                        0L,
                                        TextUnitKt.getSp(12),
                                        fontWeight2,
                                        fontFamily2,
                                        TextUnitKt.getEm(0.01d),
                                        0,
                                        TextUnitKt.getSp(16),
                                        2,
                                        12451673));
                composerImpl.updateRememberedValue(rememberedValue2);
            }
            composerImpl.end(false);
            composerImpl.end(false);
            MaterialThemeKt.MaterialTheme(
                    colorScheme,
                    null,
                    (Typography) rememberedValue2,
                    ComposableLambdaKt.rememberComposableLambda(
                            -1789300341,
                            new Function2() { // from class:
                                              // com.android.settingslib.spa.framework.theme.SettingsThemeKt$SettingsTheme$1
                                {
                                    super(2);
                                }

                                /* JADX WARN: Type inference failed for: r0v5, types: [com.android.settingslib.spa.framework.theme.SettingsThemeKt$SettingsTheme$1$1, kotlin.jvm.internal.Lambda] */
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
                                    ProvidedValue defaultProvidedValue$runtime_release =
                                            ContentColorKt.LocalContentColor
                                                    .defaultProvidedValue$runtime_release(
                                                            new Color(
                                                                    ((ColorScheme)
                                                                                    ((ComposerImpl)
                                                                                                    composer2)
                                                                                            .consume(
                                                                                                    ColorSchemeKt
                                                                                                            .LocalColorScheme))
                                                                            .onSurface));
                                    final Function2 function2 = Function2.this;
                                    CompositionLocalKt.CompositionLocalProvider(
                                            defaultProvidedValue$runtime_release,
                                            ComposableLambdaKt.rememberComposableLambda(
                                                    742377035,
                                                    new Function2() { // from class:
                                                                      // com.android.settingslib.spa.framework.theme.SettingsThemeKt$SettingsTheme$1.1
                                                        {
                                                            super(2);
                                                        }

                                                        @Override // kotlin.jvm.functions.Function2
                                                        public final Object invoke(
                                                                Object obj3, Object obj4) {
                                                            Composer composer3 = (Composer) obj3;
                                                            if ((((Number) obj4).intValue() & 11)
                                                                    == 2) {
                                                                ComposerImpl composerImpl3 =
                                                                        (ComposerImpl) composer3;
                                                                if (composerImpl3.getSkipping()) {
                                                                    composerImpl3.skipToGroupEnd();
                                                                    return Unit.INSTANCE;
                                                                }
                                                            }
                                                            OpaqueKey opaqueKey3 =
                                                                    ComposerKt.invocation;
                                                            Function2.this.invoke(composer3, 0);
                                                            return Unit.INSTANCE;
                                                        }
                                                    },
                                                    composer2),
                                            composer2,
                                            56);
                                    return Unit.INSTANCE;
                                }
                            },
                            composerImpl),
                    composerImpl,
                    3072,
                    2);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.framework.theme.SettingsThemeKt$SettingsTheme$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SettingsThemeKt.SettingsTheme(
                                    content,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
