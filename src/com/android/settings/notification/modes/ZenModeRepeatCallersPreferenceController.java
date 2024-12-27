package com.android.settings.notification.modes;

import android.content.Context;
import android.service.notification.ZenPolicy;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;

import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeRepeatCallersPreferenceController
        extends AbstractZenModePreferenceController
        implements Preference.OnPreferenceChangeListener {
    public final int mRepeatCallersThreshold;

    public ZenModeRepeatCallersPreferenceController(
            Context context, ZenModesBackend zenModesBackend, int i) {
        super(context, "zen_mode_repeat_callers", zenModesBackend);
        this.mRepeatCallersThreshold = i;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        final boolean booleanValue = ((Boolean) obj).booleanValue();
        return savePolicy(
                new Function() { // from class:
                                 // com.android.settings.notification.modes.ZenModeRepeatCallersPreferenceController$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj2) {
                        return ((ZenPolicy.Builder) obj2).allowRepeatCallers(booleanValue);
                    }
                });
    }

    @Override // com.android.settings.notification.modes.AbstractZenModePreferenceController
    public final void updateState(Preference preference, ZenMode zenMode) {
        TwoStatePreference twoStatePreference = (TwoStatePreference) preference;
        if (zenMode.getPolicy().getPriorityCategoryCalls() == 1
                && zenMode.getPolicy().getPriorityCallSenders() == 1) {
            twoStatePreference.setEnabled(false);
            twoStatePreference.setChecked(true);
        } else {
            twoStatePreference.setEnabled(true);
            twoStatePreference.setChecked(
                    zenMode.getPolicy().getPriorityCategoryRepeatCallers() == 1);
        }
        preference.setSummary(
                this.mContext.getString(
                        R.string.zen_mode_repeat_callers_summary,
                        Integer.valueOf(this.mRepeatCallersThreshold)));
    }
}
