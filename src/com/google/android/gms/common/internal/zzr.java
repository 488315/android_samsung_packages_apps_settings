package com.google.android.gms.common.internal;

import android.content.Context;
import android.os.Looper;

import com.google.android.gms.common.stats.ConnectionTracker;
import com.google.android.gms.internal.common.zzi;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zzr extends GmsClientSupervisor {
    public final HashMap zzb = new HashMap();
    public final Context zzc;
    public volatile zzi zzd;
    public final ConnectionTracker zzf;
    public final long zzg;
    public final long zzh;

    public zzr(Context context, Looper looper) {
        zzq zzqVar = new zzq(this);
        this.zzc = context.getApplicationContext();
        this.zzd = new zzi(looper, zzqVar);
        if (ConnectionTracker.zzc == null) {
            synchronized (ConnectionTracker.zzb) {
                try {
                    if (ConnectionTracker.zzc == null) {
                        ConnectionTracker connectionTracker = new ConnectionTracker();
                        connectionTracker.zza = new ConcurrentHashMap<>();
                        ConnectionTracker.zzc = connectionTracker;
                    }
                } finally {
                }
            }
        }
        ConnectionTracker connectionTracker2 = ConnectionTracker.zzc;
        Preconditions.checkNotNull(connectionTracker2);
        this.zzf = connectionTracker2;
        this.zzg = 5000L;
        this.zzh = 300000L;
    }

    @Override // com.google.android.gms.common.internal.GmsClientSupervisor
    public final boolean zzc(zzn zznVar, zze zzeVar, String str, Executor executor) {
        boolean z;
        synchronized (this.zzb) {
            try {
                zzo zzoVar = (zzo) this.zzb.get(zznVar);
                if (zzoVar == null) {
                    zzoVar = new zzo(this, zznVar);
                    ((HashMap) zzoVar.zzb).put(zzeVar, zzeVar);
                    zzoVar.zze(str, executor);
                    this.zzb.put(zznVar, zzoVar);
                } else {
                    this.zzd.removeMessages(0, zznVar);
                    if (((HashMap) zzoVar.zzb).containsKey(zzeVar)) {
                        String zznVar2 = zznVar.toString();
                        StringBuilder sb = new StringBuilder(zznVar2.length() + 81);
                        sb.append(
                                "Trying to bind a GmsServiceConnection that was already connected"
                                    + " before.  config=");
                        sb.append(zznVar2);
                        throw new IllegalStateException(sb.toString());
                    }
                    ((HashMap) zzoVar.zzb).put(zzeVar, zzeVar);
                    int i = zzoVar.zzc;
                    if (i == 1) {
                        zzeVar.onServiceConnected(zzoVar.zzg, zzoVar.zze);
                    } else if (i == 2) {
                        zzoVar.zze(str, executor);
                    }
                }
                z = zzoVar.zzd;
            } catch (Throwable th) {
                throw th;
            }
        }
        return z;
    }
}
