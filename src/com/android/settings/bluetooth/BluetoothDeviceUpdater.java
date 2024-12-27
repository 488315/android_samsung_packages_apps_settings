package com.android.settings.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.settings.connecteddevice.DevicePreferenceCallback;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BluetoothDeviceUpdater
        implements BluetoothCallback, LocalBluetoothProfileManager.ServiceListener {
    public static final boolean DBG = Log.isLoggable("BluetoothDeviceUpdater", 3);
    public final Context mContext;
    public final DevicePreferenceCallback mDevicePreferenceCallback;
    protected LocalBluetoothManager mLocalManager;
    public final int mMetricsCategory;
    public final SettingsMetricsFeatureProvider mMetricsFeatureProvider;
    public Context mPrefContext;
    public final Map mPreferenceMap;

    public BluetoothDeviceUpdater(
            Context context,
            DevicePreferenceCallback devicePreferenceCallback,
            LocalBluetoothManager localBluetoothManager,
            int i) {
        this.mContext = context;
        this.mDevicePreferenceCallback = devicePreferenceCallback;
        this.mPreferenceMap = new HashMap();
        this.mLocalManager = localBluetoothManager;
        this.mMetricsCategory = i;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    public void forceUpdate() {
        if (this.mLocalManager == null) {
            Log.e(getLogTag(), "forceUpdate() Bluetooth is not supported on this device");
        } else {
            if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                removeAllDevicesFromPreference();
                return;
            }
            Iterator it =
                    ((ArrayList) this.mLocalManager.mCachedDeviceManager.getCachedDevicesCopy())
                            .iterator();
            while (it.hasNext()) {
                update((CachedBluetoothDevice) it.next());
            }
        }
    }

    public String getLogTag() {
        return "BluetoothDeviceUpdater";
    }

    public final boolean isDeviceConnected(CachedBluetoothDevice cachedBluetoothDevice) {
        if (cachedBluetoothDevice == null) {
            return false;
        }
        BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
        if (DBG) {
            Log.d(
                    getLogTag(),
                    "isDeviceConnected() device name : "
                            + cachedBluetoothDevice.getName()
                            + ", is connected : "
                            + bluetoothDevice.isConnected()
                            + " , is profile connected : "
                            + cachedBluetoothDevice.isConnected());
        }
        return bluetoothDevice.getBondState() == 12 && bluetoothDevice.isConnected();
    }

    public final boolean isDeviceInCachedDevicesList(CachedBluetoothDevice cachedBluetoothDevice) {
        return ((ArrayList) this.mLocalManager.mCachedDeviceManager.getCachedDevicesCopy())
                .contains(cachedBluetoothDevice);
    }

    public abstract boolean isFilterMatched(CachedBluetoothDevice cachedBluetoothDevice);

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onAclConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {
        Log.d(
                getLogTag(),
                "onAclConnectionStateChanged() device: "
                        + cachedBluetoothDevice.getName()
                        + ", state: "
                        + i);
        update(cachedBluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onBluetoothStateChanged(int i) {
        if (12 == i) {
            forceUpdate();
        } else if (10 == i) {
            removeAllDevicesFromPreference();
        }
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {
        update(cachedBluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceBondStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        update(cachedBluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceDeleted(CachedBluetoothDevice cachedBluetoothDevice) {
        removePreference(cachedBluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onProfileConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i, int i2) {
        if (DBG) {
            String logTag = getLogTag();
            StringBuilder sb = new StringBuilder("onProfileConnectionStateChanged() device: ");
            sb.append(cachedBluetoothDevice.getName());
            sb.append(", state: ");
            sb.append(i);
            sb.append(", bluetoothProfile: ");
            Preference$$ExternalSyntheticOutline0.m(sb, i2, logTag);
        }
        update(cachedBluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public final void onServiceConnected() {
        forceUpdate();
    }

    public final void registerCallback() {
        LocalBluetoothManager localBluetoothManager = this.mLocalManager;
        if (localBluetoothManager == null) {
            Log.e(getLogTag(), "registerCallback() Bluetooth is not supported on this device");
            return;
        }
        localBluetoothManager.setForegroundActivity(this.mContext);
        this.mLocalManager.mEventManager.registerCallback(this);
        this.mLocalManager.mProfileManager.addServiceListener(this);
        forceUpdate();
    }

    public final void removeAllDevicesFromPreference() {
        LocalBluetoothManager localBluetoothManager = this.mLocalManager;
        if (localBluetoothManager == null) {
            Log.e(
                    getLogTag(),
                    "removeAllDevicesFromPreference() BT is not supported on this device");
            return;
        }
        Iterator it =
                ((ArrayList) localBluetoothManager.mCachedDeviceManager.getCachedDevicesCopy())
                        .iterator();
        while (it.hasNext()) {
            removePreference((CachedBluetoothDevice) it.next());
        }
    }

    public final void removePreference(CachedBluetoothDevice cachedBluetoothDevice) {
        BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
        CachedBluetoothDevice cachedBluetoothDevice2 = cachedBluetoothDevice.mSubDevice;
        boolean containsKey = ((HashMap) this.mPreferenceMap).containsKey(bluetoothDevice);
        DevicePreferenceCallback devicePreferenceCallback = this.mDevicePreferenceCallback;
        if (containsKey) {
            if (((HashMap) this.mPreferenceMap).containsKey(bluetoothDevice)) {
                devicePreferenceCallback.onDeviceRemoved(
                        (Preference) ((HashMap) this.mPreferenceMap).get(bluetoothDevice));
                ((HashMap) this.mPreferenceMap).remove(bluetoothDevice);
                return;
            }
            return;
        }
        if (cachedBluetoothDevice2 != null) {
            BluetoothDevice bluetoothDevice2 = cachedBluetoothDevice2.mDevice;
            if (((HashMap) this.mPreferenceMap).containsKey(bluetoothDevice2)) {
                devicePreferenceCallback.onDeviceRemoved(
                        (Preference) ((HashMap) this.mPreferenceMap).get(bluetoothDevice2));
                ((HashMap) this.mPreferenceMap).remove(bluetoothDevice2);
            }
        }
    }

    public final void unregisterCallback() {
        LocalBluetoothManager localBluetoothManager = this.mLocalManager;
        if (localBluetoothManager == null) {
            Log.e(getLogTag(), "unregisterCallback() Bluetooth is not supported on this device");
            return;
        }
        localBluetoothManager.setForegroundActivity(null);
        this.mLocalManager.mEventManager.unregisterCallback(this);
        this.mLocalManager.mProfileManager.removeServiceListener(this);
    }

    public void update(CachedBluetoothDevice cachedBluetoothDevice) {
        if (isFilterMatched(cachedBluetoothDevice)) {
            addPreference(cachedBluetoothDevice);
        } else {
            removePreference(cachedBluetoothDevice);
        }
    }

    public BluetoothDeviceUpdater(
            Context context, DevicePreferenceCallback devicePreferenceCallback, int i) {
        this(
                context,
                devicePreferenceCallback,
                LocalBluetoothManager.getInstance(context, Utils.mOnInitCallback),
                i);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public final void onServiceDisconnected() {}

    public void addPreference(CachedBluetoothDevice cachedBluetoothDevice) {}
}
