package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothVolumeControl;
import android.content.Context;
import android.util.Log;

import com.android.settings.R;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class VolumeControlProfile implements LocalBluetoothProfile {
    public final CachedBluetoothDeviceManager mDeviceManager;
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothVolumeControl mService;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class VolumeControlProfileServiceListener
            implements BluetoothProfile.ServiceListener {
        public VolumeControlProfileServiceListener() {}

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            Log.d("VolumeControlProfile", "Bluetooth service connected");
            BluetoothVolumeControl bluetoothVolumeControl =
                    (BluetoothVolumeControl) bluetoothProfile;
            VolumeControlProfile.this.mService = bluetoothVolumeControl;
            List connectedDevices = bluetoothVolumeControl.getConnectedDevices();
            while (!connectedDevices.isEmpty()) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) connectedDevices.remove(0);
                CachedBluetoothDevice findDevice =
                        VolumeControlProfile.this.mDeviceManager.findDevice(bluetoothDevice);
                if (findDevice == null) {
                    Log.d(
                            "VolumeControlProfile",
                            "VolumeControlProfile found new device: " + bluetoothDevice);
                    VolumeControlProfile volumeControlProfile = VolumeControlProfile.this;
                    findDevice =
                            volumeControlProfile.mDeviceManager.addDevice(
                                    volumeControlProfile.mProfileManager, bluetoothDevice);
                }
                if (findDevice != null) {
                    findDevice.onProfileStateChanged(VolumeControlProfile.this, 2);
                    findDevice.refresh();
                }
            }
            VolumeControlProfile.this.mProfileManager.callServiceConnectedListeners();
            VolumeControlProfile.this.mIsProfileReady = true;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceDisconnected(int i) {
            Log.d("VolumeControlProfile", "Bluetooth service disconnected");
            VolumeControlProfile.this.mProfileManager.callServiceDisconnectedListeners();
            VolumeControlProfile volumeControlProfile = VolumeControlProfile.this;
            volumeControlProfile.mIsProfileReady = false;
            volumeControlProfile.mService = null;
        }
    }

    public VolumeControlProfile(
            Context context,
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager,
            LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        BluetoothAdapter.getDefaultAdapter()
                .getProfileProxy(context, new VolumeControlProfileServiceListener(), 23);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean accessProfileEnabled() {
        return false;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothVolumeControl bluetoothVolumeControl = this.mService;
        if (bluetoothVolumeControl == null) {
            return 0;
        }
        return bluetoothVolumeControl.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return 0;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getNameResource(BluetoothDevice bluetoothDevice) {
        return R.string.summary_empty;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getOrdinal() {
        return 1;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getProfileId() {
        return 23;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isEnabled(BluetoothDevice bluetoothDevice) {
        BluetoothVolumeControl bluetoothVolumeControl = this.mService;
        return (bluetoothVolumeControl == null
                        || bluetoothDevice == null
                        || bluetoothVolumeControl.getConnectionPolicy(bluetoothDevice) <= 0)
                ? false
                : true;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z) {
        if (this.mService == null || bluetoothDevice == null) {
            return false;
        }
        Log.d("VolumeControlProfile", bluetoothDevice.getAnonymizedAddress() + " setEnabled: " + z);
        return z
                ? this.mService.setConnectionPolicy(bluetoothDevice, 100)
                : this.mService.setConnectionPolicy(bluetoothDevice, 0);
    }

    public final String toString() {
        return "VCP";
    }
}
