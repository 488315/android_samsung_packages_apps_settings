package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zzf extends zza {
    public final IBinder zze;
    public final /* synthetic */ BaseGmsClient zzf;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzf(BaseGmsClient baseGmsClient, int i, IBinder iBinder, Bundle bundle) {
        super(baseGmsClient, i, bundle);
        this.zzf = baseGmsClient;
        this.zze = iBinder;
    }

    @Override // com.google.android.gms.common.internal.zza
    public final void zzb(ConnectionResult connectionResult) {
        BaseGmsClient.BaseOnConnectionFailedListener baseOnConnectionFailedListener = this.zzf.zzx;
        if (baseOnConnectionFailedListener != null) {
            ((zai) baseOnConnectionFailedListener).zaa.onConnectionFailed(connectionResult);
        }
        System.currentTimeMillis();
    }

    @Override // com.google.android.gms.common.internal.zza
    public final boolean zzd() {
        try {
            IBinder iBinder = this.zze;
            Preconditions.checkNotNull(iBinder);
            String interfaceDescriptor = iBinder.getInterfaceDescriptor();
            BaseGmsClient baseGmsClient = this.zzf;
            if (!baseGmsClient.getServiceDescriptor().equals(interfaceDescriptor)) {
                String serviceDescriptor = baseGmsClient.getServiceDescriptor();
                StringBuilder sb =
                        new StringBuilder(
                                serviceDescriptor.length()
                                        + 34
                                        + String.valueOf(interfaceDescriptor).length());
                sb.append("service descriptor mismatch: ");
                sb.append(serviceDescriptor);
                sb.append(" vs. ");
                sb.append(interfaceDescriptor);
                Log.w("GmsClient", sb.toString());
                return false;
            }
            IInterface createServiceInterface = baseGmsClient.createServiceInterface(this.zze);
            if (createServiceInterface == null
                    || !(BaseGmsClient.zzn(baseGmsClient, 2, 4, createServiceInterface)
                            || BaseGmsClient.zzn(baseGmsClient, 3, 4, createServiceInterface))) {
                return false;
            }
            baseGmsClient.zzB = null;
            BaseGmsClient.BaseConnectionCallbacks baseConnectionCallbacks = baseGmsClient.zzw;
            if (baseConnectionCallbacks == null) {
                return true;
            }
            ((zah) baseConnectionCallbacks).zaa.onConnected();
            return true;
        } catch (RemoteException unused) {
            Log.w("GmsClient", "service probably died");
            return false;
        }
    }
}
