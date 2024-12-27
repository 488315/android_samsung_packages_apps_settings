package com.android.settings.applications;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.InstallSourceInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.util.Pair;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppStoreUtil {
    public static Intent getAppStoreLink(Context context, String str, String str2) {
        Intent resolveIntent =
                resolveIntent(
                        context, new Intent("android.intent.action.SHOW_APP_INFO").setPackage(str));
        if (resolveIntent == null) {
            return null;
        }
        resolveIntent.putExtra("android.intent.extra.PACKAGE_NAME", str2);
        return resolveIntent;
    }

    public static Pair getInstallerPackageNameAndInstallSourceInfo(Context context, String str) {
        InstallSourceInfo installSourceInfo;
        String installingPackageName;
        String originatingPackageName;
        String initiatingPackageName;
        Objects.requireNonNull(context);
        Objects.requireNonNull(str);
        String str2 = null;
        try {
            installSourceInfo = context.getPackageManager().getInstallSourceInfo(str);
        } catch (PackageManager.NameNotFoundException e) {
            e = e;
            installSourceInfo = null;
        }
        try {
            installingPackageName = installSourceInfo.getInstallingPackageName();
            originatingPackageName = installSourceInfo.getOriginatingPackageName();
            initiatingPackageName = installSourceInfo.getInitiatingPackageName();
        } catch (PackageManager.NameNotFoundException e2) {
            e = e2;
            Log.e(
                    "AppStoreUtil",
                    "Exception while retrieving the package installer of ".concat(str),
                    e);
            return new Pair(str2, installSourceInfo);
        }
        if (originatingPackageName != null
                && initiatingPackageName != null
                && !initiatingPackageName.equals("com.android.shell")) {
            if ((context.getPackageManager().getApplicationInfo(initiatingPackageName, 0).flags & 1)
                    != 0) {
                str2 = originatingPackageName;
                return new Pair(str2, installSourceInfo);
            }
        }
        str2 = installingPackageName;
        return new Pair(str2, installSourceInfo);
    }

    public static Intent resolveIntent(Context context, Intent intent) {
        ResolveInfo resolveActivity = context.getPackageManager().resolveActivity(intent, 0);
        if (resolveActivity == null) {
            return null;
        }
        Intent intent2 = new Intent(intent.getAction());
        ActivityInfo activityInfo = resolveActivity.activityInfo;
        return intent2.setClassName(activityInfo.packageName, activityInfo.name);
    }
}
