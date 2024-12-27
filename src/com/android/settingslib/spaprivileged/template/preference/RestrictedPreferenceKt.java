package com.android.settingslib.spaprivileged.template.preference;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;

import com.android.settingslib.spa.widget.preference.PreferenceKt;
import com.android.settingslib.spa.widget.preference.PreferenceModel;
import com.android.settingslib.spaprivileged.model.enterprise.BaseUserRestricted;
import com.android.settingslib.spaprivileged.model.enterprise.NoRestricted;
import com.android.settingslib.spaprivileged.model.enterprise.RestrictedMode;
import com.android.settingslib.spaprivileged.model.enterprise.Restrictions;
import com.android.settingslib.spaprivileged.model.enterprise.RestrictionsProviderKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class RestrictedPreferenceKt {
    public static final void RestrictedPreference(
            final PreferenceModel model,
            final Restrictions restrictions,
            Composer composer,
            final int i) {
        Intrinsics.checkNotNullParameter(model, "model");
        Intrinsics.checkNotNullParameter(restrictions, "restrictions");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1355795893);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        RestrictedPreference(
                model,
                restrictions,
                RestrictedPreferenceKt$RestrictedPreference$1.INSTANCE,
                composerImpl,
                72);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.preference.RestrictedPreferenceKt$RestrictedPreference$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            RestrictedPreferenceKt.RestrictedPreference(
                                    PreferenceModel.this,
                                    restrictions,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public static final Function0 restrictEnabled(
            RestrictedMode restrictedMode, Function0 enabled) {
        Intrinsics.checkNotNullParameter(enabled, "enabled");
        return Intrinsics.areEqual(restrictedMode, NoRestricted.INSTANCE)
                ? enabled
                : new Function0() { // from class:
                                    // com.android.settingslib.spaprivileged.template.preference.RestrictedPreferenceKt$restrictEnabled$1
                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final /* bridge */ /* synthetic */ Object mo1068invoke() {
                        return Boolean.FALSE;
                    }
                };
    }

    public static final Object restrictOnClick(RestrictedMode restrictedMode, Object obj) {
        if (Intrinsics.areEqual(restrictedMode, NoRestricted.INSTANCE)
                || Intrinsics.areEqual(restrictedMode, BaseUserRestricted.INSTANCE)) {
            return obj;
        }
        return null;
    }

    /* JADX WARN: Type inference failed for: r0v11, types: [com.android.settingslib.spaprivileged.template.preference.RestrictedPreferenceKt$RestrictedPreference$4, kotlin.jvm.internal.Lambda] */
    public static final void RestrictedPreference(
            final PreferenceModel model,
            final Restrictions restrictions,
            final Function2 restrictionsProviderFactory,
            Composer composer,
            final int i) {
        Intrinsics.checkNotNullParameter(model, "model");
        Intrinsics.checkNotNullParameter(restrictions, "restrictions");
        Intrinsics.checkNotNullParameter(
                restrictionsProviderFactory, "restrictionsProviderFactory");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-141433959);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        composerImpl.startReplaceGroup(2080394618);
        if (restrictions.keys.isEmpty()) {
            PreferenceKt.Preference(model, false, composerImpl, 8, 2);
            composerImpl.end(false);
            RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
            if (endRestartGroup != null) {
                endRestartGroup.block =
                        new Function2() { // from class:
                                          // com.android.settingslib.spaprivileged.template.preference.RestrictedPreferenceKt$RestrictedPreference$3
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                RestrictedPreferenceKt.RestrictedPreference(
                                        PreferenceModel.this,
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
        RestrictedMode restrictedMode =
                (RestrictedMode)
                        RestrictionsProviderKt.rememberRestrictedMode(
                                        restrictionsProviderFactory, restrictions, composerImpl)
                                .getValue();
        composerImpl.startReplaceGroup(2080394822);
        boolean changed = composerImpl.changed(restrictedMode);
        Object rememberedValue = composerImpl.rememberedValue();
        if (changed || rememberedValue == Composer.Companion.Empty) {
            rememberedValue = new RestrictedPreferenceModel(model, restrictedMode);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        final RestrictedPreferenceModel restrictedPreferenceModel =
                (RestrictedPreferenceModel) rememberedValue;
        composerImpl.end(false);
        restrictedPreferenceModel.RestrictionWrapper(
                ComposableLambdaKt.rememberComposableLambda(
                        1134552909,
                        new Function2() { // from class:
                                          // com.android.settingslib.spaprivileged.template.preference.RestrictedPreferenceKt$RestrictedPreference$4
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
                                PreferenceKt.Preference(
                                        RestrictedPreferenceModel.this, false, composer2, 0, 2);
                                return Unit.INSTANCE;
                            }
                        },
                        composerImpl),
                composerImpl,
                6);
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            endRestartGroup2.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.preference.RestrictedPreferenceKt$RestrictedPreference$5
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            RestrictedPreferenceKt.RestrictedPreference(
                                    PreferenceModel.this,
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
