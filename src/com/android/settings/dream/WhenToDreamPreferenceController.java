package com.android.settings.dream;

import android.content.Context;

import androidx.preference.Preference;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.display.AmbientDisplayAlwaysOnPreferenceController;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.dream.DreamBackend;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WhenToDreamPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin {
    public final DreamBackend mBackend;
    public final boolean mDreamsDisabledByAmbientModeSuppression;
    public final boolean mDreamsEnabledOnBattery;

    @VisibleForTesting
    public WhenToDreamPreferenceController(Context context, boolean z, boolean z2) {
        super(context);
        this.mBackend = DreamBackend.getInstance(context);
        this.mDreamsDisabledByAmbientModeSuppression = z;
        this.mDreamsEnabledOnBattery = z2;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "when_to_start";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        if (this.mDreamsDisabledByAmbientModeSuppression
                && AmbientDisplayAlwaysOnPreferenceController.isAodSuppressedByBedtime(
                        this.mContext)) {
            preference.setSummary(R.string.screensaver_settings_when_to_dream_bedtime);
            return;
        }
        int whenToDreamSetting = this.mBackend.getWhenToDreamSetting();
        BaseSearchIndexProvider baseSearchIndexProvider = DreamSettings.SEARCH_INDEX_DATA_PROVIDER;
        preference.setSummary(
                whenToDreamSetting != 0
                        ? whenToDreamSetting != 1
                                ? whenToDreamSetting != 2
                                        ? R.string.screensaver_settings_summary_never
                                        : R.string.screensaver_settings_summary_either_long
                                : this.mDreamsEnabledOnBattery
                                        ? R.string.screensaver_settings_summary_dock
                                        : R.string.screensaver_settings_summary_dock_and_charging
                        : R.string.screensaver_settings_summary_sleep);
    }
}
