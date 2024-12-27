package com.android.settings.notification;

import android.content.Context;
import android.os.Vibrator;

import com.android.settings.SettingsPreferenceFragment;
import com.android.settingslib.core.lifecycle.Lifecycle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class VibrateIconPreferenceController extends SettingPrefController {
    public final boolean mHasVibrator;

    public VibrateIconPreferenceController(
            Context context,
            SettingsPreferenceFragment settingsPreferenceFragment,
            Lifecycle lifecycle) {
        super(context, settingsPreferenceFragment, lifecycle);
        this.mHasVibrator = ((Vibrator) context.getSystemService(Vibrator.class)).hasVibrator();
        this.mPreference =
                new SettingPref(3, "vibrate_icon", "status_bar_show_vibrate_icon", 0, new int[0]);
    }

    @Override // com.android.settings.notification.SettingPrefController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mHasVibrator;
    }
}
