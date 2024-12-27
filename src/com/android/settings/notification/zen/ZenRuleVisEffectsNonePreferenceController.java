package com.android.settings.notification.zen;

import android.app.AutomaticZenRule;
import android.service.notification.ZenPolicy;
import android.util.Pair;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settingslib.widget.SelectorWithWidgetPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenRuleVisEffectsNonePreferenceController
        extends AbstractZenCustomRulePreferenceController {
    public SelectorWithWidgetPreference mPreference;

    public static /* synthetic */ void $r8$lambda$y0odmMzAD7Xv2Bg1VTqzVQTKWEk(
            ZenRuleVisEffectsNonePreferenceController zenRuleVisEffectsNonePreferenceController) {
        zenRuleVisEffectsNonePreferenceController.mMetricsFeatureProvider.action(
                zenRuleVisEffectsNonePreferenceController.mContext,
                1397,
                Pair.create(1603, zenRuleVisEffectsNonePreferenceController.mId));
        zenRuleVisEffectsNonePreferenceController.mRule.setZenPolicy(
                new ZenPolicy.Builder(
                                zenRuleVisEffectsNonePreferenceController.mRule.getZenPolicy())
                        .hideAllVisualEffects()
                        .build());
        zenRuleVisEffectsNonePreferenceController.mBackend.updateZenRule(
                zenRuleVisEffectsNonePreferenceController.mId,
                zenRuleVisEffectsNonePreferenceController.mRule);
    }

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
                                             // com.android.settings.notification.zen.ZenRuleVisEffectsNonePreferenceController$$ExternalSyntheticLambda0
                    @Override // com.android.settingslib.widget.SelectorWithWidgetPreference.OnClickListener
                    public final void onRadioButtonClicked(
                            SelectorWithWidgetPreference selectorWithWidgetPreference2) {
                        ZenRuleVisEffectsNonePreferenceController
                                .$r8$lambda$y0odmMzAD7Xv2Bg1VTqzVQTKWEk(
                                        ZenRuleVisEffectsNonePreferenceController.this);
                    }
                };
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
        this.mPreference.setChecked(this.mRule.getZenPolicy().shouldHideAllVisualEffects());
    }
}
