package com.android.settings.fuelgauge;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserManager;
import android.util.Pair;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.InstrumentedPreferenceFragment;
import com.android.settings.fuelgauge.batterytip.AppInfo;
import com.android.settings.fuelgauge.batterytip.BatteryTipUtils;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.utils.StringUtil;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RestrictAppPreferenceController extends BasePreferenceController {
    static final String KEY_RESTRICT_APP = "restricted_app";
    List<AppInfo> mAppInfos;
    private AppOpsManager mAppOpsManager;
    private boolean mEnableAppBatteryUsagePage;
    private InstrumentedPreferenceFragment mPreferenceFragment;
    private UserManager mUserManager;

    public RestrictAppPreferenceController(Context context) {
        super(context, KEY_RESTRICT_APP);
        this.mAppOpsManager = (AppOpsManager) context.getSystemService("appops");
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        this.mUserManager = userManager;
        this.mAppInfos = BatteryTipUtils.getRestrictedAppsList(this.mAppOpsManager, userManager);
        this.mEnableAppBatteryUsagePage =
                this.mContext
                        .getResources()
                        .getBoolean(R.bool.config_app_battery_usage_list_enabled);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (this.mAppInfos.size() <= 0 || this.mEnableAppBatteryUsagePage) ? 2 : 0;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!getPreferenceKey().equals(preference.getKey())) {
            return super.handlePreferenceTreeClick(preference);
        }
        RestrictedAppDetails.startRestrictedAppDetails(this.mPreferenceFragment, this.mAppInfos);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getMetricsFeatureProvider().action(this.mContext, 1906, new Pair[0]);
        return true;
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
        List<AppInfo> restrictedAppsList =
                BatteryTipUtils.getRestrictedAppsList(this.mAppOpsManager, this.mUserManager);
        this.mAppInfos = restrictedAppsList;
        int size = restrictedAppsList.size();
        preference.setVisible(size > 0);
        preference.setSummary(
                StringUtil.getIcuPluralsString(
                        this.mContext, size, R.string.restricted_app_summary));
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public RestrictAppPreferenceController(
            InstrumentedPreferenceFragment instrumentedPreferenceFragment) {
        this(instrumentedPreferenceFragment.getContext());
        this.mPreferenceFragment = instrumentedPreferenceFragment;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
