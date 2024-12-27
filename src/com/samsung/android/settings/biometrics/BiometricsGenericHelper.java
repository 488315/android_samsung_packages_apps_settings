package com.samsung.android.settings.biometrics;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.password.BiometricsChooseLockGeneric;
import com.android.settings.password.ScreenLockType;
import com.android.settings.password.SetupBiometricsChooseLockGeneric;

import com.samsung.android.app.SemMultiWindowManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.theftprotection.TheftProtectionUtils;
import com.samsung.android.settings.theftprotection.timer.ProtectionTimerService$TimeDelayState;
import com.sec.ims.configuration.DATA;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class BiometricsGenericHelper {
    public static int getAppId(int i) {
        int i2;
        if (i == 1) {
            i2 = R.string.bio_fingerprint_sanner_title;
        } else if (i != 256) {
            Log.e("BiometricsGenericHelper", "Wrong case!!");
            i2 = R.string.biometrics_and_security_title;
        } else {
            i2 = R.string.bio_face_recognition_title;
        }
        MainClearConfirm$$ExternalSyntheticOutline0.m(i2, "appId : ", "BiometricsGenericHelper");
        return i2;
    }

    public static Intent getBiometricsChooseLockGenericIntent(Context context, boolean z) {
        return z
                ? new Intent(context, (Class<?>) SetupBiometricsChooseLockGeneric.class)
                : new Intent(context, (Class<?>) BiometricsChooseLockGeneric.class);
    }

    public static String getConfirmLockHeader(Context context, int i) {
        ScreenLockType fromQuality =
                ScreenLockType.fromQuality(
                        new LockPatternUtils(context).getKeyguardStoredPasswordQuality(i));
        if (ScreenLockType.PATTERN.equals(fromQuality)) {
            return context.getString(R.string.sec_biometrics_confirm_screen_lock_pattern);
        }
        if (ScreenLockType.PASSWORD.equals(fromQuality)) {
            return context.getString(R.string.sec_biometrics_confirm_screen_lock_password);
        }
        if (ScreenLockType.PIN.equals(fromQuality)) {
            return context.getString(R.string.sec_biometrics_confirm_screen_lock_pin);
        }
        return null;
    }

    public static Intent getFmmPopupIntent() {
        Log.d("BiometricsGenericHelper", "getFmmPopupIntent");
        Intent intent = new Intent();
        intent.setPackage("com.samsung.android.fmm");
        intent.setAction("com.samsung.android.fmm.action.UserpppDialog");
        return intent;
    }

    public static int getFullScreenHeight(Context context) {
        Display defaultDisplay =
                ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        try {
            Display.class.getMethod("getRealSize", Point.class).invoke(defaultDisplay, point);
            int i = point.y;
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i, "getFullScreenHeight : ", "BiometricsGenericHelper");
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static Intent getMandatoryBiometricIntentForBiometricsSetting(
            Context context, int i, boolean z) {
        Intent intent = new Intent("com.samsung.intent.action.MANDATORY_BIOMETRICS_PROMPT");
        intent.putExtra("pp_security_delay", true);
        intent.putExtra(
                "pp_security_delay_title_res",
                R.string.sec_biometrics_mandatory_biometirc_disclaimer_title_change_biometrics);
        intent.putExtra(
                "pp_security_delay_description_res",
                R.string
                        .sec_biometrics_mandaotry_biometric_disclaimer_in_progress_description_biometrics);
        if (i == 1) {
            intent.putExtra("pp_setting_name_res", R.string.sec_fingerprint_useful_feature_title);
            if (z) {
                intent.putExtra("pp_action_name", "android.settings.FINGERPRINT_ENROLL");
            } else {
                intent.putExtra("pp_action_name", "android.settings.FINGERPRINT_SETTINGS");
            }
        } else if (i == 256) {
            intent.putExtra(
                    "pp_setting_name_res", R.string.sec_face_useful_face_recognition_feature_title);
            if (z) {
                intent.putExtra("pp_action_name", "com.samsung.settings.REGISTER_FACE");
            } else {
                intent.putExtra("pp_action_name", "android.settings.FACE_SETTINGS");
            }
        }
        intent.putExtra("pp_package_name", context.getPackageName());
        intent.putExtra(
                "pp_noti_in_progress_res",
                R.string.sec_biometircs_mandatory_biometric_notification_in_progress_content);
        intent.putExtra(
                "pp_noti_delay_end_res",
                R.string.sec_biometircs_mandatory_biometric_notification_delay_ended_content);
        intent.putExtra("pp_caller_name", "ChangeBiometric");
        return intent;
    }

    public static ProtectionTimerService$TimeDelayState getMandatoryBiometricsDelayTimeState(
            Context context) {
        long j =
                Settings.Secure.getLong(
                        context.getContentResolver(), "mandatory_biometrics_grace_time", 0L);
        long j2 =
                Settings.Secure.getLong(
                        context.getContentResolver(), "mandatory_biometrics_delay_time", 0L);
        StringBuilder m =
                SnapshotStateObserver$$ExternalSyntheticOutline0.m(
                        j, "getMandatoryBiometricsDelayTimeState: grace: ", ", delayTime: ");
        m.append(j2);
        Log.d("BiometricsGenericHelper", m.toString());
        return Settings.Secure.getLong(
                                context.getContentResolver(), "mandatory_biometrics_grace_time", 0L)
                        > 0
                ? ProtectionTimerService$TimeDelayState.GRACE_PERIOD
                : Settings.Secure.getLong(
                                        context.getContentResolver(),
                                        "mandatory_biometrics_delay_time",
                                        0L)
                                > 0
                        ? ProtectionTimerService$TimeDelayState.SECURITY_DELAY
                        : ProtectionTimerService$TimeDelayState.NONE;
    }

    public static String getSaLogIdByDisplayType(Context context, int i) {
        if (context.getResources().getConfiguration().semDisplayDeviceType != 5) {
            return String.valueOf(i);
        }
        return i + "_S";
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        if (displayMetrics == null) {
            Log.e("BiometricsGenericHelper", "DisplayMetrics is null!");
            return 0;
        }
        int i = displayMetrics.widthPixels;
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "screenWidth : ", "BiometricsGenericHelper");
        return i;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0041  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int getSecurityLevel(android.content.Context r7, int r8) {
        /*
            java.lang.String r0 = "BiometricsGenericHelper"
            r1 = 2
            r2 = 3
            r3 = 0
            r4 = 1
            if (r8 != r4) goto L29
            boolean r5 = com.samsung.android.settings.security.SecurityUtils.hasFingerprintFeature(r7)
            if (r5 == 0) goto L29
            android.hardware.fingerprint.FingerprintManager r5 = com.android.settings.Utils.getFingerprintManagerOrNull(r7)
            if (r5 == 0) goto L29
            int r5 = r5.semGetSecurityLevel()
            java.lang.String r6 = "fingerprintSecurityLevel : "
            androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0.m1m(r5, r6, r0)
            if (r5 != r2) goto L21
            r5 = r2
            goto L2a
        L21:
            if (r5 != r1) goto L25
            r5 = r1
            goto L2a
        L25:
            if (r5 != r4) goto L29
            r5 = r4
            goto L2a
        L29:
            r5 = r3
        L2a:
            r6 = 256(0x100, float:3.59E-43)
            if (r8 != r6) goto L48
            android.hardware.face.FaceManager r7 = com.android.settings.Utils.getFaceManagerOrNull(r7)
            if (r7 == 0) goto L48
            int r7 = r7.semGetSecurityLevel(r3)
            java.lang.String r8 = "faceSecurityLevel : "
            androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0.m1m(r7, r8, r0)
            if (r7 != r2) goto L41
            r1 = r2
            goto L49
        L41:
            if (r7 != r1) goto L44
            goto L49
        L44:
            if (r7 != r4) goto L48
            r1 = r4
            goto L49
        L48:
            r1 = r5
        L49:
            java.lang.String r7 = "getSecurityLevel : "
            androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0.m1m(r1, r7, r0)
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.biometrics.BiometricsGenericHelper.getSecurityLevel(android.content.Context,"
                    + " int):int");
    }

    public static String getSecurityLevelDescription(Context context, int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "getSecurityLevelDescription : ", "BiometricsGenericHelper");
        if (context == null) {
            return ApnSettings.MVNO_NONE;
        }
        int securityLevel = getSecurityLevel(context, i);
        if (securityLevel == 1) {
            return context.getResources()
                    .getQuantityString(
                            R.plurals.sec_biometrics_common_about_security_notice_body_strong,
                            72,
                            72);
        }
        if (securityLevel == 2) {
            return context.getString(
                    R.string.sec_biometrics_common_about_security_notice_body_weak, 24, 4);
        }
        if (securityLevel == 3) {
            return context.getString(
                    R.string.sec_biometrics_common_about_security_notice_body_convenience, 24, 4);
        }
        Log.w("BiometricsGenericHelper", "getDescriptionString : wrong security level");
        return ApnSettings.MVNO_NONE;
    }

    public static void hideMenuList(FragmentActivity fragmentActivity, View view, boolean z) {
        ViewGroup viewGroup =
                (ViewGroup) fragmentActivity.findViewById(android.R.id.list_container);
        if (viewGroup == null || view == null) {
            return;
        }
        if (!z) {
            if (view.getParent() == viewGroup) {
                viewGroup.removeView(view);
            }
        } else {
            view.semSetRoundedCorners(3);
            view.semSetRoundedCornerColor(
                    3,
                    fragmentActivity
                            .getResources()
                            .getColor(R.color.sec_widget_round_and_bgcolor, null));
            view.setBackgroundColor(
                    fragmentActivity
                            .getResources()
                            .getColor(R.color.list_item_background_color, null));
            if (view.getParent() == null) {
                viewGroup.addView(view);
            }
        }
    }

    public static void insertSaLog(Context context, int i, int i2) {
        LoggingHelper.insertEventLogging(getSaLogIdByDisplayType(context, i), i2);
    }

    public static boolean isAvailableMemorySizeToEnroll() {
        long j;
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            j = statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong();
        } catch (Exception e) {
            e.printStackTrace();
            j = -1;
        }
        return j < 0 || j >= 10485760;
    }

    public static boolean isBlockedInMultiWindowMode(Context context, Configuration configuration) {
        return !isEnabledBiometricsMenuInDexMode(context)
                && (configuration.windowConfiguration.getWindowingMode() == 5
                        || configuration.windowConfiguration.isSplitScreen());
    }

    public static boolean isEnabledBiometricsMenuInDexMode(Context context) {
        return Utils.isDesktopStandaloneMode(context) || Utils.isNewDexMode(context);
    }

    public static void isLandscapeDefaultModel() {
        Log.d("BiometricsGenericHelper", "isLandscapeDefaultModel : false");
    }

    public static boolean isLightTheme(Context context) {
        return context == null || (context.getResources().getConfiguration().uiMode & 48) != 32;
    }

    public static boolean isMandatoryBiometricsAccessible(Context context) {
        return getMandatoryBiometricsDelayTimeState(context)
                        == ProtectionTimerService$TimeDelayState.GRACE_PERIOD
                || TheftProtectionUtils.isInTrustedPlace(context);
    }

    public static boolean isPopupWindowMode() {
        int mode = new SemMultiWindowManager().getMode();
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                mode, "isPopupWindowMode : ", "BiometricsGenericHelper");
        return (mode & 1) == 1;
    }

    public static void makeLinkText(
            Context context, TextView textView, String str, ClickableSpan clickableSpan) {
        if (textView == null) {
            return;
        }
        Typeface create = Typeface.create(Typeface.create("sec", 0), 600, false);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        spannableStringBuilder.setSpan(clickableSpan, 0, str.length(), 0);
        spannableStringBuilder.setSpan(
                new ForegroundColorSpan(
                        context.getResources()
                                .getColor(
                                        R.color.sec_biometrics_guide_common_guide_tip_color, null)),
                0,
                str.length(),
                0);
        spannableStringBuilder.setSpan(new TypefaceSpan(create), 0, str.length(), 0);
        SpannableStringBuilder spannableStringBuilder2 =
                new SpannableStringBuilder(textView.getText());
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(
                spannableStringBuilder2
                        .append((CharSequence) "\n")
                        .append((CharSequence) spannableStringBuilder));
    }

    public static boolean makeTipLink(
            Context context, TextView textView, int i, ClickableSpan clickableSpan) {
        if (textView == null) {
            return false;
        }
        String string =
                context.getString(
                        i,
                        DATA.DM_FIELD_INDEX.SMS_WRITE_UICC,
                        DATA.DM_FIELD_INDEX.EMERGENCY_CONTROL_PREF);
        int indexOf = TextUtils.indexOf(string, DATA.DM_FIELD_INDEX.SMS_WRITE_UICC);
        int indexOf2 = TextUtils.indexOf(string, DATA.DM_FIELD_INDEX.EMERGENCY_CONTROL_PREF);
        if (string == null || indexOf == -1 || indexOf2 == -1) {
            Log.w("BiometricsGenericHelper", "Link index is wrong!!");
            return false;
        }
        SpannableString spannableString =
                new SpannableString(
                        string.replaceAll(DATA.DM_FIELD_INDEX.SMS_WRITE_UICC, ApnSettings.MVNO_NONE)
                                .replaceAll(
                                        DATA.DM_FIELD_INDEX.EMERGENCY_CONTROL_PREF,
                                        ApnSettings.MVNO_NONE));
        spannableString.setSpan(clickableSpan, indexOf, indexOf2 - 2, 0);
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        return true;
    }

    public static boolean needFmmBackupPasswordPopup(Context context) {
        if ((context instanceof Activity)
                && SemPersonaManager.isKnoxId(
                        ((Activity) context)
                                .getIntent()
                                .getIntExtra(
                                        "android.intent.extra.USER_ID", UserHandle.myUserId()))) {
            Log.d("BiometricsGenericHelper", "Knox case. Fmm backup popup is false.");
            return false;
        }
        int i = Settings.Secure.getInt(context.getContentResolver(), "fmm_unlock_recovery", 0);
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "needFmmBackupPasswordPopup : ", "BiometricsGenericHelper");
        if (i == 0) {
            Cursor query =
                    context.getContentResolver()
                            .query(
                                    Uri.parse("content://com.samsung.android.fmm/fmmsupport"),
                                    null,
                                    null,
                                    null,
                                    null);
            if (query != null) {
                try {
                    try {
                        int columnIndex = query.getColumnIndex("fmmsupport");
                        while (query.moveToNext()) {
                            if (Boolean.valueOf(query.getString(columnIndex)).booleanValue()) {
                                Log.d(
                                        "BiometricsGenericHelper",
                                        "Support FMM : " + query.getString(columnIndex));
                                query.close();
                                Log.d(
                                        "BiometricsGenericHelper",
                                        "needFmmBackupPasswordPopup : true");
                                return true;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } finally {
                    query.close();
                }
            }
            Log.d("BiometricsGenericHelper", "isSupportFindMyMobileFeature : false");
        }
        Log.d("BiometricsGenericHelper", "needFmmBackupPasswordPopup : false");
        return false;
    }

    public static void removeSideMargin(Activity activity) {
        Log.d("BiometricsGenericHelper", "removeSideMargin");
        LinearLayout linearLayout = (LinearLayout) activity.findViewById(R.id.content_start_pane);
        LinearLayout linearLayout2 = (LinearLayout) activity.findViewById(R.id.content_end_pane);
        if (linearLayout == null || linearLayout2 == null) {
            return;
        }
        linearLayout.setVisibility(8);
        linearLayout2.setVisibility(8);
    }

    public static void insertSaLog(Context context, int i) {
        SALogging.insertSALog(getSaLogIdByDisplayType(context, i));
    }
}
