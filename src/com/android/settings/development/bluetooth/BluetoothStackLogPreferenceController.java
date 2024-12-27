package com.android.settings.development.bluetooth;

import android.content.Context;
import android.os.SystemProperties;
import android.util.Log;

import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothStackLogPreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    static final String BLUETOOTH_BTSTACK_LOG_MODE_PROPERTY_PERSIST = "persist.log.tag.bluetooth";
    static final int BTSTACK_LOG_MODE_DEBUG_INDEX = 1;
    static final int BTSTACK_LOG_MODE_ERROR_INDEX = 4;
    static final int BTSTACK_LOG_MODE_INFO_INDEX = 2;
    static final int BTSTACK_LOG_MODE_VERBOSE_INDEX = 0;
    static final int BTSTACK_LOG_MODE_WARN_INDEX = 3;
    public final String[] mListEntries;
    public final String[] mListValues;

    public BluetoothStackLogPreferenceController(Context context) {
        super(context);
        this.mListValues = context.getResources().getStringArray(R.array.bt_stack_log_level_values);
        this.mListEntries =
                context.getResources().getStringArray(R.array.sec_bt_stack_log_level_entries);
    }

    public int getBluetoothLogLevelIndex() {
        if (Log.isLoggable("bluetooth", 2)) {
            return 0;
        }
        if (Log.isLoggable("bluetooth", 3)) {
            return 1;
        }
        if (Log.isLoggable("bluetooth", 4)) {
            return 2;
        }
        if (Log.isLoggable("bluetooth", 5)) {
            return 3;
        }
        return Log.isLoggable("bluetooth", 6) ? 4 : 2;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bt_stack_log_level";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        SystemProperties.set(BLUETOOTH_BTSTACK_LOG_MODE_PROPERTY_PERSIST, (String) null);
        SystemProperties.set("log.tag.bluetooth", (String) null);
        ((ListPreference) this.mPreference).setValue(this.mListValues[2]);
        ((ListPreference) this.mPreference).setSummary(this.mListEntries[2]);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        SystemProperties.set(BLUETOOTH_BTSTACK_LOG_MODE_PROPERTY_PERSIST, obj.toString());
        SystemProperties.set("log.tag.bluetooth", obj.toString());
        updateState(this.mPreference);
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        ListPreference listPreference = (ListPreference) preference;
        int bluetoothLogLevelIndex = getBluetoothLogLevelIndex();
        listPreference.setValue(this.mListValues[bluetoothLogLevelIndex]);
        listPreference.setSummary(this.mListEntries[bluetoothLogLevelIndex]);
    }
}
