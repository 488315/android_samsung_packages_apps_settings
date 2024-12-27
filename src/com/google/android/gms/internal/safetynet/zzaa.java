package com.google.android.gms.internal.safetynet;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zzaa implements Result {
    public final Status zza;
    public final com.google.android.gms.safetynet.zzd zzb;

    public zzaa(Status status, com.google.android.gms.safetynet.zzd zzdVar) {
        this.zza = status;
        this.zzb = zzdVar;
    }

    @Override // com.google.android.gms.common.api.Result
    public final Status getStatus() {
        return this.zza;
    }
}
