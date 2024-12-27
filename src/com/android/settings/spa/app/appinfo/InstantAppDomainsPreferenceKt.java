package com.android.settings.spa.app.appinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.material3.AndroidAlertDialog_androidKt;
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
import androidx.compose.runtime.State;
import androidx.compose.runtime.StructuralEqualityPolicy;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.saveable.RememberSaveableKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.lifecycle.compose.FlowExtKt;

import com.android.settings.R;
import com.android.settingslib.spa.framework.theme.SettingsDimension;
import com.android.settingslib.spa.widget.preference.PreferenceKt;
import com.android.settingslib.spa.widget.preference.PreferenceModel;

import kotlin.Unit;
import kotlin.collections.EmptySet;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class InstantAppDomainsPreferenceKt {
    /* JADX WARN: Type inference failed for: r4v3, types: [com.android.settings.spa.app.appinfo.InstantAppDomainsPreferenceKt$Dialog$1, kotlin.jvm.internal.Lambda] */
    public static final void Dialog(
            final State state, final Function0 function0, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1255121686);
        if ((i & 14) == 0) {
            i2 = (composerImpl2.changed(state) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl2.changedInstance(function0) ? 32 : 16;
        }
        if ((i2 & 91) == 18 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            composerImpl = composerImpl2;
            AndroidAlertDialog_androidKt.m175AlertDialogOix01E0(
                    function0,
                    ComposableSingletons$InstantAppDomainsPreferenceKt.f44lambda1,
                    null,
                    null,
                    null,
                    ComposableSingletons$InstantAppDomainsPreferenceKt.f45lambda2,
                    ComposableLambdaKt.rememberComposableLambda(
                            341323939,
                            new Function2() { // from class:
                                              // com.android.settings.spa.app.appinfo.InstantAppDomainsPreferenceKt$Dialog$1
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
                                    State state2 = State.this;
                                    Modifier.Companion companion = Modifier.Companion.$$INSTANCE;
                                    boolean z = false;
                                    ColumnMeasurePolicy columnMeasurePolicy =
                                            ColumnKt.columnMeasurePolicy(
                                                    Arrangement.Top,
                                                    Alignment.Companion.Start,
                                                    composer2,
                                                    0);
                                    int currentCompositeKeyHash =
                                            ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                    ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                    PersistentCompositionLocalMap currentCompositionLocalScope =
                                            composerImpl4.currentCompositionLocalScope();
                                    Modifier materializeModifier =
                                            ComposedModifierKt.materializeModifier(
                                                    composer2, companion);
                                    ComposeUiNode.Companion.getClass();
                                    Function0 function02 = ComposeUiNode.Companion.Constructor;
                                    if (!(composerImpl4.applier instanceof Applier)) {
                                        ComposablesKt.invalidApplier();
                                        throw null;
                                    }
                                    composerImpl4.startReusableNode();
                                    if (composerImpl4.inserting) {
                                        composerImpl4.createNode(function02);
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
                                            ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                    Function2 function2 =
                                            ComposeUiNode.Companion.SetCompositeKeyHash;
                                    if (composerImpl4.inserting
                                            || !Intrinsics.areEqual(
                                                    composerImpl4.rememberedValue(),
                                                    Integer.valueOf(currentCompositeKeyHash))) {
                                        AnimatedContentKt$$ExternalSyntheticOutline0.m(
                                                currentCompositeKeyHash,
                                                composerImpl4,
                                                currentCompositeKeyHash,
                                                function2);
                                    }
                                    Updater.m221setimpl(
                                            composer2,
                                            materializeModifier,
                                            ComposeUiNode.Companion.SetModifier);
                                    composerImpl4.startReplaceGroup(2092713139);
                                    Iterator it = ((Iterable) state2.getValue()).iterator();
                                    while (it.hasNext()) {
                                        Composer composer3 = composer2;
                                        TextKt.m210Text4IGK_g(
                                                (String) it.next(),
                                                PaddingKt.m87paddingVpY3zN4$default(
                                                        companion,
                                                        0.0f,
                                                        SettingsDimension.itemPaddingAround,
                                                        1),
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
                                                composer3,
                                                0,
                                                0,
                                                131068);
                                        composerImpl4 = composerImpl4;
                                        companion = companion;
                                        composer2 = composer3;
                                        z = false;
                                    }
                                    ComposerImpl composerImpl5 = composerImpl4;
                                    composerImpl5.end(z);
                                    composerImpl5.end(true);
                                    OpaqueKey opaqueKey3 = ComposerKt.invocation;
                                    return Unit.INSTANCE;
                                }
                            },
                            composerImpl2),
                    null,
                    0L,
                    0L,
                    0L,
                    0L,
                    0.0f,
                    null,
                    composerImpl,
                    ((i2 >> 3) & 14) | 1769520,
                    0,
                    16284);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.appinfo.InstantAppDomainsPreferenceKt$Dialog$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            InstantAppDomainsPreferenceKt.Dialog(
                                    State.this,
                                    function0,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public static final void InstantAppDomainsPreference(
            final ApplicationInfo app, Composer composer, final int i) {
        Intrinsics.checkNotNullParameter(app, "app");
        final ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(991873661);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Context context =
                (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        if (!app.isInstantApp()) {
            RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
            if (endRestartGroup != null) {
                endRestartGroup.block =
                        new Function2() { // from class:
                                          // com.android.settings.spa.app.appinfo.InstantAppDomainsPreferenceKt$InstantAppDomainsPreference$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                InstantAppDomainsPreferenceKt.InstantAppDomainsPreference(
                                        app,
                                        (Composer) obj,
                                        RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                return Unit.INSTANCE;
                            }
                        };
                return;
            }
            return;
        }
        composerImpl.startReplaceGroup(-1999516188);
        Object rememberedValue = composerImpl.rememberedValue();
        Object obj = Composer.Companion.Empty;
        if (rememberedValue == obj) {
            rememberedValue = new InstantAppDomainsPresenter(context, app);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        InstantAppDomainsPresenter instantAppDomainsPresenter =
                (InstantAppDomainsPresenter) rememberedValue;
        composerImpl.end(false);
        final MutableState mutableState =
                (MutableState)
                        RememberSaveableKt.rememberSaveable(
                                new Object[0],
                                null,
                                new Function0() { // from class:
                                                  // com.android.settings.spa.app.appinfo.InstantAppDomainsPreferenceKt$InstantAppDomainsPreference$openDialog$2
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
        final MutableState collectAsStateWithLifecycle =
                FlowExtKt.collectAsStateWithLifecycle(
                        instantAppDomainsPresenter.summaryFlow,
                        StringResources_androidKt.stringResource(
                                composerImpl, R.string.summary_placeholder),
                        composerImpl,
                        8);
        PreferenceKt.Preference(
                new PreferenceModel(
                        composerImpl,
                        collectAsStateWithLifecycle,
                        mutableState) { // from class:
                                        // com.android.settings.spa.app.appinfo.InstantAppDomainsPreferenceKt$InstantAppDomainsPreference$2
                    public final Function0 onClick;
                    public final Function0 summary;
                    public final String title;

                    {
                        this.title =
                                StringResources_androidKt.stringResource(
                                        composerImpl,
                                        R.string.app_launch_supported_domain_urls_title);
                        this.summary =
                                new Function0() { // from class:
                                                  // com.android.settings.spa.app.appinfo.InstantAppDomainsPreferenceKt$InstantAppDomainsPreference$2$summary$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    /* renamed from: invoke */
                                    public final Object mo1068invoke() {
                                        return (String) collectAsStateWithLifecycle.getValue();
                                    }
                                };
                        this.onClick =
                                new Function0() { // from class:
                                                  // com.android.settings.spa.app.appinfo.InstantAppDomainsPreferenceKt$InstantAppDomainsPreference$2$onClick$1
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
                },
                false,
                composerImpl,
                0,
                2);
        MutableState collectAsStateWithLifecycle2 =
                FlowExtKt.collectAsStateWithLifecycle(
                        instantAppDomainsPresenter.domainsFlow,
                        EmptySet.INSTANCE,
                        composerImpl,
                        56);
        if (((Boolean) mutableState.getValue()).booleanValue()) {
            composerImpl.startReplaceGroup(-1999515533);
            boolean changed = composerImpl.changed(mutableState);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (changed || rememberedValue2 == obj) {
                rememberedValue2 =
                        new Function0() { // from class:
                                          // com.android.settings.spa.app.appinfo.InstantAppDomainsPreferenceKt$InstantAppDomainsPreference$3$1
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
            Dialog(collectAsStateWithLifecycle2, (Function0) rememberedValue2, composerImpl, 0);
        }
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            endRestartGroup2.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.appinfo.InstantAppDomainsPreferenceKt$InstantAppDomainsPreference$4
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj2, Object obj3) {
                            ((Number) obj3).intValue();
                            InstantAppDomainsPreferenceKt.InstantAppDomainsPreference(
                                    app,
                                    (Composer) obj2,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
