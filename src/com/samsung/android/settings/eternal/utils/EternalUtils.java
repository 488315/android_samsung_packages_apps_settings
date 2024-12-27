package com.samsung.android.settings.eternal.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class EternalUtils {
    public static String buildLogMessage(String str) {
        return new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(new Date())
                + " SSM[SelfBnRTest]BnRDocumentStorageAccessHelper "
                + str
                + "\n";
    }

    public static ArrayList getLaunchablePackageList(Context context) {
        ArrayList arrayList = new ArrayList();
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        intent.addCategory("android.intent.category.LAUNCHER");
        Iterator it =
                context.getPackageManager()
                        .semQueryIntentActivitiesAsUser(intent, 786560, 0)
                        .iterator();
        while (it.hasNext()) {
            arrayList.add(((ResolveInfo) it.next()).activityInfo.packageName);
        }
        return arrayList;
    }

    public static String getUid(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] split = str.split("/");
        if (split.length < 2) {
            return null;
        }
        return split[1];
    }

    public static boolean isSemAvailable(Context context) {
        PackageManager packageManager = context.getPackageManager();
        return packageManager != null
                && packageManager.hasSystemFeature("com.samsung.feature.samsung_experience_mobile");
    }
}
