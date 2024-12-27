package com.android.settings.development.bluetooth;

import android.util.Log;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothCodecListPreferenceController
        extends AbstractBluetoothListPreferenceController {
    public static final boolean DEBUG = Log.isLoggable("BtExtCodecCtr", 3);

    @Override // com.android.settings.development.bluetooth.AbstractBluetoothListPreferenceController, com.android.settingslib.development.DeveloperOptionsPreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mListPreference =
                (ListPreference)
                        preferenceScreen.findPreference("bluetooth_audio_codec_settings_list");
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bluetooth_audio_codec_settings_list";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (!DEBUG) {
            return false;
        }
        Log.d("BtExtCodecCtr", "isAvailable: false");
        return false;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        return false;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        setupDefaultListPreference();
    }
}
