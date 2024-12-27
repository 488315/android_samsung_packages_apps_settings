package com.android.settingslib.spa.widget.preference;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;

import com.android.settingslib.spa.framework.util.EntryHighlightKt;
import com.android.settingslib.spa.widget.ui.SwitchKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class TwoTargetSwitchPreferenceKt {
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.settingslib.spa.widget.preference.TwoTargetSwitchPreferenceKt$TwoTargetSwitchPreference$2, kotlin.jvm.internal.Lambda] */
    public static final void TwoTargetSwitchPreference(
            final SwitchPreferenceModel model,
            final Function0 function0,
            final Function0 function02,
            Composer composer,
            final int i,
            final int i2) {
        int i3;
        Intrinsics.checkNotNullParameter(model, "model");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1752224251);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 14) == 0) {
            i3 = (composerImpl.changed(model) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        int i4 = 2 & i2;
        if (i4 != 0) {
            i3 |= 48;
        } else if ((i & 112) == 0) {
            i3 |= composerImpl.changedInstance(function0) ? 32 : 16;
        }
        if ((4 & i2) != 0) {
            i3 |= 384;
        } else if ((i & 896) == 0) {
            i3 |= composerImpl.changedInstance(function02) ? 256 : 128;
        }
        if ((i3 & 731) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (i4 != 0) {
                function0 =
                        new Function0() { // from class:
                                          // com.android.settingslib.spa.widget.preference.TwoTargetSwitchPreferenceKt$TwoTargetSwitchPreference$1
                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final /* bridge */ /* synthetic */ Object mo1068invoke() {
                                return Boolean.TRUE;
                            }
                        };
            }
            OpaqueKey opaqueKey = ComposerKt.invocation;
            EntryHighlightKt.EntryHighlight(
                    ComposableLambdaKt.rememberComposableLambda(
                            -538174218,
                            new Function2() { // from class:
                                              // com.android.settingslib.spa.widget.preference.TwoTargetSwitchPreferenceKt$TwoTargetSwitchPreference$2
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(2);
                                }

                                /* JADX WARN: Type inference failed for: r10v7, types: [com.android.settingslib.spa.widget.preference.TwoTargetSwitchPreferenceKt$TwoTargetSwitchPreference$2$1, kotlin.jvm.internal.Lambda] */
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
                                    String title = SwitchPreferenceModel.this.getTitle();
                                    Function0 summary = SwitchPreferenceModel.this.getSummary();
                                    Function0 function03 = function0;
                                    Function0 function04 = function02;
                                    Function2 icon = SwitchPreferenceModel.this.getIcon();
                                    final SwitchPreferenceModel switchPreferenceModel =
                                            SwitchPreferenceModel.this;
                                    TwoTargetPreferenceKt.TwoTargetPreference(
                                            title,
                                            summary,
                                            function03,
                                            function04,
                                            icon,
                                            ComposableLambdaKt.rememberComposableLambda(
                                                    -901696562,
                                                    new Function2() { // from class:
                                                                      // com.android.settingslib.spa.widget.preference.TwoTargetSwitchPreferenceKt$TwoTargetSwitchPreference$2.1
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
                                                            SwitchKt.SettingsSwitch(
                                                                    (Boolean)
                                                                            SwitchPreferenceModel
                                                                                    .this
                                                                                    .getChecked()
                                                                                    .mo1068invoke(),
                                                                    SwitchPreferenceModel.this
                                                                            .getChangeable(),
                                                                    SwitchPreferenceModel.this
                                                                            .getTitle(),
                                                                    SwitchPreferenceModel.this
                                                                            .getOnCheckedChange(),
                                                                    null,
                                                                    composer3,
                                                                    0,
                                                                    16);
                                                            return Unit.INSTANCE;
                                                        }
                                                    },
                                                    composer2),
                                            composer2,
                                            196608,
                                            0);
                                    return Unit.INSTANCE;
                                }
                            },
                            composerImpl),
                    composerImpl,
                    6);
        }
        final Function0 function03 = function0;
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.preference.TwoTargetSwitchPreferenceKt$TwoTargetSwitchPreference$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            TwoTargetSwitchPreferenceKt.TwoTargetSwitchPreference(
                                    SwitchPreferenceModel.this,
                                    function03,
                                    function02,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1),
                                    i2);
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
