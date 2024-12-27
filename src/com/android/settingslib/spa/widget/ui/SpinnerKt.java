package com.android.settingslib.spa.widget.ui;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.ColumnScope;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.foundation.layout.RowScope;
import androidx.compose.foundation.selection.SelectableGroupKt;
import androidx.compose.material.icons.outlined.ExpandLessKt;
import androidx.compose.material.icons.outlined.ExpandMoreKt;
import androidx.compose.material3.AndroidMenu_androidKt;
import androidx.compose.material3.ButtonColors;
import androidx.compose.material3.ButtonDefaults;
import androidx.compose.material3.ButtonKt;
import androidx.compose.material3.ColorScheme;
import androidx.compose.material3.ColorSchemeKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.ProvidableCompositionLocal;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.StructuralEqualityPolicy;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.saveable.RememberSaveableKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.PathNode;
import androidx.compose.ui.graphics.vector.VectorKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;

import com.android.settingslib.spa.framework.theme.SettingsDimension;

import com.sec.ims.volte2.data.VolteConstants;

import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SpinnerKt {
    public static final void ExpandIcon(final boolean z, Composer composer, final int i) {
        int i2;
        ImageVector imageVector;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(896160472);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(z) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            if (z) {
                imageVector = ExpandLessKt._expandLess;
                if (imageVector == null) {
                    ImageVector.Builder builder =
                            new ImageVector.Builder(
                                    "Outlined.ExpandLess",
                                    24.0f,
                                    24.0f,
                                    24.0f,
                                    24.0f,
                                    0L,
                                    0,
                                    false,
                                    96);
                    EmptyList emptyList = VectorKt.EmptyPath;
                    SolidColor solidColor = new SolidColor(Color.Black);
                    ArrayList arrayList = new ArrayList(32);
                    arrayList.add(new PathNode.MoveTo(12.0f, 8.0f));
                    arrayList.add(new PathNode.RelativeLineTo(-6.0f, 6.0f));
                    arrayList.add(new PathNode.RelativeLineTo(1.41f, 1.41f));
                    arrayList.add(new PathNode.LineTo(12.0f, 10.83f));
                    arrayList.add(new PathNode.RelativeLineTo(4.59f, 4.58f));
                    arrayList.add(new PathNode.LineTo(18.0f, 14.0f));
                    arrayList.add(new PathNode.RelativeLineTo(-6.0f, -6.0f));
                    arrayList.add(PathNode.Close.INSTANCE);
                    ImageVector.Builder.m390addPathoIyEayM$default(builder, arrayList, solidColor);
                    imageVector = builder.build();
                    ExpandLessKt._expandLess = imageVector;
                }
            } else {
                imageVector = ExpandMoreKt._expandMore;
                if (imageVector == null) {
                    ImageVector.Builder builder2 =
                            new ImageVector.Builder(
                                    "Outlined.ExpandMore",
                                    24.0f,
                                    24.0f,
                                    24.0f,
                                    24.0f,
                                    0L,
                                    0,
                                    false,
                                    96);
                    EmptyList emptyList2 = VectorKt.EmptyPath;
                    SolidColor solidColor2 = new SolidColor(Color.Black);
                    ArrayList arrayList2 = new ArrayList(32);
                    arrayList2.add(new PathNode.MoveTo(16.59f, 8.59f));
                    arrayList2.add(new PathNode.LineTo(12.0f, 13.17f));
                    arrayList2.add(new PathNode.LineTo(7.41f, 8.59f));
                    arrayList2.add(new PathNode.LineTo(6.0f, 10.0f));
                    arrayList2.add(new PathNode.RelativeLineTo(6.0f, 6.0f));
                    arrayList2.add(new PathNode.RelativeLineTo(6.0f, -6.0f));
                    arrayList2.add(new PathNode.RelativeLineTo(-1.41f, -1.41f));
                    arrayList2.add(PathNode.Close.INSTANCE);
                    ImageVector.Builder.m390addPathoIyEayM$default(
                            builder2, arrayList2, solidColor2);
                    imageVector = builder2.build();
                    ExpandMoreKt._expandMore = imageVector;
                }
            }
            androidx.compose.material3.IconKt.m188Iconww6aTOc(
                    imageVector, (String) null, (Modifier) null, 0L, composerImpl, 48, 12);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.ui.SpinnerKt$ExpandIcon$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SpinnerKt.ExpandIcon(
                                    z,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    /* JADX WARN: Type inference failed for: r6v6, types: [com.android.settingslib.spa.widget.ui.SpinnerKt$Spinner$2$3, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Type inference failed for: r7v10, types: [com.android.settingslib.spa.widget.ui.SpinnerKt$Spinner$2$5, kotlin.jvm.internal.Lambda] */
    public static final void Spinner(
            final List options,
            final Integer num,
            final Function1 setId,
            Composer composer,
            final int i) {
        Intrinsics.checkNotNullParameter(options, "options");
        Intrinsics.checkNotNullParameter(setId, "setId");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(99934478);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        if (options.isEmpty()) {
            RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
            if (endRestartGroup != null) {
                endRestartGroup.block =
                        new Function2() { // from class:
                                          // com.android.settingslib.spa.widget.ui.SpinnerKt$Spinner$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                SpinnerKt.Spinner(
                                        options,
                                        num,
                                        setId,
                                        (Composer) obj,
                                        RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                return Unit.INSTANCE;
                            }
                        };
                return;
            }
            return;
        }
        final MutableState mutableState =
                (MutableState)
                        RememberSaveableKt.rememberSaveable(
                                new Object[0],
                                null,
                                new Function0() { // from class:
                                                  // com.android.settingslib.spa.widget.ui.SpinnerKt$Spinner$expanded$2
                                    @Override // kotlin.jvm.functions.Function0
                                    /* renamed from: invoke */
                                    public final Object mo1068invoke() {
                                        return SnapshotStateKt.mutableStateOf(
                                                Boolean.FALSE, StructuralEqualityPolicy.INSTANCE);
                                    }
                                },
                                composerImpl,
                                3080,
                                6);
        Modifier.Companion companion = Modifier.Companion.$$INSTANCE;
        float f = SettingsDimension.itemPaddingStart;
        float f2 = SettingsDimension.itemPaddingAround;
        float f3 = SettingsDimension.itemPaddingEnd;
        Modifier selectableGroup =
                SelectableGroupKt.selectableGroup(
                        PaddingKt.m88paddingqDBjuR0(companion, f, f2, f3, f2));
        MeasurePolicy maybeCachedBoxMeasurePolicy =
                BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
        int i2 = composerImpl.compoundKeyHash;
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
                composerImpl,
                maybeCachedBoxMeasurePolicy,
                ComposeUiNode.Companion.SetMeasurePolicy);
        Updater.m221setimpl(
                composerImpl,
                currentCompositionLocalScope,
                ComposeUiNode.Companion.SetResolvedCompositionLocals);
        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
        if (composerImpl.inserting
                || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i2))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i2, composerImpl, i2, function2);
        }
        Updater.m221setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
        float f4 = 0;
        final PaddingValuesImpl paddingValuesImpl = new PaddingValuesImpl(f3, f4, f3, f4);
        Modifier semantics =
                SemanticsModifierKt.semantics(
                        companion,
                        false,
                        new Function1() { // from class:
                                          // com.android.settingslib.spa.widget.ui.SpinnerKt$Spinner$2$1
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                SemanticsPropertyReceiver semantics2 =
                                        (SemanticsPropertyReceiver) obj;
                                Intrinsics.checkNotNullParameter(semantics2, "$this$semantics");
                                SemanticsPropertiesKt.m503setRolekuIjeqM(semantics2, 6);
                                return Unit.INSTANCE;
                            }
                        });
        PaddingValuesImpl paddingValuesImpl2 = ButtonDefaults.ContentPadding;
        ProvidableCompositionLocal providableCompositionLocal = ColorSchemeKt.LocalColorScheme;
        long j = ((ColorScheme) composerImpl.consume(providableCompositionLocal)).primaryContainer;
        long j2 =
                ((ColorScheme) composerImpl.consume(providableCompositionLocal)).onPrimaryContainer;
        long j3 = Color.Unspecified;
        ButtonColors m177copyjRlVdoo =
                ButtonDefaults.getDefaultButtonColors$material3_release(
                                (ColorScheme) composerImpl.consume(providableCompositionLocal))
                        .m177copyjRlVdoo(j, j2, j3, j3);
        composerImpl.startReplaceGroup(-1848956515);
        boolean changed = composerImpl.changed(mutableState);
        Object rememberedValue = composerImpl.rememberedValue();
        Object obj = Composer.Companion.Empty;
        if (changed || rememberedValue == obj) {
            rememberedValue =
                    new Function0() { // from class:
                                      // com.android.settingslib.spa.widget.ui.SpinnerKt$Spinner$2$2$1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            MutableState.this.setValue(Boolean.TRUE);
                            return Unit.INSTANCE;
                        }
                    };
            composerImpl.updateRememberedValue(rememberedValue);
        }
        composerImpl.end(false);
        ButtonKt.Button(
                (Function0) rememberedValue,
                semantics,
                false,
                null,
                m177copyjRlVdoo,
                null,
                null,
                paddingValuesImpl,
                null,
                ComposableLambdaKt.rememberComposableLambda(
                        -1333127420,
                        new Function3() { // from class:
                                          // com.android.settingslib.spa.widget.ui.SpinnerKt$Spinner$2$3
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(3);
                            }

                            @Override // kotlin.jvm.functions.Function3
                            public final Object invoke(Object obj2, Object obj3, Object obj4) {
                                Object obj5;
                                RowScope Button = (RowScope) obj2;
                                Composer composer2 = (Composer) obj3;
                                int intValue = ((Number) obj4).intValue();
                                Intrinsics.checkNotNullParameter(Button, "$this$Button");
                                if ((intValue & 81) == 16) {
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    if (composerImpl2.getSkipping()) {
                                        composerImpl2.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                List<SpinnerOption> list = options;
                                Integer num2 = num;
                                Iterator<T> it = list.iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        obj5 = null;
                                        break;
                                    }
                                    obj5 = it.next();
                                    int i3 = ((SpinnerOption) obj5).id;
                                    if (num2 != null && i3 == num2.intValue()) {
                                        break;
                                    }
                                }
                                SpinnerKt.m1049access$SpinnerTextFNF3uiM(
                                        (SpinnerOption) obj5, null, 0L, composer2, 0, 6);
                                SpinnerKt.ExpandIcon(
                                        ((Boolean) mutableState.getValue()).booleanValue(),
                                        composer2,
                                        0);
                                OpaqueKey opaqueKey3 = ComposerKt.invocation;
                                return Unit.INSTANCE;
                            }
                        },
                        composerImpl),
                composerImpl,
                817889280,
                364);
        boolean booleanValue = ((Boolean) mutableState.getValue()).booleanValue();
        composerImpl.startReplaceGroup(-1848956027);
        boolean changed2 = composerImpl.changed(mutableState);
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (changed2 || rememberedValue2 == obj) {
            rememberedValue2 =
                    new Function0() { // from class:
                                      // com.android.settingslib.spa.widget.ui.SpinnerKt$Spinner$2$4$1
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
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        composerImpl.end(false);
        AndroidMenu_androidKt.m176DropdownMenuIlH_yew(
                booleanValue,
                (Function0) rememberedValue2,
                BackgroundKt.m29backgroundbw27NRU(
                        companion,
                        ((ColorScheme) composerImpl.consume(providableCompositionLocal))
                                .secondaryContainer,
                        RectangleShapeKt.RectangleShape),
                0L,
                null,
                null,
                null,
                0L,
                0.0f,
                0.0f,
                null,
                ComposableLambdaKt.rememberComposableLambda(
                        1536083705,
                        new Function3() { // from class:
                                          // com.android.settingslib.spa.widget.ui.SpinnerKt$Spinner$2$5
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(3);
                            }

                            /* JADX WARN: Type inference failed for: r4v1, types: [com.android.settingslib.spa.widget.ui.SpinnerKt$Spinner$2$5$1, kotlin.jvm.internal.Lambda] */
                            @Override // kotlin.jvm.functions.Function3
                            public final Object invoke(Object obj2, Object obj3, Object obj4) {
                                ColumnScope DropdownMenu = (ColumnScope) obj2;
                                Composer composer2 = (Composer) obj3;
                                int intValue = ((Number) obj4).intValue();
                                Intrinsics.checkNotNullParameter(
                                        DropdownMenu, "$this$DropdownMenu");
                                if ((intValue & 81) == 16) {
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    if (composerImpl2.getSkipping()) {
                                        composerImpl2.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                for (final SpinnerOption spinnerOption : options) {
                                    ComposableLambdaImpl rememberComposableLambda =
                                            ComposableLambdaKt.rememberComposableLambda(
                                                    218298887,
                                                    new Function2() { // from class:
                                                                      // com.android.settingslib.spa.widget.ui.SpinnerKt$Spinner$2$5.1
                                                        {
                                                            super(2);
                                                        }

                                                        @Override // kotlin.jvm.functions.Function2
                                                        public final Object invoke(
                                                                Object obj5, Object obj6) {
                                                            Composer composer3 = (Composer) obj5;
                                                            if ((((Number) obj6).intValue() & 11)
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
                                                            SpinnerKt
                                                                    .m1049access$SpinnerTextFNF3uiM(
                                                                            SpinnerOption.this,
                                                                            PaddingKt
                                                                                    .m89paddingqDBjuR0$default(
                                                                                            Modifier
                                                                                                    .Companion
                                                                                                    .$$INSTANCE,
                                                                                            0.0f,
                                                                                            0.0f,
                                                                                            24,
                                                                                            0.0f,
                                                                                            11),
                                                                            ((ColorScheme)
                                                                                            ((ComposerImpl)
                                                                                                            composer3)
                                                                                                    .consume(
                                                                                                            ColorSchemeKt
                                                                                                                    .LocalColorScheme))
                                                                                    .onSecondaryContainer,
                                                                            composer3,
                                                                            48,
                                                                            0);
                                                            return Unit.INSTANCE;
                                                        }
                                                    },
                                                    composer2);
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                    composerImpl3.startReplaceGroup(-2041325374);
                                    boolean changed3 =
                                            composerImpl3.changed(mutableState)
                                                    | composerImpl3.changed(setId)
                                                    | composerImpl3.changed(spinnerOption);
                                    final Function1 function1 = setId;
                                    final MutableState mutableState2 = mutableState;
                                    Object rememberedValue3 = composerImpl3.rememberedValue();
                                    if (changed3 || rememberedValue3 == Composer.Companion.Empty) {
                                        rememberedValue3 =
                                                new Function0() { // from class:
                                                                  // com.android.settingslib.spa.widget.ui.SpinnerKt$Spinner$2$5$2$1
                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    {
                                                        super(0);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function0
                                                    /* renamed from: invoke */
                                                    public final Object mo1068invoke() {
                                                        mutableState2.setValue(Boolean.FALSE);
                                                        Function1.this.invoke(
                                                                Integer.valueOf(spinnerOption.id));
                                                        return Unit.INSTANCE;
                                                    }
                                                };
                                        composerImpl3.updateRememberedValue(rememberedValue3);
                                    }
                                    composerImpl3.end(false);
                                    AndroidMenu_androidKt.DropdownMenuItem(
                                            rememberComposableLambda,
                                            (Function0) rememberedValue3,
                                            null,
                                            null,
                                            null,
                                            false,
                                            null,
                                            paddingValuesImpl,
                                            null,
                                            composerImpl3,
                                            12582918,
                                            VolteConstants.ErrorCode.ALTERNATIVE_SERVICES);
                                }
                                OpaqueKey opaqueKey3 = ComposerKt.invocation;
                                return Unit.INSTANCE;
                            }
                        },
                        composerImpl),
                composerImpl,
                0,
                48,
                2040);
        composerImpl.end(true);
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            endRestartGroup2.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.ui.SpinnerKt$Spinner$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj2, Object obj3) {
                            ((Number) obj3).intValue();
                            SpinnerKt.Spinner(
                                    options,
                                    num,
                                    setId,
                                    (Composer) obj2,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x004c  */
    /* renamed from: access$SpinnerText-FNF3uiM, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m1049access$SpinnerTextFNF3uiM(
            final com.android.settingslib.spa.widget.ui.SpinnerOption r33,
            androidx.compose.ui.Modifier r34,
            long r35,
            androidx.compose.runtime.Composer r37,
            final int r38,
            final int r39) {
        /*
            Method dump skipped, instructions count: 234
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.spa.widget.ui.SpinnerKt.m1049access$SpinnerTextFNF3uiM(com.android.settingslib.spa.widget.ui.SpinnerOption,"
                    + " androidx.compose.ui.Modifier, long, androidx.compose.runtime.Composer, int,"
                    + " int):void");
    }
}
