package com.android.settings.notification.zen;

import android.app.NotificationManager;

import androidx.preference.Preference;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenFooterPreferenceController extends AbstractZenModePreferenceController {
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        ZenModeBackend zenModeBackend = this.mBackend;
        if (NotificationManager.Policy.secAreAnyScreenOffEffectsSuppressed(
                        zenModeBackend.mPolicy.suppressedVisualEffects)
                || NotificationManager.Policy.secAreAnyScreenOnEffectsSuppressed(
                        zenModeBackend.mPolicy.suppressedVisualEffects)) {
            preference.setTitle(R.string.zen_mode_blocked_effects_footer);
        } else {
            preference.setTitle(R.string.zen_mode_restrict_notifications_mute_footer);
        }
    }
}
