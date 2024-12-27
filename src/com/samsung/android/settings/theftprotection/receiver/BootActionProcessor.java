package com.samsung.android.settings.theftprotection.receiver;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.provider.Settings;

import com.samsung.android.settings.theftprotection.TheftProtectionConstants;
import com.samsung.android.settings.theftprotection.TheftProtectionUtils;
import com.samsung.android.settings.theftprotection.location.LocationManager$$ExternalSyntheticLambda0;
import com.samsung.android.settings.theftprotection.location.LocationStorage;
import com.samsung.android.settings.theftprotection.logging.Log;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BootActionProcessor implements ProcessAction {
    @Override // com.samsung.android.settings.theftprotection.receiver.ProcessAction
    public final void handle(Context context, Intent intent) {
        Log.d("BootActionProcessor", "register the trust locations : BOOT COMPLETED");
        long currentTimeMillis =
                System.currentTimeMillis()
                        - Settings.Secure.getLong(
                                context.getContentResolver(), "mandatory_biometrics_base_time", 0L);
        if (ProcessAction.isSecurityDelayActive(context)) {
            TheftProtectionUtils.isSecurityDelayTest();
            long j = TheftProtectionConstants.SECURITY_DELAY_DURATION_MILLIS - currentTimeMillis;
            long elapsedRealtime = SystemClock.elapsedRealtime() + j;
            Settings.Secure.putLong(
                    context.getContentResolver(),
                    "mandatory_biometrics_delay_time",
                    elapsedRealtime);
            ProcessAction.notifyTimeDelayStart(context, j);
            ((AlarmManager) context.getSystemService(AlarmManager.class))
                    .setExactAndAllowWhileIdle(
                            3,
                            elapsedRealtime,
                            ProcessAction.getIntentForSecurityDelayExpiration(context));
        } else if (ProcessAction.isSecurityGraceActive(context)) {
            TheftProtectionUtils.isSecurityDelayTest();
            long j2 = TheftProtectionConstants.SECURITY_DELAY_DURATION_MILLIS;
            TheftProtectionUtils.isSecurityDelayTest();
            long elapsedRealtime2 =
                    SystemClock.elapsedRealtime()
                            + ((j2 + TheftProtectionConstants.SECURITY_GRACE_DURATION_MILLIS)
                                    - currentTimeMillis);
            Settings.Secure.putLong(
                    context.getContentResolver(),
                    "mandatory_biometrics_grace_time",
                    elapsedRealtime2);
            ((AlarmManager) context.getSystemService(AlarmManager.class))
                    .setExactAndAllowWhileIdle(
                            3,
                            elapsedRealtime2,
                            ProcessAction.getIntentForSecurityGraceExpiration(context));
        }
        LocationStorage locationStorage = LocationStorage.InstanceHolder.INSTANCE;
        ((ArrayList) locationStorage.loadLocationData())
                .forEach(new BootActionProcessor$$ExternalSyntheticLambda0());
        TheftProtectionUtils.updateInSignificantPlace(context, false);
        TheftProtectionUtils.updateRequirementsSatisfied(context);
        if (TheftProtectionUtils.isMandatoryBiometricEnabled(context)) {
            Log.d("LocationManager", "registerAllGeofence");
            ((ArrayList) locationStorage.loadLocationData())
                    .forEach(new LocationManager$$ExternalSyntheticLambda0(context, 1));
        }
    }
}
