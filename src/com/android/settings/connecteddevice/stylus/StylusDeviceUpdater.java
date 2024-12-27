package com.android.settings.connecteddevice.stylus;

import android.content.Context;
import android.hardware.BatteryState;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.util.Log;
import android.view.InputDevice;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.connecteddevice.DevicePreferenceCallback;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.overlay.FeatureFactoryImpl;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class StylusDeviceUpdater
        implements InputManager.InputDeviceListener, InputManager.InputDeviceBatteryListener {
    public Context mContext;
    public final DevicePreferenceCallback mDevicePreferenceCallback;
    public final DashboardFragment mFragment;
    public final InputManager mInputManager;
    public BatteryState mLastBatteryState;
    Integer mLastDetectedUsiId;
    public final SettingsMetricsFeatureProvider mMetricsFeatureProvider;
    public final List mRegisteredBatteryCallbackIds = new ArrayList();
    Preference mUsiPreference;

    public StylusDeviceUpdater(
            Context context,
            DashboardFragment dashboardFragment,
            DevicePreferenceCallback devicePreferenceCallback) {
        this.mFragment = dashboardFragment;
        this.mDevicePreferenceCallback = devicePreferenceCallback;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
        this.mContext = context;
        this.mInputManager = (InputManager) context.getSystemService(InputManager.class);
    }

    public final void forceUpdate() {
        if (!isUsiBatteryValid() || hasConnectedBluetoothStylusDevice()) {
            synchronized (this) {
                Preference preference = this.mUsiPreference;
                if (preference != null) {
                    this.mDevicePreferenceCallback.onDeviceRemoved(preference);
                    this.mUsiPreference = null;
                }
            }
            return;
        }
        synchronized (this) {
            try {
                if (this.mUsiPreference == null) {
                    Preference preference2 = new Preference(this.mContext);
                    this.mUsiPreference = preference2;
                    this.mDevicePreferenceCallback.onDeviceAdded(preference2);
                }
                this.mUsiPreference.setKey("stylus_usi_device");
                this.mUsiPreference.setTitle(R.string.stylus_connected_devices_title);
                this.mUsiPreference.setIcon(R.drawable.ic_stylus);
                this.mUsiPreference.setOnPreferenceClickListener(
                        new Preference
                                .OnPreferenceClickListener() { // from class:
                                                               // com.android.settings.connecteddevice.stylus.StylusDeviceUpdater$$ExternalSyntheticLambda0
                            @Override // androidx.preference.Preference.OnPreferenceClickListener
                            public final boolean onPreferenceClick(Preference preference3) {
                                StylusDeviceUpdater stylusDeviceUpdater = StylusDeviceUpdater.this;
                                stylusDeviceUpdater.mMetricsFeatureProvider.logClickedPreference(
                                        preference3,
                                        stylusDeviceUpdater.mFragment.getMetricsCategory());
                                Bundle bundle = new Bundle();
                                bundle.putInt(
                                        "device_input_id",
                                        stylusDeviceUpdater.mLastDetectedUsiId.intValue());
                                SubSettingLauncher subSettingLauncher =
                                        new SubSettingLauncher(
                                                stylusDeviceUpdater.mFragment.getContext());
                                String name = StylusUsiDetailsFragment.class.getName();
                                SubSettingLauncher.LaunchRequest launchRequest =
                                        subSettingLauncher.mLaunchRequest;
                                launchRequest.mDestinationName = name;
                                launchRequest.mArguments = bundle;
                                launchRequest.mSourceMetricsCategory =
                                        stylusDeviceUpdater.mFragment.getMetricsCategory();
                                subSettingLauncher.launch();
                                return true;
                            }
                        });
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public Preference getPreference() {
        return this.mUsiPreference;
    }

    public boolean hasConnectedBluetoothStylusDevice() {
        for (int i : this.mInputManager.getInputDeviceIds()) {
            InputDevice inputDevice = this.mInputManager.getInputDevice(i);
            if (inputDevice != null
                    && inputDevice.supportsSource(16386)
                    && this.mInputManager.getInputDeviceBluetoothAddress(i) != null) {
                return true;
            }
        }
        return false;
    }

    public boolean isUsiBatteryValid() {
        BatteryState batteryState = this.mLastBatteryState;
        return batteryState != null
                && batteryState.isPresent()
                && this.mLastBatteryState.getCapacity() > 0.0f;
    }

    public final void onBatteryStateChanged(int i, long j, BatteryState batteryState) {
        this.mLastBatteryState = batteryState;
        this.mLastDetectedUsiId = Integer.valueOf(i);
        forceUpdate();
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceAdded(int i) {
        InputDevice inputDevice = this.mInputManager.getInputDevice(i);
        if (inputDevice == null) {
            return;
        }
        if (inputDevice.supportsSource(16386) && !inputDevice.isExternal()) {
            try {
                this.mInputManager.addInputDeviceBatteryListener(
                        i, this.mContext.getMainExecutor(), this);
                ((ArrayList) this.mRegisteredBatteryCallbackIds).add(Integer.valueOf(i));
            } catch (IllegalArgumentException e) {
                Log.e("StylusDeviceUpdater", e.getMessage());
            }
        }
        forceUpdate();
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceChanged(int i) {
        InputDevice inputDevice = this.mInputManager.getInputDevice(i);
        if (inputDevice != null && inputDevice.supportsSource(16386)) {
            forceUpdate();
        }
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceRemoved(int i) {
        Log.d("StylusDeviceUpdater", String.format("Input device removed %d", Integer.valueOf(i)));
        forceUpdate();
    }
}
