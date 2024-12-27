package com.android.settingslib.spa.widget.preference;

import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.ColorScheme;
import androidx.compose.material3.ColorSchemeKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.RectangleShapeKt;

import com.android.settingslib.spa.framework.theme.SettingsDimension;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class TwoTargetPreferenceKt {
    public static final void PreferenceDivider(Composer composer, final int i) {
        long Color;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-158031319);
        if (i == 0 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Modifier m97sizeVpY3zN4 =
                    SizeKt.m97sizeVpY3zN4(
                            PaddingKt.m87paddingVpY3zN4$default(
                                    Modifier.Companion.$$INSTANCE,
                                    SettingsDimension.itemPaddingEnd,
                                    0.0f,
                                    2),
                            1,
                            SettingsDimension.itemDividerHeight);
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
                            m97sizeVpY3zN4, Color, RectangleShapeKt.RectangleShape),
                    composerImpl,
                    0);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.preference.TwoTargetPreferenceKt$PreferenceDivider$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            TwoTargetPreferenceKt.PreferenceDivider(
                                    (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01f0  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0079  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void TwoTargetPreference(
            final java.lang.String r23,
            final kotlin.jvm.functions.Function0 r24,
            kotlin.jvm.functions.Function0 r25,
            final kotlin.jvm.functions.Function0 r26,
            kotlin.jvm.functions.Function2 r27,
            final kotlin.jvm.functions.Function2 r28,
            androidx.compose.runtime.Composer r29,
            final int r30,
            final int r31) {
        /*
            Method dump skipped, instructions count: 501
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.spa.widget.preference.TwoTargetPreferenceKt.TwoTargetPreference(java.lang.String,"
                    + " kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function0,"
                    + " kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function2,"
                    + " kotlin.jvm.functions.Function2, androidx.compose.runtime.Composer, int,"
                    + " int):void");
    }
}
