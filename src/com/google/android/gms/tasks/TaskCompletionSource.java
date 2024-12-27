package com.google.android.gms.tasks;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Response;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class TaskCompletionSource {
    public final zzw zza = new zzw();

    public final void setException(ApiException apiException) {
        zzw zzwVar = this.zza;
        zzwVar.getClass();
        synchronized (zzwVar.zza) {
            zzwVar.zzh();
            zzwVar.zzc = true;
            zzwVar.zzf = apiException;
        }
        zzwVar.zzb.zzb(zzwVar);
    }

    public final void setResult(Response response) {
        zzw zzwVar = this.zza;
        synchronized (zzwVar.zza) {
            zzwVar.zzh();
            zzwVar.zzc = true;
            zzwVar.zze = response;
        }
        zzwVar.zzb.zzb(zzwVar);
    }

    public final void trySetException(Exception exc) {
        zzw zzwVar = this.zza;
        zzwVar.getClass();
        Preconditions.checkNotNull(exc, "Exception must not be null");
        synchronized (zzwVar.zza) {
            try {
                if (zzwVar.zzc) {
                    return;
                }
                zzwVar.zzc = true;
                zzwVar.zzf = exc;
                zzwVar.zzb.zzb(zzwVar);
            } finally {
            }
        }
    }
}
