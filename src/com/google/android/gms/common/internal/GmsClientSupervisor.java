package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.ServiceConnection;
import android.os.HandlerThread;

import java.util.HashMap;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class GmsClientSupervisor {
    static HandlerThread zza;
    public static final Object zzc = new Object();
    public static zzr zzd;

    public static zzr getInstance(Context context) {
        synchronized (zzc) {
            try {
                if (zzd == null) {
                    zzd = new zzr(context.getApplicationContext(), context.getMainLooper());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return zzd;
    }

    public static HandlerThread getOrStartHandlerThread() {
        synchronized (zzc) {
            try {
                HandlerThread handlerThread = zza;
                if (handlerThread != null) {
                    return handlerThread;
                }
                HandlerThread handlerThread2 = new HandlerThread("GoogleApiHandler", 9);
                zza = handlerThread2;
                handlerThread2.start();
                return zza;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void zzb(String str, ServiceConnection serviceConnection, boolean z) {
        zzn zznVar = new zzn(str, z);
        zzr zzrVar = (zzr) this;
        Preconditions.checkNotNull(serviceConnection, "ServiceConnection must not be null");
        synchronized (zzrVar.zzb) {
            try {
                zzo zzoVar = (zzo) zzrVar.zzb.get(zznVar);
                if (zzoVar == null) {
                    String zznVar2 = zznVar.toString();
                    StringBuilder sb = new StringBuilder(zznVar2.length() + 50);
                    sb.append("Nonexistent connection status for service config: ");
                    sb.append(zznVar2);
                    throw new IllegalStateException(sb.toString());
                }
                if (!((HashMap) zzoVar.zzb).containsKey(serviceConnection)) {
                    String zznVar3 = zznVar.toString();
                    StringBuilder sb2 = new StringBuilder(zznVar3.length() + 76);
                    sb2.append(
                            "Trying to unbind a GmsServiceConnection  that was not bound before. "
                                + " config=");
                    sb2.append(zznVar3);
                    throw new IllegalStateException(sb2.toString());
                }
                ((HashMap) zzoVar.zzb).remove(serviceConnection);
                if (((HashMap) zzoVar.zzb).isEmpty()) {
                    zzrVar.zzd.sendMessageDelayed(zzrVar.zzd.obtainMessage(0, zznVar), zzrVar.zzg);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public abstract boolean zzc(zzn zznVar, zze zzeVar, String str, Executor executor);
}
