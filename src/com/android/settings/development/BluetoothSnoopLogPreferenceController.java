package com.android.settings.development;

import android.content.Context;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothSnoopLogPreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    static final String BLUETOOTH_BTSNOOP_LOG_MODE_PROPERTY = "persist.bluetooth.btsnooplogmode";
    static final int BTSNOOP_LOG_MODE_DISABLED_INDEX = 0;
    static final int BTSNOOP_LOG_MODE_FILTERED_INDEX = 1;
    static final int BTSNOOP_LOG_MODE_FULL_INDEX = 2;
    public final DevelopmentSettingsDashboardFragment mFragment;
    public final String[] mListEntries;
    public final String[] mListValues;

    public BluetoothSnoopLogPreferenceController(
            Context context,
            DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment) {
        super(context);
        this.mListValues = context.getResources().getStringArray(R.array.bt_hci_snoop_log_values);
        this.mListEntries = context.getResources().getStringArray(R.array.bt_hci_snoop_log_entries);
        this.mFragment = developmentSettingsDashboardFragment;
    }

    public final int getDefaultModeIndex() {
        String string =
                Settings.Global.getString(
                        this.mContext.getContentResolver(), "bluetooth_btsnoop_default_mode");
        int i = 0;
        while (true) {
            String[] strArr = this.mListValues;
            if (i >= strArr.length) {
                return 0;
            }
            if (TextUtils.equals(string, strArr[i])) {
                return i;
            }
            i++;
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bt_hci_snoop_log";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        SystemProperties.set(BLUETOOTH_BTSNOOP_LOG_MODE_PROPERTY, (String) null);
        ((ListPreference) this.mPreference).setValue(this.mListValues[getDefaultModeIndex()]);
        ((ListPreference) this.mPreference).setSummary(this.mListEntries[getDefaultModeIndex()]);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        SystemProperties.set(BLUETOOTH_BTSNOOP_LOG_MODE_PROPERTY, obj.toString());
        updateState(this.mPreference);
        DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment = this.mFragment;
        if (developmentSettingsDashboardFragment == null) {
            return true;
        }
        BluetoothSnoopLogFilterProfileMapPreferenceController
                bluetoothSnoopLogFilterProfileMapPreferenceController =
                        (BluetoothSnoopLogFilterProfileMapPreferenceController)
                                developmentSettingsDashboardFragment
                                        .getDevelopmentOptionsController(
                                                BluetoothSnoopLogFilterProfileMapPreferenceController
                                                        .class);
        BluetoothSnoopLogFilterProfilePbapPreferenceController
                bluetoothSnoopLogFilterProfilePbapPreferenceController =
                        (BluetoothSnoopLogFilterProfilePbapPreferenceController)
                                developmentSettingsDashboardFragment
                                        .getDevelopmentOptionsController(
                                                BluetoothSnoopLogFilterProfilePbapPreferenceController
                                                        .class);
        bluetoothSnoopLogFilterProfileMapPreferenceController.updateState(
                bluetoothSnoopLogFilterProfileMapPreferenceController.mPreference);
        bluetoothSnoopLogFilterProfilePbapPreferenceController.updateState(
                bluetoothSnoopLogFilterProfilePbapPreferenceController.mPreference);
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        String[] strArr;
        ListPreference listPreference = (ListPreference) preference;
        String str = SystemProperties.get(BLUETOOTH_BTSNOOP_LOG_MODE_PROPERTY);
        int defaultModeIndex = getDefaultModeIndex();
        int i = 0;
        while (true) {
            strArr = this.mListValues;
            if (i >= strArr.length) {
                break;
            }
            if (TextUtils.equals(str, strArr[i])) {
                defaultModeIndex = i;
                break;
            }
            i++;
        }
        listPreference.setValue(strArr[defaultModeIndex]);
        listPreference.setSummary(this.mListEntries[defaultModeIndex]);
    }
}
