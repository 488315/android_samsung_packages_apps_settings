package com.google.android.gms.tasks;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zzi implements Runnable {
    public final /* synthetic */ Task zza;
    public final /* synthetic */ zzj zzb;

    public zzi(zzj zzjVar, Task task) {
        this.zzb = zzjVar;
        this.zza = task;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzb.zzb) {
            try {
                OnCompleteListener onCompleteListener = this.zzb.zzc;
                if (onCompleteListener != null) {
                    onCompleteListener.onComplete(this.zza);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
