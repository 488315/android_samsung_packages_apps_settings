package com.samsung.android.settings.theftprotection;

import android.accounts.AccountManager;
import android.app.trust.TrustManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricManager;
import android.os.Environment;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.biometrics.fingerprint.FingerprintLockSettings;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.theftprotection.location.LocationManager$$ExternalSyntheticLambda0;
import com.samsung.android.settings.theftprotection.location.LocationStorage;
import com.samsung.android.settings.theftprotection.logging.Log;
import com.sec.ims.configuration.DATA;

import java.io.File;
import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class TheftProtectionUtils {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.theftprotection.TheftProtectionUtils$1, reason: invalid class name */
    public final class AnonymousClass1 extends ClickableSpan {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final /* synthetic */ Context val$context;

        public AnonymousClass1(Context context) {
            this.val$context = context;
        }

        @Override // android.text.style.ClickableSpan
        public final void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.val$context);
            builder.setPositiveButton(
                    R.string.dlg_ok, new TheftProtectionUtils$1$$ExternalSyntheticLambda0());
            View inflate =
                    LayoutInflater.from(this.val$context)
                            .inflate(R.layout.mandatory_biometric_info_dialog, (ViewGroup) null);
            builder.P.mTitle = "How Identity check protects you";
            builder.setView(inflate);
            builder.create().show();
        }

        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
        public final void updateDrawState(TextPaint textPaint) {
            super.updateDrawState(textPaint);
            textPaint.setUnderlineText(true);
            textPaint.setColor(this.val$context.getColor(R.color.sec_widget_body_text_color));
        }
    }

    public static Intent getIntentForMandatoryBiometricsSetting(boolean z) {
        Intent intent = new Intent("com.samsung.intent.action.MANDATORY_BIOMETRICS_PROMPT");
        intent.putExtra("pp_security_delay", z);
        intent.putExtra("pp_security_delay_title_res", R.string.protection_timer_disclaimer_title);
        intent.putExtra("pp_security_delay_description_res", R.string.protection_timer_description);
        intent.putExtra("pp_noti_in_progress_res", R.string.protection_timer_notification_message);
        intent.putExtra(
                "pp_noti_delay_end_res", R.string.protection_timer_expiration_notification_message);
        intent.putExtra(
                "pp_noti_delay_end_immediately_res",
                R.string.protection_timer_immediately_expiration_notification_message);
        intent.putExtra("pp_package_name", KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
        intent.putExtra("pp_action_name", "com.samsung.intent.action.IDENTITY_CHECK_MENU");
        intent.putExtra("pp_caller_name", "RequireBiometricOff");
        return intent;
    }

    public static int getMandatoryBiometricStatus(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), "mandatory_biometrics", -1);
    }

    public static String getStringFromPackage(Context context, int i, String str) {
        try {
            return context.createPackageContext(str, 0).getString(i);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("TheftProtectionUtils", "getStringFromPackage: " + e.getMessage());
            return null;
        }
    }

    public static boolean hasBiometricStrong(Context context) {
        try {
            int canAuthenticate =
                    ((BiometricManager) context.getSystemService("biometric")).canAuthenticate(15);
            return canAuthenticate == 0 || 7 == canAuthenticate || 9 == canAuthenticate;
        } catch (SecurityException e) {
            Log.e("TheftProtectionUtils", "hasBiometricStrong: " + e.getMessage());
            return false;
        }
    }

    public static boolean isInTrustedPlace(Context context) {
        TrustManager trustManager = (TrustManager) context.getSystemService("trust");
        boolean isInSignificantPlace =
                trustManager != null ? trustManager.isInSignificantPlace() : false;
        Log.d("TheftProtectionUtils", "isInTrustedPlace : " + isInSignificantPlace);
        return isInSignificantPlace;
    }

    public static boolean isMandatoryBiometricEnabled(Context context) {
        boolean z = getMandatoryBiometricStatus(context) == 1;
        boolean z2 = !hasBiometricStrong(context);
        Log.d(
                "TheftProtectionUtils",
                "isMandatoryBiometricEnabled: mandatoryBiometric=" + z + ", hasMissingInfo=" + z2);
        return z && !z2;
    }

    public static boolean isSamsungAccountSignedIn(Context context) {
        return AccountManager.get(context).getAccountsByType("com.osp.app.signin").length > 0;
    }

    public static void isSecurityDelayTest() {
        new File(Environment.getExternalStorageDirectory(), "go_to_nebula.test");
    }

    public static void setLinkableText(TextView textView) {
        if (textView == null || textView.getText() == null) {
            return;
        }
        String replace =
                textView.getText()
                        .toString()
                        .replace("%1$s", DATA.DM_FIELD_INDEX.SMS_WRITE_UICC)
                        .replace("%2$s", DATA.DM_FIELD_INDEX.EMERGENCY_CONTROL_PREF);
        int indexOf = TextUtils.indexOf(replace, DATA.DM_FIELD_INDEX.SMS_WRITE_UICC);
        int indexOf2 = TextUtils.indexOf(replace, DATA.DM_FIELD_INDEX.EMERGENCY_CONTROL_PREF);
        if (indexOf == -1 || indexOf2 == -1) {
            return;
        }
        int i = indexOf2 + 2;
        if (indexOf <= i) {
            i = indexOf;
            indexOf = i;
        }
        String replaceAll =
                replace.replaceAll(DATA.DM_FIELD_INDEX.SMS_WRITE_UICC, ApnSettings.MVNO_NONE)
                        .replaceAll(
                                DATA.DM_FIELD_INDEX.EMERGENCY_CONTROL_PREF, ApnSettings.MVNO_NONE);
        int i2 = indexOf - 4;
        if (i2 > replaceAll.length()) {
            textView.setText(replaceAll);
            return;
        }
        Context context = textView.getContext();
        SpannableString spannableString = new SpannableString(replaceAll);
        spannableString.setSpan(new AnonymousClass1(context), i, i2, 33);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(spannableString);
    }

    public static void setMandatoryBiometricStatus(Context context, int i) {
        Settings.Secure.putInt(context.getContentResolver(), "mandatory_biometrics", i);
        if (i != 1) {
            LocationStorage locationStorage = LocationStorage.InstanceHolder.INSTANCE;
            ((ArrayList) locationStorage.loadLocationData())
                    .forEach(new TheftProtectionUtils$$ExternalSyntheticLambda0());
            Log.d("LocationManager", "unregisterAllGeofence");
            ((ArrayList) locationStorage.loadLocationData())
                    .forEach(new LocationManager$$ExternalSyntheticLambda0(context, 0));
            updateInSignificantPlace(context, false);
            Intent intent =
                    new Intent("com.samsung.android.settings.action.GRACE_TIME_FORCE_TERMINATION");
            intent.setPackage(context.getPackageName());
            context.sendBroadcast(intent);
        } else if (isMandatoryBiometricEnabled(context)) {
            Log.d("LocationManager", "registerAllGeofence");
            ((ArrayList) LocationStorage.InstanceHolder.INSTANCE.loadLocationData())
                    .forEach(new LocationManager$$ExternalSyntheticLambda0(context, 1));
        }
        updateRequirementsSatisfied(context);
        LoggingHelper.insertEventLogging(54101, 54204, String.valueOf(i));
    }

    public static void showProtectionPrompt(Fragment fragment, int i) {
        Intent intent = new Intent("com.samsung.intent.action.MANDATORY_BIOMETRICS_PROMPT");
        intent.putExtra("pp_security_delay", true);
        intent.putExtra(
                "pp_security_delay_title_res",
                R.string.protection_timer_disclaimer_title_for_trusted_places);
        intent.putExtra(
                "pp_security_delay_description_res",
                R.string.protection_timer_description_for_trusted_places);
        intent.putExtra(
                "pp_noti_in_progress_res",
                R.string.protection_timer_notification_message_for_trusted_places);
        intent.putExtra(
                "pp_noti_delay_end_res",
                R.string.protection_timer_expiration_notification_message_for_trusted_places);
        intent.putExtra(
                "pp_noti_delay_end_immediately_res",
                R.string
                        .protection_timer_immediately_expiration_notification_message_for_trusted_places);
        intent.putExtra("pp_package_name", KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
        intent.putExtra("pp_action_name", "com.samsung.intent.action.IDENTITY_CHECK_MENU");
        intent.putExtra(
                "pp_caller_name",
                i == 1001
                        ? "AddTrustedLocation"
                        : i == 1002 ? "DeleteTrustedLocation" : "EditTrustedLocation");
        fragment.startActivityForResult(intent, i);
    }

    public static void showProtectionPromptWithAccount(Fragment fragment) {
        Intent intentForMandatoryBiometricsSetting = getIntentForMandatoryBiometricsSetting(true);
        if (isSamsungAccountSignedIn(fragment.getContext())) {
            intentForMandatoryBiometricsSetting.putExtra("pp_account_authentication", true);
            intentForMandatoryBiometricsSetting.putExtra("pp_client_id", "s5d189ajvs");
        }
        fragment.startActivityForResult(intentForMandatoryBiometricsSetting, 1001);
    }

    public static void startFingerprintLockSettings(Context context) {
        Intent intent = new Intent().setClass(context, FingerprintLockSettings.class);
        intent.putExtra("previousStage", "FingerprintSettings_register");
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.e(
                    "TheftProtectionUtils",
                    "Fingerprint Lock Settings Not Found : " + e.getMessage());
        }
    }

    public static void updateInSignificantPlace(Context context, boolean z) {
        Log.d("TheftProtectionUtils", "updateInSignificantPlace : " + z);
        if (isInTrustedPlace(context) == z) {
            Log.d("TheftProtectionUtils", "updateInSignificantPlace : same with current status");
            return;
        }
        Settings.Secure.putInt(context.getContentResolver(), "in_trusted_location", z ? 1 : 0);
        if (z) {
            Intent intent = new Intent("com.samsung.android.settings.action.ENTER_TRUSTED_PLACES");
            intent.setPackage(context.getPackageName());
            context.sendBroadcast(intent);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0039  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void updateRequirementsSatisfied(android.content.Context r4) {
        /*
            r0 = 0
            java.lang.String r1 = "biometric"
            java.lang.Object r1 = r4.getSystemService(r1)     // Catch: java.lang.SecurityException -> L13
            android.hardware.biometrics.BiometricManager r1 = (android.hardware.biometrics.BiometricManager) r1     // Catch: java.lang.SecurityException -> L13
            r2 = 15
            int r1 = r1.canAuthenticate(r2)     // Catch: java.lang.SecurityException -> L13
            if (r1 != 0) goto L2c
            r1 = 1
            goto L2d
        L13:
            r1 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "setRequirementsSatisfied: "
            r2.<init>(r3)
            java.lang.String r1 = r1.getMessage()
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            java.lang.String r2 = "TheftProtectionUtils"
            com.samsung.android.settings.theftprotection.logging.Log.e(r2, r1)
        L2c:
            r1 = r0
        L2d:
            android.content.ContentResolver r2 = r4.getContentResolver()
            java.lang.String r3 = "mandatory_biometrics_requirements_satisfied"
            int r0 = android.provider.Settings.Secure.getInt(r2, r3, r0)
            if (r0 == r1) goto L40
            android.content.ContentResolver r4 = r4.getContentResolver()
            android.provider.Settings.Secure.putInt(r4, r3, r1)
        L40:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.theftprotection.TheftProtectionUtils.updateRequirementsSatisfied(android.content.Context):void");
    }
}
