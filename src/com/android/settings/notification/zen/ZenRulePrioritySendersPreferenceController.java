package com.android.settings.notification.zen;

import android.app.AutomaticZenRule;
import android.content.Context;
import android.os.AsyncTask;
import android.service.notification.ZenPolicy;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.notification.NotificationBackend;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenRulePrioritySendersPreferenceController
        extends AbstractZenCustomRulePreferenceController {
    public final ZenPrioritySendersHelper mHelper;
    public final boolean mIsMessages;
    public PreferenceCategory mPreferenceCategory;

    @VisibleForTesting SelectorWithWidgetPreference.OnClickListener mSelectorClickListener;

    public ZenRulePrioritySendersPreferenceController(
            Context context, Lifecycle lifecycle, NotificationBackend notificationBackend) {
        super(context, "zen_mode_settings_category_messages", lifecycle);
        this.mSelectorClickListener =
                new SelectorWithWidgetPreference
                        .OnClickListener() { // from class:
                                             // com.android.settings.notification.zen.ZenRulePrioritySendersPreferenceController.2
                    @Override // com.android.settingslib.widget.SelectorWithWidgetPreference.OnClickListener
                    public final void onRadioButtonClicked(
                            SelectorWithWidgetPreference selectorWithWidgetPreference) {
                        ZenRulePrioritySendersPreferenceController
                                zenRulePrioritySendersPreferenceController =
                                        ZenRulePrioritySendersPreferenceController.this;
                        AutomaticZenRule automaticZenRule =
                                zenRulePrioritySendersPreferenceController.mRule;
                        if (automaticZenRule == null || automaticZenRule.getZenPolicy() == null) {
                            return;
                        }
                        ZenPrioritySendersHelper zenPrioritySendersHelper =
                                zenRulePrioritySendersPreferenceController.mHelper;
                        int prioritySenders =
                                zenRulePrioritySendersPreferenceController.getPrioritySenders();
                        AutomaticZenRule automaticZenRule2 =
                                zenRulePrioritySendersPreferenceController.mRule;
                        int[] iArr =
                                zenPrioritySendersHelper.settingsToSaveOnClick(
                                        selectorWithWidgetPreference,
                                        prioritySenders,
                                        (automaticZenRule2 == null
                                                        || automaticZenRule2.getZenPolicy() == null)
                                                ? -10
                                                : zenRulePrioritySendersPreferenceController
                                                        .mRule
                                                        .getZenPolicy()
                                                        .getPriorityConversationSenders());
                        int i = iArr[0];
                        int i2 = iArr[1];
                        if (i == -10 && i2 == -10) {
                            return;
                        }
                        boolean z = zenRulePrioritySendersPreferenceController.mIsMessages;
                        if (i != -10) {
                            if (z) {
                                zenRulePrioritySendersPreferenceController.mRule.setZenPolicy(
                                        new ZenPolicy.Builder(
                                                        zenRulePrioritySendersPreferenceController
                                                                .mRule.getZenPolicy())
                                                .allowMessages(
                                                        ZenModeBackend
                                                                .getZenPolicySettingFromPrefKey(
                                                                        ZenModeBackend
                                                                                .getKeyFromSetting(
                                                                                        i)))
                                                .build());
                            } else {
                                zenRulePrioritySendersPreferenceController.mRule.setZenPolicy(
                                        new ZenPolicy.Builder(
                                                        zenRulePrioritySendersPreferenceController
                                                                .mRule.getZenPolicy())
                                                .allowCalls(
                                                        ZenModeBackend
                                                                .getZenPolicySettingFromPrefKey(
                                                                        ZenModeBackend
                                                                                .getKeyFromSetting(
                                                                                        i)))
                                                .build());
                            }
                        }
                        if (z && i2 != -10) {
                            zenRulePrioritySendersPreferenceController.mRule.setZenPolicy(
                                    new ZenPolicy.Builder(
                                                    zenRulePrioritySendersPreferenceController.mRule
                                                            .getZenPolicy())
                                            .allowConversations(i2)
                                            .build());
                        }
                        zenRulePrioritySendersPreferenceController.mBackend.updateZenRule(
                                zenRulePrioritySendersPreferenceController.mId,
                                zenRulePrioritySendersPreferenceController.mRule);
                    }
                };
        this.mIsMessages = true;
        this.mHelper =
                new ZenPrioritySendersHelper(
                        context,
                        true,
                        this.mBackend,
                        notificationBackend,
                        this.mSelectorClickListener);
    }

    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        PreferenceCategory preferenceCategory =
                (PreferenceCategory) preferenceScreen.findPreference(this.KEY);
        this.mPreferenceCategory = preferenceCategory;
        this.mHelper.displayPreference(preferenceCategory);
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return this.KEY;
    }

    public final int getPrioritySenders() {
        AutomaticZenRule automaticZenRule = this.mRule;
        if (automaticZenRule == null || automaticZenRule.getZenPolicy() == null) {
            return -10;
        }
        if (this.mIsMessages) {
            int priorityMessageSenders = this.mRule.getZenPolicy().getPriorityMessageSenders();
            String str = ZenModeBackend.ZEN_MODE_FROM_NONE;
            if (priorityMessageSenders == 1) {
                return 0;
            }
            if (priorityMessageSenders != 2) {
                return priorityMessageSenders != 3 ? -1 : 2;
            }
            return 1;
        }
        int priorityCallSenders = this.mRule.getZenPolicy().getPriorityCallSenders();
        String str2 = ZenModeBackend.ZEN_MODE_FROM_NONE;
        if (priorityCallSenders == 1) {
            return 0;
        }
        if (priorityCallSenders != 2) {
            return priorityCallSenders != 3 ? -1 : 2;
        }
        return 1;
    }

    @Override // com.android.settings.notification.zen.AbstractZenCustomRulePreferenceController,
              // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        if (this.mIsMessages) {
            new AsyncTask() { // from class:
                              // com.android.settings.notification.zen.ZenRulePrioritySendersPreferenceController.1
                @Override // android.os.AsyncTask
                public final Object doInBackground(Object[] objArr) {
                    ZenRulePrioritySendersPreferenceController.this.mHelper.updateChannelCounts();
                    return null;
                }

                @Override // android.os.AsyncTask
                public final void onPostExecute(Object obj) {
                    if (((AbstractPreferenceController)
                                            ZenRulePrioritySendersPreferenceController.this)
                                    .mContext
                            == null) {
                        return;
                    }
                    ZenRulePrioritySendersPreferenceController
                            zenRulePrioritySendersPreferenceController =
                                    ZenRulePrioritySendersPreferenceController.this;
                    zenRulePrioritySendersPreferenceController.updateState(
                            zenRulePrioritySendersPreferenceController.mPreferenceCategory);
                }
            }.execute(new Void[0]);
        }
        this.mHelper.updateSummaries();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        AutomaticZenRule automaticZenRule = this.mRule;
        if (automaticZenRule == null || automaticZenRule.getZenPolicy() == null) {
            return;
        }
        int prioritySenders = getPrioritySenders();
        AutomaticZenRule automaticZenRule2 = this.mRule;
        this.mHelper.updateState(
                prioritySenders,
                (automaticZenRule2 == null || automaticZenRule2.getZenPolicy() == null)
                        ? -10
                        : this.mRule.getZenPolicy().getPriorityConversationSenders());
    }
}
