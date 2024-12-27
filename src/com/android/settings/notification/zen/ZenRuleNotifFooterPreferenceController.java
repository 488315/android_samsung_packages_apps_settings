package com.android.settings.notification.zen;

import android.app.AutomaticZenRule;

import androidx.preference.Preference;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenRuleNotifFooterPreferenceController
        extends AbstractZenCustomRulePreferenceController {
    @Override // com.android.settings.notification.zen.AbstractZenCustomRulePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (!super.isAvailable() || this.mRule.getZenPolicy() == null) {
            return false;
        }
        return this.mRule.getZenPolicy().shouldHideAllVisualEffects()
                || this.mRule.getZenPolicy().shouldShowAllVisualEffects();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        AutomaticZenRule automaticZenRule = this.mRule;
        if (automaticZenRule == null || automaticZenRule.getZenPolicy() == null) {
            return;
        }
        if (this.mRule.getZenPolicy().shouldShowAllVisualEffects()) {
            preference.setTitle(R.string.zen_mode_restrict_notifications_mute_footer);
        } else if (this.mRule.getZenPolicy().shouldHideAllVisualEffects()) {
            preference.setTitle(R.string.zen_mode_restrict_notifications_hide_footer);
        } else {
            preference.setTitle((CharSequence) null);
        }
    }
}
