package com.android.settingslib.spa.widget.preference;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.FillElement;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.selection.SelectableGroupKt;
import androidx.compose.foundation.selection.SelectableKt;
import androidx.compose.material3.RadioButtonKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.semantics.Role;

import com.android.settingslib.spa.framework.theme.SettingsDimension;
import com.android.settingslib.spa.widget.ui.CategoryKt;
import com.android.settingslib.spa.widget.ui.TextKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class RadioPreferencesKt {
    public static final void Radio2(
            final ListPreferenceOption option,
            final int i,
            final boolean z,
            final Function1 onIdSelected,
            Composer composer,
            final int i2) {
        int i3;
        Intrinsics.checkNotNullParameter(option, "option");
        Intrinsics.checkNotNullParameter(onIdSelected, "onIdSelected");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(723839285);
        if ((i2 & 14) == 0) {
            i3 = (composerImpl.changed(option) ? 4 : 2) | i2;
        } else {
            i3 = i2;
        }
        if ((i2 & 112) == 0) {
            i3 |= composerImpl.changed(i) ? 32 : 16;
        }
        if ((i2 & 896) == 0) {
            i3 |= composerImpl.changed(z) ? 256 : 128;
        }
        if ((i2 & 7168) == 0) {
            i3 |= composerImpl.changedInstance(onIdSelected) ? 2048 : 1024;
        }
        int i4 = i3;
        if ((i4 & 5851) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            boolean z2 = option.id == i;
            Modifier.Companion companion = Modifier.Companion.$$INSTANCE;
            FillElement fillElement = SizeKt.FillWholeMaxWidth;
            Role role = new Role(3);
            composerImpl.startReplaceGroup(1713074366);
            boolean z3 = ((i4 & 14) == 4) | ((i4 & 7168) == 2048);
            Object rememberedValue = composerImpl.rememberedValue();
            if (z3 || rememberedValue == Composer.Companion.Empty) {
                rememberedValue =
                        new Function0() { // from class:
                                          // com.android.settingslib.spa.widget.preference.RadioPreferencesKt$Radio2$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                Function1.this.invoke(Integer.valueOf(option.id));
                                return Unit.INSTANCE;
                            }
                        };
                composerImpl.updateRememberedValue(rememberedValue);
            }
            composerImpl.end(false);
            Modifier padding =
                    PaddingKt.padding(
                            SelectableKt.m119selectableXHw0xAI(
                                    fillElement, z2, z, role, (Function0) rememberedValue),
                            SettingsDimension.dialogItemPadding);
            RowMeasurePolicy rowMeasurePolicy =
                    RowKt.rowMeasurePolicy(
                            Arrangement.Start,
                            Alignment.Companion.CenterVertically,
                            composerImpl,
                            48);
            int i5 = composerImpl.compoundKeyHash;
            PersistentCompositionLocalMap currentCompositionLocalScope =
                    composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier =
                    ComposedModifierKt.materializeModifier(composerImpl, padding);
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
                    composerImpl, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m221setimpl(
                    composerImpl,
                    currentCompositionLocalScope,
                    ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting
                    || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i5))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(i5, composerImpl, i5, function2);
            }
            Updater.m221setimpl(
                    composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            RadioButtonKt.RadioButton(
                    z2, null, null, z, null, null, composerImpl, ((i4 << 3) & 7168) | 48, 52);
            SpacerKt.Spacer(
                    composerImpl,
                    SizeKt.m100width3ABfNKs(companion, SettingsDimension.itemDividerHeight));
            TextKt.SettingsListItem(option.text, z, composerImpl, (i4 >> 3) & 112, 0);
            composerImpl.end(true);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.preference.RadioPreferencesKt$Radio2$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            RadioPreferencesKt.Radio2(
                                    ListPreferenceOption.this,
                                    i,
                                    z,
                                    onIdSelected,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i2 | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public static final void RadioPreferences(
            final ListPreferenceModel model, Composer composer, final int i) {
        int i2;
        Intrinsics.checkNotNullParameter(model, "model");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1195818891);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(model) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            CategoryKt.CategoryTitle(model.getTitle(), composerImpl, 0);
            Modifier.Companion companion = Modifier.Companion.$$INSTANCE;
            SpacerKt.Spacer(
                    composerImpl,
                    SizeKt.m100width3ABfNKs(companion, SettingsDimension.itemDividerHeight));
            Modifier selectableGroup = SelectableGroupKt.selectableGroup(companion);
            ColumnMeasurePolicy columnMeasurePolicy =
                    ColumnKt.columnMeasurePolicy(
                            Arrangement.Top, Alignment.Companion.Start, composerImpl, 0);
            int i3 = composerImpl.compoundKeyHash;
            PersistentCompositionLocalMap currentCompositionLocalScope =
                    composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier =
                    ComposedModifierKt.materializeModifier(composerImpl, selectableGroup);
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
                    composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
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
            composerImpl.startReplaceGroup(-931940174);
            Iterator it = model.getOptions().iterator();
            while (it.hasNext()) {
                Radio2(
                        (ListPreferenceOption) it.next(),
                        ((SnapshotMutableIntStateImpl) model.getSelectedId()).getIntValue(),
                        ((Boolean) model.getEnabled().mo1068invoke()).booleanValue(),
                        new Function1() { // from class:
                                          // com.android.settingslib.spa.widget.preference.RadioPreferencesKt$RadioPreferences$1$1
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                ListPreferenceModel.this
                                        .getOnIdSelected()
                                        .invoke(Integer.valueOf(((Number) obj).intValue()));
                                return Unit.INSTANCE;
                            }
                        },
                        composerImpl,
                        0);
            }
            composerImpl.end(false);
            composerImpl.end(true);
            OpaqueKey opaqueKey2 = ComposerKt.invocation;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.preference.RadioPreferencesKt$RadioPreferences$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            RadioPreferencesKt.RadioPreferences(
                                    ListPreferenceModel.this,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
