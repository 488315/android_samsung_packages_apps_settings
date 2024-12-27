package com.samsung.android.scloud.oem.lib.qbnr;

import android.content.Context;
import android.os.ParcelFileDescriptor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public interface ISCloudQBNRClient {
    void backup(
            Context context,
            ParcelFileDescriptor parcelFileDescriptor,
            QBNRClientHelper.AnonymousClass2.AnonymousClass1.C00531 c00531);

    String getDescription(Context context);

    String getLabel(Context context);

    void restore(
            Context context,
            ParcelFileDescriptor parcelFileDescriptor,
            QBNRClientHelper.AnonymousClass2.AnonymousClass1.C00531 c00531);
}
