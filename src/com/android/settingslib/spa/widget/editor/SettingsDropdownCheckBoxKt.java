package com.android.settingslib.spa.widget.editor;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$End$1;
import androidx.compose.foundation.layout.FillElement;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.selection.ToggleableKt;
import androidx.compose.material3.ButtonDefaults;
import androidx.compose.material3.CheckboxKt;
import androidx.compose.material3.ColorScheme;
import androidx.compose.material3.ColorSchemeKt;
import androidx.compose.material3.InteractiveComponentSizeKt;
import androidx.compose.material3.MinimumInteractiveModifier;
import androidx.compose.material3.TextKt;
import androidx.compose.material3.Typography;
import androidx.compose.material3.TypographyKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.AlphaKt;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.semantics.Role;

import com.android.settingslib.spa.framework.theme.SettingsDimension;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SettingsDropdownCheckBoxKt {
    /* JADX WARN: Type inference failed for: r3v9, types: [com.android.settingslib.spa.widget.editor.SettingsDropdownCheckBoxKt$SettingsDropdownCheckBox$2, kotlin.jvm.internal.Lambda] */
    public static final void SettingsDropdownCheckBox(
            final String label,
            final List options,
            String str,
            boolean z,
            String str2,
            Function0 function0,
            Composer composer,
            final int i,
            final int i2) {
        boolean z2;
        Intrinsics.checkNotNullParameter(label, "label");
        Intrinsics.checkNotNullParameter(options, "options");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-2071621006);
        String str3 = (i2 & 4) != 0 ? ApnSettings.MVNO_NONE : str;
        boolean z3 = (i2 & 8) != 0 ? true : z;
        String str4 = null;
        String str5 = (i2 & 16) != 0 ? null : str2;
        final Function0 function02 =
                (i2 & 32) != 0
                        ? new Function0() { // from class:
                                            // com.android.settingslib.spa.widget.editor.SettingsDropdownCheckBoxKt$SettingsDropdownCheckBox$1
                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final /* bridge */ /* synthetic */ Object mo1068invoke() {
                                return Unit.INSTANCE;
                            }
                        }
                        : function0;
        OpaqueKey opaqueKey = ComposerKt.invocation;
        List list = options;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (((Boolean) ((SettingsDropdownCheckOption) obj).selected.getValue())
                    .booleanValue()) {
                arrayList.add(obj);
            }
        }
        if (!arrayList.isEmpty()) {
            ArrayList arrayList2 = new ArrayList();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Object next = it.next();
                if (((SettingsDropdownCheckOption) next).isSelectAll) {
                    arrayList2.add(next);
                }
            }
            str4 =
                    CollectionsKt___CollectionsKt.joinToString$default(
                            arrayList2.isEmpty() ? arrayList : arrayList2,
                            null,
                            null,
                            null,
                            new Function1() { // from class:
                                              // com.android.settingslib.spa.widget.editor.SettingsDropdownCheckBoxKt$getDisplayText$3
                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj2) {
                                    SettingsDropdownCheckOption it2 =
                                            (SettingsDropdownCheckOption) obj2;
                                    Intrinsics.checkNotNullParameter(it2, "it");
                                    return it2.text;
                                }
                            },
                            31);
        }
        if (str4 == null) {
            str4 = str3;
        }
        if (z3) {
            ArrayList arrayList3 = new ArrayList();
            for (Object obj2 : list) {
                if (!((SettingsDropdownCheckOption) obj2).isSelectAll) {
                    arrayList3.add(obj2);
                }
            }
            if (!arrayList3.isEmpty()) {
                Iterator it2 = arrayList3.iterator();
                while (it2.hasNext()) {
                    if (((SettingsDropdownCheckOption) it2.next()).changeable) {
                        z2 = true;
                        break;
                    }
                }
            }
        }
        z2 = false;
        DropdownTextBoxKt.DropdownTextBox(
                label,
                str4,
                z2,
                str5,
                false,
                ComposableLambdaKt.rememberComposableLambda(
                        1542069162,
                        new Function3() { // from class:
                                          // com.android.settingslib.spa.widget.editor.SettingsDropdownCheckBoxKt$SettingsDropdownCheckBox$2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(3);
                            }

                            @Override // kotlin.jvm.functions.Function3
                            public final Object invoke(Object obj3, Object obj4, Object obj5) {
                                DropdownTextBoxScope DropdownTextBox = (DropdownTextBoxScope) obj3;
                                Composer composer2 = (Composer) obj4;
                                int intValue = ((Number) obj5).intValue();
                                Intrinsics.checkNotNullParameter(
                                        DropdownTextBox, "$this$DropdownTextBox");
                                if ((intValue & 81) == 16) {
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    if (composerImpl2.getSkipping()) {
                                        composerImpl2.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                for (final SettingsDropdownCheckOption settingsDropdownCheckOption :
                                        options) {
                                    final List<SettingsDropdownCheckOption> list2 = options;
                                    final Function0 function03 = function02;
                                    SettingsDropdownCheckBoxKt.access$CheckboxItem(
                                            settingsDropdownCheckOption,
                                            new Function1() { // from class:
                                                              // com.android.settingslib.spa.widget.editor.SettingsDropdownCheckBoxKt$SettingsDropdownCheckBox$2.1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(1);
                                                }

                                                @Override // kotlin.jvm.functions.Function1
                                                public final Object invoke(Object obj6) {
                                                    SettingsDropdownCheckOption it3 =
                                                            (SettingsDropdownCheckOption) obj6;
                                                    Intrinsics.checkNotNullParameter(it3, "it");
                                                    SettingsDropdownCheckOption.this.onClick
                                                            .mo1068invoke();
                                                    SettingsDropdownCheckOption
                                                            settingsDropdownCheckOption2 =
                                                                    SettingsDropdownCheckOption
                                                                            .this;
                                                    boolean z4 =
                                                            settingsDropdownCheckOption2.changeable;
                                                    if (z4) {
                                                        List<SettingsDropdownCheckOption> list3 =
                                                                list2;
                                                        if (z4) {
                                                            MutableState mutableState =
                                                                    settingsDropdownCheckOption2
                                                                            .selected;
                                                            boolean z5 = true;
                                                            boolean z6 =
                                                                    !((Boolean)
                                                                                    mutableState
                                                                                            .getValue())
                                                                            .booleanValue();
                                                            if (settingsDropdownCheckOption2
                                                                    .isSelectAll) {
                                                                ArrayList arrayList4 =
                                                                        new ArrayList();
                                                                for (Object obj7 : list3) {
                                                                    if (((SettingsDropdownCheckOption)
                                                                                    obj7)
                                                                            .changeable) {
                                                                        arrayList4.add(obj7);
                                                                    }
                                                                }
                                                                Iterator it4 =
                                                                        arrayList4.iterator();
                                                                while (it4.hasNext()) {
                                                                    ((SettingsDropdownCheckOption)
                                                                                    it4.next())
                                                                            .selected.setValue(
                                                                                    Boolean.valueOf(
                                                                                            z6));
                                                                }
                                                            } else {
                                                                mutableState.setValue(
                                                                        Boolean.valueOf(z6));
                                                            }
                                                            ArrayList arrayList5 = new ArrayList();
                                                            ArrayList arrayList6 = new ArrayList();
                                                            for (Object obj8 : list3) {
                                                                if (((SettingsDropdownCheckOption)
                                                                                obj8)
                                                                        .isSelectAll) {
                                                                    arrayList5.add(obj8);
                                                                } else {
                                                                    arrayList6.add(obj8);
                                                                }
                                                            }
                                                            Pair pair =
                                                                    new Pair(
                                                                            arrayList5, arrayList6);
                                                            List list4 = (List) pair.getFirst();
                                                            List list5 = (List) pair.getSecond();
                                                            if (!(list5 instanceof Collection)
                                                                    || !list5.isEmpty()) {
                                                                Iterator it5 = list5.iterator();
                                                                while (true) {
                                                                    if (!it5.hasNext()) {
                                                                        break;
                                                                    }
                                                                    if (!((Boolean)
                                                                                    ((SettingsDropdownCheckOption)
                                                                                                    it5
                                                                                                            .next())
                                                                                                    .selected
                                                                                                    .getValue())
                                                                            .booleanValue()) {
                                                                        z5 = false;
                                                                        break;
                                                                    }
                                                                }
                                                            }
                                                            Iterator it6 = list4.iterator();
                                                            while (it6.hasNext()) {
                                                                ((SettingsDropdownCheckOption)
                                                                                it6.next())
                                                                        .selected.setValue(
                                                                                Boolean.valueOf(
                                                                                        z5));
                                                            }
                                                        }
                                                        function03.mo1068invoke();
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            },
                                            composer2,
                                            0);
                                }
                                OpaqueKey opaqueKey3 = ComposerKt.invocation;
                                return Unit.INSTANCE;
                            }
                        },
                        composerImpl),
                composerImpl,
                (i & 14) | 221184 | ((i >> 3) & 7168),
                0);
        OpaqueKey opaqueKey2 = ComposerKt.invocation;
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            final String str6 = str3;
            final boolean z4 = z3;
            final String str7 = str5;
            final Function0 function03 = function02;
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.editor.SettingsDropdownCheckBoxKt$SettingsDropdownCheckBox$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj3, Object obj4) {
                            ((Number) obj4).intValue();
                            SettingsDropdownCheckBoxKt.SettingsDropdownCheckBox(
                                    label,
                                    options,
                                    str6,
                                    z4,
                                    str7,
                                    function03,
                                    (Composer) obj3,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1),
                                    i2);
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public static final void access$CheckboxItem(
            final SettingsDropdownCheckOption settingsDropdownCheckOption,
            final Function1 function1,
            Composer composer,
            final int i) {
        int i2;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1720898114);
        if ((i & 14) == 0) {
            i2 = (composerImpl2.changed(settingsDropdownCheckOption) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl2.changedInstance(function1) ? 32 : 16;
        }
        if ((i2 & 91) == 18 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Modifier.Companion companion = Modifier.Companion.$$INSTANCE;
            FillElement fillElement = SizeKt.FillWholeMaxWidth;
            StaticProvidableCompositionLocal staticProvidableCompositionLocal =
                    InteractiveComponentSizeKt.LocalMinimumInteractiveComponentSize;
            Modifier then = fillElement.then(MinimumInteractiveModifier.INSTANCE);
            boolean booleanValue =
                    ((Boolean) settingsDropdownCheckOption.selected.getValue()).booleanValue();
            Role role = new Role(1);
            composerImpl2.startReplaceGroup(-939962990);
            boolean z = ((i2 & 14) == 4) | ((i2 & 112) == 32);
            Object rememberedValue = composerImpl2.rememberedValue();
            if (z || rememberedValue == Composer.Companion.Empty) {
                rememberedValue =
                        new Function1() { // from class:
                                          // com.android.settingslib.spa.widget.editor.SettingsDropdownCheckBoxKt$CheckboxItem$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                ((Boolean) obj).booleanValue();
                                Function1.this.invoke(settingsDropdownCheckOption);
                                return Unit.INSTANCE;
                            }
                        };
                composerImpl2.updateRememberedValue(rememberedValue);
            }
            composerImpl2.end(false);
            boolean z2 = settingsDropdownCheckOption.changeable;
            Modifier padding =
                    PaddingKt.padding(
                            ToggleableKt.m121toggleableXHw0xAI(
                                    then, booleanValue, z2, role, (Function1) rememberedValue),
                            ButtonDefaults.TextButtonContentPadding);
            Arrangement$End$1 arrangement$End$1 = Arrangement.Start;
            RowMeasurePolicy rowMeasurePolicy =
                    RowKt.rowMeasurePolicy(
                            Arrangement.m66spacedBy0680j_4(SettingsDimension.itemPaddingAround),
                            Alignment.Companion.CenterVertically,
                            composerImpl2,
                            54);
            int i3 = composerImpl2.compoundKeyHash;
            PersistentCompositionLocalMap currentCompositionLocalScope =
                    composerImpl2.currentCompositionLocalScope();
            Modifier materializeModifier =
                    ComposedModifierKt.materializeModifier(composerImpl2, padding);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (!(composerImpl2.applier instanceof Applier)) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl2.startReusableNode();
            if (composerImpl2.inserting) {
                composerImpl2.createNode(function0);
            } else {
                composerImpl2.useNode();
            }
            Updater.m221setimpl(
                    composerImpl2, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m221setimpl(
                    composerImpl2,
                    currentCompositionLocalScope,
                    ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl2.inserting
                    || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(i3))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(i3, composerImpl2, i3, function2);
            }
            Updater.m221setimpl(
                    composerImpl2, materializeModifier, ComposeUiNode.Companion.SetModifier);
            CheckboxKt.Checkbox(
                    ((Boolean) settingsDropdownCheckOption.selected.getValue()).booleanValue(),
                    null,
                    null,
                    settingsDropdownCheckOption.changeable,
                    null,
                    null,
                    composerImpl2,
                    48,
                    52);
            TextKt.m210Text4IGK_g(
                    settingsDropdownCheckOption.text,
                    AlphaKt.alpha(companion, z2 ? 1.0f : 0.38f),
                    ((ColorScheme) composerImpl2.consume(ColorSchemeKt.LocalColorScheme)).primary,
                    0L,
                    null,
                    null,
                    null,
                    0L,
                    null,
                    null,
                    0L,
                    0,
                    false,
                    0,
                    0,
                    null,
                    ((Typography) composerImpl2.consume(TypographyKt.LocalTypography)).labelLarge,
                    composerImpl2,
                    0,
                    0,
                    65528);
            composerImpl = composerImpl2;
            composerImpl.end(true);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.editor.SettingsDropdownCheckBoxKt$CheckboxItem$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SettingsDropdownCheckBoxKt.access$CheckboxItem(
                                    SettingsDropdownCheckOption.this,
                                    function1,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
