package com.google.android.gms.common.api.internal;

import androidx.collection.ArraySet;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zaae extends zap {
    public final ArraySet zad;
    public final GoogleApiManager zae;

    public zaae(
            LifecycleFragment lifecycleFragment,
            GoogleApiManager googleApiManager,
            GoogleApiAvailability googleApiAvailability) {
        super(lifecycleFragment, googleApiAvailability);
        new ArraySet(0);
        this.zae = googleApiManager;
        this.mLifecycleFragment.addCallback(this);
    }

    @Override // com.google.android.gms.common.api.internal.zap
    public final void zab(ConnectionResult connectionResult, int i) {
        this.zae.zaz(connectionResult, i);
    }

    @Override // com.google.android.gms.common.api.internal.zap
    public final void zac() {
        com.google.android.gms.internal.base.zaq zaqVar = this.zae.zat;
        zaqVar.sendMessage(zaqVar.obtainMessage(3));
    }
}
