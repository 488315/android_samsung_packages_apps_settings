package com.android.settings.notification;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ScreenLockSoundPreferenceController extends SettingPrefController {
    @Override // com.android.settings.notification.SettingPrefController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mContext.getResources().getBoolean(R.bool.config_show_screen_locking_sounds);
    }
}
