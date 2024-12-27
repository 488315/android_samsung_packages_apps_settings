package com.samsung.android.settings.asbase.vibration;

import android.content.ContentResolver;
import android.provider.Settings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract /* synthetic */ class SecSoundDeviceVibrationController$$ExternalSyntheticOutline0 {
    public static void m(ContentResolver contentResolver, String str, String str2) {
        Settings.System.putInt(contentResolver, str2, Integer.valueOf(str).intValue());
    }
}
