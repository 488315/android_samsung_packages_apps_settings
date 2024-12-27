package com.android.settingslib.spaprivileged.template.preference;

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
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.semantics.Role;

import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.spa.widget.preference.PreferenceModel;
import com.android.settingslib.spaprivileged.model.enterprise.BlockedByAdminImpl;
import com.android.settingslib.spaprivileged.model.enterprise.RestrictedMode;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class RestrictedPreferenceModel implements PreferenceModel {
    public final Function0 enabled;
    public final Function2 icon;
    public final Function0 onClick;
    public final RestrictedMode restrictedMode;
    public final Function0 summary;
    public final String title;

    public RestrictedPreferenceModel(PreferenceModel model, RestrictedMode restrictedMode) {
        Intrinsics.checkNotNullParameter(model, "model");
        this.restrictedMode = restrictedMode;
        this.title = model.getTitle();
        this.summary = model.getSummary();
        this.icon = model.getIcon();
        this.enabled = RestrictedPreferenceKt.restrictEnabled(restrictedMode, model.getEnabled());
        this.onClick =
                (Function0)
                        RestrictedPreferenceKt.restrictOnClick(restrictedMode, model.getOnClick());
    }

    public final void RestrictionWrapper(final Function2 content, Composer composer, final int i) {
        int i2;
        Intrinsics.checkNotNullParameter(content, "content");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1040742008);
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
            composerImpl.startReplaceGroup(2002465214);
            if (!(this.restrictedMode instanceof BlockedByAdminImpl)) {
                content.invoke(composerImpl, Integer.valueOf(i2 & 14));
                composerImpl.end(false);
                RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
                if (endRestartGroup != null) {
                    endRestartGroup.block =
                            new Function2() { // from class:
                                              // com.android.settingslib.spaprivileged.template.preference.RestrictedPreferenceModel$RestrictionWrapper$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(2);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj, Object obj2) {
                                    ((Number) obj2).intValue();
                                    RestrictedPreferenceModel.this.RestrictionWrapper(
                                            content,
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
            Modifier m34clickableXHw0xAI$default =
                    ClickableKt.m34clickableXHw0xAI$default(
                            Modifier.Companion.$$INSTANCE,
                            false,
                            null,
                            new Role(0),
                            new Function0() { // from class:
                                              // com.android.settingslib.spaprivileged.template.preference.RestrictedPreferenceModel$RestrictionWrapper$2
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                /* renamed from: invoke */
                                public final Object mo1068invoke() {
                                    BlockedByAdminImpl blockedByAdminImpl =
                                            (BlockedByAdminImpl)
                                                    RestrictedPreferenceModel.this.restrictedMode;
                                    RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                                            blockedByAdminImpl.context,
                                            blockedByAdminImpl.enforcedAdmin);
                                    return Unit.INSTANCE;
                                }
                            },
                            3);
            MeasurePolicy maybeCachedBoxMeasurePolicy =
                    BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int i3 = composerImpl.compoundKeyHash;
            PersistentCompositionLocalMap currentCompositionLocalScope =
                    composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier =
                    ComposedModifierKt.materializeModifier(
                            composerImpl, m34clickableXHw0xAI$default);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (!(composerImpl.applier instanceof Applier)) {
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
                    || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i3))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(i3, composerImpl, i3, function2);
            }
            Updater.m221setimpl(
                    composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            SimpleLayoutKt$$ExternalSyntheticOutline0.m(i2 & 14, content, composerImpl, true);
        }
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            endRestartGroup2.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.preference.RestrictedPreferenceModel$RestrictionWrapper$4
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            RestrictedPreferenceModel.this.RestrictionWrapper(
                                    content,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
    public final Function0 getEnabled() {
        return this.enabled;
    }

    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
    public final Function2 getIcon() {
        return this.icon;
    }

    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
    public final Function0 getOnClick() {
        return this.onClick;
    }

    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
    public final Function0 getSummary() {
        return this.summary;
    }

    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
    public final String getTitle() {
        return this.title;
    }
}
