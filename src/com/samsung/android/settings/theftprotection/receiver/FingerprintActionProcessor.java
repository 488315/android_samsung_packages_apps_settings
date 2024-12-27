package com.samsung.android.settings.theftprotection.receiver;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.samsung.android.settings.theftprotection.TheftProtectionUtils;
import com.samsung.android.settings.theftprotection.location.LocationManager$$ExternalSyntheticLambda0;
import com.samsung.android.settings.theftprotection.location.LocationStorage;
import com.samsung.android.settings.theftprotection.logging.Log;
import com.samsung.android.settings.theftprotection.timer.ProtectionTimerManager;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class FingerprintActionProcessor implements ProcessAction {
    @Override // com.samsung.android.settings.theftprotection.receiver.ProcessAction
    public final void handle(Context context, Intent intent) {
        Log.d("FingerprintActionProcessor", "handle Action Fingerprint");
        String action = intent.getAction();
        action.getClass();
        if (action.equals("com.samsung.android.intent.action.FINGERPRINT_ADDED")) {
            if (TheftProtectionUtils.isMandatoryBiometricEnabled(context)) {
                Log.d("LocationManager", "registerAllGeofence");
                ((ArrayList) LocationStorage.InstanceHolder.INSTANCE.loadLocationData())
                        .forEach(new LocationManager$$ExternalSyntheticLambda0(context, 1));
            }
        } else if (action.equals("com.samsung.android.intent.action.FINGERPRINT_RESET")) {
            ProtectionTimerManager.getInstance().getClass();
            Log.d("ProtectionTimerManager", "stopTimer()");
            ProtectionTimerManager.NoLeakCountDownTimer noLeakCountDownTimer =
                    ProtectionTimerManager.sCountDownTimer;
            if (noLeakCountDownTimer != null) {
                noLeakCountDownTimer.cancel();
                ProtectionTimerManager.sCountDownTimer = null;
            }
            Settings.Secure.putLong(
                    context.getContentResolver(), "mandatory_biometrics_delay_time", 0L);
            Settings.Secure.putLong(
                    context.getContentResolver(), "mandatory_biometrics_grace_time", 0L);
            ((ArrayList) LocationStorage.InstanceHolder.INSTANCE.loadLocationData())
                    .forEach(new FingerprintActionProcessor$$ExternalSyntheticLambda0());
            TheftProtectionUtils.updateInSignificantPlace(context, false);
        }
        TheftProtectionUtils.updateRequirementsSatisfied(context);
    }
}
