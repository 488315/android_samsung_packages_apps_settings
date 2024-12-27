package com.android.settings.development;

import android.content.Context;

import androidx.preference.Preference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.development.AbstractLogpersistPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class LogPersistPreferenceController extends AbstractLogpersistPreferenceController
        implements PreferenceControllerMixin {
    public final DevelopmentSettingsDashboardFragment mFragment;

    public LogPersistPreferenceController(
            Context context,
            DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment,
            Lifecycle lifecycle) {
        super(context, lifecycle);
        this.mFragment = developmentSettingsDashboardFragment;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        writeLogpersistOption(null, true);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        updateLogpersistValues();
    }
}
