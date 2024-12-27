package com.android.settings.spa.network;

import android.content.Context;

import androidx.compose.material.icons.outlined.SignalCellularAltKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableIntState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotIntStateKt;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.saveable.RememberSaveableKt;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.lifecycle.compose.FlowExtKt;

import com.android.settings.R;
import com.android.settings.network.SimOnboardingService;
import com.android.settingslib.spa.widget.preference.ListPreferenceKt;
import com.android.settingslib.spa.widget.preference.ListPreferenceModel;
import com.android.settingslib.spa.widget.scaffold.BottomAppBarButton;
import com.android.settingslib.spa.widget.scaffold.SuwScaffoldKt;
import com.android.settingslib.spa.widget.ui.IconKt;

import com.samsung.android.knox.custom.IKnoxCustomManager;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SafeFlow;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SimOnboardingPrimarySimKt {
    public static final void CreatePrimarySimListPreference(
            final String title,
            final List list,
            final MutableIntState selectedId,
            final ImageVector icon,
            final Function1 onIdSelected,
            Composer composer,
            final int i) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(list, "list");
        Intrinsics.checkNotNullParameter(selectedId, "selectedId");
        Intrinsics.checkNotNullParameter(icon, "icon");
        Intrinsics.checkNotNullParameter(onIdSelected, "onIdSelected");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-941407362);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        ListPreferenceKt.ListPreference(
                new ListPreferenceModel(
                        title,
                        list,
                        selectedId,
                        onIdSelected,
                        icon) { // from class:
                                // com.android.settings.spa.network.SimOnboardingPrimarySimKt$CreatePrimarySimListPreference$1
                    public final ComposableLambdaImpl icon;
                    public final Function1 onIdSelected;
                    public final List options;
                    public final MutableIntState selectedId;
                    public final String title;

                    {
                        this.title = title;
                        this.options = list;
                        this.selectedId = selectedId;
                        this.onIdSelected = onIdSelected;
                        this.icon =
                                new ComposableLambdaImpl(
                                        2122140001,
                                        true,
                                        new Function2() { // from class:
                                                          // com.android.settings.spa.network.SimOnboardingPrimarySimKt$CreatePrimarySimListPreference$1$icon$1
                                            {
                                                super(2);
                                            }

                                            @Override // kotlin.jvm.functions.Function2
                                            public final Object invoke(Object obj, Object obj2) {
                                                Composer composer2 = (Composer) obj;
                                                if ((((Number) obj2).intValue() & 11) == 2) {
                                                    ComposerImpl composerImpl2 =
                                                            (ComposerImpl) composer2;
                                                    if (composerImpl2.getSkipping()) {
                                                        composerImpl2.skipToGroupEnd();
                                                        return Unit.INSTANCE;
                                                    }
                                                }
                                                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                                IconKt.SettingsIcon(ImageVector.this, composer2, 0);
                                                return Unit.INSTANCE;
                                            }
                                        });
                    }

                    @Override // com.android.settingslib.spa.widget.preference.ListPreferenceModel
                    public final Function2 getIcon() {
                        return this.icon;
                    }

                    @Override // com.android.settingslib.spa.widget.preference.ListPreferenceModel
                    public final Function1 getOnIdSelected() {
                        return this.onIdSelected;
                    }

                    @Override // com.android.settingslib.spa.widget.preference.ListPreferenceModel
                    public final List getOptions() {
                        return this.options;
                    }

                    @Override // com.android.settingslib.spa.widget.preference.ListPreferenceModel
                    public final MutableIntState getSelectedId() {
                        return this.selectedId;
                    }

                    @Override // com.android.settingslib.spa.widget.preference.ListPreferenceModel
                    public final String getTitle() {
                        return this.title;
                    }
                },
                composerImpl,
                8);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.network.SimOnboardingPrimarySimKt$CreatePrimarySimListPreference$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SimOnboardingPrimarySimKt.CreatePrimarySimListPreference(
                                    title,
                                    list,
                                    selectedId,
                                    icon,
                                    onIdSelected,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.settings.spa.network.SimOnboardingPrimarySimKt$SimOnboardingPrimarySimImpl$1, kotlin.jvm.internal.Lambda] */
    public static final void SimOnboardingPrimarySimImpl(
            final Function0 nextAction,
            final Function0 cancelAction,
            final SimOnboardingService onboardingService,
            Composer composer,
            final int i) {
        Intrinsics.checkNotNullParameter(nextAction, "nextAction");
        Intrinsics.checkNotNullParameter(cancelAction, "cancelAction");
        Intrinsics.checkNotNullParameter(onboardingService, "onboardingService");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1835425836);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        SuwScaffoldKt.SuwScaffold(
                SignalCellularAltKt.getSignalCellularAlt(),
                StringResources_androidKt.stringResource(
                        composerImpl, R.string.sim_onboarding_primary_sim_title),
                new BottomAppBarButton(
                        StringResources_androidKt.stringResource(composerImpl, R.string.done),
                        true,
                        nextAction),
                new BottomAppBarButton(
                        StringResources_androidKt.stringResource(composerImpl, R.string.cancel),
                        true,
                        cancelAction),
                ComposableLambdaKt.rememberComposableLambda(
                        -1570411112,
                        new Function2() { // from class:
                                          // com.android.settings.spa.network.SimOnboardingPrimarySimKt$SimOnboardingPrimarySimImpl$1
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
                                MutableIntState mutableIntState =
                                        (MutableIntState)
                                                RememberSaveableKt.rememberSaveable(
                                                        new Object[0],
                                                        null,
                                                        new Function0() { // from class:
                                                                          // com.android.settings.spa.network.SimOnboardingPrimarySimKt$SimOnboardingPrimarySimImpl$1$callsSelectedId$1
                                                            @Override // kotlin.jvm.functions.Function0
                                                            /* renamed from: invoke */
                                                            public final Object mo1068invoke() {
                                                                return SnapshotIntStateKt
                                                                        .mutableIntStateOf(-1);
                                                            }
                                                        },
                                                        composer2,
                                                        3080,
                                                        6);
                                MutableIntState mutableIntState2 =
                                        (MutableIntState)
                                                RememberSaveableKt.rememberSaveable(
                                                        new Object[0],
                                                        null,
                                                        new Function0() { // from class:
                                                                          // com.android.settings.spa.network.SimOnboardingPrimarySimKt$SimOnboardingPrimarySimImpl$1$textsSelectedId$1
                                                            @Override // kotlin.jvm.functions.Function0
                                                            /* renamed from: invoke */
                                                            public final Object mo1068invoke() {
                                                                return SnapshotIntStateKt
                                                                        .mutableIntStateOf(-1);
                                                            }
                                                        },
                                                        composer2,
                                                        3080,
                                                        6);
                                MutableIntState mutableIntState3 =
                                        (MutableIntState)
                                                RememberSaveableKt.rememberSaveable(
                                                        new Object[0],
                                                        null,
                                                        new Function0() { // from class:
                                                                          // com.android.settings.spa.network.SimOnboardingPrimarySimKt$SimOnboardingPrimarySimImpl$1$mobileDataSelectedId$1
                                                            @Override // kotlin.jvm.functions.Function0
                                                            /* renamed from: invoke */
                                                            public final Object mo1068invoke() {
                                                                return SnapshotIntStateKt
                                                                        .mutableIntStateOf(-1);
                                                            }
                                                        },
                                                        composer2,
                                                        3080,
                                                        6);
                                SimOnboardingPageProviderKt.SimOnboardingMessage(
                                        StringResources_androidKt.stringResource(
                                                composer2, R.string.sim_onboarding_primary_sim_msg),
                                        composer2,
                                        0);
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                Context context =
                                        (Context)
                                                composerImpl3.consume(
                                                        AndroidCompositionLocals_androidKt
                                                                .LocalContext);
                                composerImpl3.startReplaceGroup(-1641258616);
                                SimOnboardingService simOnboardingService =
                                        SimOnboardingService.this;
                                Object rememberedValue = composerImpl3.rememberedValue();
                                Composer$Companion$Empty$1 composer$Companion$Empty$1 =
                                        Composer.Companion.Empty;
                                if (rememberedValue == composer$Companion$Empty$1) {
                                    rememberedValue =
                                            FlowKt.flowOn(
                                                    new SafeFlow(
                                                            new SimOnboardingPrimarySimKt$SimOnboardingPrimarySimImpl$1$primarySimInfo$1$1(
                                                                    context,
                                                                    simOnboardingService,
                                                                    null)),
                                                    Dispatchers.Default);
                                    composerImpl3.updateRememberedValue(rememberedValue);
                                }
                                composerImpl3.end(false);
                                PrimarySimRepository.PrimarySimInfo primarySimInfo =
                                        (PrimarySimRepository.PrimarySimInfo)
                                                FlowExtKt.collectAsStateWithLifecycle(
                                                                (Flow) rememberedValue,
                                                                null,
                                                                composerImpl3,
                                                                56)
                                                        .getValue();
                                if (primarySimInfo != null) {
                                    final SnapshotMutableIntStateImpl snapshotMutableIntStateImpl =
                                            (SnapshotMutableIntStateImpl) mutableIntState;
                                    snapshotMutableIntStateImpl.setIntValue(
                                            SimOnboardingService.this.targetPrimarySimCalls);
                                    final SnapshotMutableIntStateImpl snapshotMutableIntStateImpl2 =
                                            (SnapshotMutableIntStateImpl) mutableIntState2;
                                    snapshotMutableIntStateImpl2.setIntValue(
                                            SimOnboardingService.this.targetPrimarySimTexts);
                                    final SnapshotMutableIntStateImpl snapshotMutableIntStateImpl3 =
                                            (SnapshotMutableIntStateImpl) mutableIntState3;
                                    snapshotMutableIntStateImpl3.setIntValue(
                                            SimOnboardingService.this.targetPrimarySimMobileData);
                                    final MutableState collectAsStateWithLifecycle =
                                            FlowExtKt.collectAsStateWithLifecycle(
                                                    SimOnboardingService.this
                                                            .targetPrimarySimAutoDataSwitch,
                                                    null,
                                                    composerImpl3,
                                                    56);
                                    final SimOnboardingService simOnboardingService2 =
                                            SimOnboardingService.this;
                                    NetworkCellularGroupProviderKt.PrimarySimImpl(
                                            primarySimInfo,
                                            snapshotMutableIntStateImpl,
                                            snapshotMutableIntStateImpl2,
                                            snapshotMutableIntStateImpl3,
                                            null,
                                            null,
                                            null,
                                            null,
                                            new Function1() { // from class:
                                                              // com.android.settings.spa.network.SimOnboardingPrimarySimKt$SimOnboardingPrimarySimImpl$1.1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(1);
                                                }

                                                @Override // kotlin.jvm.functions.Function1
                                                public final Object invoke(Object obj3) {
                                                    int intValue = ((Number) obj3).intValue();
                                                    ((SnapshotMutableIntStateImpl)
                                                                    snapshotMutableIntStateImpl)
                                                            .setIntValue(intValue);
                                                    simOnboardingService2.targetPrimarySimCalls =
                                                            intValue;
                                                    return Unit.INSTANCE;
                                                }
                                            },
                                            new Function1() { // from class:
                                                              // com.android.settings.spa.network.SimOnboardingPrimarySimKt$SimOnboardingPrimarySimImpl$1.2
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(1);
                                                }

                                                @Override // kotlin.jvm.functions.Function1
                                                public final Object invoke(Object obj3) {
                                                    int intValue = ((Number) obj3).intValue();
                                                    ((SnapshotMutableIntStateImpl)
                                                                    snapshotMutableIntStateImpl2)
                                                            .setIntValue(intValue);
                                                    simOnboardingService2.targetPrimarySimTexts =
                                                            intValue;
                                                    return Unit.INSTANCE;
                                                }
                                            },
                                            new Function1() { // from class:
                                                              // com.android.settings.spa.network.SimOnboardingPrimarySimKt$SimOnboardingPrimarySimImpl$1.3
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(1);
                                                }

                                                @Override // kotlin.jvm.functions.Function1
                                                public final Object invoke(Object obj3) {
                                                    int intValue = ((Number) obj3).intValue();
                                                    ((SnapshotMutableIntStateImpl)
                                                                    snapshotMutableIntStateImpl3)
                                                            .setIntValue(intValue);
                                                    simOnboardingService2
                                                                    .targetPrimarySimMobileData =
                                                            intValue;
                                                    return Unit.INSTANCE;
                                                }
                                            },
                                            composerImpl3,
                                            8,
                                            0,
                                            IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp);
                                    composerImpl3.startReplaceGroup(-1641257077);
                                    boolean changed =
                                            composerImpl3.changed(collectAsStateWithLifecycle);
                                    Object rememberedValue2 = composerImpl3.rememberedValue();
                                    if (changed || rememberedValue2 == composer$Companion$Empty$1) {
                                        rememberedValue2 =
                                                new Function0() { // from class:
                                                                  // com.android.settings.spa.network.SimOnboardingPrimarySimKt$SimOnboardingPrimarySimImpl$1$4$1
                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    {
                                                        super(0);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function0
                                                    /* renamed from: invoke */
                                                    public final Object mo1068invoke() {
                                                        return (Boolean)
                                                                collectAsStateWithLifecycle
                                                                        .getValue();
                                                    }
                                                };
                                        composerImpl3.updateRememberedValue(rememberedValue2);
                                    }
                                    composerImpl3.end(false);
                                    final SimOnboardingService simOnboardingService3 =
                                            SimOnboardingService.this;
                                    AutomaticDataSwitchingPreferenceKt
                                            .AutomaticDataSwitchingPreference(
                                                    (Function0) rememberedValue2,
                                                    new Function1() { // from class:
                                                                      // com.android.settings.spa.network.SimOnboardingPrimarySimKt$SimOnboardingPrimarySimImpl$1.5
                                                        {
                                                            super(1);
                                                        }

                                                        @Override // kotlin.jvm.functions.Function1
                                                        public final Object invoke(Object obj3) {
                                                            Boolean bool = (Boolean) obj3;
                                                            bool.getClass();
                                                            SimOnboardingService.this
                                                                    .targetPrimarySimAutoDataSwitch
                                                                    .updateState(null, bool);
                                                            return Unit.INSTANCE;
                                                        }
                                                    },
                                                    composerImpl3,
                                                    0);
                                }
                                return Unit.INSTANCE;
                            }
                        },
                        composerImpl),
                composerImpl,
                24576,
                0);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.network.SimOnboardingPrimarySimKt$SimOnboardingPrimarySimImpl$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SimOnboardingPrimarySimKt.SimOnboardingPrimarySimImpl(
                                    Function0.this,
                                    cancelAction,
                                    onboardingService,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
