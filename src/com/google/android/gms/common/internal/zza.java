package com.google.android.gms.common.internal;

import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class zza {
    public final int zza;
    public Object zza$1;
    public final Bundle zzb;
    public boolean zzb$1;
    public final /* synthetic */ BaseGmsClient zzc;
    public final /* synthetic */ BaseGmsClient zzd;

    public zza(BaseGmsClient baseGmsClient, int i, Bundle bundle) {
        this.zzc = baseGmsClient;
        Boolean bool = Boolean.TRUE;
        this.zzd = baseGmsClient;
        this.zza$1 = bool;
        this.zzb$1 = false;
        this.zza = i;
        this.zzb = bundle;
    }

    public abstract void zzb(ConnectionResult connectionResult);

    public abstract boolean zzd();

    public final void zzg() {
        synchronized (this) {
            this.zza$1 = null;
        }
        synchronized (this.zzd.zzt) {
            this.zzd.zzt.remove(this);
        }
    }
}
