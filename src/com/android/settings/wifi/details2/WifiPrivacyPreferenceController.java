package com.android.settings.wifi.details2;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.res.StringResources_androidKt;

import com.android.settings.R;
import com.android.settings.spa.SpaActivity;
import com.android.settings.spa.preference.ComposePreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.spa.widget.preference.PreferenceKt;
import com.android.settingslib.spa.widget.preference.PreferenceModel;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u00000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0004\b\u0007\u0018\u0000"
                + " \u00152\u00020\u0001:\u0001\u0015B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\r"
                + "\u0010\u000e\u001a\u00020\u000fH\u0017¢\u0006\u0002\u0010\u0010J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u000f2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0005R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n"
                + "\u0000\u001a\u0004\b\n"
                + "\u0010\u000b\"\u0004\b\f\u0010\r"
                + "¨\u0006\u0016"
        },
        d2 = {
            "Lcom/android/settings/wifi/details2/WifiPrivacyPreferenceController;",
            "Lcom/android/settings/spa/preference/ComposePreferenceController;",
            "context",
            "Landroid/content/Context;",
            "preferenceKey",
            ApnSettings.MVNO_NONE,
            "(Landroid/content/Context;Ljava/lang/String;)V",
            "wifiEntryKey",
            "wifiManager",
            "Landroid/net/wifi/WifiManager;",
            "getWifiManager",
            "()Landroid/net/wifi/WifiManager;",
            "setWifiManager",
            "(Landroid/net/wifi/WifiManager;)V",
            "Content",
            ApnSettings.MVNO_NONE,
            "(Landroidx/compose/runtime/Composer;I)V",
            "getAvailabilityStatus",
            ApnSettings.MVNO_NONE,
            "setWifiEntryKey",
            GoodSettingsContract.EXTRA_NAME.POLICY_KEY,
            "Companion",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class WifiPrivacyPreferenceController extends ComposePreferenceController {
    public static final int $stable = 8;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion();
    private static final int PREF_SEND_DHCP_HOST_NAME_DISABLE = 1;
    private static final int PREF_SEND_DHCP_HOST_NAME_ENABLE = 0;
    private String wifiEntryKey;
    private WifiManager wifiManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Companion {}

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiPrivacyPreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(preferenceKey, "preferenceKey");
        Object systemService = context.getSystemService((Class<Object>) WifiManager.class);
        Intrinsics.checkNotNull(systemService);
        this.wifiManager = (WifiManager) systemService;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController
    public void Content(Composer composer, final int i) {
        final ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1491995021);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        PreferenceKt.Preference(
                new PreferenceModel(
                        composerImpl,
                        this) { // from class:
                                // com.android.settings.wifi.details2.WifiPrivacyPreferenceController$Content$1
                    public final ComposableLambdaImpl icon =
                            ComposableSingletons$WifiPrivacyPreferenceControllerKt.f70lambda1;
                    public final Function0 onClick;
                    public final String title;

                    {
                        this.title =
                                StringResources_androidKt.stringResource(
                                        composerImpl, R.string.wifi_privacy_settings);
                        this.onClick =
                                new Function0() { // from class:
                                                  // com.android.settings.wifi.details2.WifiPrivacyPreferenceController$Content$1$onClick$1
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    /* renamed from: invoke */
                                    public final Object mo1068invoke() {
                                        String str;
                                        Context context;
                                        str = WifiPrivacyPreferenceController.this.wifiEntryKey;
                                        if (str != null) {
                                            WifiPrivacyPreferenceController
                                                    wifiPrivacyPreferenceController =
                                                            WifiPrivacyPreferenceController.this;
                                            SpaActivity.Companion companion = SpaActivity.Companion;
                                            context =
                                                    ((AbstractPreferenceController)
                                                                    wifiPrivacyPreferenceController)
                                                            .mContext;
                                            Intrinsics.checkNotNullExpressionValue(
                                                    context,
                                                    "access$getMContext$p$s1785566730(...)");
                                            WifiPrivacyPageProvider wifiPrivacyPageProvider =
                                                    WifiPrivacyPageProvider.INSTANCE;
                                            SpaActivity.Companion.startSpaActivity(
                                                    context, "WifiPrivacy/".concat(str));
                                        }
                                        return Unit.INSTANCE;
                                    }
                                };
                    }

                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final Function2 getIcon() {
                        return this.icon;
                    }

                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final Function0 getOnClick() {
                        return this.onClick;
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
                                      // com.android.settings.wifi.details2.WifiPrivacyPreferenceController$Content$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            WifiPrivacyPreferenceController.this.Content(
                                    (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.wifiManager.isConnectedMacRandomizationSupported() ? 0 : 2;
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

    public final void setWifiEntryKey(String key) {
        this.wifiEntryKey = key;
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
}
