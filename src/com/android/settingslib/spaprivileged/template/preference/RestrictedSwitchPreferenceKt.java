package com.android.settingslib.spaprivileged.template.preference;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;

import com.android.settingslib.spa.widget.preference.SwitchPreferenceKt;
import com.android.settingslib.spa.widget.preference.SwitchPreferenceModel;
import com.android.settingslib.spaprivileged.model.enterprise.Restrictions;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class RestrictedSwitchPreferenceKt {
    public static final void RestrictedSwitchPreference(
            final SwitchPreferenceModel model,
            final Restrictions restrictions,
            final Function2 restrictionsProviderFactory,
            Composer composer,
            final int i) {
        Intrinsics.checkNotNullParameter(model, "model");
        Intrinsics.checkNotNullParameter(restrictions, "restrictions");
        Intrinsics.checkNotNullParameter(
                restrictionsProviderFactory, "restrictionsProviderFactory");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-662267579);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        composerImpl.startReplaceGroup(377743030);
        if (restrictions.keys.isEmpty() && restrictions.enhancedConfirmation == null) {
            SwitchPreferenceKt.SwitchPreference(model, composerImpl, 8);
            composerImpl.end(false);
            RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
            if (endRestartGroup != null) {
                endRestartGroup.block =
                        new Function2() { // from class:
                                          // com.android.settingslib.spaprivileged.template.preference.RestrictedSwitchPreferenceKt$RestrictedSwitchPreference$3
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                RestrictedSwitchPreferenceKt.RestrictedSwitchPreference(
                                        SwitchPreferenceModel.this,
                                        restrictions,
                                        restrictionsProviderFactory,
                                        (Composer) obj,
                                        RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                return Unit.INSTANCE;
                            }
                        };
                return;
            }
            return;
        }
        composerImpl.end(false);
        RestrictedSwitchPreferenceModel.Companion.RestrictedSwitchWrapper(
                restrictionsProviderFactory,
                model,
                restrictions,
                ComposableSingletons$RestrictedSwitchPreferenceKt.f105lambda1,
                composerImpl,
                ((i >> 6) & 14) | 28224);
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            endRestartGroup2.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.preference.RestrictedSwitchPreferenceKt$RestrictedSwitchPreference$4
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            RestrictedSwitchPreferenceKt.RestrictedSwitchPreference(
                                    SwitchPreferenceModel.this,
                                    restrictions,
                                    restrictionsProviderFactory,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
