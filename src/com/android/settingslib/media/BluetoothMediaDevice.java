package com.android.settingslib.media;

import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaRoute2Info;

import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothMediaDevice extends MediaDevice {
    public final AudioManager mAudioManager;
    public final CachedBluetoothDevice mCachedDevice;

    public BluetoothMediaDevice(
            Context context,
            CachedBluetoothDevice cachedBluetoothDevice,
            MediaRoute2Info mediaRoute2Info) {
        super(context, mediaRoute2Info);
        this.mCachedDevice = cachedBluetoothDevice;
        this.mAudioManager = (AudioManager) context.getSystemService(AudioManager.class);
        initDeviceRecord();
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final String getAddress() {
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        return cachedBluetoothDevice != null
                ? cachedBluetoothDevice.mDevice.getAddress()
                : ApnSettings.MVNO_NONE;
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final int getDevice() {
        return 128;
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final String getId() {
        return (!this.mCachedDevice.isHearingAidDevice() || this.mCachedDevice.getHiSyncId() == 0)
                ? this.mCachedDevice.mDevice.getAddress()
                : Long.toString(this.mCachedDevice.getHiSyncId());
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final String getName() {
        return this.mCachedDevice.getName();
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final boolean isCarKitDevice() {
        BluetoothClass bluetoothClass = this.mCachedDevice.mDevice.getBluetoothClass();
        if (bluetoothClass == null) {
            return false;
        }
        int deviceClass = bluetoothClass.getDeviceClass();
        return deviceClass == 1032 || deviceClass == 1056;
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final boolean isConnected() {
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        return cachedBluetoothDevice.mBondState == 12 && cachedBluetoothDevice.isConnected();
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final boolean isFastPairDevice() {
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        return cachedBluetoothDevice != null
                && BluetoothUtils.getBooleanMetaData(cachedBluetoothDevice.mDevice, 6);
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final boolean isMutingExpectedDevice() {
        return this.mAudioManager.getMutingExpectedDevice() != null
                && this.mCachedDevice
                        .mDevice
                        .getAddress()
                        .equals(this.mAudioManager.getMutingExpectedDevice().getAddress());
    }
}
