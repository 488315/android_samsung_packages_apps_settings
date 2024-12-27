package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothPan;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PanProfile implements LocalBluetoothProfile {
    public final CachedBluetoothDeviceManager mDeviceManager;
    public final HashMap mDeviceRoleMap = new HashMap();
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothPan mService;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PanServiceListener implements BluetoothProfile.ServiceListener {
        public PanServiceListener() {}

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            BluetoothPan bluetoothPan = (BluetoothPan) bluetoothProfile;
            PanProfile.this.mService = bluetoothPan;
            List<BluetoothDevice> connectedDevices = bluetoothPan.getConnectedDevices();
            if (!connectedDevices.isEmpty()) {
                for (BluetoothDevice bluetoothDevice : connectedDevices) {
                    CachedBluetoothDevice findDevice =
                            PanProfile.this.mDeviceManager.findDevice(bluetoothDevice);
                    if (findDevice == null) {
                        Log.w(
                                "PanProfile",
                                "PanProfile found new device: "
                                        + bluetoothDevice.getAddressForLogging());
                        PanProfile panProfile = PanProfile.this;
                        findDevice =
                                panProfile.mDeviceManager.addDevice(
                                        panProfile.mProfileManager, bluetoothDevice);
                    }
                    if (findDevice != null) {
                        Log.d("PanProfile", "Update cached device: " + findDevice.getNameForLog());
                        findDevice.onProfileStateChanged(PanProfile.this, 2);
                        findDevice.refresh();
                    } else {
                        Log.d("PanProfile", "Bluetooth device is null");
                    }
                }
            }
            PanProfile panProfile2 = PanProfile.this;
            panProfile2.mIsProfileReady = true;
            panProfile2.mProfileManager.callServiceConnectedListeners();
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceDisconnected(int i) {
            PanProfile panProfile = PanProfile.this;
            panProfile.mIsProfileReady = false;
            panProfile.mProfileManager.callServiceDisconnectedListeners();
            PanProfile.this.mService = null;
        }
    }

    static {
        new HashSet<String>() { // from class: com.android.settingslib.bluetooth.PanProfile.1
            {
                add("PANNAP");
                add("PANU");
            }
        };
    }

    public PanProfile(
            Context context,
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager,
            LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, new PanServiceListener(), 5);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean accessProfileEnabled() {
        return true;
    }

    public final void finalize() {
        Log.d("PanProfile", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(5, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("PanProfile", "Error cleaning up PAN proxy", th);
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothPan bluetoothPan = this.mService;
        if (bluetoothPan == null) {
            return 0;
        }
        return bluetoothPan.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return R.drawable.sec_bluetooth_2d_bluetooth_pan_network;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getNameResource(BluetoothDevice bluetoothDevice) {
        return isLocalRoleNap(bluetoothDevice)
                ? R.string.bluetooth_profile_pan_nap
                : R.string.bluetooth_profile_pan;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getOrdinal() {
        return 5;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getProfileId() {
        return 5;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isEnabled(BluetoothDevice bluetoothDevice) {
        return true;
    }

    public final boolean isLocalRoleNap(final BluetoothDevice bluetoothDevice) {
        if (this.mDeviceRoleMap.containsKey(bluetoothDevice)) {
            return ((Integer) this.mDeviceRoleMap.get(bluetoothDevice)).intValue() == 1;
        }
        BluetoothPan bluetoothPan = this.mService;
        if (!(bluetoothPan == null ? Collections.emptyList() : bluetoothPan.getConnectedDevices())
                .stream()
                        .anyMatch(
                                new Predicate() { // from class:
                                                  // com.android.settingslib.bluetooth.PanProfile$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        return ((BluetoothDevice) obj)
                                                .getAddress()
                                                .equals(bluetoothDevice.getAddress());
                                    }
                                })) {
            return false;
        }
        if (isNapEnabled()) {
            setLocalRole(bluetoothDevice, 1);
        } else {
            setLocalRole(bluetoothDevice, 2);
        }
        return ((Integer) this.mDeviceRoleMap.get(bluetoothDevice)).intValue() == 1;
    }

    public final boolean isNapEnabled() {
        BluetoothPan bluetoothPan = this.mService;
        if (bluetoothPan == null) {
            return false;
        }
        return bluetoothPan.isTetheringOn();
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z) {
        if (this.mService == null) {
            return false;
        }
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m("setEnabled: ", "PanProfile", z);
        if (!z) {
            return this.mService.setConnectionPolicy(bluetoothDevice, 0);
        }
        List connectedDevices = this.mService.getConnectedDevices();
        if (connectedDevices != null) {
            Iterator it = connectedDevices.iterator();
            while (it.hasNext()) {
                this.mService.setConnectionPolicy((BluetoothDevice) it.next(), 0);
            }
        }
        return this.mService.setConnectionPolicy(bluetoothDevice, 100);
    }

    public final void setLocalRole(BluetoothDevice bluetoothDevice, int i) {
        if (getConnectionStatus(bluetoothDevice) == 0) {
            this.mDeviceRoleMap.remove(bluetoothDevice);
        } else {
            this.mDeviceRoleMap.put(bluetoothDevice, Integer.valueOf(i));
        }
    }

    public final String toString() {
        return "PAN";
    }
}
