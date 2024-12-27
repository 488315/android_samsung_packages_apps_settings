package com.android.settings.development;

import android.bluetooth.BluetoothA2dp;
import android.os.SystemProperties;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothA2dpHwOffloadPreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener,
                PreferenceControllerMixin,
                BluetoothServiceConnectionListener,
                LifecycleObserver,
                OnDestroy {
    public BluetoothA2dp mBluetoothA2dp;
    boolean mChanged;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bluetooth_disable_a2dp_hw_offload";
    }

    @Override // com.android.settings.development.BluetoothServiceConnectionListener
    public final void onBluetoothServiceConnected(BluetoothA2dp bluetoothA2dp) {
        Log.d("DevSettingsA2dpOffload", "onBluetoothServiceConnected");
        this.mBluetoothA2dp = bluetoothA2dp;
    }

    @Override // com.android.settings.development.BluetoothServiceConnectionListener
    public final void onBluetoothServiceDisconnected() {
        Log.d("DevSettingsA2dpOffload", "onBluetoothServiceDisconnected");
        this.mBluetoothA2dp = null;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public final void onDestroy() {
        Log.d("DevSettingsA2dpOffload", "onDestroy");
        this.mBluetoothA2dp = null;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchEnabled() {
        super.onDeveloperOptionsSwitchEnabled();
        ((TwoStatePreference) this.mPreference)
                .setChecked(
                        SystemProperties.getBoolean(
                                "persist.bluetooth.a2dp_offload.disabled", false));
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        this.mChanged = true;
        SystemProperties.set(
                "persist.bluetooth.a2dp_offload.disabled",
                Boolean.toString(
                        !SystemProperties.getBoolean(
                                "persist.bluetooth.a2dp_offload.disabled", false)));
        BluetoothA2dp bluetoothA2dp = this.mBluetoothA2dp;
        if (bluetoothA2dp != null) {
            bluetoothA2dp.setA2dpOffloadMode();
        }
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        ((TwoStatePreference) this.mPreference)
                .setChecked(
                        SystemProperties.getBoolean(
                                "persist.bluetooth.a2dp_offload.disabled", false));
    }

    @Override // com.android.settings.development.BluetoothServiceConnectionListener
    public final void onBluetoothCodecUpdated() {}
}
