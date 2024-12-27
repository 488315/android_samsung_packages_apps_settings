package com.samsung.android.settings.biometrics.fingerprint;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.Utils;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.security.SecurityUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FingerprintReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d("FpstFingerprintReceiver", "onReceive : " + action.toString());
        if (!"com.samsung.android.intent.action.FINGERPRINT_RESET".equals(action)) {
            if (!"com.samsung.android.intent.action.FINGERPRINT_WEB_SIGNIN".equals(action)
                    || SecurityUtils.isEnabledSamsungPass(context)) {
                return;
            }
            Settings.Secure.putIntForUser(
                    context.getContentResolver(),
                    "fingerprint_webpass",
                    1,
                    ActivityManager.getCurrentUser());
            return;
        }
        FingerprintManager fingerprintManagerOrNull = Utils.getFingerprintManagerOrNull(context);
        if (fingerprintManagerOrNull == null
                || fingerprintManagerOrNull.hasEnrolledFingerprints(
                        ActivityManager.getCurrentUser())) {
            Log.e("FpstFingerprintReceiver", "Fingerprint exist!");
            return;
        }
        Log.d("FpstFingerprintReceiver", "ResetFingerprintSupportingFeatures");
        Settings.System.putIntForUser(
                context.getContentResolver(),
                "fingerprint_secret_box",
                0,
                ActivityManager.getCurrentUser());
        Settings.Secure.putIntForUser(
                context.getContentResolver(),
                "fingerprint_webpass",
                0,
                ActivityManager.getCurrentUser());
        Settings.System.putIntForUser(
                context.getContentResolver(),
                "fingerprint_fingerIndex",
                0,
                ActivityManager.getCurrentUser());
        Settings.Secure.putIntForUser(
                context.getContentResolver(),
                "fingerprint_used_samsungaccount",
                0,
                ActivityManager.getCurrentUser());
        Settings.Secure.putIntForUser(
                context.getContentResolver(),
                "fingerprint_samsungaccount_confirmed",
                0,
                ActivityManager.getCurrentUser());
        Settings.Secure.putIntForUser(
                context.getContentResolver(),
                "fingerprint_used_samsungaccount",
                0,
                ActivityManager.getCurrentUser());
        if (SemPersonaManager.isKnoxId(UserHandle.getCallingUserId())
                || SemPersonaManager.isKnoxId(UserHandle.getCallingUserId())) {
            return;
        }
        Log.d("FpstFingerprintReceiver", "setFingerprintScreenLockDisable");
        FingerprintSettingsUtils.removeFingerprintLock(
                ActivityManager.getCurrentUser(), context, new LockPatternUtils(context));
    }
}
