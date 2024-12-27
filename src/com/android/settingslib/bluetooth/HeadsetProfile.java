package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.os.ParcelUuid;
import android.os.SystemProperties;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import com.android.settings.R;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class HeadsetProfile implements LocalBluetoothProfile {
    public static final ParcelUuid[] UUIDS = {BluetoothUuid.HSP, BluetoothUuid.HFP};
    public final BluetoothAdapter mBluetoothAdapter;
    public final CachedBluetoothDeviceManager mDeviceManager;
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothHeadset mService;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class HeadsetServiceListener implements BluetoothProfile.ServiceListener {
        public HeadsetServiceListener() {}

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            BluetoothHeadset bluetoothHeadset = (BluetoothHeadset) bluetoothProfile;
            HeadsetProfile.this.mService = bluetoothHeadset;
            if (bluetoothHeadset == null) {
                Log.w("HeadsetProfile", "mService is null");
                return;
            }
            List<BluetoothDevice> connectedDevices = bluetoothHeadset.getConnectedDevices();
            if (connectedDevices != null && !connectedDevices.isEmpty()) {
                for (BluetoothDevice bluetoothDevice : connectedDevices) {
                    CachedBluetoothDevice findDevice =
                            HeadsetProfile.this.mDeviceManager.findDevice(bluetoothDevice);
                    if (findDevice == null) {
                        Log.d(
                                "HeadsetProfile",
                                "HeadsetProfile found new device: "
                                        + bluetoothDevice.getAddressForLogging());
                        HeadsetProfile headsetProfile = HeadsetProfile.this;
                        findDevice =
                                headsetProfile.mDeviceManager.addDevice(
                                        headsetProfile.mProfileManager, bluetoothDevice);
                    }
                    if (findDevice != null) {
                        Log.d(
                                "HeadsetProfile",
                                "Update cached device : " + findDevice.getNameForLog());
                        findDevice.onProfileStateChanged(HeadsetProfile.this, 2);
                        findDevice.refresh();
                    } else {
                        Log.d("HeadsetProfile", "Bluetooth device is null");
                    }
                }
            }
            HeadsetProfile headsetProfile2 = HeadsetProfile.this;
            headsetProfile2.mIsProfileReady = true;
            headsetProfile2.mProfileManager.callServiceConnectedListeners();
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceDisconnected(int i) {
            HeadsetProfile.this.mProfileManager.callServiceDisconnectedListeners();
            HeadsetProfile headsetProfile = HeadsetProfile.this;
            headsetProfile.mIsProfileReady = false;
            headsetProfile.mService = null;
        }
    }

    public HeadsetProfile(
            Context context,
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager,
            LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mBluetoothAdapter = defaultAdapter;
        defaultAdapter.getProfileProxy(context, new HeadsetServiceListener(), 1);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean accessProfileEnabled() {
        return true;
    }

    public final void finalize() {
        Log.d("HeadsetProfile", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(1, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("HeadsetProfile", "Error cleaning up HID proxy", th);
            }
        }
    }

    public final BluetoothDevice getActiveDevice() {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter == null) {
            return null;
        }
        List activeDevices = bluetoothAdapter.getActiveDevices(1);
        if (activeDevices.size() > 0) {
            return (BluetoothDevice) activeDevices.get(0);
        }
        return null;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        if (this.mService == null) {
            return 0;
        }
        if (BluetoothUtils.DEBUG) {
            Log.d("HeadsetProfile", "getConnectionStatus :: device : " + bluetoothDevice.getName());
        }
        List<BluetoothDevice> connectedDevices = this.mService.getConnectedDevices();
        if (connectedDevices != null && !connectedDevices.isEmpty()) {
            Iterator<BluetoothDevice> it = connectedDevices.iterator();
            while (it.hasNext()) {
                if (it.next().equals(bluetoothDevice)) {
                    int connectionState = this.mService.getConnectionState(bluetoothDevice);
                    ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                            connectionState, "getConnectionStatus :: ", "HeadsetProfile");
                    return connectionState;
                }
            }
        }
        Log.d(
                "HeadsetProfile",
                "getConnectionStatus :: BluetoothProfile.STATE_DISCONNECTED (cannot find device)");
        return 0;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return R.drawable.list_ic_mono_headset;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getNameResource(BluetoothDevice bluetoothDevice) {
        return R.string.bluetooth_profile_headset;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getOrdinal() {
        return 1;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getProfileId() {
        return 1;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isEnabled(BluetoothDevice bluetoothDevice) {
        BluetoothHeadset bluetoothHeadset = this.mService;
        return bluetoothHeadset != null
                && bluetoothHeadset.getConnectionPolicy(bluetoothDevice) > 0;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    public final boolean isSwbMenuSupported(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            Log.w("HeadsetProfile", "isSwbMenuSupported :: device is null");
            return false;
        }
        if (SystemProperties.getBoolean("bluetooth.hfp.swb.supported", false)) {
            BluetoothHeadset bluetoothHeadset = this.mService;
            if (bluetoothHeadset == null
                    ? false
                    : bluetoothHeadset.isCodecSupported(bluetoothDevice, 4)) {
                if (!SystemProperties.getBoolean("persist.bluetooth.pts", false)
                        || !SystemProperties.get("persist.bluetooth.forcehfpversion")
                                .equals("VER_1_7")) {
                    return true;
                }
                Log.w(
                        "HeadsetProfile",
                        "isSwbMenuSupported :: swb menu not supported by force hfp version"
                            + " changed");
                return false;
            }
        }
        return false;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z) {
        BluetoothHeadset bluetoothHeadset = this.mService;
        if (bluetoothHeadset == null) {
            return false;
        }
        return z
                ? bluetoothHeadset.setConnectionPolicy(bluetoothDevice, 100)
                : bluetoothHeadset.setConnectionPolicy(bluetoothDevice, 0);
    }

    public final String toString() {
        return "HEADSET";
    }
}
