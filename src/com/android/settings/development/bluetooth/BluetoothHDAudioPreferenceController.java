package com.android.settings.development.bluetooth;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.development.DevelopmentSettingsDashboardFragment;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothHDAudioPreferenceController
        extends AbstractBluetoothPreferenceController
        implements Preference.OnPreferenceChangeListener {
    public final AbstractBluetoothPreferenceController.Callback mCallback;

    public BluetoothHDAudioPreferenceController(
            Context context,
            Lifecycle lifecycle,
            AbstractBluetoothPreferenceController.Callback callback) {
        super(context, lifecycle);
        this.mCallback = callback;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bluetooth_hd_audio_settings";
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        BluetoothA2dp bluetoothA2dp = this.mBluetoothA2dp;
        if (bluetoothA2dp == null) {
            this.mPreference.setEnabled(false);
            return true;
        }
        boolean booleanValue = ((Boolean) obj).booleanValue();
        BluetoothDevice a2dpActiveDevice = getA2dpActiveDevice();
        if (a2dpActiveDevice == null) {
            this.mPreference.setEnabled(false);
            return true;
        }
        bluetoothA2dp.setOptionalCodecsEnabled(a2dpActiveDevice, booleanValue ? 1 : 0);
        if (booleanValue) {
            bluetoothA2dp.enableOptionalCodecs(a2dpActiveDevice);
        } else {
            bluetoothA2dp.disableOptionalCodecs(a2dpActiveDevice);
        }
        Iterator it =
                ((ArrayList)
                                ((DevelopmentSettingsDashboardFragment) this.mCallback)
                                        .mPreferenceControllers)
                        .iterator();
        while (it.hasNext()) {
            AbstractPreferenceController abstractPreferenceController =
                    (AbstractPreferenceController) it.next();
            if (abstractPreferenceController
                    instanceof AbstractBluetoothDialogPreferenceController) {
                ((AbstractBluetoothDialogPreferenceController) abstractPreferenceController)
                        .onHDAudioEnabled();
            }
            if (abstractPreferenceController instanceof AbstractBluetoothListPreferenceController) {
                BluetoothCodecListPreferenceController bluetoothCodecListPreferenceController =
                        (BluetoothCodecListPreferenceController)
                                ((AbstractBluetoothListPreferenceController)
                                        abstractPreferenceController);
                if (BluetoothCodecListPreferenceController.DEBUG) {
                    bluetoothCodecListPreferenceController.getClass();
                    Log.d("BtExtCodecCtr", "onHDAudioEnabled: enabled=" + booleanValue);
                }
                ListPreference listPreference =
                        bluetoothCodecListPreferenceController.mListPreference;
                if (listPreference == null) {
                    Log.e("BtExtCodecCtr", "onHDAudioEnabled: List preference is null");
                } else {
                    listPreference.setEnabled(booleanValue);
                }
            }
        }
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        BluetoothA2dp bluetoothA2dp = this.mBluetoothA2dp;
        if (bluetoothA2dp == null) {
            this.mPreference.setEnabled(false);
            return;
        }
        BluetoothDevice a2dpActiveDevice = getA2dpActiveDevice();
        if (a2dpActiveDevice == null) {
            Log.e("BtHDAudioCtr", "Active device is null. To disable HD audio button");
            this.mPreference.setEnabled(false);
            return;
        }
        boolean z = bluetoothA2dp.isOptionalCodecsSupported(a2dpActiveDevice) == 1;
        this.mPreference.setEnabled(z);
        if (z) {
            ((TwoStatePreference) this.mPreference)
                    .setChecked(bluetoothA2dp.isOptionalCodecsEnabled(a2dpActiveDevice) == 1);
        }
    }
}
