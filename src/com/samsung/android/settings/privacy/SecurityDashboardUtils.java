package com.samsung.android.settings.privacy;

import android.R;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SemSystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.DateFormat;

import androidx.compose.ui.text.SaversKt$LocaleSaver$2$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.SettingsActivity;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.core.instrumentation.Instrumentable;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.restriction.RestrictionPolicy;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.knox.UCMUtils;
import com.samsung.android.settings.softwareupdate.SoftwareUpdateUtils;
import com.samsung.android.util.SemLog;
import com.sec.ims.extensions.Extensions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SecurityDashboardUtils {
    public static final Uri FMM_SUPPORT_URI;

    static {
        Uri.parse("content://com.samsung.android.samsungaccount.accountmanagerprovider");
        FMM_SUPPORT_URI = Uri.parse("content://com.samsung.android.fmm/fmmsupport");
    }

    public static long getDaysCountSinceLastGPSystemUpdate(Context context) {
        Date date = new Date();
        new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date);
        return TimeUnit.DAYS.convert(
                date.getTime() - ((Date) getGPSystemUpdateDate(context).orElse(date)).getTime(),
                TimeUnit.MILLISECONDS);
    }

    public static long getDaysCountSinceLastSecurityUpdate() {
        Date parse;
        Date date = new Date();
        new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date);
        String str = Build.VERSION.SECURITY_PATCH;
        if (!TextUtils.isEmpty(str)) {
            try {
                parse = new SimpleDateFormat("yyyy-MM-dd").parse(str);
            } catch (ParseException e) {
                SemLog.e(
                        "SecurityDashboardUtils",
                        "Exception details of Security Update parsing date " + e);
            }
            return TimeUnit.DAYS.convert(
                    date.getTime() - ((Date) Optional.ofNullable(parse).orElse(date)).getTime(),
                    TimeUnit.MILLISECONDS);
        }
        parse = null;
        return TimeUnit.DAYS.convert(
                date.getTime() - ((Date) Optional.ofNullable(parse).orElse(date)).getTime(),
                TimeUnit.MILLISECONDS);
    }

    public static Optional getGPSystemUpdateDate(Context context) {
        Date date;
        try {
            date =
                    new SimpleDateFormat("yyyy-MM-dd")
                            .parse(
                                    context.getPackageManager()
                                            .getPackageInfo(
                                                    context.getResources()
                                                            .getString(
                                                                    R.string
                                                                            .default_audio_route_category_name),
                                                    0)
                                            .versionName);
        } catch (PackageManager.NameNotFoundException | ParseException e) {
            e.printStackTrace();
            date = null;
        }
        return Optional.ofNullable(date);
    }

    public static String getGPSystemUpdateSummary(Context context) {
        Optional gPSystemUpdateDate = getGPSystemUpdateDate(context);
        return gPSystemUpdateDate.isEmpty()
                ? ApnSettings.MVNO_NONE
                : DateFormat.format(
                                DateFormat.getBestDateTimePattern(Locale.getDefault(), "dMMMMyyyy"),
                                (Date) gPSystemUpdateDate.get())
                        .toString();
    }

    public static String getSecurityUpdateSummary() {
        String str = Build.VERSION.SECURITY_PATCH;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return DateFormat.format(
                            DateFormat.getBestDateTimePattern(Locale.getDefault(), "dMMMMyyyy"),
                            new SimpleDateFormat("yyyy-MM-dd").parse(str))
                    .toString();
        } catch (ParseException e) {
            SemLog.e("SecurityDashboardUtils", "ParseException : " + e.getMessage());
            return str;
        }
    }

    public static boolean isAccountSignedIn(Context context, String str) {
        return AccountManager.get(context).getAccountsByType(str).length > 0;
    }

    public static boolean isChinaModel() {
        return "China".equalsIgnoreCase(SemSystemProperties.get("ro.csc.country_code"));
    }

    public static boolean isFMMSupported(Context context) {
        try {
            if (context.getPackageManager().getApplicationInfo("com.samsung.android.fmm", 0)
                    != null) {
                ContentResolver contentResolver = context.getContentResolver();
                String string =
                        Settings.Secure.getString(contentResolver, "fmm_main_switch_support");
                SemLog.d("SecurityDashboardUtils", "FMM supported value = " + string);
                if (TextUtils.equals("Y", string)) {
                    return true;
                }
                if (TextUtils.isEmpty(string)) {
                    try {
                        Cursor query =
                                contentResolver.query(FMM_SUPPORT_URI, null, null, null, null);
                        if (query != null) {
                            do {
                                try {
                                    if (query.moveToNext()) {}
                                } finally {
                                }
                            } while ("true".compareTo(query.getString(0)) != 0);
                            SemLog.d("SecurityDashboardUtils", "FMM is supported");
                            query.close();
                            return true;
                        }
                        if (query != null) {
                            query.close();
                        }
                    } catch (Exception e) {
                        SemLog.e(
                                "SecurityDashboardUtils",
                                "FMM Support API failed with exception" + e.getMessage());
                    }
                }
                return false;
            }
        } catch (PackageManager.NameNotFoundException unused) {
            SemLog.e("SecurityDashboardUtils", "Couldn't find FMM package ");
        }
        return false;
    }

    public static boolean isFindMyMobileEnabled(Context context) {
        return isAccountSignedIn(context, "com.osp.app.signin")
                && Settings.System.getInt(context.getContentResolver(), "remote_control", 0) == 1;
    }

    public static boolean isGPSuSupported(Context context) {
        return (TextUtils.isEmpty(context.getString(R.string.default_audio_route_category_name))
                        ^ true)
                && !isChinaModel()
                && isAccountSignedIn(context, RestrictionPolicy.GOOGLE_ACCOUNT_TYPE)
                && !Utils.isRestrictedProfile(context);
    }

    public static boolean isGoogleServiceEnabled(Context context) {
        try {
            return context.getPackageManager()
                    .getApplicationInfo("com.google.android.gms", 0)
                    .enabled;
        } catch (PackageManager.NameNotFoundException unused) {
            SemLog.d("SecurityDashboardUtils", "Google Service package is not found");
            return false;
        }
    }

    public static boolean isMDMEnabled(Context context) {
        return Utils.getEnterprisePolicyEnabled(
                                context,
                                "content://com.sec.knox.provider/RestrictionPolicy2",
                                "isLockScreenEnabled",
                                new String[] {"false"})
                        == 0
                || KnoxUtils.checkKnoxCustomSettingsHiddenItem(16)
                || UCMUtils.isUCMKeyguardEnabled();
    }

    public static boolean isOfflineLockSupported(Context context) {
        ResolveInfo resolveActivity =
                context.getPackageManager()
                        .resolveActivity(
                                new Intent(
                                                "com.google.android.gms.personalsafety.settings.AUTO_LOCK_SETTINGS")
                                        .setPackage("com.google.android.gms"),
                                0);
        return (resolveActivity == null || resolveActivity.activityInfo == null) ? false : true;
    }

    public static boolean isRemoteLockSupported(Context context) {
        ResolveInfo resolveActivity =
                context.getPackageManager()
                        .resolveActivity(
                                new Intent("com.google.android.gms.settings.QRL_SETTINGS")
                                        .setPackage("com.google.android.gms"),
                                0);
        return (resolveActivity == null || resolveActivity.activityInfo == null) ? false : true;
    }

    public static boolean isSamsungAccountSupported(Context context) {
        Bundle bundle;
        try {
            ApplicationInfo applicationInfo =
                    context.getPackageManager().getApplicationInfo("com.osp.app.signin", 128);
            float f = 0.0f;
            if (applicationInfo != null && (bundle = applicationInfo.metaData) != null) {
                f = bundle.getFloat("SecurityInfoProvider", 0.0f);
            }
            SemLog.d("SecurityDashboardUtils", "checkIfSASupportsSecurityInfo: version = " + f);
            return f >= 3.0f;
        } catch (PackageManager.NameNotFoundException unused) {
            SemLog.e("SecurityDashboardUtils", "Couldn't find Samsung Account Package ");
            return false;
        }
    }

    public static boolean isScreenLockEnabled(Context context) {
        int myUserId = Extensions.UserHandle.myUserId();
        StringBuilder sb = Utils.sBuilder;
        return new LockPatternUtils(context).getCredentialTypeForUser(myUserId) != -1;
    }

    public static boolean isSecurityUpdateInstalled(Context context) {
        if (!Utils.isPackageEnabled(context, "com.ws.dm")) {
            if (!(Utils.isPackageEnabled(context, "com.samsung.sdm.sdmviewer")
                    ? true
                    : Utils.isPackageEnabled(context, "com.samsung.sdm")
                            ? SoftwareUpdateUtils.isVZWFotaNewEnabled(context)
                            : false)) {
                int fotaBadgeCount = SoftwareUpdateUtils.getFotaBadgeCount(context);
                SemLog.d(
                        "SecurityDashboardUtils",
                        "Security update fota badge count - >" + fotaBadgeCount);
                return fotaBadgeCount == 0;
            }
        }
        int i = Settings.System.getInt(context.getContentResolver(), "available_for_update", 0);
        SemLog.d("SecurityDashboardUtils", "Security update available for update - >" + i);
        return i == 0;
    }

    public static boolean isTheftProtectionSupported(Context context) {
        String str = SemSystemProperties.get("ro.product.device", ApnSettings.MVNO_NONE);
        if (str.startsWith("e1") || str.startsWith("e2") || str.startsWith("e3")) {
            return false;
        }
        return Settings.Secure.getInt(
                                context.getContentResolver(), "theft_detection_lock_supported", 0)
                        != 0
                || isOfflineLockSupported(context)
                || isRemoteLockSupported(context);
    }

    public static void launchChooseLockScreen(Context context) {
        context.startActivity(
                new Intent("android.app.action.SET_NEW_PASSWORD")
                        .setPackage(context.getPackageName()));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void startFragment(int i, Fragment fragment, String str) {
        FragmentActivity activity = fragment.getActivity();
        if (!(activity instanceof SettingsActivity)) {
            SaversKt$LocaleSaver$2$$ExternalSyntheticOutline0.m(
                    "Parent isn't SettingsActivity nor PreferenceActivity, thus there's no way to"
                        + " launch the given Fragment (name: ",
                    str,
                    ", requestCode: 0)",
                    "SecurityDashboardUtils");
            return;
        }
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(activity);
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = str;
        subSettingLauncher.setTitleRes(i, null);
        launchRequest.mSourceMetricsCategory =
                fragment instanceof Instrumentable
                        ? ((Instrumentable) fragment).getMetricsCategory()
                        : 0;
        subSettingLauncher.launch();
        activity.overridePendingTransition(0, 0);
    }
}
