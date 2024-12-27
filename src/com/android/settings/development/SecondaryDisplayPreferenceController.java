package com.android.settings.development;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SecondaryDisplayPreferenceController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    public final String[] mListSummaries;
    public final String[] mListValues;

    public SecondaryDisplayPreferenceController(Context context) {
        super(context);
        this.mListValues =
                context.getResources().getStringArray(R.array.overlay_display_devices_values);
        this.mListSummaries =
                context.getResources().getStringArray(R.array.overlay_display_devices_entries);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "overlay_display_devices";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        Settings.Global.putString(
                this.mContext.getContentResolver(), "overlay_display_devices", null);
        updateSecondaryDisplayDevicesOptions();
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Settings.Global.putString(
                this.mContext.getContentResolver(), "overlay_display_devices", obj.toString());
        updateSecondaryDisplayDevicesOptions();
        return true;
    }

    public final void updateSecondaryDisplayDevicesOptions() {
        String[] strArr;
        String string =
                Settings.Global.getString(
                        this.mContext.getContentResolver(), "overlay_display_devices");
        int i = 0;
        int i2 = 0;
        while (true) {
            strArr = this.mListValues;
            if (i2 >= strArr.length) {
                break;
            }
            if (TextUtils.equals(string, strArr[i2])) {
                i = i2;
                break;
            }
            i2++;
        }
        ListPreference listPreference = (ListPreference) this.mPreference;
        listPreference.setValue(strArr[i]);
        listPreference.setSummary(this.mListSummaries[i]);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        updateSecondaryDisplayDevicesOptions();
    }
}
