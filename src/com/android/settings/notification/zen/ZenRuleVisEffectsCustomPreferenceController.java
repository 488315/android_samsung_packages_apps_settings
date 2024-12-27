package com.android.settings.notification.zen;

import android.app.AutomaticZenRule;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenRuleVisEffectsCustomPreferenceController
        extends AbstractZenCustomRulePreferenceController {
    public SelectorWithWidgetPreference mPreference;

    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SelectorWithWidgetPreference selectorWithWidgetPreference =
                (SelectorWithWidgetPreference) preferenceScreen.findPreference(this.KEY);
        this.mPreference = selectorWithWidgetPreference;
        selectorWithWidgetPreference.mListener =
                new SelectorWithWidgetPreference
                        .OnClickListener() { // from class:
                                             // com.android.settings.notification.zen.ZenRuleVisEffectsCustomPreferenceController$$ExternalSyntheticLambda0
                    @Override // com.android.settingslib.widget.SelectorWithWidgetPreference.OnClickListener
                    public final void onRadioButtonClicked(
                            SelectorWithWidgetPreference selectorWithWidgetPreference2) {
                        ZenRuleVisEffectsCustomPreferenceController.this.launchCustomSettings$1();
                    }
                };
        selectorWithWidgetPreference.setExtraWidgetOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.notification.zen.ZenRuleVisEffectsCustomPreferenceController$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ZenRuleVisEffectsCustomPreferenceController.this.launchCustomSettings$1();
                    }
                });
    }

    public final void launchCustomSettings$1() {
        this.mMetricsFeatureProvider.action(this.mContext, 1398, Pair.create(1603, this.mId));
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = ZenCustomRuleBlockedEffectsSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        Bundle bundle = new Bundle();
        bundle.putString("RULE_ID", this.mId);
        launchRequest.mArguments = bundle;
        launchRequest.mSourceMetricsCategory = 1609;
        subSettingLauncher.launch();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        AutomaticZenRule automaticZenRule;
        super.updateState(preference);
        if (this.mId == null
                || (automaticZenRule = this.mRule) == null
                || automaticZenRule.getZenPolicy() == null) {
            return;
        }
        this.mPreference.setChecked(
                (this.mRule.getZenPolicy().shouldHideAllVisualEffects()
                                || this.mRule.getZenPolicy().shouldShowAllVisualEffects())
                        ? false
                        : true);
    }
}
