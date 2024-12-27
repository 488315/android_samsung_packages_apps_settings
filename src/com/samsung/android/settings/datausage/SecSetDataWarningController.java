package com.samsung.android.settings.datausage;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.CarrierConfigManager;
import android.util.Log;

import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecSetDataWarningController extends TogglePreferenceController {
    private static final String TAG = "SecBillingCycleSettings.SecSetDataWarningController";

    public SecSetDataWarningController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return SecBillingCycleManager.getInstance(this.mContext).getAvailableBillingCycleSetting()
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

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return 0;
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
        return SecBillingCycleManager.getInstance(this.mContext).getPolicyWarningBytes() != -1;
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
        SecBillingCycleManager secBillingCycleManager =
                SecBillingCycleManager.getInstance(this.mContext);
        if (z) {
            CarrierConfigManager carrierConfigManager =
                    (CarrierConfigManager)
                            secBillingCycleManager.mContext.getSystemService("carrier_config");
            long j = 2000000000;
            if (carrierConfigManager != null && carrierConfigManager.getConfig() != null) {
                j =
                        Math.max(
                                carrierConfigManager
                                        .getConfig()
                                        .getLong("data_warning_threshold_bytes_long"),
                                2000000000L);
            }
            Log.d("SecBillingCycleSettings.SecBillingCycleManager", "getDefaultWarningBytes:" + j);
            secBillingCycleManager.setPolicyWarningBytes(j);
        } else {
            secBillingCycleManager.setPolicyWarningBytes(-1L);
        }
        LoggingHelper.insertEventLogging(getMetricsCategory(), 7191, z);
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
