package com.samsung.android.settings.smartpopupview;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.UserHandle;
import android.provider.Settings;

import com.samsung.android.app.SemMultiWindowManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SmartPopupViewUtil {
    public static List filterMultiWindowSupportPackages(Context context, List list) {
        ArrayList arrayList = new ArrayList();
        PackageManager packageManager = context.getPackageManager();
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            Intent addCategory =
                    new Intent("android.intent.action.MAIN")
                            .addCategory("android.intent.category.LAUNCHER");
            try {
                addCategory.setPackage(str);
                Iterator<ResolveInfo> it2 =
                        packageManager.queryIntentActivities(addCategory, 0).iterator();
                while (true) {
                    if (it2.hasNext()) {
                        if ((new SemMultiWindowManager().getSupportedModes(it2.next()) & 2) != 0) {
                            arrayList.add(str);
                            break;
                        }
                    }
                }
            } catch (IllegalArgumentException unused) {
            }
        }
        return arrayList;
    }

    public static List getPackageStrListFromDB(Context context) {
        String stringForUser =
                Settings.Secure.getStringForUser(
                        context.getContentResolver(),
                        "floating_noti_package_list",
                        UserHandle.myUserId());
        return stringForUser == null
                ? new ArrayList()
                : new ArrayList(Arrays.asList(stringForUser.split(":")));
    }
}
