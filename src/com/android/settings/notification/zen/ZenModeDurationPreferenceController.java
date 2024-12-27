package com.android.settings.notification.zen;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeDurationPreferenceController extends AbstractZenModePreferenceController {
    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "zen_mode_duration_settings";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final CharSequence getSummary() {
        int zenDuration = getZenDuration();
        if (zenDuration < 0) {
            return this.mContext.getString(R.string.zen_mode_duration_summary_always_prompt);
        }
        if (zenDuration == 0) {
            return this.mContext.getString(R.string.zen_mode_duration_summary_forever);
        }
        if (zenDuration < 60) {
            return this.mContext
                    .getResources()
                    .getQuantityString(
                            R.plurals.zen_mode_duration_summary_time_minutes,
                            zenDuration,
                            Integer.valueOf(zenDuration));
        }
        int i = zenDuration / 60;
        return this.mContext
                .getResources()
                .getQuantityString(
                        R.plurals.zen_mode_duration_summary_time_hours, i, Integer.valueOf(i));
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return getZenMode() == 0;
    }
}
