package com.android.settings.notification.zen;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settingslib.widget.SelectorWithWidgetPreference;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeVisEffectsNonePreferenceController
        extends AbstractZenModePreferenceController
        implements SelectorWithWidgetPreference.OnClickListener {
    public SelectorWithWidgetPreference mPreference;

    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SelectorWithWidgetPreference selectorWithWidgetPreference =
                (SelectorWithWidgetPreference) preferenceScreen.findPreference(this.KEY);
        this.mPreference = selectorWithWidgetPreference;
        selectorWithWidgetPreference.mListener = this;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.settingslib.widget.SelectorWithWidgetPreference.OnClickListener
    public final void onRadioButtonClicked(
            SelectorWithWidgetPreference selectorWithWidgetPreference) {
        this.mMetricsFeatureProvider.action(this.mContext, 1396, true);
        this.mBackend.saveVisualEffectsPolicy(FileType.SASF, false);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        this.mPreference.setChecked(this.mBackend.mPolicy.suppressedVisualEffects == 0);
    }
}
