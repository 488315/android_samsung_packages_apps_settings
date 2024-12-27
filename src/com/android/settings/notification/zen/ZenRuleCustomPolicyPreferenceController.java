package com.android.settings.notification.zen;

import android.app.AutomaticZenRule;
import android.os.Bundle;
import android.service.notification.ZenAdapters;
import android.service.notification.ZenPolicy;
import android.view.View;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenRuleCustomPolicyPreferenceController
        extends AbstractZenCustomRulePreferenceController {
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
                                             // com.android.settings.notification.zen.ZenRuleCustomPolicyPreferenceController$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ZenRuleCustomPolicyPreferenceController
                                zenRuleCustomPolicyPreferenceController =
                                        ZenRuleCustomPolicyPreferenceController.this;
                        zenRuleCustomPolicyPreferenceController.setCustomPolicy();
                        zenRuleCustomPolicyPreferenceController.launchCustomSettings();
                    }
                });
        this.mPreference.mListener =
                new SelectorWithWidgetPreference
                        .OnClickListener() { // from class:
                                             // com.android.settings.notification.zen.ZenRuleCustomPolicyPreferenceController$$ExternalSyntheticLambda1
                    @Override // com.android.settingslib.widget.SelectorWithWidgetPreference.OnClickListener
                    public final void onRadioButtonClicked(
                            SelectorWithWidgetPreference selectorWithWidgetPreference2) {
                        ZenRuleCustomPolicyPreferenceController
                                zenRuleCustomPolicyPreferenceController =
                                        ZenRuleCustomPolicyPreferenceController.this;
                        zenRuleCustomPolicyPreferenceController.setCustomPolicy();
                        zenRuleCustomPolicyPreferenceController.launchCustomSettings();
                    }
                };
    }

    public final void launchCustomSettings() {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = ZenCustomRuleConfigSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        Bundle bundle = new Bundle();
        bundle.putString("RULE_ID", this.mId);
        launchRequest.mArguments = bundle;
        launchRequest.mSourceMetricsCategory = 1605;
        subSettingLauncher.launch();
    }

    public final void setCustomPolicy() {
        if (this.mRule.getZenPolicy() == null) {
            AutomaticZenRule automaticZenRule = this.mRule;
            ZenPolicy zenPolicy = new ZenPolicy();
            ZenModeBackend zenModeBackend = this.mBackend;
            int prioritySendersToPeopleType =
                    zenModeBackend.mPolicy.allowCalls()
                            ? ZenAdapters.prioritySendersToPeopleType(
                                    zenModeBackend.mPolicy.allowCallsFrom())
                            : 4;
            automaticZenRule.setZenPolicy(
                    new ZenPolicy.Builder(zenPolicy)
                            .allowAlarms(zenModeBackend.mPolicy.allowAlarms())
                            .allowCalls(prioritySendersToPeopleType)
                            .allowEvents(zenModeBackend.mPolicy.allowEvents())
                            .allowMedia(zenModeBackend.mPolicy.allowMedia())
                            .allowMessages(
                                    zenModeBackend.mPolicy.allowMessages()
                                            ? ZenAdapters.prioritySendersToPeopleType(
                                                    zenModeBackend.mPolicy.allowMessagesFrom())
                                            : 4)
                            .allowConversations(
                                    zenModeBackend.mPolicy.allowConversations()
                                            ? zenModeBackend.mPolicy.allowConversationsFrom()
                                            : 3)
                            .allowReminders(zenModeBackend.mPolicy.allowReminders())
                            .allowRepeatCallers(zenModeBackend.mPolicy.allowRepeatCallers())
                            .allowSystem(zenModeBackend.mPolicy.allowSystem())
                            .showFullScreenIntent(zenModeBackend.mPolicy.showFullScreenIntents())
                            .showLights(zenModeBackend.mPolicy.showLights())
                            .showInAmbientDisplay(zenModeBackend.mPolicy.showAmbient())
                            .showInNotificationList(zenModeBackend.mPolicy.showInNotificationList())
                            .showBadges(zenModeBackend.mPolicy.showBadges())
                            .showPeeking(zenModeBackend.mPolicy.showPeeking())
                            .showStatusBarIcons(zenModeBackend.mPolicy.showStatusBarIcons())
                            .build());
            zenModeBackend.updateZenRule(this.mId, this.mRule);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        AutomaticZenRule automaticZenRule;
        super.updateState(preference);
        if (this.mId == null || (automaticZenRule = this.mRule) == null) {
            return;
        }
        this.mPreference.setChecked(automaticZenRule.getZenPolicy() != null);
    }
}
