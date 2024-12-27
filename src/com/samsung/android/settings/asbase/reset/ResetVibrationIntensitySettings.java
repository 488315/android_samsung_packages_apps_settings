package com.samsung.android.settings.asbase.reset;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;

import com.samsung.android.settings.asbase.utils.VibUtils;
import com.samsung.android.settings.general.BaseResetSettingsData;
import com.samsung.android.settings.general.OnResetSettingsDataListener;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class ResetVibrationIntensitySettings {
    public static final OnResetSettingsDataListener RESET_SETTINGS_DATA = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.asbase.reset.ResetVibrationIntensitySettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseResetSettingsData {
        @Override // com.samsung.android.settings.general.OnResetSettingsDataListener
        public final void resetSettings(Context context) {
            ContentResolver contentResolver = context.getContentResolver();
            if (VibUtils.isSupportDcHaptic(context)) {
                Settings.System.putIntForUser(contentResolver, "VIB_FEEDBACK_MAGNITUDE", 1, -2);
            } else {
                Settings.System.putIntForUser(contentResolver, "VIB_FEEDBACK_MAGNITUDE", 2, -2);
            }
            Settings.System.putIntForUser(contentResolver, "VIB_RECVCALL_MAGNITUDE", 5, -2);
            Settings.System.putIntForUser(
                    contentResolver, "SEM_VIBRATION_NOTIFICATION_INTENSITY", 5, -2);
            Settings.System.putIntForUser(
                    contentResolver, "SEM_VIBRATION_FORCE_TOUCH_INTENSITY", 4, -2);
            Settings.System.putIntForUser(contentResolver, "media_vibration_intensity", 5, -2);
        }
    }
}
