package com.samsung.android.settings.biometrics.fingerprint;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.util.SeslKoreanGeneralizer;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;

import com.samsung.android.settings.Rune$$ExternalSyntheticOutline0;
import com.samsung.android.settings.biometrics.BiometricsGenericHelper;
import com.samsung.android.settings.lockscreen.LockUtils;
import com.samsung.android.settings.security.SecurityUtils;
import com.sec.ims.volte2.data.VolteConstants;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class FingerprintSettingsUtils {
    public static final boolean SUB_DISPLAY_IS_LARGE_SCREEN =
            Rune$$ExternalSyntheticOutline0.m(
                    "SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY", "LARGESCREEN");

    public static String convertEvtToString(int i) {
        switch (i) {
            case 1000:
                return "MSG_REFRESH_FINGERPRINT_TEMPLATES";
            case 1001:
                return "MSG_FINGER_AUTH_SUCCESS";
            case 1002:
                return "MSG_FINGER_AUTH_FAIL";
            case 1003:
                return "MSG_FINGER_AUTH_ERROR";
            case VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI /* 1004 */:
                return "MSG_FINGER_AUTH_HELP";
            default:
                return "MSG_DEFAULT";
        }
    }

    public static int getEnrolledFingerNumber(FingerprintManager fingerprintManager, int i) {
        List enrolledFingerprints;
        int size =
                (fingerprintManager == null
                                || (enrolledFingerprints =
                                                fingerprintManager.getEnrolledFingerprints(i))
                                        == null)
                        ? 0
                        : enrolledFingerprints.size();
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                size, "enrolledFingerprint : ", "FpstFingerprintSettingsUtils");
        return size;
    }

    public static boolean getFingerprintAlwaysOnBooleanValue(Context context, int i) {
        int intForUser =
                Settings.Secure.getIntForUser(
                        context.getContentResolver(), "fingerprint_always_on", 1, i);
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                intForUser, "getFingerprintAlwaysOnBooleanValue :", "FpstFingerprintSettingsUtils");
        return intForUser != 0;
    }

    public static boolean getFingerprintEffectValue(Context context, int i) {
        int intForUser =
                Settings.Secure.getIntForUser(
                        context.getContentResolver(), "fingerprint_effect", -1, i);
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                intForUser, "getFingerprintEffectValue :", "FpstFingerprintSettingsUtils");
        return intForUser != 0;
    }

    public static Intent getFingerprintEnrollmentIntentForAosp(Context context, boolean z) {
        Log.d("FpstFingerprintSettingsUtils", "getFingerprintEnrollmentIntentForAosp");
        Intent intent = new Intent();
        intent.setClassName(context.getPackageName(), FingerprintLockSettings.class.getName());
        if (z) {
            intent.putExtra("previousStage", "google_setupwizard_fingerprint");
        } else {
            intent.putExtra("previousStage", "fingerprint_register_external");
        }
        return intent;
    }

    public static int getFingerprintLockIconValue(Context context, int i) {
        int intForUser =
                Settings.Secure.getIntForUser(
                        context.getContentResolver(), "fingerprint_screen_on_icon_lock", -1, i);
        if (intForUser == -1) {
            intForUser = 2;
            setFingerprintLockIconValue(context, 2, i);
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                intForUser, "getFingerprintLockIconValue :", "FpstFingerprintSettingsUtils");
        return intForUser;
    }

    public static int getFingerprintScreenOffIconAodDbValue(Context context, int i) {
        int i2 = -1;
        if (isSupportFingerprintScreenOffIconAod()) {
            int intForUser =
                    Settings.Secure.getIntForUser(
                            context.getContentResolver(), "fingerprint_screen_off_icon_aod", -1, i);
            if (intForUser == -1) {
                int intForUser2 =
                        Settings.Secure.getIntForUser(
                                context.getContentResolver(), "fingerprint_adaptive_icon", -1, i);
                if (intForUser2 == 0) {
                    intForUser = 0;
                    setFingerprintScreenOffIconAodValue(context, 0, i);
                } else {
                    i2 = 1;
                    if (intForUser2 == 1) {
                        setFingerprintScreenOffIconAodValue(context, 1, i);
                    } else {
                        intForUser = 2;
                        setFingerprintScreenOffIconAodValue(context, 2, i);
                    }
                }
            }
            i2 = intForUser;
        } else {
            Log.d(
                    "FpstFingerprintSettingsUtils",
                    "getFingerprintScreenOffIconAodDbValue: not supported device");
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i2, "getFingerprintScreenOffIconAodDbValue :", "FpstFingerprintSettingsUtils");
        return i2;
    }

    public static boolean getFingerprintUnlockEnabled(LockPatternUtils lockPatternUtils, int i) {
        return lockPatternUtils != null && lockPatternUtils.getBiometricState(1, i) == 1;
    }

    public static int getMaxFingerEnroll(FingerprintManager fingerprintManager) {
        int semGetMaxEnrollmentNumber =
                fingerprintManager != null ? fingerprintManager.semGetMaxEnrollmentNumber() : -1;
        if (semGetMaxEnrollmentNumber == -1) {
            semGetMaxEnrollmentNumber = 3;
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                semGetMaxEnrollmentNumber, "getMaxFingerEnroll: ", "FpstFingerprintSettingsUtils");
        return semGetMaxEnrollmentNumber;
    }

    public static boolean getStayOnLockScreen(Context context, int i) {
        if (context == null) {
            return false;
        }
        int intForUser =
                Settings.Secure.getIntForUser(
                        context.getContentResolver(), "fingerprint_stay_on_lock_screen", -1, i);
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                intForUser, "getStayOnLockScreen : ", "FpstFingerprintSettingsUtils");
        if (intForUser != -1) {
            return intForUser == 1;
        }
        setStayOnLockScreen(context, i, false);
        return false;
    }

    public static void isSupportFasterRecognition() {
        Log.d("FpstFingerprintSettingsUtils", "isSupportFasterRecognition : false");
    }

    public static void isSupportFingerprintAlwaysOn() {
        isSupportFingerprintAlwaysOnType();
        Log.d("FpstFingerprintSettingsUtils", "isSupportFingerprintAlwaysOn : true");
    }

    public static void isSupportFingerprintAlwaysOnType() {
        Log.d(
                "FpstFingerprintSettingsUtils",
                "Support Fingerprint Always On for Dual screen : false");
    }

    public static boolean isSupportFingerprintScreenOffIconAod() {
        boolean isSupportAodService = LockUtils.isSupportAodService();
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "isSupportFingerprintScreenOffIconAod : ",
                "FpstFingerprintSettingsUtils",
                isSupportAodService);
        return isSupportAodService;
    }

    public static void removeFingerprintLock(
            int i, Context context, LockPatternUtils lockPatternUtils) {
        Log.d("FpstFingerprintSettingsUtils", "removeFingerprintLock");
        SecurityUtils.removeBiometricLock(context, lockPatternUtils, 1, i);
    }

    public static void setFingerprintAlwaysOnTypeValue(Context context, int i, int i2) {
        setFingerprintAlwaysOnValue(context, i2, i != 0);
        Settings.Secure.putIntForUser(
                context.getContentResolver(), "fingerprint_always_on_type", i, i2);
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("Set Fingerprint Always On Type : "),
                i,
                "FpstFingerprintSettingsUtils");
    }

    public static void setFingerprintAlwaysOnValue(Context context, int i, boolean z) {
        Settings.Secure.putIntForUser(
                context.getContentResolver(), "fingerprint_always_on", z ? 1 : 0, i);
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                new StringBuilder("setFingerprintAlwaysOnValue : "),
                z,
                "FpstFingerprintSettingsUtils");
    }

    public static void setFingerprintEffectValue(Context context, int i, boolean z) {
        Settings.Secure.putIntForUser(
                context.getContentResolver(), "fingerprint_effect", z ? -1 : 0, i);
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                new StringBuilder("setFingerprintEffectValue: "),
                z,
                "FpstFingerprintSettingsUtils");
    }

    public static void setFingerprintLock(
            int i, Context context, LockPatternUtils lockPatternUtils) {
        Log.d("FpstFingerprintSettingsUtils", "setFingerprintLock");
        SecurityUtils.setBiometricLock(context, lockPatternUtils, 1, i);
    }

    public static void setFingerprintLockIconValue(Context context, int i, int i2) {
        Settings.Secure.putIntForUser(
                context.getContentResolver(), "fingerprint_screen_on_icon_lock", i, i2);
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("setFingerprintLockIconValue: "),
                i,
                "FpstFingerprintSettingsUtils");
    }

    public static void setFingerprintScreenOffIconAodValue(Context context, int i, int i2) {
        if (!isSupportFingerprintScreenOffIconAod()) {
            Log.d(
                    "FpstFingerprintSettingsUtils",
                    "setFingerprintScreenOffIconAodValue: not supported device");
        } else {
            Settings.Secure.putIntForUser(
                    context.getContentResolver(), "fingerprint_screen_off_icon_aod", i, i2);
            Preference$$ExternalSyntheticOutline0.m(
                    new StringBuilder("setFingerprintScreenOffIconAodValue: "),
                    i,
                    "FpstFingerprintSettingsUtils");
        }
    }

    public static void setStayOnLockScreen(Context context, int i, boolean z) {
        if (context != null) {
            Settings.Secure.putIntForUser(
                    context.getContentResolver(), "fingerprint_stay_on_lock_screen", z ? 1 : 0, i);
            Preference$$ExternalSyntheticOutline0.m(
                    new StringBuilder("setStayOnLockScreen : "),
                    z ? 1 : 0,
                    "FpstFingerprintSettingsUtils");
        }
    }

    public static void showSensorErrorDialog(Activity activity, String str, boolean z) {
        showSensorErrorDialog(
                activity, activity.getString(R.string.sec_fingerprint_attention), str, z);
    }

    public static void showSensorErrorDialog(
            final Activity activity, String str, String str2, final boolean z) {
        if (activity == null) {
            Log.secE("FpstFingerprintSettingsUtils", "Activity is null. Skip SensorErrorDialog");
            return;
        }
        if (activity.getString(R.string.sec_fingerprint_error_message_sensor_error).equals(str2)) {
            str = activity.getString(R.string.sec_fingerprint_error_message_sensor_error_title);
        }
        AlertDialog create =
                new AlertDialog.Builder(
                                activity, BiometricsGenericHelper.isLightTheme(activity) ? 5 : 4)
                        .setTitle(str)
                        .setMessage(new SeslKoreanGeneralizer().naturalizeText(str2))
                        .setPositiveButton(
                                android.R.string.ok,
                                new FingerprintSettingsUtils$$ExternalSyntheticLambda0())
                        .create();
        create.setOnDismissListener(
                new DialogInterface
                        .OnDismissListener() { // from class:
                                               // com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsUtils$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        boolean z2 = z;
                        Activity activity2 = activity;
                        if (z2) {
                            Intent intent = new Intent();
                            intent.putExtra("screen_lock_force_destroy", true);
                            activity2.setResult(0, intent);
                            activity2.finish();
                        }
                    }
                });
        create.getWindow().setDimAmount(0.3f);
        create.getWindow().addFlags(2);
        create.show();
    }
}
