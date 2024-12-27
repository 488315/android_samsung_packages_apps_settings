package com.android.settingslib.spa.widget.dialog;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.foundation.text.selection.SimpleLayoutKt$$ExternalSyntheticOutline0;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.text.TextStyle;

import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$IntRef;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SettingsAlterDialogContentKt {
    public static final PaddingValuesImpl DialogPadding;
    public static final PaddingValuesImpl IconPadding;
    public static final PaddingValuesImpl TextPadding;
    public static final PaddingValuesImpl TitlePadding;
    public static final float ButtonsMainAxisSpacing = 8;
    public static final float ButtonsCrossAxisSpacing = 12;

    static {
        float f = 24;
        DialogPadding = new PaddingValuesImpl(f, f, f, f);
        float f2 = 16;
        IconPadding = PaddingKt.m84PaddingValuesa9UjIt4$default(0.0f, 0.0f, f2, 7);
        TitlePadding = PaddingKt.m84PaddingValuesa9UjIt4$default(0.0f, 0.0f, f2, 7);
        TextPadding = PaddingKt.m84PaddingValuesa9UjIt4$default(0.0f, 0.0f, f, 7);
    }

    /* renamed from: AlertDialogFlowRow-ixp7dh8, reason: not valid java name */
    public static final void m1040AlertDialogFlowRowixp7dh8(
            final float f,
            final float f2,
            final Function2 content,
            Composer composer,
            final int i) {
        int i2;
        Intrinsics.checkNotNullParameter(content, "content");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1196021488);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(f) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl.changed(f2) ? 32 : 16;
        }
        if ((i & 896) == 0) {
            i2 |= composerImpl.changedInstance(content) ? 256 : 128;
        }
        if ((i2 & 731) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            composerImpl.startReplaceGroup(1197325469);
            boolean z = ((i2 & 14) == 4) | ((i2 & 112) == 32);
            Object rememberedValue = composerImpl.rememberedValue();
            if (z || rememberedValue == Composer.Companion.Empty) {
                rememberedValue =
                        new MeasurePolicy() { // from class:
                            // com.android.settingslib.spa.widget.dialog.SettingsAlterDialogContentKt$AlertDialogFlowRow$1$1
                            public static final void measure_3p2s80s$startNewSequence(
                                    List list,
                                    Ref$IntRef ref$IntRef,
                                    MeasureScope measureScope,
                                    float f3,
                                    List list2,
                                    List list3,
                                    Ref$IntRef ref$IntRef2,
                                    List list4,
                                    Ref$IntRef ref$IntRef3,
                                    Ref$IntRef ref$IntRef4) {
                                if (!list.isEmpty()) {
                                    ref$IntRef.element =
                                            measureScope.mo48roundToPx0680j_4(f3)
                                                    + ref$IntRef.element;
                                }
                                ((ArrayList) list)
                                        .add(0, CollectionsKt___CollectionsKt.toList(list2));
                                list3.add(Integer.valueOf(ref$IntRef2.element));
                                list4.add(Integer.valueOf(ref$IntRef.element));
                                ref$IntRef.element += ref$IntRef2.element;
                                ref$IntRef3.element =
                                        Math.max(ref$IntRef3.element, ref$IntRef4.element);
                                ((ArrayList) list2).clear();
                                ref$IntRef4.element = 0;
                                ref$IntRef2.element = 0;
                            }

                            /* JADX WARN: Removed duplicated region for block: B:10:0x00a7  */
                            /* JADX WARN: Removed duplicated region for block: B:13:0x00b0 A[SYNTHETIC] */
                            @Override // androidx.compose.ui.layout.MeasurePolicy
                            /* renamed from: measure-3p2s80s */
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final androidx.compose.ui.layout.MeasureResult mo4measure3p2s80s(
                                    final androidx.compose.ui.layout.MeasureScope r25,
                                    java.util.List r26,
                                    long r27) {
                                /*
                                    Method dump skipped, instructions count: 292
                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                */
                                throw new UnsupportedOperationException(
                                        "Method not decompiled:"
                                            + " com.android.settingslib.spa.widget.dialog.SettingsAlterDialogContentKt$AlertDialogFlowRow$1$1.mo4measure3p2s80s(androidx.compose.ui.layout.MeasureScope,"
                                            + " java.util.List,"
                                            + " long):androidx.compose.ui.layout.MeasureResult");
                            }
                        };
                composerImpl.updateRememberedValue(rememberedValue);
            }
            MeasurePolicy measurePolicy = (MeasurePolicy) rememberedValue;
            composerImpl.end(false);
            Modifier.Companion companion = Modifier.Companion.$$INSTANCE;
            int i3 = composerImpl.compoundKeyHash;
            PersistentCompositionLocalMap currentCompositionLocalScope =
                    composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier =
                    ComposedModifierKt.materializeModifier(composerImpl, companion);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            int i4 = ((((i2 >> 6) & 14) << 6) & 896) | 6;
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
                    composerImpl, measurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
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
            SimpleLayoutKt$$ExternalSyntheticOutline0.m(
                    (i4 >> 6) & 14, content, composerImpl, true);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.dialog.SettingsAlterDialogContentKt$AlertDialogFlowRow$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SettingsAlterDialogContentKt.m1040AlertDialogFlowRowixp7dh8(
                                    f,
                                    f2,
                                    content,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x007e  */
    /* JADX WARN: Type inference failed for: r4v18, types: [com.android.settingslib.spa.widget.dialog.SettingsAlterDialogContentKt$SettingsAlertDialogContent$2$1, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Type inference failed for: r4v21, types: [com.android.settingslib.spa.widget.dialog.SettingsAlterDialogContentKt$SettingsAlertDialogContent$3, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Type inference failed for: r7v5, types: [com.android.settingslib.spa.widget.dialog.SettingsAlterDialogContentKt$SettingsAlertDialogContent$1$1, kotlin.jvm.internal.Lambda] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SettingsAlertDialogContent(
            final com.android.settingslib.spa.widget.dialog.AlertDialogButton r16,
            final com.android.settingslib.spa.widget.dialog.AlertDialogButton r17,
            final java.lang.String r18,
            kotlin.jvm.functions.Function2 r19,
            final kotlin.jvm.functions.Function2 r20,
            androidx.compose.runtime.Composer r21,
            final int r22,
            final int r23) {
        /*
            Method dump skipped, instructions count: 293
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.spa.widget.dialog.SettingsAlterDialogContentKt.SettingsAlertDialogContent(com.android.settingslib.spa.widget.dialog.AlertDialogButton,"
                    + " com.android.settingslib.spa.widget.dialog.AlertDialogButton,"
                    + " java.lang.String, kotlin.jvm.functions.Function2,"
                    + " kotlin.jvm.functions.Function2, androidx.compose.runtime.Composer, int,"
                    + " int):void");
    }

    /* renamed from: access$ProvideContentColorTextStyle-3J-VO9M, reason: not valid java name */
    public static final void m1041access$ProvideContentColorTextStyle3JVO9M(
            final long j,
            final TextStyle textStyle,
            final Function2 function2,
            Composer composer,
            final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-98818256);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(j) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl.changed(textStyle) ? 32 : 16;
        }
        if ((i & 896) == 0) {
            i2 |= composerImpl.changedInstance(function2) ? 256 : 128;
        }
        if ((i2 & 731) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal =
                    TextKt.LocalTextStyle;
            CompositionLocalKt.CompositionLocalProvider(
                    new ProvidedValue[] {
                        ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(
                                new Color(j)),
                        dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(
                                ((TextStyle)
                                                composerImpl.consume(
                                                        dynamicProvidableCompositionLocal))
                                        .merge(textStyle))
                    },
                    function2,
                    composerImpl,
                    ((i2 >> 3) & 112) | 8);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.dialog.SettingsAlterDialogContentKt$ProvideContentColorTextStyle$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SettingsAlterDialogContentKt
                                    .m1041access$ProvideContentColorTextStyle3JVO9M(
                                            j,
                                            textStyle,
                                            function2,
                                            (Composer) obj,
                                            RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0055  */
    /* JADX WARN: Type inference failed for: r2v6, types: [com.android.settingslib.spa.widget.dialog.SettingsAlterDialogContentKt$SettingsAlertDialogContent$5, kotlin.jvm.internal.Lambda] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SettingsAlertDialogContent(
            final kotlin.jvm.functions.Function2 r21,
            androidx.compose.ui.Modifier r22,
            final kotlin.jvm.functions.Function2 r23,
            final kotlin.jvm.functions.Function2 r24,
            final kotlin.jvm.functions.Function2 r25,
            androidx.compose.runtime.Composer r26,
            final int r27,
            final int r28) {
        /*
            Method dump skipped, instructions count: 255
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.spa.widget.dialog.SettingsAlterDialogContentKt.SettingsAlertDialogContent(kotlin.jvm.functions.Function2,"
                    + " androidx.compose.ui.Modifier, kotlin.jvm.functions.Function2,"
                    + " kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2,"
                    + " androidx.compose.runtime.Composer, int, int):void");
    }
}
