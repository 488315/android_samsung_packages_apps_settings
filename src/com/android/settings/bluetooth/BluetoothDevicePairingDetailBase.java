package com.android.settings.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceCategory;

import com.android.settings.R;
import com.android.settings.accessibility.AccessibilityStatsLogUtils;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.HearingAidStatsLogUtils;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BluetoothDevicePairingDetailBase extends DeviceListPreferenceFragment {
    protected BluetoothProgressCategory mAvailableDevicesCategory;
    public boolean mInitialScanStarted;

    @Override // com.android.settings.bluetooth.DeviceListPreferenceFragment
    public final void enableScanning() {
        if (!this.mInitialScanStarted) {
            if (this.mAvailableDevicesCategory != null) {
                this.devicePreferenceMap.clear();
                PreferenceCategory preferenceCategory = this.mDeviceListGroup;
                Intrinsics.checkNotNull(preferenceCategory);
                preferenceCategory.removeAll();
            }
            this.mLocalManager.mCachedDeviceManager.clearNonBondedDevices();
            this.mInitialScanStarted = true;
        }
        super.enableScanning();
    }

    @Override // com.android.settings.bluetooth.DeviceListPreferenceFragment
    public void initPreferencesFromPreferenceScreen() {
        this.mAvailableDevicesCategory =
                (BluetoothProgressCategory) findPreference("available_devices");
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onBluetoothStateChanged(int i) {
        updateContent(i);
        if (i == 12) {
            showBluetoothTurnedOnToast();
        }
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceBondStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        BluetoothDevice bluetoothDevice;
        if (i == 12) {
            finish();
            return;
        }
        if (i == 11) {
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            SettingsMetricsFeatureProvider metricsFeatureProvider =
                    featureFactoryImpl.getMetricsFeatureProvider();
            FragmentActivity activity = getActivity();
            metricsFeatureProvider.getClass();
            HearingAidStatsLogUtils.sDeviceAddressToBondEntryMap.put(
                    cachedBluetoothDevice.mDevice.getAddress(),
                    Integer.valueOf(
                            AccessibilityStatsLogUtils.convertToHearingAidInfoBondEntry(
                                    MetricsFeatureProvider.getAttribution(activity))));
        }
        BluetoothDevice bluetoothDevice2 = this.mSelectedDevice;
        if (bluetoothDevice2 == null
                || (bluetoothDevice = cachedBluetoothDevice.mDevice) == null
                || !bluetoothDevice2.equals(bluetoothDevice)
                || i != 10) {
            return;
        }
        enableScanning();
    }

    @Override // com.android.settings.bluetooth.DeviceListPreferenceFragment
    public final void onDevicePreferenceClick(BluetoothDevicePreference bluetoothDevicePreference) {
        disableScanning();
        super.onDevicePreferenceClick(bluetoothDevicePreference);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onProfileConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i, int i2) {
        if (cachedBluetoothDevice.isConnected()) {
            BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
            if (bluetoothDevice == null
                    || !((ArrayList) this.mSelectedList).contains(bluetoothDevice)) {
                onDeviceDeleted(cachedBluetoothDevice);
            } else {
                finish();
            }
        }
    }

    @Override // com.android.settings.bluetooth.DeviceListPreferenceFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        if (this.mLocalManager == null) {
            Log.e(getTAG(), "Bluetooth is not supported on this device");
        } else {
            updateBluetooth();
        }
    }

    @Override // com.android.settings.bluetooth.DeviceListPreferenceFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        if (this.mLocalManager == null) {
            Log.e(getTAG(), "Bluetooth is not supported on this device");
        } else {
            disableScanning();
        }
    }

    @Override // com.android.settings.bluetooth.DeviceListPreferenceFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        this.mInitialScanStarted = false;
        super.onViewCreated(view, bundle);
    }

    public void showBluetoothTurnedOnToast() {
        Toast.makeText(getContext(), R.string.connected_device_bluetooth_turned_on_toast, 0).show();
    }

    public void updateBluetooth() {
        if (this.mBluetoothAdapter.isEnabled()) {
            updateContent(this.mBluetoothAdapter.getState());
        } else {
            this.mBluetoothAdapter.enable();
        }
    }

    public void updateContent(int i) {
        if (i == 10) {
            finish();
        } else {
            if (i != 12) {
                return;
            }
            this.mBluetoothAdapter.enable();
            enableScanning();
        }
    }
}
