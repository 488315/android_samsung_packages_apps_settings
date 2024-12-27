package com.android.settings.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.connecteddevice.DevicePreferenceCallback;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.CachedBluetoothDeviceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SavedBluetoothDeviceUpdater extends BluetoothDeviceUpdater
        implements Preference.OnPreferenceClickListener {
    BluetoothAdapter mBluetoothAdapter;
    public final boolean mShowConnectedDevice;

    public SavedBluetoothDeviceUpdater(
            Context context, DevicePreferenceCallback devicePreferenceCallback, boolean z, int i) {
        super(context, devicePreferenceCallback, i);
        this.mShowConnectedDevice = z;
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override // com.android.settings.bluetooth.BluetoothDeviceUpdater
    public final void forceUpdate() {
        CachedBluetoothDevice findDevice;
        if (!this.mBluetoothAdapter.isEnabled()) {
            removeAllDevicesFromPreference();
            return;
        }
        CachedBluetoothDeviceManager cachedBluetoothDeviceManager =
                this.mLocalManager.mCachedDeviceManager;
        List<BluetoothDevice> mostRecentlyConnectedDevices =
                this.mBluetoothAdapter.getMostRecentlyConnectedDevices();
        Iterator it = new ArrayList(((HashMap) this.mPreferenceMap).keySet()).iterator();
        while (it.hasNext()) {
            BluetoothDevice bluetoothDevice = (BluetoothDevice) it.next();
            if (!mostRecentlyConnectedDevices.contains(bluetoothDevice)
                    && (findDevice = cachedBluetoothDeviceManager.findDevice(bluetoothDevice))
                            != null) {
                removePreference(findDevice);
            }
        }
        for (BluetoothDevice bluetoothDevice2 : mostRecentlyConnectedDevices) {
            CachedBluetoothDevice findDevice2 =
                    cachedBluetoothDeviceManager.findDevice(bluetoothDevice2);
            if (findDevice2 != null
                    && !cachedBluetoothDeviceManager.isSubDevice(bluetoothDevice2)) {
                update(findDevice2);
            }
        }
    }

    @Override // com.android.settings.bluetooth.BluetoothDeviceUpdater
    public final String getLogTag() {
        return "SavedBluetoothDeviceUpdater";
    }

    @Override // com.android.settings.bluetooth.BluetoothDeviceUpdater
    public boolean isFilterMatched(CachedBluetoothDevice cachedBluetoothDevice) {
        BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
        boolean isExclusivelyManagedBluetoothDevice =
                BluetoothUtils.isExclusivelyManagedBluetoothDevice(this.mContext, bluetoothDevice);
        Log.d(
                "SavedBluetoothDeviceUpdater",
                "isFilterMatched() device name : "
                        + cachedBluetoothDevice.getName()
                        + ", is connected : "
                        + bluetoothDevice.isConnected()
                        + ", is profile connected : "
                        + cachedBluetoothDevice.isConnected()
                        + ", is exclusively managed : "
                        + isExclusivelyManagedBluetoothDevice);
        return bluetoothDevice.getBondState() == 12
                && (this.mShowConnectedDevice
                        || (!bluetoothDevice.isConnected()
                                && isDeviceInCachedDevicesList(cachedBluetoothDevice)))
                && !isExclusivelyManagedBluetoothDevice;
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        return false;
    }

    @Override // com.android.settings.bluetooth.BluetoothDeviceUpdater
    public final void update(CachedBluetoothDevice cachedBluetoothDevice) {
        if (!isFilterMatched(cachedBluetoothDevice)) {
            removePreference(cachedBluetoothDevice);
        }
        Log.d("SavedBluetoothDeviceUpdater", "Map : " + this.mPreferenceMap);
    }
}
