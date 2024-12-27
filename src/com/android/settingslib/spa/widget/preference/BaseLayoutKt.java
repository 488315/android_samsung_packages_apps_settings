package com.android.settingslib.spa.widget.preference;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
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

import com.android.settingslib.spa.framework.theme.SettingsDimension;
import com.android.settingslib.spa.widget.ui.TextKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BaseLayoutKt {
    /* renamed from: BaseIcon-TDGSqEk, reason: not valid java name */
    public static final void m1042BaseIconTDGSqEk(
            final Function2 function2,
            final Modifier modifier,
            final float f,
            Composer composer,
            final int i) {
        int i2;
        Intrinsics.checkNotNullParameter(modifier, "modifier");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1091226295);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changedInstance(function2) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl.changed(modifier) ? 32 : 16;
        }
        if ((i & 896) == 0) {
            i2 |= composerImpl.changed(f) ? 256 : 128;
        }
        if ((i2 & 731) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            if (function2 != null) {
                composerImpl.startReplaceGroup(423924011);
                Modifier m96size3ABfNKs =
                        SizeKt.m96size3ABfNKs(modifier, SettingsDimension.itemIconContainerSize);
                MeasurePolicy maybeCachedBoxMeasurePolicy =
                        BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
                int i3 = composerImpl.compoundKeyHash;
                PersistentCompositionLocalMap currentCompositionLocalScope =
                        composerImpl.currentCompositionLocalScope();
                Modifier materializeModifier =
                        ComposedModifierKt.materializeModifier(composerImpl, m96size3ABfNKs);
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
                Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting
                        || !Intrinsics.areEqual(
                                composerImpl.rememberedValue(), Integer.valueOf(i3))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(
                            i3, composerImpl, i3, function22);
                }
                Updater.m221setimpl(
                        composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
                function2.invoke(composerImpl, Integer.valueOf(i2 & 14));
                composerImpl.end(true);
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(423924206);
                SpacerKt.Spacer(
                        composerImpl, SizeKt.m100width3ABfNKs(Modifier.Companion.$$INSTANCE, f));
                composerImpl.end(false);
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.preference.BaseLayoutKt$BaseIcon$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            BaseLayoutKt.m1042BaseIconTDGSqEk(
                                    Function2.this,
                                    modifier,
                                    f,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:105:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0125  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x016e  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x017a  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0181  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0188  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01de  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x029e  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x018b  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0184  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x017d  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0170  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x010c  */
    /* renamed from: BaseLayout-IBxwOmc, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m1043BaseLayoutIBxwOmc(
            final java.lang.String r21,
            final kotlin.jvm.functions.Function2 r22,
            androidx.compose.ui.Modifier r23,
            java.lang.String r24,
            kotlin.jvm.functions.Function2 r25,
            kotlin.jvm.functions.Function0 r26,
            float r27,
            float r28,
            float r29,
            kotlin.jvm.functions.Function2 r30,
            androidx.compose.runtime.Composer r31,
            final int r32,
            final int r33) {
        /*
            Method dump skipped, instructions count: 675
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.spa.widget.preference.BaseLayoutKt.m1043BaseLayoutIBxwOmc(java.lang.String,"
                    + " kotlin.jvm.functions.Function2, androidx.compose.ui.Modifier,"
                    + " java.lang.String, kotlin.jvm.functions.Function2,"
                    + " kotlin.jvm.functions.Function0, float, float, float,"
                    + " kotlin.jvm.functions.Function2, androidx.compose.runtime.Composer, int,"
                    + " int):void");
    }

    public static final void Titles(
            final String str,
            final String str2,
            final Function2 function2,
            final Modifier modifier,
            Composer composer,
            final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1000644484);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(str) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl.changed(str2) ? 32 : 16;
        }
        if ((i & 896) == 0) {
            i2 |= composerImpl.changedInstance(function2) ? 256 : 128;
        }
        if ((i & 7168) == 0) {
            i2 |= composerImpl.changed(modifier) ? 2048 : 1024;
        }
        int i3 = i2;
        if ((i3 & 5851) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            ColumnMeasurePolicy columnMeasurePolicy =
                    ColumnKt.columnMeasurePolicy(
                            Arrangement.Top, Alignment.Companion.Start, composerImpl, 0);
            int i4 = composerImpl.compoundKeyHash;
            PersistentCompositionLocalMap currentCompositionLocalScope =
                    composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier =
                    ComposedModifierKt.materializeModifier(composerImpl, modifier);
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
            Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting
                    || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i4))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(i4, composerImpl, i4, function22);
            }
            Updater.m221setimpl(
                    composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            TextKt.SettingsTitle(str, str2, false, composerImpl, i3 & 126, 4);
            SimpleLayoutKt$$ExternalSyntheticOutline0.m(
                    (i3 >> 6) & 14, function2, composerImpl, true);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.preference.BaseLayoutKt$Titles$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            BaseLayoutKt.Titles(
                                    str,
                                    str2,
                                    function2,
                                    modifier,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
