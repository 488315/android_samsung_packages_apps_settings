package com.android.settings.spa.network;

import android.telephony.SubscriptionInfo;
import android.util.Log;

import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material.icons.outlined.SignalCellularAltKt;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.StructuralEqualityPolicy;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.platform.TestTagKt;
import androidx.compose.ui.res.StringResources_androidKt;

import com.android.settings.R;
import com.android.settings.network.SimOnboardingService;
import com.android.settingslib.spa.framework.theme.SettingsDimension;
import com.android.settingslib.spa.widget.dialog.AlertDialogButton;
import com.android.settingslib.spa.widget.dialog.AlertDialogPresenter;
import com.android.settingslib.spa.widget.dialog.SettingsAlertDialogKt;
import com.android.settingslib.spa.widget.dialog.SettingsAlertDialogKt$rememberAlertDialogPresenter$alertDialogPresenter$1$1;
import com.android.settingslib.spa.widget.editor.SettingsOutlinedTextFieldKt;
import com.android.settingslib.spa.widget.preference.PreferenceKt;
import com.android.settingslib.spa.widget.preference.PreferenceModel;
import com.android.settingslib.spa.widget.scaffold.BottomAppBarButton;
import com.android.settingslib.spa.widget.scaffold.SuwScaffoldKt;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.presence.ServiceTuple;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KFunction;
import kotlin.text.StringsKt__StringsJVMKt;

