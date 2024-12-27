package com.android.settings.applications;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.UserHandle;

import com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags;
import com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlagsImpl;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class InstalledAppCounter extends AppCounter {
    public final int mInstallReason;

    public InstalledAppCounter(Context context, int i, PackageManager packageManager) {
        this(context, i, packageManager, new FeatureFlagsImpl());
    }

    @Override // com.android.settings.applications.AppCounter
    public final boolean includeInCount(ApplicationInfo applicationInfo) {
        return includeInCount(this.mInstallReason, this.mPm, applicationInfo);
    }

    public InstalledAppCounter(
            Context context, int i, PackageManager packageManager, FeatureFlags featureFlags) {
        super(context, packageManager, featureFlags);
        this.mInstallReason = i;
    }

    public static boolean includeInCount(
            int i, PackageManager packageManager, ApplicationInfo applicationInfo) {
        int userId = UserHandle.getUserId(applicationInfo.uid);
        if (i != -1
                && packageManager.getInstallReason(
                                applicationInfo.packageName, new UserHandle(userId))
                        != i) {
            return false;
        }
        int i2 = applicationInfo.flags;
        if ((i2 & 128) != 0 || (i2 & 1) == 0) {
            return true;
        }
        List queryIntentActivitiesAsUser =
                packageManager.queryIntentActivitiesAsUser(
                        new Intent("android.intent.action.MAIN", (Uri) null)
                                .addCategory("android.intent.category.LAUNCHER")
                                .setPackage(applicationInfo.packageName),
                        786944,
                        userId);
        return (queryIntentActivitiesAsUser == null || queryIntentActivitiesAsUser.size() == 0)
                ? false
                : true;
    }
}
