package com.samsung.android.settings.development;

import android.bluetooth.BluetoothAdapter;
import android.os.Debug;
import android.os.SystemProperties;
import android.sysprop.BluetoothProperties;
import android.util.Log;

import androidx.appcompat.view.SeslContentViewInsetsCallback$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.SwitchPreference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BluetoothDualModeDisablePreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    public static final boolean DBG = Debug.semIsProductDev();
    public static final boolean LEAUDIO_ENABLED =
            ((Boolean) BluetoothProperties.isProfileBapUnicastClientEnabled().orElse(Boolean.FALSE))
                    .booleanValue();

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bluetooth_dualmode_disable";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        StringBuilder sb = new StringBuilder("isAvailable :: debug ");
        boolean z = DBG;
        sb.append(z);
        sb.append(", LeAudio enabled ");
        boolean z2 = LEAUDIO_ENABLED;
        SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                sb, z2, "BluetoothDualModeDisablePreferenceController");
        return z && z2;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsEnabled() {
        super.onDeveloperOptionsEnabled();
        StringBuilder sb = new StringBuilder("onDeveloperOptionsEnabled :: debug ");
        boolean z = DBG;
        sb.append(z);
        sb.append(", LeAudio enabled ");
        boolean z2 = LEAUDIO_ENABLED;
        SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                sb, z2, "BluetoothDualModeDisablePreferenceController");
        if (z && z2) {
            ((SwitchPreference) this.mPreference)
                    .setChecked(
                            SystemProperties.getBoolean(
                                    "persist.bluetooth.leaudio.dualmodeoff", false));
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        SystemProperties.set(
                "persist.bluetooth.leaudio.dualmodeoff", Boolean.toString(booleanValue));
        Log.i(
                "BluetoothDualModeDisablePreferenceController",
                "onPreferenceChange :: Dual Mode usage set to " + booleanValue);
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null) {
            Log.e(
                    "BluetoothDualModeDisablePreferenceController",
                    "onPreferenceChange :: Failed to get Bluetooth adapter");
            return true;
        }
        defaultAdapter.semShutdown();
        defaultAdapter.enable();
        return true;
    }
}
