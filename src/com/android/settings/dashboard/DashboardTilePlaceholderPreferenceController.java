package com.android.settings.dashboard;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DashboardTilePlaceholderPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin {
    public int mOrder;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        Preference findPreference = preferenceScreen.findPreference("dashboard_tile_placeholder");
        if (findPreference != null) {
            this.mOrder = findPreference.getOrder();
            preferenceScreen.removePreference(findPreference);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "dashboard_tile_placeholder";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return false;
    }
}
