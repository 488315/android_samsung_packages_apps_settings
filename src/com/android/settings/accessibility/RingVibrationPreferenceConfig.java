package com.android.settings.accessibility;

import android.content.Context;
import android.provider.Settings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class RingVibrationPreferenceConfig extends VibrationPreferenceConfig {
    public RingVibrationPreferenceConfig(Context context) {
        super(context, "ring_vibration_intensity", 33);
    }

    @Override // com.android.settings.accessibility.VibrationPreferenceConfig
    public final boolean isRestrictedByRingerModeSilent() {
        return true;
    }

    @Override // com.android.settings.accessibility.VibrationPreferenceConfig
    public final boolean updateIntensity(int i) {
        boolean updateIntensity = super.updateIntensity(i);
        Settings.System.putInt(this.mContentResolver, "vibrate_when_ringing", i == 0 ? 0 : 1);
        return updateIntensity;
    }
}
