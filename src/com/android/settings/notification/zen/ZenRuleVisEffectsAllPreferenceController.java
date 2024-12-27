package com.android.settings.notification.zen;

import android.app.AutomaticZenRule;
import android.service.notification.ZenPolicy;
import android.util.Pair;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settingslib.widget.SelectorWithWidgetPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenRuleVisEffectsAllPreferenceController
        extends AbstractZenCustomRulePreferenceController {
    public SelectorWithWidgetPreference mPreference;

    /* renamed from: $r8$lambda$7PljjSzlEv-Tc8h5P4fyvIxbqB0, reason: not valid java name */
    public static /* synthetic */ void m991$r8$lambda$7PljjSzlEvTc8h5P4fyvIxbqB0(
            ZenRuleVisEffectsAllPreferenceController zenRuleVisEffectsAllPreferenceController) {
        zenRuleVisEffectsAllPreferenceController.mMetricsFeatureProvider.action(
                zenRuleVisEffectsAllPreferenceController.mContext,
                1396,
                Pair.create(1603, zenRuleVisEffectsAllPreferenceController.mId));
        zenRuleVisEffectsAllPreferenceController.mRule.setZenPolicy(
                new ZenPolicy.Builder(zenRuleVisEffectsAllPreferenceController.mRule.getZenPolicy())
                        .showAllVisualEffects()
                        .build());
        zenRuleVisEffectsAllPreferenceController.mBackend.updateZenRule(
                zenRuleVisEffectsAllPreferenceController.mId,
                zenRuleVisEffectsAllPreferenceController.mRule);
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
                                             // com.android.settings.notification.zen.ZenRuleVisEffectsAllPreferenceController$$ExternalSyntheticLambda0
                    @Override // com.android.settingslib.widget.SelectorWithWidgetPreference.OnClickListener
                    public final void onRadioButtonClicked(
                            SelectorWithWidgetPreference selectorWithWidgetPreference2) {
                        ZenRuleVisEffectsAllPreferenceController
                                .m991$r8$lambda$7PljjSzlEvTc8h5P4fyvIxbqB0(
                                        ZenRuleVisEffectsAllPreferenceController.this);
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
        this.mPreference.setChecked(this.mRule.getZenPolicy().shouldShowAllVisualEffects());
    }
}
