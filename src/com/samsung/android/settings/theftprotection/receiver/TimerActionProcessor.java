package com.samsung.android.settings.theftprotection.receiver;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.provider.Settings;

import com.samsung.android.settings.theftprotection.TheftProtectionConstants;
import com.samsung.android.settings.theftprotection.TheftProtectionUtils;
import com.samsung.android.settings.theftprotection.logging.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class TimerActionProcessor implements ProcessAction {
    @Override // com.samsung.android.settings.theftprotection.receiver.ProcessAction
    public final void handle(Context context, Intent intent) {
        String action = intent.getAction();
        action.getClass();
        switch (action) {
            case "com.samsung.android.settings.action.GRACE_TIME_FORCE_TERMINATION":
                Log.d("TimerActionProcessor", "Grace period forced termination");
                if (ProcessAction.isSecurityGraceActive(context)) {
                    Settings.Secure.putLong(
                            context.getContentResolver(), "mandatory_biometrics_grace_time", 0L);
                    ProcessAction.cancelNotifications(context);
                    ((AlarmManager) context.getSystemService(AlarmManager.class))
                            .cancel(ProcessAction.getIntentForSecurityGraceExpiration(context));
                    break;
                }
                break;
            case "android.intent.action.TIME_SET":
                long elapsedRealtime = SystemClock.elapsedRealtime();
                if (!ProcessAction.isSecurityDelayActive(context)) {
                    if (ProcessAction.isSecurityGraceActive(context)) {
                        long j =
                                Settings.Secure.getLong(
                                                context.getContentResolver(),
                                                "mandatory_biometrics_grace_time",
                                                0L)
                                        - elapsedRealtime;
                        TheftProtectionUtils.isSecurityDelayTest();
                        long j2 = TheftProtectionConstants.SECURITY_GRACE_DURATION_MILLIS;
                        TheftProtectionUtils.isSecurityDelayTest();
                        Settings.Secure.putLong(
                                context.getContentResolver(),
                                "mandatory_biometrics_base_time",
                                System.currentTimeMillis()
                                        - ((j2
                                                        + TheftProtectionConstants
                                                                .SECURITY_DELAY_DURATION_MILLIS)
                                                - j));
                        break;
                    }
                } else {
                    long j3 =
                            Settings.Secure.getLong(
                                            context.getContentResolver(),
                                            "mandatory_biometrics_delay_time",
                                            0L)
                                    - elapsedRealtime;
                    TheftProtectionUtils.isSecurityDelayTest();
                    Settings.Secure.putLong(
                            context.getContentResolver(),
                            "mandatory_biometrics_base_time",
                            System.currentTimeMillis()
                                    - (TheftProtectionConstants.SECURITY_DELAY_DURATION_MILLIS
                                            - j3));
                    break;
                }
                break;
            case "com.samsung.android.settings.action.TIME_DELAY_EXPIRATION":
                long elapsedRealtime2 = SystemClock.elapsedRealtime();
                TheftProtectionUtils.isSecurityDelayTest();
                long j4 =
                        elapsedRealtime2 + TheftProtectionConstants.SECURITY_GRACE_DURATION_MILLIS;
                Log.d(
                        "TimerActionProcessor",
                        "Security delay is expired. expirationTime(GraceTime)=" + j4);
                Settings.Secure.putLong(
                        context.getContentResolver(), "mandatory_biometrics_delay_time", 0L);
                Settings.Secure.putLong(
                        context.getContentResolver(), "mandatory_biometrics_grace_time", j4);
                ProcessAction.notifyTimeDelayExpired(context, false);
                ((AlarmManager) context.getSystemService(AlarmManager.class))
                        .setExactAndAllowWhileIdle(
                                3, j4, ProcessAction.getIntentForSecurityGraceExpiration(context));
                break;
            case "com.samsung.android.settings.action.ENTER_TRUSTED_PLACES":
                Log.d("TimerActionProcessor", "Enter trusted places");
                if (!ProcessAction.isSecurityDelayActive(context)) {
                    if (ProcessAction.isSecurityGraceActive(context)) {
                        Settings.Secure.putLong(
                                context.getContentResolver(),
                                "mandatory_biometrics_grace_time",
                                0L);
                        ProcessAction.cancelNotifications(context);
                        ((AlarmManager) context.getSystemService(AlarmManager.class))
                                .cancel(ProcessAction.getIntentForSecurityGraceExpiration(context));
                        break;
                    }
                } else {
                    Settings.Secure.putLong(
                            context.getContentResolver(), "mandatory_biometrics_delay_time", 0L);
                    ProcessAction.cancelNotifications(context);
                    ((AlarmManager) context.getSystemService(AlarmManager.class))
                            .cancel(ProcessAction.getIntentForSecurityDelayExpiration(context));
                    ProcessAction.notifyTimeDelayExpired(context, true);
                    break;
                }
                break;
            case "com.samsung.android.settings.action.GRACE_TIME_EXPIRATION":
                Log.d("TimerActionProcessor", "Grace Time is expired");
                Settings.Secure.putLong(
                        context.getContentResolver(), "mandatory_biometrics_grace_time", 0L);
                ProcessAction.cancelNotifications(context);
                break;
            case "com.samsung.android.settings.action.START_SECURITY_DELAY":
                long elapsedRealtime3 = SystemClock.elapsedRealtime();
                TheftProtectionUtils.isSecurityDelayTest();
                long j5 = TheftProtectionConstants.SECURITY_DELAY_DURATION_MILLIS;
                long longExtra =
                        intent.getLongExtra("delay_expiration_time", elapsedRealtime3 + j5);
                Log.d("TimerActionProcessor", "Start security delay. expirationTime=" + longExtra);
                Settings.Secure.putLong(
                        context.getContentResolver(),
                        "mandatory_biometrics_base_time",
                        System.currentTimeMillis());
                Settings.Secure.putLong(
                        context.getContentResolver(), "mandatory_biometrics_delay_time", longExtra);
                TheftProtectionUtils.isSecurityDelayTest();
                ProcessAction.notifyTimeDelayStart(context, j5);
                ((AlarmManager) context.getSystemService(AlarmManager.class))
                        .setExactAndAllowWhileIdle(
                                3,
                                longExtra,
                                ProcessAction.getIntentForSecurityDelayExpiration(context));
                break;
        }
    }
}
