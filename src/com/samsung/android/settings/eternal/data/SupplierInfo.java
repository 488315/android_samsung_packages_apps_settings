package com.samsung.android.settings.eternal.data;

import android.net.Uri;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SupplierInfo {
    public final String mDTDVersion;
    public final String mPackageName;
    public final String mUnSupportedSolution;
    public final Uri mUri;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder {
        public String mDTDVersion;
        public String mPackageName;
        public String mUnSupportedSolution;
        public Uri mUri;
    }

    public SupplierInfo(Builder builder) {
        this.mUri = builder.mUri;
        this.mPackageName = builder.mPackageName;
        this.mDTDVersion = builder.mDTDVersion;
        this.mUnSupportedSolution = builder.mUnSupportedSolution;
    }
}
