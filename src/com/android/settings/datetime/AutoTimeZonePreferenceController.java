package com.android.settings.datetime;

import android.app.time.TimeManager;
import android.app.time.TimeZoneCapabilitiesAndConfig;
import android.app.time.TimeZoneConfiguration;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.preference.Preference;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.Utils;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.widget.SecRestrictedSwitchPreference;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AutoTimeZonePreferenceController extends TogglePreferenceController {
    private UpdateTimeAndDateCallback mCallback;
    private boolean mIsFromSUW;
    private LocationManager mLocationManager;
    private LocationTimeZoneDetectionPreferenceController
            mLocationTimeZoneDetectionPreferenceController;
    private final TimeManager mTimeManager;

    public AutoTimeZonePreferenceController(Context context, String str) {
        super(context, str);
        this.mTimeManager = (TimeManager) context.getSystemService(TimeManager.class);
        Context context2 = this.mContext;
        String str2 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        if (Utils.isWifiOnly(context2)) {
            this.mLocationTimeZoneDetectionPreferenceController =
                    new LocationTimeZoneDetectionPreferenceController(this.mContext);
            this.mLocationManager =
                    (LocationManager) context.getSystemService(LocationManager.class);
        }
    }

    private TimeZoneCapabilitiesAndConfig getTimeZoneCapabilitiesAndConfig() {
        return this.mTimeManager.getTimeZoneCapabilitiesAndConfig();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        Context context = this.mContext;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        if (Utils.isWifiOnly(context) && Rune.isChinaModel()) {
            return 3;
        }
        int configureAutoDetectionEnabledCapability =
                getTimeZoneCapabilitiesAndConfig()
                        .getCapabilities()
                        .getConfigureAutoDetectionEnabledCapability();
        if (configureAutoDetectionEnabledCapability == 10) {
            return 5;
        }
        if (configureAutoDetectionEnabledCapability == 20
                || configureAutoDetectionEnabledCapability == 30
                || configureAutoDetectionEnabledCapability == 40) {
            return 0;
        }
        throw new IllegalStateException(
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                        configureAutoDetectionEnabledCapability, "Unknown capability="));
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
        return R.string.menu_key_system;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return LocationProviderStatusPreferenceController.hasLocationTimeZoneNoTelephonyFallback(
                        this.mTimeManager.getTimeZoneCapabilitiesAndConfig().getDetectorStatus())
                ? this.mContext
                        .getResources()
                        .getString(R.string.auto_zone_requires_location_summary)
                : ApnSettings.MVNO_NONE;
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
        return isEnabled();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @VisibleForTesting
    public boolean isEnabled() {
        return getTimeZoneCapabilitiesAndConfig().getConfiguration().isAutoDetectionEnabled();
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
        boolean updateTimeZoneConfiguration =
                this.mTimeManager.updateTimeZoneConfiguration(
                        new TimeZoneConfiguration.Builder().setAutoDetectionEnabled(z).build());
        ((DateTimeSettings) this.mCallback).updatePreferenceStates();
        return updateTimeZoneConfiguration;
    }

    public AutoTimeZonePreferenceController setFromSUW(boolean z) {
        this.mIsFromSUW = z;
        return this;
    }

    public AutoTimeZonePreferenceController setTimeAndDateCallback(
            UpdateTimeAndDateCallback updateTimeAndDateCallback) {
        this.mCallback = updateTimeAndDateCallback;
        return this;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        if (preference instanceof SecRestrictedSwitchPreference) {
            ((SecRestrictedSwitchPreference) preference).setChecked(isEnabled());
            StringBuilder sb = com.android.settings.Utils.sBuilder;
            if (SemCscFeature.getInstance()
                    .getBoolean("CscFeature_Common_SupportGeolocationFallback", false)) {
                if (isEnabled()) {
                    preference.setSummary(ApnSettings.MVNO_NONE);
                } else {
                    preference.setSummary(R.string.sec_automatic_time_zone_geolocation_off_summary);
                }
            }
            Context context = this.mContext;
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            if (Utils.isWifiOnly(context)) {
                if (this.mLocationManager.isLocationEnabled()) {
                    preference.setSummary(R.string.automatic_time_zone_wifi_summary);
                } else {
                    preference.setSummary(
                            R.string.sec_set_timezone_based_on_location_summary_location_off);
                }
                preference.setEnabled(
                        this.mLocationTimeZoneDetectionPreferenceController
                                .isLocationTimezoneEnabled());
            }
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
