package com.samsung.android.settings;

import android.content.Context;
import android.content.pm.PackageManager;

import com.samsung.android.util.SemLog;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class PkgUtils {
    public static final HashMap mHasPackageMap = new HashMap();
    public static final HashMap mPackageEnabledMap = new HashMap();

    public static boolean hasPackage(Context context, String str) {
        Boolean bool = (Boolean) mHasPackageMap.get(str);
        if (bool != null) {
            SemLog.i("PkgUtil", "return hasPackageFromCache : " + str + " , exists : " + bool);
            return bool.booleanValue();
        }
        SemLog.i("PkgUtil", "hasPackage not in cache : " + str);
        boolean z = false;
        if (context == null) {
            SemLog.e("PkgUtil", "hasPackage Context is null");
            return false;
        }
        try {
            context.getPackageManager().getApplicationInfo(str, 128);
            z = true;
        } catch (PackageManager.NameNotFoundException unused) {
            SemLog.d("PkgUtil", "Package not found : " + str);
        }
        SemLog.i("PkgUtil", "mHasPackageMap put : " + str + " , exists : hasPkg");
        mHasPackageMap.put(str, Boolean.valueOf(z));
        return z;
    }

    public static boolean isPackageEnabled(Context context, String str) {
        Boolean bool = (Boolean) mPackageEnabledMap.get(str);
        if (bool != null) {
            SemLog.i(
                    "PkgUtil",
                    "return isPackageEnabledFromCache : " + str + " , enabled : " + bool);
            return bool.booleanValue();
        }
        SemLog.i("PkgUtil", "isPackageEnabled not in cache : " + str);
        boolean z = false;
        if (context == null) {
            SemLog.e("PkgUtil", "isPackageEnabled Context is null");
            return false;
        }
        try {
            z = context.getPackageManager().getApplicationInfo(str, 128).enabled;
        } catch (PackageManager.NameNotFoundException unused) {
            SemLog.d("PkgUtil", "Package not found : " + str);
        }
        SemLog.i("PkgUtil", "mHasPackageMap put : " + str + " , exists : hasPkg");
        mPackageEnabledMap.put(str, Boolean.valueOf(z));
        return z;
    }
}
