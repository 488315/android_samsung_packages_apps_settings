package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.satellite.SatelliteManager;
import android.util.Log;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.network.CarrierConfigCache;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SatelliteSettingPreferenceController extends TelephonyBasePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    private static final String TAG = "SatelliteSettingPreferenceController";
    CarrierConfigCache mCarrierConfigCache;
    private Boolean mIsSatelliteEligible;
    SatelliteManager mSatelliteManager;

    public SatelliteSettingPreferenceController(Context context, String str) {
        super(context, str);
        this.mIsSatelliteEligible = null;
        this.mCarrierConfigCache = CarrierConfigCache.getInstance(context);
        this.mSatelliteManager =
                (SatelliteManager) context.getSystemService(SatelliteManager.class);
    }

    private static void logd(String str) {
        Log.d(TAG, str);
    }

    private static void loge(String str) {
        Log.e(TAG, str);
    }

    private void updateSummary(Preference preference) {
        try {
            boolean z =
                    !this.mSatelliteManager
                            .getAttachRestrictionReasonsForCarrier(this.mSubId)
                            .contains(2);
            Boolean bool = this.mIsSatelliteEligible;
            if (bool != null && bool.booleanValue() == z) {
                return;
            }
            Boolean valueOf = Boolean.valueOf(z);
            this.mIsSatelliteEligible = valueOf;
            preference.setSummary(
                    this.mContext.getString(
                            valueOf.booleanValue()
                                    ? R.string.satellite_setting_enabled_summary
                                    : R.string.satellite_setting_disabled_summary));
        } catch (IllegalArgumentException | IllegalStateException | SecurityException e) {
            loge(e.toString());
            preference.setSummary(R.string.satellite_setting_disabled_summary);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.network.telephony.TelephonyAvailabilityCallback
    public int getAvailabilityStatus(int i) {
        this.mCarrierConfigCache.getClass();
        return CarrierConfigCache.getConfigForSubId(i).getBoolean("satellite_attach_supported_bool")
                ? 0
                : 2;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!getPreferenceKey().equals(preference.getKey())) {
            return false;
        }
        Intent intent =
                new Intent("android.settings.SATELLITE_SETTING")
                        .setPackage(this.mContext.getPackageName());
        intent.putExtra(":settings:show_fragment_as_subsetting", true);
        intent.putExtra("sub_id", this.mSubId);
        this.mContext.startActivity(intent);
        return true;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    public void init(int i) {
        logd(SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, "init(), subId="));
        this.mSubId = i;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (preference != null) {
            updateSummary(preference);
        }
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {}

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {}
}
