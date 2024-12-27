package com.samsung.android.settings.theftprotection.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.samsung.android.settings.theftprotection.logging.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ProtectionReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        ProcessAction fingerprintActionProcessor;
        if (intent == null) {
            Log.d("ProtectionReceiver", "onReceive : intent is empty");
            return;
        }
        String action = intent.getAction();
        Log.d("ProtectionReceiver", "onReceive : " + action);
        if (TextUtils.isEmpty(action)) {
            return;
        }
        action.getClass();
        switch (action) {
            case "com.samsung.android.intent.action.FINGERPRINT_ADDED":
            case "com.samsung.android.intent.action.FINGERPRINT_RESET":
                fingerprintActionProcessor = new FingerprintActionProcessor();
                break;
            case "com.samsung.android.settings.action.GEOFENCE_STATE_CHANGED":
                fingerprintActionProcessor = new LocationActionProcessor();
                break;
            case "com.samsung.android.settings.action.GRACE_TIME_FORCE_TERMINATION":
            case "android.intent.action.TIME_SET":
            case "com.samsung.android.settings.action.TIME_DELAY_EXPIRATION":
            case "com.samsung.android.settings.action.ENTER_TRUSTED_PLACES":
            case "com.samsung.android.settings.action.GRACE_TIME_EXPIRATION":
            case "com.samsung.android.settings.action.START_SECURITY_DELAY":
                fingerprintActionProcessor = new TimerActionProcessor();
                break;
            case "android.intent.action.BOOT_COMPLETED":
                fingerprintActionProcessor = new BootActionProcessor();
                break;
            default:
                fingerprintActionProcessor = null;
                break;
        }
        if (fingerprintActionProcessor != null) {
            fingerprintActionProcessor.handle(context, intent);
        }
    }
}
