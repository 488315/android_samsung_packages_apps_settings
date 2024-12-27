package com.samsung.scpm.odm.dos.common;

import android.os.Bundle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class ScpmDataSet {
    public final int rcode;
    public final int result;
    public final String rmsg;

    public ScpmDataSet(int i, int i2, String str) {
        this.result = i;
        this.rcode = i2;
        this.rmsg = str;
    }

    public static ScpmDataSet create(Bundle bundle) {
        String str;
        int i = 2;
        int i2 = 90000000;
        if (bundle != null) {
            i = bundle.getInt("result", 2);
            i2 = bundle.getInt("rcode", 90000000);
            str = bundle.getString("rmsg", null);
        } else {
            str = "The returned value from SCPM is not correct(null or empty).";
        }
        return new ScpmDataSet(i, i2, str);
    }
}
