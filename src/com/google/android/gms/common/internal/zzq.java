package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zzq implements Handler.Callback {
    public final /* synthetic */ zzr zza;

    public /* synthetic */ zzq(zzr zzrVar) {
        this.zza = zzrVar;
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        int i = message.what;
        if (i == 0) {
            synchronized (this.zza.zzb) {
                try {
                    zzn zznVar = (zzn) message.obj;
                    zzo zzoVar = (zzo) this.zza.zzb.get(zznVar);
                    if (zzoVar != null && ((HashMap) zzoVar.zzb).isEmpty()) {
                        if (zzoVar.zzd) {
                            zzoVar.zza.zzd.removeMessages(1, zzoVar.zzf);
                            zzr zzrVar = zzoVar.zza;
                            zzrVar.zzf.unbindService(zzrVar.zzc, zzoVar);
                            zzoVar.zzd = false;
                            zzoVar.zzc = 2;
                        }
                        this.zza.zzb.remove(zznVar);
                    }
                } finally {
                }
            }
            return true;
        }
        if (i != 1) {
            return false;
        }
        synchronized (this.zza.zzb) {
            try {
                zzn zznVar2 = (zzn) message.obj;
                zzo zzoVar2 = (zzo) this.zza.zzb.get(zznVar2);
                if (zzoVar2 != null && zzoVar2.zzc == 3) {
                    String valueOf = String.valueOf(zznVar2);
                    StringBuilder sb = new StringBuilder(valueOf.length() + 47);
                    sb.append("Timeout waiting for ServiceConnection callback ");
                    sb.append(valueOf);
                    Log.e("GmsClientSupervisor", sb.toString(), new Exception());
                    ComponentName componentName = zzoVar2.zzg;
                    if (componentName == null) {
                        zznVar2.getClass();
                        componentName = null;
                    }
                    if (componentName == null) {
                        String str = zznVar2.zzc;
                        Preconditions.checkNotNull(str);
                        componentName = new ComponentName(str, "unknown");
                    }
                    zzoVar2.onServiceDisconnected(componentName);
                }
            } finally {
            }
        }
        return true;
    }
}
