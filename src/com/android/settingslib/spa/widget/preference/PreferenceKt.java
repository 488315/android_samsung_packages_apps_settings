package com.android.settingslib.spa.widget.preference;

import androidx.compose.foundation.ClickableKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;

import com.android.settingslib.spa.framework.util.EntryHighlightKt;
import com.android.settingslib.spa.framework.util.EntryLoggerKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class PreferenceKt {
    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.settingslib.spa.widget.preference.PreferenceKt$Preference$1, kotlin.jvm.internal.Lambda] */
    public static final void Preference(
            final PreferenceModel model,
            final boolean z,
            Composer composer,
            final int i,
            final int i2) {
        int i3;
        Intrinsics.checkNotNullParameter(model, "model");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1244008091);
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
            i3 |= composerImpl.changed(z) ? 32 : 16;
        }
        if ((i3 & 91) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (i4 != 0) {
                z = false;
            }
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Function0 wrapOnClickWithLog =
                    EntryLoggerKt.wrapOnClickWithLog(model.getOnClick(), composerImpl);
            boolean booleanValue = ((Boolean) model.getEnabled().mo1068invoke()).booleanValue();
            final Modifier modifier = Modifier.Companion.$$INSTANCE;
            if (wrapOnClickWithLog != null) {
                modifier =
                        ClickableKt.m34clickableXHw0xAI$default(
                                modifier, booleanValue, null, null, wrapOnClickWithLog, 6);
            }
            EntryHighlightKt.EntryHighlight(
                    ComposableLambdaKt.rememberComposableLambda(
                            1122297430,
                            new Function2() { // from class:
                                              // com.android.settingslib.spa.widget.preference.PreferenceKt$Preference$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                                    String title = PreferenceModel.this.getTitle();
                                    PreferenceModel.this.getClass();
                                    BasePreferenceKt.m1044BasePreferencejXF2sa8(
                                            title,
                                            PreferenceModel.this.getSummary(),
                                            modifier,
                                            null,
                                            PreferenceModel.this.getSummaryContentDescription(),
                                            z,
                                            PreferenceModel.this.getIcon(),
                                            PreferenceModel.this.getEnabled(),
                                            0.0f,
                                            0.0f,
                                            0.0f,
                                            null,
                                            composer2,
                                            0,
                                            0,
                                            3840);
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
                                      // com.android.settingslib.spa.widget.preference.PreferenceKt$Preference$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            PreferenceKt.Preference(
                                    PreferenceModel.this,
                                    z,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1),
                                    i2);
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
