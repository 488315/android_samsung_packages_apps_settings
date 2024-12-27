package com.android.settings.bluetooth;

import android.content.Context;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.core.lifecycle.Lifecycle;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothDetailsHearingDeviceController extends BluetoothDetailsController {
    public final List mControllers;
    public final Lifecycle mLifecycle;
    public final LocalBluetoothManager mManager;

    public BluetoothDetailsHearingDeviceController(
            Context context,
            PreferenceFragmentCompat preferenceFragmentCompat,
            LocalBluetoothManager localBluetoothManager,
            CachedBluetoothDevice cachedBluetoothDevice,
            Lifecycle lifecycle) {
        super(context, preferenceFragmentCompat, cachedBluetoothDevice, lifecycle);
        this.mControllers = new ArrayList();
        this.mManager = localBluetoothManager;
        this.mLifecycle = lifecycle;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "hearing_device_group";
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mControllers.stream()
                .anyMatch(new BluetoothDetailsHearingDeviceController$$ExternalSyntheticLambda0());
    }

    public void setSubControllers(
            BluetoothDetailsHearingDeviceSettingsController
                    bluetoothDetailsHearingDeviceSettingsController,
            BluetoothDetailsHearingAidsPresetsController
                    bluetoothDetailsHearingAidsPresetsController) {
        ((ArrayList) this.mControllers).clear();
        ((ArrayList) this.mControllers).add(bluetoothDetailsHearingDeviceSettingsController);
        ((ArrayList) this.mControllers).add(bluetoothDetailsHearingAidsPresetsController);
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void refresh() {}

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void init(PreferenceScreen preferenceScreen) {}
}
