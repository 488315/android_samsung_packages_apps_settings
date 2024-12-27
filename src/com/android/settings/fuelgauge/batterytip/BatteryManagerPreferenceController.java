package com.android.settings.fuelgauge.batterytip;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserManager;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.fuelgauge.PowerUsageFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.utils.StringUtil;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BatteryManagerPreferenceController extends BasePreferenceController {
    private static final String KEY_BATTERY_MANAGER = "smart_battery_manager";
    private AppOpsManager mAppOpsManager;
    private boolean mEnableAppBatteryUsagePage;
    private PowerUsageFeatureProvider mPowerUsageFeatureProvider;
    private UserManager mUserManager;

    public BatteryManagerPreferenceController(Context context) {
        super(context, KEY_BATTERY_MANAGER);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mPowerUsageFeatureProvider = featureFactoryImpl.getPowerUsageFeatureProvider();
        this.mAppOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        this.mEnableAppBatteryUsagePage =
                this.mContext
                        .getResources()
                        .getBoolean(R.bool.config_app_battery_usage_list_enabled);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        this.mPowerUsageFeatureProvider.getClass();
        if (!this.mContext.getResources().getBoolean(R.bool.config_battery_manager_consider_ac)) {
            return 1;
        }
        this.mPowerUsageFeatureProvider.getClass();
        return 3;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (this.mEnableAppBatteryUsagePage) {
            return;
        }
        updateSummary(
                preference,
                ((ArrayList)
                                BatteryTipUtils.getRestrictedAppsList(
                                        this.mAppOpsManager, this.mUserManager))
                        .size());
    }

    public void updateSummary(Preference preference, int i) {
        if (i > 0) {
            preference.setSummary(
                    StringUtil.getIcuPluralsString(
                            this.mContext, i, R.string.battery_manager_app_restricted));
        } else {
            this.mPowerUsageFeatureProvider.getClass();
            preference.setSummary(R.string.battery_manager_summary_unsupported);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
