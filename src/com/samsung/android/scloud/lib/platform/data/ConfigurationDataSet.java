package com.samsung.android.scloud.lib.platform.data;

import android.os.ParcelFileDescriptor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ConfigurationDataSet extends ScpmDataSet {
    public final ParcelFileDescriptor parcelFileDescriptor;

    public ConfigurationDataSet(
            int i, int i2, String str, String str2, ParcelFileDescriptor parcelFileDescriptor) {
        super(i, i2, str, str2);
        this.parcelFileDescriptor = parcelFileDescriptor;
    }
}
