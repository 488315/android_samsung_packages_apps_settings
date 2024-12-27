package com.android.settings.development;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.sysprop.BluetoothProperties;
import android.text.TextUtils;

import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothLeAudioModePreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    BluetoothAdapter mBluetoothAdapter;
    public boolean mChanged;
    public final DevelopmentSettingsDashboardFragment mFragment;
    public final String[] mListSummaries;
    public final String[] mListValues;
    String mNewMode;

    public BluetoothLeAudioModePreferenceController(
            Context context,
            DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment) {
        super(context);
        this.mChanged = false;
        this.mFragment = developmentSettingsDashboardFragment;
        this.mBluetoothAdapter =
                ((BluetoothManager) context.getSystemService(BluetoothManager.class)).getAdapter();
        this.mListValues =
                context.getResources().getStringArray(R.array.bluetooth_leaudio_mode_values);
        this.mListSummaries = context.getResources().getStringArray(R.array.bluetooth_leaudio_mode);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bluetooth_leaudio_mode";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return ((Boolean)
                        BluetoothProperties.isProfileBapBroadcastSourceEnabled()
                                .orElse(Boolean.FALSE))
                .booleanValue();
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment = this.mFragment;
        if (developmentSettingsDashboardFragment == null) {
            return false;
        }
        BluetoothRebootDialog.show(developmentSettingsDashboardFragment);
        this.mChanged = true;
        this.mNewMode = obj.toString();
        return false;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        String[] strArr;
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter == null) {
            return;
        }
        String str =
                bluetoothAdapter.isLeAudioBroadcastSourceSupported() == 10
                        ? "broadcast"
                        : this.mBluetoothAdapter.isLeAudioSupported() == 10
                                ? "unicast"
                                : "disabled";
        int i = 0;
        while (true) {
            strArr = this.mListValues;
            if (i >= strArr.length) {
                i = 0;
                break;
            } else if (TextUtils.equals(str, strArr[i])) {
                break;
            } else {
                i++;
            }
        }
        ListPreference listPreference = (ListPreference) preference;
        listPreference.setValue(strArr[i]);
        listPreference.setSummary(this.mListSummaries[i]);
        if (this.mBluetoothAdapter.isEnabled()) {
            return;
        }
        listPreference.setEnabled(false);
    }
}
