package com.samsung.android.gtscell.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u0000<\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0000\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\r\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\t\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J"
                + " \u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010"
                + "\t\u001a\u00020\n"
                + "J$\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010"
                + "\t\u001a\u00020\n"
                + "J\u0016\u0010\r"
                + "\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0016\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\"\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u00102\b\b\u0002\u0010"
                + "\t\u001a\u00020\n"
                + "¨\u0006\u0013"
        },
        d2 = {
            "Lcom/samsung/android/gtscell/utils/PackageManagerUtils;",
            ApnSettings.MVNO_NONE,
            "()V",
            "getApplicationInfo",
            "Landroid/content/pm/ApplicationInfo;",
            "context",
            "Landroid/content/Context;",
            NetworkAnalyticsConstants.DBConstants.PACKAGE_NAME,
            ApnSettings.MVNO_NONE,
            "flags",
            ApnSettings.MVNO_NONE,
            "getPackageInfo",
            "Landroid/content/pm/PackageInfo;",
            "getVersionCode",
            ApnSettings.MVNO_NONE,
            "getVersionName",
            ApnSettings.MVNO_NONE,
            "hasPackage",
            ApnSettings.MVNO_NONE,
            "gtscell_release"
        },
        k = 1,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public final class PackageManagerUtils {
    public static final PackageManagerUtils INSTANCE = new PackageManagerUtils();

    private PackageManagerUtils() {}

    public static /* synthetic */ PackageInfo getPackageInfo$default(
            PackageManagerUtils packageManagerUtils,
            Context context,
            CharSequence charSequence,
            int i,
            int i2,
            Object obj) {
        if ((i2 & 4) != 0) {
            i = 0;
        }
        return packageManagerUtils.getPackageInfo(context, charSequence, i);
    }

    public static /* synthetic */ boolean hasPackage$default(
            PackageManagerUtils packageManagerUtils,
            Context context,
            String str,
            int i,
            int i2,
            Object obj) {
        if ((i2 & 4) != 0) {
            i = 0;
        }
        return packageManagerUtils.hasPackage(context, str, i);
    }

    public final ApplicationInfo getApplicationInfo(
            Context context, CharSequence pkgName, int flags) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(pkgName, "pkgName");
        try {
            return context.getPackageManager().getApplicationInfo(pkgName.toString(), flags);
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    public final PackageInfo getPackageInfo(Context context, CharSequence pkgName, int flags) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        if (pkgName == null) {
            return null;
        }
        try {
            return context.getPackageManager().getPackageInfo(pkgName.toString(), flags);
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    public final long getVersionCode(Context context, CharSequence pkgName) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(pkgName, "pkgName");
        CharSequence charSequence = pkgName.length() > 0 ? pkgName : null;
        if (charSequence != null) {
            PackageInfo packageInfo$default =
                    getPackageInfo$default(INSTANCE, context, charSequence, 0, 4, null);
            Long valueOf =
                    packageInfo$default != null
                            ? Long.valueOf(packageInfo$default.getLongVersionCode())
                            : null;
            if (valueOf != null) {
                return valueOf.longValue();
            }
        }
        return -1L;
    }

    public final String getVersionName(Context context, CharSequence pkgName) {
        String str;
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(pkgName, "pkgName");
        PackageInfo packageInfo$default =
                getPackageInfo$default(this, context, pkgName, 0, 4, null);
        return (packageInfo$default == null || (str = packageInfo$default.versionName) == null)
                ? ApnSettings.MVNO_NONE
                : str;
    }

    public final boolean hasPackage(Context context, String pkgName, int flags) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        return getPackageInfo(context, pkgName, flags) != null;
    }
}
