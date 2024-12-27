package com.android.settings.notification.zen;

import android.content.Context;

import androidx.preference.Preference;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class LifeStyleZenModePeoplePreferenceController
        extends AbstractZenModePreferenceController {
    public final String KEY;

    public LifeStyleZenModePeoplePreferenceController(Context context, Lifecycle lifecycle) {
        super(context, "lifestyle_mode_behavior_people", lifecycle);
        this.KEY = "lifestyle_mode_behavior_people";
        BixbyRoutineActionHandler.getInstance().getClass();
    }

    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return this.KEY;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        preference.setEnabled(true);
        SecPreferenceUtils.applySummaryColor((SecPreference) preference, true);
        preference.setSummary(BixbyRoutineActionHandler.calculatePeopleSummary(this.mContext));
        BixbyRoutineActionHandler.setPeoplesummary(this.mContext);
    }
}
