package com.android.settings.notification.zen;

import android.app.AutomaticZenRule;
import android.content.Context;
import android.service.notification.ZenPolicy;
import android.util.Log;
import android.util.Pair;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;
import com.android.settingslib.core.lifecycle.Lifecycle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenRuleRepeatCallersPreferenceController
        extends AbstractZenCustomRulePreferenceController
        implements Preference.OnPreferenceChangeListener {
    public final int mRepeatCallersThreshold;

    public ZenRuleRepeatCallersPreferenceController(Context context, Lifecycle lifecycle, int i) {
        super(context, "zen_mode_repeat_callers", lifecycle);
        this.mRepeatCallersThreshold = i;
    }

    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        preferenceScreen
                .findPreference(this.KEY)
                .setSummary(
                        this.mContext.getString(
                                R.string.zen_mode_repeat_callers_summary,
                                Integer.valueOf(this.mRepeatCallersThreshold)));
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        if (ZenModeSettingsBase.DEBUG) {
            Log.d(
                    "PrefControllerMixin",
                    this.KEY
                            + " onPrefChange mRule="
                            + this.mRule
                            + " mCategory=4 allow="
                            + booleanValue);
        }
        this.mMetricsFeatureProvider.action(
                this.mContext,
                171,
                Pair.create(1602, Integer.valueOf(booleanValue ? 1 : 0)),
                Pair.create(1603, this.mId));
        this.mRule.setZenPolicy(
                new ZenPolicy.Builder(this.mRule.getZenPolicy())
                        .allowRepeatCallers(booleanValue)
                        .build());
        this.mBackend.updateZenRule(this.mId, this.mRule);
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        AutomaticZenRule automaticZenRule = this.mRule;
        if (automaticZenRule == null || automaticZenRule.getZenPolicy() == null) {
            return;
        }
        TwoStatePreference twoStatePreference = (TwoStatePreference) preference;
        if (this.mRule.getZenPolicy().getPriorityCallSenders() == 1) {
            twoStatePreference.setEnabled(false);
            twoStatePreference.setChecked(true);
        } else {
            twoStatePreference.setEnabled(true);
            twoStatePreference.setChecked(
                    this.mRule.getZenPolicy().getPriorityCategoryRepeatCallers() == 1);
        }
    }
}
