package com.android.settingslib.spa.widget.dialog;

import android.content.res.Configuration;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.ScrollKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.RowScope;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.AndroidAlertDialog_androidKt;
import androidx.compose.material3.ButtonKt;
import androidx.compose.material3.TextKt;
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
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.StructuralEqualityPolicy;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.saveable.RememberSaveableKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.window.DialogProperties;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SettingsAlertDialogKt {
    /* JADX WARN: Type inference failed for: r1v11, types: [com.android.settingslib.spa.widget.dialog.SettingsAlertDialogKt$SettingsAlertDialog$4$1, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Type inference failed for: r1v15, types: [com.android.settingslib.spa.widget.dialog.SettingsAlertDialogKt$SettingsAlertDialog$5, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.settingslib.spa.widget.dialog.SettingsAlertDialogKt$SettingsAlertDialog$2$1, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.settingslib.spa.widget.dialog.SettingsAlertDialogKt$SettingsAlertDialog$3$1, kotlin.jvm.internal.Lambda] */
    public static final void SettingsAlertDialog(
            final AlertDialogPresenter alertDialogPresenter,
            final AlertDialogButton alertDialogButton,
            final AlertDialogButton alertDialogButton2,
            final String str,
            final Function2 function2,
            Composer composer,
            final int i) {
        int i2;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1145510599);
        if ((i & 14) == 0) {
            i2 = (composerImpl2.changed(alertDialogPresenter) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl2.changed(alertDialogButton) ? 32 : 16;
        }
        if ((i & 896) == 0) {
            i2 |= composerImpl2.changed(alertDialogButton2) ? 256 : 128;
        }
        if ((i & 7168) == 0) {
            i2 |= composerImpl2.changed(str) ? 2048 : 1024;
        }
        if ((57344 & i) == 0) {
            i2 |=
                    composerImpl2.changedInstance(function2)
                            ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT
                            : 8192;
        }
        if ((i2 & 46811) == 9362 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            SettingsAlertDialogKt$SettingsAlertDialog$1
                    settingsAlertDialogKt$SettingsAlertDialog$1 =
                            new SettingsAlertDialogKt$SettingsAlertDialog$1(
                                    0,
                                    alertDialogPresenter,
                                    AlertDialogPresenter.class,
                                    "close",
                                    "close()V",
                                    0);
            Modifier m100width3ABfNKs =
                    SizeKt.m100width3ABfNKs(
                            Modifier.Companion.$$INSTANCE, getDialogWidth(composerImpl2));
            composerImpl2.startReplaceGroup(1360774428);
            ComposableLambdaImpl rememberComposableLambda =
                    alertDialogButton2 == null
                            ? null
                            : ComposableLambdaKt.rememberComposableLambda(
                                    1668333143,
                                    new Function2() { // from class:
                                                      // com.android.settingslib.spa.widget.dialog.SettingsAlertDialogKt$SettingsAlertDialog$2$1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(2);
                                        }

                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj, Object obj2) {
                                            Composer composer2 = (Composer) obj;
                                            if ((((Number) obj2).intValue() & 11) == 2) {
                                                ComposerImpl composerImpl3 =
                                                        (ComposerImpl) composer2;
                                                if (composerImpl3.getSkipping()) {
                                                    composerImpl3.skipToGroupEnd();
                                                    return Unit.INSTANCE;
                                                }
                                            }
                                            OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                            SettingsAlertDialogKt.access$Button(
                                                    alertDialogPresenter,
                                                    alertDialogButton2,
                                                    composer2,
                                                    0);
                                            return Unit.INSTANCE;
                                        }
                                    },
                                    composerImpl2);
            composerImpl2.end(false);
            composerImpl2.startReplaceGroup(1360774475);
            ComposableLambdaImpl rememberComposableLambda2 =
                    str == null
                            ? null
                            : ComposableLambdaKt.rememberComposableLambda(
                                    837604271,
                                    new Function2() { // from class:
                                                      // com.android.settingslib.spa.widget.dialog.SettingsAlertDialogKt$SettingsAlertDialog$3$1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(2);
                                        }

                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj, Object obj2) {
                                            Composer composer2 = (Composer) obj;
                                            if ((((Number) obj2).intValue() & 11) == 2) {
                                                ComposerImpl composerImpl3 =
                                                        (ComposerImpl) composer2;
                                                if (composerImpl3.getSkipping()) {
                                                    composerImpl3.skipToGroupEnd();
                                                    return Unit.INSTANCE;
                                                }
                                            }
                                            OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                            TextKt.m210Text4IGK_g(
                                                    str, null, 0L, 0L, null, null, null, 0L, null,
                                                    null, 0L, 0, false, 0, 0, null, null, composer2,
                                                    0, 0, 131070);
                                            return Unit.INSTANCE;
                                        }
                                    },
                                    composerImpl2);
            composerImpl2.end(false);
            composerImpl2.startReplaceGroup(1360774518);
            ComposableLambdaImpl rememberComposableLambda3 =
                    function2 != null
                            ? ComposableLambdaKt.rememberComposableLambda(
                                    656940788,
                                    new Function2() { // from class:
                                                      // com.android.settingslib.spa.widget.dialog.SettingsAlertDialogKt$SettingsAlertDialog$4$1
                                        {
                                            super(2);
                                        }

                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj, Object obj2) {
                                            Composer composer2 = (Composer) obj;
                                            if ((((Number) obj2).intValue() & 11) == 2) {
                                                ComposerImpl composerImpl3 =
                                                        (ComposerImpl) composer2;
                                                if (composerImpl3.getSkipping()) {
                                                    composerImpl3.skipToGroupEnd();
                                                    return Unit.INSTANCE;
                                                }
                                            }
                                            OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                            Modifier verticalScroll$default =
                                                    ScrollKt.verticalScroll$default(
                                                            Modifier.Companion.$$INSTANCE,
                                                            ScrollKt.rememberScrollState(
                                                                    composer2));
                                            Function2 function22 = Function2.this;
                                            ColumnMeasurePolicy columnMeasurePolicy =
                                                    ColumnKt.columnMeasurePolicy(
                                                            Arrangement.Top,
                                                            Alignment.Companion.Start,
                                                            composer2,
                                                            0);
                                            int currentCompositeKeyHash =
                                                    ComposablesKt.getCurrentCompositeKeyHash(
                                                            composer2);
                                            ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                            PersistentCompositionLocalMap
                                                    currentCompositionLocalScope =
                                                            composerImpl4
                                                                    .currentCompositionLocalScope();
                                            Modifier materializeModifier =
                                                    ComposedModifierKt.materializeModifier(
                                                            composer2, verticalScroll$default);
                                            ComposeUiNode.Companion.getClass();
                                            Function0 function0 =
                                                    ComposeUiNode.Companion.Constructor;
                                            if (!(composerImpl4.applier instanceof Applier)) {
                                                ComposablesKt.invalidApplier();
                                                throw null;
                                            }
                                            composerImpl4.startReusableNode();
                                            if (composerImpl4.inserting) {
                                                composerImpl4.createNode(function0);
                                            } else {
                                                composerImpl4.useNode();
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
                                            Function2 function23 =
                                                    ComposeUiNode.Companion.SetCompositeKeyHash;
                                            if (composerImpl4.inserting
                                                    || !Intrinsics.areEqual(
                                                            composerImpl4.rememberedValue(),
                                                            Integer.valueOf(
                                                                    currentCompositeKeyHash))) {
                                                AnimatedContentKt$$ExternalSyntheticOutline0.m(
                                                        currentCompositeKeyHash,
                                                        composerImpl4,
                                                        currentCompositeKeyHash,
                                                        function23);
                                            }
                                            Updater.m221setimpl(
                                                    composer2,
                                                    materializeModifier,
                                                    ComposeUiNode.Companion.SetModifier);
                                            function22.invoke(composer2, 0);
                                            composerImpl4.end(true);
                                            return Unit.INSTANCE;
                                        }
                                    },
                                    composerImpl2)
                            : null;
            composerImpl2.end(false);
            composerImpl = composerImpl2;
            AndroidAlertDialog_androidKt.m175AlertDialogOix01E0(
                    settingsAlertDialogKt$SettingsAlertDialog$1,
                    ComposableLambdaKt.rememberComposableLambda(
                            -350166655,
                            new Function2() { // from class:
                                              // com.android.settingslib.spa.widget.dialog.SettingsAlertDialogKt$SettingsAlertDialog$5
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(2);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj, Object obj2) {
                                    Composer composer2 = (Composer) obj;
                                    if ((((Number) obj2).intValue() & 11) == 2) {
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                        if (composerImpl3.getSkipping()) {
                                            composerImpl3.skipToGroupEnd();
                                            return Unit.INSTANCE;
                                        }
                                    }
                                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                    AlertDialogButton alertDialogButton3 = AlertDialogButton.this;
                                    if (alertDialogButton3 != null) {
                                        SettingsAlertDialogKt.access$Button(
                                                alertDialogPresenter,
                                                alertDialogButton3,
                                                composer2,
                                                0);
                                    }
                                    return Unit.INSTANCE;
                                }
                            },
                            composerImpl2),
                    m100width3ABfNKs,
                    rememberComposableLambda,
                    null,
                    rememberComposableLambda2,
                    rememberComposableLambda3,
                    null,
                    0L,
                    0L,
                    0L,
                    0L,
                    0.0f,
                    new DialogProperties(3),
                    composerImpl,
                    48,
                    3072,
                    8080);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.dialog.SettingsAlertDialogKt$SettingsAlertDialog$6
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SettingsAlertDialogKt.SettingsAlertDialog(
                                    AlertDialogPresenter.this,
                                    alertDialogButton,
                                    alertDialogButton2,
                                    str,
                                    function2,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    /* JADX WARN: Type inference failed for: r4v2, types: [com.android.settingslib.spa.widget.dialog.SettingsAlertDialogKt$Button$2, kotlin.jvm.internal.Lambda] */
    public static final void access$Button(
            final AlertDialogPresenter alertDialogPresenter,
            final AlertDialogButton alertDialogButton,
            Composer composer,
            final int i) {
        int i2;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1769331401);
        if ((i & 14) == 0) {
            i2 = (composerImpl2.changed(alertDialogPresenter) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl2.changed(alertDialogButton) ? 32 : 16;
        }
        if ((i2 & 91) == 18 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            composerImpl = composerImpl2;
            ButtonKt.TextButton(
                    new Function0() { // from class:
                                      // com.android.settingslib.spa.widget.dialog.SettingsAlertDialogKt$Button$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            SettingsAlertDialogKt$rememberAlertDialogPresenter$alertDialogPresenter$1$1
                                    settingsAlertDialogKt$rememberAlertDialogPresenter$alertDialogPresenter$1$1 =
                                            (SettingsAlertDialogKt$rememberAlertDialogPresenter$alertDialogPresenter$1$1)
                                                    alertDialogPresenter;
                            settingsAlertDialogKt$rememberAlertDialogPresenter$alertDialogPresenter$1$1
                                    .$openDialog$delegate.setValue(Boolean.FALSE);
                            alertDialogButton.onClick.mo1068invoke();
                            return Unit.INSTANCE;
                        }
                    },
                    null,
                    alertDialogButton.enabled,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    ComposableLambdaKt.rememberComposableLambda(
                            1154287980,
                            new Function3() { // from class:
                                              // com.android.settingslib.spa.widget.dialog.SettingsAlertDialogKt$Button$2
                                {
                                    super(3);
                                }

                                @Override // kotlin.jvm.functions.Function3
                                public final Object invoke(Object obj, Object obj2, Object obj3) {
                                    RowScope TextButton = (RowScope) obj;
                                    Composer composer2 = (Composer) obj2;
                                    int intValue = ((Number) obj3).intValue();
                                    Intrinsics.checkNotNullParameter(
                                            TextButton, "$this$TextButton");
                                    if ((intValue & 81) == 16) {
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                        if (composerImpl3.getSkipping()) {
                                            composerImpl3.skipToGroupEnd();
                                            return Unit.INSTANCE;
                                        }
                                    }
                                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                    TextKt.m210Text4IGK_g(
                                            AlertDialogButton.this.text,
                                            null,
                                            0L,
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
                                            null,
                                            composer2,
                                            0,
                                            0,
                                            131070);
                                    return Unit.INSTANCE;
                                }
                            },
                            composerImpl2),
                    composerImpl2,
                    805306368,
                    FileType.VCF);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.dialog.SettingsAlertDialogKt$Button$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SettingsAlertDialogKt.access$Button(
                                    AlertDialogPresenter.this,
                                    alertDialogButton,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public static final float getDialogWidth(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-2117715653);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        float f =
                r0.screenWidthDp
                        * (((Configuration)
                                                        composerImpl.consume(
                                                                AndroidCompositionLocals_androidKt
                                                                        .LocalConfiguration))
                                                .orientation
                                        == 2
                                ? 0.65f
                                : 0.85f);
        composerImpl.end(false);
        return f;
    }

    public static final SettingsAlertDialogKt$rememberAlertDialogPresenter$alertDialogPresenter$1$1
            rememberAlertDialogPresenter(
                    AlertDialogButton alertDialogButton,
                    AlertDialogButton alertDialogButton2,
                    String str,
                    ComposableLambdaImpl composableLambdaImpl,
                    Composer composer,
                    int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-1968157581);
        if ((i & 2) != 0) {
            alertDialogButton2 = null;
        }
        if ((i & 4) != 0) {
            str = null;
        }
        OpaqueKey opaqueKey = ComposerKt.invocation;
        MutableState mutableState =
                (MutableState)
                        RememberSaveableKt.rememberSaveable(
                                new Object[0],
                                null,
                                new Function0() { // from class:
                                                  // com.android.settingslib.spa.widget.dialog.SettingsAlertDialogKt$rememberAlertDialogPresenter$openDialog$2
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
        composerImpl.startReplaceGroup(1930022286);
        Object rememberedValue = composerImpl.rememberedValue();
        if (rememberedValue == Composer.Companion.Empty) {
            rememberedValue =
                    new SettingsAlertDialogKt$rememberAlertDialogPresenter$alertDialogPresenter$1$1(
                            mutableState);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        SettingsAlertDialogKt$rememberAlertDialogPresenter$alertDialogPresenter$1$1
                settingsAlertDialogKt$rememberAlertDialogPresenter$alertDialogPresenter$1$1 =
                        (SettingsAlertDialogKt$rememberAlertDialogPresenter$alertDialogPresenter$1$1)
                                rememberedValue;
        composerImpl.end(false);
        if (((Boolean) mutableState.getValue()).booleanValue()) {
            SettingsAlertDialog(
                    settingsAlertDialogKt$rememberAlertDialogPresenter$alertDialogPresenter$1$1,
                    alertDialogButton,
                    alertDialogButton2,
                    str,
                    composableLambdaImpl,
                    composerImpl,
                    24582);
        }
        composerImpl.end(false);
        return settingsAlertDialogKt$rememberAlertDialogPresenter$alertDialogPresenter$1$1;
    }
}
