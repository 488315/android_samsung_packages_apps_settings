package com.android.settings.connecteddevice.audiosharing;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothVolumeControl;
import android.content.Context;
import android.media.AudioManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.SeekBar;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.bluetooth.BluetoothDeviceUpdater;
import com.android.settings.bluetooth.Utils;
import com.android.settings.connecteddevice.DevicePreferenceCallback;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.VolumeControlProfile;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AudioSharingDeviceVolumeControlUpdater extends BluetoothDeviceUpdater
        implements Preference.OnPreferenceClickListener {
    public final LocalBluetoothManager mBtManager;
    public final VolumeControlProfile mVolumeControl;

    public AudioSharingDeviceVolumeControlUpdater(
            Context context, DevicePreferenceCallback devicePreferenceCallback, int i) {
        super(context, devicePreferenceCallback, i);
        LocalBluetoothManager localBluetoothManager = Utils.getLocalBluetoothManager(context);
        this.mBtManager = localBluetoothManager;
        this.mVolumeControl =
                localBluetoothManager == null
                        ? null
                        : localBluetoothManager.mProfileManager.mVolumeControlProfile;
    }

    @Override // com.android.settings.bluetooth.BluetoothDeviceUpdater
    public final void addPreference(final CachedBluetoothDevice cachedBluetoothDevice) {
        if (cachedBluetoothDevice == null) {
            return;
        }
        BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
        if (((HashMap) this.mPreferenceMap).containsKey(bluetoothDevice)) {
            return;
        }
        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener =
                new SeekBar.OnSeekBarChangeListener() { // from class:
                    // com.android.settings.connecteddevice.audiosharing.AudioSharingDeviceVolumeControlUpdater.1
                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onStopTrackingTouch(SeekBar seekBar) {
                        int progress = seekBar.getProgress();
                        int groupId = AudioSharingUtils.getGroupId(cachedBluetoothDevice);
                        if (groupId != -1
                                && groupId
                                        == Settings.Secure.getInt(
                                                AudioSharingDeviceVolumeControlUpdater.this.mContext
                                                        .getContentResolver(),
                                                "bluetooth_le_broadcast_fallback_active_group_id",
                                                -1)) {
                            AudioSharingDeviceVolumeControlUpdater
                                    audioSharingDeviceVolumeControlUpdater =
                                            AudioSharingDeviceVolumeControlUpdater.this;
                            audioSharingDeviceVolumeControlUpdater.getClass();
                            try {
                                ((AudioManager)
                                                audioSharingDeviceVolumeControlUpdater.mContext
                                                        .getSystemService(AudioManager.class))
                                        .setStreamVolume(
                                                3,
                                                Math.round(
                                                        (progress
                                                                        * (r0.getStreamMaxVolume(3)
                                                                                - r0
                                                                                        .getStreamMinVolume(
                                                                                                3)))
                                                                / 255),
                                                0);
                                audioSharingDeviceVolumeControlUpdater.mMetricsFeatureProvider
                                        .action(
                                                audioSharingDeviceVolumeControlUpdater.mContext,
                                                1927,
                                                true);
                                return;
                            } catch (RuntimeException e) {
                                Log.e(
                                        "AudioSharingDeviceVolumeControlUpdater",
                                        "Fail to setAudioManagerStreamVolumeForFallbackDevice,"
                                            + " error = "
                                                + e);
                                return;
                            }
                        }
                        AudioSharingDeviceVolumeControlUpdater
                                audioSharingDeviceVolumeControlUpdater2 =
                                        AudioSharingDeviceVolumeControlUpdater.this;
                        CachedBluetoothDevice cachedBluetoothDevice2 = cachedBluetoothDevice;
                        VolumeControlProfile volumeControlProfile =
                                audioSharingDeviceVolumeControlUpdater2.mVolumeControl;
                        if (volumeControlProfile != null) {
                            BluetoothDevice bluetoothDevice2 = cachedBluetoothDevice2.mDevice;
                            BluetoothVolumeControl bluetoothVolumeControl =
                                    volumeControlProfile.mService;
                            if (bluetoothVolumeControl == null) {
                                Log.w(
                                        "VolumeControlProfile",
                                        "Proxy not attached to service. Cannot set volume offset.");
                            } else if (bluetoothDevice2 == null) {
                                Log.w(
                                        "VolumeControlProfile",
                                        "Device is null. Cannot set volume offset.");
                            } else {
                                bluetoothVolumeControl.setDeviceVolume(
                                        bluetoothDevice2, progress, true);
                            }
                            audioSharingDeviceVolumeControlUpdater2.mMetricsFeatureProvider.action(
                                    audioSharingDeviceVolumeControlUpdater2.mContext, 1927, false);
                        }
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {}
                };
        AudioSharingDeviceVolumePreference audioSharingDeviceVolumePreference =
                new AudioSharingDeviceVolumePreference(this.mPrefContext, cachedBluetoothDevice);
        audioSharingDeviceVolumePreference.setMax(255);
        audioSharingDeviceVolumePreference.setMin(0);
        audioSharingDeviceVolumePreference.mOnSeekBarChangeListener = onSeekBarChangeListener;
        audioSharingDeviceVolumePreference.setKey("audio_sharing_volume_control");
        audioSharingDeviceVolumePreference.setIcon(R.drawable.ic_bt_untethered_earbuds);
        audioSharingDeviceVolumePreference.setTitle(cachedBluetoothDevice.getName());
        ((HashMap) this.mPreferenceMap).put(bluetoothDevice, audioSharingDeviceVolumePreference);
        this.mDevicePreferenceCallback.onDeviceAdded(audioSharingDeviceVolumePreference);
    }

    @Override // com.android.settings.bluetooth.BluetoothDeviceUpdater
    public final String getLogTag() {
        return "AudioSharingDeviceVolumeControlUpdater";
    }

    @Override // com.android.settings.bluetooth.BluetoothDeviceUpdater
    public final boolean isFilterMatched(CachedBluetoothDevice cachedBluetoothDevice) {
        boolean z;
        if (isDeviceConnected(cachedBluetoothDevice)
                && isDeviceInCachedDevicesList(cachedBluetoothDevice)
                && cachedBluetoothDevice.isConnectedLeAudioDevice()) {
            LocalBluetoothManager localBluetoothManager = this.mBtManager;
            if (AudioSharingUtils.isBroadcasting(localBluetoothManager)
                    && BluetoothUtils.hasConnectedBroadcastSource(
                            cachedBluetoothDevice, localBluetoothManager)) {
                z = true;
                Log.d(
                        "AudioSharingDeviceVolumeControlUpdater",
                        "isFilterMatched() device : "
                                + cachedBluetoothDevice.getName()
                                + ", isFilterMatched : "
                                + z);
                return z;
            }
        }
        z = false;
        Log.d(
                "AudioSharingDeviceVolumeControlUpdater",
                "isFilterMatched() device : "
                        + cachedBluetoothDevice.getName()
                        + ", isFilterMatched : "
                        + z);
        return z;
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        return true;
    }

    @Override // com.android.settings.bluetooth.BluetoothDeviceUpdater
    public final void update(CachedBluetoothDevice cachedBluetoothDevice) {
        super.update(cachedBluetoothDevice);
        Log.d("AudioSharingDeviceVolumeControlUpdater", "Map : " + this.mPreferenceMap);
    }
}
