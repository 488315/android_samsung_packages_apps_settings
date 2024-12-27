package com.android.settings.development;

import android.content.Context;
import android.os.SystemProperties;
import android.text.TextUtils;

import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothAvrcpVersionPreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    static final String BLUETOOTH_AVRCP_VERSION_PROPERTY = "persist.bluetooth.avrcpversion";
    public final String[] mListSummaries;
    public final String[] mListValues;

    public BluetoothAvrcpVersionPreferenceController(Context context) {
        super(context);
        this.mListValues =
                context.getResources().getStringArray(R.array.bluetooth_avrcp_version_values);
        this.mListSummaries =
                context.getResources().getStringArray(R.array.bluetooth_avrcp_versions);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bluetooth_select_avrcp_version";
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        SystemProperties.set(BLUETOOTH_AVRCP_VERSION_PROPERTY, obj.toString());
        updateState(this.mPreference);
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        String[] strArr;
        ListPreference listPreference = (ListPreference) preference;
        String str = SystemProperties.get(BLUETOOTH_AVRCP_VERSION_PROPERTY);
        int i = 0;
        int i2 = 0;
        while (true) {
            strArr = this.mListValues;
            if (i2 >= strArr.length) {
                break;
            }
            if (TextUtils.equals(str, strArr[i2])) {
                i = i2;
                break;
            }
            i2++;
        }
        listPreference.setValue(strArr[i]);
        listPreference.setSummary(this.mListSummaries[i]);
    }
}
