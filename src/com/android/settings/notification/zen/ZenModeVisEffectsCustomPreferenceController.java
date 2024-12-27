package com.android.settings.notification.zen;

import android.app.NotificationManager;
import android.view.View;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeVisEffectsCustomPreferenceController
        extends AbstractZenModePreferenceController {
    public SelectorWithWidgetPreference mPreference;

    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SelectorWithWidgetPreference selectorWithWidgetPreference =
                (SelectorWithWidgetPreference) preferenceScreen.findPreference(this.KEY);
        this.mPreference = selectorWithWidgetPreference;
        selectorWithWidgetPreference.setExtraWidgetOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.notification.zen.ZenModeVisEffectsCustomPreferenceController$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ZenModeVisEffectsCustomPreferenceController.this.launchCustomSettings$2();
                    }
                });
        this.mPreference.mListener =
                new SelectorWithWidgetPreference
                        .OnClickListener() { // from class:
                                             // com.android.settings.notification.zen.ZenModeVisEffectsCustomPreferenceController$$ExternalSyntheticLambda1
                    @Override // com.android.settingslib.widget.SelectorWithWidgetPreference.OnClickListener
                    public final void onRadioButtonClicked(
                            SelectorWithWidgetPreference selectorWithWidgetPreference2) {
                        ZenModeVisEffectsCustomPreferenceController.this.launchCustomSettings$2();
                    }
                };
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    public final void launchCustomSettings$2() {
        this.mMetricsFeatureProvider.action(this.mContext, 1399, true);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = ZenModeBlockedEffectsSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        subSettingLauncher.setTitleRes(R.string.zen_mode_what_to_block_title, null);
        launchRequest.mSourceMetricsCategory = 1400;
        subSettingLauncher.launch();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        SelectorWithWidgetPreference selectorWithWidgetPreference = this.mPreference;
        ZenModeBackend zenModeBackend = this.mBackend;
        boolean areAllVisualEffectsSuppressed =
                NotificationManager.Policy.areAllVisualEffectsSuppressed(
                        zenModeBackend.mPolicy.suppressedVisualEffects);
        boolean z = false;
        boolean z2 = zenModeBackend.mPolicy.suppressedVisualEffects == 0;
        if (!areAllVisualEffectsSuppressed && !z2) {
            z = true;
        }
        selectorWithWidgetPreference.setChecked(z);
    }
}
