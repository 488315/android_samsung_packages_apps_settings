package com.android.settings.development;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.SystemProperties;
import android.sysprop.BluetoothProperties;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

import java.util.Optional;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothLeAudioPreferenceController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    static final String LE_AUDIO_SWITCHER_DISABLED_PROPERTY =
            "persist.bluetooth.leaudio_switcher.disabled";
    BluetoothAdapter mBluetoothAdapter;
    boolean mChanged;
    public final DevelopmentSettingsDashboardFragment mFragment;

    public BluetoothLeAudioPreferenceController(
            Context context,
            DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment) {
        super(context);
        this.mChanged = false;
        this.mFragment = developmentSettingsDashboardFragment;
        this.mBluetoothAdapter =
                ((BluetoothManager) context.getSystemService(BluetoothManager.class)).getAdapter();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bluetooth_disable_leaudio";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        Optional isProfileBapUnicastClientEnabled =
                BluetoothProperties.isProfileBapUnicastClientEnabled();
        Boolean bool = Boolean.FALSE;
        return ((Boolean) isProfileBapUnicastClientEnabled.orElse(bool)).booleanValue()
                && !((Boolean)
                                BluetoothProperties.isProfileBapBroadcastSourceEnabled()
                                        .orElse(bool))
                        .booleanValue();
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        BluetoothRebootDialog.show(this.mFragment);
        this.mChanged = true;
        return false;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        if (this.mBluetoothAdapter == null) {
            return;
        }
        boolean z = SystemProperties.getBoolean("ro.bluetooth.leaudio_switcher.supported", false);
        int isLeAudioSupported = this.mBluetoothAdapter.isLeAudioSupported();
        ((TwoStatePreference) this.mPreference).setChecked(!(isLeAudioSupported == 10));
        if (isLeAudioSupported == 1 || !z) {
            this.mPreference.setEnabled(false);
        }
    }
}
