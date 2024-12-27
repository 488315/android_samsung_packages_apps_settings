package com.google.android.gms.internal.safetynet;

import com.google.android.gms.common.api.Status;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zzu extends zzd {
    public final /* synthetic */ zzn zza;

    public zzu(zzn zznVar) {
        this.zza = zznVar;
    }

    @Override // com.google.android.gms.internal.safetynet.zzd,
              // com.google.android.gms.internal.safetynet.zzg
    public final void zzg(Status status, com.google.android.gms.safetynet.zzd zzdVar) {
        this.zza.setResult(new zzaa(status, zzdVar));
    }
}
