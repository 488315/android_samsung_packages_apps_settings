package com.android.settings.development.bluetooth;

import android.bluetooth.BluetoothCodecConfig;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothQualityDialogPreferenceController
        extends AbstractBluetoothDialogPreferenceController {
    public int convertCfgToBtnIndex(int i) {
        int i2 = i - 1000;
        return i2 < 0 ? getDefaultIndex() : i2;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        ((BaseBluetoothDialogPreference) this.mPreference).mCallback = this;
    }

    @Override // com.android.settings.development.bluetooth.AbstractBluetoothDialogPreferenceController
    public final int getCurrentIndexByConfig(BluetoothCodecConfig bluetoothCodecConfig) {
        return convertCfgToBtnIndex((int) bluetoothCodecConfig.getCodecSpecific1());
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bluetooth_a2dp_ldac_playback_quality";
    }

    @Override // com.android.settings.development.bluetooth.AbstractBluetoothDialogPreferenceController
    public final List getSelectableIndex() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 4; i++) {
            arrayList.add(Integer.valueOf(i));
        }
        return arrayList;
    }

    @Override // com.android.settings.development.bluetooth.AbstractBluetoothDialogPreferenceController
    public final void onHDAudioEnabled() {
        this.mPreference.setEnabled(false);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        BluetoothCodecConfig currentCodecConfig = getCurrentCodecConfig();
        if (currentCodecConfig != null && currentCodecConfig.getCodecType() == 4) {
            preference.setEnabled(true);
        } else {
            preference.setEnabled(false);
            preference.setSummary(ApnSettings.MVNO_NONE);
        }
    }

    @Override // com.android.settings.development.bluetooth.AbstractBluetoothDialogPreferenceController
    public final void writeConfigurationValues(int i) {
        this.mBluetoothA2dpConfigStore.mCodecSpecific1Value =
                (i == 0 || i == 1 || i == 2 || i == 3) ? i + 1000 : 0L;
    }
}
