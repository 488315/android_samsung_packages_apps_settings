package com.google.android.gms.common.internal;

import android.content.Context;
import android.util.SparseIntArray;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zal {
    public final SparseIntArray zaa;
    public final GoogleApiAvailabilityLight zab;

    public zal() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.zab;
        this.zaa = new SparseIntArray();
        this.zab = googleApiAvailability;
    }

    public final int zab(Context context, Api.Client client) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(client);
        int minApkVersion = client.getMinApkVersion();
        int i = this.zaa.get(minApkVersion, -1);
        if (i == -1) {
            i = 0;
            int i2 = 0;
            while (true) {
                if (i2 >= this.zaa.size()) {
                    i = -1;
                    break;
                }
                int keyAt = this.zaa.keyAt(i2);
                if (keyAt > minApkVersion && this.zaa.get(keyAt) == 0) {
                    break;
                }
                i2++;
            }
            if (i == -1) {
                i = this.zab.isGooglePlayServicesAvailable(context, minApkVersion);
            }
            this.zaa.put(minApkVersion, i);
        }
        return i;
    }
}
