package com.samsung.android.settings.languagepack.data;

import android.content.Context;
import android.content.pm.PackageManager;

import com.android.settings.Utils;

import com.samsung.android.settings.languagepack.logger.Log;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class PackageInfo {
    public final String mPkgName;
    public String mPreloadedVersionName;
    public final int mType;
    public long mLatestVersion = -1;
    public final List mVariant = new ArrayList();
    public boolean mIsPreloaded = false;
    public long mPreloadedVersionCode = -1;

    public PackageInfo(String str, int i) {
        this.mPkgName = str;
        this.mType = i;
    }

    public final boolean isPackageInstalled(Context context) {
        String str = this.mPkgName;
        boolean hasPackage = Utils.hasPackage(context, str);
        Log.d(
                "PackageInfo",
                "isPackageInstalled() : "
                        + str
                        + "=[installed:"
                        + hasPackage
                        + "][preloaded:"
                        + this.mIsPreloaded
                        + "]");
        return this.mIsPreloaded || hasPackage;
    }

    public final boolean isUnInstallablePackage(Context context) {
        String str = this.mPkgName;
        boolean z = false;
        try {
            int i = context.getPackageManager().getApplicationInfo(str, 0).flags;
            if ((i & 1) == 0 || (i & 128) != 0) {
                z = true;
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        Log.d("PackageInfo", "isUnInstallablePackage() : [" + str + "] result = " + z);
        return z;
    }

    public final boolean needToUpdate(Context context) {
        String str = this.mPkgName;
        long j = -1;
        if (context != null) {
            try {
                android.content.pm.PackageInfo packageInfo =
                        context.getPackageManager().getPackageInfo(str, 0);
                if (packageInfo != null) {
                    j =
                            this.mIsPreloaded
                                    ? this.mPreloadedVersionCode >= packageInfo.getLongVersionCode()
                                            ? this.mPreloadedVersionCode
                                            : packageInfo.getLongVersionCode()
                                    : packageInfo.getLongVersionCode();
                } else if (this.mIsPreloaded) {
                    j = this.mPreloadedVersionCode;
                }
            } catch (PackageManager.NameNotFoundException unused) {
                if (this.mIsPreloaded) {
                    j = this.mPreloadedVersionCode;
                }
            }
        }
        Log.d(
                "PackageInfo",
                "needToUpdate() : "
                        + str
                        + "=[currentVersion:"
                        + j
                        + "][latestVersion:"
                        + this.mLatestVersion
                        + "]");
        return !isPackageInstalled(context) || this.mLatestVersion > j;
    }
}
