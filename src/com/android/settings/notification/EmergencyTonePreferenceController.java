package com.android.settings.notification;

import android.content.Context;
import android.content.res.Resources;
import android.telephony.TelephonyManager;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class EmergencyTonePreferenceController extends SettingPrefController {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.EmergencyTonePreferenceController$1, reason: invalid class name */
    public final class AnonymousClass1 extends SettingPref {
        @Override // com.android.settings.notification.SettingPref
        public final String getCaption(Resources resources, int i) {
            if (i == 0) {
                return resources.getString(R.string.emergency_tone_silent);
            }
            if (i == 1) {
                return resources.getString(R.string.emergency_tone_alert);
            }
            if (i == 2) {
                return resources.getString(R.string.emergency_tone_vibrate);
            }
            throw new IllegalArgumentException();
        }

        @Override // com.android.settings.notification.SettingPref
        public final boolean isApplicable(Context context) {
            TelephonyManager telephonyManager =
                    (TelephonyManager) context.getSystemService("phone");
            return telephonyManager != null && telephonyManager.getCurrentPhoneType() == 2;
        }
    }
}
