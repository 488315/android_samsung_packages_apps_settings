package com.android.settings.deviceinfo;

import android.os.SystemProperties;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FccEquipmentIdPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin {
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference("fcc_equipment_id");
        if (findPreference != null) {
            findPreference.setSummary(
                    SystemProperties.get(
                            "ro.ril.fccid",
                            this.mContext.getResources().getString(R.string.device_info_default)));
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "fcc_equipment_id";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return !TextUtils.isEmpty(SystemProperties.get("ro.ril.fccid"));
    }
}
