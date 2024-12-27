package com.android.settings.notification;

import com.android.settings.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PhoneRingtonePreferenceController extends RingtonePreferenceControllerBase {
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "phone_ringtone";
    }

    @Override // com.android.settings.notification.RingtonePreferenceControllerBase
    public final int getRingtoneType() {
        return 1;
    }

    @Override // com.android.settings.notification.RingtonePreferenceControllerBase,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return Utils.isVoiceCapable(this.mContext);
    }
}
