package com.android.settings.datetime;

import android.app.time.LocationTimeZoneAlgorithmStatus;
import android.app.time.TimeManager;
import android.app.time.TimeZoneCapabilitiesAndConfig;
import android.app.time.TimeZoneDetectorStatus;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.service.timezone.TimeZoneProviderStatus;
import android.text.TextUtils;
import android.view.View;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.location.LocationSettings;
import com.android.settingslib.widget.BannerMessagePreference;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LocationProviderStatusPreferenceController extends BasePreferenceController
        implements TimeManager.TimeZoneDetectorListener {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private BannerMessagePreference mPreference;
    private final TimeManager mTimeManager;

    public LocationProviderStatusPreferenceController(Context context, String str) {
        super(context, str);
        this.mPreference = null;
        TimeManager timeManager = (TimeManager) context.getSystemService(TimeManager.class);
        this.mTimeManager = timeManager;
        timeManager.addTimeZoneDetectorListener(context.getMainExecutor(), this);
    }

    private TimeZoneProviderStatus getLtzpStatusToReport() {
        LocationTimeZoneAlgorithmStatus locationTimeZoneAlgorithmStatus =
                this.mTimeManager
                        .getTimeZoneCapabilitiesAndConfig()
                        .getDetectorStatus()
                        .getLocationTimeZoneAlgorithmStatus();
        TimeZoneProviderStatus primaryProviderReportedStatus =
                locationTimeZoneAlgorithmStatus.getPrimaryProviderReportedStatus();
        TimeZoneProviderStatus secondaryProviderReportedStatus =
                locationTimeZoneAlgorithmStatus.getSecondaryProviderReportedStatus();
        return (primaryProviderReportedStatus == null || secondaryProviderReportedStatus == null)
                ? primaryProviderReportedStatus != null
                        ? primaryProviderReportedStatus
                        : secondaryProviderReportedStatus
                : pickWorstLtzpStatus(
                        primaryProviderReportedStatus, secondaryProviderReportedStatus);
    }

    public static boolean hasLocationTimeZoneNoTelephonyFallback(
            TimeZoneDetectorStatus timeZoneDetectorStatus) {
        return timeZoneDetectorStatus.getTelephonyTimeZoneAlgorithmStatus().getAlgorithmStatus()
                        == 1
                && timeZoneDetectorStatus.getLocationTimeZoneAlgorithmStatus().getStatus() != 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$displayPreference$0(View view) {
        launchLocationSettings();
    }

    private void launchLocationSettings() {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = LocationSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = getMetricsCategory();
        subSettingLauncher.launch();
    }

    private static TimeZoneProviderStatus pickWorstLtzpStatus(
            TimeZoneProviderStatus timeZoneProviderStatus,
            TimeZoneProviderStatus timeZoneProviderStatus2) {
        return scoreLtzpStatus(timeZoneProviderStatus) >= scoreLtzpStatus(timeZoneProviderStatus2)
                ? timeZoneProviderStatus
                : timeZoneProviderStatus2;
    }

    private static int scoreLtzpStatus(TimeZoneProviderStatus timeZoneProviderStatus) {
        if (timeZoneProviderStatus.getLocationDetectionDependencyStatus() <= 2) {
            return 0;
        }
        return timeZoneProviderStatus.getLocationDetectionDependencyStatus();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        BannerMessagePreference bannerMessagePreference =
                (BannerMessagePreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = bannerMessagePreference;
        bannerMessagePreference.setPositiveButtonText$1(
                R.string.location_time_zone_provider_fix_dialog_ok_button);
        bannerMessagePreference.setPositiveButtonOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.datetime.LocationProviderStatusPreferenceController$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        LocationProviderStatusPreferenceController.this.lambda$displayPreference$0(
                                view);
                    }
                });
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return !TextUtils.isEmpty(getSummary()) ? 1 : 2;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        TimeZoneCapabilitiesAndConfig timeZoneCapabilitiesAndConfig =
                this.mTimeManager.getTimeZoneCapabilitiesAndConfig();
        TimeZoneDetectorStatus detectorStatus = timeZoneCapabilitiesAndConfig.getDetectorStatus();
        if (!timeZoneCapabilitiesAndConfig.getCapabilities().isUseLocationEnabled()
                && hasLocationTimeZoneNoTelephonyFallback(detectorStatus)) {
            return this.mContext.getString(
                    R.string.location_time_zone_detection_status_summary_blocked_by_settings);
        }
        TimeZoneProviderStatus ltzpStatusToReport = getLtzpStatusToReport();
        if (ltzpStatusToReport == null) {
            return ApnSettings.MVNO_NONE;
        }
        int locationDetectionDependencyStatus =
                ltzpStatusToReport.getLocationDetectionDependencyStatus();
        return locationDetectionDependencyStatus == 6
                ? this.mContext.getString(
                        R.string.location_time_zone_detection_status_summary_blocked_by_settings)
                : locationDetectionDependencyStatus == 5
                        ? this.mContext.getString(
                                R.string
                                        .location_time_zone_detection_status_summary_degraded_by_settings)
                        : locationDetectionDependencyStatus == 4
                                ? this.mContext.getString(
                                        R.string
                                                .location_time_zone_detection_status_summary_blocked_by_environment)
                                : locationDetectionDependencyStatus == 3
                                        ? this.mContext.getString(
                                                R.string
                                                        .location_time_zone_detection_status_summary_temporarily_unavailable)
                                        : ApnSettings.MVNO_NONE;
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

    public void onChange() {
        BannerMessagePreference bannerMessagePreference = this.mPreference;
        if (bannerMessagePreference != null) {
            bannerMessagePreference.setVisible(getAvailabilityStatus() == 1);
            refreshSummary(this.mPreference);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
