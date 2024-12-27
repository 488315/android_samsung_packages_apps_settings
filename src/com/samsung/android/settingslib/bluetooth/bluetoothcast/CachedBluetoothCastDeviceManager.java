package com.samsung.android.settingslib.bluetooth.bluetoothcast;

import android.content.Context;
import android.util.Log;

import com.android.settingslib.bluetooth.LocalBluetoothManager;

import com.samsung.android.bluetooth.SemBluetoothCastDevice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class CachedBluetoothCastDeviceManager {
    public final LocalBluetoothManager mBtManager;
    public final List mCachedCastDevices = new ArrayList();
    public final Context mContext;

    public CachedBluetoothCastDeviceManager(
            Context context, LocalBluetoothManager localBluetoothManager) {
        this.mContext = context;
        this.mBtManager = localBluetoothManager;
    }

    public final CachedBluetoothCastDevice addCastDevice(
            LocalBluetoothCastProfileManager localBluetoothCastProfileManager,
            SemBluetoothCastDevice semBluetoothCastDevice) {
        CachedBluetoothCastDevice cachedBluetoothCastDevice =
                new CachedBluetoothCastDevice(
                        this.mContext, localBluetoothCastProfileManager, semBluetoothCastDevice);
        synchronized (this) {
            try {
                Log.d(
                        "CachedBluetoothCastDeviceManager",
                        "addCastDevice :: " + cachedBluetoothCastDevice.getName());
                if (((ArrayList) this.mCachedCastDevices).contains(cachedBluetoothCastDevice)) {
                    Log.d(
                            "CachedBluetoothCastDeviceManager",
                            "addCastDevice :: newDevice is added already");
                    return findCastDevice(semBluetoothCastDevice);
                }
                ((ArrayList) this.mCachedCastDevices).add(cachedBluetoothCastDevice);
                cachedBluetoothCastDevice.mSequence =
                        ((ArrayList) this.mCachedCastDevices).indexOf(cachedBluetoothCastDevice);
                BluetoothCastEventManager bluetoothCastEventManager =
                        this.mBtManager.mCastEventManager;
                synchronized (bluetoothCastEventManager.mCallbacks) {
                    try {
                        Iterator it = ((ArrayList) bluetoothCastEventManager.mCallbacks).iterator();
                        while (it.hasNext()) {
                            ((BluetoothCastCallback) it.next())
                                    .onCastDeviceAdded(cachedBluetoothCastDevice);
                        }
                    } finally {
                    }
                }
                return cachedBluetoothCastDevice;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final synchronized void clearAllCastedDevices() {
        try {
            Log.d("CachedBluetoothCastDeviceManager", "clearAllCastedDevices");
            for (int size = ((ArrayList) this.mCachedCastDevices).size() - 1; size >= 0; size--) {
                ((ArrayList) this.mCachedCastDevices)
                        .remove(
                                (CachedBluetoothCastDevice)
                                        ((ArrayList) this.mCachedCastDevices).get(size));
            }
            synchronized (this) {
                for (int size2 = ((ArrayList) this.mCachedCastDevices).size() - 1;
                        size2 >= 0;
                        size2--) {
                    ((CachedBluetoothCastDevice) ((ArrayList) this.mCachedCastDevices).get(size2))
                                    .mSequence =
                            size2;
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized CachedBluetoothCastDevice findCastDevice(
            SemBluetoothCastDevice semBluetoothCastDevice) {
        Iterator it = ((ArrayList) this.mCachedCastDevices).iterator();
        while (it.hasNext()) {
            CachedBluetoothCastDevice cachedBluetoothCastDevice =
                    (CachedBluetoothCastDevice) it.next();
            if (cachedBluetoothCastDevice.mCastDevice.equals(semBluetoothCastDevice)) {
                return cachedBluetoothCastDevice;
            }
        }
        return null;
    }

    public final synchronized Collection getCachedCastDevicesCopy() {
        return new ArrayList(this.mCachedCastDevices);
    }
}
