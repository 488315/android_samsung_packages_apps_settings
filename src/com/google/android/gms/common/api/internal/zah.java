package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;

import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.zzw;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zah extends zac {
    public final TaskCompletionSource zaa;

    public zah(TaskCompletionSource taskCompletionSource) {
        super(4);
        this.zaa = taskCompletionSource;
    }

    @Override // com.google.android.gms.common.api.internal.zac
    public final boolean zaa(zabq zabqVar) {
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(((HashMap) zabqVar.zag).get(null));
        return false;
    }

    @Override // com.google.android.gms.common.api.internal.zac
    public final Feature[] zab(zabq zabqVar) {
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(((HashMap) zabqVar.zag).get(null));
        return null;
    }

    public final void zac(zabq zabqVar) {
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                ((HashMap) zabqVar.zag).remove(null));
        TaskCompletionSource taskCompletionSource = this.zaa;
        Boolean bool = Boolean.FALSE;
        zzw zzwVar = taskCompletionSource.zza;
        synchronized (zzwVar.zza) {
            try {
                if (zzwVar.zzc) {
                    return;
                }
                zzwVar.zzc = true;
                zzwVar.zze = bool;
                zzwVar.zzb.zzb(zzwVar);
            } finally {
            }
        }
    }

    @Override // com.google.android.gms.common.api.internal.zai
    public final void zad(Status status) {
        this.zaa.trySetException(new ApiException(status));
    }

    @Override // com.google.android.gms.common.api.internal.zai
    public final void zae(Exception exc) {
        this.zaa.trySetException(exc);
    }

    @Override // com.google.android.gms.common.api.internal.zai
    public final void zaf(zabq zabqVar) {
        try {
            zac(zabqVar);
        } catch (DeadObjectException e) {
            zad(zai.zah(e));
            throw e;
        } catch (RemoteException e2) {
            zad(zai.zah(e2));
        } catch (RuntimeException e3) {
            this.zaa.trySetException(e3);
        }
    }

    @Override // com.google.android.gms.common.api.internal.zai
    public final /* bridge */ /* synthetic */ void zag(zaad zaadVar, boolean z) {}
}
