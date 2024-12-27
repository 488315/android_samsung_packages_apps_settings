package com.samsung.android.sdk.mobileservice.util;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SdkLog {
    public static final /* synthetic */ SdkLog[] $VALUES = {new SdkLog("INSTANCE", 0)};
    public static final boolean ENG = "eng".equals(Build.TYPE);

    /* JADX INFO: Fake field, exist only in values array */
    SdkLog EF5;

    public static void d(String str, String str2) {
        int i;
        ArrayList arrayList = new ArrayList();
        if (!TextUtils.isEmpty(str2)) {
            int length = str2.length();
            if (length > 512) {
                int i2 = 0;
                while (true) {
                    int i3 = i2 + 1;
                    i = 512 * i3;
                    arrayList.add(str2.substring(512 * i2, i));
                    if (length <= (i2 + 2) * 512) {
                        break;
                    } else {
                        i2 = i3;
                    }
                }
                arrayList.add(str2.substring(i, length));
            } else {
                arrayList.add(str2);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Log.d("SEMS-13.0.07_".concat(str), (String) it.next());
        }
    }

    public static SdkLog valueOf(String str) {
        return (SdkLog) Enum.valueOf(SdkLog.class, str);
    }

    public static SdkLog[] values() {
        return (SdkLog[]) $VALUES.clone();
    }
}
