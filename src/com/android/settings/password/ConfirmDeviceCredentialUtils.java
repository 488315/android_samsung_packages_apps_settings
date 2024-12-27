package com.android.settings.password;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.IActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.IntentSender;
import android.content.pm.UserInfo;
import android.os.RemoteException;
import android.os.UserManager;
import android.util.Log;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.internal.widget.LockPatternUtils;

import com.samsung.android.settings.Rune;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ConfirmDeviceCredentialUtils {
    public static void checkForPendingIntent(Activity activity) {
        int intExtra = activity.getIntent().getIntExtra("android.intent.extra.TASK_ID", -1);
        int windowingMode =
                activity.getResources().getConfiguration().windowConfiguration.getWindowingMode();
        if (intExtra != -1 && windowingMode != 6) {
            try {
                IActivityManager service = ActivityManager.getService();
                ActivityOptions makeBasic = ActivityOptions.makeBasic();
                if (windowingMode == 6 || windowingMode == 5) {
                    makeBasic.setLaunchWindowingMode(windowingMode);
                }
                if (Rune.isSamsungDexMode(activity)) {
                    makeBasic.setLaunchDisplayId(activity.getDisplay().getDisplayId());
                }
                service.startActivityFromRecents(intExtra, makeBasic.toBundle());
                return;
            } catch (RemoteException unused) {
            } catch (IllegalArgumentException e) {
                Log.e("ConfirmDeviceCredentialUtils", "Invalid taskId", e);
            }
        }
        IntentSender intentSender =
                (IntentSender)
                        activity.getIntent().getParcelableExtra("android.intent.extra.INTENT");
        if (intentSender != null) {
            try {
                activity.startIntentSenderForResult(
                        intentSender,
                        -1,
                        null,
                        0,
                        0,
                        0,
                        ActivityOptions.makeBasic()
                                .setPendingIntentBackgroundActivityStartMode(1)
                                .toBundle());
            } catch (IntentSender.SendIntentException unused2) {
            }
        }
    }

    public static void reportSuccessfulAttempt(
            LockPatternUtils lockPatternUtils,
            UserManager userManager,
            DevicePolicyManager devicePolicyManager,
            int i,
            boolean z) {
        UserInfo userInfo;
        if (z) {
            lockPatternUtils.reportSuccessfulPasswordAttempt(i);
            if (Flags.allowPrivateProfile()
                    && android.multiuser.Flags.enableBiometricsToUnlockPrivateSpace()
                    && android.multiuser.Flags.enablePrivateSpaceFeatures()
                    && (userInfo = userManager.getUserInfo(i)) != null
                    && ((userManager
                                            .getUserProperties(userInfo.getUserHandle())
                                            .isAuthAlwaysRequiredToDisableQuietMode()
                                    && userInfo.isProfile())
                            || userInfo.isManagedProfile())) {
                lockPatternUtils.userPresent(i);
            }
        } else {
            devicePolicyManager.reportSuccessfulBiometricAttempt(i);
        }
        if (!(Flags.allowPrivateProfile()
                        && android.multiuser.Flags.enableBiometricsToUnlockPrivateSpace()
                        && android.multiuser.Flags.enablePrivateSpaceFeatures())
                && userManager.isManagedProfile(i)) {
            lockPatternUtils.userPresent(i);
        }
    }
}
