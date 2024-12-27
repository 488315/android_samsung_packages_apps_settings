package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHidHost;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import com.android.settings.R;

import com.samsung.android.knox.ex.peripheral.PeripheralConstants;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class HidProfile implements LocalBluetoothProfile {
    public final CachedBluetoothDeviceManager mDeviceManager;
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothHidHost mService;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class HidHostServiceListener implements BluetoothProfile.ServiceListener {
        public HidHostServiceListener() {}

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            BluetoothHidHost bluetoothHidHost = (BluetoothHidHost) bluetoothProfile;
            HidProfile.this.mService = bluetoothHidHost;
            List<BluetoothDevice> connectedDevices = bluetoothHidHost.getConnectedDevices();
            if (!connectedDevices.isEmpty()) {
                for (BluetoothDevice bluetoothDevice : connectedDevices) {
                    CachedBluetoothDevice findDevice =
                            HidProfile.this.mDeviceManager.findDevice(bluetoothDevice);
                    if (findDevice == null) {
                        Log.w(
                                "HidProfile",
                                "HidProfile found new device: "
                                        + bluetoothDevice.getAddressForLogging());
                        HidProfile hidProfile = HidProfile.this;
                        findDevice =
                                hidProfile.mDeviceManager.addDevice(
                                        hidProfile.mProfileManager, bluetoothDevice);
                    }
                    if (findDevice != null) {
                        try {
                            Log.d(
                                    "HidProfile",
                                    "Update cached device : " + findDevice.getNameForLog());
                            findDevice.onProfileStateChanged(HidProfile.this, 2);
                            findDevice.refresh();
                        } catch (NullPointerException unused) {
                            Log.d("HidProfile", "Handle NullPointerException!!!");
                        }
                    } else {
                        Log.d("HidProfile", "Bluetooth device is null");
                    }
                }
            }
            HidProfile hidProfile2 = HidProfile.this;
            hidProfile2.mIsProfileReady = true;
            hidProfile2.mProfileManager.callServiceConnectedListeners();
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceDisconnected(int i) {
            Log.d("HidProfile", "Bluetooth service disconnected");
            HidProfile hidProfile = HidProfile.this;
            hidProfile.mIsProfileReady = false;
            hidProfile.mProfileManager.callServiceDisconnectedListeners();
            HidProfile.this.mService = null;
        }
    }

    public HidProfile(
            Context context,
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager,
            LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        BluetoothAdapter.getDefaultAdapter()
                .getProfileProxy(context, new HidHostServiceListener(), 4);
    }

    public static int getHidClassDrawable(BluetoothClass bluetoothClass) {
        return bluetoothClass.semGetPeripheralMinorClass() == 1344
                ? R.drawable.sec_bluetooth_2d_keyboard
                : bluetoothClass.semGetPeripheralMinorClass() == 1472
                        ? R.drawable.sec_bluetooth_2d_input
                        : bluetoothClass.semGetPeripheralMinorClass() == 1408
                                ? R.drawable.sec_bluetooth_2d_mouse
                                : (bluetoothClass.semGetPeripheralMinorSubClass() == 1284
                                                || bluetoothClass.semGetPeripheralMinorSubClass()
                                                        == 1288
                                                || bluetoothClass.semGetPeripheralMinorSubClass()
                                                        == 1292)
                                        ? R.drawable.sec_bluetooth_2d_game
                                        : R.drawable.sec_bluetooth_2d_sound_balance_sound_accessory;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean accessProfileEnabled() {
        return true;
    }

    public final void finalize() {
        Log.d("HidProfile", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(4, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("HidProfile", "Error cleaning up HID proxy", th);
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothHidHost bluetoothHidHost = this.mService;
        if (bluetoothHidHost == null) {
            return 0;
        }
        List connectedDevices = bluetoothHidHost.getConnectedDevices();
        if (!connectedDevices.isEmpty()) {
            Iterator it = connectedDevices.iterator();
            while (it.hasNext()) {
                if (((BluetoothDevice) it.next()).equals(bluetoothDevice)) {
                    int connectionState = this.mService.getConnectionState(bluetoothDevice);
                    ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                            connectionState, "getConnectionStatus :: ", "HidProfile");
                    return connectionState;
                }
            }
        }
        Log.d(
                "HidProfile",
                "getConnectionStatus :: BluetoothProfile.STATE_DISCONNECTED (cannot find device)");
        return 0;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return bluetoothClass == null
                ? R.drawable.sec_bluetooth_2d_sound_balance_sound_accessory
                : getHidClassDrawable(bluetoothClass);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getNameResource(BluetoothDevice bluetoothDevice) {
        BluetoothClass bluetoothClass = bluetoothDevice.getBluetoothClass();
        return bluetoothClass != null
                ? (bluetoothClass.semGetPeripheralMinorClass() == 1344
                                || bluetoothClass.semGetPeripheralMinorClass() == 1472)
                        ? R.string.bluetooth_profile_textinput
                        : R.string.bluetooth_profile_hid
                : R.string.bluetooth_profile_hid;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getOrdinal() {
        return 4;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getProfileId() {
        return 4;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isEnabled(BluetoothDevice bluetoothDevice) {
        BluetoothHidHost bluetoothHidHost = this.mService;
        return (bluetoothHidHost == null
                        || bluetoothHidHost.getConnectionPolicy(bluetoothDevice) == 0)
                ? false
                : true;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z) {
        BluetoothHidHost bluetoothHidHost = this.mService;
        if (bluetoothHidHost == null) {
            return false;
        }
        return z
                ? bluetoothHidHost.setConnectionPolicy(bluetoothDevice, 100)
                : bluetoothHidHost.setConnectionPolicy(bluetoothDevice, 0);
    }

    public final String toString() {
        return PeripheralConstants.ConnectionProfile.HID;
    }
}
