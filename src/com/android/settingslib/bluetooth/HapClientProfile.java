package com.android.settingslib.bluetooth;

import android.R;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHapClient;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class HapClientProfile implements LocalBluetoothProfile {
    public final BluetoothAdapter mBluetoothAdapter;
    public final CachedBluetoothDeviceManager mDeviceManager;
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothHapClient mService;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class HapClientServiceListener implements BluetoothProfile.ServiceListener {
        public HapClientServiceListener() {}

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            BluetoothHapClient bluetoothHapClient = (BluetoothHapClient) bluetoothProfile;
            HapClientProfile.this.mService = bluetoothHapClient;
            List connectedDevices = bluetoothHapClient.getConnectedDevices();
            while (!connectedDevices.isEmpty()) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) connectedDevices.remove(0);
                CachedBluetoothDevice findDevice =
                        HapClientProfile.this.mDeviceManager.findDevice(bluetoothDevice);
                if (findDevice == null) {
                    Log.w(
                            "HapClientProfile",
                            "HapClient profile found new device: " + bluetoothDevice);
                    findDevice = HapClientProfile.this.mDeviceManager.addDevice(bluetoothDevice);
                }
                findDevice.onProfileStateChanged(HapClientProfile.this, 2);
                findDevice.refresh();
            }
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager =
                    HapClientProfile.this.mDeviceManager;
            synchronized (cachedBluetoothDeviceManager) {
                cachedBluetoothDeviceManager.mHearingAidDeviceManager.updateHearingAidsDevices();
            }
            HapClientProfile hapClientProfile = HapClientProfile.this;
            hapClientProfile.mIsProfileReady = true;
            hapClientProfile.mProfileManager.callServiceConnectedListeners();
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceDisconnected(int i) {
            HapClientProfile hapClientProfile = HapClientProfile.this;
            hapClientProfile.mIsProfileReady = false;
            hapClientProfile.mProfileManager.callServiceDisconnectedListeners();
        }
    }

    public HapClientProfile(
            Context context,
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager,
            LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        BluetoothManager bluetoothManager =
                (BluetoothManager) context.getSystemService(BluetoothManager.class);
        if (bluetoothManager == null) {
            this.mBluetoothAdapter = null;
            return;
        }
        BluetoothAdapter adapter = bluetoothManager.getAdapter();
        this.mBluetoothAdapter = adapter;
        adapter.getProfileProxy(context, new HapClientServiceListener(), 28);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean accessProfileEnabled() {
        return false;
    }

    public final void finalize() {
        Log.d("HapClientProfile", "finalize()");
        BluetoothProfile bluetoothProfile = this.mService;
        if (bluetoothProfile != null) {
            try {
                this.mBluetoothAdapter.closeProfileProxy(28, bluetoothProfile);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("HapClientProfile", "Error cleaning up HAP Client proxy", th);
            }
        }
    }

    public final int getActivePresetIndex(BluetoothDevice bluetoothDevice) {
        BluetoothHapClient bluetoothHapClient = this.mService;
        if (bluetoothHapClient != null) {
            return bluetoothHapClient.getActivePresetIndex(bluetoothDevice);
        }
        Log.w("HapClientProfile", "Proxy not attached to service. Cannot get active preset index.");
        return 0;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothHapClient bluetoothHapClient = this.mService;
        if (bluetoothHapClient == null) {
            return 0;
        }
        return bluetoothHapClient.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return R.drawable.ic_clear_mtrl_alpha;
    }

    public final int getHapGroup(BluetoothDevice bluetoothDevice) {
        BluetoothHapClient bluetoothHapClient = this.mService;
        if (bluetoothHapClient != null) {
            return bluetoothHapClient.getHapGroup(bluetoothDevice);
        }
        Log.w("HapClientProfile", "Proxy not attached to service. Cannot get hap group.");
        return -1;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getNameResource(BluetoothDevice bluetoothDevice) {
        return com.android.settings.R.string.bluetooth_profile_hearing_aid;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getOrdinal() {
        return 1;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getProfileId() {
        return 28;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isEnabled(BluetoothDevice bluetoothDevice) {
        BluetoothHapClient bluetoothHapClient = this.mService;
        return (bluetoothHapClient == null
                        || bluetoothDevice == null
                        || bluetoothHapClient.getConnectionPolicy(bluetoothDevice) <= 0)
                ? false
                : true;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    public final void selectPreset(BluetoothDevice bluetoothDevice, int i) {
        BluetoothHapClient bluetoothHapClient = this.mService;
        if (bluetoothHapClient == null) {
            Log.w("HapClientProfile", "Proxy not attached to service. Cannot select preset.");
        } else {
            bluetoothHapClient.selectPreset(bluetoothDevice, i);
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z) {
        BluetoothHapClient bluetoothHapClient = this.mService;
        if (bluetoothHapClient == null || bluetoothDevice == null) {
            return false;
        }
        if (!z) {
            return bluetoothHapClient.setConnectionPolicy(bluetoothDevice, 0);
        }
        if (bluetoothHapClient.getConnectionPolicy(bluetoothDevice) < 100) {
            return this.mService.setConnectionPolicy(bluetoothDevice, 100);
        }
        return false;
    }

    public final String toString() {
        return "HapClient";
    }
}
