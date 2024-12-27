package com.android.settingslib.spa.widget.preference;

import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.material3.ColorScheme;
import androidx.compose.material3.ColorSchemeKt;
import androidx.compose.material3.SurfaceKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;

import com.android.settingslib.spa.framework.theme.SettingsDimension;
import com.android.settingslib.spa.framework.theme.SettingsShape;
import com.android.settingslib.spa.framework.util.EntryHighlightKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class MainSwitchPreferenceKt {
    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.settingslib.spa.widget.preference.MainSwitchPreferenceKt$MainSwitchPreference$1, kotlin.jvm.internal.Lambda] */
    public static final void MainSwitchPreference(
            final SwitchPreferenceModel model, Composer composer, final int i) {
        int i2;
        Intrinsics.checkNotNullParameter(model, "model");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(722892629);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(model) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            EntryHighlightKt.EntryHighlight(
                    ComposableLambdaKt.rememberComposableLambda(
                            1278374406,
                            new Function2() { // from class:
                                              // com.android.settingslib.spa.widget.preference.MainSwitchPreferenceKt$MainSwitchPreference$1
                                {
                                    super(2);
                                }

                                /* JADX WARN: Type inference failed for: r14v12, types: [com.android.settingslib.spa.widget.preference.MainSwitchPreferenceKt$MainSwitchPreference$1$1, kotlin.jvm.internal.Lambda] */
                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj, Object obj2) {
                                    long j;
                                    Composer composer2 = (Composer) obj;
                                    if ((((Number) obj2).intValue() & 11) == 2) {
                                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                        if (composerImpl2.getSkipping()) {
                                            composerImpl2.skipToGroupEnd();
                                            return Unit.INSTANCE;
                                        }
                                    }
                                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                    Modifier m85padding3ABfNKs =
                                            PaddingKt.m85padding3ABfNKs(
                                                    Modifier.Companion.$$INSTANCE,
                                                    SettingsDimension.itemPaddingEnd);
                                    if (Intrinsics.areEqual(
                                            (Boolean)
                                                    SwitchPreferenceModel.this
                                                            .getChecked()
                                                            .mo1068invoke(),
                                            Boolean.TRUE)) {
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                        composerImpl3.startReplaceGroup(-1965998446);
                                        j =
                                                ((ColorScheme)
                                                                composerImpl3.consume(
                                                                        ColorSchemeKt
                                                                                .LocalColorScheme))
                                                        .primaryContainer;
                                        composerImpl3.end(false);
                                    } else {
                                        ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                        composerImpl4.startReplaceGroup(-1965998379);
                                        j =
                                                ((ColorScheme)
                                                                composerImpl4.consume(
                                                                        ColorSchemeKt
                                                                                .LocalColorScheme))
                                                        .secondaryContainer;
                                        composerImpl4.end(false);
                                    }
                                    long j2 = j;
                                    RoundedCornerShape roundedCornerShape =
                                            SettingsShape.CornerExtraLarge;
                                    final SwitchPreferenceModel switchPreferenceModel =
                                            SwitchPreferenceModel.this;
                                    SurfaceKt.m198SurfaceT9BRK9s(
                                            m85padding3ABfNKs,
                                            roundedCornerShape,
                                            j2,
                                            0L,
                                            0.0f,
                                            0.0f,
                                            null,
                                            ComposableLambdaKt.rememberComposableLambda(
                                                    -1553078965,
                                                    new Function2() { // from class:
                                                                      // com.android.settingslib.spa.widget.preference.MainSwitchPreferenceKt$MainSwitchPreference$1.1
                                                        {
                                                            super(2);
                                                        }

                                                        @Override // kotlin.jvm.functions.Function2
                                                        public final Object invoke(
                                                                Object obj3, Object obj4) {
                                                            Composer composer3 = (Composer) obj3;
                                                            if ((((Number) obj4).intValue() & 11)
                                                                    == 2) {
                                                                ComposerImpl composerImpl5 =
                                                                        (ComposerImpl) composer3;
                                                                if (composerImpl5.getSkipping()) {
                                                                    composerImpl5.skipToGroupEnd();
                                                                    return Unit.INSTANCE;
                                                                }
                                                            }
                                                            OpaqueKey opaqueKey3 =
                                                                    ComposerKt.invocation;
                                                            float f = 20;
                                                            SwitchPreferenceKt
                                                                    .m1046InternalSwitchPreference0HqY7hA(
                                                                            SwitchPreferenceModel
                                                                                    .this
                                                                                    .getTitle(),
                                                                            null,
                                                                            null,
                                                                            (Boolean)
                                                                                    SwitchPreferenceModel
                                                                                            .this
                                                                                            .getChecked()
                                                                                            .mo1068invoke(),
                                                                            ((Boolean)
                                                                                            SwitchPreferenceModel
                                                                                                    .this
                                                                                                    .getChangeable()
                                                                                                    .mo1068invoke())
                                                                                    .booleanValue(),
                                                                            f,
                                                                            f,
                                                                            24,
                                                                            SwitchPreferenceModel
                                                                                    .this
                                                                                    .getOnCheckedChange(),
                                                                            composer3,
                                                                            14352384,
                                                                            6);
                                                            return Unit.INSTANCE;
                                                        }
                                                    },
                                                    composer2),
                                            composer2,
                                            12582966,
                                            120);
                                    return Unit.INSTANCE;
                                }
                            },
                            composerImpl),
                    composerImpl,
                    6);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.preference.MainSwitchPreferenceKt$MainSwitchPreference$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            MainSwitchPreferenceKt.MainSwitchPreference(
                                    SwitchPreferenceModel.this,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
