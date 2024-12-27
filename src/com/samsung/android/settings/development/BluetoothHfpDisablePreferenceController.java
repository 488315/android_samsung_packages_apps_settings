package com.samsung.android.settings.development;

import android.bluetooth.BluetoothAdapter;
import android.os.Debug;
import android.os.SystemProperties;
import android.util.Log;

import androidx.appcompat.view.SeslContentViewInsetsCallback$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.SwitchPreference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BluetoothHfpDisablePreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    public static final boolean DBG = Debug.semIsProductDev();

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bluetooth_hfp_disable";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        StringBuilder sb = new StringBuilder("isAvailable :: debug ");
        boolean z = DBG;
        SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                sb, z, "BluetoothHfpDisablePreferenceController");
        return z;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsEnabled() {
        super.onDeveloperOptionsEnabled();
        StringBuilder sb = new StringBuilder("onDeveloperOptionsEnabled :: debug ");
        boolean z = DBG;
        SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                sb, z, "BluetoothHfpDisablePreferenceController");
        if (z) {
            ((SwitchPreference) this.mPreference)
                    .setChecked(SystemProperties.getBoolean("persist.bluetooth.disablehfp", false));
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        SystemProperties.set("persist.bluetooth.disablehfp", Boolean.toString(booleanValue));
        Log.i(
                "BluetoothHfpDisablePreferenceController",
                "onPreferenceChange :: HFP unuseage set to " + booleanValue);
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null) {
            defaultAdapter.semShutdown();
            defaultAdapter.enable();
        } else {
            Log.e(
                    "BluetoothHfpDisablePreferenceController",
                    "onPreferenceChange :: Failed to get Bluetooth adapter");
        }
        SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                new StringBuilder("onDeveloperOptionsEnabled :: debug "),
                DBG,
                "BluetoothHfpDisablePreferenceController");
        return true;
    }
}
