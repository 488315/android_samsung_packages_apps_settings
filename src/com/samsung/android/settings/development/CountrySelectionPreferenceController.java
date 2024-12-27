package com.samsung.android.settings.development;

import android.os.SemSystemProperties;

import androidx.preference.Preference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.util.SemLog;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class CountrySelectionPreferenceController extends DeveloperOptionsPreferenceController
        implements PreferenceControllerMixin {
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "tss_country_chooser";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        boolean z =
                "true".equals(SemSystemProperties.get("mdc.unified"))
                        && "false".equals(SemSystemProperties.get("mdc.singlesku"));
        SemLog.d("CountrySelectionPreferenceController", "isAvailable() : " + z);
        return z;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        this.mPreference.setSummary(
                new Locale(
                                ApnSettings.MVNO_NONE,
                                SemSystemProperties.get("persist.sys.selected_country_iso"))
                        .getDisplayCountry());
    }
}
