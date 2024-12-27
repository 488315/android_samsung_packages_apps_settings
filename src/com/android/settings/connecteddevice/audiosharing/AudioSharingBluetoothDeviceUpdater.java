package com.android.settings.connecteddevice.audiosharing;

import android.bluetooth.BluetoothAdapter;
import android.util.Log;
import android.util.Pair;

import androidx.preference.Preference;

import com.android.settings.bluetooth.BluetoothDeviceUpdater;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AudioSharingBluetoothDeviceUpdater extends BluetoothDeviceUpdater
        implements Preference.OnPreferenceClickListener {
    @Override // com.android.settings.bluetooth.BluetoothDeviceUpdater
    public final String getLogTag() {
        return "AudioSharingBluetoothDeviceUpdater";
    }

    @Override // com.android.settings.bluetooth.BluetoothDeviceUpdater
    public final boolean isFilterMatched(CachedBluetoothDevice cachedBluetoothDevice) {
        if (isDeviceConnected(cachedBluetoothDevice)
                && isDeviceInCachedDevicesList(cachedBluetoothDevice)) {
            BluetoothAdapter.getDefaultAdapter();
        }
        Log.d(
                "AudioSharingBluetoothDeviceUpdater",
                "isFilterMatched() device : "
                        + cachedBluetoothDevice.getName()
                        + ", isFilterMatched : false");
        return false;
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        int i = this.mMetricsCategory;
        SettingsMetricsFeatureProvider settingsMetricsFeatureProvider =
                this.mMetricsFeatureProvider;
        settingsMetricsFeatureProvider.logClickedPreference(preference, i);
        settingsMetricsFeatureProvider.action(this.mContext, 1935, new Pair[0]);
        return true;
    }

    @Override // com.android.settings.bluetooth.BluetoothDeviceUpdater
    public final void update(CachedBluetoothDevice cachedBluetoothDevice) {
        super.update(cachedBluetoothDevice);
        Log.d("AudioSharingBluetoothDeviceUpdater", "Map : " + this.mPreferenceMap);
    }
}
