package com.samsung.scpm.odm.dos.product;

import android.os.Bundle;
import android.os.ParcelFileDescriptor;

import com.samsung.scpm.odm.dos.common.ScpmDataSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ProductDataSet extends ScpmDataSet {
    public final ParcelFileDescriptor parcelFileDescriptor;

    public ProductDataSet(int i, int i2, String str, ParcelFileDescriptor parcelFileDescriptor) {
        super(i, i2, str);
        this.parcelFileDescriptor = parcelFileDescriptor;
    }

    public static ProductDataSet create(Bundle bundle, ParcelFileDescriptor parcelFileDescriptor) {
        ScpmDataSet create = ScpmDataSet.create(bundle);
        if (bundle != null) {
            bundle.getString("modelCode", null);
            bundle.getString("modelName", null);
            bundle.getString("modifiedDate", null);
            bundle.getString("division", null);
            bundle.getString("color", null);
            bundle.getString("marketingName", null);
            bundle.getString("keySpec", null);
        }
        return new ProductDataSet(create.result, create.rcode, create.rmsg, parcelFileDescriptor);
    }

    public static ProductDataSet create(Throwable th) {
        return new ProductDataSet(
                2,
                90000000,
                "There is an exception, please check  { " + th.getMessage() + "}",
                null);
    }
}
