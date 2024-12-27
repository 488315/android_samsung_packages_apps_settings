package com.android.settings.network;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Looper;

import androidx.activity.compose.PredictiveBackHandlerKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionScopedCoroutineScopeCanceller;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.ResetNetworkRequest;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.spa.preference.ComposePreferenceController;
import com.android.settingslib.spa.widget.dialog.AlertDialogButton;
import com.android.settingslib.spa.widget.dialog.AlertDialogPresenter;
import com.android.settingslib.spa.widget.dialog.SettingsAlertDialogKt;
import com.android.settingslib.spa.widget.dialog.SettingsAlertDialogKt$rememberAlertDialogPresenter$alertDialogPresenter$1$1;
import com.android.settingslib.spa.widget.preference.PreferenceModel;
import com.android.settingslib.spaprivileged.model.enterprise.Restrictions;
import com.android.settingslib.spaprivileged.template.preference.RestrictedPreferenceKt;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.sec.ims.presence.ServiceTuple;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KFunction;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.internal.ContextScope;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000<\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\b\u0007\u0018\u0000"
                + " \u00132\u00020\u0001:\u0001\u0013B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\r"
                + "\u0010\t\u001a\u00020\n"
                + "H\u0017¢\u0006\u0002\u0010\u000bJ\b\u0010\f\u001a\u00020\r"
                + "H\u0016J\u0010\u0010\u000e\u001a\u00020\n"
                + "2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0007R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n"
                + "\u0000¨\u0006\u0014"
        },
        d2 = {
            "Lcom/android/settings/network/BluetoothWiFiResetPreferenceController;",
            "Lcom/android/settings/spa/preference/ComposePreferenceController;",
            "context",
            "Landroid/content/Context;",
            "preferenceKey",
            ApnSettings.MVNO_NONE,
            "(Landroid/content/Context;Ljava/lang/String;)V",
            "restrictionChecker",
            "Lcom/android/settings/network/NetworkResetRestrictionChecker;",
            "Content",
            ApnSettings.MVNO_NONE,
            "(Landroidx/compose/runtime/Composer;I)V",
            "getAvailabilityStatus",
            ApnSettings.MVNO_NONE,
            UniversalCredentialManager.RESET_APPLET_FORM_FACTOR,
            "coroutineScope",
            "Lkotlinx/coroutines/CoroutineScope;",
            "resetOperation",
            "Ljava/lang/Runnable;",
            "Companion",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class BluetoothWiFiResetPreferenceController extends ComposePreferenceController {
    public static final int $stable = 8;
    private static final Companion Companion = new Companion();
    private static final String TAG = "BluetoothWiFiResetPref";
    private final NetworkResetRestrictionChecker restrictionChecker;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Companion {}

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BluetoothWiFiResetPreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(preferenceKey, "preferenceKey");
        this.restrictionChecker = new NetworkResetRestrictionChecker(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void reset(CoroutineScope coroutineScope) {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getMetricsFeatureProvider().action(this.mContext, 1998, true);
        BuildersKt.launch$default(
                coroutineScope,
                null,
                null,
                new BluetoothWiFiResetPreferenceController$reset$1(this, null),
                3);
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController
    public void Content(Composer composer, final int i) {
        final ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-72282486);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Object rememberedValue = composerImpl.rememberedValue();
        if (rememberedValue == Composer.Companion.Empty) {
            rememberedValue =
                    PredictiveBackHandlerKt$$ExternalSyntheticOutline0.m(
                            EffectsKt.createCompositionCoroutineScope(
                                    EmptyCoroutineContext.INSTANCE, composerImpl),
                            composerImpl);
        }
        final ContextScope contextScope =
                (ContextScope)
                        ((CompositionScopedCoroutineScopeCanceller) rememberedValue).coroutineScope;
        final SettingsAlertDialogKt$rememberAlertDialogPresenter$alertDialogPresenter$1$1
                rememberAlertDialogPresenter =
                        SettingsAlertDialogKt.rememberAlertDialogPresenter(
                                new AlertDialogButton(
                                        StringResources_androidKt.stringResource(
                                                composerImpl,
                                                R.string.reset_bluetooth_wifi_button_text),
                                        new Function0() { // from class:
                                                          // com.android.settings.network.BluetoothWiFiResetPreferenceController$Content$dialogPresenter$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            /* renamed from: invoke */
                                            public final Object mo1068invoke() {
                                                BluetoothWiFiResetPreferenceController.this.reset(
                                                        contextScope);
                                                return Unit.INSTANCE;
                                            }
                                        },
                                        2),
                                new AlertDialogButton(
                                        StringResources_androidKt.stringResource(
                                                composerImpl, R.string.cancel),
                                        (Function0) null,
                                        6),
                                StringResources_androidKt.stringResource(
                                        composerImpl, R.string.reset_bluetooth_wifi_title),
                                ComposableSingletons$BluetoothWiFiResetPreferenceControllerKt
                                        .f18lambda1,
                                composerImpl,
                                0);
        RestrictedPreferenceKt.RestrictedPreference(
                new PreferenceModel(
                        composerImpl,
                        rememberAlertDialogPresenter) { // from class:
                                                        // com.android.settings.network.BluetoothWiFiResetPreferenceController$Content$1
                    public final KFunction onClick;
                    public final String title;

                    {
                        this.title =
                                StringResources_androidKt.stringResource(
                                        composerImpl, R.string.reset_bluetooth_wifi_title);
                        this.onClick =
                                new BluetoothWiFiResetPreferenceController$Content$1$onClick$1(
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
                    public final String getTitle() {
                        return this.title;
                    }
                },
                new Restrictions(0, CollectionsKt__CollectionsJVMKt.listOf("no_network_reset"), 5),
                composerImpl,
                64);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.network.BluetoothWiFiResetPreferenceController$Content$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            BluetoothWiFiResetPreferenceController.this.Content(
                                    (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        NetworkResetRestrictionChecker networkResetRestrictionChecker = this.restrictionChecker;
        return (!networkResetRestrictionChecker.mUserManager.isAdminUser()
                        || networkResetRestrictionChecker.hasUserBaseRestriction())
                ? 2
                : 0;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    public final Runnable resetOperation() {
        ResetNetworkOperationBuilder resetNetworkOperationBuilder;
        if (SubscriptionUtil.isSimHardwareVisible(this.mContext)) {
            resetNetworkOperationBuilder =
                    new ResetNetworkRequest(28)
                            .toResetNetworkOperationBuilder(this.mContext, Looper.getMainLooper());
        } else {
            resetNetworkOperationBuilder =
                    new ResetNetworkRequest(31)
                            .toResetNetworkOperationBuilder(this.mContext, Looper.getMainLooper());
            resetNetworkOperationBuilder.resetTelephonyAndNetworkPolicyManager(
                    Preference.DEFAULT_ORDER);
        }
        return new ResetNetworkOperationBuilder$$ExternalSyntheticLambda0(
                resetNetworkOperationBuilder, 1);
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
