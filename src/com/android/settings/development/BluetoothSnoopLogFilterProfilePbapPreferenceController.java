package com.android.settings.development;

import android.content.Context;
import android.sysprop.BluetoothProperties;

import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothSnoopLogFilterProfilePbapPreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    static final int BTSNOOP_LOG_PROFILE_FILTER_MODE_DISABLED_INDEX = 0;
    static final int BTSNOOP_LOG_PROFILE_FILTER_MODE_FULL_FILTER_INDEX = 3;
    static final int BTSNOOP_LOG_PROFILE_FILTER_MODE_HEADER_INDEX = 2;
    static final int BTSNOOP_LOG_PROFILE_FILTER_MODE_MAGIC_INDEX = 1;
    public final String[] mListEntries;
    public final String[] mListValues;
    public final String mProfilesFilterDisabledEntry;

    public BluetoothSnoopLogFilterProfilePbapPreferenceController(Context context) {
        super(context);
        this.mListValues =
                context.getResources()
                        .getStringArray(R.array.bt_hci_snoop_log_profile_filter_values);
        this.mListEntries =
                context.getResources()
                        .getStringArray(R.array.bt_hci_snoop_log_profile_filter_entries);
        this.mProfilesFilterDisabledEntry =
                context.getResources()
                        .getString(R.string.bt_hci_snoop_log_filtered_mode_disabled_summary);
    }

    public static boolean isSnoopLogModeFilteredEnabled() {
        return BluetoothProperties.snoop_log_mode()
                        .orElse(BluetoothProperties.snoop_log_mode_values.DISABLED)
                == BluetoothProperties.snoop_log_mode_values.FILTERED;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bt_hci_snoop_log_filter_pbap";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        BluetoothProperties.snoop_log_filter_profile_pbap(
                BluetoothProperties.snoop_log_filter_profile_pbap_values.DISABLED);
        ((ListPreference) this.mPreference).setValue(this.mListValues[0]);
        ((ListPreference) this.mPreference).setSummary(this.mListEntries[0]);
        this.mPreference.setEnabled(false);
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchEnabled() {
        super.onDeveloperOptionsSwitchEnabled();
        this.mPreference.setEnabled(isSnoopLogModeFilteredEnabled());
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        BluetoothProperties.snoop_log_filter_profile_pbap(
                BluetoothProperties.snoop_log_filter_profile_pbap_values.valueOf(
                        obj.toString().toUpperCase(Locale.US)));
        updateState(this.mPreference);
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        String[] strArr;
        super.updateState(preference);
        ListPreference listPreference = (ListPreference) preference;
        int i = 0;
        if (!isSnoopLogModeFilteredEnabled()) {
            this.mPreference.setEnabled(false);
            listPreference.setSummary(this.mProfilesFilterDisabledEntry);
            return;
        }
        this.mPreference.setEnabled(true);
        BluetoothProperties.snoop_log_filter_profile_pbap_values
                snoop_log_filter_profile_pbap_valuesVar =
                        (BluetoothProperties.snoop_log_filter_profile_pbap_values)
                                BluetoothProperties.snoop_log_filter_profile_pbap()
                                        .orElse(
                                                BluetoothProperties
                                                        .snoop_log_filter_profile_pbap_values
                                                        .DISABLED);
        int i2 = 0;
        while (true) {
            strArr = this.mListValues;
            if (i2 >= strArr.length) {
                break;
            }
            if (snoop_log_filter_profile_pbap_valuesVar
                    == BluetoothProperties.snoop_log_filter_profile_pbap_values.valueOf(
                            strArr[i2].toUpperCase(Locale.US))) {
                i = i2;
                break;
            }
            i2++;
        }
        listPreference.setValue(strArr[i]);
        listPreference.setSummary(this.mListEntries[i]);
    }
}
