package com.sec.ims.util;

import android.os.SemSystemProperties;
import android.text.TextUtils;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.HashSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class IMSLog {
    private static String SALES_CODE;
    private static final boolean SHIP_BUILD =
            "true".equals(SemSystemProperties.get("ro.product_ship", "false"));
    private static HashSet<String> mShowSLogInShipBuildSet;

    static {
        String str = SemSystemProperties.get("persist.omc.sales_code", ApnSettings.MVNO_NONE);
        SALES_CODE = str;
        if (TextUtils.isEmpty(str)) {
            SALES_CODE = SemSystemProperties.get("ro.csc.sales_code", ApnSettings.MVNO_NONE);
        }
        HashSet<String> hashSet = new HashSet<>();
        mShowSLogInShipBuildSet = hashSet;
        hashSet.add("ATX");
        mShowSLogInShipBuildSet.add("OMX");
        mShowSLogInShipBuildSet.add("VDR");
        mShowSLogInShipBuildSet.add("VDP");
        mShowSLogInShipBuildSet.add("VOP");
    }

    public static String checker(Object obj) {
        if (obj == null) {
            return null;
        }
        if (isShipBuild()) {
            return "xxxxx";
        }
        return ApnSettings.MVNO_NONE + obj;
    }

    public static boolean isShipBuild() {
        return SHIP_BUILD && !mShowSLogInShipBuildSet.contains(SALES_CODE);
    }
}
