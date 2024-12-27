package com.android.settingslib.development;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.development.BluetoothMaxConnectedAudioDevicesPreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class DeveloperOptionsPreferenceController extends AbstractPreferenceController {
    public Preference mPreference;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public boolean isAvailable() {
        return !(this instanceof BluetoothMaxConnectedAudioDevicesPreferenceController);
    }

    public void onDeveloperOptionsDisabled() {
        if (isAvailable()) {
            onDeveloperOptionsSwitchDisabled();
        }
    }

    public void onDeveloperOptionsEnabled() {
        if (isAvailable()) {
            onDeveloperOptionsSwitchEnabled();
        }
    }

    public void onDeveloperOptionsSwitchDisabled() {
        this.mPreference.setEnabled(false);
    }

    public void onDeveloperOptionsSwitchEnabled() {
        this.mPreference.setEnabled(true);
    }
}
