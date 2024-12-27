package com.android.settings.development;

import android.os.SystemProperties;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothMaxConnectedAudioDevicesPreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    static final String MAX_CONNECTED_AUDIO_DEVICES_PROPERTY =
            "persist.bluetooth.maxconnectedaudiodevices";
    public int mDefaultMaxConnectedAudioDevices;

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        ListPreference listPreference = (ListPreference) this.mPreference;
        CharSequence[] charSequenceArr = listPreference.mEntries;
        charSequenceArr[0] =
                String.format(
                        charSequenceArr[0].toString(),
                        Integer.valueOf(this.mDefaultMaxConnectedAudioDevices));
        listPreference.setEntries(charSequenceArr);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bluetooth_max_connected_audio_devices";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        SystemProperties.set(MAX_CONNECTED_AUDIO_DEVICES_PROPERTY, ApnSettings.MVNO_NONE);
        updateState(this.mPreference);
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchEnabled() {
        super.onDeveloperOptionsSwitchEnabled();
        updateState(this.mPreference);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        String obj2 = obj.toString();
        if (((ListPreference) preference).findIndexOfValue(obj2) <= 0) {
            obj2 = ApnSettings.MVNO_NONE;
        }
        SystemProperties.set(MAX_CONNECTED_AUDIO_DEVICES_PROPERTY, obj2);
        updateState(preference);
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        ListPreference listPreference = (ListPreference) preference;
        CharSequence[] charSequenceArr = listPreference.mEntries;
        String str = SystemProperties.get(MAX_CONNECTED_AUDIO_DEVICES_PROPERTY);
        int i = 0;
        if (!str.isEmpty()) {
            int findIndexOfValue = listPreference.findIndexOfValue(str);
            if (findIndexOfValue < 0) {
                SystemProperties.set(MAX_CONNECTED_AUDIO_DEVICES_PROPERTY, ApnSettings.MVNO_NONE);
            } else {
                i = findIndexOfValue;
            }
        }
        listPreference.setValueIndex(i);
        listPreference.setSummary(charSequenceArr[i]);
    }
}
