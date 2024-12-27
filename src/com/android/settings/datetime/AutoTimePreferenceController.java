package com.android.settings.datetime;

import android.app.time.TimeCapabilitiesAndConfig;
import android.app.time.TimeConfiguration;
import android.app.time.TimeManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.SecSwitchPreference;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.datetime.DateTimeUtils;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AutoTimePreferenceController extends TogglePreferenceController {
    private static final String KEY_AUTO_TIME = "auto_time";
    private UpdateTimeAndDateCallback mCallback;
    private final TimeManager mTimeManager;

    public AutoTimePreferenceController(Context context, String str) {
        super(context, str);
        this.mTimeManager = (TimeManager) context.getSystemService(TimeManager.class);
    }

    private TimeCapabilitiesAndConfig getTimeCapabilitiesAndConfig() {
        return this.mTimeManager.getTimeCapabilitiesAndConfig();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        int configureAutoDetectionEnabledCapability =
                getTimeCapabilitiesAndConfig()
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
        return getTimeCapabilitiesAndConfig().getConfiguration().isAutoDetectionEnabled();
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
        boolean updateTimeConfiguration =
                this.mTimeManager.updateTimeConfiguration(
                        new TimeConfiguration.Builder().setAutoDetectionEnabled(z).build());
        ((DateTimeSettings) this.mCallback).updatePreferenceStates();
        LoggingHelper.insertEventLogging(38, 4750);
        return updateTimeConfiguration;
    }

    public void setDateAndTimeCallback(UpdateTimeAndDateCallback updateTimeAndDateCallback) {
        this.mCallback = updateTimeAndDateCallback;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        if (preference instanceof SecSwitchPreference) {
            if (preference.isEnabled()) {
                preference.setEnabled(DateTimeUtils.applyEDMDateTimeChangePolicy(this.mContext));
            }
            ((SecSwitchPreference) preference).setChecked(isEnabled());
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
