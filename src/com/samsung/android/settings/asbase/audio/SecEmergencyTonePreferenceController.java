package com.samsung.android.settings.asbase.audio;

import androidx.preference.Preference;
import androidx.preference.SecDropDownPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.notification.SettingPrefController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecEmergencyTonePreferenceController extends SettingPrefController {
    @Override // com.android.settings.notification.SettingPrefController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        SecDropDownPreference secDropDownPreference = (SecDropDownPreference) preference;
        secDropDownPreference.getClass();
        SecPreferenceUtils.applySummaryColor(secDropDownPreference, true);
    }
}
