package com.google.android.gms.common.internal;

import com.google.android.gms.common.ConnectionResult;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zzg extends zza {
    public final /* synthetic */ BaseGmsClient zze;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzg(BaseGmsClient baseGmsClient, int i) {
        super(baseGmsClient, i, null);
        this.zze = baseGmsClient;
    }

    @Override // com.google.android.gms.common.internal.zza
    public final void zzb(ConnectionResult connectionResult) {
        BaseGmsClient baseGmsClient = this.zze;
        baseGmsClient.getClass();
        baseGmsClient.zzc.onReportServiceBinding(connectionResult);
        System.currentTimeMillis();
    }

    @Override // com.google.android.gms.common.internal.zza
    public final boolean zzd() {
        this.zze.zzc.onReportServiceBinding(ConnectionResult.RESULT_SUCCESS);
        return true;
    }
}
