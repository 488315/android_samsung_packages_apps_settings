package com.android.settings.notification.zen;

import android.util.Pair;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settingslib.widget.SelectorWithWidgetPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenRuleDefaultPolicyPreferenceController
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
                                             // com.android.settings.notification.zen.ZenRuleDefaultPolicyPreferenceController$$ExternalSyntheticLambda0
                    @Override // com.android.settingslib.widget.SelectorWithWidgetPreference.OnClickListener
                    public final void onRadioButtonClicked(
                            SelectorWithWidgetPreference selectorWithWidgetPreference2) {
                        ZenRuleDefaultPolicyPreferenceController
                                zenRuleDefaultPolicyPreferenceController =
                                        ZenRuleDefaultPolicyPreferenceController.this;
                        zenRuleDefaultPolicyPreferenceController.mRule.setZenPolicy(null);
                        zenRuleDefaultPolicyPreferenceController.mBackend.updateZenRule(
                                zenRuleDefaultPolicyPreferenceController.mId,
                                zenRuleDefaultPolicyPreferenceController.mRule);
                    }
                };
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        if (this.mId == null || this.mRule == null) {
            return;
        }
        this.mMetricsFeatureProvider.action(this.mContext, 1606, Pair.create(1603, this.mId));
        this.mPreference.setChecked(this.mRule.getZenPolicy() == null);
    }
}
