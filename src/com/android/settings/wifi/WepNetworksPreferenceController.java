package com.android.settings.wifi;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.StructuralEqualityPolicy;
import androidx.compose.runtime.saveable.RememberSaveableKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.lifecycle.compose.FlowExtKt;

import com.android.settings.R;
import com.android.settings.spa.preference.ComposePreferenceController;
import com.android.settingslib.spa.framework.compose.OverridableFlow;
import com.android.settingslib.spa.widget.dialog.AlertDialogButton;
import com.android.settingslib.spa.widget.dialog.SettingsAlertDialogWithIconKt;
import com.android.settingslib.spa.widget.preference.SwitchPreferenceKt;
import com.android.settingslib.spa.widget.preference.SwitchPreferenceModel;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.FlowKt;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000:\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0004\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0006\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\u000b\b\u0007\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0006\u0010\u0007J\u000f\u0010"
                + "\t\u001a\u00020\bH\u0016¢\u0006\u0004\b\t\u0010\n"
                + "J\u000f\u0010\f\u001a\u00020\u000bH\u0017¢\u0006\u0004\b\f\u0010\r"
                + "J\u000f\u0010\u000e\u001a\u00020\u0004H\u0016¢\u0006\u0004\b\u000e\u0010\u000fR\"\u0010\u0011\u001a\u00020\u00108\u0006@\u0006X\u0086\u000e¢\u0006\u0012\n"
                + "\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R%\u0010\u001a\u001a\u0010\u0012\f\u0012\n"
                + " \u0019*\u0004\u0018\u00010\u00180\u00180\u00178\u0006¢\u0006\f\n"
                + "\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010"
                + " \u001a\u00020\u00188BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001f¨\u0006#²\u0006\u000e\u0010!\u001a\u0004\u0018\u00010\u00188\n"
                + "X\u008a\u0084\u0002²\u0006\u000e\u0010\"\u001a\u00020\u00188\n"
                + "@\n"
                + "X\u008a\u008e\u0002"
        },
        d2 = {
            "Lcom/android/settings/wifi/WepNetworksPreferenceController;",
            "Lcom/android/settings/spa/preference/ComposePreferenceController;",
            "Landroid/content/Context;",
            "context",
            ApnSettings.MVNO_NONE,
            "preferenceKey",
            "<init>",
            "(Landroid/content/Context;Ljava/lang/String;)V",
            ApnSettings.MVNO_NONE,
            "getAvailabilityStatus",
            "()I",
            ApnSettings.MVNO_NONE,
            "Content",
            "(Landroidx/compose/runtime/Composer;I)V",
            "getSummary",
            "()Ljava/lang/String;",
            "Landroid/net/wifi/WifiManager;",
            "wifiManager",
            "Landroid/net/wifi/WifiManager;",
            "getWifiManager",
            "()Landroid/net/wifi/WifiManager;",
            "setWifiManager",
            "(Landroid/net/wifi/WifiManager;)V",
            "Lcom/android/settingslib/spa/framework/compose/OverridableFlow;",
            ApnSettings.MVNO_NONE,
            "kotlin.jvm.PlatformType",
            "wepAllowedFlow",
            "Lcom/android/settingslib/spa/framework/compose/OverridableFlow;",
            "getWepAllowedFlow",
            "()Lcom/android/settingslib/spa/framework/compose/OverridableFlow;",
            "getCarrierAllowed",
            "()Z",
            "carrierAllowed",
            "checked",
            "openDialog",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class WepNetworksPreferenceController extends ComposePreferenceController {
    public static final int $stable = 8;
    private final OverridableFlow wepAllowedFlow;
    private WifiManager wifiManager;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WepNetworksPreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(preferenceKey, "preferenceKey");
        Object systemService = context.getSystemService((Class<Object>) WifiManager.class);
        Intrinsics.checkNotNull(systemService);
        this.wifiManager = (WifiManager) systemService;
        this.wepAllowedFlow =
                new OverridableFlow(
                        FlowKt.callbackFlow(
                                new WepNetworksPreferenceController$wepAllowedFlow$1(this, null)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Boolean Content$lambda$0(State state) {
        return (Boolean) state.getValue();
    }

    private static final boolean Content$lambda$1(MutableState mutableState) {
        return ((Boolean) mutableState.getValue()).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void Content$lambda$2(MutableState mutableState, boolean z) {
        mutableState.setValue(Boolean.valueOf(z));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean getCarrierAllowed() {
        return this.wifiManager.isWepSupported();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController
    public void Content(Composer composer, final int i) {
        final ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-817246455);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final MutableState collectAsStateWithLifecycle =
                FlowExtKt.collectAsStateWithLifecycle(
                        this.wepAllowedFlow.flow, null, composerImpl, 56);
        final MutableState mutableState =
                (MutableState)
                        RememberSaveableKt.rememberSaveable(
                                new Object[0],
                                null,
                                new Function0() { // from class:
                                                  // com.android.settings.wifi.WepNetworksPreferenceController$Content$openDialog$2
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
        final android.net.wifi.WifiInfo connectionInfo = this.wifiManager.getConnectionInfo();
        SwitchPreferenceKt.SwitchPreference(
                new SwitchPreferenceModel(
                        composerImpl,
                        this,
                        collectAsStateWithLifecycle,
                        connectionInfo,
                        mutableState) { // from class:
                                        // com.android.settings.wifi.WepNetworksPreferenceController$Content$1
                    public final Function0 checked;
                    public final Function1 onCheckedChange;
                    public final Function0 summary;
                    public final /* synthetic */ WepNetworksPreferenceController this$0;
                    public final String title;

                    {
                        this.this$0 = this;
                        this.title =
                                StringResources_androidKt.stringResource(
                                        composerImpl, R.string.wifi_allow_wep_networks);
                        this.summary =
                                new Function0() { // from class:
                                                  // com.android.settings.wifi.WepNetworksPreferenceController$Content$1$summary$1
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    /* renamed from: invoke */
                                    public final Object mo1068invoke() {
                                        return WepNetworksPreferenceController.this.getSummary();
                                    }
                                };
                        this.checked =
                                new Function0() { // from class:
                                                  // com.android.settings.wifi.WepNetworksPreferenceController$Content$1$checked$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    /* renamed from: invoke */
                                    public final Object mo1068invoke() {
                                        Boolean Content$lambda$0;
                                        Content$lambda$0 =
                                                WepNetworksPreferenceController.Content$lambda$0(
                                                        collectAsStateWithLifecycle);
                                        return Content$lambda$0;
                                    }
                                };
                        this.onCheckedChange =
                                new Function1() { // from class:
                                                  // com.android.settings.wifi.WepNetworksPreferenceController$Content$1$onCheckedChange$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj) {
                                        Boolean bool = (Boolean) obj;
                                        boolean booleanValue = bool.booleanValue();
                                        if (booleanValue
                                                || connectionInfo.getCurrentSecurityType() != 1) {
                                            this.getWifiManager().setWepAllowed(booleanValue);
                                            this.getWepAllowedFlow()
                                                    .overrideChannel
                                                    .mo1469trySendJP2dKIU(bool);
                                        } else {
                                            WepNetworksPreferenceController.Content$lambda$2(
                                                    mutableState, true);
                                        }
                                        return Unit.INSTANCE;
                                    }
                                };
                    }

                    @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
                    public final Function0 getChangeable() {
                        final WepNetworksPreferenceController wepNetworksPreferenceController =
                                this.this$0;
                        return new Function0() { // from class:
                                                 // com.android.settings.wifi.WepNetworksPreferenceController$Content$1$changeable$1
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                boolean carrierAllowed;
                                carrierAllowed =
                                        WepNetworksPreferenceController.this.getCarrierAllowed();
                                return Boolean.valueOf(carrierAllowed);
                            }
                        };
                    }

                    @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
                    public final Function0 getChecked() {
                        return this.checked;
                    }

                    @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
                    public final Function1 getOnCheckedChange() {
                        return this.onCheckedChange;
                    }

                    @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
                    public final Function0 getSummary() {
                        return this.summary;
                    }

                    @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
                    public final String getTitle() {
                        return this.title;
                    }
                },
                composerImpl,
                0);
        if (Content$lambda$1(mutableState)) {
            composerImpl.startReplaceGroup(129402326);
            boolean changed = composerImpl.changed(mutableState);
            Object rememberedValue = composerImpl.rememberedValue();
            Object obj = Composer.Companion.Empty;
            if (changed || rememberedValue == obj) {
                rememberedValue =
                        new Function0() { // from class:
                                          // com.android.settings.wifi.WepNetworksPreferenceController$Content$2$1
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                WepNetworksPreferenceController.Content$lambda$2(
                                        MutableState.this, false);
                                return Unit.INSTANCE;
                            }
                        };
                composerImpl.updateRememberedValue(rememberedValue);
            }
            Function0 function0 = (Function0) rememberedValue;
            composerImpl.end(false);
            AlertDialogButton alertDialogButton =
                    new AlertDialogButton(
                            StringResources_androidKt.stringResource(
                                    composerImpl, R.string.sim_action_yes),
                            new Function0() { // from class:
                                              // com.android.settings.wifi.WepNetworksPreferenceController$Content$3
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                /* renamed from: invoke */
                                public final Object mo1068invoke() {
                                    WepNetworksPreferenceController.this
                                            .getWifiManager()
                                            .setWepAllowed(false);
                                    OverridableFlow wepAllowedFlow =
                                            WepNetworksPreferenceController.this
                                                    .getWepAllowedFlow();
                                    wepAllowedFlow.overrideChannel.mo1469trySendJP2dKIU(
                                            Boolean.FALSE);
                                    WepNetworksPreferenceController.Content$lambda$2(
                                            mutableState, false);
                                    return Unit.INSTANCE;
                                }
                            },
                            2);
            String stringResource =
                    StringResources_androidKt.stringResource(composerImpl, R.string.wifi_cancel);
            composerImpl.startReplaceGroup(129402785);
            boolean changed2 = composerImpl.changed(mutableState);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (changed2 || rememberedValue2 == obj) {
                rememberedValue2 =
                        new Function0() { // from class:
                                          // com.android.settings.wifi.WepNetworksPreferenceController$Content$4$1
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                WepNetworksPreferenceController.Content$lambda$2(
                                        MutableState.this, false);
                                return Unit.INSTANCE;
                            }
                        };
                composerImpl.updateRememberedValue(rememberedValue2);
            }
            composerImpl.end(false);
            SettingsAlertDialogWithIconKt.SettingsAlertDialogWithIcon(
                    function0,
                    alertDialogButton,
                    new AlertDialogButton(stringResource, (Function0) rememberedValue2, 2),
                    StringResources_androidKt.stringResource(
                            composerImpl, R.string.wifi_settings_wep_networks_disconnect_title),
                    null,
                    ComposableSingletons$WepNetworksPreferenceControllerKt.f69lambda1,
                    composerImpl,
                    196608,
                    16);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.wifi.WepNetworksPreferenceController$Content$5
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj2, Object obj3) {
                            ((Number) obj3).intValue();
                            WepNetworksPreferenceController.this.Content(
                                    (Composer) obj2,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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

    public final OverridableFlow getWepAllowedFlow() {
        return this.wepAllowedFlow;
    }

    public final WifiManager getWifiManager() {
        return this.wifiManager;
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

    public final void setWifiManager(WifiManager wifiManager) {
        Intrinsics.checkNotNullParameter(wifiManager, "<set-?>");
        this.wifiManager = wifiManager;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public String getSummary() {
        String string =
                this.mContext.getString(
                        getCarrierAllowed()
                                ? R.string.wifi_allow_wep_networks_summary
                                : R.string.wifi_allow_wep_networks_summary_carrier_not_allow);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        return string;
    }
}
