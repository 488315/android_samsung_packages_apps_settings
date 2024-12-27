package com.samsung.android.settings.analyzestorage.external.database.datasource;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.samsung.android.settings.analyzestorage.domain.log.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AbsAppDataSource {
    public static final String[] UNUSED_APP_SKIP_LIST = {
        "com.samsung.android.sm",
        "com.samsung.android.lool",
        "com.samsung.android.sm.provider",
        "com.samsung.android.sm.policy",
        "com.cleanmaster.sdk",
        "com.sec.android.security.LogGuard",
        "com.sec.android.security.NetworkGuard",
        "com.sec.android.gallery3d"
    };
    public String mTag = "AbsAppDataSource";

    public final List getTargetAppList(Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(9344);
        ArrayList arrayList = new ArrayList();
        for (ApplicationInfo applicationInfo : installedApplications) {
            if (applicationInfo != null
                    && isSuitableTargetApp(packageManager, applicationInfo)
                    && !Arrays.asList(UNUSED_APP_SKIP_LIST).contains(applicationInfo.packageName)) {
                if (TextUtils.isEmpty(
                        String.valueOf(packageManager.getApplicationLabel(applicationInfo)))) {
                    Log.d(
                            this.mTag,
                            "getTargetAppList() ] "
                                    + applicationInfo.packageName
                                    + " is not available.");
                } else {
                    arrayList.add(applicationInfo);
                }
            }
        }
        Log.d(this.mTag, "getTargetAppList() ] Filtered Target App Count : " + arrayList.size());
        return arrayList;
    }

    public boolean isSuitableTargetApp(
            PackageManager packageManager, ApplicationInfo applicationInfo) {
        return true;
    }
}
