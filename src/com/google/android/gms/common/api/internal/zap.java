package com.google.android.gms.common.api.internal;

import android.content.DialogInterface;
import android.os.Looper;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class zap extends LifecycleCallback implements DialogInterface.OnCancelListener {
    public volatile boolean zaa;
    public final AtomicReference zab;
    public final GoogleApiAvailability zac;
    public final com.google.android.gms.internal.base.zaq zad;

    public zap(LifecycleFragment lifecycleFragment, GoogleApiAvailability googleApiAvailability) {
        super(lifecycleFragment);
        this.zab = new AtomicReference(null);
        this.zad = new com.google.android.gms.internal.base.zaq(Looper.getMainLooper());
        this.zac = googleApiAvailability;
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        ConnectionResult connectionResult = new ConnectionResult(13, null);
        zam zamVar = (zam) this.zab.get();
        int i = zamVar == null ? -1 : zamVar.zaa;
        this.zab.set(null);
        zab(connectionResult, i);
    }

    public abstract void zab(ConnectionResult connectionResult, int i);

    public abstract void zac();
}
