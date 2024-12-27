package com.samsung.android.scloud.lib.platform.api;

import android.os.Bundle;
import android.os.ParcelFileDescriptor;

import com.samsung.android.scloud.lib.platform.data.ConfigurationDataSet;
import com.samsung.android.scloud.lib.platform.data.ScpmDataSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class DataSetFactory {
    public static ConfigurationDataSet newConfigurationDataSet(
            Bundle bundle, ParcelFileDescriptor parcelFileDescriptor) {
        ScpmDataSet newScpmDataSet = newScpmDataSet(bundle);
        if (bundle != null) {
            bundle.getString("filterId", null);
        }
        return new ConfigurationDataSet(
                newScpmDataSet.result,
                newScpmDataSet.rcode,
                newScpmDataSet.rmsg,
                newScpmDataSet.token,
                parcelFileDescriptor);
    }

    public static ScpmDataSet newScpmDataSet(Bundle bundle) {
        String str;
        int i = 2;
        String str2 = null;
        int i2 = 90000000;
        if (bundle != null) {
            i = bundle.getInt("result", 2);
            String string = bundle.getString("token", null);
            i2 = bundle.getInt("rcode", 90000000);
            str = bundle.getString("rmsg", null);
            str2 = string;
        } else {
            str = "The returned value from SCPM is not correct(null or empty).";
        }
        return new ScpmDataSet(i, i2, str, str2);
    }

    public static ConfigurationDataSet newConfigurationDataSet(Throwable th) {
        return new ConfigurationDataSet(
                2,
                90000000,
                "There is an exception, please check  { " + th.getMessage() + "}",
                null,
                null);
    }
}
