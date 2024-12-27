package com.android.settings.development.bluetooth;

import android.bluetooth.BluetoothCodecConfig;

import androidx.preference.PreferenceScreen;
import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothBitPerSampleDialogPreferenceController
        extends AbstractBluetoothDialogPreferenceController {
    public int convertCfgToBtnIndex(int i) {
        int defaultIndex = getDefaultIndex();
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                i2 = 4;
                if (i == 4) {
                    return 3;
                }
                if (i != 8) {
                    WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                            .m(i, "Unsupported config:", "BtBitPerSampleCtr");
                    return defaultIndex;
                }
            }
        }
        return i2;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        ((BaseBluetoothDialogPreference) this.mPreference).mCallback = this;
    }

    @Override // com.android.settings.development.bluetooth.AbstractBluetoothDialogPreferenceController
    public final int getCurrentIndexByConfig(BluetoothCodecConfig bluetoothCodecConfig) {
        return convertCfgToBtnIndex(bluetoothCodecConfig.getBitsPerSample());
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bluetooth_bit_per_sample_settings";
    }

    @Override // com.android.settings.development.bluetooth.AbstractBluetoothDialogPreferenceController
    public final List getSelectableIndex() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(getDefaultIndex()));
        BluetoothCodecConfig currentCodecConfig = getCurrentCodecConfig();
        if (currentCodecConfig != null) {
            int bitsPerSample =
                    getSelectableByCodecType(currentCodecConfig.getCodecType()).getBitsPerSample();
            for (int i = 0; i < 4; i++) {
                int i2 = AbstractBluetoothDialogPreferenceController.BITS_PER_SAMPLES[i];
                if ((bitsPerSample & i2) != 0) {
                    arrayList.add(Integer.valueOf(convertCfgToBtnIndex(i2)));
                }
            }
        }
        return arrayList;
    }

    @Override // com.android.settings.development.bluetooth.AbstractBluetoothDialogPreferenceController
    public final void writeConfigurationValues(int i) {
        int highestBitsPerSample;
        if (i != 0) {
            highestBitsPerSample = 1;
            if (i != 1) {
                highestBitsPerSample = 2;
                if (i != 2) {
                    if (i != 3) {
                        if (i == 4) {
                            highestBitsPerSample = 8;
                        }
                        highestBitsPerSample = 0;
                    } else {
                        highestBitsPerSample = 4;
                    }
                }
            }
        } else {
            BluetoothCodecConfig currentCodecConfig = getCurrentCodecConfig();
            if (currentCodecConfig != null) {
                highestBitsPerSample =
                        AbstractBluetoothDialogPreferenceController.getHighestBitsPerSample(
                                getSelectableByCodecType(currentCodecConfig.getCodecType()));
            }
            highestBitsPerSample = 0;
        }
        this.mBluetoothA2dpConfigStore.mBitsPerSample = highestBitsPerSample;
    }
}
