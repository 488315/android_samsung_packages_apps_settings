package com.android.settingslib.spa.widget.ui;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.ColorScheme;
import androidx.compose.material3.ColorSchemeKt;
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
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;

import com.android.settingslib.spa.framework.theme.SettingsDimension;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class TextKt {
    public static final void PlaceholderTitle(final String title, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl;
        Intrinsics.checkNotNullParameter(title, "title");
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1113811083);
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
            Modifier padding =
                    PaddingKt.padding(SizeKt.FillWholeMaxSize, SettingsDimension.itemPadding);
            MeasurePolicy maybeCachedBoxMeasurePolicy =
                    BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
            int i3 = composerImpl2.compoundKeyHash;
            PersistentCompositionLocalMap currentCompositionLocalScope =
                    composerImpl2.currentCompositionLocalScope();
            Modifier materializeModifier =
                    ComposedModifierKt.materializeModifier(composerImpl2, padding);
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
            androidx.compose.material3.TextKt.m210Text4IGK_g(
                    title,
                    null,
                    ((ColorScheme) composerImpl2.consume(ColorSchemeKt.LocalColorScheme)).onSurface,
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
                    ((Typography) composerImpl2.consume(TypographyKt.LocalTypography)).titleLarge,
                    composerImpl2,
                    i2 & 14,
                    0,
                    65530);
            composerImpl = composerImpl2;
            composerImpl.end(true);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.ui.TextKt$PlaceholderTitle$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            TextKt.PlaceholderTitle(
                                    title,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0050  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SettingsBody(
            final java.lang.String r30,
            java.lang.String r31,
            int r32,
            androidx.compose.runtime.Composer r33,
            final int r34,
            final int r35) {
        /*
            Method dump skipped, instructions count: 255
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.spa.widget.ui.TextKt.SettingsBody(java.lang.String,"
                    + " java.lang.String, int, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:18:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0060  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SettingsDialogItem(
            final java.lang.String r29,
            boolean r30,
            androidx.compose.runtime.Composer r31,
            final int r32,
            final int r33) {
        /*
            Method dump skipped, instructions count: 207
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.spa.widget.ui.TextKt.SettingsDialogItem(java.lang.String,"
                    + " boolean, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:18:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0060  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SettingsListItem(
            final java.lang.String r29,
            boolean r30,
            androidx.compose.runtime.Composer r31,
            final int r32,
            final int r33) {
        /*
            Method dump skipped, instructions count: 214
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.spa.widget.ui.TextKt.SettingsListItem(java.lang.String,"
                    + " boolean, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0051  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SettingsTitle(
            final java.lang.String r28,
            java.lang.String r29,
            boolean r30,
            androidx.compose.runtime.Composer r31,
            final int r32,
            final int r33) {
        /*
            Method dump skipped, instructions count: 240
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.spa.widget.ui.TextKt.SettingsTitle(java.lang.String,"
                    + " java.lang.String, boolean, androidx.compose.runtime.Composer, int,"
                    + " int):void");
    }
}
