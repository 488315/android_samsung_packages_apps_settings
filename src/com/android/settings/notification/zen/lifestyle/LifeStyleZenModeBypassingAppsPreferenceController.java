package com.android.settings.notification.zen.lifestyle;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.notification.zen.AbstractZenModePreferenceController;

import com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class LifeStyleZenModeBypassingAppsPreferenceController
        extends AbstractZenModePreferenceController {

    @VisibleForTesting protected SecPreference mPreference;

    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        SecPreference secPreference =
                (SecPreference) preferenceScreen.findPreference("lifestyle_mode_add_apps");
        this.mPreference = secPreference;
        secPreference.getClass();
        SecPreferenceUtils.applySummaryColor(secPreference, true);
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "lifestyle_mode_add_apps";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        BixbyRoutineActionHandler.setAppSummary(this.mContext);
        preference.setSummary(BixbyRoutineActionHandler.app_description);
    }
}
