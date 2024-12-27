package com.samsung.android.settingslib.bluetooth;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.android.settings.R;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.CachedBluetoothDeviceManager;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;

import com.samsung.android.knox.ex.peripheral.PeripheralConstants;

import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SppProfile implements LocalBluetoothProfile {
    public final Context mContext;
    public final CachedBluetoothDeviceManager mDeviceManager;
    public final AnonymousClass1 mHandler;
    public final boolean mIsProfileReady = true;
    public final LocalBluetoothAdapter mLocalAdapter;

    public SppProfile(
            Context context,
            LocalBluetoothAdapter localBluetoothAdapter,
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager) {
        Handler handler =
                new Handler(
                        Looper
                                .getMainLooper()) { // from class:
                                                    // com.samsung.android.settingslib.bluetooth.SppProfile.1
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        if (message.what != 0) {
                            return;
                        }
                        SppProfile sppProfile = SppProfile.this;
                        Set<BluetoothDevice> bondedDevices =
                                sppProfile.mLocalAdapter.mAdapter.getBondedDevices();
                        if (bondedDevices == null || bondedDevices.isEmpty()) {
                            return;
                        }
                        for (BluetoothDevice bluetoothDevice : bondedDevices) {
                            if (bluetoothDevice.semIsGearConnected()) {
                                CachedBluetoothDevice findDevice =
                                        sppProfile.mDeviceManager.findDevice(bluetoothDevice);
                                if (findDevice == null) {
                                    Log.w(
                                            "SppProfile",
                                            "SppProfile found new device: "
                                                    + bluetoothDevice.getAddressForLogging());
                                    findDevice =
                                            sppProfile.mDeviceManager.addDevice(bluetoothDevice);
                                }
                                if (findDevice != null) {
                                    Log.d(
                                            "SppProfile",
                                            "Update cached device : " + findDevice.getNameForLog());
                                    findDevice.onProfileStateChanged(sppProfile, 2);
                                    findDevice.refresh();
                                } else {
                                    Log.d("SppProfile", "Bluetooth device is null");
                                }
                            }
                        }
                    }
                };
        this.mContext = context;
        this.mLocalAdapter = localBluetoothAdapter;
        this.mDeviceManager = cachedBluetoothDeviceManager;
        Message message = new Message();
        message.what = 0;
        handler.sendMessageDelayed(message, 300L);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean accessProfileEnabled() {
        return false;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        return bluetoothDevice.semIsGearConnected() ? 2 : 0;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return R.drawable.list_ic_general_device;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getNameResource(BluetoothDevice bluetoothDevice) {
        return R.string.unknown;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getOrdinal() {
        return 200;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getProfileId() {
        return 200;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isEnabled(BluetoothDevice bluetoothDevice) {
        return bluetoothDevice.semIsGearConnected();
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z) {
        if (!z) {
            CachedBluetoothDevice findDevice = this.mDeviceManager.findDevice(bluetoothDevice);
            if (findDevice != null) {
                findDevice.shouldLaunchGM(this.mContext.getPackageName(), false);
                return true;
            }
            Log.d("SppProfile", "disconnect :: Bluetooth device is null");
        }
        return false;
    }

    public final String toString() {
        return PeripheralConstants.ConnectionProfile.SPP;
    }

    public final void finalize() {}
}
