package com.google.android.gms.common.stats;

import android.content.Context;
import android.content.ServiceConnection;

import com.google.android.gms.common.internal.zzo;

import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ConnectionTracker {
    public static final Object zzb = new Object();
    public static volatile ConnectionTracker zzc;
    public ConcurrentHashMap<ServiceConnection, ServiceConnection> zza;

    public final void unbindService(Context context, ServiceConnection serviceConnection) {
        if ((serviceConnection instanceof zzo) || !this.zza.containsKey(serviceConnection)) {
            try {
                context.unbindService(serviceConnection);
            } catch (IllegalArgumentException
                    | IllegalStateException
                    | NoSuchElementException unused) {
            }
        } else {
            try {
                try {
                    context.unbindService(this.zza.get(serviceConnection));
                } catch (IllegalArgumentException
                        | IllegalStateException
                        | NoSuchElementException unused2) {
                }
            } finally {
                this.zza.remove(serviceConnection);
            }
        }
    }
}
