package com.android.settingslib.spaprivileged.template.preference;

import android.content.Context;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.text.selection.SimpleLayoutKt$$ExternalSyntheticOutline0;
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
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.semantics.Role;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.state.ToggleableState;

import com.android.settings.R;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.spa.widget.preference.SwitchPreferenceModel;
import com.android.settingslib.spaprivileged.framework.compose.StringResourcesKt;
import com.android.settingslib.spaprivileged.model.enterprise.BaseUserRestricted;
import com.android.settingslib.spaprivileged.model.enterprise.BlockedByAdminImpl;
import com.android.settingslib.spaprivileged.model.enterprise.BlockedByEcmImpl;
import com.android.settingslib.spaprivileged.model.enterprise.EnterpriseRepository;
import com.android.settingslib.spaprivileged.model.enterprise.NoRestricted;
import com.android.settingslib.spaprivileged.model.enterprise.RestrictedMode;
import com.android.settingslib.spaprivileged.model.enterprise.Restrictions;
import com.android.settingslib.spaprivileged.model.enterprise.RestrictionsProviderKt;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class RestrictedSwitchPreferenceModel implements SwitchPreferenceModel {
    public static final Companion Companion = new Companion();
    public final Function0 changeable;
    public final Function0 checked;
    public final Function2 icon;
    public final Function1 onCheckedChange;
    public final RestrictedMode restrictedMode;
    public final Function0 summary;
    public final String title;

    public RestrictedSwitchPreferenceModel(
            Context context, SwitchPreferenceModel model, RestrictedMode restrictedMode) {
        Function0 checked;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(model, "model");
        this.restrictedMode = restrictedMode;
        this.title = model.getTitle();
        this.summary =
                Companion.getSummary(
                        context,
                        new Function0() { // from class:
                                          // com.android.settingslib.spaprivileged.template.preference.RestrictedSwitchPreferenceModel$summary$1
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                return RestrictedSwitchPreferenceModel.this.restrictedMode;
                            }
                        },
                        model.getSummary(),
                        model.getChecked());
        this.icon = model.getIcon();
        if (restrictedMode == null) {
            checked =
                    new Function0() { // from class:
                                      // com.android.settingslib.spaprivileged.template.preference.RestrictedSwitchPreferenceModel$checked$1
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final /* bridge */ /* synthetic */ Object mo1068invoke() {
                            return null;
                        }
                    };
        } else if (restrictedMode instanceof NoRestricted) {
            checked = model.getChecked();
        } else if (restrictedMode instanceof BaseUserRestricted) {
            checked =
                    new Function0() { // from class:
                                      // com.android.settingslib.spaprivileged.template.preference.RestrictedSwitchPreferenceModel$checked$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final /* bridge */ /* synthetic */ Object mo1068invoke() {
                            return Boolean.FALSE;
                        }
                    };
        } else if (restrictedMode instanceof BlockedByAdminImpl) {
            checked = model.getChecked();
        } else {
            if (!(restrictedMode instanceof BlockedByEcmImpl)) {
                throw new NoWhenBranchMatchedException();
            }
            checked = model.getChecked();
        }
        this.checked = checked;
        this.changeable =
                RestrictedPreferenceKt.restrictEnabled(restrictedMode, model.getChangeable());
        this.onCheckedChange =
                (Function1)
                        RestrictedPreferenceKt.restrictOnClick(
                                restrictedMode, model.getOnCheckedChange());
    }

    public static final ToggleableState access$ToggleableState(
            RestrictedSwitchPreferenceModel restrictedSwitchPreferenceModel, Boolean bool) {
        restrictedSwitchPreferenceModel.getClass();
        if (Intrinsics.areEqual(bool, Boolean.TRUE)) {
            return ToggleableState.On;
        }
        if (Intrinsics.areEqual(bool, Boolean.FALSE)) {
            return ToggleableState.Off;
        }
        if (bool == null) {
            return ToggleableState.Indeterminate;
        }
        throw new NoWhenBranchMatchedException();
    }

    public final void RestrictionWrapper(final Function2 content, Composer composer, final int i) {
        int i2;
        Intrinsics.checkNotNullParameter(content, "content");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1702735763);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changedInstance(content) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl.changed(this) ? 32 : 16;
        }
        if ((i2 & 91) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            RestrictedMode restrictedMode = this.restrictedMode;
            boolean z = restrictedMode instanceof BlockedByAdminImpl;
            BiasAlignment biasAlignment = Alignment.Companion.TopStart;
            Modifier.Companion companion = Modifier.Companion.$$INSTANCE;
            Applier applier = composerImpl.applier;
            if (z) {
                composerImpl.startReplaceGroup(1672988410);
                Modifier semantics =
                        SemanticsModifierKt.semantics(
                                ClickableKt.m34clickableXHw0xAI$default(
                                        companion,
                                        false,
                                        null,
                                        new Role(2),
                                        new Function0() { // from class:
                                                          // com.android.settingslib.spaprivileged.template.preference.RestrictedSwitchPreferenceModel$RestrictionWrapper$1
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            /* renamed from: invoke */
                                            public final Object mo1068invoke() {
                                                BlockedByAdminImpl blockedByAdminImpl =
                                                        (BlockedByAdminImpl)
                                                                RestrictedSwitchPreferenceModel.this
                                                                        .restrictedMode;
                                                RestrictedLockUtils
                                                        .sendShowAdminSupportDetailsIntent(
                                                                blockedByAdminImpl.context,
                                                                blockedByAdminImpl.enforcedAdmin);
                                                return Unit.INSTANCE;
                                            }
                                        },
                                        3),
                                false,
                                new Function1() { // from class:
                                                  // com.android.settingslib.spaprivileged.template.preference.RestrictedSwitchPreferenceModel$RestrictionWrapper$2
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj) {
                                        SemanticsPropertyReceiver semantics2 =
                                                (SemanticsPropertyReceiver) obj;
                                        Intrinsics.checkNotNullParameter(
                                                semantics2, "$this$semantics");
                                        RestrictedSwitchPreferenceModel
                                                restrictedSwitchPreferenceModel =
                                                        RestrictedSwitchPreferenceModel.this;
                                        SemanticsPropertiesKt.setToggleableState(
                                                semantics2,
                                                RestrictedSwitchPreferenceModel
                                                        .access$ToggleableState(
                                                                restrictedSwitchPreferenceModel,
                                                                (Boolean)
                                                                        restrictedSwitchPreferenceModel
                                                                                .checked
                                                                                .mo1068invoke()));
                                        return Unit.INSTANCE;
                                    }
                                });
                MeasurePolicy maybeCachedBoxMeasurePolicy =
                        BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                int i3 = composerImpl.compoundKeyHash;
                PersistentCompositionLocalMap currentCompositionLocalScope =
                        composerImpl.currentCompositionLocalScope();
                Modifier materializeModifier =
                        ComposedModifierKt.materializeModifier(composerImpl, semantics);
                ComposeUiNode.Companion.getClass();
                Function0 function0 = ComposeUiNode.Companion.Constructor;
                if (!(applier instanceof Applier)) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m221setimpl(
                        composerImpl,
                        maybeCachedBoxMeasurePolicy,
                        ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m221setimpl(
                        composerImpl,
                        currentCompositionLocalScope,
                        ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting
                        || !Intrinsics.areEqual(
                                composerImpl.rememberedValue(), Integer.valueOf(i3))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(i3, composerImpl, i3, function2);
                }
                Updater.m221setimpl(
                        composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
                content.invoke(composerImpl, Integer.valueOf(i2 & 14));
                composerImpl.end(true);
                composerImpl.end(false);
            } else if (restrictedMode instanceof BlockedByEcmImpl) {
                composerImpl.startReplaceGroup(1672988886);
                Modifier semantics2 =
                        SemanticsModifierKt.semantics(
                                ClickableKt.m34clickableXHw0xAI$default(
                                        companion,
                                        false,
                                        null,
                                        new Role(2),
                                        new Function0() { // from class:
                                                          // com.android.settingslib.spaprivileged.template.preference.RestrictedSwitchPreferenceModel$RestrictionWrapper$4
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            /* renamed from: invoke */
                                            public final Object mo1068invoke() {
                                                BlockedByEcmImpl blockedByEcmImpl =
                                                        (BlockedByEcmImpl)
                                                                RestrictedSwitchPreferenceModel.this
                                                                        .restrictedMode;
                                                blockedByEcmImpl.context.startActivity(
                                                        blockedByEcmImpl.intent);
                                                return Unit.INSTANCE;
                                            }
                                        },
                                        3),
                                false,
                                new Function1() { // from class:
                                                  // com.android.settingslib.spaprivileged.template.preference.RestrictedSwitchPreferenceModel$RestrictionWrapper$5
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj) {
                                        SemanticsPropertyReceiver semantics3 =
                                                (SemanticsPropertyReceiver) obj;
                                        Intrinsics.checkNotNullParameter(
                                                semantics3, "$this$semantics");
                                        RestrictedSwitchPreferenceModel
                                                restrictedSwitchPreferenceModel =
                                                        RestrictedSwitchPreferenceModel.this;
                                        SemanticsPropertiesKt.setToggleableState(
                                                semantics3,
                                                RestrictedSwitchPreferenceModel
                                                        .access$ToggleableState(
                                                                restrictedSwitchPreferenceModel,
                                                                (Boolean)
                                                                        restrictedSwitchPreferenceModel
                                                                                .checked
                                                                                .mo1068invoke()));
                                        return Unit.INSTANCE;
                                    }
                                });
                MeasurePolicy maybeCachedBoxMeasurePolicy2 =
                        BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                int i4 = composerImpl.compoundKeyHash;
                PersistentCompositionLocalMap currentCompositionLocalScope2 =
                        composerImpl.currentCompositionLocalScope();
                Modifier materializeModifier2 =
                        ComposedModifierKt.materializeModifier(composerImpl, semantics2);
                ComposeUiNode.Companion.getClass();
                Function0 function02 = ComposeUiNode.Companion.Constructor;
                if (!(applier instanceof Applier)) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function02);
                } else {
                    composerImpl.useNode();
                }
                Updater.m221setimpl(
                        composerImpl,
                        maybeCachedBoxMeasurePolicy2,
                        ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m221setimpl(
                        composerImpl,
                        currentCompositionLocalScope2,
                        ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting
                        || !Intrinsics.areEqual(
                                composerImpl.rememberedValue(), Integer.valueOf(i4))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(
                            i4, composerImpl, i4, function22);
                }
                Updater.m221setimpl(
                        composerImpl, materializeModifier2, ComposeUiNode.Companion.SetModifier);
                content.invoke(composerImpl, Integer.valueOf(i2 & 14));
                composerImpl.end(true);
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(1672989347);
                SimpleLayoutKt$$ExternalSyntheticOutline0.m(i2 & 14, content, composerImpl, false);
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.preference.RestrictedSwitchPreferenceModel$RestrictionWrapper$7
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            RestrictedSwitchPreferenceModel.this.RestrictionWrapper(
                                    content,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
    public final Function0 getChangeable() {
        return this.changeable;
    }

    @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
    public final Function0 getChecked() {
        return this.checked;
    }

    @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
    public final Function2 getIcon() {
        return this.icon;
    }

    @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
    public final Function1 getOnCheckedChange() {
        return this.onCheckedChange;
    }

    @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
    public final Function0 getSummary() {
        return this.summary;
    }

    @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
    public final String getTitle() {
        return this.title;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Companion {
        public static Function0 getSummary(
                final Context context,
                final Function0 restrictedModeSupplier,
                final Function0 summaryIfNoRestricted,
                final Function0 checked) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(restrictedModeSupplier, "restrictedModeSupplier");
            Intrinsics.checkNotNullParameter(summaryIfNoRestricted, "summaryIfNoRestricted");
            Intrinsics.checkNotNullParameter(checked, "checked");
            return new Function0() { // from class:
                                     // com.android.settingslib.spaprivileged.template.preference.RestrictedSwitchPreferenceModel$Companion$getSummary$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* renamed from: invoke */
                public final Object mo1068invoke() {
                    RestrictedMode restrictedMode =
                            (RestrictedMode) restrictedModeSupplier.mo1068invoke();
                    if (restrictedMode instanceof NoRestricted) {
                        return (String) summaryIfNoRestricted.mo1068invoke();
                    }
                    if (restrictedMode instanceof BaseUserRestricted) {
                        String string = context.getString(R.string.disabled);
                        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                        return string;
                    }
                    if (restrictedMode instanceof BlockedByAdminImpl) {
                        BlockedByAdminImpl blockedByAdminImpl = (BlockedByAdminImpl) restrictedMode;
                        Boolean bool = (Boolean) checked.mo1068invoke();
                        blockedByAdminImpl.getClass();
                        boolean areEqual = Intrinsics.areEqual(bool, Boolean.TRUE);
                        EnterpriseRepository enterpriseRepository =
                                blockedByAdminImpl.enterpriseRepository;
                        return areEqual
                                ? enterpriseRepository.getEnterpriseString(
                                        R.string.enabled_by_admin,
                                        "Settings.ENABLED_BY_ADMIN_SWITCH_SUMMARY")
                                : Intrinsics.areEqual(bool, Boolean.FALSE)
                                        ? enterpriseRepository.getEnterpriseString(
                                                R.string.disabled_by_admin,
                                                "Settings.DISABLED_BY_ADMIN_SWITCH_SUMMARY")
                                        : ApnSettings.MVNO_NONE;
                    }
                    if (restrictedMode instanceof BlockedByEcmImpl) {
                        String string2 = context.getString(R.string.disabled);
                        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                        return string2;
                    }
                    if (restrictedMode == null) {
                        return StringResourcesKt.getPlaceholder(context);
                    }
                    throw new NoWhenBranchMatchedException();
                }
            };
        }

        /* JADX WARN: Type inference failed for: r0v7, types: [com.android.settingslib.spaprivileged.template.preference.RestrictedSwitchPreferenceModel$Companion$RestrictedSwitchWrapper$1, kotlin.jvm.internal.Lambda] */
        public final void RestrictedSwitchWrapper(
                final SwitchPreferenceModel model,
                final RestrictedMode restrictedMode,
                final Function3 content,
                Composer composer,
                final int i) {
            Intrinsics.checkNotNullParameter(model, "model");
            Intrinsics.checkNotNullParameter(content, "content");
            ComposerImpl composerImpl = (ComposerImpl) composer;
            composerImpl.startRestartGroup(1653982923);
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Context context =
                    (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            composerImpl.startReplaceGroup(533095648);
            boolean changed =
                    ((((i & 112) ^ 48) > 32 && composerImpl.changed(restrictedMode))
                                    || (i & 48) == 32)
                            | composerImpl.changed(model);
            Object rememberedValue = composerImpl.rememberedValue();
            if (changed || rememberedValue == Composer.Companion.Empty) {
                rememberedValue =
                        new RestrictedSwitchPreferenceModel(context, model, restrictedMode);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            final RestrictedSwitchPreferenceModel restrictedSwitchPreferenceModel =
                    (RestrictedSwitchPreferenceModel) rememberedValue;
            composerImpl.end(false);
            restrictedSwitchPreferenceModel.RestrictionWrapper(
                    ComposableLambdaKt.rememberComposableLambda(
                            -1773126529,
                            new Function2() { // from class:
                                              // com.android.settingslib.spaprivileged.template.preference.RestrictedSwitchPreferenceModel$Companion$RestrictedSwitchWrapper$1
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
                                    Function3.this.invoke(
                                            restrictedSwitchPreferenceModel, composer2, 0);
                                    return Unit.INSTANCE;
                                }
                            },
                            composerImpl),
                    composerImpl,
                    6);
            RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
            if (endRestartGroup != null) {
                endRestartGroup.block =
                        new Function2() { // from class:
                                          // com.android.settingslib.spaprivileged.template.preference.RestrictedSwitchPreferenceModel$Companion$RestrictedSwitchWrapper$2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                RestrictedSwitchPreferenceModel.Companion.this
                                        .RestrictedSwitchWrapper(
                                                model,
                                                restrictedMode,
                                                content,
                                                (Composer) obj,
                                                RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                return Unit.INSTANCE;
                            }
                        };
            }
        }

        public final void RestrictedSwitchWrapper(
                final Function2 function2,
                final SwitchPreferenceModel model,
                final Restrictions restrictions,
                final Function3 content,
                Composer composer,
                final int i) {
            Intrinsics.checkNotNullParameter(function2, "<this>");
            Intrinsics.checkNotNullParameter(model, "model");
            Intrinsics.checkNotNullParameter(restrictions, "restrictions");
            Intrinsics.checkNotNullParameter(content, "content");
            ComposerImpl composerImpl = (ComposerImpl) composer;
            composerImpl.startRestartGroup(1017448046);
            OpaqueKey opaqueKey = ComposerKt.invocation;
            int i2 = i >> 3;
            RestrictedSwitchWrapper(
                    model,
                    (RestrictedMode)
                            RestrictionsProviderKt.rememberRestrictedMode(
                                            function2, restrictions, composerImpl)
                                    .getValue(),
                    content,
                    composerImpl,
                    (i2 & 896) | 8 | (i2 & 7168));
            RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
            if (endRestartGroup != null) {
                endRestartGroup.block =
                        new Function2() { // from class:
                                          // com.android.settingslib.spaprivileged.template.preference.RestrictedSwitchPreferenceModel$Companion$RestrictedSwitchWrapper$3
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                RestrictedSwitchPreferenceModel.Companion.this
                                        .RestrictedSwitchWrapper(
                                                function2,
                                                model,
                                                restrictions,
                                                content,
                                                (Composer) obj,
                                                RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                return Unit.INSTANCE;
                            }
                        };
            }
        }
    }
}
