package com.android.settingslib.spaprivileged.template.preference;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;

import com.android.settingslib.spa.widget.preference.SwitchPreferenceModel;
import com.android.settingslib.spa.widget.preference.TwoTargetSwitchPreferenceKt;
import com.android.settingslib.spaprivileged.model.enterprise.RestrictedMode;
import com.android.settingslib.spaprivileged.model.enterprise.Restrictions;
import com.android.settingslib.spaprivileged.model.enterprise.RestrictionsProviderKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class RestrictedTwoTargetSwitchPreferenceKt {
    public static final void RestrictedTwoTargetSwitchPreference(
            final SwitchPreferenceModel model,
            final Restrictions restrictions,
            Function0 function0,
            final Function0 function02,
            Composer composer,
            final int i,
            final int i2) {
        Intrinsics.checkNotNullParameter(model, "model");
        Intrinsics.checkNotNullParameter(restrictions, "restrictions");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-318029233);
        if ((i2 & 4) != 0) {
            function0 =
                    new Function0() { // from class:
                                      // com.android.settingslib.spaprivileged.template.preference.RestrictedTwoTargetSwitchPreferenceKt$RestrictedTwoTargetSwitchPreference$1
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final /* bridge */ /* synthetic */ Object mo1068invoke() {
                            return Boolean.TRUE;
                        }
                    };
        }
        OpaqueKey opaqueKey = ComposerKt.invocation;
        int i3 = i >> 3;
        RestrictedTwoTargetSwitchPreference(
                model,
                function0,
                function02,
                restrictions,
                RestrictedTwoTargetSwitchPreferenceKt$RestrictedTwoTargetSwitchPreference$2
                        .INSTANCE,
                composerImpl,
                (i3 & 112) | 4104 | (i3 & 896),
                0);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            final Function0 function03 = function0;
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.preference.RestrictedTwoTargetSwitchPreferenceKt$RestrictedTwoTargetSwitchPreference$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            RestrictedTwoTargetSwitchPreferenceKt
                                    .RestrictedTwoTargetSwitchPreference(
                                            SwitchPreferenceModel.this,
                                            restrictions,
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

    /* JADX WARN: Type inference failed for: r3v8, types: [com.android.settingslib.spaprivileged.template.preference.RestrictedTwoTargetSwitchPreferenceKt$RestrictedTwoTargetSwitchPreference$6, kotlin.jvm.internal.Lambda] */
    public static final void RestrictedTwoTargetSwitchPreference(
            final SwitchPreferenceModel model,
            Function0 function0,
            final Function0 function02,
            final Restrictions restrictions,
            final Function2 restrictionsProviderFactory,
            Composer composer,
            final int i,
            final int i2) {
        Intrinsics.checkNotNullParameter(model, "model");
        Intrinsics.checkNotNullParameter(restrictions, "restrictions");
        Intrinsics.checkNotNullParameter(
                restrictionsProviderFactory, "restrictionsProviderFactory");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(2041831217);
        final Function0 function03 =
                (i2 & 2) != 0
                        ? new Function0() { // from class:
                                            // com.android.settingslib.spaprivileged.template.preference.RestrictedTwoTargetSwitchPreferenceKt$RestrictedTwoTargetSwitchPreference$4
                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final /* bridge */ /* synthetic */ Object mo1068invoke() {
                                return Boolean.TRUE;
                            }
                        }
                        : function0;
        OpaqueKey opaqueKey = ComposerKt.invocation;
        composerImpl.startReplaceGroup(1589643820);
        if (restrictions.keys.isEmpty() && restrictions.enhancedConfirmation == null) {
            TwoTargetSwitchPreferenceKt.TwoTargetSwitchPreference(
                    model, function03, function02, composerImpl, (i & 112) | 8 | (i & 896), 0);
            composerImpl.end(false);
            RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
            if (endRestartGroup != null) {
                endRestartGroup.block =
                        new Function2() { // from class:
                                          // com.android.settingslib.spaprivileged.template.preference.RestrictedTwoTargetSwitchPreferenceKt$RestrictedTwoTargetSwitchPreference$5
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                RestrictedTwoTargetSwitchPreferenceKt
                                        .RestrictedTwoTargetSwitchPreference(
                                                SwitchPreferenceModel.this,
                                                function03,
                                                function02,
                                                restrictions,
                                                restrictionsProviderFactory,
                                                (Composer) obj,
                                                RecomposeScopeImplKt.updateChangedFlags(i | 1),
                                                i2);
                                return Unit.INSTANCE;
                            }
                        };
                return;
            }
            return;
        }
        composerImpl.end(false);
        final RestrictedMode restrictedMode =
                (RestrictedMode)
                        RestrictionsProviderKt.rememberRestrictedMode(
                                        restrictionsProviderFactory, restrictions, composerImpl)
                                .getValue();
        RestrictedSwitchPreferenceModel.Companion.RestrictedSwitchWrapper(
                model,
                restrictedMode,
                ComposableLambdaKt.rememberComposableLambda(
                        -616848596,
                        new Function3() { // from class:
                                          // com.android.settingslib.spaprivileged.template.preference.RestrictedTwoTargetSwitchPreferenceKt$RestrictedTwoTargetSwitchPreference$6
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(3);
                            }

                            @Override // kotlin.jvm.functions.Function3
                            public final Object invoke(Object obj, Object obj2, Object obj3) {
                                SwitchPreferenceModel restrictedModel = (SwitchPreferenceModel) obj;
                                ((Number) obj3).intValue();
                                Intrinsics.checkNotNullParameter(
                                        restrictedModel, "restrictedModel");
                                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                TwoTargetSwitchPreferenceKt.TwoTargetSwitchPreference(
                                        restrictedModel,
                                        RestrictedPreferenceKt.restrictEnabled(
                                                RestrictedMode.this, function03),
                                        (Function0)
                                                RestrictedPreferenceKt.restrictOnClick(
                                                        RestrictedMode.this, function02),
                                        (Composer) obj2,
                                        8,
                                        0);
                                return Unit.INSTANCE;
                            }
                        },
                        composerImpl),
                composerImpl,
                3464);
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            endRestartGroup2.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.preference.RestrictedTwoTargetSwitchPreferenceKt$RestrictedTwoTargetSwitchPreference$7
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            RestrictedTwoTargetSwitchPreferenceKt
                                    .RestrictedTwoTargetSwitchPreference(
                                            SwitchPreferenceModel.this,
                                            function03,
                                            function02,
                                            restrictions,
                                            restrictionsProviderFactory,
                                            (Composer) obj,
                                            RecomposeScopeImplKt.updateChangedFlags(i | 1),
                                            i2);
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
