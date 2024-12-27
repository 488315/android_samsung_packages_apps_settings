package com.google.android.gms.safetynet;

import android.os.Parcel;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.internal.safetynet.zzaf;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class zzm implements RemoteCall {
    public final /* synthetic */ SafetyNetClient zza;

    public /* synthetic */ zzm(SafetyNetClient safetyNetClient) {}

    @Override // com.google.android.gms.common.api.internal.RemoteCall
    public final void accept(Object obj, Object obj2) {
        zzo zzoVar = new zzo((TaskCompletionSource) obj2);
        com.google.android.gms.internal.safetynet.zzh zzhVar =
                (com.google.android.gms.internal.safetynet.zzh) ((zzaf) obj).getService();
        Parcel obtain = Parcel.obtain();
        obtain.writeInterfaceToken("com.google.android.gms.safetynet.internal.ISafetyNetService");
        int i = com.google.android.gms.internal.safetynet.zzc.$r8$clinit;
        obtain.writeStrongBinder(zzoVar);
        zzhVar.zzb(14, obtain);
    }
}
