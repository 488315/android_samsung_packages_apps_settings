package com.android.settingslib.spa.widget.preference;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.ScrollKt;
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
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.StructuralEqualityPolicy;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.saveable.RememberSaveableKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.semantics.Role;

import com.android.settingslib.spa.framework.theme.SettingsDimension;
import com.android.settingslib.spa.widget.dialog.SettingsDialogKt;
import com.android.settingslib.spa.widget.ui.TextKt;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ListPreferenceKt {
    /* JADX WARN: Type inference failed for: r4v3, types: [com.android.settingslib.spa.widget.preference.ListPreferenceKt$ListPreference$2, kotlin.jvm.internal.Lambda] */
    public static final void ListPreference(
            final ListPreferenceModel model, Composer composer, final int i) {
        int i2;
        Intrinsics.checkNotNullParameter(model, "model");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1522305333);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(model) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            final MutableState mutableState =
                    (MutableState)
                            RememberSaveableKt.rememberSaveable(
                                    new Object[0],
                                    null,
                                    new Function0() { // from class:
                                                      // com.android.settingslib.spa.widget.preference.ListPreferenceKt$ListPreference$dialogOpened$2
                                        @Override // kotlin.jvm.functions.Function0
                                        /* renamed from: invoke */
                                        public final Object mo1068invoke() {
                                            return SnapshotStateKt.mutableStateOf(
                                                    Boolean.FALSE,
                                                    StructuralEqualityPolicy.INSTANCE);
                                        }
                                    },
                                    composerImpl,
                                    3080,
                                    6);
            composerImpl.startReplaceGroup(230223281);
            boolean booleanValue = ((Boolean) mutableState.getValue()).booleanValue();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (booleanValue) {
                String title = model.getTitle();
                composerImpl.startReplaceGroup(230223389);
                boolean changed = composerImpl.changed(mutableState);
                Object rememberedValue = composerImpl.rememberedValue();
                if (changed || rememberedValue == composer$Companion$Empty$1) {
                    rememberedValue =
                            new Function0() { // from class:
                                              // com.android.settingslib.spa.widget.preference.ListPreferenceKt$ListPreference$1$1
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                /* renamed from: invoke */
                                public final Object mo1068invoke() {
                                    MutableState.this.setValue(Boolean.FALSE);
                                    return Unit.INSTANCE;
                                }
                            };
                    composerImpl.updateRememberedValue(rememberedValue);
                }
                composerImpl.end(false);
                SettingsDialogKt.SettingsDialog(
                        title,
                        (Function0) rememberedValue,
                        ComposableLambdaKt.rememberComposableLambda(
                                -534643978,
                                new Function2() { // from class:
                                                  // com.android.settingslib.spa.widget.preference.ListPreferenceKt$ListPreference$2
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
                                        Modifier verticalScroll$default =
                                                ScrollKt.verticalScroll$default(
                                                        SelectableGroupKt.selectableGroup(
                                                                Modifier.Companion.$$INSTANCE),
                                                        ScrollKt.rememberScrollState(composer2));
                                        final ListPreferenceModel listPreferenceModel =
                                                ListPreferenceModel.this;
                                        final MutableState mutableState2 = mutableState;
                                        ColumnMeasurePolicy columnMeasurePolicy =
                                                ColumnKt.columnMeasurePolicy(
                                                        Arrangement.Top,
                                                        Alignment.Companion.Start,
                                                        composer2,
                                                        0);
                                        int currentCompositeKeyHash =
                                                ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                        PersistentCompositionLocalMap currentCompositionLocalScope =
                                                composerImpl3.currentCompositionLocalScope();
                                        Modifier materializeModifier =
                                                ComposedModifierKt.materializeModifier(
                                                        composer2, verticalScroll$default);
                                        ComposeUiNode.Companion.getClass();
                                        Function0 function0 = ComposeUiNode.Companion.Constructor;
                                        if (!(composerImpl3.applier instanceof Applier)) {
                                            ComposablesKt.invalidApplier();
                                            throw null;
                                        }
                                        composerImpl3.startReusableNode();
                                        if (composerImpl3.inserting) {
                                            composerImpl3.createNode(function0);
                                        } else {
                                            composerImpl3.useNode();
                                        }
                                        Updater.m221setimpl(
                                                composer2,
                                                columnMeasurePolicy,
                                                ComposeUiNode.Companion.SetMeasurePolicy);
                                        Updater.m221setimpl(
                                                composer2,
                                                currentCompositionLocalScope,
                                                ComposeUiNode.Companion
                                                        .SetResolvedCompositionLocals);
                                        Function2 function2 =
                                                ComposeUiNode.Companion.SetCompositeKeyHash;
                                        if (composerImpl3.inserting
                                                || !Intrinsics.areEqual(
                                                        composerImpl3.rememberedValue(),
                                                        Integer.valueOf(currentCompositeKeyHash))) {
                                            AnimatedContentKt$$ExternalSyntheticOutline0.m(
                                                    currentCompositeKeyHash,
                                                    composerImpl3,
                                                    currentCompositeKeyHash,
                                                    function2);
                                        }
                                        Updater.m221setimpl(
                                                composer2,
                                                materializeModifier,
                                                ComposeUiNode.Companion.SetModifier);
                                        composerImpl3.startReplaceGroup(-163256618);
                                        Iterator it = listPreferenceModel.getOptions().iterator();
                                        while (it.hasNext()) {
                                            ListPreferenceKt.access$Radio(
                                                    (ListPreferenceOption) it.next(),
                                                    ((SnapshotMutableIntStateImpl)
                                                                    listPreferenceModel
                                                                            .getSelectedId())
                                                            .getIntValue(),
                                                    ((Boolean)
                                                                    listPreferenceModel
                                                                            .getEnabled()
                                                                            .mo1068invoke())
                                                            .booleanValue(),
                                                    new Function1() { // from class:
                                                                      // com.android.settingslib.spa.widget.preference.ListPreferenceKt$ListPreference$2$1$1
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(1);
                                                        }

                                                        @Override // kotlin.jvm.functions.Function1
                                                        public final Object invoke(Object obj3) {
                                                            int intValue =
                                                                    ((Number) obj3).intValue();
                                                            mutableState2.setValue(Boolean.FALSE);
                                                            ListPreferenceModel.this
                                                                    .getOnIdSelected()
                                                                    .invoke(
                                                                            Integer.valueOf(
                                                                                    intValue));
                                                            return Unit.INSTANCE;
                                                        }
                                                    },
                                                    composer2,
                                                    0);
                                        }
                                        composerImpl3.end(false);
                                        composerImpl3.end(true);
                                        OpaqueKey opaqueKey3 = ComposerKt.invocation;
                                        return Unit.INSTANCE;
                                    }
                                },
                                composerImpl),
                        composerImpl,
                        384);
            }
            composerImpl.end(false);
            composerImpl.startReplaceGroup(230223838);
            boolean z = (i2 & 14) == 4 || ((i2 & 8) != 0 && composerImpl.changed(model));
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (z || rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 =
                        new PreferenceModel(
                                model,
                                mutableState) { // from class:
                                                // com.android.settingslib.spa.widget.preference.ListPreferenceKt$ListPreference$3$1
                            public final Function0 enabled;
                            public final Function2 icon;
                            public final Function0 onClick;
                            public final Function0 summary;
                            public final String title;

                            {
                                this.title = model.getTitle();
                                this.summary =
                                        new Function0() { // from class:
                                                          // com.android.settingslib.spa.widget.preference.ListPreferenceKt$ListPreference$3$1$summary$1
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            /* renamed from: invoke */
                                            public final Object mo1068invoke() {
                                                Object obj;
                                                String str;
                                                List options =
                                                        ListPreferenceModel.this.getOptions();
                                                ListPreferenceModel listPreferenceModel =
                                                        ListPreferenceModel.this;
                                                Iterator it = options.iterator();
                                                while (true) {
                                                    if (!it.hasNext()) {
                                                        obj = null;
                                                        break;
                                                    }
                                                    obj = it.next();
                                                    if (((ListPreferenceOption) obj).id
                                                            == ((SnapshotMutableIntStateImpl)
                                                                            listPreferenceModel
                                                                                    .getSelectedId())
                                                                    .getIntValue()) {
                                                        break;
                                                    }
                                                }
                                                ListPreferenceOption listPreferenceOption =
                                                        (ListPreferenceOption) obj;
                                                return (listPreferenceOption == null
                                                                || (str = listPreferenceOption.text)
                                                                        == null)
                                                        ? ApnSettings.MVNO_NONE
                                                        : str;
                                            }
                                        };
                                this.icon = model.getIcon();
                                this.enabled = model.getEnabled();
                                this.onClick =
                                        model.getOptions().isEmpty() ^ true
                                                ? new Function0() { // from class:
                                                                    // com.android.settingslib.spa.widget.preference.ListPreferenceKt$ListPreference$3$1$onClick$1
                                                    {
                                                        super(0);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function0
                                                    /* renamed from: invoke */
                                                    public final Object mo1068invoke() {
                                                        MutableState.this.setValue(Boolean.TRUE);
                                                        return Unit.INSTANCE;
                                                    }
                                                }
                                                : null;
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
                        };
                composerImpl.updateRememberedValue(rememberedValue2);
            }
            composerImpl.end(false);
            PreferenceKt.Preference(
                    (ListPreferenceKt$ListPreference$3$1) rememberedValue2,
                    false,
                    composerImpl,
                    0,
                    2);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.preference.ListPreferenceKt$ListPreference$4
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            ListPreferenceKt.ListPreference(
                                    ListPreferenceModel.this,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public static final void access$Radio(
            final ListPreferenceOption listPreferenceOption,
            final int i,
            final boolean z,
            final Function1 function1,
            Composer composer,
            final int i2) {
        int i3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1608089327);
        if ((i2 & 14) == 0) {
            i3 = (composerImpl.changed(listPreferenceOption) ? 4 : 2) | i2;
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
            i3 |= composerImpl.changedInstance(function1) ? 2048 : 1024;
        }
        int i4 = i3;
        if ((i4 & 5851) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            boolean z2 = listPreferenceOption.id == i;
            Modifier.Companion companion = Modifier.Companion.$$INSTANCE;
            FillElement fillElement = SizeKt.FillWholeMaxWidth;
            Role role = new Role(3);
            composerImpl.startReplaceGroup(-83282774);
            boolean z3 = ((i4 & 14) == 4) | ((i4 & 7168) == 2048);
            Object rememberedValue = composerImpl.rememberedValue();
            if (z3 || rememberedValue == Composer.Companion.Empty) {
                rememberedValue =
                        new Function0() { // from class:
                                          // com.android.settingslib.spa.widget.preference.ListPreferenceKt$Radio$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                Function1.this.invoke(Integer.valueOf(listPreferenceOption.id));
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
            boolean z4 = composerImpl.applier instanceof Applier;
            if (!z4) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
            Updater.m221setimpl(composerImpl, rowMeasurePolicy, function2);
            Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m221setimpl(composerImpl, currentCompositionLocalScope, function22);
            Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting
                    || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i5))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(i5, composerImpl, i5, function23);
            }
            Function2 function24 = ComposeUiNode.Companion.SetModifier;
            Updater.m221setimpl(composerImpl, materializeModifier, function24);
            RadioButtonKt.RadioButton(
                    z2, null, null, z, null, null, composerImpl, ((i4 << 3) & 7168) | 48, 52);
            SpacerKt.Spacer(
                    composerImpl,
                    SizeKt.m100width3ABfNKs(companion, SettingsDimension.itemPaddingEnd));
            ColumnMeasurePolicy columnMeasurePolicy =
                    ColumnKt.columnMeasurePolicy(
                            Arrangement.Top, Alignment.Companion.Start, composerImpl, 0);
            int i6 = composerImpl.compoundKeyHash;
            PersistentCompositionLocalMap currentCompositionLocalScope2 =
                    composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier2 =
                    ComposedModifierKt.materializeModifier(composerImpl, companion);
            if (!z4) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m221setimpl(composerImpl, columnMeasurePolicy, function2);
            Updater.m221setimpl(composerImpl, currentCompositionLocalScope2, function22);
            if (composerImpl.inserting
                    || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i6))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(i6, composerImpl, i6, function23);
            }
            Updater.m221setimpl(composerImpl, materializeModifier2, function24);
            TextKt.SettingsDialogItem(
                    listPreferenceOption.text, z, composerImpl, (i4 >> 3) & 112, 0);
            composerImpl.startReplaceGroup(-1812238187);
            if (!Intrinsics.areEqual(listPreferenceOption.summary, new String())) {
                TextKt.SettingsBody(listPreferenceOption.summary, null, 1, composerImpl, 384, 2);
            }
            composerImpl.end(false);
            composerImpl.end(true);
            composerImpl.end(true);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.preference.ListPreferenceKt$Radio$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            ListPreferenceKt.access$Radio(
                                    ListPreferenceOption.this,
                                    i,
                                    z,
                                    function1,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i2 | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
