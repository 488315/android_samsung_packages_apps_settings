package com.android.settings.network;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.UiccCardInfo;
import android.telephony.UiccSlotInfo;
import android.util.Log;

import androidx.activity.compose.PredictiveBackHandlerKt$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.FillElement;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.AlertDialogDefaults;
import androidx.compose.material3.AlertDialogKt;
import androidx.compose.material3.ColorSchemeKt;
import androidx.compose.material3.ProgressIndicatorKt;
import androidx.compose.material3.ShapesKt;
import androidx.compose.material3.SurfaceKt;
import androidx.compose.material3.tokens.ColorSchemeKeyTokens;
import androidx.compose.material3.tokens.DialogTokens;
import androidx.compose.material3.tokens.ShapeKeyTokens;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionScopedCoroutineScopeCanceller;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.StructuralEqualityPolicy;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.saveable.RememberSaveableKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.compose.LocalLifecycleOwnerKt;

import com.android.settings.R;
import com.android.settings.SidecarFragment;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.network.telephony.ToggleSubscriptionDialogActivity;
import com.android.settings.spa.SpaActivity;
import com.android.settings.spa.network.SimOnboardingPageProvider;
import com.android.settings.wifi.WifiPickerTrackerHelper;
import com.android.settingslib.spa.SpaBaseDialogActivity;
import com.android.settingslib.spa.framework.theme.SettingsDimension;
import com.android.settingslib.spa.framework.util.FlowsKt;
import com.android.settingslib.spa.widget.dialog.AlertDialogButton;
import com.android.settingslib.spa.widget.dialog.SettingsAlertDialogKt;
import com.android.settingslib.spa.widget.dialog.SettingsAlertDialogKt$rememberAlertDialogPresenter$alertDialogPresenter$1$1;
import com.android.settingslib.spa.widget.dialog.SettingsAlertDialogWithIconKt;
import com.android.settingslib.spa.widget.ui.TextKt;
import com.android.settingslib.spaprivileged.framework.common.ContextsKt;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"
        },
        d2 = {
            "Lcom/android/settings/network/SimOnboardingActivity;",
            "Lcom/android/settingslib/spa/SpaBaseDialogActivity;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class SimOnboardingActivity extends SpaBaseDialogActivity {
    public static final SimOnboardingService onboardingService = new SimOnboardingService();
    public final Function1 callbackListener = new SimOnboardingActivity$callbackListener$1(this);
    public Context context;
    public EnableMultiSimSidecar enableMultiSimSidecar;
    public CoroutineScope scope;
    public MutableState showDsdsProgressDialog;
    public MutableState showError;
    public MutableState showProgressDialog;
    public MutableState showRestartDialog;
    public MutableState showStartingDialog;
    public SwitchToEuiccSubscriptionSidecar switchToEuiccSubscriptionSidecar;
    public SwitchToRemovableSlotSidecar switchToRemovableSlotSidecar;
    public WifiPickerTrackerHelper wifiPickerTrackerHelper;

    public static void initServiceData(final Context context, int i, Function1 callback) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(callback, "callback");
        final SimOnboardingService simOnboardingService = onboardingService;
        simOnboardingService.getClass();
        simOnboardingService.clear();
        simOnboardingService.callback = callback;
        simOnboardingService.targetSubId = i;
        simOnboardingService.subscriptionManager =
                (SubscriptionManager) context.getSystemService(SubscriptionManager.class);
        simOnboardingService.telephonyManager =
                (TelephonyManager) context.getSystemService(TelephonyManager.class);
        List activeSubscriptions =
                SubscriptionUtil.getActiveSubscriptions(simOnboardingService.subscriptionManager);
        Intrinsics.checkNotNullExpressionValue(activeSubscriptions, "getActiveSubscriptions(...)");
        simOnboardingService.activeSubInfoList = activeSubscriptions;
        Log.d(
                "SimOnboardingService",
                "startInit: targetSubId:"
                        + simOnboardingService.targetSubId
                        + ", activeSubInfoList: "
                        + activeSubscriptions);
        ThreadUtils.postOnBackgroundThread(
                new Runnable() { // from class:
                                 // com.android.settings.network.SimOnboardingService$initData$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Object obj;
                        List list;
                        UiccSlotInfo[] uiccSlotsInfo;
                        SimOnboardingService simOnboardingService2 = simOnboardingService;
                        List availableSubscriptions =
                                SubscriptionUtil.getAvailableSubscriptions(context);
                        Intrinsics.checkNotNullExpressionValue(
                                availableSubscriptions, "getAvailableSubscriptions(...)");
                        simOnboardingService2.getClass();
                        simOnboardingService2.availableSubInfoList = availableSubscriptions;
                        SimOnboardingService simOnboardingService3 = simOnboardingService;
                        Iterator it = simOnboardingService3.availableSubInfoList.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                obj = null;
                                break;
                            } else {
                                obj = it.next();
                                if (((SubscriptionInfo) obj).getSubscriptionId()
                                        == simOnboardingService3.targetSubId) {
                                    break;
                                }
                            }
                        }
                        simOnboardingService3.targetSubInfo = (SubscriptionInfo) obj;
                        SimOnboardingService simOnboardingService4 = simOnboardingService;
                        SubscriptionInfo subscriptionInfo = simOnboardingService4.targetSubInfo;
                        if (subscriptionInfo != null) {
                            ((ArrayList) simOnboardingService4.userSelectedSubInfoList)
                                    .add(subscriptionInfo);
                        }
                        SimOnboardingService simOnboardingService5 = simOnboardingService;
                        Log.d(
                                "SimOnboardingService",
                                "targetSubId: "
                                        + simOnboardingService5.targetSubId
                                        + " , targetSubInfo: "
                                        + simOnboardingService5.targetSubInfo);
                        SimOnboardingService simOnboardingService6 = simOnboardingService;
                        TelephonyManager telephonyManager = simOnboardingService6.telephonyManager;
                        if (telephonyManager == null
                                || (uiccSlotsInfo = telephonyManager.getUiccSlotsInfo()) == null
                                || (list = ArraysKt___ArraysKt.toList(uiccSlotsInfo)) == null) {
                            list = EmptyList.INSTANCE;
                        }
                        Intrinsics.checkNotNullParameter(list, "<set-?>");
                        simOnboardingService6.slotInfoList = list;
                        Log.d(
                                "SimOnboardingService",
                                "slotInfoList: " + simOnboardingService.slotInfoList + ".");
                        SimOnboardingService simOnboardingService7 = simOnboardingService;
                        TelephonyManager telephonyManager2 = simOnboardingService7.telephonyManager;
                        List<UiccCardInfo> uiccCardsInfo =
                                telephonyManager2 != null
                                        ? telephonyManager2.getUiccCardsInfo()
                                        : null;
                        Intrinsics.checkNotNull(uiccCardsInfo);
                        simOnboardingService7.uiccCardInfoList = uiccCardsInfo;
                        Log.d(
                                "SimOnboardingService",
                                "uiccCardInfoList: " + simOnboardingService.uiccCardInfoList);
                        simOnboardingService.targetPrimarySimCalls =
                                SubscriptionManager.getDefaultVoiceSubscriptionId();
                        simOnboardingService.targetPrimarySimTexts =
                                SubscriptionManager.getDefaultSmsSubscriptionId();
                        simOnboardingService.targetPrimarySimMobileData =
                                SubscriptionManager.getDefaultDataSubscriptionId();
                        SubscriptionInfo subscriptionInfo2 = simOnboardingService.targetSubInfo;
                        boolean z = false;
                        boolean isEmbedded =
                                subscriptionInfo2 != null ? subscriptionInfo2.isEmbedded() : false;
                        SimOnboardingService simOnboardingService8 = simOnboardingService;
                        if (simOnboardingService8.slotInfoList.isEmpty()) {
                            Log.w("SimOnboardingService", "UICC Slot info list is empty.");
                        } else {
                            z =
                                    UiccSlotUtil.isRemovableSimEnabled(
                                            simOnboardingService8.slotInfoList);
                        }
                        boolean isMultipleEnabledProfilesSupported =
                                simOnboardingService.isMultipleEnabledProfilesSupported();
                        SimOnboardingService simOnboardingService9 = simOnboardingService;
                        int i2 = simOnboardingService9.targetPrimarySimCalls;
                        int i3 = simOnboardingService9.targetPrimarySimTexts;
                        int i4 = simOnboardingService9.targetPrimarySimMobileData;
                        StringBuilder m =
                                Utils$$ExternalSyntheticOutline0.m(
                                        "doesTargetSimHaveEsimOperation: ",
                                        isEmbedded,
                                        ", isRemovableSimEnabled: ",
                                        z,
                                        ", isMultipleEnabledProfilesSupported: ");
                        m.append(isMultipleEnabledProfilesSupported);
                        m.append(", targetPrimarySimCalls: ");
                        m.append(i2);
                        m.append(", targetPrimarySimTexts: ");
                        m.append(i3);
                        m.append(", targetPrimarySimMobileData: ");
                        m.append(i4);
                        Log.d("SimOnboardingService", m.toString());
                    }
                });
    }

    public static Flow sidecarReceiverFlow(SidecarFragment sidecarFragment) {
        Intrinsics.checkNotNullParameter(sidecarFragment, "<this>");
        return FlowKt.buffer$default(
                new FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1(
                        FlowKt.callbackFlow(
                                new SimOnboardingActivity$sidecarReceiverFlow$1(
                                        sidecarFragment, null)),
                        new SimOnboardingActivity$sidecarReceiverFlow$2(3, null)),
                -1);
    }

    @Override // com.android.settingslib.spa.SpaBaseDialogActivity
    public final void Content(Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(960275658);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        MutableState mutableState =
                (MutableState)
                        RememberSaveableKt.rememberSaveable(
                                new Object[0],
                                null,
                                new Function0() { // from class:
                                                  // com.android.settings.network.SimOnboardingActivity$Content$1
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
        Intrinsics.checkNotNullParameter(mutableState, "<set-?>");
        this.showStartingDialog = mutableState;
        MutableState mutableState2 =
                (MutableState)
                        RememberSaveableKt.rememberSaveable(
                                new Object[0],
                                null,
                                new Function0() { // from class:
                                                  // com.android.settings.network.SimOnboardingActivity$Content$2
                                    @Override // kotlin.jvm.functions.Function0
                                    /* renamed from: invoke */
                                    public final Object mo1068invoke() {
                                        return SnapshotStateKt.mutableStateOf(
                                                SimOnboardingActivity$Companion$ErrorType
                                                        .ERROR_NONE,
                                                StructuralEqualityPolicy.INSTANCE);
                                    }
                                },
                                composerImpl,
                                3080,
                                6);
        Intrinsics.checkNotNullParameter(mutableState2, "<set-?>");
        this.showError = mutableState2;
        MutableState mutableState3 =
                (MutableState)
                        RememberSaveableKt.rememberSaveable(
                                new Object[0],
                                null,
                                new Function0() { // from class:
                                                  // com.android.settings.network.SimOnboardingActivity$Content$3
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
        Intrinsics.checkNotNullParameter(mutableState3, "<set-?>");
        this.showProgressDialog = mutableState3;
        MutableState mutableState4 =
                (MutableState)
                        RememberSaveableKt.rememberSaveable(
                                new Object[0],
                                null,
                                new Function0() { // from class:
                                                  // com.android.settings.network.SimOnboardingActivity$Content$4
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
        Intrinsics.checkNotNullParameter(mutableState4, "<set-?>");
        this.showDsdsProgressDialog = mutableState4;
        MutableState mutableState5 =
                (MutableState)
                        RememberSaveableKt.rememberSaveable(
                                new Object[0],
                                null,
                                new Function0() { // from class:
                                                  // com.android.settings.network.SimOnboardingActivity$Content$5
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
        Intrinsics.checkNotNullParameter(mutableState5, "<set-?>");
        this.showRestartDialog = mutableState5;
        Object rememberedValue = composerImpl.rememberedValue();
        if (rememberedValue == Composer.Companion.Empty) {
            rememberedValue =
                    PredictiveBackHandlerKt$$ExternalSyntheticOutline0.m(
                            EffectsKt.createCompositionCoroutineScope(
                                    EmptyCoroutineContext.INSTANCE, composerImpl),
                            composerImpl);
        }
        CoroutineScope coroutineScope =
                ((CompositionScopedCoroutineScopeCanceller) rememberedValue).coroutineScope;
        Intrinsics.checkNotNullParameter(coroutineScope, "<set-?>");
        this.scope = coroutineScope;
        Context context =
                (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        Intrinsics.checkNotNullParameter(context, "<set-?>");
        this.context = context;
        this.wifiPickerTrackerHelper =
                new WifiPickerTrackerHelper(
                        new LifecycleRegistry(
                                (LifecycleOwner)
                                        composerImpl.consume(
                                                LocalLifecycleOwnerKt.LocalLifecycleOwner)),
                        getContext(),
                        null);
        registerSidecarReceiverFlow(composerImpl, 8);
        ErrorDialogImpl(composerImpl, 8);
        RestartDialogImpl(composerImpl, 8);
        EffectsKt.LaunchedEffect(
                composerImpl, Unit.INSTANCE, new SimOnboardingActivity$Content$6(this, null));
        composerImpl.startReplaceGroup(-1778062175);
        MutableState mutableState6 = this.showStartingDialog;
        if (mutableState6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("showStartingDialog");
            throw null;
        }
        if (((Boolean) mutableState6.getValue()).booleanValue()) {
            StartingDialogImpl(
                    new Function0() { // from class:
                        // com.android.settings.network.SimOnboardingActivity$Content$7
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            boolean isRemovableSimEnabled;
                            SimOnboardingService simOnboardingService =
                                    SimOnboardingActivity.onboardingService;
                            TelephonyManager telephonyManager =
                                    simOnboardingService.telephonyManager;
                            if ((telephonyManager != null
                                            ? telephonyManager.getActiveModemCount()
                                            : 0)
                                    > 1) {
                                Log.d(
                                        "SimOnboardingService",
                                        "DSDS is already enabled. Condition not satisfied.");
                            } else {
                                TelephonyManager telephonyManager2 =
                                        simOnboardingService.telephonyManager;
                                if (telephonyManager2 != null
                                        && telephonyManager2.isMultiSimSupported() == 0) {
                                    boolean z = !simOnboardingService.activeSubInfoList.isEmpty();
                                    if (simOnboardingService.isMultipleEnabledProfilesSupported()
                                            && z) {
                                        Log.d(
                                                "SimOnboardingService",
                                                "Device supports MEP and eSIM operation and eSIM"
                                                    + " profile is enabled. DSDS condition"
                                                    + " satisfied.");
                                    } else {
                                        SubscriptionInfo subscriptionInfo =
                                                simOnboardingService.targetSubInfo;
                                        if (subscriptionInfo != null
                                                ? subscriptionInfo.isEmbedded()
                                                : false) {
                                            if (simOnboardingService.slotInfoList.isEmpty()) {
                                                Log.w(
                                                        "SimOnboardingService",
                                                        "UICC Slot info list is empty.");
                                                isRemovableSimEnabled = false;
                                            } else {
                                                isRemovableSimEnabled =
                                                        UiccSlotUtil.isRemovableSimEnabled(
                                                                simOnboardingService.slotInfoList);
                                            }
                                            if (isRemovableSimEnabled) {
                                                Log.d(
                                                        "SimOnboardingService",
                                                        "eSIM operation and removable PSIM is"
                                                            + " enabled. DSDS condition"
                                                            + " satisfied.");
                                            }
                                        }
                                        SubscriptionInfo subscriptionInfo2 =
                                                simOnboardingService.targetSubInfo;
                                        if (!(subscriptionInfo2 != null
                                                ? subscriptionInfo2.isEmbedded()
                                                : false)) {
                                            simOnboardingService.activeSubInfoList.stream()
                                                    .anyMatch(
                                                            SimOnboardingService$isEsimProfileEnabled$1
                                                                    .INSTANCE);
                                        }
                                        Log.d(
                                                "SimOnboardingService",
                                                "DSDS condition not satisfied.");
                                    }
                                    TelephonyManager telephonyManager3 =
                                            simOnboardingService.telephonyManager;
                                    if (telephonyManager3 != null
                                            ? telephonyManager3
                                                    .doesSwitchMultiSimConfigTriggerReboot()
                                            : false) {
                                        Log.d(
                                                "SimOnboardingActivity",
                                                "Device does not support reboot free DSDS.");
                                        MutableState mutableState7 =
                                                SimOnboardingActivity.this.showRestartDialog;
                                        if (mutableState7 == null) {
                                            Intrinsics.throwUninitializedPropertyAccessException(
                                                    "showRestartDialog");
                                            throw null;
                                        }
                                        mutableState7.setValue(Boolean.TRUE);
                                    } else {
                                        Log.d("SimOnboardingActivity", "Enable DSDS mode");
                                        SimOnboardingActivity.this
                                                .getShowDsdsProgressDialog()
                                                .setValue(Boolean.TRUE);
                                        EnableMultiSimSidecar enableMultiSimSidecar =
                                                SimOnboardingActivity.this.enableMultiSimSidecar;
                                        if (enableMultiSimSidecar != null) {
                                            enableMultiSimSidecar.run$1();
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                                Log.d("SimOnboardingService", "Hardware does not support DSDS.");
                            }
                            SimOnboardingActivity simOnboardingActivity =
                                    SimOnboardingActivity.this;
                            simOnboardingActivity.getClass();
                            SimOnboardingPageProvider simOnboardingPageProvider =
                                    SimOnboardingPageProvider.INSTANCE;
                            String m =
                                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                            SimOnboardingActivity.onboardingService.targetSubId,
                                            "SimOnboardingPageProvider/");
                            SpaActivity.Companion companion = SpaActivity.Companion;
                            SpaActivity.Companion.startSpaActivity(simOnboardingActivity, m);
                            return Unit.INSTANCE;
                        }
                    },
                    new Function0() { // from class:
                        // com.android.settings.network.SimOnboardingActivity$Content$8
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            SimOnboardingActivity.this.finish();
                            return Unit.INSTANCE;
                        }
                    },
                    composerImpl,
                    512);
        }
        composerImpl.end(false);
        composerImpl.startReplaceGroup(-1778060722);
        MutableState mutableState7 = this.showProgressDialog;
        if (mutableState7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("showProgressDialog");
            throw null;
        }
        if (((Boolean) mutableState7.getValue()).booleanValue()) {
            SubscriptionInfo subscriptionInfo = onboardingService.targetSubInfo;
            CharSequence displayName =
                    subscriptionInfo != null ? subscriptionInfo.getDisplayName() : null;
            if (displayName == null) {
                displayName = ApnSettings.MVNO_NONE;
            }
            ProgressDialogImpl(
                    StringResources_androidKt.stringResource(
                            R.string.sim_onboarding_progressbar_turning_sim_on,
                            new Object[] {displayName},
                            composerImpl),
                    composerImpl,
                    64);
        }
        composerImpl.end(false);
        if (((Boolean) getShowDsdsProgressDialog().getValue()).booleanValue()) {
            ProgressDialogImpl(
                    StringResources_androidKt.stringResource(
                            composerImpl, R.string.sim_action_enabling_sim_without_carrier_name),
                    composerImpl,
                    64);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.network.SimOnboardingActivity$Content$9
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SimOnboardingActivity.this.Content(
                                    (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public final void ErrorDialogImpl(Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-141962637);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        SettingsAlertDialogKt$rememberAlertDialogPresenter$alertDialogPresenter$1$1
                rememberAlertDialogPresenter =
                        SettingsAlertDialogKt.rememberAlertDialogPresenter(
                                new AlertDialogButton(
                                        StringResources_androidKt.stringResource(
                                                composerImpl, android.R.string.ok),
                                        new Function0() { // from class:
                                                          // com.android.settings.network.SimOnboardingActivity$ErrorDialogImpl$errorDialogPresenterForEuiccSlotSidecar$1
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            /* renamed from: invoke */
                                            public final Object mo1068invoke() {
                                                SimOnboardingActivity.this.finish();
                                                return Unit.INSTANCE;
                                            }
                                        },
                                        2),
                                null,
                                StringResources_androidKt.stringResource(
                                        composerImpl,
                                        R.string.privileged_action_disable_fail_title),
                                ComposableSingletons$SimOnboardingActivityKt.f20lambda2,
                                composerImpl,
                                2);
        SettingsAlertDialogKt$rememberAlertDialogPresenter$alertDialogPresenter$1$1
                rememberAlertDialogPresenter2 =
                        SettingsAlertDialogKt.rememberAlertDialogPresenter(
                                new AlertDialogButton(
                                        StringResources_androidKt.stringResource(
                                                composerImpl, android.R.string.ok),
                                        new Function0() { // from class:
                                                          // com.android.settings.network.SimOnboardingActivity$ErrorDialogImpl$errorDialogPresenterForRemovableSlotSidecar$1
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            /* renamed from: invoke */
                                            public final Object mo1068invoke() {
                                                SimOnboardingActivity.this.finish();
                                                return Unit.INSTANCE;
                                            }
                                        },
                                        2),
                                null,
                                StringResources_androidKt.stringResource(
                                        composerImpl, R.string.sim_action_enable_sim_fail_title),
                                ComposableSingletons$SimOnboardingActivityKt.f21lambda3,
                                composerImpl,
                                2);
        SettingsAlertDialogKt$rememberAlertDialogPresenter$alertDialogPresenter$1$1
                rememberAlertDialogPresenter3 =
                        SettingsAlertDialogKt.rememberAlertDialogPresenter(
                                new AlertDialogButton(
                                        StringResources_androidKt.stringResource(
                                                composerImpl, android.R.string.ok),
                                        new Function0() { // from class:
                                                          // com.android.settings.network.SimOnboardingActivity$ErrorDialogImpl$errorDialogPresenterForMultiSimSidecar$1
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            /* renamed from: invoke */
                                            public final Object mo1068invoke() {
                                                SimOnboardingActivity.this.finish();
                                                return Unit.INSTANCE;
                                            }
                                        },
                                        2),
                                null,
                                StringResources_androidKt.stringResource(
                                        composerImpl, R.string.dsds_activation_failure_title),
                                ComposableSingletons$SimOnboardingActivityKt.f22lambda4,
                                composerImpl,
                                2);
        int ordinal =
                ((SimOnboardingActivity$Companion$ErrorType) getShowError().getValue()).ordinal();
        if (ordinal == 1) {
            rememberAlertDialogPresenter.open();
        } else if (ordinal == 2) {
            rememberAlertDialogPresenter2.open();
        } else if (ordinal == 3) {
            rememberAlertDialogPresenter3.open();
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.network.SimOnboardingActivity$ErrorDialogImpl$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SimOnboardingActivity.this.ErrorDialogImpl(
                                    (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.settings.network.SimOnboardingActivity$ProgressDialogImpl$2, kotlin.jvm.internal.Lambda] */
    public final void ProgressDialogImpl(final String title, Composer composer, final int i) {
        int i2;
        Intrinsics.checkNotNullParameter(title, "title");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1177317763);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(title) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            AlertDialogKt.BasicAlertDialog(
                    new Function0() { // from class:
                                      // com.android.settings.network.SimOnboardingActivity$ProgressDialogImpl$1
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final /* bridge */ /* synthetic */ Object mo1068invoke() {
                            return Unit.INSTANCE;
                        }
                    },
                    SizeKt.m100width3ABfNKs(
                            Modifier.Companion.$$INSTANCE,
                            SettingsAlertDialogKt.getDialogWidth(composerImpl)),
                    null,
                    ComposableLambdaKt.rememberComposableLambda(
                            -1007300855,
                            new Function2() { // from class:
                                              // com.android.settings.network.SimOnboardingActivity$ProgressDialogImpl$2
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(2);
                                }

                                /* JADX WARN: Type inference failed for: r14v8, types: [com.android.settings.network.SimOnboardingActivity$ProgressDialogImpl$2$1, kotlin.jvm.internal.Lambda] */
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
                                    float f = AlertDialogDefaults.TonalElevation;
                                    ShapeKeyTokens shapeKeyTokens = DialogTokens.ContainerShape;
                                    long value =
                                            ColorSchemeKt.getValue(
                                                    ColorSchemeKeyTokens.SurfaceContainerHigh,
                                                    composer2);
                                    Shape value2 =
                                            ShapesKt.getValue(
                                                    DialogTokens.ContainerShape, composer2);
                                    final String str = title;
                                    SurfaceKt.m198SurfaceT9BRK9s(
                                            null,
                                            value2,
                                            value,
                                            0L,
                                            0.0f,
                                            0.0f,
                                            null,
                                            ComposableLambdaKt.rememberComposableLambda(
                                                    -2137544220,
                                                    new Function2() { // from class:
                                                                      // com.android.settings.network.SimOnboardingActivity$ProgressDialogImpl$2.1
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(2);
                                                        }

                                                        @Override // kotlin.jvm.functions.Function2
                                                        public final Object invoke(
                                                                Object obj3, Object obj4) {
                                                            Composer composer3 = (Composer) obj3;
                                                            if ((((Number) obj4).intValue() & 11)
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
                                                            Modifier.Companion companion =
                                                                    Modifier.Companion.$$INSTANCE;
                                                            FillElement fillElement =
                                                                    SizeKt.FillWholeMaxWidth;
                                                            float f2 =
                                                                    SettingsDimension
                                                                            .itemPaddingStart;
                                                            Modifier m85padding3ABfNKs =
                                                                    PaddingKt.m85padding3ABfNKs(
                                                                            fillElement, f2);
                                                            BiasAlignment.Vertical vertical =
                                                                    Alignment.Companion
                                                                            .CenterVertically;
                                                            String str2 = str;
                                                            RowMeasurePolicy rowMeasurePolicy =
                                                                    RowKt.rowMeasurePolicy(
                                                                            Arrangement.Start,
                                                                            vertical,
                                                                            composer3,
                                                                            48);
                                                            int currentCompositeKeyHash =
                                                                    ComposablesKt
                                                                            .getCurrentCompositeKeyHash(
                                                                                    composer3);
                                                            ComposerImpl composerImpl4 =
                                                                    (ComposerImpl) composer3;
                                                            PersistentCompositionLocalMap
                                                                    currentCompositionLocalScope =
                                                                            composerImpl4
                                                                                    .currentCompositionLocalScope();
                                                            Modifier materializeModifier =
                                                                    ComposedModifierKt
                                                                            .materializeModifier(
                                                                                    composer3,
                                                                                    m85padding3ABfNKs);
                                                            ComposeUiNode.Companion.getClass();
                                                            Function0 function0 =
                                                                    ComposeUiNode.Companion
                                                                            .Constructor;
                                                            boolean z =
                                                                    composerImpl4.applier
                                                                            instanceof Applier;
                                                            if (!z) {
                                                                ComposablesKt.invalidApplier();
                                                                throw null;
                                                            }
                                                            composerImpl4.startReusableNode();
                                                            if (composerImpl4.inserting) {
                                                                composerImpl4.createNode(function0);
                                                            } else {
                                                                composerImpl4.useNode();
                                                            }
                                                            Function2 function2 =
                                                                    ComposeUiNode.Companion
                                                                            .SetMeasurePolicy;
                                                            Updater.m221setimpl(
                                                                    composer3,
                                                                    rowMeasurePolicy,
                                                                    function2);
                                                            Function2 function22 =
                                                                    ComposeUiNode.Companion
                                                                            .SetResolvedCompositionLocals;
                                                            Updater.m221setimpl(
                                                                    composer3,
                                                                    currentCompositionLocalScope,
                                                                    function22);
                                                            Function2 function23 =
                                                                    ComposeUiNode.Companion
                                                                            .SetCompositeKeyHash;
                                                            if (composerImpl4.inserting
                                                                    || !Intrinsics.areEqual(
                                                                            composerImpl4
                                                                                    .rememberedValue(),
                                                                            Integer.valueOf(
                                                                                    currentCompositeKeyHash))) {
                                                                AnimatedContentKt$$ExternalSyntheticOutline0
                                                                        .m(
                                                                                currentCompositeKeyHash,
                                                                                composerImpl4,
                                                                                currentCompositeKeyHash,
                                                                                function23);
                                                            }
                                                            Function2 function24 =
                                                                    ComposeUiNode.Companion
                                                                            .SetModifier;
                                                            Updater.m221setimpl(
                                                                    composer3,
                                                                    materializeModifier,
                                                                    function24);
                                                            ProgressIndicatorKt
                                                                    .m193CircularProgressIndicatorLxG7B9w(
                                                                            null, 0L, 0.0f, 0L, 0,
                                                                            composer3, 0, 31);
                                                            Modifier m89paddingqDBjuR0$default =
                                                                    PaddingKt
                                                                            .m89paddingqDBjuR0$default(
                                                                                    companion, f2,
                                                                                    0.0f, 0.0f,
                                                                                    0.0f, 14);
                                                            ColumnMeasurePolicy
                                                                    columnMeasurePolicy =
                                                                            ColumnKt
                                                                                    .columnMeasurePolicy(
                                                                                            Arrangement
                                                                                                    .Top,
                                                                                            Alignment
                                                                                                    .Companion
                                                                                                    .Start,
                                                                                            composer3,
                                                                                            0);
                                                            int currentCompositeKeyHash2 =
                                                                    ComposablesKt
                                                                            .getCurrentCompositeKeyHash(
                                                                                    composer3);
                                                            PersistentCompositionLocalMap
                                                                    currentCompositionLocalScope2 =
                                                                            composerImpl4
                                                                                    .currentCompositionLocalScope();
                                                            Modifier materializeModifier2 =
                                                                    ComposedModifierKt
                                                                            .materializeModifier(
                                                                                    composer3,
                                                                                    m89paddingqDBjuR0$default);
                                                            if (!z) {
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
                                                                    composer3,
                                                                    columnMeasurePolicy,
                                                                    function2);
                                                            Updater.m221setimpl(
                                                                    composer3,
                                                                    currentCompositionLocalScope2,
                                                                    function22);
                                                            if (composerImpl4.inserting
                                                                    || !Intrinsics.areEqual(
                                                                            composerImpl4
                                                                                    .rememberedValue(),
                                                                            Integer.valueOf(
                                                                                    currentCompositeKeyHash2))) {
                                                                AnimatedContentKt$$ExternalSyntheticOutline0
                                                                        .m(
                                                                                currentCompositeKeyHash2,
                                                                                composerImpl4,
                                                                                currentCompositeKeyHash2,
                                                                                function23);
                                                            }
                                                            Updater.m221setimpl(
                                                                    composer3,
                                                                    materializeModifier2,
                                                                    function24);
                                                            TextKt.SettingsTitle(
                                                                    str2, null, false, composer3, 0,
                                                                    6);
                                                            composerImpl4.end(true);
                                                            composerImpl4.end(true);
                                                            return Unit.INSTANCE;
                                                        }
                                                    },
                                                    composer2),
                                            composer2,
                                            12582912,
                                            121);
                                    return Unit.INSTANCE;
                                }
                            },
                            composerImpl),
                    composerImpl,
                    3078,
                    4);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.network.SimOnboardingActivity$ProgressDialogImpl$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SimOnboardingActivity.this.ProgressDialogImpl(
                                    title,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public final void RestartDialogImpl(Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1833708684);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        AlertDialogButton alertDialogButton =
                new AlertDialogButton(
                        StringResources_androidKt.stringResource(
                                composerImpl, R.string.sim_action_reboot),
                        new Function0() { // from class:
                                          // com.android.settings.network.SimOnboardingActivity$RestartDialogImpl$restartDialogPresenter$1
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                ((SimOnboardingActivity$callbackListener$1)
                                                SimOnboardingActivity.this.callbackListener)
                                        .invoke(
                                                SimOnboardingActivity$Companion$CallbackType
                                                        .CALLBACK_ENABLE_DSDS);
                                return Unit.INSTANCE;
                            }
                        },
                        2);
        SubscriptionInfo subscriptionInfo = onboardingService.targetSubInfo;
        CharSequence displayName =
                subscriptionInfo != null ? subscriptionInfo.getDisplayName() : null;
        if (displayName == null) {
            displayName = ApnSettings.MVNO_NONE;
        }
        SettingsAlertDialogKt$rememberAlertDialogPresenter$alertDialogPresenter$1$1
                rememberAlertDialogPresenter =
                        SettingsAlertDialogKt.rememberAlertDialogPresenter(
                                alertDialogButton,
                                new AlertDialogButton(
                                        StringResources_androidKt.stringResource(
                                                R.string.sim_action_restart_dialog_cancel,
                                                new Object[] {displayName},
                                                composerImpl),
                                        new Function0() { // from class:
                                                          // com.android.settings.network.SimOnboardingActivity$RestartDialogImpl$restartDialogPresenter$2
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            /* renamed from: invoke */
                                            public final Object mo1068invoke() {
                                                ((SimOnboardingActivity$callbackListener$1)
                                                                SimOnboardingActivity.this
                                                                        .callbackListener)
                                                        .invoke(
                                                                SimOnboardingActivity$Companion$CallbackType
                                                                        .CALLBACK_ONBOARDING_COMPLETE);
                                                return Unit.INSTANCE;
                                            }
                                        },
                                        2),
                                StringResources_androidKt.stringResource(
                                        composerImpl, R.string.sim_action_restart_dialog_title),
                                ComposableSingletons$SimOnboardingActivityKt.f19lambda1,
                                composerImpl,
                                0);
        MutableState mutableState = this.showRestartDialog;
        if (mutableState == null) {
            Intrinsics.throwUninitializedPropertyAccessException("showRestartDialog");
            throw null;
        }
        if (((Boolean) mutableState.getValue()).booleanValue()) {
            EffectsKt.LaunchedEffect(
                    composerImpl,
                    Unit.INSTANCE,
                    new SimOnboardingActivity$RestartDialogImpl$1(
                            rememberAlertDialogPresenter, null));
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.network.SimOnboardingActivity$RestartDialogImpl$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SimOnboardingActivity simOnboardingActivity =
                                    SimOnboardingActivity.this;
                            int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                            SimOnboardingService simOnboardingService =
                                    SimOnboardingActivity.onboardingService;
                            simOnboardingActivity.RestartDialogImpl(
                                    (Composer) obj, updateChangedFlags);
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public final void StartingDialogImpl(
            final Function0 nextAction,
            final Function0 cancelAction,
            Composer composer,
            final int i) {
        Intrinsics.checkNotNullParameter(nextAction, "nextAction");
        Intrinsics.checkNotNullParameter(cancelAction, "cancelAction");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(246290943);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        String string = getString(R.string.sim_onboarding_setup);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        AlertDialogButton alertDialogButton = new AlertDialogButton(string, nextAction, 2);
        String string2 = getString(R.string.sim_onboarding_close);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
        SettingsAlertDialogWithIconKt.SettingsAlertDialogWithIcon(
                cancelAction,
                alertDialogButton,
                new AlertDialogButton(string2, cancelAction, 2),
                StringResources_androidKt.stringResource(
                        composerImpl, R.string.sim_onboarding_dialog_starting_title),
                ComposableSingletons$SimOnboardingActivityKt.f23lambda5,
                ComposableSingletons$SimOnboardingActivityKt.f24lambda6,
                composerImpl,
                ((i >> 3) & 14) | 221184,
                0);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.network.SimOnboardingActivity$StartingDialogImpl$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SimOnboardingActivity.this.StartingDialogImpl(
                                    nextAction,
                                    cancelAction,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    @Override // android.app.Activity
    public final void finish() {
        setProgressDialog(false);
        onboardingService.clear();
        super.finish();
    }

    public final Context getContext() {
        Context context = this.context;
        if (context != null) {
            return context;
        }
        Intrinsics.throwUninitializedPropertyAccessException("context");
        throw null;
    }

    public final CoroutineScope getScope() {
        CoroutineScope coroutineScope = this.scope;
        if (coroutineScope != null) {
            return coroutineScope;
        }
        Intrinsics.throwUninitializedPropertyAccessException("scope");
        throw null;
    }

    public final MutableState getShowDsdsProgressDialog() {
        MutableState mutableState = this.showDsdsProgressDialog;
        if (mutableState != null) {
            return mutableState;
        }
        Intrinsics.throwUninitializedPropertyAccessException("showDsdsProgressDialog");
        throw null;
    }

    public final MutableState getShowError() {
        MutableState mutableState = this.showError;
        if (mutableState != null) {
            return mutableState;
        }
        Intrinsics.throwUninitializedPropertyAccessException("showError");
        throw null;
    }

    @Override // com.android.settingslib.spa.SpaBaseDialogActivity,
              // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity,
              // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!ContextsKt.getUserManager(this).isAdminUser()) {
            Log.e(
                    "SimOnboardingActivity",
                    "It is not the admin user. Unable to toggle subscription.");
            finish();
            return;
        }
        int intExtra = getIntent().getIntExtra("sub_id", -1);
        initServiceData(this, intExtra, this.callbackListener);
        SimOnboardingService simOnboardingService = onboardingService;
        if (!SubscriptionManager.isUsableSubscriptionId(simOnboardingService.targetSubId)) {
            Log.e("SimOnboardingActivity", "The subscription id is not usable.");
            finish();
            return;
        }
        if (simOnboardingService.activeSubInfoList.isEmpty()) {
            Log.d(
                    "SimOnboardingActivity",
                    "onboardingService.activeSubInfoList is empty, start"
                        + " ToggleSubscriptionDialogActivity");
            startActivity(
                    ToggleSubscriptionDialogActivity.getIntent(
                            getApplicationContext(), intExtra, true));
            finish();
            return;
        }
        this.switchToEuiccSubscriptionSidecar =
                SwitchToEuiccSubscriptionSidecar.get(getFragmentManager());
        FragmentManager fragmentManager = getFragmentManager();
        int i = SwitchToRemovableSlotSidecar.$r8$clinit;
        this.switchToRemovableSlotSidecar =
                (SwitchToRemovableSlotSidecar)
                        SidecarFragment.get(
                                fragmentManager,
                                "SwitchRemovableSidecar",
                                SwitchToRemovableSlotSidecar.class);
        FragmentManager fragmentManager2 = getFragmentManager();
        int i2 = EnableMultiSimSidecar.$r8$clinit;
        this.enableMultiSimSidecar =
                (EnableMultiSimSidecar)
                        SidecarFragment.get(
                                fragmentManager2,
                                "EnableMultiSimSidecar",
                                EnableMultiSimSidecar.class);
    }

    public final void onStateChange(SidecarFragment sidecarFragment) {
        SimOnboardingService simOnboardingService = onboardingService;
        SwitchToEuiccSubscriptionSidecar switchToEuiccSubscriptionSidecar =
                this.switchToEuiccSubscriptionSidecar;
        Function1 function1 = this.callbackListener;
        boolean z = false;
        if (sidecarFragment == switchToEuiccSubscriptionSidecar) {
            Intrinsics.checkNotNull(switchToEuiccSubscriptionSidecar);
            int i = switchToEuiccSubscriptionSidecar.mState;
            if (i == 2) {
                Log.i("SimOnboardingActivity", "Successfully enable the eSIM profile.");
                SwitchToEuiccSubscriptionSidecar switchToEuiccSubscriptionSidecar2 =
                        this.switchToEuiccSubscriptionSidecar;
                Intrinsics.checkNotNull(switchToEuiccSubscriptionSidecar2);
                switchToEuiccSubscriptionSidecar2.setState(0, 0);
                BuildersKt.launch$default(
                        getScope(),
                        null,
                        null,
                        new SimOnboardingActivity$handleSwitchToEuiccSubscriptionSidecarStateChange$1(
                                this, null),
                        3);
                return;
            }
            if (i != 3) {
                return;
            }
            Log.i("SimOnboardingActivity", "Failed to enable the eSIM profile.");
            SwitchToEuiccSubscriptionSidecar switchToEuiccSubscriptionSidecar3 =
                    this.switchToEuiccSubscriptionSidecar;
            Intrinsics.checkNotNull(switchToEuiccSubscriptionSidecar3);
            switchToEuiccSubscriptionSidecar3.setState(0, 0);
            getShowError().setValue(SimOnboardingActivity$Companion$ErrorType.ERROR_EUICC_SLOT);
            ((SimOnboardingActivity$callbackListener$1) function1)
                    .invoke(SimOnboardingActivity$Companion$CallbackType.CALLBACK_ERROR);
            return;
        }
        SwitchToRemovableSlotSidecar switchToRemovableSlotSidecar =
                this.switchToRemovableSlotSidecar;
        if (sidecarFragment != switchToRemovableSlotSidecar) {
            if (sidecarFragment == this.enableMultiSimSidecar) {
                getShowDsdsProgressDialog().setValue(Boolean.FALSE);
                EnableMultiSimSidecar enableMultiSimSidecar = this.enableMultiSimSidecar;
                Intrinsics.checkNotNull(enableMultiSimSidecar);
                int i2 = enableMultiSimSidecar.mState;
                if (i2 != 2) {
                    if (i2 != 3) {
                        return;
                    }
                    EnableMultiSimSidecar enableMultiSimSidecar2 = this.enableMultiSimSidecar;
                    Intrinsics.checkNotNull(enableMultiSimSidecar2);
                    enableMultiSimSidecar2.setState(0, 0);
                    Log.i("SimOnboardingActivity", "Failed to switch to DSDS without rebooting.");
                    getShowError()
                            .setValue(SimOnboardingActivity$Companion$ErrorType.ERROR_ENABLE_DSDS);
                    ((SimOnboardingActivity$callbackListener$1) function1)
                            .invoke(SimOnboardingActivity$Companion$CallbackType.CALLBACK_ERROR);
                    return;
                }
                EnableMultiSimSidecar enableMultiSimSidecar3 = this.enableMultiSimSidecar;
                Intrinsics.checkNotNull(enableMultiSimSidecar3);
                enableMultiSimSidecar3.setState(0, 0);
                Log.i("SimOnboardingActivity", "Successfully switched to DSDS without reboot.");
                initServiceData(this, simOnboardingService.targetSubId, function1);
                SimOnboardingPageProvider simOnboardingPageProvider =
                        SimOnboardingPageProvider.INSTANCE;
                String m =
                        SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                simOnboardingService.targetSubId, "SimOnboardingPageProvider/");
                SpaActivity.Companion companion = SpaActivity.Companion;
                SpaActivity.Companion.startSpaActivity(this, m);
                return;
            }
            return;
        }
        Intrinsics.checkNotNull(switchToRemovableSlotSidecar);
        int i3 = switchToRemovableSlotSidecar.mState;
        if (i3 != 2) {
            if (i3 != 3) {
                return;
            }
            Log.e("SimOnboardingActivity", "Failed switching to removable slot.");
            SwitchToRemovableSlotSidecar switchToRemovableSlotSidecar2 =
                    this.switchToRemovableSlotSidecar;
            Intrinsics.checkNotNull(switchToRemovableSlotSidecar2);
            switchToRemovableSlotSidecar2.setState(0, 0);
            getShowError().setValue(SimOnboardingActivity$Companion$ErrorType.ERROR_REMOVABLE_SLOT);
            ((SimOnboardingActivity$callbackListener$1) function1)
                    .invoke(SimOnboardingActivity$Companion$CallbackType.CALLBACK_ERROR);
            return;
        }
        Log.i("SimOnboardingActivity", "Successfully switched to removable slot.");
        SwitchToRemovableSlotSidecar switchToRemovableSlotSidecar3 =
                this.switchToRemovableSlotSidecar;
        Intrinsics.checkNotNull(switchToRemovableSlotSidecar3);
        switchToRemovableSlotSidecar3.setState(0, 0);
        SubscriptionManager subscriptionManager = simOnboardingService.subscriptionManager;
        if (subscriptionManager != null && subscriptionManager.canDisablePhysicalSubscription()) {
            z = true;
        }
        SubscriptionInfo subscriptionInfo = simOnboardingService.targetSubInfo;
        if (subscriptionInfo == null || !z) {
            Log.i(
                    "SimOnboardingService",
                    "The device does not support toggling pSIM. It is enough to just enable the"
                        + " removable slot.");
        } else {
            SubscriptionManager subscriptionManager2 = simOnboardingService.subscriptionManager;
            if (subscriptionManager2 != null) {
                subscriptionManager2.setUiccApplicationsEnabled(
                        subscriptionInfo.getSubscriptionId(), true);
            }
        }
        BuildersKt.launch$default(
                getScope(),
                null,
                null,
                new SimOnboardingActivity$handleSwitchToRemovableSlotSidecarStateChange$1(
                        this, null),
                3);
    }

    public final void registerSidecarReceiverFlow(Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-723407348);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        SwitchToEuiccSubscriptionSidecar switchToEuiccSubscriptionSidecar =
                this.switchToEuiccSubscriptionSidecar;
        Flow sidecarReceiverFlow =
                switchToEuiccSubscriptionSidecar != null
                        ? sidecarReceiverFlow(switchToEuiccSubscriptionSidecar)
                        : null;
        composerImpl.startReplaceGroup(1207093941);
        Lifecycle.State state = Lifecycle.State.STARTED;
        if (sidecarReceiverFlow != null) {
            DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal =
                    AndroidCompositionLocals_androidKt.LocalConfiguration;
            FlowsKt.collectLatestWithLifecycle(
                    sidecarReceiverFlow,
                    (LifecycleOwner)
                            composerImpl.consume(LocalLifecycleOwnerKt.LocalLifecycleOwner),
                    state,
                    new SimOnboardingActivity$registerSidecarReceiverFlow$1(this, null));
        }
        composerImpl.end(false);
        SwitchToRemovableSlotSidecar switchToRemovableSlotSidecar =
                this.switchToRemovableSlotSidecar;
        Flow sidecarReceiverFlow2 =
                switchToRemovableSlotSidecar != null
                        ? sidecarReceiverFlow(switchToRemovableSlotSidecar)
                        : null;
        composerImpl.startReplaceGroup(1207094121);
        if (sidecarReceiverFlow2 != null) {
            DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal2 =
                    AndroidCompositionLocals_androidKt.LocalConfiguration;
            FlowsKt.collectLatestWithLifecycle(
                    sidecarReceiverFlow2,
                    (LifecycleOwner)
                            composerImpl.consume(LocalLifecycleOwnerKt.LocalLifecycleOwner),
                    state,
                    new SimOnboardingActivity$registerSidecarReceiverFlow$2(this, null));
        }
        composerImpl.end(false);
        EnableMultiSimSidecar enableMultiSimSidecar = this.enableMultiSimSidecar;
        Flow sidecarReceiverFlow3 =
                enableMultiSimSidecar != null ? sidecarReceiverFlow(enableMultiSimSidecar) : null;
        if (sidecarReceiverFlow3 != null) {
            DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal3 =
                    AndroidCompositionLocals_androidKt.LocalConfiguration;
            FlowsKt.collectLatestWithLifecycle(
                    sidecarReceiverFlow3,
                    (LifecycleOwner)
                            composerImpl.consume(LocalLifecycleOwnerKt.LocalLifecycleOwner),
                    state,
                    new SimOnboardingActivity$registerSidecarReceiverFlow$3(this, null));
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.network.SimOnboardingActivity$registerSidecarReceiverFlow$4
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SimOnboardingActivity.this.registerSidecarReceiverFlow(
                                    (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public final void setProgressDialog(boolean z) {
        MutableState mutableState = this.showProgressDialog;
        if (mutableState == null) {
            return;
        }
        if (mutableState == null) {
            Intrinsics.throwUninitializedPropertyAccessException("showProgressDialog");
            throw null;
        }
        mutableState.setValue(Boolean.valueOf(z));
        getSharedPreferences("sim_action_dialog_prefs", 0)
                .edit()
                .putInt("progress_state", z ? 1 : 0)
                .apply();
        Log.i("SimOnboardingActivity", "setProgressState:" + (z ? 1 : 0));
    }
}
