package com.android.settings.applications;

import android.app.LocaleConfig;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.LocaleList;
import android.text.TextUtils;
import android.util.FeatureFlagUtils;
import android.util.Log;

import com.android.settings.R;

import java.util.List;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppLocaleUtil {
    public static final boolean DEBUG = Build.IS_DEBUGGABLE;
    public static final Intent LAUNCHER_ENTRY_INTENT =
            new Intent("android.intent.action.MAIN")
                    .addCategory("android.intent.category.LAUNCHER");
    static LocaleConfig sLocaleConfig;

    public static boolean canDisplayLocaleUi(
            Context context, ApplicationInfo applicationInfo, List list) {
        boolean z;
        String str = applicationInfo.packageName;
        String[] stringArray =
                context.getResources()
                        .getStringArray(R.array.config_disallowed_app_localeChange_packages);
        int length = stringArray.length;
        boolean z2 = false;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = false;
                break;
            }
            if (TextUtils.equals(str, stringArray[i])) {
                z = true;
                break;
            }
            i++;
        }
        final String str2 = applicationInfo.packageName;
        boolean anyMatch =
                list.stream()
                        .anyMatch(
                                new Predicate() { // from class:
                                                  // com.android.settings.applications.AppLocaleUtil$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        return ((ResolveInfo) obj)
                                                .activityInfo.packageName.equals(str2);
                                    }
                                });
        boolean isSignedWithPlatformKey = applicationInfo.isSignedWithPlatformKey();
        if (!z && !isSignedWithPlatformKey && anyMatch) {
            String str3 = applicationInfo.packageName;
            LocaleConfig localeConfig = sLocaleConfig;
            LocaleList packageLocales =
                    localeConfig != null
                            ? getPackageLocales(localeConfig)
                            : getPackageLocales(context, str3);
            if (packageLocales == null
                    ? !FeatureFlagUtils.isEnabled(context, "settings_app_locale_opt_in_enabled")
                            && getAssetLocales(context, str3).length > 0
                    : packageLocales.size() > 0) {
                z2 = true;
            }
        }
        if (DEBUG) {
            Log.i(
                    "AppLocaleUtil",
                    "Can display preference - ["
                            + applicationInfo.packageName
                            + "] : isDisallowedPackage : "
                            + z
                            + " / isSignedWithPlatformKey : "
                            + isSignedWithPlatformKey
                            + " / hasLauncherEntry : "
                            + anyMatch
                            + " / canDisplay : "
                            + z2
                            + " / 1.1");
        }
        return z2;
    }

    public static String[] getAssetLocales(Context context, String str) {
        try {
            String[] nonSystemLocales =
                    context.getPackageManager()
                            .getResourcesForApplication(str)
                            .getAssets()
                            .getNonSystemLocales();
            if (nonSystemLocales == null) {
                Log.i("AppLocaleUtil", "[" + str + "] locales are null.");
            }
            if (nonSystemLocales.length <= 0) {
                Log.i("AppLocaleUtil", "[" + str + "] locales length is 0.");
                return new String[0];
            }
            if (DEBUG) {
                Log.i("AppLocaleUtil", "First asset locale - [" + str + "] " + nonSystemLocales[0]);
            }
            return nonSystemLocales;
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("AppLocaleUtil", "Can not found the package name : " + str + " / " + e);
            return new String[0];
        }
    }

    public static LocaleList getPackageLocales(LocaleConfig localeConfig) {
        if (localeConfig.getStatus() == 0) {
            return localeConfig.getSupportedLocales();
        }
        return null;
    }

    public static LocaleList getPackageLocales(Context context, String str) {
        try {
            return getPackageLocales(new LocaleConfig(context.createPackageContext(str, 0)));
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("AppLocaleUtil", "Can not found the package name : " + str + " / " + e);
            return null;
        }
    }
}
