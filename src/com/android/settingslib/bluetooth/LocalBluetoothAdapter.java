package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class LocalBluetoothAdapter {
    public static LocalBluetoothAdapter sInstance;
    public final BluetoothAdapter mAdapter;
    public long mLastScan;
    public LocalBluetoothProfileManager mProfileManager;
    public int mState = Integer.MIN_VALUE;

    public LocalBluetoothAdapter(BluetoothAdapter bluetoothAdapter) {
        this.mAdapter = bluetoothAdapter;
    }

    public static synchronized LocalBluetoothAdapter getInstance() {
        LocalBluetoothAdapter localBluetoothAdapter;
        BluetoothAdapter defaultAdapter;
        synchronized (LocalBluetoothAdapter.class) {
            try {
                if (sInstance == null
                        && (defaultAdapter = BluetoothAdapter.getDefaultAdapter()) != null) {
                    sInstance = new LocalBluetoothAdapter(defaultAdapter);
                }
                localBluetoothAdapter = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return localBluetoothAdapter;
    }

    public final void cancelDiscovery() {
        this.mAdapter.cancelDiscovery();
    }

    public final synchronized int getBluetoothState() {
        if (this.mAdapter.getState() != this.mState) {
            setBluetoothStateInt(this.mAdapter.getState());
        }
        return this.mState;
    }

    public final boolean setBluetoothEnabled(boolean z) {
        boolean enable = z ? this.mAdapter.enable() : this.mAdapter.disable();
        if (enable) {
            setBluetoothStateInt(z ? 11 : 13);
        } else if (this.mAdapter.getState() != this.mState) {
            setBluetoothStateInt(this.mAdapter.getState());
        }
        return enable;
    }

    public final synchronized void setBluetoothStateInt(int i) {
        LocalBluetoothProfileManager localBluetoothProfileManager;
        synchronized (this) {
            if (this.mState == i) {
                return;
            }
            this.mState = i;
            if (i == 12 && (localBluetoothProfileManager = this.mProfileManager) != null) {
                localBluetoothProfileManager.updateLocalProfiles();
                BluetoothEventManager bluetoothEventManager =
                        localBluetoothProfileManager.mEventManager;
                bluetoothEventManager.readRestoredDevices();
                bluetoothEventManager.readPairedDevices();
            }
        }
    }

    public final boolean startScanning(boolean z) {
        BluetoothA2dp bluetoothA2dp;
        StringBuilder m =
                RowView$$ExternalSyntheticOutline0.m("startScanning :: ", ", isDiscovering : ", z);
        m.append(this.mAdapter.isDiscovering());
        Log.d("LocalBluetoothAdapter", m.toString());
        if (!this.mAdapter.isDiscovering()) {
            if (!z) {
                if (this.mLastScan + 300000 > System.currentTimeMillis()) {
                    Log.w(
                            "LocalBluetoothAdapter",
                            "startScanning:mLastScan="
                                    + this.mLastScan
                                    + ", EXPIRATION=300000, currentTime="
                                    + System.currentTimeMillis());
                    return false;
                }
                A2dpProfile a2dpProfile = this.mProfileManager.mA2dpProfile;
                if (a2dpProfile != null && (bluetoothA2dp = a2dpProfile.mService) != null) {
                    List<BluetoothDevice> connectedDevices = bluetoothA2dp.getConnectedDevices();
                    if (!connectedDevices.isEmpty()
                            && a2dpProfile.mService.isA2dpPlaying(connectedDevices.get(0))) {
                        Log.w("LocalBluetoothAdapter", "startScanning ::A2dp is playing");
                        return false;
                    }
                }
                this.mProfileManager.getClass();
            }
            if (this.mAdapter.startDiscovery()) {
                this.mLastScan = System.currentTimeMillis();
                Log.d(
                        "LocalBluetoothAdapter",
                        "startScanning :: done! mLastScan=" + this.mLastScan);
                return true;
            }
        }
        return false;
    }

    public final void stopScanning() {
        if (this.mAdapter.isDiscovering()) {
            this.mAdapter.cancelDiscovery();
        }
    }
}
