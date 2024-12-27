package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zze implements ServiceConnection {
    public final /* synthetic */ BaseGmsClient zza;
    public final int zzb;

    public zze(BaseGmsClient baseGmsClient, int i) {
        this.zza = baseGmsClient;
        this.zzb = i;
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        int i;
        int i2;
        if (iBinder == null) {
            BaseGmsClient baseGmsClient = this.zza;
            synchronized (baseGmsClient.zzp) {
                i = baseGmsClient.zzv;
            }
            if (i == 3) {
                baseGmsClient.zzC = true;
                i2 = 5;
            } else {
                i2 = 4;
            }
            Handler handler = baseGmsClient.zzb;
            handler.sendMessage(handler.obtainMessage(i2, baseGmsClient.zzd.get(), 16));
            return;
        }
        synchronized (this.zza.zzq) {
            try {
                BaseGmsClient baseGmsClient2 = this.zza;
                IInterface queryLocalInterface =
                        iBinder.queryLocalInterface(
                                "com.google.android.gms.common.internal.IGmsServiceBroker");
                baseGmsClient2.zzr =
                        (queryLocalInterface == null || !(queryLocalInterface instanceof zzac))
                                ? new zzac(iBinder)
                                : (zzac) queryLocalInterface;
            } catch (Throwable th) {
                throw th;
            }
        }
        BaseGmsClient baseGmsClient3 = this.zza;
        int i3 = this.zzb;
        baseGmsClient3.getClass();
        zzg zzgVar = new zzg(baseGmsClient3, 0);
        Handler handler2 = baseGmsClient3.zzb;
        handler2.sendMessage(handler2.obtainMessage(7, i3, -1, zzgVar));
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        BaseGmsClient baseGmsClient;
        synchronized (this.zza.zzq) {
            baseGmsClient = this.zza;
            baseGmsClient.zzr = null;
        }
        Handler handler = baseGmsClient.zzb;
        handler.sendMessage(handler.obtainMessage(6, this.zzb, 1));
    }
}
