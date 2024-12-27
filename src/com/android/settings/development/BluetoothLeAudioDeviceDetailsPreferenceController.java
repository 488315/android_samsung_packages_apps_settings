package com.android.settings.development;

import android.bluetooth.BluetoothAdapter;
import android.os.SystemProperties;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothLeAudioDeviceDetailsPreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    public static int sLeAudioSupportedStateCache = Integer.MAX_VALUE;
    BluetoothAdapter mBluetoothAdapter;
    boolean mLeAudioEnabledByDefault;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bluetooth_show_leaudio_device_details";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        BluetoothAdapter bluetoothAdapter;
        int isLeAudioSupported;
        if (sLeAudioSupportedStateCache == Integer.MAX_VALUE
                && (bluetoothAdapter = this.mBluetoothAdapter) != null
                && (isLeAudioSupported = bluetoothAdapter.isLeAudioSupported()) != 1) {
            sLeAudioSupportedStateCache = isLeAudioSupported;
        }
        return !this.mLeAudioEnabledByDefault && sLeAudioSupportedStateCache == 10;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        SystemProperties.set(
                "persist.bluetooth.leaudio.toggle_visible",
                Boolean.toString(((Boolean) obj).booleanValue()));
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        if (isAvailable()) {
            ((TwoStatePreference) this.mPreference)
                    .setChecked(
                            SystemProperties.getBoolean(
                                    "persist.bluetooth.leaudio.toggle_visible", true));
        }
    }
}
