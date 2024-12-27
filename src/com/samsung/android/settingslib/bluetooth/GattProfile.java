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

import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class GattProfile implements LocalBluetoothProfile {
    public final Context mContext;
    public final CachedBluetoothDeviceManager mDeviceManager;
    public final AnonymousClass1 mHandler;
    public final boolean mIsProfileReady = true;
    public final LocalBluetoothAdapter mLocalAdapter;

    public GattProfile(
            Context context,
            LocalBluetoothAdapter localBluetoothAdapter,
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager) {
        Handler handler =
                new Handler(
                        Looper
                                .getMainLooper()) { // from class:
                                                    // com.samsung.android.settingslib.bluetooth.GattProfile.1
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        if (message.what != 0) {
                            return;
                        }
                        GattProfile gattProfile = GattProfile.this;
                        Set<BluetoothDevice> bondedDevices =
                                gattProfile.mLocalAdapter.mAdapter.getBondedDevices();
                        if (bondedDevices == null || bondedDevices.isEmpty()) {
                            return;
                        }
                        for (BluetoothDevice bluetoothDevice : bondedDevices) {
                            if (bluetoothDevice.semIsGattConnected()) {
                                CachedBluetoothDevice findDevice =
                                        gattProfile.mDeviceManager.findDevice(bluetoothDevice);
                                if (findDevice == null) {
                                    Log.w(
                                            "GattProfile",
                                            "GattProfile found new device: "
                                                    + bluetoothDevice.getAddressForLogging());
                                    findDevice =
                                            gattProfile.mDeviceManager.addDevice(bluetoothDevice);
                                }
                                if (findDevice != null) {
                                    Log.d(
                                            "GattProfile",
                                            "Update cached device : " + findDevice.getNameForLog());
                                    findDevice.onProfileStateChanged(gattProfile, 2);
                                    findDevice.refresh();
                                } else {
                                    Log.d("GattProfile", "Bluetooth device is null");
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
        return bluetoothDevice.semIsGattConnected() ? 2 : 0;
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
        return 7;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getProfileId() {
        return 7;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isEnabled(BluetoothDevice bluetoothDevice) {
        return bluetoothDevice.semIsGattConnected();
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
            Log.d("GattProfile", "disconnect :: Bluetooth device is null");
        }
        return false;
    }

    public final String toString() {
        return "GATT";
    }

    public final void finalize() {}
}
