package com.google.android.gms.internal.safetynet;

import android.os.Parcel;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation$ApiMethodImpl;
import com.google.android.gms.common.api.internal.zabv;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.safetynet.SafetyNet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zzn extends BaseImplementation$ApiMethodImpl {
    public final zzu zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzn(zabv zabvVar) {
        super(zabvVar);
        Api api = SafetyNet.API;
        Preconditions.checkNotNull(zabvVar, "GoogleApiClient must not be null");
        Preconditions.checkNotNull(api, "Api must not be null");
        this.zza = new zzu(this);
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    public final /* bridge */ /* synthetic */ zzaa createFailedResult(Status status) {
        return new zzaa(status, null);
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation$ApiMethodImpl
    public final void doExecute(Api.Client client) {
        zzh zzhVar = (zzh) ((zzaf) client).getService();
        Parcel obtain = Parcel.obtain();
        obtain.writeInterfaceToken("com.google.android.gms.safetynet.internal.ISafetyNetService");
        int i = zzc.$r8$clinit;
        zzu zzuVar = this.zza;
        if (zzuVar == null) {
            obtain.writeStrongBinder(null);
        } else {
            obtain.writeStrongBinder(zzuVar);
        }
        zzhVar.zzb(5, obtain);
    }
}
