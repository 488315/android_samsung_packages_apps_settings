package com.samsung.android.settings.datausage;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecSetDataLimitController extends TogglePreferenceController {
    private static final String TAG = "SecBillingCycleSettings.SecSetDataLimitController";
    private SecBillingCycleSettings mParent;

    public SecSetDataLimitController(Context context, String str) {
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
        SecBillingCycleManager secBillingCycleManager =
                SecBillingCycleManager.getInstance(this.mContext);
        return (secBillingCycleManager.isDataLimitRestrict()
                        || secBillingCycleManager.getPolicyLimitBytes() == -1)
                ? false
                : true;
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
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            String salesCode = Utils.getSalesCode();
            if (this.mParent == null || !("VZW".equals(salesCode) || "VPP".equals(salesCode))) {
                secBillingCycleManager.setPolicyLimitBytesByDefault();
            } else {
                SecBillingCycleSettings secBillingCycleSettings = this.mParent;
                if (secBillingCycleSettings.isAdded()) {
                    String string =
                            secBillingCycleSettings
                                    .getResources()
                                    .getString(R.string.data_usage_limit_dialog_vzw);
                    Bundle bundle = new Bundle();
                    bundle.putCharSequence("message", string);
                    SecConfirmLimitFragment secConfirmLimitFragment = new SecConfirmLimitFragment();
                    secConfirmLimitFragment.setArguments(bundle);
                    secConfirmLimitFragment.setTargetFragment(secBillingCycleSettings, 0);
                    secConfirmLimitFragment.show(
                            secBillingCycleSettings.getFragmentManager(), "confirmLimit");
                }
            }
        } else {
            secBillingCycleManager.setPolicyLimitBytes(-1L);
        }
        LoggingHelper.insertEventLogging(getMetricsCategory(), 7123, z);
        return true;
    }

    public void setParentFragment(SecBillingCycleSettings secBillingCycleSettings) {
        this.mParent = secBillingCycleSettings;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        Log.d(TAG, "updateState()");
        super.updateState(preference);
        SecBillingCycleManager secBillingCycleManager =
                SecBillingCycleManager.getInstance(this.mContext);
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        String salesCode = Utils.getSalesCode();
        boolean z = "VZW".equals(salesCode) || "VPP".equals(salesCode);
        boolean isSprModel = Rune.isSprModel();
        if (z || isSprModel) {
            preference.setTitle(R.string.data_usage_disable_mobile_limit_vzw);
            if (z) {
                preference.setSummary(ApnSettings.MVNO_NONE);
            }
        }
        if (secBillingCycleManager.isDataLimitRestrict()) {
            preference.setEnabled(false);
        } else {
            preference.setEnabled(true);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
