package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.Feature;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zacv {
    public final /* synthetic */ TaskApiCall$Builder zaa;
    public final Feature[] zaa$1;
    public final boolean zab;
    public final int zac;

    public zacv(TaskApiCall$Builder taskApiCall$Builder, Feature[] featureArr, boolean z, int i) {
        this.zaa = taskApiCall$Builder;
        this.zaa$1 = featureArr;
        boolean z2 = false;
        if (featureArr != null && z) {
            z2 = true;
        }
        this.zab = z2;
        this.zac = i;
    }
}
