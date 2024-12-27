package com.android.settings.development.bluetooth;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothCodecConfig;
import android.bluetooth.BluetoothCodecStatus;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;

import androidx.preference.PreferenceScreen;
import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.settings.development.BluetoothA2dpConfigStore;
import com.android.settings.development.DevelopmentSettingsDashboardFragment;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothCodecDialogPreferenceController
        extends AbstractBluetoothDialogPreferenceController {
    public final AbstractBluetoothPreferenceController.Callback mCallback;

    public BluetoothCodecDialogPreferenceController(
            Context context,
            Lifecycle lifecycle,
            BluetoothA2dpConfigStore bluetoothA2dpConfigStore,
            AbstractBluetoothPreferenceController.Callback callback) {
        super(context, lifecycle, bluetoothA2dpConfigStore);
        this.mCallback = callback;
    }

    public int convertCfgToBtnIndex(int i) {
        int defaultIndex = getDefaultIndex();
        if (i == 0) {
            return 0;
        }
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                i2 = 4;
                if (i == 4) {
                    return 3;
                }
                if (i != 7) {
                    WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                            .m(i, "Unsupported config:", "BtCodecCtr");
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
        return convertCfgToBtnIndex(bluetoothCodecConfig.getCodecType());
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bluetooth_audio_codec_settings";
    }

    @Override // com.android.settings.development.bluetooth.AbstractBluetoothDialogPreferenceController
    public final List getSelectableIndex() {
        BluetoothCodecStatus codecStatus;
        ArrayList arrayList = new ArrayList();
        BluetoothA2dp bluetoothA2dp = this.mBluetoothA2dp;
        arrayList.add(Integer.valueOf(getDefaultIndex()));
        if (bluetoothA2dp == null) {
            return arrayList;
        }
        BluetoothDevice a2dpActiveDevice = getA2dpActiveDevice();
        if (a2dpActiveDevice == null) {
            Log.d("BtCodecCtr", "Unable to get selectable index. No Active Bluetooth device");
            return arrayList;
        }
        if (bluetoothA2dp.isOptionalCodecsEnabled(a2dpActiveDevice) == 1) {
            BluetoothA2dp bluetoothA2dp2 = this.mBluetoothA2dp;
            List<BluetoothCodecConfig> list = null;
            if (bluetoothA2dp2 != null
                    && (codecStatus = bluetoothA2dp2.getCodecStatus(a2dpActiveDevice)) != null) {
                list = codecStatus.getCodecsSelectableCapabilities();
            }
            if (list != null) {
                ArrayList arrayList2 = new ArrayList();
                Iterator<BluetoothCodecConfig> it = list.iterator();
                while (it.hasNext()) {
                    arrayList2.add(Integer.valueOf(convertCfgToBtnIndex(it.next().getCodecType())));
                }
                return arrayList2;
            }
        }
        arrayList.add(Integer.valueOf(convertCfgToBtnIndex(0)));
        return arrayList;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.settings.development.bluetooth.AbstractBluetoothDialogPreferenceController
    public final void onHDAudioEnabled() {
        writeConfigurationValues(0);
    }

    @Override // com.android.settings.development.bluetooth.AbstractBluetoothDialogPreferenceController
    public final void onIndexUpdated(int i) {
        super.onIndexUpdated(i);
        Iterator it =
                ((ArrayList)
                                ((DevelopmentSettingsDashboardFragment) this.mCallback)
                                        .mPreferenceControllers)
                        .iterator();
        while (it.hasNext()) {
            AbstractPreferenceController abstractPreferenceController =
                    (AbstractPreferenceController) it.next();
            if ((abstractPreferenceController
                            instanceof AbstractBluetoothDialogPreferenceController)
                    && !(abstractPreferenceController
                            instanceof BluetoothCodecDialogPreferenceController)) {
                ((AbstractBluetoothDialogPreferenceController) abstractPreferenceController)
                        .onBluetoothCodecUpdated();
            }
        }
    }

    @Override // com.android.settings.development.bluetooth.AbstractBluetoothDialogPreferenceController
    public final void writeConfigurationValues(int i) {
        int i2 = 1000000;
        int i3 = 0;
        if (i != 0) {
            int i4 = 1;
            if (i != 1) {
                i4 = 2;
                if (i != 2) {
                    if (i == 3) {
                        i3 = 4;
                    } else if (i != 4) {
                        i2 = 0;
                    } else {
                        i3 = 7;
                    }
                }
            }
            i3 = i4;
        }
        BluetoothA2dpConfigStore bluetoothA2dpConfigStore = this.mBluetoothA2dpConfigStore;
        bluetoothA2dpConfigStore.mCodecTypeNative = i3;
        bluetoothA2dpConfigStore.mCodecPriority = i2;
        BluetoothCodecConfig selectableByCodecType = getSelectableByCodecType(i3);
        if (selectableByCodecType == null) {
            Log.d("BtCodecCtr", "Selectable config is null. Unable to reset");
        }
        bluetoothA2dpConfigStore.mSampleRate =
                AbstractBluetoothDialogPreferenceController.getHighestSampleRate(
                        selectableByCodecType);
        bluetoothA2dpConfigStore.mBitsPerSample =
                AbstractBluetoothDialogPreferenceController.getHighestBitsPerSample(
                        selectableByCodecType);
        bluetoothA2dpConfigStore.mChannelMode =
                AbstractBluetoothDialogPreferenceController.getHighestChannelMode(
                        selectableByCodecType);
    }
}
