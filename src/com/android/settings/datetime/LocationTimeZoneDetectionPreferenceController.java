package com.android.settings.datetime;

import android.app.time.TimeManager;
import android.app.time.TimeZoneCapabilitiesAndConfig;
import android.app.time.TimeZoneConfiguration;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.util.Log;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.core.InstrumentedPreferenceFragment;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.datausage.DataUsageUtils;
import com.android.settings.network.telephony.MobileNetworkUtils;
import com.android.settingslib.Utils;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.connection.ConnectionsUtils;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LocationTimeZoneDetectionPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnStart, OnStop, TimeManager.TimeZoneDetectorListener {
    private static final String TAG = "location_time_zone_detection";
    private InstrumentedPreferenceFragment mFragment;
    private final LocationManager mLocationManager;
    private Preference mPreference;
    private final TimeManager mTimeManager;
    private TimeZoneCapabilitiesAndConfig mTimeZoneCapabilitiesAndConfig;

    public LocationTimeZoneDetectionPreferenceController(Context context) {
        super(context, TAG);
        this.mTimeManager = (TimeManager) context.getSystemService(TimeManager.class);
        this.mLocationManager = (LocationManager) context.getSystemService(LocationManager.class);
    }

    private TimeZoneCapabilitiesAndConfig getTimeZoneCapabilitiesAndConfig(boolean z) {
        if (z || this.mTimeZoneCapabilitiesAndConfig == null) {
            this.mTimeZoneCapabilitiesAndConfig =
                    this.mTimeManager.getTimeZoneCapabilitiesAndConfig();
        }
        return this.mTimeZoneCapabilitiesAndConfig;
    }

    private void refreshUi() {
        getTimeZoneCapabilitiesAndConfig(true);
        refreshSummary(this.mPreference);
        this.mPreference.setEnabled(isLocationTimezoneEnabled());
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        int configureGeoDetectionEnabledCapability;
        if (Rune.isChinaModel()
                || Utils.isWifiOnly(this.mContext)
                || (configureGeoDetectionEnabledCapability =
                                getTimeZoneCapabilitiesAndConfig(false)
                                        .getCapabilities()
                                        .getConfigureGeoDetectionEnabledCapability())
                        == 10
                || configureGeoDetectionEnabledCapability == 20) {
            return 3;
        }
        if (configureGeoDetectionEnabledCapability == 30
                || configureGeoDetectionEnabledCapability == 40) {
            return 0;
        }
        throw new IllegalStateException(
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                        configureGeoDetectionEnabledCapability, "Unknown capability="));
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        int i;
        TimeZoneCapabilitiesAndConfig timeZoneCapabilitiesAndConfig =
                getTimeZoneCapabilitiesAndConfig(false);
        int configureGeoDetectionEnabledCapability =
                timeZoneCapabilitiesAndConfig
                        .getCapabilities()
                        .getConfigureGeoDetectionEnabledCapability();
        TimeZoneConfiguration configuration = timeZoneCapabilitiesAndConfig.getConfiguration();
        if (configureGeoDetectionEnabledCapability == 10) {
            i = R.string.location_time_zone_detection_not_supported;
        } else if (configureGeoDetectionEnabledCapability == 20) {
            i = R.string.location_time_zone_detection_not_allowed;
        } else if (configureGeoDetectionEnabledCapability == 30) {
            i =
                    !configuration.isAutoDetectionEnabled()
                            ? R.string.location_time_zone_detection_auto_is_off
                            : !this.mLocationManager.isLocationEnabled()
                                    ? R.string.location_app_permission_summary_location_off
                                    : R.string.location_time_zone_detection_not_applicable;
        } else {
            if (configureGeoDetectionEnabledCapability != 40) {
                throw new IllegalStateException(
                        SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                configureGeoDetectionEnabledCapability,
                                "Unexpected configureGeoDetectionEnabledCapability="));
            }
            i =
                    (ConnectionsUtils.isWifiEnabled(this.mContext)
                                    || MobileNetworkUtils.isMobileDataEnabled(this.mContext))
                            ? R.string.sec_set_timezone_based_on_location_summary
                            : R.string.sec_set_timezone_based_on_location_summary_mobiledata_wifi;
        }
        return this.mContext.getString(i);
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
        return getTimeZoneCapabilitiesAndConfig(false).getConfiguration().isGeoDetectionEnabled();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    public boolean isLocationTimezoneEnabled() {
        Context context = this.mContext;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        if (!Utils.isWifiOnly(context)) {
            int configureGeoDetectionEnabledCapability =
                    getTimeZoneCapabilitiesAndConfig(false)
                            .getCapabilities()
                            .getConfigureGeoDetectionEnabledCapability();
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    configureGeoDetectionEnabledCapability,
                    "configureGeoDetectionEnabledCapability = ",
                    TAG);
            if (configureGeoDetectionEnabledCapability != 40) {
                return false;
            }
        } else if (!this.mLocationManager.isLocationEnabled()) {
            return false;
        }
        boolean isWifiEnabled = ConnectionsUtils.isWifiEnabled(this.mContext);
        boolean z =
                DataUsageUtils.hasActiveSim(this.mContext)
                        && MobileNetworkUtils.isMobileDataEnabled(this.mContext);
        Log.i(TAG, "isWifiEnabled = " + isWifiEnabled + " isMobileDataEnabled = " + z);
        return isWifiEnabled || z;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    public void onChange() {
        refreshUi();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mTimeManager.addTimeZoneDetectorListener(this.mContext.getMainExecutor(), this);
        refreshUi();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mTimeManager.removeTimeZoneDetectorListener(this);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        return this.mTimeManager.updateTimeZoneConfiguration(
                new TimeZoneConfiguration.Builder().setGeoDetectionEnabled(z).build());
    }

    public void setFragment(InstrumentedPreferenceFragment instrumentedPreferenceFragment) {
        this.mFragment = instrumentedPreferenceFragment;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        refreshUi();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
