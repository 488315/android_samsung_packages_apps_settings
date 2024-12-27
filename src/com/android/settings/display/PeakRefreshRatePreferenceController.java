package com.android.settings.display;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.display.RefreshRateSettingsUtils;
import com.android.server.display.feature.flags.Flags;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PeakRefreshRatePreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    private static final float INVALIDATE_REFRESH_RATE = -1.0f;
    private static final String TAG = "RefreshRatePrefCtr";
    private DeviceConfigDisplaySettings mDeviceConfigDisplaySettings;
    private final Handler mHandler;
    private final IDeviceConfigChange mOnDeviceConfigChange;
    float mPeakRefreshRate;
    private Preference mPreference;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.display.PeakRefreshRatePreferenceController$1, reason: invalid class name */
    public final class AnonymousClass1 implements IDeviceConfigChange {
        public AnonymousClass1() {}
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class DeviceConfigDisplaySettings
            implements DeviceConfig.OnPropertiesChangedListener, Executor {
        public DeviceConfigDisplaySettings() {}

        @Override // java.util.concurrent.Executor
        public final void execute(Runnable runnable) {
            if (PeakRefreshRatePreferenceController.this.mHandler != null) {
                PeakRefreshRatePreferenceController.this.mHandler.post(runnable);
            }
        }

        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
            if (PeakRefreshRatePreferenceController.this.mOnDeviceConfigChange != null) {
                PeakRefreshRatePreferenceController peakRefreshRatePreferenceController =
                        PeakRefreshRatePreferenceController.this;
                peakRefreshRatePreferenceController.updateState(
                        peakRefreshRatePreferenceController.mPreference);
                PeakRefreshRatePreferenceController peakRefreshRatePreferenceController2 =
                        PeakRefreshRatePreferenceController.this;
                peakRefreshRatePreferenceController2.updateState(
                        peakRefreshRatePreferenceController2.mPreference);
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface IDeviceConfigChange {}

    public PeakRefreshRatePreferenceController(Context context, String str) {
        super(context, str);
        this.mHandler = new Handler(context.getMainLooper());
        this.mDeviceConfigDisplaySettings = new DeviceConfigDisplaySettings();
        this.mOnDeviceConfigChange = new AnonymousClass1();
        this.mPeakRefreshRate =
                Math.round(
                        Flags.backUpSmoothDisplayAndForcePeakRefreshRate()
                                ? RefreshRateSettingsUtils.findHighestRefreshRateAmongAllDisplays(
                                        context)
                                : RefreshRateSettingsUtils.findHighestRefreshRateForDefaultDisplay(
                                        context));
        Log.d(TAG, "DEFAULT_REFRESH_RATE : 60.0 mPeakRefreshRate : " + this.mPeakRefreshRate);
    }

    private float getDefaultPeakRefreshRate() {
        this.mDeviceConfigDisplaySettings.getClass();
        float f =
                DeviceConfig.getFloat(
                        "display_manager", "peak_refresh_rate_default", INVALIDATE_REFRESH_RATE);
        Log.d(TAG, "DeviceConfig getDefaultPeakRefreshRate : " + f);
        if (f == INVALIDATE_REFRESH_RATE) {
            f =
                    this.mContext
                            .getResources()
                            .getInteger(R.integer.config_doublePressOnStemPrimaryBehavior);
        }
        Log.d(TAG, "DeviceConfig getDefaultPeakRefreshRate : " + f);
        return f;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        int round = Math.round(this.mPeakRefreshRate);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = findPreference;
        findPreference.setSummary(
                this.mContext.getString(
                        com.android.settings.R.string.peak_refresh_rate_summary,
                        Integer.valueOf(round)));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (!this.mContext
                                .getResources()
                                .getBoolean(com.android.settings.R.bool.config_show_smooth_display)
                        || this.mPeakRefreshRate <= 60.0f)
                ? 3
                : 0;
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
        return com.android.settings.R.string.menu_key_display;
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

    public void injectDeviceConfigDisplaySettings(
            DeviceConfigDisplaySettings deviceConfigDisplaySettings) {
        this.mDeviceConfigDisplaySettings = deviceConfigDisplaySettings;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        float f =
                Settings.System.getFloat(
                        this.mContext.getContentResolver(),
                        "peak_refresh_rate",
                        getDefaultPeakRefreshRate());
        return Math.round(f) == Math.round(this.mPeakRefreshRate) || Float.isInfinite(f);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        DeviceConfigDisplaySettings deviceConfigDisplaySettings = this.mDeviceConfigDisplaySettings;
        deviceConfigDisplaySettings.getClass();
        DeviceConfig.addOnPropertiesChangedListener(
                "display_manager", deviceConfigDisplaySettings, deviceConfigDisplaySettings);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        DeviceConfigDisplaySettings deviceConfigDisplaySettings = this.mDeviceConfigDisplaySettings;
        deviceConfigDisplaySettings.getClass();
        DeviceConfig.removeOnPropertiesChangedListener(deviceConfigDisplaySettings);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        float f =
                Flags.backUpSmoothDisplayAndForcePeakRefreshRate()
                        ? Float.POSITIVE_INFINITY
                        : this.mPeakRefreshRate;
        if (!z) {
            f = 60.0f;
        }
        Log.d(TAG, "setChecked to : " + f);
        return Settings.System.putFloat(this.mContext.getContentResolver(), "peak_refresh_rate", f);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
