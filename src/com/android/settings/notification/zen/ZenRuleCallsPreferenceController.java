package com.android.settings.notification.zen;

import android.app.AutomaticZenRule;
import android.content.Context;
import android.service.notification.ZenPolicy;
import android.text.TextUtils;
import android.util.Pair;

import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settingslib.core.lifecycle.Lifecycle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenRuleCallsPreferenceController
        extends AbstractZenCustomRulePreferenceController
        implements Preference.OnPreferenceChangeListener {
    public final String[] mListValues;

    public ZenRuleCallsPreferenceController(Context context, Lifecycle lifecycle) {
        super(context, "zen_mode_calls", lifecycle);
        this.mListValues = context.getResources().getStringArray(R.array.zen_mode_contacts_values);
    }

    public int getIndexOfSendersValue(String str) {
        int i = 0;
        while (true) {
            String[] strArr = this.mListValues;
            if (i >= strArr.length) {
                return 3;
            }
            if (TextUtils.equals(str, strArr[i])) {
                return i;
            }
            i++;
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        int zenPolicySettingFromPrefKey =
                ZenModeBackend.getZenPolicySettingFromPrefKey(obj.toString());
        this.mMetricsFeatureProvider.action(
                this.mContext,
                170,
                Pair.create(1602, Integer.valueOf(zenPolicySettingFromPrefKey)),
                Pair.create(1603, this.mId));
        this.mRule.setZenPolicy(
                new ZenPolicy.Builder(this.mRule.getZenPolicy())
                        .allowCalls(zenPolicySettingFromPrefKey)
                        .build());
        this.mBackend.updateZenRule(this.mId, this.mRule);
        updateFromContactsValue(preference);
        return true;
    }

    public final void updateFromContactsValue(Preference preference) {
        AutomaticZenRule automaticZenRule = this.mRule;
        if (automaticZenRule == null || automaticZenRule.getZenPolicy() == null) {
            return;
        }
        ListPreference listPreference = (ListPreference) preference;
        ZenPolicy zenPolicy = this.mRule.getZenPolicy();
        this.mBackend.getClass();
        int priorityCallSenders = zenPolicy.getPriorityCallSenders();
        listPreference.setSummary(
                priorityCallSenders != 1
                        ? priorityCallSenders != 2
                                ? priorityCallSenders != 3
                                        ? R.string.zen_mode_none_calls
                                        : R.string.zen_mode_from_starred
                                : R.string.zen_mode_from_contacts
                        : R.string.zen_mode_from_anyone);
        int priorityCallSenders2 = this.mRule.getZenPolicy().getPriorityCallSenders();
        listPreference.setValue(
                this.mListValues[
                        getIndexOfSendersValue(
                                priorityCallSenders2 != 1
                                        ? priorityCallSenders2 != 2
                                                ? priorityCallSenders2 != 3
                                                        ? ZenModeBackend.ZEN_MODE_FROM_NONE
                                                        : "zen_mode_from_starred"
                                                : "zen_mode_from_contacts"
                                        : "zen_mode_from_anyone")]);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        updateFromContactsValue(preference);
    }
}
