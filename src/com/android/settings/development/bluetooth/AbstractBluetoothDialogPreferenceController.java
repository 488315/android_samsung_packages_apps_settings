package com.android.settings.development.bluetooth;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothCodecConfig;
import android.bluetooth.BluetoothCodecStatus;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.preference.Preference;

import com.android.settings.development.BluetoothA2dpConfigStore;
import com.android.settingslib.core.lifecycle.Lifecycle;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AbstractBluetoothDialogPreferenceController
        extends AbstractBluetoothPreferenceController {
    public final BluetoothA2dpConfigStore mBluetoothA2dpConfigStore;
    public static final int[] SAMPLE_RATES = {32, 16, 8, 4, 2, 1};
    public static final int[] BITS_PER_SAMPLES = {4, 8, 2, 1};
    public static final int[] CHANNEL_MODES = {2, 1};

    public AbstractBluetoothDialogPreferenceController(
            Context context,
            Lifecycle lifecycle,
            BluetoothA2dpConfigStore bluetoothA2dpConfigStore) {
        super(context, lifecycle);
        this.mBluetoothA2dpConfigStore = bluetoothA2dpConfigStore;
    }

    public static int getHighestBitsPerSample(BluetoothCodecConfig bluetoothCodecConfig) {
        if (bluetoothCodecConfig == null) {
            Log.d("AbstractBtDlgCtr", "Unable to get highest bits per sample. Config is empty");
            return 0;
        }
        int bitsPerSample = bluetoothCodecConfig.getBitsPerSample();
        for (int i = 0; i < 4; i++) {
            int i2 = BITS_PER_SAMPLES[i];
            if ((bitsPerSample & i2) != 0) {
                return i2;
            }
        }
        return 0;
    }

    public static int getHighestChannelMode(BluetoothCodecConfig bluetoothCodecConfig) {
        if (bluetoothCodecConfig == null) {
            Log.d("AbstractBtDlgCtr", "Unable to get highest channel mode. Config is empty");
            return 0;
        }
        int channelMode = bluetoothCodecConfig.getChannelMode();
        for (int i = 0; i < 2; i++) {
            int i2 = CHANNEL_MODES[i];
            if ((channelMode & i2) != 0) {
                return i2;
            }
        }
        return 0;
    }

    public static int getHighestSampleRate(BluetoothCodecConfig bluetoothCodecConfig) {
        if (bluetoothCodecConfig == null) {
            Log.d("AbstractBtDlgCtr", "Unable to get highest sample rate. Config is empty");
            return 0;
        }
        int sampleRate = bluetoothCodecConfig.getSampleRate();
        for (int i = 0; i < 6; i++) {
            int i2 = SAMPLE_RATES[i];
            if ((sampleRate & i2) != 0) {
                return i2;
            }
        }
        return 0;
    }

    public final BluetoothCodecConfig getCurrentCodecConfig() {
        BluetoothA2dp bluetoothA2dp = this.mBluetoothA2dp;
        if (bluetoothA2dp == null) {
            return null;
        }
        BluetoothDevice a2dpActiveDevice = getA2dpActiveDevice();
        if (a2dpActiveDevice == null) {
            Log.d("AbstractBtDlgCtr", "Unable to get current codec config. No active device.");
            return null;
        }
        BluetoothCodecStatus codecStatus = bluetoothA2dp.getCodecStatus(a2dpActiveDevice);
        if (codecStatus != null) {
            return codecStatus.getCodecConfig();
        }
        Log.d("AbstractBtDlgCtr", "Unable to get current codec config. Codec status is null");
        return null;
    }

    public abstract int getCurrentIndexByConfig(BluetoothCodecConfig bluetoothCodecConfig);

    public final int getDefaultIndex() {
        return ((BaseBluetoothDialogPreference) this.mPreference).getDefaultIndex();
    }

    public final BluetoothCodecConfig getSelectableByCodecType(int i) {
        BluetoothCodecStatus codecStatus;
        BluetoothDevice a2dpActiveDevice = getA2dpActiveDevice();
        if (a2dpActiveDevice == null) {
            Log.d("AbstractBtDlgCtr", "Unable to get selectable config. No active device.");
            return null;
        }
        BluetoothA2dp bluetoothA2dp = this.mBluetoothA2dp;
        for (BluetoothCodecConfig bluetoothCodecConfig :
                (bluetoothA2dp == null
                                || (codecStatus = bluetoothA2dp.getCodecStatus(a2dpActiveDevice))
                                        == null)
                        ? null
                        : codecStatus.getCodecsSelectableCapabilities()) {
            if (bluetoothCodecConfig.getCodecType() == i) {
                return bluetoothCodecConfig;
            }
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "Unable to find matching codec config, type is ", "AbstractBtDlgCtr");
        return null;
    }

    public abstract List getSelectableIndex();

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final CharSequence getSummary() {
        int currentIndexByConfig;
        BaseBluetoothDialogPreference baseBluetoothDialogPreference =
                (BaseBluetoothDialogPreference) this.mPreference;
        BluetoothCodecConfig currentCodecConfig = getCurrentCodecConfig();
        if (currentCodecConfig == null) {
            Log.d(
                    "AbstractBtDlgCtr",
                    "Unable to get current config index. Current codec Config is null.");
            currentIndexByConfig = getDefaultIndex();
        } else {
            currentIndexByConfig = getCurrentIndexByConfig(currentCodecConfig);
        }
        return baseBluetoothDialogPreference.generateSummary(currentIndexByConfig);
    }

    @Override // com.android.settings.development.bluetooth.AbstractBluetoothPreferenceController,
              // com.android.settings.development.BluetoothServiceConnectionListener
    public final void onBluetoothServiceConnected(BluetoothA2dp bluetoothA2dp) {
        super.onBluetoothServiceConnected(bluetoothA2dp);
        BluetoothCodecConfig currentCodecConfig = getCurrentCodecConfig();
        if (currentCodecConfig == null) {
            return;
        }
        int codecType = currentCodecConfig.getCodecType();
        BluetoothA2dpConfigStore bluetoothA2dpConfigStore = this.mBluetoothA2dpConfigStore;
        bluetoothA2dpConfigStore.mCodecTypeNative = codecType;
        bluetoothA2dpConfigStore.mSampleRate = currentCodecConfig.getSampleRate();
        bluetoothA2dpConfigStore.mBitsPerSample = currentCodecConfig.getBitsPerSample();
        bluetoothA2dpConfigStore.mChannelMode = currentCodecConfig.getChannelMode();
        bluetoothA2dpConfigStore.mCodecPriority = 1000000;
        bluetoothA2dpConfigStore.mCodecSpecific1Value = currentCodecConfig.getCodecSpecific1();
    }

    public void onIndexUpdated(int i) {
        BluetoothA2dp bluetoothA2dp = this.mBluetoothA2dp;
        if (bluetoothA2dp == null) {
            return;
        }
        writeConfigurationValues(i);
        BluetoothA2dpConfigStore bluetoothA2dpConfigStore = this.mBluetoothA2dpConfigStore;
        bluetoothA2dpConfigStore.getClass();
        BluetoothCodecConfig build =
                new BluetoothCodecConfig.Builder()
                        .setCodecType(bluetoothA2dpConfigStore.mCodecTypeNative)
                        .setCodecPriority(bluetoothA2dpConfigStore.mCodecPriority)
                        .setSampleRate(bluetoothA2dpConfigStore.mSampleRate)
                        .setBitsPerSample(bluetoothA2dpConfigStore.mBitsPerSample)
                        .setChannelMode(bluetoothA2dpConfigStore.mChannelMode)
                        .setCodecSpecific1(bluetoothA2dpConfigStore.mCodecSpecific1Value)
                        .setCodecSpecific2(0L)
                        .setCodecSpecific3(0L)
                        .setCodecSpecific4(0L)
                        .build();
        BluetoothDevice a2dpActiveDevice = getA2dpActiveDevice();
        if (a2dpActiveDevice != null) {
            bluetoothA2dp.setCodecConfigPreference(a2dpActiveDevice, build);
        }
        Preference preference = this.mPreference;
        preference.setSummary(((BaseBluetoothDialogPreference) preference).generateSummary(i));
    }

    public abstract void writeConfigurationValues(int i);

    public void onHDAudioEnabled() {}
}
