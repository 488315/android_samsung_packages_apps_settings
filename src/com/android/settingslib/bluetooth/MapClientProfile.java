package com.android.settingslib.bluetooth;

import android.R;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothMapClient;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class MapClientProfile implements LocalBluetoothProfile {
    public final CachedBluetoothDeviceManager mDeviceManager;
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothMapClient mService;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MapClientServiceListener implements BluetoothProfile.ServiceListener {
        public MapClientServiceListener() {}

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            BluetoothMapClient bluetoothMapClient = (BluetoothMapClient) bluetoothProfile;
            MapClientProfile.this.mService = bluetoothMapClient;
            List connectedDevices = bluetoothMapClient.getConnectedDevices();
            while (!connectedDevices.isEmpty()) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) connectedDevices.remove(0);
                CachedBluetoothDevice findDevice =
                        MapClientProfile.this.mDeviceManager.findDevice(bluetoothDevice);
                if (findDevice == null) {
                    Log.w("MapClientProfile", "MapProfile found new device: " + bluetoothDevice);
                    MapClientProfile mapClientProfile = MapClientProfile.this;
                    findDevice =
                            mapClientProfile.mDeviceManager.addDevice(
                                    mapClientProfile.mProfileManager, bluetoothDevice);
                }
                findDevice.onProfileStateChanged(MapClientProfile.this, 2);
                findDevice.refresh();
            }
            MapClientProfile.this.mProfileManager.callServiceConnectedListeners();
            MapClientProfile.this.mIsProfileReady = true;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceDisconnected(int i) {
            MapClientProfile.this.mProfileManager.callServiceDisconnectedListeners();
            MapClientProfile.this.mIsProfileReady = false;
        }
    }

    static {
        ParcelUuid[] parcelUuidArr = new ParcelUuid[1];
        ParcelUuid parcelUuid = BluetoothUuid.MAS;
    }

    public MapClientProfile(
            Context context,
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager,
            LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        BluetoothAdapter.getDefaultAdapter()
                .getProfileProxy(context, new MapClientServiceListener(), 18);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean accessProfileEnabled() {
        return true;
    }

    public final void finalize() {
        Log.d("MapClientProfile", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(18, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("MapClientProfile", "Error cleaning up MAP Client proxy", th);
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothMapClient bluetoothMapClient = this.mService;
        if (bluetoothMapClient == null) {
            return 0;
        }
        return bluetoothMapClient.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return R.drawable.ic_signal_cellular_2_5_bar;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getNameResource(BluetoothDevice bluetoothDevice) {
        return com.android.settings.R.string.bluetooth_profile_map;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getOrdinal() {
        return 0;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getProfileId() {
        return 18;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isEnabled(BluetoothDevice bluetoothDevice) {
        BluetoothMapClient bluetoothMapClient = this.mService;
        return bluetoothMapClient != null
                && bluetoothMapClient.getConnectionPolicy(bluetoothDevice) > 0;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isProfileReady() {
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                new StringBuilder("isProfileReady(): "), this.mIsProfileReady, "MapClientProfile");
        return this.mIsProfileReady;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z) {
        BluetoothMapClient bluetoothMapClient = this.mService;
        if (bluetoothMapClient == null) {
            return false;
        }
        return z
                ? bluetoothMapClient.setConnectionPolicy(bluetoothDevice, 100)
                : bluetoothMapClient.setConnectionPolicy(bluetoothDevice, 0);
    }

    public final String toString() {
        return "MAP Client";
    }
}
