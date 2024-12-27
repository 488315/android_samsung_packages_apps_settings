package com.samsung.android.settings.notification;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.telephony.SubscriptionManager;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class StatusBarShowNetworkInfoController extends TogglePreferenceController {
    private Context mContext;

    public StatusBarShowNetworkInfoController(Context context, String str) {
        super(context, str);
        this.mContext = context;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!Rune.STATUS_NETWORK_SIM_MOBILITY) {
            return 3;
        }
        int slotIndex =
                SubscriptionManager.getSlotIndex(SubscriptionManager.getDefaultSubscriptionId());
        return (("ORANGE"
                                        .equals(
                                                slotIndex >= 0 && slotIndex < 2
                                                        ? SemCarrierFeature.getInstance()
                                                                .getString(
                                                                        slotIndex,
                                                                        "CarrierFeature_SystemUI_ConfigOpBrandingForIndicatorIcon",
                                                                        ApnSettings.MVNO_NONE,
                                                                        false)
                                                        : SemCscFeature.getInstance()
                                                                .getString(
                                                                        "CscFeature_SystemUI_ConfigOpBrandingForIndicatorIcon",
                                                                        ApnSettings.MVNO_NONE))
                                && "HOME".equals(Rune.getCarrierInfoLogo()))
                        || "BOTH".equals(Rune.getCarrierInfoLogo()))
                ? 0
                : 3;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getMetricsCategory() {
        return 4050;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_notifications;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return Settings.System.getInt(
                        this.mContext.getContentResolver(),
                        "status_bar_show_network_information",
                        1)
                == 1;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        Settings.System.putInt(
                this.mContext.getContentResolver(),
                "status_bar_show_network_information",
                z ? 1 : 0);
        LoggingHelper.insertEventLogging(getMetricsCategory(), 6033, z ? 1L : 0L);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
