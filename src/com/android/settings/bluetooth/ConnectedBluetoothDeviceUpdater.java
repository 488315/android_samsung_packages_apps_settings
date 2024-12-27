package com.android.settings.bluetooth;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

import com.android.settings.connecteddevice.DevicePreferenceCallback;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;

import com.sec.ims.presence.ServiceTuple;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ConnectedBluetoothDeviceUpdater extends BluetoothDeviceUpdater {
    public static final boolean DBG = Log.isLoggable("BluetoothDeviceUpdater", 3);
    public final AudioManager mAudioManager;

    public ConnectedBluetoothDeviceUpdater(
            Context context, DevicePreferenceCallback devicePreferenceCallback, int i) {
        super(context, devicePreferenceCallback, i);
        this.mAudioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
    }

    @Override // com.android.settings.bluetooth.BluetoothDeviceUpdater
    public final void addPreference(CachedBluetoothDevice cachedBluetoothDevice) {
        cachedBluetoothDevice.getClass();
    }

    @Override // com.android.settings.bluetooth.BluetoothDeviceUpdater
    public final String getLogTag() {
        return "ConnBluetoothDeviceUpdater";
    }

    @Override // com.android.settings.bluetooth.BluetoothDeviceUpdater
    public final boolean isFilterMatched(CachedBluetoothDevice cachedBluetoothDevice) {
        int mode = this.mAudioManager.getMode();
        if (mode == 1 || mode != 2) {}
        if (BluetoothUtils.isExclusivelyManagedBluetoothDevice(
                        this.mContext, cachedBluetoothDevice.mDevice)
                && DBG) {
            Log.d(
                    "ConnBluetoothDeviceUpdater",
                    "isFilterMatched() hide BluetoothDevice with exclusive manager");
        }
        return false;
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onAudioModeChanged() {
        forceUpdate();
    }

    @Override // com.android.settings.bluetooth.BluetoothDeviceUpdater
    public final void update(CachedBluetoothDevice cachedBluetoothDevice) {
        super.update(cachedBluetoothDevice);
        Log.d("ConnBluetoothDeviceUpdater", "Map : " + this.mPreferenceMap);
    }
}
