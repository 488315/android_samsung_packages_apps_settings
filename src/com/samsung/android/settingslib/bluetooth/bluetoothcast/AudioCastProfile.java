package com.samsung.android.settingslib.bluetooth.bluetoothcast;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;

import com.android.settings.R;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import com.samsung.android.bluetooth.SemBluetoothAudioCast;
import com.samsung.android.bluetooth.SemBluetoothCastDevice;
import com.samsung.android.bluetooth.SemBluetoothCastProfile;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class AudioCastProfile {
    public final String TAG = "AudioCastProfile";
    public final AnonymousClass1 mAudioCastProfileListener;
    public final CachedBluetoothCastDeviceManager mCastDeviceManager;
    public final LocalBluetoothCastProfileManager mCastProfileManager;
    public final Context mContext;
    public SemBluetoothAudioCast mService;

    public AudioCastProfile(
            Context context,
            CachedBluetoothCastDeviceManager cachedBluetoothCastDeviceManager,
            LocalBluetoothCastProfileManager localBluetoothCastProfileManager) {
        SemBluetoothCastProfile.BluetoothCastProfileListener bluetoothCastProfileListener =
                new SemBluetoothCastProfile
                        .BluetoothCastProfileListener() { // from class:
                                                          // com.samsung.android.settingslib.bluetooth.bluetoothcast.AudioCastProfile.1
                    public final void onServiceConnected(
                            SemBluetoothCastProfile semBluetoothCastProfile) {
                        int connectionState;
                        Log.d(AudioCastProfile.this.TAG, "AudioCastProfile Proxy object connected");
                        SemBluetoothAudioCast semBluetoothAudioCast =
                                (SemBluetoothAudioCast) semBluetoothCastProfile;
                        AudioCastProfile.this.mService = semBluetoothAudioCast;
                        List<SemBluetoothCastDevice> audioCastDevices =
                                semBluetoothAudioCast.getAudioCastDevices();
                        if (audioCastDevices == null || audioCastDevices.size() <= 0) {
                            return;
                        }
                        for (SemBluetoothCastDevice semBluetoothCastDevice : audioCastDevices) {
                            int remoteDeviceRole = semBluetoothCastDevice.getRemoteDeviceRole();
                            if (remoteDeviceRole == 2) {
                                CachedBluetoothCastDevice findCastDevice =
                                        AudioCastProfile.this.mCastDeviceManager.findCastDevice(
                                                semBluetoothCastDevice);
                                if (findCastDevice == null
                                        && ((connectionState =
                                                                AudioCastProfile.this.mService
                                                                        .getConnectionState(
                                                                                semBluetoothCastDevice))
                                                        == 2
                                                || connectionState == 1)) {
                                    AudioCastProfile audioCastProfile = AudioCastProfile.this;
                                    findCastDevice =
                                            audioCastProfile.mCastDeviceManager.addCastDevice(
                                                    audioCastProfile.mCastProfileManager,
                                                    semBluetoothCastDevice);
                                }
                                if (findCastDevice != null) {
                                    Log.d(
                                            AudioCastProfile.this.TAG,
                                            "add castdevice "
                                                    + semBluetoothCastDevice.getAddressForLog()
                                                    + "/"
                                                    + String.valueOf(
                                                            AudioCastProfile.this.mService
                                                                    .getConnectionState(
                                                                            semBluetoothCastDevice)));
                                    AudioCastProfile audioCastProfile2 = AudioCastProfile.this;
                                    findCastDevice.onCastProfileStateChanged(
                                            audioCastProfile2,
                                            audioCastProfile2.mService.getConnectionState(
                                                    semBluetoothCastDevice));
                                    findCastDevice.dispatchAttributesChanged();
                                }
                            } else if (remoteDeviceRole == 1) {
                                LocalBluetoothManager localBluetoothManager =
                                        LocalBluetoothManager.getInstance(
                                                AudioCastProfile.this.mContext, null);
                                BluetoothDevice remoteDevice =
                                        BluetoothAdapter.getDefaultAdapter()
                                                .getRemoteDevice(
                                                        semBluetoothCastDevice.getAddress());
                                if (localBluetoothManager != null) {
                                    CachedBluetoothDevice findDevice =
                                            localBluetoothManager.mCachedDeviceManager.findDevice(
                                                    remoteDevice);
                                    if (findDevice == null) {
                                        Log.d(AudioCastProfile.this.TAG, "cacheddevice is null");
                                    } else {
                                        Log.d(
                                                AudioCastProfile.this.TAG,
                                                "change cacheddevice "
                                                        + findDevice.mDevice.getAddressForLogging()
                                                        + "/"
                                                        + String.valueOf(
                                                                AudioCastProfile.this.mService
                                                                        .getConnectionState(
                                                                                semBluetoothCastDevice)));
                                        if (AudioCastProfile.this.mService.getConnectionState(
                                                        semBluetoothCastDevice)
                                                == 2) {
                                            findDevice.mBluetoothCastMsg =
                                                    findDevice.mContext.getString(
                                                            R.string.bluetooth_cast_shared_with,
                                                            semBluetoothCastDevice.getPeerName());
                                        } else {
                                            findDevice.mBluetoothCastMsg = null;
                                        }
                                        findDevice.refresh();
                                    }
                                }
                            }
                        }
                    }

                    public final void onServiceDisconnected() {
                        Log.d(
                                AudioCastProfile.this.TAG,
                                "AudioCastProfile Proxy object disconnected");
                        AudioCastProfile audioCastProfile = AudioCastProfile.this;
                        if (audioCastProfile.mService != null) {
                            audioCastProfile.mService = null;
                        }
                    }
                };
        Log.d("AudioCastProfile", "AudioCastProfile");
        this.mContext = context;
        this.mCastDeviceManager = cachedBluetoothCastDeviceManager;
        this.mCastProfileManager = localBluetoothCastProfileManager;
        SemBluetoothAudioCast.getProxy(context, bluetoothCastProfileListener);
    }

    public final void finalize() {
        super.finalize();
        SemBluetoothAudioCast semBluetoothAudioCast = this.mService;
        if (semBluetoothAudioCast != null) {
            semBluetoothAudioCast.closeProxy();
        }
    }
}
