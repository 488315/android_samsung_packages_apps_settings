package com.android.settings.development;

import android.bluetooth.BluetoothAdapter;
import android.os.SystemProperties;
import android.sysprop.BluetoothProperties;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothLeAudioAllowListPreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    static final String BYPASS_LE_AUDIO_ALLOWLIST_PROPERTY =
            "persist.bluetooth.leaudio.bypass_allow_list";
    BluetoothAdapter mBluetoothAdapter;
    boolean mLeAudioConnectionByDefault;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bluetooth_bypass_leaudio_allowlist";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return ((Boolean)
                                BluetoothProperties.isProfileBapUnicastClientEnabled()
                                        .orElse(Boolean.FALSE))
                        .booleanValue()
                && this.mLeAudioConnectionByDefault;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        if (SystemProperties.getBoolean(BYPASS_LE_AUDIO_ALLOWLIST_PROPERTY, false)) {
            SystemProperties.set(BYPASS_LE_AUDIO_ALLOWLIST_PROPERTY, Boolean.toString(false));
            ((TwoStatePreference) this.mPreference).setChecked(false);
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        SystemProperties.set(
                BYPASS_LE_AUDIO_ALLOWLIST_PROPERTY,
                ((Boolean) obj).booleanValue() ? "true" : "false");
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter == null) {
            this.mPreference.setEnabled(false);
            return;
        }
        if (bluetoothAdapter.isLeAudioSupported() != 10) {
            this.mPreference.setEnabled(false);
            ((TwoStatePreference) this.mPreference).setChecked(false);
        } else {
            this.mPreference.setEnabled(true);
            ((TwoStatePreference) this.mPreference)
                    .setChecked(
                            SystemProperties.getBoolean(BYPASS_LE_AUDIO_ALLOWLIST_PROPERTY, false));
        }
    }
}
