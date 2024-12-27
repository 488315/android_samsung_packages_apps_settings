package com.google.android.gms.tasks;

import java.util.ArrayDeque;
import java.util.Queue;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zzr {
    public final Object zza = new Object();
    public Queue zzb;
    public boolean zzc;

    public final void zza(zzj zzjVar) {
        synchronized (this.zza) {
            try {
                if (this.zzb == null) {
                    this.zzb = new ArrayDeque();
                }
                ((ArrayDeque) this.zzb).add(zzjVar);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void zzb(Task task) {
        zzj zzjVar;
        synchronized (this.zza) {
            if (this.zzb != null && !this.zzc) {
                this.zzc = true;
                while (true) {
                    synchronized (this.zza) {
                        try {
                            zzjVar = (zzj) ((ArrayDeque) this.zzb).poll();
                            if (zzjVar == null) {
                                this.zzc = false;
                                return;
                            }
                        } finally {
                        }
                    }
                    synchronized (zzjVar.zzb) {
                        try {
                            if (zzjVar.zzc != null) {
                                zzjVar.zza.execute(new zzi(zzjVar, task));
                            }
                        } finally {
                        }
                    }
                }
            }
        }
    }
}
