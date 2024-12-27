package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeAudio;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;

import com.android.settings.R;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class LeAudioProfile implements LocalBluetoothProfile {
    public final BluetoothAdapter mBluetoothAdapter;
    public final CachedBluetoothDeviceManager mDeviceManager;
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothLeAudio mService;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LeAudioServiceListener implements BluetoothProfile.ServiceListener {
        public LeAudioServiceListener() {}

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            Log.d("LeAudioProfile", "Bluetooth service connected");
            BluetoothLeAudio bluetoothLeAudio = (BluetoothLeAudio) bluetoothProfile;
            LeAudioProfile.this.mService = bluetoothLeAudio;
            List<BluetoothDevice> connectedDevices = bluetoothLeAudio.getConnectedDevices();
            while (!connectedDevices.isEmpty()) {
                BluetoothDevice remove = connectedDevices.remove(0);
                CachedBluetoothDevice findDevice =
                        LeAudioProfile.this.mDeviceManager.findDevice(remove);
                if (findDevice == null) {
                    Log.d("LeAudioProfile", "LeAudioProfile found new device: " + remove);
                    findDevice = LeAudioProfile.this.mDeviceManager.addDevice(remove);
                }
                findDevice.onProfileStateChanged(LeAudioProfile.this, 2);
                findDevice.refresh();
            }
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager =
                    LeAudioProfile.this.mDeviceManager;
            synchronized (cachedBluetoothDeviceManager) {
                cachedBluetoothDeviceManager.mHearingAidDeviceManager.updateHearingAidsDevices();
            }
            LeAudioProfile.this.mProfileManager.callServiceConnectedListeners();
            LeAudioProfile.this.mIsProfileReady = true;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceDisconnected(int i) {
            Log.d("LeAudioProfile", "Bluetooth service disconnected");
            LeAudioProfile.this.mProfileManager.callServiceDisconnectedListeners();
            LeAudioProfile.this.mIsProfileReady = false;
        }
    }

    public LeAudioProfile(
            Context context,
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager,
            LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mBluetoothAdapter = defaultAdapter;
        defaultAdapter.getProfileProxy(context, new LeAudioServiceListener(), 22);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean accessProfileEnabled() {
        return true;
    }

    public final void finalize() {
        Log.d("LeAudioProfile", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(22, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("LeAudioProfile", "Error cleaning up LeAudio proxy", th);
            }
        }
    }

    public final List getActiveDevices() {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        return bluetoothAdapter == null ? new ArrayList() : bluetoothAdapter.getActiveDevices(22);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothLeAudio bluetoothLeAudio = this.mService;
        if (bluetoothLeAudio == null) {
            return 0;
        }
        return bluetoothLeAudio.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return R.drawable.sec_bluetooth_2d_low_energy_audio;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getNameResource(BluetoothDevice bluetoothDevice) {
        return R.string.sec_bluetooth_profile_le_audio;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getOrdinal() {
        return 22;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getProfileId() {
        return 22;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isEnabled(BluetoothDevice bluetoothDevice) {
        BluetoothLeAudio bluetoothLeAudio = this.mService;
        return (bluetoothLeAudio == null
                        || bluetoothDevice == null
                        || bluetoothLeAudio.getConnectionPolicy(bluetoothDevice) <= 0)
                ? false
                : true;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z) {
        BluetoothLeAudio bluetoothLeAudio = this.mService;
        if (bluetoothLeAudio == null || bluetoothDevice == null) {
            return false;
        }
        return z
                ? bluetoothLeAudio.setConnectionPolicy(bluetoothDevice, 100)
                : bluetoothLeAudio.setConnectionPolicy(bluetoothDevice, 0);
    }

    public final String toString() {
        return "LE_AUDIO";
    }
}
