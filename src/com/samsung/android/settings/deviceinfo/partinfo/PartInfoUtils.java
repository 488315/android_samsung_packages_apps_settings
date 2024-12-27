package com.samsung.android.settings.deviceinfo.partinfo;

import android.os.Build;
import android.text.TextUtils;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class PartInfoUtils {
    public static boolean validCheckReplacementFileName(String str) {
        ArrayList<String> arrayList =
                new ArrayList<
                        String>() { // from class:
                                    // com.samsung.android.settings.deviceinfo.partinfo.PartInfoUtils.1
                    {
                        add("e1s");
                        add("e1q");
                        add("SM-K751");
                        add("SC-51E");
                        add("SCG25");
                        add("e2s");
                        add("e2q");
                        add("e3q");
                        add("SM-K758");
                        add("SC-52E");
                        add("SCG26");
                    }
                };
        String str2 = Build.DEVICE;
        return (TextUtils.isEmpty(str2) || !arrayList.contains(str2))
                ? str.startsWith("SVC_")
                : str.startsWith("SVC_") && !str.endsWith("_battery");
    }
}