import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SimOnboardingLabelSimKt {
    /* JADX WARN: Type inference failed for: r4v2, types: [com.android.settings.spa.network.SimOnboardingLabelSimKt$LabelSimPreference$alertDialogPresenter$3, kotlin.jvm.internal.Lambda] */
    public static final void LabelSimPreference(
            final SimOnboardingService simOnboardingService,
            final SubscriptionInfo subscriptionInfo,
            Composer composer,
            final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-719185756);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final String obj = subscriptionInfo.getDisplayName().toString();
        composerImpl.startReplaceGroup(-1702938620);
        Object rememberedValue = composerImpl.rememberedValue();
        if (rememberedValue == Composer.Companion.Empty) {
            rememberedValue =
                    SnapshotStateKt.mutableStateOf(
                            simOnboardingService.getSubscriptionInfoDisplayName(subscriptionInfo),
                            StructuralEqualityPolicy.INSTANCE);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        final MutableState mutableState = (MutableState) rememberedValue;
        composerImpl.end(false);
        final MutableState phoneNumber = SimsSectionKt.phoneNumber(subscriptionInfo, composerImpl);
        final SettingsAlertDialogKt$rememberAlertDialogPresenter$alertDialogPresenter$1$1
                rememberAlertDialogPresenter =
                        SettingsAlertDialogKt.rememberAlertDialogPresenter(
                                new AlertDialogButton(
                                        StringResources_androidKt.stringResource(
                                                composerImpl,
                                                R.string.mobile_network_sim_name_rename),
                                        !StringsKt__StringsJVMKt.isBlank(
                                                (String) mutableState.getValue()),
                                        new Function0() { // from class:
                                                          // com.android.settings.spa.network.SimOnboardingLabelSimKt$LabelSimPreference$alertDialogPresenter$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            /* renamed from: invoke */
                                            public final Object mo1068invoke() {
                                                SimOnboardingService simOnboardingService2 =
                                                        SimOnboardingService.this;
                                                SubscriptionInfo subInfo = subscriptionInfo;
                                                String newName =
                                                        ((String) mutableState.getValue()).length()
                                                                        == 0
                                                                ? obj
                                                                : (String) mutableState.getValue();
                                                simOnboardingService2.getClass();
                                                Intrinsics.checkNotNullParameter(
                                                        subInfo, "subInfo");
                                                Intrinsics.checkNotNullParameter(
                                                        newName, "newName");
                                                if (!Intrinsics.areEqual(
                                                        subInfo.getDisplayName(), newName)) {
                                                    simOnboardingService2.renameMutableMap.put(
                                                            Integer.valueOf(
                                                                    subInfo.getSubscriptionId()),
                                                            newName);
                                                    Log.d(
                                                            "SimOnboardingService",
                                                            "renameMutableMap add "
                                                                    + subInfo.getSubscriptionId()
                                                                    + " & "
                                                                    + newName
                                                                    + " into: "
                                                                    + simOnboardingService2
                                                                            .renameMutableMap);
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        }),
                                new AlertDialogButton(
                                        StringResources_androidKt.stringResource(
                                                composerImpl, R.string.cancel),
                                        new Function0() { // from class:
                                                          // com.android.settings.spa.network.SimOnboardingLabelSimKt$LabelSimPreference$alertDialogPresenter$2
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            /* renamed from: invoke */
                                            public final Object mo1068invoke() {
                                                mutableState.setValue(
                                                        SimOnboardingService.this
                                                                .getSubscriptionInfoDisplayName(
                                                                        subscriptionInfo));
                                                return Unit.INSTANCE;
                                            }
                                        },
                                        2),
                                StringResources_androidKt.stringResource(
                                        composerImpl,
                                        R.string.sim_onboarding_label_sim_dialog_title),
                                ComposableLambdaKt.rememberComposableLambda(
                                        -72957116,
                                        new Function2() { // from class:
                                                          // com.android.settings.spa.network.SimOnboardingLabelSimKt$LabelSimPreference$alertDialogPresenter$3
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(2);
                                            }

                                            /* JADX WARN: Type inference failed for: r4v1, types: [com.android.settings.spa.network.SimOnboardingLabelSimKt$LabelSimPreference$alertDialogPresenter$3$1, kotlin.jvm.internal.Lambda] */
                                            @Override // kotlin.jvm.functions.Function2
                                            public final Object invoke(Object obj2, Object obj3) {
                                                Composer composer2 = (Composer) obj2;
                                                if ((((Number) obj3).intValue() & 11) == 2) {
                                                    ComposerImpl composerImpl2 =
                                                            (ComposerImpl) composer2;
                                                    if (composerImpl2.getSkipping()) {
                                                        composerImpl2.skipToGroupEnd();
                                                        return Unit.INSTANCE;
                                                    }
                                                }
                                                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                                String str = (String) phoneNumber.getValue();
                                                if (str == null) {
                                                    str = ApnSettings.MVNO_NONE;
                                                }
                                                TextKt.m210Text4IGK_g(
                                                        str,
                                                        PaddingKt.m89paddingqDBjuR0$default(
                                                                Modifier.Companion.$$INSTANCE,
                                                                0.0f,
                                                                0.0f,
                                                                0.0f,
                                                                SettingsDimension
                                                                        .itemPaddingVertical,
                                                                7),
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
                                                        131068);
                                                String str2 = (String) mutableState.getValue();
                                                String stringResource =
                                                        StringResources_androidKt.stringResource(
                                                                composer2,
                                                                R.string
                                                                        .sim_onboarding_label_sim_dialog_label);
                                                final String str3 = obj;
                                                ComposableLambdaImpl rememberComposableLambda =
                                                        ComposableLambdaKt.rememberComposableLambda(
                                                                1367398779,
                                                                new Function2() { // from class:
                                                                                  // com.android.settings.spa.network.SimOnboardingLabelSimKt$LabelSimPreference$alertDialogPresenter$3.1
                                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                    {
                                                                        super(2);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function2
                                                                    public final Object invoke(
                                                                            Object obj4,
                                                                            Object obj5) {
                                                                        Composer composer3 =
                                                                                (Composer) obj4;
                                                                        if ((((Number) obj5)
                                                                                                .intValue()
                                                                                        & 11)
                                                                                == 2) {
                                                                            ComposerImpl
                                                                                    composerImpl3 =
                                                                                            (ComposerImpl)
                                                                                                    composer3;
                                                                            if (composerImpl3
                                                                                    .getSkipping()) {
                                                                                composerImpl3
                                                                                        .skipToGroupEnd();
                                                                                return Unit
                                                                                        .INSTANCE;
                                                                            }
                                                                        }
                                                                        OpaqueKey opaqueKey3 =
                                                                                ComposerKt
                                                                                        .invocation;
                                                                        TextKt.m210Text4IGK_g(
                                                                                str3, null, 0L, 0L,
                                                                                null, null, null,
                                                                                0L, null, null, 0L,
                                                                                0, false, 0, 0,
                                                                                null, null,
                                                                                composer3, 0, 0,
                                                                                131070);
                                                                        return Unit.INSTANCE;
                                                                    }
                                                                },
                                                                composer2);
                                                Modifier testTag =
                                                        TestTagKt.testTag(
                                                                SizeKt.FillWholeMaxWidth,
                                                                "contentInput");
                                                ComposerImpl composerImpl3 =
                                                        (ComposerImpl) composer2;
                                                composerImpl3.startReplaceGroup(-339906104);
                                                final MutableState mutableState2 = mutableState;
                                                Object rememberedValue2 =
                                                        composerImpl3.rememberedValue();
                                                if (rememberedValue2 == Composer.Companion.Empty) {
                                                    rememberedValue2 =
                                                            new Function1() { // from class:
                                                                              // com.android.settings.spa.network.SimOnboardingLabelSimKt$LabelSimPreference$alertDialogPresenter$3$2$1
                                                                {
                                                                    super(1);
                                                                }

                                                                @Override // kotlin.jvm.functions.Function1
                                                                public final Object invoke(
                                                                        Object obj4) {
                                                                    String it = (String) obj4;
                                                                    Intrinsics
                                                                            .checkNotNullParameter(
                                                                                    it, "it");
                                                                    MutableState.this.setValue(it);
                                                                    return Unit.INSTANCE;
                                                                }
                                                            };
                                                    composerImpl3.updateRememberedValue(
                                                            rememberedValue2);
                                                }
                                                composerImpl3.end(false);
                                                SettingsOutlinedTextFieldKt
                                                        .SettingsOutlinedTextField(
                                                                str2,
                                                                stringResource,
                                                                null,
                                                                false,
                                                                false,
                                                                null,
                                                                rememberComposableLambda,
                                                                testTag,
                                                                (Function1) rememberedValue2,
                                                                composerImpl3,
                                                                114819072,
                                                                60);
                                                return Unit.INSTANCE;
                                            }
                                        },
                                        composerImpl),
                                composerImpl,
                                0);
        PreferenceKt.Preference(
                new PreferenceModel(
                        rememberAlertDialogPresenter,
                        mutableState,
                        phoneNumber) { // from class:
                                       // com.android.settings.spa.network.SimOnboardingLabelSimKt$LabelSimPreference$1
                    public final KFunction onClick;
                    public final Function0 summary;
                    public final String title;

                    {
                        this.title = (String) mutableState.getValue();
                        this.summary =
                                new Function0() { // from class:
                                                  // com.android.settings.spa.network.SimOnboardingLabelSimKt$LabelSimPreference$1$summary$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    /* renamed from: invoke */
                                    public final Object mo1068invoke() {
                                        String str = (String) phoneNumber.getValue();
                                        return str == null ? ApnSettings.MVNO_NONE : str;
                                    }
                                };
                        this.onClick =
                                new SimOnboardingLabelSimKt$LabelSimPreference$1$onClick$1(
                                        0,
                                        rememberAlertDialogPresenter,
                                        AlertDialogPresenter.class,
                                        ServiceTuple.BASIC_STATUS_OPEN,
                                        "open()V",
                                        0);
                    }

                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final Function0 getOnClick() {
                        return (Function0) this.onClick;
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
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.network.SimOnboardingLabelSimKt$LabelSimPreference$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj2, Object obj3) {
                            ((Number) obj3).intValue();
                            SimOnboardingLabelSimKt.LabelSimPreference(
                                    SimOnboardingService.this,
                                    subscriptionInfo,
                                    (Composer) obj2,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.settings.spa.network.SimOnboardingLabelSimKt$SimOnboardingLabelSimImpl$1, kotlin.jvm.internal.Lambda] */
    public static final void SimOnboardingLabelSimImpl(
            final Function0 nextAction,
            final Function0 cancelAction,
            final SimOnboardingService onboardingService,
            Composer composer,
            final int i) {
        Intrinsics.checkNotNullParameter(nextAction, "nextAction");
        Intrinsics.checkNotNullParameter(cancelAction, "cancelAction");
        Intrinsics.checkNotNullParameter(onboardingService, "onboardingService");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1992969800);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        SuwScaffoldKt.SuwScaffold(
                SignalCellularAltKt.getSignalCellularAlt(),
                StringResources_androidKt.stringResource(
                        composerImpl, R.string.sim_onboarding_label_sim_title),
                new BottomAppBarButton(
                        StringResources_androidKt.stringResource(
                                composerImpl, R.string.sim_onboarding_next),
                        true,
                        nextAction),
                new BottomAppBarButton(
                        StringResources_androidKt.stringResource(composerImpl, R.string.cancel),
                        true,
                        cancelAction),
                ComposableLambdaKt.rememberComposableLambda(
                        1031209340,
                        new Function2() { // from class:
                                          // com.android.settings.spa.network.SimOnboardingLabelSimKt$SimOnboardingLabelSimImpl$1
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
                                SimOnboardingLabelSimKt.access$LabelSimBody(
                                        SimOnboardingService.this, composer2, 8);
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
                                      // com.android.settings.spa.network.SimOnboardingLabelSimKt$SimOnboardingLabelSimImpl$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SimOnboardingLabelSimKt.SimOnboardingLabelSimImpl(
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

    public static final void access$LabelSimBody(
            final SimOnboardingService simOnboardingService, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1276313636);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        SimOnboardingPageProviderKt.SimOnboardingMessage(
                StringResources_androidKt.stringResource(
                        composerImpl, R.string.sim_onboarding_label_sim_msg),
                composerImpl,
                0);
        Iterator it = simOnboardingService.getSelectableSubscriptionInfoList().iterator();
        while (it.hasNext()) {
            LabelSimPreference(
                    simOnboardingService, (SubscriptionInfo) it.next(), composerImpl, 72);
        }
        OpaqueKey opaqueKey2 = ComposerKt.invocation;
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.network.SimOnboardingLabelSimKt$LabelSimBody$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SimOnboardingLabelSimKt.access$LabelSimBody(
                                    SimOnboardingService.this,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
