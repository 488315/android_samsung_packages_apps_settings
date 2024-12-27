package com.samsung.android.settings.lockscreen;

import android.content.ContentResolver;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.general.BaseResetSettingsData;
import com.samsung.android.settings.general.OnResetSettingsDataListener;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class LockScreenDataReset {
    public static final OnResetSettingsDataListener RESET_SETTINGS_DATA = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.lockscreen.LockScreenDataReset$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseResetSettingsData {
        @Override // com.samsung.android.settings.general.OnResetSettingsDataListener
        public final void resetSettings(Context context) {
            Log.i("LockScreenDataReset", "Reset LockScreen Settings called");
            ContentResolver contentResolver = context.getContentResolver();
            int semGetTransitionEffectValue = FingerprintManager.semGetTransitionEffectValue();
            if (semGetTransitionEffectValue == -1) {
                semGetTransitionEffectValue = 1;
            }
            Settings.System.putInt(
                    contentResolver, "screen_transition_effect", semGetTransitionEffectValue);
            Settings.System.putInt(context.getContentResolver(), "now_bar_enabled", 1);
            String str = ApnSettings.MVNO_NONE;
            try {
                str = SystemProperties.get("ro.csc.sales_code");
                if (TextUtils.isEmpty(str)) {
                    str = SystemProperties.get("ril.sales_code");
                }
            } catch (Exception unused) {
                Log.i("LockScreenDataReset", "readSalesCode failed");
            }
            str.getClass();
            switch (str) {
                case "2DX":
                case "ACG":
                case "LRA":
                case "NZC":
                case "USC":
                case "XNF":
                case "XNX":
                case "XNZ":
                    Settings.System.putIntForUser(
                            context.getContentResolver(), "dualclock_menu_settings", 0, -2);
                    break;
                default:
                    Settings.System.putIntForUser(
                            context.getContentResolver(), "dualclock_menu_settings", 1, -2);
                    break;
            }
        }
    }
}
