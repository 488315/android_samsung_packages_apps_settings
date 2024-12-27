package com.samsung.android.settings.deviceinfo.aboutphone.deviceimage;

import android.content.Context;

import com.samsung.android.settings.deviceinfo.aboutphone.SecMyDeviceInfoFragment$$ExternalSyntheticLambda0;
import com.samsung.scpm.odm.dos.product.ScpmProduct;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DeviceImageManager {
    public final Context mContext;
    public AnonymousClass2 mDeviceImageReceiver;
    public SecMyDeviceInfoFragment$$ExternalSyntheticLambda0 mListener;
    public final ScpmProduct mScpmProduct;

    public DeviceImageManager(Context context) {
        this.mContext = context;
        this.mScpmProduct = new ScpmProduct(context);
    }
}
