package com.android.settings.development;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.SystemProperties;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothLeAudioHwOffloadPreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    BluetoothAdapter mBluetoothAdapter;
    boolean mChanged;
    public final DevelopmentSettingsDashboardFragment mFragment;

    public BluetoothLeAudioHwOffloadPreferenceController(
            Context context,
            DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment) {
        super(context);
        this.mChanged = false;
        this.mFragment = developmentSettingsDashboardFragment;
        this.mBluetoothAdapter =
                ((BluetoothManager) context.getSystemService(BluetoothManager.class)).getAdapter();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bluetooth_disable_le_audio_hw_offload";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter == null) {
            return;
        }
        boolean z = bluetoothAdapter.isLeAudioSupported() == 10;
        boolean z2 = SystemProperties.getBoolean("ro.bluetooth.leaudio_offload.supported", false);
        boolean z3 = SystemProperties.getBoolean("persist.bluetooth.a2dp_offload.disabled", false);
        if (!z || !z2 || z3) {
            this.mPreference.setEnabled(false);
        } else {
            ((TwoStatePreference) this.mPreference).setChecked(true);
            SystemProperties.set("persist.bluetooth.leaudio_offload.disabled", "true");
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        BluetoothRebootDialog.show(this.mFragment);
        this.mChanged = true;
        return false;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter == null) {
            return;
        }
        boolean z = bluetoothAdapter.isLeAudioSupported() == 10;
        boolean z2 = SystemProperties.getBoolean("ro.bluetooth.leaudio_offload.supported", false);
        boolean z3 = SystemProperties.getBoolean("persist.bluetooth.a2dp_offload.disabled", false);
        if (z && z2 && !z3) {
            ((TwoStatePreference) this.mPreference)
                    .setChecked(
                            SystemProperties.getBoolean(
                                    "persist.bluetooth.leaudio_offload.disabled", true));
        } else {
            this.mPreference.setEnabled(false);
            ((TwoStatePreference) this.mPreference).setChecked(true);
        }
    }
}
