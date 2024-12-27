package com.android.settings.biometrics;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.storage.StorageManager;
import android.text.BidiFormatter;
import android.text.SpannableStringBuilder;
import android.util.Log;

import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.VerifyCredentialResponse;
import com.android.settings.R;
import com.android.settings.password.ChooseLockGeneric;
import com.android.settings.password.SetupChooseLockGeneric;

import com.google.android.setupcompat.util.WizardManagerHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BiometricUtils {
    public static int sAllowEnrollPosture;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class GatekeeperCredentialNotMatchException extends IllegalStateException {
        public GatekeeperCredentialNotMatchException(String str) {
            super(str);
        }
    }

    public static boolean containsGatekeeperPasswordHandle(Intent intent) {
        return intent != null && intent.hasExtra("gk_pw_handle");
    }

    public static void copyMultiBiometricExtras(Intent intent, Intent intent2) {
        PendingIntent pendingIntent = (PendingIntent) intent.getExtra("enroll_after_face", null);
        if (pendingIntent != null) {
            intent2.putExtra("enroll_after_face", pendingIntent);
        }
        PendingIntent pendingIntent2 = (PendingIntent) intent.getExtra("enroll_after_finger", null);
        if (pendingIntent2 != null) {
            intent2.putExtra("enroll_after_finger", pendingIntent2);
        }
    }

    public static Intent getChooseLockIntent(Context context, Intent intent) {
        if (!WizardManagerHelper.isAnySetupWizard(intent)) {
            return new Intent(context, (Class<?>) ChooseLockGeneric.class);
        }
        Intent intent2 = new Intent(context, (Class<?>) SetupChooseLockGeneric.class);
        if (StorageManager.isFileEncrypted()) {
            intent2.putExtra("lockscreen.password_type", 131072);
            intent2.putExtra("show_options_button", true);
        }
        WizardManagerHelper.copyWizardManagerExtras(intent, intent2);
        return intent2;
    }

    public static String getCombinedScreenLockOptions(
            Context context, CharSequence charSequence, boolean z, boolean z2) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        BidiFormatter bidiFormatter = BidiFormatter.getInstance();
        spannableStringBuilder.append(bidiFormatter.unicodeWrap(charSequence));
        if (z) {
            spannableStringBuilder.append((CharSequence) bidiFormatter.unicodeWrap(" • "));
            String string = context.getString(R.string.security_settings_fingerprint);
            spannableStringBuilder.append(
                    (CharSequence)
                            bidiFormatter.unicodeWrap(
                                    Character.toUpperCase(string.charAt(0)) + string.substring(1)));
        }
        if (z2) {
            spannableStringBuilder.append((CharSequence) bidiFormatter.unicodeWrap(" • "));
            String string2 = context.getString(R.string.keywords_face_settings);
            spannableStringBuilder.append(
                    (CharSequence)
                            bidiFormatter.unicodeWrap(
                                    Character.toUpperCase(string2.charAt(0))
                                            + string2.substring(1)));
        }
        return spannableStringBuilder.toString();
    }

    public static long getGatekeeperPasswordHandle(Intent intent) {
        return intent.getLongExtra("gk_pw_handle", 0L);
    }

    public static boolean isAnyMultiBiometricFlow(Activity activity) {
        return activity.getIntent().hasExtra("enroll_after_face")
                || activity.getIntent().hasExtra("enroll_after_finger");
    }

    public static void removeGatekeeperPasswordHandle(Context context, Intent intent) {
        if (intent != null && containsGatekeeperPasswordHandle(intent)) {
            new LockPatternUtils(context)
                    .removeGatekeeperPasswordHandle(intent.getLongExtra("gk_pw_handle", 0L));
            Log.d("BiometricUtils", "Removed handle");
        }
    }

    public static byte[] requestGatekeeperHat(Context context, Intent intent, int i, long j) {
        if (!containsGatekeeperPasswordHandle(intent)) {
            throw new IllegalStateException("Gatekeeper Password is missing!!");
        }
        VerifyCredentialResponse verifyGatekeeperPasswordHandle =
                new LockPatternUtils(context)
                        .verifyGatekeeperPasswordHandle(
                                intent.getLongExtra("gk_pw_handle", 0L), j, i);
        if (verifyGatekeeperPasswordHandle.isMatched()) {
            return verifyGatekeeperPasswordHandle.getGatekeeperHAT();
        }
        Log.e("BiometricUtils", "Unable to request Gatekeeper HAT");
        return null;
    }

    public static boolean tryStartingNextBiometricEnroll(Activity activity) {
        PendingIntent pendingIntent =
                (PendingIntent) activity.getIntent().getExtra("enroll_after_face");
        if (pendingIntent == null) {
            pendingIntent = (PendingIntent) activity.getIntent().getExtra("enroll_after_finger");
        }
        if (pendingIntent == null) {
            return false;
        }
        try {
            activity.startIntentSenderForResult(pendingIntent.getIntentSender(), 6, null, 0, 0, 0);
            return true;
        } catch (IntentSender.SendIntentException e) {
            Log.e("BiometricUtils", "Pending intent canceled: " + e);
            return false;
        }
    }
}
