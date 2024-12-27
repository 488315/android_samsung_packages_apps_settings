package com.android.settings.connecteddevice.audiosharing;

import android.content.Context;

import com.android.settings.R;
import com.android.settings.widget.SeekBarPreference;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioSharingDeviceVolumePreference extends SeekBarPreference {
    public final CachedBluetoothDevice mCachedDevice;

    public AudioSharingDeviceVolumePreference(
            Context context, CachedBluetoothDevice cachedBluetoothDevice) {
        super(context);
        setLayoutResource(R.layout.preference_volume_slider);
        this.mCachedDevice = cachedBluetoothDevice;
    }
}
