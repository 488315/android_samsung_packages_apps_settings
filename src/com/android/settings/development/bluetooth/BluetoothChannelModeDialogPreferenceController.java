package com.android.settings.development.bluetooth;

import android.bluetooth.BluetoothCodecConfig;

import androidx.preference.PreferenceScreen;
import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothChannelModeDialogPreferenceController
        extends AbstractBluetoothDialogPreferenceController {
    public int convertCfgToBtnIndex(int i) {
        int defaultIndex = getDefaultIndex();
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                        .m(i, "Unsupported config:", "BtChannelModeCtr");
                return defaultIndex;
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
        return convertCfgToBtnIndex(bluetoothCodecConfig.getChannelMode());
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bluetooth_channel_mode_settings";
    }

    @Override // com.android.settings.development.bluetooth.AbstractBluetoothDialogPreferenceController
    public final List getSelectableIndex() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(getDefaultIndex()));
        BluetoothCodecConfig currentCodecConfig = getCurrentCodecConfig();
        if (currentCodecConfig != null) {
            int channelMode =
                    getSelectableByCodecType(currentCodecConfig.getCodecType()).getChannelMode();
            for (int i = 0; i < 2; i++) {
                int i2 = AbstractBluetoothDialogPreferenceController.CHANNEL_MODES[i];
                if ((channelMode & i2) != 0) {
                    arrayList.add(Integer.valueOf(convertCfgToBtnIndex(i2)));
                }
            }
        }
        return arrayList;
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x0006, code lost:

       if (r2 != 2) goto L11;
    */
    @Override // com.android.settings.development.bluetooth.AbstractBluetoothDialogPreferenceController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void writeConfigurationValues(int r2) {
        /*
            r1 = this;
            if (r2 == 0) goto L9
            r0 = 1
            if (r2 == r0) goto L1d
            r0 = 2
            if (r2 == r0) goto L1d
            goto L1c
        L9:
            android.bluetooth.BluetoothCodecConfig r2 = r1.getCurrentCodecConfig()
            if (r2 == 0) goto L1c
            int r2 = r2.getCodecType()
            android.bluetooth.BluetoothCodecConfig r2 = r1.getSelectableByCodecType(r2)
            int r0 = com.android.settings.development.bluetooth.AbstractBluetoothDialogPreferenceController.getHighestChannelMode(r2)
            goto L1d
        L1c:
            r0 = 0
        L1d:
            com.android.settings.development.BluetoothA2dpConfigStore r1 = r1.mBluetoothA2dpConfigStore
            r1.mChannelMode = r0
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.development.bluetooth.BluetoothChannelModeDialogPreferenceController.writeConfigurationValues(int):void");
    }
}
