package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.util.Log;

import com.google.android.gms.internal.common.zzc;
import com.google.android.gms.internal.safetynet.zzaf;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zzd extends com.google.android.gms.internal.common.zzb implements IInterface {
    public BaseGmsClient zza;
    public final int zzb;

    public zzd(BaseGmsClient baseGmsClient, int i) {
        super("com.google.android.gms.common.internal.IGmsCallbacks");
        this.zza = baseGmsClient;
        this.zzb = i;
    }

    @Override // com.google.android.gms.internal.common.zzb
    public final boolean zza(int i, Parcel parcel, Parcel parcel2) {
        if (i == 1) {
            int readInt = parcel.readInt();
            IBinder readStrongBinder = parcel.readStrongBinder();
            Bundle bundle = (Bundle) zzc.zza(parcel, Bundle.CREATOR);
            Preconditions.checkNotNull(
                    this.zza,
                    "onPostInitComplete can be called only once per call to getRemoteService");
            BaseGmsClient baseGmsClient = this.zza;
            int i2 = this.zzb;
            baseGmsClient.getClass();
            zzf zzfVar = new zzf(baseGmsClient, readInt, readStrongBinder, bundle);
            Handler handler = baseGmsClient.zzb;
            handler.sendMessage(handler.obtainMessage(1, i2, -1, zzfVar));
            this.zza = null;
        } else if (i == 2) {
            parcel.readInt();
            Log.wtf(
                    "GmsClient",
                    "received deprecated onAccountValidationComplete callback, ignoring",
                    new Exception());
        } else {
            if (i != 3) {
                return false;
            }
            int readInt2 = parcel.readInt();
            IBinder readStrongBinder2 = parcel.readStrongBinder();
            zzj zzjVar = (zzj) zzc.zza(parcel, zzj.CREATOR);
            BaseGmsClient baseGmsClient2 = this.zza;
            Preconditions.checkNotNull(
                    baseGmsClient2,
                    "onPostInitCompleteWithConnectionInfo can be called only once per call"
                        + " togetRemoteService");
            Preconditions.checkNotNull(zzjVar);
            baseGmsClient2.zzD = zzjVar;
            if (baseGmsClient2 instanceof zzaf) {
                ConnectionTelemetryConfiguration connectionTelemetryConfiguration = zzjVar.zzd;
                RootTelemetryConfigManager.getInstance()
                        .zza(
                                connectionTelemetryConfiguration == null
                                        ? null
                                        : connectionTelemetryConfiguration.zza);
            }
            Bundle bundle2 = zzjVar.zza;
            Preconditions.checkNotNull(
                    this.zza,
                    "onPostInitComplete can be called only once per call to getRemoteService");
            BaseGmsClient baseGmsClient3 = this.zza;
            int i3 = this.zzb;
            baseGmsClient3.getClass();
            zzf zzfVar2 = new zzf(baseGmsClient3, readInt2, readStrongBinder2, bundle2);
            Handler handler2 = baseGmsClient3.zzb;
            handler2.sendMessage(handler2.obtainMessage(1, i3, -1, zzfVar2));
            this.zza = null;
        }
        parcel2.writeNoException();
        return true;
    }
}
