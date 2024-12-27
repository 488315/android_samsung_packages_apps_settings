package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;

import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.gms.tasks.zzj;
import com.google.android.gms.tasks.zzw;

import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zag extends zac {
    public final zacv zaa;
    public final TaskCompletionSource zab;
    public final ApiExceptionMapper zad;

    public zag(
            int i,
            zacv zacvVar,
            TaskCompletionSource taskCompletionSource,
            ApiExceptionMapper apiExceptionMapper) {
        super(i);
        this.zab = taskCompletionSource;
        this.zaa = zacvVar;
        this.zad = apiExceptionMapper;
        if (i == 2 && zacvVar.zab) {
            throw new IllegalArgumentException(
                    "Best-effort write calls cannot pass methods that should auto-resolve missing"
                        + " features.");
        }
    }

    @Override // com.google.android.gms.common.api.internal.zac
    public final boolean zaa(zabq zabqVar) {
        return this.zaa.zab;
    }

    @Override // com.google.android.gms.common.api.internal.zac
    public final Feature[] zab(zabq zabqVar) {
        return this.zaa.zaa$1;
    }

    @Override // com.google.android.gms.common.api.internal.zai
    public final void zad(Status status) {
        this.zad.getClass();
        this.zab.trySetException(
                status.hasResolution()
                        ? new ResolvableApiException(status)
                        : new ApiException(status));
    }

    @Override // com.google.android.gms.common.api.internal.zai
    public final void zae(Exception exc) {
        this.zab.trySetException(exc);
    }

    @Override // com.google.android.gms.common.api.internal.zai
    public final void zaf(zabq zabqVar) {
        TaskCompletionSource taskCompletionSource = this.zab;
        try {
            zacv zacvVar = this.zaa;
            zacvVar.zaa.zaa.accept(zabqVar.zac, taskCompletionSource);
        } catch (DeadObjectException e) {
            throw e;
        } catch (RemoteException e2) {
            zad(zai.zah(e2));
        } catch (RuntimeException e3) {
            taskCompletionSource.trySetException(e3);
        }
    }

    @Override // com.google.android.gms.common.api.internal.zai
    public final void zag(zaad zaadVar, boolean z) {
        Map map = zaadVar.zab;
        Boolean valueOf = Boolean.valueOf(z);
        TaskCompletionSource taskCompletionSource = this.zab;
        map.put(taskCompletionSource, valueOf);
        zzw zzwVar = taskCompletionSource.zza;
        zaac zaacVar = new zaac(zaadVar, taskCompletionSource);
        zzwVar.getClass();
        zzwVar.zzb.zza(new zzj(TaskExecutors.MAIN_THREAD, zaacVar));
        synchronized (zzwVar.zza) {
            try {
                if (zzwVar.zzc) {
                    zzwVar.zzb.zzb(zzwVar);
                }
            } finally {
            }
        }
    }
}
